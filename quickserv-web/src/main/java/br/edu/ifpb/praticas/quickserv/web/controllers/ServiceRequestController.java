/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.ifpb.praticas.quickserv.web.controllers;

import br.edu.ifpb.praticas.quickserv.shared.domain.Address;
import br.edu.ifpb.praticas.quickserv.shared.domain.Client;
import br.edu.ifpb.praticas.quickserv.shared.domain.Period;
import br.edu.ifpb.praticas.quickserv.shared.domain.ServiceRequest;
import br.edu.ifpb.praticas.quickserv.shared.domain.ServiceType;
import br.edu.ifpb.praticas.quickserv.shared.services.interfaces.ServiceRequestService;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJBException;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

/**
 *
 * @author Pedro Arthur
 */

@Named("serviceRequestCtrl")
@RequestScoped
public class ServiceRequestController {
    
    @Inject
    private ServiceRequestService serviceRequestService;
    
    private ServiceRequest serviceRequest = new ServiceRequest();
    private Address address = new Address();
    private ServiceType serviceTypeSearch = ServiceType.ELECTRIC;
    
    private List<ServiceRequest> searchResult = new ArrayList<>();
        
    @Inject
    private SuggestedHoursController suggestedHoursCtrl;
    
    @PostConstruct
    private void init() {
        System.out.println("[ServiceRequestController] constructed!");
        listAllOrderedByDate();
    }
    
    public void save(Client client) {
        
        List<Period> periods = suggestedHoursCtrl.getPeriods();
        serviceRequest.addSuggestedDates(periods);
        serviceRequest.setLocate(address);
        serviceRequest.setOwner(client);
        
        try {
            serviceRequestService.save(serviceRequest);
            FacesMessage message = createMessage(
                    "O Serviço "+serviceRequest.getTitle()+" foi postado com sucesso!", 
                    FacesMessage.SEVERITY_INFO);
            addMessage("serviceRequestMsg", message);
        } catch (EJBException ex) {
            FacesMessage message = createMessage(ex.getCausedByException()
                    .getMessage(), 
                    FacesMessage.SEVERITY_ERROR);
            addMessage("serviceRequestMsg", message);
        } finally {
            suggestedHoursCtrl.endConversation();
        }
    }
    
    private void addMessage(String clientId, FacesMessage message) {
        FacesContext context = FacesContext.getCurrentInstance();
        context.addMessage(clientId, message);
    }
    
    private FacesMessage createMessage(String text, FacesMessage.Severity severity) {
        FacesMessage message = new FacesMessage(text);
        message.setSeverity(severity);
        return message;
    }
    
    public Long getServiceRequestQuantityByClient(Client client) {
        if(client == null) return 0L;
        Long quantity = serviceRequestService.countByClient(client);
        return quantity;
    }
    
    public List<ServiceRequest> listByClient(Client client) {
        List<ServiceRequest> serviceRequests = serviceRequestService.listByClient(client);
        return serviceRequests;
    }
    
    public String listByTypeOrderedByDate() {
        this.searchResult = serviceRequestService.listByTypeOrderedByDate(this.serviceTypeSearch);
        return null;
    }
    
    public List<ServiceRequest> getListAllOrderedByDate() {
        return serviceRequestService.listOrderedByDate();
    }
    
    public String listAllOrderedByDate() {
        this.searchResult = getListAllOrderedByDate();
        return null;
    }
    
    public ServiceType[] getTypes() {
        return ServiceType.values();
    }

    public ServiceRequest getServiceRequest() {
        return serviceRequest;
    }

    public void setServiceRequest(ServiceRequest serviceRequest) {
        this.serviceRequest = serviceRequest;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public ServiceType getServiceTypeSearch() {
        return serviceTypeSearch;
    }

    public void setServiceTypeSearch(ServiceType serviceTypeSearch) {
        this.serviceTypeSearch = serviceTypeSearch;
    }

    public List<ServiceRequest> getSearchResult() {
        return searchResult;
    }

    public void setSearchResult(List<ServiceRequest> searchResult) {
        this.searchResult = searchResult;
    }
    
}
