/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.mil.armada.inv.remotos.catalogo;

import ec.mil.armada.inv.modelo.catalago.CatArticulo;
import java.math.BigInteger;
import java.util.List;
import javax.ejb.Remote;

/**
 *
 * @author maria_rodriguez
 */
@Remote
public interface CatArticuloFacadeRemote {

    void create(CatArticulo catArticulo);

    void edit(CatArticulo catArticulo);

    void remove(CatArticulo catArticulo);

    CatArticulo find(Object id);

    List<CatArticulo> findAll();

    List<CatArticulo> findRange(int[] range);

    int count();

    
    int countDepedientes(BigInteger codigoPapa, String tipoTabla);

   List<CatArticulo> listArticuloByParam(BigInteger nivel, BigInteger codigoPapa, String estado, String descripcion);

   List<CatArticulo> listByTipoNivel(BigInteger codigoPapa, BigInteger nivel);

   int numeroArticulo(BigInteger tipoArt, BigInteger subtipoArt);


   boolean verificaExisBySubtipo(BigInteger idSubtipo, BigInteger idTipo);

    List<CatArticulo> listTipoByExistencia(BigInteger idBodega);
    
    
    
}
