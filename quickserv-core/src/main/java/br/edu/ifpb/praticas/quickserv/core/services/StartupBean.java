/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.ifpb.praticas.quickserv.core.services;

import br.edu.ifpb.praticas.quickserv.core.dao.interfaces.AdminDAO;
import br.edu.ifpb.praticas.quickserv.shared.domain.UserAccount;
import br.edu.ifpb.praticas.quickserv.shared.enums.UserRole;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.Singleton;
import javax.ejb.Startup;

/**
 *
 * @author Pedro Arthur
 */

@Singleton
@Startup
public class StartupBean {
    
    @EJB
    private AdminDAO adminDAO;
    
    @PostConstruct
    private void init() {
        if(!adminDAO.hasAnAdminRegistered()) {
            UserAccount userAccount = new UserAccount();
            userAccount.setActived(true);
            userAccount.setRole(UserRole.ADMIN);
            userAccount.setPhoto(new byte[0]);
            userAccount.setPassword("admin");
            userAccount.setUsername("admin@admin.com");
            adminDAO.persist(userAccount);
        }
    }
}
