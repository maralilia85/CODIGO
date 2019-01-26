/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.mil.armada.inv.servicios.inventario;

import ec.mil.armada.inv.remotos.inventario.InvExistenciaBodegaFacadeRemote;
import ec.mil.armada.inv.modelo.catalago.CatArticulo;
import ec.mil.armada.inv.modelo.catalago.CatBodega;
import ec.mil.armada.inv.modelo.inventario.InvExistenciaBodega;
import ec.mil.armada.inv.servicios.abstractFacade.AbstractFacade;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
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
public class InvExistenciaBodegaFacade extends AbstractFacade<InvExistenciaBodega> implements InvExistenciaBodegaFacadeRemote {
    @PersistenceContext(unitName = "ec.mil.armada_Inv-Servicios_ejb_1.0PU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public InvExistenciaBodegaFacade() {
        super(InvExistenciaBodega.class);
    }
    @Override
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
    public List<InvExistenciaBodega> listAllByParam(BigInteger bodId, BigInteger artId, BigInteger artIdTipo, String estado, String descripcion, BigInteger existencia, String operador) {
        final StringBuilder SQLNative = new StringBuilder();
        boolean isBodega = bodId != null;
        boolean isArticulo = artId != null;
        boolean isArtTipo = artIdTipo != null;
        boolean isEstado = estado != null && estado.trim().length() > 0 && !(estado.trim().toLowerCase().compareTo("null") == 0);
        boolean isDescripcion = descripcion != null && descripcion.trim().length() > 0 && !(descripcion.trim().toLowerCase().compareTo("null") == 0);
        boolean isExistencia = existencia != null;
        boolean isOperador = operador != null && operador.trim().length() > 0 && !(operador.trim().toLowerCase().compareTo("null") == 0);

        SQLNative.append(" SELECT EB.EXI_ID ,EB.EXI_EXISTENCIA ,  ");
        SQLNative.append("   EB.EXI_COMPROMETIDO , EB.EXI_STOCKMIN  ,EB.EXI_STOCKMAX  , ");
        SQLNative.append("   EB.EXI_PRECIOCOSTO , EB.EXI_PRECIOVENTA ,EB.EXI_COSTOPROM  , ");
        SQLNative.append("   EB.EXI_ESTADO , EB.EXI_STOCKSEG , EB.EXI_COSTOINICIAL ,EB.EXI_CANTIDADINICIAL, ");
        SQLNative.append("   EB.ART_ID , EB.BOD_ID, EB.BOD_ID_UBI ");
        SQLNative.append("   FROM INV_EXISTENCIA_BODEGA EB ");
        SQLNative.append("  LEFT JOIN CAT_BODEGA B ON EB.BOD_ID = B.BOD_ID ");
        SQLNative.append("   LEFT JOIN CAT_ARTICULO A ON EB.ART_ID = A.ART_ID ");
        SQLNative.append("   LEFT JOIN CAT_ARTICULO S ON A.ART_ID_FK = S.ART_ID ");
        SQLNative.append("   LEFT JOIN CAT_ARTICULO T ON T.ART_ID = S.ART_ID_FK ");
        SQLNative.append("   WHERE 1=1 ");
        if (isBodega || isArticulo || isArtTipo || isDescripcion ) {
            if (isBodega) {
                SQLNative.append("   AND EB.BOD_ID = ").append(bodId);

            }
            if (isArticulo) {
                SQLNative.append("   AND EB.ART_ID = ").append(artId);

            }
            if(isArtTipo){
             SQLNative.append("   AND  T.ART_ID = ").append(artIdTipo);
            }
            if (isEstado) {
                SQLNative.append(" AND EB.EXI_ESTADO =  '").append(estado).append("' ");

            }
            if (isDescripcion) {
                SQLNative.append("       AND  A.ART_NOMBCOMERCIAL || ' '||  A.ART_NOMBGENERICO || ' '|| A.ART_CODIGO  || ' '|| A.ART_CODBARRA ");
                SQLNative.append("   LIKE '%").append(descripcion.toUpperCase()).append("%'");

            }
            if (isExistencia && isOperador) {
                //Buscar por existencia
                if (operador.equalsIgnoreCase("IG")) {
                    SQLNative.append(" AND EB.EXI_EXISTENCIA =  ").append(existencia);
                }
                if (operador.equalsIgnoreCase("MA")) {
                    SQLNative.append(" AND EB.EXI_EXISTENCIA >  ").append(existencia);
                }
                if (operador.equalsIgnoreCase("ME")) {
                    SQLNative.append(" AND EB.EXI_EXISTENCIA <   ").append(existencia);
                }

            }

        } else {
            SQLNative.append(" AND 1=2   ");

        }
        final Query query = em.createNativeQuery(SQLNative.toString());
        final List<Object[]> datos = query.getResultList();
        final List<InvExistenciaBodega> datosRetorno = new ArrayList<>();
        for (final Object[] obj : datos) {
            int a = 0;
            final InvExistenciaBodega inv = new InvExistenciaBodega();
            inv.setExiId(new BigInteger(obj[a].toString()));
            a++;
            if(obj[a] != null ){
            inv.setExiExistencia(new BigInteger(obj[a].toString()));
            }
            
            a++;
              if(obj[a] != null ){
            inv.setExiComprometido(new BigInteger(obj[a].toString()));}
            a++;
              if(obj[a] != null ){
             inv.setExiStockmin(new BigInteger(obj[a].toString()));}
            a++;
              if(obj[a] != null ){
            inv.setExiStockmax(new BigInteger(obj[a].toString()));}
            a++;
              if(obj[a] != null ){
             inv.setExiPreciocosto(((BigDecimal) obj[a]).doubleValue());}
            a++;
              if(obj[a] != null ){
            inv.setExiPrecioventa(((BigDecimal) obj[a]).doubleValue());}
            a++;
              if(obj[a] != null ){
            inv.setExiCostoprom(((BigDecimal) obj[a]).doubleValue());}
            a++;
              if(obj[a] != null ){
            inv.setExiEstado((String) obj[a]);}
            a++;
              if(obj[a] != null ){
            inv.setExiStockseg(new BigInteger(obj[a].toString()));}
            a++;
              if(obj[a] != null ){
            inv.setExiCostoinicial(((BigDecimal) obj[a]).doubleValue());}
            a++;
              if(obj[a] != null ){
            inv.setExiCantidadinicial(new BigInteger(obj[a].toString()));}
            a++;
              if(obj[a] != null ){
            inv.setCatArticulo(new CatArticulo());
            inv.setCatArticulo(objCatArticulo(new BigInteger(obj[a].toString())));}
            a++;

              if(obj[a] != null ){
            inv.setCatBodega(new CatBodega());
            inv.setCatBodega(objCatBodega(new BigInteger(obj[a].toString())));}
            a++;
            
            if(obj[a] != null){
            inv.setCatBodegaUbi(new CatBodega());
            inv.setCatBodegaUbi(objCatBodega(new BigInteger(obj[a].toString())));
            }
            datosRetorno.add(inv);
        }

        return datosRetorno;
    }

