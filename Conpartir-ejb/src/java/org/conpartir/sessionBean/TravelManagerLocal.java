package org.conpartir.sessionBean;

import java.util.Date;
import java.util.List;
import javax.ejb.Local;
import org.conpartir.entity.Client;
import org.conpartir.entity.Driver;
import org.conpartir.entity.Travel;

/**
 * Interfaccia locale <i>TravelManagerLocal</i> che espone tutti metodi che
 * possono essere applicati al Session Bean.
 *
 * @author Conpartir Group
 * @version 0.3 21 settembre 2016
 * @see <a href="http://docs.oracle.com/javaee/6/tutorial/doc/gipjg.html">
 * Session Bean </a>
 */
@Local
public interface TravelManagerLocal {

    /**
     * Permette di creare una tupla nel database tramite un oggetto di tipo
     * Travel
     *
     * @param travel da memorizzare nel database.
     */
    public void createTravel(Travel travel);

    /**
     * Permette di creare una tupla nel database tramite i valori esplici
     *
     * @param driver_id identificativo univoco del Driver del viaggio.
     * @param client_id identificativo univoco del client del viaggio.
     * @param origin valore dell'attributo orgin del viaggio.
     * @param destination valore dell'attributo destination del viaggio.
     * @param data valore dell'attributo data del viaggio.
     * @param time valore dell'attributo time del viaggio.
     * @param freeSeats valore dll'attributo freeSeats.
     */
    public void createTravel(Long driver_id, Long client_id, String origin,
            String destination, Date data, Date time, int freeSeats);

    /**
     * Restituisce l'identificativo univoco del Travel.
     *
     * @param driver_id identificativo univoco del Driver del viaggio.
     * @param client_id identificativo univoco del client del viaggio.
     * @param data valore dell'attributo data del viaggio.
     * @param time valore dell'attributo time del viaggio.
     * @param origine valore dell'attributo orgin del viaggio.
     * @param destination valore dell'attributo destination del viaggio.
     * @return Long identificativo univoco del Travel cercato.
     */
    public Long getTravel_ID(Long driver_id, Long client_id, Date data, Date time,
            String origine, String destination);

    /**
     * Il metodo ricerca e restituisce tutti i viaggi di un certo utente a
     * partire da data e tempo
     *
     * @param client_id identificativo univoco del client del viaggio.
     * @param data valore dell'attributo data del viaggio.
     * @param time valore dell'attributo time del viaggio.
     * @return List di Travel che rispettano i criteri di ricerca.
     */
    public List<Travel> getClientTravel(Long client_id, Date data, Date time);

    /**
     * Il metodo ricerca e restituisce tutti i viaggi di un certo utente prima
     * di una certa data e orario.
     *
     * @param client_id identificativo univoco del client del viaggio.
     * @param data valore dell'attributo data del viaggio.
     * @param time valore dell'attributo time del viaggio.
     * @return List di Travel che rispettano i criteri di ricerca.
     */
    public List<Travel> getClientTravelBefore(Long client_id, Date data, Date time);

    /**
     * Il metodo ricerca e restituisce tutti i viaggi di un certo utente dopo
     * una certa data e orario.
     *
     * @param client_id identificativo univoco del client del viaggio.
     * @param data valore dell'attributo data del viaggio.
     * @param time valore dell'attributo time del viaggio.
     * @return List di Travel che rispettano i criteri di ricerca.
     */
    public List<Travel> getClientTravelAfter(Long client_id, Date data, Date time);

    /**
     * Ricerca e restituisce i vaggi per destinazione e punto di partenza.
     *
     * @param origin punto di partenza del viaggio.
     * @param destination punto di arrivo.
     * @return List di Travel che rispettano i criteri di ricerca.
     */
    public List<Travel> searchByOriginDestination(String origin, String destination);

    /**
     * Ricerca e restituisce i vaggi per destinazione, punto di partenza e data
     * di partenza.
     *
     * @param data la data di partenza.
     * @param origin punto di partenza del viaggio.
     * @param destination punto di arrivo.
     * @return List di Travel che rispettano i criteri di ricerca.
     */
    public List<Travel> searchByOriginDestinationDate(Date data, String origin, String destination);

    /**
     * Ricerca e restituisce i vaggi per destinazione, punto di partenza, data
     * di partenza e orario.
     *
     * @param time l'orario di partenza.
     * @param data la data di partenza.
     * @param origin punto di partenza del viaggio.
     * @param destination punto di arrivo.
     * @return List di Travel che rispettano i criteri di ricerca.
     */
    public List<Travel> searchByOriginDestinationDateTime(Date data, Date time, String origin, String destination);

    /**
     * Restituisce l'ogetto Travel che ha l'identificativo univoco pari a quello
     * passato come argomento.
     *
     * @param travelID identificativo univoco del viaggio.
     * @return Travel oggetto cercato.
     */
    public Travel getTravel(Long travelID);

    /**
     * Il metodo restituisce i travels relazionati con quello inserito cercando
     * quelli con gli stessi dati, ma clients diversi
     *
     * @param travelID identificativo univoco del Travel.
     * @return List di Travel che rispettano i criteri di ricerca.
     */
    public List<Travel> getRelatedTravels(Long travelID);

    /**
     * Restituisce, se esiste, il proprietario della macchina del viaggio che ha
     * l'identificativo univoco uguale a quello passato come parametro.
     *
     * @param travel_id identificativo univoco del Travel.
     * @return Client cercato.
     */
    public Client getInfoClientEqualDriver(Long travel_id);

    /**
     * Restituisce, se esiste, la macchina del viaggio che ha l'identificativo
     * univoco uguale a quello passato come parametro.
     *
     * @param travel_id identificativo univoco del Travel.
     * @return Driver cercato.
     */
    public Driver getInfoDriverEqualClient(Long travel_id);

    /**
     * Aggiungere un passeggero al viaggio se non ne fa già parte e se ci sono
     * ancora i posti disponibili.
     *
     * @param travel_id identificativo univoco del Travel.
     * @param passengerID identificativo univoco del cliente.
     * @return boolean vale True se l'aggiunta è andata a buon fine altrimenti
     * False.
     */
    public boolean addPassenger(Long travel_id, Long passengerID);
}
