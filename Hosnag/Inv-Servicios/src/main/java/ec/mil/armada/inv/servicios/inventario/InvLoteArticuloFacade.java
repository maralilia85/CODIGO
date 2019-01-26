/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.mil.armada.inv.servicios.inventario;

import com.sun.imageio.plugins.common.BogusColorSpace;
import ec.mil.armada.inv.modelo.catalago.CatArticulo;
import ec.mil.armada.inv.modelo.catalago.CatBodega;
import ec.mil.armada.inv.modelo.inventario.InvLoteArticulo;
import ec.mil.armada.inv.servicios.abstractFacade.AbstractFacade;
import java.math.BigDecimal;
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
public class InvLoteArticuloFacade extends AbstractFacade<InvLoteArticulo> implements ec.mil.armada.inv.remotos.inventario.InvLoteArticuloFacadeRemote {
    @PersistenceContext(unitName = "ec.mil.armada_Inv-Servicios_ejb_1.0PU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public InvLoteArticuloFacade() {
        super(InvLoteArticulo.class);
    }
    public CatArticulo objCatArticulo(BigInteger artId){
    
        CatArticulo objeto = null;

        final Query query = em.createQuery("Select object (a) from CatArticulo a where a.artId = :artId ");
        query.setParameter("artId", artId);
        query.setHint("eclipselink.refresh", true);
        final List<CatArticulo> result = query.getResultList();
        if (result.size() > 0) {
            objeto = result.get(0);
        }
        return objeto;
    }
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
    public List<InvLoteArticulo> listAllByParam(BigInteger bodId, BigInteger artId, BigInteger artIdTipo, String estado, String descripcion, String fecha, String operador) {
        final StringBuilder SQLNative = new StringBuilder();
        boolean isBodega = bodId != null;
        boolean isArticulo = artId != null;
        boolean isArtTipo = artIdTipo != null;
        boolean isEstado = estado != null && estado.trim().length() > 0 && !(estado.trim().toLowerCase().compareTo("null") == 0);
        boolean isDescripcion = descripcion != null && descripcion.trim().length() > 0 && !(descripcion.trim().toLowerCase().compareTo("null") == 0);
        boolean isfecha = fecha  != null && fecha.trim().length() > 0 && !(fecha.trim().toLowerCase().compareTo("null") == 0);
        boolean isOperador = operador != null && operador.trim().length() > 0 && !(operador.trim().toLowerCase().compareTo("null") == 0);

        SQLNative.append(" SELECT LA.LOT_ID ,LA.LOT_DESCRIPION ,LA.LOT_ESTADO,  ");
        SQLNative.append("   LA.LOT_FECHAELAB ,LA.LOT_FECHACADUC ,LA.LOT_REGSANITARIO , LA.LOT_CANTIDAD , LA.LOT_CANTINGRESO ,LA.LOT_CANTEGRESO , ");
        SQLNative.append("   LA.BOD_ID, LA.ART_ID ");
        SQLNative.append("   FROM INV_LOTE_ARTICULO LA ");
        SQLNative.append("   LEFT JOIN CAT_ARTICULO A ON LA.ART_ID = A.ART_ID ");
        SQLNative.append("   LEFT JOIN CAT_ARTICULO S ON A.ART_ID_FK = S.ART_ID ");
        SQLNative.append("   LEFT JOIN CAT_ARTICULO T ON T.ART_ID = S.ART_ID_FK ");
        SQLNative.append("   WHERE 1=1 ");
        if (isBodega || isArticulo || isArtTipo) {
            if (isBodega) {
                SQLNative.append("   AND LA.BOD_ID = ").append(bodId);

            }
            if (isArticulo) {
                SQLNative.append("   AND LA.ART_ID = ").append(artId);

            }
            if (isArtTipo) {
                SQLNative.append("   AND  T.ART_ID = ").append(artIdTipo);

            }
            if (isEstado) {
                SQLNative.append(" AND LA.LOT_ESTADO =  '").append(estado).append("' ");

            }
            if (isDescripcion) {
                SQLNative.append("       AND  A.ART_NOMBCOMERCIAL || ' '||  A.ART_NOMBGENERICO || ' '|| A.ART_CODIGO  || ' '|| A.ART_CODBARRA ");
                SQLNative.append("   LIKE '%").append(descripcion.toUpperCase()).append("%'");

            }
            if (isfecha && isOperador) {
                //Buscar por existencia
                if (operador.equalsIgnoreCase("=")) {
                     SQLNative.append(" AND TRUNC (LA.LOT_FECHACADUC) <= to_date('").append(fecha).append("','dd/mm/yyyy')");
                }
                if (operador.equalsIgnoreCase(">")) {
                     SQLNative.append(" AND TRUNC (LA.LOT_FECHACADUC) > to_date('").append(fecha).append("','dd/mm/yyyy')");
                }
                if (operador.equalsIgnoreCase("<")) {
                    SQLNative.append(" AND TRUNC (LA.LOT_FECHACADUC) <  to_date('").append(fecha).append("','dd/mm/yyyy')");
                   
                }

            }

        } else {
            SQLNative.append(" AND 1=2   ");

        }
final Query query = em.createNativeQuery(SQLNative.toString());
        final List<Object[]> datos = query.getResultList();
        final List<InvLoteArticulo> datosRetorno = new ArrayList<>();
        for (final Object[] obj : datos) {
            int a = 0;
            final InvLoteArticulo inv = new InvLoteArticulo();
            inv.setLotId(new BigInteger(obj[a].toString()));
            a++;
            inv.setLotDescripion((String) obj[a]);
            a++;
            inv.setLotEstado((String) obj[a]);
            a++;
             inv.setLotFechaelab((Date) obj[a]);
            a++;
             inv.setLotFechacaduc((Date) obj[a]);
            a++;
             inv.setLotRegsanitario((String) obj[a]);
            a++;
              if(obj[a] != null){
             inv.setLotCantidad(new BigInteger(obj[a].toString()));}
            a++;
            if(obj[a] != null){
             inv.setLotCantingreso(new BigInteger(obj[a].toString()));}
            a++;
             if(obj[a] != null){
             inv.setLotCantegreso(new BigInteger(obj[a].toString()));}
            a++;
            inv.setCatBodega(new CatBodega());
            inv.setBodId(new BigInteger(obj[a].toString()));
            inv.setCatBodega(objCatBodega(new BigInteger(obj[a].toString())));
            a++;
            inv.setCatArticulo(new CatArticulo());
            inv.setArtId(new BigInteger(obj[a].toString()));
            inv.setCatArticulo(objCatArticulo(new BigInteger(obj[a].toString())));
            
            datosRetorno.add(inv);
        }

        return datosRetorno;
    }
    
