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
import org.conpartir.entity.Driver;
import org.conpartir.entity.Travel;

/**
 *
 * @author Blu Light
 */
@Local
public interface TravelManagerLocal {
    
    /**
     * Permette di creare una tupla nel database tramite un oggetto di tipo post
     * @param post
     */
    public void createTravel(Travel post);
    
    /**
     * Permette di creare una tupla nel database tramite i valori esplici
     */
    public void createTravel(Long driver_id, Long client_id, String origin, 
            String destination, Date data, Date time, int freeSeats);
    
    /**
     * Restituisce un long che rappresenta il campo travel_id del oggetto Travel
     */
    public Long getTravel_ID(Long driver_id, Long client_id, Date data, Date time, 
            String origine, String destination);
    
    
    /**
     * Il metodo risponde alla ricerca più generale per destinazione e punto di partenza
     * Restituisce una lista di viaggi possibili
     */
    public List<Travel> searchByOriginDestination(String origin, String destination);
    
    /**
     * Il metodo risponde alla ricerca per destinazione, data e punto di partenza
     * Restituisce una lista di viaggi possibili
     */
    public List<Travel> searchByOriginDestinationDate(Date data, String destination, String origin);
    
    /**
     * Il metodo risponde alla ricerca per destinazione, ora e punto di partenza
     * Restituisce una lista di viaggi possibili
     */
    public List<Travel> searchByOriginDestinationDateTime(Date data, Date time, String origin, String destination);
    
    /**
     * Il metodo decrementa di 1 il numero dei posti disponibili, se possibile, e restituisce true 
     * altrimenti restituisce false
     */
    //public boolean subFreeSeat (Long travel_id);
    
    public Travel getTravel(Long travelID);
    
    public Client getInfoClientEqualDriver(Long travel_id);
    
    public Driver getInfoDriverEqualClient(Long travel_id);
    
    public boolean addPassenger(Long travel_id, Long passengerID);
}
