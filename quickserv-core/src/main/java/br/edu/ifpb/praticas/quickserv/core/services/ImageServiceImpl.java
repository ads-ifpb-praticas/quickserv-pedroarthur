/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.ifpb.praticas.quickserv.core.services;

import br.edu.ifpb.praticas.quickserv.core.dao.interfaces.ImageDAO;
import br.edu.ifpb.praticas.quickserv.shared.services.interfaces.ImageService;
import javax.ejb.EJB;
import javax.ejb.Remote;
import javax.ejb.Stateless;

/**
 *
 * @author Pedro Arthur
 */

@Remote(ImageService.class)
@Stateless
public class ImageServiceImpl implements ImageService {
    
    @EJB
    private ImageDAO imageDAO;

    @Override
    public byte[] getUserImage(String username) {
        return imageDAO.getImageByUsername(username);
    }

    @Override
    public byte[] getDocumentPhotoByRequest(Long requestId) {
        return imageDAO.getDocumentPhoto(requestId);
    }
    
}
