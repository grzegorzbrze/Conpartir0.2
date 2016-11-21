package org.conpartir.sessionBean;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import org.conpartir.entity.Taxi;
import org.conpartir.facade.TaxiFacadeLocal;

/**
 * Implementazione del SessionBean Stateless <i>TaxiManager</i> per la gestione
 * che riguarda il Taxi. Implemententa tutti i metodi esposti nell'interfaccia
 * TaxiManagerLocal.
 *
 * @author Conpartir Group
 * @version 0.3 21 settembre 2016
 * @see <a href="http://docs.oracle.com/javaee/6/tutorial/doc/gipjg.html">
 * Session Bean </a>
 */
@Stateless
public class TaxiManager implements TaxiManagerLocal {

    /**
     * EntityManager che rende persistenti le modiche del SessionBean.
     */
    @EJB
    private TaxiFacadeLocal taxiFacade;

    /**
     * Crea una tupla nel database tramite un oggetto di tipo driver
     *
     * @param taxi da memorizzare nel database.
     */
    @Override
    public void createTaxi(Taxi taxi) {
        if (taxi.getCreator_id() != null && taxi.getClient_id() != null
                && taxi.getData().toString() != null && taxi.getTime() != null
                && taxi.getOrigin() != null && taxi.getDestination() != null
                && taxi.getFreeSeats() != 0) {

            taxi.setDestination(taxi.getDestination().toLowerCase());
            taxi.setOrigin(taxi.getOrigin().toLowerCase());
            taxiFacade.create(taxi);
        }
    }

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
    @Override
    public void createTaxi(Long creator_id, Long id_client, Date data, Date time,
            String origin, String destination, int freeSeat, String coordStart, String coordEnd) {
        if (creator_id != null && id_client != null && data.toString() != null
                && time.toString() != null && origin != null && destination != null
                && freeSeat != 0 && coordStart != null && coordEnd != null) {
            Taxi taxi = new Taxi();
            taxi.setCreator_id(creator_id);
            taxi.setClient_id(id_client);
            taxi.setData(data);
            taxi.setTime(time);
            taxi.setOrigin(origin.toLowerCase());
            taxi.setDestination(destination.toLowerCase());
            taxi.setFreeSeats(freeSeat);
            taxi.setCoordStart(coordStart);
            taxi.setCoordEnd(coordEnd);
            taxiFacade.create(taxi);
        }
    }

    /**
     * Aggiunge un nuovo passeggero al viaggio in Taxi.
     *
     * @param taxi_id identificativo univoco dell'Entity Taxi.
     * @param passengerID identificativo univoco del nuovo passeggero.
     * @return boolean vale True se l'aggiunta è andata a buon fine altrimenti
     * vale False.
     */
    @Override
    public boolean addPassenger(Long taxi_id, Long passengerID) {
        boolean risultato = true;
        Taxi tax = getTaxi(taxi_id);
        if (tax.getTaxi_id() != null) {
            List<Taxi> prenotati = this.searchByOriginDestinationDateTime(tax.getData(), tax.getTime(), tax.getOrigin(), tax.getDestination());
            for (Taxi prenotato : prenotati) {
                if (prenotato.getCreator_id().equals(tax.getCreator_id())
                        && prenotato.getClient_id().equals(passengerID)) {
                    risultato = false;
                }
            }
        }
        if (risultato == true) {
            List<Taxi> taxis = taxiFacade.findAll();
            for (Taxi temp : taxis) {
                if (temp.getTaxi_id().equals(taxi_id)) {
                    int postiRimasti = temp.getFreeSeats() - 1;
                    if (postiRimasti >= 0) {
                        createTaxi(temp.getCreator_id(), passengerID, temp.getData(),
                                temp.getTime(), temp.getOrigin(), temp.getDestination(),
                                postiRimasti, temp.getCoordStart(), temp.getCoordEnd());
                        temp.setFreeSeats(postiRimasti);
                    } else {
                        risultato = false;
                    }
                }
            }
        }
        return risultato;
    }

