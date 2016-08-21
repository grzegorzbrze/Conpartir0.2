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
            clientFacade.create(client);             
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
            nuovo.setGmail(true);
            clientFacade.create(nuovo); 
            
        } 
    }
    
    @Override
      public void editClient(String email, String name, String surname, char gender, int age, 
             String pass, String urlPhoto, String gmail) {
          Client editable = this.getClient(email);
          
          if(!name.equals("undefined") && !name.equals(" ")) editable.setName(name);
          if(!surname.equals("undefined") && !surname.equals(" ")) editable.setSurname(surname);
          if(gender!='u') editable.setGender(gender);
          if(age!=0) editable.setAge(age);
          if(!pass.equals("undefined") && !pass.equals(" ")) editable.setPass(pass);
          if(!urlPhoto.equals("undefined") && !urlPhoto.equals(" ")) editable.setUrlPhoto(urlPhoto);
          if(gmail.equals("true")) editable.setGmail(true);
          if(gmail.equals("false")) editable.setGmail(false);
          
          //System.out.println(editable);
         // clientFacade.edit(editable);
          
         
      
      };    
    
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
    @Override
    public Boolean isGmail(String email) {
        boolean result = false;
        Client client = new Client();
       
        if (isEmail(email)==true) {
             List<Client> list = clientFacade.findAll();
             for (Client temp : list){
                 String tempEmail = temp.getEmail();
                 if (tempEmail.equals(email)){
                     client = temp;            
                 }
             }
        }
        
        if (client.getGmail()==true) result = true;
    
    return result;
    }
    
    @Override
    public void setClientGmail(String email, boolean value) {        
        Client client = new Client();
       
        if (isEmail(email)==true) {
             List<Client> list = clientFacade.findAll();
             for (Client temp : list){
                 String tempEmail = temp.getEmail();
                 if (tempEmail.equals(email)){
                     client = temp;            
                 }
             }
        }
        client.setGmail(value);   
     
    };
}
