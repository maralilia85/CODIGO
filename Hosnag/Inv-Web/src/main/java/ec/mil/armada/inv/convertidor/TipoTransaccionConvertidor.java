/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.mil.armada.inv.convertidor;


import ec.mil.armada.inv.enumerador.TipoTransaccionEnum;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

/**
 *
 * @author maria_rodriguez
 */
@FacesConverter(value = "tipoTransaccionConvertidor")
public class TipoTransaccionConvertidor implements Converter {

    private static final Logger LOG = Logger.getLogger(TipoTransaccionConvertidor.class.getName());

    @Override
    public Object getAsObject(final FacesContext context, final UIComponent component, final String value) {
        LOG.log(Level.INFO, "Valor que viene de la vista metodo getasobject{0}", value);

        if (value.isEmpty()) {
            return Boolean.FALSE;
        } else {

            if (TipoTransaccionEnum.ET.equals(TipoTransaccionEnum.valueOf(value))) {
                LOG.log(Level.INFO, "Valor  Administrativo {0}", TipoTransaccionEnum.ET);
                return TipoTransaccionEnum.ET;
            }
            if (TipoTransaccionEnum.EV.equals(TipoTransaccionEnum.valueOf(value))) {
                LOG.log(Level.INFO, "Valor  Administrativo {0}", TipoTransaccionEnum.EV);
                return TipoTransaccionEnum.EV;
            }
            if (TipoTransaccionEnum.ID.equals(TipoTransaccionEnum.valueOf(value))) {
                LOG.log(Level.INFO, "Valor  Administrativo {0}", TipoTransaccionEnum.ID);
                return TipoTransaccionEnum.ID;
            } 
            if (TipoTransaccionEnum.IF.equals(TipoTransaccionEnum.valueOf(value))) {
                LOG.log(Level.INFO, "Valor  Administrativo {0}", TipoTransaccionEnum.IF);
                return TipoTransaccionEnum.IF;
            } 
            if (TipoTransaccionEnum.IR.equals(TipoTransaccionEnum.valueOf(value))) {
                LOG.log(Level.INFO, "Valor  Administrativo {0}", TipoTransaccionEnum.IR);
                return TipoTransaccionEnum.IR;
            } 
            else {
                return TipoTransaccionEnum.IT;
            }
        }
    }

    @Override
    public String getAsString(final FacesContext context, final UIComponent component, final Object value) {
        String nombre= "";
        if (value == null) {
            nombre = TipoTransaccionEnum.IF.getEnumerador();
        } else {
            final String valor = (String) value.toString().trim();
            if (TipoTransaccionEnum.ET.toString().compareTo(valor) == 0) {
                nombre = TipoTransaccionEnum.ET.toString();
            }
             if (TipoTransaccionEnum.EV.toString().compareTo(valor) == 0) {
                nombre = TipoTransaccionEnum.EV.toString();
            }
              if (TipoTransaccionEnum.ID.toString().compareTo(valor) == 0) {
                nombre = TipoTransaccionEnum.ID.toString();
            }
               if (TipoTransaccionEnum.IF.toString().compareTo(valor) == 0) {
                nombre = TipoTransaccionEnum.IF.toString();
            }
               if (TipoTransaccionEnum.IR.toString().compareTo(valor) == 0) {
                nombre = TipoTransaccionEnum.IR.toString();
            }
               if (TipoTransaccionEnum.IT.toString().compareTo(valor) == 0) {
                nombre = TipoTransaccionEnum.IT.toString();
            }
        }
        return nombre;
    }
}
        

