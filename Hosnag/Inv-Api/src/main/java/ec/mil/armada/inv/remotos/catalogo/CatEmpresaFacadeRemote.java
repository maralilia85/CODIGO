/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.mil.armada.inv.remotos.catalogo;

import ec.mil.armada.inv.modelo.catalago.CatEmpresa;
import java.util.List;
import javax.ejb.Remote;

/**
 *
 * @author maria_rodriguez
 */
@Remote
public interface CatEmpresaFacadeRemote {

    void create(CatEmpresa catEmpresa);

    void edit(CatEmpresa catEmpresa);

    void remove(CatEmpresa catEmpresa);

    CatEmpresa find(Object id);

    List<CatEmpresa> findAll();

    List<CatEmpresa> findRange(int[] range);

    int count();

    List<CatEmpresa> listEmpDescripcionByParam(String tipo, String estado, String descripcion);
    
}
