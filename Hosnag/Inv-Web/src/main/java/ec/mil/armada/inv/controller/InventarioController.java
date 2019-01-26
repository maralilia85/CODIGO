/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.mil.armada.inv.controller;


import ec.mil.armada.inv.modelo.catalago.CatArticulo;
import ec.mil.armada.inv.modelo.catalago.CatBodega;
import ec.mil.armada.inv.modelo.catalago.CatEmpresa;
import ec.mil.armada.inv.modelo.catalago.CatGeneral;
import ec.mil.armada.inv.modelo.inventario.InvCabTransaccion;
import ec.mil.armada.inv.modelo.inventario.InvCabsolicitudbod;
import ec.mil.armada.inv.modelo.inventario.InvCabtomafisica;
import ec.mil.armada.inv.modelo.inventario.InvDetTransaccion;
import ec.mil.armada.inv.modelo.inventario.InvDetsolicitudbod;
import ec.mil.armada.inv.modelo.inventario.InvDettomafisica;
import ec.mil.armada.inv.modelo.inventario.InvExistenciaBodega;
import ec.mil.armada.inv.modelo.inventario.InvLoteArticulo;
import ec.mil.armada.inv.modelo.registro.RegAdmision;
import ec.mil.armada.inv.modelo.registro.RegCabliquidacion;
import ec.mil.armada.inv.modelo.registro.RegCabordencompra;
import ec.mil.armada.inv.modelo.registro.RegCabtemp;
import ec.mil.armada.inv.modelo.registro.RegDetliquidacion;
import ec.mil.armada.inv.modelo.registro.RegDetordencompra;
import ec.mil.armada.inv.modelo.registro.RegDettemp;
import ec.mil.armada.inv.modelo.registro.RegPaciente;
import ec.mil.armada.inv.remotos.catalogo.CatArticuloFacadeRemote;
import ec.mil.armada.inv.remotos.catalogo.CatBodegaFacadeRemote;
import ec.mil.armada.inv.remotos.catalogo.CatEmpresaFacadeRemote;
import ec.mil.armada.inv.remotos.catalogo.CatGeneralFacadeRemote;
import ec.mil.armada.inv.remotos.inventario.InvCabsolicitudbodFacadeRemote;
import ec.mil.armada.inv.remotos.inventario.InvCabtomafisicaFacadeRemote;
import ec.mil.armada.inv.remotos.inventario.InvDetsolicitudbodFacadeRemote;
import ec.mil.armada.inv.remotos.inventario.InvDettomafisicaFacadeRemote;
import ec.mil.armada.inv.remotos.registro.RegAdmisionFacadeRemote;
import ec.mil.armada.inv.remotos.registro.RegCabordencompraFacadeRemote;
import ec.mil.armada.inv.remotos.registro.RegDetordencompraFacadeRemote;
import ec.mil.armada.inv.remotos.registro.RegPacienteFacadeRemote;
import ec.mil.armada.inv.remotos.inventario.InvCabTransaccionFacadeRemote;
import ec.mil.armada.inv.remotos.inventario.InvDetTransaccionFacadeRemote;
import ec.mil.armada.inv.remotos.inventario.InvExistenciaBodegaFacadeRemote;
import ec.mil.armada.inv.remotos.inventario.InvLoteArticuloFacadeRemote;
import ec.mil.armada.inv.remotos.registro.RegCabliquidacionFacadeRemote;
import ec.mil.armada.inv.remotos.registro.RegCabtempFacadeRemote;
import ec.mil.armada.inv.remotos.registro.RegDetliquidacionFacadeRemote;
import ec.mil.armada.inv.remotos.registro.RegDettempFacadeRemote;
import java.awt.BorderLayout;
import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.Connection;
import java.sql.SQLException;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.AbstractList;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Locale;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.model.SelectItem;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import javax.servlet.ServletContext;
import org.primefaces.component.tabview.TabView;
import org.primefaces.context.RequestContext;
import org.primefaces.event.TabChangeEvent;
import org.primefaces.model.menu.MenuItem;

import javax.servlet.http.HttpServletRequest;
import javax.sql.DataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRExporter;
import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.export.JExcelApiExporterParameter;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import net.sf.jasperreports.engine.export.JRXlsExporter;
import net.sf.jasperreports.engine.util.JRLoader;
import org.primefaces.model.StreamedContent;

/**
 *
 * @author maria_rodriguez
 */
@Named(value = "inventarioController")
@ViewScoped
public class InventarioController implements Serializable {
    
private static final Logger LOG = Logger.getLogger(InventarioController.class.getName());
    private static final long serialVersionUID = 6669598813422808015L;

    @EJB
    transient private CatGeneralFacadeRemote catGeneralFacade;
    @EJB
    transient private CatEmpresaFacadeRemote catEmpresaFacade;
    @EJB
    transient private RegDetordencompraFacadeRemote regDetordencompraFacade;
    @EJB
    transient private RegCabordencompraFacadeRemote regCabordencompraFacade;
    @EJB
    transient private InvDetTransaccionFacadeRemote invDetTransaccionFacade;
    @EJB
    transient private InvCabTransaccionFacadeRemote invCabTransaccionFacade;
    @EJB
    transient private CatArticuloFacadeRemote catArticuloFacade;
    @EJB
    transient private CatBodegaFacadeRemote catBodegaFacade;
    @EJB
    transient private InvLoteArticuloFacadeRemote invLoteArticuloFacade;
    @EJB
    transient private InvExistenciaBodegaFacadeRemote invExistenciaBodegaFacade;
    @EJB
    transient private InvDetsolicitudbodFacadeRemote invDetsolicitudbodFacade;
    @EJB
    transient private InvCabsolicitudbodFacadeRemote invCabsolicitudbodFacade;
    @EJB
    transient private InvDettomafisicaFacadeRemote invDettomafisicaFacade;
    @EJB
    transient private InvCabtomafisicaFacadeRemote invCabtomafisicaFacade;
    @EJB
    transient private RegPacienteFacadeRemote regPacienteFacade;
    @EJB
    private RegAdmisionFacadeRemote regAdmisionFacade;
    @EJB
    private RegCabtempFacadeRemote regCabtempFacade;
     @EJB
    private RegDettempFacadeRemote regDettempFacade;
     @EJB
    private RegDetliquidacionFacadeRemote regDetliquidacionFacade;
    @EJB
    private RegCabliquidacionFacadeRemote regCabliquidacionFacade;
    
    

    /**
     * Creates a new instance of InventarioController
     */
    public InventarioController() {
         idMenu = (String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("idMenu");
    }
    //<editor-fold defaultstate="collapsed" desc="Declaracion de Variables">
    //<editor-fold defaultstate="collapsed" desc="Variables-Consultas">
    private InvExistenciaBodega objInvExistenciaBodega;
    private List<InvExistenciaBodega> listInvExistenciaBodega;
    private InvLoteArticulo objInvLoteArticulo;
    private List<InvLoteArticulo> lisInvLoteArticulo;
    private String tipoBsq;
    private String descripcion;
    private String tipoFiltro;
    private String tipoMovimiento;
    private CatBodega objCatBodega;
    private CatArticulo objCatArticulo;
    private BigInteger tipoArticulo;
    private int inxTab;
    private InvCabTransaccion objInvCabTransaccion;
    private List<InvDetTransaccion> listInvDetTransaccion;
    private List<InvCabTransaccion> listInvCabTransaccion;
    private InvDetTransaccion objInvDetTransaccion;
    private Date fechaIni;
    private Date fechaFin;
    private CatBodega objBodega;
    private CatEmpresa objEmpresa;
    private BigInteger seccionId;
    private BigInteger perchaId;
    private BigInteger columnaId;
    private BigInteger nivelId;
    private int periodo;
    private int meses;
    private Double porciento;
    private BigInteger artId;

    //</editor-fold>
    //<editor-fold defaultstate="collapsed" desc="Orden de compra">
    private RegCabordencompra objRegCabordencompra;
    private RegDetordencompra objRegDetordencompra;
    private List<RegDetordencompra> listRegDetordencompra;
    private List<RegCabordencompra> listRegCabordencompra;
    private String fecha;

    //</editor-fold>
    //<editor-fold defaultstate="collapsed" desc="Solicitudes Bodega">
    private List<InvCabsolicitudbod> listCabSolBodega;
    private List<InvDetsolicitudbod> listDetSolBodega;
    private InvCabsolicitudbod objCabSolBodega;
    private InvDetsolicitudbod objDetSolBodega;
    private BigInteger areaId;
    private BigInteger cantLote;

    //</editor-fold>
    //<editor-fold defaultstate="collapsed" desc="Ajuste">
    private CatGeneral objCatGeneral;
    boolean ctrIng = false;
    boolean ctrEgreso = false;
    //</editor-fold>
    //<editor-fold defaultstate="collapsed" desc="Variables Transaccion">
    private BigInteger proveedorId;
    private BigInteger numeroOrdenCompra;
    private String formulario;
    private String formEgreso;
    private String formAjuste;
    private Boolean iva = false;
    private String tipoTransaccion;
    private BigInteger idTransaccion;
    private String area = "A";
    //</editor-fold>
    //<editor-fold defaultstate="collapsed" desc="Toma fisica Inventario">
    InvCabtomafisica objInvCabtomafisica;
    List<InvDettomafisica> listInvDettomafisica;
    InvDettomafisica objInvDettomafisica;
    private Boolean etiquetas = false;
    private String mensajeInv;
    private Boolean ctrFinaliza = false;
    private InvCabTransaccion objAjusSob;
    private InvCabTransaccion objAjusFalt;
     //</editor-fold>
    private String idBodega;
    private String URL = "";
    private String idMenu;
    private List<CatGeneral> listSubMenu;
    private List<CatGeneral> listMenuItem;
    private InvDetTransaccion objAjusFaltante;
    private InvDetTransaccion objAjusSobrante;
    private List<InvDettomafisica> listAjusFaltante;
    private List<InvDettomafisica> listAjusSobrante;
    private String formInv;
    
      //<editor-fold defaultstate="collapsed" desc="Farmacia">
    private BigInteger noDoc;
    private InvCabTransaccion objTransferencia;
    private List<InvCabTransaccion> listCabTransferencia;
    private List<InvDetTransaccion> listDetTransferencia;
    private RegPaciente objRegPaciente;
    private RegAdmision objRegAdmision;
    private RegCabtemp objRegCabtemp;
    private RegDettemp objRegDettemp;
     private List<RegDettemp> listRegDettemp;
     private RegCabliquidacion objRegCabliquidacion;
     private List<RegDetliquidacion> listRegDetliquidacion;
     String mes = null;
     String anio = null;
     private Boolean validada = false;
     private BigInteger servicioId;
    
      //</editor-fold>
     //<editor-fold defaultstate="collapsed" desc="Reportes">
     
    private String pathImpresion;
    private String nameReport;
    private String nameServidor;
    BigInteger codigo = null;
    BigInteger codigoBod = null;
    String fInicio = null;
    String fFin = null;
    
    @Resource(name = "ConeccionPrueba")
    transient private DataSource coneccionPrueba;
    protected String pathReporteFinal;
    private static final String extensionReporte = ".jasper";
    
    //</editor-fold>
    private int caducados;
    
    
    

    //</editor-fold>
    //<editor-fold defaultstate="collapsed" desc="Funciones">
      @PostConstruct
    public void init() {
        nameServidor = catGeneralFacade.descripcion("SERV");
        formulario = "formIngresoBodega";
        formEgreso = "formSolicitudes";
        formAjuste = "formAjuste";
        formInv = "formPlanif";
        //OBTIENE BODEGA DE OPCIONES DEL MENU
        final HttpServletRequest request = getHttpServletRequest();
        idBodega = (String) request.getAttribute("idBodega");
         //TODO obtener empresa del usuario conectado
         objEmpresa = catEmpresaFacade.find(new BigInteger("1"));
         if(objEmpresa == null){
          mostrarMensajeError("Info", "No existe registro de empresa por favor parametrice.");
         }
     if (idBodega != null) {
            if (Integer.parseInt(idBodega) == 0) {
                //es catalogo
                objBodega = new CatBodega();
                objCatBodega = new CatBodega();
            } else {
                //obtiene bodega
                objBodega = catBodegaFacade.find(new BigInteger(idBodega));
                objCatBodega = objBodega;

            }

            if (objBodega == null) {
                mostrarMensajeError("Info", "No existe registro de bodega por favor parametrice.");
                
            }
            
        }
     verificaCaducados();
        }
    
    public void verificaCaducados(){
    //verifica caducados
        if (objBodega != null) {
            fInicio = convertirFechaString(new Date());
            caducados = invLoteArticuloFacade.verificaLoteCaducados(objBodega.getBodId(), fInicio, "C");
            if(caducados !=0){
                mostrarMensajeError("Alerta.Caducados", "Existen- " +caducados + " Lotes Caducados.Verifique.");
            }
        }
    }
    
    public String recibeOpcMenu(BigInteger genId) {
        //TODO obtener la bodega del user que se conecta
        if(genId != null){
        objCatGeneral = catGeneralFacade.find(genId);
        URL = objCatGeneral.getGenValor();
        }
    return URL;

    }

     public void enviaOpcion(final String opcion) {
        final HttpServletRequest request = getHttpServletRequest();
        if (opcion != null) {
            request.setAttribute("idBodega", opcion);
        }

    }

    public double redondear(double numero, int digitos) {
        int cifras = (int) Math.pow(10, digitos);
        return Math.rint(numero * cifras) / cifras;
    }
    public void imprimirReporte(String tipo) throws JRException, IOException, SQLException {

        if (objBodega.getBodId() != null) {
            codigoBod = objBodega.getBodId();
        }
        if (tipo.equalsIgnoreCase("IG")) {
            codigo = objInvCabTransaccion.getCtrId();
            nameReport = "reportCabeceraIngresoFactura";
        }
         if (tipo.equalsIgnoreCase("LI")) {
            codigo = objRegCabliquidacion.getCliId();
            nameReport = "reportLiquidacion";
        }
        if (tipo.equalsIgnoreCase("DE")) {
            codigo = objInvCabTransaccion.getCtrId();
            nameReport = "reportDevolucion";
        }
        if (tipo.equalsIgnoreCase("TM")) {
            codigo = objRegCabtemp.getTemId();
            nameReport = "reportTemperetura";
        }
        if (tipo.equalsIgnoreCase("CI")) {
            codigo = objInvCabTransaccion.getCtrId();
            nameReport = "reportComprobanteIngresoFactura";
        }
        if (tipo.equalsIgnoreCase("EG")) {
            codigo = objInvCabTransaccion.getCtrId();
            nameReport = "reportCompCabEgresoBodega";
        }
        if (tipo.equalsIgnoreCase("CE")) {
            codigo = objInvCabTransaccion.getCtrId();
            nameReport = "reportCompCabEgresoBodega";
        }

        if (tipo.equalsIgnoreCase("CTIB")) {
           codigo = objInvCabTransaccion.getCtrId();
            nameReport = "CTIB";
        }
if (tipo.equalsIgnoreCase("AC")) {
            codigo = objInvCabTransaccion.getCtrId();
            nameReport = "ACTA";
        }
        if (tipo.equalsIgnoreCase("CO")) {
            nameReport = "reportConsolidado";
        }
         if (tipo.equalsIgnoreCase("SA")) {

            if (tipoFiltro.equalsIgnoreCase("ME")) {
                nameReport = "reportSaldoMenor";
            }
            if (tipoFiltro.equalsIgnoreCase("IG")) {
                nameReport = "reportSaldoCero";
            }
            if (tipoFiltro.equalsIgnoreCase("MA")) {
                nameReport = "reportSaldo";
            }

        }
       

        if (tipo.equalsIgnoreCase("PL")) {
            codigo = objInvCabtomafisica.getCtfId();
            if (objInvCabtomafisica.getCtfEstado().equalsIgnoreCase("N")) {
                nameReport = "reportCabPlanifTomaFisica";
            } else {
                nameReport = "reportCabTomaFisica";
            }

        }
        if (tipo.equalsIgnoreCase("RC")) {
            codigo = objInvCabtomafisica.getCtfId();
            nameReport = "reportComparativoTomaFisica";
        }
        if (tipo.equalsIgnoreCase("AJ")) {
            codigo = objInvCabTransaccion.getCtrId();
            nameReport = "reportCabeceraAjuste";
        }
        if (tipo.equalsIgnoreCase("KA")) {
            nameReport = "reportKardex";
            if(objCatArticulo.getArtNombgenerico() != null){
            descripcion = objCatArticulo.getArtNombgenerico();
            }
            else
            if(objCatArticulo.getArtCodigo() != null){
            descripcion = objCatArticulo.getArtCodigo();
            }
            
        }
        if (tipo.equalsIgnoreCase("LO")) {
            if (tipoFiltro.equalsIgnoreCase("C")) {
                nameReport = "reportLoteCaducado";
            }
            if (tipoFiltro.equalsIgnoreCase("L")) {
                nameReport = "reportLote";
            }
        }
        if (tipo.equalsIgnoreCase("MO")) {

            if (tipoMovimiento.equalsIgnoreCase("E")) {
                nameReport = "reportMovEgreso";
            }
            if (tipoMovimiento.equalsIgnoreCase("I")) {
                nameReport = "reportMovIngreso";
            }

        }

        generarReportePDF();

    }

public String generaReporteServlet(final String nombServicio, final BigInteger codigo, final String nombreReporte, final String ubicaReportes) {
        String cadenaImprimir = "";
        if ((nombServicio != null && nombServicio.trim().length() > 0 && !(nombServicio.trim().toUpperCase(Locale.getDefault()).compareTo("null") == 0))
                && (nombreReporte != null && nombreReporte.trim().length() > 0 && !(nombreReporte.trim().toUpperCase(Locale.getDefault()).compareTo("null") == 0))
                && (ubicaReportes != null && ubicaReportes.trim().length() > 0 && !(ubicaReportes.trim().toUpperCase(Locale.getDefault()).compareTo("null") == 0))
                && codigo != null) {
            final String pathReportes = nameServidor;
            cadenaImprimir = pathReportes + nombServicio + "?codigo=" + codigo + "&nombre=" + nombreReporte + "&ubicacion=" + ubicaReportes;
        } else {
            mostrarMensajeError("Error", "Faltan Parametros para imprimir el reporte ");
        }
        System.out.println("la cadena a imprimir es "+cadenaImprimir);
        return cadenaImprimir;
    }
    public void generaReporte(final String tipoReporte, final String urlReporte, final String nombreReporte, final Map<String, Object> parametrosReporte) throws JRException, IOException, SQLException {
        //final String codUnidad = getUnidadSalud().getUnsId().toString();
        pathReporteFinal = null;

        Connection coneccion = null;
        try {
            coneccion = coneccionPrueba.getConnection();
            if (coneccion.isClosed()) {
                LOG.info("La conexion  es nula");
            }
        } catch (Exception e) {
            LOG.info(e.getMessage());
        }

        final String pathReporte = nameServidor;

//        final HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
        final ServletContext servletContext = (ServletContext) FacesContext.getCurrentInstance().getExternalContext().getContext();
        final String aux = servletContext.getRealPath(urlReporte);
        final JasperReport jasperReport = (JasperReport) JRLoader.loadObjectFromFile(aux + nombreReporte + extensionReporte);

        parametrosReporte.put("pathReporte", aux + "/");

        if (tipoReporte.equalsIgnoreCase("PDF")) {
            final JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parametrosReporte, coneccion);
            final JRExporter exporter = new JRPdfExporter();
            exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
            //exporter.setParameter(JRExporterParameter.OUTPUT_FILE, new File(servletContext.getRealPath(urlReporte) + nombreReporte + codi + ".pdf"));
            exporter.exportReport();
            pathReporteFinal = pathReporte + urlReporte + nombreReporte  + ".pdf";
            System.out.println("el path es "+pathReporte);

        } 
        if (coneccion != null) {
            coneccion.close();
        }

    }
private void generarReportePDF() throws JRException, IOException, SQLException {
        Connection coneccion = null;
        final String pathReporte = nameServidor;

        try {
            coneccion = coneccionPrueba.getConnection();
            System.out.println("la conexion es "+coneccion);

        } catch (Exception e) {
            Logger.getLogger(InventarioController.class.getName()).log(Level.INFO, null, e);
            mostrarMensajeError("Error", e.getCause().getMessage());
        }

        final ServletContext servletContext = (ServletContext) FacesContext.getCurrentInstance().getExternalContext().getContext();
         String realPath = servletContext.getRealPath("/paginas/reportes");

        final JasperReport jasperReport = (JasperReport) JRLoader.loadObjectFromFile(realPath + "/" + nameReport + extensionReporte);

        final Map<String, Object> parametros = new HashMap<>();
        parametros.put("path", realPath + "/");
        parametros.put("clave", codigo);
        parametros.put("bodId", codigoBod);
       
        parametros.put("descripcion", "%" + descripcion + "%");
        
        parametros.put("tipoId", tipoArticulo);        
        parametros.put("fecha", fInicio);
        parametros.put("fechaIni", fInicio);
        parametros.put("fechaFin", fFin);        
        parametros.put("direccion", realPath + "/");
        
               
        final JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parametros, coneccion);
        
