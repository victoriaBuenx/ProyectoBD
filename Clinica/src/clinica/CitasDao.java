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
public class CitasDao {
   
    public boolean insertarCita(int idPaciente, int idDentista, Date fecha, String hora, String motivo, String notas) {
        String sql = "INSERT INTO Citas (idPaciente, idDentista, Fecha, Hora, Motivo, Notas) VALUES (?, ?, ?, ?, ?, ?)";

        PreparedStatement ps = null;
        Connection con = null;

        try {
            Conexion conexion = new Conexion();
            con = conexion.conexion();

            ps = con.prepareStatement(sql);
            ps.setInt(1, idPaciente);
            ps.setInt(2, idDentista);
            ps.setDate(3, new java.sql.Date(fecha.getTime()));
            ps.setString(4, hora);
            ps.setString(5, motivo);
            ps.setString(6, notas);

            int filasAfectadas = ps.executeUpdate();

            if (filasAfectadas > 0) {
                JOptionPane.showMessageDialog(null, "Cita registrada con éxito.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
                return true;
            } else {
                JOptionPane.showMessageDialog(null, "No se pudo registrar la cita.", "Error", JOptionPane.ERROR_MESSAGE);
                return false;
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al insertar cita: " + e.getMessage(), "Error SQL", JOptionPane.ERROR_MESSAGE);
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
    
    public boolean actualizarCita(int idCita, int idPaciente, int idDentista, Date fecha, String hora, String motivo, String notas) {
        String sql = "UPDATE Citas SET idPaciente = ?, idDentista = ?, Fecha = ?, Hora = ?, Motivo = ?, Notas = ? WHERE idCita = ?";

        try (Connection con = new Conexion().conexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, idPaciente);
            ps.setInt(2, idDentista);
            ps.setDate(3, new java.sql.Date(fecha.getTime()));
            ps.setString(4, hora);
            ps.setString(5, motivo);
            ps.setString(6, notas);
            ps.setInt(7, idCita);

            int filasAfectadas = ps.executeUpdate();

            if (filasAfectadas > 0) {
                JOptionPane.showMessageDialog(null, "Cita actualizada correctamente.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
                return true;
            } else {
                JOptionPane.showMessageDialog(null, "No se pudo actualizar la cita.", "Error", JOptionPane.ERROR_MESSAGE);
                return false;
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al actualizar la cita: " + e.getMessage(), "Error SQL", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
            return false;
        }
    }

    
    public boolean eliminarCita(int idCita) {
        String sql = "DELETE FROM Citas WHERE idCita = ?";
        PreparedStatement ps = null;
        Connection con = null;

        try {
            Conexion conexion = new Conexion();
            con = conexion.conexion();
            ps = con.prepareStatement(sql);
            ps.setInt(1, idCita);

            int filasAfectadas = ps.executeUpdate();

            if (filasAfectadas > 0) {
                JOptionPane.showMessageDialog(null, "Cita eliminada con éxito.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
                return true;
            } else {
                JOptionPane.showMessageDialog(null, "No se encontró la cita a eliminar.", "Error", JOptionPane.ERROR_MESSAGE);
                return false;
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al eliminar cita: " + e.getMessage(), "Error SQL", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
            return false;
        }
    }  
}
