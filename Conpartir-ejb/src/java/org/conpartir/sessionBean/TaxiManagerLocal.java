/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.conpartir.sessionBean;

import java.util.Date;
import java.util.List;
import javax.ejb.Local;
import org.conpartir.entity.Client;
import org.conpartir.entity.Taxi;

/**
 *
 * @author Blu Light
 */
@Local
public interface TaxiManagerLocal {
    
    public void createTaxi(Taxi taxi);
    
    public void createTaxi(Long creator_id, Long id_client, 
            Date data, Date time, String origin, String destination, int freeSeat);
    
    public boolean addPassenger(Long taxi_id, Long id_passenger);
    
    public List<Taxi> getTaxisReserved(Long id_passenger);
    
    
    /**
     * Resistuisce la lista dei taxi creati dal client
     * @param id_client
     * @return 
     */
    public List<Taxi> getTaxiCreated(Long id_client);
    
    /**
     * Il metodo risponde alla ricerca più generale per destinazione e punto di partenza
     * Restituisce una lista di taxi possibili
     */
    public List<Taxi> searchByOriginDestination(String origin, String destination);
    
    /**
     * Il metodo risponde alla ricerca per destinazione, data e punto di partenza
     * Restituisce una lista di taxi possibili
     */
    public List<Taxi> searchByOriginDestinationDate(Date data, String destination, String origin);
    
    /**
     * Il metodo risponde alla ricerca per destinazione, ora e punto di partenza
     * Restituisce una lista di taxi possibili
     */
    public List<Taxi> searchByOriginDestinationDateTime(Date data, Date time, String origin, String destination);
    
    public Taxi getTaxi(Long taxi_id);
    
}
