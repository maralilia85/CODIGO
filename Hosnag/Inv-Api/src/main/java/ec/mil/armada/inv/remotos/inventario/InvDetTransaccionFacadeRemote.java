/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.mil.armada.inv.remotos.inventario;

import ec.mil.armada.inv.modelo.inventario.InvCabTransaccion;
import ec.mil.armada.inv.modelo.inventario.InvDetTransaccion;
import ec.mil.armada.inv.modelo.inventario.InvDetsolicitudbod;
import ec.mil.armada.inv.modelo.inventario.InvDettomafisica;
import ec.mil.armada.inv.modelo.inventario.InvExistenciaBodega;
import ec.mil.armada.inv.modelo.inventario.InvLoteArticulo;
import ec.mil.armada.inv.modelo.registro.RegDetordencompra;
import java.math.BigInteger;
import java.util.List;
import javax.ejb.Remote;

/**
 *
 * @author maria_rodriguez
 */
@Remote
public interface InvDetTransaccionFacadeRemote {

    void create(InvDetTransaccion invDetTransaccion);

    void edit(InvDetTransaccion invDetTransaccion);

    void remove(InvDetTransaccion invDetTransaccion);

    InvDetTransaccion find(Object id);

    List<InvDetTransaccion> findAll();

    List<InvDetTransaccion> findRange(int[] range);

    int count();

    List<InvDetTransaccion> listAllByParam(BigInteger bodId, BigInteger artId, BigInteger tipoArt, String descripcion, String estado, String tipo, String fechaIni, String fechaFin, String operador);

    InvExistenciaBodega objInvExistenciaBodega(BigInteger exiId);

    InvLoteArticulo objInvLoteArticulo(BigInteger loteId);

    void crearDetalle(InvCabTransaccion objCabecera, List<RegDetordencompra> listExiCantidad, String estado);

    List<InvDetTransaccion> listAllByParam(BigInteger claveCabecera, String estado);

    boolean verificaExisNull(BigInteger clavecab);

    Double calcular(Double valor1, Double valor2, String operacion);

    void editarDetTrans(InvDetTransaccion objeto, String valor);

    void eliminaDetalle(InvDetTransaccion objeto);

    Double sumaDetTotal(BigInteger clavecab, String campo);

    void crearDetalleBySolic(InvCabTransaccion objCabecera, List<InvDetsolicitudbod> listExiCantidad, String estado);

    void crearDetalleByTomaFisica(InvCabTransaccion objCabecera, List<InvDettomafisica> listInvDettomafisica, String estado);

    String crearDetalleByParam(BigInteger bodId, BigInteger idCabecera, BigInteger artId, BigInteger cantidad, String estado, String tipoTrans);

   
    void crearLiquidacion(BigInteger bodId, BigInteger servId, BigInteger claveTabla, String siglaTabla, String observacion, String estado, Double subtotal, Double tDesc, Double tIva, InvDetTransaccion objTransaccion);

    void crearLiquidacion(BigInteger claveTabla, String siglaTabla, String observacion, String estado, BigInteger exiId, BigInteger cant, Double valor);

    String crearDetalleByParam(BigInteger bodId, BigInteger idCabecera, BigInteger artId, BigInteger cantidad, String estado, String tipoTrans, Double cPromedio);
    
}
