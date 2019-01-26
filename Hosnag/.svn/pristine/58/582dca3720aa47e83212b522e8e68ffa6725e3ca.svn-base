/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.mil.armada.inv.servicios.catalogo;

import ec.mil.armada.inv.servicios.abstractFacade.AbstractFacade;
import ec.mil.armada.inv.modelo.catalago.CatDireccion;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author maria_rodriguez
 */
@Stateless
public class CatDireccionFacade extends AbstractFacade<CatDireccion> implements ec.mil.armada.inv.remotos.catalogo.CatDireccionFacadeRemote {
    @PersistenceContext(unitName = "ec.mil.armada_Inv-Servicios_ejb_1.0PU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public CatDireccionFacade() {
        super(CatDireccion.class);
    }
    
}
