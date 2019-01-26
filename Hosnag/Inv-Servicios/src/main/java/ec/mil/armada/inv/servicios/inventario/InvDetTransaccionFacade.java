/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.mil.armada.inv.servicios.inventario;

import ec.mil.armada.inv.remotos.inventario.InvLoteArticuloFacadeRemote;
import ec.mil.armada.inv.remotos.inventario.InvExistenciaBodegaFacadeRemote;
import ec.mil.armada.inv.remotos.inventario.InvCabTransaccionFacadeRemote;
import ec.mil.armada.inv.modelo.catalago.CatBodega;
import ec.mil.armada.inv.modelo.catalago.CatGeneral;
import ec.mil.armada.inv.modelo.inventario.InvCabTransaccion;
import ec.mil.armada.inv.modelo.inventario.InvDetTransaccion;
import ec.mil.armada.inv.modelo.inventario.InvDetsolicitudbod;
import ec.mil.armada.inv.modelo.inventario.InvDettomafisica;
import ec.mil.armada.inv.modelo.inventario.InvExistenciaBodega;
import ec.mil.armada.inv.modelo.inventario.InvLoteArticulo;
import ec.mil.armada.inv.modelo.registro.RegCabliquidacion;
import ec.mil.armada.inv.modelo.registro.RegCabordencompra;
import ec.mil.armada.inv.modelo.registro.RegDetliquidacion;
import ec.mil.armada.inv.modelo.registro.RegDetordencompra;
import ec.mil.armada.inv.remotos.catalogo.CatBodegaFacadeRemote;
import ec.mil.armada.inv.remotos.catalogo.CatGeneralFacadeRemote;
import ec.mil.armada.inv.remotos.inventario.InvDettomafisicaFacadeRemote;
import ec.mil.armada.inv.remotos.registro.RegCabliquidacionFacadeRemote;
import ec.mil.armada.inv.remotos.registro.RegDetliquidacionFacadeRemote;
import ec.mil.armada.inv.servicios.abstractFacade.AbstractFacade;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
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
public class InvDetTransaccionFacade extends AbstractFacade<InvDetTransaccion> implements ec.mil.armada.inv.remotos.inventario.InvDetTransaccionFacadeRemote {
    
    @EJB
    private RegDetliquidacionFacadeRemote regDetliquidacionFacade;
    @EJB
    private RegCabliquidacionFacadeRemote regCabliquidacionFacade;
    @EJB
    private InvExistenciaBodegaFacadeRemote invExistenciaBodegaFacade;
    @EJB
    private InvDettomafisicaFacadeRemote invDettomafisicaFacade;
    @EJB
    private InvCabTransaccionFacadeRemote invCabTransaccionFacade;
    @EJB
    private InvLoteArticuloFacadeRemote invLoteArticuloFacade;
    @EJB
    transient private CatGeneralFacadeRemote catGeneralFacade;
    
    @PersistenceContext(unitName = "ec.mil.armada_Inv-Servicios_ejb_1.0PU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public InvDetTransaccionFacade() {
        super(InvDetTransaccion.class);
    }

    public InvCabTransaccion objInvCabTransaccion(BigInteger ctrId) {

        InvCabTransaccion objeto = null;

        final Query query = em.createQuery("Select object (c) from InvCabTransaccion c where c.ctrId = :ctrId  ");
        query.setParameter("ctrId", ctrId);
        query.setHint("eclipselink.refresh", true);
        final List<InvCabTransaccion> result = query.getResultList();
        if (result.size() > 0) {
            objeto = result.get(0);
        }
        return objeto;
    }

    @Override
    public InvExistenciaBodega objInvExistenciaBodega(BigInteger exiId) {

        InvExistenciaBodega objeto = null;

        final Query query = em.createQuery("Select object (e) from InvExistenciaBodega e where e.exiId = :exiId  ");
        query.setParameter("exiId", exiId);
        query.setHint("eclipselink.refresh", true);
        final List<InvExistenciaBodega> result = query.getResultList();
        if (result.size() > 0) {
            objeto = result.get(0);
        }
        return objeto;
    }

    @Override
    public InvLoteArticulo objInvLoteArticulo(BigInteger loteId) {

        InvLoteArticulo objeto = null;

        final Query query = em.createQuery("Select object (l) from InvLoteArticulo l where l.lotId = :loteId  ");
        query.setParameter("loteId", loteId);
        query.setHint("eclipselink.refresh", true);
        final List<InvLoteArticulo> result = query.getResultList();
        if (result.size() > 0) {
            objeto = result.get(0);
        }
        return objeto;
    }

