/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.ifpb.praticas.quickserv.core.dao.impl;

import br.edu.ifpb.praticas.quickserv.core.dao.interfaces.ServiceProposalDAO;
import br.edu.ifpb.praticas.quickserv.shared.domain.ServiceProposal;
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

@Local(ServiceProposalDAO.class)
@Stateless
public class ServiceProposalDAOJpaImpl implements ServiceProposalDAO {
    
    @PersistenceContext
    private EntityManager em;

    @Override
    public List<ServiceProposal> listByServiceRequest(Long serviceRequestId) {
        String sql = "SELECT sp FROM ServiceProposal sp"
                + " WHERE sp.serviceRequest.id = :serviceRequestId";     
        TypedQuery<ServiceProposal> query = this.em
                .createQuery(sql, ServiceProposal.class)
                .setParameter("serviceRequestId", serviceRequestId);
        return query.getResultList();
    }

    @Override
    public void persist(ServiceProposal obj) {
        this.em.persist(obj);
    }

    @Override
    public void update(ServiceProposal obj) {
        this.em.merge(obj);
    }

    @Override
    public List<ServiceProposal> listByProfessional(String professionalCpf) {
        String sql = "SELECT sp FROM ServiceProposal sp WHERE sp.professional.cpf = :professionalCpf";
        TypedQuery<ServiceProposal> query = em
                .createQuery(sql, ServiceProposal.class)
                .setParameter("professionalCpf", professionalCpf);
        return query.getResultList();
    }

    @Override
    public Long countByProfessional(String professionalCpf) {
        String sql = "SELECT COUNT(sp) FROM ServiceProposal sp WHERE sp.professional.cpf = :professionalCpf";
        TypedQuery<Long> query = this.em
                .createQuery(sql, Long.class)
                .setParameter("professionalCpf", professionalCpf);
        return query.getSingleResult();
    }

    @Override
    public ServiceProposal find(Long serviceProposalId) {
        return this.em.find(ServiceProposal.class, serviceProposalId);
    }

    @Override
    public void delete(ServiceProposal serviceProposal) {
        this.em.remove(serviceProposal);
    }
    
    @Override
    public Long countByServiceRequestId(Long serviceRequestId) {
        TypedQuery<Long> query = this.em.createQuery("SELECT COUNT(sp) FROM ServiceProposal sp"
                + " WHERE sp.serviceRequest.id = :serviceRequestId",
                Long.class)
                .setParameter("serviceRequestId", serviceRequestId);
        return query.getSingleResult();
    }
    
}
