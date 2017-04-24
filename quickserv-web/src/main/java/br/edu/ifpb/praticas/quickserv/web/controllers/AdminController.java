/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.ifpb.praticas.quickserv.web.controllers;

import br.edu.ifpb.praticas.quickserv.shared.domain.UserAccount;
import br.edu.ifpb.praticas.quickserv.web.utils.SessionUtils;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpSession;

/**
 *
 * @author Pedro Arthur
 */

@Named("adminCtrl")
@RequestScoped
public class AdminController {
    
    @Inject
    private AdminController controller;
    
    public UserAccount getLoggedAdmin() {
        
        HttpSession session = SessionUtils.getSession(false);
        return (UserAccount) session.getAttribute("loggedUserAccount");
    }
    
    
}
