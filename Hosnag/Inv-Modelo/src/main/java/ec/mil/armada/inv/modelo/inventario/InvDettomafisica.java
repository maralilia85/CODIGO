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
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 *
 * @author maria_rodriguez
 */
@Entity
@Table(name = "INV_DETTOMAFISICA")
@NamedQueries({
    @NamedQuery(name = "InvDettomafisica.findAll", query = "SELECT i FROM InvDettomafisica i")})
public class InvDettomafisica implements Serializable {
    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "DTF_ID")
    private BigInteger dtfId;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 256)
    @Column(name = "DTF_OBSERVACION")
    private String dtfObservacion;
    @Basic(optional = false)
    @NotNull
    @Column(name = "DTF_ESTADO")
    private String dtfEstado;
    @Basic(optional = false)
    @NotNull
    @Column(name = "DTF_CANTIDADTOMA")
    private BigInteger dtfCantidadtoma;
    @Basic(optional = false)
    @NotNull
    @Column(name = "DTF_NUMERO")
    private BigInteger dtfNumero;
    @Basic(optional = false)
    @NotNull
    @Column(name = "DTF_SALDOACTUAL")
    private BigInteger dtfSaldoactual;
    @Basic(optional = false)
    @NotNull
    @Column(name = "DTF_CANTDIF")
    private BigInteger dtfCantdif;
    @Basic(optional = false)
    @NotNull
    @Column(name = "DTF_CANTSOB")
    private BigInteger dtfCantsob;
    @Column(name = "CTF_ID")
    private BigInteger ctfId;
    @Column(name = "EXI_ID")
    private BigInteger exiId;
    @Column(name = "LOT_ID")
    private BigInteger lotId;
    @Column(name = "DTF_EXICOSTO")
    private Double dtfExiCosto;
    @Column(name = "DTF_TOTALSOB")
    private Double dtfTotalSob;
    @Column(name = "DTF_TOTALFALT")
    private Double dtfTotalFalt;
    @Column(name = "DTF_TOTAL")
    private Double dtfTotal;
    
    
     @Transient
    private InvCabtomafisica invCabTomaFisica;
    @Transient
    private InvExistenciaBodega invExistenciaBodega;
    @Transient
    private InvLoteArticulo invLoteArticulo;
    
    

    public InvDettomafisica() {
    }

   
    public String getDtfObservacion() {
        return dtfObservacion;
    }

    public void setDtfObservacion(String dtfObservacion) {
        this.dtfObservacion = dtfObservacion;
    }

   

    public BigInteger getDtfCantidadtoma() {
        return dtfCantidadtoma;
    }

    public void setDtfCantidadtoma(BigInteger dtfCantidadtoma) {
        this.dtfCantidadtoma = dtfCantidadtoma;
    }

    public BigInteger getDtfNumero() {
        return dtfNumero;
    }

    public void setDtfNumero(BigInteger dtfNumero) {
        this.dtfNumero = dtfNumero;
    }

    public BigInteger getDtfSaldoactual() {
        return dtfSaldoactual;
    }

    public void setDtfSaldoactual(BigInteger dtfSaldoactual) {
        this.dtfSaldoactual = dtfSaldoactual;
    }

    public BigInteger getDtfCantdif() {
        return dtfCantdif;
    }

    public void setDtfCantdif(BigInteger dtfCantdif) {
        this.dtfCantdif = dtfCantdif;
    }

    public BigInteger getDtfCantsob() {
        return dtfCantsob;
    }

    public void setDtfCantsob(BigInteger dtfCantsob) {
        this.dtfCantsob = dtfCantsob;
    }

    public BigInteger getDtfId() {
        return dtfId;
    }

    public void setDtfId(BigInteger dtfId) {
        this.dtfId = dtfId;
    }

    public String getDtfEstado() {
        return dtfEstado;
    }

    public void setDtfEstado(String dtfEstado) {
        this.dtfEstado = dtfEstado;
    }

    public BigInteger getCtfId() {
        return ctfId;
    }

    public void setCtfId(BigInteger ctfId) {
        this.ctfId = ctfId;
    }

    public InvCabtomafisica getInvCabTomaFisica() {
        return invCabTomaFisica;
    }

    public void setInvCabTomaFisica(InvCabtomafisica invCabTomaFisica) {
        this.invCabTomaFisica = invCabTomaFisica;
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

    public Double getDtfExiCosto() {
        return dtfExiCosto;
    }

    public void setDtfExiCosto(Double dtfExiCosto) {
        this.dtfExiCosto = dtfExiCosto;
    }

    public Double getDtfTotalSob() {
        return dtfTotalSob;
    }

    public void setDtfTotalSob(Double dtfTotalSob) {
        this.dtfTotalSob = dtfTotalSob;
    }

    public Double getDtfTotalFalt() {
        return dtfTotalFalt;
    }

    public void setDtfTotalFalt(Double dtfTotalFalt) {
        this.dtfTotalFalt = dtfTotalFalt;
    }

    public Double getDtfTotal() {
        return dtfTotal;
    }

    public void setDtfTotal(Double dtfTotal) {
        this.dtfTotal = dtfTotal;
    }

  

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (dtfId != null ? dtfId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof InvDettomafisica)) {
            return false;
        }
        InvDettomafisica other = (InvDettomafisica) object;
        if ((this.dtfId == null && other.dtfId != null) || (this.dtfId != null && !this.dtfId.equals(other.dtfId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ec.mil.armada.inv.modelo.inventario.InvDettomafisica[ dtfId=" + dtfId + " ]";
    }
    
}
