/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import model.Producto;
import db.Conexion;
import java.util.ArrayList;
import java.util.List;
import java.sql.ResultSet;

/**
 *
 * @author Oscar Josue
 */

public class ProductoDAO {
    Conexion cn = new Conexion();
    Connection con;
    PreparedStatement ps;
     ResultSet rs;
     
    public boolean registrarProducto(Producto pro) {
        // Nota que NO incluimos id_producto porque es AUTO_INCREMENT
        String sql = "INSERT INTO Producto (nombre, marca, categoria, talla, precio, entrada, salida) VALUES (?,?,?,?,?,?,?)";
        try {
            con = cn.conectar();
            ps = con.prepareStatement(sql);
            ps.setString(1, pro.getNombre());
            ps.setString(2, pro.getMarca());
            ps.setString(3, pro.getCategoria());
            ps.setString(4, pro.getTalla());
            ps.setDouble(5, pro.getPrecio());
            ps.setInt(6, pro.getEntrada());
            ps.setInt(7, pro.getSalida());
            ps.execute();
            return true;
        } catch (SQLException e) {
            System.out.println("Error en DAO: " + e.toString());
            return false;
        } finally {
            try {
                con.close();
            } catch (SQLException e) {
                System.out.println(e.toString());
            }
        }
    }
    
    public List ListarProductos() {
    List<Producto> ListaPro = new ArrayList();
    String sql = "SELECT * FROM Producto"; // Tu tabla de SQLyog
    try {
        con = cn.conectar();
        ps = con.prepareStatement(sql);
        rs = ps.executeQuery();
        while (rs.next()) {
            Producto pro = new Producto();
            pro.setId(rs.getInt("id_producto")); // Ojo con los nombres de tu SQL
            pro.setNombre(rs.getString("nombre"));
            pro.setMarca(rs.getString("marca"));
            pro.setCategoria(rs.getString("categoria"));
            pro.setTalla(rs.getString("talla"));
            pro.setPrecio(rs.getDouble("precio"));
            pro.setEntrada(rs.getInt("entrada"));
            pro.setSalida(rs.getInt("salida"));
            ListaPro.add(pro);
        }
    } catch (SQLException e) {
        System.out.println(e.toString());
    }
    return ListaPro;
}
    
    public boolean EliminarProducto(int id) {
    String sql = "DELETE FROM Producto WHERE id_producto = ?";
    try {
        con = cn.conectar();
        ps = con.prepareStatement(sql);
        ps.setInt(1, id);
        ps.execute();
        return true;
    } catch (SQLException e) {
        System.out.println(e.toString());
        return false;
    } finally {
        try {
            con.close();
        } catch (SQLException e) {
            System.out.println(e.toString());
        }
    }
}
    
    public boolean ModificarProducto(Producto pro) {
    // Usamos el ID en el WHERE para saber cuál registro cambiar
    String sql = "UPDATE Producto SET nombre=?, marca=?, categoria=?, talla=?, precio=?, entrada=?, salida=? WHERE id_producto=?";
    try {
        con = cn.conectar();
        ps = con.prepareStatement(sql);
        ps.setString(1, pro.getNombre());
        ps.setString(2, pro.getMarca());
        ps.setString(3, pro.getCategoria());
        ps.setString(4, pro.getTalla());
        ps.setDouble(5, pro.getPrecio());
        ps.setInt(6, pro.getEntrada());
        ps.setInt(7, pro.getSalida());
        ps.setInt(8, pro.getId()); // El ID va al final para el WHERE
        ps.execute();
        return true;
    } catch (SQLException e) {
        System.out.println("Error al modificar: " + e.toString());
        return false;
    } finally {
        try {
            con.close();
        } catch (SQLException e) {
            System.out.println(e.toString());
        }
    }
}
    public List<Producto> BuscarProducto(String valor) {
    List<Producto> listaPro = new ArrayList();
    // Buscamos coincidencia en nombre, marca, categoria O talla
    String sql = "SELECT * FROM Producto WHERE nombre LIKE ? OR marca LIKE ? OR categoria LIKE ? OR talla LIKE ?";
    try {
        con = cn.conectar();
        ps = con.prepareStatement(sql);
        // El % permite que busque palabras que "contengan" lo que escribas
        ps.setString(1, "%" + valor + "%");
        ps.setString(2, "%" + valor + "%");
        ps.setString(3, "%" + valor + "%");
        ps.setString(4, "%" + valor + "%");
        rs = ps.executeQuery();
        while (rs.next()) {
            Producto pro = new Producto();
            pro.setId(rs.getInt("id_producto"));
            pro.setNombre(rs.getString("nombre"));
            pro.setMarca(rs.getString("marca"));
            pro.setCategoria(rs.getString("categoria"));
            pro.setTalla(rs.getString("talla"));
            pro.setPrecio(rs.getDouble("precio"));
            pro.setEntrada(rs.getInt("entrada"));
            pro.setSalida(rs.getInt("salida"));
            listaPro.add(pro);
        }
    } catch (SQLException e) {
        System.out.println(e.toString());
    }
    return listaPro;
}
}
