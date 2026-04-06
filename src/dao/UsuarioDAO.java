/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;
import db.Conexion;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import model.Usuario;

/**
 *
 * @author Oscar Josue
 */
public class UsuarioDAO {
    
    Conexion cn = new Conexion();
    Connection con;
    PreparedStatement ps;
    ResultSet rs;
     public Usuario validarLogin(String username, String contrasena) {
        Usuario usuario = null;

        String sql = "SELECT * FROM Usuario WHERE username = ? AND contrasena = ?";

        try {
            Connection con = Conexion.conectar();
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, username);
            ps.setString(2, contrasena);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                usuario = new Usuario();
                usuario.setIdUsuario(rs.getInt("id_usuario"));
                usuario.setNombreCompleto(rs.getString("nombre_completo"));
                usuario.setUsername(rs.getString("username"));
                usuario.setContrasena(rs.getString("contrasena"));
                usuario.setRol(rs.getString("rol"));
            }

            rs.close();
            ps.close();
            con.close();

        } catch (SQLException e) {
            System.out.println("Error al validar login: " + e.getMessage());
        }

        return usuario;
    }
     
     
     
     public boolean RegistrarUsuario(Usuario user) {
    String sql = "INSERT INTO Usuario (nombre_completo, username, contrasena, rol) VALUES (?,?,?,?)";
    try {
        con = cn.conectar();
        ps = con.prepareStatement(sql);
        ps.setString(1, user.getNombreCompleto());
        ps.setString(2, user.getUsername());
        ps.setString(3, user.getContrasena());
        ps.setString(4, user.getRol());
        ps.execute();
        return true;
    } catch (SQLException e) {
        System.out.println("Error al registrar: " + e.toString());
        return false;
    }
}
     
     public List ListarUsuarios() {
    List<Usuario> listaU = new ArrayList();
    String sql = "SELECT * FROM Usuario";
    try {
        con = cn.conectar();
        ps = con.prepareStatement(sql);
        rs = ps.executeQuery();
        while (rs.next()) {
            Usuario u = new Usuario();
            u.setIdUsuario(rs.getInt("id_usuario"));
            u.setNombreCompleto(rs.getString("nombre_completo"));
            u.setUsername(rs.getString("username"));
            u.setContrasena(rs.getString("contrasena"));
            u.setRol(rs.getString("rol"));
            listaU.add(u);
        }
    } catch (SQLException e) {
        System.out.println("Error al listar usuarios: " + e.toString());
    }
    return listaU;
}
     
     public boolean EliminarUsuario(int id) {
    String sql = "DELETE FROM Usuario WHERE id_usuario = ?";
    try {
        con = cn.conectar();
        ps = con.prepareStatement(sql);
        ps.setInt(1, id);
        ps.execute();
        return true;
    } catch (SQLException e) {
        System.out.println("Error al eliminar usuario: " + e.toString());
        return false;
    }
}

// --- MÉTODO PARA MODIFICAR ---
public boolean ModificarUsuario(Usuario user) {
    String sql = "UPDATE Usuario SET nombre_completo=?, username=?, contrasena=?, rol=? WHERE id_usuario=?";
    try {
        con = cn.conectar();
        ps = con.prepareStatement(sql);
        ps.setString(1, user.getNombreCompleto());
        ps.setString(2, user.getUsername());
        ps.setString(3, user.getContrasena());
        ps.setString(4, user.getRol());
        ps.setInt(5, user.getIdUsuario());
        ps.execute();
        return true;
    } catch (SQLException e) {
        System.out.println("Error al modificar usuario: " + e.toString());
        return false;
    }
}
     
}