    @Override
    public List<InvDetTransaccion> listAllByParam(BigInteger bodId, BigInteger artId, BigInteger tipoArt, String descripcion, String estado, String tipo, String fechaIni, String fechaFin, String operador) {
        final StringBuilder SQLNative = new StringBuilder();
        boolean isBodega = bodId != null;
        boolean isArticulo = artId != null;
        boolean isTipoArt = tipoArt != null;
        boolean isDescripcion = descripcion != null && descripcion.trim().length() > 0 && !(descripcion.trim().toLowerCase().compareTo("null") == 0);
        boolean isEstado = estado != null && estado.trim().length() > 0 && !(estado.trim().toLowerCase().compareTo("null") == 0);
        boolean isTipo = tipo != null && tipo.trim().length() > 0 && !(tipo.trim().toLowerCase().compareTo("null") == 0);
        boolean isfechaIni = fechaIni != null && fechaIni.trim().length() > 0 && !(fechaIni.trim().toLowerCase().compareTo("null") == 0);
        boolean isfechaFin = fechaFin != null && fechaFin.trim().length() > 0 && !(fechaFin.trim().toLowerCase().compareTo("null") == 0);
        boolean isOperador = operador != null && operador.trim().length() > 0 && !(operador.trim().toLowerCase().compareTo("null") == 0);

        SQLNative.append(" SELECT DT.DTR_ID ,DT.DTR_CANTINGRESO , DT.DTR_CANTEGRESO ,DT.DTR_ESTADO, DT.DTR_COSTOINGRESO, DT.DTR_COSTROEGRESO, DT.DTR_IVA, DT.DTR_DESC, DT.DTR_TOTAL, DT.EXI_EXISTENCIA, ");
        SQLNative.append("   DT.EXI_EXISTENCIA + NVL(DT.DTR_CANTINGRESO, 0) - NVL(DT.DTR_CANTEGRESO,0) SALDO, DT.DTR_VALORIVA, DT.DTR_VALORDESC, DT.DTR_PORDESC, DT.EXI_COSTOPROM, DT.DTR_CINGRESOUNITARIO, DT.DTR_MOTIVDEVOLUCION, DT.EXI_ID, DT.LOT_ID, DT.CTR_ID ");
        SQLNative.append("   FROM INV_DETTRANSACCION DT ");
        SQLNative.append("   LEFT JOIN INV_CABTRANSACCION CT ON DT.CTR_ID = CT.CTR_ID ");
        SQLNative.append("   LEFT JOIN INV_EXISTENCIA_BODEGA EB ON DT.EXI_ID = EB.EXI_ID ");
        SQLNative.append("   LEFT JOIN CAT_ARTICULO A ON EB.ART_ID = A.ART_ID ");
        SQLNative.append("  LEFT JOIN CAT_ARTICULO S ON A.ART_ID_FK = S.ART_ID ");
        SQLNative.append("  LEFT JOIN CAT_ARTICULO T ON T.ART_ID = S.ART_ID_FK");

        SQLNative.append("   WHERE 1=1 ");
        if (isBodega || isTipo || isDescripcion) {
            if (isBodega) {
                SQLNative.append("   AND CT.BOD_ID = ").append(bodId);

            }

            if (isEstado) {
                SQLNative.append(" AND DT.DTR_ESTADO =  '").append(estado).append("' ");

            }
            if (isTipo) {
                SQLNative.append(" AND CT.CTR_TIPO IN  (").append(tipo).append(") ");

            }
            if (isArticulo) {
                SQLNative.append(" AND EB.ART_ID =  '").append(artId);

            }
            if (isTipoArt) {
                SQLNative.append("   AND T.ART_ID = ").append(tipoArt);

            }
            if (isDescripcion) {
                SQLNative.append("       AND  A.ART_NOMBCOMERCIAL || ' '||  A.ART_NOMBGENERICO || ' '|| A.ART_CODIGO  || ' '|| A.ART_CODBARRA ");
                SQLNative.append("   LIKE '%").append(descripcion.toUpperCase()).append("%'");

            }
            if (isfechaIni && isOperador) {
                //Buscar por existencia
                if (operador.equalsIgnoreCase("=")) {
                    SQLNative.append(" AND TRUNC (CT.CTR_FECHA) = to_date('").append(fechaIni).append("','dd/mm/yyyy')");
                }
                if (operador.equalsIgnoreCase(">=")) {
                    SQLNative.append(" AND TRUNC (CT.CTR_FECHA) >= to_date('").append(fechaIni).append("','dd/mm/yyyy')");
                }
                if (operador.equalsIgnoreCase("<=")) {
                    SQLNative.append(" AND TRUNC (CT.CTR_FECHA) <= to_date('").append(fechaIni).append("','dd/mm/yyyy')");
                }
                if (isfechaFin && operador.equalsIgnoreCase("ENTRE")) {
                    SQLNative.append("  AND TRUNC (CT.CTR_FECHA) BETWEEN to_date('").append(fechaIni).append("','dd/mm/yyyy') and to_date('").append(fechaFin).append("','dd/mm/yyyy')");
                }

            }
            SQLNative.append("   ORDER BY CT.CTR_FECHA ASC  ");

        } else {
            SQLNative.append(" AND 1=2   ");

        }
        final Query query = em.createNativeQuery(SQLNative.toString());
        final List<Object[]> datos = query.getResultList();
        final List<InvDetTransaccion> datosRetorno = new ArrayList<>();
        for (final Object[] obj : datos) {
            int a = 0;
            final InvDetTransaccion inv = new InvDetTransaccion();
            inv.setDtrId(new BigInteger(obj[a].toString()));
            a++;
            if (obj[a] != null) {
                inv.setDtrCantingreso(new BigInteger(obj[a].toString()));
            }
            a++;
            if (obj[a] != null) {
                inv.setDtrCantegreso(new BigInteger(obj[a].toString()));
            }
            a++;
            inv.setDtrEstado((String) obj[a]);
            a++;
            if (obj[a] != null) {
                inv.setDtrCostoIngreso(((BigDecimal) obj[a]).doubleValue());
            }
            a++;
            if (obj[a] != null) {
                inv.setDtrCostoEgreso(((BigDecimal) obj[a]).doubleValue());
            }
            a++;
            if (obj[a] != null) {
                inv.setDtrIva((String) obj[a]);
            }
            a++;
            if (obj[a] != null) {
                inv.setDtrDesc((String) obj[a]);
            }
            a++;
            if (obj[a] != null) {
                inv.setDtrTotal(((BigDecimal) obj[a]).doubleValue());
            }
            a++;
            if (obj[a] != null) {
                inv.setExiExistencia(new BigInteger(obj[a].toString()));
            }
            a++;
            if (obj[a] != null) {
                inv.setSaldo(new BigInteger(obj[a].toString()));
            }
            a++;
            if (obj[a] != null) {
                inv.setDtrValorIva(((BigDecimal) obj[a]).doubleValue());
            }
            a++;
            if (obj[a] != null) {
                inv.setDtrValorDesc(((BigDecimal) obj[a]).doubleValue());
            }
            a++;
            if (obj[a] != null) {
                inv.setDtrPorDesc(new BigInteger(obj[a].toString()));
            }
            a++;
            if (obj[a] != null) {
                inv.setExiCostoProm(((BigDecimal) obj[a]).doubleValue());
            }
            a++;
            if (obj[a] != null) {
                inv.setDtrCIngresoUnitario(((BigDecimal) obj[a]).doubleValue());
            }
            a++;
            if (obj[a] != null) {
                inv.setDtrMotivDevolucion((String) obj[a]);
            }
            a++;

            if (obj[a] != null) {
                inv.setInvExistenciaBodega(new InvExistenciaBodega());
                inv.setInvExistenciaBodega(objInvExistenciaBodega(new BigInteger(obj[a].toString())));
            }
            a++;

            if (obj[a] != null) {
                inv.setLotId(new BigInteger(obj[a].toString()));
                inv.setInvLoteArticulo(new InvLoteArticulo());
                inv.setInvLoteArticulo(objInvLoteArticulo(new BigInteger(obj[a].toString())));
            }
            a++;

            if (obj[a] != null) {
                inv.setCtrId(new BigInteger(obj[a].toString()));
                inv.setInvCabTransaccion(new InvCabTransaccion());
                inv.setInvCabTransaccion(objInvCabTransaccion(new BigInteger(obj[a].toString())));
            }
            datosRetorno.add(inv);
        }

        return datosRetorno;
    }

