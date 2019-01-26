/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.mil.armada.inv.modelo.registro;

import java.io.Serializable;
import java.math.BigDecimal;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 *
 * @author maria_rodriguez
 */
@Entity
@Table(name = "REG_USUARIO")
@NamedQueries({
    @NamedQuery(name = "RegUsuario.findAll", query = "SELECT r FROM RegUsuario r")})
public class RegUsuario implements Serializable {
    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "USU_ID")
    private BigDecimal usuId;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 10)
    @Column(name = "USU_USUARIO")
    private String usuUsuario;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "USU_CLAVE")
    private String usuClave;
    @Basic(optional = false)
    @NotNull
    @Column(name = "USU_ESTADO")
    private Character usuEstado;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "USU_PERFIL")
    private String usuPerfil;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 4)
    @Column(name = "USU_SIGLASPERFIL")
    private String usuSiglasperfil;

    public RegUsuario() {
    }

    public RegUsuario(BigDecimal usuId) {
        this.usuId = usuId;
    }

    public RegUsuario(BigDecimal usuId, String usuUsuario, String usuClave, Character usuEstado, String usuPerfil, String usuSiglasperfil) {
        this.usuId = usuId;
        this.usuUsuario = usuUsuario;
        this.usuClave = usuClave;
        this.usuEstado = usuEstado;
        this.usuPerfil = usuPerfil;
        this.usuSiglasperfil = usuSiglasperfil;
    }

    public BigDecimal getUsuId() {
        return usuId;
    }

    public void setUsuId(BigDecimal usuId) {
        this.usuId = usuId;
    }

    public String getUsuUsuario() {
        return usuUsuario;
    }

    public void setUsuUsuario(String usuUsuario) {
        this.usuUsuario = usuUsuario;
    }

    public String getUsuClave() {
        return usuClave;
    }

    public void setUsuClave(String usuClave) {
        this.usuClave = usuClave;
    }

    public Character getUsuEstado() {
        return usuEstado;
    }

    public void setUsuEstado(Character usuEstado) {
        this.usuEstado = usuEstado;
    }

    public String getUsuPerfil() {
        return usuPerfil;
    }

    public void setUsuPerfil(String usuPerfil) {
        this.usuPerfil = usuPerfil;
    }

    public String getUsuSiglasperfil() {
        return usuSiglasperfil;
    }

    public void setUsuSiglasperfil(String usuSiglasperfil) {
        this.usuSiglasperfil = usuSiglasperfil;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (usuId != null ? usuId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof RegUsuario)) {
            return false;
        }
        RegUsuario other = (RegUsuario) object;
        if ((this.usuId == null && other.usuId != null) || (this.usuId != null && !this.usuId.equals(other.usuId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ec.mil.armada.inv.modelo.registro.RegUsuario[ usuId=" + usuId + " ]";
    }
    
}
