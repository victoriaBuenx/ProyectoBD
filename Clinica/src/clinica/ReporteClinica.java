/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package clinica;

import java.util.HashMap;
import java.sql.Connection;
import java.util.Map;
import javax.swing.WindowConstants;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.view.JasperViewer;
import net.sf.jasperreports.engine.util.JRLoader;

/**
 *
 * @author victo
 */
public class ReporteClinica {

    public void generarReporte(String nombreReporte, Map<String, Object> parametros) {
        try {
            JasperReport report = (JasperReport) JRLoader.loadObject(
                getClass().getResource("/reportes/" + nombreReporte + ".jasper")
            );

            Conexion conexionBD = new Conexion();
            Connection con = conexionBD.conexion();

            if (con != null) {
                JasperPrint jprint = JasperFillManager.fillReport(report, parametros, con);
                JasperViewer view = new JasperViewer(jprint, false);
                view.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
                view.setVisible(true);
            } else {
                System.out.println("No se pudo establecer conexión con la base de datos.");
            }

        } catch (JRException ex) {
            ex.printStackTrace();
        }
    }
}
    