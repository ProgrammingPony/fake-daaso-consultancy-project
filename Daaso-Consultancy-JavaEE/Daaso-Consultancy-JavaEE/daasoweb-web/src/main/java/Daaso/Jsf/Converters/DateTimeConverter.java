/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Daaso.Jsf.Converters;

import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.faces.context.FacesContext;
import javax.faces.component.UIComponent;

import java.time.LocalDateTime;

/**
 *
 * @author Omar
 */
@FacesConverter("DateTimeConverter")
public class DateTimeConverter implements Converter {
    @Override
    public String getAsString(FacesContext context, UIComponent component, Object modelValue) {
        String dateTime =( (LocalDateTime) modelValue ).toString();
        StringBuilder formattedPhoneNumber = new StringBuilder();

        formattedPhoneNumber.append( dateTime.substring(0, 10) ).append( " at " ).append( dateTime.substring(11, 19) );

        return formattedPhoneNumber.toString();
    }

    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String submittedValue) {
        // Conversion is not necessary for now. However, if you ever intend to use 
        // it on input components, you probably want to implement it here.
        throw new UnsupportedOperationException("Not implemented yet");
    }
}
