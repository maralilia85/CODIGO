/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.mil.armada.inv.servicios.catalogo;

import ec.mil.armada.inv.servicios.abstractFacade.AbstractFacade;
import ec.mil.armada.inv.modelo.catalago.CatEmpresa;
import ec.mil.armada.inv.modelo.catalago.CatBodega;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author maria_rodriguez
 */
@Stateless
public class CatBodegaFacade extends AbstractFacade<CatBodega> implements ec.mil.armada.inv.remotos.catalogo.CatBodegaFacadeRemote {
    @PersistenceContext(unitName = "ec.mil.armada_Inv-Servicios_ejb_1.0PU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public CatBodegaFacade() {
        super(CatBodega.class);
    }

    /**
     *
     * @param tipo B(Bodega) S(Seccion) P(Percha) C(Columna) N(Nivel)
     * @param nivel 0(Bodega) 1 y 2 otros
     * @param codigoPapa para recuperar los hijos
     * @param estado A(Activo) I(Inactivo)
     * @param descripcion 
     * @param empId Empresa a la q pertenece los registros
     * @return lista de bodega
     */
    @Override
     public List<CatBodega> listAllBodegaByParam(final String tipo, final BigInteger nivel, final BigInteger codigoPapa, final String estado, final String descripcion, final BigInteger empId) {
        final StringBuilder SQLNative = new StringBuilder();
        SQLNative.append("SELECT B.BOD_ID, B.EMP_ID , B.EML_ID, B.BOD_DESCRIPCION, B.BOD_TIPO, B.BOD_ESTADO, B.BOD_ID_FK, B.BOD_NIVEL, E.EML_NOMBRE  ");
        SQLNative.append("   FROM CAT_BODEGA B");
        SQLNative.append("   LEFT JOIN REG_EMPLEADO E ON E.EML_ID = B.EML_ID ");
        SQLNative.append("   WHERE 1=1 ");
        boolean isEmpresa = empId != null;
        boolean isCodigoPapa = codigoPapa != null;
        boolean isNivel = nivel != null;
        boolean isEstado = estado != null && estado.trim().length() > 0 && !(estado.trim().toLowerCase().compareTo("null") == 0);
        boolean isTipo = tipo != null && tipo.trim().length() > 0 && !(tipo.trim().toLowerCase().compareTo("null") == 0);
        boolean isDescripcion = descripcion != null && descripcion.trim().length() > 0 && !(descripcion.trim().toLowerCase().compareTo("null") == 0);

        //validar al menos que se cumpla una condicion para ejecutar el sql
        if ((isEmpresa && isNivel) || isCodigoPapa) {
            if (isDescripcion) {
                SQLNative.append("       AND  B.BOD_DESCRIPCION ");
                SQLNative.append("   LIKE '%").append(descripcion.toUpperCase()).append("%'");
            }
            if(isEmpresa){
            SQLNative.append("   AND B.EMP_ID = ").append(empId);
            }
            if (isTipo) {
                SQLNative.append("   AND B.BOD_TIPO = '").append(tipo).append("' ");
            }
            if (isNivel) {
                SQLNative.append("   AND B.BOD_NIVEL = ").append(nivel);
            }
            if (isCodigoPapa) {
                SQLNative.append("   AND B.BOD_ID_FK  = ").append(codigoPapa);
            }

            if (isEstado) {
                SQLNative.append(" AND B.BOD_ESTADO =  '").append(estado).append("' ");
            }

            SQLNative.append("   ORDER BY B.BOD_ID ASC  ");
        } else {
            SQLNative.append(" AND 1=2   ");

        }

        final Query query = em.createNativeQuery(SQLNative.toString());
        final List<Object[]> datos = query.getResultList();
        final List<CatBodega> datosRetorno = new ArrayList<>();
        for (final Object[] obj : datos) {
            int a = 0;
            final CatBodega bod = new CatBodega();
            bod.setBodId(new BigInteger(obj[a].toString()));
            a++;
             if(obj[a] != null){
            bod.setCatEmp(new CatEmpresa());
            bod.getCatEmp().setEmpId(new BigInteger(obj[a].toString()));}
            a++;
             if(obj[a] != null){
            bod.setEmlId(new BigInteger(obj[a].toString()));}
            a++;
            bod.setBodDescripcion((String) obj[a]);
            a++;
            bod.setBodTipo((String) obj[a]);
            a++;
            bod.setBodEstado((String) obj[a]);
            a++;
             if(obj[a] != null){
            bod.setBodPapa(new CatBodega());
            bod.getBodPapa().setBodId(new BigInteger(obj[a].toString()));}
            a++;
             if(obj[a] != null){
            bod.setBodNivel(new BigInteger(obj[a].toString()));}
            a++;
            bod.setNombreEmpleado((String) obj[a]);
            datosRetorno.add(bod);
        }
        return datosRetorno;
    }
    
