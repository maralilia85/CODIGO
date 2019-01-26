/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.mil.armada.inv.remotos.registro;

import ec.mil.armada.inv.modelo.catalago.CatGeneral;
import ec.mil.armada.inv.modelo.registro.RegPaciente;
import java.math.BigInteger;
import java.util.List;
import javax.ejb.Remote;

/**
 *
 * @author DET-PC
 */
@Remote
public interface RegPacienteFacadeRemote {

    void create(RegPaciente regPaciente);

    void edit(RegPaciente regPaciente);

    void remove(RegPaciente regPaciente);

    RegPaciente find(Object id);

    List<RegPaciente> findAll();

    List<RegPaciente> findRange(int[] range);

    int count();

    List<RegPaciente> objPacByParam(String descripcion, String estado);

    CatGeneral objCatGeneral(BigInteger genId);
    
}
