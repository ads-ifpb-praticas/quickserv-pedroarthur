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
import br.edu.ifpb.praticas.quickserv.shared.enums.UserRole;
import java.time.LocalDateTime;
import javax.ejb.EJBException;
import org.junit.Before;
import org.junit.Test;
import static org.mockito.Matchers.any;
import org.mockito.Mock;
import static org.mockito.Mockito.doReturn;
import org.mockito.MockitoAnnotations;

/**
 *
 * @author Pedro Arthur
 */

public class LoginServiceTest {
    
    private LoginServiceImpl loginService;
    @Mock
    private LoginDAO loginDAO;
    @Mock
    private RegisterRequestDAO registerRequestDAO;
    
    private UserAccount userTest;
    private RegisterRequest registerRequestTest;
    
    @Before
    public void setUp() {
        
        this.userTest = new UserAccount("teste", "teste", 
                UserRole.PROFESSIONAL, true);
        this.registerRequestTest = new RegisterRequest(userTest, 
                new byte[0], RegisterRequestStatus.ACCEPTED, 
                LocalDateTime.now());
        
        MockitoAnnotations.initMocks(this);
        
        doReturn(registerRequestTest).when(registerRequestDAO)
                .getUserLastRegisterRequest("teste");
        doReturn(userTest)
                .when(loginDAO)
                .signIn(any(String.class), any(String.class));
        
        this.loginService = new LoginServiceImpl(loginDAO, registerRequestDAO);
    }
    
    @Test
    public void successLogIn() {
        this.registerRequestTest.setStatus(RegisterRequestStatus.ACCEPTED);
        this.loginService.signIn("teste", "teste");
    }
    
    @Test(expected = EJBException.class)
    public void failingRegisterRequestDeniedLogIn() {
        this.registerRequestTest.setStatus(RegisterRequestStatus.DENIED);
        this.loginService.signIn("teste", "teste");
    }
    
    @Test(expected = EJBException.class)
    public void failingRegisterRequestPendentLogIn() {
        this.registerRequestTest.setStatus(RegisterRequestStatus.PENDENT);
        this.loginService.signIn("teste", "teste");
    }
    
    @Test(expected = EJBException.class)
    public void failingUserNotActivedLogIn() {
        this.userTest.setActived(false);
        this.loginService.signIn("teste", "teste");
    }
    
    @Test(expected = EJBException.class)
    public void failingInvalidCredentialsLogIn() {
        doReturn(null)
                .when(loginDAO)
                .signIn(any(String.class), any(String.class));
        this.loginService.signIn("teste", "teste");
    }

}
