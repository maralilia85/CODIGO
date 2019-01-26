/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.mil.armada.inv.remotos.registro;

import ec.mil.armada.inv.modelo.catalago.CatBodega;
import ec.mil.armada.inv.modelo.registro.RegCabtemp;
import java.math.BigInteger;
import java.util.List;
import javax.ejb.Remote;

/**
 *
 * @author DET-PC
 */
@Remote
public interface RegCabtempFacadeRemote {

    void create(RegCabtemp regCabtemp);

    void edit(RegCabtemp regCabtemp);

    void remove(RegCabtemp regCabtemp);

    RegCabtemp find(Object id);

    List<RegCabtemp> findAll();

    List<RegCabtemp> findRange(int[] range);

    int count();

    RegCabtemp generaRegTemp(BigInteger bodId, String tipo, BigInteger noTem, BigInteger min, BigInteger max, String mes);

    CatBodega objCatBodega(BigInteger bodId);

    RegCabtemp objetoByBod(String tipo, BigInteger idBod, String mes, String a, BigInteger noTer);
    
}
