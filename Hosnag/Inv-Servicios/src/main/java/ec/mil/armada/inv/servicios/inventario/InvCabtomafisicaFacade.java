/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.mil.armada.inv.servicios.inventario;

import ec.mil.armada.inv.modelo.catalago.CatArticulo;
import ec.mil.armada.inv.modelo.catalago.CatBodega;
import ec.mil.armada.inv.modelo.inventario.InvCabtomafisica;
import ec.mil.armada.inv.remotos.inventario.InvCabtomafisicaFacadeRemote;
import ec.mil.armada.inv.remotos.registro.RegEmpleadoFacadeRemote;
import ec.mil.armada.inv.servicios.abstractFacade.AbstractFacade;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
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
 * @author maria_roriguez
 */
@Stateless
public class InvCabtomafisicaFacade extends AbstractFacade<InvCabtomafisica> implements InvCabtomafisicaFacadeRemote {
    @PersistenceContext(unitName = "ec.mil.armada_Inv-Servicios_ejb_1.0PU")
    private EntityManager em;
    @EJB
    transient private RegEmpleadoFacadeRemote regEmpleadoFacade;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public InvCabtomafisicaFacade() {
        super(InvCabtomafisica.class);
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
    
    @Override
     public int obtieneNumDoc(String aFiscal, BigInteger tipoArt, BigInteger bodId) {
        int numero = 0;
        final StringBuilder SQLNative = new StringBuilder();
        SQLNative.append(" SELECT COUNT(*) ");
        SQLNative.append("   FROM INV_CABTOMAFISICA CT ");
        SQLNative.append("   WHERE TO_CHAR (CT.CTF_FECHA,'YYYY') = '").append(aFiscal).append("' ");
        SQLNative.append("   AND CT.BOD_ID = ").append(bodId);
        SQLNative.append("   AND CT.CTF_TIPOART = ").append(tipoArt);
        final Query query = getEntityManager().createNativeQuery(SQLNative.toString());
        final Object obj = query.getSingleResult();
        final BigDecimal valor = (BigDecimal) (obj);
        if (valor != null) {
            numero = valor.intValue() + 1;
        }

        return numero;

    }
     
    @Override
      public InvCabtomafisica crearObjeto(CatBodega bodega, String estado, String tipoInv, BigInteger artId) {
           boolean isBodega =  bodega.getBodId() != null;
        InvCabtomafisica objetoNew = null;
        if(isBodega){
        boolean ctrInserta = verificaDuplicadoCab(bodega.getBodId(),artId);
       
        if (ctrInserta) {
            objetoNew = new InvCabtomafisica();
            objetoNew.setCtfBodId(bodega.getBodId());
            if (bodega.getEmlId() != null) {
                //obtiene el feje de bodega
                String jefe = regEmpleadoFacade.nombreEmpleado(bodega.getEmlId());
                objetoNew.setCtfJefeBodega(jefe);
            }
           
            objetoNew.setCtfEstado(estado);
            objetoNew.setCtfFecha(new Date());
            objetoNew.setCtfImpetiquetas("N");
  
        //Genera numero por año fiscal, por bodega y por tipo de articulo
            //obtiene anio actual(perido fiscal)
            final Calendar now = Calendar.getInstance();
            String pFiscal = String.valueOf(now.get(Calendar.YEAR));
            objetoNew.setCtfAnofiscal(pFiscal);

            int numeroDoc = obtieneNumDoc(pFiscal,artId,bodega.getBodId());
            if (numeroDoc != 0) {
                objetoNew.setCtfNumero(BigInteger.valueOf(numeroDoc));
            }

            //tipo inv
            objetoNew.setCtfTipoInv(tipoInv);
            //tipo de articulo
            if (artId != null) {
                objetoNew.setCtfTipoArt(artId);
//                //obtiene el encargado de cada tipo de articulo
//                CatArticulo objArt = catArticuloFacade.find(artId);
//                if (objArt != null && objArt.getEmlId() != null) {
//                    String encargado = regEmpleadoFacade.nombreEmpleado(objArt.getEmlId());
//                    objetoNew.setCtrEncargado(encargado);
//                }
            }
            create(objetoNew);

        }
        else{
        if(!ctrInserta){
           //existe cabecera son finalizar.
            //objetoNew = objetoByTipoEstado(bodega.getBodId(), artId, "N");
        }
       
        }
        
        }
        else{
            //no existe bodega
       
        }
   

        return objetoNew;
    }
    
    @Override
     public boolean verificaDuplicadoCab(BigInteger bodId,BigInteger tipoArt){
        boolean ctrInserta = false;
        final StringBuilder SQLNative = new StringBuilder();
        SQLNative.append(" SELECT COUNT(*) ");
        SQLNative.append("   FROM INV_CABTOMAFISICA CT ");
        SQLNative.append("   WHERE CT.BOD_ID = ").append(bodId);
         SQLNative.append("  AND CT.CTF_TIPOART = ").append(tipoArt);
        SQLNative.append(" AND CT.CTF_ESTADO not in ('I','F') ");
        final Query query = getEntityManager().createNativeQuery(SQLNative.toString());
        final Object obj = query.getSingleResult();
        final BigDecimal valor = (BigDecimal) (obj);
        if (valor.intValue() == 0) {
           ctrInserta = true;
        }

        return ctrInserta;

    
    }
    @Override
     public InvCabtomafisica objetoByTipoEstado(BigInteger bodId,BigInteger tipoArt, String estado) {

        InvCabtomafisica objeto = null;
        Boolean isBodega = bodId != null;
        Boolean isTipoArt = tipoArt != null;
        boolean isEstado = estado != null && estado.trim().length() > 0 && !(estado.trim().toLowerCase().compareTo("null") == 0);
        if (isBodega && isTipoArt && isEstado ) {

            final Query query = em.createQuery("Select object (c) from InvCabtomafisica c where c.ctfBodId = :bodId and c.ctfTipoArt = :tipoArt and c.ctfEstado = :estado  ");
            query.setParameter("bodId", bodId);
            query.setParameter("tipoArt", tipoArt);
            query.setParameter("estado", estado);
            query.setHint("eclipselink.refresh", true);
            final List<InvCabtomafisica> result = query.getResultList();
            if (result.size() > 0) {
                objeto = result.get(0);
            }
        }
        return objeto;
    }
    @Override
      public List<InvCabtomafisica> listAllByParam(BigInteger bodId, String estado, String tipo, String fechaIni, String fechaFin, String operador, BigInteger noDoc, BigInteger artId) {
        final StringBuilder SQLNative = new StringBuilder();
        boolean isBodega = bodId != null;
        boolean isArTipo = artId != null;
        boolean isNumDoc = noDoc != null;
        boolean isEstado = estado != null && estado.trim().length() > 0 && !(estado.trim().toLowerCase().compareTo("null") == 0);
        boolean isTipo = tipo != null && tipo.trim().length() > 0 && !(tipo.trim().toLowerCase().compareTo("null") == 0);
        boolean isfechaIni = fechaIni != null && fechaIni.trim().length() > 0 && !(fechaIni.trim().toLowerCase().compareTo("null") == 0);
        boolean isfechaFin = fechaFin != null && fechaFin.trim().length() > 0 && !(fechaFin.trim().toLowerCase().compareTo("null") == 0);
        boolean isOperador = operador != null && operador.trim().length() > 0 && !(operador.trim().toLowerCase().compareTo("null") == 0);

        SQLNative.append("SELECT CT.CTF_ID, CT.CTF_ESTADO, CT.CTF_FECHA, CT.CTF_IMPETIQUETAS, CT.CTF_NUMERO, CT.CTF_OBSERVACION, CT.CTF_TIPOINV,   ");
        SQLNative.append("  CT.BOD_ID, CT.CTF_TIPOART  ");
        SQLNative.append("   FROM INV_CABTOMAFISICA CT ");
        SQLNative.append("   WHERE 1=1 ");
        if (isBodega || isArTipo) {
            if (isBodega) {
                SQLNative.append("   AND CT.BOD_ID = ").append(bodId);

            }
            if (isNumDoc) {
                SQLNative.append("   AND CT.CTF_NUMERO = ").append(noDoc);

            }

            if (isEstado) {
                SQLNative.append(" AND CT.CTF_ESTADO =  '").append(estado).append("' ");

            }
            if (isTipo) {
                SQLNative.append(" AND CT.CTF_TIPOINV =  '").append(tipo).append("' ");

            }
            if (isArTipo) {
                SQLNative.append(" AND CT.CTF_TIPOART = ").append(artId);

            }
            if (isfechaIni && isOperador) {
                //Buscar por existencia
                if (operador.equalsIgnoreCase("=")) {
                    SQLNative.append(" AND TRUNC (CT.CTF_FECHA) = to_date('").append(fechaIni).append("','dd/mm/yyyy')");
                }
                if (operador.equalsIgnoreCase(">=")) {
                    SQLNative.append(" AND TRUNC (CT.CTF_FECHA) >= to_date('").append(fechaIni).append("','dd/mm/yyyy')");
                }
                if (operador.equalsIgnoreCase("<=")) {
                    SQLNative.append(" AND TRUNC (CT.CTF_FECHA) <= to_date('").append(fechaIni).append("','dd/mm/yyyy')");
                }
                if (isfechaFin && operador.equalsIgnoreCase("ENTRE")) {
                    SQLNative.append("  AND TRUNC (CT.CTF_FECHA) BETWEEN to_date('").append(fechaIni).append("','dd/mm/yyyy') and to_date('").append(fechaFin).append("','dd/mm/yyyy')");
                }

            }

        } else {
            SQLNative.append(" AND 1=2   ");

        }
        final Query query = em.createNativeQuery(SQLNative.toString());
        final List<Object[]> datos = query.getResultList();
        final List<InvCabtomafisica> datosRetorno = new ArrayList<>();
        for (final Object[] obj : datos) {
            int a = 0;
            final InvCabtomafisica inv = new InvCabtomafisica();
            inv.setCtfId(new BigInteger(obj[a].toString()));
            a++;

            if (obj[a] != null) {
                inv.setCtfEstado(((String) obj[a]));
            }
            a++;
            if (obj[a] != null) {
                inv.setCtfFecha(((Date) obj[a]));
            }
            a++;
            if (obj[a] != null) {
                inv.setCtfImpetiquetas(((String) obj[a]));
            }
            a++;
            if (obj[a] != null) {
                inv.setCtfNumero(new BigInteger(obj[a].toString()));
            }
            a++;
            if (obj[a] != null) {
                inv.setCtfObservacion(((String) obj[a]));
            }
            a++;

            if (obj[a] != null) {
                inv.setCtfTipoInv(((String) obj[a]));
            }
            a++;

            if (obj[a] != null) {
                inv.setCatBodega(new CatBodega());
                inv.setCtfBodId(new BigInteger(obj[a].toString()));
                inv.setCatBodega(objCatBodega(new BigInteger(obj[a].toString())));
            }
            a++;

            if (obj[a] != null) {
                inv.setCatArticulo(new CatArticulo());
                inv.setCtfTipoArt(new BigInteger(obj[a].toString()));
                inv.setCatArticulo(objCatArticulo(new BigInteger(obj[a].toString())));
            }

            datosRetorno.add(inv);
        }

        return datosRetorno;
    }
      
    @Override
      public void editarObjeto(InvCabtomafisica objeto, String valor) {
        InvCabtomafisica objetoEdit = new InvCabtomafisica();
        boolean isValor = valor != null && valor.trim().length() > 0 && !(valor.trim().toLowerCase().compareTo("null") == 0);
        if (objeto.getCtfId() != null && isValor) {
            objetoEdit = find(objeto.getCtfId());
            if (objeto.getCatArticulo() != null && valor.equalsIgnoreCase("TA")) {
                //tipo de articulo
                objetoEdit.setCtfTipoArt(objeto.getCtfTipoArt());
            }
            if (objeto.getCatBodega() != null && valor.equalsIgnoreCase("BO")) {
                objetoEdit.setCtfBodId(objeto.getCtfBodId());
            }
            
            if (objeto.getCtfEstado() != null && valor.equalsIgnoreCase("ES")) {
                boolean ctrEditEstado = verificaEstadoHijos(objetoEdit.getCtfId(), "T");
                if (ctrEditEstado) {
                    objetoEdit.setCtfEstado(objeto.getCtfEstado());
                }
            }

           
            if (objeto.getCtfTipoInv() != null && valor.equalsIgnoreCase("TI")) {
                objetoEdit.setCtfTipoInv(objeto.getCtfTipoInv());
            }
            
            if (objeto.getCtfObservacion() != null && valor.equalsIgnoreCase("OB")) {
                objetoEdit.setCtfObservacion(objeto.getCtfObservacion());
            }
            if (objeto.getCtfFecha() != null && valor.equalsIgnoreCase("FD")) {
                objetoEdit.setCtfFecha(objeto.getCtfFecha());
            }
            if (objeto.getCtfImpetiquetas() != null && valor.equalsIgnoreCase("IE")) {
                objetoEdit.setCtfImpetiquetas(objeto.getCtfImpetiquetas());
            }
           

        }

        edit(objetoEdit);

    }
    
    @Override
      public boolean verificaEstadoHijos(BigInteger claveCab, String estado){
        boolean ctrEdit = false;
        final StringBuilder SQLNative = new StringBuilder();
        SQLNative.append(" SELECT COUNT(*) ");
        SQLNative.append("   FROM INV_CABTOMAFISICA CT ");
        SQLNative.append("   WHERE CT.CTF_ID = ").append(claveCab);
        SQLNative.append(" AND CT.CTF_ESTADO =  '").append(estado).append("' ");
        final Query query = getEntityManager().createNativeQuery(SQLNative.toString());
        final Object obj = query.getSingleResult();
        final BigDecimal valor = (BigDecimal) (obj);
        if (valor.intValue() == 0) {
           ctrEdit = true;
        }

        return ctrEdit;

    
    }
      
    @Override
     public String modificaEstadoCabDet(BigInteger clavecab, String estado) {
        String mensaje = null;
        
        if (clavecab != null) {
            InvCabtomafisica objEdit = new InvCabtomafisica();
            objEdit = find(clavecab);
            if (objEdit != null) {
       
                    //ACTUALIZA ESTADO DE LOS HIJOS
                    final StringBuilder SQLNative = new StringBuilder();
                    SQLNative.append(" UPDATE INV_DETTOMAFISICA DT SET DT.DTF_ESTADO = '").append(estado).append("' ");
                    SQLNative.append(" WHERE ");
                    SQLNative.append(" DT.CTF_ID = ").append(clavecab);
                    final Query query = getEntityManager().createNativeQuery(SQLNative.toString());
                    query.executeUpdate();
                    //editar cabecera
                    objEdit.setCtfEstado(estado);
                    edit(objEdit);
                    mensaje = "Los registros se modificaron satisfactoriamente.";

            } else {
                mensaje = "Objeto cabecera es nulo.";
            }

        } else {
            mensaje = "Clave de cabecera es nula.";
        }

        return mensaje;
    }
    @Override
       public boolean validaPendientes(BigInteger claveCab){
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
        public boolean validaDiferencias(BigInteger claveCab){
         //verifica si existe diferencia 
        boolean ctrDiferencias = false;
        final StringBuilder SQLNative = new StringBuilder();
        SQLNative.append(" SELECT COUNT(*) ");
        SQLNative.append("   FROM INV_DETTOMAFISICA DT  ");       
        SQLNative.append("   WHERE DT.CTF_ID = ").append(claveCab);
        SQLNative.append("   AND (DT.DTF_CANTDIF != 0 OR DT.DTF_CANTSOB != 0) ");
        final Query query = getEntityManager().createNativeQuery(SQLNative.toString());
        final Object obj = query.getSingleResult();
        final BigDecimal valor = (BigDecimal) (obj);
        if (valor.intValue() != 0) {
           ctrDiferencias = true;
        }

        return ctrDiferencias;

    
    }
        
    @Override
       public String validaFinalizarInv(BigInteger clavecab, String estado) {
        String mensaje = null;
        boolean ctrEditHijos = true;
        boolean ctrPendiente = false;
        boolean ctrDiferencia = false;
        if (clavecab != null) {
            InvCabtomafisica objEdit = new InvCabtomafisica();
            objEdit = find(clavecab);
            if (objEdit != null) {
                if (estado.equalsIgnoreCase("F")) {
                    //valida que este tomado todo los item
                    ctrPendiente = validaPendientes(clavecab);
                    if (ctrPendiente) {
                        mensaje = "Existe toma fisica pendientes de ingresar.";
                        ctrEditHijos = false;
                    } else {
                        //valida si existe diferencia en los item
                        ctrDiferencia = validaDiferencias(clavecab);
                        if (ctrDiferencia) {
                            mensaje = "Existe diferencias en la toma fisica.";
                            ctrEditHijos = false;
                        }
                    }
                }

            } else {
                mensaje = "Objeto cabecera es nulo.";
            }

        } else {
            mensaje = "Clave de cabecera es nula.";
        }
        return mensaje;

    }
        
}
      
