/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.ifpb.praticas.quickserv.core.dao.interfaces;

/**
 *
 * @author Pedro Arthur
 */
public interface ImageDAO {
    
    byte[] getImageByUsername(String username);
    byte[] getDocumentPhoto(Long requestId);
}