        final JRExporter exporter = new JRPdfExporter();
        exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
        exporter.setParameter(JRExporterParameter.OUTPUT_FILE, new java.io.File(realPath + "/" + nameReport + ".pdf"));
        
        exporter.exportReport();
//        System.out.println(" exportes  " + exporter);

        pathImpresion = nameServidor + "/paginas/reportes/"  + nameReport + ".pdf";
        System.out.println("el pathdeimpreseion es "+pathImpresion);

//        FacesContext.getCurrentInstance().getExternalContext().addResponseHeader("Cache-Control", "no-cache");
//        FacesContext.getCurrentInstance().getExternalContext().addResponseHeader("Pragma", "no-cache");
//        FacesContext.getCurrentInstance().getExternalContext().addResponseHeader("Expires", "0");
//        FacesContext.getCurrentInstance().getExternalContext().setResponseContentType("application/pdf");
//        FacesContext.getCurrentInstance().getExternalContext().redirect("../../reportes/ingresos.pdf");
        coneccion.close();
        System.out.println("salio");
    }



    //<editor-fold defaultstate="collapsed" desc="Consultas">
    public void enceraValores() {
        descripcion = "";
        //buscarByFiltro("U");
    }

    public void buscarByFiltro(String tipo) 
    {
        try {
        if (fechaIni != null) {
            fInicio = convertirFechaString(fechaIni);
        }

        if (fechaFin != null) {
            fFin = convertirFechaString(fechaFin);

        }

        if (tipo.equalsIgnoreCase("U")) {
            //Buscar Ubicacion
            listInvExistenciaBodega = invExistenciaBodegaFacade.listAllByParam(objCatBodega.getBodId(), null, tipoArticulo, "A", descripcion, null, null);
        }
             if (tipo.equalsIgnoreCase("C")) {
            //calcular stock
                 if(periodo == 0){
            listInvExistenciaBodega = invExistenciaBodegaFacade.listAllByParam(objCatBodega.getBodId(), null, tipoArticulo, "A", descripcion, null, null);
                 } 
            else{
            listInvExistenciaBodega = invExistenciaBodegaFacade.calcularStock(objBodega.getBodId(), null, tipoArticulo, "F", descripcion, fInicio, fFin, meses, periodo, porciento, tipoFiltro);
                 }
        }
        if (tipo.equalsIgnoreCase("K")) {
            //Buscar Kardex
            listInvDetTransaccion = invDetTransaccionFacade.listAllByParam(objCatBodega.getBodId(), null, null, descripcion, "F", null, null, null, null);
            if (listInvDetTransaccion.size() > 0) {

                objCatArticulo = catArticuloFacade.find(listInvDetTransaccion.get(0).getInvExistenciaBodega().getCatArticulo().getArtId());
                objInvExistenciaBodega = invExistenciaBodegaFacade.find(listInvDetTransaccion.get(0).getInvExistenciaBodega().getExiId());
            }

        }
        if (tipo.equalsIgnoreCase("S")) {
            //Buscar SALDO

            //listInvExistenciaBodega = invExistenciaBodegaFacade.listAllByParam(objCatBodega.getBodId(), null, tipoArticulo, "A", descripcion, BigInteger.ZERO, tipoFiltro);
            listInvExistenciaBodega = invExistenciaBodegaFacade.listSaldoByParam(objCatBodega.getBodId(), null, tipoArticulo, "F", descripcion, BigInteger.ZERO, tipoFiltro, fInicio, fFin);

        }
        if (tipo.equalsIgnoreCase("M")) {
            //Buscar por Movimientos
            if (tipoMovimiento.equalsIgnoreCase("I")) {
               
                listInvCabTransaccion = invCabTransaccionFacade.listAllByParam(objCatBodega.getBodId(), null, "'IF','IT','IR','ID'", fInicio, fFin, "ENTRE",tipoArticulo);
               

                //listInvDetTransaccion = invDetTransaccionFacade.listAllByParam(objCatBodega.getBodId(), null, tipoArticulo, descripcion, null, "'IF','IT'", fInicio, fFin, "ENTRE");

            }
            if (tipoMovimiento.equalsIgnoreCase("E")) {
                 listInvCabTransaccion = invCabTransaccionFacade.listAllByParam(objCatBodega.getBodId(), null, "'ET','EV'", fInicio, fFin, "ENTRE",tipoArticulo);
                //listInvDetTransaccion = invDetTransaccionFacade.listAllByParam(objCatBodega.getBodId(), null, tipoArticulo, descripcion, null, "'ET','EV'", fInicio, fFin, "ENTRE");

            }

        }
        if (tipo.equalsIgnoreCase("L")) {
            //Buscar por lote
            lisInvLoteArticulo = invLoteArticuloFacade.listAllByParam(objCatBodega.getBodId(), null, tipoArticulo, null, null, null, null);
        }
        if(tipo.equalsIgnoreCase("C")){
            //Buscar caducados
            fInicio =convertirFechaString(new Date());
            lisInvLoteArticulo = invLoteArticuloFacade.listAllByParam(objCatBodega.getBodId(), null, tipoArticulo, null, null, fInicio, "=");
  
            }
        
        if (tipo.equalsIgnoreCase("B")) {
            //Buscar por bodega
            listInvExistenciaBodega = invExistenciaBodegaFacade.listAllByParam(null, null, null, "A", descripcion, null, null);
        }
        if (tipo.equalsIgnoreCase("O")) {
            //Buscar consolidado
            listInvExistenciaBodega = invExistenciaBodegaFacade.listAllByParam(objCatBodega.getBodId(), null, tipoArticulo, "A", descripcion, null, null);
        }
    } catch (Exception e) {
        for (Throwable t = e.getCause(); t != null; t = t.getCause()) {
                if (t.getMessage().contains("java.sql.SQLException")) {
                    mostrarMensajeError("Error", "No se pudo crear el registro." + t.getMessage());
                    break;
                }
            }
        }
    
    }
    public void valoresCalculo(String tipoS, BigInteger codigoArt){
    tipoFiltro = tipoS;
   artId = codigoArt;
    }
    public void calcularStock() {
        System.out.println("el perido es " + periodo);

        if (periodo != 0) {

    //fechaIni = new Date();
            //fechaFin = sumaFechaDias(fechaIni, meses*30);
            String fInicio = null;
            String fFin = null;

            if (fechaIni != null && fechaFin != null) {
                fInicio = convertirFechaString(fechaIni);
                fFin = convertirFechaString(fechaFin);
                meses = calcularMesesAFecha(fechaIni, fechaFin);
                System.out.println("los meses son "+meses);
                if(meses != 0){
                //STOCK DE SEGURIDAD HALLAR PORCIENTO
                porciento = meses * 0.01;

                listInvExistenciaBodega = invExistenciaBodegaFacade.calcularStock(objBodega.getBodId(), artId, tipoArticulo, "F", descripcion, fInicio, fFin, meses, periodo, porciento, null);
                }

            }
        } else {
            mostrarMensajeError("Alerta", "Seleccione Periodo.");
        }

    }

    public Date sumaFechaDias(final Date fecha, final int dias) {

        //La variable dias en positivo es para sumar dias y en negativo para restar dias
        final Calendar calendar = Calendar.getInstance();
        if (fecha != null) {
            calendar.setTime(fecha); // Configuramos la fecha que se recibe
            calendar.add(Calendar.DAY_OF_YEAR, dias);  // numero de dias a anadir, o restar en caso de dias<0
        }
        return calendar.getTime(); // Devuelve el objeto Date con los nuevos dÃ­as aÃ±adidos
    }
     public static int calcularMesesAFecha(Date fechaInicio, Date fechaFin) {
        try {
            //Fecha inicio en objeto Calendar
            Calendar startCalendar = Calendar.getInstance();
            startCalendar.setTime(fechaInicio);
            //Fecha finalización en objeto Calendar
            Calendar endCalendar = Calendar.getInstance();
            endCalendar.setTime(fechaFin);
            //Cálculo de meses para las fechas de inicio y finalización
            int startMes = (startCalendar.get(Calendar.YEAR) * 12) + startCalendar.get(Calendar.MONTH);
            int endMes = (endCalendar.get(Calendar.YEAR) * 12) + endCalendar.get(Calendar.MONTH);
            //Diferencia en meses entre las dos fechas
            int diffMonth = endMes - startMes;
            return diffMonth;
        } catch (Exception e) {
            return 0;
        }
 }
