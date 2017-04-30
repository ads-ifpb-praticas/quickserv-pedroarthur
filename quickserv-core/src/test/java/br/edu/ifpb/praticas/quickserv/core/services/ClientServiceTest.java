/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.ifpb.praticas.quickserv.core.services;

import br.edu.ifpb.praticas.quickserv.core.dao.interfaces.ClientDAO;
import br.edu.ifpb.praticas.quickserv.shared.domain.Address;
import br.edu.ifpb.praticas.quickserv.shared.domain.Client;
import br.edu.ifpb.praticas.quickserv.shared.domain.UserAccount;
import br.edu.ifpb.praticas.quickserv.shared.enums.UserRole;
import javax.ejb.EJBException;
import static org.junit.Assert.assertEquals;
import org.junit.Before;
import org.junit.Test;
import static org.mockito.Matchers.any;
import org.mockito.Mock;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doReturn;
import org.mockito.MockitoAnnotations;

/**
 *
 * @author Pedro Arthur
 */
public class ClientServiceTest {
    
    private ClientServiceImpl clientService;
    @Mock
    private ClientDAO clientDao;
    private UserAccount userTest;
    private Client clientTest;
    
    @Before
    public void setUp() {
        this.userTest = new UserAccount("teste", "teste", 
                UserRole.CLIENT, true);
        this.clientTest = new Client("111.222.333-44", "Pedro Arthur", 
                "Fernandes de Vasconcelos", "(83)99999-9999", new Address(), userTest);
        
        MockitoAnnotations.initMocks(this);
        
        doNothing().when(clientDao).persist((any(Client.class)));
        doReturn(clientTest).when(clientDao).getByUsername(any(String.class));
        doReturn(false).when(clientDao).isCpfInUse(any(String.class));
        
        this.clientService = new ClientServiceImpl(clientDao);
    }
    
    @Test
    public void successSaving() {
        Client client = new Client("111.222.333-44", "Pedro Arthur", 
                "Fernandes de Vasconcelos", "(83)99999-9999", new Address(), userTest);
        clientService.save(client);
    }
    
    @Test(expected = EJBException.class)
    public void failingSaving() {
        doReturn(true).when(clientDao).isCpfInUse(any(String.class));
        
        Client client = new Client("111.222.333-44", "Pedro Arthur", 
                "Fernandes de Vasconcelos", "(83)99999-9999", new Address(), userTest);
        clientService.save(client);
    }
    
    @Test
    public void successGetByUserAccount() {
        Client client = this.clientService.getByUserAccount(userTest);
        assertEquals(client.getCpf(), clientTest.getCpf());
    }
    
    @Test(expected = EJBException.class)
    public void failingGetByUserAccount() {
        doReturn(null).when(clientDao).getByUsername(any(String.class));
        
        this.clientService.getByUserAccount(userTest);
    }
    
}
