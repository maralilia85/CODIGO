/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.mil.armada.inv.modelo.registro;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 *
 * @author DET-PC
 */
@Entity
@Table(name = "REG_DETTEMP")

public class RegDettemp implements Serializable {
    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "DTEM_ID")
    private BigInteger dtemId;
    @Column(name = "DTEM_FECHA")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dtemFecha;
    @Column(name = "DTEM_AM")
    private BigInteger dtemAm;
    @Column(name = "DTEM_PM")
    private BigInteger dtemPm;
    @Column(name = "DTEM_PROM")
    private Double dtemProm;
    @Size(max = 256)
    @Column(name = "DTEM_OBSERACION")
    private String dtemObseracion;
    @JoinColumn(name = "TEM_ID", referencedColumnName = "TEM_ID")
    @ManyToOne
    private RegCabtemp regCabtemp;

    public RegDettemp() {
    }

    public RegDettemp(BigInteger dtemId) {
        this.dtemId = dtemId;
    }

    public BigInteger getDtemId() {
        return dtemId;
    }

    public void setDtemId(BigInteger dtemId) {
        this.dtemId = dtemId;
    }

    public Date getDtemFecha() {
        return dtemFecha;
    }

    public void setDtemFecha(Date dtemFecha) {
        this.dtemFecha = dtemFecha;
    }

    public BigInteger getDtemAm() {
        return dtemAm;
    }

    public void setDtemAm(BigInteger dtemAm) {
        this.dtemAm = dtemAm;
    }

    public BigInteger getDtemPm() {
        return dtemPm;
    }

    public void setDtemPm(BigInteger dtemPm) {
        this.dtemPm = dtemPm;
    }

    public Double getDtemProm() {
        return dtemProm;
    }

    public void setDtemProm(Double dtemProm) {
        this.dtemProm = dtemProm;
    }

    public String getDtemObseracion() {
        return dtemObseracion;
    }

    public void setDtemObseracion(String dtemObseracion) {
        this.dtemObseracion = dtemObseracion;
    }

    public RegCabtemp getRegCabtemp() {
        return regCabtemp;
    }

    public void setRegCabtemp(RegCabtemp regCabtemp) {
        this.regCabtemp = regCabtemp;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (dtemId != null ? dtemId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof RegDettemp)) {
            return false;
        }
        RegDettemp other = (RegDettemp) object;
        if ((this.dtemId == null && other.dtemId != null) || (this.dtemId != null && !this.dtemId.equals(other.dtemId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ec.mil.armada.inv.modelo.registro.RegDettemp[ dtemId=" + dtemId + " ]";
    }
    
}
