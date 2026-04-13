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
     
public boolean registrarProducto(model.Producto pro) {
    String sql = "INSERT INTO producto (nombre, marca, categoria, talla, precio, entrada, salida) VALUES (?,?,?,?,?,?,?)";
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
        return true; // ESTO ES LO QUE QUITA EL ERROR ROJO
    } catch (SQLException e) {
        System.out.println(e.toString());
        return false;
    }
}

public int obtenerUltimoId() {
    // Esta consulta busca el ID más alto (el más reciente) de tu tabla
    String sql = "SELECT MAX(id_producto) FROM producto";
    int id = 0;
    try {
        con = cn.conectar();
        ps = con.prepareStatement(sql);
        rs = ps.executeQuery();
        if (rs.next()) {
            id = rs.getInt(1);
        }
    } catch (SQLException e) {
        System.out.println("Error al obtener ID: " + e.toString());
    }
    return id;
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
    
    public int IdProducto() {
    int id = 0;
    String sql = "SELECT MAX(id_producto) FROM producto";
    try {
        con = cn.conectar();
        ps = con.prepareStatement(sql);
        rs = ps.executeQuery();
        if (rs.next()) {
            id = rs.getInt(1);
        }
    } catch (SQLException e) {
        System.out.println(e.toString());
    }
    return id;
}
    
public boolean modificarProducto(Producto pro) {
    // Consulta 1: Actualiza la ficha del producto
    String sqlProd = "UPDATE producto SET nombre=?, marca=?, categoria=?, talla=?, precio=?, entrada=?, salida=? WHERE id_producto=?";
    
    // Consulta 2: Actualiza los movimientos existentes en el inventario para ese producto
    // Nota: Esto asume que quieres sincronizar la 'cantidad' del movimiento más reciente
    String sqlInv = "UPDATE inventario SET cantidad=? WHERE id_producto=? AND Tipo=?";

    try {
        con = cn.conectar();
        con.setAutoCommit(false); // Iniciamos una transacción para que se hagan ambos o ninguno

        // EJECUTAR ACTUALIZACIÓN DE PRODUCTO
        ps = con.prepareStatement(sqlProd);
        ps.setString(1, pro.getNombre());
        ps.setString(2, pro.getMarca());
        ps.setString(3, pro.getCategoria()); // Verifica si es getCategoria o get轉Categoria como en tu captura
        ps.setString(4, pro.getTalla());
        ps.setDouble(5, pro.getPrecio());
        ps.setInt(6, pro.getEntrada());
        ps.setInt(7, pro.getSalida());
        ps.setInt(8, pro.getId());
        ps.executeUpdate();

        // EJECUTAR ACTUALIZACIÓN DE INVENTARIO (Sincroniza Entradas)
        ps = con.prepareStatement(sqlInv);
        ps.setInt(1, pro.getEntrada());
        ps.setInt(2, pro.getId());
        ps.setString(3, "ENTRADA");
        ps.executeUpdate();

        con.commit(); // Guardamos ambos cambios
        return true;
        
    } catch (SQLException e) {
        try { con.rollback(); } catch (SQLException ex) { System.out.println(ex.toString()); }
        System.out.println("Error en modificación en cascada: " + e.toString());
        return false;
    } finally {
        try { if (con != null) con.close(); } catch (SQLException ex) { System.out.println(ex.toString()); }
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
 public boolean eliminarProducto(int id) {
    // CAMBIO CLAVE: Usa id_producto en lugar de id
    String sql = "DELETE FROM producto WHERE id_producto = ?"; 
    try {
        con = cn.conectar();
        ps = con.prepareStatement(sql);
        ps.setInt(1, id);
        int res = ps.executeUpdate(); // Usar executeUpdate para confirmar el borrado
        return res > 0; 
    } catch (SQLException e) {
        System.out.println("Error en Eliminar Producto: " + e.toString());
        return false;
    } finally {
        try { if (con != null) con.close(); } catch (SQLException e) { /* ... */ }
    }
}
}
