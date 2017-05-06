/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.ifpb.praticas.quickserv.web.controllers;

import br.edu.ifpb.praticas.quickserv.shared.domain.Address;
import br.edu.ifpb.praticas.quickserv.shared.domain.Professional;
import br.edu.ifpb.praticas.quickserv.shared.domain.UserAccount;
import br.edu.ifpb.praticas.quickserv.shared.enums.UserRole;
import br.edu.ifpb.praticas.quickserv.shared.services.interfaces.ProfessionalService;
import br.edu.ifpb.praticas.quickserv.web.utils.SessionUtils;
import java.io.IOException;
import javax.annotation.PostConstruct;
import javax.ejb.EJBException;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;
import org.apache.commons.io.IOUtils;

/**
 *
 * @author Pedro Arthur
 */

@Named(value = "professionalCtrl")
@RequestScoped
public class ProfessionalController {
    
    private Part documentPhotoPart;
    private Part userPhotoPart;
    
    private Professional professional;
    private Professional updatableProfessional;
    private UserAccount account;

    @Inject
    private ProfessionalService professionalService;
    
    @PostConstruct
    private void init() {
        initAttributes();
        this.updatableProfessional = getLoggedProfessional();
    }
    
    public String saveProfessional() {
        
        try {
            byte[] documentPhoto = IOUtils.toByteArray(documentPhotoPart.getInputStream());
            byte[] userPhoto = IOUtils.toByteArray(userPhotoPart.getInputStream());
            professional.setDocumentPhotoUrl(documentPhoto);
            account.setPhoto(userPhoto);
            professional.setUserAccount(account);
            
            professionalService.save(professional);
            
            FacesMessage message = 
                    createMessage("O Profissional "+professional.getFirstname()+" foi salvo com sucesso!",
                            FacesMessage.SEVERITY_INFO);
            addMessage("ProfessionalMsg", message);
            
        } catch (IOException  ex) {
            FacesMessage message = createMessage("Erro ao realizar upload das imagens!", FacesMessage.SEVERITY_ERROR);
            addMessage("ProfessionalMsg", message);
        } catch (EJBException ex) {
            String text = ex.getCausedByException().getMessage();
            FacesMessage message = createMessage(text, FacesMessage.SEVERITY_ERROR);
            addMessage("ProfessionalMsg", message);
        }
        
        return null;
    }
    
    public String saveChanges() {
        try {
            if(userPhotoPart != null) {
                System.out.println("userPhotoPart size: "+userPhotoPart.getSize());
                System.out.println("updating...");
                this.updatableProfessional.getUserAccount().setPhoto(getUserPhotoPartBytes());
            } else {
                System.out.println("userPhotoPart has size 0!");
            }
            this.professionalService.update(this.updatableProfessional);
            FacesMessage message = 
                        createMessage("As alterações foram salvas com sucesso!",
                                FacesMessage.SEVERITY_INFO);
            addMessage("ProfessionalMsg", message);
        } catch (EJBException ex) {
            String text = ex.getCausedByException().getMessage();
            FacesMessage message = createMessage(text, FacesMessage.SEVERITY_ERROR);
            addMessage("professionalMsg", message);
        }
        return null;
    }
    
    public Professional getLoggedProfessional() {
        HttpSession session = SessionUtils.getSession(false);
        UserAccount loggedUser = (UserAccount) session.getAttribute("loggedUserAccount");   
        if(loggedUser != null && loggedUser.getRole().equals(UserRole.PROFESSIONAL)) {
            Professional loggedProfessional = professionalService.getByUser(loggedUser);
            if(loggedProfessional.getAddress() == null) {
                loggedProfessional.setAddress(new Address());
            }
            return loggedProfessional;
        }
        return null;
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
    
    private void initAttributes() {
        this.documentPhotoPart = null;
        this.professional = new Professional();
        this.account = new UserAccount();
        this.account.setRole(UserRole.PROFESSIONAL);
        this.account.setActived(true);
    }

    public Part getDocumentPhotoPart() {
        return documentPhotoPart;
    }

    public void setDocumentPhotoPart(Part documentPhotoPart) {
        this.documentPhotoPart = documentPhotoPart;
    }

    public Part getUserPhotoPart() {
        return userPhotoPart;
    }

    public void setUserPhotoPart(Part userPhotoPart) {
        this.userPhotoPart = userPhotoPart;
    }
    
    public byte[] getUserPhotoPartBytes() {
        try {
            return IOUtils.toByteArray(this.userPhotoPart.getInputStream());
        } catch (IOException ex) {
            return new byte[0];
        }
    }

    public Professional getProfessional() {
        return professional;
    }

    public void setProfessional(Professional professional) {
        this.professional = professional;
    }

    public UserAccount getAccount() {
        return account;
    }

    public void setAccount(UserAccount account) {
        this.account = account;
    }

    public Professional getUpdatableProfessional() {
        return updatableProfessional;
    }

    public void setUpdatableProfessional(Professional updatableProfessional) {
        this.updatableProfessional = updatableProfessional;
    } 
   
}
