/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.ifpb.praticas.quickserv.core.services;

import br.edu.ifpb.praticas.quickserv.core.dao.config.DataSourceDefinitionConfig;
import br.edu.ifpb.praticas.quickserv.core.dao.impl.ServiceDAOJpaImpl;
import br.edu.ifpb.praticas.quickserv.core.dao.impl.ServiceProposalDAOJpaImpl;
import br.edu.ifpb.praticas.quickserv.core.dao.interfaces.ServiceDAO;
import br.edu.ifpb.praticas.quickserv.core.dao.interfaces.ServiceProposalDAO;
import br.edu.ifpb.praticas.quickserv.shared.domain.Address;
import br.edu.ifpb.praticas.quickserv.shared.domain.Professional;
import br.edu.ifpb.praticas.quickserv.shared.domain.ServiceProposal;
import br.edu.ifpb.praticas.quickserv.shared.domain.ServiceRequest;
import br.edu.ifpb.praticas.quickserv.shared.enums.RegisterRequestStatus;
import br.edu.ifpb.praticas.quickserv.shared.services.interfaces.ServiceProposalService;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import javax.ejb.EJB;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.arquillian.junit.InSequence;
import org.jboss.arquillian.persistence.ApplyScriptAfter;
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
@UsingDataSet("datasets/ServiceRequest.yml")
@ApplyScriptAfter("DELETE FROM service_proposal;\n" +
"DELETE FROM service_request_dates;\n" +
"DELETE FROM service_request;\n" +
"DELETE FROM professional;\n" +
"DELETE FROM client;\n" +
"DELETE FROM user_account;")
public class ServiceProposalServiceIT {
    
    @EJB
    private ServiceProposalService serviceProposalService;
    @EJB
    private ServiceProposalDAO serviceProposalDAO;
    
    private Professional existingProfessional;
    private ServiceRequest existingServiceRequest;
    
    
    @Deployment
    public static WebArchive createDeployment() {
        return ShrinkWrap.create(WebArchive.class, "quickserv-test.war")
                .addAsResource("META-INF/persistence.xml")
                .addClass(DataSourceDefinitionConfig.class)
                .addClass(ServiceProposal.class)
                .addClass(Professional.class)
                .addClass(ServiceRequest.class)
                .addClass(ServiceProposalService.class)
                .addClass(ServiceProposalServiceImpl.class)
                .addClass(ServiceDAO.class)
                .addClass(ServiceDAOJpaImpl.class)
                .addClass(ServiceProposalDAO.class)
                .addClass(ServiceProposalDAOJpaImpl.class);
    }
    
    @Before
    public void setUp() {
        this.existingProfessional = new Professional();
        this.existingProfessional.setCpf("502.012.576-88");
        this.existingProfessional.setDocumentPhotoUrl(new byte[0]);
        this.existingProfessional.setFirstname("Giovanna Vitória");
        this.existingProfessional.setLastname("Isadora Ribeiro");
        this.existingProfessional.setPhone("(98)98231-2679");
        
        this.existingServiceRequest = new ServiceRequest();
        this.existingServiceRequest.setId(2L);
    }
    
    @Test   
    @InSequence(2)
    public void successSave() {
        
        ServiceProposal serviceProposal = new ServiceProposal();
        serviceProposal.setChoosedDate(LocalDateTime.now());
        serviceProposal.setDescription("Description");
        serviceProposal.setPrice(new BigDecimal(500));
        serviceProposal.setServiceRequest(existingServiceRequest);
        serviceProposal.setProfessional(existingProfessional);
        serviceProposal.setId(2L);
        
        serviceProposalService.save(serviceProposal);
        
        List<ServiceProposal> result = serviceProposalService
                .listByProfessional(existingProfessional);
        
        assertEquals(2, result.size());
    }
    
    @Test
    @InSequence(1)
    public void testUpdate() {

        ServiceProposal serviceProposal = new ServiceProposal();
        serviceProposal.setChoosedDate(Timestamp
                .valueOf("2017-04-22 15:00:00").toLocalDateTime());
        serviceProposal.setDescription("Solucionarei #2");
        serviceProposal.setPrice(new BigDecimal(1000));
        serviceProposal.setServiceRequest(existingServiceRequest);
        serviceProposal.setProfessional(existingProfessional);
        serviceProposal.setId(1L);
        
        serviceProposalService.update(serviceProposal);
        ServiceProposal found = serviceProposalDAO.find(1L);
        
        assertEquals(serviceProposal.getDescription(), found.getDescription());
        assertEquals(serviceProposal.getPrice(), found.getPrice());               
    }
    
}
