/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.conpartir.sessionBean;

import java.util.Date;
import javax.ejb.Local;
import org.conpartir.entity.Driver;
import org.conpartir.entity.Post;

/**
 *
 * @author Blu Light
 */
@Local
public interface PostManagerLocal {
    
    /**
     * Permette di creare una tupla nel database tramite un oggetto di tipo post
     * @param post
     */
    public void createPost(Post post);
    
    /**
     * Permette di creare una tupla nel database tramite i valori esplici
     */
    public void createPost(Driver driver, Long client_id, String origin, 
            String destination, Date data, Date time);
    
    public Post getPost(Long driver_id, Long client_id);
}
