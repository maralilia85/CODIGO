/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.mil.armada.inv.remotos.registro;

import ec.mil.armada.inv.modelo.catalago.CatArticulo;
import ec.mil.armada.inv.modelo.catalago.CatBodega;
import ec.mil.armada.inv.modelo.catalago.CatEmpresa;
import ec.mil.armada.inv.modelo.registro.RegCabordencompra;
import java.math.BigInteger;
import java.util.List;
import javax.ejb.Remote;

/**
 *
 * @author DET-PC
 */
@Remote
public interface RegCabordencompraFacadeRemote {

    void create(RegCabordencompra regCabordencompra);

    void edit(RegCabordencompra regCabordencompra);

    void remove(RegCabordencompra regCabordencompra);

    RegCabordencompra find(Object id);

    List<RegCabordencompra> findAll();

    List<RegCabordencompra> findRange(int[] range);

    int count();

    CatArticulo objCatArticulo(BigInteger clave);

    CatBodega objCatBodega(BigInteger bodId);

    CatEmpresa objCatEmpresa(BigInteger clave);

    List<RegCabordencompra> listAllByParam(BigInteger bodId, String numero, String estado, String tipo, BigInteger tipoArtId, String fechaIni, String fechaFin, String operador);

    boolean validaPendientes(BigInteger claveCab);

    String modificaEstadoCabDet(BigInteger clavecab, String estado);

   
}