/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.ifpb.praticas.quickserv.web.controllers;

import br.edu.ifpb.praticas.quickserv.shared.domain.Address;
import br.edu.ifpb.praticas.quickserv.shared.domain.Client;
import br.edu.ifpb.praticas.quickserv.shared.domain.UserAccount;
import br.edu.ifpb.praticas.quickserv.shared.enums.UserRole;
import br.edu.ifpb.praticas.quickserv.shared.services.interfaces.ClientService;
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

@Named(value = "clientCtrl")
@RequestScoped
public class ClientController {
    
    @Inject
    private ClientService clientService;
    
    private Client client;
    private UserAccount account;
    private Address address;
    
    private Client updatableClient;
    
    private Part userPhotoPart;
    
    @PostConstruct
    private void init() {
        initAttributes();
        this.updatableClient = getLoggedClient();
    }

    public String saveClient() {
        try {
            account.setPhoto(IOUtils.toByteArray(userPhotoPart.getInputStream()));  
            client.setUserAccount(account);
            this.clientService.save(client);
            FacesMessage message = 
                    createMessage("O Cliente "+client.getFirstname()+" foi salvo com sucesso!",
                            FacesMessage.SEVERITY_INFO);
            addMessage("ClientMsg", message);
            
            initAttributes();
        }
        catch (IOException ex) {
            FacesMessage message = createMessage("Erro ao realizar upload da foto. "
                    + "Por favor, tente uma outra foto!", FacesMessage.SEVERITY_ERROR);
            addMessage("clientMsg", message);
        }
        catch (EJBException ex) {
            String text = ex.getCausedByException().getMessage();
            FacesMessage message = createMessage(text, FacesMessage.SEVERITY_ERROR);
            addMessage("clientMsg", message);
        }
        return null;
    }
    
     public String saveChanges() {
        try {
            if(userPhotoPart != null) {
                System.out.println("userPhotoPart size: "+userPhotoPart.getSize());
                System.out.println("updating...");
                this.updatableClient.getUserAccount().setPhoto(getUserPhotoPartBytes());
            } else {
                System.out.println("userPhotoPart has size 0!");
            }
            this.clientService.update(this.updatableClient);
            FacesMessage message = 
                        createMessage("As alterações foram salvas com sucesso!",
                                FacesMessage.SEVERITY_INFO);
            addMessage("ClientMsg", message);
        } catch (EJBException ex) {
            String text = ex.getCausedByException().getMessage();
            FacesMessage message = createMessage(text, FacesMessage.SEVERITY_ERROR);
            addMessage("ClientMsg", message);
        }
        return null;
    }
    
    private void initAttributes() {
        this.client = new Client();
        this.account = new UserAccount();
        this.account.setRole(UserRole.CLIENT);
        this.account.setActived(true);
        this.address = new Address();
    }
    
    public Client getLoggedClient() {
        HttpSession session = SessionUtils.getSession(false);
        if(session == null) return null;
        UserAccount user = (UserAccount) session.getAttribute("loggedUserAccount");
        try {
            Client loggedClient = clientService.getByUserAccount(user);
            if(loggedClient.getAddress() == null) {
                loggedClient.setAddress(new Address());
            }
            return loggedClient;
        } catch (EJBException ex) {
            return null;
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

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public UserAccount getAccount() {
        return account;
    }

    public void setAccount(UserAccount account) {
        this.account = account;
    }

    public Part getUserPhotoPart() {
        return userPhotoPart;
    }
    
    public byte[] getUserPhotoPartBytes() {
        try {
            return IOUtils.toByteArray(this.userPhotoPart.getInputStream());
        } catch (IOException ex) {
            return new byte[0];
        }
    }

    public void setUserPhotoPart(Part userPhotoPart) {
        this.userPhotoPart = userPhotoPart;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public Client getUpdatableClient() {
        return updatableClient;
    }

    public void setUpdatableClient(Client updatableClient) {
        this.updatableClient = updatableClient;
    }
}
