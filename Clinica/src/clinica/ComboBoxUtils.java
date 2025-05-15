/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package clinica;

import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import java.sql.*;
/**
 *
 * @author victo
 */
public class ComboBoxUtils {
    
    public void llenarComboBox(JComboBox<String> comboBox, String sql, String idColumn, String nameColumn) {
        comboBox.removeAllItems();

        try (Connection con = new Conexion().conexion();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                comboBox.addItem(rs.getInt(idColumn) + " - " + rs.getString(nameColumn));
            }

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error al cargar los datos: " + ex.getMessage(), "Error SQL", JOptionPane.ERROR_MESSAGE);
        }
    }
    
}
