/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.conpartir.sessionBean;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import org.conpartir.entity.Client;
import org.conpartir.entity.Taxi;
import org.conpartir.facade.TaxiFacadeLocal;

/**
 *
 * @author Blu Light
 */
@Stateless
public class TaxiManager implements TaxiManagerLocal {
    @EJB
    private TaxiFacadeLocal taxiFacade;
    
    @Override
    public void createTaxi(Taxi taxi) {
        taxiFacade.create(taxi);
    }

    @Override
    public void createTaxi(Long creator_id, Long id_client, Date data, Date time,
            String origin, String destination, int freeSeat) {
        Taxi taxi = new Taxi();
        taxi.setCreator_id(creator_id);
        taxi.setClient_id(id_client);
        taxi.setData(data);
        taxi.setTime(time);
        taxi.setOrigin(origin);
        taxi.setDestination(destination);
        taxi.setFreeSeats(freeSeat);
        taxiFacade.create(taxi);
    }

    @Override
    public boolean addPassenger(Long taxi_id, Long passengerID) {
        boolean risultato = true;
        Taxi tax = getTaxi(taxi_id);
        if (tax.getTaxi_id() != null){
            List<Taxi> prenotati = this.searchByOriginDestinationDateTime
        (tax.getData(), tax.getTime(), tax.getOrigin(), tax.getDestination());
        for (Taxi prenotato : prenotati){
            if (prenotato.getCreator_id().equals(tax.getCreator_id()) 
                    && prenotato.getClient_id().equals(passengerID)){
                risultato = false;
            }
        } 
        }
        if (risultato == true){
            List<Taxi> taxis = taxiFacade.findAll();
            for (Taxi temp : taxis ){
                if (temp.getTaxi_id().equals(taxi_id)){
                    int postiRimasti = temp.getFreeSeats()-1;
                    if (postiRimasti >= 0){
                        createTaxi(temp.getCreator_id(), passengerID, temp.getData(), 
                                temp.getTime(), temp.getOrigin(), temp.getDestination(), 
                                postiRimasti);
                        temp.setFreeSeats(postiRimasti);
                    }
                    else risultato = false;
                }
            }
        }
        return risultato;
    }

    @Override
    public List<Taxi> getTaxisReserved(Long id_passenger) {
        List<Taxi> taxis = new ArrayList();
        List<Taxi> list = taxiFacade.findAll();
        if (!list.isEmpty()){
        for (Taxi temp : list){
            if (temp.getClient_id().equals(id_passenger)){
                taxis.add(temp);
            }
        }
        }
        return taxis;
    }

    @Override
    public List<Taxi> getTaxiCreated(Long id_client) {
        List<Taxi> taxis = new ArrayList();
        List<Taxi> list = taxiFacade.findAll();
        for (Taxi temp : list){
            if(temp.getCreator_id().equals(id_client) && temp.getClient_id().equals(id_client)){
                taxis.add(temp);
            }
        }
        return taxis;
    }

    @Override
    public List<Taxi> searchByOriginDestination(String origin, String destination) {
        List<Taxi> taxies = new ArrayList();
        List<Taxi> viaggi = taxiFacade.findAll();
        for (Taxi temp : viaggi){
            if (temp.getOrigin().equals(origin) && temp.getDestination().equals(destination)){
                taxies.add(temp);
            }
        }
        return taxies;
    }

    @Override
    public List<Taxi> searchByOriginDestinationDate(Date data, String origin, String destination) {
        List <Taxi> lista = new ArrayList();
        List <Taxi> viaggi = taxiFacade.findAll();
        for (Taxi temp : viaggi){
            //in questo modo vengono controllate tutte le date successive a quelle dell'utente
            if (temp.getData().after(data) && temp.getOrigin().equals(origin) && temp.getDestination().equals(destination)){      
                    lista.add(temp);
            }
        }
        return lista;
    }

    @Override
    public List<Taxi> searchByOriginDestinationDateTime(Date data, Date time, String origin, String destination) {
        //in questo modo vengono controllate tutte le date successive a quelle dell'utente
        List <Taxi> lista = searchByOriginDestinationDate(data, origin, destination);
        List <Taxi> viaggi = taxiFacade.findAll();
        for (Taxi temp : viaggi){
            //in questo modo vengono controllate tutte le date uguali a quelle dell'utente
            if (temp.getData().equals(data) && temp.getOrigin().equals(origin) && temp.getDestination().equals(destination) 
                    && afterTime(temp.getTime(), time)){ 
                    lista.add(temp);
            }
        }
        return lista;
    }
    
    /**
     * Restituisce true se l'ora temp2 è dopo l'ora temp1  
     * @param tempo1
     * @param tempo2
     * @return 
     */
    protected boolean afterTime(Date tempo1, Date tempo2){
        boolean risultato = false;
        Calendar calendar1 = Calendar.getInstance();
        calendar1.setTime(tempo1);
        int ora1 = calendar1.get(Calendar.HOUR_OF_DAY);
        int min1 = calendar1.get(Calendar.MINUTE);
        int sec1 = calendar1.get(Calendar.SECOND);
        calendar1.setTime(tempo2);
        int ora2 = calendar1.get(Calendar.HOUR_OF_DAY);
        int min2 = calendar1.get(Calendar.MINUTE);
        int sec2 = calendar1.get(Calendar.SECOND);
        if (ora2 > ora1){
            risultato = true;
        }
        if (ora2 == ora1){
            if (min2 > min1){
                risultato = true;
            }
            if (min2 == min1){
                if (sec2>sec1){
                    risultato = true;
                }
                if (sec2 == sec1)
                    risultato = true;
            }
        }   
        return risultato;
    }

    @Override
    public Taxi getTaxi(Long taxi_id) {
        Taxi viaggio = new Taxi();
        for (Taxi temp : taxiFacade.findAll()){
            if (temp.getTaxi_id().equals(taxi_id))
                viaggio = temp;
        }
        return viaggio;
    
    }
    
    
}
