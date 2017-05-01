/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.ifpb.praticas.quickserv.shared.services.interfaces;

import br.edu.ifpb.praticas.quickserv.shared.domain.Client;
import br.edu.ifpb.praticas.quickserv.shared.domain.ServiceRequest;
import br.edu.ifpb.praticas.quickserv.shared.domain.ServiceType;
import br.edu.ifpb.praticas.quickserv.shared.enums.ServiceRequestStatus;
import java.util.List;

/**
 *
 * @author Pedro Arthur
 */
public interface ServiceRequestService {
    
    void save(ServiceRequest serviceRequest);
    void update(ServiceRequest serviceRequest);
    List<ServiceRequest> listByClient(Client client);
    Long countByClient(Client client);
    List<ServiceRequest> listByTypeAndStatusOrderedByDate(ServiceType type, ServiceRequestStatus status);
    List<ServiceRequest> listByStatusOrderedByDate(ServiceRequestStatus status);
    List<ServiceRequest> listOrderedByDate();
    boolean isOver(ServiceRequest serviceRequest);
    void delete(ServiceRequest serviceRequest);
}
