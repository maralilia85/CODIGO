/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.mil.armada.inv.servicios.registro;

import ec.mil.armada.inv.modelo.inventario.InvExistenciaBodega;
import ec.mil.armada.inv.modelo.registro.RegCabliquidacion;
import ec.mil.armada.inv.modelo.registro.RegDetliquidacion;
import ec.mil.armada.inv.remotos.catalogo.CatArticuloFacadeRemote;
import ec.mil.armada.inv.remotos.inventario.InvExistenciaBodegaFacadeRemote;
import ec.mil.armada.inv.remotos.registro.RegCabliquidacionFacadeRemote;
import ec.mil.armada.inv.servicios.abstractFacade.AbstractFacade;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
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
public class RegDetliquidacionFacade extends AbstractFacade<RegDetliquidacion> implements ec.mil.armada.inv.remotos.registro.RegDetliquidacionFacadeRemote {
    @EJB
    private CatArticuloFacadeRemote catArticuloFacade;
    @EJB
    private InvExistenciaBodegaFacadeRemote invExistenciaBodegaFacade;
    @EJB
    private RegCabliquidacionFacadeRemote regCabliquidacionFacade;
    @PersistenceContext(unitName = "ec.mil.armada_Inv-Servicios_ejb_1.0PU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public RegDetliquidacionFacade() {
        super(RegDetliquidacion.class);
    }
    
    @Override
    public RegDetliquidacion objRegDetLiquidacion(BigInteger claveCabecera, String estado, BigInteger exiId){
    
        RegDetliquidacion objeto = null;

        final Query query = em.createQuery("Select object (dl) from RegDetliquidacion dl  where dl.cliId = :claveCabecera and dl.dliEstado = :estado and dl.exiId = :exiId  ");
        query.setParameter("claveCabecera", claveCabecera);
        query.setParameter("estado", estado);
        query.setParameter("exiId", exiId);
        query.setHint("eclipselink.refresh", true);
        final List<RegDetliquidacion> result = query.getResultList();
        if (result.size() > 0) {
            objeto = result.get(0);
        }
        return objeto;
    } 
    
    
    @Override
    public void crearRegDetLiquidacion(BigInteger claveCab, BigInteger exiId, BigInteger cantidad, String estado, Double valor){
    RegDetliquidacion objNew = null;
     boolean isClaveCab = claveCab != null;
     boolean isExiId = exiId != null;
     boolean isCantidad = cantidad != null;
     boolean isEstado =  estado != null && estado.trim().length() > 0 && !(estado.trim().toLowerCase().compareTo("null") == 0);
    if(isClaveCab && isExiId && isCantidad)
    {
        boolean ctrInsert = verificaDuplicadoCab(claveCab, exiId, estado);
        if(ctrInsert){
        //crear detalle
            objNew = new RegDetliquidacion();
            objNew.setCliId(claveCab);
            objNew.setExiId(exiId);
            objNew.setDliValor(valor);
            objNew.setDliCantidad(cantidad);
            objNew.setDliEstado(estado);
            objNew.setDliFecha(new Date());
            if(valor != null && cantidad != null){
            Double total = redondear(cantidad.intValue() * valor,4);
            if(total != null){
            objNew.setDliVTotal(total);
            //actualizar valir iva si el articulo es con iva
            objNew.setDliTIva(valor);
            }
            }
            create(objNew);
        }
            }
    else{
    //no se crea
    }
    
    }
    
    @Override
    public boolean verificaDuplicadoCab(BigInteger claveCab, BigInteger exiId, String estado){
        boolean ctrInserta = false;
        final StringBuilder SQLNative = new StringBuilder();
        SQLNative.append(" SELECT COUNT(*) ");
        SQLNative.append("    FROM REG_DETLIQUIDACION DL ");
        SQLNative.append("   WHERE DL.CLI_ID = ").append(claveCab);
         SQLNative.append("  AND DL.EXI_ID = ").append(exiId);
        SQLNative.append(" AND DL.DLI_ESTADO = '").append(estado).append( "' " );
        final Query query = getEntityManager().createNativeQuery(SQLNative.toString());
        final Object obj = query.getSingleResult();
        final BigDecimal valor = (BigDecimal) (obj);
        if (valor.intValue() == 0) {
           ctrInserta = true;
        }

        return ctrInserta;

    
    }
    
