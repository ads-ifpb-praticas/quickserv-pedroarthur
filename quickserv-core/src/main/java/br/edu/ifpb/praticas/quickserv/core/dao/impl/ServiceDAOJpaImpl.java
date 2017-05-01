/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.ifpb.praticas.quickserv.core.dao.impl;

import br.edu.ifpb.praticas.quickserv.core.dao.interfaces.ServiceDAO;
import br.edu.ifpb.praticas.quickserv.shared.domain.Service;
import br.edu.ifpb.praticas.quickserv.shared.vo.ServicePK;
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

@Local(ServiceDAO.class)
@Stateless
public class ServiceDAOJpaImpl implements ServiceDAO {
    
    @PersistenceContext
    private EntityManager em;

    @Override
    public void persist(Service service) {
        this.em.persist(service);
    }

    @Override
    public void update(Service service) {
        this.em.merge(service);
    }

    @Override
    public List<Service> listByProfessional(String professionalCpf) {
        String sql = "SELECT s FROM Service s ";
        TypedQuery<Service> query = em.createQuery("SELECT s FROM Service s"
                + " WHERE s.serviceProposal.professional.cpf = :cpf"
                , Service.class)
                .setParameter("cpf", professionalCpf);
        return query.getResultList();
    }

    @Override
    public List<Service> listByClient(String clientCpf) {
        String sql = "SELECT s FROM Service s ";
        TypedQuery<Service> query = em.createQuery("SELECT s FROM Service s"
                + " WHERE s.serviceRequest.owner.cpf = :cpf"
                , Service.class)
                .setParameter("cpf", clientCpf);
        return query.getResultList();
    }

    @Override
    public boolean serviceExists(ServicePK pk) {
        Service found = this.em.find(Service.class, pk);
        return found != null;
    }

    @Override
    public Long countByClient(String clientCpf) {
        TypedQuery<Long> query = em.createQuery("SELECT COUNT(s) FROM Service s"
                + " WHERE s.serviceRequest.owner.cpf = :cpf"
                , Long.class)
                .setParameter("cpf", clientCpf);
        return query.getSingleResult();
    }

    @Override
    public Long countByProfessional(String professionalCpf) {
        TypedQuery<Long> query = em.createQuery("SELECT COUNT(s) FROM Service s"
                + " WHERE s.serviceProposal.professional.cpf = :cpf"
                , Long.class)
                .setParameter("cpf", professionalCpf);
        return query.getSingleResult();
    }

    @Override
    public Service getByServiceRequestId(Long requestId) {
        TypedQuery<Service> query = em.createQuery("SELECT s FROM Service s"
                + " WHERE s.serviceRequest.id = :id",
                Service.class)
                .setParameter("id", requestId);
        List<Service> results = query.getResultList();
        System.out.println("FOUND "+results.size()+" RESUUUULTS!!!!!!!!");
        if(results.isEmpty())
            return null;
        else 
            return results.get(0);
    }

    @Override
    public Service getByServiceProposalId(Long proposalId) {
        TypedQuery<Service> query = em.createQuery("SELECT s FROM Service s"
                + " WHERE s.serviceProposal.id = :id",
                Service.class)
                .setParameter("id", proposalId);
        List<Service> results = query.getResultList();
        if(results.isEmpty())
            return null;
        else return results.get(0);
    }
}
