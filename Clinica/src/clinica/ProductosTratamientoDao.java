/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package clinica;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.JOptionPane;

/**
 *
 * @author victo
 */
public class ProductosTratamientoDao {
    
    public int insertarProductosTratamiento(int idProducto, int idTratamiento, int Cantidad) {
        String sql = "INSERT INTO productostratamiento (idProducto, idTratamiento, Cantidad) VALUES (?, ?, ?)";
        int idTratamientoGenerado = -1; 

        try (Connection con = new Conexion().conexion();
            PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setInt(1, idProducto);
            ps.setInt(2, idTratamiento);
            ps.setInt(3, Cantidad);

            int filasAfectadas = ps.executeUpdate();

            if (filasAfectadas > 0) {
                ResultSet rs = ps.getGeneratedKeys();
                if (rs.next()) {
                    idTratamientoGenerado = rs.getInt(1); 
                }
                    JOptionPane.showMessageDialog(null, "Productos asignados con éxito.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(null, "No se pudo asignar el producto.",  "Error", JOptionPane.ERROR_MESSAGE);
                }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al asignar producto " + e.getMessage(), "Error SQL", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
        
        return idTratamientoGenerado;
    }
    
    public boolean actualizarProductosTratamiento(int idProductosTratamiento, int idProducto, int idTratamiento, int Cantidad) {
        String sql = "UPDATE productostratamiento SET idProducto = ?, idTratamiento = ?, Cantidad = ?WHERE idProductosTratamiento = ?";

        try (Connection con = new Conexion().conexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, idProducto);
            ps.setInt(2, idTratamiento);
            ps.setInt(3, Cantidad);
            ps.setInt(4, idProductosTratamiento);

            int filasAfectadas = ps.executeUpdate();

            if (filasAfectadas > 0) {
                JOptionPane.showMessageDialog(null, "Producto actualizado con éxito." , "Éxito", JOptionPane.INFORMATION_MESSAGE);
                return true;
            } else {
                JOptionPane.showMessageDialog(null, "No se pudo actualizar el producto.", "Error", JOptionPane.ERROR_MESSAGE);
                return false;
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al actualizar producto", "Error SQL", JOptionPane.ERROR_MESSAGE);
            return false;
        }
    }
    
    public boolean eliminarTratamiento(int idTratamiento) {
        String sql = "DELETE FROM productostratamiento WHERE idProductosTratamiento = ?";
        PreparedStatement ps = null;
        Connection con = null;

        try {
            Conexion conexion = new Conexion();
            con = conexion.conexion();

            ps = con.prepareStatement(sql);
            ps.setInt(1, idTratamiento);

            int filasAfectadas = ps.executeUpdate();

            if (filasAfectadas > 0) {
                JOptionPane.showMessageDialog(null, "Producto eliminado con éxito.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
                return true;
            } else {
                JOptionPane.showMessageDialog(null, "No se pudo eliminar el producto.", "Error", JOptionPane.ERROR_MESSAGE);
                return false;
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al eliminar producto", "Error SQL", JOptionPane.ERROR_MESSAGE);
            //e.printStackTrace();
            return false;
        }
    }
    
}
