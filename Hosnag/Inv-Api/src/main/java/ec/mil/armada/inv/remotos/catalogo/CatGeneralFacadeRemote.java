/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.mil.armada.inv.remotos.catalogo;

import ec.mil.armada.inv.modelo.catalago.CatGeneral;
import java.math.BigInteger;
import java.util.List;
import javax.ejb.Remote;

/**
 *
 * @author maria_rodriguez
 */
@Remote
public interface CatGeneralFacadeRemote {

    void create(CatGeneral catGeneral);

    void edit(CatGeneral catGeneral);

    void remove(CatGeneral catGeneral);

    CatGeneral find(Object id);

    List<CatGeneral> findAll();

    List<CatGeneral> findRange(int[] range);

    int count();
     /**
     *
     * @param tipo siglas tipo marc(Marca)
     * @param nivel 0(Papa) 1 y 2 Hijos
     * @param codigoPapa recibe el codigo del papa recupera los hijos
     * @param estado A(Activo) I(Inactivo) null(Todos)
     * @param descripcion
     * @return lista todos los elementos del objetos
     */

    List<CatGeneral> listAllCatGeneralByParam(String tipo, BigInteger nivel, BigInteger codigoPapa, String estado, String descripcion);
    /**
     *
     * @param tipo siglas tipo del papa Marc
     * @return descrpcion  de los registros hijos de catalogo general
     */
    
    
    List<CatGeneral> listCatGeneralActivoByTipo(String tipo);

    /**
     *
     * @param siglatipo (MARC,MODE,FORM)
     * @return OBJETO CATALOGO GENERAL REGISTRO NIVEL 0 Y ACTIVO
     */
    CatGeneral objCatGeneralPapaActivo(String siglatipo);

   String descripcion(String siglatipo);

   Double valor(String siglatipo);

   CatGeneral objCatGeneralPapaActivo(String siglatipo, BigInteger nivel);

    CatGeneral objByDescripcion(String descripcion);
    
}
