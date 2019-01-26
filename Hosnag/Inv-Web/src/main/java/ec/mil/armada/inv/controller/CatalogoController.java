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
import ec.mil.armada.inv.modelo.inventario.InvExistenciaBodega;
import ec.mil.armada.inv.modelo.registro.RegEmpleado;
import ec.mil.armada.inv.remotos.catalogo.CatArticuloFacadeRemote;
import ec.mil.armada.inv.remotos.catalogo.CatBodegaFacadeRemote;
import ec.mil.armada.inv.remotos.catalogo.CatEmpresaFacadeRemote;
import ec.mil.armada.inv.remotos.catalogo.CatGeneralFacadeRemote;
import ec.mil.armada.inv.remotos.registro.RegEmpleadoFacadeRemote;
import ec.mil.armada.inv.remotos.inventario.InvExistenciaBodegaFacadeRemote;
import ec.mil.armada.inv.treeTable.Estructura_bodega;
import java.io.Serializable;
import java.math.BigInteger;
import java.util.Date;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.annotation.security.PermitAll;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import javax.sound.midi.Soundbank;
import org.primefaces.component.tabview.TabView;
import org.primefaces.context.RequestContext;
import org.primefaces.event.CellEditEvent;
import org.primefaces.event.TabChangeEvent;
import org.primefaces.event.ToggleEvent;
import org.primefaces.model.DefaultTreeNode;
import org.primefaces.model.TreeNode;

/**
 *
 * @author maria_rodriguez
 */
@Named(value = "catalogoController")
@ViewScoped
@PermitAll
public class CatalogoController implements Serializable {
    @EJB
    private InvExistenciaBodegaFacadeRemote invExistenciaBodegaFacade;
    
    private static final long serialVersionUID = -1175196278877002483L;

    /**
     * Creates a new instance of CatalogoController
     */
    public CatalogoController() {
    }
    

    //<editor-fold defaultstate="collapsed" desc="Declaracion de Variables">
    private CatArticulo objArticulo;
    private CatArticulo objTipo;
    private List<CatArticulo> listCatArticulo;
    private List<CatArticulo> listCatTipo;
    private List<CatArticulo> listCatSubTipo;
    private CatEmpresa objEmpresa;
    private List<CatEmpresa> listProveedor;
    private CatBodega objBodega;
    private CatBodega objBodegaNivel;
    private List<CatBodega> listBodega;
    private List<CatBodega> listBodNivel1;
    private List<CatBodega> listBodUbi;
    private List<CatBodega> listBodNivel2;
    private CatGeneral objCatGeneral;
    private List<CatGeneral> listCatGeneral;
    private String descripcion;
    private int inxTab;
    private String tipo;
    private int nivel;
    private CatBodega bodegaPapa;
    private TreeNode root;
    private TreeNode selectNode;
    private Estructura_bodega seleccionarFila;
    private String nombreForm;
    private boolean ctrEditar;
    private BigInteger clave;
    private String formulario;
    private RegEmpleado objEmpleado;
    private List<RegEmpleado> listaRegEmpleado;
    private int noInicial = 1;
    private int noFinal = 1 ;
    private String message = "";
    private String accion = "";
    private BigInteger idTipo;
    private BigInteger idSubtipo;
    
    
    

    @EJB
    transient private CatGeneralFacadeRemote catGeneralFacade;
    @EJB
    transient private CatEmpresaFacadeRemote catEmpresaFacade;
    @EJB
    transient private CatBodegaFacadeRemote catBodegaFacade;
    @EJB
    transient private CatArticuloFacadeRemote catArticuloFacade;
    @EJB
    private RegEmpleadoFacadeRemote regEmpleadoFacade;


    //</editor-fold>
    //<editor-fold defaultstate="collapsed" desc="Funciones">
    @PostConstruct
    public void init() {
        root = new DefaultTreeNode();
        //TODO BUSCAR EMPRESA POR EL USER QUE SE LOGUEA
        objEmpresa = catEmpresaFacade.find(new BigInteger("1"));
        tipo = "A";
        formulario="formArticulos";
        nivel=2;
        if(objEmpresa == null)
        {
            mostrarMensajeError("Alerta", "No existe parametrización de la Empresa.Por favor paremetrice.Gracias");
        }
        
    }

    public void mostrarMensajeError(final String titulo, final String mensaje) {
        final FacesMessage facesMsg = new FacesMessage(FacesMessage.SEVERITY_ERROR, titulo, mensaje);
        RequestContext.getCurrentInstance().showMessageInDialog(facesMsg);
    }

    public CatGeneral devuelveCatPapa(String tiposigla) {
        CatGeneral objPapa = new CatGeneral();
        objPapa = catGeneralFacade.objCatGeneralPapaActivo(tiposigla);
        return objPapa;
    }

    //<editor-fold defaultstate="collapsed" desc="Busquedas">
    public void buscarByDescripcion(String accion,String tipoObj) {
        int size = 0;
        boolean isAccion = accion != null && accion.trim().length() > 0 && !(accion.trim().toLowerCase().compareTo("null") == 0);

        if (tipoObj.equalsIgnoreCase("A")) {
            //es articulo
            listCatArticulo = catArticuloFacade.listArticuloByParam(new BigInteger("2"), idSubtipo, null, descripcion);
            size = listCatArticulo.size();
        }
        if (tipoObj.equalsIgnoreCase("B")) {
            //es bodega
           listBodega = catBodegaFacade.listAllBodegaByParam("B", BigInteger.ZERO, null, null, descripcion, objEmpresa.getEmpId());
           size = listBodega.size();
        }
        if (tipoObj.equalsIgnoreCase("T")) {
            //es tipo nivel 0
            listCatTipo = catArticuloFacade.listArticuloByParam(BigInteger.ZERO, null, null, descripcion);
            size = listCatTipo.size();
        }
        if (tipoObj.equalsIgnoreCase("S")) {
            //es seccion nivel 1
            listBodNivel1 = catBodegaFacade.listAllBodegaByParam(null, BigInteger.ONE, objBodega.getBodId() , null, descripcion, objEmpresa.getEmpId());
            size = listBodNivel1.size();
        }
        if (tipoObj.equalsIgnoreCase("MARC")||
                tipoObj.equalsIgnoreCase("MODE")||
                tipoObj.equalsIgnoreCase("PRES")||
                tipoObj.equalsIgnoreCase("MEDI")){
            //es CATALOGO nivel 1
           listCatGeneral = catGeneralFacade.listAllCatGeneralByParam(tipo, BigInteger.ONE, null, null, descripcion);
           size = listCatGeneral.size();
             
        }
        if (isAccion) {
            if (accion.equalsIgnoreCase("N")) {
                if (size == 0) {
                    ctrEditar = true;
                    //validar tipo y subtipo
                    if(tipoObj.equalsIgnoreCase("A") && idTipo == null && idSubtipo == null ){
                        mostrarMensajeError("Alerta.Tipo y Subtipo es Requerido.", "No existe registro.Para crear nuevo registro seleccione tipo y subtipo.");
                    }
                    else{
                    final RequestContext context = RequestContext.getCurrentInstance();
                    RequestContext.getCurrentInstance().execute("PF('dlgNew').show()");
                    context.update("formNew");

                    }
                    
                }
            }
            if (accion.equalsIgnoreCase("E")) {
                if (size > 0) {
                    mostrarMensajeError("Error", "Existe registro de articulo con: " + descripcion);
                }
            }
        }

    }
   
