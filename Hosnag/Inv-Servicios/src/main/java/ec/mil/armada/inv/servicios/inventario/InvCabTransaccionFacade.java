/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.mil.armada.inv.servicios.inventario;

import ec.mil.armada.inv.remotos.inventario.InvLoteArticuloFacadeRemote;
import ec.mil.armada.inv.remotos.inventario.InvExistenciaBodegaFacadeRemote;
import ec.mil.armada.inv.remotos.inventario.InvDetTransaccionFacadeRemote;
import ec.mil.armada.inv.modelo.catalago.CatArticulo;
import ec.mil.armada.inv.modelo.catalago.CatBodega;
import ec.mil.armada.inv.modelo.inventario.InvCabTransaccion;
import ec.mil.armada.inv.modelo.inventario.InvDetTransaccion;
import ec.mil.armada.inv.modelo.inventario.InvExistenciaBodega;
import ec.mil.armada.inv.modelo.inventario.InvLoteArticulo;
import ec.mil.armada.inv.remotos.catalogo.CatArticuloFacadeRemote;
import ec.mil.armada.inv.remotos.registro.RegCabliquidacionFacadeRemote;
import ec.mil.armada.inv.remotos.registro.RegCabordencompraFacadeRemote;
import ec.mil.armada.inv.remotos.registro.RegDetliquidacionFacadeRemote;
import ec.mil.armada.inv.remotos.registro.RegEmpleadoFacadeRemote;
import ec.mil.armada.inv.servicios.abstractFacade.AbstractFacade;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author maria_rodriguez
 */
@Stateless
public class InvCabTransaccionFacade extends AbstractFacade<InvCabTransaccion> implements ec.mil.armada.inv.remotos.inventario.InvCabTransaccionFacadeRemote {
    @EJB
    private RegCabliquidacionFacadeRemote regCabliquidacionFacade;
    @EJB
    private RegDetliquidacionFacadeRemote regDetliquidacionFacade;
   
    @EJB
    private InvLoteArticuloFacadeRemote invLoteArticuloFacade;
    @EJB
    private InvExistenciaBodegaFacadeRemote invExistenciaBodegaFacade;
    @EJB
    private InvDetTransaccionFacadeRemote invDetTransaccionFacade;
    @EJB
    transient private CatArticuloFacadeRemote catArticuloFacade;
    @EJB
    transient private RegEmpleadoFacadeRemote regEmpleadoFacade;
    @EJB
    private RegCabordencompraFacadeRemote regCabordencompraFacade;
    
    @PersistenceContext(unitName = "ec.mil.armada_Inv-Servicios_ejb_1.0PU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public InvCabTransaccionFacade() {
        super(InvCabTransaccion.class);
    }
    
    @Override
    public CatBodega objCatBodega(BigInteger bodId){
    
        CatBodega objeto = null;

        final Query query = em.createQuery("Select object (b) from CatBodega b where b.bodId = :bodId  ");
        query.setParameter("bodId", bodId);
        query.setHint("eclipselink.refresh", true);
        final List<CatBodega> result = query.getResultList();
        if (result.size() > 0) {
            objeto = result.get(0);
        }
        return objeto;
    }
    @Override
     public CatArticulo objCatArticulo(BigInteger artId){
    
        CatArticulo objeto = null;

        final Query query = em.createQuery("Select object (a) from CatArticulo a where a.artId = :artId ");
        query.setParameter("artId", artId);
        query.setHint("eclipselink.refresh", true);
        final List<CatArticulo> result = query.getResultList();
        if (result.size() > 0) {
            objeto = result.get(0);
        }
        return objeto;
    }
    
