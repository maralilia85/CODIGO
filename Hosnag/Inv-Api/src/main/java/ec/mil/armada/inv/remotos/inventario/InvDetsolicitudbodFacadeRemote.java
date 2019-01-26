/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.mil.armada.inv.remotos.inventario;

import ec.mil.armada.inv.modelo.inventario.InvCabsolicitudbod;
import ec.mil.armada.inv.modelo.inventario.InvDetsolicitudbod;
import ec.mil.armada.inv.modelo.inventario.InvExistenciaBodega;
import java.math.BigInteger;
import java.util.List;
import javax.ejb.Remote;

/**
 *
 * @author maria_rodriguez
 */
@Remote
public interface InvDetsolicitudbodFacadeRemote {

    void create(InvDetsolicitudbod invDetsolicitudbod);

    void edit(InvDetsolicitudbod invDetsolicitudbod);

    void remove(InvDetsolicitudbod invDetsolicitudbod);

    InvDetsolicitudbod find(Object id);

    List<InvDetsolicitudbod> findAll();

    List<InvDetsolicitudbod> findRange(int[] range);

    int count();

    List<InvDetsolicitudbod> listAllByParam(BigInteger cabecera, String estado);

    InvExistenciaBodega objInvExistenciaBodega(BigInteger exiId);

    InvCabsolicitudbod objInvCabsolicitudbod(BigInteger csId);
    
}
