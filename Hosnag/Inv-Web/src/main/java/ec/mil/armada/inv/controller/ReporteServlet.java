/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.mil.armada.inv.controller;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.sql.DataSource;
import net.sf.jasperreports.engine.JRExporter;
import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import net.sf.jasperreports.engine.util.JRLoader;

/**
 *
 * @author maria_rodriguez
 */
@WebServlet(name = "ReporteServlet", urlPatterns = {"/ReporteServlet"})
public class ReporteServlet extends HttpServlet {

    private static final Logger LOG = Logger.getLogger(ReporteServlet.class.getName());

    @Resource(name = "ConeccionPrueba")
    transient private DataSource coneccionPrueba;

    private static final long serialVersionUID = 9187823264470812592L;

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     * @throws java.sql.SQLException
     */
    protected void processRequest(final HttpServletRequest request, final HttpServletResponse response)
            throws ServletException, IOException, SQLException {
        String pathReal, ubicacion;

        response.setContentType("text/html;charset=UTF-8");
        final HttpSession session = request.getSession();
        session.setAttribute("clave", request.getParameter("clave"));
        session.setAttribute("direccion", request.getParameter("direccion"));

        //para verificar los parametros
        //nombredel reporte
        session.setAttribute("nombre", request.getParameter("nombre"));
        session.setAttribute("ubicacion", request.getParameter("ubicacion"));

        response.setContentType("application/pdf");
        final ServletOutputStream out = response.getOutputStream();
//        pathReal = session.getServletContext().getRealPath("/reportes");
        ubicacion = session.getAttribute("ubicacion").toString();
        pathReal = session.getServletContext().getRealPath(ubicacion);

        response.reset();
        response.resetBuffer();
//        String nombreReporte = "/repRefDerivado.jasper";
        final String nombreReporte = "/" + session.getAttribute("nombre").toString() + ".jasper";

        final Map parametros = new HashMap();
        parametros.put("clave", session.getAttribute("clave"));
        parametros.put("direccion", pathReal + "/");

        Connection coneccion = null;
        try {
            coneccion = coneccionPrueba.getConnection();
            if (coneccion.isClosed()) {
                LOG.info("La conexion  es nula");
            }
        } catch (Exception e) {
            LOG.info(e.getMessage());
        }

        try {
            final JasperReport jasperReport = (JasperReport) JRLoader.loadObjectFromFile(pathReal + nombreReporte);
            final JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parametros, coneccion);
            final JRExporter exporter = new JRPdfExporter();
            exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
            exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, out);
            exporter.exportReport();
        } catch (RuntimeException e) {
            throw e;
        } catch (Exception e) {
            Logger.getLogger(ReporteServlet.class.getName()).log(Level.INFO, null, e);
        }
        if (coneccion != null) {
            coneccion.close();
        }

    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(final HttpServletRequest request, final HttpServletResponse response)
            throws ServletException, IOException {
        try {
            processRequest(request, response);
        } catch (SQLException ex) {
            Logger.getLogger(ReporteServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(final HttpServletRequest request, final HttpServletResponse response)
            throws ServletException, IOException {
        try {
            processRequest(request, response);
        } catch (SQLException ex) {
            Logger.getLogger(ReporteServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
