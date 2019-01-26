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
import java.util.Date;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 *
 * @author maria_rodriguez
 */
@Entity
@Table(name = "INV_CABTOMAFISICA")
@SequenceGenerator(name = "invCabTomaFisicaSeq", sequenceName = "INV_CABTOMAFISICA_SEQ", allocationSize = 1)
public class InvCabtomafisica implements Serializable {
    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "invCabTomaFisicaSeq")
    @Basic(optional = false)
    @NotNull
    @Column(name = "CTF_ID")
    private BigInteger ctfId;
    @Basic(optional = false)
    @NotNull
    @Column(name = "CTF_NUMERO")
    private BigInteger ctfNumero;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 256)
    @Column(name = "CTF_ANOFISCAL")
    private String ctfAnofiscal;
    @Basic(optional = false)
    @NotNull
    @Column(name = "CTF_FECHA")
    @Temporal(TemporalType.TIMESTAMP)
    private Date ctfFecha;
    @Basic(optional = false)
    @NotNull
    @Column(name = "CTF_ESTADO")
    private String ctfEstado;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 256)
    @Column(name = "CTF_OBSERVACION")
    private String ctfObservacion;
    @Basic(optional = false)
    @NotNull
    @Column(name = "CTF_IMPETIQUETAS")
    private String ctfImpetiquetas;
    @Column(name = "CTF_TIPOINV")
    private String ctfTipoInv;
    @Column(name = "CTF_TIPOART")
    private BigInteger ctfTipoArt;
    @Column(name = "BOD_ID")
    private BigInteger ctfBodId;
     @Column(name = "CTF_JEFEBODEGA")
    private String ctfJefeBodega;
    
    @Transient
     private CatArticulo catArticulo;
    @Transient
    private CatBodega catBodega;
    
    
    public InvCabtomafisica() {
    }

   
    public BigInteger getCtfNumero() {
        return ctfNumero;
    }

    public void setCtfNumero(BigInteger ctfNumero) {
        this.ctfNumero = ctfNumero;
    }

    public String getCtfAnofiscal() {
        return ctfAnofiscal;
    }

    public void setCtfAnofiscal(String ctfAnofiscal) {
        this.ctfAnofiscal = ctfAnofiscal;
    }

    public Date getCtfFecha() {
        return ctfFecha;
    }

    public void setCtfFecha(Date ctfFecha) {
        this.ctfFecha = ctfFecha;
    }

    public String getCtfObservacion() {
        return ctfObservacion;
    }

    public void setCtfObservacion(String ctfObservacion) {
        this.ctfObservacion = ctfObservacion;
    }

    public BigInteger getCtfId() {
        return ctfId;
    }

    public void setCtfId(BigInteger ctfId) {
        this.ctfId = ctfId;
    }

    public String getCtfEstado() {
        return ctfEstado;
    }

    public void setCtfEstado(String ctfEstado) {
        this.ctfEstado = ctfEstado;
    }

    public String getCtfImpetiquetas() {
        return ctfImpetiquetas;
    }

    public void setCtfImpetiquetas(String ctfImpetiquetas) {
        this.ctfImpetiquetas = ctfImpetiquetas;
    }

    public String getCtfTipoInv() {
        return ctfTipoInv;
    }

    public void setCtfTipoInv(String ctfTipoInv) {
        this.ctfTipoInv = ctfTipoInv;
    }

    public BigInteger getCtfTipoArt() {
        return ctfTipoArt;
    }

    public void setCtfTipoArt(BigInteger ctfTipoArt) {
        this.ctfTipoArt = ctfTipoArt;
    }

    public CatArticulo getCatArticulo() {
        return catArticulo;
    }

    public void setCatArticulo(CatArticulo catArticulo) {
        this.catArticulo = catArticulo;
    }

    public BigInteger getCtfBodId() {
        return ctfBodId;
    }

    public void setCtfBodId(BigInteger ctfBodId) {
        this.ctfBodId = ctfBodId;
    }

    public CatBodega getCatBodega() {
        return catBodega;
    }

    public void setCatBodega(CatBodega catBodega) {
        this.catBodega = catBodega;
    }

    public String getCtfJefeBodega() {
        return ctfJefeBodega;
    }

    public void setCtfJefeBodega(String ctfJefeBodega) {
        this.ctfJefeBodega = ctfJefeBodega;
    }

  

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (ctfId != null ? ctfId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof InvCabtomafisica)) {
            return false;
        }
        InvCabtomafisica other = (InvCabtomafisica) object;
        if ((this.ctfId == null && other.ctfId != null) || (this.ctfId != null && !this.ctfId.equals(other.ctfId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ec.mil.armada.inv.modelo.inventario.InvCabtomafisica[ ctfId=" + ctfId + " ]";
    }
    
}
