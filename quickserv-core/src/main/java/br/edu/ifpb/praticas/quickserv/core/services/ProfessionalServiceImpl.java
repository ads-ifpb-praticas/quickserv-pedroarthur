/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.ifpb.praticas.quickserv.core.services;

import br.edu.ifpb.praticas.quickserv.core.dao.interfaces.ProfessionalDAO;
import br.edu.ifpb.praticas.quickserv.core.dao.interfaces.RegisterRequestDAO;
import br.edu.ifpb.praticas.quickserv.shared.domain.Professional;
import br.edu.ifpb.praticas.quickserv.shared.domain.RegisterRequest;
import br.edu.ifpb.praticas.quickserv.shared.domain.UserAccount;
import br.edu.ifpb.praticas.quickserv.shared.enums.RegisterRequestStatus;
import br.edu.ifpb.praticas.quickserv.shared.exceptions.UserNotFoundException;
import br.edu.ifpb.praticas.quickserv.shared.services.interfaces.ProfessionalService;
import java.time.LocalDateTime;
import javax.ejb.EJB;
import javax.ejb.EJBException;
import javax.ejb.Remote;
import javax.ejb.Stateless;
import javax.persistence.EntityExistsException;

/**
 *
 * @author Pedro Arthur
 */

@Remote(ProfessionalService.class)
@Stateless
public class ProfessionalServiceImpl implements ProfessionalService {
    
    @EJB
    private ProfessionalDAO professionalDAO;
    @EJB
    private RegisterRequestDAO registerRequestDAO;

    public ProfessionalServiceImpl(ProfessionalDAO professionalDAO, RegisterRequestDAO registerRequestDAO) {
        this.professionalDAO = professionalDAO;
        this.registerRequestDAO = registerRequestDAO;
    }

    public ProfessionalServiceImpl() {
        
    }

    //Test
    @Override
    public void save(Professional professional) {
        try {
            validate(professional);
            registerRequestDAO.persist(create(professional));
            professionalDAO.persist(professional);
        } catch (EntityExistsException | IllegalArgumentException ex) {
            throw new EJBException(ex);
        }
    }
    
    private RegisterRequest create(Professional professional) {
        
        RegisterRequest registerRequest = new RegisterRequest();
        registerRequest.setAccount(professional.getUserAccount());
        registerRequest.setDocumentPhotoUrl(professional.getDocumentPhotoUrl());
        registerRequest.setStatus(RegisterRequestStatus.PENDENT);
        registerRequest.setDateTime(LocalDateTime.now());
        
        return registerRequest;
    }

    @Override
    public void update(Professional professional) {
        professionalDAO.update(professional);
    }
    
    private void validate(Professional professional) {
        if(professional == null)
            throw new IllegalArgumentException("You have to pass a professional instance to save!");
        if(professionalDAO.isCpfInUse(professional.getCpf())) {
            throw new EntityExistsException("O CPF está em uso. Por favor, insira um outro cpf e tente novamente!");
        } 
    }

    //Test  
    @Override
    public Professional getByUser(UserAccount account) {
        System.out.println("[ProfessionalServiceImpl] User Account: "+account);
        String username = account.getUsername();
        try {
            return getByUsername(username);
        } catch (UserNotFoundException ex) {
            throw new EJBException(ex);
        }
    }
    
    private Professional getByUsername(String username) throws UserNotFoundException {
        System.out.println("Username: "+username);
        Professional found = professionalDAO.getByUsername(username);
        System.out.println("Professional found: "+found);
        if(found == null)
            throw new UserNotFoundException("There isn't Professionals with the username \""+username+"\"!");
        return found;
    }
}
