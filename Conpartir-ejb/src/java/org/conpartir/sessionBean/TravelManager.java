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
import org.conpartir.entity.Driver;
import org.conpartir.entity.Travel;
import org.conpartir.facade.TravelFacadeLocal;


/**
 *
 * @author Blu Light
 */
@Stateless
public class TravelManager implements TravelManagerLocal {
    @EJB
    private TravelFacadeLocal travelFacade;

    @Override
    public void createTravel(Travel travel) {
        travelFacade.create(travel);
    }

    @Override
    public void createTravel(Driver driver, Long client_id, String origin, String destination, Date data, Date time) {
        Travel travel = new Travel();
        travel.setClient_id(client_id);
        travel.setDriver(driver);
        travel.setData(data);
        travel.setDestination(destination);
        travel.setOrigin(origin);
        travel.setTime(time);
        travelFacade.create(travel);
    }

    @Override
    public Travel getTravel(Long driver_id, Long client_id) {
        Travel travel = new Travel();
        List<Travel> list = travelFacade.findAll();
        for (Travel temp : list){
            Long temp_driverID = temp.getDriver().getDriver_id();
            Long temp_clientID = temp.getClient_id();
            if (temp_driverID.equals(driver_id) && temp_clientID.equals(client_id)){
                travel = temp;
            }
        }
        return travel;
    }

    @Override
    public List<Travel> searchByOriginDestination(String origin, String destination) {
        List <Travel> lista = new ArrayList();
        List <Travel> viaggi = travelFacade.findAll();
        for (Travel temp : viaggi){
            if (temp.getDestination().equals(destination) && temp.getOrigin().equals(origin)){
                lista.add(temp);
            }
        }
        return lista;
    }

    @Override
    public List<Travel> searchByOriginDestinationDate(Date data, String origin, String destination) {
        List <Travel> lista = new ArrayList();
        List <Travel> viaggi = travelFacade.findAll();
        for (Travel temp : viaggi){
            //in questo modo vengono controllate tutte le date successive a quelle dell'utente
            if (temp.getData().after(data) && temp.getOrigin().equals(origin) && temp.getDestination().equals(destination)){      
                    lista.add(temp);
            }
        }
        return lista;
    }

    @Override
    public List<Travel> searchByOriginDestinationDateTime(Date data, Date time, String origin, String destination) {
        //in questo modo vengono controllate tutte le date successive a quelle dell'utente
        List <Travel> lista = searchByOriginDestinationDate(data, origin, destination);
        List <Travel> viaggi = travelFacade.findAll();
        for (Travel temp : viaggi){
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
            }
        }   
        return risultato;
    }
    
     @Override
    public boolean subFreeSeat(Long driver_id, Long client_id) {
        boolean diminuito = false;
        Travel viaggio = getTravel(driver_id, client_id);
        int viaggiResidui = viaggio.getFreeSeats();
        if (viaggiResidui > 0){
            viaggiResidui = viaggiResidui - 1;
            viaggio.setFreeSeats(viaggiResidui);
            diminuito = true;
        }
        return diminuito;
    }
    
}
