/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.conpartir.sessionBean;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import org.conpartir.entity.Client;
import org.conpartir.entity.Driver;
import org.conpartir.entity.Travel;
import org.conpartir.facade.ClientFacadeLocal;
import org.conpartir.facade.DriverFacadeLocal;
import org.conpartir.facade.TravelFacadeLocal;


/**
 *
 * @author Blu Light
 */
@Stateless
public class TravelManager implements TravelManagerLocal {
    @EJB
    private DriverFacadeLocal driverFacade;
    @EJB
    private ClientFacadeLocal clientFacade;
    @EJB
    private TravelFacadeLocal travelFacade;
    

    @Override
    public void createTravel(Travel travel) {
        if (travel.getDriver_id() != null && travel.getClient_id() != null && 
                travel.getOrigin() != null && travel.getDestination() != null && 
                travel.getData().toString() != null && travel.getTime().toString() != null
                && travel.getFreeSeats() != 0){
            if (!isExist(travel.getDriver_id(), travel.getClient_id(), travel.getData(),
                    travel.getTime(), travel.getOrigin(), travel.getDestination())){
                travelFacade.create(travel);
            }
        }
    }

    @Override
    public void createTravel(Long driver_id, Long client_id, String origin, 
            String destination, Date data, Date time, int freeSeats) {
        if (driver_id != null && client_id != null && origin != null && destination != null
                && data.toString() != null && time.toString() != null && freeSeats != 0 ){
            if (!isExist(driver_id, client_id, data, time, origin, destination)){
                Travel travel = new Travel();
                travel.setClient_id(client_id);
                travel.setDriver_id(driver_id);
                travel.setData(data);
                travel.setDestination(destination);
                travel.setOrigin(origin);
                travel.setTime(time);
                travel.setFreeSeats(freeSeats);
                travelFacade.create(travel);
            }
        }
    }

    @Override
    public Long getTravel_ID(Long driver_id, Long client_id, Date data, 
            Date time, String origine, String destination) {
        Long id_travel = null;
        List<Travel> list = travelFacade.findAll();
        for (Travel temp : list){
            Long temp_driverID = temp.getDriver_id();
            Long temp_clientID = temp.getClient_id();
            Date temp_data = temp.getData();
            Date temp_time = temp.getTime();
            String temp_origine = temp.getOrigin();
            String temp_destination = temp.getDestination();
            if (temp_driverID != null && temp_clientID != null && 
                    temp_data.toString() != null && temp_time.toString() != null
                    && temp_origine != null && temp_destination != null){
                if (temp_driverID.equals(driver_id) && temp_clientID.equals(client_id) &&
                        equalsDate(temp_data,data) && temp_time.equals(time) && 
                        temp_origine.equals(origine) && temp_destination.equals(destination)){
                    id_travel = temp.getTravel_id();
                }
            }
        }
        return id_travel;
    }

    protected boolean isCreatorTravel(Long client_id, Long driver_id){
        boolean isCreator = false;
        List <Driver> drivers = driverFacade.findAll();
        for (Driver driver : drivers){
            if (driver.getClient_id().equals(client_id) && driver.getDriver_id().equals(driver_id)){
                isCreator = true;
            }
        }
        return isCreator;
    }
    
    @Override
    public List<Travel> getClientTravel(Long client_id, Date data, Date time) {
        List <Travel> lista = new ArrayList();
        List <Travel> viaggi = travelFacade.findAll();
        for (Travel temp : viaggi){
            if (temp.getClient_id().equals(client_id)){
                if (temp.getData().after(data) ){
                    lista.add(temp);
                }
                if (equalsDate(temp.getData(),data) && afterTime(temp.getTime(), time)){
                    lista.add(temp);
                }
            }
        }
        return sortListByDate(lista);
    }
    
    @Override
    public List<Travel> searchByOriginDestination(String origin, String destination) {
        List <Travel> lista = new ArrayList();
        List <Travel> viaggi = travelFacade.findAll();
        for (Travel temp : viaggi){
            if (temp.getDestination().equals(destination) && temp.getOrigin().equals(origin) 
                    && temp.getFreeSeats() > 0){
                if(isCreatorTravel(temp.getClient_id(), temp.getDriver_id())){
                    lista.add(temp);
                }
            }
        }       
        
        return sortListByDate(lista);
    }

    @Override
    public List<Travel> searchByOriginDestinationDate(Date data, String origin, String destination) {
        //System.out.println("data impostata: " + data);
        List <Travel> lista = new ArrayList();
        List <Travel> viaggi = travelFacade.findAll();
        for (Travel temp : viaggi){
            //in questo modo vengono controllate tutte le date successive a quelle dell'utente
            if (temp.getOrigin().equals(origin) && temp.getDestination().equals(destination) 
                    && temp.getFreeSeats() > 0){      
                if(isCreatorTravel(temp.getClient_id(), temp.getDriver_id())){
                    if(temp.getData().after(data) || equalsDate(temp.getData(),data)){
                        lista.add(temp);
                    }
                }
            }
        }
        return sortListByDate(lista);
    }

    @Override
    public List<Travel> searchByOriginDestinationDateTime(Date data, Date time, String origin, String destination) {
        //in questo modo vengono controllate tutte le date successive a quelle dell'utente
        List <Travel> lista = new ArrayList();
        List <Travel> viaggi = travelFacade.findAll();
        for (Travel temp : viaggi){
            //in questo modo vengono controllate tutte le date uguali a quelle dell'utente
            if (temp.getOrigin().equals(origin) && temp.getDestination().equals(destination) 
                    && temp.getFreeSeats() > 0){      
                if(isCreatorTravel(temp.getClient_id(), temp.getDriver_id())){
                    if (temp.getData().after(data) ){
                        lista.add(temp);
                    }
                    if (equalsDate(temp.getData(),data) && afterTime(time, temp.getTime())){
                        lista.add(temp);
                    }
                }
            }
        }
        return sortListByDate(lista);
    }
    
