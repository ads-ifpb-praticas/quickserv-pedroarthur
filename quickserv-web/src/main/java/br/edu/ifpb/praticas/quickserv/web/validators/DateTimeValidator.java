/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.ifpb.praticas.quickserv.web.validators;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

/**
 *
 * @author Pedro Arthur
 */

@FacesValidator("dateTimeValidator")
public class DateTimeValidator implements Validator {
    
    private DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

    @Override
    public void validate(FacesContext context, UIComponent component, Object value) throws ValidatorException {
        String parameter = value.toString();
        System.out.println("Validating... "+parameter);
        try {
            LocalDateTime dateTime = LocalDateTime.parse(parameter);
            
        } catch (DateTimeParseException ex) {
            throw new ValidatorException(createMessage("Insira uma Data válida seguindo o padrão"
                    + " \"dd/MM/yyyy HH:mm\"", context));
        }
    }
    
    private FacesMessage createMessage(String msg, FacesContext context) {
        FacesMessage message = new FacesMessage(msg);
        message.setSeverity(FacesMessage.SEVERITY_ERROR);
        return message;
    }
    
}