    public void validaEdit()
            {
                ctrEditar = false;
                }
    public void buscarByDescrp(String accion) {
        //accion N (Nuevo) E(Editar)
        boolean isAccion = accion != null && accion.trim().length() > 0 && !(accion.trim().toLowerCase().compareTo("null") == 0);
             
        listCatArticulo = catArticuloFacade.listArticuloByParam(new BigInteger("2"), null, null, descripcion);
        if (isAccion) {
            if (accion.equalsIgnoreCase("N")) {
                if (listCatArticulo.isEmpty()) {
                    final RequestContext context = RequestContext.getCurrentInstance();
                RequestContext.getCurrentInstance().execute("PF('dlgNewArt').show()");
                context.update("tabPrincipal:formNewArt");
                
                }
            }
            if (accion.equalsIgnoreCase("E")) {
                if (listCatArticulo.size() >0) {
                    mostrarMensajeError("Error", "Existe registro de articulo con: " + descripcion);
                }
            }
        }

    }
    public void buscarByDescrpTipo(String accion) {
        //accion N (Nuevo) E(Editar)
        boolean isAccion = accion != null && accion.trim().length() > 0 && !(accion.trim().toLowerCase().compareTo("null") == 0);
     
             listCatTipo = catArticuloFacade.listArticuloByParam(BigInteger.ZERO, null, null, descripcion);
      
        if (isAccion) {
            if (accion.equalsIgnoreCase("N")) {
                if (listCatTipo.isEmpty()) {
                    mostrarMensajeError("Info.Ingrese nuevo tipo", "No existe registro de tipo con la descripcion " + descripcion);
                }
            }
            if (accion.equalsIgnoreCase("E")) {
                if (listCatTipo.size() >0) {
                    mostrarMensajeError("Error", "Existe registro de tipo con: " + descripcion);
                }
            }
        }

    }


    public void buscarArticuloByPara(String tipo, BigInteger nivel, BigInteger codigoPapa, String estado, String descrip) {
        listCatArticulo = catArticuloFacade.listArticuloByParam(nivel, codigoPapa, estado, descrip);

    }


    public void buscarCatalogoGeneral(String tipo, BigInteger nivel, BigInteger codigoPapa, String estado, String descripcion) {
        if (nivel.intValue() == 0) {
            //Carga papa nivel 0
            listCatGeneral = catGeneralFacade.listAllCatGeneralByParam(tipo, nivel, codigoPapa, estado, descripcion);
        }
        if (nivel.intValue() == 1) {
            //Carga hijos del codigo papa
        }
        if (nivel.intValue() == 2) {
            //Carga hijos del codigo papa
        }

    }

    public void buscarBodega(String tipo, BigInteger nivel, BigInteger codigoBodega, String estado, String descripcion, BigInteger codigoEmp) {
        listBodega = catBodegaFacade.listAllBodegaByParam(tipo, nivel, codigoBodega, estado, descripcion, codigoEmp);
    }

    public void buscarCatGeneralByDescrp() {
        listCatGeneral = catGeneralFacade.listAllCatGeneralByParam(tipo, BigInteger.ONE, null, null, descripcion);
    }

    public void consEstructBodega() {
        root = new DefaultTreeNode();
        objBodegaNivel = new CatBodega();
        descripcion = "SECCIONES";
        objBodega = catBodegaFacade.find(objBodega.getBodId());
        listBodUbi = catBodegaFacade.listAllBodegaByParam("S", BigInteger.ONE, objBodega.getBodId(), null, null, objEmpresa.getEmpId());
        if (listBodUbi.size() > 0) {
            objBodegaNivel = listBodUbi.get(0);
            root = new DefaultTreeNode(new Estructura_bodega(objBodegaNivel.getBodId(), objBodegaNivel.getBodDescripcion(), objBodegaNivel.getBodTipo(), objBodegaNivel.getBodNivel(), objBodegaNivel.getBodEstado(), null), null);

            for (final CatBodega lista0 : listBodUbi) {
                final TreeNode childNode = new DefaultTreeNode(new Estructura_bodega(lista0.getBodId(),
                        lista0.getBodDescripcion(), lista0.getBodTipo(), lista0.getBodNivel(), lista0.getBodEstado(), null),
                        root);
                List<CatBodega> listNivel1 = catBodegaFacade.listAllBodegaByParam(null, null, lista0.getBodId(), null, null, null);
                if (listNivel1.size() > 0) {
                    for (final CatBodega lista1 : listNivel1) {
                        final TreeNode childNode1 = new DefaultTreeNode(new Estructura_bodega(lista1.getBodId(),
                                lista1.getBodDescripcion(), lista1.getBodTipo(), lista1.getBodNivel(), lista1.getBodEstado(), lista1.getNombreEmpleado()),
                                childNode);
                        List<CatBodega> listNivel2 = catBodegaFacade.listAllBodegaByParam(null, null, lista1.getBodId(), null, null, null);
                        if (listNivel2.size() > 0) {
                            for (CatBodega lista2 : listNivel2) {
                                final TreeNode childNode2 = new DefaultTreeNode(new Estructura_bodega(lista2.getBodId(),
                                         lista2.getBodDescripcion(), lista2.getBodTipo(), lista2.getBodNivel(), lista2.getBodEstado(), lista2.getNombreEmpleado()),
                                        childNode1);
                                List<CatBodega> listNivel3 = catBodegaFacade.listAllBodegaByParam(null, null, lista2.getBodId(), null, null, null);
                        if (listNivel2.size() > 0) {
                            for (CatBodega lista3 : listNivel3) {
                                final TreeNode childNode3 = new DefaultTreeNode(new Estructura_bodega(lista3.getBodId(),
                                       lista3.getBodDescripcion(), lista3.getBodTipo(), lista3.getBodNivel(), lista3.getBodEstado(), lista3.getNombreEmpleado()),
                                        childNode2);

                            }
                        }

                            }

                        }

                    }

                }

            }
        } else {
            //crear secciones
            nuevaUbi(objBodega.getBodId());
            final RequestContext context = RequestContext.getCurrentInstance();
            RequestContext.getCurrentInstance().execute("PF('dlgCrear').show()");
            context.update("tabPrincipal:formCrear");

//             objBodegaNivel.setBodDescripcion(descripcion);
//             objBodegaNivel.setBodPapa(objBodega);
//             objBodegaNivel.setBodEstado("A");
//             objBodegaNivel.setBodTipo("S");
//             objBodegaNivel.setBodNivel(BigInteger.ONE);
//             objBodegaNivel.setCatEmp(objEmpresa);
//             catBodegaFacade.create(objBodegaNivel);
//             listBodUbi = catBodegaFacade.listAllBodegaByParam("S", BigInteger.ONE, objBodega.getBodId(), null, null, objEmpresa.getEmpId());
//             
        }

    }
    public void buscarEmpleado() {
        listaRegEmpleado = regEmpleadoFacade.listEmpleadoByParam(descripcion, objEmpresa.getEmpId(), null, "A");
    }
            
