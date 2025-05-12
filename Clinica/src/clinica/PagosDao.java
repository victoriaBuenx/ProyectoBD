/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package clinica;

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
        String sqlPago = "SELECT t.MontoTotal, IFNULL(SUM(p.MontoPagado), 0) AS TotalPagado " +
                         "FROM Tratamientos t " +
                         "LEFT JOIN Pagos p ON t.idTratamiento = p.idTratamiento " +
                         "WHERE t.idTratamiento = ? " +
                         "GROUP BY t.idTratamiento";
        
        String sqlInsertPago = "INSERT INTO Pagos (idTratamiento, MetodoPago, FechaPago, ModalidadPago, MontoPagado) " +
                               "VALUES (?, ?, ?, ?, ?)";
    
        PreparedStatement psSelect = null;
        PreparedStatement psInsert = null;
        Connection con = null;  
    
        try {
            Conexion conexion = new Conexion();  
            con = conexion.conexion();  
    
            // Verificar el monto total y el monto ya pagado
            psSelect = con.prepareStatement(sqlPago);
            psSelect.setInt(1, idTratamiento);
            ResultSet rs = psSelect.executeQuery();
    
            if (rs.next()) {
                double montoTotal = rs.getDouble("MontoTotal");
                double totalPagado = rs.getDouble("TotalPagado");
    
                if ((totalPagado + montoPagado) > montoTotal) {
                    JOptionPane.showMessageDialog(null, "El pago excede el monto total del tratamiento.", "Error", JOptionPane.ERROR_MESSAGE);
                    return false;
                }
            }
    
            psInsert = con.prepareStatement(sqlInsertPago);
            psInsert.setInt(1, idTratamiento);
            psInsert.setString(2, metodoPago);
            psInsert.setDate(3, new java.sql.Date(fechaPago.getTime())); 
            psInsert.setString(4, modalidadPago);
            psInsert.setInt(5, montoPagado);
    
            int filasAfectadas = psInsert.executeUpdate();
    
            if (filasAfectadas > 0) {
                JOptionPane.showMessageDialog(null, "Pago registrado con éxito.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
                return true;
            } else {
                JOptionPane.showMessageDialog(null, "No se pudo registrar el pago.", "Error", JOptionPane.ERROR_MESSAGE);
                return false;
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al insertar pago", "Error SQL", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
            return false;
        } finally {
            try {
                if (psSelect != null) psSelect.close();
                if (psInsert != null) psInsert.close();
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
            JOptionPane.showMessageDialog(null, "Error al actualizar pago", "Error SQL", JOptionPane.ERROR_MESSAGE);
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
            JOptionPane.showMessageDialog(null, "Error al eliminar pago", "Error SQL", JOptionPane.ERROR_MESSAGE);
            return false;
        }
    }
    
    public void buscarPagos(String textoBusqueda, JTable tabla) {
        String sql = "SELECT p.idPago, " +
                     "CONCAT(t.idTratamiento, ' - ', t.Nombre) AS Tratamiento, " +
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
                "ID Pago", "Tratamiento", "Método de Pago", "Fecha de Pago", "Modalidad", "Monto Pagado"
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
