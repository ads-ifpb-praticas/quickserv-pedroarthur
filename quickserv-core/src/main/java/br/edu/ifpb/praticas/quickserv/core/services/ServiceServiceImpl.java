/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.ifpb.praticas.quickserv.core.services;

import br.edu.ifpb.praticas.quickserv.core.dao.interfaces.ServiceDAO;
import br.edu.ifpb.praticas.quickserv.core.dao.interfaces.ServiceRequestDAO;
import br.edu.ifpb.praticas.quickserv.shared.domain.Address;
import br.edu.ifpb.praticas.quickserv.shared.domain.Client;
import br.edu.ifpb.praticas.quickserv.shared.domain.Professional;
import br.edu.ifpb.praticas.quickserv.shared.domain.Service;
import br.edu.ifpb.praticas.quickserv.shared.domain.ServiceProposal;
import br.edu.ifpb.praticas.quickserv.shared.domain.ServiceRequest;
import br.edu.ifpb.praticas.quickserv.shared.enums.Evaluation;
import br.edu.ifpb.praticas.quickserv.shared.enums.ServiceRequestStatus;
import br.edu.ifpb.praticas.quickserv.shared.services.interfaces.ServiceService;
import br.edu.ifpb.praticas.quickserv.shared.vo.ServicePK;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.EJBException;
import javax.ejb.Remote;
import javax.ejb.Stateless;
import javax.persistence.EntityExistsException;

/**
 *
 * @author Pedro Arthur
 */

@Remote(ServiceService.class)
@Stateless
public class ServiceServiceImpl implements ServiceService {
    
    @EJB
    private ServiceDAO serviceDao;
    @EJB
    private ServiceRequestDAO requestDAO;

    @Override
    public void newService(ServiceRequest request, ServiceProposal proposal) {
        try {
            validate(request);
            validate(proposal);
            ServicePK pk = new ServicePK(request.getId(), proposal.getId());
            validate(pk);
            request.setStatus(ServiceRequestStatus.IN_PROGRESS);
            requestDAO.update(request);
            Service service = new Service(request, proposal);
            serviceDao.persist(service);
        } catch (IllegalArgumentException | EntityExistsException ex) {
            throw new EJBException(ex);
        } 
    }
    
    private void validate(ServicePK pk) {
        if(serviceDao.serviceExists(pk))
            throw new EntityExistsException("This service already exists");
    }
    
    private void validate(ServiceProposal proposal) {
        if(proposal == null)
            throw new IllegalArgumentException("service proposal is null. you must provide a valid service proposal.");
    }
    
    private void validate(ServiceRequest request) {
        if(request == null)     
            throw new IllegalArgumentException("service request is null. you must provide a valid service request.");
        if(!request.getStatus().equals(ServiceRequestStatus.PENDENT))
            throw new IllegalArgumentException("Você não pode aceitar propostas"
                    + " para uma solicitação de serviço com estado \""+request.getStatus().getDescription()+"\"!");
        Address address = request.getLocate();
        if(address.getStreet() == null 
                || address.getStreet().isEmpty()) 
            throw new IllegalArgumentException("Before register the service you must provide fill the street field.");
        if(address.getNumber() == null 
                || address.getNumber() == 0) 
            throw new IllegalArgumentException("Before register the service you must provide fill the number field.");
        if(address.getComplemento() == null 
                || address.getComplemento().isEmpty())
            throw new IllegalArgumentException("Before register the service you must provide fill the complement field.");
    }

    @Override
    public void evaluate(Service service, Evaluation evaluation) {
        service.setEvaluation(evaluation);
        this.serviceDao.update(service);
    }

    @Override
    public List<Service> listServicesByClient(Client client) {
        return this.serviceDao.listByClient(client.getCpf());
    }

    @Override
    public List<Service> listServicesByProfessional(Professional professional) {
        return this.serviceDao.listByProfessional(professional.getCpf());
    }

    @Override
    public Long countByClient(Client client) {
        return this.serviceDao.countByClient(client.getCpf());
    }

    @Override
    public Long countByProfessional(Professional professional) {
        return this.serviceDao.countByProfessional(professional.getCpf());
    }
    
}
