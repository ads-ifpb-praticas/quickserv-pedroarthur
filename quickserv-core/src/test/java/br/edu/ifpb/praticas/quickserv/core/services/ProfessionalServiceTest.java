/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.ifpb.praticas.quickserv.core.services;

import br.edu.ifpb.praticas.quickserv.core.dao.interfaces.ProfessionalDAO;
import br.edu.ifpb.praticas.quickserv.core.dao.interfaces.RegisterRequestDAO;
import br.edu.ifpb.praticas.quickserv.shared.domain.Address;
import br.edu.ifpb.praticas.quickserv.shared.domain.Professional;
import br.edu.ifpb.praticas.quickserv.shared.domain.RegisterRequest;
import br.edu.ifpb.praticas.quickserv.shared.domain.UserAccount;
import br.edu.ifpb.praticas.quickserv.shared.enums.RegisterRequestStatus;
import br.edu.ifpb.praticas.quickserv.shared.enums.UserRole;
import br.edu.ifpb.praticas.quickserv.shared.services.interfaces.ProfessionalService;
import javax.ejb.EJBException;
import org.junit.Before;
import org.junit.Test;
import static org.mockito.Matchers.any;
import org.mockito.Mock;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doReturn;
import static org.junit.Assert.assertEquals;
import org.mockito.MockitoAnnotations;

/**
 *
 * @author Pedro Arthur
 */

public class ProfessionalServiceTest {
    
    private ProfessionalService professionalService;
    @Mock
    private ProfessionalDAO professionalDAO;
    @Mock
    private RegisterRequestDAO registerRequestDAO;
    
    private Professional professionalTest;
    private UserAccount userTest;
    
    @Before
    public void setUp() {
        
        this.userTest = new UserAccount(
                "teste", 
                "teste", 
                UserRole.PROFESSIONAL, 
                true
        );
        
        this.professionalTest = new Professional(
                "111.222.333-44", 
                "Pedro Arthur", 
                "Fernandes de Vasconcelos", 
                "(83)99999-9999", 
                new Address(), 
                new byte[0], 
                RegisterRequestStatus.PENDENT, 
                userTest
        );
        
        MockitoAnnotations.initMocks(this);
        
//      ProfessionalDAO stub
        doNothing().when(professionalDAO)
                .persist((any(Professional.class)));
        doReturn(professionalTest).when(professionalDAO)
                .getByUsername(any(String.class));
        doReturn(false).when(professionalDAO)
                .isCpfInUse(any(String.class));
        
//      RegisterRequestDAO stub
        doNothing().when(registerRequestDAO)
                .persist(any(RegisterRequest.class));
        
        
        this.professionalService = new ProfessionalServiceImpl(professionalDAO, registerRequestDAO);
    }
    
    @Test
    public void successSave() {
        this.professionalService.save(professionalTest);
    }
    
    @Test(expected = EJBException.class)
    public void failingSaveNull() {
        this.professionalService.save(null);
    }
    
    @Test(expected = EJBException.class)
    public void failingSaveCpfInUse() {
        doReturn(true).when(professionalDAO)
                .isCpfInUse(any(String.class));
        this.professionalService.save(professionalTest);
    }
    
    @Test
    public void successGetByUserAccount() {
        Professional found = this.professionalService.getByUser(userTest);
        assertEquals(found.getUserAccount().getUsername(), userTest.getUsername());
    }
    
    @Test(expected = EJBException.class)
    public void failingGetByUserAccount() {
        doReturn(null).when(professionalDAO)
                .getByUsername(any(String.class));
        this.professionalService.getByUser(userTest);
    }
    
    
}
