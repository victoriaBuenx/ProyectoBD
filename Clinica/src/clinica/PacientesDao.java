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
public class PacientesDao {
    Inicio cbxPacientes= new Inicio();
    
    public boolean insertarPaciente(String nombre, String apellidoPaterno, String apellidoMaterno,
                                    Date fechaNacimiento, String telefono, String correo, Date fechaRegistro) {
        String sql = "INSERT INTO Pacientes (Nombre, ApellidoPaterno, ApellidoMaterno, FechaNacimiento, "
                + "Telefono, Correo, FechaRegistro) " +
                     "VALUES (?, ?, ?, ?, ?, ?, ?)";

        PreparedStatement ps = null;
        Connection con = null;  

        try {
            Conexion conexion = new Conexion();  
            con = conexion.conexion();  

            ps = con.prepareStatement(sql);
            ps.setString(1, nombre);
            ps.setString(2, apellidoPaterno);
            ps.setString(3, apellidoMaterno);
            ps.setDate(4, new java.sql.Date(fechaNacimiento.getTime())); 
            ps.setString(5, telefono);
            ps.setString(6, correo);
            ps.setDate(7, new java.sql.Date(fechaRegistro.getTime())); 

            int filasAfectadas = ps.executeUpdate();

            if (filasAfectadas > 0) {
                JOptionPane.showMessageDialog(null, "Paciente registrado con éxito.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
                cbxPacientes.llenarComboBox();
                return true;
            } else {
                JOptionPane.showMessageDialog(null, "No se pudo registrar el paciente.", "Error", JOptionPane.ERROR_MESSAGE);
                return false;
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al insertar paciente: " + e.getMessage(), "Error SQL", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
            return false;
        }
    }
    
    public boolean actualizarPaciente(int idPaciente, String nombre, String apellidoPaterno, String apellidoMaterno,
                                  Date fechaNacimiento, String telefono, String correo, Date fechaRegistro) {
        String sql = "UPDATE Pacientes SET Nombre = ?, ApellidoPaterno = ?, ApellidoMaterno = ?, FechaNacimiento = ?, " +
                     "Telefono = ?, Correo = ?, FechaRegistro = ? WHERE idPaciente = ?";

        try (Connection con = new Conexion().conexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, nombre);
            ps.setString(2, apellidoPaterno);
            ps.setString(3, apellidoMaterno);
            ps.setDate(4, new java.sql.Date(fechaNacimiento.getTime()));
            ps.setString(5, telefono);
            ps.setString(6, correo);
            ps.setDate(7, new java.sql.Date(fechaRegistro.getTime()));
            ps.setInt(8, idPaciente);

            int filasAfectadas = ps.executeUpdate();

            if (filasAfectadas > 0) {
                JOptionPane.showMessageDialog(null, "Paciente actualizado con éxito." , "Éxito", JOptionPane.INFORMATION_MESSAGE);
                return true;
            } else {
                JOptionPane.showMessageDialog(null, "No se pudo actualizar el paciente.", "Error", JOptionPane.ERROR_MESSAGE);
                return false;
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al actualizar paciente: " + e.getMessage(), "Error SQL", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
            return false;
        }
    }
    
    public boolean eliminarPaciente(int idPaciente) {
        String sql = "DELETE FROM Pacientes WHERE idPaciente = ?";

        try (Connection con = new Conexion().conexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, idPaciente);

            int filasAfectadas = ps.executeUpdate();

            if (filasAfectadas > 0) {
                JOptionPane.showMessageDialog(null, "Paciente eliminado con éxito." ,"Éxito", JOptionPane.INFORMATION_MESSAGE);
                return true;
            } else {
                JOptionPane.showMessageDialog(null, "No se pudo eliminar el paciente.", "Error", JOptionPane.ERROR_MESSAGE);
                return false;
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al eliminar paciente: " + e.getMessage(), "Error SQL", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
            return false;
        }
    } 
    
    public void buscarPacientes(String textoBusqueda, JTable tabla) {
    String sql = "SELECT * FROM Pacientes WHERE CONCAT(idPaciente, ' ', Nombre, ' ', ApellidoPaterno, ' ', ApellidoMaterno, ' ', FechaNacimiento, ' ', Telefono, ' ', Correo, ' ', FechaRegistro) LIKE ?";

    try (Connection con = new Conexion().conexion();
         PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, "%" + textoBusqueda + "%");
            ResultSet rs = ps.executeQuery();

            DefaultTableModel modelo = new DefaultTableModel();
            modelo.setColumnIdentifiers(new Object[]{
                "ID", "Nombre", "Apellido Paterno", "Apellido Materno", "Fecha Nac.", "Teléfono", "Correo", "Fecha Registro"
            });

            while (rs.next()) {
                modelo.addRow(new Object[]{
                    rs.getInt("idPaciente"),
                    rs.getString("Nombre"),
                    rs.getString("ApellidoPaterno"),
                    rs.getString("ApellidoMaterno"),
                    rs.getDate("FechaNacimiento"),
                    rs.getString("Telefono"),
                    rs.getString("Correo"),
                    rs.getDate("FechaRegistro")
                });
            }

            tabla.setModel(modelo);
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al buscar pacientes: " + e.getMessage(), "Error SQL", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }

}

