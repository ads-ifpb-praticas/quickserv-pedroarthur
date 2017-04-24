/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.ifpb.praticas.quickserv.core.dao.impl;

import br.edu.ifpb.praticas.quickserv.core.dao.interfaces.ClientDAO;
import br.edu.ifpb.praticas.quickserv.shared.domain.Client;
import java.util.List;
import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

/**
 *
 * @author Pedro Arthur
 */
@Local(ClientDAO.class)
@Stateless
public class ClientDAOJpaImpl implements ClientDAO {
    
    @PersistenceContext
    private EntityManager em;

    @Override
    public void persist(Client client) {
        em.persist(client);
    }

    @Override
    public void update(Client client) {
        em.merge(client);
    }

    @Override
    public boolean isCpfInUse(String cpf) {
        String sql = "SELECT COUNT(c) FROM Client c WHERE c.cpf = :cpf";
        TypedQuery<Long> query = em.createQuery(sql, Long.class)
                .setParameter("cpf", cpf);
        Long count = query.getSingleResult();
        return count > 0;
    }

    @Override
    public Client getByUsername(String username) {
        String sql = "SELECT c FROM Client c WHERE c.userAccount.username = :username";
        TypedQuery<Client> query = em
                .createQuery(sql, Client.class)
                .setParameter("username", username);
        List<Client> clients = query.getResultList();
        if(clients.isEmpty())
            return null;
        else
            return clients.get(0);
    }
    
}
