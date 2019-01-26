/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.mil.armada.inv.servicios.registro;

import ec.mil.armada.inv.remotos.registro.RegAdmisionFacadeRemote;
import ec.mil.armada.inv.modelo.registro.RegAdmision;
import ec.mil.armada.inv.servicios.abstractFacade.AbstractFacade;
import java.math.BigInteger;
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
public class RegAdmisionFacade extends AbstractFacade<RegAdmision> implements RegAdmisionFacadeRemote {
    @PersistenceContext(unitName = "ec.mil.armada_Inv-Servicios_ejb_1.0PU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public RegAdmisionFacade() {
        super(RegAdmision.class);
    }
    @Override
    public RegAdmision objRegAdmision(BigInteger pacId, String estado){
    
        RegAdmision objeto = null;

        final Query query = em.createQuery("Select object (a) from RegAdmision a where a.pacId = :pacId and a.admEstado = :estado  ");
        query.setParameter("pacId", pacId);
         query.setParameter("estado", estado);
        query.setHint("eclipselink.refresh", true);
        final List<RegAdmision> result = query.getResultList();
        if (result.size() > 0) {
            objeto = result.get(0);
        }
        return objeto;
    }
    
}
