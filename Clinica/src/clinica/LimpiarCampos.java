/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package clinica;

import com.toedter.calendar.JDateChooser;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JSpinner;
import javax.swing.JTextArea;
import javax.swing.JTextField;

/**
 *
 * @author victo
 */
public class LimpiarCampos {
    
    public static void limpiarCampo(JComponent campo) {
        if (campo instanceof JTextField) {
            ((JTextField) campo).setText("");
        } else if (campo instanceof JTextArea) {
            ((JTextArea) campo).setText("");
        } else if (campo instanceof JDateChooser) {
            ((JDateChooser) campo).setDate(null);
        } else if (campo instanceof JComboBox) {
            ((JComboBox<?>) campo).setSelectedItem(null); 
        } else if (campo instanceof JSpinner) {
            ((JSpinner) campo).setValue(null);
        }
    }
    
    public static void limpiarCampos(JComponent... campos) {
        for (JComponent campo : campos) {
            limpiarCampo(campo);
        }
    }
    
}
