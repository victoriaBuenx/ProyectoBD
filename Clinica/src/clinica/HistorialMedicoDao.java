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
                ps.setString(5, observaciones);  // Si hay observaciones, las insertamos
            } else {
                ps.setNull(5, Types.VARCHAR);  // Si no hay observaciones, insertamos NULL
            }

            ps.setDate(6, new java.sql.Date(ultimaActualizacion.getTime()));

            int rowsAffected = ps.executeUpdate();
            return rowsAffected > 0;  // Si se insertaron filas, la inserción fue exitosa

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error al insertar historial médico: " + ex.getMessage(), "Error SQL", JOptionPane.ERROR_MESSAGE);
            return false;
        }
    }
    
}
