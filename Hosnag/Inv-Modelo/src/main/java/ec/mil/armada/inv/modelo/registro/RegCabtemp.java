/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.mil.armada.inv.modelo.registro;

import ec.mil.armada.inv.modelo.catalago.CatBodega;
import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 *
 * @author maria_rodriguez
 */
@Entity
@Table(name = "REG_CABTEMP")
@SequenceGenerator(name = "regCabTemp", sequenceName = "REG_CABTEMP_SEQ", allocationSize = 1)
public class RegCabtemp implements Serializable {
    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "regCabTemp")
    @Basic(optional = false)
    @NotNull
    @Column(name = "TEM_ID")
    private BigInteger temId;
    @Column(name = "BOD_ID")
    private BigInteger bodId;
    @Size(max = 2)
    @Column(name = "TEM_TIPO")
    private String temTipo;
    @Column(name = "TEM_NOTER")
    private BigInteger temNoter;
    @Size(max = 1)
    @Column(name = "TEM_MES")
    private String temMes;
    @Size(max = 1)
    @Column(name = "TEM_ANIO")
    private String temAnio;
    @Column(name = "TEM_MIN")
    private BigInteger temMin;
    @Column(name = "TEM_MAX")
    private BigInteger temMax;
    
    @Transient
     private CatBodega catBodega;
  

    public RegCabtemp() {
    }

    public RegCabtemp(BigInteger temId) {
        this.temId = temId;
    }

    public BigInteger getTemId() {
        return temId;
    }

    public void setTemId(BigInteger temId) {
        this.temId = temId;
    }

    public String getTemTipo() {
        return temTipo;
    }

    public void setTemTipo(String temTipo) {
        this.temTipo = temTipo;
    }

    public BigInteger getTemNoter() {
        return temNoter;
    }

    public void setTemNoter(BigInteger temNoter) {
        this.temNoter = temNoter;
    }

    public String getTemMes() {
        return temMes;
    }

    public void setTemMes(String temMes) {
        this.temMes = temMes;
    }

    public String getTemAnio() {
        return temAnio;
    }

    public void setTemAnio(String temAnio) {
        this.temAnio = temAnio;
    }

    public BigInteger getBodId() {
        return bodId;
    }

    public void setBodId(BigInteger bodId) {
        this.bodId = bodId;
    }

    public BigInteger getTemMin() {
        return temMin;
    }

    public void setTemMin(BigInteger temMin) {
        this.temMin = temMin;
    }

    public BigInteger getTemMax() {
        return temMax;
    }

    public void setTemMax(BigInteger temMax) {
        this.temMax = temMax;
    }

    public CatBodega getCatBodega() {
        return catBodega;
    }

    public void setCatBodega(CatBodega catBodega) {
        this.catBodega = catBodega;
    }

    
   

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (temId != null ? temId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof RegCabtemp)) {
            return false;
        }
        RegCabtemp other = (RegCabtemp) object;
        if ((this.temId == null && other.temId != null) || (this.temId != null && !this.temId.equals(other.temId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ec.mil.armada.inv.modelo.registro.RegCabtemp[ temId=" + temId + " ]";
    }
    
}
