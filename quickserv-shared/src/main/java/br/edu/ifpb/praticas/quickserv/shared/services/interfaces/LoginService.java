/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.ifpb.praticas.quickserv.shared.services.interfaces;

import br.edu.ifpb.praticas.quickserv.shared.domain.UserAccount;

/**
 *
 * @author Pedro Arthur
 */
public interface LoginService {
    
    UserAccount signIn(String username, String password);
}
