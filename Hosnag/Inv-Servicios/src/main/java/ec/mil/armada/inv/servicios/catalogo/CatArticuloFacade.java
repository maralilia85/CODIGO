/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.mil.armada.inv.servicios.catalogo;

import com.sun.imageio.plugins.common.BogusColorSpace;
import ec.mil.armada.inv.servicios.abstractFacade.AbstractFacade;
import ec.mil.armada.inv.modelo.catalago.CatGeneral;
import ec.mil.armada.inv.modelo.catalago.CatArticulo;
import ec.mil.armada.inv.remotos.catalogo.CatArticuloFacadeRemote;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.security.PermitAll;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import sun.awt.image.PixelConverter;

/**
 *
 * @author maria_rodriguez
 */
@Stateless
@PermitAll
public class CatArticuloFacade extends AbstractFacade<CatArticulo> implements CatArticuloFacadeRemote {
    @PersistenceContext(unitName = "ec.mil.armada_Inv-Servicios_ejb_1.0PU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public CatArticuloFacade() {
        super(CatArticulo.class);
    }

    /**
     *
     * @param nivel 0(Tipo) 1(Subtipo) >=2(Articulos)
     * @param codigoPapa codigo del registro Tipo o Subtipo
     * @param estado A(Activo) I(Inactivo)
     * @param descripcion puede ser por codigos o nombres
     * @return Lista de Articulo
     */
    @Override
     public List<CatArticulo> listArticuloByParam(final BigInteger nivel, final BigInteger codigoPapa, final String estado, final String descripcion) {
        final StringBuilder SQLNative = new StringBuilder();
        SQLNative.append("SELECT A.ART_ID,A.ART_CODBARRA, A.ART_CODIGO, A.ART_ESTADO, A.ART_MSP, A.ART_FACTURABLE, A.ART_ID_FK , A.GENID_FORMA, A.GENID_MODELO, A.GENID_CONCEN, A.GENID_MARCA, A.ART_NOMBCOMERCIAL, A.ART_NOMBGENERICO, A.ART_NIVEL, A.ART_PARTIDA, P.ART_NOMBGENERICO, A.EML_ID, E.EML_NOMBRE,  ");
        SQLNative.append("   B.ART_ID, B.ART_NOMBGENERICO ");
        SQLNative.append("   FROM CAT_ARTICULO A");
        SQLNative.append("   LEFT JOIN CAT_ARTICULO P ON A.ART_ID_FK = P.ART_ID ");
        SQLNative.append("   LEFT JOIN CAT_ARTICULO B ON P.ART_ID_FK = B.ART_ID ");
        SQLNative.append("   LEFT JOIN REG_EMPLEADO E ON E.EML_ID = A.EML_ID ");
        SQLNative.append("   WHERE 1=1 ");
        boolean isCodigoPapa = codigoPapa != null;
        boolean isNivel = nivel != null;
        boolean isEstado = estado != null && estado.trim().length() > 0 && !(estado.trim().toLowerCase().compareTo("null") == 0);
        boolean isDescripcion = descripcion != null && descripcion.trim().length() > 0 && !(descripcion.trim().toLowerCase().compareTo("null") == 0);

        //validar al menos que se cumpla una condicion para ejecutar el sql
        if (isDescripcion || isNivel || isCodigoPapa) {
            if (isDescripcion) {
                SQLNative.append("       AND  A.ART_NOMBCOMERCIAL || ' '||  A.ART_NOMBGENERICO || ' '|| A.ART_CODIGO  || ' '|| A.ART_CODBARRA ");
                SQLNative.append("   LIKE '%").append(descripcion.toUpperCase()).append("%'");
            }
            
            if (isNivel) {
                SQLNative.append("   AND A.ART_NIVEL = ").append(nivel);
            }
            if (isCodigoPapa) {
                SQLNative.append("   AND A.ART_ID_FK = ").append(codigoPapa);
            }

            if (isEstado) {
                SQLNative.append(" AND A.ART_ESTADO =  '").append(estado).append("' ");
            }

            SQLNative.append("   ORDER BY A.ART_ID ASC  ");
        } else {
            SQLNative.append(" AND 1=2   ");

        }

        final Query query = em.createNativeQuery(SQLNative.toString());
        final List<Object[]> datos = query.getResultList();
        final List<CatArticulo> datosRetorno = new ArrayList<>();
        for (final Object[] obj : datos) {
            int a = 0;
            final CatArticulo cat = new CatArticulo();
            cat.setArtId(new BigInteger(obj[a].toString()));
            a++;
            cat.setArtCodbarra((String) obj[a]);
            a++;
            cat.setArtCodigo((String) obj[a]);
            a++;
            cat.setArtEstado((String) obj[a]);
            a++;
            cat.setArtMsp((String) obj[a]);
            a++;
            cat.setArtFacturable((String) obj[a]);
            a++;
            cat.setCatArtPapa(new CatArticulo());
             if(obj[a] != null){
            cat.getCatArtPapa().setArtId(new BigInteger(obj[a].toString()));}
            a++;
             if(obj[a] != null){
            cat.setCatGenForma(new CatGeneral());
            cat.getCatGenForma().setGenId(new BigInteger(obj[a].toString()));}
            a++;
             if(obj[a] != null){
            cat.setCatGenModelo(new CatGeneral());
            cat.getCatGenModelo().setGenId(new BigInteger(obj[a].toString()));}
            a++;
             if(obj[a] != null){
            cat.setCatGenConcen(new CatGeneral());
            cat.getCatGenConcen().setGenId(new BigInteger(obj[a].toString()));}
            a++;
            if(obj[a] != null){
            cat.setCatGenMarca(new CatGeneral());
            cat.getCatGenMarca().setGenId(new BigInteger(obj[a].toString()));}
            a++;
            cat.setArtNombcomercial((String) obj[a]);
            a++;
            cat.setArtNombgenerico((String) obj[a]);
            a++;
             if(obj[a] != null){
            cat.setArtNivel(new BigInteger(obj[a].toString()));}
            a++;
            cat.setArtPartida((String) obj[a]);
            a++;
            if(obj[a] != null){
            cat.getCatArtPapa().setArtNombgenerico((String) obj[a]);}
            a++;
            if(obj[a] != null){
            cat.setEmlId(new BigInteger(obj[a].toString()));}
            a++;
            cat.setNombreEmpleado((String) obj[a]);
            a++;
             if(obj[a] != null){
            cat.getCatArtPapa().setCatArtPapa(new CatArticulo());
            cat.getCatArtPapa().getCatArtPapa().setArtId(new BigInteger(obj[a].toString()));}
            a++;
             if(obj[a] != null){
            cat.getCatArtPapa().getCatArtPapa().setArtNombgenerico((String) obj[a]);}
            datosRetorno.add(cat);
        }
        return datosRetorno;
    }
     
