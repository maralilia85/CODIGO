/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.mil.armada.inv.remotos.catalogo;

import ec.mil.armada.inv.modelo.catalago.CatBodega;
import java.math.BigInteger;
import java.util.List;
import javax.ejb.Remote;

/**
 *
 * @author maria_rodriguez
 */
@Remote
public interface CatBodegaFacadeRemote {

    void create(CatBodega catBodega);

    void edit(CatBodega catBodega);

    void remove(CatBodega catBodega);

    CatBodega find(Object id);

    List<CatBodega> findAll();

    List<CatBodega> findRange(int[] range);

    int count();
    
    int countDepedientes (BigInteger codigoPapa, String tipoTabla);

    List<CatBodega> listAllBodegaByParam(String tipo, BigInteger nivel, BigInteger codigoPapa, String estado, String descripcion, BigInteger empId);

    List<CatBodega> listDescripcionByTipo(BigInteger codigoPapa, BigInteger empId);

    List<CatBodega> listDescripcionByTipo(BigInteger codigoPapa, BigInteger empId, String tipo);

}
