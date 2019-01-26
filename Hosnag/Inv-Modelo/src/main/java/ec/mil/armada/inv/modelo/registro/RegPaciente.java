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
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 *
 * @author DET-PC
 */
@Entity
@Table(name = "REG_PACIENTE")
@NamedQueries({
    @NamedQuery(name = "RegPaciente.findAll", query = "SELECT r FROM RegPaciente r")})
public class RegPaciente implements Serializable {
    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "PAC_ID")
    private BigInteger pacId;
    @Column(name = "PAC_NOMBREAPELLIDOS")
    private String pacNombreApellidos;
    @Size(max = 10)
    @Column(name = "PAC_HC")
    private String pacHc;
    @Size(max = 10)
    @Column(name = "PAC_CEDULA")
    private String pacCedula;
    @Column(name = "PAC_SEXO")
    private String pacSexo;
    @Column(name = "PAC_FECHANACIMIENTO")
    @Temporal(TemporalType.TIMESTAMP)
    private Date pacFechanacimiento;
    @Column(name = "PAC_IDSEGURO")
    private BigInteger pacIdSeguro;
    @Column(name = "PAC_IDSITUACION")
    private BigInteger pacIdSituacion;
    @Column(name = "PAC_ESTADO")
    private String pacEstado;
    @Column(name = "PAC_CODIGOISSFA")
    private String pacCodigoIssfa;
    
   
    @Transient
    private String pacSeguro;
    
    @Transient
    private String pacSituacion;

    public RegPaciente() {
    }

    public RegPaciente(BigInteger pacId) {
        this.pacId = pacId;
    }

    public BigInteger getPacId() {
        return pacId;
    }

    public void setPacId(BigInteger pacId) {
        this.pacId = pacId;
    }

    public String getPacNombreApellidos() {
        return pacNombreApellidos;
    }

    public void setPacNombreApellidos(String pacNombreApellidos) {
        this.pacNombreApellidos = pacNombreApellidos;
    }



    public String getPacCodigoIssfa() {
        return pacCodigoIssfa;
    }

    public void setPacCodigoIssfa(String pacCodigoIssfa) {
        this.pacCodigoIssfa = pacCodigoIssfa;
    }


    public String getPacHc() {
        return pacHc;
    }

    public void setPacHc(String pacHc) {
        this.pacHc = pacHc;
    }

    public String getPacCedula() {
        return pacCedula;
    }

    public void setPacCedula(String pacCedula) {
        this.pacCedula = pacCedula;
    }

    public String getPacSexo() {
        return pacSexo;
    }

    public void setPacSexo(String pacSexo) {
        this.pacSexo = pacSexo;
    }

    public Date getPacFechanacimiento() {
        return pacFechanacimiento;
    }

    public void setPacFechanacimiento(Date pacFechanacimiento) {
        this.pacFechanacimiento = pacFechanacimiento;
    }

    public BigInteger getPacIdSeguro() {
        return pacIdSeguro;
    }

    public void setPacIdSeguro(BigInteger pacIdSeguro) {
        this.pacIdSeguro = pacIdSeguro;
    }

    public BigInteger getPacIdSituacion() {
        return pacIdSituacion;
    }

    public void setPacIdSituacion(BigInteger pacIdSituacion) {
        this.pacIdSituacion = pacIdSituacion;
    }

    public String getPacSeguro() {
        return pacSeguro;
    }

    public void setPacSeguro(String pacSeguro) {
        this.pacSeguro = pacSeguro;
    }

    public String getPacSituacion() {
        return pacSituacion;
    }

    public void setPacSituacion(String pacSituacion) {
        this.pacSituacion = pacSituacion;
    }

    public String getPacEstado() {
        return pacEstado;
    }

    public void setPacEstado(String pacEstado) {
        this.pacEstado = pacEstado;
    }

   

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (pacId != null ? pacId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof RegPaciente)) {
            return false;
        }
        RegPaciente other = (RegPaciente) object;
        if ((this.pacId == null && other.pacId != null) || (this.pacId != null && !this.pacId.equals(other.pacId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ec.mil.armada.inv.modelo.registro.RegPaciente[ pacId=" + pacId + " ]";
    }
    
}
