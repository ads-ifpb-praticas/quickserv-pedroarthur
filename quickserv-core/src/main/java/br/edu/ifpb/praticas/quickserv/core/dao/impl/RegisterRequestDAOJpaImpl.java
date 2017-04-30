/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.ifpb.praticas.quickserv.core.dao.impl;

import br.edu.ifpb.praticas.quickserv.core.dao.interfaces.RegisterRequestDAO;
import br.edu.ifpb.praticas.quickserv.shared.domain.RegisterRequest;
import br.edu.ifpb.praticas.quickserv.shared.enums.RegisterRequestStatus;
import java.util.List;
import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import org.eclipse.persistence.config.CacheUsage;
import org.eclipse.persistence.config.QueryHints;

/**
 *
 * @author Pedro Arthur
 */

@Local(RegisterRequestDAO.class)
@Stateless
public class RegisterRequestDAOJpaImpl implements RegisterRequestDAO {
    
    @PersistenceContext
    private EntityManager em;
    
    @Override
    public void persist(RegisterRequest obj) {
        em.persist(obj);
    }

    @Override
    public void update(RegisterRequest obj) {
        em.merge(obj);
    }

    @Override
    public List<RegisterRequest> listAllOrderedByDateDesc() {
        String sql = "SELECT sr FROM ServiceRequest sr ORDER BY sr.dateTime DESC";
        TypedQuery<RegisterRequest> query = em
                .createQuery(sql, RegisterRequest.class);
        return query.getResultList();
    }
    
    @Override
    public RegisterRequest getUserLastRegisterRequest(String username) {
        System.out.println("Searching "+username+" last register request ");
        TypedQuery<RegisterRequest> query = em
                .createQuery("SELECT rr FROM RegisterRequest rr WHERE rr.account.username = :username ORDER BY rr.dateTime DESC",
                        RegisterRequest.class)
                .setParameter("username", username)
                .setMaxResults(1)
                .setHint(QueryHints.CACHE_USAGE, CacheUsage.DoNotCheckCache);
        List<RegisterRequest> result = query.getResultList();
        System.out.println("result: "+result);
        if(result.isEmpty())
            return null;
        return result.get(0);
    }

    @Override
    public boolean isPendent(Long registerRequestId) {
        String sql = "SELECT COUNT(r) FROM RegisterRequest r"
                + " WHERE r.id = :id AND r.status = :status";
        TypedQuery<Long> query = em.createQuery(sql, Long.class)
                .setParameter("id", registerRequestId)
                .setParameter("status", RegisterRequestStatus.PENDENT);
        return query.getSingleResult() > 0;
    }
    
}
