/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.ifpb.praticas.quickserv.shared.services.interfaces;

import br.edu.ifpb.praticas.quickserv.shared.domain.Professional;
import br.edu.ifpb.praticas.quickserv.shared.domain.ServiceProposal;
import br.edu.ifpb.praticas.quickserv.shared.domain.ServiceRequest;
import java.util.List;

/**
 *
 * @author Pedro Arthur
 */
public interface ServiceProposalService {
    
    List<ServiceProposal> listByServiceRequest(ServiceRequest serviceRequest);
    void save(ServiceProposal serviceProposal);
    void update(ServiceProposal serviceProposal);
    List<ServiceProposal> listByProfessional(Professional professional);
    Long countByProfessional(Professional professional);
    void delete(ServiceProposal serviceProposal);
}
