/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.mil.armada.inv.remotos.inventario;

import ec.mil.armada.inv.modelo.catalago.CatArticulo;
import ec.mil.armada.inv.modelo.inventario.InvExistenciaBodega;
import java.math.BigInteger;
import java.util.List;
import javax.ejb.Remote;

/**
 *
 * @author maria_rodriguez
 */
@Remote
public interface InvExistenciaBodegaFacadeRemote {

    void create(InvExistenciaBodega invExistenciaBodega);

    void edit(InvExistenciaBodega invExistenciaBodega);

    void remove(InvExistenciaBodega invExistenciaBodega);

    InvExistenciaBodega find(Object id);

    List<InvExistenciaBodega> findAll();

    List<InvExistenciaBodega> findRange(int[] range);

    int count();

    List<InvExistenciaBodega> listAllByParam(BigInteger bodId, BigInteger artId, BigInteger artIdTipo, String estado, String descripcion, BigInteger existencia, String operador);

    CatArticulo objCatArticulo(BigInteger artId);

    boolean verificaDuplicadoCab(BigInteger bodId, BigInteger artId);

    Double calcular(Double valor1, Double valor2, String operacion);

    void editarExistenciabyIngreso(InvExistenciaBodega objeto, String valor, BigInteger cantidad, Double tIngreso);
    
   Double redondear(double numero,int digitos);

   String crearExistencia(BigInteger bodId, CatArticulo objart);

    InvExistenciaBodega crearExistencia(InvExistenciaBodega objeto);

   InvExistenciaBodega obtieneObjetoByParam(BigInteger bodId, BigInteger artId, String estado);

   List<InvExistenciaBodega> listSaldoByParam(BigInteger bodId, BigInteger artId, BigInteger artIdTipo, String estado, String descripcion, BigInteger existencia, String operador, String fechaIni, String fechaFin);

  List<InvExistenciaBodega> calcularStock(BigInteger bodId, BigInteger artId, BigInteger artIdTipo, String estado, String descripcion, String fechaIni, String fechaFin, int meses, int periodo, double porciento, String tipoStock);

   InvExistenciaBodega crearObjExistencia(BigInteger bodId, CatArticulo objart);
    
}
