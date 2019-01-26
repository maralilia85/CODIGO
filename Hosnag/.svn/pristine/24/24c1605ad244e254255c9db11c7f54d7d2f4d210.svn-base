/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.mil.armada.inv.remotos.registro;

import ec.mil.armada.inv.modelo.inventario.InvExistenciaBodega;
import ec.mil.armada.inv.modelo.inventario.InvLoteArticulo;
import ec.mil.armada.inv.modelo.registro.RegCabordencompra;
import ec.mil.armada.inv.modelo.registro.RegDetordencompra;
import java.math.BigInteger;
import java.util.List;
import javax.ejb.Remote;

/**
 *
 * @author DET-PC
 */
@Remote
public interface RegDetordencompraFacadeRemote {

    void create(RegDetordencompra regDetordencompra);

    void edit(RegDetordencompra regDetordencompra);

    void remove(RegDetordencompra regDetordencompra);

    RegDetordencompra find(Object id);

    List<RegDetordencompra> findAll();

    List<RegDetordencompra> findRange(int[] range);

    int count();

    InvLoteArticulo objLoteArt(BigInteger clave);

    InvExistenciaBodega objExistenciaArt(BigInteger clave);

    List<RegDetordencompra> listAllByParam(BigInteger cabecera, String estado);

    RegCabordencompra objCabOrdenCompra(BigInteger cocId);
    
}
