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
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
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
@Table(name = "INV_EXISTENCIA_BODEGA")

public class InvExistenciaBodega implements Serializable {
    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "EXI_ID")
    private BigInteger exiId;
    @Basic(optional = false)
    @NotNull
    @Column(name = "EXI_EXISTENCIA")
    private BigInteger exiExistencia;
    @Column(name = "EXI_COMPROMETIDO")
    private BigInteger exiComprometido;
    @Column(name = "EXI_STOCKMIN")
    private BigInteger exiStockmin;
    @Column(name = "EXI_STOCKMAX")
    private BigInteger exiStockmax;
    @Column(name = "EXI_PRECIOCOSTO")
    private Double exiPreciocosto;
    @Column(name = "EXI_PRECIOVENTA")
    private Double exiPrecioventa;
    @Column(name = "EXI_COSTOPROM")
    private Double exiCostoprom;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 1)
    @Column(name = "EXI_ESTADO")
    private String exiEstado;
    @Column(name = "EXI_STOCKSEG")
    private BigInteger exiStockseg;
    @Column(name = "EXI_COSTOINICIAL")
    private Double exiCostoinicial;
    @Column(name = "EXI_CANTIDADINICIAL")
    private BigInteger exiCantidadinicial;
    @JoinColumn(name = "ART_ID", referencedColumnName = "ART_ID")
    @ManyToOne
    private CatArticulo catArticulo;
   
    @Column(name = "BOD_ID")
    private BigInteger bodId;
    @Column(name = "BOD_ID_UBI")
    private BigInteger bodIdUbi;
     @Transient
      private CatBodega catBodega;
     @Transient
      private CatBodega catBodegaUbi;
     @Transient
     private Double exiTotal;
    
    
//    @JoinColumn(name = "BOD_ID", referencedColumnName = "BOD_ID")
//    @ManyToOne
//    private CatBodega catBodega;

    public InvExistenciaBodega() {
    }

    public BigInteger getExiId() {
        return exiId;
    }

    public void setExiId(BigInteger exiId) {
        this.exiId = exiId;
    }

    public BigInteger getExiExistencia() {
        return exiExistencia;
    }

    public void setExiExistencia(BigInteger exiExistencia) {
        this.exiExistencia = exiExistencia;
    }

    public BigInteger getExiComprometido() {
        return exiComprometido;
    }

    public void setExiComprometido(BigInteger exiComprometido) {
        this.exiComprometido = exiComprometido;
    }


   

    public Double getExiPreciocosto() {
        return exiPreciocosto;
    }

    public void setExiPreciocosto(Double exiPreciocosto) {
        this.exiPreciocosto = exiPreciocosto;
    }

    public Double getExiPrecioventa() {
        return exiPrecioventa;
    }

    public void setExiPrecioventa(Double exiPrecioventa) {
        this.exiPrecioventa = exiPrecioventa;
    }

    public Double getExiCostoprom() {
        return exiCostoprom;
    }

    public void setExiCostoprom(Double exiCostoprom) {
        this.exiCostoprom = exiCostoprom;
    }

    public String getExiEstado() {
        return exiEstado;
    }

    public void setExiEstado(String exiEstado) {
        this.exiEstado = exiEstado;
    }

    public BigInteger getExiStockmin() {
        return exiStockmin;
    }

    public void setExiStockmin(BigInteger exiStockmin) {
        this.exiStockmin = exiStockmin;
    }

    public BigInteger getExiStockmax() {
        return exiStockmax;
    }

    public void setExiStockmax(BigInteger exiStockmax) {
        this.exiStockmax = exiStockmax;
    }

    public BigInteger getExiStockseg() {
        return exiStockseg;
    }

    public void setExiStockseg(BigInteger exiStockseg) {
        this.exiStockseg = exiStockseg;
    }

    public Double getExiCostoinicial() {
        return exiCostoinicial;
    }

    public void setExiCostoinicial(Double exiCostoinicial) {
        this.exiCostoinicial = exiCostoinicial;
    }

    public BigInteger getExiCantidadinicial() {
        return exiCantidadinicial;
    }

    public void setExiCantidadinicial(BigInteger exiCantidadinicial) {
        this.exiCantidadinicial = exiCantidadinicial;
    }



    public CatArticulo getCatArticulo() {
        return catArticulo;
    }

    public void setCatArticulo(CatArticulo catArticulo) {
        this.catArticulo = catArticulo;
    }

    public BigInteger getBodId() {
        return bodId;
    }

    public void setBodId(BigInteger bodId) {
        this.bodId = bodId;
    }

    public CatBodega getCatBodega() {
        return catBodega;
    }

    public void setCatBodega(CatBodega catBodega) {
        this.catBodega = catBodega;
    }

   

    public BigInteger getBodIdUbi() {
        return bodIdUbi;
    }

    public void setBodIdUbi(BigInteger bodIdUbi) {
        this.bodIdUbi = bodIdUbi;
    }

    public CatBodega getCatBodegaUbi() {
        return catBodegaUbi;
    }

    public void setCatBodegaUbi(CatBodega catBodegaUbi) {
        this.catBodegaUbi = catBodegaUbi;
    }

    public Double getExiTotal() {
        return exiTotal;
    }

    public void setExiTotal(Double exiTotal) {
        this.exiTotal = exiTotal;
    }

    
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (exiId != null ? exiId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof InvExistenciaBodega)) {
            return false;
        }
        InvExistenciaBodega other = (InvExistenciaBodega) object;
        if ((this.exiId == null && other.exiId != null) || (this.exiId != null && !this.exiId.equals(other.exiId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ec.mil.armada.inv.modelo.inventario.InvExistenciaartBodega[ exiId=" + exiId + " ]";
    }
    
}