    @Override
   public InvExistenciaBodega crearExistencia(InvExistenciaBodega objeto){
   InvExistenciaBodega objetoNew = null;
   boolean ctrInserta = verificaDuplicadoCab(objeto.getBodId(), objeto.getCatArticulo().getArtId());
    if(ctrInserta){
        try {
        objetoNew = new InvExistenciaBodega();
        objetoNew.setBodId(objeto.getBodId());
        objetoNew.setExiEstado("A");
        create(objetoNew);
        } catch (Exception e) {
        }
              
    }else{
        //existe registro con los valores enviados(registros duplicados)
        objetoNew = new InvExistenciaBodega();
        objetoNew.setExiId(new BigInteger("-1"));
    }
    
    
   
   return objetoNew;
   }
   
    @Override
   public String crearExistencia(BigInteger bodId, CatArticulo objart){
   InvExistenciaBodega objetoNew = null;
   String mensaje = null;
   if(bodId != null && objart != null){
       boolean ctrInserta = verificaDuplicadoCab(bodId, objart.getArtId() );
    if(ctrInserta){
        try {
        objetoNew = new InvExistenciaBodega();
        objetoNew.setBodId(bodId);
        objetoNew.setCatArticulo(objart);
        objetoNew.setExiExistencia(BigInteger.ZERO);
        objetoNew.setExiCantidadinicial(BigInteger.ZERO);
        objetoNew.setExiComprometido(BigInteger.ZERO);
        objetoNew.setExiPreciocosto(0.0);
        objetoNew.setExiPrecioventa(0.0);
        objetoNew.setExiStockmax(BigInteger.ZERO);
        objetoNew.setExiStockmin(BigInteger.ZERO);
        objetoNew.setExiStockseg(BigInteger.ZERO);
        objetoNew.setExiCostoinicial(0.0);
        objetoNew.setExiCostoprom(0.0);
        objetoNew.setExiEstado("A");
        create(objetoNew);
        mensaje= "El artículo se creó en la bodega.";
        } catch (Exception e) {
        }
              
    }
    else{
    mensaje= "Existe el articulo en la bodega.";
    }
   
   }
   else{
   mensaje= " La bodega es nula.";
   }
  
   return mensaje;
   }
   
   
    @Override
    public InvExistenciaBodega crearObjExistencia(BigInteger bodId, CatArticulo objart) {
        InvExistenciaBodega objetoNew = null;
        if (bodId != null && objart != null) {
            objetoNew = obtieneObjetoByParam(bodId, objart.getArtId(), "A");
            if (objetoNew == null) {
                try {
                    objetoNew = new InvExistenciaBodega();
                    objetoNew.setBodId(bodId);
                    objetoNew.setCatArticulo(objart);
                    objetoNew.setExiExistencia(BigInteger.ZERO);
                    objetoNew.setExiCantidadinicial(BigInteger.ZERO);
                    objetoNew.setExiComprometido(BigInteger.ZERO);
                    objetoNew.setExiPreciocosto(0.0);
                    objetoNew.setExiPrecioventa(0.0);
                    objetoNew.setExiStockmax(BigInteger.ZERO);
                    objetoNew.setExiStockmin(BigInteger.ZERO);
                    objetoNew.setExiStockseg(BigInteger.ZERO);
                    objetoNew.setExiCostoinicial(0.0);
                    objetoNew.setExiCostoprom(0.0);
                    objetoNew.setExiEstado("A");
                    create(objetoNew);
                } catch (Exception e) {
                }

            } else {

            }

        }

        return objetoNew;
    }
 
    
    
