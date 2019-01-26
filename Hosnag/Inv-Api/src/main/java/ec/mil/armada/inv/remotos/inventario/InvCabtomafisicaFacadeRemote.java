/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.mil.armada.inv.remotos.inventario;

import ec.mil.armada.inv.modelo.catalago.CatArticulo;
import ec.mil.armada.inv.modelo.catalago.CatBodega;
import ec.mil.armada.inv.modelo.inventario.InvCabtomafisica;
import java.math.BigInteger;
import java.util.List;
import javax.ejb.Remote;

/**
 *
 * @author maria_rodriguez
 */
@Remote
public interface InvCabtomafisicaFacadeRemote {

    void create(InvCabtomafisica invCabtomafisica);

    void edit(InvCabtomafisica invCabtomafisica);

    void remove(InvCabtomafisica invCabtomafisica);

    InvCabtomafisica find(Object id);

    List<InvCabtomafisica> findAll();

    List<InvCabtomafisica> findRange(int[] range);

    int count();

   CatBodega objCatBodega(BigInteger bodId);

    int obtieneNumDoc(String aFiscal, BigInteger tipoArt, BigInteger bodId);

   boolean verificaDuplicadoCab(BigInteger bodId, BigInteger tipoArt);

   InvCabtomafisica objetoByTipoEstado(BigInteger bodId, BigInteger tipoArt, String estado);

   CatArticulo objCatArticulo(BigInteger artId);

   List<InvCabtomafisica> listAllByParam(BigInteger bodId, String estado, String tipo, String fechaIni, String fechaFin, String operador, BigInteger noDoc, BigInteger artId);

   boolean verificaEstadoHijos(BigInteger claveCab, String estado);

   InvCabtomafisica crearObjeto(CatBodega bodega, String estado, String tipoInv, BigInteger artId);

   void editarObjeto(InvCabtomafisica objeto, String valor);

    boolean validaPendientes(BigInteger claveCab);

   boolean validaDiferencias(BigInteger claveCab);

   String modificaEstadoCabDet(BigInteger clavecab, String estado);

   String validaFinalizarInv(BigInteger clavecab, String estado);
    
}