    @Override
    public void crearDetalle(InvCabTransaccion objCabecera, List<RegDetordencompra> listExiCantidad, String estado) {

        if (objCabecera != null) {
            //Obtiene tipo de la cabecera Ingreso o Egreso
            //obtiene parametro iva
            String iva;
            Double valorIva;
            //Double total = 0.0;
            iva = catGeneralFacade.descripcion("IVA");
            valorIva = catGeneralFacade.valor("IVA");
            if (listExiCantidad != null) {
                //Crear detalle por lista de existencia
                for (RegDetordencompra item : listExiCantidad) {
                    InvDetTransaccion objNew = new InvDetTransaccion();
                    BigInteger cantidad;
                    Double pIngreso = null;
                    Double totalIngreso;
                    objNew.setCtrId(objCabecera.getCtrId());
                    if (objCabecera.getCtrTipo().equalsIgnoreCase("IF")) {
                        cantidad = item.getDocCantidadsol();
                        if (cantidad != null) {
                            objNew.setDtrCantingreso(cantidad);
                        }
                        pIngreso = item.getDocPreciocompra();
                        if (pIngreso != null) {
                            objNew.setDtrCostoIngreso(pIngreso);
                        }
                        totalIngreso = cantidad.intValue() * pIngreso;
                        if (totalIngreso != null) {
                            objNew.setDtrTotal(totalIngreso);
                        }
                        
                    }
                    objNew.setDtrEstado(estado);
                    if (item.getInvExistenciaBodega().getExiId() != null) {
                        objNew.setExiId(item.getInvExistenciaBodega().getExiId());
                        objNew.setExiExistencia(item.getInvExistenciaBodega().getExiExistencia());
                        objNew.setExiCostoProm(item.getInvExistenciaBodega().getExiCostoprom());
                    }
                    if (item.getDocIva() != null) {
                        if (item.getDocIva().equalsIgnoreCase("S")) {
                            objNew.setDtrIva(iva);
                            //OBTIENE VALOR DEL IVA POR CADA ITEM
                            Double ivaItem = objNew.getDtrTotal() * valorIva;
                            objNew.setDtrValorIva(ivaItem);
                        } else {
                            objNew.setDtrIva("N");
                            objNew.setDtrValorIva(0.00);
                        }
                    } else {
                        objNew.setDtrIva("N");
                        objNew.setDtrValorIva(0.00);
                    }
                    //verificar el desc
                    if (item.getDocDesc() != null) {
                        if (item.getDocDesc().equalsIgnoreCase("S")) {
                            objNew.setDtrDesc("S");
                            objNew.setDtrValorDesc(item.getDocValorDesc());
                            objNew.setDtrPorDesc(item.getDocPorDesc());
                        } else {
                            objNew.setDtrDesc("N");
                            objNew.setDtrValorDesc(new Double("0.00"));
                            objNew.setDtrPorDesc(BigInteger.ZERO);

                        }

                    } else {
                        objNew.setDtrDesc("N");
                        objNew.setDtrValorDesc(new Double("0.00"));
                        objNew.setDtrPorDesc(BigInteger.ZERO);

                    }
                    if (objNew.getDtrCostoIngreso() != null && objNew.getDtrValorDesc() != null && objNew.getDtrValorIva() != null) {
                            //COSTO INGRESO  UNITARIO FINAL(c.ingreso – descuento + iva)
                            Double vResta = objNew.getDtrCostoIngreso() - objNew.getDtrValorDesc();
                            if(valorIva != null){
                                Double vIva = 0.0;
                            //obtiene el iva del cingreso
                                if(objNew.getDtrIva().equalsIgnoreCase("S")){
                                   vIva = objNew.getDtrCostoIngreso() * valorIva;
                                    }
                                Double costoUnitario = vResta + vIva;
                            if (costoUnitario != null) {
                                objNew.setDtrCIngresoUnitario(redondear(costoUnitario, 4));
                            }
                                
                            
                            }
                            
                        }
                    create(objNew);

                }

            } else {
                //Crear un solo registro
                InvDetTransaccion objNew = new InvDetTransaccion();
                objNew.setCtrId(objCabecera.getCtrId());
                objNew.setDtrIva("N");
                objNew.setDtrValorIva(0.0);
                objNew.setDtrDesc("N");
                objNew.setDtrValorDesc(0.0);
                objNew.setDtrPorDesc(BigInteger.ZERO);
                objNew.setDtrEstado(estado);
                create(objNew);

            }
            //editar cabecera
        }

    }
    
