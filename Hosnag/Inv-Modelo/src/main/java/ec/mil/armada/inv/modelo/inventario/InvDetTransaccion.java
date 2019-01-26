/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.mil.armada.inv.modelo.inventario;

import java.io.Serializable;
import java.math.BigInteger;
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
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;

/**
 *
 * @author maria_rodriguez
 */
@Entity
@Table(name = "INV_DETTRANSACCION")

public class InvDetTransaccion implements Serializable {
    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "DTR_ID")
    @SequenceGenerator(name = "invDetTransaccionSeq", sequenceName = "INV_DETTRANSACCION_SEQ", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "invDetTransaccionSeq")
    private BigInteger dtrId;
    @Column(name = "DTR_CANTINGRESO")
    private BigInteger dtrCantingreso;
    @Column(name = "DTR_CANTEGRESO")
    private BigInteger dtrCantegreso;
    @Column(name = "DTR_COSTOINGRESO")
    private Double dtrCostoIngreso;
    @Column(name = "DTR_COSTROEGRESO")
    private Double dtrCostoEgreso;
    @Basic(optional = false)
    @NotNull
    @Column(name = "DTR_ESTADO")
    private String dtrEstado;
    @Column(name = "EXI_ID")
    private BigInteger exiId;
    @Column(name = "EXI_EXISTENCIA")
    private BigInteger exiExistencia;
    @Column(name = "LOT_ID")
    private BigInteger lotId;
    @Column(name = "CTR_ID")
    private BigInteger ctrId;
    @Column(name = "DTR_TOTAL")
    private Double dtrTotal;
    @Column(name = "DTR_IVA")
    private String dtrIva;
    @Column(name = "DTR_DESC")
    private String dtrDesc;
    @Column(name = "DTR_VALORIVA")
    private Double dtrValorIva;
    @Column(name = "DTR_VALORDESC")
    private Double dtrValorDesc;
    @Column(name = "DTR_PORDESC")
    private BigInteger dtrPorDesc;
    @Column(name = "EXI_COSTOPROM")
    private Double exiCostoProm;
    @Column(name = "DTR_CINGRESOUNITARIO")
    private Double dtrCIngresoUnitario;
    @Column(name = "DTR_MOTIVDEVOLUCION")
    private String dtrMotivDevolucion;


    
    
    
    @Transient
    private InvExistenciaBodega invExistenciaBodega;
    @Transient
    private InvLoteArticulo invLoteArticulo;
    @Transient
    private InvCabTransaccion invCabTransaccion;
    @Transient
    private BigInteger saldo;
    
    

    public InvDetTransaccion() {
    }

    public BigInteger getDtrId() {
        return dtrId;
    }

    public void setDtrId(BigInteger dtrId) {
        this.dtrId = dtrId;
    }

    public BigInteger getDtrCantingreso() {
        return dtrCantingreso;
    }

    public void setDtrCantingreso(BigInteger dtrCantingreso) {
        this.dtrCantingreso = dtrCantingreso;
    }

    public BigInteger getDtrCantegreso() {
        return dtrCantegreso;
    }

    public void setDtrCantegreso(BigInteger dtrCantegreso) {
        this.dtrCantegreso = dtrCantegreso;
    }

   
    public String getDtrEstado() {
        return dtrEstado;
    }

    public void setDtrEstado(String dtrEstado) {
        this.dtrEstado = dtrEstado;
    }

    

    public BigInteger getExiId() {
        return exiId;
    }

    public void setExiId(BigInteger exiId) {
        this.exiId = exiId;
    }

    public BigInteger getLotId() {
        return lotId;
    }

    public void setLotId(BigInteger lotId) {
        this.lotId = lotId;
    }

    public BigInteger getCtrId() {
        return ctrId;
    }

    public void setCtrId(BigInteger ctrId) {
        this.ctrId = ctrId;
    }

    public InvExistenciaBodega getInvExistenciaBodega() {
        return invExistenciaBodega;
    }

    public void setInvExistenciaBodega(InvExistenciaBodega invExistenciaBodega) {
        this.invExistenciaBodega = invExistenciaBodega;
    }

    public InvLoteArticulo getInvLoteArticulo() {
        return invLoteArticulo;
    }

    public void setInvLoteArticulo(InvLoteArticulo invLoteArticulo) {
        this.invLoteArticulo = invLoteArticulo;
    }

    public InvCabTransaccion getInvCabTransaccion() {
        return invCabTransaccion;
    }

    public void setInvCabTransaccion(InvCabTransaccion invCabTransaccion) {
        this.invCabTransaccion = invCabTransaccion;
    }

    public Double getDtrCostoIngreso() {
        return dtrCostoIngreso;
    }

    public void setDtrCostoIngreso(Double dtrCostoIngreso) {
        this.dtrCostoIngreso = dtrCostoIngreso;
    }

    public Double getDtrCostoEgreso() {
        return dtrCostoEgreso;
    }

    public void setDtrCostoEgreso(Double dtrCostoEgreso) {
        this.dtrCostoEgreso = dtrCostoEgreso;
    }

    public Double getDtrTotal() {
        return dtrTotal;
    }

    public void setDtrTotal(Double dtrTotal) {
        this.dtrTotal = dtrTotal;
    }

    public String getDtrIva() {
        return dtrIva;
    }

    public void setDtrIva(String dtrIva) {
        this.dtrIva = dtrIva;
    }

    public String getDtrDesc() {
        return dtrDesc;
    }

    public void setDtrDesc(String dtrDesc) {
        this.dtrDesc = dtrDesc;
    }

    public BigInteger getExiExistencia() {
        return exiExistencia;
    }

    public void setExiExistencia(BigInteger exiExistencia) {
        this.exiExistencia = exiExistencia;
    }

    public Double getDtrValorIva() {
        return dtrValorIva;
    }

    public void setDtrValorIva(Double dtrValorIva) {
        this.dtrValorIva = dtrValorIva;
    }

    public Double getDtrValorDesc() {
        return dtrValorDesc;
    }

    public void setDtrValorDesc(Double dtrValorDesc) {
        this.dtrValorDesc = dtrValorDesc;
    }

    public BigInteger getDtrPorDesc() {
        return dtrPorDesc;
    }

    public void setDtrPorDesc(BigInteger dtrPorDesc) {
        this.dtrPorDesc = dtrPorDesc;
    }

    public Double getExiCostoProm() {
        return exiCostoProm;
    }

    public void setExiCostoProm(Double exiCostoProm) {
        this.exiCostoProm = exiCostoProm;
    }

    public Double getDtrCIngresoUnitario() {
        return dtrCIngresoUnitario;
    }

    public void setDtrCIngresoUnitario(Double dtrCIngresoUnitario) {
        this.dtrCIngresoUnitario = dtrCIngresoUnitario;
    }

    public String getDtrMotivDevolucion() {
        return dtrMotivDevolucion;
    }

    public void setDtrMotivDevolucion(String dtrMotivDevolucion) {
        this.dtrMotivDevolucion = dtrMotivDevolucion;
    }

    public BigInteger getSaldo() {
        return saldo;
    }

    public void setSaldo(BigInteger saldo) {
        this.saldo = saldo;
    }

    
    
    
    
   

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (dtrId != null ? dtrId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof InvDetTransaccion)) {
            return false;
        }
        InvDetTransaccion other = (InvDetTransaccion) object;
        if ((this.dtrId == null && other.dtrId != null) || (this.dtrId != null && !this.dtrId.equals(other.dtrId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ec.mil.armada.inv.modelo.inventario.InvDettransaccion[ dtrId=" + dtrId + " ]";
    }
    
}