public void obtieneMesA(){
    Calendar a = Calendar.getInstance();
    anio = String.valueOf(a.get(Calendar.YEAR));
    int mesint = a.get(Calendar.MONTH);
    if(mesint == 0){
    mes = "ENERO";
    }
    if(mesint == 1){
    mes = "FEBRERO";
    }
    if(mesint == 2){
    mes = "MARZO";
    }
    
    if(mesint == 3){
    mes = "ABRIL";
    }
    if(mesint == 4){
    mes = "MAYO";
    }
    if(mesint == 5){
    mes = "JUNIO";
    }
    if(mesint == 6){
    mes = "JULIO";
    }
    if(mesint == 7){
    mes = "AGOSTO";
    }
    if(mesint == 8){
    mes = "SEPTIEMBRE";
    }
    if(mesint == 9){
    mes = "OCTUBRE";
    }
    if(mesint == 10){
    mes = "NOVIEMBRE";
    }
    if(mesint == 11){
    mes = "DICIEMBRE";
    }
    
    
    
    
     
   

}
    
    public void enceraListas(){
    listInvExistenciaBodega = new ArrayList<>();
    lisInvLoteArticulo = new ArrayList<>();
    tipoArticulo = BigInteger.ZERO;
    }

    public void buscarOrdenCompra(String filtro) {
        if (filtro.equalsIgnoreCase("OC")) {
            //Buscar solicitud orden de compra
            listRegCabordencompra = regCabordencompraFacade.listAllByParam(objCatBodega.getBodId(), descripcion, tipoFiltro, null, tipoArticulo, fecha, null, "=");
        }
        if (filtro.equalsIgnoreCase("OD")) {
            listRegDetordencompra = regDetordencompraFacade.listAllByParam(objRegCabordencompra.getCocId(), tipoFiltro);

        }

    }

    public void buscarSoliBodega(String filtro) {
        if (filtro.equalsIgnoreCase("CS")) {
            listCabSolBodega = invCabsolicitudbodFacade.listAllByParam(objCatBodega.getBodId(), descripcion, null, null, tipoArticulo, fecha, null, "=");
        }
        if (filtro.equalsIgnoreCase("DS")) {
            listDetSolBodega = invDetsolicitudbodFacade.listAllByParam(objCabSolBodega.getCsbId(), null);
        }
    }

    public void cambiaTabExistencia(final TabChangeEvent event) {
        final TabView tabView = (TabView) event.getComponent();
        final int inxA = tabView.getChildren().indexOf(event.getTab());

        if (inxA == 0) {
            //tab UBICACION
            tipoBsq = "U";
            descripcion = "";
            tipoArticulo = null;
            listInvExistenciaBodega = null;

        }
        if (inxA == 1) {
            //tab STOCK
            tipoBsq = "U";
            descripcion = "";
            tipoArticulo = null;
            listInvExistenciaBodega = null;

        }
        if (inxA == 2) {
            //tab STOCK
            descripcion = "";
            tipoArticulo = null;
            listInvExistenciaBodega = null;

        }
        if (inxA == 3) {
            //tab STOCK
            descripcion = "";
            tipoArticulo = null;
            listInvExistenciaBodega = null;

        }

    }

    public void cambiaTabConsultas(final TabChangeEvent event) {
        final TabView tabView = (TabView) event.getComponent();
        final int inxA = tabView.getChildren().indexOf(event.getTab());

        if (inxA == 0) {
            //tab kardex
            tipoBsq = "K";
            //descripcion = "";
            tipoArticulo = null;
            listInvExistenciaBodega = null;
            nameReport = "reportKardex";

        }
        if (inxA == 1) {
            //tab Saldos
            tipoBsq = "S";
            //descripcion = "";
            //tipoArticulo = null;
            listInvExistenciaBodega = null;

        }
        if (inxA == 2) {
            //tab MOVIMIENTOS
            tipoBsq = "M";
            //descripcion = "";
            //tipoArticulo = null;
            listInvExistenciaBodega = null;

        }
        if (inxA == 3) {
            //tab lote
            tipoBsq = "A";
           // descripcion = "";
            //tipoArticulo = null;
            listInvExistenciaBodega = null;

        }
        if (inxA == 4) {
        

        }


    }

    public void cambiaTabIngreso(final TabChangeEvent event) {
        final TabView tabView = (TabView) event.getComponent();
        final int inxA = tabView.getChildren().indexOf(event.getTab());

        if (inxA == 0) {
            //tab buscar solic compra
            formulario = "formSolicCompra";

        }
        if (inxA == 1) {
            //tab ingreso por compra
            tipoTransaccion = "IF";
            formulario = "formIngresoBodega";

        }
        if (inxA == 2) {
            //tab ingreso por referencia
            tipoArticulo = null;
            objRegCabordencompra = null;
            formulario = "formReferencia";
            objInvCabTransaccion = new InvCabTransaccion();
            objInvCabTransaccion.setCtrTipo("IR");
            listInvDetTransaccion = null;

        }

    }

    public void cambiaTabEgreso(final TabChangeEvent event) {
        final TabView tabView = (TabView) event.getComponent();
        final int inxA = tabView.getChildren().indexOf(event.getTab());

        if (inxA == 0) {
            //tab buscar solic bodega
            formEgreso = "formSolicitudes";
            formulario = "formSolicitudes";

        }
        if (inxA == 1) {
            //tab egreso por transferencia
            formEgreso = "formEgreso";
            formulario = "formEgreso";

        }
        if (inxA == 2) {
            //tab egreso por baja
            formEgreso = "formEgresoBaja";
            formulario = "formEgresoBaja";
             objInvCabTransaccion = new InvCabTransaccion();
             tipoArticulo = null;
            listInvDetTransaccion = null;

        }

    }

    public void cambiaTabAjuste(final TabChangeEvent event) {
        final TabView tabView = (TabView) event.getComponent();
        final int inxA = tabView.getChildren().indexOf(event.getTab());

        if (inxA == 0) {
            //tab buscar solic bodega
            formEgreso = "formAjuste";
            formulario = "formAjuste";
            formAjuste = "formAjuste";

        }

    }

    public void cambiaTabInv(final TabChangeEvent event) {
        final TabView tabView = (TabView) event.getComponent();
        final int inxA = tabView.getChildren().indexOf(event.getTab());

        if (inxA == 0) {
            //tab planificacion
            objCatArticulo = null;
            formInv = "formPlanif";
            formulario = "formPlanif";
            formAjuste = "formPlanif";

        }
        if (inxA == 1) {
            //tab ingreso toma fisica
            formInv = "formTomaFis";
            formulario = "formTomaFis";
            formAjuste = "formTomaFis";
            if(objInvCabtomafisica != null && objInvCabtomafisica.getCtfId() != null)
            {
            //entra a cargar detalle toma fisica    
            cargaCabTomaFisica();
            }

        }
         if (inxA == 2) {
            //tab ajuste
             formInv = "formAjusInv";
             formulario = "formAjusInv";
             formAjuste = "formAjusInv";
        }

    }

    public void btnNewTransaccion() {
        if(objInvCabTransaccion.getCtrTipo() == null){
            mostrarMensajeError("Alerta", "Seleccione tipo de transacción.");
        }
        else{
        final RequestContext context = RequestContext.getCurrentInstance();
        RequestContext.getCurrentInstance().execute("PF('dlgGeneraIng').show()");
        context.update("tabPrincipal:formIngreso");
        }

    }

    public void asignaUbicacion(BigInteger claveExi) {
        if (nivelId != null && claveExi != null) {
            InvExistenciaBodega objetoEdit = invExistenciaBodegaFacade.find(claveExi);
            if (objetoEdit != null) {
                objetoEdit.setBodIdUbi(nivelId);
                invExistenciaBodegaFacade.edit(objetoEdit);
                //cargar lista
                listInvExistenciaBodega = invExistenciaBodegaFacade.listAllByParam(objCatBodega.getBodId(), null, tipoArticulo, "A", descripcion, null, null);
                enceraValoresdeUbi("T");
            }
        }

    }

    public void enceraValoresdeUbi(String tipo) {
        if (tipo.equalsIgnoreCase("T")) {
            nivelId = null;
            seccionId = null;
            perchaId = null;
            columnaId = null;
        }
        if (tipo.equalsIgnoreCase("S")) {
            seccionId = null;
        }
        if (tipo.equalsIgnoreCase("P")) {
            perchaId = null;
        }
        if (tipo.equalsIgnoreCase("C")) {
            columnaId = null;
        }
        if (tipo.equalsIgnoreCase("N")) {
            nivelId = null;
        }

    }

    public void generaCabeceraByReferencia() {
        objInvCabTransaccion = invCabTransaccionFacade.crearObjeto(objBodega, null, "RE", "P", null, objInvCabTransaccion.getCtrTipo(), tipoArticulo);
        if(objInvCabTransaccion != null && objInvCabTransaccion.getCtrId() != null){
            //cargar detalle verificar si existe.
         listInvDetTransaccion = invDetTransaccionFacade.listAllByParam(objInvCabTransaccion.getCtrId(), null);
        }
    }

    public void generaCabeceraEgreso() {
        objInvCabTransaccion = invCabTransaccionFacade.crearObjeto(objBodega, null, null, "P", null, tipoTransaccion, tipoArticulo);
        if(objInvCabTransaccion != null && objInvCabTransaccion.getCtrId() != null){
            //cargar detalle verificar si existe.
         listInvDetTransaccion = invDetTransaccionFacade.listAllByParam(objInvCabTransaccion.getCtrId(), null);
        }

    }
    public void generaCabecera(String tipoTrans) {
        objInvCabTransaccion = invCabTransaccionFacade.crearObjeto(objBodega, null, null, "P", null, tipoTrans, tipoArticulo);
        if(objInvCabTransaccion != null && objInvCabTransaccion.getCtrId() != null){
            //cargar detalle verificar si existe.
         listInvDetTransaccion = invDetTransaccionFacade.listAllByParam(objInvCabTransaccion.getCtrId(), null);
        }

    }

    public void generaCabeceraAjus() {
        if (idTransaccion != null) {
            objCatGeneral = catGeneralFacade.find(idTransaccion);
            if (objCatGeneral != null && !objCatGeneral.getGenSiglatipo().isEmpty()) {
                objInvCabTransaccion = invCabTransaccionFacade.crearObjeto(objBodega, null, null, "P", null, objCatGeneral.getGenSiglatipo(), tipoArticulo);
                 if(objInvCabTransaccion != null && objInvCabTransaccion.getCtrId() != null){
            //cargar detalle verificar si existe.
         listInvDetTransaccion = invDetTransaccionFacade.listAllByParam(objInvCabTransaccion.getCtrId(), null);
        }
            } else {
                mostrarMensajeError("Alerta.null", "No existe parametrizacion de tipo ransaccion.");
            }

        } else {
            mostrarMensajeError("Alerta.null", "Seleccione tipo transacción.");
        }

    }

    public void seleccionaTipoArt(String tipoTrans) {
        if (objCabSolBodega != null && objCabSolBodega.getCsbId() != null) {
            //editar cabecera
            editarCabecera("TA");
        } else {
            //crear egreso
            tipoTransaccion = tipoTrans;
            final RequestContext context = RequestContext.getCurrentInstance();
            RequestContext.getCurrentInstance().execute("PF('dlgGeneraEg').show()");
            context.update("formNewEgreso");
        }

    }

    public void selecTipoTransacc() {
        if (objInvCabTransaccion != null && objInvCabTransaccion.getCtrId() != null) {
    //editar cabecera
            //cargar cabecera
            editarCabecera("TA");
        } else {
            //crear transaccion
            if (tipoArticulo != null && idTransaccion != null) {
                final RequestContext context = RequestContext.getCurrentInstance();
                RequestContext.getCurrentInstance().execute("PF('dlgNewAjuste').show()");
                context.update("formNewAjus");

            } else {
                mostrarMensajeError("Alerta", "Seleccione tipo de articulo.Campo requerido");
            }

        }

    }

    public void editarCabecera(String valor) {
        if (objInvCabTransaccion.getCtrId() != null) {
            if (valor.equalsIgnoreCase("TA")) {
                objInvCabTransaccion.setTipoArticulo(tipoArticulo);
            }
            if (valor.equalsIgnoreCase("OB")) {
                if(servicioId != null){
                objCatGeneral = catGeneralFacade.find(servicioId);
                if(objCatGeneral != null){
                objInvCabTransaccion.setCtrObservacion(objCatGeneral.getGenDescripcion());
                }
                }
            }
            if (valor.equalsIgnoreCase("AR") && areaId != null) {
                CatBodega area = catBodegaFacade.find(areaId);
                objInvCabTransaccion.setCtrArea(areaId);
                if(area != null){
                objInvCabTransaccion.setCatBodegaArea(area);}
               
            }
            if (valor.equalsIgnoreCase("VA")) 
            {
                if(validada){
                    objInvCabTransaccion.setCtrValida("S");
                
                }
                else{
                objInvCabTransaccion.setCtrValida("N");
                }
             
            }
            invCabTransaccionFacade.editarObjeto(objInvCabTransaccion, valor);
            //BUSCAR CABECERA PARA ACTUALIZAR VALORES
            objInvCabTransaccion = invCabTransaccionFacade.find(objInvCabTransaccion.getCtrId());
            
        }

    }

    public List<SelectItem> autocompletarArt(final String descripcion) {
        //ok
        List<SelectItem> articulo = new ArrayList<>();

        List<InvExistenciaBodega> listAux = invExistenciaBodegaFacade.listAllByParam(objBodega.getBodId(), null, tipoArticulo, "A", descripcion, null, null);
        if (listAux.size() > 0) {
            for (InvExistenciaBodega exis : listAux) {
                articulo.add(new SelectItem(exis.getExiId(), exis.getCatArticulo().getArtNombgenerico()));
            }

        } else {
            mostrarMensajeError("Info", "No existe registro del articulo para el tipo" + tipoArticulo);
        }
        return articulo;
    }
    public List<SelectItem> autocompletarCatArt(final String descripcion) {
        //ok
        List<SelectItem> articulo = new ArrayList<>();

        List<CatArticulo> listAux = catArticuloFacade.listArticuloByParam(new BigInteger("2"), null, "A", descripcion);
        if (listAux.size() > 0) {
            for (CatArticulo art : listAux) {
                articulo.add(new SelectItem(art.getArtId(), art.getArtNombgenerico()));
            }

        } else {
            mostrarMensajeError("Info", "No existe registro del articulo." );
        }
        return articulo;
    }
    
    //</editor-fold>
    //<editor-fold defaultstate="collapsed" desc="Calcular">
    public Double calTotalActual(Double existencia, Double pCosto) {
        Double totalActual = null;

        //Total actual = existencia * Pcosto
        totalActual = existencia.intValue() * pCosto;

        return totalActual;
    }

    public Double calTotalIng(Double cantidad, Double pIngreso) {

        Double totalIngreso = null;

        //Total ingreso = cantidad * Pingreso
        totalIngreso = cantidad.intValue() * pIngreso;

        return totalIngreso;
    }

    public Double calTotalCantidad(Double existencia, Double cantidad) {

        Double totalCantidad = null;

        //Total cantidad = cantidad + existencia
        totalCantidad = cantidad + existencia;

        return totalCantidad;
    }

    public Double calcularPrecioCosteo(Double existencia, Double cantidad, Double pCosto, Double pIngreso) {

        Double precioCosteo;

        //Precio Costeo = (TotalActual + TotalIngreso) / TotalCantidad
        precioCosteo = (calTotalActual(existencia, pCosto) + calTotalIng(cantidad, pIngreso)) / calTotalCantidad(existencia, cantidad);

        return precioCosteo;
    }
    //</editor-fold>
    //<editor-fold defaultstate="collapsed" desc="Utilitarios">

    public String convertirFechaString(Date date) {

        Format formatter = new SimpleDateFormat("dd/MM/yyyy");

        return formatter.format(date);

    }

    public void mostrarMensajeError(final String titulo, final String mensaje) {
        final FacesMessage facesMsg = new FacesMessage(FacesMessage.SEVERITY_ERROR, titulo, mensaje);
        RequestContext.getCurrentInstance().showMessageInDialog(facesMsg);
    }

    //</editor-fold>
    //<editor-fold defaultstate="collapsed" desc="Transacciones">
    public void generaIngreso(BigInteger claveCab, String tipo) {
        //OK
        if (tipo.equalsIgnoreCase("IF")) {
            //Ingreso por 
            if (claveCab != null) {
                objRegCabordencompra = regCabordencompraFacade.find(claveCab);
                if (objRegCabordencompra.getBodId() != null) {
                    objBodega = catBodegaFacade.find(objRegCabordencompra.getBodId());
                } 
                objInvCabTransaccion = invCabTransaccionFacade.crearObjeto(objBodega, claveCab, "OC", "P", null, tipo, objRegCabordencompra.getCocTipo());

                if (objInvCabTransaccion != null) {
                    buscarOrdenCompra("OD");
                    cargaCabDetalleTransaccion(objInvCabTransaccion.getCtrId());
                    if (listInvDetTransaccion.size() <= 0) {
                        invDetTransaccionFacade.crearDetalle(objInvCabTransaccion, listRegDetordencompra, "P");
                        cargaCabDetalleTransaccion(objInvCabTransaccion.getCtrId());
                    }

                    //editar valores totales en cabecera
                    Double total = invDetTransaccionFacade.sumaDetTotal(objInvCabTransaccion.getCtrId(), "TO");
                    //Edita total cabecera
                    if (total != null) {
                        objInvCabTransaccion.setCtrTotal(total);
                    }
                    //Edita total iva
                    Double totalIva = invDetTransaccionFacade.sumaDetTotal(objInvCabTransaccion.getCtrId(), "IV");
                    if (totalIva != null) {
                        objInvCabTransaccion.setCtrTotalIva(totalIva);
                    }
                    //Edita total descuento
                    Double totalDesc = invDetTransaccionFacade.sumaDetTotal(objInvCabTransaccion.getCtrId(), "DE");
                    if (totalDesc != null) {
                        objInvCabTransaccion.setCtrTotalDesc(totalDesc);
                    }
                    //Edita total general = (total - desc) + iva
                    if (objInvCabTransaccion.getCtrTotal() != null && objInvCabTransaccion.getCtrTotalDesc() != null && objInvCabTransaccion.getCtrTotalIva() != null) {
                        objInvCabTransaccion.setCtrTotaGeneral((objInvCabTransaccion.getCtrTotal() - objInvCabTransaccion.getCtrTotalDesc()) + objInvCabTransaccion.getCtrTotalIva());
                    }
                    invCabTransaccionFacade.edit(objInvCabTransaccion);

                } else {
                    mostrarMensajeError("Info.", "Existe cabecera de transación para esta orden de compra.");
                }

            }

        }

    }

    public void generaEgreso(BigInteger claveCabSol, String tipo) {
        if (claveCabSol != null) {
            //realizar egreso por solicitid
            objCabSolBodega = invCabsolicitudbodFacade.find(claveCabSol);
            if (objCabSolBodega.getCsbBodega() != null) {
                objBodega = catBodegaFacade.find(objCabSolBodega.getCsbBodega());
            } 
            objInvCabTransaccion = invCabTransaccionFacade.crearObjeto(objBodega, claveCabSol, "SB", "P", null, tipo, objCabSolBodega.getArtTipo());
            if (objInvCabTransaccion != null) {
                buscarSoliBodega("DS");
                cargaCabDetalleTransaccion(objInvCabTransaccion.getCtrId());
                if (listInvDetTransaccion.size() <= 0) {
                    invDetTransaccionFacade.crearDetalleBySolic(objInvCabTransaccion, listDetSolBodega, "P");
                    cargaCabDetalleTransaccion(objInvCabTransaccion.getCtrId());
                }

                //editar valores totales en cabecera
                Double total = invDetTransaccionFacade.sumaDetTotal(objInvCabTransaccion.getCtrId(), "TO");
                //Edita total cabecera
                if (total != null) {
                    objInvCabTransaccion.setCtrTotal(total);
                    invCabTransaccionFacade.edit(objInvCabTransaccion);
                }

            } else {
                mostrarMensajeError("Info.", "Existe cabecera de transación para esta orden de compra.");
            }

        } else {
            //realizar egreso manual
        }

    }

    public void cargaCabDetalleTransaccion(BigInteger clavecabecera) {
        //OK
        BigInteger idNumSolCompra = null;
        String sigla = null;
        if (clavecabecera != null) {
            //Carga por cabecera
            objInvCabTransaccion = invCabTransaccionFacade.find(clavecabecera);
        } else {
            //carga cabecera por parametros
            //buscar por filtro
            if (idTransaccion != null) {
                objCatGeneral = catGeneralFacade.find(idTransaccion);
                if (objCatGeneral != null) {
                    tipoTransaccion = objCatGeneral.getGenSiglatipo();
                }
            }
            if (objInvCabTransaccion.getCtrNumero() != null || objInvCabTransaccion.getCtrNoRef() != null) {
                if (objInvCabTransaccion.getCtrTipo() != null) {
                    tipoTransaccion = objInvCabTransaccion.getCtrTipo();
                }
                if (objInvCabTransaccion.getCtrFecha() != null) {
                    fecha = convertirFechaString(objInvCabTransaccion.getCtrFecha());
                }
                if (objRegCabordencompra != null && objRegCabordencompra.getCocNumSolCompra() != null) {
                    idNumSolCompra = objRegCabordencompra.getCocNumSolCompra();
                    sigla = "OC";
                }
                objInvCabTransaccion = invCabTransaccionFacade.objetoByParametros(tipoArticulo, objBodega.getBodId(), tipoTransaccion, areaId, null, fecha, objInvCabTransaccion.getCtrNumero(), idNumSolCompra, sigla, objInvCabTransaccion.getCtrNoRef());
                if (objInvCabTransaccion != null) {
                    if (objInvCabTransaccion.getCtrTipo().equalsIgnoreCase("IT")) {
                        //carga datos de la transferencia
                        objTransferencia = invCabTransaccionFacade.find(objInvCabTransaccion.getCtrClavetabla());
                        if (objTransferencia != null) {
                            cargaArea(objTransferencia.getCatBodega().getBodId());
                        }
                    }
                    if (objInvCabTransaccion.getCtrTipo().equalsIgnoreCase("ET")) {
                        //carga AREA O BODEGA
                        if(objInvCabTransaccion.getCtrArea() != null){
                            cargaArea(objInvCabTransaccion.getCtrArea());
                        }
                    }
                    if (objInvCabTransaccion.getCtrTipo().equalsIgnoreCase("EV")) {
                        //carga datos de la transferencia
                        objCatGeneral = catGeneralFacade.objByDescripcion(objInvCabTransaccion.getCtrObservacion());
                        if(objCatGeneral != null){
                        servicioId = objCatGeneral.getGenId();
                        }
                        if(objInvCabTransaccion.getCtrValida() != null && objInvCabTransaccion.getCtrValida().equalsIgnoreCase("S")) {
                            validada = true;
                        } else {
                            validada = false;
                        }
                       
                    }
                    

                }
            } else {
                mostrarMensajeError("Info", "Seleccione Fecha y NO.Transacción.");
            }

        }
        if (objInvCabTransaccion != null) {
            //CaRga valoreS de cabecera
            if (objInvCabTransaccion.getTipoArticulo() != null) {
                //carga tipo de articulo
                tipoArticulo = objInvCabTransaccion.getTipoArticulo();
            }
            if (objInvCabTransaccion.getCtrArea() != null) {
                //carga area
                areaId = objInvCabTransaccion.getCtrArea();
            }
            if (objInvCabTransaccion.getCtrClavetabla() != null && objInvCabTransaccion.getCtrSiglastabla() != null) {
                if (objInvCabTransaccion.getCtrSiglastabla().equalsIgnoreCase("OC")) {
                    //es por orden de compra 
                    objRegCabordencompra = regCabordencompraFacade.find(objInvCabTransaccion.getCtrClavetabla());
                    //cargar numero de orden de compra
                    numeroOrdenCompra = objRegCabordencompra.getCocNumero();
                    //cargar proveedor
                    proveedorId = objRegCabordencompra.getEmpId();
                    objRegCabordencompra = regCabordencompraFacade.find(objInvCabTransaccion.getCtrClavetabla());
                }
                if (objInvCabTransaccion.getCtrSiglastabla().equalsIgnoreCase("PA")) {
                    //CARGA DATOS DEL PACIENTE
                    objRegPaciente = regPacienteFacade.find(objInvCabTransaccion.getCtrClavetabla());
                }

            }
            if (objInvCabTransaccion.getCtrTipo() != null) {
                //carga tipo transaccion
                objCatGeneral = catGeneralFacade.objCatGeneralPapaActivo(objInvCabTransaccion.getCtrTipo(), BigInteger.ONE);
                if (objCatGeneral != null) {
                    idTransaccion = objCatGeneral.getGenId();
                }
            }

            //carga detalle.    
            listInvDetTransaccion = invDetTransaccionFacade.listAllByParam(objInvCabTransaccion.getCtrId(), null);

        } else {
            mostrarMensajeError("Info.", "No existe registro para el filtro seleccionado.");
        }

        //direccion a tab 
        inxTab = 1;

    }
    public void cargaArea(BigInteger idArea) {
        if(idArea != null){
        CatBodega objArea = catBodegaFacade.find(idArea);
        if (objArea != null) {
            area = objArea.getBodTipo();
            areaId = objInvCabTransaccion.getCtrArea();
        }
        }
    }    

    public void editarDetTrans(InvDetTransaccion objeto, String valor) {
        System.out.println("formulario es "+formulario);
        //si es egreso valida cantidad
        //ok
        invDetTransaccionFacade.editarDetTrans(objeto, valor);
        if(objeto.getInvCabTransaccion().getCtrTipo().equalsIgnoreCase("IF") && (valor.equalsIgnoreCase("CI") || valor.equalsIgnoreCase("VI"))){
        //si se modifica valor de cantidad ingreso o valor de ingreso update iva uodate descuento
         invDetTransaccionFacade.editarDetTrans(objeto, "IV");
         invDetTransaccionFacade.editarDetTrans(objeto, "DE");
        }
        //carga cabecera para actualizar valores generales
        objInvCabTransaccion = invCabTransaccionFacade.find(objInvCabTransaccion.getCtrId());
        //carga detalle.    
        listInvDetTransaccion = invDetTransaccionFacade.listAllByParam(objInvCabTransaccion.getCtrId(), null);
        final RequestContext context = RequestContext.getCurrentInstance();
        context.update("tabPrincipal:" + formulario);
    }
    
    public void editarDetTrans(InvDetTransaccion objeto, String valor, String form) {
        //si es egreso valida cantidad
        //ok
        invDetTransaccionFacade.editarDetTrans(objeto, valor);
       
        //carga cabecera para actualizar valores generales
        objInvCabTransaccion = invCabTransaccionFacade.find(objInvCabTransaccion.getCtrId());
        //carga detalle.    
        listInvDetTransaccion = invDetTransaccionFacade.listAllByParam(objInvCabTransaccion.getCtrId(), null);
        final RequestContext context = RequestContext.getCurrentInstance();
        context.update("tabPrincipal:" + form);
    }
    public boolean validaExistenciaByBodArea(BigInteger artId, BigInteger idBodArea) {
        boolean ctrEditExistencia = false;
        if (artId != null && idBodArea != null) {
            InvExistenciaBodega objNewExi = invExistenciaBodegaFacade.obtieneObjetoByParam(idBodArea,artId, "A");
            if (objNewExi != null) {
            ctrEditExistencia = true;
            }
           

        }
         return ctrEditExistencia;
    }
    public void editDetByTransf(InvDetTransaccion objeto, String valor,BigInteger idBodArea){
       
        if (objeto.getExiId() != null) {
            boolean edit = true;
            //obtiene articulo
            InvExistenciaBodega objExistencia = invExistenciaBodegaFacade.find(objeto.getExiId());
            if (objExistencia.getCatArticulo() != null) {
                if (area.equalsIgnoreCase("B")) {
                    edit = validaExistenciaByBodArea(objExistencia.getCatArticulo().getArtId(), idBodArea);
                }
                if (edit) {
                    editarDetTrans(objeto, valor);
                } else {
                    if (idBodArea != null) {
                        CatBodega bodegaarea = catBodegaFacade.find(idBodArea);
                        mostrarMensajeError("info.", "No existe existencia del articulo en la bodega." + bodegaarea.getBodDescripcion());
                        cargaCabDetalleTransaccion(objInvCabTransaccion.getCtrId());
                        
                    }

                }
            }

        }

    }
    public boolean isNumeric(final String cadena) {
        try {
            Integer.parseInt(cadena);
            return true;
        } catch (NumberFormatException nfe) {
            return false;
        }
    }
    public void editDetTransByRegulacion(InvDetTransaccion objeto, String tipoReg) {
        //VALIDA CANTIDAD A AJUSTAR
        int cantidad = 0;
        String cant;
        //valida numerico
        if (objeto.getDtrCantegreso() != null) {
            cant = objeto.getDtrCantegreso().toString();
            if (isNumeric(cant)) {

                cantidad = objeto.getDtrCantegreso().intValue();
            } else {
                mostrarMensajeError("Inf.", "La cantidad debe ser un valor númerico.");
            }
        } else if (objeto.getDtrCantingreso() != null) {
            cant = objeto.getDtrCantingreso().toString();
            if (isNumeric(cant)) {

                cantidad = objeto.getDtrCantingreso().intValue();
            } else {
                mostrarMensajeError("Inf.", "La cantidad debe ser un valor númerico.");
            }
        } if(cantidad != 0) {
        //realiza transaccion
            //obtiene tipo de transaccion en cabecera
            if (tipoReg != null) {
                if (tipoReg.equalsIgnoreCase("AS") || tipoReg.equalsIgnoreCase("ND")) {
                    //aumentar existencia
                    objeto.setDtrCostoIngreso(objeto.getDtrCIngresoUnitario());
                    invDetTransaccionFacade.editarDetTrans(objeto, "CI");

                }
                if (tipoReg.equalsIgnoreCase("AF") || tipoReg.equalsIgnoreCase("NC")) {
                    if (objeto.getDtrCantegreso().intValue() > objeto.getInvExistenciaBodega().getExiExistencia().intValue()) {
                        mostrarMensajeError("Alerta", "Existencia es menor a la cantidad del ajuste.");
                        if (objInvCabTransaccion.getCtrId() != null) {
                            listInvDetTransaccion = invDetTransaccionFacade.listAllByParam(objInvCabTransaccion.getCtrId(), null);
                        }
                    }
                    else {
                    //disminuye existencia
                    objeto.setDtrCostoEgreso(objeto.getExiCostoProm());
                    invDetTransaccionFacade.editarDetTrans(objeto, "CE");
                    invDetTransaccionFacade.editarDetTrans(objeto, "VE");
                }

                } 

                //carga cabecera para actualizar valores generales
                objInvCabTransaccion = invCabTransaccionFacade.find(objInvCabTransaccion.getCtrId());
                //carga detalle.    
                listInvDetTransaccion = invDetTransaccionFacade.listAllByParam(objInvCabTransaccion.getCtrId(), null);
                final RequestContext context = RequestContext.getCurrentInstance();
                context.update("tabPrincipal:" + formAjuste);
            }
        }

    }

    public void validaCantEgreso(InvDetTransaccion objeto, String valor,String formUpdate) {
        if (objeto.getDtrCantegreso().intValue() > objeto.getInvExistenciaBodega().getExiExistencia().intValue()) {
            mostrarMensajeError("Alerta", "Existencia es menor a la cantidad solicitada.");
            if(objInvCabTransaccion.getCtrId() != null){
           listInvDetTransaccion = invDetTransaccionFacade.listAllByParam(objInvCabTransaccion.getCtrId(), null);
            }
        } else {
            editarDetTrans(objeto, valor,formUpdate);
        }
    }
   

    

    public void crearEditLote(BigInteger artId, BigInteger claveDetalle, BigInteger cant) {
        //revizar
        if (objBodega.getBodId() != null) {
            InvLoteArticulo lote = new InvLoteArticulo();
            if (cant != null && cant.intValue() != 0) {
                objInvLoteArticulo.setLotCantidad(cant);
            }
           
            lote = invLoteArticuloFacade.crearLote(objBodega.getBodId(), artId, objInvLoteArticulo);//TODO EL USER SOLICITA QUITAR ESTA VALIDADION

            if (lote != null && claveDetalle != null) {
                //Editar lote en detalle transaccion
                objInvDetTransaccion = invDetTransaccionFacade.find(claveDetalle);
                objInvDetTransaccion.setLotId(lote.getLotId());
                invDetTransaccionFacade.editarDetTrans(objInvDetTransaccion, "LO");
                cargaCabDetalleTransaccion(objInvCabTransaccion.getCtrId());
            } else {
                objInvLoteArticulo = null;
                mostrarMensajeError("Alerta", "La clave detalle es nula.Verifique");
            }

        } else {
            mostrarMensajeError("Info", "No existe parametrizacion de la bodega.Verifique.");
        }

    }

    public void cargaLoteByEgreso(BigInteger artId, BigInteger cantidad) {
        cantLote = cantidad;
        if(objInvDetTransaccion != null)
        {
            if(objInvDetTransaccion.getLotId() == null){
        lisInvLoteArticulo = invLoteArticuloFacade.obtieneLoteProxCaducar(objInvDetTransaccion.getInvCabTransaccion().getCatBodega().getBodId(), artId, "A");
            }
            else{
            //ya existe lote descargado cargar existente
                lisInvLoteArticulo = new ArrayList<>();
                objInvLoteArticulo = invLoteArticuloFacade.find(objInvDetTransaccion.getLotId());
                if(objInvLoteArticulo != null){
                lisInvLoteArticulo.add(objInvLoteArticulo);
                }
            }
        }
    }
    public void cargaLoteByArt(BigInteger artId,String caducado){
        if(objInvCabTransaccion != null && objInvCabTransaccion.getCtrTipo() != null){
            tipoTransaccion = objInvCabTransaccion.getCtrTipo();
            if(tipoTransaccion.equalsIgnoreCase("AF") || tipoTransaccion.equalsIgnoreCase("NC")){
            ctrEgreso = true;
            }
             if(tipoTransaccion.equalsIgnoreCase("AS") || tipoTransaccion.equalsIgnoreCase("ND")){
            ctrIng = true;
            }
        
        }
         if(objInvDetTransaccion != null && objInvDetTransaccion.getInvCabTransaccion() != null && artId != null)
         {
        
             if(caducado.equalsIgnoreCase("N")){
                 lisInvLoteArticulo = invLoteArticuloFacade.listAllByParam(objInvDetTransaccion.getInvCabTransaccion().getCatBodega().getBodId(), artId, null, "A", descripcion, null, null);
             }
             else{
                 //traer caducados
                 fInicio = convertirFechaString(new Date());
              lisInvLoteArticulo = invLoteArticuloFacade.listAllByParam(objInvDetTransaccion.getInvCabTransaccion().getCatBodega().getBodId(), artId, null, "A", descripcion, fInicio, "=");
        
             }
        
                }
    
    }

    public void editarLote(InvLoteArticulo objeto) {
        invLoteArticuloFacade.crearLote(null, null, objeto);
        //Editar lote en detalle transaccion
        objInvDetTransaccion = invDetTransaccionFacade.find(objInvDetTransaccion.getDtrId());
        objInvDetTransaccion.setLotId(objeto.getLotId());
        invDetTransaccionFacade.editarDetTrans(objInvDetTransaccion, "LO");
    }

    public void descargaLote(InvLoteArticulo objeto) {
         if(objeto != null){
              //valida la cantidad de egreso
        if (objeto.getLotCantegreso().intValue() > objeto.getLotCantidad().intValue()) {
            mostrarMensajeError("Alerta", "Cantidad del lote es menor a la cantidad solicitada.");
            if(objeto.getArtId() != null){
            cargaLoteByEgreso(objeto.getArtId(), null);
            }
        } else {
            //editar la cantidad de egreso en lote
       invLoteArticuloFacade.crearLote(null, null, objeto);
         //realiza el descargo de cantidad.
        //editar en detalle el lote
            if(objInvDetTransaccion != null && objInvDetTransaccion.getDtrId() != null)
            {
            if(objeto.getLotId() != null){
            objInvDetTransaccion.setLotId(objeto.getLotId());
            editarDetTrans(objInvDetTransaccion, "LO");
                }
            }
        }
      
        }
        else{
            mostrarMensajeError("Info.LoteNull", "Seleccione el lote a modificar.");
        }
       
        
    }
    public void bajaByLote(InvLoteArticulo objeto){
         //editar la cantidad de egreso en lote
        if(objeto != null){
       invLoteArticuloFacade.crearLote(null, null, objeto);
       if(objeto.getArtId() != null){
        cargaLoteByArt(objeto.getArtId(), "S");
       }
        }
        else{
            mostrarMensajeError("Info.LoteNull", "Seleccione el lote a modificar.");
        }
    
    }

    public void crearDetalleTrans() {
        //ok
        boolean ctrInsertar = invDetTransaccionFacade.verificaExisNull(objInvCabTransaccion.getCtrId());
        if (ctrInsertar) {
            if (objInvCabTransaccion.getCtrId() != null) {
                invDetTransaccionFacade.crearDetalle(objInvCabTransaccion, null, "P");
                if (objInvCabTransaccion.getCtrTipo().equalsIgnoreCase("IF") || objInvCabTransaccion.getCtrTipo().equalsIgnoreCase("ID") || objInvCabTransaccion.getCtrTipo().equalsIgnoreCase("IR")) {

                    //editar valores totales en cabecera
                    Double total = invDetTransaccionFacade.sumaDetTotal(objInvCabTransaccion.getCtrId(), "TO");
                    //Edita total cabecera
                    if (total != null) {
                        objInvCabTransaccion.setCtrTotal(total);
                    }
                    //Edita total iva
                    Double totalIva = invDetTransaccionFacade.sumaDetTotal(objInvCabTransaccion.getCtrId(), "IV");
                    if (totalIva != null) {
                        objInvCabTransaccion.setCtrTotalIva(totalIva);
                    }
                    //Edita total descuento
                    Double totalDesc = invDetTransaccionFacade.sumaDetTotal(objInvCabTransaccion.getCtrId(), "DE");
                    if (totalDesc != null) {
                        objInvCabTransaccion.setCtrTotalDesc(totalDesc);
                    }
                    //Edita total general = (total - desc) + iva
                    if (objInvCabTransaccion.getCtrTotal() != null && objInvCabTransaccion.getCtrTotalDesc() != null && objInvCabTransaccion.getCtrTotalIva() != null) {
                        objInvCabTransaccion.setCtrTotaGeneral((objInvCabTransaccion.getCtrTotal() - objInvCabTransaccion.getCtrTotalDesc()) + objInvCabTransaccion.getCtrTotalIva());
                    }
                    invCabTransaccionFacade.edit(objInvCabTransaccion);

                }
                cargaCabDetalleTransaccion(objInvCabTransaccion.getCtrId());

            } else {
                mostrarMensajeError("Inf. cabecera null", "No existe cabacera para este detalle .Verifique");
            }
        } else {
            mostrarMensajeError("Alerta", "Existe registros nulo.Complete la información.");
        }

    }
    
    public void crearItemFueraInv() {
        System.out.println("entra");
        if (objCatArticulo.getArtId() != null) {
            objCatArticulo = catArticuloFacade.find(objCatArticulo.getArtId());
            if (objInvCabtomafisica != null) {
                if (objInvDettomafisica != null) {
                    invDettomafisicaFacade.crearItemFueraInv(objInvCabtomafisica, "T", objCatArticulo, objInvDettomafisica.getDtfCantidadtoma(), objInvDettomafisica.getDtfObservacion());
                    cargaDetTomaFisica();
                }
            }
        }
        
        objCatArticulo = null;

    }

    public void editOrdenCompra(RegCabordencompra objeto) {
        //ok
        regCabordencompraFacade.edit(objeto);
        cargaCabDetalleTransaccion(objInvCabTransaccion.getCtrId());

    }

    public void confirmaIngreso() {
        for (InvDetTransaccion detalle : listInvDetTransaccion) {
            //valida el total
            if (detalle.getDtrTotal() != null && detalle.getDtrTotal() != 0.0) {
                objInvExistenciaBodega = invExistenciaBodegaFacade.find(detalle.getInvExistenciaBodega().getExiId());
                //Actualiza existencia actual
                detalle.setExiId(detalle.getInvExistenciaBodega().getExiId());
                invDetTransaccionFacade.editarDetTrans(detalle, "EX");
                //Actualiza existencia por item
                invExistenciaBodegaFacade.editarExistenciabyIngreso(objInvExistenciaBodega, "EX", detalle.getDtrCantingreso(), null);
                //si es ingreso actualiza costos
                if (objInvCabTransaccion != null && objInvCabTransaccion.getCtrTipo() != null) {
                    if (objInvCabTransaccion.getCtrTipo().equalsIgnoreCase("IF") || objInvCabTransaccion.getCtrTipo().equalsIgnoreCase("ID") || objInvCabTransaccion.getCtrTipo().equalsIgnoreCase("IR") || objInvCabTransaccion.getCtrTipo().equalsIgnoreCase("IT") || objInvCabTransaccion.getCtrTipo().equalsIgnoreCase("IV")) {

                        if (detalle.getDtrCIngresoUnitario() != null) {
                   //Actualizar precio de costo por item
                            //precio de costo= precio de ingreso unitario final
                            objInvExistenciaBodega.setExiPreciocosto(detalle.getDtrCIngresoUnitario());
                            invExistenciaBodegaFacade.editarExistenciabyIngreso(objInvExistenciaBodega, "PC", null, null);
                            //aActualiza costopromedio por item
                            Double tIngreso = detalle.getDtrCantingreso().intValue() * detalle.getDtrCIngresoUnitario();
                            invExistenciaBodegaFacade.editarExistenciabyIngreso(objInvExistenciaBodega, "CP", detalle.getDtrCantingreso(), tIngreso);

                        }
                        if (objInvCabTransaccion.getCtrTipo().equalsIgnoreCase("IV")) {
                        //es ingreso devolucion
                            //obtiene liquidacion
                            //restar cantidades
                            //si la cantidad es cero
                            //anula liquidacion

                        }

                    }

                }

                //Actualiza el estado en detalle//
                detalle.setDtrEstado("F");
                invDetTransaccionFacade.editarDetTrans(detalle, "ES");

            } else {
                mostrarMensajeError("Alerta.Total es 0 o nulo", "Verifique valor total.");
            }

        }
        //Actualiza estado de la cabecera
        objInvCabTransaccion.setCtrEstado("F");
        invCabTransaccionFacade.editarObjeto(objInvCabTransaccion, "ES");
        cargaCabDetalleTransaccion(objInvCabTransaccion.getCtrId());
        if(objInvCabTransaccion.getCtrEstado().equalsIgnoreCase("F")){
        mostrarMensajeError("Info", "Se realizó el ingreso satisfactorioramente.");
        }
        else{
        mostrarMensajeError("Alerta", "Ocurrió un error en la transacción.Verifique.");
        }

    }

    public void inactivaIngreso() {
        if(objInvCabTransaccion != null && objInvCabTransaccion.getCtrId() != null){
        String mensaje = invCabTransaccionFacade.inactivaCabDet(objInvCabTransaccion.getCtrId());
        objInvCabTransaccion = invCabTransaccionFacade.find(objInvCabTransaccion.getCtrId());
        listInvDetTransaccion = invDetTransaccionFacade.listAllByParam(objInvCabTransaccion.getCtrId(), null);
        mostrarMensajeError("Info", mensaje);
        }
    }

    public void confirmaEgreso() {
         
        for (InvDetTransaccion detalle : listInvDetTransaccion) {

            objInvExistenciaBodega = invExistenciaBodegaFacade.find(detalle.getInvExistenciaBodega().getExiId());
            //Actualiza existencia actual
            detalle.setExiId(detalle.getInvExistenciaBodega().getExiId());
            invDetTransaccionFacade.editarDetTrans(detalle, "EX");
            //Actualiza existencia por item
            invExistenciaBodegaFacade.editarExistenciabyIngreso(objInvExistenciaBodega, "EXE", detalle.getDtrCantegreso(), null);

            //Actualiza el estado en detalle//
            detalle.setDtrEstado("F");
            invDetTransaccionFacade.editarDetTrans(detalle, "ES");

        }
        //Actualiza estado de la cabecera
        objInvCabTransaccion.setCtrEstado("F");
        invCabTransaccionFacade.editarObjeto(objInvCabTransaccion, "ES");
        mostrarMensajeError("Info", "Se realizó el egreso satisfactorioramente.");
        cargaCabDetalleTransaccion(objInvCabTransaccion.getCtrId());

    }
    
    public void crearIngresoByTransferencia(){
     //verificar si es egreso por transferencia
        if(objInvCabTransaccion.getCtrTipo() != null && objInvCabTransaccion.getCtrTipo().equalsIgnoreCase("ET")){
         
            if(area.equalsIgnoreCase("B")){
                //realizar ingreso pendiente cuando es transferencia a otra bodega
                InvCabTransaccion objetoNew = new InvCabTransaccion();
                objetoNew = invCabTransaccionFacade.crearObjeto(objInvCabTransaccion.getCatBodegaArea(), objInvCabTransaccion.getCtrId(), "ET", "P", null, "IT", objInvCabTransaccion.getCatArticulo().getArtId());
                if(objetoNew != null){
                    //TODO PENDIENTE REVIZAR
                }
            }
            
        }
    }
    

    public void confirmaBaja() {
        for (InvDetTransaccion detalle : listInvDetTransaccion) {

            objInvExistenciaBodega = invExistenciaBodegaFacade.find(detalle.getInvExistenciaBodega().getExiId());
            //Actualiza estado de la existencia por item
            objInvExistenciaBodega.setExiEstado("I");
            invExistenciaBodegaFacade.editarExistenciabyIngreso(objInvExistenciaBodega, "ES", null, null);

            //Actualiza el estado en detalle//
            detalle.setDtrEstado("F");
            invDetTransaccionFacade.editarDetTrans(detalle, "ES");

        }
        //Actualiza estado de la cabecera
        objInvCabTransaccion.setCtrEstado("F");
        invCabTransaccionFacade.editarObjeto(objInvCabTransaccion, "ES");
        mostrarMensajeError("Info", "La Baja se realizó satisfactorioramente.");
        cargaCabDetalleTransaccion(objInvCabTransaccion.getCtrId());

    }

    public void confirmaAjuste(String tipoTrans) {
        BigInteger exitencia;
        //VERIFICAR SI EL AJUSTE ES POR TOMA FISICA DEL INVENTARIO
         if (objInvCabTransaccion != null && objInvCabTransaccion.getCtrSiglastabla() != null) {
                        if (objInvCabTransaccion.getCtrSiglastabla().equalsIgnoreCase("IV")) {
                            //verifica si se relaciona con inventario modificar saldo actual del item
                            objInvCabtomafisica = invCabtomafisicaFacade.find(objInvCabTransaccion.getCtrClavetabla());
                            
                        }
                    }
        if (tipoTrans != null) {
            for (InvDetTransaccion detalle : listInvDetTransaccion) {
                objInvExistenciaBodega = invExistenciaBodegaFacade.find(detalle.getInvExistenciaBodega().getExiId());

                if (objInvExistenciaBodega != null) {

                    if (tipoTrans.equalsIgnoreCase("AF") || tipoTrans.equalsIgnoreCase("NC")) {
                    //AF = ajuste por faltante // NC= nota de credito
                        //disminuir existencia por item (realizar egreso)
                        exitencia = detalle.getDtrCantegreso();
                        invExistenciaBodegaFacade.editarExistenciabyIngreso(objInvExistenciaBodega, "EXE", detalle.getDtrCantegreso(), null);
                    }
                    if (tipoTrans.equalsIgnoreCase("AS") || tipoTrans.equalsIgnoreCase("ND")) {
                    //AS = ajuste por sobrante// ND = nota de debito
                        //aumentar existencia por item (realizar ingreso)
                        exitencia = detalle.getDtrCantingreso();
                        invExistenciaBodegaFacade.editarExistenciabyIngreso(objInvExistenciaBodega, "EX", detalle.getDtrCantingreso(), null);

                        }
                  
                           if (objInvCabtomafisica != null) {
                               //actualiza det tom fisica
                               //RECUPERA NUEVA EXISTENCIA
                               objInvExistenciaBodega = invExistenciaBodegaFacade.find(objInvExistenciaBodega.getExiId());
                                objInvDettomafisica = invDettomafisicaFacade.objetoByCabExistenciaEstado(objInvCabtomafisica.getCtfId(), objInvExistenciaBodega.getExiId(), "T");
                                if (objInvDettomafisica != null) {
                                    objInvDettomafisica.setDtfSaldoactual(objInvExistenciaBodega.getExiExistencia());
                                    invDettomafisicaFacade.editarDetToma(objInvDettomafisica, "SA");
                                }

                            }
                    //Actualiza el estado en detalle
                    detalle.setDtrEstado("F");
                    invDetTransaccionFacade.editarDetTrans(detalle, "ES");
                }

            }
            //Actualiza estado de la cabecera
            objInvCabTransaccion.setCtrEstado("F");
            invCabTransaccionFacade.editarObjeto(objInvCabTransaccion, "ES");
            mostrarMensajeError("Info", "La transacción se realizó  satisfactorioramente.");
            cargaCabDetalleTransaccion(objInvCabTransaccion.getCtrId());
        } else {
            mostrarMensajeError("Inf.Tipo Trans null", "Seleccione tipo de transacción");

        }

    }

    public void eliminaDetalle(BigInteger dtrId) {
        if (dtrId != null) {
            objInvDetTransaccion = invDetTransaccionFacade.find(dtrId);
            if(objInvDetTransaccion.getInvLoteArticulo() != null){
            //VERIFICA LOTE ELIMINA EL LOTE
            objInvLoteArticulo = invLoteArticuloFacade.find(objInvDetTransaccion.getInvLoteArticulo().getLotId());
            if(objInvLoteArticulo != null){
            invLoteArticuloFacade.remove(objInvLoteArticulo);
            }
            }
            invDetTransaccionFacade.eliminaDetalle(objInvDetTransaccion);
            
                        objInvCabTransaccion = invCabTransaccionFacade.find(objInvCabTransaccion.getCtrId());
            listInvDetTransaccion = invDetTransaccionFacade.listAllByParam(objInvCabTransaccion.getCtrId(), null);
        } else {
            mostrarMensajeError("Inf detalle null", "Seleccione detalle para eliminar.");
        }

    }

    public void editIva(InvDetTransaccion objeto) {
        System.out.println("el fprmularia es "+formulario);
        if (iva) {
            objeto.setDtrIva("S");

        } else {
            objeto.setDtrIva("N");

        }
        editarDetTrans(objeto, "IV", formulario);

    }

      //</editor-fold>
    //<editor-fold defaultstate="collapsed" desc="Inventario">
    public void cargaCabTomaFisica() {
        if(objInvCabtomafisica.getCtfFecha() != null){
        fecha = convertirFechaString(objInvCabtomafisica.getCtfFecha()); 
        }
        List<InvCabtomafisica> cabecera = invCabtomafisicaFacade.listAllByParam(objBodega.getBodId(), null, null, fecha, null, "=", objInvCabtomafisica.getCtfNumero(), tipoArticulo);
        if (cabecera.isEmpty()) {
            mostrarMensajeError("Alerta", "No se encontro registros.");
        } else {
            if (cabecera.size() == 1) {
                objInvCabtomafisica = invCabtomafisicaFacade.find(cabecera.get(0).getCtfId());
                //obtiene tipo articulo
                if (objInvCabtomafisica.getCtfTipoArt() != null) {
                    tipoArticulo = objInvCabtomafisica.getCtfTipoArt();
                }
                if (objInvCabtomafisica.getCtfImpetiquetas() != null && objInvCabtomafisica.getCtfImpetiquetas().equalsIgnoreCase("S")) {
                    etiquetas = true;
                } else {
                    etiquetas = false;
                }
                //carga detalle
                cargaDetTomaFisica();
            } else {
                mostrarMensajeError("Alerta", "Existe mas de un registro cabecera para este filtro.Por favor verifique");
            }
        }

    }

    public void cargaDetTomaFisica() {
        listInvDettomafisica = invDettomafisicaFacade.listAllByParam(objInvCabtomafisica.getCtfId(), null);
    }

    public void crearDetalleTomaFisica() {
        if (objInvCabtomafisica != null) {
//crear detalle toma fisica
            invDettomafisicaFacade.crearDetalle(objInvCabtomafisica, objInvCabtomafisica.getCtfEstado());
            listInvDettomafisica = invDettomafisicaFacade.listAllByParam(objInvCabtomafisica.getCtfId(), null);
        }

    }

    public void validaFinalizarInv() {
   //debe estar ingresada la toma fisica
        //no debe existir diferencias
        if(objInvCabtomafisica != null){
        mensajeInv = invCabtomafisicaFacade.validaFinalizarInv(objInvCabtomafisica.getCtfId(), "F");
        ctrFinaliza = mensajeInv != null && mensajeInv.trim().length() > 0 && !(mensajeInv.trim().toLowerCase().compareTo("null") == 0);
        if(!ctrFinaliza){
        mensajeInv = "Confirma finalizar el inventario.";
        }
       
        }
        
    }

    public void finalizaInv() {
        if(objInvCabtomafisica != null)
        {
        mensajeInv = invCabtomafisicaFacade.modificaEstadoCabDet(objInvCabtomafisica.getCtfId(), "F");
        objInvCabtomafisica = invCabtomafisicaFacade.find(objInvCabtomafisica.getCtfId());
        mostrarMensajeError("Inf.", mensajeInv);
        }
    }

    public void inactivaInv() {
        mensajeInv = invCabtomafisicaFacade.modificaEstadoCabDet(objInvCabtomafisica.getCtfId(), "I");
        cargaCabTomaFisica();
        cargaDetTomaFisica();
        mostrarMensajeError("Info.", mensajeInv);
    }

    public void selecTipoInv() {
        if (tipoArticulo != null) {
            objInvCabtomafisica = new InvCabtomafisica();
            listInvDettomafisica = null;
            final RequestContext context = RequestContext.getCurrentInstance();
            RequestContext.getCurrentInstance().execute("PF('dlgGeneraInv').show()");
            context.update("tabPrincipal:formNewInv");

        } else {
            mostrarMensajeError("Alerta", "Seleccione tipo de articulo.Campo requerido");
        }

    }

    public void btnNoInv() {
        cargaCabTomaFisica();
    }

    public void reporteComparativo() {
        inxTab = 1;
    }

    public void generaCabTomaFisica() {
        //estado n(planificado) tipo inv(general)
        objInvCabtomafisica = invCabtomafisicaFacade.crearObjeto(objBodega, "N", "G", tipoArticulo);
        if (objInvCabtomafisica != null) {
            crearDetalleTomaFisica();
        } else {
            mostrarMensajeError("Alerta", "Existe registro de inventario sin finalizar verifique.");
        }

    }

    public void editarCabTomaFisica(String valor) {
        if (objInvCabtomafisica.getCtfId() != null) {
            if (valor.equalsIgnoreCase("IE")) {
                if (etiquetas) {
                    objInvCabtomafisica.setCtfImpetiquetas("S");
                } else {
                    objInvCabtomafisica.setCtfImpetiquetas("N");
                }
            }
            invCabtomafisicaFacade.editarObjeto(objInvCabtomafisica, valor);
            //Buscar Cabecera para actualizar valores
            objInvCabtomafisica = invCabtomafisicaFacade.find(objInvCabtomafisica.getCtfId());
        }
    }

    public void editDetTomaFisica(InvDettomafisica objetoEdit, String valor) {
        invDettomafisicaFacade.editarDetToma(objetoEdit, valor);
        cargaCabTomaFisica();
        listInvDettomafisica = invDettomafisicaFacade.listAllByParam(objInvCabtomafisica.getCtfId(), null);
        final RequestContext context = RequestContext.getCurrentInstance();
        context.update("tabPrincipal:" + formulario);
    }
    public void generaAjusteByInv(InvCabtomafisica objCabToma,String tipoDif) {

        if (objCabToma != null) {
            boolean isBod = objCabToma.getCtfBodId() != null;
              boolean isTipoDif = tipoDif != null && tipoDif.trim().length() > 0 && !(tipoDif.trim().toLowerCase().compareTo("null") == 0);
            if (isBod) {
                objBodega = catBodegaFacade.find(objCabToma.getCtfBodId());
                if (objCabToma.getCtfTipoArt() != null) {
                    objCatArticulo = catArticuloFacade.find(objCabToma.getCtfTipoArt());
                    if (objCatArticulo != null) {
                        tipoArticulo = objCatArticulo.getArtId();
                        if (isBod && tipoArticulo != null) {
                            if(isTipoDif){
                                objCatGeneral = catGeneralFacade.objCatGeneralPapaActivo(tipoDif, BigInteger.ONE);
                                if(objCatGeneral != null){
                                idTransaccion = objCatGeneral.getGenId();
                                }
                                List<InvDettomafisica> listDiferencias = invDettomafisicaFacade.listDiferencias(objCabToma.getCtfId(), "T",tipoDif );
                            if (listDiferencias.size() > 0) {
                                objInvCabTransaccion = invCabTransaccionFacade.crearObjeto(objBodega, objCabToma.getCtfId(), "IV", "P", null, tipoDif, tipoArticulo);
                                if (objInvCabTransaccion != null) {
                                    invDetTransaccionFacade.crearDetalleByTomaFisica(objInvCabTransaccion, listDiferencias, "P");
                                    cargaCabDetalleTransaccion(objInvCabTransaccion.getCtrId());
                                    inxTab = 2;
                                }
                            }
                            }
                            else{
                              mostrarMensajeError("Info.Tipo de ajuste null", "Seleccione tipo de ajuste.");
                            }
                            


                        }
                    }
                } else {
                    mostrarMensajeError("Info.Tipo articulo Null", "Seleccione tipo de articulo.");
                }

            } else {
                mostrarMensajeError("Info.Bodega Null", "Seleccione la bodega.");
            }

        } else {
            mostrarMensajeError("Info.Cab Null", "Seleccione cabecera de la toma fisica.");
        }

    }

        //</editor-fold>
    //</editor-fold>
    
      //<editor-fold defaultstate="collapsed" desc="Funciones de Farmacia">
    public void bsqTransferencia(String estado){
        if(fechaIni != null){
        fecha = convertirFechaString(fechaIni);
        }
        if(objBodega != null){
    objTransferencia = invCabTransaccionFacade.objetoByTransferencia("ET", objBodega.getBodId(), estado, fecha, noDoc);
    listCabTransferencia = new ArrayList<>();
    listCabTransferencia.add(objTransferencia);
        }
    
    }
    public void bsqDetalleTransf(String estado){
        if(objTransferencia != null){
    listDetTransferencia = invDetTransaccionFacade.listAllByParam(objTransferencia.getCtrId(), estado);
        }
    }
    public void generaIngresoByTransf(InvCabTransaccion objTransf, List<InvDetTransaccion> listDetalle){
        if(objTransf != null && objBodega != null){
            if(objTransf.getCatBodega() != null){
            areaId = objTransf.getCatBodega().getBodId();
            }
          objInvCabTransaccion = invCabTransaccionFacade.crearObjeto(objBodega, objTransf.getCtrId(), "ET", "P", null, "IT", objTransf.getTipoArticulo());
          if(objInvCabTransaccion != null){
              cargaCabDetalleTransaccion(objInvCabTransaccion.getCtrId());
              if(listInvDetTransaccion.size() <= 0){
              // crear detalle 
                   if(listDetalle == null){
                  listDetalle = invDetTransaccionFacade.listAllByParam(objTransf.getCtrId(), null);
              }
              if(listDetalle.size() >0){
              for (InvDetTransaccion objDetTransf : listDetalle) {
               invDetTransaccionFacade.crearDetalleByParam(objBodega.getBodId(),objInvCabTransaccion.getCtrId(), objDetTransf.getInvExistenciaBodega().getCatArticulo().getArtId(), objDetTransf.getDtrCantegreso(), "P", "I",objDetTransf.getInvExistenciaBodega().getExiCostoprom());   
              }
               cargaCabDetalleTransaccion(objInvCabTransaccion.getCtrId());
               
                  //editar valores totales en cabecera
                    Double total = invDetTransaccionFacade.sumaDetTotal(objInvCabTransaccion.getCtrId(), "TO");
                    //Edita total cabecera
                    if (total != null) {
                        objInvCabTransaccion.setCtrTotal(total);
                    }
                    //Edita total iva
                    Double totalIva = invDetTransaccionFacade.sumaDetTotal(objInvCabTransaccion.getCtrId(), "IV");
                    if (totalIva != null) {
                        objInvCabTransaccion.setCtrTotalIva(totalIva);
                    }
                    //Edita total descuento
                    Double totalDesc = invDetTransaccionFacade.sumaDetTotal(objInvCabTransaccion.getCtrId(), "DE");
                    if (totalDesc != null) {
                        objInvCabTransaccion.setCtrTotalDesc(totalDesc);
                    }
                    //Edita total general = (total - desc) + iva
                    if (objInvCabTransaccion.getCtrTotal() != null && objInvCabTransaccion.getCtrTotalDesc() != null && objInvCabTransaccion.getCtrTotalIva() != null) {
                        objInvCabTransaccion.setCtrTotaGeneral((objInvCabTransaccion.getCtrTotal() - objInvCabTransaccion.getCtrTotalDesc()) + objInvCabTransaccion.getCtrTotalIva());
                    }
                    invCabTransaccionFacade.edit(objInvCabTransaccion);

              }
              }
             
          }
        }

      
    }
    public void cargaCabeceraByParm(String tipoTrans, BigInteger tipoArt, String estado){
        objInvCabTransaccion = invCabTransaccionFacade.objetoByClaveTablaTipo(tipoTrans, tipoArt, noDoc, fecha);
    
    }
    
    public void cambiaTabIngresoFarmacia(final TabChangeEvent event) {
        final TabView tabView = (TabView) event.getComponent();
        final int inxA = tabView.getChildren().indexOf(event.getTab());

        if (inxA == 0) {
            //tab buscar transferencia
            formulario = "formIngresoBodega";

        }
        if (inxA == 1) {
            //crear ingreso por transferencia
            formulario = "formIngresoFarmacia";
            tipoTransaccion = "IT";

        }
        if (inxA == 2) {
            //crear ingreso por ref o donacion
   
            objInvCabTransaccion = new InvCabTransaccion();
            listInvDetTransaccion = null;

        }

    }
    
    public void cambiaTabDes(final TabChangeEvent event) {
        final TabView tabView = (TabView) event.getComponent();
        final int inxA = tabView.getChildren().indexOf(event.getTab());

        if (inxA == 0) {
            //tab despacho
            
        }
        if (inxA == 1) {
            //buscar liquidacion
            if(objRegPaciente != null && objRegPaciente.getPacId() !=null){
                cargaCabLiquidacion(objRegPaciente.getPacCedula(), "A");
            }
            
        }
       
    }
    
    
    public void buscarPaciente(String descripcion, String estado){
        List<RegPaciente> listPaciente = regPacienteFacade.objPacByParam(descripcion, estado);
    if(listPaciente != null){
    if(listPaciente.size()>0){
        if(listPaciente.size() == 1){
        //obtiene el objeto paciente
            objRegPaciente = regPacienteFacade.find(listPaciente.get(0).getPacId());
            if(objRegPaciente != null){
                //obtiene el servicio
                if(objInvCabTransaccion != null && objInvCabTransaccion.getCtrObservacion() != null){
                //obtiene servicio por descrpcion
                    if( objInvCabTransaccion.getCtrObservacion().equalsIgnoreCase("CONSULTA EXTERNA")){
                    //GRABAR CLAVE DE PACIENTE EN EGRESO
                        objInvCabTransaccion.setCtrClavetabla(objRegPaciente.getPacId());
                        objInvCabTransaccion.setCtrSiglastabla("PA");
                        
                    }
                    else{
                //buscar admision del paciente en servicios
                    objRegAdmision = regAdmisionFacade.objRegAdmision(objRegPaciente.getPacId(), "I");
                     if(objRegAdmision != null){
                    objInvCabTransaccion.setCtrClavetabla(objRegAdmision.getAdmId());
                        objInvCabTransaccion.setCtrSiglastabla("AD");
                    }   
                //grabar clave de admision en el egreso
                }
                    invCabTransaccionFacade.editarObjeto(objInvCabTransaccion, "CT");
                     invCabTransaccionFacade.editarObjeto(objInvCabTransaccion, "SI");
                }
            
            }
        }
        else{
          mostrarMensajeError("Inf.", "Existe mas de un registro del paciente.Verifique.");
        }
    
    }
    else{
        mostrarMensajeError("Paciente.null", "No existe registro del paciente para los datos ingresados.");
    }
    }
    }
    
    public void editarMotivo(InvDetTransaccion objeto ,String formUpdate){
    if(objCatGeneral.getGenId() != null){
        if(objeto != null){
            objeto.setDtrMotivDevolucion(objCatGeneral.getGenDescripcion());
        editarDetTrans(objeto, "MT", formUpdate);
                }
    }
    }
    
     public void btnNewRegTemp() {
        final RequestContext context = RequestContext.getCurrentInstance();
        RequestContext.getCurrentInstance().execute("PF('dlgNew').show()");
        context.update("tabPrincipal:formNew");

    }

    
    public void crearRegtem(){
        BigInteger max = BigInteger.ZERO;
        BigInteger min = BigInteger.ZERO;
        if(objRegCabtemp != null && objRegCabtemp.getTemTipo() != null)
        {
            if(objRegCabtemp.getTemTipo().equalsIgnoreCase("CF")){
            max = new BigInteger("8");
            min = new BigInteger("2");
            }
            if(objRegCabtemp.getTemTipo().equalsIgnoreCase("TE")){
            max = new BigInteger("30");}
            if(objRegCabtemp.getTemTipo().equalsIgnoreCase("RA")){
            max = new BigInteger("70");}
    
    
            objRegCabtemp = regCabtempFacade.generaRegTemp(objBodega.getBodId(), objRegCabtemp.getTemTipo(), objRegCabtemp.getTemNoter(), min, max, objRegCabtemp.getTemMes());
        if(objRegCabtemp != null){
    listRegDettemp = regDettempFacade.listRegDetByCab(objRegCabtemp.getTemId(), null);
    }
        }
    }
    
    public void cargaRegTemp(){
    
    objRegCabtemp = regCabtempFacade.objetoByBod(objRegCabtemp.getTemTipo(), objBodega.getBodId(), objRegCabtemp.getTemMes(), objRegCabtemp.getTemAnio(), objRegCabtemp.getTemNoter());
    if(objRegCabtemp != null){
    listRegDettemp = regDettempFacade.listRegDetByCab(objRegCabtemp.getTemId(), null);
    }
    }
    public void crearRegTempDet(){
    if(objRegCabtemp.getTemId() != null){
        
    regDettempFacade.crearDetByCabReg(objRegCabtemp.getTemId(), new Date());
    listRegDettemp = regDettempFacade.listRegDetByCab(objRegCabtemp.getTemId(), null);
    }
    } 
    public void editarRegTemp(RegDettemp obj, String valor){
    regDettempFacade.editDetTemp(obj, valor);
    listRegDettemp = regDettempFacade.listRegDetByCab(objRegCabtemp.getTemId(), null);
    
    }
    public void obtieneDet(BigInteger idDet){
    objRegDettemp = regDettempFacade.find(idDet);
    }
    public void eliminaRegDet(){
        try {
            regDettempFacade.remove(objRegDettemp);
            listRegDettemp = regDettempFacade.listRegDetByCab(objRegCabtemp.getTemId(), null);
    
        } catch (Exception e) {
            for (Throwable t = e.getCause(); t != null; t = t.getCause()) {
                if (t.getMessage().contains("java.sql.SQL")) {
                    mostrarMensajeError("Error.Existe dependecncias.", "No se pudo eliminar el registro." + t.getMessage());
                    break;
                }
            }
        }
    }
    //</editor-fold>
    //<editor-fold defaultstate="collapsed" desc="Getter and Setter">
    //<editor-fold defaultstate="collapsed" desc="GYS Consultas">
    public InvExistenciaBodega getObjInvExistenciaBodega() {
        if (objInvExistenciaBodega == null) {
            objInvExistenciaBodega = new InvExistenciaBodega();
        }
        return objInvExistenciaBodega;
    }

    public void setObjInvExistenciaBodega(InvExistenciaBodega objInvExistenciaBodega) {
        this.objInvExistenciaBodega = objInvExistenciaBodega;
    }

    public List<InvExistenciaBodega> getListInvExistenciaBodega() {
        return listInvExistenciaBodega;
    }

    public void setListInvExistenciaBodega(List<InvExistenciaBodega> listInvExistenciaBodega) {
        this.listInvExistenciaBodega = listInvExistenciaBodega;
    }

    public InvLoteArticulo getObjInvLoteArticulo() {
        if (objInvLoteArticulo == null) {
            objInvLoteArticulo = new InvLoteArticulo();
        }
        return objInvLoteArticulo;
    }

    public void setObjInvLoteArticulo(InvLoteArticulo objInvLoteArticulo) {
        this.objInvLoteArticulo = objInvLoteArticulo;
    }

    public List<InvLoteArticulo> getLisInvLoteArticulo() {
        return lisInvLoteArticulo;
    }

    public void setLisInvLoteArticulo(List<InvLoteArticulo> lisInvLoteArticulo) {
        this.lisInvLoteArticulo = lisInvLoteArticulo;
    }

    public String getTipoBsq() {
        return tipoBsq;
    }

    public void setTipoBsq(String tipoBsq) {
        this.tipoBsq = tipoBsq;
    }

    public CatBodega getObjCatBodega() {
        if (objCatBodega == null) {
            objCatBodega = new CatBodega();
        }
        return objCatBodega;
    }

    public void setObjCatBodega(CatBodega objCatBodega) {
        this.objCatBodega = objCatBodega;
    }

    public CatArticulo getObjCatArticulo() {
        if (objCatArticulo == null) {
            objCatArticulo = new CatArticulo();
        }
        return objCatArticulo;
    }

    public void setObjCatArticulo(CatArticulo objCatArticulo) {
        this.objCatArticulo = objCatArticulo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public BigInteger getTipoArticulo() {
        return tipoArticulo;
    }

    public void setTipoArticulo(BigInteger tipoArticulo) {
        this.tipoArticulo = tipoArticulo;
    }

    public int getInxTab() {
        return inxTab;
    }

    public void setInxTab(int inxTab) {
        this.inxTab = inxTab;
    }

    public List<InvDetTransaccion> getListInvDetTransaccion() {
        return listInvDetTransaccion;
    }

    public void setListInvDetTransaccion(List<InvDetTransaccion> listInvDetTransaccion) {
        this.listInvDetTransaccion = listInvDetTransaccion;
    }

    public InvDetTransaccion getObjInvDetTransaccion() {
        if (objInvDetTransaccion == null) {
            objInvDetTransaccion = new InvDetTransaccion();
        }
        return objInvDetTransaccion;
    }

    public void setObjInvDetTransaccion(InvDetTransaccion objInvDetTransaccion) {
        this.objInvDetTransaccion = objInvDetTransaccion;
    }

    public String getTipoFiltro() {
        return tipoFiltro;
    }

    public void setTipoFiltro(String tipoFiltro) {
        this.tipoFiltro = tipoFiltro;
    }

    public Date getFechaIni() {
        return fechaIni;
    }

    public void setFechaIni(Date fechaIni) {
        this.fechaIni = fechaIni;
    }

    public Date getFechaFin() {
        return fechaFin;
    }

    public void setFechaFin(Date fechaFin) {
        this.fechaFin = fechaFin;
    }

    public String getTipoMovimiento() {
        return tipoMovimiento;
    }

    public void setTipoMovimiento(String tipoMovimiento) {
        this.tipoMovimiento = tipoMovimiento;
    }

    //</editor-fold>
    //<editor-fold defaultstate="collapsed" desc="GYS Orden de Compra">
    public RegCabordencompra getObjRegCabordencompra() {
        if (objRegCabordencompra == null) {
            objRegCabordencompra = new RegCabordencompra();
        }
        return objRegCabordencompra;
    }

    public void setObjRegCabordencompra(RegCabordencompra objRegCabordencompra) {
        this.objRegCabordencompra = objRegCabordencompra;
    }

    public RegDetordencompra getObjRegDetordencompra() {
        if (objRegDetordencompra == null) {
            objRegDetordencompra = new RegDetordencompra();
        }
        return objRegDetordencompra;
    }

    public void setObjRegDetordencompra(RegDetordencompra objRegDetordencompra) {
        this.objRegDetordencompra = objRegDetordencompra;
    }

    public List<RegDetordencompra> getListRegDetordencompra() {
        return listRegDetordencompra;
    }

    public void setListRegDetordencompra(List<RegDetordencompra> listRegDetordencompra) {
        this.listRegDetordencompra = listRegDetordencompra;
    }

    public List<RegCabordencompra> getListRegCabordencompra() {
        return listRegCabordencompra;
    }

    public void setListRegCabordencompra(List<RegCabordencompra> listRegCabordencompra) {
        this.listRegCabordencompra = listRegCabordencompra;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    //</editor-fold>
    public InvCabTransaccion getObjInvCabTransaccion() {
        if (objInvCabTransaccion == null) {
            objInvCabTransaccion = new InvCabTransaccion();
        }
        return objInvCabTransaccion;
    }
    public void cargaCabLiquidacion(String descrip, String estado) {
        BigInteger clave = null;
        String siglas = null;
        if (servicioId != null) {
            objCatGeneral = catGeneralFacade.find(servicioId);
            if (objCatGeneral != null) {
                //buscar en el paciente
                List<RegPaciente> listPaciente = regPacienteFacade.objPacByParam(descrip, estado);
                if (listPaciente != null) {
                    if (listPaciente.size() > 0) {
                        if (listPaciente.size() == 1) {
                            //obtiene el objeto paciente
                            objRegPaciente = regPacienteFacade.find(listPaciente.get(0).getPacId());
                            if (objRegPaciente != null) {
                                clave = objRegPaciente.getPacId();
                                siglas = "PA";

                            }
                        }
                        if (objCatGeneral.getGenDescripcion().equalsIgnoreCase("CONSULTA EXTERNA")) {
                            //buscar admision del paciente en servicios
                            objRegAdmision = regAdmisionFacade.objRegAdmision(objRegPaciente.getPacId(), "I");
                            if (objRegAdmision != null) {
                                clave = objRegAdmision.getAdmId();
                                siglas = "AD";
                            }

                        }
                    }

                }
            }
            objRegCabliquidacion = regCabliquidacionFacade.objRegCabliquidacion(clave, "P", siglas);
            if (objRegCabliquidacion != null) {
                listRegDetliquidacion = regDetliquidacionFacade.listRegDetliquidacionByParam(objRegCabliquidacion.getCliId(), null, "P");
            }
        }
    }

    public void setObjInvCabTransaccion(InvCabTransaccion objInvCabTransaccion) {
        this.objInvCabTransaccion = objInvCabTransaccion;
    }

    public CatBodega getObjBodega() {
        if(objBodega == null){
        objBodega = new CatBodega();
        }
   
        return objBodega;
    }

    public void setObjBodega(CatBodega objBodega) {
        this.objBodega = objBodega;
    }

    public CatEmpresa getObjEmpresa() {
        if (objEmpresa == null) {
            objEmpresa = new CatEmpresa();
           
        }
        return objEmpresa;
    }

    public void setObjEmpresa(CatEmpresa objEmpresa) {
        this.objEmpresa = objEmpresa;
    }

    public BigInteger getSeccionId() {
        return seccionId;
    }

    public void setSeccionId(BigInteger seccionId) {
        this.seccionId = seccionId;
    }

    public BigInteger getPerchaId() {
        return perchaId;
    }

    public void setPerchaId(BigInteger perchaId) {
        this.perchaId = perchaId;
    }

    public BigInteger getColumnaId() {
        return columnaId;
    }

    public void setColumnaId(BigInteger columnaId) {
        this.columnaId = columnaId;
    }

    public BigInteger getNivelId() {
        return nivelId;
    }

    public void setNivelId(BigInteger nivelId) {
        this.nivelId = nivelId;
    }

    public BigInteger getProveedorId() {
        return proveedorId;
    }

    public void setProveedorId(BigInteger proveedorId) {
        this.proveedorId = proveedorId;
    }

    public BigInteger getNumeroOrdenCompra() {
        return numeroOrdenCompra;
    }

    public void setNumeroOrdenCompra(BigInteger numeroOrdenCompra) {
        this.numeroOrdenCompra = numeroOrdenCompra;
    }

    public String getFormulario() {
        return formulario;
    }

    public void setFormulario(String formulario) {
        this.formulario = formulario;
    }

    public List<InvCabTransaccion> getListInvCabTransaccion() {
        return listInvCabTransaccion;
    }

    public void setListInvCabTransaccion(List<InvCabTransaccion> listInvCabTransaccion) {
        this.listInvCabTransaccion = listInvCabTransaccion;
    }

    public Boolean getIva() {
        return iva;
    }

    public void setIva(Boolean iva) {
        this.iva = iva;

    }

    public List<InvCabsolicitudbod> getListCabSolBodega() {
        return listCabSolBodega;
    }

    public void setListCabSolBodega(List<InvCabsolicitudbod> listCabSolBodega) {
        this.listCabSolBodega = listCabSolBodega;
    }

    public List<InvDetsolicitudbod> getListDetSolBodega() {
        return listDetSolBodega;
    }

    public void setListDetSolBodega(List<InvDetsolicitudbod> listDetSolBodega) {
        this.listDetSolBodega = listDetSolBodega;
    }

    public InvCabsolicitudbod getObjCabSolBodega() {
        return objCabSolBodega;
    }

    public void setObjCabSolBodega(InvCabsolicitudbod objCabSolBodega) {
        this.objCabSolBodega = objCabSolBodega;
    }

    public InvDetsolicitudbod getObjDetSolBodega() {
        return objDetSolBodega;
    }

    public void setObjDetSolBodega(InvDetsolicitudbod objDetSolBodega) {
        this.objDetSolBodega = objDetSolBodega;
    }

    public BigInteger getAreaId() {
        return areaId;
    }

    public void setAreaId(BigInteger areaId) {
        this.areaId = areaId;
    }

    public String getFormEgreso() {
        return formEgreso;
    }

    public void setFormEgreso(String formEgreso) {
        this.formEgreso = formEgreso;
    }

    public BigInteger getCantLote() {
        return cantLote;
    }

    public void setCantLote(BigInteger cantLote) {
        this.cantLote = cantLote;
    }

    public String getTipoTransaccion() {
        return tipoTransaccion;
    }

    public void setTipoTransaccion(String tipoTransaccion) {
        this.tipoTransaccion = tipoTransaccion;
    }

    public BigInteger getIdTransaccion() {
        return idTransaccion;
    }

    public void setIdTransaccion(BigInteger idTransaccion) {
        this.idTransaccion = idTransaccion;
    }

    public String getFormAjuste() {
        return formAjuste;
    }

    public void setFormAjuste(String formAjuste) {
        this.formAjuste = formAjuste;
    }

    public CatGeneral getObjCatGeneral() {
        if (objCatGeneral == null) {
            objCatGeneral = new CatGeneral();
        }
        return objCatGeneral;
    }

    public void setObjCatGeneral(CatGeneral objCatGeneral) {
        this.objCatGeneral = objCatGeneral;
    }

    public InvCabtomafisica getObjInvCabtomafisica() {
        if (objInvCabtomafisica == null) {
            objInvCabtomafisica = new InvCabtomafisica();
        }
        return objInvCabtomafisica;
    }

    public void setObjInvCabtomafisica(InvCabtomafisica objInvCabtomafisica) {
        this.objInvCabtomafisica = objInvCabtomafisica;
    }

    public List<InvDettomafisica> getListInvDettomafisica() {
        return listInvDettomafisica;
    }

    public void setListInvDettomafisica(List<InvDettomafisica> listInvDettomafisica) {
        this.listInvDettomafisica = listInvDettomafisica;
    }

    public InvDettomafisica getObjInvDettomafisica() {
        if (objInvDettomafisica == null) {
            objInvDettomafisica = new InvDettomafisica();
        }
        return objInvDettomafisica;
    }

    public void setObjInvDettomafisica(InvDettomafisica objInvDettomafisica) {
        this.objInvDettomafisica = objInvDettomafisica;
    }

    public Boolean getEtiquetas() {
        return etiquetas;
    }

    public void setEtiquetas(Boolean etiquetas) {
        this.etiquetas = etiquetas;
    }

    public String getMensajeInv() {
        return mensajeInv;
    }

    public void setMensajeInv(String mensajeInv) {
        this.mensajeInv = mensajeInv;
    }

    public InvCabTransaccion getObjAjusSob() {
        if(objAjusSob == null){
        objAjusSob = new InvCabTransaccion();
        }
        return objAjusSob;
    }

    public void setObjAjusSob(InvCabTransaccion objAjusSob) {
        this.objAjusSob = objAjusSob;
    }

    public InvCabTransaccion getObjAjusFalt() {
        if(objAjusFalt == null){
        objAjusFalt = new InvCabTransaccion();
        }
        return objAjusFalt;
    }

    public void setObjAjusFalt(InvCabTransaccion objAjusFalt) {
        this.objAjusFalt = objAjusFalt;
    }

   

    public String getURL() {
        return URL;
    }

    public void setURL(String URL) {
        this.URL = URL;
    }

    public List<CatGeneral> getListSubMenu() {
        if(listSubMenu == null){
        listSubMenu = catGeneralFacade.listAllCatGeneralByParam("MENU", BigInteger.ONE, null, "A", null);
        }
        return listSubMenu;
    }

    public void setListSubMenu(List<CatGeneral> listSubMenu) {
        this.listSubMenu = listSubMenu;
    }

    public List<CatGeneral> getListMenuItem(BigInteger idPapa) {
        listMenuItem = catGeneralFacade.listAllCatGeneralByParam(null, new BigInteger("2"), idPapa, "A", null);
        return listMenuItem;
    }

    public void setListMenuItem(List<CatGeneral> listMenuItem) {
        this.listMenuItem = listMenuItem;
    }

    public String getIdBodega() {
        return idBodega;
    }

    public void setIdBodega(String idBodega) {
        System.out.println("id de bodega en set es "+idBodega);
        this.idBodega = idBodega;
    }

    public String getIdMenu() {
        return idMenu;
    }

    public void setIdMenu(String idMenu) {
        this.idMenu = idMenu;
    }

    private HttpServletRequest getHttpServletRequest() {
        return ((HttpServletRequest) getExternalContext().getRequest());
    }
    protected ExternalContext getExternalContext() {
        return getFacesContext().getExternalContext();
    }
    protected FacesContext getFacesContext() {
        return (FacesContext.getCurrentInstance());
    }

    

    public List<InvDettomafisica> getListAjusFaltante() {
        return listAjusFaltante;
    }

    public void setListAjusFaltante(List<InvDettomafisica> listAjusFaltante) {
        this.listAjusFaltante = listAjusFaltante;
    }

    public List<InvDettomafisica> getListAjusSobrante() {
        return listAjusSobrante;
    }

    public void setListAjusSobrante(List<InvDettomafisica> listAjusSobrante) {
        this.listAjusSobrante = listAjusSobrante;
    }

    public boolean isCtrIng() {
        return ctrIng;
    }

    public void setCtrIng(boolean ctrIng) {
        this.ctrIng = ctrIng;
    }

    public boolean isCtrEgreso() {
        return ctrEgreso;
    }

    public void setCtrEgreso(boolean ctrEgreso) {
        this.ctrEgreso = ctrEgreso;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public InvCabTransaccion getObjTransferencia() {
        if(objTransferencia == null){
        objTransferencia = new InvCabTransaccion();
        }
        return objTransferencia;
    }

    public void setObjTransferencia(InvCabTransaccion objTransferencia) {
        this.objTransferencia = objTransferencia;
    }


    public RegPaciente getObjRegPaciente() {
        if(objRegPaciente == null){
        objRegPaciente = new RegPaciente();
        }
        return objRegPaciente;
    }

    public void setObjRegPaciente(RegPaciente objRegPaciente) {
        this.objRegPaciente = objRegPaciente;
    }

    public BigInteger getNoDoc() {
        return noDoc;
    }

    public void setNoDoc(BigInteger noDoc) {
        this.noDoc = noDoc;
    }

    public List<InvCabTransaccion> getListCabTransferencia() {
        return listCabTransferencia;
    }

    public void setListCabTransferencia(List<InvCabTransaccion> listCabTransferencia) {
        this.listCabTransferencia = listCabTransferencia;
    }

    public List<InvDetTransaccion> getListDetTransferencia() {
        return listDetTransferencia;
    }

    public void setListDetTransferencia(List<InvDetTransaccion> listDetTransferencia) {
        this.listDetTransferencia = listDetTransferencia;
    }

    public RegAdmision getObjRegAdmision() {
if(objRegAdmision != null){
objRegAdmision = new RegAdmision();
}
        return objRegAdmision;
    }

    public int getPeriodo() {
        return periodo;
    }

    public void setPeriodo(int periodo) {
        this.periodo = periodo;
    }

    public void setObjRegAdmision(RegAdmision objRegAdmision) {
        this.objRegAdmision = objRegAdmision;
    }

    public int getMeses() {
        return meses;
    }

    public void setMeses(int meses) {
        this.meses = meses;
    }

    public Double getPorciento() {
        return porciento;
    }

    public void setPorciento(Double porciento) {
        this.porciento = porciento;
    }

    public BigInteger getArtId() {
        return artId;
    }

    public void setArtId(BigInteger artId) {
        this.artId = artId;
    }

    public RegCabtemp getObjRegCabtemp() {
        if(objRegCabtemp == null){
        objRegCabtemp = new RegCabtemp();
        boolean isAnio = objRegCabtemp.getTemAnio()  != null && objRegCabtemp.getTemAnio().trim().length() > 0 && !(objRegCabtemp.getTemAnio().trim().toLowerCase().compareTo("null") == 0);
       boolean isMes = objRegCabtemp.getTemMes() != null && objRegCabtemp.getTemMes().trim().length() > 0 && !(objRegCabtemp.getTemMes().trim().toLowerCase().compareTo("null") == 0);
  
        if(!isAnio){
          obtieneMesA();
          objRegCabtemp.setTemAnio(anio);
          if(!isMes){
          objRegCabtemp.setTemMes(mes);
          }
        }
        }
        return objRegCabtemp;
    }

    public void setObjRegCabtemp(RegCabtemp objRegCabtemp) {
        this.objRegCabtemp = objRegCabtemp;
    }

    public RegDettemp getObjRegDettemp() {
        if(objRegDettemp == null){
        objRegDettemp = new RegDettemp();
        }
        return objRegDettemp;
    }

    public void setObjRegDettemp(RegDettemp objRegDettemp) {
        this.objRegDettemp = objRegDettemp;
    }

    public List<RegDettemp> getListRegDettemp() {
        return listRegDettemp;
    }

    public void setListRegDettemp(List<RegDettemp> listRegDettemp) {
        this.listRegDettemp = listRegDettemp;
    }

    public String getPathImpresion() {
        return pathImpresion;
    }

    public void setPathImpresion(String pathImpresion) {
        this.pathImpresion = pathImpresion;
    }

    public String getNameReport() {
        return nameReport;
    }

    public void setNameReport(String nameReport) {
        this.nameReport = nameReport;
    }

    public String getNameServidor() {
        return nameServidor;
    }

    public void setNameServidor(String nameServidor) {
        this.nameServidor = nameServidor;
    }

    public String getPathReporteFinal() {
        return pathReporteFinal;
    }

    public void setPathReporteFinal(String pathReporteFinal) {
        this.pathReporteFinal = pathReporteFinal;
    }

    public BigInteger getCodigo() {
        return codigo;
    }

    public void setCodigo(BigInteger codigo) {
        this.codigo = codigo;
    }

    public BigInteger getCodigoBod() {
        return codigoBod;
    }

    public void setCodigoBod(BigInteger codigoBod) {
        this.codigoBod = codigoBod;
    }

    public Boolean getCtrFinaliza() {
        return ctrFinaliza;
    }

    public void setCtrFinaliza(Boolean ctrFinaliza) {
        this.ctrFinaliza = ctrFinaliza;
    }

    public String getfInicio() {
        return fInicio;
    }

    public void setfInicio(String fInicio) {
        this.fInicio = fInicio;
    }

    public String getfFin() {
        return fFin;
    }

    public void setfFin(String fFin) {
        this.fFin = fFin;
    }

    public Boolean getValidada() {
        return validada;
    }

    public void setValidada(Boolean validada) {
        this.validada = validada;
    }

    public RegCabliquidacion getObjRegCabliquidacion() {
        if(objRegCabliquidacion == null){
        objRegCabliquidacion = new RegCabliquidacion();
        }
        return objRegCabliquidacion;
    }

    public void setObjRegCabliquidacion(RegCabliquidacion objRegCabliquidacion) {
        this.objRegCabliquidacion = objRegCabliquidacion;
    }

    public List<RegDetliquidacion> getListRegDetliquidacion() {
        return listRegDetliquidacion;
    }

    public void setListRegDetliquidacion(List<RegDetliquidacion> listRegDetliquidacion) {
        this.listRegDetliquidacion = listRegDetliquidacion;
    }

    public BigInteger getServicioId() {
        return servicioId;
    }

    public void setServicioId(BigInteger servicioId) {
        this.servicioId = servicioId;
    }

    public int getCaducados() {
        return caducados;
    }

    public void setCaducados(int caducados) {
        this.caducados = caducados;
    }

    public String getFormInv() {
        return formInv;
    }

    public void setFormInv(String formInv) {
        this.formInv = formInv;
    }

   
}
