/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.mil.armada.inv.modelo.catalago;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Collection;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 *
 * @author maria_rodriguez
 */
@Entity
@Table(name = "CAT_DIRECCION")
@NamedQueries({
    @NamedQuery(name = "CatDireccion.findAll", query = "SELECT c FROM CatDireccion c")})
public class CatDireccion implements Serializable {
    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "DIR_ID")
    private BigInteger dirId;
    @Size(max = 50)
    @Column(name = "DIR_DESCRIPCION")
    private String dirDescripcion;
    @Size(max = 4)
    @Column(name = "DIR_SIGLAS")
    private String dirSiglas;
    @Column(name = "DIR_ESTADO")
    private String dirEstado;
    @Column(name = "DIR_ID_FK")
    private BigInteger dirIdFk;
    @Column(name = "DIR_NIVEL")
    private BigInteger dirNivel;
    
    public CatDireccion() {
    }

   

    public String getDirDescripcion() {
        return dirDescripcion;
    }

    public void setDirDescripcion(String dirDescripcion) {
        this.dirDescripcion = dirDescripcion;
    }

    public String getDirSiglas() {
        return dirSiglas;
    }

    public void setDirSiglas(String dirSiglas) {
        this.dirSiglas = dirSiglas;
    }

    public BigInteger getDirId() {
        return dirId;
    }

    public void setDirId(BigInteger dirId) {
        this.dirId = dirId;
    }

    public String getDirEstado() {
        return dirEstado;
    }

    public void setDirEstado(String dirEstado) {
        this.dirEstado = dirEstado;
    }

    

    public BigInteger getDirIdFk() {
        return dirIdFk;
    }

    public void setDirIdFk(BigInteger dirIdFk) {
        this.dirIdFk = dirIdFk;
    }

    public BigInteger getDirNivel() {
        return dirNivel;
    }

    public void setDirNivel(BigInteger dirNivel) {
        this.dirNivel = dirNivel;
    }

   
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (dirId != null ? dirId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof CatDireccion)) {
            return false;
        }
        CatDireccion other = (CatDireccion) object;
        if ((this.dirId == null && other.dirId != null) || (this.dirId != null && !this.dirId.equals(other.dirId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ec.mil.armada.inv.modelo.CatDireccion[ dirId=" + dirId + " ]";
    }
    
}
