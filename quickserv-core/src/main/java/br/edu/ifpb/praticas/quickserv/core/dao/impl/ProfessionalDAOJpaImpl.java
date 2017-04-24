/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.ifpb.praticas.quickserv.core.dao.impl;

import br.edu.ifpb.praticas.quickserv.core.dao.interfaces.DAO;
import br.edu.ifpb.praticas.quickserv.core.dao.interfaces.ProfessionalDAO;
import br.edu.ifpb.praticas.quickserv.shared.domain.Client;
import br.edu.ifpb.praticas.quickserv.shared.domain.Professional;
import br.edu.ifpb.praticas.quickserv.shared.enums.RegisterRequestStatus;
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

@Local(ProfessionalDAO.class)
@Stateless
public class ProfessionalDAOJpaImpl implements ProfessionalDAO {
    
    @PersistenceContext
    private EntityManager em;

    @Override
    public void persist(Professional professional) {
        em.persist(professional);
    }

    @Override
    public void update(Professional professional) {
        em.merge(professional);
    }

    @Override
    public void updateRegisterRequestStatus(String professionalCpf, RegisterRequestStatus status) {
        
        Professional found = getByCpf(professionalCpf);
        found.setStatus(status);
        
        em.merge(found);
    }
    
    private Professional getByCpf(String cpf) {
        return em.find(Professional.class, cpf);
    }

    @Override
    public boolean isCpfInUse(String cpf) {
        String sql = "SELECT COUNT(p) FROM Professional p WHERE p.cpf = :cpf";
        TypedQuery<Long> query = em.createQuery(sql, Long.class)
                .setParameter("cpf", cpf);
        Long count = query.getSingleResult();
        return count > 0;
    }

    @Override
    public Professional getByUsername(String username) {
        String sql = "SELECT p FROM Professional p WHERE p.userAccount.username = :username";
        TypedQuery<Professional> query = em
                .createQuery(sql, Professional.class)
                .setParameter("username", username);
        
        List<Professional> professionals = query.getResultList();
        if(professionals.isEmpty())
            return null;
        else
            return professionals.get(0);
    }
    
}
