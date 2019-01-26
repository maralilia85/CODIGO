/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.mil.armada.inv.servicios.registro;

import ec.mil.armada.inv.modelo.catalago.CatDireccion;
import ec.mil.armada.inv.modelo.catalago.CatEmpresa;
import ec.mil.armada.inv.modelo.registro.RegEmpleado;
import ec.mil.armada.inv.servicios.abstractFacade.AbstractFacade;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author DET-PC
 */
@Stateless
public class RegEmpleadoFacade extends AbstractFacade<RegEmpleado> implements ec.mil.armada.inv.remotos.registro.RegEmpleadoFacadeRemote {
    @PersistenceContext(unitName = "ec.mil.armada_Inv-Servicios_ejb_1.0PU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public RegEmpleadoFacade() {
        super(RegEmpleado.class);
    }
    
    @Override
    public List<RegEmpleado> listEmpleadoByParam(final String descripcion, final BigInteger empresa, final BigInteger codigodir, final String estado) {
        final StringBuilder SQLNative = new StringBuilder();
        SQLNative.append("SELECT   EML_ID , EMP_ID , NVL(DIR_ID,0) DIR_ID , EML_NOMBRE , EML_CARGO , EML_TIPO ,EML_CEDULA  ,  EML_SEXO ,  EML_ESTADO  ");
        SQLNative.append("   FROM REG_EMPLEADO E");
        SQLNative.append("   WHERE 1=1 ");
        boolean isEmpresa = empresa != null;
        boolean iscodigoDir = codigodir != null;
        boolean isEstado = estado != null && estado.trim().length() > 0 && !(estado.trim().toLowerCase().compareTo("null") == 0);
         boolean isDescripcion = descripcion != null && descripcion.trim().length() > 0 && !(descripcion.trim().toLowerCase().compareTo("null") == 0);

        //validar al menos que se cumpla una condicion para ejecutar el sql
        if (isEmpresa && isDescripcion) {
            if (isDescripcion) {
                SQLNative.append("       AND  E.EML_NOMBRE || ' '||  E.EML_CEDULA ");
                SQLNative.append("   LIKE '%").append(descripcion.toUpperCase()).append("%'");
            }
            
            if (isEmpresa) {
                SQLNative.append("   AND E.EMP_ID = ").append(empresa);
            }
            if (iscodigoDir) {
                SQLNative.append("   AND E.DIR_ID = = ").append(codigodir);
            }

            if (isEstado) {
                SQLNative.append(" AND E.EML_ESTADO =  '").append(estado).append("' ");
            }

            SQLNative.append("   ORDER BY E.EMP_ID DESC  ");
        } else {
            SQLNative.append(" AND 1=2   ");

        }

        final Query query = em.createNativeQuery(SQLNative.toString());
        final List<Object[]> datos = query.getResultList();
        final List<RegEmpleado> datosRetorno = new ArrayList<>();
        for (final Object[] obj : datos) {
            int a = 0;
            final RegEmpleado emp = new RegEmpleado();
            emp.setEmlId(new BigInteger(obj[a].toString()));
            a++;
            emp.setCatEmpresa(new CatEmpresa());
            emp.getCatEmpresa().setEmpId(new BigInteger(obj[a].toString()));
            a++;
            emp.setCatDireccion(new CatDireccion());
            emp.getCatDireccion().setDirId(new BigInteger(obj[a].toString()));
            a++;
            emp.setEmlNombre((String) obj[a]);
            a++;
            emp.setEmlCargo((String) obj[a]);
            a++;
            emp.setEmlTipo((String) obj[a]);
            a++;            
            emp.setEmlCedula((String) obj[a]);
            a++;
            emp.setEmlSexo((String) obj[a]);
            a++;
            emp.setEmlEstado((String) obj[a]);
            a++;
            datosRetorno.add(emp);
        }
        return datosRetorno;
    }


    @Override
    public String nombreEmpleado(BigInteger emlId){
        String nombre = null;
        final Query query = em.createQuery("Select e.emlNombre from RegEmpleado e where e.emlId = :emlId  ");
        query.setParameter("emlId", emlId);
        final Object result = query.getSingleResult();
        if (result != null) {
            nombre = (String) result;
        }
        return nombre;
    }
    
    
}
