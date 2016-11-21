package org.conpartir.sessionBean;

import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import org.conpartir.entity.Client;
import org.conpartir.facade.ClientFacadeLocal;

/**
 * Implementazione del SessionBean Stateless <i>ClientManager</i> per la
 * gestione che riguarda il Client. Implemententa tutti i metodi esposti
 * nell'interfaccia ClientManagerLocal.
 *
 * @author Conpartir Group
 * @version 0.3 21 settembre 2016
 * @see <a href="http://docs.oracle.com/javaee/6/tutorial/doc/gipjg.html">
 * Session Bean </a>
 */
@Stateless
public class ClientManager implements ClientManagerLocal {

    /**
     * EntityManager che rende persistenti le modiche del SessionBean.
     */
    @EJB
    private ClientFacadeLocal clientFacade;

    /**
     * Crea una tupla nel database tramite un oggetto di tipo client
     *
     * @param client da memorizzare nel database.
     */
    @Override
    public void createClient(Client client) {
        String genere = Character.toString(client.getGender());
        if (client.getName() != null && client.getSurname() != null
                && genere != null && client.getAge() != 0 && client.getEmail() != null
                && client.getPass() != null) {
            clientFacade.create(client);
        }
    }

    /**
     * Crea una tupla nel database tramite gli argomenti esplici.
     *
     * @param name valore dell'attributo nome del Entity Client.
     * @param surname valore dell'attributo surname del Entity Client.
     * @param gender valore dell'attributo sesso del Entity Client.
     * @param age valore dell'attributo età del Entity Client.
     * @param email valore dell'attributo email del Entity Client.
     * @param pass valore dell'attributo password del Entity Client.
     */
    @Override
    public void createClient(String name, String surname, char gender, int age,
            String email, String pass) {
        String genere = Character.toString(gender);
        if (name != null && surname != null && genere != null && age != 0
                && email != null && pass != null) {
            Client nuovo = new Client();
            nuovo.setAge(age);
            nuovo.setEmail(email);
            nuovo.setGender(gender);
            nuovo.setName(name);
            nuovo.setSurname(surname);
            nuovo.setPass(pass);
            clientFacade.create(nuovo);
        }
    }

    /**
     * Modifica una tupla nel database con gli argomenti esplici.
     *
     * @param email valore dell'attributo email del Entity Client.
     * @param name valore dell'attributo nome del Entity Client.
     * @param surname valore dell'attributo surname del Entity Client.
     * @param gender valore dell'attributo sesso del Entity Client.
     * @param age valore dell'attributo età del Entity Client.
     * @param pass valore dell'attributo password del Entity Client.
     * @param urlPhoto valore dell'attributo pathPhoto del Entity Client.
     */
    @Override
    public void editClient(String email, String name, String surname, char gender, int age,
            String pass, String urlPhoto) {
        Client editable = this.getClient(email);
        if (name != null) {
            editable.setName(name);
        }
        if (surname != null) {
            editable.setSurname(surname);
        }
        if (gender != ' ') {
            editable.setGender(gender);
        }
        if (age != 0) {
            editable.setAge(age);
        }
        if (pass != null) {
            editable.setPass(pass);
        }
        if (urlPhoto != null) {
            editable.setUrlPhoto(urlPhoto);
        }
    }

    /**
     * Il metodo verifica se la stringa email è presente nel database come email
     * principale o come Gmail o come Twitter.
     *
     * @param email da verificare nel database.
     * @return boolean che ha valore True se l'email è presente altrimenti è
     * False.
     */
    @Override
    public boolean isEmail(String email) {
        boolean risultato = false;
        List<Client> list = clientFacade.findAll();
        for (Client temp : list) {
            String tempEmail = temp.getEmail();
            String tempGmail = temp.getGmailValue();
            String tempTwitter = temp.getTwitterValue();
            if (tempGmail != null && tempGmail.equals(email)) {
                risultato = true;
                break;
            }
            if (tempTwitter != null && tempTwitter.equals(email)) {
                risultato = true;
                break;
            }
            if (tempEmail.equals(email)) {
                risultato = true;
                break;
            }
        }
        return risultato;
    }

    /**
     * Restituisce la stringa l'email, se esiste, del client ricercata per ID.
     *
     * @param id del cliente di cui si cerca l'email.
     * @return String email del cliente cercato.
     */
    @Override
    public String getEmail(long id) {
        String risultato = "";
        List<Client> list = clientFacade.findAll();
        for (Client temp : list) {
            Long tempID = temp.getId();
            if (tempID.equals(id)) {
                risultato = temp.getEmail();
            }
        }
        return risultato;
    }

