/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.ifpb.praticas.quickserv.core.services;

import br.edu.ifpb.praticas.quickserv.core.dao.config.DataSourceDefinitionConfig;
import br.edu.ifpb.praticas.quickserv.core.dao.impl.ProfessionalDAOJpaImpl;
import br.edu.ifpb.praticas.quickserv.core.dao.impl.RegisterRequestDAOJpaImpl;
import br.edu.ifpb.praticas.quickserv.core.dao.interfaces.ProfessionalDAO;
import br.edu.ifpb.praticas.quickserv.core.dao.interfaces.RegisterRequestDAO;
import br.edu.ifpb.praticas.quickserv.shared.domain.Address;
import br.edu.ifpb.praticas.quickserv.shared.domain.Professional;
import br.edu.ifpb.praticas.quickserv.shared.domain.RegisterRequest;
import br.edu.ifpb.praticas.quickserv.shared.domain.UserAccount;
import br.edu.ifpb.praticas.quickserv.shared.enums.RegisterRequestStatus;
import br.edu.ifpb.praticas.quickserv.shared.enums.UserRole;
import br.edu.ifpb.praticas.quickserv.shared.services.interfaces.ProfessionalService;
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
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 *
 * @author Pedro Arthur
 */

@RunWith(Arquillian.class)
@Cleanup(phase = TestExecutionPhase.AFTER, strategy = CleanupStrategy.USED_ROWS_ONLY)
@UsingDataSet("datasets/Professional.yml")
public class ProfessionalServiceIT {
    
    @EJB
    private ProfessionalService professionalService;
    @EJB
    private RegisterRequestDAO registerRequestDAO;
    
    private Professional newProfessional;
    private Professional existingProfessional;
    private UserAccount newUserAccount;
    private UserAccount existingUserAccount;
    
    @Deployment
    public static WebArchive createDeployment() {
        return ShrinkWrap.create(WebArchive.class, "quickserv-test.war")
                .addClass(DataSourceDefinitionConfig.class)
                .addAsResource("META-INF/persistence.xml")
                //Entities
                .addClasses(UserAccount.class, Professional.class,
                        RegisterRequest.class)
                //Services
                .addClasses(ProfessionalService.class, 
                        ProfessionalServiceImpl.class)
                //DAOs
                .addClasses(ProfessionalDAO.class, 
                        ProfessionalDAOJpaImpl.class,
                        RegisterRequestDAO.class,
                        RegisterRequestDAOJpaImpl.class);
    }
    
    @Before
    public void setUp() {
        this.existingUserAccount = new UserAccount("fmcampos@wredenborg.se", "123456", 
                UserRole.PROFESSIONAL, true);
        this.newUserAccount = new UserAccount("teste@email.com", "123456", 
                UserRole.PROFESSIONAL, true);
        
        this.existingProfessional = new Professional("334.199.432-70", "Filipe Marcelo", 
                "Campos", "(62)99545-9282", new Address(), new byte[0], RegisterRequestStatus.ACCEPTED, existingUserAccount);
        this.newProfessional = new Professional("537.599.228-05", "Teste teste",
                "Teste", "(99)99999-9999", new Address(), new byte[0], RegisterRequestStatus.ACCEPTED, newUserAccount);
    }
    
    @Test
    public void successGetByUserAccount() {
        Professional found = professionalService
                .getByUser(existingUserAccount);
        System.out.println("[FOUND] Professional: "+found);
        assertEquals(found.getUserAccount().getUsername(),
                existingUserAccount.getUsername());
    }
    
    @InSequence(1)
    @Test(expected = EJBException.class) 
    public void failingGetByUserAccount() {
        professionalService.getByUser(newUserAccount);
    }
    
    @Test
    @InSequence(2)
    public void successSave() {
        professionalService.save(newProfessional);
        Professional saved = professionalService
                .getByUser(newUserAccount);
        System.out.println("[SAVED] Professional: "+saved);
        assertEquals(saved.getCpf(), newProfessional.getCpf());
        RegisterRequest userLastRegisterRequest = registerRequestDAO
                .getUserLastRegisterRequest(newUserAccount.getUsername());
        assertEquals(saved.getUserAccount().getUsername(),
                userLastRegisterRequest.getAccount().getUsername());
    }
    
    @Test(expected = EJBException.class)
    public void failingSave() {
        professionalService.save(existingProfessional);
    }
    
    @Test
    public void successUpdate() {
        existingProfessional.setFirstname("New Firstname");
        existingProfessional.setLastname("New lastname");
        existingProfessional.setPhone("(88)88888-8888");
        existingProfessional.getUserAccount().setPassword("mynewpassword");
        professionalService.update(existingProfessional);
        
        
        Professional changedProfessional = professionalService
                .getByUser(existingProfessional.getUserAccount());
        System.out.println("[UPDATED] Client.getFirstname(): "
                +changedProfessional.getFirstname());
        System.out.println("[UPDATED] Client.getLastname(): "
                +changedProfessional.getLastname());
        System.out.println("[UPDATED] Client.getPhone(): "
                +changedProfessional.getPhone());
        System.out.println("[UPDATED] Client.getUserAccount().getPassword(): "
                +changedProfessional.getUserAccount().getPassword());
        
        assertEquals(existingProfessional.getFirstname(), 
                changedProfessional.getFirstname());
        assertEquals(existingProfessional.getLastname(), 
                changedProfessional.getLastname());
        assertEquals(existingProfessional.getPhone(), 
                changedProfessional.getPhone());
        assertEquals(existingProfessional.getUserAccount().getPassword(), 
                changedProfessional.getUserAccount().getPassword());
    }
}
