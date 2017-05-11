/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.ifpb.praticas.quickserv.core.services;

import br.edu.ifpb.praticas.quickserv.core.dao.interfaces.ServiceDAO;
import br.edu.ifpb.praticas.quickserv.core.dao.interfaces.ServiceProposalDAO;
import br.edu.ifpb.praticas.quickserv.shared.domain.Professional;
import br.edu.ifpb.praticas.quickserv.shared.domain.Service;
import br.edu.ifpb.praticas.quickserv.shared.domain.ServiceProposal;
import br.edu.ifpb.praticas.quickserv.shared.domain.ServiceRequest;
import br.edu.ifpb.praticas.quickserv.shared.services.interfaces.ServiceProposalService;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.EJBException;
import javax.ejb.Remote;
import javax.ejb.Stateless;

/**
 *
 * @author Pedro Arthur
 */

@Remote(ServiceProposalService.class)
@Stateless
public class ServiceProposalServiceImpl implements ServiceProposalService {
   
    @EJB
    private ServiceProposalDAO dao;
    @EJB
    private ServiceDAO serviceDAO;

    @Override
    public List<ServiceProposal> listByServiceRequest(ServiceRequest serviceRequest) {
        return dao.listByServiceRequest(serviceRequest.getId());
    }

    @Override
    public void save(ServiceProposal serviceProposal) {
        dao.persist(serviceProposal);
    }

    @Override
    public void update(ServiceProposal serviceProposal) {
        dao.update(serviceProposal);
    }

    @Override
    public List<ServiceProposal> listByProfessional(Professional professional) {
        return dao.listByProfessional(professional.getCpf());
    }

    @Override
    public Long countByProfessional(Professional professional) {
        return dao.countByProfessional(professional.getCpf());
    }

    @Override
    public void delete(ServiceProposal serviceProposal) {
        Service service = serviceDAO
                .getByServiceProposalId(serviceProposal.getId());
        if(service != null) {
            throw new EJBException(new 
        IllegalArgumentException("Você não pode remover uma proposta de serviço que já"
                + " foi aceita por um cliente solicitante."));
        }
        else {
            ServiceProposal target = dao.find(serviceProposal.getId());
            dao.delete(target);
        }
        
    }
    
}
