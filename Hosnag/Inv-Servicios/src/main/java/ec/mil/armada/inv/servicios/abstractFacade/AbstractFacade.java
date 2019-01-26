/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.mil.armada.inv.servicios.abstractFacade;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.text.DecimalFormat;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.Query;

/**
 *
 * @author maria_rodriguez
 */
public abstract class AbstractFacade<T> {
    private Class<T> entityClass;
    private EntityManager em;

    public AbstractFacade(Class<T> entityClass) {
        this.entityClass = entityClass;
    }

    protected abstract EntityManager getEntityManager();

    public void create(T entity) {
        getEntityManager().persist(entity);
    }

    public void edit(T entity) {
        getEntityManager().merge(entity);
    }

    public void remove(T entity) {
        getEntityManager().remove(getEntityManager().merge(entity));
    }

    public T find(Object id) {
        return getEntityManager().find(entityClass, id);
    }

    public List<T> findAll() {
        javax.persistence.criteria.CriteriaQuery cq = getEntityManager().getCriteriaBuilder().createQuery();
        cq.select(cq.from(entityClass));
        return getEntityManager().createQuery(cq).getResultList();
    }

    public List<T> findRange(int[] range) {
        javax.persistence.criteria.CriteriaQuery cq = getEntityManager().getCriteriaBuilder().createQuery();
        cq.select(cq.from(entityClass));
        javax.persistence.Query q = getEntityManager().createQuery(cq);
        q.setMaxResults(range[1] - range[0] + 1);
        q.setFirstResult(range[0]);
        return q.getResultList();
    }

    public int count() {
        javax.persistence.criteria.CriteriaQuery cq = getEntityManager().getCriteriaBuilder().createQuery();
        javax.persistence.criteria.Root<T> rt = cq.from(entityClass);
        cq.select(getEntityManager().getCriteriaBuilder().count(rt));
        javax.persistence.Query q = getEntityManager().createQuery(cq);
        return ((Long) q.getSingleResult()).intValue();
    }
    public int countDepedientes(BigInteger codigoPapa, String tipoTabla) {
        int contador = 0;
        String argumento1 = "";
        String argumento2 = "";
        boolean isCodigoPapa = codigoPapa != null;
        boolean isTipoTabla = tipoTabla != null && tipoTabla.trim().length() > 0 && !(tipoTabla.trim().toLowerCase().compareTo("null") == 0);
        if (isTipoTabla) {
            if (tipoTabla.equalsIgnoreCase("B")) {
                argumento1 = " CAT_BODEGA B";
                argumento2 = " B.BOD_ID_FK = ";
            }

            final StringBuilder SQLNative = new StringBuilder();
            SQLNative.append(" SELECT COUNT(*) FROM ");
            SQLNative.append(argumento1);
            SQLNative.append(" WHERE ");
            SQLNative.append(argumento2).append(codigoPapa);

            final Query query = getEntityManager().createNativeQuery(SQLNative.toString());

            final Object obj = query.getSingleResult();
            final BigDecimal valor = (BigDecimal) (obj);
            if (valor != null) {
                contador = valor.intValue();
            }

           
        }
         return contador;
    }
    
     public int numeroDoc(String aFiscal, String argumento1, String argumento2, String tipoTabla) {
        int numero = 0;
        final StringBuilder SQLNative = new StringBuilder();
        SQLNative.append(" SELECT COUNT(*) FROM INV_CABTRANSACCION C  ");
        SQLNative.append("   FROM INV_CABTRANSACCION CT ");
        SQLNative.append("   TO_CHAR (C.CTR_FECHA,'YYYY') = '").append(aFiscal).append("' ");
        final Query query = getEntityManager().createNativeQuery(SQLNative.toString());
        final Object obj = query.getSingleResult();
        final BigDecimal valor = (BigDecimal) (obj);
        if (valor != null) {
            numero = valor.intValue();
        }

        return numero;

    }
     public Double calcular(Double valor1, Double valor2, String operacion){
        Double resultado = null;
        //Para formatear los lugares despues de la coma
        DecimalFormat objFormato = new DecimalFormat("#.####");

        if(operacion.equalsIgnoreCase("M"))
        {
            resultado = valor1 * valor2;
        }
        if(operacion.equalsIgnoreCase("S"))
        {
            resultado = valor1 + valor2;
        }
        if(operacion.equalsIgnoreCase("R"))
        {
            resultado = valor1 - valor2;
        }
        if(operacion.equalsIgnoreCase("D"))
        {
            resultado = valor1 / valor2;
        }
        
        return resultado;
    }
   
    public Double redondear(double numero,int digitos)
{
      int cifras=(int) Math.pow(10,digitos);
      return Math.rint(numero*cifras)/cifras;
}
    
}

