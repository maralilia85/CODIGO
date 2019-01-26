/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.mil.armada.inv.remotos.registro;

import ec.mil.armada.inv.modelo.inventario.InvExistenciaBodega;
import ec.mil.armada.inv.modelo.registro.RegCabliquidacion;
import ec.mil.armada.inv.modelo.registro.RegDetliquidacion;
import java.math.BigInteger;
import java.util.List;
import javax.ejb.Remote;

/**
 *
 * @author DET-PC
 */
@Remote
public interface RegDetliquidacionFacadeRemote {

    void create(RegDetliquidacion regDetliquidacion);

    void edit(RegDetliquidacion regDetliquidacion);

    void remove(RegDetliquidacion regDetliquidacion);

    RegDetliquidacion find(Object id);

    List<RegDetliquidacion> findAll();

    List<RegDetliquidacion> findRange(int[] range);

    int count();

   RegDetliquidacion objRegDetLiquidacion(BigInteger claveCabecera, String estado, BigInteger exiId);

   void crearRegDetLiquidacion(BigInteger claveCab, BigInteger exiId, BigInteger cantidad, String estado, Double valor);

 boolean verificaDuplicadoCab(BigInteger claveCab, BigInteger exiId, String estado);

 List<RegDetliquidacion> listRegDetliquidacionByParam(BigInteger claveCab, BigInteger exiId, String estado);

  InvExistenciaBodega objInvExistenciaBodega(BigInteger exiId);

    RegCabliquidacion objRegCabliquidacion(BigInteger cliId);

    RegDetliquidacion objRegDetliquidacion(BigInteger exiId, String estado, BigInteger cabecera);

    boolean verificaHijosCab(BigInteger claveTabla, String siglaTabla, String estado);

   void modificaEstado(BigInteger exiId, BigInteger claveTabla, String siglaTabla, String estado);

    void crearRegDetLiquidacion(BigInteger claveCab, BigInteger exiId, BigInteger cantidad, String estado, Double valor, Double vIva, BigInteger porcDes, Double tDesc);

    Double sumaDetTotal(BigInteger clavecab, String campo);

    void modificaEstado(BigInteger exiId, BigInteger claveTabla, String siglaTabla, String estadobsq, BigInteger cant, String estado);

   
    
}
