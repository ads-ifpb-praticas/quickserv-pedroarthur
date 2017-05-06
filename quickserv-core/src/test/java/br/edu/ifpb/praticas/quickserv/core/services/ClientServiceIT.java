/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.ifpb.praticas.quickserv.core.services;

import br.edu.ifpb.praticas.quickserv.core.dao.config.DataSourceDefinitionConfig;
import br.edu.ifpb.praticas.quickserv.core.dao.impl.ClientDAOJpaImpl;
import br.edu.ifpb.praticas.quickserv.core.dao.interfaces.ClientDAO;
import br.edu.ifpb.praticas.quickserv.shared.domain.Address;
import br.edu.ifpb.praticas.quickserv.shared.domain.Client;
import br.edu.ifpb.praticas.quickserv.shared.domain.UserAccount;
import br.edu.ifpb.praticas.quickserv.shared.enums.UserRole;
import br.edu.ifpb.praticas.quickserv.shared.services.interfaces.ClientService;
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
@UsingDataSet("datasets/Client.yml")
public class ClientServiceIT {
    
    @EJB
    private ClientService clientService;
    private UserAccount existingUserAccount;
    private UserAccount newUserAccount;
    private Client newClient;
    private Client existingClient;
    
    @Deployment
    public static WebArchive createDeployment() {
        return ShrinkWrap.create(WebArchive.class, "quickserv-test.war")
                .addClass(DataSourceDefinitionConfig.class)
                .addAsResource("META-INF/persistence.xml")
                .addClass(Client.class)
                .addClass(UserAccount.class)
                .addClass(ClientDAO.class)
                .addClass(ClientDAOJpaImpl.class)
                .addClass(ClientService.class)
                .addClass(ClientServiceImpl.class);
    }
    
    @Before
    public void setUp() {
        this.existingUserAccount = new UserAccount("fmcampos@wredenborg.se", "123456", 
                UserRole.CLIENT, true);
        this.newUserAccount = new UserAccount("teste@email.com", "123456", 
                UserRole.CLIENT, true);
        this.existingClient = new Client("334.199.432-70", "Filipe Marcelo", 
                "Campos", "(62)99545-9282", new Address(), existingUserAccount);
        this.newClient = new Client("121.144.186-50", "Teste teste",
                "Teste", "(99)99999-9999", new Address(), newUserAccount);
    }
    
    @Test
    public void successGetByUserAccount() {
        Client found = clientService.getByUserAccount(existingUserAccount);
        System.out.println("[FOUND] Cliente: "+found);
        assertEquals(found.getUserAccount().getUsername(), existingUserAccount.getUsername());
    }
    
    @Test(expected = EJBException.class)
    public void failingGetByUsername() {
        Client found = clientService.getByUserAccount(newUserAccount);
    }
    
    @Test
    public void successSave() {
        clientService.save(newClient);
        Client client = clientService.getByUserAccount(newUserAccount);
        System.out.println("[SAVED] Cliente: "+client);
        assertEquals(newClient.getCpf(), client.getCpf());
    }
    
    @Test(expected = EJBException.class)
    public void failingSave() {
        clientService.save(existingClient);
    }
    
    @Test
    public void successUpdate() {
        existingClient.setFirstname("New Firstname");
        existingClient.setLastname("New lastname");
        existingClient.setPhone("(88)88888-8888");
        existingClient.getUserAccount().setPassword("mynewpassword");
        clientService.update(existingClient);
        
        
        Client changedClient = clientService
                .getByUserAccount(existingClient.getUserAccount());
        System.out.println("[UPDATED] Client.getFirstname(): "
                +changedClient.getFirstname());
        System.out.println("[UPDATED] Client.getLastname(): "
                +changedClient.getLastname());
        System.out.println("[UPDATED] Client.getPhone(): "
                +changedClient.getPhone());
        System.out.println("[UPDATED] Client.getUserAccount().getPassword(): "
                +changedClient.getUserAccount().getPassword());
        
        assertEquals(existingClient.getFirstname(), 
                changedClient.getFirstname());
        assertEquals(existingClient.getLastname(), 
                changedClient.getLastname());
        assertEquals(existingClient.getPhone(), 
                changedClient.getPhone());
        assertEquals(existingClient.getUserAccount().getPassword(), 
                changedClient.getUserAccount().getPassword());
    }

}
