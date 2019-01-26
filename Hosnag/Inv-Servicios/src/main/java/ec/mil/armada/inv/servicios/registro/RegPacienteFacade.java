/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.mil.armada.inv.servicios.registro;

import ec.mil.armada.inv.modelo.catalago.CatArticulo;
import ec.mil.armada.inv.modelo.catalago.CatBodega;
import ec.mil.armada.inv.modelo.catalago.CatGeneral;
import ec.mil.armada.inv.modelo.registro.RegPaciente;
import ec.mil.armada.inv.servicios.abstractFacade.AbstractFacade;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Date;
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
public class RegPacienteFacade extends AbstractFacade<RegPaciente> implements ec.mil.armada.inv.remotos.registro.RegPacienteFacadeRemote {
    @PersistenceContext(unitName = "ec.mil.armada_Inv-Servicios_ejb_1.0PU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public RegPacienteFacade() {
        super(RegPaciente.class);
    }
    @Override
      public List<RegPaciente>  objPacByParam(final String descripcion, final String estado) {
        final StringBuilder SQLNative = new StringBuilder();
        SQLNative.append("SELECT P.PAC_ID, P.PAC_NOMBREAPELLIDOS, P.PAC_HC, P.PAC_CEDULA, P.PAC_SEXO, P.PAC_FECHANACIMIENTO, P.PAC_IDSEGURO, P.PAC_IDSITUACION, P.PAC_CODIGOISSFA, P.PAC_ESTADO ");
        SQLNative.append("   FROM REG_PACIENTE P ");
        
        SQLNative.append("   WHERE 1=1 ");
       
        boolean isEstado = estado != null && estado.trim().length() > 0 && !(estado.trim().toLowerCase().compareTo("null") == 0);
        boolean isDescripcion = descripcion != null && descripcion.trim().length() > 0 && !(descripcion.trim().toLowerCase().compareTo("null") == 0);

        //validar al menos que se cumpla una condicion para ejecutar el sql
        if (isDescripcion && isEstado) {
            if (isDescripcion) {
                SQLNative.append("       AND P.PAC_CEDULA  || ' ' || P.PAC_HC || ' ' || P.PAC_NOMBREAPELLIDOS ");
                SQLNative.append("   LIKE '%").append(descripcion.toUpperCase()).append("%'");
            }
            
          

            if (isEstado) {
                SQLNative.append(" AND P.PAC_ESTADO =  '").append(estado).append("' ");
            }

            SQLNative.append("  ORDER BY P.PAC_ID  ");
        } else {
            SQLNative.append(" AND 1=2   ");

        }

        final Query query = em.createNativeQuery(SQLNative.toString());
        final List<Object[]> datos = query.getResultList();
        final List<RegPaciente> datosRetorno = new ArrayList<>();
        for (final Object[] obj : datos) {
            int a = 0;
            final RegPaciente cat = new RegPaciente();
            cat.setPacId(new BigInteger(obj[a].toString()));
            a++;
            
            if(obj[a] != null){
            cat.setPacNombreApellidos((String) obj[a]);}
            a++;
            if(obj[a] != null){
            cat.setPacHc((String) obj[a]);}
            a++;
            if(obj[a] != null){
            cat.setPacCedula((String) obj[a]);}
            a++;
            if(obj[a] != null){
            cat.setPacSexo((String) obj[a]);}
            a++;
             if(obj[a] != null){
            cat.setPacFechanacimiento((Date) obj[a]);}
            a++;
            if(obj[a] != null){
            cat.setPacIdSeguro(new BigInteger(obj[a].toString()));
            cat.setPacSeguro(objCatGeneral(new BigInteger(obj[a].toString())).getGenDescripcion());
            }
            a++;
            if(obj[a] != null){
            cat.setPacSituacion(objCatGeneral(new BigInteger(obj[a].toString())).getGenDescripcion());
            }
            a++;
             if(obj[a] != null){
            cat.setPacCodigoIssfa((String) obj[a]);}
             a++;
             if(obj[a] != null){
            cat.setPacEstado((String) obj[a]);}
            
            datosRetorno.add(cat);
        }
        return datosRetorno;
    }
    
    @Override
     public CatGeneral objCatGeneral(BigInteger genId){
    
        CatGeneral objeto = null;

        final Query query = em.createQuery("Select object (g) from CatGeneral g where g.genId = :genId  ");
        query.setParameter("genId", genId);
        query.setHint("eclipselink.refresh", true);
        final List<CatGeneral> result = query.getResultList();
        if (result.size() > 0) {
            objeto = result.get(0);
        }
        return objeto;
    }
}
