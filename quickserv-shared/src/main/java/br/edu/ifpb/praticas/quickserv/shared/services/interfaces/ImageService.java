/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.ifpb.praticas.quickserv.shared.services.interfaces;

/**
 *
 * @author Pedro Arthur
 */
public interface ImageService {
    
    byte[] getUserImage(String username);
    byte[] getDocumentPhotoByRequest(Long requestId);
}
