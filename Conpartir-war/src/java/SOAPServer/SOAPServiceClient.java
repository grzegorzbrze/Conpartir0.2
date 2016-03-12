/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package SOAPServer;

import static java.lang.System.out;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import javax.ejb.EJB;
import javax.jws.Oneway;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;
import javax.xml.ws.RequestWrapper;
import org.conpartir.entity.Client;
import org.conpartir.entity.Comment;
import org.conpartir.entity.Driver;
import org.conpartir.entity.Taxi;
import org.conpartir.entity.Travel;
import org.conpartir.entity.User;
import org.conpartir.sessionBean.ClientManagerLocal;
import org.conpartir.sessionBean.DriverManagerLocal;
import org.conpartir.sessionBean.TravelManagerLocal;


/**
 *
 * @author Matteo
 */
@WebService(serviceName = "SOAPServiceClient")
public class SOAPServiceClient {
        
    @EJB
    private ClientManagerLocal clientRef;
    @EJB
    private TravelManagerLocal travelRef;
    @EJB
    private DriverManagerLocal driverRef;
    

    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Web Service Operation")

    /*    @WebMethod(operationName = "createClient")
    @Oneway
    public void createClient(@WebParam(name = "client") Client client) {
    clientRef.createClient(client);
    }*/

    @WebMethod(operationName = "createClient_1")
    @RequestWrapper(className = "createClient_1")
    @Oneway
    public void createClient(@WebParam(name = "name") String name, @WebParam(name = "surname") String surname, @WebParam(name = "gender") char gender, @WebParam(name = "age") int age, @WebParam(name = "email") String email, @WebParam(name = "pass") String pass, @WebParam(name = "urlPhoto") String urlPhoto) {
        clientRef.createClient(name, surname, gender, age, email, pass, urlPhoto);
    } 
    
     /**
     * Web service operation
     */
    @WebMethod(operationName = "getClient")
    public Client getClient(@WebParam(name = "email") String email) {
        return clientRef.getClient(email);
    }

    /**
     * Web service operation  
     */
    
    @WebMethod(operationName = "getTravels")
    public List<Travel> getTravels(@WebParam(name = "start") String start, @WebParam(name = "end") String end) {
        List<Travel> viaggi = travelRef.searchByOriginDestination(start, end);
        List<Travel> lista = new ArrayList();
        //costruisco un travel temporaneo per mantenere l'info dell travel_id
        //usato per ovviare al problema del servizo che non vede id_driver
        for (Travel viaggio : viaggi) {
            Travel temp = new Travel();
            temp.setClient_id(viaggio.getClient_id());
            temp.setData(viaggio.getData());
            temp.setDestination(viaggio.getDestination());
            temp.setDriver_id(viaggio.getDriver_id());
            temp.setFreeSeats(viaggio.getFreeSeats());
            temp.setOrigin(viaggio.getOrigin());
            temp.setTime(viaggio.getTime());
            temp.setTravel_id(viaggio.getTravel_id());
            lista.add(temp);
        }
        return lista;
    }
    
     /**
     * Web service operation
     */
    @WebMethod(operationName = "getTravelsFrom")
    public List<Travel> getTravelsFrom(@WebParam(name = "start") String start, @WebParam(name = "end") String end, @WebParam(name = "date") String date) {
        /*nota: il formato della stringa when è il seguente dd-MM-yy:HH:mm:SS
        nota: se la data ha qualche problema ad essere parselizzata usa la data odierna
        */
        Date when;    
        DateFormat format = new SimpleDateFormat("dd-MM-yy:HH:mm:SS");
        try { 
            when = format.parse(date); 
        }
        catch (Exception e) {
            when = new Date();
        }
        return travelRef.searchByOriginDestinationDate(when, start, end);   
    }

    /**
     * Web service operation
     */
    @WebMethod(operationName = "getDriver")
    public Driver getDriver(@WebParam(name = "ID") long id) {
        return driverRef.getDriver(id);
    }

    /**
     * Web service operation
     */
    @WebMethod(operationName = "getDriverIf")
    public List<Driver> getDrivers(@WebParam(name = "clientID") long clientID) {
        return driverRef.getDrivers(clientID);
    }

