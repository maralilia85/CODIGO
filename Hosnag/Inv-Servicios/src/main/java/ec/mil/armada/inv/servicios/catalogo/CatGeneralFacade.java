/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.mil.armada.inv.servicios.catalogo;

import ec.mil.armada.inv.servicios.abstractFacade.AbstractFacade;
import ec.mil.armada.inv.modelo.catalago.CatGeneral;
import java.math.BigDecimal;
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
public class CatGeneralFacade extends AbstractFacade<CatGeneral> implements ec.mil.armada.inv.remotos.catalogo.CatGeneralFacadeRemote {
    @PersistenceContext(unitName = "ec.mil.armada_Inv-Servicios_ejb_1.0PU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public CatGeneralFacade() {
        super(CatGeneral.class);
    }

   
    @Override
     public List<CatGeneral> listAllCatGeneralByParam(final String tipo, final BigInteger nivel, final BigInteger codigoPapa, final String estado, final String descripcion) {
        final StringBuilder SQLNative = new StringBuilder();
        SQLNative.append("SELECT H.GEN_ID,H.GEN_DESCRIPCION, H.GEN_SIGLATIPO, H.GEN_ESTADO, NVL(H.GEN_ID_FK,0)  GEN_ID_FK, NVL(H.GEN_NIVEL, 0) GEN_NIVEL, H.GEN_VALOR, H.GEN_OBSERVACION  ");
        SQLNative.append("   FROM CAT_GENERAL H");
        SQLNative.append("  LEFT JOIN CAT_GENERAL P ON P.GEN_ID = H.GEN_ID_FK ");
        SQLNative.append("   WHERE 1=1 ");
        boolean isCodigoPapa = codigoPapa != null;
        boolean isNivel = nivel != null;
        boolean isEstado = estado != null && estado.trim().length() > 0 && !(estado.trim().toLowerCase().compareTo("null") == 0);
        boolean isTipo = tipo != null && tipo.trim().length() > 0 && !(tipo.trim().toLowerCase().compareTo("null") == 0);
        boolean isDescripcion = descripcion != null && descripcion.trim().length() > 0 && !(descripcion.trim().toLowerCase().compareTo("null") == 0);

        //validar al menos que se cumpla una condicion para ejecutar el sql
        if (isDescripcion || isNivel) {
            if (isDescripcion) {
                SQLNative.append("       AND  H.GEN_DESCRIPCION ");
                SQLNative.append("   LIKE '%").append(descripcion.toUpperCase()).append("%'");
            }
            if (isTipo) {
                SQLNative.append("   AND P.GEN_SIGLATIPO = '").append(tipo).append("' ");
            }
            if (isNivel) {
                SQLNative.append("   AND H.GEN_NIVEL = ").append(nivel);
            }
            if (isCodigoPapa) {
                SQLNative.append("   AND H.GEN_ID_FK = ").append(codigoPapa);
            }

            if (isEstado) {
                SQLNative.append(" AND H.GEN_ESTADO =  '").append(estado).append("' ");
            }

            SQLNative.append("   ORDER BY H.GEN_ID ASC  ");
        } else {
            SQLNative.append(" AND 1=2   ");

        }

        final Query query = em.createNativeQuery(SQLNative.toString());
        final List<Object[]> datos = query.getResultList();
        final List<CatGeneral> datosRetorno = new ArrayList<>();
        for (final Object[] obj : datos) {
            int a = 0;
            final CatGeneral cat = new CatGeneral();
            cat.setGenId(new BigInteger(obj[a].toString()));
            a++;
            cat.setGenDescripcion((String) obj[a]);
            a++;
            cat.setGenSiglatipo((String) obj[a]);
            a++;
            cat.setGenEstado((String) obj[a]);
            a++;
            cat.setCatGenPapa(new CatGeneral());
            cat.getCatGenPapa().setGenId(new BigInteger(obj[a].toString()));
            a++;
            cat.setGenNivel(new BigInteger(obj[a].toString()));
            a++;
            if (obj[a] != null) {
                cat.setGenValor(((String) obj[a]));
            }
            a++;
            if (obj[a] != null) {
                cat.setGenObservacion(((String) obj[a]));
            }
            datosRetorno.add(cat);
        }
        return datosRetorno;
    }
     
  
    @Override
     public List<CatGeneral> listCatGeneralActivoByTipo(final String tipo) {
        final StringBuilder SQLNative = new StringBuilder();
        SQLNative.append(" SELECT H.GEN_ID,H.GEN_DESCRIPCION  ");
        SQLNative.append("   FROM CAT_GENERAL P");
        SQLNative.append("   LEFT JOIN CAT_GENERAL H ON P.GEN_ID = H.GEN_ID_FK");

        
        SQLNative.append("   WHERE  H.GEN_ESTADO = 'A' ");
        
        
        boolean isTipo = tipo != null && tipo.trim().length() > 0 && !(tipo.trim().toLowerCase().compareTo("null") == 0);

        //validar al menos que se cumpla una condicion para ejecutar el sql
        if (isTipo) {
                SQLNative.append(" AND P.GEN_SIGLATIPO = '").append(tipo).append("' ");
            SQLNative.append("   ORDER BY GEN_DESCRIPCION  ");
        } else {
            SQLNative.append(" AND 1=2   ");

        }

        final Query query = em.createNativeQuery(SQLNative.toString());
        final List<Object[]> datos = query.getResultList();
        final List<CatGeneral> datosRetorno = new ArrayList<>();
        for (final Object[] obj : datos) {
            int a = 0;
            final CatGeneral cat = new CatGeneral();
            cat.setGenId(new BigInteger(obj[a].toString()));
            a++;
            cat.setGenDescripcion((String) obj[a]);
            datosRetorno.add(cat);
        }
        return datosRetorno;
    }
     
