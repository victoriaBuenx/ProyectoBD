/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package clinica;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author victo
 */
public class VisualizarTablas {
    
    public void cargarDatos(JTable tabla, String sql, String[] columnas) {
        DefaultTableModel miModelo = (DefaultTableModel) tabla.getModel();
        miModelo.setRowCount(0);

        try (Connection con = new Conexion().conexion();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            List<Object[]> datos = new ArrayList<>();

            while (rs.next()) {
                Object[] fila = new Object[columnas.length];
                for (int i = 0; i < columnas.length; i++) {
                    fila[i] = rs.getObject(columnas[i]);
                }
                datos.add(fila);
            }

            for (Object[] fila : datos) {
                miModelo.addRow(fila);
            }

            tabla.setModel(miModelo);

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null,"Error al obtener los datos: " + ex.getMessage(), "Error SQL", JOptionPane.ERROR_MESSAGE);
        }
    }
    
}
