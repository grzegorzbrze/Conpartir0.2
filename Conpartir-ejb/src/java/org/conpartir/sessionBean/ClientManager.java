/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.conpartir.sessionBean;

import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import org.conpartir.entity.Client;
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
        
      
        String genere = Character.toString(client.getGender()); 
        
        if (client.getName() != null && client.getSurname() != null && 
                genere != null && client.getAge() != 0 && client.getEmail() != null && 
                client.getPass() != null){ 
            clientFacade.create(client);   System.out.println("SONO QUI");
            
           
        }
           
       
    }

    @Override
    public void createClient(String name, String surname, char gender, int age,
            String email, String pass, String urlPhoto) {
        String genere = Character.toString(gender);
        if (name != null && surname != null && genere != null && age != 0 &&
                email != null && pass != null && urlPhoto != null ){
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
