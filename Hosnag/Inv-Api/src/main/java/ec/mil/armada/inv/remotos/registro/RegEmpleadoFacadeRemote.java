/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.mil.armada.inv.remotos.registro;

import ec.mil.armada.inv.modelo.registro.RegEmpleado;
import java.math.BigInteger;
import java.util.List;
import javax.ejb.Remote;

/**
 *
 * @author DET-PC
 */
@Remote
public interface RegEmpleadoFacadeRemote {

    void create(RegEmpleado regEmpleado);

    void edit(RegEmpleado regEmpleado);

    void remove(RegEmpleado regEmpleado);

    RegEmpleado find(Object id);

    List<RegEmpleado> findAll();

    List<RegEmpleado> findRange(int[] range);

    int count();

    List<RegEmpleado> listEmpleadoByParam(String descripcion, BigInteger empresa, BigInteger codigodir, String estado);

    String nombreEmpleado(BigInteger emlId);
    
}
