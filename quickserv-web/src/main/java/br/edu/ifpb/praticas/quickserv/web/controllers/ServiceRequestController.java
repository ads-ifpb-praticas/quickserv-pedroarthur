/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.ifpb.praticas.quickserv.web.controllers;

import br.edu.ifpb.praticas.quickserv.shared.domain.Address;
import br.edu.ifpb.praticas.quickserv.shared.domain.Client;
import br.edu.ifpb.praticas.quickserv.shared.domain.ServiceRequest;
import br.edu.ifpb.praticas.quickserv.shared.domain.ServiceType;
import br.edu.ifpb.praticas.quickserv.shared.enums.ServiceRequestStatus;
import br.edu.ifpb.praticas.quickserv.shared.services.interfaces.ServiceRequestService;
import java.time.LocalDateTime;
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
        initAttributes();
        listAllOrderedByDate();
    }

    private void initAttributes() {
        this.serviceRequest = new ServiceRequest();
        searchResult = new ArrayList<>();
        serviceTypeSearch = ServiceType.ELECTRIC;
        address = new Address();
    }

    public void save(Client client) {

        List<LocalDateTime> dateTimes = suggestedHoursCtrl.getDateTimes();
        serviceRequest.addSuggestedDates(dateTimes);
        serviceRequest.setLocate(address);
        System.out.println("Locate: " + address);
        System.out.println("estado: " + address.getState());
        System.out.println("rua: " + address.getStreet());
        System.out.println("numero: " + address.getNumber());
        System.out.println("complemento: " + address.getComplemento());
        serviceRequest.setOwner(client);

        try {
            serviceRequestService.save(serviceRequest);
            FacesMessage message = createMessage(
                    "O Serviço " + serviceRequest.getTitle() + " foi postado com sucesso!",
                    FacesMessage.SEVERITY_INFO);
            addMessage("serviceRequestMsg", message);

            initAttributes();
        } catch (EJBException ex) {
            FacesMessage message = createMessage(ex.getCausedByException()
                    .getMessage(),
                    FacesMessage.SEVERITY_ERROR);
            addMessage("serviceRequestMsg", message);
        } finally {
            suggestedHoursCtrl.endConversation();
        }
    }

    public String remove(ServiceRequest request) {
        try {
            String title = request.getTitle();
            this.serviceRequestService.delete(request);
            FacesMessage message = createMessage(
                    "A solicitação \"" + title + "\" foi excluída com sucesso!",
                    FacesMessage.SEVERITY_INFO);
            addMessage("serviceRequestMsg", message);
        } catch (EJBException ex) {
            FacesMessage message = createMessage(ex.getCausedByException()
                    .getMessage(),
                    FacesMessage.SEVERITY_ERROR);
            addMessage("serviceRequestMsg", message);
        }
        return null;
    }

    public void changeServiceRequestStatus(ServiceRequest request, boolean solved) {
        request.setStatus(solved ? ServiceRequestStatus.SOLVED
                : ServiceRequestStatus.NOT_SOLVED);
        this.serviceRequestService.update(request);
        FacesMessage message = createMessage(
                "O estado da solicitação \"" + request.getTitle() + "\" foi alterado"
                + " para \"" + request.getStatus().getDescription() + "\" com sucesso!",
                FacesMessage.SEVERITY_INFO);
        addMessage("serviceRequestMsg", message);
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
        if (client == null) {
            return 0L;
        }
        Long quantity = serviceRequestService.countByClient(client);
        return quantity;
    }

    public List<ServiceRequest> listByClient(Client client) {
        List<ServiceRequest> serviceRequests = serviceRequestService.listByClient(client);
        return serviceRequests;
    }

    public String listPendentRequestsByTypeOrderedByDate() {
        this.searchResult = serviceRequestService
                .listByTypeAndStatusOrderedByDate(this.serviceTypeSearch,
                        ServiceRequestStatus.PENDENT);
        return null;
    }

    public List<ServiceRequest> getPendentRequestsOrderedByDate() {
        return serviceRequestService
                .listByStatusOrderedByDate(ServiceRequestStatus.PENDENT);
    }

    public String listAllOrderedByDate() {
        this.searchResult = getPendentRequestsOrderedByDate();
        return null;
    }

    public boolean isOver(ServiceRequest request) {
        return this.serviceRequestService.isOver(request);
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
