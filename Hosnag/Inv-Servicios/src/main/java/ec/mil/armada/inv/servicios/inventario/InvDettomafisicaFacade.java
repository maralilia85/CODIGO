/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.mil.armada.inv.servicios.inventario;

import ec.mil.armada.inv.modelo.catalago.CatArticulo;
import ec.mil.armada.inv.remotos.inventario.InvLoteArticuloFacadeRemote;
import ec.mil.armada.inv.remotos.inventario.InvExistenciaBodegaFacadeRemote;
import ec.mil.armada.inv.modelo.inventario.InvCabtomafisica;
import ec.mil.armada.inv.modelo.inventario.InvDetTransaccion;
import ec.mil.armada.inv.modelo.inventario.InvDettomafisica;
import ec.mil.armada.inv.modelo.inventario.InvExistenciaBodega;
import ec.mil.armada.inv.modelo.inventario.InvLoteArticulo;
import ec.mil.armada.inv.remotos.inventario.InvCabtomafisicaFacadeRemote;
import ec.mil.armada.inv.remotos.inventario.InvDettomafisicaFacadeRemote;
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
public class InvDettomafisicaFacade extends AbstractFacade<InvDettomafisica> implements InvDettomafisicaFacadeRemote {

    @EJB
    private InvExistenciaBodegaFacadeRemote invExistenciaBodegaFacade;
    @EJB
    private InvCabtomafisicaFacadeRemote invCabtomafisicaFacade;
    @EJB
    private InvLoteArticuloFacadeRemote invLoteArticuloFacade;

    @PersistenceContext(unitName = "ec.mil.armada_Inv-Servicios_ejb_1.0PU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public InvDettomafisicaFacade() {
        super(InvDettomafisica.class);
    }