    @Override
    public List<RegDetliquidacion>  listRegDetliquidacionByParam(final BigInteger claveCab, final BigInteger exiId, final String estado) {
        final StringBuilder SQLNative = new StringBuilder();
        SQLNative.append("SELECT DL.DLI_ID ,  DL.DLI_CANTIDAD, DL.DLI_ESTADO, DL.DLI_FECHA,  DL.DLI_VALOR, DL.DLI_VTOTAL, DL.DLI_TIVA,  ");
        SQLNative.append("   DL.EXI_ID,DL.CLI_ID");
                SQLNative.append("  FROM REG_DETLIQUIDACION DL ");

        
        SQLNative.append("   WHERE 1=1 ");
        boolean isClaveCab = claveCab != null;
        boolean isExiId = exiId != null;
        boolean isEstado = estado != null && estado.trim().length() > 0 && !(estado.trim().toLowerCase().compareTo("null") == 0);
        //validar al menos que se cumpla una condicion para ejecutar el sql
        if (isClaveCab) {
             SQLNative.append("   AND DL.CLI_ID = ").append(claveCab);
             if(isExiId){
              SQLNative.append("   AND DL.EXI_ID = ").append(exiId);
             }
            
            if (isEstado) {
                SQLNative.append(" AND DL.DLI_ESTADO =  '").append(estado).append("' ");
            }

            SQLNative.append("  ORDER BY DL.DLI_ID  ");
        } else {
            SQLNative.append(" AND 1=2   ");

        }

        final Query query = em.createNativeQuery(SQLNative.toString());
        final List<Object[]> datos = query.getResultList();
        final List<RegDetliquidacion> datosRetorno = new ArrayList<>();
        for (final Object[] obj : datos) {
            int a = 0;
            final RegDetliquidacion cat = new RegDetliquidacion();
            cat.setDliId(new BigInteger(obj[a].toString()));
            a++;
             if(obj[a] != null){
            cat.setDliCantidad(new BigInteger(obj[a].toString()));}
            a++;
            if(obj[a] != null){
            cat.setDliEstado((String) obj[a]);}
            a++;
            if(obj[a] != null){
            cat.setDliFecha((Date) obj[a]);}
            a++;
            if(obj[a] != null){
            cat.setDliValor(((BigDecimal) obj[a]).doubleValue());
            }
            a++;
            if(obj[a] != null){
            cat.setDliVTotal(((BigDecimal) obj[a]).doubleValue());
            }
            a++;
            if(obj[a] != null){
            cat.setDliTIva(((BigDecimal) obj[a]).doubleValue());
            }
            a++;
            
            if(obj[a] != null){
            cat.setExiId(new BigInteger(obj[a].toString()));
            cat.setInvExistenciaBodega(objInvExistenciaBodega(new BigInteger(obj[a].toString())));
            }
            a++;
            if(obj[a] != null){
            cat.setRegCabLiquidacion(new RegCabliquidacion());
            cat.setRegCabLiquidacion(objRegCabliquidacion(new BigInteger(obj[a].toString())));
            }
            
     
            
            datosRetorno.add(cat);
        }
        return datosRetorno;
    }
    
    @Override
  public InvExistenciaBodega objInvExistenciaBodega(BigInteger exiId) {

        InvExistenciaBodega objeto = null;

        final Query query = em.createQuery("Select object (e) from InvExistenciaBodega e where e.exiId = :exiId  ");
        query.setParameter("exiId", exiId);
        query.setHint("eclipselink.refresh", true);
        final List<InvExistenciaBodega> result = query.getResultList();
        if (result.size() > 0) {
            objeto = result.get(0);
        }
        return objeto;
    }  
  
    @Override
  public RegCabliquidacion objRegCabliquidacion(BigInteger cliId) {

        RegCabliquidacion objeto = null;

        final Query query = em.createQuery("Select object (c) from RegCabliquidacion c where c.cliId = :cliId  ");
        query.setParameter("cliId", cliId);
        query.setHint("eclipselink.refresh", true);
        final List<RegCabliquidacion> result = query.getResultList();
        if (result.size() > 0) {
            objeto = result.get(0);
        }
        return objeto;
    }  
  
