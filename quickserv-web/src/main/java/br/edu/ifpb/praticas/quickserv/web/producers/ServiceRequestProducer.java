/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.ifpb.praticas.quickserv.web.producers;

import br.edu.ifpb.praticas.quickserv.shared.services.interfaces.ServiceRequestService;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Produces;

/**
 *
 * @author Pedro Arthur
 */
@ApplicationScoped
public class ServiceRequestProducer {
    private final String resource = "java:global/quickserv-core/ServiceRequestServiceImpl!br.edu.ifpb.praticas.quickserv.shared.services.interfaces.ServiceRequestService";
    
    @Produces
    public ServiceRequestService getClientService() {
        return new ServiceLocator().lookup(resource, ServiceRequestService.class);
    }
}
