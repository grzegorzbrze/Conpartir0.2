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
import org.conpartir.temp.CommentTemp;
import org.conpartir.temp.AccountDataTemp;
import org.conpartir.temp.ClientDriverTemp;
import org.conpartir.temp.TravelDataTemp;
/*import org.conpartir.temp.DriverTemp;
import org.conpartir.temp.TaxiTemp;
import org.conpartir.temp.TravelTemp;
*/

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
    
     /**
     * Web service operation
     * Aggiunta passeggeri per un Travel
     * @param travel_id long l'id del travel in questione
     * @param email String l'email del passeggero da aggiungere
     */
    @WebMethod(operationName = "addPassenger")
    public void addPassenger(@WebParam(name = "travel_id") long travel_id, 
            @WebParam(name = "email") String email ){
        long passenger_id = clientRef.getClient(email).getId();
        travelRef.addPassenger(travel_id, passenger_id);
    }
    
     /**
     * Web service operation
     * Aggiunta passeggeri per un Taxi
     * @param taxi_id long l'id del taxi in questione
     * @param email String l'email del passeggero da aggiungere
     */
    @WebMethod(operationName = "addPassengerTaxi")
    public void addPassengerTaxi(@WebParam(name = "taxi_id") long taxi_id, 
            @WebParam(name = "email") String email ){
        long passenger_id = clientRef.getClient(email).getId();
        taxiRef.addPassenger(taxi_id, passenger_id);
    }
    
     /**
     * Web service operation
     * creazione di un client
     * @param surname String il suo cognome
     * @param name String il suo nome
     * @param gender char il suo genere 
     * @param age int la sua età
     * @param email String l'email del suo account
     * @param pass String la sua password
     */    
    @WebMethod(operationName = "createClient_1")
    @RequestWrapper(className = "createClient_1")
    @Oneway
    public void createClient(@WebParam(name = "name") String name, 
            @WebParam(name = "surname") String surname, @WebParam(name = "gender") char gender, 
            @WebParam(name = "age") int age, @WebParam(name = "email") String email, 
            @WebParam(name = "pass") String pass) {
        clientRef.createClient(name, surname, gender, age, email, pass);
    }     
    
    
     /**
     * Web service operation
     * modifica delle informazioni di un client. L'unica informazione necessaria 
     * alla modifica è la vecchia password (oldPass). I restanti parametri devono essere riempiti
     * solo se si desidera modificare il rispettivo campo nel DB
     * @param surname String il suo cognome
     * @param name String il suo nome
     * @param gender char il suo genere 
     * @param age int la sua età
     * @param urlPhoto String l'url della foto del suo profilo
     * @param email String l'email del suo account
     * @param oldPass String la sua password precedente; 
     *        necessaria per autorizzare la modifica
     * @param newPass String la sua nuova password
     * @return status String l'esito dell'operazione di modifica
     */
    @WebMethod(operationName = "editClient")
    public String editClient(@WebParam(name = "email") String email, @WebParam(name = "name") String name, 
            @WebParam(name = "surname") String surname, @WebParam(name = "gender") String gender, 
            @WebParam(name = "age") int age, @WebParam(name = "urlPhoto") String urlPhoto, 
            @WebParam(name = "oldPass") String oldPass, @WebParam(name = "newPass") String newPass){
        Client datiClient = clientRef.getClient(email);
        String status;
        char cGender = gender.charAt(0);
        if (name.equals("undefined") || name.equals(" ")){
            name = null;        }
        if (surname.equals("undefined") || surname.equals(" ")){
            surname = null;        }
        if (cGender == 'u'){
            cGender = ' ';        }
        if (newPass.equals("undefined") || newPass.equals(" ")){
            newPass = null;        }
        if (urlPhoto.equals("undefined") || urlPhoto.equals(" ")){
            urlPhoto = null;        }        
        if(oldPass != null && !datiClient.getPass().equals(oldPass)) {           
            status = "Le password inserite non coincidono";
        }
        else {
            clientRef.editClient(email, name, surname, cGender, age, newPass, urlPhoto);
            status = "Modifica dell'account eseguita con successo";
        }
        return status;
    }
    
    /**
     * Web service operation
     * Metodo per impostare il nome dell'account twitter di un utente.
     * @param email String l'email dell'utente desiderato
     * @param twitterValue String il nome account di twitter
     * @return String l'esito dell'operazione
     */
    @WebMethod(operationName = "setTwitter")
    public String setTwitter(@WebParam(name = "email") String email, 
            @WebParam(name = "twitterValue") String twitterValue) {
        if (clientRef.setOtherEmail(email, null, twitterValue)){
            return "Twitter associato con successo";
        }
        else{
            return "Questa indirizzo Twitter viene già utilizzata in un altro account.";
        }
    }
    
    /**
     * Web service operation
     * Metodo per impostare l'account google di un utente.
     * @param email String l'email dell'utente desiderato
     * @param gmailValue String l'email gmail
     * @return String l'esito dell'operazione
     */
    @WebMethod(operationName = "setGmail")
    public String setGmail(@WebParam(name = "email") String email,
            @WebParam(name = "gmailValue") String gmailValue){        
        if (clientRef.setOtherEmail(email, gmailValue, null)){
            return "Gmail associato con successo";
        }
        else{
            return "Questa Gmail viene già utilizzata in un altro account.";
        }
    }
    
       
    /**
     * Web service operation
     * Metodo per controllare se un utente ha una gmail
     * @param email String L'email dell'utente su cui effettuare il controllo
     * @return boolean la presenza o meno di un valore di gmail diverso da null
     */
    @WebMethod(operationName = "isGmailOn")
    public boolean isGmailOn(@WebParam(name = "email") String email) {
        return clientRef.getClient(email).getGmailValue() != null;
    }
       
    /**
     * Web service operation
     * Controlla se una gmail è associata a un'account
     * se esiste, restituisce l'email (per fare il login)
     * @param gmailValue String la gmail da controllare
     * @return accountEmail String l'email che identifica l'eventuale account con quella gmail
     */
    @WebMethod(operationName = "isGmailThere")
    public String isGmailThere(@WebParam(name = "gmailValue") String gmailValue) {
        Client temp = clientRef.getClientByGmail(gmailValue);
        String accountEmail = temp.getEmail();        
        return accountEmail;
    }
    
    /**
     * Web service operation
     * Metodo per controllare se un utente ha twitter
     * @param email String L'email dell'utente su cui effettuare il controllo
     * @return boolean la presenza o meno di un valore di twitter diverso da null
     */
    @WebMethod(operationName = "isTwitterOn")
    public boolean isTwitterOn(@WebParam(name = "email") String email) {
        return clientRef.getClient(email).getTwitterValue() != null;
    }
    
     /**
     * Web service operation
     * Controlla se c'é un twitter associato a un'account
     * se esiste, restituisce l'email (per fare il login)
     * @param twitterValue String il nome account di Twitter da controllare
     * @return accountEmail String l'email che identifica l'eventuale account con quel twitter   
     */
    @WebMethod(operationName = "isTwitterThere")
    public String isTwitterThere(@WebParam(name = "twitterValue") String twitterValue) {  
        Client temp = clientRef.getClientByTwitter(twitterValue);        
        String accountEmail = temp.getEmail();        
        return accountEmail;
    }
    
    /**
     * Web service operation
     * Cancella una mail secondaria su richiesta dell'utente (email)
     * il campo secondaryEmail è una stringa che indica quale valore cancellare 
     * per ora o twitter o gmail
     * Il metodo NON cancella le mail principali.
     * @param email String l'email che identifica l'utente
     * @param secondaryEmail String parametro da settare a "gmail" o "twitter"; 
     *                              identifica la "mail" secondaria da cancellare
     * @return String stato dell'operazione
     */
    @WebMethod(operationName = "deleteSecondaryEmail")
    public String deleteSecondaryEmail(@WebParam(name = "email") String email,
            @WebParam(name = "secondaryEmail") String secondaryEmail) {

        if (secondaryEmail.equals("gmail")) {
            clientRef.setOtherEmail(email, "", null);
            return "Gmail disaccoppiata con successo";
        }
        if (secondaryEmail.equals("twitter")) {
            clientRef.setOtherEmail(email, null, "");
            return "Twitter disaccoppiato con successo";
        }
        return "errore";
    }

    
    
    
     /**
     * Web service operation
     * restituisce un client data la sua mail
     * @param email String l'email del cliente di cui si desiderano i dati
     * @return user AccountDataTemp oggetto composto che contiene i dati del cliente
     */
    @WebMethod(operationName = "getClient")
    public AccountDataTemp getClient(@WebParam(name = "email") String email) {
        AccountDataTemp user = new AccountDataTemp();
        Client userData = clientRef.getClient(email);
        Long clientID = userData.getId();
        user.setAge(userData.getAge());
        user.setEmail(email);
        String sGender = Character.toString(userData.getGender());
        user.setGender(sGender);
        user.setName(userData.getName());
        user.setSurname(userData.getSurname());
        user.setUrlPhoto(userData.getUrlPhoto());
        if (userData.getGmailValue() != null){
            user.setGmail(true);
        }
        else
            user.setGmail(false);
        if (userData.getTwitterValue() != null){
            user.setTwitter(true);
        }
        else
            user.setTwitter(false);
        
        
        List<Driver> driversData = driverRef.getDrivers(clientID);
        user.setDrivers(driversData);
        Date today = new Date();    
        List<Travel> travelsData = travelRef.getClientTravelBefore(userData.getId(), today, today);
        travelsData.addAll(travelRef.getClientTravelAfter(userData.getId(), today, today));
        List<Travel> postedTravelsList = new ArrayList();
        List<Travel> bookedTravelsList = new ArrayList();
        for(int i=0;i<travelsData.size();i++) {
            long travelID = travelsData.get(i).getTravel_id();
            Travel temp = new Travel();
            temp.setData(travelsData.get(i).getData());
            temp.setDestination(travelsData.get(i).getDestination());
            temp.setOrigin(travelsData.get(i).getOrigin());
            temp.setFreeSeats(travelsData.get(i).getFreeSeats());
            temp.setTime(travelsData.get(i).getTime());
            temp.setTravel_id(travelID);
            temp.setDriver_id(travelsData.get(i).getDriver_id());
           // Driver driverTemp = travelRef.getInfoDriverEqualClient(travelID);        
            if (driversData.contains(driverRef.getDriver(temp.getDriver_id())) == false){
                bookedTravelsList.add(temp);
            }
            else{
                postedTravelsList.add(temp);
            }
        }            
        user.setBookedTravels(bookedTravelsList);
        user.setPostedTravels(postedTravelsList);
        List<Taxi> postedTaxiList = taxiRef.getTaxiCreated(clientID);
        List<Taxi> bookedTaxiList = taxiRef.getTaxisReserved(clientID);     
        user.setBookedTaxis(bookedTaxiList);
        user.setPostedTaxis(postedTaxiList);
        return user;
    }

     /**
     * Web service operation
     * Crea un oggetto guidatore
     * @param clientEmail String l'email del cliente associato all'account guidatore
     * @param carModel String identifica il modello di macchina da inserire
     * @param carYear int l'anno della macchina da inserire
     */
    @WebMethod(operationName = "createDriver")
    public void createDriver(@WebParam(name = "clientEmail") String clientEmail, 
            @WebParam(name = "carModel") String carModel, 
            @WebParam(name = "carYear") int carYear) {
        Long client_id = clientRef.getClient(clientEmail).getId();
        driverRef.createDriver(carModel, carYear, client_id);
    }
     
    /**
     * Web service operation
     * restituisce un oggetto guidatore, dato l'id
     * @param id long id del driver
     * @return Driver oggetto driver ricercato
     */
    @WebMethod(operationName = "getDriver")
    public Driver getDriver(@WebParam(name = "ID") long id) {
        return driverRef.getDriver(id);
    }
    
     /**
     * Web service operation
     * restituisce tutti gli oggetti driver associati a un cliente, se ne ha
     * @param clientEmail String l'email dell'utente interessato
     * @return lista List<Driver> una lista contenente tutti gli oggetti Driver associati a quell'utente
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
     * vecchio metodo per avere le informazioni del client/driver di un viaggio
     * vedere getClientsRelatedToTravel
     * @param travelID long l'id del viaggio di cui si vogliono ottenere client e driver
     * @return temp ClientDriverTemp oggetto composto, contenente i valori di client e driver cercati
     */
    @WebMethod(operationName = "getDriverFromTravel")
    public ClientDriverTemp getDriverFromTravel (@WebParam(name = "travelID") long travelID) {
        //List<String> values = null;        
        Client clientInfo = clientRef.getClient(travelRef.getInfoClientEqualDriver(travelID).getEmail());
        Driver driverInfo = driverRef.getDriver(travelRef.getInfoDriverEqualClient(travelID).getDriver_id());
        ClientDriverTemp temp = new ClientDriverTemp();
        temp.setAge(clientInfo.getAge());
        temp.setEmail(clientInfo.getEmail());
        temp.setGender(clientInfo.getGender());
        temp.setName(clientInfo.getName());
        temp.setSurname(clientInfo.getSurname());
        temp.setUrlPhoto(clientInfo.getUrlPhoto());
        temp.setCarModel(driverInfo.getCarModel());
        temp.setCarYear(driverInfo.getCarYear());
        return temp;
    }
    
     /**
     * Web service operation
     * Raccoglie tutti i passeggeri di un viaggio e le informazioni sul guidatore
     * e sulla macchina.
     * @param travelID long l'id del travel di cui si vogliono ottenere le informazioni
     * @return temp TravelDataTemp oggetto composto contenente client, driver, e passeggeri relazionati al viaggio
     */
    @WebMethod(operationName = "getClientsRelatedToTravel")
    public TravelDataTemp getClientsRelatedToTravel (@WebParam(name = "travelID") long travelID) {
        //List<String> values = null;        
        Travel travelObj = travelRef.getTravel(travelID);
        Driver driverInfo = driverRef.getDriver(travelObj.getDriver_id());
        Client clientInfo =  clientRef.getClient(clientRef.getEmail(driverInfo.getClient_id()));
        
        
        TravelDataTemp temp = new TravelDataTemp();
        temp.setDriverCar(driverInfo);
        temp.setDriverInfo(clientInfo);
        
        List<Travel> relatedTravels = travelRef.getRelatedTravels(travelID);
        List<Client> passengerList = new ArrayList();
        long tempId;
        for (Travel item : relatedTravels) {
            tempId = item.getClient_id();
            passengerList.add(clientRef.getClient(clientRef.getEmail(tempId)));        
        }
        temp.setPassengers(passengerList);
        
        
        return temp;
    }
    
    
     /**
     * Web service operation
     * Raccoglie tutti i passeggeri relazionati a un taxi
     * e le informazioni sul creatore
     * @param taxiID long l'id del taxi di cui si vogliono ottenere le informazioni
     * @return temp TravelDataTemp oggetto composto contenente client, driver, e passeggeri relazionati al viaggio
     */
    @WebMethod(operationName = "getClientsRelatedToTaxi")
    public TravelDataTemp getClientsRelatedToTaxi (@WebParam(name = "taxiID") long taxiID) {
        
        TravelDataTemp temp = new TravelDataTemp();
        temp.setDriverCar(null);
        
        Long creatorID = taxiRef.getTaxi(taxiID).getCreator_id();
        Client creator = clientRef.getClient(clientRef.getEmail(creatorID));
        temp.setDriverInfo(creator);
        
        
        List<Taxi> relatedTaxis = taxiRef.getRelatedTaxis(taxiID);
        List<Client> passengerList = new ArrayList();
        long tempId;
        for (Taxi item : relatedTaxis) {
            tempId = item.getClient_id();
            passengerList.add(clientRef.getClient(clientRef.getEmail(tempId)));        
        }        
        temp.setPassengers(passengerList);
        return temp;
    }
    
     /**
     * Web service operation
     * crea un viaggio in macchina
     * @param email String l'email del client creatore
     * @param id long l'id del driver creatore
     * @param from String la città di partenza
     * @param to String la città di destinazione
     * @param when String la data e l'ora di partenza, formattate opportunamente
     * @param freeSeats int il numero di posti liberi.
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
     * crea un viaggio in taxi
     * @param email String l'email dell'utente creatore
     * @param origin String la destinazione di partenza
     * @param destination String la località di arrivo
     * @param freeSeats int il numero di posti liber
     * @param when String la data e l'ora, formattate in stringa, di partenza
     * @param coordStart String le coordinate geografiche della partenza
     * @param coordEnd String le coordinate geografiche dell'arrivo
     */
    @WebMethod(operationName = "createTaxiTravel")
    public void createTaxiTravel(
            @WebParam(name = "email") String email,
            @WebParam(name = "origin") String origin, 
            @WebParam(name = "destination") String destination, 
            @WebParam(name = "freeSeats") int freeSeats, 
            @WebParam(name = "when") String when, 
            @WebParam(name = "coordStart") String coordStart,
            @WebParam(name = "coordEnd") String coordEnd){
        
        Client clientInfo = clientRef.getClient(email);
        Long clientId = clientInfo.getId();
        Long creatorId = clientId;        
        Date data = convertiStringa(when);
        taxiRef.createTaxi(creatorId, clientId, data, data, origin, destination, freeSeats, coordStart, coordEnd);       
    }
    
    
     /**
     * Web service operation  
     * restituisce i viaggi tra due località ricercate
     * @param start String la località di partenza
     * @param end String la località di arrivo
     * @return viaggi List<Travel> una lista contenente i viaggi con quelle start/end
     */
    @WebMethod(operationName = "getTravels")
    public List<Travel> getTravels(@WebParam(name = "start") String start, 
            @WebParam(name = "end") String end) {
        List<Travel> viaggi = travelRef.searchByOriginDestination(start, end);
              
        return viaggi;
    }
    
    /**
     * Web service operation  
     * Restituisce l'history di un cliente, quindi i suoi viaggi passati
     * @param email String l'email che identifica quel cliente
     * @return viaggi List<Travel> la lista dei viaggi che ha compiuto. 
     */
    @WebMethod(operationName = "getHistoryTravels")
    public List<Travel> getHistoryTravels(@WebParam(name = "email") String email) {
        Client reqClient = clientRef.getClient(email);
        Date flagDate = new Date();        
        List<Travel> viaggi = travelRef.getClientTravel(reqClient.getId(), flagDate, flagDate);       
          
        return viaggi;
    }
    
     /**
     * Web service operation
     * restituisce i viaggi a partire da una certa data
     * @param start String la località di partenza
     * @param end String la località di arrivo
     * @param date String la data oltre la quale bisogna cercare
     * @return viaggi List<Travel> una lista contenente i viaggi con quelle start/end
     */
    @WebMethod(operationName = "getTravelsFrom")
    public List<Travel> getTravelsFrom(@WebParam(name = "start") String start, 
            @WebParam(name = "end") String end, @WebParam(name = "date") String date) {
        
        Date data = convertiStringa(date);
        List<Travel> viaggi = travelRef.searchByOriginDestinationDateTime(data, data, start, end);
        return viaggi;   
    }

     /**
     * Web service operation
     * restituisce i viaggi futuri di un dato utente
     * @param email String l'email dell'utente in questione
     * @return viaggi List<Travel> una lista contenente i viaggi in cui l'utente è registrato
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
     * crea un commento 
     * @param author_email String l'email dell'account del creatore del commento
     * @param clientJudged_email String l'email del client su cui viene rilasciato il commento
     * @param travel_id long l'id del travel di riferimento
     * @param comment String il testo del commento
     * @param feedback int un numero da uno a cinque che rappresenta il grado di soddisfazione di chi scriver
     * @param when String la data formattata in stringa di quando il commento viene rilasciato
     */
    @WebMethod(operationName = "createComment")
    public void createComment(@WebParam(name = "author_email") String author_email, 
            @WebParam(name = "clientJudged_email") String clientJudged_email, 
            @WebParam(name = "travel_id") long travel_id, 
            @WebParam(name = "comment") String comment, 
            @WebParam(name = "feedback") int feedback, 
            @WebParam(name = "when") String when) {
        
        Date data = convertiStringa(when);
        long author_id = clientRef.getClient(author_email).getId();
        long clientJudged_id = clientRef.getClient(clientJudged_email).getId();
        commentRef.createComment(author_id, clientJudged_id, travel_id, comment, 
                feedback, data, data);
     
    }

    /**
     * Web service operation
     * Restituisce una lista di commenti associati all'utente che li ha ricevuti
     * Match da fare tra clientId e Id_clientJudged
     * @param clientId long l'id del cliente in questione 
     * @return lista List<Comment> la lista contenente i suddetti commenti
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
     * @param clientId l'id del client in questione
     * @param int il valore del feedback da lui ricevuto
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
     * @param clientEmail String l'email del client di cui si vogliono cercare i commenti
     * @param numMax int numero massimo di commenti che si vogliono scaricare
     * @return commentTemp List<CommentTemp> lista di commenti 
     */
    @WebMethod(operationName = "getLatestReceivedComments")
    public List<CommentTemp> getLatestReceivedComments(@WebParam(name = "clientEmail") String clientEmail, @WebParam(name = "numMax") int numMax) {
        Client cliente = clientRef.getClient(clientEmail);
        List<Comment> commenti = commentRef.getCommentReceived(cliente.getId());
        List<CommentTemp> commentTemp = new ArrayList();
        for (int i=0; i<numMax; i++){
            if (i<commenti.size()){
                CommentTemp temp = new CommentTemp();
                Long tempID = commenti.get(i).getId_author();
                String tempEmail = clientRef.getEmail(tempID);
                Client client = clientRef.getClient(tempEmail);
                client.setPass("");
                temp.setNomeAutore(client.getName());
                temp.setCognomeAutore(client.getSurname());
                temp.setTestoCommento(commenti.get(i).getComment());
                temp.setFeedBackCommento(commenti.get(i).getFeedback());
                commentTemp.add(temp);
            }
        }
        return commentTemp;
    }
        
    /**
     * Web service operation
     * Analogo di getTravels, per i Taxi: restituisce dei taxxi
     * @param from String località di partenza
     * @param to String località di destinazione
     * @reurn List<Taxi> una lista di viaggi tra quelle località
     */
    @WebMethod(operationName = "getTaxiTravels")
    public List<Taxi> getTaxiTravels(@WebParam(name = "from") String from, @WebParam(name = "to") String to) {
        List<Taxi> taxis = taxiRef.searchByOriginDestination(from, to);
        return copiaTaxi(taxis);
    }

    /**
     * Web service operation
     * Analogo di getTravelsFrom per i Taxi
     * @param from String la località di partenza
     * @param to String la località di arrivo
     * @param dateTime String data e ora formattate in stringa 
     * @return List<Taxi> lista di viaggi in taxi tra quelle località, successivi alla data
     */
    @WebMethod(operationName = "getTaxiTravelsFrom")
    public List<Taxi> getTaxiTravelsFrom(@WebParam(name = "from") String from, @WebParam(name = "to") String to, @WebParam(name = "dateTime") String dateTime) {
        Date data = convertiStringa(dateTime);
        List<Taxi> taxis = taxiRef.searchByOriginDestinationDateTime(data, data, from, to);
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
            temp.setCoordStart(taxi.getCoordStart());
            temp.setCoordEnd(taxi.getCoordEnd());
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

    /**
     * Web service operation
     * restituisce un viaggio in macchina a partire dal suo id
     * @param travel_id long l'id del viaggio ricercato
     * @return result Travel il viaggio ricercato
     */
    @WebMethod(operationName = "getSpecificCarTravel")
    public Travel getSpecificCarTravel(@WebParam(name = "travel_id") long travel_id) {
        Travel result = travelRef.getTravel(travel_id);
        
        return result;
    }

    /**
     * Web service operation
     * restituisce un viaggio in taxi a partire dall'id
     * @param taxi_id long l'id del viaggio in taxi ricercato
     * @return result Taxi il taxi ricercato
     */
    @WebMethod(operationName = "getSpecificTaxiTravel")
    public Taxi getSpecificTaxiTravel(@WebParam(name = "taxi_id") long taxi_id) {
        Taxi result = taxiRef.getTaxi(taxi_id);
        return result;
    } 
}