    @Override
   public List<InvCabTransaccion> listAllByParam(BigInteger bodId,String estado, String tipo, String fechaIni, String fechaFin, String operador, BigInteger tipoId) {
        final StringBuilder SQLNative = new StringBuilder();
        boolean isBodega = bodId != null;
        boolean isTipoId = tipoId != null;
        boolean isEstado = estado != null && estado.trim().length() > 0 && !(estado.trim().toLowerCase().compareTo("null") == 0);
        boolean isTipo = tipo != null && tipo.trim().length() > 0 && !(tipo.trim().toLowerCase().compareTo("null") == 0);
        boolean isfechaIni = fechaIni  != null && fechaIni.trim().length() > 0 && !(fechaIni.trim().toLowerCase().compareTo("null") == 0);
        boolean isfechaFin = fechaFin  != null && fechaFin.trim().length() > 0 && !(fechaFin.trim().toLowerCase().compareTo("null") == 0);
        boolean isOperador = operador != null && operador.trim().length() > 0 && !(operador.trim().toLowerCase().compareTo("null") == 0);

        SQLNative.append("SELECT CTR_ID,CTR_TIPO,CTR_NUMERO,CTR_CLAVETABLA,CTR_SIGLASTABLA,CTR_ESTADO,CTR_FECHA,CTR_TOTAL, CTR_OBSERVACION, CTR_FECHADOC, CTR_TOTALDESC, CTR_TOTALIVA, CTR_TOTAGENERAL, CTR_VALIDA,   ");
         SQLNative.append("  CTR_AREA, CTR_ENCARGAREA, BOD_ID  ");
        SQLNative.append("   FROM INV_CABTRANSACCION CT ");
        SQLNative.append("   WHERE 1=1 ");
        if (isBodega && isTipo) {
            if (isBodega) {
                SQLNative.append("   AND CT.BOD_ID = ").append(bodId);

            }
            if (isTipoId) {
                SQLNative.append("   AND CT.TIPO_ARTICULO = ").append(tipoId);

            }
            
            if (isEstado) {
                SQLNative.append(" AND CT.CTR_ESTADO =  '").append(estado).append("' ");
                

            }
            if (isTipo) {
                SQLNative.append(" AND CT.CTR_TIPO IN  (").append(tipo).append(") ");  
                

            }
            if (isfechaIni && isOperador) {
                //Buscar por existencia
                if (operador.equalsIgnoreCase("=")) {
                     SQLNative.append(" AND TRUNC (CT.CTR_FECHA) = to_date('").append(fechaIni).append("','dd/mm/yyyy')");
                }
                if (operador.equalsIgnoreCase(">=")) {
                     SQLNative.append(" AND TRUNC (CT.CTR_FECHA) >= to_date('").append(fechaIni).append("','dd/mm/yyyy')");
                }
                if (operador.equalsIgnoreCase("<=")) {
                     SQLNative.append(" AND TRUNC (CT.CTR_FECHA) <= to_date('").append(fechaIni).append("','dd/mm/yyyy')");
                }
                if (isfechaFin && operador.equalsIgnoreCase("ENTRE")) {
                     SQLNative.append("  AND TRUNC (CT.CTR_FECHA) BETWEEN to_date('").append(fechaIni).append("','dd/mm/yyyy') and to_date('").append(fechaFin).append("','dd/mm/yyyy')");
                }

            }
            
                         SQLNative.append("   ORDER BY CT.CTR_FECHA ASC  ");


        } else {
            SQLNative.append(" AND 1=2   ");

        }
final Query query = em.createNativeQuery(SQLNative.toString());
        final List<Object[]> datos = query.getResultList();
        final List<InvCabTransaccion> datosRetorno = new ArrayList<>();
        for (final Object[] obj : datos) {
            int a = 0;
            final InvCabTransaccion inv = new InvCabTransaccion();
            inv.setCtrId(new BigInteger(obj[a].toString()));
            a++;
              if(obj[a] != null){
            inv.setCtrTipo(((String) obj[a]));}
            a++;
              if(obj[a] != null){
            inv.setCtrNumero(new BigInteger(obj[a].toString()));}
            a++;
            if(obj[a] != null){
            inv.setCtrClavetabla(new BigInteger(obj[a].toString()));}
            a++;
             if(obj[a] != null){
            inv.setCtrSiglastabla(((String) obj[a]));}
            a++;
            if(obj[a] != null){
            inv.setCtrEstado(((String) obj[a]));}
            a++;
            if(obj[a] != null){
            inv.setCtrFecha(((Date) obj[a]));}
            a++;
            if(obj[a] != null){
            inv.setCtrTotal(((BigDecimal) obj[a]).doubleValue());}
            a++;
            if(obj[a] != null){
             inv.setCtrObservacion(((String) obj[a]));}
            a++;
            if (obj[a] != null) {
                inv.setCtrFechaDoc(((Date) obj[a]));
            }
            a++;
            if(obj[a] != null){
            inv.setCtrTotalDesc(((BigDecimal) obj[a]).doubleValue());}
            a++;
            if(obj[a] != null){
            inv.setCtrTotalIva(((BigDecimal) obj[a]).doubleValue());}
            a++;
            if(obj[a] != null){
            inv.setCtrTotaGeneral(((BigDecimal) obj[a]).doubleValue());}
            a++;
            if(obj[a] != null){
             inv.setCtrValida(((String) obj[a]));}
            a++;
            inv.setCatBodega(new CatBodega());
            if(obj[a] != null){
            inv.setCatBodegaArea(objCatBodega(new BigInteger(obj[a].toString())));}
            a++;
            if(obj[a] != null){
            inv.setCtrEncargArea(((String) obj[a]));}
            a++;
            inv.setCatBodega(new CatBodega());
            if(obj[a] != null){
            inv.setCatBodega(objCatBodega(new BigInteger(obj[a].toString())));}
           
            datosRetorno.add(inv);
        }

        return datosRetorno;
    }