    /**
     *
     * @param codigoPapa clave del padre para obteer los hijos si clave es null trae los padres
     * @param nivel 0 para los padres 1 subttipos y 2 para los articulos
     * @return descripcion por nivel y tipo
     */
    @Override
     public List<CatArticulo> listByTipoNivel(final BigInteger codigoPapa, final BigInteger nivel) {
        final StringBuilder SQLNative = new StringBuilder();
        SQLNative.append("SELECT A.ART_ID, A.ART_NOMBGENERICO  ");
        SQLNative.append("   FROM CAT_ARTICULO A");
        SQLNative.append("   WHERE 1=1 ");
       
        boolean isNivel = nivel != null;
         boolean isCodigoPapa = codigoPapa != null;

        //validar al menos que se cumpla una condicion para ejecutar el sql
        if (isNivel || isCodigoPapa) {
          
            if (isCodigoPapa) {
                SQLNative.append("  AND A.ART_ID_FK = ").append(codigoPapa);
            }
            
            if (isNivel) {
                SQLNative.append("   AND A.ART_NIVEL = ").append(nivel);
            }
      
            SQLNative.append(" AND A.ART_ESTADO =  'A' ");
           
            SQLNative.append("   ORDER BY A.ART_CODIGO  ");
        } else {
            //traer los registros padres nivel 0
            SQLNative.append(" AND A.ART_ID_FK IS NULL   ");

        }

        final Query query = em.createNativeQuery(SQLNative.toString());
        final List<Object[]> datos = query.getResultList();
        final List<CatArticulo> datosRetorno = new ArrayList<>();
        for (final Object[] obj : datos) {
            int a = 0;
            final CatArticulo cat = new CatArticulo();
            cat.setArtId(new BigInteger(obj[a].toString()));
            a++;
            cat.setArtNombgenerico((String) obj[a]);
            datosRetorno.add(cat);
        }
        return datosRetorno;
    }

