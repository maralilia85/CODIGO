/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.mil.armada.inv.listas;

import com.sun.javafx.scene.control.skin.VirtualFlow;
import ec.mil.armada.inv.modelo.catalago.CatArticulo;
import ec.mil.armada.inv.modelo.catalago.CatBodega;
import ec.mil.armada.inv.modelo.catalago.CatEmpresa;
import ec.mil.armada.inv.modelo.catalago.CatGeneral;
import ec.mil.armada.inv.modelo.inventario.InvExistenciaBodega;
import ec.mil.armada.inv.remotos.catalogo.CatArticuloFacadeRemote;
import ec.mil.armada.inv.remotos.catalogo.CatBodegaFacadeRemote;
import ec.mil.armada.inv.remotos.catalogo.CatEmpresaFacadeRemote;
import ec.mil.armada.inv.remotos.catalogo.CatGeneralFacadeRemote;
import ec.mil.armada.inv.remotos.inventario.InvExistenciaBodegaFacadeRemote;
import java.io.Serializable;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
import javax.faces.model.SelectItem;
import javax.inject.Named;
import javax.faces.view.ViewScoped;

/**
 *
 * @author maria_rodriguez
 */
@Named(value = "listasController")
@ViewScoped
public class ListasController implements Serializable {
   

    private static final long serialVersionUID = 1322118817318367223L;

    /**
     * Creates a new instance of ListasController
     */
    public ListasController() {
    }
    //<editor-fold defaultstate="collapsed" desc="Declaracion de variables">
    private List<SelectItem> listItemTipo;
    private List<SelectItem> listItemTipoArt;
    private List<SelectItem> listItemSubTipo;
    private List<SelectItem> listItemBodega;
    private List<SelectItem> listItemSeccion;
    private List<SelectItem> listItemPercha;
    private List<SelectItem> listItemColumna;
    private List<SelectItem> listItemNivel;
    private List<SelectItem> listCatGeneral;
    private List<SelectItem> listProveedor;
    private List<SelectItem> listExArticulo;
      private List<SelectItem> listTipoTransaccion;

    @EJB
    private CatGeneralFacadeRemote catGeneralFacade;
    @EJB
    private CatEmpresaFacadeRemote catEmpresaFacade;
    @EJB
    private CatBodegaFacadeRemote catBodegaFacade;
    @EJB
    private CatArticuloFacadeRemote catArticuloFacade;
     @EJB
    private InvExistenciaBodegaFacadeRemote invExistenciaBodegaFacade;

    //</editor-fold>
    public SelectItem devuelveItem(final Object value, final String descripcion) {
        final SelectItem registro = new SelectItem(value, descripcion);
        return registro;
    }
    public void enceraListasUbi(String tipo){
        if(tipo.equalsIgnoreCase("S")){
        }
    
    }

      //<editor-fold defaultstate="collapsed" desc="Getter and Setter">
    public List<SelectItem> getListItemTipo(BigInteger idBod) {

        listItemTipo = new ArrayList<>();
        if(idBod != null){
        final List<CatArticulo> listaAux = catArticuloFacade.listTipoByExistencia(idBod);
        if (listaAux.size() > 0) {
            for (final CatArticulo art : listaAux) {
                listItemTipo.add(devuelveItem(art.getArtId(), art.getArtNombgenerico()));
            }
        }
        }
        
        

        return listItemTipo;
    }

    public void setListItemTipo(List<SelectItem> listItemTipo) {
        this.listItemTipo = listItemTipo;
    }

    public List<SelectItem> getListItemTipoArt(int nivel) {
        listItemTipoArt = new ArrayList<>();
        BigInteger nivelaux = new BigInteger(String.valueOf(nivel));
        final List<CatArticulo> listaAux = catArticuloFacade.listByTipoNivel(null, nivelaux);
        if (listaAux.size() > 0) {
            for (final CatArticulo art : listaAux) {
                listItemTipoArt.add(devuelveItem(art.getArtId(), art.getArtNombgenerico()));
            }
        }
        return listItemTipoArt;
    }

