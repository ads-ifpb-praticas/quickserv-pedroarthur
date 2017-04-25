/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.ifpb.praticas.quickserv.core.services;

import br.edu.ifpb.praticas.quickserv.core.dao.interfaces.ServiceProposalDAO;
import br.edu.ifpb.praticas.quickserv.shared.domain.Professional;
import br.edu.ifpb.praticas.quickserv.shared.domain.ServiceProposal;
import br.edu.ifpb.praticas.quickserv.shared.domain.ServiceRequest;
import br.edu.ifpb.praticas.quickserv.shared.services.interfaces.ServiceProposalService;
import java.util.List;
import javax.ejb.EJB;
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
    
}
