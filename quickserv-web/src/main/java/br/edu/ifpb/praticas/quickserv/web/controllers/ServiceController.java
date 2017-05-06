/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.ifpb.praticas.quickserv.web.controllers;

import br.edu.ifpb.praticas.quickserv.shared.domain.Client;
import br.edu.ifpb.praticas.quickserv.shared.domain.Professional;
import br.edu.ifpb.praticas.quickserv.shared.domain.Service;
import br.edu.ifpb.praticas.quickserv.shared.domain.ServiceProposal;
import br.edu.ifpb.praticas.quickserv.shared.domain.ServiceRequest;
import br.edu.ifpb.praticas.quickserv.shared.enums.Evaluation;
import br.edu.ifpb.praticas.quickserv.shared.enums.ServiceRequestStatus;
import br.edu.ifpb.praticas.quickserv.shared.services.interfaces.ServiceService;
import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.ejb.EJBException;
import javax.enterprise.context.Conversation;
import javax.enterprise.context.ConversationScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;


/**
 *
 * @author Pedro Arthur
 */

@Named("serviceCtrl")
@ConversationScoped
public class ServiceController implements Serializable {
    
    @Inject
    private ServiceService serviceService;
    
    @Inject
    private Conversation conversation;
    
    private ServiceRequest selectedServiceRequest;
    
    @PostConstruct
    private void init() {
        System.out.println("[ServiceController] CONSTRUCTED!!!");
        beginConversation();
        initAttributes();
    }
    
    @PreDestroy
    private void preDestroy() {
        System.out.println("[ServiceController] DESTRUCTING!!!");
    }
    
    private void initAttributes() {
        this.selectedServiceRequest = new ServiceRequest();
    }
    
    public String beginNewService(ServiceRequest request) {
        beginConversation();
        this.selectedServiceRequest = request;
        return null;
    }
    
    public String saveService(ServiceProposal proposal) {
        try {
            System.out.println("MAKING A NEW SERVICE!! {{");
            System.out.println("ServiceProposal: "+proposal);
            System.out.println("ServiceRequest: "+selectedServiceRequest+" \n}}");
            
            serviceService.newService(selectedServiceRequest, proposal);
            FacesMessage message = 
                    createMessage("O Serviço foi criado com sucesso!", 
                            FacesMessage.SEVERITY_INFO);
            addMessage("serviceMessage", message);
            initAttributes();
        } catch (EJBException ex) {
            FacesMessage message = 
                    createMessage(ex.getCausedByException().getMessage(), 
                            FacesMessage.SEVERITY_ERROR);
            addMessage("serviceMessage", message);
            initAttributes();
        }
        endConversation();
        return null;
    }
    
    public boolean wasEvaluated(Evaluation evaluation) {
        return evaluation != Evaluation.NONE;
    }
    
    public boolean isSelectedRequestPendent() {
        return this.selectedServiceRequest.getStatus().equals(ServiceRequestStatus.PENDENT);
    }
    
    public String evaluate(Service service, boolean approved) {
        this.serviceService
                .evaluate(service, approved ? Evaluation.LIKE : Evaluation.DISLIKE);
        return null;
    }
    
    public Long countByClient(Client client) {
        return this.serviceService.countByClient(client);
    }
    
    public Long countByProfessional(Professional professional) {
        return this.serviceService.countByProfessional(professional);
    }
    
    public List<Service> listByClient(Client client) {
        return serviceService.listServicesByClient(client);
    }
    
    public List<Service> listByProfessional(Professional professional) {
        return serviceService.listServicesByProfessional(professional);
    }
    
    public String close() {
        System.out.println("CLOSING...");
        endConversation();
        initAttributes();
        return null;
    }
    
    private void beginConversation() {
        if (conversation.isTransient()) {
            this.conversation.begin();
        }
    }

    private void endConversation()   {
        if (!conversation.isTransient()) {
            this.conversation.end();
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

    public ServiceRequest getSelectedServiceRequest() {
        return selectedServiceRequest;
    }

    public void setSelectedServiceRequest(ServiceRequest selectedServiceRequest) {
        this.selectedServiceRequest = selectedServiceRequest;
    }
}
