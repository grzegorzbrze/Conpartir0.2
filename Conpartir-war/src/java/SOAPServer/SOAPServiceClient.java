/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package SOAPServer;

import static java.lang.System.out;
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
import org.conpartir.entity.Driver;
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
    public List<Travel> getTravelsFrom(@WebParam(name = "start") String start, @WebParam(name = "end") String end, @WebParam(name = "date") Date date) {
        List<Travel> result = null;                
        result = travelRef.searchByOriginDestinationDate(date, start, end);   
        
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
        return prova;
    }


    
}
