package org.conpartir.sessionBean;

import javax.ejb.Local;
import org.conpartir.entity.Client;

/**
 * Interfaccia locale <i>ClientManagerLocal</i> che espone tutti metodi che
 * possono essere applicati al Session Bean.
 *
 * @author Conpartir Group
 * @version 0.3 21 settembre 2016
 * @see <a href="http://docs.oracle.com/javaee/6/tutorial/doc/gipjg.html">
 * Session Bean </a>
 */
@Local
public interface ClientManagerLocal {

    /**
     * Permette di creare una tupla nel database tramite un oggetto di tipo
     * client
     *
     * @param client da memorizzare nel database.
     */
    public void createClient(Client client);

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
    public void createClient(String name, String surname, char gender, int age,
            String email, String pass);

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
    public void editClient(String email, String name, String surname, char gender, int age,
            String pass, String urlPhoto);

    /**
     * Il metodo verifica se la stringa email è presente nel database come email
     * principale o come Gmail o come Twitter.
     *
     * @param email da verificare nel database.
     * @return boolean che ha valore True se l'email è presente altrimenti è
     * False.
     */
    public boolean isEmail(String email);

    /**
     * Restituisce la stringa l'email, se esiste, del client ricercata per ID.
     *
     * @param id del cliente di cui si cerca l'email.
     * @return String email del cliente cercato.
     */
    public String getEmail(long id);

    /**
     * Restituisce l'oggetto di tipo Client, se esiste, ricercato per email.
     *
     * @param email del cliente di cui si cerca il proprietario.
     * @return Client oggetto cercato.
     */
    public Client getClient(String email);

    /**
     * Restituisce l'oggetto di tipo client, se esiste, ricercato per Gmail.
     *
     * @param gmailValue del cliente di cui si cerca il proprietario.
     * @return Client oggetto cercato.
     */
    public Client getClientByGmail(String gmailValue);

    /**
     * Restituisce l'oggetto di tipo client, se esiste, ricercato per Twitter
     *
     * @param twitter del cliente di cui si cerca il proprietario.
     * @return Client oggetto cercato.
     */
    public Client getClientByTwitter(String twitter);

    /**
     * Setta il valore dei riferimenti secondari dell'utente.
     *
     * @param email valore dell'attributo email del Entity Client.
     * @param gmailValue valore dell'attributo Gmail del Entity Client.
     * @param twitterValue valore dell'attributo Twitter del Entity Client.
     * @return boolean se la modifica richiesta è andata a buon fine.
     */
    public boolean setOtherEmail(String email, String gmailValue, String twitterValue);

}