    @Override
    public void editarExistenciabyIngreso(InvExistenciaBodega objeto, String valor,BigInteger cantidad,Double tIngreso){
       InvExistenciaBodega objetoEditar = find(objeto.getExiId());
       if(objetoEditar != null && valor.equalsIgnoreCase("EX") ){
           //editar existencia por ingreso
           //Total cantidad = cantidad + existencia
           BigInteger existencia = objeto.getExiExistencia().add(cantidad);
           objetoEditar.setExiExistencia(existencia);
           edit(objetoEditar);
       
       }
       if(objetoEditar != null && valor.equalsIgnoreCase("EXE") ){
           //editar existencia por egreso
           //Total cantidad = existencia -cantidad
           BigInteger existencia = objeto.getExiExistencia().subtract(cantidad);
           objetoEditar.setExiExistencia(existencia);
           edit(objetoEditar);
       }
       if(objetoEditar != null && valor.equalsIgnoreCase("PC") ){
           //editar precio de costo
          objetoEditar.setExiPreciocosto(objeto.getExiPreciocosto());
          
          if(objetoEditar.getExiCostoinicial() == null || objetoEditar.getExiCostoinicial() == 0){
          //editar costo inicial
          objetoEditar.setExiCostoinicial(objeto.getExiPreciocosto());
          }
          edit(objetoEditar);
          
       }
       if(objetoEditar != null && valor.equalsIgnoreCase("PV") ){
           //TODO PENDIENTE PREGUNTAR
           //si el item es facturable
           //editar precio de venta
          objetoEditar.setExiPrecioventa(objetoEditar.getExiPrecioventa());
          edit(objetoEditar);
       }
       if(objetoEditar != null && valor.equalsIgnoreCase("ES"))
       {
       //editar estado de existencia
           objetoEditar.setExiEstado(objeto.getExiEstado());
           edit(objetoEditar);
       }
       if(objetoEditar != null && valor.equalsIgnoreCase("CP") ){
           //Actualizar costo promedio
           //Total actual = existencia * Pcosto(valores antes del ingreso)
           //Total ingreso = cantidad * Pingreso
           //Total Cantidad = cantidad + existe
           //Precio Costeo = (TotalActual + TotalIngreso) / TotalCantidad
           Double tActual = objeto.getExiExistencia().intValue() * objeto.getExiCostoprom();
           Double valorSuma = calcular(tActual, tIngreso, "S");
           Double costoP =  valorSuma / objetoEditar.getExiExistencia().intValue();
           //redondear a 4 decimales el costo promedio
           objetoEditar.setExiCostoprom(redondear(costoP,4));
           edit(objetoEditar);
       
       }
       if(objetoEditar != null && valor.equalsIgnoreCase("MI"))
       {
       //editar stoctk minimo
           objetoEditar.setExiStockmin(objeto.getExiStockmin());
           edit(objetoEditar);
       }
       if(objetoEditar != null && valor.equalsIgnoreCase("MA"))
       {
       //editar stoctk maximo
           objetoEditar.setExiStockmax(objeto.getExiStockmax());
           edit(objetoEditar);
       }
       if(objetoEditar != null && valor.equalsIgnoreCase("SS"))
       {
       //editar stoctk seguridad
           objetoEditar.setExiStockseg(objeto.getExiStockseg());
           edit(objetoEditar);
       }
       
        
   }
    @Override
    public boolean verificaDuplicadoCab(BigInteger bodId, BigInteger artId){
        boolean ctrInserta = false;
        final StringBuilder SQLNative = new StringBuilder();
        SQLNative.append(" SELECT COUNT(*)   ");
        SQLNative.append("   FROM INV_EXISTENCIA_BODEGA E ");
        SQLNative.append("   WHERE E.BOD_ID = ").append(bodId);
        SQLNative.append("   AND E.ART_ID = ").append(artId);
        SQLNative.append(" AND E.EXI_ESTADO not in ('I') ");
        final Query query = getEntityManager().createNativeQuery(SQLNative.toString());
        final Object obj = query.getSingleResult();
        final BigDecimal valor = (BigDecimal) (obj);
        if (valor.intValue() == 0) {
           ctrInserta = true;
        }

        return ctrInserta;

    
    }
    @Override
     public InvExistenciaBodega obtieneObjetoByParam(BigInteger bodId, BigInteger artId, String estado){
    
        InvExistenciaBodega objeto = null;
         boolean isEstado = estado != null && estado.trim().length() > 0 && !(estado.trim().toLowerCase().compareTo("null") == 0);
        if(bodId != null && artId != null && isEstado){
        final Query query = em.createQuery("Select object (e) from InvExistenciaBodega e where e.bodId = :bodId and e.catArticulo.artId = :artId and e.exiEstado = :estado  ");
        query.setParameter("bodId", bodId);
        query.setParameter("artId", artId);
        query.setParameter("estado", estado);
        query.setHint("eclipselink.refresh", true);
        final List<InvExistenciaBodega> result = query.getResultList();
        if (result.size() > 0) {
            objeto = result.get(0);
        }
        }

       
        return objeto;
    }
     