    @Override
    public Travel getTravel(Long travelID) {
        Travel viaggio = new Travel();
        for (Travel temp : travelFacade.findAll()){
            if (temp.getTravel_id().equals(travelID))
                viaggio = temp;
        }
        return viaggio;
    }
    
    @Override
    public Client getInfoClientEqualDriver(Long travel_id) {
        Travel travel = getTravel(travel_id); 
        Long id_client = travel.getClient_id();
        Long id_driver = travel.getDriver_id();
        List<Driver> drivers = driverFacade.findAll();
        boolean corrisponde = false;
        for (Driver driver : drivers){
            //qui sotto ho il controllo che verifica se il cliente e la macchina corrispondono
            if (driver.getClient_id().equals(id_client) && driver.getDriver_id().equals(id_driver)){ 
                corrisponde = true;
            }
        }
        if (corrisponde){
            List <Client> lista = clientFacade.findAll(); //va usato facade, se no ho problemi di accesso
            for (Client cliente : lista){
                if (cliente.getId().equals(id_client)){
                    return cliente;
                }
            }
        }
        return new Client();
    }
    
    @Override
    public Driver getInfoDriverEqualClient(Long travel_id) {
        Travel travel = getTravel(travel_id);
        Long id_client = travel.getClient_id();
        Long id_driver = travel.getDriver_id();
         List<Driver> drivers = driverFacade.findAll();
        for (Driver driver : drivers){
            //qui sotto ho il controllo che verifica se il cliente e la macchina corrispondono
            if (driver.getClient_id().equals(id_client) && driver.getDriver_id().equals(id_driver)){ 
                return driver;
            }
        }
        return new Driver();
    }

    @Override
    public boolean addPassenger(Long travel_id, Long passengerID) {
       boolean risultato = true; 
       Travel trav = getTravel(travel_id);
        if (trav.getTravel_id() != null){
            List<Travel> prenotati = this.searchByOriginDestinationDateTime
        (trav.getData(), trav.getTime(), trav.getOrigin(), trav.getDestination());
            for (Travel prenotato : prenotati){
                if (prenotato.getDriver_id().equals(trav.getDriver_id()) 
                        && prenotato.getClient_id().equals(passengerID)){
                    risultato = false;
                }
            } 
        }
        if (risultato == true){
            List<Travel> travels = travelFacade.findAll();
            for (Travel temp : travels ){
                if (temp.getTravel_id().equals(travel_id)){
                    int viaggiRimasti = temp.getFreeSeats();
                    if (viaggiRimasti>0){
                        this.createTravel(temp.getDriver_id(), passengerID, temp.getOrigin(),
                                temp.getDestination(), temp.getData(), temp.getTime(), 0);
                        temp.setFreeSeats(viaggiRimasti-1);
                        subFreeSeat(travel_id);
                    }
                    else risultato = false;
                }
            }
        }
       return risultato;
    } 
    
    /**
     * Restituisce true se la data1 è uguale alla data2 
     */
    protected boolean equalsDate(Date data1, Date data2){
        boolean uguali = false;
        Calendar calendario = Calendar.getInstance();
        calendario.setTime(data1);
        int anno1 = calendario.get(Calendar.YEAR);
        int mese1 = calendario.get(Calendar.MONTH);
        int giorno1 = calendario.get(Calendar.DAY_OF_MONTH);
        calendario.setTime(data2);
        int anno2 = calendario.get(Calendar.YEAR);
        int mese2 = calendario.get(Calendar.MONTH);
        int giorno2 = calendario.get(Calendar.DAY_OF_MONTH);
        if (anno1 == anno2 && mese1 == mese2 && giorno1 == giorno2){
            uguali = true;
        }
        return uguali;
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
     
    protected boolean subFreeSeat(Long travel_id) {
        boolean diminuito = false;
        Travel viaggio = getTravel(travel_id);
        List <Travel> lista = travelFacade.findAll();
        for (Travel temp : lista){
            if(temp.getDriver_id().equals(viaggio.getDriver_id()) &&
                    temp.getOrigin().endsWith(viaggio.getOrigin()) && 
                    temp.getDestination().equals(viaggio.getDestination()) && 
                    temp.getData().equals(viaggio.getData()) && temp.getTime().equals(viaggio.getTime())
                    && temp.getFreeSeats() != viaggio.getFreeSeats() && !temp.getTravel_id().equals(travel_id)){
                temp.setFreeSeats(viaggio.getFreeSeats());
                diminuito = true;
            }       
        }
        return diminuito;
    }
    
    protected boolean isExist(Long driver_id, Long client_id, Date data, 
            Date time, String origine, String destination){
        boolean risultato = true;
        Long risID = getTravel_ID(driver_id, client_id, data, time, origine, destination);
        if (risID == null){
            risultato = false;
        }
        return risultato;
    }
    
    protected List<Travel> sortListByDate(List<Travel> travels){
        Collections.sort(travels, new Comparator<Travel>() {
            public int compare(Travel trav1, Travel trav2) {
                return trav1.getData().compareTo(trav2.getData());
            }
        });
        return travels;
    }
   
}
