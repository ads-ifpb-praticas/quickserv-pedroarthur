/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.ifpb.praticas.quickserv.web.converters;

import java.time.LocalDateTime;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

/**
 *
 * @author Pedro Arthur
 */

@FacesConverter("defaultLocalDateTimeConverter")
public class DefaultLocalDateTimeConverter implements Converter {

    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String value) {
        if(value == null || value.isEmpty()) return null;
        return LocalDateTime.parse(value);
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, Object value) {
        if(value == null) return null;
        return value.toString();
    }
    
}
