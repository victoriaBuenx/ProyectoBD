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

/**
 *
 * @author victo
 */
public class PacientesTratamientosDao {
    public boolean insertarPacienteTratamiento (int idPaciente, int idTratamiento, Date fechaInicio, Date fechaFin) {
        String sql = "INSERT INTO PacientesTratamientos (idPaciente, idTratamiento, fechaInicio, fechaFin) " +
                     "VALUES (?, ?, ?, ?)";

        PreparedStatement ps = null;
        Connection con = null;  

        try {
            Conexion conexion = new Conexion();  
            con = conexion.conexion();  

            ps = con.prepareStatement(sql);
            ps.setInt(1, idPaciente);
            ps.setInt (2, idTratamiento);
            ps.setDate(3, new java.sql.Date(fechaInicio.getTime())); 
            ps.setDate(4, new java.sql.Date(fechaFin.getTime())); 

            int filasAfectadas = ps.executeUpdate();

            if (filasAfectadas > 0) {
                JOptionPane.showMessageDialog(null, "PacienteTratamiento registrado con éxito.");
                return true;
            } else {
                JOptionPane.showMessageDialog(null, "No se pudo registrar el PacienteTratamiento.");
                return false;
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al insertar pacienteTratamiento: " + e.getMessage(), "Error SQL", JOptionPane.ERROR_MESSAGE);
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
    
    public boolean actualizarPacienteTratamiento(int idPacienteTratamiento, int idPaciente, int idTratamiento, Date fechaInicio, Date fechaFin) {
        String sql = "UPDATE PacientesTratamiento SET idPaciente = ?, idTratamiento = ?, fechaInicio = ?, "
                + "fechaFin = ?, ? WHERE idPaciente = ?";

        try (Connection con = new Conexion().conexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, idPaciente);
            ps.setInt(2, idTratamiento);
            ps.setDate(3, new java.sql.Date(fechaInicio.getTime())); 
            ps.setDate(4, new java.sql.Date(fechaFin.getTime()));
            ps.setInt(5, idPacienteTratamiento);

            int filasAfectadas = ps.executeUpdate();

            if (filasAfectadas > 0) {
                JOptionPane.showMessageDialog(null, "PacienteTratamiento actualizado con éxito.");
                return true;
            } else {
                JOptionPane.showMessageDialog(null, "No se pudo actualizar el pacienteTratamiento.");
                return false;
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al actualizar pacienteTratamiento: " + e.getMessage(), "Error SQL", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
            return false;
        }
    }
    
    public boolean eliminarPaciente(int idPacienteTratamiento) {
        String sql = "DELETE FROM Pacientes WHERE idPaciente = ?";

        try (Connection con = new Conexion().conexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, idPacienteTratamiento);

            int filasAfectadas = ps.executeUpdate();

            if (filasAfectadas > 0) {
                JOptionPane.showMessageDialog(null, "PacienteTratamiento eliminado con éxito.");
                return true;
            } else {
                JOptionPane.showMessageDialog(null, "No se pudo eliminar el pacienteTratamiento.");
                return false;
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al eliminar pacienteTratamiento: " + e.getMessage(), "Error SQL", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
            return false;
        }
    }
    
}
