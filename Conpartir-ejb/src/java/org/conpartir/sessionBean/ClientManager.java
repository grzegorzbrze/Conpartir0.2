/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.conpartir.sessionBean;

import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import org.conpartir.entity.Client;
import org.conpartir.entity.Driver;
import org.conpartir.entity.Taxi;
import org.conpartir.facade.ClientFacadeLocal;

/**
 *
 * @author Blu Light
 */
@Stateless
public class ClientManager implements ClientManagerLocal {
    @EJB
    private ClientFacadeLocal clientFacade;    
    
    @Override
    public void createClient(Client client) {
       clientFacade.create(client);
    }

    @Override
    public void createClient(String name, String surname, char gender, int age, String email, String pass, String urlPhoto) {
        Client nuovo  = new Client();
        nuovo.setAge(age);
        nuovo.setEmail(email);
        nuovo.setGender(gender);
        nuovo.setName(name);
        nuovo.setSurname(surname);
        nuovo.setPass(pass);
        nuovo.setUrlPhoto(urlPhoto);
        clientFacade.create(nuovo);  
    }

    @Override
    public void createClient(String name, String surname, char gender, int age, String email, String pass, String urlPhoto, List<Driver> drivers) {
       Client nuovo  = new Client();
        nuovo.setAge(age);
        nuovo.setEmail(email);
        nuovo.setGender(gender);
        nuovo.setName(name);
        nuovo.setSurname(surname);
        nuovo.setPass(pass);
        nuovo.setUrlPhoto(urlPhoto);
        nuovo.setDrivers(drivers);
        clientFacade.create(nuovo);  
    }
    
    @Override
    public boolean isEmail(String email) {
        boolean risultato = false;
        List<Client> list = clientFacade.findAll();
        for (Client temp : list){
            String tempEmail = temp.getEmail();
            if (tempEmail.equals(email)){
                risultato = true;
            }
        }
        return risultato;
    }

    @Override
    public String getEmail(long id) {
        String risultato = "";
        List<Client> list = clientFacade.findAll();
        for (Client temp : list){
            Long tempID = temp.getId();
            if (tempID.equals(id)){
                risultato = temp.getEmail();
            }
        }
        return risultato;        
    }
    
    @Override
    public Client getClient(String email) {
        Client client = new Client();
        List<Client> list = clientFacade.findAll();
        for (Client temp : list){
            String tempEmail = temp.getEmail();
            if (tempEmail.equals(email)){
                client = temp;            
            }
        }
        return client;
    }

}