    @Override
    public int numeroArticulo(BigInteger tipoArt, BigInteger subtipoArt) {
        //OBTIENE NUMERO TIPO DE ARTICULO y SUBTIPO
        System.out.println("entra a obtiene numero");
        int numero = 0;
        int nivel = 0;
        boolean isSubtipo = subtipoArt != null;
        boolean isTipo = tipoArt != null;
        final StringBuilder SQLNative = new StringBuilder();
        if (isSubtipo || isTipo) {
            if (isTipo) {
                nivel = 1;
                //crear codigo para subtipo nivel 1
                SQLNative.append(" SELECT MAX(CAST(A.ART_CODIGO AS integer)) ART_CODIGO FROM CAT_ARTICULO A ");
                SQLNative.append("   WHERE A.ART_NIVEL = 1  ");

            }
            if (isSubtipo) {
                nivel = 2;
                //crear codigo para el nivel 2 item
                SQLNative.append(" SELECT MAX(A.ART_CODIGO) ART_CODIGO FROM CAT_ARTICULO A ");
                SQLNative.append(" WHERE A.ART_ID_FK = ").append(subtipoArt);

            }

        } else {
            //es nivel 0 crear codigo del tipo
            SQLNative.append(" SELECT MAX (CAST(A.ART_CODIGO AS integer)) FROM CAT_ARTICULO A ");
            SQLNative.append("   WHERE A.ART_NIVEL = 0 OR A.ART_ID_FK IS NULL ");
        }
        final Query query = getEntityManager().createNativeQuery(SQLNative.toString());
        final Object obj = query.getSingleResult();
        if (obj != null) {
                if (nivel < 2) {
                final BigDecimal valor = (BigDecimal) (obj);
                numero = valor.intValue() + 1;

            } else {
                final String valor = (String) (obj);
                final int codigo = Integer.parseInt(valor);
                numero = codigo + 1;

            }

        }
        System.out.println("el numero es "+numero);

        return numero;

    }
  
    @Override
     public boolean verificaExisBySubtipo(BigInteger idSubtipo,BigInteger idTipo) {
        //verifica por cabecera registros nulos
        boolean ctrElimina = false;
        final StringBuilder SQLNative = new StringBuilder();
        if (idSubtipo != null) {
            //verifica existencia por subtipo
             SQLNative.append("   SELECT  count(*) FROM  INV_EXISTENCIA_BODEGA E ");
          SQLNative.append("  LEFT JOIN CAT_ARTICULO S on E.ART_ID = S.ART_ID  ");
        SQLNative.append("   WHERE E.EXI_EXISTENCIA > 0 ");
            SQLNative.append("   AND S.ART_ID_FK = ").append(idSubtipo);
        }
        else if(idTipo != null){
        //verifica si tipo tiene hijos
            SQLNative.append("   SELECT  count(*) FROM  CAT_ARTICULO T ");
        SQLNative.append("   WHERE T.ART_ID_FK = ").append(idTipo);
        }
   
        final Query query = getEntityManager().createNativeQuery(SQLNative.toString());
        final Object obj = query.getSingleResult();
        final BigDecimal valor = (BigDecimal) (obj);
        if (valor.intValue() == 0) {
            ctrElimina = true;
        }

        return ctrElimina;

    }
     
    @Override
     public List<CatArticulo> listTipoByExistencia(final BigInteger idBodega) {

        boolean isBodega = idBodega != null;
        final StringBuilder SQLNative = new StringBuilder();
        SQLNative.append(" SELECT T.ART_ID,  ");
        SQLNative.append("  (SELECT TN.ART_NOMBGENERICO FROM CAT_ARTICULO TN WHERE TN.ART_ID = T.ART_ID ) NOMBRE ");
        SQLNative.append("   FROM INV_EXISTENCIA_BODEGA E ");
        SQLNative.append("   LEFT JOIN CAT_ARTICULO A ON E.ART_ID = A.ART_ID ");
        SQLNative.append("   LEFT JOIN CAT_ARTICULO S ON A.ART_ID_FK = S.ART_ID ");
        SQLNative.append("   LEFT JOIN CAT_ARTICULO T ON S.ART_ID_FK = T.ART_ID ");
        SQLNative.append("   WHERE 1=1 ");

        //validar al menos que se cumpla una condicion para ejecutar el sql
        if (isBodega) {
            SQLNative.append(" AND  E.BOD_ID = ").append(idBodega);

            SQLNative.append(" AND T.ART_ESTADO =  'A' ");

            SQLNative.append(" GROUP BY T.ART_ID ");

        } else {
            SQLNative.append(" AND 1=2  ");
        }

        final Query query = em.createNativeQuery(SQLNative.toString());
        final List<Object[]> datos = query.getResultList();
        final List<CatArticulo> datosRetorno = new ArrayList<>();
        for (final Object[] obj : datos) {
            int a = 0;
            final CatArticulo cat = new CatArticulo();
            cat.setArtId(new BigInteger(obj[a].toString()));
            a++;
            cat.setArtNombgenerico((String) obj[a]);
            datosRetorno.add(cat);
        }
        return datosRetorno;
    }

  
     
}
