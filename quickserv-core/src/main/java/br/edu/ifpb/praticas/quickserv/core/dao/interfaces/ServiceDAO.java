/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.ifpb.praticas.quickserv.core.dao.interfaces;

import br.edu.ifpb.praticas.quickserv.shared.domain.Service;
import br.edu.ifpb.praticas.quickserv.shared.vo.ServicePK;
import java.util.List;

/**
 *
 * @author Pedro Arthur
 */
public interface ServiceDAO extends DAO<Service> {

    List<Service> listByProfessional(String professionalCpf);
    List<Service> listByClient(String professionalCpf);
    boolean serviceExists(ServicePK pk);
    Long countByClient(String clientCpf);
    Long countByProfessional(String professionalCpf);
    Service getByServiceRequestId(Long requestId);
    Service getByServiceProposalId(Long proposalId);
}