    @Override
    public InvCabTransaccion crearObjeto(CatBodega bodega, BigInteger claveTabla, String siglasTabla, String estado, String retira, String tipo, BigInteger artId) {

        InvCabTransaccion objetoNew = null;
          boolean isBodega = bodega != null && bodega.getBodId() != null;
          boolean isClaveTabla = claveTabla != null;
          boolean isSiglas =  siglasTabla != null && siglasTabla.trim().length() > 0 && !(siglasTabla.trim().toLowerCase().compareTo("null") == 0);
        if(isBodega){
          boolean ctrInserta = verificaDuplicadoCab(claveTabla, siglasTabla, tipo, bodega.getBodId(), artId);
             if (ctrInserta) {
            objetoNew = new InvCabTransaccion();
            objetoNew.setCatBodega(bodega);
            if (bodega.getEmlId() != null) {
                //obtiene el feje de bodega
                String jefe = regEmpleadoFacade.nombreEmpleado(bodega.getEmlId());
                objetoNew.setCtrJefe(jefe);
            }
            objetoNew.setCtrClavetabla(claveTabla);
            objetoNew.setCtrSiglastabla(siglasTabla);
            objetoNew.setCtrEstado(estado);
            objetoNew.setCtrFecha(new Date());
            objetoNew.setCtrTotal(0.00);
            objetoNew.setCtrTotalDesc(0.00);
            objetoNew.setCtrTotalIva(0.00);
            objetoNew.setCtrTotaGeneral(0.00);
            objetoNew.setCtrValida("N");
        //Genera numero por año fiscal, por bodega y por tipo de articulo
            //obtiene anio actual(perido fiscal)
            final Calendar now = Calendar.getInstance();
            String pFiscal = String.valueOf(now.get(Calendar.YEAR));

            int numeroDoc = numeroDoc(pFiscal,artId,bodega.getBodId(),tipo);
            if (numeroDoc != 0) {
                objetoNew.setCtrNumero(BigInteger.valueOf(numeroDoc));
            }

            //si es egreso se graba persona que retira
            if (retira != null) {
                objetoNew.setCtrPersonaRetira(retira);
            }

            //tipo transaccion
            objetoNew.setCtrTipo(tipo);
            //tipo de articulo
            if (artId != null) {
                objetoNew.setTipoArticulo(artId);
                //obtiene el encargado de cada tipo de articulo
                CatArticulo objArt = catArticuloFacade.find(artId);
                if (objArt != null && objArt.getEmlId() != null) {
                    String encargado = regEmpleadoFacade.nombreEmpleado(objArt.getEmlId());
                    objetoNew.setCtrEncargado(encargado);
                }
            }
            create(objetoNew);

        }
             else{
             //existe cabecera con la clave, retornar cabecera.
                 if(isSiglas && isClaveTabla ){
                objetoNew = objetoByClaveTablaTipo(claveTabla, siglasTabla);
                 }
                 else{
                 objetoNew = objetoByClaveTablaTipo(tipo, artId, bodega.getBodId(), estado);
                 }
             }
        }
      
      
     
        else{
  //no existe bodega objeto null;
      
        }

        return objetoNew;
    }
    
