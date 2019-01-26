/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.mil.armada.inv.remotos.inventario;

import ec.mil.armada.inv.modelo.inventario.InvLoteArticulo;
import java.math.BigInteger;
import java.util.List;
import javax.ejb.Remote;

/**
 *
 * @author maria_rodriguez
 */
@Remote
public interface InvLoteArticuloFacadeRemote {

    void create(InvLoteArticulo invLoteArticulo);

    void edit(InvLoteArticulo invLoteArticulo);

    void remove(InvLoteArticulo invLoteArticulo);

    InvLoteArticulo find(Object id);

    List<InvLoteArticulo> findAll();

    List<InvLoteArticulo> findRange(int[] range);

    int count();

    List<InvLoteArticulo> listAllByParam(BigInteger bodId, BigInteger artId, BigInteger artIdTipo, String estado, String descripcion, String fecha, String operador);

    InvLoteArticulo crearLote(BigInteger bodId, BigInteger artId, InvLoteArticulo objNew);

    boolean verificaDuplicadoLote(BigInteger artId, String descripLote);

    List<InvLoteArticulo> obtieneLoteProxCaducar(BigInteger bodId, BigInteger artId, String estado);

    int verificaLoteCaducados(BigInteger bodId, String fecha, String filtro);
    
}
