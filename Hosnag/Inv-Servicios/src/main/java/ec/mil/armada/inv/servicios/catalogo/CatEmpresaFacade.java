/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.mil.armada.inv.servicios.catalogo;

import ec.mil.armada.inv.servicios.abstractFacade.AbstractFacade;
import ec.mil.armada.inv.modelo.catalago.CatEmpresa;
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
public class CatEmpresaFacade extends AbstractFacade<CatEmpresa> implements ec.mil.armada.inv.remotos.catalogo.CatEmpresaFacadeRemote {
    @PersistenceContext(unitName = "ec.mil.armada_Inv-Servicios_ejb_1.0PU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public CatEmpresaFacade() {
        super(CatEmpresa.class);
    }

    /**
     *
     * @param tipo P(Proveedor) H(Hospital)
     * @param estado A(Activo) I(Inactivo) null(Muestra todo)
     * @param descripcion
     * @return list Descripcion de empresa o proveedor
     */
    @Override
     public List<CatEmpresa> listEmpDescripcionByParam(final String tipo, final String estado, final String descripcion) {
        final StringBuilder SQLNative = new StringBuilder();
        SQLNative.append("SELECT EMP_ID, EMP_NOMBRE  ");
        SQLNative.append("   FROM CAT_EMPRESA P");
        SQLNative.append("   WHERE 1=1 ");
        boolean isEstado = estado != null && estado.trim().length() > 0 && !(estado.trim().toLowerCase().compareTo("null") == 0);
        boolean isTipo = tipo != null && tipo.trim().length() > 0 && !(tipo.trim().toLowerCase().compareTo("null") == 0);
        boolean isDescripcion = descripcion != null && descripcion.trim().length() > 0 && !(descripcion.trim().toLowerCase().compareTo("null") == 0);

        //validar al menos que se cumpla una condicion para ejecutar el sql
        if (isDescripcion || isTipo) {
            if (isDescripcion) {
                SQLNative.append("       AND  P.EMP_NOMBRE ");
                SQLNative.append("   LIKE '%").append(descripcion).append("%'");
            }
            if (isTipo) {
                SQLNative.append("  AND  P.EMP_TIPO = '").append(tipo).append("' ");
            }
          
            if (isEstado) {
                SQLNative.append(" AND P.EMP_ESTADO =  '").append(estado).append("' ");
            }

            SQLNative.append("   ORDER BY P.EMP_NOMBRE  ");
        } else {
            SQLNative.append(" AND 1=2   ");

        }

        final Query query = em.createNativeQuery(SQLNative.toString());
        final List<Object[]> datos = query.getResultList();
        final List<CatEmpresa> datosRetorno = new ArrayList<>();
        for (final Object[] obj : datos) {
            int a = 0;
            final CatEmpresa emp = new CatEmpresa();
            emp.setEmpId(new BigInteger(obj[a].toString()));
            a++;
            emp.setEmpNombre((String) obj[a]);
           datosRetorno.add(emp);
          
        }
        return datosRetorno;
    }
    
}