    @Override
    public void editarObjeto(InvCabTransaccion objeto, String valor) {
        InvCabTransaccion objetoEdit = new InvCabTransaccion();
        boolean isValor = valor != null && valor.trim().length() > 0 && !(valor.trim().toLowerCase().compareTo("null") == 0);
        if (objeto.getCtrId() != null && isValor) {
            objetoEdit = find(objeto.getCtrId());
            if (objeto.getTipoArticulo() != null && valor.equalsIgnoreCase("TA")) {
                //tipo de articulo
                objetoEdit.setTipoArticulo(objeto.getTipoArticulo());
            }
            if (objeto.getCatBodega() != null && valor.equalsIgnoreCase("BO")) {
                objetoEdit.setCatBodega(objeto.getCatBodega());
            }
            if (objeto.getCtrClavetabla() != null && valor.equalsIgnoreCase("CT")) {
                objetoEdit.setCtrClavetabla(objeto.getCtrClavetabla());
            }
            if (objeto.getCtrSiglastabla() != null && valor.equalsIgnoreCase("SI")) {
                objetoEdit.setCtrSiglastabla(objeto.getCtrSiglastabla());
            }
            if (objeto.getCtrValida() != null && valor.equalsIgnoreCase("VA")) {
                objetoEdit.setCtrValida(objeto.getCtrValida());
            }
            if (objeto.getCtrEncargado() != null && valor.equalsIgnoreCase("EN")) {
                objetoEdit.setCtrEncargado(objeto.getCtrEncargado());
            }
            if (objeto.getCtrEstado() != null && valor.equalsIgnoreCase("ES")) {
                boolean ctrEditEstado = verificaEstadoHijos(objetoEdit.getCtrId(), "P");
                if (ctrEditEstado) {
                    objetoEdit.setCtrEstado(objeto.getCtrEstado());
                    //actualiza la fecha
                    objetoEdit.setCtrFecha(new Date());
                    //verifica si e spor orden de compra
                    if(objetoEdit.getCtrTipo().equalsIgnoreCase("IF")){
                    //modificar estado de orden de compra
                      if(objetoEdit.getCtrClavetabla() != null && objetoEdit.getCtrSiglastabla() != null && objetoEdit.getCtrSiglastabla().equalsIgnoreCase("OC")){
                      regCabordencompraFacade.modificaEstadoCabDet(objetoEdit.getCtrClavetabla(), objetoEdit.getCtrEstado());
                      }  
                    }
                   
                }
            }

            if (objeto.getCtrNoRef() != null && valor.equalsIgnoreCase("NR")) {
                objetoEdit.setCtrNoRef(objeto.getCtrNoRef());
            }
            if (objeto.getCtrPersonaRetira() != null && valor.equalsIgnoreCase("PR")) {
                objetoEdit.setCtrPersonaRetira(objeto.getCtrPersonaRetira());
            }

            if (objeto.getCtrTipo() != null && valor.equalsIgnoreCase("TI")) {
                objetoEdit.setCtrTipo(objeto.getCtrTipo());
            }
            if (valor.equalsIgnoreCase("AR") && objeto.getCtrArea() != null && objeto.getCatBodegaArea() != null ) {
                objetoEdit.setCtrArea(objeto.getCtrArea());
                if(objeto.getCatBodegaArea().getEmlId() != null){
                //obtiene el encragado del area.
                String encargado = regEmpleadoFacade.nombreEmpleado(objeto.getCatBodegaArea().getEmlId());
               objetoEdit.setCtrEncargArea(encargado);
                }
            }
            if (valor.equalsIgnoreCase("TO")) {
                Double vTotal = invDetTransaccionFacade.sumaDetTotal(objeto.getCtrId(), valor);
                if (vTotal != null) {
                    objetoEdit.setCtrTotal(vTotal);
                    //modificar valores de total general
                    actualizaValorTotal(objeto.getCtrId());
                }
            }
             if (valor.equalsIgnoreCase("DE")) {
                Double vDescTotal = invDetTransaccionFacade.sumaDetTotal(objeto.getCtrId(), valor);
                if (vDescTotal != null) {
                    objetoEdit.setCtrTotalDesc(vDescTotal);
                    //modificar valores de total general
                    actualizaValorTotal(objeto.getCtrId());
                }
            }
              if (valor.equalsIgnoreCase("IV")) {
                Double vIvaTotal = invDetTransaccionFacade.sumaDetTotal(objeto.getCtrId(), valor);
                if (vIvaTotal != null) {
                    objetoEdit.setCtrTotalIva(vIvaTotal);
                    //modificar valores de total general
                    actualizaValorTotal(objeto.getCtrId());
                }
            }
            if (objeto.getCtrObservacion() != null && valor.equalsIgnoreCase("OB")) {
                objetoEdit.setCtrObservacion(objeto.getCtrObservacion());
            }
            if (objeto.getCtrFechaDoc() != null && valor.equalsIgnoreCase("FD")) {
                objetoEdit.setCtrFechaDoc(objeto.getCtrFechaDoc());
            }
            if (objeto.getCtrFechaDoc() != null && valor.equalsIgnoreCase("PR")) {
                objetoEdit.setCtrPersonaRetira(objeto.getCtrPersonaRetira());
            }
            if (objeto.getCtrValida() != null && valor.equalsIgnoreCase("VA")) {
                objetoEdit.setCtrValida(objeto.getCtrValida());
            }

        }

        edit(objetoEdit);

    }
    @Override
    public int numeroDoc(String aFiscal, BigInteger tipoArt, BigInteger bodId, String tipoTrans) {
        //OBTIENE NUMERO POR AFISACL, TIPO DE ARTICULO, BODEGA, Y TIPO DE TRANSACCION
        int numero = 0;
        final StringBuilder SQLNative = new StringBuilder();
        SQLNative.append(" SELECT COUNT(*) ");
        SQLNative.append("   FROM INV_CABTRANSACCION CT ");
        SQLNative.append("   WHERE TO_CHAR (CT.CTR_FECHA,'YYYY') = '").append(aFiscal).append("' ");
        SQLNative.append("   AND CT.BOD_ID = ").append(bodId);
        SQLNative.append("   AND CT.TIPO_ARTICULO = ").append(tipoArt);
         SQLNative.append("  AND CT.CTR_TIPO = '").append(tipoTrans).append( "' " );
        final Query query = getEntityManager().createNativeQuery(SQLNative.toString());
        final Object obj = query.getSingleResult();
        final BigDecimal valor = (BigDecimal) (obj);
        if (valor != null) {
            numero = valor.intValue() + 1;
        }

        return numero;

    }
    @Override
    public boolean verificaDuplicadoCab(BigInteger claveTabla,String siglaTabla){
        boolean ctrInserta = false;
        final StringBuilder SQLNative = new StringBuilder();
        SQLNative.append(" SELECT COUNT(*) ");
        SQLNative.append("   FROM INV_CABTRANSACCION CT ");
        SQLNative.append("   WHERE CT.CTR_CLAVETABLA = ").append(claveTabla);
         SQLNative.append("  AND CT.CTR_SIGLASTABLA = '").append(siglaTabla).append( "' " );
        SQLNative.append(" AND CT.CTR_ESTADO not in ('I') ");
        final Query query = getEntityManager().createNativeQuery(SQLNative.toString());
        final Object obj = query.getSingleResult();
        final BigDecimal valor = (BigDecimal) (obj);
        if (valor.intValue() == 0) {
           ctrInserta = true;
        }

        return ctrInserta;

    
    }
    @Override
     public boolean verificaDuplicadoCab(BigInteger clavetabla, String siglaTabla, String tipoTrans, BigInteger idBod, BigInteger tipoArt){
        boolean ctrInserta = false;
        boolean isclave = clavetabla != null;
        boolean isBodega = idBod != null;
        boolean isTipoArt = tipoArt != null;
        boolean isSiglas =  siglaTabla != null && siglaTabla.trim().length() > 0 && !(siglaTabla.trim().toLowerCase().compareTo("null") == 0);
        boolean isTipoTrans =  tipoTrans != null && tipoTrans.trim().length() > 0 && !(tipoTrans.trim().toLowerCase().compareTo("null") == 0);
        final StringBuilder SQLNative = new StringBuilder();
        SQLNative.append(" SELECT COUNT(*) ");
        SQLNative.append("   FROM INV_CABTRANSACCION CT ");
        SQLNative.append("   WHERE 1 = 1 ");
        if(isBodega){
        SQLNative.append("   AND CT.BOD_ID = ").append(idBod);
        SQLNative.append(" AND CT.CTR_ESTADO not in ('I','F') ");
        }
        if(isclave){
        SQLNative.append("   AND CT.CTR_CLAVETABLA = ").append(clavetabla);
        }
        if(isSiglas){
        SQLNative.append("  AND CT.CTR_SIGLASTABLA = '").append(siglaTabla).append( "' " );
        }
        if(isTipoTrans){
        SQLNative.append("  AND CT.CTR_TIPO = '").append(tipoTrans).append( "' " );
        }
        if(isTipoArt){
        SQLNative.append("  AND CT.TIPO_ARTICULO = ").append(tipoArt);
        }
        
        final Query query = getEntityManager().createNativeQuery(SQLNative.toString());
        final Object obj = query.getSingleResult();
        final BigDecimal valor = (BigDecimal) (obj);
        if (valor.intValue() == 0) {
           ctrInserta = true;
        }

        return ctrInserta;

    
    }
    
