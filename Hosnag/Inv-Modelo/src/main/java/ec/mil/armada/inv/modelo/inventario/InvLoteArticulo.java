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
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author maria_rodriguez
 */
@Entity
@Table(name = "INV_LOTE_ARTICULO")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "InvLoteArticulo.findAll", query = "SELECT i FROM InvLoteArticulo i")})
public class InvLoteArticulo implements Serializable {
    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "LOT_ID")
    @SequenceGenerator(name = "invLoteArtiSeq", sequenceName = "INV_LOTE_ARTI_SEQ", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "invLoteArtiSeq")
    private BigInteger lotId;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 256)
    @Column(name = "LOT_DESCRIPION")
    private String lotDescripion;
    @Basic(optional = false)
    @NotNull
    @Column(name = "LOT_ESTADO")
    private String lotEstado;
    @Basic(optional = false)
    @NotNull
    @Column(name = "LOT_FECHAELAB")
    @Temporal(TemporalType.TIMESTAMP)
    private Date lotFechaelab;
    @Basic(optional = false)
    @NotNull
    @Column(name = "LOT_FECHACADUC")
    @Temporal(TemporalType.TIMESTAMP)
    private Date lotFechacaduc;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 256)
    @Column(name = "LOT_REGSANITARIO")
    private String lotRegsanitario;
    @Basic(optional = false)
    @NotNull
    @Column(name = "LOT_CANTINGRESO")
    private BigInteger lotCantingreso;
    @Basic(optional = false)
    @NotNull
    @Column(name = "LOT_CANTEGRESO")
    private BigInteger lotCantegreso;
    @Basic(optional = false)
    @NotNull
    @Column(name = "LOT_CANTIDAD")
    private BigInteger lotCantidad;
    @Column(name = "LOT_COSTO")
    private Double lotCosto;
    
    @Column(name = "ART_ID")
    private BigInteger artId;
    
     @Column(name = "BOD_ID")
    private BigInteger bodId;
    
    @Transient
    private CatArticulo catArticulo;
    
    @Transient
    private CatBodega catBodega;

    public InvLoteArticulo() {
    }

    public InvLoteArticulo(BigInteger lotId) {
        this.lotId = lotId;
    }

    public BigInteger getLotId() {
        return lotId;
    }

    public void setLotId(BigInteger lotId) {
        this.lotId = lotId;
    }

    public String getLotEstado() {
        return lotEstado;
    }

    public void setLotEstado(String lotEstado) {
        this.lotEstado = lotEstado;
    }

    public CatArticulo getCatArticulo() {
        return catArticulo;
    }

    public void setCatArticulo(CatArticulo catArticulo) {
        this.catArticulo = catArticulo;
    }

   

    public String getLotDescripion() {
        return lotDescripion;
    }

    public void setLotDescripion(String lotDescripion) {
        this.lotDescripion = lotDescripion;
    }

    

    public Date getLotFechaelab() {
        return lotFechaelab;
    }

    public void setLotFechaelab(Date lotFechaelab) {
        this.lotFechaelab = lotFechaelab;
    }

    public Date getLotFechacaduc() {
        return lotFechacaduc;
    }

    public void setLotFechacaduc(Date lotFechacaduc) {
        this.lotFechacaduc = lotFechacaduc;
    }

    public String getLotRegsanitario() {
        return lotRegsanitario;
    }

    public void setLotRegsanitario(String lotRegsanitario) {
        this.lotRegsanitario = lotRegsanitario;
    }

    public BigInteger getLotCantingreso() {
        return lotCantingreso;
    }

    public void setLotCantingreso(BigInteger lotCantingreso) {
        this.lotCantingreso = lotCantingreso;
    }

    public BigInteger getLotCantegreso() {
        return lotCantegreso;
    }

    public void setLotCantegreso(BigInteger lotCantegreso) {
        this.lotCantegreso = lotCantegreso;
    }

    public BigInteger getLotCantidad() {
        return lotCantidad;
    }

    public void setLotCantidad(BigInteger lotCantidad) {
        this.lotCantidad = lotCantidad;
    }

    public CatBodega getCatBodega() {
        return catBodega;
    }

    public void setCatBodega(CatBodega catBodega) {
        this.catBodega = catBodega;
    }

    public BigInteger getArtId() {
        return artId;
    }

    public void setArtId(BigInteger artId) {
        this.artId = artId;
    }

    public BigInteger getBodId() {
        return bodId;
    }

    public void setBodId(BigInteger bodId) {
        this.bodId = bodId;
    }

    public Double getLotCosto() {
        return lotCosto;
    }

    public void setLotCosto(Double lotCosto) {
        this.lotCosto = lotCosto;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (lotId != null ? lotId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof InvLoteArticulo)) {
            return false;
        }
        InvLoteArticulo other = (InvLoteArticulo) object;
        if ((this.lotId == null && other.lotId != null) || (this.lotId != null && !this.lotId.equals(other.lotId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ec.mil.armada.inv.modelo.inventario.InvLoteArticulo[ lotId=" + lotId + " ]";
    }
    
}
