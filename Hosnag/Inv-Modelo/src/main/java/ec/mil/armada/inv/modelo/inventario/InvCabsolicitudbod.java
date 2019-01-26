/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.mil.armada.inv.modelo.inventario;

import ec.mil.armada.inv.modelo.catalago.CatArticulo;
import ec.mil.armada.inv.modelo.catalago.CatBodega;
import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
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
@Table(name = "INV_CABSOLICITUDBOD")
@NamedQueries({
    @NamedQuery(name = "InvCabsolicitudbod.findAll", query = "SELECT i FROM InvCabsolicitudbod i")})
public class InvCabsolicitudbod implements Serializable {
    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "CSB_ID")
    private BigInteger csbId;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 4)
    @Column(name = "CSB_ANOFISCAL")
    private String csbAnofiscal;
    @Basic(optional = false)
    @NotNull
    @Column(name = "CSB_ESTADO")
    private String csbEstado;
    @Basic(optional = false)
    @NotNull
    @Column(name = "CSB_TIPO")
    private String csbTipo;
    @Basic(optional = false)
    @NotNull
    @Column(name = "CSB_NUMERO")
    private BigInteger csbNumero;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 256)
    @Column(name = "CSB_MOTIVO")
    private String csbMotivo;
    @Column(name = "CSB_NOREF")
    private BigInteger csbNoref;
    @Column(name = "CSB_BODEGA")
    private BigInteger csbBodega;
    @Column(name = "CSB_AREASOLIC")
    private BigInteger csbAreasolic;
    @Column(name = "ART_TIPO")
    private BigInteger artTipo;
    @Column(name = "CSB_FECHA")
    @Temporal(TemporalType.TIMESTAMP)
    private Date csbFecha;
    @Column(name = "CSB_SUBDIRECCION")
    private String csbSubDireccion;
    @Column(name = "CSB_FECHASDA")
    @Temporal(TemporalType.TIMESTAMP)
    private Date csbFechaSda;
    @Column(name = "CSB_MOTIVOSDA")
    private String csbMotivoSda;
    @Column(name = "CSB_DIAS")
    private BigInteger csbDias;
    
     @Transient
     private CatArticulo catArticulo;
     @Transient
     private CatBodega catBodega;
     @Transient
     private CatBodega catArea;
     
    
   

    public InvCabsolicitudbod() {
    }

    public BigInteger getCsbId() {
        return csbId;
    }

    public void setCsbId(BigInteger csbId) {
        this.csbId = csbId;
    }

    public String getCsbEstado() {
        return csbEstado;
    }

    public void setCsbEstado(String csbEstado) {
        this.csbEstado = csbEstado;
    }

    public String getCsbTipo() {
        return csbTipo;
    }

    public void setCsbTipo(String csbTipo) {
        this.csbTipo = csbTipo;
    }

   

    public String getCsbAnofiscal() {
        return csbAnofiscal;
    }

    public void setCsbAnofiscal(String csbAnofiscal) {
        this.csbAnofiscal = csbAnofiscal;
    }

  
    public BigInteger getCsbNumero() {
        return csbNumero;
    }

    public void setCsbNumero(BigInteger csbNumero) {
        this.csbNumero = csbNumero;
    }

    public String getCsbMotivo() {
        return csbMotivo;
    }

    public void setCsbMotivo(String csbMotivo) {
        this.csbMotivo = csbMotivo;
    }

    public BigInteger getCsbNoref() {
        return csbNoref;
    }

    public void setCsbNoref(BigInteger csbNoref) {
        this.csbNoref = csbNoref;
    }

    public BigInteger getCsbBodega() {
        return csbBodega;
    }

    public void setCsbBodega(BigInteger csbBodega) {
        this.csbBodega = csbBodega;
    }

    public BigInteger getCsbAreasolic() {
        return csbAreasolic;
    }

    public void setCsbAreasolic(BigInteger csbAreasolic) {
        this.csbAreasolic = csbAreasolic;
    }

    public BigInteger getArtTipo() {
        return artTipo;
    }

    public void setArtTipo(BigInteger artTipo) {
        this.artTipo = artTipo;
    }

    public Date getCsbFecha() {
        return csbFecha;
    }

    public void setCsbFecha(Date csbFecha) {
        this.csbFecha = csbFecha;
    }

    public String getCsbSubDireccion() {
        return csbSubDireccion;
    }

    public void setCsbSubDireccion(String csbSubDireccion) {
        this.csbSubDireccion = csbSubDireccion;
    }

    public Date getCsbFechaSda() {
        return csbFechaSda;
    }

    public void setCsbFechaSda(Date csbFechaSda) {
        this.csbFechaSda = csbFechaSda;
    }

    public String getCsbMotivoSda() {
        return csbMotivoSda;
    }

    public void setCsbMotivoSda(String csbMotivoSda) {
        this.csbMotivoSda = csbMotivoSda;
    }

    public BigInteger getCsbDias() {
        return csbDias;
    }

    public void setCsbDias(BigInteger csbDias) {
        this.csbDias = csbDias;
    }

    public CatArticulo getCatArticulo() {
        return catArticulo;
    }

    public void setCatArticulo(CatArticulo catArticulo) {
        this.catArticulo = catArticulo;
    }

    public CatBodega getCatBodega() {
        return catBodega;
    }

    public void setCatBodega(CatBodega catBodega) {
        this.catBodega = catBodega;
    }

    public CatBodega getCatArea() {
        return catArea;
    }

    public void setCatArea(CatBodega catArea) {
        this.catArea = catArea;
    }

  

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (csbId != null ? csbId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof InvCabsolicitudbod)) {
            return false;
        }
        InvCabsolicitudbod other = (InvCabsolicitudbod) object;
        if ((this.csbId == null && other.csbId != null) || (this.csbId != null && !this.csbId.equals(other.csbId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ec.mil.armada.inv.modelo.inventario.InvCabsolicitudbod[ csbId=" + csbId + " ]";
    }
    
}