    @Override
    public List<InvCabTransaccion> listAllByParam(BigInteger noDoc, String estado, String noRef, BigInteger noOComp, String tipo,String fechaIni) {
        final StringBuilder SQLNative = new StringBuilder();
        boolean isNoDoc = noDoc != null;
        boolean isNoRef = noRef != null;
        boolean isNoOrden = noOComp != null;
         boolean isTipo = tipo != null && tipo.trim().length() > 0 && !(tipo.trim().toLowerCase().compareTo("null") == 0);
        boolean isEstado = estado != null && estado.trim().length() > 0 && !(estado.trim().toLowerCase().compareTo("null") == 0);
        SQLNative.append("SELECT CT.CTR_ID,CTR_TIPO,CT.CTR_NUMERO,CT.CTR_CLAVETABLA,CT.CTR_SIGLASTABLA,CT.CTR_ESTADO,CT.CTR_FECHA,CT.CTR_TOTAL, CT.CTR_OBSERVACION, CT.CTR_FECHADOC, CT.CTR_VALIDA, CT.BOD_ID ");
        SQLNative.append("   FROM INV_CABTRANSACCION CT ");
        SQLNative.append("   LEFT JOIN REG_CABORDENCOMPRA CO ON CT.CTR_CLAVETABLA = CO.COC_ID ");
        SQLNative.append("   WHERE 1=1 ");
        if (isNoDoc || isNoRef || isNoOrden) {
             if (isNoDoc) {
                 //Buscar por numero de ingreso
                   SQLNative.append("   AND CT.CTR_NUMERO =  ").append(noDoc);
            }
            if (isNoRef) {
                //Buscar por numero de referencia
              SQLNative.append("   AND CT.CTR_NOREF = '").append(noRef).append("' ");
            }
            if (isNoOrden) {
                //Buscar por numero de solicitud de orden de compra
              SQLNative.append("   AND CO.COC_NUMSOLCOMPRA =  ").append(noOComp);
            }
            if(isTipo){
            SQLNative.append(" AND CT.CTR_TIPO =  '").append(tipo).append("' ");
            }
           
            if (isEstado) {
                SQLNative.append(" AND CT.CTR_ESTADO =  '").append(estado).append("' ");
            }

        } else {
            SQLNative.append(" AND 1=2   ");

        }
        final Query query = em.createNativeQuery(SQLNative.toString());
        final List<Object[]> datos = query.getResultList();
        final List<InvCabTransaccion> datosRetorno = new ArrayList<>();
        for (final Object[] obj : datos) {
            int a = 0;
            final InvCabTransaccion inv = new InvCabTransaccion();
            inv.setCtrId(new BigInteger(obj[a].toString()));
            a++;
            if (obj[a] != null) {
                inv.setCtrTipo(((String) obj[a]));
            }
            a++;
            if (obj[a] != null) {
                inv.setCtrNumero(new BigInteger(obj[a].toString()));
            }
            a++;
            if (obj[a] != null) {
                inv.setCtrClavetabla(new BigInteger(obj[a].toString()));
            }
            a++;
            if (obj[a] != null) {
                inv.setCtrSiglastabla(((String) obj[a]));
            }
            a++;
            if (obj[a] != null) {
                inv.setCtrEstado(((String) obj[a]));
            }
            a++;
            if (obj[a] != null) {
                inv.setCtrFecha(((Date) obj[a]));
            }
            a++;
            if (obj[a] != null) {
                inv.setCtrTotal(((BigDecimal) obj[a]).doubleValue());
            }
            a++;
            if (obj[a] != null) {
                inv.setCtrObservacion(((String) obj[a]));
            }
            a++;
                 if (obj[a] != null) {
                inv.setCtrFechaDoc(((Date) obj[a]));
            }
            a++;
            if(obj[a] != null){
             inv.setCtrValida(((String) obj[a]));}
            a++;
            
            inv.setCatBodega(new CatBodega());
            if (obj[a] != null) {
                inv.setCatBodega(objCatBodega(new BigInteger(obj[a].toString())));
            }

            datosRetorno.add(inv);
        }

        return datosRetorno;
    }
    
