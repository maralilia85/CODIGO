/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.mil.armada.inv.remotos.inventario;

import ec.mil.armada.inv.modelo.catalago.CatArticulo;
import ec.mil.armada.inv.modelo.catalago.CatBodega;
import ec.mil.armada.inv.modelo.inventario.InvCabsolicitudbod;
import java.math.BigInteger;
import java.util.List;
import javax.ejb.Remote;

/**
 *
 * @author maria_rodriguez
 */
@Remote
public interface InvCabsolicitudbodFacadeRemote {

    void create(InvCabsolicitudbod invCabsolicitudbod);

    void edit(InvCabsolicitudbod invCabsolicitudbod);

    void remove(InvCabsolicitudbod invCabsolicitudbod);

    InvCabsolicitudbod find(Object id);

    List<InvCabsolicitudbod> findAll();

    List<InvCabsolicitudbod> findRange(int[] range);

    int count();

    CatArticulo objCatArticulo(BigInteger clave);

    CatBodega objCatBodega(BigInteger bodId);

    List<InvCabsolicitudbod> listAllByParam(BigInteger bodId, String numero, String estado, String tipo, BigInteger tipoArtId, String fechaIni, String fechaFin, String operador);

   boolean validaEstados(BigInteger claveCab, String estado);

   String modificaEstadoCabDet(BigInteger clavecab, String estado);
    
}