    @Override
    public void crearDetalleBySolic(InvCabTransaccion objCabecera, List<InvDetsolicitudbod> listExiCantidad, String estado) {

        if (objCabecera != null) {
        //Obtiene tipo de la cabecera Ingreso o Egreso
        
            if (listExiCantidad != null) {
                //Crear detalle por lista de existencia que biene en solicitud a bodega
                for (InvDetsolicitudbod item : listExiCantidad) {
                    InvDetTransaccion objNew = new InvDetTransaccion();
                    BigInteger cantidad = null;
                    Double costoProm = null;
                    Double totalEgreso;
                    objNew.setCtrId(objCabecera.getCtrId());
                    objNew.setDtrEstado(estado);
                    
                    if (item.getInvExistenciaBodega().getExiId() != null) {
                        objNew.setExiId(item.getInvExistenciaBodega().getExiId());
                        objNew.setExiExistencia(item.getInvExistenciaBodega().getExiExistencia());
                        objNew.setExiCostoProm(item.getInvExistenciaBodega().getExiCostoprom());
                    }
                    if(item.getDsbCantsolicitada() != null){
                     cantidad = item.getDsbCantsolicitada();
                        if (cantidad != null) {
                            objNew.setDtrCantegreso(cantidad);
                        }
                    }
                    if(item.getInvExistenciaBodega() != null && item.getInvExistenciaBodega().getExiCostoprom() != null){
                      costoProm = item.getInvExistenciaBodega().getExiCostoprom();
                        if (costoProm != null) {
                            objNew.setDtrCostoEgreso(costoProm);
                        }
                    }
                    if(cantidad != null && costoProm != null){
                    totalEgreso = cantidad.intValue() * costoProm;
                        if (totalEgreso != null) {
                            objNew.setDtrTotal(totalEgreso);
                        }
                    }
                     create(objNew);

                }

            } else {
                //Crear un solo registro
                InvDetTransaccion objNew = new InvDetTransaccion();
                objNew.setCtrId(objCabecera.getCtrId());
                objNew.setDtrIva("N");
                objNew.setDtrDesc("N");
                objNew.setDtrPorDesc(BigInteger.ZERO);
                objNew.setDtrEstado(estado);
                create(objNew);
               

            }
             //editar cabecera
        }

    }

    @Override
    public List<InvDetTransaccion> listAllByParam(BigInteger claveCabecera, String estado) {
        final StringBuilder SQLNative = new StringBuilder();
        boolean isCabecera = claveCabecera != null;
        boolean isEstado = estado != null && estado.trim().length() > 0 && !(estado.trim().toLowerCase().compareTo("null") == 0);

        SQLNative.append(" SELECT DT.DTR_ID ,DT.DTR_CANTINGRESO , DT.DTR_CANTEGRESO ,DT.DTR_ESTADO, DT.DTR_COSTOINGRESO, DT.DTR_COSTROEGRESO, DT.DTR_IVA, DT.DTR_DESC, DT.DTR_TOTAL, DT.EXI_EXISTENCIA,   ");
        SQLNative.append("   DT.DTR_VALORIVA, DT.DTR_VALORDESC, DT.DTR_PORDESC, DT.EXI_COSTOPROM, DT.DTR_CINGRESOUNITARIO, DT.DTR_MOTIVDEVOLUCION, DT.EXI_ID, DT.LOT_ID, DT.CTR_ID ");
        SQLNative.append("   FROM INV_DETTRANSACCION DT ");
        SQLNative.append("   LEFT JOIN INV_CABTRANSACCION CT ON DT.CTR_ID = CT.CTR_ID ");
        SQLNative.append("   LEFT JOIN INV_EXISTENCIA_BODEGA EB ON DT.EXI_ID = EB.EXI_ID ");
        SQLNative.append("   LEFT JOIN CAT_ARTICULO A ON EB.ART_ID = A.ART_ID ");
        SQLNative.append("  LEFT JOIN CAT_ARTICULO S ON A.ART_ID_FK = S.ART_ID ");
        SQLNative.append("  LEFT JOIN CAT_ARTICULO T ON T.ART_ID = S.ART_ID_FK");

        SQLNative.append("   WHERE 1=1 ");
        if (isCabecera) {

            SQLNative.append("   AND CT.CTR_ID = ").append(claveCabecera);

            if (isEstado) {
                SQLNative.append(" AND DT.DTR_ESTADO =  '").append(estado).append("' ");

            }

        } else {
            SQLNative.append(" AND 1=2   ");

        }
        final Query query = em.createNativeQuery(SQLNative.toString());
        final List<Object[]> datos = query.getResultList();
        final List<InvDetTransaccion> datosRetorno = new ArrayList<>();
        for (final Object[] obj : datos) {
            int a = 0;
            final InvDetTransaccion inv = new InvDetTransaccion();
            inv.setDtrId(new BigInteger(obj[a].toString()));
            a++;
            if (obj[a] != null) {
                inv.setDtrCantingreso(new BigInteger(obj[a].toString()));
            }
            a++;
            if (obj[a] != null) {
                inv.setDtrCantegreso(new BigInteger(obj[a].toString()));
            }
            a++;
            inv.setDtrEstado((String) obj[a]);
            a++;
            if (obj[a] != null) {
                inv.setDtrCostoIngreso(((BigDecimal) obj[a]).doubleValue());
            }
            a++;
            if (obj[a] != null) {
                inv.setDtrCostoEgreso(((BigDecimal) obj[a]).doubleValue());
            }
            a++;
            if (obj[a] != null) {
                inv.setDtrIva((String) obj[a]);
            }
            a++;
            if (obj[a] != null) {
                inv.setDtrDesc((String) obj[a]);
            }
            a++;
            if (obj[a] != null) {
                inv.setDtrTotal(((BigDecimal) obj[a]).doubleValue());
            }
            a++;
             if (obj[a] != null) {
                inv.setExiExistencia(new BigInteger(obj[a].toString()));
            }
            a++;
            if (obj[a] != null) {
                inv.setDtrValorIva(((BigDecimal) obj[a]).doubleValue());
            }
            a++;
            if (obj[a] != null) {
                inv.setDtrValorDesc(((BigDecimal) obj[a]).doubleValue());
            }
            a++;
            if (obj[a] != null) {
                inv.setDtrPorDesc(new BigInteger(obj[a].toString()));
            }
            a++;
                if (obj[a] != null) {
                inv.setExiCostoProm(((BigDecimal) obj[a]).doubleValue());
            }
            a++;
             if (obj[a] != null) {
                inv.setDtrCIngresoUnitario(((BigDecimal) obj[a]).doubleValue());
            }
            a++;
             if (obj[a] != null) {
                inv.setDtrMotivDevolucion((String) obj[a]);
            }
            a++;
            
            if (obj[a] != null) {
                inv.setInvExistenciaBodega(new InvExistenciaBodega());
                inv.setInvExistenciaBodega(objInvExistenciaBodega(new BigInteger(obj[a].toString())));
            }
            a++;
            if (obj[a] != null) {
                inv.setLotId(new BigInteger(obj[a].toString()));;
                inv.setInvLoteArticulo(new InvLoteArticulo());
                inv.setInvLoteArticulo(objInvLoteArticulo(new BigInteger(obj[a].toString())));
            }
            a++;
            if (obj[a] != null) {
                inv.setCtrId(new BigInteger(obj[a].toString()));;
                 inv.setInvCabTransaccion(new InvCabTransaccion());
                inv.setInvCabTransaccion(objInvCabTransaccion(new BigInteger(obj[a].toString())));
            }
            datosRetorno.add(inv);
        }

        return datosRetorno;
    }

