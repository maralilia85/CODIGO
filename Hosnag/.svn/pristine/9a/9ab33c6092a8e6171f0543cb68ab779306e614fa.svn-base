/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.mil.armada.inv.modelo.registro;

import ec.mil.armada.inv.modelo.catalago.CatDireccion;
import ec.mil.armada.inv.modelo.catalago.CatEmpresa;
import java.io.Serializable;
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
import javax.validation.constraints.Size;

/**
 *
 * @author maria_rodriguez
 */
@Entity
@Table(name = "REG_EMPLEADO")
@NamedQueries({
    @NamedQuery(name = "RegEmpleado.findAll", query = "SELECT r FROM RegEmpleado r")})
public class RegEmpleado implements Serializable {
    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "EML_ID")
    private BigInteger emlId;
    @Size(max = 50)
    @Column(name = "EML_NOMBRE")
    private String emlNombre;
    @Size(max = 50)
    @Column(name = "EML_CARGO")
    private String emlCargo;
    @Column(name = "EML_TIPO")
    private String emlTipo;
    @Size(max = 10)
    @Column(name = "EML_CEDULA")
    private String emlCedula;
    @Column(name = "EML_SEXO")
    private String emlSexo;
    @Column(name = "EML_ESTADO")
    private String emlEstado;
    @JoinColumn(name = "DIR_ID", referencedColumnName = "DIR_ID")
    @ManyToOne
    private CatDireccion catDireccion;
    @JoinColumn(name = "EMP_ID", referencedColumnName = "EMP_ID")
    @ManyToOne
    private CatEmpresa catEmpresa;

    public RegEmpleado() {
    }

    

    public String getEmlNombre() {
        return emlNombre;
    }

    public void setEmlNombre(String emlNombre) {
        this.emlNombre = emlNombre;
    }

    public String getEmlCargo() {
        return emlCargo;
    }

    public void setEmlCargo(String emlCargo) {
        this.emlCargo = emlCargo;
    }

   
    public String getEmlCedula() {
        return emlCedula;
    }

    public void setEmlCedula(String emlCedula) {
        this.emlCedula = emlCedula;
    }

    public CatDireccion getCatDireccion() {
        return catDireccion;
    }

    public void setCatDireccion(CatDireccion catDireccion) {
        this.catDireccion = catDireccion;
    }

    public BigInteger getEmlId() {
        return emlId;
    }

    public void setEmlId(BigInteger emlId) {
        this.emlId = emlId;
    }

    public String getEmlTipo() {
        return emlTipo;
    }

    public void setEmlTipo(String emlTipo) {
        this.emlTipo = emlTipo;
    }

    public String getEmlSexo() {
        return emlSexo;
    }

    public void setEmlSexo(String emlSexo) {
        this.emlSexo = emlSexo;
    }

    public String getEmlEstado() {
        return emlEstado;
    }

    public void setEmlEstado(String emlEstado) {
        this.emlEstado = emlEstado;
    }

    public CatEmpresa getCatEmpresa() {
        return catEmpresa;
    }

    public void setCatEmpresa(CatEmpresa catEmpresa) {
        this.catEmpresa = catEmpresa;
    }
    
    

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (emlId != null ? emlId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof RegEmpleado)) {
            return false;
        }
        RegEmpleado other = (RegEmpleado) object;
        if ((this.emlId == null && other.emlId != null) || (this.emlId != null && !this.emlId.equals(other.emlId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ec.mil.armada.inv.modelo.RegEmpleado[ emlId=" + emlId + " ]";
    }
    
}