    public void enceraListas(String tipoList) {
        if (tipoList.equalsIgnoreCase("S")) {
            listBodNivel1 = null;
        }
         if (tipoList.equalsIgnoreCase("T")) {
            objArticulo = new CatArticulo();
        }
         if(tipoList.equalsIgnoreCase("C")){
         objBodegaNivel = new CatBodega();
         bodegaPapa = new CatBodega();
         noInicial = 1;
         noFinal = 1;
         ctrEditar = false;
         message = "";
         accion = "";
         }


    }


    public String obtienePadre(BigInteger id,String tipo) {
        CatArticulo objeto = new CatArticulo();
        String nombre = "";
        if (id != null) {
            objeto = catArticuloFacade.find(id);
            if(objeto != null&& tipo.equalsIgnoreCase("P") ){
             nombre = objeto.getArtNombgenerico();
            }
             if(objeto != null&& tipo.equalsIgnoreCase("A") ){
             nombre = objeto.getCatArtPapa().getArtNombgenerico();
            }
           
        }
        return nombre;
    }

    public String obtieneObjGeneral(BigInteger id) {
        objCatGeneral = new CatGeneral();
        String nombre = "";
        if (id != null) {
            objCatGeneral = catGeneralFacade.find(id);
            if(objCatGeneral != null)
            {
             nombre = objCatGeneral.getGenDescripcion();
            }
           
        }

        return nombre;
    }
    public String obtieneArticulo(BigInteger id) {
        objArticulo = new CatArticulo();
        String nombre = "";
        if (id != null) {
            objArticulo = catArticuloFacade.find(id);
            if(objArticulo != null)
            {
             nombre = objArticulo.getArtNombgenerico();
            }
           
        }
       

        return nombre;
    }
    public String obtieneBodega(BigInteger id) {
       
        objBodegaNivel = new CatBodega();
        String nombre = "";
        if (id != null) {
            objBodegaNivel = catBodegaFacade.find(id);
            if(objBodegaNivel != null)
            {
             nombre = objBodegaNivel.getBodDescripcion();
            }
           
        }
       

        return nombre;
    }


    //</editor-fold>
    //<editor-fold defaultstate="collapsed" desc="Crear">
    public void crearGeneral(String tipoCrear){
        if(tipoCrear.equalsIgnoreCase("A")){
            
            crearArticulo(nivel, null);
            
        
        }
        if(tipoCrear.equalsIgnoreCase("B")){
        //crear bodega
            crearBodega(tipoCrear, null, nivel);
            
        }
        if(tipoCrear.equalsIgnoreCase("T")){
        //crear bodega
            crearArticulo(nivel, null);
        }
         if(tipoCrear.equalsIgnoreCase("S")){
        //crear nivel 1 de bodega
             if(objBodega.getBodId() !=null )
                 {
            crearBodega(tipoCrear, objBodega, nivel);
            }
        }
         if (tipoCrear.equalsIgnoreCase("MARC")||
                tipoCrear.equalsIgnoreCase("MODE")||
                tipoCrear.equalsIgnoreCase("PRES")||
                tipoCrear.equalsIgnoreCase("MEDI")){
            //es CATALOGO nivel 1
             crearCatGeneral(nivel);
        }
    
    }
   
    
  