    /**
     * Restituisce la lista di Taxi che il cliente ha prenotato.
     *
     * @param id_passenger identificativo univoco del passeggero.
     * @return List di Taxi che il cliente ha prenotato.
     */
    @Override
    public List<Taxi> getTaxisReserved(Long id_passenger) {
        List<Taxi> taxis = new ArrayList();
        List<Taxi> list = taxiFacade.findAll();
        if (!list.isEmpty()) {
            for (Taxi temp : list) {
                if (temp.getClient_id().equals(id_passenger)) {
                    taxis.add(temp);
                }
            }
        }
        return taxis;
    }

    /**
     * Restituisce i taxi relazionati con quello inserito cercando quelli con
     * gli stessi dati, ma clienti diversi.
     *
     * @param taxiID identificativo univoco del Taxi.
     * @return List di Taxi che rispettano i criteri di ricerca.
     */
    @Override
    public List<Taxi> getRelatedTaxis(Long taxiID) {
        Taxi viaggio = new Taxi();
        List<Taxi> viaggiRelazionati = new ArrayList();
        for (Taxi temp : taxiFacade.findAll()) {
            if (temp.getTaxi_id().equals(taxiID)) {
                viaggio = temp;
            }
        }

        for (Taxi temp : taxiFacade.findAll()) {
            if (temp.getDestination().equals(viaggio.getDestination())
                    && temp.getCreator_id().equals(viaggio.getCreator_id())
                    && temp.getData().equals(viaggio.getData())
                    && temp.getOrigin().equals(viaggio.getOrigin())) {
                viaggiRelazionati.add(temp);
            }
        }

        return viaggiRelazionati;
    }

    /**
     * Resistuisce la lista dei taxi creati dal cliente.
     *
     * @param id_client identificativo univoco del cliente.
     * @return List di Taxi che il cliente ha creato.
     */
    @Override
    public List<Taxi> getTaxiCreated(Long id_client) {
        List<Taxi> taxis = new ArrayList();
        List<Taxi> list = taxiFacade.findAll();
        for (Taxi temp : list) {
            if (temp.getCreator_id().equals(id_client) && temp.getClient_id().equals(id_client)) {
                taxis.add(temp);
            }
        }
        return taxis;
    }

    /**
     * Il metodo risponde alla ricerca più generale per destinazione e punto di
     * partenza e restituisce una lista di taxi possibili.
     *
     * @param origin la partenza del viaggio.
     * @param destination la destinazione del viaggio.
     * @return List di Taxi che rispettano i criteri di ricerca.
     */
    @Override
    public List<Taxi> searchByOriginDestination(String origin, String destination) {
        List<Taxi> taxies = new ArrayList();
        List<Taxi> viaggi = taxiFacade.findAll();
        origin = origin.toLowerCase();
        destination = destination.toLowerCase();
        //il primo if esclude le entry che riguardano i passeggeri (client_id != creator_id)
        //per evitare risultati ridondanti
        for (Taxi temp : viaggi) {
            if (temp.getClient_id().equals(temp.getCreator_id())) {
                if (temp.getOrigin().equals(origin) && temp.getDestination().equals(destination)) {
                    taxies.add(temp);
                }
            }
        }
        return sortListByDate(taxies);
    }