    @Override
    public RegDetliquidacion objRegDetliquidacion(BigInteger exiId, String estado, BigInteger cabecera) {

        RegDetliquidacion objeto = null;

        final Query query = em.createQuery("Select object (d) from RegDetliquidacion d where d.exiId = :exiId and d.cliId = :cabecera and d.dliEstado = :estado  ");
        query.setParameter("exiId", exiId);
        query.setParameter("estado", estado);
        query.setParameter("cabecera", cabecera);
        query.setHint("eclipselink.refresh", true);
        final List<RegDetliquidacion> result = query.getResultList();
        if (result.size() > 0) {
            objeto = result.get(0);
        }
        return objeto;
    } 
    
    @Override
    public boolean verificaHijosCab(BigInteger claveTabla,String siglaTabla, String estado){
        boolean ctrInserta = false;
        final StringBuilder SQLNative = new StringBuilder();
        SQLNative.append(" SELECT COUNT(*) ");
        SQLNative.append("    FROM REG_DETLIQUIDACION DL WHERE ");
            SQLNative.append("    DL.CLI_ID = (SELECT CL.CLI_ID FROM REG_CABLIQUIDACION CL ");
            SQLNative.append("  WHERE CL.CLI_SIGLATABLA = '").append(siglaTabla).append("' ");
            SQLNative.append("   AND CL.CLI_CLAVETABLA = ").append(claveTabla);
            SQLNative.append(" AND CL.CLI_ESTADO = '").append(estado).append("' ) ");
         
            SQLNative.append(" AND DL.DLI_ESTADO = '").append(estado).append("' ");
        final Query query = getEntityManager().createNativeQuery(SQLNative.toString());
        final Object obj = query.getSingleResult();
        final BigDecimal valor = (BigDecimal) (obj);
        if (valor.intValue() == 0) {
           ctrInserta = true;
        }

        return ctrInserta;

    
    }
    

    @Override
    public void modificaEstado(BigInteger exiId, BigInteger claveTabla, String siglaTabla, String estado) {
        RegDetliquidacion objetoEdit = new RegDetliquidacion();
        RegCabliquidacion objCabecera = new RegCabliquidacion();
        //obtiene el objeto hijo
        if (exiId != null) {
            objCabecera = regCabliquidacionFacade.objRegCabliquidacion(claveTabla, "G", siglaTabla);
            if (objCabecera != null) {
                objetoEdit = objRegDetliquidacion(exiId, estado, objCabecera.getCliId());
                if (objetoEdit != null) {
                    //modifica el estado del hijo
                    objetoEdit.setDliEstado(estado);
                    edit(objetoEdit);
                    //valida si la cabecera tiene pendientes
                    boolean isEdit = verificaHijosCab(claveTabla, siglaTabla, "G");
                    if (isEdit) {
                        //modifica el estado de cabecera
                        objCabecera.setCliEstado(estado);
                        regCabliquidacionFacade.edit(objCabecera);
                    }

                }
            }
        }

    }
    @Override
    public void crearRegDetLiquidacion(BigInteger claveCab, BigInteger exiId, BigInteger cantidad, String estado,
            Double valor, Double vIva, BigInteger porcDes, Double tDesc) {
        RegDetliquidacion objNew = null;
        boolean isClaveCab = claveCab != null;
        boolean isExiId = exiId != null;
        boolean isCantidad = cantidad != null;
        boolean isEstado = estado != null && estado.trim().length() > 0 && !(estado.trim().toLowerCase().compareTo("null") == 0);
        if (isClaveCab && isExiId && isCantidad) {
            boolean ctrInsert = verificaDuplicadoCab(claveCab, exiId, estado);
            if (ctrInsert) {
                //crear detalle
                objNew = new RegDetliquidacion();
                objNew.setCliId(claveCab);
                objNew.setExiId(exiId);
                objNew.setDliValor(valor);
                objNew.setDliCantidad(cantidad);
                objNew.setDliTIva(0.0);
                objNew.setDliTDesc(tDesc);
                objNew.setDliPorc(porcDes);
                objNew.setDliEstado(estado);
                objNew.setDliFecha(new Date());
                if (valor != null && cantidad != null) {
                    Double total = redondear(cantidad.intValue() * valor, 4) ;
                    
                    if (total != null) {
                        objNew.setDliVTotal(total);
                        //actualizar valir iva si el articulo es con iva
                        if (vIva != null) {
                            objNew.setDliTIva(objNew.getDliVTotal() * vIva);
                        }
                    }
                }
                create(objNew);

            }
            else{
            RegDetliquidacion objEdit = objRegDetLiquidacion(claveCab, estado, exiId);
            if(objEdit != null){
                //editar cantidades
            BigInteger cantidadnew = objEdit.getDliCantidad().add(cantidad);
            objEdit.setDliCantidad(cantidadnew);
            objEdit.setDliValor(valor);
            Double sTotal = redondear(cantidadnew.intValue() * valor, 4);
            objEdit.setDliVTotal(sTotal);
             edit(objEdit);
            }
            }
        


        } else {
            //no se crea
        }

    }

