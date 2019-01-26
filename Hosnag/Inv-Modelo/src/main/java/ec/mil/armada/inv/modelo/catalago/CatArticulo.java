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
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;

/**
 *
 * @author maria_rodriguez
 */
@Entity
@Table(name = "CAT_ARTICULO")
public class CatArticulo implements Serializable {
    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "ART_ID")
    private BigInteger artId;
    @Column(name = "ART_NOMBCOMERCIAL")
    private String artNombcomercial;
    @Column(name = "ART_NOMBGENERICO")
    private String artNombgenerico;
    @Column(name = "ART_ESTADO")
    private String artEstado;
    @Column(name = "ART_MSP")
    private String artMsp;
    @Column(name = "ART_FACTURABLE")
    private String artFacturable;
    @Column(name = "ART_CODIGO")
    private String artCodigo;
    @Column(name = "ART_CODBARRA")
    private String artCodbarra;
    @Column(name = "ART_NIVEL")
    private BigInteger artNivel;
    @Column(name = "ART_PARTIDA")
    private String artPartida;
    @JoinColumn(name = "ART_ID_FK", referencedColumnName = "ART_ID")
    @ManyToOne
    private CatArticulo catArtPapa;
    @JoinColumn(name = "GENID_CONCEN", referencedColumnName = "GEN_ID")
    @ManyToOne
    private CatGeneral catGenConcen;
    @JoinColumn(name = "GENID_FORMA", referencedColumnName = "GEN_ID")
    @ManyToOne
    private CatGeneral catGenForma;
    @JoinColumn(name = "GENID_MODELO", referencedColumnName = "GEN_ID")
    @ManyToOne
    private CatGeneral catGenModelo;
    @JoinColumn(name = "GENID_MARCA", referencedColumnName = "GEN_ID")
    @ManyToOne
    private CatGeneral catGenMarca;
    @Column(name = "EML_ID")
    private BigInteger emlId;
     @Transient
    private String nombreEmpleado;

    public BigInteger getArtId() {
        return artId;
    }

    public void setArtId(BigInteger artId) {
        this.artId = artId;
    }

    public String getArtNombcomercial() {
        return artNombcomercial;
    }

    public void setArtNombcomercial(String artNombcomercial) {
        this.artNombcomercial = artNombcomercial;
    }

    public String getArtNombgenerico() {
        return artNombgenerico;
    }

    public void setArtNombgenerico(String artNombgenerico) {
        this.artNombgenerico = artNombgenerico;
    }

  
    public String getArtEstado() {
        return artEstado;
    }

    public void setArtEstado(String artEstado) {
        this.artEstado = artEstado;
    }

    public String getArtMsp() {
        return artMsp;
    }

    public void setArtMsp(String artMsp) {
        this.artMsp = artMsp;
    }

    public String getArtFacturable() {
        return artFacturable;
    }

    public void setArtFacturable(String artFacturable) {
        this.artFacturable = artFacturable;
    }

    public String getArtCodigo() {
        return artCodigo;
    }

    public void setArtCodigo(String artCodigo) {
        this.artCodigo = artCodigo;
    }

    public String getArtCodbarra() {
        return artCodbarra;
    }

    public void setArtCodbarra(String artCodbarra) {
        this.artCodbarra = artCodbarra;
    }

    public BigInteger getArtNivel() {
        return artNivel;
    }

    public void setArtNivel(BigInteger artNivel) {
        this.artNivel = artNivel;
    }

    public String getArtPartida() {
        return artPartida;
    }

    public void setArtPartida(String artPartida) {
        this.artPartida = artPartida;
    }

    public CatArticulo getCatArtPapa() {
        return catArtPapa;
    }

    public void setCatArtPapa(CatArticulo catArtPapa) {
        this.catArtPapa = catArtPapa;
    }

    public CatGeneral getCatGenConcen() {
        return catGenConcen;
    }

    public void setCatGenConcen(CatGeneral catGenConcen) {
        this.catGenConcen = catGenConcen;
    }

    public CatGeneral getCatGenForma() {
        return catGenForma;
    }

    public void setCatGenForma(CatGeneral catGenForma) {
        this.catGenForma = catGenForma;
    }

    public CatGeneral getCatGenModelo() {
        return catGenModelo;
    }

    public void setCatGenModelo(CatGeneral catGenModelo) {
        this.catGenModelo = catGenModelo;
    }

    public CatGeneral getCatGenMarca() {
        return catGenMarca;
    }

    public void setCatGenMarca(CatGeneral catGenMarca) {
        this.catGenMarca = catGenMarca;
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
