/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.mil.armada.inv.convertidor;


import ec.mil.armada.inv.enumerador.GeneralEnum;
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
@FacesConverter(value = "generalConvertidor")
public class GeneralConvertidor implements Converter {

    private static final Logger LOG = Logger.getLogger(GeneralConvertidor.class.getName());

    @Override
    public Object getAsObject(final FacesContext context, final UIComponent component, final String value) {
        LOG.log(Level.INFO, "Valor que viene de la vista metodo getasobject{0}", value);

        if (value.isEmpty()) {
            return Boolean.FALSE;
        } else {

            if (GeneralEnum.I.equals(GeneralEnum.valueOf(value))) {
                LOG.log(Level.INFO, "Valor  Administrativo {0}", GeneralEnum.I);
                return GeneralEnum.I;
            }
            if (GeneralEnum.M.equals(GeneralEnum.valueOf(value))) {
                LOG.log(Level.INFO, "Valor  Administrativo {0}", GeneralEnum.M);
                return GeneralEnum.M;
            }
            if (GeneralEnum.S.equals(GeneralEnum.valueOf(value))) {
                LOG.log(Level.INFO, "Valor  Administrativo {0}", GeneralEnum.S);
                return GeneralEnum.S;
            } else {
                return GeneralEnum.A;
            }
        }
    }

    @Override
    public String getAsString(final FacesContext context, final UIComponent component, final Object value) {
        String nombre= "";
        if (value == null) {
            nombre = GeneralEnum.M.getEnumerador();
        } else {
            final String valor = (String) value.toString().trim();
            if (GeneralEnum.I.toString().compareTo(valor) == 0) {
                nombre = GeneralEnum.I.toString();
            }
             if (GeneralEnum.M.toString().compareTo(valor) == 0) {
                nombre = GeneralEnum.M.toString();
            }
              if (GeneralEnum.S.toString().compareTo(valor) == 0) {
                nombre = GeneralEnum.S.toString();
            }
               if (GeneralEnum.A.toString().compareTo(valor) == 0) {
                nombre = GeneralEnum.A.toString();
            }
        }
        return nombre;
    }
}
        

