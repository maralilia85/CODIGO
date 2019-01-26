/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.mil.armada.inv.modelo.registro;

import ec.mil.armada.inv.modelo.catalago.CatBodega;
import ec.mil.armada.inv.modelo.catalago.CatGeneral;
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

/**
 *
 * @author DET-PC
 */
@Entity
@Table(name = "REG_CABLIQUIDACION")
@SequenceGenerator(name = "regCabliquidacion", sequenceName = "REG_CABLIQUIDACION_SEQ", allocationSize = 1)

public class RegCabliquidacion implements Serializable {
    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "regCabliquidacion")
    @Basic(optional = false)
    @NotNull
    @Column(name = "CLI_ID")
    private BigInteger cliId;
    @Column(name = "CLI_NUMERO")
    private BigInteger cliNumero;
    @Size(max = 10)
    @Column(name = "CLI_AFISCAL")
    private String cliAfiscal;
    @Column(name = "CLI_CLAVETABLA")
    private BigInteger cliClavetabla;
    @Size(max = 1)
    @Column(name = "CLI_SIGLATABLA")
    private String cliSiglatabla;
    @Size(max = 100)
    @Column(name = "CLI_OBSERVACION")
    private String cliObservacion;
    @Size(max = 1)
    @Column(name = "CLI_ESTADO")
    private String cliEstado;
    @Column(name = "CLI_FECHA")
    @Temporal(TemporalType.TIMESTAMP)
    private Date cliFecha;
    @Column(name = "CLI_FECHAHASTA")
    @Temporal(TemporalType.TIMESTAMP)
    private Date cliFechaHasta;
    @Column(name = "CLI_SUBTOTAL")
    private Double cliSubTotal;
    @Column(name = "CLI_IVA")
    private Double cliIva;
    @Column(name = "CLI_PORC")
    private Double cliPorc;
    @Column(name = "CLI_TOTAL")
    private Double cliTotal;
    @Column(name = "CLI_DESC")
    private Double cliDesc;
    @Column(name = "CLI_BODID")
    private BigInteger cliBodId;
    @Column(name = "CLI_SERVID")
    private BigInteger cliServId;
    @Transient
    private CatBodega catBodega;
    @Transient
    private CatGeneral catGeneral;

    public RegCabliquidacion() {
    }

    public RegCabliquidacion(BigInteger cliId) {
        this.cliId = cliId;
    }

    public BigInteger getCliId() {
        return cliId;
    }

    public void setCliId(BigInteger cliId) {
        this.cliId = cliId;
    }

    public BigInteger getCliNumero() {
        return cliNumero;
    }

    public void setCliNumero(BigInteger cliNumero) {
        this.cliNumero = cliNumero;
    }

    public String getCliAfiscal() {
        return cliAfiscal;
    }

    public void setCliAfiscal(String cliAfiscal) {
        this.cliAfiscal = cliAfiscal;
    }

    public BigInteger getCliClavetabla() {
        return cliClavetabla;
    }

    public void setCliClavetabla(BigInteger cliClavetabla) {
        this.cliClavetabla = cliClavetabla;
    }

    public String getCliSiglatabla() {
        return cliSiglatabla;
    }

    public void setCliSiglatabla(String cliSiglatabla) {
        this.cliSiglatabla = cliSiglatabla;
    }

    public String getCliObservacion() {
        return cliObservacion;
    }

    public void setCliObservacion(String cliObservacion) {
        this.cliObservacion = cliObservacion;
    }

    public String getCliEstado() {
        return cliEstado;
    }

    public void setCliEstado(String cliEstado) {
        this.cliEstado = cliEstado;
    }

    public Date getCliFecha() {
        return cliFecha;
    }

    public void setCliFecha(Date cliFecha) {
        this.cliFecha = cliFecha;
    }
     public Date getCliFechaHasta() {
        return cliFechaHasta;
    }

    public void setCliFechaHasta(Date cliFechaHasta) {
        this.cliFechaHasta = cliFechaHasta;
    }

    public Double getCliSubTotal() {
        return cliSubTotal;
    }

    public void setCliSubTotal(Double cliSubTotal) {
        this.cliSubTotal = cliSubTotal;
    }

    public Double getCliIva() {
        return cliIva;
    }

    public void setCliIva(Double cliIva) {
        this.cliIva = cliIva;
    }

    public Double getCliPorc() {
        return cliPorc;
    }

    public void setCliPorc(Double cliPorc) {
        this.cliPorc = cliPorc;
    }

    public Double getCliTotal() {
        return cliTotal;
    }

    public void setCliTotal(Double cliTotal) {
        this.cliTotal = cliTotal;
    }

    public CatBodega getCatBodega() {
        return catBodega;
    }

    public void setCatBodega(CatBodega catBodega) {
        this.catBodega = catBodega;
    }

    public CatGeneral getCatGeneral() {
        return catGeneral;
    }

    public void setCatGeneral(CatGeneral catGeneral) {
        this.catGeneral = catGeneral;
    }

    public BigInteger getCliBodId() {
        return cliBodId;
    }

    public void setCliBodId(BigInteger cliBodId) {
        this.cliBodId = cliBodId;
    }

    public BigInteger getCliServId() {
        return cliServId;
    }

    public void setCliServId(BigInteger cliServId) {
        this.cliServId = cliServId;
    }

    public Double getCliDesc() {
        return cliDesc;
    }

    public void setCliDesc(Double cliDesc) {
        this.cliDesc = cliDesc;
    }
    

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (cliId != null ? cliId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof RegCabliquidacion)) {
            return false;
        }
        RegCabliquidacion other = (RegCabliquidacion) object;
        if ((this.cliId == null && other.cliId != null) || (this.cliId != null && !this.cliId.equals(other.cliId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ec.mil.armada.inv.modelo.registro.RegCabliquidacion[ cliId=" + cliId + " ]";
    }

   
}
