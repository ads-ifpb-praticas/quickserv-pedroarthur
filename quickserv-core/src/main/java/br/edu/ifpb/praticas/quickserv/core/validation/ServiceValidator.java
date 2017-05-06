/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.ifpb.praticas.quickserv.core.validation;

import br.edu.ifpb.praticas.quickserv.shared.domain.Service;
import br.edu.ifpb.praticas.quickserv.shared.domain.ServiceProposal;
import br.edu.ifpb.praticas.quickserv.shared.domain.ServiceRequest;
import br.edu.ifpb.praticas.quickserv.shared.enums.ServiceRequestStatus;

/**
 *
 * @author Pedro Arthur
 */
public class ServiceValidator {
    
    public static void validate(Service service) {
        if(service == null)
            throw new IllegalArgumentException("This service is null");
        ServiceRequest request = service.getServiceRequest();
        ServiceProposal proposal = service.getServiceProposal();
        if(service.getEvaluation() == null)
            throw new IllegalArgumentException("This service doesn't have any evaluation state.");
        if(proposal == null)
            throw new IllegalArgumentException("service request is null. you must provide a valid service request.");
        if(request == null)     
            throw new IllegalArgumentException("service request is null. you must provide a valid service request.");
        if(!request.getStatus().equals(ServiceRequestStatus.PENDENT))
            throw new IllegalArgumentException("Você não pode aceitar propostas"
                    + " para uma solicitação de serviço com estado \""+request.getStatus().getDescription()+"\"!");
    }
}
