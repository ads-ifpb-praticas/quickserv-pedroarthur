/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.ifpb.praticas.quickserv.core.services;

import br.edu.ifpb.praticas.quickserv.core.dao.interfaces.ImageDAO;
import br.edu.ifpb.praticas.quickserv.shared.services.interfaces.ImageService;
import javax.ejb.EJB;
import javax.ejb.EJBException;
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

    public ImageServiceImpl(ImageDAO imageDAO) {
        this.imageDAO = imageDAO;
    }

    public ImageServiceImpl() {
    }

    @Override
    public byte[] getUserImage(String username) {
        byte[] result = imageDAO.getImageByUsername(username);
        if(result == null)
            throw new EJBException(
                    new IllegalArgumentException("There isn't photos related"
                            + " to the username \""+username+"\""));
        return result;
    }

    @Override
    public byte[] getDocumentPhotoByRequest(Long requestId) {
        byte[] result = imageDAO.getDocumentPhoto(requestId);
        if(result == null)
            throw new EJBException(
                    new IllegalArgumentException("There isn't photos related"
                            + " to the registerRequestId \""+requestId+"\""));
        return result;
    }
    
}
