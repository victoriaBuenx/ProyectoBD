/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package clinica;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Date;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import java.sql.*;

/**
 *
 * @author victo
 */
public class PagosDao {
    
    public boolean insertarPagos(int idTratamiento, String metodoPago, Date fechaPago, String modalidadPago, int montoPagado) {
        String sql = "INSERT INTO Pagos (idTratamiento, MetodoPago, FechaPago, ModalidadPago, MontoPagado) " +
                     "VALUES (?, ?, ?, ?, ?)";

        PreparedStatement ps = null;
        Connection con = null;  

        try {
            Conexion conexion = new Conexion();  
            con = conexion.conexion();  

            ps = con.prepareStatement(sql);
            ps.setInt(1, idTratamiento);
            ps.setString(2, metodoPago);
            ps.setDate(3, new java.sql.Date(fechaPago.getTime())); 
            ps.setString(4, modalidadPago);
            ps.setInt(5, montoPagado);

            int filasAfectadas = ps.executeUpdate();

            if (filasAfectadas > 0) {
                JOptionPane.showMessageDialog(null, "Pago registrado con éxito.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
                return true;
            } else {
                JOptionPane.showMessageDialog(null, "No se pudo registrar el pago.", "Error", JOptionPane.ERROR_MESSAGE);
                return false;
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al insertar pago: " + e.getMessage(), "Error SQL", JOptionPane.ERROR_MESSAGE);
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
    
    public boolean actualizarPago(int idPago, int idTratamiento, String metodoPago, Date fechaPago, String modalidadPago, int montoPagado) {
        String sql = "UPDATE Pagos SET idTratamiento = ?, MetodoPago = ?, FechaPago = ?, ModalidadPago = ?, MontoPagado = ? WHERE idPago = ?";

        try (Connection con = new Conexion().conexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, idTratamiento);
            ps.setString(2, metodoPago);
            ps.setDate(3, new java.sql.Date(fechaPago.getTime()));
            ps.setString(4, modalidadPago);
            ps.setInt(5, montoPagado);
            ps.setInt(6, idPago);

            int filasAfectadas = ps.executeUpdate();
            if (filasAfectadas > 0) {
                JOptionPane.showMessageDialog(null, "Pago actualizado con éxito." , "Éxito", JOptionPane.INFORMATION_MESSAGE);
                return true;
            } else {
                JOptionPane.showMessageDialog(null, "No se pudo actualizar el pago.", "Error", JOptionPane.ERROR_MESSAGE);
                return false;
            }     
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al actualizar pago: " + e.getMessage(), "Error SQL", JOptionPane.ERROR_MESSAGE);
            return false;
        }
    }
    
    public boolean eliminarPago(int idPago) {
        String sql = "DELETE FROM Pagos WHERE idPago = ?";

        try (Connection con = new Conexion().conexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, idPago);

            int filasAfectadas = ps.executeUpdate();
            if (filasAfectadas > 0) {
                JOptionPane.showMessageDialog(null, "Pago eliminado con éxito." ,"Éxito", JOptionPane.INFORMATION_MESSAGE);
                return true;
            } else {
                JOptionPane.showMessageDialog(null, "No se pudo eliminar el pago.", "Error", JOptionPane.ERROR_MESSAGE);
                return false;
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al eliminar pago: " + e.getMessage(), "Error SQL", JOptionPane.ERROR_MESSAGE);
            return false;
        }
    }
    
    public void buscarPagos(String textoBusqueda, JTable tabla) {
        String sql = "SELECT p.idPago, " +
                     "t.Nombre AS Tratamiento, " +
                     "p.MetodoPago, p.FechaPago, p.ModalidadPago, p.MontoPagado " +
                     "FROM Pagos p " +
                     "JOIN Tratamientos t ON p.idTratamiento = t.idTratamiento " +
                     "WHERE CONCAT(p.idPago, ' ', t.Nombre, ' ', p.MetodoPago, ' ', p.FechaPago, ' ', p.ModalidadPago, ' ', p.MontoPagado) LIKE ?";

        try (Connection con = new Conexion().conexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, "%" + textoBusqueda + "%");
            ResultSet rs = ps.executeQuery();

            DefaultTableModel modelo = new DefaultTableModel();
            modelo.setColumnIdentifiers(new Object[]{
                "id", "Tratamiento", "MetodoPago", "FechaPago", "ModalidadPago", "MontoPagado"
            });

            while (rs.next()) {
                modelo.addRow(new Object[]{
                    rs.getInt("idPago"),
                    rs.getString("Tratamiento"),
                    rs.getString("MetodoPago"),
                    rs.getDate("FechaPago"),
                    rs.getString("ModalidadPago"),
                    rs.getDouble("MontoPagado")
                });
            }

            tabla.setModel(modelo);
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al buscar pagos: " + e.getMessage(), "Error SQL", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }   
}