    @Override
     public boolean verificaEstadoHijos(BigInteger claveCab, String estado){
        boolean ctrEdit = false;
        final StringBuilder SQLNative = new StringBuilder();
        SQLNative.append(" SELECT COUNT(*) ");
        SQLNative.append("   FROM INV_DETTRANSACCION DT ");
        SQLNative.append("   WHERE DT.CTR_ID = ").append(claveCab);
        SQLNative.append(" AND DT.DTR_ESTADO =  '").append(estado).append("' ");
        final Query query = getEntityManager().createNativeQuery(SQLNative.toString());
        final Object obj = query.getSingleResult();
        final BigDecimal valor = (BigDecimal) (obj);
        if (valor.intValue() == 0) {
           ctrEdit = true;
        }

        return ctrEdit;

    
    }
     
     
    @Override
     public boolean validaInactiva(BigInteger claveCab, String estado, BigInteger exiId){
         //pendiente regla del negocio
        boolean ctrEdit = false;
        final StringBuilder SQLNative = new StringBuilder();
        SQLNative.append(" SELECT COUNT(*) ");
        SQLNative.append("   FROM INV_DETTRANSACCION DT ");
        SQLNative.append("   LEFT JOIN INV_CABTRANSACCION C on DT.CTR_ID = C.CTR_ID ");        
        SQLNative.append("   WHERE DT.CTR_ID NOT IN  ").append(claveCab);
        SQLNative.append("   AND DT.EXI_ID =  ").append(exiId);
        SQLNative.append("   AND C.CTR_TIPO IN ('ET','EV') ");
        SQLNative.append(" AND DT.DTR_ESTADO =  '").append(estado).append("' ");
        final Query query = getEntityManager().createNativeQuery(SQLNative.toString());
        final Object obj = query.getSingleResult();
        final BigDecimal valor = (BigDecimal) (obj);
        if (valor.intValue() == 0) {
           ctrEdit = true;
        }

        return ctrEdit;

    
    }
     
    @Override
    public String inactivaCabDet(BigInteger ctrId) {
        String mensaje = "";
         boolean isPeFiscal = false;
        boolean isEgreso = false;
        InvExistenciaBodega existencia = null;
        InvLoteArticulo lote = new InvLoteArticulo();
        int contador = 0;
        if (ctrId != null) {
            //obtiene cabecera
            InvCabTransaccion objetoIncativa = find(ctrId);
            if (objetoIncativa != null) {
                //verifica si tiene hijos la cabecera
                 List<InvDetTransaccion> hijos = invDetTransaccionFacade.listAllByParam(ctrId, null);
                 if(hijos.size() > 0){
                 //verificar el periodo fiscal
                final Calendar now = Calendar.getInstance();
                String pFiscalActual = String.valueOf(now.get(Calendar.YEAR));
                
                Format formatter = new SimpleDateFormat("yyyy");
                String pFiscalCabecera = formatter.format(objetoIncativa.getCtrFecha());
                
                if (pFiscalActual.equalsIgnoreCase(pFiscalCabecera)) {
                    isPeFiscal = true;
                    if (isPeFiscal) {
                        //verifica que los hijos no hayan tenido egreso
                         hijos = invDetTransaccionFacade.listAllByParam(ctrId, null);
                        for (InvDetTransaccion det : hijos) {
                            if (det.getInvExistenciaBodega().getExiId() != null) {
                                isEgreso = validaInactiva(ctrId, "F", det.getInvExistenciaBodega().getExiId());
                                if (!isEgreso) {
                                    mensaje = "Existen egresos realizados.";
                                    break;
                                }
                                
                            }
                            
                        }
                        if (isEgreso) {

                            //edita la listas de item
                            //List<InvDetTransaccion> hijos = invDetTransaccionFacade.listAllByParam(ctrId, null);
                            for (InvDetTransaccion detalle : hijos) {
                                if (detalle.getDtrId() != null) {
                                    if (detalle.getInvExistenciaBodega() != null) {
                                     existencia = detalle.getInvExistenciaBodega();}
                                    
                                    detalle = invDetTransaccionFacade.find(detalle.getDtrId());
                                    detalle.setDtrEstado("I");
                                    invDetTransaccionFacade.edit(detalle);
                                    //actualiza valores anteriores del objeto existencia
                                    if (existencia != null) {
                                        if(detalle.getExiExistencia() != null && detalle.getExiCostoProm() != null ){
                                        existencia.setExiExistencia(detalle.getExiExistencia());
                                        existencia.setExiCostoprom(detalle.getExiCostoProm());
                                         invExistenciaBodegaFacade.edit(existencia);
                                        }
                                       
                                    }
                                   
                                    if(objetoIncativa.getCtrTipo().equalsIgnoreCase("IV")){
                                        //ingreso por devolucion
                                         //inactiva liquidacion
                                        regDetliquidacionFacade.modificaEstado(detalle.getExiId(), objetoIncativa.getCtrClavetabla(), objetoIncativa.getCtrSiglastabla(), "I");
                                    }
                                    
                                    //verificar si existe lote
                                    if(detalle.getLotId() != null){
                                             lote = invLoteArticuloFacade.find(detalle.getLotId());                                    
                                    }
                                    if(objetoIncativa.getCtrTipo().equalsIgnoreCase("IF") || objetoIncativa.getCtrTipo().equalsIgnoreCase("IR") || objetoIncativa.getCtrTipo().equalsIgnoreCase("IF")){
                                    //es ingreso inactiva lote
                                        lote.setLotEstado("I");
                                            invLoteArticuloFacade.edit(lote);
                          
                                    }
                                    else{
                                     if(detalle.getLotId() != null){
                                    //si es egreso deshacer el descargo
                                      BigInteger cantidad = lote.getLotCantidad().add(lote.getLotCantegreso());
                                      lote.setLotCantidad(cantidad);
                                      invLoteArticuloFacade.edit(lote);
                                         
                                    }
                                    }
                                    
                                    
                                }
                            }
                            objetoIncativa.setCtrEstado("I");
                            edit(objetoIncativa);
                            mensaje = "Los registros se inactivaron satisfactorimente.";
                            
                        }

                        //envia mensaje
                    } else {
                        mensaje = "Periodo fiscal diferentes.";
                    }
                }
                 
                 }
                 else{
                 //solo existe cabecera inactivar cabecera
                      objetoIncativa.setCtrEstado("I");
                      edit(objetoIncativa);
                      mensaje = "Los registros se inactivaron satisfactorimente.";
                 }
         
            } else {
                mensaje = "Objeto Cabecera es nulo";
            }
        } else {
            mensaje = "Clave Cabecera nula";
        }
        return mensaje;
    }
    @Override
    public InvCabTransaccion objetoByClaveTablaTipo(BigInteger claveTabla, String sigla) {

        InvCabTransaccion objeto = null;
        Boolean isClave = claveTabla != null;
        boolean isSigla = sigla != null && sigla.trim().length() > 0 && !(sigla.trim().toLowerCase().compareTo("null") == 0);
        if (isClave && isSigla) {

            final Query query = em.createQuery("Select object (c) from InvCabTransaccion c where c.ctrClavetabla = :claveTabla and c.ctrSiglastabla = :sigla  ");
            query.setParameter("claveTabla", claveTabla);
            query.setParameter("sigla", sigla);
            query.setHint("eclipselink.refresh", true);
            final List<InvCabTransaccion> result = query.getResultList();
            if (result.size() > 0) {
                objeto = result.get(0);
            }
        }
        return objeto;
    }
    
