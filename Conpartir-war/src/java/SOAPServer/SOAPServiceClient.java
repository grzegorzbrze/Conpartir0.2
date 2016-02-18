/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package SOAPServer;

import java.util.List;
import javax.ejb.EJB;
import javax.jws.Oneway;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;
import javax.xml.ws.RequestWrapper;
import org.conpartir.entity.Client;
import org.conpartir.sessionBean.ClientManagerLocal;
import org.conpartir.sessionBean.DriverManagerLocal;
import org.conpartir.sessionBean.PostManagerLocal;

/**
 *
 * @author Matteo
 */
@WebService(serviceName = "SOAPServiceClient")
public class SOAPServiceClient {
        
    @EJB
    private ClientManagerLocal clientRef;
    @EJB
    private PostManagerLocal postRef;
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
    @WebMethod(operationName = "createDriver")
    @Oneway
    public void createDriver(@WebParam(name = "ID") Long ID) {
        driverRef.createDriver(ID);
    }

    /**
     * Web service operation
     */
    @WebMethod(operationName = "getDriver")
    public String getDriver(@WebParam(name = "ID") Long ID) {
        String risposta;
        risposta = driverRef.getDriver(ID);
        return risposta;
    }

    /**
     * Web service operation
     */
    @WebMethod(operationName = "createPost")
    @Oneway
    public void createPost(@WebParam(name = "Param") String Param) {
    }
    
    
}
