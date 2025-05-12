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
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
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
            JOptionPane.showMessageDialog(null, "Error al insertar cita", "Error SQL", JOptionPane.ERROR_MESSAGE);
            //e.printStackTrace();
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
            JOptionPane.showMessageDialog(null, "Error al actualizar la cita", "Error SQL", JOptionPane.ERROR_MESSAGE);
            //e.printStackTrace();
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
            JOptionPane.showMessageDialog(null, "Error al eliminar cita", "Error SQL", JOptionPane.ERROR_MESSAGE);
            //e.printStackTrace();
            return false;
        }
    }  
    
    public boolean registrarCitaYActualizarHistorial(int idPaciente, int idDentista, Date fecha, String hora, String motivo, String notas) {
        Connection con = null;
        PreparedStatement psCita = null;
        PreparedStatement psHistorial = null;
        PreparedStatement psVerificarHistorial = null;
        ResultSet rs = null;

        String sqlCita = "INSERT INTO Citas (idPaciente, idDentista, Fecha, Hora, Motivo, Notas) VALUES (?, ?, ?, ?, ?, ?)";
        String sqlVerificarHistorial = "SELECT idHistorialMedico FROM HistorialMedico WHERE idPaciente = ?";
        String sqlActualizarHistorial = "UPDATE HistorialMedico SET UltimaActualizacion = ?, Observaciones = CONCAT(Observaciones, '\n[Cita el ', ?, '] Motivo: ', ?) WHERE idPaciente = ?";

        try {
            Conexion conexion = new Conexion();
            con = conexion.conexion();
            con.setAutoCommit(false);

            // Verificar que el paciente tenga historial
            psVerificarHistorial = con.prepareStatement(sqlVerificarHistorial);
            psVerificarHistorial.setInt(1, idPaciente);
            rs = psVerificarHistorial.executeQuery();

            if (!rs.next()) {
                //JOptionPane.showMessageDialog(null, "El paciente no tiene historial médico. No se puede registrar la cita.", "Error", JOptionPane.ERROR_MESSAGE);
                return false;
            }

            // Insertar la cita
            psCita = con.prepareStatement(sqlCita);
            psCita.setInt(1, idPaciente);
            psCita.setInt(2, idDentista);
            psCita.setDate(3, new java.sql.Date(fecha.getTime()));
            psCita.setString(4, hora);
            psCita.setString(5, motivo);
            psCita.setString(6, notas);
            psCita.executeUpdate();

            // Actualizar historial
            psHistorial = con.prepareStatement(sqlActualizarHistorial);
            psHistorial.setDate(1, new java.sql.Date(fecha.getTime()));
            psHistorial.setDate(2, new java.sql.Date(fecha.getTime())); // para mostrar el día de la cita
            psHistorial.setString(3, motivo);
            psHistorial.setInt(4, idPaciente);
            psHistorial.executeUpdate();

            con.commit();
            JOptionPane.showMessageDialog(null, "Cita registrada y historial actualizado.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
            return true;

        } catch (SQLException e) {
            try {
                if (con != null) con.rollback();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            e.printStackTrace();
            return false;
        } finally {
            try {
                if (rs != null) rs.close();
                if (psVerificarHistorial != null) psVerificarHistorial.close();
                if (psCita != null) psCita.close();
                if (psHistorial != null) psHistorial.close();
                if (con != null) con.setAutoCommit(true);
                if (con != null) con.close();
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(null, "Error al cerrar recursos: " + e.getMessage());
            }
        }
    }
    
    public boolean actualizarCitaYHistorial(int idCita, int idPaciente, int idDentista, Date fecha, String hora, String motivo, String notas) {
        Connection con = null;
        PreparedStatement psActualizarCita = null;
        PreparedStatement psActualizarHistorial = null;
        PreparedStatement psVerificarHistorial = null;
        ResultSet rs = null;

        String sqlActualizarCita = "UPDATE Citas SET idPaciente = ?, idDentista = ?, Fecha = ?, Hora = ?, Motivo = ?, Notas = ? WHERE idCita = ?";
        String sqlVerificarHistorial = "SELECT idHistorialMedico FROM HistorialMedico WHERE idPaciente = ?";
        String sqlActualizarHistorial = "UPDATE HistorialMedico SET UltimaActualizacion = ?, Observaciones = CONCAT(Observaciones, '\n[Actualización el ', ?, '] Motivo: ', ?) WHERE idPaciente = ?";

        try {
            Conexion conexion = new Conexion();
            con = conexion.conexion();
            con.setAutoCommit(false); // Inicia la transacción

            // Verificar historial
            psVerificarHistorial = con.prepareStatement(sqlVerificarHistorial);
            psVerificarHistorial.setInt(1, idPaciente);
            rs = psVerificarHistorial.executeQuery();

            if (!rs.next()) {
                JOptionPane.showMessageDialog(null, "El paciente no tiene historial médico. No se puede actualizar la cita.", "Error", JOptionPane.ERROR_MESSAGE);
                return false;
            }

            // Actualizar cita
            psActualizarCita = con.prepareStatement(sqlActualizarCita);
            psActualizarCita.setInt(1, idPaciente);
            psActualizarCita.setInt(2, idDentista);
            psActualizarCita.setDate(3, new java.sql.Date(fecha.getTime()));
            psActualizarCita.setString(4, hora);
            psActualizarCita.setString(5, motivo);
            psActualizarCita.setString(6, notas);
            psActualizarCita.setInt(7, idCita);
            psActualizarCita.executeUpdate();

            // Actualizar historial
            psActualizarHistorial = con.prepareStatement(sqlActualizarHistorial);
            psActualizarHistorial.setDate(1, new java.sql.Date(fecha.getTime()));
            psActualizarHistorial.setDate(2, new java.sql.Date(fecha.getTime()));
            psActualizarHistorial.setString(3, motivo);
            psActualizarHistorial.setInt(4, idPaciente);
            psActualizarHistorial.executeUpdate();

            con.commit();
            return true;

        } catch (SQLException e) {
            try {
                if (con != null) con.rollback();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            e.printStackTrace();
            return false;
        } finally {
            try {
                if (rs != null) rs.close();
                if (psVerificarHistorial != null) psVerificarHistorial.close();
                if (psActualizarCita != null) psActualizarCita.close();
                if (psActualizarHistorial != null) psActualizarHistorial.close();
                if (con != null) con.setAutoCommit(true);
                if (con != null) con.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
    
    public void buscarCitas(String textoBusqueda, JTable tabla) {
        String sql = "SELECT c.idCita, " +
                     "CONCAT(p.idPaciente, ' - ', p.Nombre) AS Paciente, " +
                     "CONCAT(d.idDentista, ' - ', d.Nombre) AS Dentista, " +
                     "c.Fecha, c.Hora, c.Motivo, c.Notas " +
                     "FROM Citas c " +
                     "JOIN Pacientes p ON c.idPaciente = p.idPaciente " +
                     "JOIN Dentistas d ON c.idDentista = d.idDentista " +
                     "WHERE CONCAT(c.idCita, ' ', p.Nombre, ' ', d.Nombre, ' ', c.Fecha, ' ', c.Hora, ' ', c.Motivo, ' ', c.Notas) LIKE ?";

        try (Connection con = new Conexion().conexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, "%" + textoBusqueda + "%");
            ResultSet rs = ps.executeQuery();

            DefaultTableModel modelo = new DefaultTableModel();
            modelo.setColumnIdentifiers(new Object[]{
                "ID", "Paciente", "Dentista", "Fecha", "Hora", "Motivo", "Notas"
            });

            while (rs.next()) {
                modelo.addRow(new Object[]{
                    rs.getInt("idCita"),
                    rs.getString("Paciente"),
                    rs.getString("Dentista"),
                    rs.getDate("Fecha"),
                    rs.getString("Hora"),
                    rs.getString("Motivo"),
                    rs.getString("Notas")
                });
            }

            tabla.setModel(modelo);
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al buscar citas: " + e.getMessage(), "Error SQL", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }
}