    @Override
    public InvCabTransaccion objetoByClaveTablaTipo(String tipoTrans, BigInteger tipoArt, BigInteger idBod, String estado) {

        InvCabTransaccion objeto = null;
        boolean isTipoArt = tipoArt != null;
        boolean isBod = idBod != null;
        boolean isEstado = estado != null && estado.trim().length() > 0 && !(estado.trim().toLowerCase().compareTo("null") == 0);
        boolean istipoTrans = tipoTrans != null && tipoTrans.trim().length() > 0 && !(tipoTrans.trim().toLowerCase().compareTo("null") == 0);
        if (isBod && isTipoArt && isEstado && istipoTrans) {

            final Query query = em.createQuery("Select object (c) from InvCabTransaccion c where c.ctrEstado = :estado and c.ctrTipo = :tipoTrans and c.tipoArticulo = :tipoArt and c.catBodega.bodId = :idBod  ");
            query.setParameter("estado", estado);
            query.setParameter("tipoArt", tipoArt);
            query.setParameter("idBod", idBod);
            query.setParameter("tipoTrans", tipoTrans);
            query.setHint("eclipselink.refresh", true);
            final List<InvCabTransaccion> result = query.getResultList();
            if (result.size() > 0) {
                objeto = result.get(0);
            }
        }
        return objeto;
    }
    @Override
    public Double actualizaValorTotal(BigInteger clavecab) {
     //actualiza valor general de cabecera y redondea a 4 cifras decimales
        Double total = null;
        if (clavecab != null) {
            final StringBuilder SQLNative = new StringBuilder();
            SQLNative.append(" UPDATE INV_CABTRANSACCION C SET C.CTR_TOTAGENERAL = ROUND((C.CTR_TOTAL - C.CTR_TOTALDESC) + C.CTR_TOTALIVA,4) ");
            SQLNative.append(" WHERE ");
            SQLNative.append("   C.CTR_ID = ").append(clavecab);
            final Query query = getEntityManager().createNativeQuery(SQLNative.toString());
            query.executeUpdate();
           
        }

        return total;

    }
    
