/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.ifpb.praticas.quickserv.core.dao.impl;

import br.edu.ifpb.praticas.quickserv.core.dao.interfaces.LoginDAO;
import br.edu.ifpb.praticas.quickserv.shared.domain.RegisterRequest;
import br.edu.ifpb.praticas.quickserv.shared.domain.UserAccount;
import br.edu.ifpb.praticas.quickserv.shared.enums.RegisterRequestStatus;
import java.util.List;
import javafx.animation.Animation.Status;
import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

/**
 *
 * @author Pedro Arthur
 */

@Local(LoginDAO.class)
@Stateless
public class LoginDAOJpaImpl implements LoginDAO {
    
    @PersistenceContext
    private EntityManager em;

    @Override
    public UserAccount signIn(String username, String password) {
        TypedQuery<UserAccount> query = em
                .createQuery("SELECT u FROM UserAccount u"
                + " WHERE u.username = :username AND u.password = :password",
                UserAccount.class)
                .setParameter("username", username)
                .setParameter("password", password);
        
        List<UserAccount> results = query.getResultList();
        if(results.isEmpty())
            return null;
        return results.get(0);
    }
    
}
