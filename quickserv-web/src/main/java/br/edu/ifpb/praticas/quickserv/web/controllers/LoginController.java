/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.ifpb.praticas.quickserv.web.controllers;

import br.edu.ifpb.praticas.quickserv.shared.domain.Client;
import br.edu.ifpb.praticas.quickserv.shared.domain.UserAccount;
import br.edu.ifpb.praticas.quickserv.shared.enums.UserRole;
import br.edu.ifpb.praticas.quickserv.shared.services.interfaces.LoginService;
import br.edu.ifpb.praticas.quickserv.web.utils.SessionUtils;
import javax.ejb.EJBException;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.application.FacesMessage.Severity;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpSession;

/**
 *
 * @author Pedro Arthur
 */

@Named
@RequestScoped
public class LoginController {
    
    private String username;
    private String password;
    
    @Inject
    private LoginService loginService;
    
    public String signIn() {
        try {
            UserAccount user = loginService.signIn(username, password);
            addUserOnSession(user);
            return redirect(user.getRole());
        } catch (EJBException ex) {
            FacesMessage message = 
                    createMessage(ex.getCausedByException().getMessage(),
                            FacesMessage.SEVERITY_ERROR);
            addMessage("loginMessage", message);
            return null;
        }
    }
    
    public String signOut() {
        removeUserSession();
        return "/index?faces-redirect=true";
    }
    
    private String redirect(UserRole role) {
        switch(role) {
            case CLIENT:
                return "/client/home.xhtml?faces-redirect=true";
            case PROFESSIONAL:
                return "/professional/home.xhtml?faces-redirect=true";
            case ADMIN:
                return "/admin/home.xhtml?faces-redirect=true";
            default:
                return null;
        }
    }
    
    private void addMessage(String clientId, FacesMessage message) {
        FacesContext context = FacesContext.getCurrentInstance();
        context.addMessage(clientId, message);
    }
    
    private FacesMessage createMessage(String text, Severity severity) {
        FacesMessage message = new FacesMessage(text);
        message.setSeverity(severity);
        return message;
    }
    
    private void addUserOnSession(UserAccount user) {
        HttpSession session = SessionUtils.getSession(true);
        session.setAttribute("loggedUserAccount", user);
    }
    
    private void removeUserSession() {
        HttpSession session = SessionUtils.getSession(false);
        session.setAttribute("loggedUserAccount", null);
        session.invalidate();
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
