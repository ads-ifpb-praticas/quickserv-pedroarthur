/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.ifpb.praticas.quickserv.web.controllers;

import br.edu.ifpb.praticas.quickserv.shared.services.interfaces.ImageService;
import javax.inject.Inject;
import javax.inject.Named;
import org.omnifaces.cdi.GraphicImageBean;

/**
 *
 * @author Pedro Arthur
 */

@Named("imageCtrl")
@GraphicImageBean
public class ImageController {
    
    @Inject
    private ImageService imageService;
    
    public byte[] getPhotoByUsername(String username) {
        return imageService.getUserImage(username);
    }
    
    public byte[] getDocumentPhoto(Long requestId) {
        return imageService.getDocumentPhotoByRequest(requestId);
    }
}
