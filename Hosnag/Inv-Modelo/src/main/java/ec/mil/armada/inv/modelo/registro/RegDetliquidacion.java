/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.mil.armada.inv.modelo.registro;


import ec.mil.armada.inv.modelo.inventario.InvExistenciaBodega;
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
@Table(name = "REG_DETLIQUIDACION")
@SequenceGenerator(name = "regDetliquidacion", sequenceName = "REG_DETLIQUIDACION_SEQ", allocationSize = 1)
public class RegDetliquidacion implements Serializable {
    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "regDetliquidacion")
    @Basic(optional = false)
    @NotNull
    @Column(name = "DLI_ID")
    private BigInteger dliId;
    @Column(name = "EXI_ID")
    private BigInteger exiId;
    @Column(name = "CLI_ID")
    private BigInteger cliId;
    @Column(name = "DLI_NUMERO")
    private BigInteger dliNumero;
    @Size(max = 10)
    @Column(name = "DLI_AFISCAL")
    private String dliAfiscal;
    @Column(name = "DLI_VALOR")
    private Double dliValor;
    @Size(max = 1)
    @Column(name = "DLI_ESTADO")
    private String dliEstado;
    @Column(name = "DLI_CANTIDAD")
    private BigInteger dliCantidad;
    @Column(name = "DLI_FECHA")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dliFecha;
    @Column(name = "DLI_VTOTAL")
    private Double dliVTotal;
    @Column(name = "DLI_TIVA")
    private Double dliTIva;
    @Column(name = "DLI_PORC")
    private BigInteger dliPorc;
    @Column(name = "DLI_TDESC")
    private Double dliTDesc;
    
    
    @Transient
    private RegCabliquidacion regCabLiquidacion;
   
    @Transient
    private InvExistenciaBodega invExistenciaBodega;

    public RegDetliquidacion() {
    }

    public RegDetliquidacion(BigInteger dliId) {
        this.dliId = dliId;
    }

    public BigInteger getDliId() {
        return dliId;
    }

    public void setDliId(BigInteger dliId) {
        this.dliId = dliId;
    }

    public BigInteger getExiId() {
        return exiId;
    }

    public void setExiId(BigInteger exiId) {
        this.exiId = exiId;
    }

    public BigInteger getCliId() {
        return cliId;
    }

    public void setCliId(BigInteger cliId) {
        this.cliId = cliId;
    }

    public BigInteger getDliNumero() {
        return dliNumero;
    }

    public void setDliNumero(BigInteger dliNumero) {
        this.dliNumero = dliNumero;
    }

    public String getDliAfiscal() {
        return dliAfiscal;
    }

    public void setDliAfiscal(String dliAfiscal) {
        this.dliAfiscal = dliAfiscal;
    }

    public Double getDliValor() {
        return dliValor;
    }

    public void setDliValor(Double dliValor) {
        this.dliValor = dliValor;
    }

    public String getDliEstado() {
        return dliEstado;
    }

    public void setDliEstado(String dliEstado) {
        this.dliEstado = dliEstado;
    }

    public BigInteger getDliCantidad() {
        return dliCantidad;
    }

    public void setDliCantidad(BigInteger dliCantidad) {
        this.dliCantidad = dliCantidad;
    }

    public Date getDliFecha() {
        return dliFecha;
    }

    public void setDliFecha(Date dliFecha) {
        this.dliFecha = dliFecha;
    }

    public Double getDliVTotal() {
        return dliVTotal;
    }

    public void setDliVTotal(Double dliVTotal) {
        this.dliVTotal = dliVTotal;
    }

    public RegCabliquidacion getRegCabLiquidacion() {
        return regCabLiquidacion;
    }

    public void setRegCabLiquidacion(RegCabliquidacion regCabLiquidacion) {
        this.regCabLiquidacion = regCabLiquidacion;
    }


    public InvExistenciaBodega getInvExistenciaBodega() {
        return invExistenciaBodega;
    }

    public void setInvExistenciaBodega(InvExistenciaBodega invExistenciaBodega) {
        this.invExistenciaBodega = invExistenciaBodega;
    }

    public Double getDliTIva() {
        return dliTIva;
    }

    public void setDliTIva(Double dliTIva) {
        this.dliTIva = dliTIva;
    }

    public BigInteger getDliPorc() {
        return dliPorc;
    }

    public void setDliPorc(BigInteger dliPorc) {
        this.dliPorc = dliPorc;
    }

    public Double getDliTDesc() {
        return dliTDesc;
    }

    public void setDliTDesc(Double dliTDesc) {
        this.dliTDesc = dliTDesc;
    }

   
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (dliId != null ? dliId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof RegDetliquidacion)) {
            return false;
        }
        RegDetliquidacion other = (RegDetliquidacion) object;
        if ((this.dliId == null && other.dliId != null) || (this.dliId != null && !this.dliId.equals(other.dliId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ec.mil.armada.inv.modelo.registro.RegDetliquidacion[ dliId=" + dliId + " ]";
    }
    
}
