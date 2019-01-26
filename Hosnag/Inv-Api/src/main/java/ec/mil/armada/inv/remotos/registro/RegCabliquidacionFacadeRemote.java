/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.mil.armada.inv.remotos.registro;

import ec.mil.armada.inv.modelo.registro.RegCabliquidacion;
import java.math.BigInteger;
import java.util.List;
import javax.ejb.Remote;

/**
 *
 * @author DET-PC
 */
@Remote
public interface RegCabliquidacionFacadeRemote {

    void create(RegCabliquidacion regCabliquidacion);

    void edit(RegCabliquidacion regCabliquidacion);

    void remove(RegCabliquidacion regCabliquidacion);

    RegCabliquidacion find(Object id);

    List<RegCabliquidacion> findAll();

    List<RegCabliquidacion> findRange(int[] range);

    int count();

    boolean verificaDuplicadoCab(BigInteger claveTabla, String siglaTabla, String estado);

    RegCabliquidacion objRegCabliquidacion(BigInteger claveTabla, String estado, String siglaTabla);

    RegCabliquidacion crearObjeto(BigInteger claveTabla, String siglasTabla, String observacion, String estado);

    int numeroDoc(String aFiscal, BigInteger claveTabla, String siglasTabla, String estado);
  
    void editCabLiq(RegCabliquidacion objeto, String valor);

    RegCabliquidacion crearObjeto(BigInteger bodId, BigInteger servId, BigInteger claveTabla, String siglasTabla, String observacion, String estado, Double subtotal, Double totalDesc, Double totalIva);

    Double actualizaValorTotal(BigInteger clavecab);

    
}
