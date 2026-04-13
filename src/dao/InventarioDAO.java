package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import model.Inventario;
import db.Conexion;
import javax.swing.table.DefaultTableModel;
/**
 *
 * @author Oscar Josue
 */
public class InventarioDAO {
    Conexion cn = new Conexion();
    Connection con;
    PreparedStatement ps;
    ResultSet rs;

public boolean RegistrarInventario(Inventario inv) {
    // IMPORTANTE: Verifica si tu tabla es 'inventario' o 'inventarios'
    // Y si tu tabla de productos es 'producto' o 'productos'
    String sql = "INSERT INTO inventario (id_producto, cantidad, ubicacion, fecha_ingreso, Tipo) VALUES (?, ?, ?, ?, ?)";
    try {
        con = cn.conectar();
        ps = con.prepareStatement(sql);
        ps.setInt(1, inv.getId_producto());
        ps.setInt(2, inv.getCantidad());
        ps.setString(3, inv.getUbicacion());
        ps.setString(4, inv.getFecha());
        ps.setString(5, inv.getTipo());
        ps.execute();
        return true;
    } catch (SQLException e) {
        System.out.println("Error al registrar: " + e.toString());
        return false;
    }
}

public List ListarInventario() {
    List<Inventario> listaInv = new ArrayList();
    
    // CORRECCIÓN: Usamos id_producto en ambas tablas para que el JOIN conecte
    String sql = "SELECT i.*, p.nombre FROM inventario i " +
                 "INNER JOIN producto p ON i.id_producto = p.id_producto " + 
                 "ORDER BY i.id_inventario DESC";
                 
    try {
        con = cn.conectar();
        ps = con.prepareStatement(sql);
        rs = ps.executeQuery();
        
        while (rs.next()) {
            Inventario inv = new Inventario();
            inv.setId_inventario(rs.getInt("id_inventario"));
            inv.setId_producto(rs.getInt("id_producto"));
            inv.setCantidad(rs.getInt("cantidad"));
            inv.setUbicacion(rs.getString("ubicacion"));
            inv.setTipo(rs.getString("Tipo"));
            inv.setFecha(rs.getString("fecha_ingreso"));
            
            // Si le pusiste la variable nombrePro a tu clase Inventario:
            // inv.setNombrePro(rs.getString("nombre")); 
            
            listaInv.add(inv);
        }
    } catch (SQLException e) {
        System.out.println("Error al listar inventario: " + e.toString());
    }
    return listaInv;
}

   public boolean EliminarInventarioPorProducto(int id_producto) {
    // Asegúrate de que el nombre de la columna aquí también coincida
    String sql = "DELETE FROM inventario WHERE id_producto = ?";
    try {
        con = cn.conectar();
        ps = con.prepareStatement(sql);
        ps.setInt(1, id_producto);
        ps.executeUpdate();
        return true; // Retornamos true aunque no haya historial para no bloquear el proceso
    } catch (SQLException e) {
        System.out.println("Error en InventarioDAO: " + e.toString());
        return false;
    } finally {
        try { if (con != null) con.close(); } catch (SQLException e) { /* ... */ }
    }
}
   
public boolean ActualizarInventario(Inventario inv) {
    // SQL que busca por id_producto para sobreescribir y evitar duplicados
    String sql = "UPDATE inventario SET cantidad=?, tipo=?, fecha=NOW() WHERE id_producto=?";
    try {
        con = cn.conectar();
        ps = con.prepareStatement(sql);
        ps.setInt(1, inv.getCantidad());
        ps.setString(2, inv.getTipo());
        ps.setInt(3, inv.getId_producto());
        ps.execute();
        return true;
    } catch (SQLException e) {
        System.out.println("Error: " + e.toString());
        return false;
    }
}
   }