    public void setListItemTipoArt(List<SelectItem> listItemTipoArt) {
        this.listItemTipoArt = listItemTipoArt;
    }

    public List<SelectItem> getListItemSubTipo(BigInteger clavePapa, BigInteger nivel) {

        listItemSubTipo = new ArrayList<>();
        if (clavePapa != null) {
            final List<CatArticulo> listaAux = catArticuloFacade.listByTipoNivel(clavePapa, nivel);
            if (listaAux.size() > 0) {
                for (final CatArticulo art : listaAux) {
                    listItemSubTipo.add(devuelveItem(art.getArtId(), art.getArtNombgenerico()));
                }
            }
        }

        return listItemSubTipo;
    }

    public void setListItemSubTipo(List<SelectItem> listItemSubTipo) {
        this.listItemSubTipo = listItemSubTipo;
    }

    public List<SelectItem> getListItemSeccion(BigInteger codigoBodega, BigInteger empId) {
        if (codigoBodega != null && empId != null) {
            listItemSeccion = new ArrayList<>();
            final List<CatBodega> listAux = catBodegaFacade.listDescripcionByTipo(codigoBodega, empId);
            if (listAux.size() > 0) {
                for (CatBodega bodega : listAux) {
                    listItemSeccion.add(devuelveItem(bodega.getBodId(), bodega.getBodDescripcion()));

                }
            }
        }

        return listItemSeccion;
    }

    public void setListItemSeccion(List<SelectItem> listItemSeccion) {
        this.listItemSeccion = listItemSeccion;
    }

    public List<SelectItem> getListItemPercha(BigInteger codigoBodega, BigInteger empId) {
         if (codigoBodega != null && empId != null) {
        listItemPercha = new ArrayList<>();
        final List<CatBodega> listAux = catBodegaFacade.listDescripcionByTipo(codigoBodega, empId);
        if (listAux.size() > 0) {
            for (CatBodega bodega : listAux) {
                listItemPercha.add(devuelveItem(bodega.getBodId(), bodega.getBodDescripcion()));

            }
        }
         }
        return listItemPercha;
    }

    public void setListItemPercha(List<SelectItem> listItemPercha) {
        this.listItemPercha = listItemPercha;
    }

    public List<SelectItem> getListItemColumna(BigInteger codigoBodega, BigInteger empId) {
        if (codigoBodega != null && empId != null) {
            listItemColumna = new ArrayList<>();
              final List<CatBodega> listAux = catBodegaFacade.listDescripcionByTipo(codigoBodega, empId);
            if(listAux.size() >0){
            for (CatBodega bodega : listAux) {
                listItemColumna.add(devuelveItem(bodega.getBodId(), bodega.getBodDescripcion()));
            }

        }
        }
        return listItemColumna;
    }

    public void setListItemColumna(List<SelectItem> listItemColumna) {
        this.listItemColumna = listItemColumna;
    }

    public List<SelectItem> getListItemNivel(BigInteger codigoBodega, BigInteger empId) {
        if (codigoBodega != null && empId != null) {
            listItemNivel = new ArrayList<>();
             final List<CatBodega> listAux = catBodegaFacade.listDescripcionByTipo(codigoBodega, empId);
             if(listAux.size() >0){
            for (CatBodega bodega : listAux) {
                listItemNivel.add(devuelveItem(bodega.getBodId(), bodega.getBodDescripcion()));

            }
        }
        }
        return listItemNivel;
    }

    public void setListItemNivel(List<SelectItem> listItemNivel) {
        this.listItemNivel = listItemNivel;
    }

    public List<SelectItem> getListCatGeneral(String tipo) {
        boolean isTipo = tipo != null && tipo.trim().length() > 0 && !(tipo.trim().toLowerCase().compareTo("null") == 0);
        if (isTipo) {
            listCatGeneral = new ArrayList<>();
            final List<CatGeneral> listAux = catGeneralFacade.listCatGeneralActivoByTipo(tipo);
            for (CatGeneral general : listAux) {
                listCatGeneral.add(devuelveItem(general.getGenId(), general.getGenDescripcion()));

            }
        }

        return listCatGeneral;
    }

