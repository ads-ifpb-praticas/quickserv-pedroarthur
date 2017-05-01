/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.ifpb.praticas.quickserv.core.dao.interfaces;

import br.edu.ifpb.praticas.quickserv.shared.domain.ServiceRequest;
import br.edu.ifpb.praticas.quickserv.shared.domain.ServiceType;
import br.edu.ifpb.praticas.quickserv.shared.enums.ServiceRequestStatus;
import java.util.List;

/**
 *
 * @author Pedro Arthur
 */
public interface ServiceRequestDAO extends DAO<ServiceRequest> {
    
    List<ServiceRequest> listByClientCpf(String cpf);
    Long countByClient(String clientCpf);
    List<ServiceRequest> listByTypeAndStatusOrderedByDate(ServiceType type, 
            ServiceRequestStatus status);
    List<ServiceRequest> listByStatusOrderByDate(ServiceRequestStatus status);
    List<ServiceRequest> listOrderByDate();
    ServiceRequest find(Long serviceRequestId);
    void delete(ServiceRequest serviceRequest);
}
