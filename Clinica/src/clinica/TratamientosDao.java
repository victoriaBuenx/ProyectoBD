/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package clinica;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.sql.SQLException;
import java.util.Date;
import javax.swing.JOptionPane;
import java.sql.ResultSet;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;


/**
 *
 * @author victo
 */
public class TratamientosDao {
    
    public int insertarTratamiento(int idDentista, String nombre, String descripcion, int montoTotal, int productoUsado) {
        String sql = "INSERT INTO Tratamientos (idDentista, Nombre, Descripcion, MontoTotal, ProductoUsado) VALUES (?, ?, ?, ?, ?)";
        int idTratamientoGenerado = -1; 

        try (Connection con = new Conexion().conexion();
            PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setInt(1, idDentista);
            ps.setString(2, nombre);
            ps.setString(3, descripcion);
            ps.setInt(4, montoTotal);
            ps.setInt(5, productoUsado);

            int filasAfectadas = ps.executeUpdate();

            if (filasAfectadas > 0) {
                ResultSet rs = ps.getGeneratedKeys();
                if (rs.next()) {
                    idTratamientoGenerado = rs.getInt(1); 
                }
                    JOptionPane.showMessageDialog(null, "Tratamiento registrado con éxito.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(null, "No se pudo registrar el tratamiento.",  "Error", JOptionPane.ERROR_MESSAGE);
                }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al insertar tratamiento", "Error SQL", JOptionPane.ERROR_MESSAGE);
            //e.printStackTrace();
        }
            return idTratamientoGenerado;
        }
    
    public boolean actualizarTratamiento(int idTratamiento, int idDentista, String nombre, String descripcion, int montoTotal, int productoUsado) {
        String sql = "UPDATE Tratamientos SET idDentista = ?, Nombre = ?, Descripcion = ?, MontoTotal = ? ProductoUsado = ? WHERE idTratamiento = ?";

        try (Connection con = new Conexion().conexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, idDentista);
            ps.setString(2, nombre);
            ps.setString(3, descripcion);
            ps.setInt(4, montoTotal);
            ps.setInt(5, productoUsado);
            ps.setInt(6, idTratamiento);

            int filasAfectadas = ps.executeUpdate();

            if (filasAfectadas > 0) {
                JOptionPane.showMessageDialog(null, "Tratamiento actualizado con éxito." , "Éxito", JOptionPane.INFORMATION_MESSAGE);
                return true;
            } else {
                JOptionPane.showMessageDialog(null, "No se pudo actualizar el tratamiento.", "Error", JOptionPane.ERROR_MESSAGE);
                return false;
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al actualizar tratamiento", "Error SQL", JOptionPane.ERROR_MESSAGE);
            return false;
        }
    }
    
    public boolean eliminarTratamiento(int idTratamiento) {
        String sql = "DELETE FROM Tratamientos WHERE idTratamiento = ?";
        PreparedStatement ps = null;
        Connection con = null;

        try {
            Conexion conexion = new Conexion();
            con = conexion.conexion();

            ps = con.prepareStatement(sql);
            ps.setInt(1, idTratamiento);

            int filasAfectadas = ps.executeUpdate();

            if (filasAfectadas > 0) {
                JOptionPane.showMessageDialog(null, "Tratamiento eliminado con éxito.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
                return true;
            } else {
                JOptionPane.showMessageDialog(null, "No se pudo eliminar el tratamiento.", "Error", JOptionPane.ERROR_MESSAGE);
                return false;
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al eliminar tratamiento", "Error SQL", JOptionPane.ERROR_MESSAGE);
            //e.printStackTrace();
            return false;
        }
    }
    
    public void buscarTratamientos(String textoBusqueda, JTable tabla) {
        String sql = "SELECT t.idTratamiento, " +
                     "CONCAT(d.idDentista, ' - ', d.Nombre) AS Dentista, " +
                     "t.Nombre AS Tratamiento, t.Descripcion, t.MontoTotal, " +
                     "CONCAT(p.idPaciente, ' - ', p.Nombre) AS Paciente, " +
                     "pt.FechaInicio, pt.FechaFin " +
                     "FROM Tratamientos t " +
                     "INNER JOIN PacientesTratamientos pt ON t.idTratamiento = pt.idTratamiento " +
                     "INNER JOIN Pacientes p ON pt.idPaciente = p.idPaciente " +
                     "INNER JOIN Dentistas d ON t.idDentista = d.idDentista " +
                     "WHERE CONCAT(t.idTratamiento, ' ', d.Nombre, ' ', t.Nombre, ' ', t.Descripcion, ' ', t.MontoTotal, ' ', p.Nombre, ' ', pt.FechaInicio, ' ', pt.FechaFin) LIKE ?";

        try (Connection con = new Conexion().conexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, "%" + textoBusqueda + "%");
            ResultSet rs = ps.executeQuery();

            DefaultTableModel modelo = new DefaultTableModel();
            modelo.setColumnIdentifiers(new Object[]{
                "idTratamiento", "Dentista", "Paciente", "Tratamiento", "Descripcion",  "FechaInicio", "FechaFin", "MontoTotal"
            });

            while (rs.next()) {
                modelo.addRow(new Object[]{
                    rs.getInt("idTratamiento"),
                    rs.getString("Dentista"),
                    rs.getString("Paciente"),
                    rs.getString("Tratamiento"),
                    rs.getString("Descripcion"),
                    rs.getDate("FechaInicio"),
                    rs.getDate("FechaFin"),
                    rs.getDouble("MontoTotal")
                });
            }

            tabla.setModel(modelo);
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al buscar tratamientos: " + e.getMessage(), "Error SQL", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }
}
