/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.mil.armada.inv.servicios.registro;

import ec.mil.armada.inv.modelo.registro.RegCabliquidacion;
import ec.mil.armada.inv.remotos.registro.RegDetliquidacionFacadeRemote;
import ec.mil.armada.inv.servicios.abstractFacade.AbstractFacade;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author maria_rodriguez
 */
@Stateless
public class RegCabliquidacionFacade extends AbstractFacade<RegCabliquidacion> implements ec.mil.armada.inv.remotos.registro.RegCabliquidacionFacadeRemote {
    @EJB
    private RegDetliquidacionFacadeRemote regDetliquidacionFacade;
    @PersistenceContext(unitName = "ec.mil.armada_Inv-Servicios_ejb_1.0PU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public RegCabliquidacionFacade() {
        super(RegCabliquidacion.class);
    }
    @Override
    public RegCabliquidacion objRegCabliquidacion(BigInteger claveTabla, String estado, String siglaTabla) {

        RegCabliquidacion objeto = null;
        boolean isClaveTabla = claveTabla != null;
        boolean isSiglas = siglaTabla != null && siglaTabla.trim().length() > 0 && !(siglaTabla.trim().toLowerCase().compareTo("null") == 0);
        boolean isEstado = estado != null && estado.trim().length() > 0 && !(estado.trim().toLowerCase().compareTo("null") == 0);
        if (isClaveTabla && isSiglas && isEstado) {
            final Query query = em.createQuery("Select object (cl) from RegCabliquidacion cl  where cl.cliClavetabla = :claveTabla and cl.cliEstado = :estado and cl.cliSiglatabla = :siglaTabla  ");
            query.setParameter("claveTabla", claveTabla);
            query.setParameter("estado", estado);
            query.setParameter("siglaTabla", siglaTabla);
            query.setHint("eclipselink.refresh", true);
            final List<RegCabliquidacion> result = query.getResultList();
            if (result.size() > 0) {
                objeto = result.get(0);
            }
        }

        return objeto;
    }

    @Override
    public boolean verificaDuplicadoCab(BigInteger claveTabla, String siglaTabla, String estado) {
        boolean ctrInserta = false;
        boolean isClaveTabla = claveTabla != null;
        boolean isSiglas = siglaTabla != null && siglaTabla.trim().length() > 0 && !(siglaTabla.trim().toLowerCase().compareTo("null") == 0);
        boolean isEstado = estado != null && estado.trim().length() > 0 && !(estado.trim().toLowerCase().compareTo("null") == 0);
        if (isClaveTabla && isSiglas && isEstado) {
            final StringBuilder SQLNative = new StringBuilder();
            SQLNative.append(" SELECT COUNT(*) ");
            SQLNative.append("    FROM REG_CABLIQUIDACION CL ");
            SQLNative.append("   WHERE CL.CLI_CLAVETABLA = ").append(claveTabla);
            SQLNative.append("  AND CL.CLI_SIGLATABLA = '").append(siglaTabla).append("' ");
            SQLNative.append(" AND CL.CLI_ESTADO = '").append(estado).append("' ");
            final Query query = getEntityManager().createNativeQuery(SQLNative.toString());
            final Object obj = query.getSingleResult();
            final BigDecimal valor = (BigDecimal) (obj);
            if (valor.intValue() == 0) {
                ctrInserta = true;
            }

        }

        return ctrInserta;

    }

    @Override
    public RegCabliquidacion crearObjeto(BigInteger claveTabla, String siglasTabla, String observacion, String estado){
    RegCabliquidacion objNew = null;
    boolean isClaveTabla = claveTabla != null;
    boolean isSiglas =  siglasTabla != null && siglasTabla.trim().length() > 0 && !(siglasTabla.trim().toLowerCase().compareTo("null") == 0);
     boolean isEstado =  estado != null && estado.trim().length() > 0 && !(estado.trim().toLowerCase().compareTo("null") == 0);
    if(isClaveTabla && isSiglas && isEstado)
    {
         boolean ctrInserta = verificaDuplicadoCab(claveTabla, siglasTabla, estado);
             if (ctrInserta) {
                  //crear cabecera
        //obtiene anio actual(perido fiscal)
            final Calendar now = Calendar.getInstance();
            String pFiscal = String.valueOf(now.get(Calendar.YEAR));
            objNew =  new RegCabliquidacion();
            objNew.setCliAfiscal(pFiscal);
            objNew.setCliClavetabla(claveTabla);
            objNew.setCliEstado(estado);
            objNew.setCliFecha(new Date());
            objNew.setCliObservacion(observacion);
            objNew.setCliSiglatabla(siglasTabla);
            int numero = numeroDoc(pFiscal, claveTabla, siglasTabla, estado);
            if(numero != 0){
            objNew.setCliNumero(BigInteger.valueOf(numero));
            }
            create(objNew);
             }
             else{
             //existe el objeto recupera
                 objNew = objRegCabliquidacion(claveTabla, estado, siglasTabla);
             }
       

    
    }
     return objNew;
    }
    
    
    @Override
    public int numeroDoc(String aFiscal, BigInteger claveTabla, String siglasTabla, String estado) {
        //OBTIENE NUMERO POR AFISACL, CLAVE TABLA, ESTADO
        int numero = 0;
        final StringBuilder SQLNative = new StringBuilder();
         SQLNative.append(" SELECT COUNT(*) ");
        SQLNative.append("    FROM REG_CABLIQUIDACION CL ");
        SQLNative.append("   WHERE CL.CLI_CLAVETABLA = ").append(claveTabla);
         SQLNative.append("  AND CL.CLI_SIGLATABLA = '").append(siglasTabla).append( "' " );
        SQLNative.append(" AND CL.CLI_ESTADO = '").append(estado).append( "' " );
        final Query query = getEntityManager().createNativeQuery(SQLNative.toString());
        final Object obj = query.getSingleResult();
        final BigDecimal valor = (BigDecimal) (obj);
        if (valor != null) {
            numero = valor.intValue() + 1;
        }

        return numero;

    }
    