    public void crearArticulo(int nivel, CatArticulo objPapa) {
        
         String codigo = "01";

        try {
            BigInteger nivelaux = new BigInteger(String.valueOf(nivel));
            objArticulo = new CatArticulo();

            if (nivel == 0) {
                //Crear tipo
                objArticulo.setArtMsp("N");
                objArticulo.setArtFacturable("N");
                objArticulo.setArtNombcomercial(descripcion.toUpperCase());
                objArticulo.setArtNombgenerico(descripcion.toUpperCase());
                idTipo = null;
                idSubtipo = null;
            }
            if (nivel == 1) {
                //Crear subtipo
                objArticulo.setArtMsp("N");
                objArticulo.setArtFacturable("N");
                if(objPapa != null){
                     idTipo = objPapa.getArtId();
                    objPapa = catArticuloFacade.find(idTipo);
                    if(objPapa != null){
                objArticulo.setCatArtPapa(objPapa);
                    }
                
                }

            }
            if (nivel == 2) {
                idTipo = null;
                //Crear articulo
                objArticulo.setArtMsp("S");
                objArticulo.setArtFacturable("S");
                objArticulo.setArtNombcomercial(descripcion.toUpperCase());
                objArticulo.setArtNombgenerico(descripcion.toUpperCase());
                
                if (idSubtipo != null) {
                    objPapa = catArticuloFacade.find(idSubtipo);
                    if (objPapa != null) {
                        objArticulo.setCatArtPapa(objPapa);
                        //obtiene codigo del padre + 000
                        codigo = objPapa.getArtCodigo() + "0000";

                    }

                }
            }

            
            objArticulo.setArtEstado("A");
            objArticulo.setArtNivel(nivelaux);
            //otener el codigo del artico
            int codi = catArticuloFacade.numeroArticulo(idTipo, idSubtipo);
            if(codi != 0){
            codigo = String.valueOf(codi);
            if(codi < 10){
                codigo = "0" + codigo;
            }
            }
                
            objArticulo.setArtCodigo(codigo);
            objArticulo.setArtCodbarra(codigo);
            catArticuloFacade.create(objArticulo);
            if (nivel == 2) {
                listCatArticulo = catArticuloFacade.listArticuloByParam(nivelaux, null, null, descripcion);

            }
            if (nivel == 0) {
                listCatTipo = catArticuloFacade.listArticuloByParam(nivelaux, null, null, descripcion);
            }
            if (nivel == 1) {
                listCatSubTipo = catArticuloFacade.listArticuloByParam(nivelaux, objPapa.getArtId(), null, null);

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

    public void crearCatGeneral(int nivelaux) {
        BigInteger nivel = new BigInteger(String.valueOf(nivelaux));
        objCatGeneral = new CatGeneral();
        try {

            if (nivelaux == 0) {
                //Crear nivel 0
                objCatGeneral.setGenDescripcion(descripcion);
                objCatGeneral.setGenNivel(nivel);
                catGeneralFacade.create(objCatGeneral);
            } else if (nivelaux == 1) {
                if (!tipo.isEmpty()) {
                    CatGeneral objPapa = devuelveCatPapa(tipo);
                    if (objPapa != null) {
                        objCatGeneral.setCatGenPapa(objPapa);
                        objCatGeneral.setGenDescripcion(descripcion.toUpperCase());
                        objCatGeneral.setGenNivel(nivel);
                        objCatGeneral.setGenEstado("A");
                        catGeneralFacade.create(objCatGeneral);
                    }
                }
            }
            listCatGeneral = catGeneralFacade.listAllCatGeneralByParam(tipo, BigInteger.ONE, null, null, descripcion);
            descripcion = "";
            

        } catch (Exception e) {
            for (Throwable t = e.getCause(); t != null; t = t.getCause()) {
                if (t.getMessage().contains("java.sql.SQL")) {
                    mostrarMensajeError("Error", "No se pudo crear el registro." + t.getMessage());
                    break;
                }
            }
        }

    }

    public void crearBodega(String tipo, CatBodega papa, int nivel) {
       
        try {
             objBodegaNivel = new CatBodega();
           
            if (papa != null) {
                objBodegaNivel.setBodPapa(papa);
            }
            else{
            objBodegaNivel.setBodPapa(null);
            }
           

            objBodegaNivel.setBodNivel(new BigInteger(String.valueOf(nivel)));
            objBodegaNivel.setBodDescripcion(descripcion.toUpperCase());
              
            objBodegaNivel.setCatEmp(objEmpresa);
            objBodegaNivel.setBodTipo(tipo);
            objBodegaNivel.setBodEstado("A");
            catBodegaFacade.create(objBodegaNivel);
            if(nivel == 0){
                //crear bodega
                 listBodega = null;
            }
            if(nivel == 1){
                //crear nivel 1
                listBodNivel1 = null;
            }
            if(nivel == 2){
                //crear nivel 2
                listBodNivel2 = catBodegaFacade.listAllBodegaByParam(null, new BigInteger("2"), papa.getBodId(), null, null, objEmpresa.getEmpId());
               
               
            }
             descripcion = "";
           
        } catch (Exception e) {
            for (Throwable t = e.getCause(); t != null; t = t.getCause()) {
                if (t.getMessage().contains("java.sql.SQL")) {
                    mostrarMensajeError("Error", "No se pudo crear el registro." + t.getMessage());
                    break;
                }
            }
        }

    }
    
    public void nuevaUbi(BigInteger clavePapa){
        
        bodegaPapa = new CatBodega();
        if(clavePapa != null ){
             bodegaPapa = catBodegaFacade.find(clavePapa);
        }
        
        objBodegaNivel = new CatBodega();
         boolean isPapa = bodegaPapa != null;
         if (isPapa) {
            int niv = bodegaPapa.getBodNivel().intValue() + 1;
            objBodegaNivel.setBodNivel(new BigInteger(String.valueOf(niv)));
            if(niv == 1){
            objBodegaNivel.setBodTipo("S");
            descripcion = "SECCIONES";
            }
            if(niv == 2){
            objBodegaNivel.setBodTipo("P");
             descripcion = "PERCHA";
            }
              if(niv == 3){
            objBodegaNivel.setBodTipo("C");
             descripcion = "COLUMNA";
            }
            if(niv == 4){
            objBodegaNivel.setBodTipo("N");
            descripcion = "NIVEL";
            }
    
    }
    }
    
    public void crearUbiAutomatica() {
        
        boolean isIni = noInicial > 0;
        boolean isFin = noFinal > 0; 
        boolean isPapa = bodegaPapa != null;
        if (isPapa && isIni && isFin) {
                        
            for (int i = noInicial; i <= noFinal; i++) {
                objBodegaNivel.setBodPapa(bodegaPapa);
                objBodegaNivel.setBodEstado("A");
                objBodegaNivel.setBodDescripcion(descripcion + "-" + i);
                if (objEmpresa.getEmpId() != null) {
                    objBodegaNivel.setCatEmp(objEmpresa);
                    catBodegaFacade.create(objBodegaNivel);
                }
                
                
            }
            consEstructBodega();
            
        }
        
    }
    public void btnAsignaEmp(BigInteger clave, String tipoObj) {
        if(tipoObj.equalsIgnoreCase("B")){
        objBodega = catBodegaFacade.find(clave);
        
        }
        if(tipoObj.equalsIgnoreCase("T")){
       objArticulo = catArticuloFacade.find(clave);
        
        }
        
    }

    public void asignarEmpleado(String tipoEmp, BigInteger emlId) {
        if (tipoEmp.equalsIgnoreCase("J")) {
            //el jefe se aasigna a la bodega
            if (emlId != null) {
                objBodega.setEmlId(emlId);
                catBodegaFacade.edit(objBodega);
                listBodega = null;
            } else if (tipoEmp.equalsIgnoreCase("E")) {
                //el encragado se asigna al subtipo
                if (emlId != null) {
                objArticulo.setEmlId(emlId);
                catArticuloFacade.edit(objArticulo);
            }
            }
        }

    }

    //</editor-fold>
    //<editor-fold defaultstate="collapsed" desc="Editar">
       public void onCellEditBodega(final CellEditEvent event) {
        final int posicion = event.getRowIndex();
        if(listBodega != null && !listBodega.isEmpty()){
             final CatBodega objCatBodega = listBodega.get(posicion);
        if(objCatBodega != null){
         if(objCatBodega.getBodEstado().equalsIgnoreCase("I")){

            validaDep(objCatBodega.getBodId(), "B", "I");
           if(ctrEditar){
               mostrarMensajeError("Alerta", message);
                listBodega = null;
           }
           else{
        catBodegaFacade.edit(objCatBodega);
         listBodega = null;

           }
        }
          else{
            catBodegaFacade.edit(objCatBodega);
         listBodega = null;
        
        }
       
       
        }
           
      
        
        }
       
    }
            public void onCellEditNivel1(final CellEditEvent event) {
        final int posicion = event.getRowIndex();
        if(listBodNivel1 != null && !listBodNivel1.isEmpty()){
        final CatBodega objCatBodega = listBodNivel1.get(posicion);
        if (objCatBodega != null) {
             if(objCatBodega.getBodEstado().equalsIgnoreCase("I")){

            validaDep(objCatBodega.getBodId(), "B", "I");
           if(ctrEditar){
               mostrarMensajeError("Alerta", message);
                listBodNivel1 = null;
           }
           else{
        catBodegaFacade.edit(objCatBodega);
         listBodNivel1 = null;

           }
        }
             else{
            catBodegaFacade.edit(objCatBodega);
             listBodNivel1 = null;
             }
        }
        }

       

    }
        public void onCellEditNivel2(final CellEditEvent event) {
        final int posicion = event.getRowIndex();
        if(listBodNivel2 != null && !listBodNivel2.isEmpty()){
        final CatBodega objCatBodega = listBodNivel2.get(posicion);
        if (objCatBodega != null) {
             if(objCatBodega.getBodEstado().equalsIgnoreCase("I")){

            validaDep(objCatBodega.getBodId(), "B", "I");
           if(ctrEditar){
               mostrarMensajeError("Alerta", message);
                 listBodNivel2 = catBodegaFacade.listAllBodegaByParam(null, new BigInteger("2"), objCatBodega.getBodPapa().getBodId(), null, null, objEmpresa.getEmpId());

           }
           else{
        catBodegaFacade.edit(objCatBodega);
          listBodNivel2 = catBodegaFacade.listAllBodegaByParam(null, new BigInteger("2"), objCatBodega.getBodPapa().getBodId(), null, null, objEmpresa.getEmpId());


           }
        }
             else{
            catBodegaFacade.edit(objCatBodega);
             listBodNivel2 = catBodegaFacade.listAllBodegaByParam(null, new BigInteger("2"), objCatBodega.getBodPapa().getBodId(), null, null, objEmpresa.getEmpId());

             }
        }
        }
        
       

    }
    
    public void onCellEditCatGeneral(final CellEditEvent event) {
        final int posicion = event.getRowIndex();
        if(listCatGeneral !=null && !listCatArticulo.isEmpty()){
        final CatGeneral objCatGen = listCatGeneral.get(posicion);
        catGeneralFacade.edit(objCatGen);
        listCatGeneral = catGeneralFacade.listAllCatGeneralByParam(tipo, BigInteger.ONE, null, null, descripcion);
        }
    }

    public void onCellEditArt(final CellEditEvent event) {
        
            final int posicion = event.getRowIndex();
            
            if(listCatArticulo != null && !listCatArticulo.isEmpty()){
            final CatArticulo objCatArticuloAux = listCatArticulo.get(posicion);
            if (objCatArticuloAux != null) {
                catArticuloFacade.edit(objCatArticuloAux);
                listCatArticulo = catArticuloFacade.listArticuloByParam(new BigInteger("2"), null, null, descripcion);

            }
      
            }
            
    }

    public void onCellEditTipo(final CellEditEvent event) {
        final int posicion = event.getRowIndex();
        if(listCatTipo != null && !listCatTipo.isEmpty()){
        CatArticulo objNewArticulo = listCatTipo.get(posicion);
        objNewArticulo.setCatArtPapa(null);
        if(objNewArticulo.getArtEstado().equalsIgnoreCase("I")){
        //validar editar 
           if(objNewArticulo.getCatArtPapa() == null){
           idTipo = objArticulo.getArtId();
           boolean ctrInactiva = validaInactivaTipo(idTipo, null);
           if(ctrInactiva){
           catArticuloFacade.edit(objNewArticulo);
           }
           else{
               mostrarMensajeError("Alerta", "Existe existen dependencias para el tipo.");
           }
           }
          
        }
        else{
       catArticuloFacade.edit(objNewArticulo);
        }
        
        
        
        BigInteger nivelaux = objNewArticulo.getArtNivel();
        
        
         if (nivelaux.intValue() == 2) {
                listCatArticulo = catArticuloFacade.listArticuloByParam(nivelaux, null, null, descripcion);

            }
            if (nivelaux.intValue() == 0) {
                listCatTipo = catArticuloFacade.listArticuloByParam(nivelaux, null, null, descripcion);
            }
            if (nivelaux.intValue() == 1) {
                listCatSubTipo = catArticuloFacade.listArticuloByParam(nivelaux, objNewArticulo.getCatArtPapa().getArtId(), null, null);

            }
       
    }
    }

    public void onCellEditSubTipo(final CellEditEvent event) {
        final int posicion = event.getRowIndex();
        if(listCatSubTipo != null && !listCatSubTipo.isEmpty()){
        CatArticulo objNewArticulo = listCatSubTipo.get(posicion);
        if(objNewArticulo.getArtEstado().equalsIgnoreCase("I")){
        //validar editar 
           if(objNewArticulo.getCatArtPapa() != null){
           idSubtipo = objArticulo.getArtId();
           boolean ctrInactiva = validaInactivaTipo(null, idSubtipo);
           if(ctrInactiva){
           catArticuloFacade.edit(objNewArticulo);
           }
           else{
               mostrarMensajeError("Alerta", "Existe existencia en bodegas para el subtipo.");
           }
           }
          
        }
        else{
        catArticuloFacade.edit(objNewArticulo);
        }
        
        listCatSubTipo = catArticuloFacade.listArticuloByParam(BigInteger.ONE, objNewArticulo.getCatArtPapa().getArtId(), null, null);
        }
    }

    public void editCatGeneral(CatGeneral obj) {
        
        catGeneralFacade.edit(obj);
        listCatGeneral = catGeneralFacade.listAllCatGeneralByParam(tipo, BigInteger.ONE, null, null, descripcion);
    }

    public void editArticulo(CatArticulo obj,String tipo){
   
     if(tipo.equalsIgnoreCase("P")){
         //padre
     if(objArticulo.getArtId() != null)
     obj.setCatArtPapa(objArticulo);
     }
     if(tipo.equalsIgnoreCase("A")){
         //abuelo
     if(objTipo.getArtId() != null)
     obj.getCatArtPapa().setCatArtPapa(objTipo);
     }
     if(tipo.equalsIgnoreCase("MARC")){
     obj.setCatGenMarca(objCatGeneral);
     }
     if(tipo.equalsIgnoreCase("MODE")){
     obj.setCatGenModelo(objCatGeneral);
     }
     if(tipo.equalsIgnoreCase("MEDI")){
     obj.setCatGenForma(objCatGeneral);
     }
      if(tipo.equalsIgnoreCase("PRES")){
     obj.setCatGenConcen(objCatGeneral);
     }
     
    catArticuloFacade.edit(obj);
    listCatArticulo = catArticuloFacade.listArticuloByParam(new BigInteger("2"), null, null, descripcion);
    }
    public void asignaEmpleado(String tipoObj, BigInteger emplClave) {
        if (tipoObj.equalsIgnoreCase("B")) {
            if (emplClave != null) {

                objBodega.setEmlId(emplClave);
                catBodegaFacade.edit(objBodega);
                listBodega = null;
            }
        }
        if (tipoObj.equalsIgnoreCase("T")) {
            descripcion = "";
            if (emplClave != null) {
                objArticulo.setEmlId(emplClave);
                catArticuloFacade.edit(objArticulo);
                listCatTipo = catArticuloFacade.listArticuloByParam(BigInteger.ZERO, null, null, descripcion);

            }
        }

    }
   

    //</editor-fold>
    //<editor-fold defaultstate="collapsed" desc="Eliminar">
    public void eliminaCatgeneral() {
        try {
            catGeneralFacade.remove(objCatGeneral);
            listCatGeneral = catGeneralFacade.listAllCatGeneralByParam(tipo, BigInteger.ONE, null, null, descripcion);
        } catch (Exception e) {
            for (Throwable t = e.getCause(); t != null; t = t.getCause()) {
                if (t.getMessage().contains("java.sql.SQL")) {
                    mostrarMensajeError("Error.Existe dependecncias.", "No se pudo eliminar el registro." + t.getMessage());
                    break;
                }
            }
        }

    }
     public void eliminaArticulo() {
        try {
            //validar si no existe dependencia
           catArticuloFacade.remove(objArticulo);
           if(tipo.equalsIgnoreCase("T")){
                listCatTipo = catArticuloFacade.listArticuloByParam(BigInteger.ZERO, null, null, descripcion);
           }
           else{
               listCatArticulo = catArticuloFacade.listArticuloByParam(new BigInteger("2"), null, null, descripcion);
           }
           
          
        } catch (Exception e) {
            for (Throwable t = e.getCause(); t != null; t = t.getCause()) {
                if (t.getMessage().contains("java.sql.SQL")) {
                    mostrarMensajeError("Error.Existe dependencias.", "No se pudo eliminar el registro.");
                    break;
                }
            }
        }

    }
      public void eliminaTipo() {
        try {
            idTipo = null;
            idSubtipo = null;
            boolean ctrElimina = false;
            //validar si existe articulo con existencia en bodega para eliminar
            if(objArticulo != null){
                if(objArticulo.getCatArtPapa() == null){
                idTipo = objArticulo.getArtId();
                message = "Existe registros dependientes.";
                }
                else{
                idSubtipo = objArticulo.getArtId();
                 message = "Existe existencia en bodegas para el subtipo.";
                }
             ctrElimina = catArticuloFacade.verificaExisBySubtipo(idSubtipo, idTipo);
             if(ctrElimina)
             {
           catArticuloFacade.remove(objArticulo);
           listCatTipo = catArticuloFacade.listArticuloByParam(BigInteger.ZERO, null, null, descripcion);
             }
             else{
                 
                 mostrarMensajeError("Alerta", message);
             }
            
            }
           
        } catch (Exception e) {
            for (Throwable t = e.getCause(); t != null; t = t.getCause()) {
                if (t.getMessage().contains("java.sql.SQL")) {
                    mostrarMensajeError("Error", "No se pudo eliminar el registro." + t.getMessage());
                    break;
                }
            }
        }

    }
      
      public boolean validaInactivaTipo(BigInteger idSub, BigInteger idTip) {

        idTipo = idTip;
        idSubtipo = idSub;
        boolean ctrElimina = false;
        //validar si existe articulo con existencia en bodega para eliminar                
        ctrElimina = catArticuloFacade.verificaExisBySubtipo(idSubtipo, idTipo);

        return ctrElimina;
    }
      
       public void eliminaBodega() {
        try {
            BigInteger nivel = objBodegaNivel.getBodNivel();
          catBodegaFacade.remove(objBodegaNivel);
          
          if(nivel.intValue() == 0){
          //elimina bodega
               listBodega = catBodegaFacade.listAllBodegaByParam("B", BigInteger.ZERO, null, null, null, objEmpresa.getEmpId());
          }
          if(nivel.intValue() == 1){
           //elimina nivel 1
              listBodNivel1 = null;
          
          }
          if(nivel.intValue() == 2){
          //elimina nivel 2
            
              if(objBodegaNivel.getBodPapa() != null){
              listBodNivel2 = catBodegaFacade.listAllBodegaByParam(null, new BigInteger("2"), objBodegaNivel.getBodPapa().getBodId(), null, null, objEmpresa.getEmpId());
              }
          
          }
         
        } catch (Exception e) {
            for (Throwable t = e.getCause(); t != null; t = t.getCause()) {
                if (t.getMessage().contains("java.sql.SQL")) {
                    mostrarMensajeError("Error", "No se pudo crear el registro." + t.getMessage());
                    break;
                }
            }
        }

    }
       
       public void asignaArticuloBod() {
        String mensaje;
        try {
            if (objArticulo != null && objBodega != null) {
                mensaje = invExistenciaBodegaFacade.crearExistencia(objBodega.getBodId(), objArticulo);
                mostrarMensajeError("Info.", mensaje);
            } else {
                mostrarMensajeError("Info.", "Seleccione la bodega");
            }
        } catch (Exception e) {
            for (Throwable t = e.getCause(); t != null; t = t.getCause()) {
                if (t.getMessage().contains("java.sql.SQL")) {
                    mostrarMensajeError("Error.", "No se asigná el registro." + t.getMessage());
                    break;
                }
            }
        }

    }
    //</editor-fold>
        //<editor-fold defaultstate="collapsed" desc="Validacion">

    public void cambiaTab(final TabChangeEvent event) {
        final TabView tabView = (TabView) event.getComponent();
        final int inxA = tabView.getChildren().indexOf(event.getTab());

        if (inxA == 0) {
            //tab Articulos
            descripcion = "";
            idTipo = null;
            idSubtipo = null;
            tipo = "A";
            nivel = 2;
            formulario="formArticulos";
            listCatArticulo = null;
           

        }
        if (inxA == 1) {
            //tab Bodega
            tipo = "B";
            formulario="formBodega";
            nivel = 0;
            descripcion = "";
            //listCatArticulo = null;

        }
        if (inxA == 2) {
            //tab tipo y Subtipo
            tipo = "T";
            nivel = 0;
            formulario="formTipo";
            descripcion = "";
            //listCatArticulo = null;
            listCatTipo = catArticuloFacade.listArticuloByParam(BigInteger.ZERO, null, null, descripcion);
           

        }
        if (inxA == 3) {
            //tab seccion/p/c/n
            tipo = "S";
            formulario="formSeccion";
            nivel = 1;
            //listCatArticulo = null;
             descripcion = "";
             listCatTipo = null;

        }
        if (inxA == 4) {
            //marca
            tipo = "MARC";
             descripcion = "";
             formulario = "formMarca";
             nivel = 1;
             //listCatArticulo = null;
             listCatTipo = null;
             listCatGeneral = catGeneralFacade.listAllCatGeneralByParam(tipo, BigInteger.ONE, null, null, descripcion);
             
        }

        if (inxA == 5) {
            //modelo
            tipo = "MODE";
            nivel = 1;
            formulario = "formModelo";
             descripcion = "";
            listCatGeneral = catGeneralFacade.listAllCatGeneralByParam(tipo, BigInteger.ONE, null, null, descripcion);
        }
        if (inxA == 6) {
            //medida
            tipo = "MEDI";
            nivel = 1;
             formulario = "formForma";
             descripcion = "";
            listCatGeneral = catGeneralFacade.listAllCatGeneralByParam(tipo, BigInteger.ONE, null, null, descripcion);
        }
        if (inxA == 7) {
            //presentacion
            tipo = "PRES";
            nivel = 1;
             formulario = "formMPrsentacion";
             descripcion = "";
            listCatGeneral = catGeneralFacade.listAllCatGeneralByParam(tipo, BigInteger.ONE, null, null, descripcion);

        }
        
    }

    public void onRowToggleTipo(final ToggleEvent event) {
        objArticulo = (CatArticulo) event.getData();
        listCatSubTipo = catArticuloFacade.listArticuloByParam(BigInteger.ONE, objArticulo.getArtId(), null, null);

    }
    public void onRowToggleBodegaNiv2(final ToggleEvent event) {
         CatBodega objPapa = (CatBodega) event.getData();
        listBodNivel2 = catBodegaFacade.listAllBodegaByParam(null, new BigInteger("2"), objPapa.getBodId(), null, null, objEmpresa.getEmpId());

    }
      
    public boolean isNumeric(final String cadena) {
        try {
            Integer.parseInt(cadena);
            return true;
        } catch (NumberFormatException nfe) {
            return false;
        }
    }
    public void validaDep(BigInteger clave, String tabla, String acc){
        objBodegaNivel = catBodegaFacade.find(clave);
        boolean isInactiva = acc.equalsIgnoreCase("I");
        boolean isElimina = acc.equalsIgnoreCase("E");
          boolean isModifica= acc.equalsIgnoreCase("M");
        if(objBodegaNivel != null){
            if(isInactiva  || isElimina ){
                 int conta = catBodegaFacade.countDepedientes(objBodegaNivel.getBodId(), tabla);
                 if(conta > 0){
                 ctrEditar = true;
                  if(isInactiva){
                   message = "Existe registros dependientes.No puede Inactivar.";
                   accion = "I";
                  }
                  if(isElimina){
                   message = "Existe registros dependientes.No puede Eliminar.";
                   accion = "E";
                  }
                
                 }
                 else{
                     ctrEditar = false;
                     if(isInactiva){
                   message = "Confirma Inactivar el registro.";
                   accion = "I";
                  }
                  if(isElimina){
                   message = "Confirma Eliminar el registro.";
                   accion = "E";
                  }
                 
                 }
        
            
            }
            else{
            ctrEditar = false;
            accion="M";
            message = "";
            }
       
        }
   
    }
    public void midifica(){
        if(accion.equalsIgnoreCase("I")){
            objBodegaNivel.setBodEstado("I");
            catBodegaFacade.edit(objBodegaNivel);
        }
        if(accion.equalsIgnoreCase("E")){
            eliminaBodega();
        }
        if(accion.equalsIgnoreCase("M")){
             catBodegaFacade.edit(objBodegaNivel);
        }
    consEstructBodega();
    }
      //</editor-fold>

   

    //</editor-fold>
    //<editor-fold defaultstate="collapsed" desc="Getter and Setter">
    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public CatArticulo getObjArticulo() {
        if (objArticulo == null) {
            objArticulo = new CatArticulo();
        }
        return objArticulo;
    }

    public void setObjArticulo(CatArticulo objArticulo) {
        this.objArticulo = objArticulo;
    }

    public List<CatArticulo> getListCatArticulo() {

        return listCatArticulo;
    }

    public void setListCatArticulo(List<CatArticulo> listCatArticulo) {
        this.listCatArticulo = listCatArticulo;
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

    public List<CatEmpresa> getListProveedor() {
        return listProveedor;
    }

    public void setListProveedor(List<CatEmpresa> listProveedor) {
        this.listProveedor = listProveedor;
    }

    public CatBodega getObjBodega() {
        if (objBodega == null) {
            objBodega = new CatBodega();
        }
        return objBodega;
    }

    public void setObjBodega(CatBodega objBodega) {
        this.objBodega = objBodega;
    }

    public List<CatBodega> getListBodega() {
        if (listBodega == null && objEmpresa != null) {

            listBodega = catBodegaFacade.listAllBodegaByParam("B", BigInteger.ZERO, null, null, null, objEmpresa.getEmpId());

        }
        return listBodega;
    }

    public void setListBodega(List<CatBodega> listBodega) {
        this.listBodega = listBodega;
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

    public List<CatGeneral> getListCatGeneral() {
        return listCatGeneral;
    }

    public void setListCatGeneral(List<CatGeneral> listCatGeneral) {
        this.listCatGeneral = listCatGeneral;
    }

    public int getInxTab() {
        return inxTab;
    }

    public void setInxTab(int inxTab) {
        this.inxTab = inxTab;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public CatBodega getBodegaPapa() {
        if (bodegaPapa == null) {
            bodegaPapa = new CatBodega();
        }
        return bodegaPapa;
    }

    public void setBodegaPapa(CatBodega bodegaPapa) {
        this.bodegaPapa = bodegaPapa;
    }

    //</editor-fold>
    public List<CatArticulo> getListCatTipo() {
   
        return listCatTipo;
    }

    public void setListCatTipo(List<CatArticulo> listCatTipo) {
        this.listCatTipo = listCatTipo;
    }

    public List<CatArticulo> getListCatSubTipo() {
     
        return listCatSubTipo;
    }

    public void setListCatSubTipo(List<CatArticulo> listCatSubTipo) {
        this.listCatSubTipo = listCatSubTipo;
    }

    public TreeNode getRoot() {
        return root;
    }

    public void setRoot(TreeNode root) {
        this.root = root;
    }

    public TreeNode getSelectNode() {
        return selectNode;
    }

    public void setSelectNode(TreeNode selectNode) {
        this.selectNode = selectNode;
    }

    public Estructura_bodega getSeleccionarFila() {
        return seleccionarFila;
    }

    public void setSeleccionarFila(Estructura_bodega seleccionarFila) {
        this.seleccionarFila = seleccionarFila;
    }

    public String getNombreForm() {
        return nombreForm;
    }

    public void setNombreForm(String nombreForm) {
        this.nombreForm = nombreForm;
    }

    public int getNivel() {
        return nivel;
    }

    public void setNivel(int nivel) {
        this.nivel = nivel;
    }

    public List<CatBodega> getListBodNivel1() {
        if(objEmpresa != null && objBodega != null){
        if(listBodNivel1 == null && objEmpresa.getEmpId() != null && objBodega.getBodId() != null){
        listBodNivel1 = catBodegaFacade.listAllBodegaByParam(null, BigInteger.ONE, objBodega.getBodId() , null, null, objEmpresa.getEmpId());
        }
        }
        
        return listBodNivel1;
    }

    public void setListBodSeccion(List<CatBodega> listBodSeccion) {
        this.listBodNivel1 = listBodSeccion;
    }

    public List<CatBodega> getListBodNivel2() {
        return listBodNivel2;
    }

    public void setListBodPercha(List<CatBodega> listBodPercha) {
        this.listBodNivel2 = listBodPercha;
    }

    public boolean isCtrEditar() {
        return ctrEditar;
    }

    public void setCtrEditar(boolean ctrEditar) {
        this.ctrEditar = ctrEditar;
    }

    public BigInteger getClave() {
        return clave;
    }

    public void setClave(BigInteger clave) {
        this.clave = clave;
    }

    public CatBodega getObjBodegaNivel() {
        if(objBodegaNivel == null){
    objBodegaNivel = new CatBodega();
    }
        return objBodegaNivel;
    }

    public void setObjBodegaNivel(CatBodega objBodegaNivel) {
        this.objBodegaNivel = objBodegaNivel;
    }

    public String getFormulario() {
        return formulario;
    }

    public void setFormulario(String formulario) {
        this.formulario = formulario;
    }

    public RegEmpleado getObjEmpleado() {
        if (objEmpleado == null) {
            objEmpleado = new RegEmpleado();
        }
        return objEmpleado;
    }

    public void setObjEmpleado(RegEmpleado objEmpleado) {
        this.objEmpleado = objEmpleado;
    }

    public List<RegEmpleado> getListaRegEmpleado() {
        return listaRegEmpleado;
    }

    public void setListaRegEmpleado(List<RegEmpleado> listaRegEmpleado) {
        this.listaRegEmpleado = listaRegEmpleado;
    }

    public CatArticulo getObjTipo() {
        if (objTipo == null) {
            objTipo = new CatArticulo();
           
        }
         return objTipo;
    }


    public void setObjTipo(CatArticulo objTipo) {
        this.objTipo = objTipo;
    }

    public List<CatBodega> getListBodUbi() {
        if(listBodUbi == null){
        listBodUbi = catBodegaFacade.listAllBodegaByParam(tipo, BigInteger.ZERO, null, null, null, null);
        }
        return listBodUbi;
    }

    public void setListBodUbi(List<CatBodega> listBodUbi) {
        this.listBodUbi = listBodUbi;
    }

    public int getNoInicial() {
        return noInicial;
    }

    public void setNoInicial(int noInicial) {
        this.noInicial = noInicial;
    }

    public int getNoFinal() {
        return noFinal;
    }

    public void setNoFinal(int noFinal) {
        this.noFinal = noFinal;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getAccion() {
        return accion;
    }

    public void setAccion(String accion) {
        this.accion = accion;
    }

    public BigInteger getIdTipo() {
        return idTipo;
    }

    public void setIdTipo(BigInteger idTipo) {
        this.idTipo = idTipo;
    }

    public BigInteger getIdSubtipo() {
        return idSubtipo;
    }

    public void setIdSubtipo(BigInteger idSubtipo) {
        this.idSubtipo = idSubtipo;
    }

    

   



}