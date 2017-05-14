/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.ifpb.praticas.quickserv.core.services;

import br.edu.ifpb.praticas.quickserv.core.dao.interfaces.ServiceDAO;
import br.edu.ifpb.praticas.quickserv.core.dao.interfaces.ServiceProposalDAO;
import br.edu.ifpb.praticas.quickserv.core.dao.interfaces.ServiceRequestDAO;
import br.edu.ifpb.praticas.quickserv.core.validation.AddressValidator;
import br.edu.ifpb.praticas.quickserv.shared.domain.Client;
import br.edu.ifpb.praticas.quickserv.shared.domain.Service;
import br.edu.ifpb.praticas.quickserv.shared.domain.ServiceRequest;
import br.edu.ifpb.praticas.quickserv.shared.enums.ServiceType;
import br.edu.ifpb.praticas.quickserv.shared.enums.ServiceRequestStatus;
import br.edu.ifpb.praticas.quickserv.shared.services.interfaces.ServiceRequestService;
import java.time.LocalDateTime;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.EJBException;
import javax.ejb.Remote;
import javax.ejb.Stateless;

/**
 *
 * @author Pedro Arthur
 */

@Remote(ServiceRequestService.class)
@Stateless
public class ServiceRequestServiceImpl implements ServiceRequestService {
    
    @EJB
    private ServiceRequestDAO serviceRequestDAO;
    @EJB
    private ServiceDAO serviceDAO;
    @EJB
    private ServiceProposalDAO serviceProposalDAO;
    

    public ServiceRequestServiceImpl(ServiceRequestDAO serviceRequestDAO, ServiceDAO serviceDAO, ServiceProposalDAO serviceProposalDAO) {
        this.serviceRequestDAO = serviceRequestDAO;
        this.serviceDAO = serviceDAO;
        this.serviceProposalDAO = serviceProposalDAO;
    }

    public ServiceRequestServiceImpl() {
    }

    @Override
    public void save(ServiceRequest serviceRequest) {
        try {
            validate(serviceRequest);
            AddressValidator.validate(serviceRequest.getLocate());
            serviceRequest.setDateTime(LocalDateTime.now());
            serviceRequestDAO.persist(serviceRequest);
        } catch (IllegalArgumentException ex) {
            throw new EJBException(ex);
        }
    }
    
    private void validate(ServiceRequest serviceRequest) {
        if(serviceRequest.getId() != null) {
            ServiceRequest found = serviceRequestDAO.find(serviceRequest.getId());
            if(found != null)
                throw new IllegalArgumentException("this request already exists!");
        }
    }

    @Override
    public void update(ServiceRequest serviceRequest) {
        serviceRequestDAO.update(serviceRequest);
    }

    @Override
    public List<ServiceRequest> listByClient(Client client) {
        return serviceRequestDAO.listByClientCpf(client.getCpf());
    }

    @Override
    public Long countByClient(Client client) {
        return serviceRequestDAO.countByClient(client.getCpf());
    }

    @Override
    public List<ServiceRequest> listByTypeAndStatusOrderedByDate(ServiceType type, ServiceRequestStatus status) {
        return serviceRequestDAO.listByTypeAndStatusOrderedByDate(type, status);
    }

    @Override
    public List<ServiceRequest> listByStatusOrderedByDate(ServiceRequestStatus status) {
        return serviceRequestDAO.listByStatusOrderByDate(status);
    }
    
    @Override
    public List<ServiceRequest> listOrderedByDate() {
        return serviceRequestDAO.listOrderByDate();
    }

    @Override
    public boolean isOver(ServiceRequest serviceRequest) {
        if(serviceRequest.getStatus().equals(ServiceRequestStatus.SOLVED) || 
                serviceRequest.getStatus().equals(ServiceRequestStatus.NOT_SOLVED))
            return true;
        Service service = this.serviceDAO.getByServiceRequestId(serviceRequest.getId());
        System.out.println("[ServiceRequestServiceImpl.isOver] service is "+service);
        if(service == null) return false;
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime expected = service.getServiceProposal().getChoosedDate().plusDays(1);
        return expected.isBefore(now) || expected.isEqual(now);
    }

    @Override
    public void delete(ServiceRequest serviceRequest) {
        Long requestId = serviceRequest.getId();
        Long count = serviceProposalDAO.countByServiceRequestId(requestId);
        if(count > 0) {
            System.out.println("TEM MAIS DE 0!!!!!!");
            throw new EJBException(new 
        IllegalArgumentException("Você não pode remover uma solicitação de serviço com status"
                + " \""+serviceRequest.getStatus().getDescription()+"\"!"));
        }
        System.out.println("NÃO TEM MAIS DE 0, DELETANDO.");
        ServiceRequest target = serviceRequestDAO.find(requestId);
        serviceRequestDAO.delete(target);
    }
}
