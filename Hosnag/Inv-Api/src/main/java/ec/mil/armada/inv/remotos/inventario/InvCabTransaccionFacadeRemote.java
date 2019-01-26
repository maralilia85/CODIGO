/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.mil.armada.inv.remotos.inventario;

import ec.mil.armada.inv.modelo.catalago.CatArticulo;
import ec.mil.armada.inv.modelo.catalago.CatBodega;
import ec.mil.armada.inv.modelo.inventario.InvCabTransaccion;
import java.math.BigInteger;
import java.util.List;
import javax.ejb.Remote;

/**
 *
 * @author maria_rodriguez
 */
@Remote
public interface InvCabTransaccionFacadeRemote {

    void create(InvCabTransaccion invCabTransaccion);

    void edit(InvCabTransaccion invCabTransaccion);

    void remove(InvCabTransaccion invCabTransaccion);

    InvCabTransaccion find(Object id);

    List<InvCabTransaccion> findAll();

    List<InvCabTransaccion> findRange(int[] range);

    int count();

    List<InvCabTransaccion> listAllByParam(BigInteger bodId, String estado, String tipo, String fechaIni, String fechaFin, String operador,BigInteger tipoId);

    CatBodega objCatBodega(BigInteger bodId);

    int numeroDoc(String aFiscal,BigInteger tipoArt, BigInteger bodId,String tipoTrans);

   InvCabTransaccion crearObjeto(CatBodega bodega, BigInteger claveTabla, String siglasTabla, String estado, String retira, String tipo, BigInteger artId);

   boolean verificaDuplicadoCab(BigInteger claveTabla,String siglaTabla);

    List<InvCabTransaccion> listAllByParam(BigInteger noDoc, String estado, String noRef, BigInteger noOComp,String tipo,String fechaIn);

    void editarObjeto(InvCabTransaccion objeto, String valor);

    boolean verificaEstadoHijos(BigInteger claveCab, String estado);

    boolean validaInactiva(BigInteger claveCab, String estado,BigInteger exiId);

    String inactivaCabDet(BigInteger ctrId);

    InvCabTransaccion objetoByClaveTablaTipo(BigInteger claveTabla, String sigla);

    Double actualizaValorTotal(BigInteger clavecab);

    InvCabTransaccion objetoByClaveTablaTipo(String tipoTrans, BigInteger tipoArt, BigInteger idBod, String estado);

    boolean verificaDuplicadoCab(BigInteger clavetabla, String siglaTabla,  String tipoTrans, BigInteger idBod, BigInteger tipoArt);

    InvCabTransaccion objetoByTransferencia(String tipoTrans, BigInteger idBodArea, String estado, String fecha, BigInteger noDoc);

    public CatArticulo objCatArticulo(BigInteger artId);

    InvCabTransaccion objetoByParametros(BigInteger tipoArt, BigInteger bodId, String tipoTrans, BigInteger idBodArea, String estado, String fecha, BigInteger noDoc, BigInteger claveTabla, String sigla, String noRef);
    
}
