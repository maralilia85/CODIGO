/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.mil.armada.inv.servicios.registro;

import ec.mil.armada.inv.modelo.registro.RegCabtemp;
import ec.mil.armada.inv.modelo.registro.RegDettemp;
import ec.mil.armada.inv.servicios.abstractFacade.AbstractFacade;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
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
public class RegDettempFacade extends AbstractFacade<RegDettemp> implements ec.mil.armada.inv.remotos.registro.RegDettempFacadeRemote {
    @PersistenceContext(unitName = "ec.mil.armada_Inv-Servicios_ejb_1.0PU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public RegDettempFacade() {
        super(RegDettemp.class);
    }
    @Override
    public void crearDetByCabReg(BigInteger idCab, Date fecha) {
        RegDettemp objNew = new RegDettemp();
        //valida que no exista registro con la misma fecha
        Format formatter = new SimpleDateFormat("dd/MM/yyyy");
        String f = formatter.format(fecha);

        if (idCab != null) {
            boolean inserta = verificaDuplicado(idCab, f);
            if (inserta) {

                RegCabtemp objCab = objRegCab(idCab);
                if (objCab != null) {
                    objNew.setRegCabtemp(objCab);
                    objNew.setDtemFecha(fecha);
                    create(objNew);
                }
            }

        }

    }
   
    @Override
   public void editDetTemp(RegDettemp obj, String valor){
   if(obj != null && obj.getDtemId() != null){
   RegDettemp objEdit = new RegDettemp();
   objEdit = find(obj.getDtemId());
   boolean isValor = valor != null && valor.trim().length() > 0 && !(valor.trim().toLowerCase().compareTo("null") == 0);
        
   if(objEdit != null && isValor){
   if(valor.equalsIgnoreCase("OB")){
   objEdit.setDtemObseracion(obj.getDtemObseracion());
   }
    if(valor.equalsIgnoreCase("AM")){
   objEdit.setDtemAm(obj.getDtemAm());
   }
    if(valor.equalsIgnoreCase("PM")){
   objEdit.setDtemPm(obj.getDtemPm());
   }
    if(objEdit.getDtemAm() != null && objEdit.getDtemPm() != null){
    //calula promedio
      BigInteger vSuma = objEdit.getDtemAm().add(objEdit.getDtemPm());
      objEdit.setDtemProm(new Double(vSuma.toString()));
              
   }
       edit(objEdit);
   }
   }
   }
    @Override
    public List<RegDettemp>  listRegDetByCab(final BigInteger claveCab, final String fecha) {
        final StringBuilder SQLNative = new StringBuilder();
        SQLNative.append("SELECT DT.DTEM_ID, DT.TEM_ID, DT.DTEM_FECHA, DT.DTEM_AM, DT.DTEM_PM, DT.DTEM_PROM, DT.DTEM_OBSERACION  ");
                SQLNative.append("   FROM REG_DETTEMP DT ");

        
        SQLNative.append("   WHERE 1=1 ");
        boolean isClaveCab = claveCab != null;
        boolean isFecha = fecha != null && fecha.trim().length() > 0 && !(fecha.trim().toLowerCase().compareTo("null") == 0);
        //validar al menos que se cumpla una condicion para ejecutar el sql
        if (isClaveCab) {
             SQLNative.append("   AND DT.TEM_ID = ").append(claveCab);
             
            if (isFecha) {
               
                 SQLNative.append(" AND TRUNC (DT.DTEM_FECHA) = to_date('").append(fecha).append("','dd/mm/yyyy')");
            }

            SQLNative.append("  ORDER BY DT.DTEM_ID  ");
        } else {
            SQLNative.append(" AND 1=2   ");

        }

        final Query query = em.createNativeQuery(SQLNative.toString());
        final List<Object[]> datos = query.getResultList();
        final List<RegDettemp> datosRetorno = new ArrayList<>();
        for (final Object[] obj : datos) {
            int a = 0;
            final RegDettemp cat = new RegDettemp();
            cat.setDtemId(new BigInteger(obj[a].toString()));
            a++;
             if(obj[a] != null){
            cat.setRegCabtemp(new RegCabtemp());
            cat.setRegCabtemp(objRegCab(new BigInteger(obj[a].toString())));
             }
            a++;
                   if(obj[a] != null){
            cat.setDtemFecha((Date) obj[a]);}
            a++;
            if(obj[a] != null){
            cat.setDtemAm(new BigInteger(obj[a].toString()));}
            a++;
            if(obj[a] != null){
            cat.setDtemPm(new BigInteger(obj[a].toString()));}
            a++;
            if(obj[a] != null){
            cat.setDtemProm(((BigDecimal) obj[a]).doubleValue());
            }
            a++;
             if(obj[a] != null){
            cat.setDtemObseracion((String) obj[a]);}
            
            datosRetorno.add(cat);
        }
        return datosRetorno;
    }
    
    @Override
     public RegCabtemp objRegCab(BigInteger cliId) {

        RegCabtemp objeto = null;

        final Query query = em.createQuery("Select object (c) from RegCabtemp c where c.temId = :cliId  ");
        query.setParameter("cliId", cliId);
        query.setHint("eclipselink.refresh", true);
        final List<RegCabtemp> result = query.getResultList();
        if (result.size() > 0) {
            objeto = result.get(0);
        }
        return objeto;
    } 
     
    @Override
      public boolean verificaDuplicado(BigInteger cab, String fecha) {
        //verifica por cabecera registros nulos
        boolean ctrInserta = false;
         boolean isFecha = fecha != null && fecha.trim().length() > 0 && !(fecha.trim().toLowerCase().compareTo("null") == 0);
       
        final StringBuilder SQLNative = new StringBuilder();
        SQLNative.append("   SELECT count(*) FROM REG_DETTEMP DT  ");
        SQLNative.append("   WHERE 1=1 ");
        if(cab != null){
         SQLNative.append("   AND DT.TEM_ID = ").append(cab);
        }
        if (isFecha) {
            SQLNative.append(" AND TRUNC (DT.DTEM_FECHA) = to_date('").append(fecha).append("','dd/mm/yyyy')");
            }
        final Query query = getEntityManager().createNativeQuery(SQLNative.toString());
        final Object obj = query.getSingleResult();
        final BigDecimal valor = (BigDecimal) (obj);
        if (valor.intValue() == 0) {
            ctrInserta = true;
        }

        return ctrInserta;

    }
 
   
}
