/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.mil.armada.inv.modelo.inventario;

import ec.mil.armada.inv.modelo.catalago.CatArticulo;
import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

/**
 *
 * @author maria_rodriguez
 */
@Entity
@Table(name = "INV_COSTO_ARTICULO")

public class InvCostoArticulo implements Serializable {
    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "COS_ID")
    private BigInteger cosId;
    @Basic(optional = false)
    @NotNull
    @Column(name = "COS_ESTADO")
    private String cosEstado;
    @Basic(optional = false)
    @NotNull
    @Column(name = "COS_PRECIOCOSTO")
    private Double cosPreciocosto;
    @Basic(optional = false)
    @NotNull
    @Column(name = "COS_CLAVETABLA")
    private BigInteger cosClavetabla;
    @Basic(optional = false)
    @NotNull
    @Column(name = "COS_SIGLASTABLA")
    private String cosSiglastabla;
    @Basic(optional = false)
    @NotNull
    @Column(name = "COS_ORDEN")
    private BigInteger cosOrden;
    @JoinColumn(name = "ART_ID", referencedColumnName = "ART_ID")
    @ManyToOne
    private CatArticulo catArticulo;

    public InvCostoArticulo() {
    }

    public BigInteger getCosId() {
        return cosId;
    }

    public void setCosId(BigInteger cosId) {
        this.cosId = cosId;
    }

    public String getCosEstado() {
        return cosEstado;
    }

    public void setCosEstado(String cosEstado) {
        this.cosEstado = cosEstado;
    }

    public String getCosSiglastabla() {
        return cosSiglastabla;
    }

    public void setCosSiglastabla(String cosSiglastabla) {
        this.cosSiglastabla = cosSiglastabla;
    }

    public CatArticulo getCatArticulo() {
        return catArticulo;
    }

    public void setCatArticulo(CatArticulo catArticulo) {
        this.catArticulo = catArticulo;
    }

    public Double getCosPreciocosto() {
        return cosPreciocosto;
    }

    public void setCosPreciocosto(Double cosPreciocosto) {
        this.cosPreciocosto = cosPreciocosto;
    }

  

    public BigInteger getCosClavetabla() {
        return cosClavetabla;
    }

    public void setCosClavetabla(BigInteger cosClavetabla) {
        this.cosClavetabla = cosClavetabla;
    }

  

    public BigInteger getCosOrden() {
        return cosOrden;
    }

    public void setCosOrden(BigInteger cosOrden) {
        this.cosOrden = cosOrden;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (cosId != null ? cosId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof InvCostoArticulo)) {
            return false;
        }
        InvCostoArticulo other = (InvCostoArticulo) object;
        if ((this.cosId == null && other.cosId != null) || (this.cosId != null && !this.cosId.equals(other.cosId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ec.mil.armada.inv.modelo.inventario.InvCostoArticulo[ cosId=" + cosId + " ]";
    }
    
}
