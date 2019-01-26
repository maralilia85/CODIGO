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
 * @author maria_rodriguez
 */
@Entity
@Table(name = "REG_AUDITORIA")
@NamedQueries({
    @NamedQuery(name = "RegAuditoria.findAll", query = "SELECT r FROM RegAuditoria r")})
public class RegAuditoria implements Serializable {
    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "AUD_ID")
    private BigDecimal audId;
    @Basic(optional = false)
    @NotNull
    @Column(name = "AUD_FECHA")
    @Temporal(TemporalType.TIMESTAMP)
    private Date audFecha;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 256)
    @Column(name = "AUD_NOMBRETABLA")
    private String audNombretabla;
    @Basic(optional = false)
    @NotNull
    @Column(name = "AUD_CLAVETABLA")
    private BigInteger audClavetabla;
    @Basic(optional = false)
    @NotNull
    @Column(name = "AUD_TIPOACCION")
    private Character audTipoaccion;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 256)
    @Column(name = "AUD_OBSERVACION")
    private String audObservacion;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 4)
    @Column(name = "AUD_TABLASIGLAS")
    private String audTablasiglas;

    public RegAuditoria() {
    }

    public RegAuditoria(BigDecimal audId) {
        this.audId = audId;
    }

    public RegAuditoria(BigDecimal audId, Date audFecha, String audNombretabla, BigInteger audClavetabla, Character audTipoaccion, String audObservacion, String audTablasiglas) {
        this.audId = audId;
        this.audFecha = audFecha;
        this.audNombretabla = audNombretabla;
        this.audClavetabla = audClavetabla;
        this.audTipoaccion = audTipoaccion;
        this.audObservacion = audObservacion;
        this.audTablasiglas = audTablasiglas;
    }

    public BigDecimal getAudId() {
        return audId;
    }

    public void setAudId(BigDecimal audId) {
        this.audId = audId;
    }

    public Date getAudFecha() {
        return audFecha;
    }

    public void setAudFecha(Date audFecha) {
        this.audFecha = audFecha;
    }

    public String getAudNombretabla() {
        return audNombretabla;
    }

    public void setAudNombretabla(String audNombretabla) {
        this.audNombretabla = audNombretabla;
    }

    public BigInteger getAudClavetabla() {
        return audClavetabla;
    }

    public void setAudClavetabla(BigInteger audClavetabla) {
        this.audClavetabla = audClavetabla;
    }

    public Character getAudTipoaccion() {
        return audTipoaccion;
    }

    public void setAudTipoaccion(Character audTipoaccion) {
        this.audTipoaccion = audTipoaccion;
    }

    public String getAudObservacion() {
        return audObservacion;
    }

    public void setAudObservacion(String audObservacion) {
        this.audObservacion = audObservacion;
    }

    public String getAudTablasiglas() {
        return audTablasiglas;
    }

    public void setAudTablasiglas(String audTablasiglas) {
        this.audTablasiglas = audTablasiglas;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (audId != null ? audId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof RegAuditoria)) {
            return false;
        }
        RegAuditoria other = (RegAuditoria) object;
        if ((this.audId == null && other.audId != null) || (this.audId != null && !this.audId.equals(other.audId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ec.mil.armada.inv.modelo.registro.RegAuditoria[ audId=" + audId + " ]";
    }
    
}
