/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package clinica;


import clinica.Conexion;

import java.sql.*;
import javax.swing.JOptionPane;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Date;
/**
 *
 * @author victo
 */
public class HistorialMedicoDao {
    
    public boolean insertarHistorialMedico(int idPaciente, String alergias, String enfermedades, String medicacion, String observaciones, 
            Date ultimaActualizacion) {
        String sql = "INSERT INTO HistorialMedico (idPaciente, Alergias, Enfermedades, Medicacion, Observaciones, UltimaActualizacion) " +
                     "VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection con = new Conexion().conexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, idPaciente);
            ps.setString(2, alergias);
            ps.setString(3, enfermedades);
            ps.setString(4, medicacion);

            if (observaciones != null && !observaciones.isEmpty()) {
                ps.setString(5, observaciones);  
            } else {
                ps.setNull(5, Types.VARCHAR); 
            }

            ps.setDate(6, new java.sql.Date(ultimaActualizacion.getTime()));

            int filasAfectadas = ps.executeUpdate();

            if (filasAfectadas > 0) {
                JOptionPane.showMessageDialog(null, "Historial médico registrado con éxito.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
                return true;
            } else {
                JOptionPane.showMessageDialog(null, "No se pudo registrar el historial médico.", "Error", JOptionPane.ERROR_MESSAGE);
                return false;
            }

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error al insertar historial médico: " + ex.getMessage(), "Error SQL", JOptionPane.ERROR_MESSAGE);
            return false;
        }
    }
    
        public boolean actualizarHistorialMedico(int idHistorial, int idPaciente, String alergias, String enfermedades, 
                                           String medicacion, String observaciones, Date ultimaActualizacion) {
            String sql = "UPDATE HistorialMedico SET idPaciente = ?, Alergias = ?, Enfermedades = ?, Medicacion = ?, " +
                         "Observaciones = ?, UltimaActualizacion = ? WHERE idHistorialMedico = ?";

            try (Connection con = new Conexion().conexion();
                 PreparedStatement ps = con.prepareStatement(sql)) {

                ps.setInt(1, idPaciente);
                ps.setString(2, alergias);
                ps.setString(3, enfermedades);
                ps.setString(4, medicacion);

                if (observaciones != null && !observaciones.isEmpty()) {
                    ps.setString(5, observaciones);  
                } else {
                    ps.setNull(5, Types.VARCHAR);  
                }

                ps.setDate(6, new java.sql.Date(ultimaActualizacion.getTime()));
                ps.setInt(7, idHistorial);

                int filasAfectadas = ps.executeUpdate();
                 
                if (filasAfectadas > 0) {
                    JOptionPane.showMessageDialog(null, "Historial Médico actualizado con éxito.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
                    return true;
                } else {
                    JOptionPane.showMessageDialog(null, "No se pudo actualizar el historial médico.", "Error", JOptionPane.ERROR_MESSAGE);
                    return false;
                }

            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(null, "Error al actualizar historial médico: " + ex.getMessage(), "Error SQL", JOptionPane.ERROR_MESSAGE);
                return false;
            }
        }

    public boolean eliminarHistorialMedico(int idHistorial) {
        String sql = "DELETE FROM HistorialMedico WHERE idHistorialMedico = ?";

        try (Connection con = new Conexion().conexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, idHistorial);

            int filasAfectadas = ps.executeUpdate();

            if (filasAfectadas > 0) {
                JOptionPane.showMessageDialog(null, "Historial médico eliminado con éxito." ,"Éxito", JOptionPane.INFORMATION_MESSAGE);
                return true;
            } else {
                JOptionPane.showMessageDialog(null, "No se pudo eliminar el historial médico.", "Error", JOptionPane.ERROR_MESSAGE);
                return false;
            } 

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error al eliminar historial médico: " + ex.getMessage(), "Error SQL", JOptionPane.ERROR_MESSAGE);
            return false;
        }
    }

}
