/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.mil.armada.inv.remotos.registro;

import ec.mil.armada.inv.modelo.registro.RegCabtemp;
import ec.mil.armada.inv.modelo.registro.RegDettemp;
import java.math.BigInteger;
import java.util.Date;
import java.util.List;
import javax.ejb.Remote;

/**
 *
 * @author DET-PC
 */
@Remote
public interface RegDettempFacadeRemote {

    void create(RegDettemp regDettemp);

    void edit(RegDettemp regDettemp);

    void remove(RegDettemp regDettemp);

    RegDettemp find(Object id);

    List<RegDettemp> findAll();

    List<RegDettemp> findRange(int[] range);

    int count();

    void crearDetByCabReg(BigInteger idCab, Date fecha);

    void editDetTemp(RegDettemp obj, String valor);

    List<RegDettemp> listRegDetByCab(BigInteger claveCab, String fecha);

    RegCabtemp objRegCab(BigInteger cliId);

    boolean verificaDuplicado(BigInteger cab, String fecha);
    
}
