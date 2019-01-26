/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.mil.armada.inv.modelo.inventario;

import ec.mil.armada.inv.modelo.catalago.CatArticulo;
import ec.mil.armada.inv.modelo.catalago.CatBodega;
import java.io.Serializable;
import java.math.BigInteger;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 *
 * @author maria_rodriguez
 */
@Entity
@Table(name = "INV_CABTRANSACCION")
@SequenceGenerator(name = "invCabTransaccionSeq", sequenceName = "INV_CABTRANSACCION_SEQ", allocationSize = 1)
public class InvCabTransaccion implements Serializable {
    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "invCabTransaccionSeq")
    @Basic(optional = false)
    @NotNull
    @Column(name = "CTR_ID")
    private BigInteger ctrId;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 2)
    @Column(name = "CTR_TIPO")
    private String ctrTipo;
    @Basic(optional = false)
    @NotNull
    @Column(name = "CTR_NUMERO")
    private BigInteger ctrNumero;
    @Column(name = "CTR_CLAVETABLA")
    private BigInteger ctrClavetabla;
    @Size(max = 1)
    @Column(name = "CTR_SIGLASTABLA")
    private String ctrSiglastabla;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 1)
    @Column(name = "CTR_ESTADO")
    private String ctrEstado;
    @Column(name = "CTR_FECHA")
    @Temporal(TemporalType.TIMESTAMP)
    private Date ctrFecha;
    @Column(name = "CTR_TOTAL")
    private Double ctrTotal;
    @JoinColumn(name = "BOD_ID", referencedColumnName = "BOD_ID")
    @ManyToOne
    private CatBodega catBodega;
    @Column(name = "CTR_JEFE")
    private String ctrJefe;
    @Column(name = "CTR_ENCARGADO")
    private String ctrEncargado;
    @Column(name = "CTR_PERSONARETIRA")
    private String ctrPersonaRetira;
    @Column(name = "CTR_NOREF")
    private String ctrNoRef;
    @Column(name = "TIPO_ARTICULO")
    private BigInteger tipoArticulo;
    @Column(name = "CTR_OBSERVACION")
    private String ctrObservacion;
    @Column(name = "CTR_FECHADOC")
    @Temporal(TemporalType.TIMESTAMP)
    private Date ctrFechaDoc;
    @Column(name = "CTR_TOTALDESC")
    private Double ctrTotalDesc;
    @Column(name = "CTR_TOTALIVA")
    private Double ctrTotalIva;
    @Column(name = "CTR_TOTAGENERAL")
    private Double ctrTotaGeneral;
    @Column(name = "CTR_AREA")
    private BigInteger ctrArea;
    @Column(name = "CTR_ENCARGAREA")
    private String ctrEncargArea;
    @Column(name = "CTR_RESPFALTANTE")
    private String ctrRespFaltante;
     @Column(name = "CTR_VALIDA")
    private String ctrValida;
   
    
     
     @Transient
     private CatArticulo catArticulo;
     @Transient
     private CatBodega catBodegaArea;
   
   

    public InvCabTransaccion() {
    }

   
   
    public String getCtrTipo() {
        return ctrTipo;
    }

    public void setCtrTipo(String ctrTipo) {
        this.ctrTipo = ctrTipo;
    }

    public BigInteger getCtrNumero() {
        return ctrNumero;
    }

    public void setCtrNumero(BigInteger ctrNumero) {
        this.ctrNumero = ctrNumero;
    }

    public BigInteger getCtrClavetabla() {
        return ctrClavetabla;
    }

    public void setCtrClavetabla(BigInteger ctrClavetabla) {
        this.ctrClavetabla = ctrClavetabla;
    }

    public String getCtrSiglastabla() {
        return ctrSiglastabla;
    }

    public void setCtrSiglastabla(String ctrSiglastabla) {
        this.ctrSiglastabla = ctrSiglastabla;
    }

    public String getCtrEstado() {
        return ctrEstado;
    }

    public void setCtrEstado(String ctrEstado) {
        this.ctrEstado = ctrEstado;
    }

    public Date getCtrFecha() {
        return ctrFecha;
    }

    public void setCtrFecha(Date ctrFecha) {
        this.ctrFecha = ctrFecha;
    }

    public BigInteger getCtrId() {
        return ctrId;
    }

    public void setCtrId(BigInteger ctrId) {
        this.ctrId = ctrId;
    }

    public CatBodega getCatBodega() {
        return catBodega;
    }

    public void setCatBodega(CatBodega catBodega) {
        this.catBodega = catBodega;
    }

    public Double getCtrTotal() {
        return ctrTotal;
    }

    public void setCtrTotal(Double ctrTotal) {
        this.ctrTotal = ctrTotal;
    }

    public String getCtrJefe() {
        return ctrJefe;
    }

    public void setCtrJefe(String ctrJefe) {
        this.ctrJefe = ctrJefe;
    }

    public String getCtrEncargado() {
        return ctrEncargado;
    }

    public void setCtrEncargado(String ctrEncargado) {
        this.ctrEncargado = ctrEncargado;
    }

    public String getCtrPersonaRetira() {
        return ctrPersonaRetira;
    }

    public void setCtrPersonaRetira(String ctrPersonaRetira) {
        this.ctrPersonaRetira = ctrPersonaRetira;
    }

    public String getCtrNoRef() {
        return ctrNoRef;
    }

    public void setCtrNoRef(String ctrNoRef) {
        this.ctrNoRef = ctrNoRef;
    }

    public BigInteger getTipoArticulo() {
        return tipoArticulo;
    }

    public void setTipoArticulo(BigInteger tipoArticulo) {
        this.tipoArticulo = tipoArticulo;
    }

    public CatArticulo getCatArticulo() {
        return catArticulo;
    }

    public void setCatArticulo(CatArticulo catArticulo) {
        this.catArticulo = catArticulo;
    }

    public String getCtrObservacion() {
        return ctrObservacion;
    }

    public void setCtrObservacion(String ctrObservacion) {
        this.ctrObservacion = ctrObservacion;
    }

    public Date getCtrFechaDoc() {
        return ctrFechaDoc;
    }

    public void setCtrFechaDoc(Date ctrFechaDoc) {
        this.ctrFechaDoc = ctrFechaDoc;
    }

    public Double getCtrTotalDesc() {
        return ctrTotalDesc;
    }

    public void setCtrTotalDesc(Double ctrTotalDesc) {
        this.ctrTotalDesc = ctrTotalDesc;
    }

    public Double getCtrTotalIva() {
        return ctrTotalIva;
    }

    public void setCtrTotalIva(Double ctrTotalIva) {
        this.ctrTotalIva = ctrTotalIva;
    }

    public Double getCtrTotaGeneral() {
        return ctrTotaGeneral;
    }

    public void setCtrTotaGeneral(Double ctrTotaGeneral) {
        this.ctrTotaGeneral = ctrTotaGeneral;
    }

    public BigInteger getCtrArea() {
        return ctrArea;
    }

    public void setCtrArea(BigInteger ctrArea) {
        this.ctrArea = ctrArea;
    }

    public String getCtrEncargArea() {
        return ctrEncargArea;
    }

    public void setCtrEncargArea(String ctrEncargArea) {
        this.ctrEncargArea = ctrEncargArea;
    }

    public CatBodega getCatBodegaArea() {
        return catBodegaArea;
    }

    public void setCatBodegaArea(CatBodega catBodegaArea) {
        this.catBodegaArea = catBodegaArea;
    }

    public String getCtrRespFaltante() {
        return ctrRespFaltante;
    }

    public void setCtrRespFaltante(String ctrRespFaltante) {
        this.ctrRespFaltante = ctrRespFaltante;
    }

    public String getCtrValida() {
        return ctrValida;
    }

    public void setCtrValida(String ctrValida) {
        this.ctrValida = ctrValida;
    }

    

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (ctrId != null ? ctrId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof InvCabTransaccion)) {
            return false;
        }
        InvCabTransaccion other = (InvCabTransaccion) object;
        if ((this.ctrId == null && other.ctrId != null) || (this.ctrId != null && !this.ctrId.equals(other.ctrId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ec.mil.armada.inv.modelo.inventario.InvCabtransaccion[ ctrId=" + ctrId + " ]";
    }
    
}
