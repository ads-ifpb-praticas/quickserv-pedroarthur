/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.ifpb.praticas.quickserv.core.services;

import br.edu.ifpb.praticas.quickserv.core.dao.interfaces.ServiceRequestDAO;
import br.edu.ifpb.praticas.quickserv.shared.domain.Client;
import br.edu.ifpb.praticas.quickserv.shared.domain.ServiceRequest;
import br.edu.ifpb.praticas.quickserv.shared.domain.ServiceType;
import br.edu.ifpb.praticas.quickserv.shared.services.interfaces.ServiceRequestService;
import java.time.LocalDateTime;
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

@Remote(ServiceRequestService.class)
@Stateless
public class ServiceRequestServiceImpl implements ServiceRequestService {
    
    @EJB
    private ServiceRequestDAO serviceRequestDAO;

    @Override
    public void save(ServiceRequest serviceRequest) {
        try {
            serviceRequest.setDateTime(LocalDateTime.now());
            serviceRequestDAO.persist(serviceRequest);
        } catch (EntityExistsException ex) {
            throw new EJBException(ex);
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
    public List<ServiceRequest> listByTypeOrderedByDate(ServiceType type) {
        return serviceRequestDAO.listByTypeOrderedByDate(type);
    }

    @Override
    public List<ServiceRequest> listOrderedByDate() {
        return serviceRequestDAO.listOrderByDate();
    }
}