    @Override
    public boolean verificaExisNull(BigInteger clavecab) {
        //verifica por cabecera registros nulos
        boolean ctrInserta = false;
        final StringBuilder SQLNative = new StringBuilder();
        SQLNative.append("   SELECT  count(*) FROM  INV_DETTRANSACCION d ");
        SQLNative.append("   WHERE D.EXI_ID IS NULL ");
        if (clavecab != null) {
            SQLNative.append("   AND D.CTR_ID = ").append(clavecab);
        }
        final Query query = getEntityManager().createNativeQuery(SQLNative.toString());
        final Object obj = query.getSingleResult();
        final BigDecimal valor = (BigDecimal) (obj);
        if (valor.intValue() == 0) {
            ctrInserta = true;
        }

        return ctrInserta;

    }

    @Override
    public void editarDetTrans(InvDetTransaccion objeto, String valor) {
        InvDetTransaccion objetoEdit = new InvDetTransaccion();
        InvCabTransaccion objCabecera = new InvCabTransaccion();
        
        if (objeto.getDtrId() != null) {
            String valorCabecera = null;
            objetoEdit = find(objeto.getDtrId());
            if(objetoEdit.getCtrId() != null){
            //obtiene cabecera
                objCabecera = invCabTransaccionFacade.find(objetoEdit.getCtrId());
            }
            if (valor.equalsIgnoreCase("EX") && objeto.getExiId() != null) {
                objetoEdit.setExiId(objeto.getExiId());
                InvExistenciaBodega existencia = objInvExistenciaBodega(objeto.getExiId());
                if(existencia != null){
                //obtener valores del objeto existencia
              
               if(existencia.getExiExistencia() != null){
                objetoEdit.setExiExistencia(existencia.getExiExistencia());
               }
               
                if(existencia.getExiCostoprom() != null){
               objetoEdit.setExiCostoProm(existencia.getExiCostoprom());
               }
               if(existencia.getExiPreciocosto() != null){
               objetoEdit.setDtrCIngresoUnitario(existencia.getExiPreciocosto());
               }
                }
                
            }
            if (valor.equalsIgnoreCase("LO") && objeto.getLotId() != null) {
                objetoEdit.setLotId(objeto.getLotId());
            }
            if (valor.equalsIgnoreCase("ES") && objeto.getDtrEstado() != null) {
                objetoEdit.setDtrEstado(objeto.getDtrEstado());
                if(objetoEdit.getDtrEstado().equalsIgnoreCase("F")){
                //valida si es egreso por venta
                    if(objCabecera != null && objCabecera.getCtrTipo() != null){
                       
                         if(objCabecera.getCtrTipo().equalsIgnoreCase("EV")){
                              //Es egreso pro venta -Generar liquidacion
                             CatGeneral objSer = catGeneralFacade.objByDescripcion(objCabecera.getCtrObservacion());
                             //revizar si lleva iva pendiente
                             if(objSer != null && objCabecera.getCatBodega() != null){
                                 crearLiquidacion(objCabecera.getCatBodega().getBodId(), objSer.getGenId(), objCabecera.getCtrClavetabla(), objCabecera.getCtrSiglastabla(), 
                                         objCabecera.getCtrObservacion(), "P", objCabecera.getCtrTotal(), objCabecera.getCtrTotalDesc(), objCabecera.getCtrTotalIva(), objetoEdit);
                             }
                                     
                         }
                          if(objCabecera.getCtrTipo().equalsIgnoreCase("IV")){
                          //Es ingereso por devolucion -inactiva liquidacion
                              regDetliquidacionFacade.modificaEstado(objetoEdit.getExiId(), objCabecera.getCtrClavetabla(), objCabecera.getCtrSiglastabla(), "P", objetoEdit.getDtrCantingreso(),"I");
                          }
                       
                    }
                }
            }
            if (valor.equalsIgnoreCase("CI") && objeto.getDtrCantingreso() != null) {
                objetoEdit.setDtrCantingreso(objeto.getDtrCantingreso());
                //si existe precio de costo modificar cantidad total
                if(objetoEdit.getDtrCostoIngreso() != null){
                    Double total = objetoEdit.getDtrCantingreso().intValue() * objetoEdit.getDtrCostoIngreso();
                    objetoEdit.setDtrTotal(redondear(total, 4));
                    if (objetoEdit.getCtrId() != null) {
                    //editar la cantidad total en cabecera
                    valorCabecera = "TO";
                    }
                
                }
                else{
                if(objCabecera.getCtrTipo() != null && objCabecera.getCtrTipo().equalsIgnoreCase("IV")){
                //es ingreso por devolucion
                    if(objetoEdit.getExiCostoProm() != null && objetoEdit.getDtrCantingreso() != null)
                    {
                     Double total = objetoEdit.getDtrCantingreso().intValue() * objetoEdit.getExiCostoProm();
                    objetoEdit.setDtrTotal(redondear(total, 4));
                    if (objetoEdit.getCtrId() != null) {
                    //editar la cantidad total en cabecera
                    valorCabecera = "TO";
                }
                    }
                }
                }
               
            }
            if (valor.equalsIgnoreCase("CE") && objeto.getDtrCantegreso() != null) {
                objetoEdit.setDtrCantegreso(objeto.getDtrCantegreso());
                if (objetoEdit.getExiCostoProm() != null) {
                    objetoEdit.setDtrCostoEgreso(objetoEdit.getExiCostoProm());
                    if (objetoEdit.getDtrCostoEgreso() != null) {
                        Double totalEgreso = objetoEdit.getDtrCantegreso().intValue() * objetoEdit.getDtrCostoEgreso();
                        objetoEdit.setDtrTotal(redondear(totalEgreso, 4));
                        if (objetoEdit.getCtrId() != null) {
                    //editar la cantidad total en cabecera
                    valorCabecera = "TO";
                }
                    }
                }
            }
            if (valor.equalsIgnoreCase("AS") && objeto.getDtrCantingreso() != null) {
                //ajuste por faltante
                objetoEdit.setDtrCantingreso(objeto.getDtrCantingreso());
                if (objetoEdit.getExiCostoProm() != null) {
                    objetoEdit.setDtrCostoIngreso(objetoEdit.getExiCostoProm());
                    if (objetoEdit.getDtrCostoIngreso() != null) {
                        Double totalAju = objetoEdit.getDtrCantingreso().intValue() * objetoEdit.getDtrCostoIngreso();
                        objetoEdit.setDtrTotal(redondear(totalAju, 4));
                        if (objetoEdit.getCtrId() != null) {
                    //editar la cantidad total en cabecera
                    valorCabecera = "TO";
                    // TODO editar el detalle de la toma fisica
                  
                }
                    }
                }
            }
            
            if (valor.equalsIgnoreCase("DE") && objeto.getDtrPorDesc() != null && objeto.getDtrPorDesc().intValue() != 0) {
                 //actualiza porciente de descuento
                objetoEdit.setDtrDesc("S");
                objetoEdit.setDtrPorDesc(objeto.getDtrPorDesc());
                if(objetoEdit.getDtrCostoIngreso() != null || objetoEdit.getDtrCostoEgreso() != null){
                //calcula el valor de descuente por item
                objetoEdit.setDtrValorDesc(objetoEdit.getDtrTotal() * objeto.getDtrPorDesc().intValue() / 100 );
               valorCabecera = "DE";
               //editar costo unitario
               valor = "CU";
               
                }
            }
            if (valor.equalsIgnoreCase("IV") && objeto.getDtrIva() != null && (objeto.getDtrIva().equalsIgnoreCase("S") || !objeto.getDtrIva().equalsIgnoreCase("0"))) {
            //actualizar valor iva
            //obtiene parametro iva
            String iva;
            Double valorIva;
            iva = catGeneralFacade.descripcion("IVA");
            valorIva = catGeneralFacade.valor("IVA");
            objetoEdit.setDtrIva(iva);
            objeto.setDtrIva(iva);
                //editar valor del iva por cada item
                if (valorIva != null && objetoEdit.getDtrTotal() != null ) {
                    Double vIva = objetoEdit.getDtrTotal() * valorIva ;
                    objetoEdit.setDtrValorIva(redondear(vIva,4));
                    //editar cantidad total de IVA en cabecera trasanccion
                    valorCabecera = "IV";
                    //editar costo unitario
                    valor = "CU";
                
                }
                
                

            }
            if (valor.equalsIgnoreCase("VI") && objeto.getDtrCostoIngreso() != null) {
                //edtar costo ingreso
                objetoEdit.setDtrCostoIngreso(objeto.getDtrCostoIngreso());
                //editar cantidad total
                if (objeto.getDtrCantingreso() != null && objeto.getDtrCantingreso().intValue() != 0) {
                    Double total = objetoEdit.getDtrCantingreso().intValue() * objetoEdit.getDtrCostoIngreso();
                    objetoEdit.setDtrTotal(redondear(total, 4));
                    valorCabecera = "TO";
                }
                //editar costo de ingreso unitario
                valor ="CU";
               

            }
            
             if (valor.equalsIgnoreCase("CU")) {
                 Double vDesc = 0.0;
                 Double vIva = 0.0;
                 if (objeto.getDtrCostoIngreso() != null) {
                //COSTO INGRESO  UNITARIO FINAL(c.ingreso – descuento + iva)
                    if(objeto.getDtrDesc().equalsIgnoreCase("S") && objeto.getDtrPorDesc() != null){
                       //obtiene descuento
                        vDesc= objeto.getDtrCostoIngreso() * objeto.getDtrPorDesc().intValue() / 100;
                    }
                    if(objeto.getDtrIva() != null && !objeto.getDtrIva().equalsIgnoreCase("N")){
                    //obtiene iva
                        vIva = objeto.getDtrCostoIngreso() * Integer.parseInt(objeto.getDtrIva()) / 100;
                    }
                
                Double costoUnitario = (objeto.getDtrCostoIngreso() - vDesc) + vIva;
                if(costoUnitario != null){
                 objetoEdit.setDtrCIngresoUnitario(redondear(costoUnitario,4));
                }
               
               
            }
             }
            
            if (valor.equalsIgnoreCase("VE") && objeto.getDtrCostoEgreso() != null) {
                objetoEdit.setDtrCostoEgreso(objeto.getDtrCostoEgreso());
                //editar cantidad total
                if (objeto.getDtrCantegreso() != null && objeto.getDtrCantegreso().intValue() != 0) {
                    Double totalE = objeto.getDtrCantegreso().intValue() * objeto.getDtrCostoEgreso();
                    objetoEdit.setDtrTotal(redondear(totalE, 4));
                    valorCabecera = "TO";

                }

            }
             if (valor.equalsIgnoreCase("TC") && objeto.getExiCostoProm() != null) {
               
                //editar cantidad total
                if (objeto.getDtrCantingreso() != null && objeto.getExiCostoProm().intValue() != 0) {
                    Double totalIC = objeto.getDtrCantingreso().intValue() * objeto.getExiCostoProm();
                    objetoEdit.setDtrTotal(redondear(totalIC, 4));
                    valorCabecera = "TO";

                }

            }
             if(valor.equalsIgnoreCase("MT") && objeto.getDtrMotivDevolucion() != null){
             objetoEdit.setDtrMotivDevolucion(objeto.getDtrMotivDevolucion());
             }
            
            edit(objetoEdit);
             //editar cantidad total de cabecera
                if (objCabecera.getCtrId() != null && valorCabecera != null) {
                    invCabTransaccionFacade.editarObjeto(objCabecera, valorCabecera);
                }
                
            
               
       

        }
    }

