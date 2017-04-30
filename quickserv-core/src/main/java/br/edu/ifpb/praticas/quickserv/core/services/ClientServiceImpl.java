/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.ifpb.praticas.quickserv.core.services;

import br.edu.ifpb.praticas.quickserv.core.dao.interfaces.ClientDAO;
import br.edu.ifpb.praticas.quickserv.shared.domain.Client;
import br.edu.ifpb.praticas.quickserv.shared.domain.UserAccount;
import br.edu.ifpb.praticas.quickserv.shared.exceptions.UserNotFoundException;
import br.edu.ifpb.praticas.quickserv.shared.services.interfaces.ClientService;
import javax.ejb.EJB;
import javax.ejb.EJBException;
import javax.ejb.Remote;
import javax.ejb.Stateless;
import javax.persistence.EntityExistsException;

/**
 *
 * @author Pedro Arthur
 */

@Remote(ClientService.class)
@Stateless
public class ClientServiceImpl implements ClientService {
    
    @EJB
    private ClientDAO clientDAO;

    public ClientServiceImpl(ClientDAO clientDAO) {
        this.clientDAO = clientDAO;
    }

    public ClientServiceImpl() {
    }

    @Override
    public void save(Client client) {
        try {
            validate(client);
            clientDAO.persist(client);
        } catch (EntityExistsException ex) {
            throw new EJBException(ex);
        }
    }

    @Override
    public void update(Client client) {
        clientDAO.update(client);
    }
    
    private void validate(Client client) {
        if(clientDAO.isCpfInUse(client.getCpf())) {
            throw new EntityExistsException("O CPF está em uso. Por favor, insira um outro cpf e tente novamente!");
        } 
    }

    @Override
    public Client getByUserAccount(UserAccount userAccount) {
        try {
            return getByUsername(userAccount.getUsername());
        } catch (UserNotFoundException ex) {
            throw new EJBException(ex);
        }
    }
    
    private Client getByUsername(String username) throws UserNotFoundException {
        Client found = clientDAO.getByUsername(username);
        if(found == null)
            throw new UserNotFoundException("There isn't Clients with the username \""+username+"\"!");
        return found;
    }
    
}
