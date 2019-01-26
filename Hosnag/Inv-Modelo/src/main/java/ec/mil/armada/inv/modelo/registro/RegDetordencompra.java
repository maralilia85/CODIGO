/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.mil.armada.inv.modelo.registro;


import ec.mil.armada.inv.modelo.inventario.InvExistenciaBodega;
import ec.mil.armada.inv.modelo.inventario.InvLoteArticulo;
import java.io.Serializable;
import java.math.BigInteger;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;

/**
 *
 * @author maria_rodriguez
 */
@Entity
@Table(name = "REG_DETORDENCOMPRA")

public class RegDetordencompra implements Serializable {
    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "DOC_ID")
    private BigInteger docId;
    @Column(name = "LOT_ID")
    private BigInteger lotId;
    @Column(name = "EXI_ID")
    private BigInteger exiId;
    @Column(name = "COC_ID")
    private BigInteger cocId;
    @Column(name = "DOC_NUMERO")
    private BigInteger docNumero;
    @Column(name = "DOC_CANTIDADSOL")
    private BigInteger docCantidadsol;
    @Column(name = "DOC_PRECIOCOMPRA")
    private Double docPreciocompra;
    @Basic(optional = false)
    @NotNull
    @Column(name = "DOC_ESTADO")
    private String docEstado;
    @Column(name = "DOC_IVA")
    private String docIva;
    @Column(name = "DOC_DESC")
    private String docDesc;
    @Column(name = "DOC_VALORDESC")
    private Double docValorDesc;
    @Column(name = "DOC_PORDESC")
    private BigInteger docPorDesc;
    
    @Transient
    private  InvExistenciaBodega invExistenciaBodega;
    @Transient
    private InvLoteArticulo invLoteArticulo;
    @Transient
    private RegCabordencompra regCabordencompra;
    
    public RegDetordencompra() {
    }

    public BigInteger getDocId() {
        return docId;
    }

    public void setDocId(BigInteger docId) {
        this.docId = docId;
    }

    public BigInteger getLotId() {
        return lotId;
    }

    public void setLotId(BigInteger lotId) {
        this.lotId = lotId;
    }

    public BigInteger getExiId() {
        return exiId;
    }

    public void setExiId(BigInteger exiId) {
        this.exiId = exiId;
    }

    public BigInteger getCocId() {
        return cocId;
    }

    public void setCocId(BigInteger cocId) {
        this.cocId = cocId;
    }

    public BigInteger getDocNumero() {
        return docNumero;
    }

    public void setDocNumero(BigInteger docNumero) {
        this.docNumero = docNumero;
    }

    public BigInteger getDocCantidadsol() {
        return docCantidadsol;
    }

    public void setDocCantidadsol(BigInteger docCantidadsol) {
        this.docCantidadsol = docCantidadsol;
    }

    public Double getDocPreciocompra() {
        return docPreciocompra;
    }

    public void setDocPreciocompra(Double docPreciocompra) {
        this.docPreciocompra = docPreciocompra;
    }

    public String getDocEstado() {
        return docEstado;
    }

    public void setDocEstado(String docEstado) {
        this.docEstado = docEstado;
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

    public RegCabordencompra getRegCabordencompra() {
        return regCabordencompra;
    }

    public void setRegCabordencompra(RegCabordencompra regCabordencompra) {
        this.regCabordencompra = regCabordencompra;
    }

    public String getDocIva() {
        return docIva;
    }

    public void setDocIva(String docIva) {
        this.docIva = docIva;
    }

    public String getDocDesc() {
        return docDesc;
    }

    public void setDocDesc(String docDesc) {
        this.docDesc = docDesc;
    }

    public Double getDocValorDesc() {
        return docValorDesc;
    }

    public void setDocValorDesc(Double docValorDesc) {
        this.docValorDesc = docValorDesc;
    }

    public BigInteger getDocPorDesc() {
        return docPorDesc;
    }

    public void setDocPorDesc(BigInteger docPorDesc) {
        this.docPorDesc = docPorDesc;
    }

   
  
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (docId != null ? docId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof RegDetordencompra)) {
            return false;
        }
        RegDetordencompra other = (RegDetordencompra) object;
        if ((this.docId == null && other.docId != null) || (this.docId != null && !this.docId.equals(other.docId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ec.mil.armada.inv.modelo.inventario.RegDetordencompra[ docId=" + docId + " ]";
    }
    
}