    @Override
         public List<InvExistenciaBodega> listSaldoByParam(BigInteger bodId, BigInteger artId, BigInteger artIdTipo, String estado, String descripcion, BigInteger existencia, String operador, String fechaIni, String fechaFin) {
        final StringBuilder SQLNative = new StringBuilder();
        boolean isBodega = bodId != null;
        boolean isArticulo = artId != null;
        boolean isArtTipo = artIdTipo != null;
        boolean isEstado = estado != null && estado.trim().length() > 0 && !(estado.trim().toLowerCase().compareTo("null") == 0);
        boolean isDescripcion = descripcion != null && descripcion.trim().length() > 0 && !(descripcion.trim().toLowerCase().compareTo("null") == 0);
        boolean isExistencia = existencia != null;
        boolean isOperador = operador != null && operador.trim().length() > 0 && !(operador.trim().toLowerCase().compareTo("null") == 0);
        boolean isfechaIni = fechaIni != null && fechaIni.trim().length() > 0 && !(fechaIni.trim().toLowerCase().compareTo("null") == 0);
        boolean isfechaFin = fechaFin != null && fechaFin.trim().length() > 0 && !(fechaFin.trim().toLowerCase().compareTo("null") == 0);

       
        
        SQLNative.append(" SELECT EXIID,SALDO, ARTID, COSTO, (SALDO * COSTO ) TOTAL  FROM (SELECT DT.EXI_ID  EXIID, ");
        SQLNative.append("  (SELECT E.ART_ID FROM INV_EXISTENCIA_BODEGA E WHERE E.EXI_ID = DT.EXI_ID) ARTID, ");
        SQLNative.append("  (SELECT E.EXI_COSTOPROM FROM INV_EXISTENCIA_BODEGA E WHERE E.EXI_ID = DT.EXI_ID) COSTO,");
        
        
        SQLNative.append("   NVL((SELECT E.EXI_CANTIDADINICIAL FROM INV_EXISTENCIA_BODEGA E WHERE E.EXI_ID = DT.EXI_ID),0) + ");
        SQLNative.append("   (NVL((SELECT SUM(DI.DTR_CANTINGRESO) FROM INV_DETTRANSACCION DI WHERE DI.EXI_ID = DT.EXI_ID ),0) -  ");
        SQLNative.append("   (NVL((SELECT SUM(DE.DTR_CANTEGRESO) FROM INV_DETTRANSACCION DE WHERE DE.EXI_ID = DT.EXI_ID ),0)))   SALDO ");
        
        SQLNative.append(" FROM INV_DETTRANSACCION DT ");
        SQLNative.append("  LEFT JOIN INV_CABTRANSACCION CT ON DT.CTR_ID = CT.CTR_ID ");
        SQLNative.append("  LEFT JOIN INV_EXISTENCIA_BODEGA E ON DT.EXI_ID = E.EXI_ID ");
        SQLNative.append("  LEFT JOIN CAT_ARTICULO A ON A.ART_ID = E.ART_ID ");
        SQLNative.append("   WHERE 1=1 ");
        if (isBodega || isArticulo || isArtTipo || isDescripcion ) {
            if (isBodega) {
                SQLNative.append("   AND CT.BOD_ID = ").append(bodId);

            }
            if (isArticulo) {
                SQLNative.append("   AND E.ART_ID = ").append(artId);

            }
            if(isArtTipo){
             SQLNative.append("   AND  CT.TIPO_ARTICULO = ").append(artIdTipo);
            }
            if (isEstado) {
                SQLNative.append(" AND DT.DTR_ESTADO =  '").append(estado).append("' ");

            }
            if (isDescripcion) {
                SQLNative.append("       AND  A.ART_NOMBCOMERCIAL || ' '||  A.ART_NOMBGENERICO || ' '|| A.ART_CODIGO  || ' '|| A.ART_CODBARRA ");
                SQLNative.append("   LIKE '%").append(descripcion.toUpperCase()).append("%'");

            }
            if(isfechaIni && isfechaFin){
            //buscar betwen
            }
            else if(isfechaIni){
            //buscar hasta
             SQLNative.append(" AND TRUNC (CT.CTR_FECHA) <= to_date('").append(fechaIni).append("','dd/mm/yyyy')");
            }
             SQLNative.append("   GROUP BY DT.EXI_ID) ");
             
            if (isExistencia && isOperador) {
                 SQLNative.append("   WHERE 1=1 ");
                //Buscar por existencia
                if (operador.equalsIgnoreCase("IG")) {
                    SQLNative.append(" AND SALDO =  ").append(existencia);
                }
                if (operador.equalsIgnoreCase("MA")) {
                    SQLNative.append(" AND SALDO >  ").append(existencia);
                }
                if (operador.equalsIgnoreCase("ME")) {
                    SQLNative.append(" AND SALDO <   ").append(existencia);
                }

            }
            else{
             SQLNative.append(" AND 1=2   ");
            }

        } else {
            SQLNative.append(" AND 1=2   ");

        }
        final Query query = em.createNativeQuery(SQLNative.toString());
        final List<Object[]> datos = query.getResultList();
        final List<InvExistenciaBodega> datosRetorno = new ArrayList<>();
        for (final Object[] obj : datos) {
            int a = 0;
            final InvExistenciaBodega inv = new InvExistenciaBodega();
            inv.setExiId(new BigInteger(obj[a].toString()));
            a++;
             if(obj[a] != null ){
            inv.setExiExistencia(new BigInteger(obj[a].toString()));
            }
             a++;
            if(obj[a] != null ){
            inv.setCatArticulo(new CatArticulo());
            inv.setCatArticulo(objCatArticulo(new BigInteger(obj[a].toString())));}
            a++;
            if(obj[a] != null ){
            inv.setExiCostoprom(((BigDecimal) obj[a]).doubleValue());}
            a++;
            if(obj[a] != null ){
            inv.setExiTotal(((BigDecimal) obj[a]).doubleValue());}
           
            
            datosRetorno.add(inv);
        }

        return datosRetorno;
    }
 
    
    @Override
    public List<InvExistenciaBodega> calcularStock(BigInteger bodId, BigInteger artId, BigInteger artIdTipo, String estado, String descripcion,
        String fechaIni, String fechaFin, int meses, int periodo, double porciento, String tipoStock) {
        final StringBuilder SQLNative = new StringBuilder();
        boolean istipoStock = tipoStock != null && tipoStock.trim().length() > 0 && !(tipoStock.trim().toLowerCase().compareTo("null") == 0);
        boolean isfechaIni = fechaIni != null && fechaIni.trim().length() > 0 && !(fechaIni.trim().toLowerCase().compareTo("null") == 0);
        boolean isfechaFin = fechaFin != null && fechaFin.trim().length() > 0 && !(fechaFin.trim().toLowerCase().compareTo("null") == 0);
        boolean isBodega = bodId != null;
        boolean isArticulo = artId != null;
        boolean isArtTipo = artIdTipo != null;
        boolean isEstado = estado != null && estado.trim().length() > 0 && !(estado.trim().toLowerCase().compareTo("null") == 0);
        boolean isDescripcion = descripcion != null && descripcion.trim().length() > 0 && !(descripcion.trim().toLowerCase().compareTo("null") == 0);

        if (isfechaIni && isfechaFin) {
            SQLNative.append(" SELECT EXIID, SMINIMO, SMAXIMO , SSEGURIDAD, ARTID  FROM (SELECT DT.EXI_ID  EXIID,");
            SQLNative.append(" (SELECT E.ART_ID FROM INV_EXISTENCIA_BODEGA E WHERE E.EXI_ID = DT.EXI_ID) ARTID, ");

           
                //CALCULAR STOCK MINIMO = PROMEDIO DE CONSUMO MENSUAL(sumar la cantidad de egreso /numero de meses) * PERIODO DE REPOSICION/30 DIAS
               
                 SQLNative.append(" ROUND (NVL((SELECT SUM(DE.DTR_CANTEGRESO) FROM INV_DETTRANSACCION DE WHERE DE.EXI_ID = DT.EXI_ID ),0) /  ").append(meses).append(" * ").append(periodo).append(" / 30 ) ");
                  SQLNative.append(" SMINIMO, ");
           
                
                    //CALCULAR STOCK MAXIMO = PROMEDIO DE CONSUMO MENSUAL * PERIODO DE REPOSICION
                    SQLNative.append(" ROUND (NVL((SELECT SUM(DE.DTR_CANTEGRESO) FROM INV_DETTRANSACCION DE WHERE DE.EXI_ID = DT.EXI_ID ),0) /  ").append(meses).append(" * ").append(periodo).append(" ) ");
                     SQLNative.append(" SMAXIMO, ");

          
                    
                        //CALCULAR STOCK DE SEGURIDAD = PROMEDIO DE CONSUMO MENSUAL *  % PERIODO DE REPOSICION(EJEMPLO 16 PERIODODO/100 =0.16%)
                        SQLNative.append("  ROUND (NVL((SELECT SUM(DE.DTR_CANTEGRESO) FROM INV_DETTRANSACCION DE WHERE DE.EXI_ID = DT.EXI_ID ),0) *  ").append(porciento).append(" ) ");
                         SQLNative.append(" SSEGURIDAD ");
                    
                
            }

            SQLNative.append(" FROM INV_DETTRANSACCION DT ");
            SQLNative.append("  LEFT JOIN INV_CABTRANSACCION CT ON DT.CTR_ID = CT.CTR_ID ");
            SQLNative.append("  LEFT JOIN INV_EXISTENCIA_BODEGA E ON DT.EXI_ID = E.EXI_ID ");
            SQLNative.append("  LEFT JOIN CAT_ARTICULO A ON A.ART_ID = E.ART_ID ");
            SQLNative.append("   WHERE 1=1 ");
             SQLNative.append("  AND TRUNC (CT.CTR_FECHA) BETWEEN to_date('").append(fechaIni).append("','dd/mm/yyyy') and to_date('").append(fechaFin).append("','dd/mm/yyyy')");
            
            if (isBodega || isArticulo || isArtTipo || isDescripcion) {
                if (isBodega) {
                    SQLNative.append("   AND CT.BOD_ID = ").append(bodId);

                }
                if (isArticulo) {
                    SQLNative.append("   AND DT.EXI_ID = ").append(artId);

                }
                if (isArtTipo) {
                    SQLNative.append("   AND  CT.TIPO_ARTICULO = ").append(artIdTipo);
                }
                if (isEstado) {
                    SQLNative.append(" AND DT.DTR_ESTADO =  '").append(estado).append("' ");

                }
                if (isDescripcion) {
                    SQLNative.append("       AND  A.ART_NOMBCOMERCIAL || ' '||  A.ART_NOMBGENERICO || ' '|| A.ART_CODIGO  || ' '|| A.ART_CODBARRA ");
                    SQLNative.append("   LIKE '%").append(descripcion.toUpperCase()).append("%'");

                }

            }
              SQLNative.append(" GROUP BY DT.EXI_ID ) ");
           
       
         final Query query = em.createNativeQuery(SQLNative.toString());
        final List<Object[]> datos = query.getResultList();
        final List<InvExistenciaBodega> datosRetorno = new ArrayList<>();
        for (final Object[] obj : datos) {
            int a = 0;
            final InvExistenciaBodega inv = new InvExistenciaBodega();
            inv.setExiId(new BigInteger(obj[a].toString()));
            InvExistenciaBodega objExi = find(inv.getExiId());
            a++;
            if(obj[a] != null ){
            inv.setExiStockmin(new BigInteger(obj[a].toString()));
            objExi.setExiStockmin(inv.getExiStockmin());
            edit(objExi);
            
            }
            a++;
            if(obj[a] != null ){
            inv.setExiStockmax(new BigInteger(obj[a].toString()));
            objExi.setExiStockmax(inv.getExiStockmax());
            edit(objExi);
            }
            a++;
            if(obj[a] != null ){
            inv.setExiStockseg(new BigInteger(obj[a].toString()));
            objExi.setExiStockseg(inv.getExiStockseg());
            edit(objExi);
            }
            a++;
            if(obj[a] != null ){
            inv.setCatArticulo(new CatArticulo());
            inv.setCatArticulo(objCatArticulo(new BigInteger(obj[a].toString())));}
            datosRetorno.add(inv);
        }

        return datosRetorno;
        
    }

       
     
     
}
