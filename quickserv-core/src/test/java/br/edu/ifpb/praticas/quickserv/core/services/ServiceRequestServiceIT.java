/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.ifpb.praticas.quickserv.core.services;

import br.edu.ifpb.praticas.quickserv.core.dao.config.DataSourceDefinitionConfig;
import br.edu.ifpb.praticas.quickserv.core.dao.impl.ServiceDAOJpaImpl;
import br.edu.ifpb.praticas.quickserv.core.dao.impl.ServiceProposalDAOJpaImpl;
import br.edu.ifpb.praticas.quickserv.core.dao.impl.ServiceRequestDAOJpaImpl;
import br.edu.ifpb.praticas.quickserv.core.dao.interfaces.ServiceDAO;
import br.edu.ifpb.praticas.quickserv.core.dao.interfaces.ServiceProposalDAO;
import br.edu.ifpb.praticas.quickserv.core.dao.interfaces.ServiceRequestDAO;
import br.edu.ifpb.praticas.quickserv.shared.domain.Address;
import br.edu.ifpb.praticas.quickserv.shared.domain.Client;
import br.edu.ifpb.praticas.quickserv.shared.domain.ServiceRequest;
import br.edu.ifpb.praticas.quickserv.shared.domain.UserAccount;
import br.edu.ifpb.praticas.quickserv.shared.enums.ServiceRequestStatus;
import br.edu.ifpb.praticas.quickserv.shared.enums.ServiceType;
import br.edu.ifpb.praticas.quickserv.shared.enums.UserRole;
import br.edu.ifpb.praticas.quickserv.shared.services.interfaces.ServiceRequestService;
import com.google.common.collect.Ordering;
import java.sql.Timestamp;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.EJBException;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.arquillian.junit.InSequence;
import org.jboss.arquillian.persistence.ApplyScriptAfter;
import org.jboss.arquillian.persistence.ApplyScriptBefore;
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
@UsingDataSet("datasets/ServiceRequest.yml")
@Cleanup(phase = TestExecutionPhase.AFTER, strategy = CleanupStrategy.USED_ROWS_ONLY)
@ApplyScriptAfter("DELETE FROM service_proposal;\n" +
"DELETE FROM service_request_dates;\n" +
"DELETE FROM service_request;\n" +
"DELETE FROM professional;\n" +
"DELETE FROM client;\n" +
"DELETE FROM user_account;")
public class ServiceRequestServiceIT {
    
    @EJB
    private ServiceRequestService serviceRequestService;
    @EJB
    private ServiceRequestDAO serviceRequestDAO;
    private ServiceRequest existingServiceRequest;
    private ServiceRequest newServiceRequest;
    private UserAccount existingUserAccount;
    private Client existingClient;
    
    @Deployment
    public static WebArchive createDeploy() {
        return ShrinkWrap.create(WebArchive.class, "quickserv-test.war")
                .addAsResource("META-INF/persistence.xml")
                .addClass(DataSourceDefinitionConfig.class)
                .addClass(UserAccount.class)
                .addClass(Client.class)
                .addClass(ServiceRequest.class)
                .addClass(ServiceRequestService.class)
                .addClass(ServiceRequestServiceImpl.class)
                .addClass(ServiceRequestDAO.class)
                .addClass(ServiceRequestDAOJpaImpl.class)
                .addClass(ServiceDAO.class)
                .addClass(ServiceDAOJpaImpl.class)
                .addClass(ServiceProposalDAO.class)
                .addClass(ServiceProposalDAOJpaImpl.class);
    }
    