    @Override
    public InvCabTransaccion objetoByTransferencia(String tipoTrans, BigInteger idBodArea, String estado, String fecha, BigInteger noDoc) {

        InvCabTransaccion objeto = null;
        boolean isBodArea = idBodArea != null;
        boolean isNoDoc = noDoc != null;
         boolean isFecha = fecha != null && fecha.trim().length() > 0 && !(fecha.trim().toLowerCase().compareTo("null") == 0);
        boolean isEstado = estado != null && estado.trim().length() > 0 && !(estado.trim().toLowerCase().compareTo("null") == 0);
        boolean istipoTrans = tipoTrans != null && tipoTrans.trim().length() > 0 && !(tipoTrans.trim().toLowerCase().compareTo("null") == 0);
        final StringBuilder sql = new StringBuilder();
        sql.append("SELECT * FROM INV_CABTRANSACCION CT   ");
        sql.append("    WHERE 1=1  ");

        if (istipoTrans && isBodArea) {
            if (istipoTrans) {
                sql.append(" AND CT.CTR_TIPO =  '").append(tipoTrans).append("' ");
            }
            if (isBodArea) {
                sql.append("    AND CT.CTR_AREA =").append(idBodArea);
            }
            if (isNoDoc) {
                sql.append("   AND CT.CTR_NUMERO =  ").append(noDoc);

            }
            if (isEstado) {

                sql.append(" AND CT.CTR_ESTADO =  '").append(estado).append("' ");
            }
            if (isFecha) {
                sql.append(" AND TRUNC (CT.CTR_FECHA) = to_date('").append(fecha).append("','dd/mm/yyyy')");
            }

            final List<InvCabTransaccion> result = em.createNativeQuery(sql.toString(), InvCabTransaccion.class)
                    .setHint("eclipselink.refresh", true)
                    .getResultList();
            if (result.size() > 0) {
                objeto = result.get(0);
                objeto.setCatBodegaArea(objCatBodega(result.get(0).getCtrArea()));
                objeto.setCatArticulo(objCatArticulo(result.get(0).getTipoArticulo()));
            }

        } else {
            sql.append("    WHERE 1=2  ");

        }
        return objeto;
    }

    @Override
    public InvCabTransaccion objetoByParametros(BigInteger tipoArt, BigInteger bodId, String tipoTrans,
            BigInteger idBodArea, String estado, String fecha, BigInteger noDoc,BigInteger claveTabla, String sigla, String noRef) {

        InvCabTransaccion objeto = null;
        boolean isBodArea = idBodArea != null;
        boolean isTipoArt = tipoArt != null;
        boolean isBod = bodId != null;
        boolean isNoDoc = noDoc != null;
        boolean isFecha = fecha != null && fecha.trim().length() > 0 && !(fecha.trim().toLowerCase().compareTo("null") == 0);
        boolean isEstado = estado != null && estado.trim().length() > 0 && !(estado.trim().toLowerCase().compareTo("null") == 0);
        boolean istipoTrans = tipoTrans != null && tipoTrans.trim().length() > 0 && !(tipoTrans.trim().toLowerCase().compareTo("null") == 0);
        boolean isClave = claveTabla != null;
        boolean isSigla = sigla != null && sigla.trim().length() > 0 && !(sigla.trim().toLowerCase().compareTo("null") == 0);
        boolean isnoRef = noRef != null && noRef.trim().length() > 0 && !(noRef.trim().toLowerCase().compareTo("null") == 0);
        
        final StringBuilder sql = new StringBuilder();
        sql.append("SELECT * FROM INV_CABTRANSACCION CT   ");
        sql.append("    WHERE 1=1  ");

        if (istipoTrans && isTipoArt && isBod && isFecha && isNoDoc) {
            if (istipoTrans) {
                sql.append(" AND CT.CTR_TIPO =  '").append(tipoTrans).append("' ");
            }
            if (isTipoArt) {
                sql.append(" AND CT.TIPO_ARTICULO =  '").append(tipoArt).append("' ");
            }
            if (isBod) {
                sql.append("    AND CT.BOD_ID =").append(bodId);
            }
            if (isBodArea) {
                sql.append("    AND CT.CTR_AREA =").append(idBodArea);
            }
            if (isNoDoc) {
                sql.append("   AND CT.CTR_NUMERO =  ").append(noDoc);

            }
            if (isClave) {
                sql.append("   AND CT.CTR_CLAVETABLA =  ").append(claveTabla);

            }
            if (isSigla) {
                sql.append(" AND CT.CTR_SIGLASTABLA =  '").append(sigla).append("' ");
            }
            if (isnoRef) {
                sql.append(" AND CT.CTR_NOREF =  '").append(noRef).append("' ");
            }
            if (isEstado) {

                sql.append(" AND CT.CTR_ESTADO =  '").append(estado).append("' ");
            }
            if (isFecha) {
                sql.append(" AND TRUNC (CT.CTR_FECHA) = to_date('").append(fecha).append("','dd/mm/yyyy')");
            }

            final List<InvCabTransaccion> result = em.createNativeQuery(sql.toString(), InvCabTransaccion.class)
                    .setHint("eclipselink.refresh", true)
                    .getResultList();
            if (result.size() > 0) {
                objeto = result.get(0);
                objeto.setCatBodegaArea(objCatBodega(result.get(0).getCtrArea()));
                objeto.setCatArticulo(objCatArticulo(result.get(0).getTipoArticulo()));
            }

        } else {
            sql.append("    WHERE 1=2  ");

        }
        return objeto;
    }

}
