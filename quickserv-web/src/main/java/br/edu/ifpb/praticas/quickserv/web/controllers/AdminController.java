/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.ifpb.praticas.quickserv.web.controllers;

import br.edu.ifpb.praticas.quickserv.shared.domain.RegisterRequest;
import br.edu.ifpb.praticas.quickserv.shared.domain.UserAccount;
import br.edu.ifpb.praticas.quickserv.shared.dto.ProfessionalRegisterRequest;
import br.edu.ifpb.praticas.quickserv.shared.enums.RegisterRequestStatus;
import br.edu.ifpb.praticas.quickserv.shared.services.interfaces.AdminService;
import br.edu.ifpb.praticas.quickserv.web.utils.SessionUtils;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpSession;

/**
 *
 * @author Pedro Arthur
 */

@Named("adminCtrl")
@RequestScoped
public class AdminController {
    
    @Inject
    private AdminService service;
    private List<ProfessionalRegisterRequest> professionalRequests;
    
    @PostConstruct
    private void init() {
        listPendentProfessionalRegisterRequests();
    }
    
    public UserAccount getLoggedAdmin() {
        HttpSession session = SessionUtils.getSession(false);
        return (UserAccount) session.getAttribute("loggedUserAccount");
    }
    
    public String respondRequest(RegisterRequest requestRegister, boolean accept) {
        this.service.approveSolicitation(requestRegister, accept);
        listPendentProfessionalRegisterRequests();
        return null;
    }
    
    public List<ProfessionalRegisterRequest> getPendentProfessionalRegisterRequests() {
        return this.service
                .listProfessionalsRegisterRequestsByStatus
        (RegisterRequestStatus.PENDENT);
    }
    
    public void listPendentProfessionalRegisterRequests() {
        this.professionalRequests = getPendentProfessionalRegisterRequests();
    }

    public List<ProfessionalRegisterRequest> getProfessionalRequests() {
        return professionalRequests;
    }

    public void setProfessionalRequests(List<ProfessionalRegisterRequest> 
            professionalRequests) {
        this.professionalRequests = professionalRequests;
    }
}
