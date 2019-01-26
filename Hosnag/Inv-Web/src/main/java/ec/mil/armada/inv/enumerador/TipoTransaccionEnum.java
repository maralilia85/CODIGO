/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.mil.armada.inv.enumerador;

/**
 *
 * @author maria_rodriguez
 */
public enum TipoTransaccionEnum {
      /**
     * Ingreso por factura.
     */
    IF("Ing.Factura"),

    /**
     *Ingreso por Transferencia.
     */
  
    IT("Ing.Transferencia"),
     /**
     * Ingreso por Donacion.
     */
    
    ID("Ing.Donacion"),
     /**
     * Ingreso por Referencia.
     */
    
    IR("Ing.Referencia"),
     /**
     * Ingreso por Referencia.
     */
    
    ET("Eg.Transferencia"),
    
     /**
     * Medicamentos.
     */
    EV("Eg.Venta");
    
   
    
    private TipoTransaccionEnum(final String enumerador){
        this.enumerador = enumerador;
    
    }
     private String enumerador;

    public String getEnumerador() {
        return enumerador;
    }

    public void setEnumerador(String enumerador) {
        this.enumerador = enumerador;
    }
    
    
}
