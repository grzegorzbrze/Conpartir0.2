/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.conpartir.sessionBean;

import java.util.List;
import javax.ejb.Local;
import org.conpartir.entity.Client;
import org.conpartir.entity.Driver;

/**
 *
 * @author Blu Light
 */
@Local
public interface ClientManagerLocal {
    
    /**
     * Permette di creare una tupla nel database tramite un oggetto di tipo client
     * @param client 
     */
    public void createClient(Client client);

    /**
     * Permette di creare una tupla nel database tramite i valori esplici
     */
    public void createClient(String name, String surname, char gender, int age, 
            String email, String pass, String urlPhoto, List<Driver> drivers);
    
    /**
     * Permette di creare una tupla nel database tramite i valori esplici
     * esclusa la lista dei driver
     */
    public void createClient(String name, String surname, char gender, int age, 
            String email, String pass, String urlPhoto);
    
    
    /**
     * Il metodo verifica se la stringa email è presente nel database
     * @return  
     */
    public boolean isEmail(String email);

    /**
     * Restituisce la stringa l'email, se esiste, del client ricercata per ID
     * @return
     */
    public String getEmail(long id);
    
    /**
     * Restituisce l'oggetto di tipo client, se esiste, ricercato per email
     * @param email
     * @return 
     */
    public Client getClient(String email);
}
