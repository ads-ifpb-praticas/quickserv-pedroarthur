/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.ifpb.praticas.quickserv.core.services;

import br.edu.ifpb.praticas.quickserv.core.dao.config.DataSourceDefinitionConfig;
import br.edu.ifpb.praticas.quickserv.core.dao.impl.LoginDAOJpaImpl;
import br.edu.ifpb.praticas.quickserv.core.dao.impl.RegisterRequestDAOJpaImpl;
import br.edu.ifpb.praticas.quickserv.core.dao.interfaces.LoginDAO;
import br.edu.ifpb.praticas.quickserv.core.dao.interfaces.RegisterRequestDAO;
import br.edu.ifpb.praticas.quickserv.shared.domain.RegisterRequest;
import br.edu.ifpb.praticas.quickserv.shared.domain.UserAccount;
import br.edu.ifpb.praticas.quickserv.shared.services.interfaces.LoginService;
import javax.ejb.EJB;
import javax.ejb.EJBException;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.arquillian.persistence.Cleanup;
import org.jboss.arquillian.persistence.CleanupStrategy;
import org.jboss.arquillian.persistence.TestExecutionPhase;
import org.jboss.arquillian.persistence.UsingDataSet;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import static org.junit.Assert.assertNotNull;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 *
 * @author Pedro Arthur
 */

@RunWith(Arquillian.class)
@Cleanup(phase = TestExecutionPhase.AFTER, strategy = CleanupStrategy.USED_ROWS_ONLY)
public class LoginServiceIT {
    
    @EJB
    private LoginService loginService;
    
    @Deployment
    public static WebArchive createDeployment() {
        return ShrinkWrap.create(WebArchive.class, "quickserv-test.war")
                .addClass(DataSourceDefinitionConfig.class)
                .addAsResource("META-INF/persistence.xml")
                .addClass(LoginService.class)
                .addClass(LoginServiceImpl.class)
                .addClass(UserAccount.class)
                .addClass(RegisterRequest.class)
                .addClass(RegisterRequestDAO.class)
                .addClass(RegisterRequestDAOJpaImpl.class)
                .addClass(LoginDAO.class)
                .addClass(LoginDAOJpaImpl.class);
    }
    
    @Test
    @UsingDataSet("datasets/LoginCase.yml")
    public void successLogin() {
        UserAccount user = loginService
                .signIn("giovanna-vitoria96@arteche.com.br", "123456");
        assertNotNull(user);
    }
    
    @Test(expected = EJBException.class)
    @UsingDataSet("LoginCase.yml")
    public void failingLogin() {
        UserAccount user = loginService
                .signIn("giovanna-vitoria96@arteche.com.br", "12345678");
    }
    
    
}
