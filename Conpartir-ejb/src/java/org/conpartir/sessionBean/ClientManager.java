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
            String email, String pass) {
        String genere = Character.toString(gender);
        if (name != null && surname != null && genere != null && age != 0 &&
                email != null && pass != null){
            Client nuovo  = new Client();
            nuovo.setAge(age);
            nuovo.setEmail(email);
            nuovo.setGender(gender);
            nuovo.setName(name);
            nuovo.setSurname(surname);
            nuovo.setPass(pass);
            clientFacade.create(nuovo);    
        } 
    }
    
    @Override
      public void editClient(String email, String name, String surname, char gender, int age, 
             String pass, String urlPhoto) {
          Client editable = this.getClient(email);
          if(name != null) editable.setName(name);
          if(surname != null) editable.setSurname(surname);
          if(gender != ' ') editable.setGender(gender);
          if(age != 0) editable.setAge(age);
          if(pass != null) editable.setPass(pass);
          if(urlPhoto != null) editable.setUrlPhoto(urlPhoto);           
      };    
    
    
    @Override
    public boolean isEmail(String email) {
        boolean risultato = false;
        List<Client> list = clientFacade.findAll();
        for (Client temp : list){
            String tempEmail = temp.getEmail();
            String tempGmail = temp.getGmailValue();
            String tempTwitter = temp.getTwitterValue();
            if (tempGmail != null && tempGmail.equals(email) ){
                risultato = true;  
                break;
            }
            if (tempTwitter != null && tempTwitter.equals(email)){
                risultato = true; 
                break;
            }
            if (tempEmail.equals(email)){
                risultato = true;
                break;
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
    public Client getClientByGmail(String gmailValue) {
        Client client = new Client();
        List<Client> list = clientFacade.findAll();
        for (Client temp : list){
            String tempGmail = temp.getGmailValue();
            if (tempGmail.equals(gmailValue)){
                client = temp; 
                            
            }
        }
       return client;
    }
    
    @Override
    public Client getClientByTwitter(String twitter) {
        Client client = new Client();
        List<Client> list = clientFacade.findAll();
        for (Client temp : list){
            if (temp.getTwitterValue().equals(twitter)){
                client = temp;                          
            }
        }
       return client;
    }
        
    @Override
    public boolean setOtherEmail(String email, String gmailValue, String twitterValue) {
        Client owner = this.getClient(email);
        boolean ris = false;
        if (gmailValue != null || gmailValue == ""){
            /*  il seguente if verifica se il gmailValue è stato usato in una 
                qualunque email, gmail o twitter. Se non esiste nel DB allora
                può essere direttamente modificato il campo gmailValue nel proprietario.
            */
            if (this.isEmail(gmailValue)){
                /*  il seguente if verifca se il proprietario che richiede la modifica
                    ha lo stesso email e gmail o lo stesso twitter e gmail. 
                    Il tal caso il campo gmailValue del proprietario viene direttamente modificato.
                */
                if (owner.getTwitterValue() != null){
                    if (owner.getEmail().equals(gmailValue) || owner.getTwitterValue().equals(gmailValue)){
                        if(gmailValue == "") owner.setGmailValue(null);
                        else owner.setGmailValue(gmailValue);
                        ris = true;
                    }
                }
                else{
                    if (owner.getEmail().equals(gmailValue)){
                        if(gmailValue == "") owner.setGmailValue(null);
                        else owner.setGmailValue(gmailValue);
                        ris = true;
                    }
                }
            }
            else{
                if(gmailValue == "") owner.setGmailValue(null);
                else owner.setGmailValue(gmailValue);
                ris = true;
            }       
        }
        if (twitterValue != null || twitterValue == ""){
            /*  il seguente if verifica se il twitterValue è stato usato in una 
                qualunque email, gmail o twitter. Se non esiste nel DB allora
                può essere direttamente modificato il campo twitterValue nel proprietario.
            */
            if (this.isEmail(twitterValue)){
                /*  il seguente if verifca se il proprietario che richiede la modifica
                    ha lo stesso email e twitter o lo stesso gmail e twitter. 
                    Il tal caso il campo twitterValue del proprietario viene direttamente modificato.
                */
                if (owner.getGmailValue() != null){
                    if (owner.getEmail().equals(twitterValue) || owner.getGmailValue().equals(twitterValue)){
                        if(twitterValue=="") owner.setTwitterValue(null);
                        else owner.setTwitterValue(twitterValue);
                        ris = true;
                    }
                }
                else{
                    if (owner.getEmail().equals(twitterValue)){
                        if(twitterValue=="") owner.setTwitterValue(null);
                        else owner.setTwitterValue(twitterValue);
                        ris = true;
                    }
                }
                    
            }
            else{
                if(twitterValue=="") owner.setTwitterValue(null);
                else owner.setTwitterValue(twitterValue);
                ris = true;
            }
        }
        return ris;
    }
    
}