    @Before
    public void setUp() {
        this.existingUserAccount = new UserAccount("fmcampos@wredenborg.se", "123456", 
                UserRole.CLIENT, true);
        this.existingClient = new Client("334.199.432-70", "Filipe Marcelo", 
                "Campos", "(62)99545-9282", new Address(), existingUserAccount);
        this.existingServiceRequest = new ServiceRequest();
        this.existingServiceRequest.setId(1L);
        this.existingServiceRequest.setDateTime(Timestamp.valueOf("2017-04-21 15:00:55.094").toLocalDateTime());
        this.existingServiceRequest.setDescription("Pintura numa casa localizada no Jardim Oásis");
        this.existingServiceRequest.setStatus(ServiceRequestStatus.PENDENT);
        this.existingServiceRequest.setTitle("Pintura numa casa localizada no Residencial Olinda");
        this.existingServiceRequest.setType(ServiceType.PAINT);
        this.existingServiceRequest.setLocate(new Address("Brasil", "GO", "Goiânia", "Residencial Olinda", "Rua MA 13", 326, "Casa"));
        this.existingServiceRequest.setOwner(existingClient);
        
        this.newServiceRequest = new ServiceRequest();
        this.newServiceRequest.setId(4L);
        this.newServiceRequest.setDateTime(Timestamp.valueOf("2017-04-24 15:00:55.094").toLocalDateTime());
        this.newServiceRequest.setDescription("Construção de 2 paredes na Residencial Olinda");
        this.newServiceRequest.setStatus(ServiceRequestStatus.PENDENT);
        this.newServiceRequest.setTitle("Construção de 2 paredes na Residencial Olinda");
        this.newServiceRequest.setType(ServiceType.MASONRY);
        this.newServiceRequest.setLocate(new Address("Brasil", "GO", "Goiânia", "Residencial Olinda", "Rua MA 13", 326, "Casa"));
        this.newServiceRequest.setOwner(existingClient);
    }
    
    
    @Test
    @InSequence(1)
    public void successSaving() {
        serviceRequestService.save(newServiceRequest);
        int size = serviceRequestDAO.listOrderByDate().size();
        assertEquals(4, size);
    }
    
    @InSequence(2)
    @Test(expected = EJBException.class)
    public void failingSaving() {
        serviceRequestService.save(existingServiceRequest);
    }
    
    @InSequence(3)
    @Test
    public void successStateUpdate() {
        this.existingServiceRequest.setStatus(ServiceRequestStatus.SOLVED);
        serviceRequestService.update(existingServiceRequest);
        ServiceRequest found = serviceRequestDAO.find(1L);
        assertEquals(ServiceRequestStatus.SOLVED, found.getStatus());
    }
    
    @InSequence(4)
    @Test
    public void successDelete() {
        ServiceRequest request = new ServiceRequest();
        request.setId(2L);
        this.serviceRequestService.delete(request);
        int size = serviceRequestDAO.listOrderByDate().size();
        assertEquals(2, size);
    }
    
    @InSequence(5)
    @Test(expected = EJBException.class)
    public void failingDelete() {
        this.serviceRequestService.delete(existingServiceRequest);
    }
    
    @InSequence(6)
    @Test
    public void listByClient() {
        List<ServiceRequest> result = this.serviceRequestService.listByClient(existingClient);
        assertEquals(3, result.size());
    }
    
    @InSequence(7)
    @Test
    public void countByClient() {
        Long count = this.serviceRequestService.countByClient(existingClient);
        assertEquals((Long)3L, count);
    }
    
    @Test
    public void listOrderedByDate() {
        List<ServiceRequest> listOrderedByDate = serviceRequestService.listOrderedByDate(); 
        assertEquals((Long)3L, listOrderedByDate.get(0).getId());
        assertEquals((Long)2L, listOrderedByDate.get(1).getId());
        assertEquals((Long)1L, listOrderedByDate.get(2).getId());
    }
    
    @Test
    public void listByStatusOrderedByDate() {
        List<ServiceRequest> listOrderedByDate = serviceRequestService.listByStatusOrderedByDate(ServiceRequestStatus.PENDENT);
        assertEquals((Long)3L, listOrderedByDate.get(0).getId());
        assertEquals((Long)2L, listOrderedByDate.get(1).getId());
        assertEquals((Long)1L, listOrderedByDate.get(2).getId());
    }
    
    
}