    @Override
    public void eliminaDetalle(InvDetTransaccion objeto) {
        InvDetTransaccion objElimina = find(objeto.getDtrId());
         InvCabTransaccion objCabecera = new InvCabTransaccion();
         
        if (objElimina != null) {
            if(objElimina.getCtrId() != null){
            objCabecera = invCabTransaccionFacade.find(objElimina.getCtrId());}
            remove(objElimina);
                   
            //actualiza cabecera
                         //editar cantidad total de cabecera
                if (objCabecera.getCtrId() != null) {
                     invCabTransaccionFacade.editarObjeto(objCabecera, "DE");
                      invCabTransaccionFacade.editarObjeto(objCabecera, "IV");
                    invCabTransaccionFacade.editarObjeto(objCabecera, "TO");
                }

        }

    }

    @Override
    public Double sumaDetTotal(BigInteger clavecab,String campo) {
        //suma detalle total por cabecera clave y redondea a 4 decimales
        Double total = null;
        final StringBuilder SQLNative = new StringBuilder();
        SQLNative.append(" SELECT ");
        if(campo.equalsIgnoreCase("TO")){
         SQLNative.append(" ROUND(NVL(SUM(DE.DTR_TOTAL), 0),4) DTR_TOTAL  ");
        }
        if(campo.equalsIgnoreCase("IV")){
          SQLNative.append(" ROUND(NVL(SUM(DE.DTR_VALORIVA), 0),4) DTR_VALORIVA  ");
        }
        if(campo.equalsIgnoreCase("DE")){
          SQLNative.append(" ROUND(NVL(SUM(DE.DTR_VALORDESC), 0),4) DTR_VALORDESC  ");
        }
        SQLNative.append("   FROM INV_DETTRANSACCION DE WHERE ");
        SQLNative.append("   DE.CTR_ID = ").append(clavecab);
        final Query query = getEntityManager().createNativeQuery(SQLNative.toString());
        final Object obj = query.getSingleResult();
        final BigDecimal valor = (BigDecimal) (obj);
        if (valor != null) {
            total = valor.doubleValue();
        }

        return total;

    }
    