    @Override
    public InvLoteArticulo crearLote(BigInteger bodId, BigInteger artId, InvLoteArticulo objNew) {

        if (objNew.getLotId() == null) {
            //Crear lote
            //validar si existe lote con la misma descripcion y el mismo articulo
            //boolean ctrInserta = verificaDuplicadoLote(artId, objNew.getLotDescripion());//TODO SE QUITA VALIDACION POR SOLICITUD DEL USER
            boolean isBodega = bodId != null;
            boolean isArti = artId != null;
            if (isBodega && isArti) {
                objNew.setArtId(artId);
                objNew.setBodId(bodId);
                objNew.setLotEstado("A");
                create(objNew);

            }
           
        } else {
            //Editar lote
             InvLoteArticulo objEditar = find(objNew.getLotId());
             if(objEditar != null){
                 if(objNew.getLotCantegreso() != null){
                 objEditar.setLotCantegreso(objNew.getLotCantegreso());
                 }
                  if(objNew.getLotCantingreso() != null){
                 objEditar.setLotCantingreso(objNew.getLotCantingreso());
                 }
                 if(objNew.getLotCantidad() != null && objEditar.getLotCantegreso() != null){
                 BigInteger cantActual = objNew.getLotCantidad();
                 BigInteger cantidad;
                 cantidad = cantActual.subtract(objEditar.getLotCantegreso());
                 if(cantidad != null){
                 objEditar.setLotCantidad(cantidad);
                 }
                 }
                 if(objNew.getLotCantidad() != null && objEditar.getLotCantingreso() != null){
                 BigInteger cantActual = objNew.getLotCantidad();
                 BigInteger cantidad;
                 cantidad = cantActual.add(objEditar.getLotCantingreso());
                 if(cantidad != null){
                 objEditar.setLotCantidad(cantidad);
                 }
                 }
                 
                 if(objNew.getLotCosto() != null){
                 objEditar.setLotCosto(objNew.getLotCosto());
                 }
                 if(objNew.getLotDescripion() != null){
                 objEditar.setLotDescripion(objNew.getLotDescripion());
                 }
                 if(objNew.getLotEstado() != null){
                 objEditar.setLotEstado(objNew.getLotEstado());
                 }
                 if(objNew.getLotFechacaduc() != null){
                  objEditar.setLotFechacaduc(objNew.getLotFechacaduc());
                 }
                 if(objNew.getLotFechaelab() != null){
                  objEditar.setLotFechaelab(objNew.getLotFechaelab());
                 }
                 if(objNew.getLotRegsanitario() != null){
                  objEditar.setLotRegsanitario(objNew.getLotRegsanitario());
                 }
             
             }
            edit(objEditar);
        }

        return objNew;

    }
 
