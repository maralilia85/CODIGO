/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.mil.armada.inv.servicios.registro;

import ec.mil.armada.inv.modelo.catalago.CatBodega;
import ec.mil.armada.inv.remotos.registro.RegCabtempFacadeRemote;
import ec.mil.armada.inv.modelo.registro.RegCabtemp;
import ec.mil.armada.inv.servicios.abstractFacade.AbstractFacade;
import java.math.BigInteger;
import java.util.Calendar;
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
public class RegCabtempFacade extends AbstractFacade<RegCabtemp> implements RegCabtempFacadeRemote {
    @PersistenceContext(unitName = "ec.mil.armada_Inv-Servicios_ejb_1.0PU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public RegCabtempFacade() {
        super(RegCabtemp.class);
    }
    @Override
    public RegCabtemp generaRegTemp(BigInteger bodId, String tipo, BigInteger noTem, BigInteger min, BigInteger max, String mes){
    RegCabtemp objNew = null;
    //valida si existe cabecera por parametros
    Calendar a = Calendar.getInstance();
    String anio = String.valueOf(a.get(Calendar.YEAR));
    objNew = objetoByBod(tipo, bodId, mes, anio, noTem);
    if(objNew == null){
    //crear
        objNew = new RegCabtemp();
    boolean isMes = mes != null && mes.trim().length() > 0 && !(mes.trim().toLowerCase().compareTo("null") == 0);
    
    objNew.setTemAnio(anio);
    objNew.setTemTipo(tipo);
    
    objNew.setBodId(bodId);
    
    if(noTem != null){
    objNew.setTemNoter(noTem);}
    
    if(min != null){
     objNew.setTemMin(min);
    }
    if(max != null){
     objNew.setTemMax(max);
    }
    if(isMes){
    objNew.setTemMes(mes);
    }
    create(objNew);
    
    }
    return  objNew;
    }
    
    @Override
   public CatBodega objCatBodega(BigInteger bodId){
    
        CatBodega objeto = null;

        final Query query = em.createQuery("Select object (b) from CatBodega b where b.bodId = :bodId  ");
        query.setParameter("bodId", bodId);
        query.setHint("eclipselink.refresh", true);
        final List<CatBodega> result = query.getResultList();
        if (result.size() > 0) {
            objeto = result.get(0);
        }
        return objeto;
    } 
   
    @Override
   public RegCabtemp objetoByBod(String tipo, BigInteger idBod, String mes, String a, BigInteger noTer) {

        RegCabtemp objeto = null;
        boolean isBod = idBod != null;
        boolean isNoTer = noTer != null;
         boolean isMes = mes != null && mes.trim().length() > 0 && !(mes.trim().toLowerCase().compareTo("null") == 0);
        boolean isA = a != null && a.trim().length() > 0 && !(a.trim().toLowerCase().compareTo("null") == 0);
        boolean istipo = tipo != null && tipo.trim().length() > 0 && !(tipo.trim().toLowerCase().compareTo("null") == 0);
        final StringBuilder sql = new StringBuilder();
        sql.append("SELECT * FROM REG_CABTEMP CT   ");
        sql.append("    WHERE 1=1  ");

        if (istipo && isBod) {
            if (istipo) {
                sql.append(" AND CT.TEM_TIPO =  '").append(tipo).append("' ");
            }
            if (isBod) {
                sql.append("    AND CT.BOD_ID =").append(idBod);
            }
            if (isNoTer) {
                sql.append("   AND CT.TEM_NOTER =  ").append(noTer);

            }
            if (isMes) {

                sql.append(" AND TEM_MES =  '").append(mes).append("' ");
            }
            if (isA) {
                sql.append(" AND TEM_ANIO =  '").append(a).append("' ");
            }

            final List<RegCabtemp> result = em.createNativeQuery(sql.toString(), RegCabtemp.class)
                    .setHint("eclipselink.refresh", true)
                    .getResultList();
            if (result.size() > 0) {
                objeto = result.get(0);
               objeto.setCatBodega(objCatBodega(result.get(0).getBodId()));
            }

        } else {
            sql.append("    WHERE 1=2  ");

        }
        return objeto;
    }

   
}
