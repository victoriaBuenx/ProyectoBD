/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package clinica;

import com.toedter.calendar.JDateChooser;
import java.awt.Component;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.JSpinner;
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
            long numero = Long.parseLong(campo.getText().trim());
            if (numero < 0) {
                throw new NumberFormatException();
            }
            return true;
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, mensaje, "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
    }
    
    public boolean validarNumero(JSpinner spinner, String mensaje) {
        try {
            Object value = spinner.getValue();
            long numero = Long.parseLong(value.toString().trim());
            if (numero < 0) {
                throw new NumberFormatException(); 
            }
            return true;
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, mensaje, "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
    }

    public boolean validarFormatoHora(JTextField campo, String mensaje) {
        String texto = campo.getText().trim();
        if (!texto.matches("^([01]\\d|2[0-3]):[0-5]\\d$")) {
            JOptionPane.showMessageDialog(null, mensaje + " (Formato válido: HH:mm en 24 horas)", "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }

        return true;
    }
    public boolean validarFormatoHoraA(JTextField campo, String mensaje) {
        String texto = campo.getText().trim();

        if (!texto.matches("^(\\d{2}):(\\d{2})-(\\d{2}):(\\d{2})$")) {
            JOptionPane.showMessageDialog(null, mensaje + " (Formato incorrecto. Usa HH:mm-HH:mm)", "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }

        try {
            String[] partes = texto.split("-");
            SimpleDateFormat formatoHora = new SimpleDateFormat("HH:mm");
            formatoHora.setLenient(false); 

            Date horaInicio = formatoHora.parse(partes[0]);
            Date horaFin = formatoHora.parse(partes[1]);

            if (!horaInicio.before(horaFin)) {
                JOptionPane.showMessageDialog(null, "La hora de inicio debe ser menor que la hora de fin", "Error", JOptionPane.ERROR_MESSAGE);
                return false;
            }

            return true;
        } catch (ParseException e) {
            JOptionPane.showMessageDialog(null, "Error al analizar la hora: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
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
    
    public boolean validarFecha(JDateChooser dateChooser) {
        Date fechaSeleccionada = dateChooser.getDate();
        Date fechaActual = new Date();

        if (fechaSeleccionada == null) {
            JOptionPane.showMessageDialog(null, "Debe seleccionar una fecha.", "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        if (fechaSeleccionada.before(quitarHora(fechaActual))) {
            JOptionPane.showMessageDialog(null, "No se permiten fechas pasadas.", "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        
        return true;
    }

    public boolean validarCorreo(JTextField campo) {
        String correo = campo.getText().trim();
        String regex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(correo);

        if (!matcher.matches()) {
            JOptionPane.showMessageDialog(null, "Correo electrónico no válido.", "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }

        return true;
    }

    private static Date quitarHora(Date fecha) {
        java.util.Calendar cal = java.util.Calendar.getInstance();
        cal.setTime(fecha);
        cal.set(java.util.Calendar.HOUR_OF_DAY, 0);
        cal.set(java.util.Calendar.MINUTE, 0);
        cal.set(java.util.Calendar.SECOND, 0);
        cal.set(java.util.Calendar.MILLISECOND, 0);
        return cal.getTime();
    }

}

