/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.ifpb.praticas.quickserv.core.dao.impl;

import br.edu.ifpb.praticas.quickserv.core.dao.interfaces.AdminDAO;
import br.edu.ifpb.praticas.quickserv.shared.domain.UserAccount;
import br.edu.ifpb.praticas.quickserv.shared.enums.UserRole;
import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

/**
 *
 * @author Pedro Arthur
 */

@Local(AdminDAO.class)
@Stateless
public class AdminDAOJpaImpl implements AdminDAO {
    
    @PersistenceContext
    private EntityManager em;

    @Override
    public boolean hasAnAdminRegistered() {
        String sql = "SELECT COUNT(u) FROM UserAccount u WHERE u.role = :role";
        TypedQuery<Long> query = em.createQuery(sql, Long.class)
                .setParameter("role", UserRole.ADMIN);
        return query.getSingleResult() > 0;
    }

    @Override
    public void persist(UserAccount obj) {
        em.persist(obj);
    }

    @Override
    public void update(UserAccount obj) {
        em.merge(obj);
    }
    
}