    @Override
 public Double sumaDetTotal(BigInteger clavecab,String campo) {
        //suma detalle total por cabecera clave y redondea a 4 decimales
        Double total = null;
        final StringBuilder SQLNative = new StringBuilder();
        SQLNative.append(" SELECT ");
        if(campo.equalsIgnoreCase("TO")){
         SQLNative.append(" ROUND(NVL(SUM(DL.DLI_VTOTAL), 0),4) SUBTOTAL  ");
        }
        if(campo.equalsIgnoreCase("IV")){
          SQLNative.append(" ROUND(NVL(SUM(DL.DLI_TIVA), 0),4) IVA  ");
        }
        if(campo.equalsIgnoreCase("DE")){
          SQLNative.append(" ROUND(NVL(SUM(DL.DLI_TDESC), 0),4) DESCUENTO  ");
        }
        SQLNative.append("   FROM REG_DETLIQUIDACION  DL WHERE ");
        SQLNative.append("   DL.CLI_ID = ").append(clavecab);
        final Query query = getEntityManager().createNativeQuery(SQLNative.toString());
        final Object obj = query.getSingleResult();
        final BigDecimal valor = (BigDecimal) (obj);
        if (valor != null) {
            total = valor.doubleValue();
        }

        return total;

    }
    @Override
 public void modificaEstado(BigInteger exiId, BigInteger claveTabla, String siglaTabla, String estadobsq, BigInteger cant, String estado) {
        RegDetliquidacion objetoEdit = new RegDetliquidacion();
        RegCabliquidacion objCabecera = new RegCabliquidacion();
        //obtiene el objeto hijo
        if (exiId != null) {
            objCabecera = regCabliquidacionFacade.objRegCabliquidacion(claveTabla, estadobsq, siglaTabla);
            if (objCabecera != null) {
                objetoEdit = objRegDetliquidacion(exiId, estadobsq, objCabecera.getCliId());
                if (objetoEdit != null) {
                    if(estado.equalsIgnoreCase("I")){
                    //restar cantidades
                     BigInteger restar = objetoEdit.getDliCantidad().subtract(cant);
                     objetoEdit.setDliCantidad(restar);
                     Double valor = objetoEdit.getDliValor();
                     Double sTotal = redondear(restar.intValue()*valor, 4);
                     objetoEdit.setDliVTotal(sTotal);
                     if(restar.intValue() == 0){
                     //si las cantidad son cero inactivar estado
                    objetoEdit.setDliEstado(estado);
                    edit(objetoEdit);
                     }
                    
                    }
                    //editar total el cabecera
                    regCabliquidacionFacade.editCabLiq(objCabecera, "TO");
                    
                    //valida si la cabecera tiene pendientes
                    boolean isEdit = verificaHijosCab(claveTabla, siglaTabla, estadobsq);
                    if (isEdit) {
                        //modifica el estado de cabecera
                        objCabecera.setCliEstado(estado);
                        regCabliquidacionFacade.edit(objCabecera);
                    }

                }
            }
        }

    }
    
    
}
