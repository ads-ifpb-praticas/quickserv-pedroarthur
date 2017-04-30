/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.ifpb.praticas.quickserv.core.dao.interfaces;

import br.edu.ifpb.praticas.quickserv.shared.domain.ServiceProposal;
import java.util.List;

/**
 *
 * @author Pedro Arthur
 */
public interface ServiceProposalDAO extends DAO<ServiceProposal> {
    
    List<ServiceProposal> listByServiceRequest(Long serviceRequestId);
    List<ServiceProposal> listByProfessional(String professionalCpf);
    Long countByProfessional(String professionalCpf);
}
