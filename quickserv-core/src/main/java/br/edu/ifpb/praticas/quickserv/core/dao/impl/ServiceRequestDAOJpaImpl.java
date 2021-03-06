/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.ifpb.praticas.quickserv.core.dao.impl;

import br.edu.ifpb.praticas.quickserv.core.dao.interfaces.ServiceRequestDAO;
import br.edu.ifpb.praticas.quickserv.shared.domain.ServiceRequest;
import br.edu.ifpb.praticas.quickserv.shared.enums.ServiceType;
import br.edu.ifpb.praticas.quickserv.shared.enums.ServiceRequestStatus;
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

@Local(ServiceRequestDAO.class)
@Stateless
public class ServiceRequestDAOJpaImpl implements ServiceRequestDAO {
    
    @PersistenceContext
    private EntityManager em;

    @Override
    public List<ServiceRequest> listByClientCpf(String cpf) {
        TypedQuery<ServiceRequest> query = em
                .createQuery("SELECT sr FROM ServiceRequest sr "
                + "WHERE sr.owner.cpf = :cpf"
                , ServiceRequest.class)
                .setParameter("cpf", cpf);
        
        return query.getResultList();
    }

    @Override
    public void persist(ServiceRequest obj) {
        em.persist(obj);
    }

    @Override
    public void update(ServiceRequest obj) {
        em.merge(obj);
    }

    @Override
    public Long countByClient(String clientCpf) {
        String sql = "SELECT COUNT(sr) FROM ServiceRequest sr WHERE sr.owner.cpf = :cpf";
        TypedQuery<Long> query = em
                .createQuery(sql, Long.class)
                .setParameter("cpf", clientCpf);
        
        return query.getSingleResult();
    }

    @Override
    public List<ServiceRequest> listByTypeAndStatusOrderedByDate(ServiceType type, 
            ServiceRequestStatus status) {
        String sql = "SELECT sr FROM ServiceRequest sr"
                + " WHERE sr.type = :type AND sr.status = :status ORDER BY sr.dateTime";
        TypedQuery<ServiceRequest> query = em.createQuery(sql, ServiceRequest.class)
                .setParameter("type", type)
                .setParameter("status", status);
        return query.getResultList();
    }

    @Override
    public List<ServiceRequest> listOrderByDate() {
        String sql = "SELECT sr FROM ServiceRequest sr ORDER BY sr.dateTime DESC";
        TypedQuery<ServiceRequest> query = em.createQuery(sql, ServiceRequest.class);
        return query.getResultList();
    }

    @Override
    public List<ServiceRequest> listByStatusOrderByDate(ServiceRequestStatus status) {
        String sql = "SELECT sr FROM ServiceRequest sr"
                + " WHERE sr.status = :status ORDER BY sr.dateTime DESC";
        TypedQuery<ServiceRequest> query = em
                .createQuery(sql, ServiceRequest.class)
                .setParameter("status", status);
        return query.getResultList();
    }

    @Override
    public ServiceRequest find(Long serviceRequestId) {
        return em.find(ServiceRequest.class, serviceRequestId);
    }

    @Override
    public void delete(ServiceRequest serviceRequest) {
        em.remove(serviceRequest);
    }
    
}
