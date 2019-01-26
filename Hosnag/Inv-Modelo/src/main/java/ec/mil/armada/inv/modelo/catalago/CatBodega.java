/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.mil.armada.inv.modelo.catalago;

import java.io.Serializable;
import java.math.BigInteger;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;

/**
 *
 * @author maria_rodriguez
 */
@Entity
@Table(name = "CAT_BODEGA")
 @SequenceGenerator(name = "segCatBodega",sequenceName = "CAT_BODEGA_SEQ",allocationSize = 1)

public class CatBodega implements Serializable {
    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "segCatBodega")
    @Basic(optional = false)
    @NotNull
    @Column(name = "BOD_ID")
    private BigInteger bodId;
    @Column(name = "BOD_DESCRIPCION")
    private String bodDescripcion;
    @Column(name = "BOD_TIPO")
    private String bodTipo;
    @Column(name = "BOD_ESTADO")
    private String bodEstado;
    @Column(name = "BOD_NIVEL")
    private BigInteger bodNivel;
    @JoinColumn(name = "BOD_ID_FK", referencedColumnName = "BOD_ID")
    @ManyToOne
    private CatBodega bodPapa;
    @JoinColumn(name = "EMP_ID", referencedColumnName = "EMP_ID")
    @ManyToOne
    private CatEmpresa catEmp;
    @Column(name = "EML_ID")
    private BigInteger emlId;
    
    @Transient
    private String nombreEmpleado;
    
    
    public CatBodega() {
    }


    public String getBodDescripcion() {
        return bodDescripcion;
    }

    public void setBodDescripcion(String bodDescripcion) {
        this.bodDescripcion = bodDescripcion;
    }

    public String getBodTipo() {
        return bodTipo;
    }

    public void setBodTipo(String bodTipo) {
        this.bodTipo = bodTipo;
    }

    public String getBodEstado() {
        return bodEstado;
    }

    public void setBodEstado(String bodEstado) {
        this.bodEstado = bodEstado;
    }

   
    public BigInteger getBodNivel() {
        return bodNivel;
    }

    public void setBodNivel(BigInteger bodNivel) {
        this.bodNivel = bodNivel;
    }


    public BigInteger getBodId() {
        return bodId;
    }

    public void setBodId(BigInteger bodId) {
        this.bodId = bodId;
    }

    public CatBodega getBodPapa() {
        return bodPapa;
    }

    public void setBodPapa(CatBodega bodPapa) {
        this.bodPapa = bodPapa;
    }

    public CatEmpresa getCatEmp() {
        return catEmp;
    }

    public void setCatEmp(CatEmpresa catEmp) {
        this.catEmp = catEmp;
    }

    public BigInteger getEmlId() {
        return emlId;
    }

    public void setEmlId(BigInteger emlId) {
        this.emlId = emlId;
    }

    public String getNombreEmpleado() {
        return nombreEmpleado;
    }

    public void setNombreEmpleado(String nombreEmpleado) {
        this.nombreEmpleado = nombreEmpleado;
    }

  

   
    
}