    /**
     *
     * 
     * @param codigoPapa codigo del papa tabla recursiva 
     * @param empId Codigo de la empresa
     * @return descipcion de los registros hijos activos
     */

    @Override
     public List<CatBodega> listDescripcionByTipo(final BigInteger codigoPapa, final BigInteger empId) {
        final StringBuilder SQLNative = new StringBuilder();
        SQLNative.append("SELECT H.BOD_ID, H.BOD_DESCRIPCION  ");
        SQLNative.append("   FROM CAT_BODEGA H");
        SQLNative.append("   WHERE 1=1 ");
        boolean isEmpresa = empId != null;
        boolean isCodigoPapa = codigoPapa != null;

        //validar que se cumpla las condicion para ejecutar el sql
        if (isEmpresa) {
             SQLNative.append("   AND H.EMP_ID = ").append(empId);  
         
                //TRAER LOS REGISTROS DEPENDIENTES DEL CODIGO PAPA
            if(isCodigoPapa){
              SQLNative.append("  AND H.BOD_ID_FK = ").append(codigoPapa);
            }
            else{
             SQLNative.append("  AND H.BOD_ID_FK IS NULL ");
            }
            SQLNative.append("   AND H.BOD_ESTADO = 'A' ");
            SQLNative.append("   ORDER BY H.BOD_ID ASC  ");

        } else {
            SQLNative.append(" AND 1=2   ");

        }

        final Query query = em.createNativeQuery(SQLNative.toString());
        final List<Object[]> datos = query.getResultList();
        final List<CatBodega> datosRetorno = new ArrayList<>();
        for (final Object[] obj : datos) {
            int a = 0;
            final CatBodega bod = new CatBodega();
            bod.setBodId(new BigInteger(obj[a].toString()));
            a++;
            bod.setBodDescripcion((String) obj[a]);
            datosRetorno.add(bod);
        }
        return datosRetorno;
    }
    @Override
     public List<CatBodega> listDescripcionByTipo(final BigInteger codigoPapa, final BigInteger empId,String tipo) {
        final StringBuilder SQLNative = new StringBuilder();
        SQLNative.append("SELECT H.BOD_ID, H.BOD_DESCRIPCION  ");
        SQLNative.append("   FROM CAT_BODEGA H");
        SQLNative.append("   WHERE 1=1 ");
        boolean isEmpresa = empId != null;
        boolean isCodigoPapa = codigoPapa != null;
         boolean isTipo = tipo != null && tipo.trim().length() > 0 && !(tipo.trim().toLowerCase().compareTo("null") == 0);

        //validar que se cumpla las condicion para ejecutar el sql
        if (isEmpresa) {
             SQLNative.append("   AND H.EMP_ID = ").append(empId);  
             if(isTipo){
                 SQLNative.append("   AND H.BOD_TIPO = '").append(tipo).append("' ");
             
             }
         
                //TRAER LOS REGISTROS DEPENDIENTES DEL CODIGO PAPA
            if(isCodigoPapa){
              SQLNative.append("  AND H.BOD_ID_FK = ").append(codigoPapa);
            }
            else{
             SQLNative.append("  AND H.BOD_ID_FK IS NULL ");
            }
            SQLNative.append("   AND H.BOD_ESTADO = 'A' ");
            SQLNative.append("   ORDER BY H.BOD_ID ASC  ");

        } else {
            SQLNative.append(" AND 1=2   ");

        }

        final Query query = em.createNativeQuery(SQLNative.toString());
        final List<Object[]> datos = query.getResultList();
        final List<CatBodega> datosRetorno = new ArrayList<>();
        for (final Object[] obj : datos) {
            int a = 0;
            final CatBodega bod = new CatBodega();
            bod.setBodId(new BigInteger(obj[a].toString()));
            a++;
            bod.setBodDescripcion((String) obj[a]);
            datosRetorno.add(bod);
        }
        return datosRetorno;
    }
     
     
}
