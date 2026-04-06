package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import model.Inventario;
import db.Conexion;
/**
 *
 * @author Oscar Josue
 */
public class InventarioDAO {
 Conexion cn = new Conexion();
    Connection con;
    PreparedStatement ps;
    ResultSet rs;

    // MÉTODO PARA GUARDAR (RegistrarInventario)
    public boolean RegistrarInventario(Inventario inv) {
        String sql = "INSERT INTO Inventario (id_producto, stock_actual, ubicacion, fecha_ingreso) VALUES (?,?,?,?)";
        try {
            con = cn.conectar(); // Verifica si es conectar() o getConnection()
            ps = con.prepareStatement(sql);
            ps.setInt(1, inv.getId_producto());
            ps.setInt(2, inv.getStock_total());
            ps.setString(3, inv.getUbicacion());
            ps.setString(4, inv.getNombre_producto()); // Aquí pasamos la fecha
            ps.execute();
            return true;
        } catch (SQLException e) {
            System.out.println(e.toString());
            return false;
        }
    }

    // MÉTODO PARA ELIMINAR (EliminarInventario)
    public boolean EliminarInventario(int id) {
        String sql = "DELETE FROM Inventario WHERE id_inventario = ?";
        try {
            con = cn.conectar();
            ps = con.prepareStatement(sql);
            ps.setInt(1, id);
            ps.execute();
            return true;
        } catch (SQLException e) {
            System.out.println(e.toString());
            return false;
        }
    }

    // MÉTODO PARA MODIFICAR (ModificarInventario)
    public boolean ModificarInventario(Inventario inv) {
        String sql = "UPDATE Inventario SET id_producto=?, stock_actual=?, ubicacion=?, fecha_ingreso=? WHERE id_inventario=?";
        try {
            con = cn.conectar();
            ps = con.prepareStatement(sql);
            ps.setInt(1, inv.getId_producto());
            ps.setInt(2, inv.getStock_total());
            ps.setString(3, inv.getUbicacion());
            ps.setString(4, inv.getNombre_producto());
            ps.setInt(5, inv.getId_inventario());
            ps.execute();
            return true;
        } catch (SQLException e) {
            System.out.println(e.toString());
            return false;
        }
    }

    // MÉTODO PARA LISTAR
    public List ListarInventario() {
        List<Inventario> listaInv = new ArrayList();
        String sql = "SELECT * FROM Inventario";
        try {
            con = cn.conectar();
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                Inventario inv = new Inventario();
                inv.setId_inventario(rs.getInt("id_inventario"));
                inv.setId_producto(rs.getInt("id_producto"));
                inv.setStock_total(rs.getInt("stock_actual"));
                inv.setUbicacion(rs.getString("ubicacion"));
                inv.setNombre_producto(rs.getString("fecha_ingreso"));
                listaInv.add(inv);
            }
        } catch (SQLException e) {
            System.out.println(e.toString());
        }
        return listaInv;
    }
}
