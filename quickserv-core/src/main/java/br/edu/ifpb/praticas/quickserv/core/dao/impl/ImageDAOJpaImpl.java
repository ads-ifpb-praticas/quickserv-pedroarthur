/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.ifpb.praticas.quickserv.core.dao.impl;

import br.edu.ifpb.praticas.quickserv.core.dao.interfaces.ImageDAO;
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

@Local(ImageDAO.class)
@Stateless
public class ImageDAOJpaImpl implements ImageDAO {
    
    @PersistenceContext
    private EntityManager em;

    @Override
    public byte[] getImageByUsername(String username) {
        String sql = "SELECT u.photo FROM UserAccount u WHERE u.username = :username";
        TypedQuery<byte[]> query = em.createQuery(sql, byte[].class)
                .setParameter("username", username);
        List<byte[]> results = query.getResultList();
        if(results.isEmpty()) return new byte[0];
        return results.get(0);
    }

    @Override
    public byte[] getDocumentPhoto(Long requestId) {
        String sql = "SELECT r.documentPhotoUrl FROM RegisterRequest r WHERE r.id = :requestId";
        TypedQuery<byte[]> query = em
                .createQuery(sql, byte[].class)
                .setParameter("requestId", requestId);
        List<byte[]> results = query.getResultList();
        if(results.isEmpty()) return new byte[0];
        return results.get(0);
    }
    
}
