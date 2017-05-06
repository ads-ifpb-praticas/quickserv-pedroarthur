/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.ifpb.praticas.quickserv.core.services;

import br.edu.ifpb.praticas.quickserv.core.validation.ServiceValidator;
import br.edu.ifpb.praticas.quickserv.shared.domain.Service;
import br.edu.ifpb.praticas.quickserv.shared.domain.ServiceProposal;
import br.edu.ifpb.praticas.quickserv.shared.domain.ServiceRequest;
import br.edu.ifpb.praticas.quickserv.shared.enums.Evaluation;
import br.edu.ifpb.praticas.quickserv.shared.enums.ServiceRequestStatus;
import org.junit.Before;
import org.junit.Test;

/**
 *
 * @author Pedro Arthur
 */
public class ServiceValidatorTest {
    
    private Service service = new Service();
    
    @Before
    public void setUp() {
        service.setEvaluation(Evaluation.NONE);
        ServiceProposal proposal = new ServiceProposal();
        service.setServiceProposal(proposal);
        ServiceRequest request = new ServiceRequest();
        service.setServiceRequest(request);
        
        request.setStatus(ServiceRequestStatus.PENDENT);
    }
    
    @Test(expected = IllegalArgumentException.class)
    public void testNullRequest() {
        service.setServiceRequest(null);
        ServiceValidator.validate(service);
    }
    
    @Test(expected = IllegalArgumentException.class)
    public void testNullProposal() {
        service.setServiceProposal(null);
        ServiceValidator.validate(service);
    }
    
    @Test(expected = IllegalArgumentException.class)
    public void testNonPendentRequest() {
        service.getServiceRequest().setStatus(ServiceRequestStatus.SOLVED);
        ServiceValidator.validate(service);
    }
    
    @Test(expected = IllegalArgumentException.class)
    public void testNullService() {
        ServiceValidator.validate(null);
    }
    
    
}