    @Override
    public void crearDetalleByTomaFisica(InvCabTransaccion objCabecera, List<InvDettomafisica> listInvDettomafisica, String estado) {

        if (objCabecera != null) {
        //Obtiene tipo de la cabecera Ingreso o Egreso
        
            if (listInvDettomafisica != null) {
                //Crear detalle por lista de existencia que biene en solicitud a bodega
                for (InvDettomafisica item : listInvDettomafisica) {
                    InvDetTransaccion objNew = new InvDetTransaccion();
                    BigInteger cantidad = null;
                    Double costoProm = null;
                    Double totalEgreso;
                    Double totalIngreso;
                    objNew.setCtrId(objCabecera.getCtrId());
                    objNew.setDtrEstado(estado);
                    objNew.setDtrDesc("N");
                    objNew.setDtrIva("N");
                    objNew.setDtrPorDesc(BigInteger.ZERO);
                    objNew.setDtrValorDesc(0.0);
                    objNew.setDtrValorIva(0.0);
                    
                    
                    if (item.getInvExistenciaBodega().getExiId() != null) {
                        objNew.setExiId(item.getInvExistenciaBodega().getExiId());
                        objNew.setExiExistencia(item.getInvExistenciaBodega().getExiExistencia());
                        objNew.setExiCostoProm(item.getInvExistenciaBodega().getExiCostoprom());
                    }
                    if(objCabecera.getCtrTipo().equalsIgnoreCase("AS")){
                    //realizar ajuste por sobrante(realizar ingreso)
                         if(item.getDtfCantsob() != null && item.getDtfCantsob().intValue() != 0){
                        //realizar ingreso
                     cantidad = item.getDtfCantsob();
                        if (cantidad != null) {
                            objNew.setDtrCantingreso(cantidad);
                        }
                    }
                    if(item.getInvExistenciaBodega() != null && item.getInvExistenciaBodega().getExiCostoprom() != null){
                      costoProm = item.getInvExistenciaBodega().getExiCostoprom();
                        if (costoProm != null) {
                            objNew.setDtrCostoIngreso(costoProm);
                        }
                    }
                    if(cantidad != null && costoProm != null){
                    totalIngreso = cantidad.intValue() * costoProm;
                        if (totalIngreso != null) {
                            objNew.setDtrTotal(totalIngreso);
                        }
                    }
                    }
                    if(objCabecera.getCtrTipo().equalsIgnoreCase("AF")){
                    //realizar ajuste por faltante(realizar egreso)
                         if(item.getDtfCantdif() != null && item.getDtfCantdif().intValue() != 0){
                        //realizar egreso
                     cantidad = item.getDtfCantdif();
                        if (cantidad != null) {
                            objNew.setDtrCantegreso(cantidad);
                        }
                    }
                    if(item.getInvExistenciaBodega() != null && item.getInvExistenciaBodega().getExiCostoprom() != null){
                      costoProm = item.getInvExistenciaBodega().getExiCostoprom();
                        if (costoProm != null) {
                            objNew.setDtrCostoEgreso(costoProm);
                        }
                    }
                    if(cantidad != null && costoProm != null){
                    totalEgreso = cantidad.intValue() * costoProm;
                        if (totalEgreso != null) {
                            objNew.setDtrTotal(totalEgreso);
                        }
                    }
                    }
                   
                     create(objNew);
                      //editar cantidad total de cabecera
                

                }
                if (objCabecera.getCtrId() != null) {
                     objCabecera = invCabTransaccionFacade.find(objCabecera.getCtrId());
                    invCabTransaccionFacade.editarObjeto(objCabecera, "TO");
                }

            } else {
                //Crear un solo registro
                InvDetTransaccion objNew = new InvDetTransaccion();
                objNew.setCtrId(objCabecera.getCtrId());
                objNew.setDtrIva("N");
                objNew.setDtrDesc("N");
                objNew.setDtrPorDesc(BigInteger.ZERO);
                objNew.setDtrEstado(estado);
                create(objNew);
               

            }
             //editar cabecera
        }

    }
    
