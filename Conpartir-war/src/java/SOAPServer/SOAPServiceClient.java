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
     * @return 
     */
    @WebMethod(operationName = "getClient")
    public Client getClient(@WebParam(name = "email") String email) {
        //TODO write your implementation code here:
        Client requested = clientRef.getClient(email);
        out.println(""+requested);
        return requested;
    }
    


    /**
     * Web service operation  
     */
    
    @WebMethod(operationName = "getTravels")
    public List<Travel> getTravels(@WebParam(name = "start") String start, @WebParam(name = "end") String end) {
        List<Travel> result = new ArrayList();
        List<Travel> lista = new ArrayList();
        
        Date today = Date.from(Instant.now());        
        result = travelRef.searchByOriginDestinationDate(today, start, end);   
       
        
       
        int i;
        //costruisco un travel temporaneo per mantenere l'info dell travel_id
        for (i=0;i<result.size();i++) {
            Travel temp = new Travel();
            temp.setClient_id(result.get(i).getClient_id());
            temp.setData(result.get(i).getData());
            temp.setDestination(result.get(i).getDestination());
            temp.setDriver_id(result.get(i).getDriver_id());
            temp.setFreeSeats(result.get(i).getFreeSeats());
            temp.setOrigin(result.get(i).getOrigin());
            temp.setTime(result.get(i).getTime());
            temp.setTravel_id(result.get(i).getTravel_id());
            
           // out.print(temp);
           // out.print(result.get(i));  
            
            lista.add(temp);
        }
        
        
        return lista;
    }
    
     /**
     * Web service operation
     */
    @WebMethod(operationName = "getTravelsFrom")
    public List<Travel> getTravelsFrom(@WebParam(name = "start") String start, @WebParam(name = "end") String end, @WebParam(name = "date") String date) {
        List<Travel> result = null; 
        Date when = null;       
        DateFormat format = new SimpleDateFormat("dd-MM-yyyy");
        try { 
            when = format.parse(date); 
        }
        catch (Exception e) {
        }
        //System.out.println(when); 
        result = travelRef.searchByOriginDestinationDate(when, start, end);   
        
        return result;
    }

    /**
     * Web service operation
     */
    @WebMethod(operationName = "getDriver")
    public Driver getDriver(@WebParam(name = "ID") long id) {
        Driver result = new Driver();
        result = driverRef.getDriver(id);
        
        return result;
    }

    /**
     * Web service operation
     */
    @WebMethod(operationName = "getDriverIf")
    public List<Driver> getDriverIf(@WebParam(name = "clientID") long clientID) {
        List<Driver> result = null;
        //TODO write your implementation code here:
        return result;
    }

    /**
     * Web service operation
     */
    @WebMethod(operationName = "getDriverFromTravel")
    public List<Object> getDriverFromTravel (@WebParam(name = "travelID") long travelID) {
        //List<String> values = null;
        Client clientInfo = new Client();
        Driver driverInfo = new Driver();
        
        
        clientInfo = travelRef.getInfoClientEqualDriver(travelID);
        clientInfo.setPass(null);
        
        driverInfo = travelRef.getInfoDriverEqualClient(travelID);
        
        List<Object> prova = new ArrayList();
        prova.add(clientInfo);
        prova.add(driverInfo);
        out.print(prova);
        return prova;
    }

    /**
     * Web service operation
     */
    @WebMethod(operationName = "createCarTravel")
    @Oneway
    public void createCarTravel(@WebParam(name = "email") String email, @WebParam(name = "id") Long id , @WebParam(name = "from") String from, @WebParam(name = "to") String to, @WebParam(name = "when") String when, @WebParam(name = "freeSeats") int freeSeats) {
        Client clientInfo = new Client();
        Driver driverInfo = new Driver();
        Travel nuovoViaggio = new Travel();
        driverInfo = driverRef.getDriver(id);
        clientInfo = clientRef.getClient(email);
        
        Date data = null;       
        DateFormat format = new SimpleDateFormat("dd-MM-yyyy");
        try { 
            data = format.parse(when); 
        }
        catch (Exception e) {
        }
        nuovoViaggio.setClient_id(clientInfo.getId());
        nuovoViaggio.setFreeSeats(freeSeats);
        nuovoViaggio.setDestination(to);
        nuovoViaggio.setOrigin(from);
        nuovoViaggio.setData(data);
        nuovoViaggio.setDriver_id(id);
        //TODO: NUOVOVIAGGIO.SETTIME
        travelRef.createTravel(nuovoViaggio);
       
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