    @Override
    public InvCabtomafisica objInvCabTransaccion(BigInteger ctfId) {

        InvCabtomafisica objeto = null;

        final Query query = em.createQuery("Select object (c) from InvCabtomafisica c where c.ctfId = :ctfId  ");
        query.setParameter("ctfId", ctfId);
        query.setHint("eclipselink.refresh", true);
        final List<InvCabtomafisica> result = query.getResultList();
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
    public void crearDetalle(InvCabtomafisica objCabecera, String estado) {

        if (objCabecera != null) {
            //obtiene existencia en la bodega por tipo articulo
            List<InvExistenciaBodega> listExistencia = invExistenciaBodegaFacade.listAllByParam(objCabecera.getCtfBodId(), null, objCabecera.getCtfTipoArt(), "A", null, null, null);
            if (listExistencia.size() > 0) {
                for (InvExistenciaBodega objExistencia : listExistencia) {

                    try {
                        InvDettomafisica objNew = new InvDettomafisica();
                        objNew.setCtfId(objCabecera.getCtfId());
                        objNew.setExiId(objExistencia.getExiId());
                        objNew.setDtfSaldoactual(objExistencia.getExiExistencia());
                        objNew.setDtfExiCosto(objExistencia.getExiCostoprom());
                        objNew.setDtfEstado(objCabecera.getCtfEstado());
                        create(objNew);
                    } catch (Exception e) {
                    }

                }

            }

        }

    }

    @Override
    public void editarDetToma(InvDettomafisica objeto, String valor) {
        boolean ctrEstado = false;
        InvDettomafisica objEdit = new InvDettomafisica();
        if (objeto.getDtfId() != null) {
            objEdit = find(objeto.getDtfId());
            if (objEdit != null) {
                if (valor.equalsIgnoreCase("CT") && objeto.getDtfCantidadtoma() != null) {
                    objEdit.setDtfCantidadtoma(objeto.getDtfCantidadtoma());
                    if(objeto.getDtfExiCosto() != null){
                    objEdit.setDtfTotal(objEdit.getDtfCantidadtoma().intValue() * objeto.getDtfExiCosto());
                    }
                    
                    objEdit.setDtfEstado("T");
                    if (objEdit.getCtfId() != null) {
                        //editar estado de cabecera
                        InvCabtomafisica objetoCab = invCabtomafisicaFacade.find(objEdit.getCtfId());
                        if (objetoCab != null) {
                            objetoCab.setCtfEstado("T");
                            invCabtomafisicaFacade.editarObjeto(objetoCab, "ES");
                        }
                    }
                    if (objEdit.getDtfCantidadtoma().intValue() > objEdit.getDtfSaldoactual().intValue()) {
                        //existe sobrante
                        BigInteger valorToma = objEdit.getDtfCantidadtoma().subtract(objeto.getDtfSaldoactual());
                        objEdit.setDtfCantsob(valorToma);
                        objEdit.setDtfCantdif(BigInteger.ZERO);
                        objEdit.setDtfTotalFalt(0.0);
                        if (objEdit.getDtfExiCosto() != null) {
                            objEdit.setDtfTotalSob(valorToma.intValue() * objEdit.getDtfExiCosto());
                            
                        }
                        
                      
                    }
                    if (objEdit.getDtfCantidadtoma().intValue() < objEdit.getDtfSaldoactual().intValue()) {
                        //existente faltante
                        BigInteger valorToma = objeto.getDtfSaldoactual().subtract(objEdit.getDtfCantidadtoma());
                        objEdit.setDtfCantdif(valorToma);
                        objEdit.setDtfCantsob(BigInteger.ZERO);
                        objEdit.setDtfTotalSob(0.0);
                        if (objEdit.getDtfExiCosto() != null) {
                            objEdit.setDtfTotalFalt(valorToma.intValue() * objEdit.getDtfExiCosto());
                        }
                        
                    }

                    if (objEdit.getDtfCantidadtoma().intValue() == objEdit.getDtfSaldoactual().intValue()) {
                        // no existe diferencia
                        objEdit.setDtfCantdif(BigInteger.ZERO);
                        objEdit.setDtfTotalSob(0.0);
                        // no existe fatante
                        objEdit.setDtfCantsob(BigInteger.ZERO);
                        objEdit.setDtfTotalFalt(0.0);
                    }

                }
                if (valor.equalsIgnoreCase("OB") && objeto.getDtfObservacion() != null) {
                    objEdit.setDtfObservacion(objeto.getDtfObservacion());
                }
                if (valor.equalsIgnoreCase("ES") && objeto.getDtfEstado() != null) {
                    objEdit.setDtfEstado(objeto.getDtfEstado());

                    if (objEdit.getCtfId() != null) {
                        //editar estado de cabecera
                        InvCabtomafisica objetoCab = invCabtomafisicaFacade.find(objEdit.getCtfId());
                        if (objetoCab != null) {
                            objetoCab.setCtfEstado("T");
                            invCabtomafisicaFacade.editarObjeto(objetoCab, "ES");
                        }
                    }
                }
                if (valor.equalsIgnoreCase("SA") && objeto.getDtfSaldoactual() != null) {
                    objEdit.setDtfSaldoactual(objeto.getDtfSaldoactual());
                    if (objEdit.getDtfCantidadtoma().intValue() == objEdit.getDtfSaldoactual().intValue()) {
                        // no existe diferencia
                        objEdit.setDtfCantdif(BigInteger.ZERO);
                        objEdit.setDtfTotalSob(0.0);
                        objEdit.setDtfCantsob(BigInteger.ZERO);
                        objEdit.setDtfTotalFalt(0.0);
                    }
                }
                edit(objEdit);
            }
        }

    }

    @Override
    public List<InvDettomafisica> listAllByParam(BigInteger claveCabecera, String estado) {
        final StringBuilder SQLNative = new StringBuilder();
        boolean isCabecera = claveCabecera != null;
        boolean isEstado = estado != null && estado.trim().length() > 0 && !(estado.trim().toLowerCase().compareTo("null") == 0);

        SQLNative.append(" SELECT DT.DTF_ID, DT.DTF_OBSERVACION, DT.DTF_ESTADO, DT.DTF_CANTIDADTOMA, DT.DTF_SALDOACTUAL, DT.DTF_CANTDIF, DT.DTF_CANTSOB,   ");
        SQLNative.append("   DT.DTF_EXICOSTO, DT.DTF_TOTALSOB, DT.DTF_TOTALFALT, DT.CTF_ID, DT.EXI_ID  ");
        SQLNative.append("   FROM INV_DETTOMAFISICA DT ");
        SQLNative.append("   LEFT JOIN INV_EXISTENCIA_BODEGA EB ON DT.EXI_ID = EB.EXI_ID ");

        SQLNative.append("   WHERE 1=1 ");
        if (isCabecera) {

            SQLNative.append("   AND DT.CTF_ID = ").append(claveCabecera);

            if (isEstado) {
                SQLNative.append(" AND DT.DTF_ESTADO =  '").append(estado).append("' ");

            }

        } else {
            SQLNative.append(" AND 1=2   ");

        }
        final Query query = em.createNativeQuery(SQLNative.toString());
        final List<Object[]> datos = query.getResultList();
        final List<InvDettomafisica> datosRetorno = new ArrayList<>();
        for (final Object[] obj : datos) {
            int a = 0;
            final InvDettomafisica inv = new InvDettomafisica();
            inv.setDtfId(new BigInteger(obj[a].toString()));
            a++;
            if (obj[a] != null) {
                inv.setDtfObservacion((String) obj[a]);
            }
            a++;
            if (obj[a] != null) {
                inv.setDtfEstado((String) obj[a]);
            }
            a++;
            if (obj[a] != null) {
                inv.setDtfCantidadtoma(new BigInteger(obj[a].toString()));
            }
            a++;
            if (obj[a] != null) {
                inv.setDtfSaldoactual(new BigInteger(obj[a].toString()));
            }
            a++;
            if (obj[a] != null) {
                inv.setDtfCantdif(new BigInteger(obj[a].toString()));
            }
            a++;
            if (obj[a] != null) {
                inv.setDtfCantsob(new BigInteger(obj[a].toString()));
            }
            a++;
            if (obj[a] != null) {
                inv.setDtfExiCosto(((BigDecimal) obj[a]).doubleValue());
            }
            a++;
            if (obj[a] != null) {
                inv.setDtfTotalSob(((BigDecimal) obj[a]).doubleValue());
            }
            a++;
            if (obj[a] != null) {
                inv.setDtfTotalFalt(((BigDecimal) obj[a]).doubleValue());
            }
            a++;

            if (obj[a] != null) {
                inv.setCtfId(new BigInteger(obj[a].toString()));
                inv.setInvCabTomaFisica(new InvCabtomafisica());
                inv.setInvCabTomaFisica(objInvCabTransaccion(new BigInteger(obj[a].toString())));
            }
            a++;
            if (obj[a] != null) {
                inv.setExiId(new BigInteger(obj[a].toString()));
                inv.setInvExistenciaBodega(new InvExistenciaBodega());
                inv.setInvExistenciaBodega(objInvExistenciaBodega(new BigInteger(obj[a].toString())));
            }

            datosRetorno.add(inv);
        }

        return datosRetorno;
    }

    @Override
    public boolean validaPendientes(BigInteger claveCab) {
        //verifica si existe valores sin ingresar toma fisica
        boolean ctrPendientes = false;
        final StringBuilder SQLNative = new StringBuilder();
        SQLNative.append(" SELECT COUNT(*) ");
        SQLNative.append("   FROM INV_DETTOMAFISICA DT  ");
        SQLNative.append("   WHERE DT.CTF_ID = ").append(claveCab);
        SQLNative.append("    AND (DT.DTF_CANTIDADTOMA IS NULL OR DT.DTF_ESTADO = 'N') ");
        final Query query = getEntityManager().createNativeQuery(SQLNative.toString());
        final Object obj = query.getSingleResult();
        final BigDecimal valor = (BigDecimal) (obj);
        if (valor.intValue() != 0) {
            ctrPendientes = true;
        }

        return ctrPendientes;

    }

    @Override
    public boolean validaDiferencias(BigInteger claveCab) {
        //verifica si existe diferencia 
        boolean ctrDiferencias = false;
        final StringBuilder SQLNative = new StringBuilder();
        SQLNative.append(" SELECT COUNT(*) ");
        SQLNative.append("   FROM INV_DETTOMAFISICA DT  ");
        SQLNative.append("   WHERE DT.CTF_ID = ").append(claveCab);
        SQLNative.append("    AND (DT.DTF_CANTDIF != 0 OR DT.DTF_CANTSOB != 0) ");
        final Query query = getEntityManager().createNativeQuery(SQLNative.toString());
        final Object obj = query.getSingleResult();
        final BigDecimal valor = (BigDecimal) (obj);
        if (valor.intValue() != 0) {
            ctrDiferencias = true;
        }

        return ctrDiferencias;

    }

    @Override
    public boolean validaEstados(BigInteger claveCab, String estado) {
        //verifica estado de hijos
        boolean ctrEstado = false;
        final StringBuilder SQLNative = new StringBuilder();
        SQLNative.append(" SELECT COUNT(*) ");
        SQLNative.append("   FROM INV_DETTOMAFISICA DT  ");
        SQLNative.append("   WHERE DT.CTF_ID = ").append(claveCab);
        SQLNative.append("    AND DT.DTF_ESTADO = '").append(estado).append("' ");
        final Query query = getEntityManager().createNativeQuery(SQLNative.toString());
        final Object obj = query.getSingleResult();
        final BigDecimal valor = (BigDecimal) (obj);
        if (valor.intValue() == 0) {
            ctrEstado = true;
        }

        return ctrEstado;

    }

    @Override
    public List<InvDettomafisica> listDiferencias(BigInteger claveCabecera, String estado, String tipoDif) {
        final StringBuilder SQLNative = new StringBuilder();
        boolean isCabecera = claveCabecera != null;
        boolean isEstado = estado != null && estado.trim().length() > 0 && !(estado.trim().toLowerCase().compareTo("null") == 0);
        boolean isTipoDif = tipoDif != null && tipoDif.trim().length() > 0 && !(tipoDif.trim().toLowerCase().compareTo("null") == 0);

        SQLNative.append(" SELECT DT.DTF_ID, DT.DTF_OBSERVACION, DT.DTF_ESTADO, DT.DTF_CANTIDADTOMA, DT.DTF_SALDOACTUAL, DT.DTF_CANTDIF, DT.DTF_CANTSOB,   ");
        SQLNative.append("   DT.DTF_EXICOSTO, DT.DTF_TOTALSOB, DT.DTF_TOTALFALT, DT.CTF_ID, DT.EXI_ID  ");
        SQLNative.append("   FROM INV_DETTOMAFISICA DT ");
        SQLNative.append("   LEFT JOIN INV_EXISTENCIA_BODEGA EB ON DT.EXI_ID = EB.EXI_ID ");
        SQLNative.append("   WHERE 1=1 ");
        if (isCabecera && isTipoDif) {

            SQLNative.append("   AND DT.CTF_ID = ").append(claveCabecera);
            if (isTipoDif) {
                if (tipoDif.equalsIgnoreCase("AS")) {
                    //traer sobrantes
                    SQLNative.append("   AND DT.DTF_CANTSOB != 0 ");
                }
                if (tipoDif.equalsIgnoreCase("AF")) {
                    //traer faltante
                    SQLNative.append("   AND DT.DTF_CANTDIF != 0 ");
                }
            }

            if (isEstado) {
                SQLNative.append(" AND DT.DTF_ESTADO =  '").append(estado).append("' ");

            }

        } else {
            SQLNative.append(" AND 1=2   ");

        }
        final Query query = em.createNativeQuery(SQLNative.toString());
        final List<Object[]> datos = query.getResultList();
        final List<InvDettomafisica> datosRetorno = new ArrayList<>();
        for (final Object[] obj : datos) {
            int a = 0;
            final InvDettomafisica inv = new InvDettomafisica();
            inv.setDtfId(new BigInteger(obj[a].toString()));
            a++;
            if (obj[a] != null) {
                inv.setDtfObservacion((String) obj[a]);
            }
            a++;
            if (obj[a] != null) {
                inv.setDtfEstado((String) obj[a]);
            }
            a++;
            if (obj[a] != null) {
                inv.setDtfCantidadtoma(new BigInteger(obj[a].toString()));
            }
            a++;
            if (obj[a] != null) {
                inv.setDtfSaldoactual(new BigInteger(obj[a].toString()));
            }
            a++;
            if (obj[a] != null) {
                inv.setDtfCantdif(new BigInteger(obj[a].toString()));
            }
            a++;
            if (obj[a] != null) {
                inv.setDtfCantsob(new BigInteger(obj[a].toString()));
            }
            a++;
            if (obj[a] != null) {
                inv.setDtfExiCosto(((BigDecimal) obj[a]).doubleValue());
            }
            a++;
            if (obj[a] != null) {
                inv.setDtfTotalSob(((BigDecimal) obj[a]).doubleValue());
            }
            a++;
            if (obj[a] != null) {
                inv.setDtfTotalFalt(((BigDecimal) obj[a]).doubleValue());
            }
            a++;

            if (obj[a] != null) {
                inv.setCtfId(new BigInteger(obj[a].toString()));
                inv.setInvCabTomaFisica(new InvCabtomafisica());
                inv.setInvCabTomaFisica(objInvCabTransaccion(new BigInteger(obj[a].toString())));
            }
            a++;
            if (obj[a] != null) {
                inv.setExiId(new BigInteger(obj[a].toString()));
                inv.setInvExistenciaBodega(new InvExistenciaBodega());
                inv.setInvExistenciaBodega(objInvExistenciaBodega(new BigInteger(obj[a].toString())));
            }

            datosRetorno.add(inv);
        }

        return datosRetorno;
    }

    @Override
    public InvDettomafisica objetoByCabExistenciaEstado(BigInteger cabecera, BigInteger existencia, String estado) {

        InvDettomafisica objeto = null;
        Boolean isCabecera = cabecera != null;
        Boolean isExistencia = existencia != null;
        boolean isEstado = estado != null && estado.trim().length() > 0 && !(estado.trim().toLowerCase().compareTo("null") == 0);
        if (isCabecera && isExistencia && isEstado) {

            final Query query = em.createQuery("Select object (d) from InvDettomafisica d where d.ctfId = :cabecera and d.exiId = :existencia and d.dtfEstado = :estado  ");
            query.setParameter("cabecera", cabecera);
            query.setParameter("existencia", existencia);
            query.setParameter("estado", estado);
            query.setHint("eclipselink.refresh", true);
            final List<InvDettomafisica> result = query.getResultList();
            if (result.size() > 0) {
                objeto = result.get(0);
            }
        }
        return objeto;
    }

    public String editarDetTomaByAjus(InvDetTransaccion objDetTransaccion, String valor) {
        boolean ctrEstado = false;
        String mensaje = null;
        InvDettomafisica objEdit = new InvDettomafisica();
        if (objDetTransaccion != null && objDetTransaccion.getCtrId() != null && objDetTransaccion.getDtrId() != null && objDetTransaccion.getDtrEstado() != null && objDetTransaccion.getExiId() != null) {
            InvDettomafisica objeto = objetoByCabExistenciaEstado(objDetTransaccion.getCtrId(), objDetTransaccion.getDtrId(), objDetTransaccion.getDtrEstado());
            InvExistenciaBodega existencia = invExistenciaBodegaFacade.find(objDetTransaccion.getExiId());

            if (objeto != null && objeto.getDtfId() != null) {
                objEdit = find(objeto.getDtfId());
                if (objEdit != null) {
                    //editar saldo actual de la existencia
                    if (existencia != null) {
                        objEdit.setDtfSaldoactual(existencia.getExiExistencia());
                        if (objEdit.getDtfCantidadtoma().intValue() > objEdit.getDtfSaldoactual().intValue()) {
                            //es sobrante
                            BigInteger valorToma = objEdit.getDtfCantidadtoma().subtract(objeto.getDtfSaldoactual());
                            objEdit.setDtfCantsob(valorToma);
                            if (objEdit.getDtfExiCosto() != null) {
                                objEdit.setDtfTotalSob(valorToma.intValue() * objEdit.getDtfExiCosto());
                            }
                        }
                        if (objEdit.getDtfCantidadtoma().intValue() < objEdit.getDtfSaldoactual().intValue()) {
                            //es faltante
                            BigInteger valorToma = objeto.getDtfSaldoactual().subtract(objEdit.getDtfCantidadtoma());
                            objEdit.setDtfCantdif(valorToma);
                            if (objEdit.getDtfExiCosto() != null) {
                                objEdit.setDtfTotalFalt(valorToma.intValue() * objEdit.getDtfExiCosto());
                            }
                        }

                        if (objEdit.getDtfCantidadtoma().intValue() == objEdit.getDtfSaldoactual().intValue()) {
                            // no existe diferencia
                            objEdit.setDtfCantdif(BigInteger.ZERO);
                            objEdit.setDtfTotalSob(0.0);
                            objEdit.setDtfCantsob(BigInteger.ZERO);
                            objEdit.setDtfTotalFalt(0.0);
                        }

                        objEdit.setDtfEstado("F");
                        if (objEdit.getCtfId() != null) {
                            //editar estado de cabecera
                            InvCabtomafisica objetoCab = invCabtomafisicaFacade.find(objEdit.getCtfId());
                            if (objetoCab != null) {
                                objetoCab.setCtfEstado("T");
                                boolean ctrEditEstado = invCabtomafisicaFacade.verificaEstadoHijos(objetoCab.getCtfId(), "F");
                                if (ctrEditEstado) {
                                    objetoCab.setCtfEstado("F");
                                    invCabtomafisicaFacade.edit(objetoCab);
                                }

                            }
                        }

                        edit(objEdit);
                        mensaje = "Transaccion realizada con exito.";
                    } else {
                        mensaje = "No se realizo la transaccion existencia nula.";
                    }

                }
            }

        } else {
            mensaje = "No se realizo la transaccion objeto nulo.";
        }
        return mensaje;

    }
    
    @Override
    public void crearItemFueraInv(InvCabtomafisica objCab, String estado, CatArticulo objArticulo, BigInteger cant, String observacion){
       boolean isOb = observacion != null && observacion.trim().length() > 0 && !(observacion.trim().toLowerCase().compareTo("null") == 0);

        if(objCab != null && objArticulo != null){
    InvExistenciaBodega objExistencia = new InvExistenciaBodega();
            objExistencia = invExistenciaBodegaFacade.crearObjExistencia(objCab.getCtfBodId(), objArticulo);
            if(objExistencia != null){
            InvDettomafisica objDetalle = new InvDettomafisica();
            objDetalle.setCtfId(objCab.getCtfId());
            if(cant != null){
            objDetalle.setDtfCantidadtoma(cant);
            }
            if(isOb){
            objDetalle.setDtfObservacion(observacion);
            }
            objDetalle.setExiId(objExistencia.getExiId());
            objDetalle.setDtfEstado(estado);
            objDetalle.setDtfCantsob(BigInteger.ZERO);
            objDetalle.setDtfCantdif(BigInteger.ZERO);
            objDetalle.setDtfExiCosto(objExistencia.getExiPreciocosto());
            if(objDetalle.getDtfCantidadtoma() != null && objDetalle.getDtfExiCosto() != null){
            objDetalle.setDtfTotal(objDetalle.getDtfCantidadtoma().intValue() * objDetalle.getDtfExiCosto());
            }
            
                create(objDetalle);
            }
        }
    
    }

}
