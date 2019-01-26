/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.mil.armada.inv.remotos.catalogo;

import ec.mil.armada.inv.modelo.catalago.CatDireccion;
import java.util.List;
import javax.ejb.Remote;

/**
 *
 * @author maria_rodriguez
 */
@Remote
public interface CatDireccionFacadeRemote {

    void create(CatDireccion catDireccion);

    void edit(CatDireccion catDireccion);

    void remove(CatDireccion catDireccion);

    CatDireccion find(Object id);

    List<CatDireccion> findAll();

    List<CatDireccion> findRange(int[] range);

    int count();
    
}
