/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.mil.armada.inv.remotos.registro;

import ec.mil.armada.inv.modelo.registro.RegAdmision;
import java.math.BigInteger;
import java.util.List;
import javax.ejb.Remote;

/**
 *
 * @author DET-PC
 */
@Remote
public interface RegAdmisionFacadeRemote {

    void create(RegAdmision regAdmision);

    void edit(RegAdmision regAdmision);

    void remove(RegAdmision regAdmision);

    RegAdmision find(Object id);

    List<RegAdmision> findAll();

    List<RegAdmision> findRange(int[] range);

    int count();

    RegAdmision objRegAdmision(BigInteger pacId, String estado);
    
}
