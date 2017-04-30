/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.ifpb.praticas.quickserv.core.services;

import br.edu.ifpb.praticas.quickserv.core.dao.interfaces.LoginDAO;
import br.edu.ifpb.praticas.quickserv.core.dao.interfaces.RegisterRequestDAO;
import br.edu.ifpb.praticas.quickserv.shared.domain.RegisterRequest;
import br.edu.ifpb.praticas.quickserv.shared.domain.UserAccount;
import br.edu.ifpb.praticas.quickserv.shared.enums.RegisterRequestStatus;
import br.edu.ifpb.praticas.quickserv.shared.exceptions.LoginException;
import br.edu.ifpb.praticas.quickserv.shared.services.interfaces.LoginService;
import javax.ejb.EJB;
import javax.ejb.EJBException;
import javax.ejb.Remote;
import javax.ejb.Stateless;

/**
 *
 * @author Pedro Arthur
 */
@Remote(LoginService.class)
@Stateless
public class LoginServiceImpl implements LoginService {

    @EJB
    private LoginDAO loginDAO;
    @EJB
    private RegisterRequestDAO registerRequestDAO;

    public LoginServiceImpl(LoginDAO loginDAO, RegisterRequestDAO registerRequestDAO) {
        this.loginDAO = loginDAO;
        this.registerRequestDAO = registerRequestDAO;
    }

    public LoginServiceImpl() {
    }

    @Override
    public UserAccount signIn(String username, String password) {
        try {
            UserAccount account = getUserAccount(username, password);
            validate(account);
            return account;
        } catch (LoginException ex) {
            throw new EJBException(ex);
        }
    }

    private UserAccount getUserAccount(String username, String password) {
        
        UserAccount user = loginDAO.signIn(username, password);
        if(user == null) throw new LoginException("Wrong credentials!");
        if (!user.isActived()) throw new LoginException("User wasn't activated yet!");
        return user;
    }
    
    private void validate(UserAccount userAccount) {
        
        RegisterRequest userLastRegisterRequest = registerRequestDAO.getUserLastRegisterRequest(userAccount.getUsername());
        if(userLastRegisterRequest != null) { 
            RegisterRequestStatus status = userLastRegisterRequest.getStatus();
            if(!status.equals(RegisterRequestStatus.ACCEPTED)) {
                if(status.equals(RegisterRequestStatus.PENDENT))
                    throw new LoginException("O registro do usuário "+userAccount.getUsername()+" ainda não foi aprovado!");
                else if(status.equals(RegisterRequestStatus.DENIED)) {
                    throw new LoginException("O registro do usuário "+userAccount.getUsername()+" foi negado!");
                }
            }
        }
    }
}
