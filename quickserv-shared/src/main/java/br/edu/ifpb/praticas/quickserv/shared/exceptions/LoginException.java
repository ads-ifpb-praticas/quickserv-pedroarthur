/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.ifpb.praticas.quickserv.shared.exceptions;

/**
 *
 * @author Pedro Arthur
 */
public class LoginException extends RuntimeException {
    
    public LoginException(String message) {
        super(message);
    }

    public LoginException() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
