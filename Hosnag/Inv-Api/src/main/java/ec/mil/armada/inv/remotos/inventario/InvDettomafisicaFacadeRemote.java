/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.mil.armada.inv.remotos.inventario;

import ec.mil.armada.inv.modelo.catalago.CatArticulo;
import ec.mil.armada.inv.modelo.inventario.InvCabtomafisica;
import ec.mil.armada.inv.modelo.inventario.InvDetTransaccion;
import ec.mil.armada.inv.modelo.inventario.InvDettomafisica;
import ec.mil.armada.inv.modelo.inventario.InvExistenciaBodega;
import ec.mil.armada.inv.modelo.inventario.InvLoteArticulo;
import java.math.BigInteger;
import java.util.List;
import javax.ejb.Remote;

/**
 *
 * @author maria_rodriguez
 */
@Remote
public interface InvDettomafisicaFacadeRemote {

    void create(InvDettomafisica invDettomafisica);

    void edit(InvDettomafisica invDettomafisica);

    void remove(InvDettomafisica invDettomafisica);

    InvDettomafisica find(Object id);

    List<InvDettomafisica> findAll();

    List<InvDettomafisica> findRange(int[] range);

    int count();

    InvExistenciaBodega objInvExistenciaBodega(BigInteger exiId);

    InvLoteArticulo objInvLoteArticulo(BigInteger loteId);

    void crearDetalle(InvCabtomafisica objCabecera, String estado);

    void editarDetToma(InvDettomafisica objeto, String valor);

    List<InvDettomafisica> listAllByParam(BigInteger claveCabecera, String estado);

    InvCabtomafisica objInvCabTransaccion(BigInteger ctfId);

    boolean validaPendientes(BigInteger claveCab);

    boolean validaDiferencias(BigInteger claveCab);

   boolean validaEstados(BigInteger claveCab, String estado);

    List<InvDettomafisica> listDiferencias(BigInteger claveCabecera, String estado, String tipoDif);

    InvDettomafisica objetoByCabExistenciaEstado(BigInteger cabecera, BigInteger existencia, String estado);

    String editarDetTomaByAjus(InvDetTransaccion objDetTransaccion, String valor);

     void crearItemFueraInv(InvCabtomafisica objCab, String estado, CatArticulo objArticulo, BigInteger cant, String observacion);
    
}
