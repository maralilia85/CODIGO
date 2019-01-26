/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.mil.armada.inv.servicios.inventario;

import ec.mil.armada.inv.modelo.catalago.CatArticulo;
import ec.mil.armada.inv.modelo.catalago.CatBodega;
import ec.mil.armada.inv.modelo.inventario.InvCabsolicitudbod;
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
 * @author maria_rodriguez
 */
@Stateless
public class InvCabsolicitudbodFacade extends AbstractFacade<InvCabsolicitudbod> implements ec.mil.armada.inv.remotos.inventario.InvCabsolicitudbodFacadeRemote {
    @PersistenceContext(unitName = "ec.mil.armada_Inv-Servicios_ejb_1.0PU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public InvCabsolicitudbodFacade() {
        super(InvCabsolicitudbod.class);
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
    public List<InvCabsolicitudbod> listAllByParam(BigInteger bodId, String numero, String estado, String tipo, BigInteger tipoArtId, String fechaIni, String fechaFin, String operador) {
        final StringBuilder SQLNative = new StringBuilder();
        boolean isBodega = bodId != null;
        boolean isNumero = numero != null;
        boolean isTipoArtId = tipoArtId != null;
        boolean isEstado = estado != null && estado.trim().length() > 0 && !(estado.trim().toLowerCase().compareTo("null") == 0);
        boolean isTipo = tipo != null && tipo.trim().length() > 0 && !(tipo.trim().toLowerCase().compareTo("null") == 0);
        boolean isfechaIni = fechaIni != null && fechaIni.trim().length() > 0 && !(fechaIni.trim().toLowerCase().compareTo("null") == 0);
        boolean isfechaFin = fechaFin != null && fechaFin.trim().length() > 0 && !(fechaFin.trim().toLowerCase().compareTo("null") == 0);
        boolean isOperador = operador != null && operador.trim().length() > 0 && !(operador.trim().toLowerCase().compareTo("null") == 0);

        SQLNative.append("  SELECT  CS.CSB_ID, CS.CSB_ESTADO,CS.CSB_TIPO,CS.CSB_NUMERO,CS.CSB_MOTIVO,CS.CSB_NOREF,CS.CSB_FECHA,  ");
        SQLNative.append("  CS.CSB_SUBDIRECCION,CS.CSB_FECHASDA,CS.CSB_MOTIVOSDA,CS.CSB_DIAS,CS.CSB_BODEGA,CS.CSB_AREASOLIC,CS.ART_TIPO  ");
        SQLNative.append("    FROM INV_CABSOLICITUDBOD CS ");

        SQLNative.append("   WHERE 1=1 ");
        if (isBodega || isTipoArtId || isEstado || isNumero) {
            if (isBodega) {
                SQLNative.append("   AND CS.CSB_BODEGA = ").append(bodId);

            }
            if (isTipoArtId) {
                SQLNative.append("   AND CS.ART_TIPO = ").append(bodId);

            }

            if (isEstado) {
                SQLNative.append(" AND CS.CSB_ESTADO =  '").append(estado).append("' ");

            }
            if (isTipo) {
                SQLNative.append(" AND CS.CSB_TIPO =  '").append(tipo).append("' ");

            }
            if (isNumero) {
                SQLNative.append("       AND  CS.CSB_NUMERO || ' '||  CS.CSB_NOREF ");
                SQLNative.append("   LIKE '%").append(numero).append("%'");

            }
            if (isfechaIni && isOperador) {
                //Buscar por existencia
                if (operador.equalsIgnoreCase("=")) {
                    SQLNative.append(" AND TRUNC (CS.CSB_FECHA) = to_date('").append(fechaIni).append("','dd/mm/yyyy')");
                }
                if (operador.equalsIgnoreCase(">=")) {
                    SQLNative.append(" AND TRUNC (CS.CSB_FECHA) >= to_date('").append(fechaIni).append("','dd/mm/yyyy')");
                }
                if (operador.equalsIgnoreCase("<=")) {
                    SQLNative.append(" AND TRUNC (CS.CSB_FECHA) <= to_date('").append(fechaIni).append("','dd/mm/yyyy')");
                }
                if (isfechaFin && operador.equalsIgnoreCase("ENTRE")) {
                    SQLNative.append("  AND TRUNC (CS.CSB_FECHA) BETWEEN to_date('").append(fechaIni).append("','dd/mm/yyyy') and to_date('").append(fechaFin).append("','dd/mm/yyyy')");
                }

            }

        } else {
            SQLNative.append(" AND 1=2   ");

        }
        final Query query = em.createNativeQuery(SQLNative.toString());
        final List<Object[]> datos = query.getResultList();
        final List<InvCabsolicitudbod> datosRetorno = new ArrayList<>();
        for (final Object[] obj : datos) {
            int a = 0;
            final InvCabsolicitudbod inv = new InvCabsolicitudbod();
            inv.setCsbId(new BigInteger(obj[a].toString()));
            a++;
            if (obj[a] != null) {
                inv.setCsbEstado(((String) obj[a]));
            }
            a++;
            if (obj[a] != null) {
                inv.setCsbTipo(((String) obj[a]));
            }
            a++;
            if (obj[a] != null) {
                inv.setCsbNumero(new BigInteger(obj[a].toString()));
            }
            a++;

            if (obj[a] != null) {
                inv.setCsbMotivo(((String) obj[a]));
            }
            a++;
            if (obj[a] != null) {
                inv.setCsbNoref(new BigInteger(obj[a].toString()));
            }
            a++;

            if (obj[a] != null) {
                inv.setCsbFecha(((Date) obj[a]));
            }
            a++;
            if (obj[a] != null) {
                inv.setCsbSubDireccion(((String) obj[a]));
            }
            a++;
            if (obj[a] != null) {
                inv.setCsbFechaSda(((Date) obj[a]));
            }
            a++;

            if (obj[a] != null) {
                inv.setCsbMotivoSda(((String) obj[a]));
            }
            a++;
            if (obj[a] != null) {
                inv.setCsbDias(new BigInteger(obj[a].toString()));
            }
            a++;

            if (obj[a] != null) {
                inv.setCsbBodega(new BigInteger(obj[a].toString()));
                inv.setCatBodega(objCatBodega(new BigInteger(obj[a].toString())));
            }
            a++;
            if (obj[a] != null) {
                inv.setCsbAreasolic(new BigInteger(obj[a].toString()));
                inv.setCatArea(objCatBodega(new BigInteger(obj[a].toString())));
            }
            a++;

            if (obj[a] != null) {
                inv.setArtTipo(new BigInteger(obj[a].toString()));
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
            InvCabsolicitudbod objEdit = new InvCabsolicitudbod();
            objEdit = find(clavecab);
            if (objEdit != null) {
       
                    //ACTUALIZA ESTADO DE LOS HIJOS
                    final StringBuilder SQLNative = new StringBuilder();
                    SQLNative.append(" UPDATE INV_DETSOLICITUDBOD DS SET DS.DSB_ESTADO = '").append(estado).append("' ");
                    SQLNative.append(" WHERE ");
                    SQLNative.append(" DS.CSB_ID = ").append(clavecab);
                    final Query query = getEntityManager().createNativeQuery(SQLNative.toString());
                    query.executeUpdate();
                   
                    //verifica estados de hijos
               
                    boolean ctrModificaEstadoCab = validaEstados(clavecab, "P");
                    if(ctrModificaEstadoCab){
                        //actualiza cabecera
                        objEdit.setCsbEstado("F");
                        edit(objEdit);
                         mensaje = "Los registros se modificaron satisfactoriamente.";
                    }
                    

            } else {
                mensaje = "Objeto cabecera es nulo.";
            }

        } else {
            mensaje = "Clave o estado de cabecera es nula.";
        }

        return mensaje;
    }
     

    @Override
     public boolean validaEstados(BigInteger claveCab, String estado){
         //verifica si existe valores pendientes
        boolean ctrPendientes = false;
        final StringBuilder SQLNative = new StringBuilder();
        SQLNative.append(" SELECT COUNT(*) ");
        SQLNative.append("   FROM INV_DETSOLICITUDBOD DS  ");       
        SQLNative.append("   WHERE DS.CSB_ID = ").append(claveCab);
        SQLNative.append("  AND  DS.DSB_ESTADO = '").append(estado).append("' ");
        final Query query = getEntityManager().createNativeQuery(SQLNative.toString());
        final Object obj = query.getSingleResult();
        final BigDecimal valor = (BigDecimal) (obj);
        if (valor.intValue() != 0) {
           ctrPendientes = true;
        }

        return ctrPendientes;

    
    }
    
}
