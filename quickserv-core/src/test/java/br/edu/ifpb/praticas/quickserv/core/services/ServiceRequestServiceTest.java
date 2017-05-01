/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.ifpb.praticas.quickserv.core.services;

import br.edu.ifpb.praticas.quickserv.core.dao.interfaces.ServiceDAO;
import br.edu.ifpb.praticas.quickserv.core.dao.interfaces.ServiceProposalDAO;
import br.edu.ifpb.praticas.quickserv.shared.domain.ServiceProposal;
import br.edu.ifpb.praticas.quickserv.shared.services.interfaces.ServiceProposalService;
import java.security.Provider.Service;
import org.junit.Test;
import org.mockito.Mock;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.any;
import org.mockito.MockitoAnnotations;

/**
 *
 * @author Pedro Arthur
 */


public class ServiceRequestServiceTest {
    
    private ServiceProposalService serviceProposalService;
    @Mock
    private ServiceDAO serviceDAO;
    @Mock
    private ServiceProposalDAO serviceProposalDAO;
    
    public void setUp() {
        
        MockitoAnnotations.initMocks(this);
        
        doNothing().when(serviceProposalDAO).delete(any(ServiceProposal.class));
        ServiceProposal proposal = new ServiceProposal();
        proposal.setId(1L);
    }
    
    @Test
    public void successDelete() {
        
    }
    
    @Test
    public void failingDelete() {
        
    }
    
    
}
