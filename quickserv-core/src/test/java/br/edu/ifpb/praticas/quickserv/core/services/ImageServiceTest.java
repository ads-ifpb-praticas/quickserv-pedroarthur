/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.ifpb.praticas.quickserv.core.services;

import br.edu.ifpb.praticas.quickserv.core.dao.interfaces.ImageDAO;
import br.edu.ifpb.praticas.quickserv.shared.services.interfaces.ImageService;
import javax.ejb.EJBException;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Matchers.any;
import org.mockito.MockitoAnnotations;

/**
 *
 * @author Pedro Arthur
 */
public class ImageServiceTest {
    
    private ImageService imageService;
    @Mock
    private ImageDAO imageDAO;
    
    @Before
    public void setUp() {
        
        MockitoAnnotations.initMocks(this);
        
        doReturn(new byte[0]).when(imageDAO)
                .getDocumentPhoto(any(Long.class));
        doReturn(new byte[0]).when(imageDAO)
                .getImageByUsername(any(String.class));
        
        this.imageService = new ImageServiceImpl(imageDAO);
    }
    
    @Test
    public void successGetUserImage() {
        this.imageService.getUserImage("teste");
    }
    
    @Test(expected = EJBException.class)
    public void failiginGetUserImage() {
        doReturn(null).when(imageDAO)
                .getImageByUsername(any(String.class));
        this.imageService.getUserImage("teste");
    }
    
    @Test
    public void successGetDocumentPhoto() {
        this.imageService.getDocumentPhotoByRequest(1L);
    }
    
    @Test(expected = EJBException.class)
    public void failiginGetDocumentPhoto() {
        doReturn(null).when(imageDAO)
                .getDocumentPhoto(any(Long.class));
        this.imageService.getDocumentPhotoByRequest(1L);
    }
}
