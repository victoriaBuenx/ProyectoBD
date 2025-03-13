/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package clinica;

import com.toedter.calendar.JDateChooser;
import java.awt.Component;
import java.util.Arrays;
import java.util.List;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

/**
 *
 * @author victo
 */
public class ValidarCampos {
    public boolean validarCamposVacios(Component... campos) {
        for (Component campo : campos) {
            if (campo instanceof JTextField && ((JTextField) campo).getText().trim().isEmpty()) {
                JOptionPane.showMessageDialog(null, "Todos los campos son obligatorios.", "Error", JOptionPane.ERROR_MESSAGE);
                return false;
            } else if (campo instanceof JTextArea && ((JTextArea) campo).getText().trim().isEmpty()) {
                JOptionPane.showMessageDialog(null, "Todos los campos son obligatorios.", "Error", JOptionPane.ERROR_MESSAGE);
                return false;
            } else if (campo instanceof JComboBox && ((JComboBox<?>) campo).getSelectedItem() == null) {
                JOptionPane.showMessageDialog(null, "Todos los campos son obligatorios.", "Error", JOptionPane.ERROR_MESSAGE);
                return false;
            } else if (campo instanceof JDateChooser && ((JDateChooser) campo).getDate() == null) {
                JOptionPane.showMessageDialog(null, "Todos los campos son obligatorios.", "Error", JOptionPane.ERROR_MESSAGE);
                return false;
            }
        }
        return true;
    }

    public boolean validarNumero(JTextField campo, String mensaje) {
        try {
            Long.parseLong(campo.getText().trim());
            return true;
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, mensaje, "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
    }

    public boolean validarFormatoHora(JTextField campo, String mensaje) {
        if (!campo.getText().matches("^(\\d{2}:\\d{2})$")) {
            JOptionPane.showMessageDialog(null, mensaje, "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        return true;
    }
    public boolean validarFormatoHoraA(JTextField campo, String mensaje) {
        if (!campo.getText().matches("^(\\d{2}:\\d{2})-(\\d{2}:\\d{2})$")) {
            JOptionPane.showMessageDialog(null, mensaje, "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        return true;
    }

    public boolean validarEspecialidad(JComboBox <String> comboBox) {
        List<String> especialidadesValidas = Arrays.asList("Odontopedriata", "Ortodoncista", "Periodoncista", 
                                                            "Endodoncista", "Patologo", "Prostodoncista", "Cirujano");
        Object selectedItem= comboBox.getSelectedItem();
        if (selectedItem == null || !especialidadesValidas.contains(comboBox.getSelectedItem().toString())) {
            JOptionPane.showMessageDialog(null, "La especialidad seleccionada no es válida.", "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        return true;
    }
    
}
