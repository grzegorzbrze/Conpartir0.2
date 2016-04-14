/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package SOAPServer;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
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
import org.conpartir.sessionBean.CommentMagangerLocal;
import org.conpartir.sessionBean.DriverManagerLocal;
import org.conpartir.sessionBean.TaxiManagerLocal;
import org.conpartir.sessionBean.TravelManagerLocal;

import com.google.gson.Gson;



/**
 *
 * @author Matteo
 */
@WebService(serviceName = "SOAPServiceClient")
public class SOAPServiceClient {
    @EJB
    private TaxiManagerLocal taxiRef;
    @EJB
    private CommentMagangerLocal commentRef; 
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
    public void createClient(@WebParam(name = "name") String name, 
            @WebParam(name = "surname") String surname, @WebParam(name = "gender") char gender, 
            @WebParam(name = "age") int age, @WebParam(name = "email") String email, 
            @WebParam(name = "pass") String pass, @WebParam(name = "urlPhoto") String urlPhoto) {
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
    @WebMethod(operationName = "createDriver")
    public void createDriver(@WebParam(name = "client_id") long client_id, 
            @WebParam(name = "carModel") String carModel, 
            @WebParam(name = "carYear") int carYear) {
        driverRef.createDriver(carModel, carYear, client_id);
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
    @WebMethod(operationName = "getDrivers")
    public List<Driver> getDrivers(@WebParam(name = "clientEmail") String clientEmail) {
        List<Driver> lista = new ArrayList();
        
        Client cliente = clientRef.getClient(clientEmail);
        List<Driver> drivers = driverRef.getDrivers(cliente.getId());
        for (Driver driver : drivers){
            Driver temp = new Driver();
            temp.setClient_id(cliente.getId());
            temp.setCarModel(driver.getCarModel());
            temp.setCarYear(driver.getCarYear());
            temp.setDriver_id(driver.getDriver_id());
            lista.add(temp);
        }
        return lista;
    }
        
     /**
     * Web service operation
     */
    @WebMethod(operationName = "getDriverFromTravel")
    public List<Object> getDriverFromTravel (@WebParam(name = "travelID") long travelID) {
        //List<String> values = null;
        Client clientInfo = travelRef.getInfoClientEqualDriver(travelID);
        Driver driverInfo = travelRef.getInfoDriverEqualClient(travelID);
        
        clientInfo.setPass(null);
        
        List<Object> lista = new ArrayList();
        lista.add(clientInfo);
        lista.add(driverInfo);
        return lista;
    }
    
     /**
     * Web service operation
     */
    @WebMethod(operationName = "createCarTravel")
    @Oneway
    public void createCarTravel(@WebParam(name = "email") String email, 
            @WebParam(name = "id") Long id , @WebParam(name = "from") String from, 
            @WebParam(name = "to") String to, @WebParam(name = "when") String when, 
            @WebParam(name = "freeSeats") int freeSeats) {
        /* nota: in questo metodo id è del driver*/
        Client clientInfo = clientRef.getClient(email);
        Long clientId = clientInfo.getId();
        Date data = convertiStringa(when);
        travelRef.createTravel(id, clientId, from, to, data, data, freeSeats);
        
    }
    
     /**
     * Web service operation  
     */
    @WebMethod(operationName = "getTravels")
    public List<String> getTravels(@WebParam(name = "start") String start, 
            @WebParam(name = "end") String end) {
        List<Travel> viaggi = travelRef.searchByOriginDestination(start, end);
        List<String> stringhe = new ArrayList();
        for (Travel viaggio : viaggi){
            String email = clientRef.getEmail(viaggio.getClient_id());
            Client cliente = clientRef.getClient(email);
            cliente.setPass(null);
            Driver driver = driverRef.getDriver(viaggio.getDriver_id());
            
            Gson gson = new Gson();
            String utente = '"' + "client" + '"' +':' + gson.toJson(cliente);
            String autista = '"' + "client" + '"' +':' + gson.toJson(driver);
            String travel = '"' + "client" + '"' +':' + gson.toJson(viaggio);
            
            String jsonString ='{'+ utente+','+ autista+','+ travel +'}';
            
            String prova = gson.toJson(jsonString);
            stringhe.add(prova);
        }
        
        return stringhe;
    }
    
     /**
     * Web service operation
     */
    @WebMethod(operationName = "getTravelsFrom")
    public List<String> getTravelsFrom(@WebParam(name = "start") String start, 
            @WebParam(name = "end") String end, @WebParam(name = "when") String when) {
        
        Date data = convertiStringa(when);
        List<Travel> viaggi = travelRef.searchByOriginDestinationDate(data, start, end);
        List<String> stringhe = new ArrayList();
        for (Travel viaggio : viaggi){
            String email = clientRef.getEmail(viaggio.getClient_id());
            Client cliente = clientRef.getClient(email);
            cliente.setPass(null);
            Driver driver = driverRef.getDriver(viaggio.getDriver_id());
            
            Gson gson = new Gson();
            
            String client = "client";
            String drive = "driver";
            String trav = "travel";
            
           String utente = '"' + client + '"' +':' + gson.toJson(cliente);
            String autista = '"' + drive + '"' +':' + gson.toJson(driver);
            String travel = '"' + trav + '"' +':' + gson.toJson(viaggio);
            
           String jsonString ='{'+ utente+','+ autista+','+ travel +'}';
            stringhe.add(jsonString);
        }
        
        
        
        return stringhe;   
    }

     /**
     * Web service operation
     */
    @WebMethod(operationName = "getClientTravel")
    public List<Travel> getClientTravel(@WebParam(name = "email") String email) {
        List<Travel> lista = new ArrayList();
        if (email != null){
            Client temp = clientRef.getClient(email);
            Date data = new Date();
            lista = travelRef.getClientTravel(temp.getId(), data, data);
        }
        return lista;
    }
 
    
     /**
     * Web service operation
     */
    @WebMethod(operationName = "createComment")
    public void createCommet(@WebParam(name = "author_id") long author_id, 
            @WebParam(name = "clientJudged_id") long clientJudged_id, 
            @WebParam(name = "travel_id") long travel_id, 
            @WebParam(name = "comment") String comment, 
            @WebParam(name = "feedback") int feedback, 
            @WebParam(name = "when") String when) {
        
        Date data = convertiStringa(when);
        commentRef.createComment(author_id, clientJudged_id, travel_id, comment, 
                feedback, data, data);
    }

    /**
     * Web service operation
     * Restituisce una lista di commenti associati all'utente che li ha ricevuti
     * Match da fare tra clientId e Id_clientJudged
     */
    @WebMethod(operationName = "getComments")
    public List<Comment> getComments(@WebParam(name = "clientId") long clientId) {
        List<Comment> commenti = commentRef.getCommentReceived(clientId);
        List<Comment> lista = copiaCommenti(commenti);
        return lista;
    }

    /**
     * Web service operation
     * Restituisce il valore complessivo dei feedback ricevuti da un utente 
     * Match da fare tra clientId 
     */
    @WebMethod(operationName = "getFeedbackValue")
    public int getFeedbacks(@WebParam(name = "clientId") long clientId) {
        return commentRef.getAverageFeedback(clientId);
    }

    /**
     * Web service operation
     * Restituisce una list con gli ultimi numMax commenti ricevuti in ordine di tempo 
     * max 10
     * da usare per mostrare l'attività degli utenti in homepage
     */
    @WebMethod(operationName = "getLatestReceivedComments")
    public List<Comment> getLatestReceivedComments(@WebParam(name = "clientEmail") String clientEmail, @WebParam(name = "numMax") int numMax) {
        Client cliente = clientRef.getClient(clientEmail);
        List<Comment> commenti = commentRef.getCommentReceived(cliente.getId());
        List<Comment> primiNCommenti = new ArrayList();
        for (int i=0; i<numMax; i++){
            if (i<commenti.size()){
                primiNCommenti.add(i, commenti.get(i));
            }
        }
        return primiNCommenti;
    }
    
    /**
     * Web service operation
     */
    @WebMethod(operationName = "createTaxiTravel")
    public void createTaxiTravel(@WebParam(name = "creator_id") long creator_id, 
            @WebParam(name = "client_id") long client_id, 
            @WebParam(name = "origin") String origin, 
            @WebParam(name = "destination") String destination, 
            @WebParam(name = "freeSeat") int freeSeat, @WebParam(name = "when") String when){
        Date data = convertiStringa(when);
        taxiRef.createTaxi(creator_id, client_id, data, data, origin, destination, freeSeat);
    }
    
    /**
     * Web service operation
     * Analogo di getTravels, per i Taxi
     */
    @WebMethod(operationName = "getTaxiTravels")
    public List<Taxi> getTaxiTravels(@WebParam(name = "from") String from, @WebParam(name = "to") String to) {
        List<Taxi> taxis = taxiRef.searchByOriginDestination(from, to);
        return copiaTaxi(taxis);
    }

    /**
     * Web service operation
     * Analogo di getTravelsFrom per i Taxi
     * PERO', in questo caso nell'oggetto dateTime verrà incluso anche il tempo secondo il formato "2016-05-04T00:00:00". 
     * Il motivo è che per i taxi è anche importante sapere l'ora
     */
    @WebMethod(operationName = "getTaxiTravelsFrom")
    public List<Taxi> getTaxiTravelsFrom(@WebParam(name = "from") String from, @WebParam(name = "to") String to, @WebParam(name = "dateTime") String dateTime) {
        Date data = convertiStringa(dateTime);
        List<Taxi> taxis = taxiRef.searchByOriginDestinationDate(data, from, to);
        return copiaTaxi(taxis);
    }
    
    protected List<Taxi> copiaTaxi(List<Taxi> taxis){
        List<Taxi> lista = new ArrayList();
        for(Taxi taxi : taxis){
            Taxi temp = new Taxi();
            temp.setCreator_id(taxi.getCreator_id());
            temp.setTaxi_id(taxi.getTaxi_id());
            temp.setClient_id(taxi.getClient_id());
            temp.setData(taxi.getData());
            temp.setTime(taxi.getTime());
            temp.setDestination(taxi.getDestination());
            temp.setOrigin(taxi.getOrigin());
            temp.setFreeSeats(taxi.getFreeSeats());            
            lista.add(temp);
        }
        return lista;
    }
 
    protected List<Travel> copiaViaggi(List<Travel> viaggi){
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

    protected List<Comment> copiaCommenti(List<Comment> commenti){
        List<Comment> lista = new ArrayList();
        for(Comment commento : commenti){
            Comment temp = new Comment();
            temp.setId(commento.getId());
            temp.setId_author(commento.getId_author());
            temp.setId_clientJudged(commento.getId_clientJudged());
            temp.setId_travel(commento.getId_travel());
            temp.setComment(commento.getComment());
            temp.setComment_date(commento.getComment_date());
            temp.setCommet_hour(commento.getCommet_hour());
            temp.setFeedback(commento.getFeedback());
            lista.add(temp);
        }
        return lista;
    }

    protected Date convertiStringa(String when){
        Date data;       
        DateFormat format = new SimpleDateFormat("dd-MM-yy:HH:mm:SS");
        try { 
            data = format.parse(when); 
        }
        catch (Exception e) {
            data = new Date();
        }
        return data;
    }

   
    
    
}