    /**
     * Il metodo risponde alla ricerca per destinazione, data e punto di
     * partenza e restituisce una lista di taxi possibili.
     *
     * @param data la data delle partenza.
     * @param origin la partenza del viaggio.
     * @param destination la destinazione del viaggio.
     * @return List di Taxi che rispettano i criteri di ricerca.
     */
    @Override
    public List<Taxi> searchByOriginDestinationDate(Date data, String origin, String destination) {
        List<Taxi> lista = new ArrayList();
        List<Taxi> viaggi = taxiFacade.findAll();
        origin = origin.toLowerCase();
        destination = destination.toLowerCase();
        for (Taxi temp : viaggi) {
            //il primo if esclude le entry che riguardano i passeggeri (client_id != creator_id)
            //per evitare risultati ridondanti
            if (temp.getClient_id().equals(temp.getCreator_id())) {
                //in questo modo vengono controllate tutte le date successive a quelle dell'utente
                if (temp.getOrigin().equals(origin) && temp.getDestination().equals(destination)) {
                    if (temp.getData().after(data)) {
                        lista.add(temp);
                    }
                    if (temp.getData().equals(data)) {
                        lista.add(temp);
                    }
                }
            }
        }
        return sortListByDate(lista);
    }

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
    @Override
    public List<Taxi> searchByOriginDestinationDateTime(Date data, Date time, String origin, String destination) {
        //in questo modo vengono controllate tutte le date successive a quelle dell'utente
        List<Taxi> lista = new ArrayList();
        List<Taxi> viaggi = taxiFacade.findAll();
        origin = origin.toLowerCase();
        destination = destination.toLowerCase();
        for (Taxi temp : viaggi) {
            //il primo if esclude le entry che riguardano i passeggeri (client_id != creator_id)
            //per evitare risultati ridondanti
            if (temp.getClient_id().equals(temp.getCreator_id())) {
                if ("undefined".equals(destination)) {
                    if (temp.getOrigin().equals(origin) && temp.getFreeSeats() > 0) {
                        if (temp.getData().equals(data) && afterTime(temp.getTime(), time)) {
                            lista.add(temp);
                        }
                        if (temp.getData().after(data)) {
                            lista.add(temp);
                        }
                    }
                } else if (temp.getOrigin().equals(origin) && temp.getDestination().equals(destination) && temp.getFreeSeats() > 0) {
                    if (temp.getData().equals(data) && afterTime(temp.getTime(), time)) {
                        lista.add(temp);
                    }
                    if (temp.getData().after(data)) {
                        lista.add(temp);
                    }
                }
            }
        }
        return sortListByDate(lista);
    }

    /**
     * Restituisce l'oggetto Taxi che ha l'identificativo univoco uguale a
     * quello passato come parametro.
     *
     * @param taxi_id identificativo univoco del Taxi.
     * @return Taxi oggetto cercato se esiste.
     */
    @Override
    public Taxi getTaxi(Long taxi_id) {
        Taxi viaggio = new Taxi();
        for (Taxi temp : taxiFacade.findAll()) {
            if (temp.getTaxi_id().equals(taxi_id)) {
                viaggio = temp;
            }
        }
        return viaggio;
    }

    /**
     * Verifica se la seconda data è dopo la prima, entrambe passate come
     * parametro.
     *
     * @param tempo1 valore della prima data da confrontare.
     * @param tempo2 valore della seconda data da confrontare.
     * @return boolean vale True se la seconda data è dopo la prima altrimenti è
     * False
     */
    protected boolean afterTime(Date tempo1, Date tempo2) {
        boolean risultato = false;
        Calendar calendar1 = Calendar.getInstance();
        calendar1.setTime(tempo1);
        int ora1 = calendar1.get(Calendar.HOUR_OF_DAY);
        int min1 = calendar1.get(Calendar.MINUTE);
        int sec1 = calendar1.get(Calendar.SECOND);
        calendar1.setTime(tempo2);
        int ora2 = calendar1.get(Calendar.HOUR_OF_DAY);
        int min2 = calendar1.get(Calendar.MINUTE);
        int sec2 = calendar1.get(Calendar.SECOND);
        if (ora2 > ora1) {
            risultato = true;
        }
        if (ora2 == ora1) {
            if (min2 > min1) {
                risultato = true;
            }
            if (min2 == min1) {
                if (sec2 > sec1) {
                    risultato = true;
                }
                if (sec2 == sec1) {
                    risultato = true;
                }
            }
        }
        return risultato;
    }

    /**
     * Ordina i viaggi in Taxi in base alla data in cui sono stati creati.
     *
     * @param taxis lista di Taxi da ordinare.
     * @return List di Taxi ordinati in base alla data.
     */
    protected List<Taxi> sortListByDate(List<Taxi> taxis) {
        Collections.sort(taxis, new Comparator<Taxi>() {
            public int compare(Taxi tax1, Taxi tax2) {
                return tax1.getData().compareTo(tax2.getData());
            }
        });
        return taxis;
    }
}