    @Override
      public CatGeneral objCatGeneralPapaActivo(final String siglatipo) {
        CatGeneral objeto = null;
        final Query query = em.createQuery("Select object (g) from CatGeneral g where g.genSiglatipo = :siglatipo and g.genNivel=0  and g.genEstado='A' ");
        query.setParameter("siglatipo", siglatipo);
        query.setHint("eclipselink.refresh", true);
        final List<CatGeneral> result = query.getResultList();
        if (result.size() > 0) {
            objeto = result.get(0);
        }
        return objeto;
    }
      
    @Override
    public String descripcion(final String siglatipo) {
        //Para buscar parametros nivel 0 por sigla tipo
        String valor = null;
        final StringBuilder SQLNative = new StringBuilder();
        SQLNative.append(" SELECT *  FROM CAT_GENERAL I  ");
        SQLNative.append("   WHERE I.GEN_SIGLATIPO = '").append(siglatipo).append("' ");
        SQLNative.append("   AND I.GEN_ESTADO = 'A' ");
        final Query query = em.createNativeQuery(SQLNative.toString(), CatGeneral.class);
        final List<CatGeneral> lista = query.getResultList();
        if (lista.size() > 0) {
            valor = lista.get(0).getGenDescripcion();

        }
        return valor;
    }
    @Override
    public CatGeneral objByDescripcion(String descripcion) {
        CatGeneral obj = null;
        final StringBuilder SQLNative = new StringBuilder();
        SQLNative.append(" SELECT *  FROM CAT_GENERAL I  ");
        SQLNative.append("   WHERE I.GEN_DESCRIPCION = '").append(descripcion).append("' ");
        SQLNative.append("   AND I.GEN_ESTADO = 'A' ");
        final Query query = em.createNativeQuery(SQLNative.toString(), CatGeneral.class);
        final List<CatGeneral> lista = query.getResultList();
        if (lista.size() > 0) {
            obj = lista.get(0);

        }
        return obj;
    }
    @Override
     public Double valor(final String siglatipo) {
        //Para buscar parametros nivel 0 por sigla tipo
        Double valor = null;
        final StringBuilder SQLNative = new StringBuilder();
        SQLNative.append(" SELECT *  FROM CAT_GENERAL I  ");
        SQLNative.append("   WHERE I.GEN_SIGLATIPO = '").append(siglatipo).append("' ");
        SQLNative.append("   AND I.GEN_ESTADO = 'A' ");
        final Query query = em.createNativeQuery(SQLNative.toString(), CatGeneral.class);
        final List<CatGeneral> lista = query.getResultList();
        if (lista.size() > 0) {
            String v = lista.get(0).getGenValor();
            valor = Double.parseDouble(v);

        }
        return valor;
    }
    @Override
      public CatGeneral objCatGeneralPapaActivo(final String siglatipo, BigInteger nivel) {
        CatGeneral objeto = null;
        final StringBuilder SQLNative = new StringBuilder();
        SQLNative.append(" SELECT *  FROM CAT_GENERAL I  ");
        SQLNative.append("   WHERE I.GEN_SIGLATIPO = '").append(siglatipo).append("' ");
        SQLNative.append("   AND I.GEN_ESTADO = 'A' ");
        SQLNative.append("   AND I.GEN_NIVEL = ").append(nivel);
        final Query query = em.createNativeQuery(SQLNative.toString(), CatGeneral.class);
        final List<CatGeneral> result = query.getResultList();
        if (result.size() > 0) {
            objeto = result.get(0);
        }
        return objeto;
    }
}