    @Override
     public String crearDetalleByParam(BigInteger bodId, BigInteger idCabecera, BigInteger artId, BigInteger cantidad, String estado, String tipoTrans) {
        String mensaje = null;
        InvDetTransaccion objNew = null;
        if (idCabecera != null) {
            //Obtiene objeto existencia en bodega
            InvExistenciaBodega objetoExistencia = invExistenciaBodegaFacade.obtieneObjetoByParam(bodId, artId, "A");
            if (objetoExistencia != null) {
                //crear detalle de la transaccion
                objNew = new InvDetTransaccion();
                Double costoProm = null;
                Double costoPrecio = null;
                Double total;
                objNew.setCtrId(idCabecera);
                objNew.setDtrEstado(estado);
                objNew.setDtrIva("N");
                objNew.setDtrDesc("N");
                objNew.setDtrPorDesc(BigInteger.ZERO);

                if (objetoExistencia.getExiId() != null) {
                    objNew.setExiId(objetoExistencia.getExiId());
                    objNew.setExiExistencia(objetoExistencia.getExiExistencia());
                    if(objetoExistencia.getExiCostoprom() != null ){
                    objNew.setExiCostoProm(objetoExistencia.getExiCostoprom());
                    costoProm = objetoExistencia.getExiCostoprom();
                    }
                    
                    if(objetoExistencia.getExiPreciocosto() != null){
                    costoPrecio = objetoExistencia.getExiPreciocosto();
                    }
                    else{
                    costoPrecio = costoProm;
                    }
                    
                    if (cantidad != null && costoPrecio != null) {
                        total = cantidad.intValue() * costoPrecio;
                        objNew.setDtrTotal(total);
                        if (tipoTrans.equalsIgnoreCase("I")) {
                            //es ingreso
                            objNew.setDtrCantingreso(cantidad);
                            if (costoPrecio != null) {
                                objNew.setDtrCostoIngreso(costoPrecio);
                            }

                        }
                        if (tipoTrans.equalsIgnoreCase("E")) {
                            //es egreso
                            objNew.setDtrCantegreso(cantidad);
                            if (costoPrecio != null) {
                                objNew.setDtrCostoEgreso(costoPrecio);
                            }

                        }

                      
                        create(objNew);
                        mensaje = objNew.getDtrId().toString();
                        mensaje = "Se creó el detalle de la transacción.";
                    } else {
                        mensaje = "0";
                    }
                }

            } else {

                mensaje = "-1";
            }

        } else {

            mensaje = "0";
        }
        return  mensaje;

    }
     
    @Override
   public void crearLiquidacion(BigInteger claveTabla, String siglaTabla,String observacion, String estado,BigInteger exiId,BigInteger cant, Double valor){
       RegCabliquidacion objNew = new RegCabliquidacion();
       objNew = regCabliquidacionFacade.crearObjeto(claveTabla, siglaTabla, observacion, estado);
       if(objNew != null){
       //crear detalle liquidacion
           
            regDetliquidacionFacade.crearRegDetLiquidacion(objNew.getCliId(), exiId, cant, estado, valor);
       }
   
   }
    
    @Override
   public void crearLiquidacion(BigInteger bodId, BigInteger servId, BigInteger claveTabla, String siglaTabla,String observacion, String estado,
           Double subtotal, Double tDesc, Double tIva, InvDetTransaccion objTransaccion ){
       RegCabliquidacion objNew = new RegCabliquidacion();
       objNew = regCabliquidacionFacade.crearObjeto(bodId, servId, claveTabla, siglaTabla, observacion, estado, subtotal, tDesc, null);
       if(objNew != null){
       //crear detalle liquidacion
            regDetliquidacionFacade.crearRegDetLiquidacion(objNew.getCliId(), objTransaccion.getExiId(), objTransaccion.getDtrCantegreso(), estado, 
            objTransaccion.getDtrCostoEgreso(), objTransaccion.getDtrValorIva(), objTransaccion.getDtrPorDesc(), objTransaccion.getDtrValorDesc());
            }
         
            //editar total el cabecera
            regCabliquidacionFacade.editCabLiq(objNew, "TO");
          
            
       }
   
    @Override
  public String crearDetalleByParam(BigInteger bodId, BigInteger idCabecera, BigInteger artId, BigInteger cantidad, String estado, String tipoTrans, Double cPromedio) {
        String mensaje = null;
        InvDetTransaccion objNew = null;
        if (idCabecera != null) {
            //Obtiene objeto existencia en bodega
            InvExistenciaBodega objetoExistencia = invExistenciaBodegaFacade.obtieneObjetoByParam(bodId, artId, "A");
            if (objetoExistencia != null) {
                //crear detalle de la transaccion
                objNew = new InvDetTransaccion();
                Double costoProm = null;
                Double costoPrecio = null;
                Double total;
                objNew.setCtrId(idCabecera);
                objNew.setDtrEstado(estado);
                objNew.setDtrIva("N");
                objNew.setDtrDesc("N");
                objNew.setDtrPorDesc(BigInteger.ZERO);

                if (objetoExistencia.getExiId() != null) {
                    objNew.setExiId(objetoExistencia.getExiId());
                    objNew.setExiExistencia(objetoExistencia.getExiExistencia());
                    if(objetoExistencia.getExiCostoprom() != null && objetoExistencia.getExiCostoprom() != 0 ){
                    objNew.setExiCostoProm(objetoExistencia.getExiCostoprom());
                    costoProm = objetoExistencia.getExiCostoprom();
                    }
                    else{
                        //costo promedio es cero o nulo obtiene por defecto el de bodega general
                    costoProm = cPromedio;
                    }
                    
                    costoPrecio = costoProm;
                    if (cantidad != null && costoPrecio != null) {
                        total = cantidad.intValue() * costoPrecio;
                        objNew.setDtrTotal(total);
                        if (tipoTrans.equalsIgnoreCase("I")) {
                            //es ingreso
                            objNew.setDtrCantingreso(cantidad);
                            if (costoPrecio != null) {
                                objNew.setDtrCostoIngreso(costoPrecio);
                                objNew.setDtrCIngresoUnitario(costoPrecio);
                            }

                        }
                        if (tipoTrans.equalsIgnoreCase("E")) {
                            //es egreso
                            objNew.setDtrCantegreso(cantidad);
                            if (costoPrecio != null) {
                                objNew.setDtrCostoEgreso(costoPrecio);
                            }

                        }

                      
                        create(objNew);
                        mensaje = "Se creó el detalle de la transacción.";
                    } else {
                        //cantidad nula
                        mensaje = "0";
                    }
                }

            } else {
                //No existe existencia en bodega

                mensaje = "-1";
            }

        } else {
         // clave cab nula
            mensaje = "0";
        }
        return  mensaje;

    }
      
      
   
}
