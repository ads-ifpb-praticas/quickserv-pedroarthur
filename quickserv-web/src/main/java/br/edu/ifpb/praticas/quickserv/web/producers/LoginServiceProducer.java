/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.ifpb.praticas.quickserv.web.producers;

import br.edu.ifpb.praticas.quickserv.shared.services.interfaces.LoginService;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.context.Dependent;
import javax.enterprise.inject.Default;
import javax.enterprise.inject.Produces;

/**
 *
 * @author Pedro Arthur
 */

@ApplicationScoped
public class LoginServiceProducer {
    
    private final String resource = "java:global/quickserv-core/LoginServiceImpl!br.edu.ifpb.praticas.quickserv.shared.services.interfaces.LoginService";
    
    @Default
    @Dependent
    @Produces
    public LoginService getLoginService() {
        System.out.println("HELLO WORLD!");
        return new ServiceLocator().lookup(this.resource, LoginService.class);
    }
}
