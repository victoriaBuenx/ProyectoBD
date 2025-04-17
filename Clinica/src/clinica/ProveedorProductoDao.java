/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package clinica;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import javax.swing.JOptionPane;

/**
 *
 * @author victo
 */
public class ProveedorProductoDao {
    public boolean insertarProveedorProducto (int idProveedor, int idProducto, int CantidadDisponible, Date FechaRegistro) {
        String sql = "INSERT INTO ProveedorProducto (idProveedor, idProducto, CantidadDisponible, FechaRegistro) " +
                     "VALUES (?, ?, ?, ?)";

        PreparedStatement ps = null;
        Connection con = null;  

        try {
            Conexion conexion = new Conexion();  
            con = conexion.conexion();  

            ps = con.prepareStatement(sql);
            ps.setInt(1, idProveedor);
            ps.setInt(2, idProducto);
            ps.setInt(3, CantidadDisponible); 
            ps.setDate(4, new java.sql.Date(FechaRegistro.getTime())); 

            int filasAfectadas = ps.executeUpdate();

            if (filasAfectadas > 0) {
                return true;
            } else {
                JOptionPane.showMessageDialog(null, "No se pudo registrar el ProveedorProducto.");
                return false;
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al insertar proveedorProducto: " + e.getMessage(), "Error SQL", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
            return false;
        } finally {
            try {
                if (ps != null) ps.close();
                if (con != null) con.close();
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(null, "Error al cerrar los recursos: " + e.getMessage());
            }
        }
    }
    
    public boolean actualizarProveedorProducto(int idProveedorProducto, int idProveedor, int idProducto, int CantidadDisponible, Date FechaRegistro) {
        String sql = "UPDATE ProveedorProducto SET idProveedor = ?, idProducto = ?, CantidadDisponible = ?, FechaRegistro = ? WHERE idProveedorProducto = ?";

        try (Connection con = new Conexion().conexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, idProveedor);
            ps.setInt(2, idProducto);
            ps.setInt(3, CantidadDisponible);
            ps.setDate(4, new java.sql.Date(FechaRegistro.getTime()));
            ps.setInt(5, idProveedorProducto);

            int filasAfectadas = ps.executeUpdate();

            return filasAfectadas > 0;
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al actualizar ProveedorProducto: " + e.getMessage(), "Error SQL", JOptionPane.ERROR_MESSAGE);
            return false;
        }
    }
    
    public int obtenerIdProveedorProducto(int idProveedor, int idProducto) {
        String sql = "SELECT idProveedorProducto FROM ProveedorProducto WHERE idProveedor = ? AND idProducto = ?";

        try (Connection con = new Conexion().conexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, idProveedor);
            ps.setInt(2, idProducto);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt("idProveedorProducto");
                }
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al obtener idProveedorProducto: " + e.getMessage(), "Error SQL", JOptionPane.ERROR_MESSAGE);
        }

        return -1; // Retorna -1 si no se encuentra el idPacienteTratamiento
    }
    
    public boolean eliminarProveedorProducto(int idProveedorProducto) {
        String sql = "DELETE FROM Pacientes WHERE idProveedor = ?";

        try (Connection con = new Conexion().conexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, idProveedorProducto);

            int filasAfectadas = ps.executeUpdate();

            if (filasAfectadas > 0) {
                JOptionPane.showMessageDialog(null, "ProveedorProducto eliminado con éxito.");
                return true;
            } else {
                JOptionPane.showMessageDialog(null, "No se pudo eliminar el proveedorProducto.");
                return false;
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al eliminar proveedorProducto: " + e.getMessage(), "Error SQL", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
            return false;
        }
    }
    
}
