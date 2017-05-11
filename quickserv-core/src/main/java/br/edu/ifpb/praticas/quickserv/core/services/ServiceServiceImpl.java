/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.ifpb.praticas.quickserv.core.services;

import br.edu.ifpb.praticas.quickserv.core.dao.interfaces.ServiceDAO;
import br.edu.ifpb.praticas.quickserv.core.dao.interfaces.ServiceRequestDAO;
import br.edu.ifpb.praticas.quickserv.core.validation.ServiceValidator;
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
            Service service = new Service(request, proposal);
            ServiceValidator.validate(service);
            ServicePK pk = new ServicePK(request.getId(), proposal.getId());
            validate(pk);
            request.setStatus(ServiceRequestStatus.IN_PROGRESS);
            requestDAO.update(request);
            serviceDao.persist(service);
        } catch (IllegalArgumentException | EntityExistsException ex) {
            throw new EJBException(ex);
        } 
    }
    
    private void validate(ServicePK pk) {
        if(serviceDao.serviceExists(pk))
            throw new EntityExistsException("This service already exists");
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