    /**
     * Restituisce l'oggetto di tipo Client, se esiste, ricercato per email.
     *
     * @param email del cliente di cui si cerca il proprietario.
     * @return Client oggetto cercato.
     */
    @Override
    public Client getClient(String email) {
        Client client = new Client();
        List<Client> list = clientFacade.findAll();
        for (Client temp : list) {
            String tempEmail = temp.getEmail();
            if (tempEmail.equals(email)) {
                client = temp;
            }
        }
        return client;
    }

    /**
     * Restituisce l'oggetto di tipo client, se esiste, ricercato per Gmail.
     *
     * @param gmailValue del cliente di cui si cerca il proprietario.
     * @return Client oggetto cercato.
     */
    @Override
    public Client getClientByGmail(String gmailValue) {
        Client client = new Client();
        List<Client> list = clientFacade.findAll();
        for (Client temp : list) {
            String tempGmail = temp.getGmailValue();
            if (tempGmail != null && tempGmail.equals(gmailValue)) {
                client = temp;

            }
        }
        return client;
    }

    /**
     * Restituisce l'oggetto di tipo client, se esiste, ricercato per Twitter
     *
     * @param twitter del cliente di cui si cerca il proprietario.
     * @return Client oggetto cercato.
     */
    @Override
    public Client getClientByTwitter(String twitter) {
        Client client = new Client();
        List<Client> list = clientFacade.findAll();
        for (Client temp : list) {
            String tempTwitt = temp.getTwitterValue();
            if (twitter.equals(tempTwitt)) {
                client = temp;
            }
        }
        return client;
    }

    /**
     * Setta il valore dei riferimenti secondari dell'utente.
     *
     * @param email valore dell'attributo email del Entity Client.
     * @param gmailValue valore dell'attributo Gmail del Entity Client.
     * @param twitterValue valore dell'attributo Twitter del Entity Client.
     * @return boolean se la modifica richiesta è andata a buon fine.
     */
    @Override
    public boolean setOtherEmail(String email, String gmailValue, String twitterValue) {
        Client owner = this.getClient(email);
        boolean ris = false;
        if (gmailValue != null || gmailValue == "") {
            /*  il seguente if verifica se il gmailValue è stato usato in una 
                qualunque email, gmail o twitter. Se non esiste nel DB allora
                può essere direttamente modificato il campo gmailValue nel proprietario.
             */
            if (this.isEmail(gmailValue)) {
                /*  il seguente if verifca se il proprietario che richiede la modifica
                    ha lo stesso email e gmail o lo stesso twitter e gmail. 
                    Il tal caso il campo gmailValue del proprietario viene direttamente modificato.
                 */
                if (owner.getTwitterValue() != null) {
                    if (owner.getEmail().equals(gmailValue) || owner.getTwitterValue().equals(gmailValue)) {
                        if (gmailValue == "") {
                            owner.setGmailValue(null);
                        } else {
                            owner.setGmailValue(gmailValue);
                        }
                        ris = true;
                    }
                } else if (owner.getEmail().equals(gmailValue)) {
                    if (gmailValue == "") {
                        owner.setGmailValue(null);
                    } else {
                        owner.setGmailValue(gmailValue);
                    }
                    ris = true;
                }
            } else {
                if (gmailValue == "") {
                    owner.setGmailValue(null);
                } else {
                    owner.setGmailValue(gmailValue);
                }
                ris = true;
            }
        }
        if (twitterValue != null || twitterValue == "") {
            /*  il seguente if verifica se il twitterValue è stato usato in una 
                qualunque email, gmail o twitter. Se non esiste nel DB allora
                può essere direttamente modificato il campo twitterValue nel proprietario.
             */
            if (this.isEmail(twitterValue)) {
                /*  il seguente if verifca se il proprietario che richiede la modifica
                    ha lo stesso email e twitter o lo stesso gmail e twitter. 
                    Il tal caso il campo twitterValue del proprietario viene direttamente modificato.
                 */
                if (owner.getGmailValue() != null) {
                    if (owner.getEmail().equals(twitterValue) || owner.getGmailValue().equals(twitterValue)) {
                        if (twitterValue == "") {
                            owner.setTwitterValue(null);
                        } else {
                            owner.setTwitterValue(twitterValue);
                        }
                        ris = true;
                    }
                } else if (owner.getEmail().equals(twitterValue)) {
                    if (twitterValue == "") {
                        owner.setTwitterValue(null);
                    } else {
                        owner.setTwitterValue(twitterValue);
                    }
                    ris = true;
                }

            } else {
                if (twitterValue == "") {
                    owner.setTwitterValue(null);
                } else {
                    owner.setTwitterValue(twitterValue);
                }
                ris = true;
            }
        }
        return ris;
    }

}