    /**
     * Web service operation
     */
    @WebMethod(operationName = "getDriverFromTravel")
    public List<User> getDriverFromTravel (@WebParam(name = "travelID") long travelID) {
        //List<String> values = null;
        Client clientInfo = travelRef.getInfoClientEqualDriver(travelID);
        Driver driverInfo = travelRef.getInfoDriverEqualClient(travelID);
        
        clientInfo.setPass(null);
        
        List<User> lista = new ArrayList();
        lista.add(clientInfo);
        lista.add(driverInfo);
        return lista;
    }

    /**
     * Web service operation
     */
    @WebMethod(operationName = "createCarTravel")
    @Oneway
    public void createCarTravel(@WebParam(name = "email") String email, @WebParam(name = "id") Long id , @WebParam(name = "from") String from, @WebParam(name = "to") String to, @WebParam(name = "when") String when, @WebParam(name = "freeSeats") int freeSeats) {
        /* nota: in questo metodo id è del driver
        nota: il formato della stringa when è il seguente dd-MM-yy:HH:mm:SS
        nota: se la data ha qualche problema ad essere parselizzata usa la data odierna
        */
        Client clientInfo = clientRef.getClient(email);
        Date data;       
        DateFormat format = new SimpleDateFormat("dd-MM-yy:HH:mm:SS");
        try { 
            data = format.parse(when); 
        }
        catch (Exception e) {
            data = new Date();
            
        }
        travelRef.createTravel(id, clientInfo.getId(), from, to, data, data, freeSeats);
    }

    /**
     * Web service operation
     * Restituisce una lista di commenti associati all'utente che li ha ricevuti
     * Match da fare tra clientId e Id_clientJudged
     */
    @WebMethod(operationName = "getComments")
    public List<Comment> getComments(@WebParam(name = "clientId") long clientId) {
        //TODO write your implementation code here:
        return null;
    }

    /**
     * Web service operation
     * Restituisce il valore complessivo dei feedback ricevuti da un utente 
     * Match da fare tra clientId e Id_clientJudged
     */
    @WebMethod(operationName = "getFeedbackValue")
    public int getFeedbacks(@WebParam(name = "clientId") long clientId) {
        int mediaFeedback = 0;
        //TODO write your implementation code here:
        return mediaFeedback;
    }

    /**
     * Web service operation
     * restituisce i commenti scritti da un utente
     * match su Id_author
     */
    @WebMethod(operationName = "getCommentsPosted")
    public List<Comment> getCommentsPosted(@WebParam(name = "authorId") long authorId) {
        //TODO write your implementation code here:
        return null;
    }

    /**
     * Web service operation
     * Restituisce una list con gli ultimi tot commenti fatti in ordine di tempo 
     * max 10
     * da usare per mostrare l'attività degli utenti in homepage
     */
    @WebMethod(operationName = "getLatestComments")
    public List<Comment> getLatestComments() {
        //TODO write your implementation code here:
        return null;
    }

    /**
     * Web service operation
     * Analogo di getTravels, per i Taxi
     */
    @WebMethod(operationName = "getTaxiTravels")
    public List<Taxi> getTaxiTravels(@WebParam(name = "from") String from, @WebParam(name = "to") String to) {
        //TODO write your implementation code here:
        return null;
    }

    /**
     * Web service operation
     * Analogo di getTravelsFrom per i Taxi
     * PERO', in questo caso nell'oggetto dateTime verrà incluso anche il tempo secondo il formato "2016-05-04T00:00:00". 
     * Il motivo è che per i taxi è anche importante sapere l'ora
     */
    @WebMethod(operationName = "getTaxiTravelsFrom")
    public List<Taxi> getTaxiTravelsFrom(@WebParam(name = "from") String from, @WebParam(name = "to") String to, @WebParam(name = "dateTime") String dateTime) {
        //TODO write your implementation code here:
        return null;
    }

    /**
     * Web service operation
     * Idea di metodo secondario per i taxi
     * restituire tutti i viaggi in taxi in partenza da quel momento in poi
     * in una situazione reale servirebbe anche un parametro FROM per definire in quale città si cerca, nel nostro caso è solo Torino quindi l'ho omesso
     */
    @WebMethod(operationName = "getTaxiIncoming")
    public List<Taxi> getTaxiIncoming(@WebParam(name = "dateTime") String dateTime) {
        //TODO write your implementation code here:
        return null;
    }

    /**
     * Web service operation
    
    @WebMethod(operationName = "createTaxiTravel")
    @Oneway
    public void createTaxiTravel(@WebParam(name = "from") String from, @WebParam(name = "to") String to, @WebParam(name = "day") String day, @WebParam(name = "hour") String hour) {
    }
 */

    
}
