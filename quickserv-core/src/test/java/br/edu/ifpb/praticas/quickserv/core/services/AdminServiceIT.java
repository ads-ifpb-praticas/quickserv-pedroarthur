/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.ifpb.praticas.quickserv.core.services;

import br.edu.ifpb.praticas.quickserv.core.dao.config.DataSourceDefinitionConfig;
import br.edu.ifpb.praticas.quickserv.core.dao.impl.AdminDAOJpaImpl;
import br.edu.ifpb.praticas.quickserv.core.dao.impl.RegisterRequestDAOJpaImpl;
import br.edu.ifpb.praticas.quickserv.core.dao.interfaces.AdminDAO;
import br.edu.ifpb.praticas.quickserv.core.dao.interfaces.RegisterRequestDAO;
import br.edu.ifpb.praticas.quickserv.shared.domain.RegisterRequest;
import br.edu.ifpb.praticas.quickserv.shared.domain.UserAccount;
import br.edu.ifpb.praticas.quickserv.shared.enums.RegisterRequestStatus;
import br.edu.ifpb.praticas.quickserv.shared.enums.UserRole;
import br.edu.ifpb.praticas.quickserv.shared.services.interfaces.AdminService;
import java.time.LocalDateTime;
import javax.ejb.EJB;
import javax.ejb.EJBException;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.arquillian.junit.InSequence;
import org.jboss.arquillian.persistence.Cleanup;
import org.jboss.arquillian.persistence.CleanupStrategy;
import org.jboss.arquillian.persistence.TestExecutionPhase;
import org.jboss.arquillian.persistence.UsingDataSet;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import static org.junit.Assert.assertEquals;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 *
 * @author Pedro Arthur
 */

@RunWith(Arquillian.class)
@Cleanup(phase = TestExecutionPhase.AFTER, strategy = CleanupStrategy.USED_ROWS_ONLY)
@UsingDataSet("datasets/Admin.yml")
public class AdminServiceIT {
    
    @EJB
    private AdminService adminService;
    @EJB
    private AdminDAO adminDAO;
    @EJB
    private RegisterRequestDAO registerRequestDAO;
    
    @Deployment
    public static WebArchive createDeployment() {
        return ShrinkWrap.create(WebArchive.class, "quickserv-test.war")
                .addAsResource("META-INF/persistence.xml")
                .addClass(DataSourceDefinitionConfig.class)
                .addClass(UserAccount.class)
                .addClass(RegisterRequest.class)
                .addClass(AdminService.class)
                .addClass(AdminServiceImpl.class)
                .addClass(AdminDAO.class)
                .addClass(AdminDAOJpaImpl.class)
                .addClass(RegisterRequestDAO.class)
                .addClass(RegisterRequestDAOJpaImpl.class);
    }
    
    @Test
    @InSequence(1)
    public void successSave() {
        UserAccount newAdminAccount = new UserAccount(
                "nfbarros@iname.com", 
                "123456",
                UserRole.ADMIN,
                true
        );
        adminService.save(newAdminAccount);
        assertEquals(true, adminDAO.hasAnAdminRegistered());
    }
    
    @Test(expected = EJBException.class)
    @InSequence(2)
    public void failingSave() {
        adminService.save(null);
    }
    
    @Test(expected = EJBException.class)
    @InSequence(3)
    public void failingApproveSolicitation() {
        UserAccount acceptedUserAccount = new UserAccount(
                "flavia_e_cardoso@lta-am.com.br",
                "123456",
                UserRole.PROFESSIONAL,
                true
        );
        
        RegisterRequest acceptedRegisterRequest = new RegisterRequest(
                acceptedUserAccount,
                new byte[0],
                RegisterRequestStatus.ACCEPTED,
                LocalDateTime.of(2017, 4, 21, 15, 0, 55, 94)
        );
        acceptedRegisterRequest.setId(5L);
        adminService.approveSolicitation(acceptedRegisterRequest, false);
    }
    
    @Test
    @InSequence(4)
    public void successApproveSolicitation() {
        UserAccount pendentUserAccount = new UserAccount(
                "giovanna-vitoria96@arteche.com.br",
                "123456",
                UserRole.PROFESSIONAL,
                true
        );
        
        RegisterRequest pendentRegisterRequest = new RegisterRequest(
                pendentUserAccount,
                new byte[0],
                RegisterRequestStatus.PENDENT,
                LocalDateTime.of(2017, 4, 21, 15, 0, 55, 94)
        );
        pendentRegisterRequest.setId(4L);
        
        adminService.approveSolicitation(pendentRegisterRequest, true);
        assertEquals(false, registerRequestDAO.isPendent(pendentRegisterRequest.getId()));
    }
    
}
