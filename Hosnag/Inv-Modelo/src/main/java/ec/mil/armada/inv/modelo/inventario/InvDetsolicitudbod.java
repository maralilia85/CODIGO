/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.mil.armada.inv.modelo.inventario;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

/**
 *
 * @author maria_rodriguez
 */
@Entity
@Table(name = "INV_DETSOLICITUDBOD")
@NamedQueries({
    @NamedQuery(name = "InvDetsolicitudbod.findAll", query = "SELECT i FROM InvDetsolicitudbod i")})
public class InvDetsolicitudbod implements Serializable {
    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "DSB_ID")
    private BigInteger dsbId;
    @Basic(optional = false)
    @NotNull
    @Column(name = "DSB_NUMERO")
    private BigInteger dsbNumero;
    @Basic(optional = false)
    @NotNull
    @Column(name = "DSB_ESTADO")
    private String dsbEstado;
    @Basic(optional = false)
    @NotNull
    @Column(name = "DSB_CANTSOLICITADA")
    private BigInteger dsbCantsolicitada;
    @Basic(optional = false)
    @NotNull
    @Column(name = "DSB_CANTAPROBADA")
    private BigInteger dsbCantaprobada;
    @Basic(optional = false)
    @NotNull
    @Column(name = "DSB_STOCKSATISFACE")
    private String dsbStocksatisface;
    @Column(name = "DSB_STOCKNOSATISFACE")
    private String dsbStockNoSatisface;
    @JoinColumn(name = "CSB_ID", referencedColumnName = "CSB_ID")
    @ManyToOne
    private InvCabsolicitudbod invCabsolicitudbod;
    @JoinColumn(name = "EXI_ID", referencedColumnName = "EXI_ID")
    @ManyToOne
    private InvExistenciaBodega invExistenciaBodega;
    @Column(name = "DBS_DEMANDAREQ")
    private BigInteger dsbDemandaReq;

    public InvDetsolicitudbod() {
    }

    public BigInteger getDsbId() {
        return dsbId;
    }

    public void setDsbId(BigInteger dsbId) {
        this.dsbId = dsbId;
    }

    public String getDsbEstado() {
        return dsbEstado;
    }

    public void setDsbEstado(String dsbEstado) {
        this.dsbEstado = dsbEstado;
    }

    public String getDsbStocksatisface() {
        return dsbStocksatisface;
    }

    public void setDsbStocksatisface(String dsbStocksatisface) {
        this.dsbStocksatisface = dsbStocksatisface;
    }

  

    public BigInteger getDsbNumero() {
        return dsbNumero;
    }

    public void setDsbNumero(BigInteger dsbNumero) {
        this.dsbNumero = dsbNumero;
    }

   
    public BigInteger getDsbCantsolicitada() {
        return dsbCantsolicitada;
    }

    public void setDsbCantsolicitada(BigInteger dsbCantsolicitada) {
        this.dsbCantsolicitada = dsbCantsolicitada;
    }

    public BigInteger getDsbCantaprobada() {
        return dsbCantaprobada;
    }

    public void setDsbCantaprobada(BigInteger dsbCantaprobada) {
        this.dsbCantaprobada = dsbCantaprobada;
    }

   

    public InvCabsolicitudbod getInvCabsolicitudbod() {
        return invCabsolicitudbod;
    }

    public void setInvCabsolicitudbod(InvCabsolicitudbod invCabsolicitudbod) {
        this.invCabsolicitudbod = invCabsolicitudbod;
    }

    public InvExistenciaBodega getInvExistenciaBodega() {
        return invExistenciaBodega;
    }

    public void setInvExistenciaBodega(InvExistenciaBodega invExistenciaBodega) {
        this.invExistenciaBodega = invExistenciaBodega;
    }

    public String getDsbStockNoSatisface() {
        return dsbStockNoSatisface;
    }

    public void setDsbStockNoSatisface(String dsbStockNoSatisface) {
        this.dsbStockNoSatisface = dsbStockNoSatisface;
    }

    public BigInteger getDsbDemandaReq() {
        return dsbDemandaReq;
    }

    public void setDsbDemandaReq(BigInteger dsbDemandaReq) {
        this.dsbDemandaReq = dsbDemandaReq;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (dsbId != null ? dsbId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof InvDetsolicitudbod)) {
            return false;
        }
        InvDetsolicitudbod other = (InvDetsolicitudbod) object;
        if ((this.dsbId == null && other.dsbId != null) || (this.dsbId != null && !this.dsbId.equals(other.dsbId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ec.mil.armada.inv.modelo.inventario.InvDetsolicitudbod[ dsbId=" + dsbId + " ]";
    }
    
}