    @Override
 public boolean verificaDuplicadoLote(BigInteger artId, String descripLote){
        boolean ctrInserta = false;
        final StringBuilder SQLNative = new StringBuilder();
        SQLNative.append("   SELECT COUNT (*) FROM  ");
        SQLNative.append("   INV_LOTE_ARTICULO L ");
        SQLNative.append("   WHERE L.ART_ID = ").append(artId);
        SQLNative.append("   AND L.LOT_DESCRIPION =  '").append(descripLote).append("'");
         SQLNative.append("  AND L.LOT_ESTADO NOT IN ('I') ");
        final Query query = getEntityManager().createNativeQuery(SQLNative.toString());
        final Object obj = query.getSingleResult();
        final BigDecimal valor = (BigDecimal) (obj);
        if (valor.intValue() == 0) {
           ctrInserta = true;
        }

        return ctrInserta;

    
    }
 
    @Override
  public List<InvLoteArticulo> obtieneLoteProxCaducar(BigInteger bodId, BigInteger artId, String estado) {
      //OBTIENE EL LOTE PROXIMO A CADUCAR
        final StringBuilder SQLNative = new StringBuilder();
        boolean isBodega = bodId != null;
        boolean isArticulo = artId != null;
        boolean isEstado = estado != null && estado.trim().length() > 0 && !(estado.trim().toLowerCase().compareTo("null") == 0);
       

        SQLNative.append(" SELECT LA.LOT_ID ,LA.LOT_DESCRIPION ,LA.LOT_ESTADO,  ");
        SQLNative.append("   LA.LOT_FECHAELAB ,LA.LOT_FECHACADUC ,LA.LOT_REGSANITARIO , LA.LOT_CANTIDAD , LA.LOT_CANTINGRESO ,LA.LOT_CANTEGRESO , ");
        SQLNative.append("   LA.BOD_ID, LA.ART_ID ");
        SQLNative.append("   FROM INV_LOTE_ARTICULO LA ");
        SQLNative.append("   WHERE 1=1 ");
        if (isBodega && isArticulo) {
            if (isBodega) {
                SQLNative.append("   AND LA.BOD_ID = ").append(bodId);

            }
            if (isArticulo) {
                SQLNative.append("   AND LA.ART_ID = ").append(artId);
                SQLNative.append("   AND LA.LOT_FECHACADUC = (SELECT MIN (L.LOT_FECHACADUC) CADUCADO FROM INV_LOTE_ARTICULO L WHERE L.BOD_ID = " ).append(bodId).append(" AND  L.ART_ID = " ).append(artId).append(" AND L.LOT_CANTIDAD > 0 )");

            }
           
            if (isEstado) {
                SQLNative.append(" AND LA.LOT_ESTADO =  '").append(estado).append("' ");

            }
            SQLNative.append(" AND LA.LOT_CANTIDAD > 0  ");
          
          

        } else {
            SQLNative.append(" AND 1=2   ");

        }
final Query query = em.createNativeQuery(SQLNative.toString());
        final List<Object[]> datos = query.getResultList();
        final List<InvLoteArticulo> datosRetorno = new ArrayList<>();
        for (final Object[] obj : datos) {
            int a = 0;
            final InvLoteArticulo inv = new InvLoteArticulo();
            inv.setLotId(new BigInteger(obj[a].toString()));
            a++;
            inv.setLotDescripion((String) obj[a]);
            a++;
            inv.setLotEstado((String) obj[a]);
            a++;
             inv.setLotFechaelab((Date) obj[a]);
            a++;
             inv.setLotFechacaduc((Date) obj[a]);
            a++;
             inv.setLotRegsanitario((String) obj[a]);
            a++;
              if(obj[a] != null){
             inv.setLotCantidad(new BigInteger(obj[a].toString()));}
            a++;
            if(obj[a] != null){
             inv.setLotCantingreso(new BigInteger(obj[a].toString()));}
            a++;
             if(obj[a] != null){
             inv.setLotCantegreso(new BigInteger(obj[a].toString()));}
            a++;
            inv.setCatBodega(new CatBodega());
            inv.setBodId(new BigInteger(obj[a].toString()));
            inv.setCatBodega(objCatBodega(new BigInteger(obj[a].toString())));
            a++;
            inv.setCatArticulo(new CatArticulo());
            inv.setArtId(new BigInteger(obj[a].toString()));
            inv.setCatArticulo(objCatArticulo(new BigInteger(obj[a].toString())));
            
            datosRetorno.add(inv);
        }

        return datosRetorno;
    }
    @Override
    public int verificaLoteCaducados(BigInteger bodId, String fecha, String filtro) {
        int cont = -1;
        boolean isBodega = bodId != null;
        boolean isFecha = fecha != null && fecha.trim().length() > 0 && !(fecha.trim().toLowerCase().compareTo("null") == 0);
        boolean isFiltro = filtro != null && filtro.trim().length() > 0 && !(filtro.trim().toLowerCase().compareTo("null") == 0);

        final StringBuilder SQLNative = new StringBuilder();
        if (isBodega && isFecha && isFiltro) {
            SQLNative.append("   SELECT COUNT (*) FROM  ");
            SQLNative.append("   INV_LOTE_ARTICULO L ");
            SQLNative.append("   WHERE L.BOD_ID = ").append(bodId);
            SQLNative.append("  AND L.LOT_ESTADO NOT IN ('I') ");
            
            if (filtro.equalsIgnoreCase("C")) {
                //Buscar caducados
                SQLNative.append(" AND TRUNC (L.LOT_FECHACADUC) <= to_date('").append(fecha).append("','dd/mm/yyyy')");
            }
            if (filtro.equalsIgnoreCase("P")) {
                SQLNative.append(" AND TRUNC (L.LOT_FECHACADUC) > to_date('").append(fecha).append("','dd/mm/yyyy')");
            }

            final Query query = getEntityManager().createNativeQuery(SQLNative.toString());
            final Object obj = query.getSingleResult();
            final BigDecimal valor = (BigDecimal) (obj);
            if (valor != null) {
                cont = valor.intValue();
            }

        }

        return cont;

    }

}
