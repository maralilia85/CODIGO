/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.mil.armada.inv.servicios.registro;

import ec.mil.armada.inv.modelo.catalago.CatArticulo;
import ec.mil.armada.inv.modelo.catalago.CatBodega;
import ec.mil.armada.inv.modelo.catalago.CatEmpresa;
import ec.mil.armada.inv.remotos.registro.RegCabordencompraFacadeRemote;
import ec.mil.armada.inv.modelo.registro.RegCabordencompra;
import ec.mil.armada.inv.servicios.abstractFacade.AbstractFacade;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author DET-PC
 */
@Stateless
public class RegCabordencompraFacade extends AbstractFacade<RegCabordencompra> implements RegCabordencompraFacadeRemote {

    @PersistenceContext(unitName = "ec.mil.armada_Inv-Servicios_ejb_1.0PU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public RegCabordencompraFacade() {
        super(RegCabordencompra.class);
    }

    @Override
    public CatBodega objCatBodega(BigInteger bodId) {

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
    public CatEmpresa objCatEmpresa(BigInteger clave) {

        CatEmpresa objeto = null;

        final Query query = em.createQuery("Select object (p) from CatEmpresa p where p.empId = :clave  ");
        query.setParameter("clave", clave);
        query.setHint("eclipselink.refresh", true);
        final List<CatEmpresa> result = query.getResultList();
        if (result.size() > 0) {
            objeto = result.get(0);
        }
        return objeto;
    }

    @Override
    public CatArticulo objCatArticulo(BigInteger clave) {

        CatArticulo objeto = null;

        final Query query = em.createQuery("Select object (a) from CatArticulo a where a.artId = :clave  ");
        query.setParameter("clave", clave);
        query.setHint("eclipselink.refresh", true);
        final List<CatArticulo> result = query.getResultList();
        if (result.size() > 0) {
            objeto = result.get(0);
        }
        return objeto;
    }

   
    @Override
    public List<RegCabordencompra> listAllByParam(BigInteger bodId, String numero, String estado, String tipo, BigInteger tipoArtId, String fechaIni, String fechaFin, String operador) {
        final StringBuilder SQLNative = new StringBuilder();
        boolean isBodega = bodId != null;
        boolean isNumero = numero != null;
        boolean isTipoArtId = tipoArtId != null;
        boolean isEstado = estado != null && estado.trim().length() > 0 && !(estado.trim().toLowerCase().compareTo("null") == 0);
        boolean isTipo = tipo != null && tipo.trim().length() > 0 && !(tipo.trim().toLowerCase().compareTo("null") == 0);
        boolean isfechaIni = fechaIni != null && fechaIni.trim().length() > 0 && !(fechaIni.trim().toLowerCase().compareTo("null") == 0);
        boolean isfechaFin = fechaFin != null && fechaFin.trim().length() > 0 && !(fechaFin.trim().toLowerCase().compareTo("null") == 0);
        boolean isOperador = operador != null && operador.trim().length() > 0 && !(operador.trim().toLowerCase().compareTo("null") == 0);

        SQLNative.append(" SELECT  CO.COC_ID, CO.COC_NUMERO,CO.COC_ESTADO,CO.COC_FECHA ,CO.COC_TIPOORDEN ,CO.COC_OBSERVACION,  ");
        SQLNative.append("  CO.COC_NOFACTURA, CO.COC_FECHAFACTURA ,CO.COC_TOTALFACTURA,CO.COC_DESC, CO.COC_IVA, CO.COC_TOTAL, CO.COC_NUMSOLCOMPRA, ");
        SQLNative.append("    CO.BOD_ID,CO.EMP_ID, CO.COC_TIPO ");
        SQLNative.append("    FROM REG_CABORDENCOMPRA CO ");
   
  
        SQLNative.append("   WHERE 1=1 ");
        if (isBodega || isTipoArtId || isEstado || isNumero) {
            if (isBodega) {
                SQLNative.append("   AND CO.BOD_ID = ").append(bodId);

            }
            if (isTipoArtId) {
                SQLNative.append("   AND CO.COC_TIPO = ").append(bodId);

            }

            if (isEstado) {
                SQLNative.append(" AND CO.COC_ESTADO =  '").append(estado).append("' ");

            }
            if (isTipo) {
                SQLNative.append(" AND CO.COC_TIPOORDEN =  '").append(tipo).append("' ");

            }
            if (isNumero) {
                SQLNative.append("       AND  CO.COC_NOFACTURA || ' '||  CO.COC_NUMERO || ' '||  CO.COC_NUMSOLCOMPRA ");
                SQLNative.append("   LIKE '%").append(numero).append("%'");

            }
            if (isfechaIni && isOperador) {
                //Buscar por existencia
                if (operador.equalsIgnoreCase("=")) {
                    SQLNative.append(" AND TRUNC (CO.COC_FECHA) = to_date('").append(fechaIni).append("','dd/mm/yyyy')");
                }
                if (operador.equalsIgnoreCase(">=")) {
                    SQLNative.append(" AND TRUNC (CO.COC_FECHA) >= to_date('").append(fechaIni).append("','dd/mm/yyyy')");
                }
                if (operador.equalsIgnoreCase("<=")) {
                    SQLNative.append(" AND TRUNC (CO.COC_FECHA) <= to_date('").append(fechaIni).append("','dd/mm/yyyy')");
                }
                if (isfechaFin && operador.equalsIgnoreCase("ENTRE")) {
                    SQLNative.append("  AND TRUNC (CO.COC_FECHA) BETWEEN to_date('").append(fechaIni).append("','dd/mm/yyyy') and to_date('").append(fechaFin).append("','dd/mm/yyyy')");
                }

            }

        } else {
            SQLNative.append(" AND 1=2   ");

        }
        final Query query = em.createNativeQuery(SQLNative.toString());
        final List<Object[]> datos = query.getResultList();
        final List<RegCabordencompra> datosRetorno = new ArrayList<>();
        for (final Object[] obj : datos) {
            int a = 0;
            final RegCabordencompra inv = new RegCabordencompra();
            inv.setCocId(new BigInteger(obj[a].toString()));
            a++;
            if (obj[a] != null) {
                inv.setCocNumero(new BigInteger(obj[a].toString()));
            }
            a++;
            if (obj[a] != null) {
                inv.setCocEstado(((String) obj[a]));
            }
            a++;
            if (obj[a] != null) {
                inv.setCocFecha(((Date) obj[a]));
            }
            a++;
            if (obj[a] != null) {
                inv.setCocTipoorden(((String) obj[a]));
            }
            a++;
            if (obj[a] != null) {
                inv.setCocObservacion(((String) obj[a]));
            }
            a++;
            
            if (obj[a] != null) {
                inv.setCocNofactura(((String) obj[a]));
            }
            a++;
            if (obj[a] != null) {
                inv.setCocFechaFactura(((Date) obj[a]));
            }
            a++;
             if (obj[a] != null) {
                inv.setCocTotalFactura(((BigDecimal) obj[a]).doubleValue());
            }
            a++;
              if (obj[a] != null) {
                inv.setCocDesc(((BigDecimal) obj[a]).doubleValue());
            }
            a++;
              if (obj[a] != null) {
                inv.setCocIva(((BigDecimal) obj[a]).doubleValue());
            }
            a++;
              if (obj[a] != null) {
                inv.setCocTotal(((BigDecimal) obj[a]).doubleValue());
            }
            a++;
            if (obj[a] != null) {
            inv.setCocNumSolCompra(new BigInteger(obj[a].toString()));
            }
            a++;
            inv.setCatBodega(new CatBodega());
            if (obj[a] != null) {
                inv.setCatBodega(objCatBodega(new BigInteger(obj[a].toString())));
            }
            a++;
            
            inv.setCatEmpresa(new CatEmpresa());
            if (obj[a] != null) {
                inv.setCatEmpresa(objCatEmpresa(new BigInteger(obj[a].toString())));
            }
            a++;
            inv.setCatArticulo(new CatArticulo());
            if (obj[a] != null) {
                inv.setCatArticulo(objCatArticulo(new BigInteger(obj[a].toString())));
            }
            
            datosRetorno.add(inv);
        }

        return datosRetorno;
    }

    
    @Override
     public String modificaEstadoCabDet(BigInteger clavecab, String estado) {
        String mensaje = null;
        
        if (clavecab != null && estado != null) {
            RegCabordencompra objEdit = new RegCabordencompra();
            objEdit = find(clavecab);
            if (objEdit != null) {
       
                    //ACTUALIZA ESTADO DE LOS HIJOS
                    final StringBuilder SQLNative = new StringBuilder();
                    SQLNative.append(" UPDATE REG_DETORDENCOMPRA DO SET DO.DOC_ESTADO = '").append(estado).append("' ");
                    SQLNative.append(" WHERE ");
                    SQLNative.append(" DO.COC_ID = ").append(clavecab);
                    final Query query = getEntityManager().createNativeQuery(SQLNative.toString());
                    query.executeUpdate();
                   
                        //editar estado cabecera
                        objEdit.setCocEstado("F");
                        edit(objEdit);
                         mensaje = "Los registros se modificaron satisfactoriamente.";
                    

            } else {
                mensaje = "Objeto cabecera es nulo.";
            }

        } else {
            mensaje = "Clave o estado de cabecera es nula.";
        }

        return mensaje;
    }
     
    @Override
     public boolean validaPendientes(BigInteger claveCab){
         //verifica si existe valores pendientes
        boolean ctrPendientes = false;
        final StringBuilder SQLNative = new StringBuilder();
        SQLNative.append(" SELECT COUNT(*) ");
        SQLNative.append("   FROM REG_DETORDENCOMPRA DO  ");       
        SQLNative.append("   WHERE DO.COC_ID = ").append(claveCab);
        SQLNative.append("   AND DO.DOC_ESTADO = 'P' ");
        final Query query = getEntityManager().createNativeQuery(SQLNative.toString());
        final Object obj = query.getSingleResult();
        final BigDecimal valor = (BigDecimal) (obj);
        if (valor.intValue() != 0) {
           ctrPendientes = true;
        }

        return ctrPendientes;

    
    }
}
