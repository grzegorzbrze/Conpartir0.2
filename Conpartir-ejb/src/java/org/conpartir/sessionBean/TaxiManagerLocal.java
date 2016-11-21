package org.conpartir.sessionBean;

import java.util.Date;
import java.util.List;
import javax.ejb.Local;
import org.conpartir.entity.Taxi;

/**
 * Interfaccia locale <i>TaxiManagerLocal</i> che espone tutti metodi che
 * possono essere applicati al Session Bean.
 *
 * @author Conpartir Group
 * @version 0.3 21 settembre 2016
 * @see <a href="http://docs.oracle.com/javaee/6/tutorial/doc/gipjg.html">
 * Session Bean </a>
 */
@Local
public interface TaxiManagerLocal {

    /**
     * Crea una tupla nel database tramite un oggetto di tipo driver
     *
     * @param taxi da memorizzare nel database.
     */
    public void createTaxi(Taxi taxi);

    /**
     * Crea una tupla nel database tramite gli argomenti esplici.
     *
     * @param creator_id identificativo univoco del creato del Taxi.
     * @param id_client identificativo univoco del cliente.
     * @param data valore dell'attributo data del Entity Taxi.
     * @param time valore dell'attributo time del Entity Taxi.
     * @param origin valore dell'attributo origin del Entity Taxi.
     * @param destination valore dell'attributo destination del Entity Taxi.
     * @param freeSeat valore dell'attributo freeSeat del Entity Taxi.
     * @param coordStart valore dell'attributo coordStart del Entity Taxi.
     * @param coordEnd valore dell'attributo coordEnd del Entity Taxi.
     */
    public void createTaxi(Long creator_id, Long id_client, Date data, Date time, String origin, String destination, int freeSeat, String coordStart, String coordEnd);

    /**
     * Aggiunge un nuovo passeggero al viaggio in Taxi.
     *
     * @param taxi_id identificativo univoco dell'Entity Taxi.
     * @param id_passenger identificativo univoco del nuovo passeggero.
     * @return boolean vale True se l'aggiunta è andata a buon fine altrimenti
     * vale False.
     */
    public boolean addPassenger(Long taxi_id, Long id_passenger);

    /**
     * Restituisce la lista di Taxi che il cliente ha prenotato.
     *
     * @param id_passenger identificativo univoco del passeggero.
     * @return List di Taxi che il cliente ha prenotato.
     */
    public List<Taxi> getTaxisReserved(Long id_passenger);

    /**
     * Resistuisce la lista dei taxi creati dal cliente.
     *
     * @param id_client identificativo univoco del cliente.
     * @return List di Taxi che il cliente ha creato.
     */
    public List<Taxi> getTaxiCreated(Long id_client);

    /**
     * Il metodo risponde alla ricerca più generale per destinazione e punto di
     * partenza e restituisce una lista di taxi possibili.
     *
     * @param origin la partenza del viaggio.
     * @param destination la destinazione del viaggio.
     * @return List di Taxi che rispettano i criteri di ricerca.
     */
    public List<Taxi> searchByOriginDestination(String origin, String destination);

    /**
     * Il metodo risponde alla ricerca per destinazione, data e punto di
     * partenza e restituisce una lista di taxi possibili.
     *
     * @param data la data delle partenza.
     * @param origin la partenza del viaggio.
     * @param destination la destinazione del viaggio.
     * @return List di Taxi che rispettano i criteri di ricerca.
     */
    public List<Taxi> searchByOriginDestinationDate(Date data, String origin, String destination);

    /**
     * Il metodo risponde alla ricerca per destinazione, data, ora e punto di
     * partenza e restituisce una lista di taxi possibili.
     *
     * @param data la data delle partenza.
     * @param time l'orario di partenza.
     * @param origin la partenza del viaggio.
     * @param destination la destinazione del viaggio.
     * @return List di Taxi che rispettano i criteri di ricerca.
     */
    public List<Taxi> searchByOriginDestinationDateTime(Date data, Date time, String origin, String destination);

    /**
     * Restituisce l'oggetto Taxi che ha l'identificativo univoco uguale a
     * quello passato come parametro.
     *
     * @param taxi_id identificativo univoco del Taxi.
     * @return Taxi oggetto cercato se esiste.
     */
    public Taxi getTaxi(Long taxi_id);

    /**
     * Restituisce i taxi relazionati con quello inserito cercando quelli con
     * gli stessi dati, ma clienti diversi.
     *
     * @param taxiID identificativo univoco del Taxi.
     * @return List di Taxi che rispettano i criteri di ricerca.
     */
    public List<Taxi> getRelatedTaxis(Long taxiID);

}