    @Override
    public RegCabliquidacion crearObjeto(BigInteger bodId, BigInteger servId, BigInteger claveTabla, String siglasTabla, 
            String observacion, String estado, Double subtotal, Double totalDesc, Double totalIva){
    RegCabliquidacion objNew = null;
    boolean isClaveTabla = claveTabla != null;
    boolean isSiglas =  siglasTabla != null && siglasTabla.trim().length() > 0 && !(siglasTabla.trim().toLowerCase().compareTo("null") == 0);
     boolean isEstado =  estado != null && estado.trim().length() > 0 && !(estado.trim().toLowerCase().compareTo("null") == 0);
    if(isClaveTabla && isSiglas && isEstado)
    {
         boolean ctrInserta = verificaDuplicadoCab(claveTabla, siglasTabla, estado);
             if (ctrInserta) {
                  //crear cabecera
        //obtiene anio actual(perido fiscal)
            final Calendar now = Calendar.getInstance();
            String pFiscal = String.valueOf(now.get(Calendar.YEAR));
            objNew =  new RegCabliquidacion();
            objNew.setCliBodId(bodId);
            objNew.setCliServId(servId);
            objNew.setCliAfiscal(pFiscal);
            objNew.setCliClavetabla(claveTabla);
            objNew.setCliEstado(estado);
            objNew.setCliFecha(new Date());
            objNew.setCliObservacion(observacion);
            objNew.setCliSiglatabla(siglasTabla);
            objNew.setCliSubTotal(subtotal);
            if(totalIva == null){
            objNew.setCliIva(0.0);
            }
            objNew.setCliDesc(totalDesc);
            if(subtotal != null){
            objNew.setCliPorc(subtotal*0.10);
            }
            Double suma = objNew.getCliSubTotal() + objNew.getCliIva() + objNew.getCliPorc();
            objNew.setCliTotal(suma - objNew.getCliDesc());
            int numero = numeroDoc(pFiscal, claveTabla, siglasTabla, estado);
            if(numero != 0){
            objNew.setCliNumero(BigInteger.valueOf(numero));
            }
            create(objNew);
             }
             else{
             //existe el objeto recupera
                 objNew = objRegCabliquidacion(claveTabla, estado, siglasTabla);
             }
       

    
    }
     return objNew;
    }
    
    
    @Override
    public void editCabLiq(RegCabliquidacion objeto, String valor){
        Double total = null;
        
        RegCabliquidacion objetoEdit = new RegCabliquidacion();
        boolean isValor = valor != null && valor.trim().length() > 0 && !(valor.trim().toLowerCase().compareTo("null") == 0);
        if (objeto.getCliId() != null && isValor) {
            objetoEdit = find(objeto.getCliId());
            if(objetoEdit != null){
            
            if (valor.equalsIgnoreCase("TO")) {
                total = regDetliquidacionFacade.sumaDetTotal(objeto.getCliId(), valor);
                if (total != null) {
                    objetoEdit.setCliSubTotal(redondear(total,4));
                    //modificar valores de total general
                    Double desc = redondear(total*0.10, 4);
                    objetoEdit.setCliPorc(desc);
                    actualizaValorTotal(objeto.getCliId());
                    valor = "IV";
                }
            }
            if (valor.equalsIgnoreCase("IV")) {
                total = regDetliquidacionFacade.sumaDetTotal(objeto.getCliId(), valor);
                if (total != null) {
                    objetoEdit.setCliIva(redondear(total,4));
                    //modificar valores de total general
                    actualizaValorTotal(objeto.getCliId());
                }
            }
            if (valor.equalsIgnoreCase("DE")) {
                total = regDetliquidacionFacade.sumaDetTotal(objeto.getCliId(), valor);
                if (total != null) {
                    objetoEdit.setCliDesc(redondear(total,4));
                    //modificar valores de total general
                    actualizaValorTotal(objeto.getCliId());
                    valor = "DE";
                }
            }
          
                edit(objetoEdit);
            }
    }
    }
    
    @Override
    public Double actualizaValorTotal(BigInteger clavecab) {
     //actualiza valor general de cabecera y redondea a 4 cifras decimales
        Double total = null;
        if (clavecab != null) {
            final StringBuilder SQLNative = new StringBuilder();
            SQLNative.append(" UPDATE REG_CABLIQUIDACION CL ");
            SQLNative.append(" SET CL.CLI_TOTAL =  ROUND((CL.CLI_SUBTOTAL + CL.CLI_IVA + CL.CLI_PORC) - CL.CLI_DESC, 4) ");
            SQLNative.append(" WHERE ");
            SQLNative.append(" CL.CLI_ID = ").append(clavecab);
            final Query query = getEntityManager().createNativeQuery(SQLNative.toString());
            query.executeUpdate();
           
        }

        return total;

    }
    
}