    public void setListCatGeneral(List<SelectItem> listCatGeneral) {
        this.listCatGeneral = listCatGeneral;
    }

    public List<SelectItem> getListTipoTransaccion(String tipo) {
         boolean isTipo = tipo != null && tipo.trim().length() > 0 && !(tipo.trim().toLowerCase().compareTo("null") == 0);
        if (isTipo) {
            listTipoTransaccion = new ArrayList<>();
            final List<CatGeneral> listAux = catGeneralFacade.listCatGeneralActivoByTipo(tipo);
            for (CatGeneral general : listAux) {
                listTipoTransaccion.add(devuelveItem(general.getGenSiglatipo(), general.getGenDescripcion()));

            }
        }
        return listTipoTransaccion;
    }
        public List<SelectItem> getListDescrip(String tipo) {
         boolean isTipo = tipo != null && tipo.trim().length() > 0 && !(tipo.trim().toLowerCase().compareTo("null") == 0);
        if (isTipo) {
            listTipoTransaccion = new ArrayList<>();
            final List<CatGeneral> listAux = catGeneralFacade.listCatGeneralActivoByTipo(tipo);
            for (CatGeneral general : listAux) {
                listTipoTransaccion.add(devuelveItem(general.getGenDescripcion(), general.getGenDescripcion()));

            }
        }
        return listTipoTransaccion;
    }

    public void setListTipoTransaccion(List<SelectItem> listTipoTransaccion) {
        this.listTipoTransaccion = listTipoTransaccion;
    }

    public List<SelectItem> getListProveedor(String tipo, String estado, String descripcion) {
        boolean isTipo = tipo != null && tipo.trim().length() > 0 && !(tipo.trim().toLowerCase().compareTo("null") == 0);
        boolean isEstado = estado != null && estado.trim().length() > 0 && !(estado.trim().toLowerCase().compareTo("null") == 0);
        boolean isDesc = descripcion != null && descripcion.trim().length() > 0 && !(descripcion.trim().toLowerCase().compareTo("null") == 0);
        if (isTipo && isEstado) {
            listProveedor = new ArrayList<>();
            final List<CatEmpresa> listAux = catEmpresaFacade.listEmpDescripcionByParam(tipo, estado, descripcion);
            for (CatEmpresa empresa : listAux) {
                listProveedor.add(devuelveItem(empresa.getEmpId(), empresa.getEmpNombre()));

            }
        }
        return listProveedor;
    }

    public void setListProveedor(List<SelectItem> listProveedor) {
        this.listProveedor = listProveedor;
    }

    public List<SelectItem> getListItemBodega(BigInteger empId ,String tipo) {
       
        if (empId != null) {
            listItemBodega = new ArrayList<>();
            final List<CatBodega> listAux = catBodegaFacade.listDescripcionByTipo(null, empId,tipo);
            for (CatBodega bodega : listAux) {
                listItemBodega.add(devuelveItem(bodega.getBodId(), bodega.getBodDescripcion()));
            }

        }

        return listItemBodega;
    }

    public void setListItemBodega(List<SelectItem> listItemBodega) {
        this.listItemBodega = listItemBodega;
    }
     public List<SelectItem> getListExArticulo(BigInteger bodId, BigInteger tipoArt, String descripcion ) {
        boolean isDescripcion = descripcion != null && descripcion.trim().length() > 0 && !(descripcion.trim().toLowerCase().compareTo("null") == 0);
         if(bodId != null && tipoArt != null && isDescripcion)
         listExArticulo = new ArrayList<>();
         List<InvExistenciaBodega> listAux  = invExistenciaBodegaFacade.listAllByParam(bodId, null, tipoArt, "A", descripcion, null, null);
         if(listAux.size() > 0){
             for (InvExistenciaBodega exis : listAux) {
                 listExArticulo.add(new SelectItem(exis.getCatArticulo().getArtCodigo(), exis.getCatArticulo().getArtNombgenerico()));
                 
             }
         }
        return listExArticulo;
    }
     
    
     



    public void setListExArticulo(List<SelectItem> listExArticulo) {
        this.listExArticulo = listExArticulo;
    }
    //</editor-fold>

   

}
