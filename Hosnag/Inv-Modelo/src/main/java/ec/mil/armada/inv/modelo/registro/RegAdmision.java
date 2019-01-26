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
@Table(name = "REG_ADMISION")
@NamedQueries({
    @NamedQuery(name = "RegAdmision.findAll", query = "SELECT r FROM RegAdmision r")})
public class RegAdmision implements Serializable {
    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "ADM_ID")
    private BigInteger admId;
    @Column(name = "PAC_ID")
    private BigInteger pacId;
    @Column(name = "ADM_NUMERO")
    private BigInteger admNumero;
    @Column(name = "ADM_FECHA")
    @Temporal(TemporalType.TIMESTAMP)
    private Date admFecha;
    @Size(max = 10)
    @Column(name = "ADM_PISO")
    private String admPiso;
    @Size(max = 10)
    @Column(name = "ADM_CAMA")
    private String admCama;
    @Size(max = 50)
    @Column(name = "ADM_SERVICIO")
    private String admServicio;
    @Size(max = 50)
    @Column(name = "ADM_SEGURO")
    private String admSeguro;
    @Size(max = 50)
    @Column(name = "ADM_SITUACION")
    private String admSituacion;
    @Column(name = "ADM_ESTADO")
    private String admEstado;

    public RegAdmision() {
    }

    public RegAdmision(BigInteger admId) {
        this.admId = admId;
    }

    public BigInteger getAdmId() {
        return admId;
    }

    public void setAdmId(BigInteger admId) {
        this.admId = admId;
    }

    public BigInteger getPacId() {
        return pacId;
    }

    public void setPacId(BigInteger pacId) {
        this.pacId = pacId;
    }

    public BigInteger getAdmNumero() {
        return admNumero;
    }

    public void setAdmNumero(BigInteger admNumero) {
        this.admNumero = admNumero;
    }

    public Date getAdmFecha() {
        return admFecha;
    }

    public void setAdmFecha(Date admFecha) {
        this.admFecha = admFecha;
    }

    public String getAdmPiso() {
        return admPiso;
    }

    public void setAdmPiso(String admPiso) {
        this.admPiso = admPiso;
    }

    public String getAdmCama() {
        return admCama;
    }

    public void setAdmCama(String admCama) {
        this.admCama = admCama;
    }

    public String getAdmServicio() {
        return admServicio;
    }

    public void setAdmServicio(String admServicio) {
        this.admServicio = admServicio;
    }

    public String getAdmSeguro() {
        return admSeguro;
    }

    public void setAdmSeguro(String admSeguro) {
        this.admSeguro = admSeguro;
    }

    public String getAdmSituacion() {
        return admSituacion;
    }

    public void setAdmSituacion(String admSituacion) {
        this.admSituacion = admSituacion;
    }

    public String getAdmEstado() {
        return admEstado;
    }

    public void setAdmEstado(String admEstado) {
        this.admEstado = admEstado;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (admId != null ? admId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof RegAdmision)) {
            return false;
        }
        RegAdmision other = (RegAdmision) object;
        if ((this.admId == null && other.admId != null) || (this.admId != null && !this.admId.equals(other.admId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ec.mil.armada.inv.modelo.registro.RegAdmision[ admId=" + admId + " ]";
    }
    
}
