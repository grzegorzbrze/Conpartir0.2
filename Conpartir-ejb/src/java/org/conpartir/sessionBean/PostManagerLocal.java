/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.conpartir.sessionBean;

import java.util.Date;
import java.util.List;
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
    
    /**
     * Restituisce un oggetto di tipo Post a partire dagli ID del driver e client 
     */
    public Post getPost(Long driver_id, Long client_id);
    
    
    /**
     * Il metodo risponde alla ricerca più generale per destinazione e punto di partenza
     * Restituisce una lista di viaggi possibili
     */
    public List<Post> searchByOriginDestination(String origin, String destination);
    
    /**
     * Il metodo risponde alla ricerca per destinazione, data e punto di partenza
     * Restituisce una lista di viaggi possibili
     */
    public List<Post> searchByOriginDestinationDate(Date data, String destination, String origin);
    
    /**
     * Il metodo risponde alla ricerca per destinazione, ora e punto di partenza
     * Restituisce una lista di viaggi possibili
     */
    public List<Post> searchByOriginDestinationDateTime(Date data, Date time, String origin, String destination);
    
}
