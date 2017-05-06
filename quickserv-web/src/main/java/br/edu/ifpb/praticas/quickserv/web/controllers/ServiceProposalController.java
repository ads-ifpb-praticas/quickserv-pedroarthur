/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.ifpb.praticas.quickserv.web.controllers;

import br.edu.ifpb.praticas.quickserv.shared.domain.Professional;
import br.edu.ifpb.praticas.quickserv.shared.domain.ServiceProposal;
import br.edu.ifpb.praticas.quickserv.shared.domain.ServiceRequest;
import br.edu.ifpb.praticas.quickserv.shared.services.interfaces.ServiceProposalService;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
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
@Named(value = "serviceProposalCtrl")
@ConversationScoped
public class ServiceProposalController implements Serializable {

    @Inject
    private Conversation conversation;
    private ServiceRequest serviceRequest;
    private ServiceProposal serviceProposal;

    @Inject
    private ServiceProposalService serviceProposalService;
    private LocalDateTime dateTime;

    @PostConstruct
    private void init() {
        System.out.println("[ServiceProposalController] I'm constructed!");
        beginConversation();
        initAttributes();
    }

    @PreDestroy
    private void preDestroy() {
        System.out.println("[ServiceProposalController] I'm Destructed!");
    }

    private void initAttributes() {
        this.serviceRequest = new ServiceRequest();
        this.serviceProposal = new ServiceProposal();
    }

    private void beginConversation() {
        if (conversation.isTransient()) {
            this.conversation.begin();
        }
    }

    private void endConversation() {
        if (!conversation.isTransient()) {
            this.conversation.end();
        }
    }

    public String beginSave(ServiceRequest serviceRequest) {
        this.serviceRequest = serviceRequest;
        System.out.println("ServiceRequest SELECTED: " + serviceRequest);
        return null;
    }

    public String save(Professional professional) {

        this.serviceProposal.setServiceRequest(this.serviceRequest);
        this.serviceProposal.setProfessional(professional);
        this.serviceProposal.setChoosedDate(this.dateTime);
        try {
            this.serviceProposalService.save(serviceProposal);

            initAttributes();
            endConversation();

            FacesMessage message = createMessage("Proposta enviada com sucesso!", FacesMessage.SEVERITY_INFO);
            addMessage("serviceProposalMsg", message);
        } catch (EJBException ex) {
            FacesMessage message = createMessage(ex.getCausedByException().getMessage(), FacesMessage.SEVERITY_ERROR);
            addMessage("serviceProposalMsg", message);
        }
        return null;
    }

    public String remove(ServiceProposal proposal) {
        try {
            this.serviceProposalService.delete(proposal);
            FacesMessage message = createMessage("A proposta foi excluída com sucesso!", FacesMessage.SEVERITY_INFO);
            addMessage("serviceProposalMsg", message);
        } catch (EJBException ex) {
            FacesMessage message = createMessage(ex.getCausedByException().getMessage(), FacesMessage.SEVERITY_ERROR);
            addMessage("serviceProposalMsg", message);
        }
        endConversation();
        return null;
    }

    public List<ServiceProposal> listByProfessional(Professional professional) {
        List<ServiceProposal> proposals
                = this.serviceProposalService.listByProfessional(professional);
        return proposals;
    }

    public List<ServiceProposal> listByServiceRequest(ServiceRequest request) {
        if (request == null) {
            return new ArrayList<>();
        }
        return this.serviceProposalService.listByServiceRequest(request);
    }

    public Long countByProfessional(Professional professional) {
        return this.serviceProposalService.countByProfessional(professional);
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

    public ServiceProposal getServiceProposal() {
        return serviceProposal;
    }

    public void setServiceProposal(ServiceProposal serviceProposal) {
        this.serviceProposal = serviceProposal;
    }

    public ServiceRequest getServiceRequest() {
        return serviceRequest;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }

}
