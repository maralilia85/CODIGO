/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.mil.armada.inv.servicios.inventario;

import ec.mil.armada.inv.remotos.inventario.InvExistenciaBodegaFacadeRemote;
import ec.mil.armada.inv.modelo.inventario.InvCabsolicitudbod;
import ec.mil.armada.inv.modelo.inventario.InvDetsolicitudbod;
import ec.mil.armada.inv.modelo.inventario.InvExistenciaBodega;
import ec.mil.armada.inv.remotos.inventario.InvCabsolicitudbodFacadeRemote;
import ec.mil.armada.inv.servicios.abstractFacade.AbstractFacade;
import java.math.BigInteger;
import java.util.ArrayList;
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
public class InvDetsolicitudbodFacade extends AbstractFacade<InvDetsolicitudbod> implements ec.mil.armada.inv.remotos.inventario.InvDetsolicitudbodFacadeRemote {
    @EJB
    private InvExistenciaBodegaFacadeRemote invExistenciaBodegaFacade;
    @EJB
    private InvCabsolicitudbodFacadeRemote invCabsolicitudbodFacade;
    @PersistenceContext(unitName = "ec.mil.armada_Inv-Servicios_ejb_1.0PU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public InvDetsolicitudbodFacade() {
        super(InvDetsolicitudbod.class);
    }
    @Override
      public List<InvDetsolicitudbod> listAllByParam(BigInteger cabecera, String estado) {
        final StringBuilder SQLNative = new StringBuilder();
        boolean isCabecera = cabecera != null;
        boolean isEstado = estado != null && estado.trim().length() > 0 && !(estado.trim().toLowerCase().compareTo("null") == 0);
        
        SQLNative.append(" SELECT DS.DSB_ID, DS.DSB_NUMERO,DS.DSB_ESTADO,DS.DSB_CANTSOLICITADA,DS.DSB_CANTAPROBADA,DS.DSB_STOCKSATISFACE,DS.DSB_STOCKNOSATISFACE,DS.DBS_DEMANDAREQ, ");
        SQLNative.append("  DS.CSB_ID, DS.EXI_ID ");
        SQLNative.append("  FROM INV_DETSOLICITUDBOD DS ");
        SQLNative.append("   WHERE 1=1 ");
        if (isCabecera) {
            if (isCabecera) {
                SQLNative.append("   AND DS.CSB_ID = ").append(cabecera);

            }
            if (isEstado) {
                SQLNative.append(" AND DS.DSB_ESTADO =  '").append(estado).append("' ");

            }

        } else {
            SQLNative.append(" AND 1=2   ");

        }
        final Query query = em.createNativeQuery(SQLNative.toString());
        final List<Object[]> datos = query.getResultList();
        final List<InvDetsolicitudbod> datosRetorno = new ArrayList<>();
        for (final Object[] obj : datos) {
            int a = 0;
            final InvDetsolicitudbod inv = new InvDetsolicitudbod();
            inv.setDsbId(new BigInteger(obj[a].toString()));
            a++;
            if (obj[a] != null) {
                inv.setDsbNumero(new BigInteger(obj[a].toString()));
            }
            a++;
           
            if (obj[a] != null) {
                inv.setDsbEstado((String) obj[a]);

            }
            a++;
            if (obj[a] != null) {
                inv.setDsbCantsolicitada(new BigInteger(obj[a].toString()));

            }
            a++;
            if (obj[a] != null) {
                inv.setDsbCantaprobada(new BigInteger(obj[a].toString()));

            }
            a++;
            if (obj[a] != null) {
                inv.setDsbStocksatisface((String) obj[a]);

            }
            a++;
             if (obj[a] != null) {
                inv.setDsbStockNoSatisface((String) obj[a]);

            }
            a++;
             if (obj[a] != null) {
                inv.setDsbDemandaReq(new BigInteger(obj[a].toString()));
            }
             a++;
            if (obj[a] != null) {
                inv.setInvCabsolicitudbod(new InvCabsolicitudbod());
                inv.setInvCabsolicitudbod(objInvCabsolicitudbod(new BigInteger(obj[a].toString())));
            }
            a++;
            if (obj[a] != null) {
                 inv.setInvExistenciaBodega(new InvExistenciaBodega());
                inv.setInvExistenciaBodega(objInvExistenciaBodega(new BigInteger(obj[a].toString())));
                
            }
            datosRetorno.add(inv);
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
      public InvCabsolicitudbod objInvCabsolicitudbod(BigInteger csId) {

        InvCabsolicitudbod objeto = null;

        final Query query = em.createQuery("Select object (c) from InvCabsolicitudbod c where c.csbId = :csId  ");
        query.setParameter("csId", csId);
        query.setHint("eclipselink.refresh", true);
        final List<InvCabsolicitudbod> result = query.getResultList();
        if (result.size() > 0) {
            objeto = result.get(0);
        }
        return objeto;
    }

}
