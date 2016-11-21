package org.conpartir.sessionBean;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import org.conpartir.entity.Client;
import org.conpartir.entity.Driver;
import org.conpartir.entity.Travel;
import org.conpartir.facade.ClientFacadeLocal;
import org.conpartir.facade.DriverFacadeLocal;
import org.conpartir.facade.TravelFacadeLocal;

/**
 * Implementazione del SessionBean Stateless <i>TravelManager</i> per la
 * gestione che riguarda il Travel. Implemententa tutti i metodi esposti
 * nell'interfaccia TravelManagerLocal.
 *
 * @author Conpartir Group
 * @version 0.3 21 settembre 2016
 * @see <a href="http://docs.oracle.com/javaee/6/tutorial/doc/gipjg.html">
 * Session Bean </a>
 */
@Stateless
public class TravelManager implements TravelManagerLocal {

    /**
     * EntityManager che rende persistenti le modiche dei SessionBean.
     */
    @EJB
    private DriverFacadeLocal driverFacade;
    @EJB
    private ClientFacadeLocal clientFacade;
    @EJB
    private TravelFacadeLocal travelFacade;

    /**
     * Permette di creare una tupla nel database tramite un oggetto di tipo
     * Travel
     *
     * @param travel da memorizzare nel database.
     */
    @Override
    public void createTravel(Travel travel) {
        if (travel.getDriver_id() != null && travel.getClient_id() != null
                && travel.getOrigin() != null && travel.getDestination() != null
                && travel.getData().toString() != null && travel.getTime().toString() != null
                && travel.getFreeSeats() != 0) {
            if (!isExist(travel.getDriver_id(), travel.getClient_id(), travel.getData(),
                    travel.getTime(), travel.getOrigin(), travel.getDestination())) {
                travel.setDestination(travel.getDestination().toLowerCase());
                travel.setOrigin(travel.getOrigin().toLowerCase().toLowerCase());
                travelFacade.create(travel);
            }
        }
    }

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
    @Override
    public void createTravel(Long driver_id, Long client_id, String origin,
            String destination, Date data, Date time, int freeSeats) {
        if (driver_id != null && client_id != null && origin != null && destination != null
                && data.toString() != null && time.toString() != null) {
            if (!isExist(driver_id, client_id, data, time, origin, destination)) {
                Travel travel = new Travel();
                travel.setClient_id(client_id);
                travel.setDriver_id(driver_id);
                travel.setData(data);
                travel.setDestination(destination.toLowerCase());
                travel.setOrigin(origin.toLowerCase());
                travel.setTime(time);
                travel.setFreeSeats(freeSeats);
                travelFacade.create(travel);
            }
        }
    }

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
    @Override
    public Long getTravel_ID(Long driver_id, Long client_id, Date data,
            Date time, String origine, String destination) {
        Long id_travel = null;
        List<Travel> list = travelFacade.findAll();
        for (Travel temp : list) {
            Long temp_driverID = temp.getDriver_id();
            Long temp_clientID = temp.getClient_id();
            Date temp_data = temp.getData();
            Date temp_time = temp.getTime();
            String temp_origine = temp.getOrigin();
            String temp_destination = temp.getDestination();
            if (temp_driverID != null && temp_clientID != null
                    && temp_data.toString() != null && temp_time.toString() != null
                    && temp_origine != null && temp_destination != null) {
                if (temp_driverID.equals(driver_id) && temp_clientID.equals(client_id)
                        && equalsDate(temp_data, data) && temp_time.equals(time)
                        && temp_origine.equals(origine.toLowerCase()) && temp_destination.equals(destination.toLowerCase())) {
                    id_travel = temp.getTravel_id();
                }
            }
        }
        return id_travel;
    }

    /**
     * Il metodo ricerca e restituisce tutti i viaggi di un certo utente a
     * partire da data e tempo
     *
     * @param client_id identificativo univoco del client del viaggio.
     * @param data valore dell'attributo data del viaggio.
     * @param time valore dell'attributo time del viaggio.
     * @return List di Travel che rispettano i criteri di ricerca.
     */
    @Override
    public List<Travel> getClientTravel(Long client_id, Date data, Date time) {
        List<Travel> lista = new ArrayList();
        List<Travel> viaggi = travelFacade.findAll();

        if (!data.equals(time) && !data.before(new Date(99, 12, 12))) {
            for (Travel temp : viaggi) {
                if (temp.getClient_id().equals(client_id)) {
                    if (temp.getData().after(data)) {
                        lista.add(temp);
                    }
                    if (equalsDate(temp.getData(), data) && afterTime(temp.getTime(), time)) {
                        lista.add(temp);
                    }
                }
            }
        } else {
            Date today = new Date();
            for (Travel temp : viaggi) {
                if (temp.getClient_id().equals(client_id)) {
                    if (temp.getData().before(data)) {
                        lista.add(temp);
                    }
                }
            }
        }
        return sortListByDate(lista);
    }

    /**
     * Il metodo ricerca e restituisce tutti i viaggi di un certo utente prima
     * di una certa data e orario.
     *
     * @param client_id identificativo univoco del client del viaggio.
     * @param data valore dell'attributo data del viaggio.
     * @param time valore dell'attributo time del viaggio.
     * @return List di Travel che rispettano i criteri di ricerca.
     */
    @Override
    public List<Travel> getClientTravelBefore(Long client_id, Date data, Date time) {
        List<Travel> lista = new ArrayList();
        List<Travel> viaggi = travelFacade.findAll();

        Date today = new Date();
        for (Travel temp : viaggi) {
            if (temp.getClient_id().equals(client_id)) {
                if (temp.getData().before(data)) {
                    lista.add(temp);
                }
                if (equalsDate(temp.getData(), data) && afterTime(temp.getTime(), time)) {
                    lista.add(temp);
                }
            }
        }
        return sortListByDate(lista);
    }

    /**
     * Il metodo ricerca e restituisce tutti i viaggi di un certo utente dopo
     * una certa data e orario.
     *
     * @param client_id identificativo univoco del client del viaggio.
     * @param data valore dell'attributo data del viaggio.
     * @param time valore dell'attributo time del viaggio.
     * @return List di Travel che rispettano i criteri di ricerca.
     */
    @Override
    public List<Travel> getClientTravelAfter(Long client_id, Date data, Date time) {
        List<Travel> lista = new ArrayList();
        List<Travel> viaggi = travelFacade.findAll();

        Date today = new Date();
        for (Travel temp : viaggi) {
            if (temp.getClient_id().equals(client_id)) {
                if (temp.getData().after(data)) {
                    lista.add(temp);
                }
                if (equalsDate(temp.getData(), data) && beforeTime(temp.getTime(), time)) {
                    lista.add(temp);
                }
            }
        }
        return sortListByDate(lista);
    }

    /**
     * Ricerca e restituisce i vaggi per destinazione e punto di partenza.
     *
     * @param origin punto di partenza del viaggio.
     * @param destination punto di arrivo.
     * @return List di Travel che rispettano i criteri di ricerca.
     */
    @Override
    public List<Travel> searchByOriginDestination(String origin, String destination) {
        List<Travel> lista = new ArrayList();
        List<Travel> viaggi = travelFacade.findAll();
        origin = origin.toLowerCase();
        destination = destination.toLowerCase();
        for (Travel temp : viaggi) {
            if (temp.getDestination().equals(destination) && temp.getOrigin().equals(origin)
                    && temp.getFreeSeats() > 0) {
                if (isCreatorTravel(temp.getClient_id(), temp.getDriver_id())) {
                    lista.add(temp);
                }
            }
        }
        return sortListByDate(lista);
    }

    /**
     * Ricerca e restituisce i vaggi per destinazione, punto di partenza e data
     * di partenza.
     *
     * @param data la data di partenza.
     * @param origin punto di partenza del viaggio.
     * @param destination punto di arrivo.
     * @return List di Travel che rispettano i criteri di ricerca.
     */
    @Override
    public List<Travel> searchByOriginDestinationDate(Date data, String origin, String destination) {
        List<Travel> lista = new ArrayList();
        List<Travel> viaggi = travelFacade.findAll();
        origin = origin.toLowerCase();
        destination = destination.toLowerCase();
        for (Travel temp : viaggi) {
            //in questo modo vengono controllate tutte le date successive a quelle dell'utente
            if (temp.getOrigin().equals(origin) && temp.getDestination().equals(destination)
                    && temp.getFreeSeats() > 0) {
                if (isCreatorTravel(temp.getClient_id(), temp.getDriver_id())) {
                    if (temp.getData().after(data) || equalsDate(temp.getData(), data)) {
                        lista.add(temp);
                    }
                }
            }
        }
        return sortListByDate(lista);
    }

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
    @Override
    public List<Travel> searchByOriginDestinationDateTime(Date data, Date time, String origin, String destination) {
        //in questo modo vengono controllate tutte le date successive a quelle dell'utente
        List<Travel> lista = new ArrayList();
        List<Travel> viaggi = travelFacade.findAll();
        origin = origin.toLowerCase();
        destination = destination.toLowerCase();
        for (Travel temp : viaggi) {
            //in questo modo vengono controllate tutte le date uguali a quelle dell'utente
            if (temp.getOrigin().equals(origin) && temp.getDestination().equals(destination)
                    && temp.getFreeSeats() > 0) {
                if (isCreatorTravel(temp.getClient_id(), temp.getDriver_id())) {
                    if (temp.getData().after(data)) {
                        lista.add(temp);
                    }
                    if (equalsDate(temp.getData(), data) && afterTime(time, temp.getTime())) {
                        lista.add(temp);
                    }
                }
            }
        }
        return sortListByDate(lista);
    }

    /**
     * Restituisce l'ogetto Travel che ha l'identificativo univoco pari a quello
     * passato come argomento.
     *
     * @param travelID identificativo univoco del viaggio.
     * @return Travel oggetto cercato.
     */
    @Override
    public Travel getTravel(Long travelID) {
        for (Travel temp : travelFacade.findAll()) {
            if (temp.getTravel_id().equals(travelID)) {
                return temp;
            }
        }
        return new Travel();
    }

    /**
     * Il metodo restituisce i travels relazionati con quello inserito cercando
     * quelli con gli stessi dati, ma clients diversi
     *
     * @param travelID identificativo univoco del Travel.
     * @return List di Travel che rispettano i criteri di ricerca.
     */
    @Override
    public List<Travel> getRelatedTravels(Long travelID) {
        Travel viaggio = new Travel();
        List<Travel> viaggiRelazionati = new ArrayList();
        for (Travel temp : travelFacade.findAll()) {
            if (temp.getTravel_id().equals(travelID)) {
                viaggio = temp;
            }
        }
        for (Travel temp : travelFacade.findAll()) {
            if (temp.getDriver_id().equals(viaggio.getDriver_id())
                    && temp.getDestination().equals(viaggio.getDestination())
                    && temp.getCalendarTime().equals(viaggio.getCalendarTime())
                    && temp.getCalendarData().equals(viaggio.getCalendarData())
                    && temp.getOrigin().equals(viaggio.getOrigin())) {
                viaggiRelazionati.add(temp);
            }
        }

        return viaggiRelazionati;
    }

    /**
     * Restituisce, se esiste, il proprietario della macchina del viaggio che ha
     * l'identificativo univoco uguale a quello passato come parametro.
     *
     * @param travel_id identificativo univoco del Travel.
     * @return Client cercato.
     */
    @Override
    public Client getInfoClientEqualDriver(Long travel_id) {
        Travel travel = getTravel(travel_id);
        Long id_client = travel.getClient_id();
        Long id_driver = travel.getDriver_id();
        List<Driver> drivers = driverFacade.findAll();
        boolean corrisponde = false;
        for (Driver driver : drivers) {
            //qui sotto ho il controllo che verifica se il cliente e la macchina corrispondono
            if (driver.getClient_id().equals(id_client) && driver.getDriver_id().equals(id_driver)) {
                corrisponde = true;
            }
        }
        if (corrisponde) {
            List<Client> lista = clientFacade.findAll();
            for (Client cliente : lista) {
                if (cliente.getId().equals(id_client)) {
                    return cliente;
                }
            }
        }
        return null;
    }

    /**
     * Restituisce, se esiste, la macchina del viaggio che ha l'identificativo
     * univoco uguale a quello passato come parametro.
     *
     * @param travel_id identificativo univoco del Travel.
     * @return Driver cercato.
     */
    @Override
    public Driver getInfoDriverEqualClient(Long travel_id) {
        Travel travel = getTravel(travel_id);
        Long id_client = travel.getClient_id();
        Long id_driver = travel.getDriver_id();
        List<Driver> drivers = driverFacade.findAll();
        for (Driver driver : drivers) {
            //qui sotto ho il controllo che verifica se il cliente e la macchina corrispondono
            if (driver.getClient_id().equals(id_client) && driver.getDriver_id().equals(id_driver)) {
                return driver;
            }
        }
        return null;
    }

    /**
     * Aggiungere un passeggero al viaggio se non ne fa già parte e se ci sono
     * ancora i posti disponibili.
     *
     * @param travel_id identificativo univoco del Travel.
     * @param passengerID identificativo univoco del cliente.
     * @return boolean vale True se l'aggiunta è andata a buon fine altrimenti
     * False.
     */
    @Override
    public boolean addPassenger(Long travel_id, Long passengerID) {
        boolean risultato = true;
        Travel trav = getTravel(travel_id);
        if (trav.getTravel_id() != null) {
            List<Travel> prenotati = travelFacade.findAll();
            for (Travel prenotato : prenotati) {
                if (prenotato.getDriver_id().equals(trav.getDriver_id())
                        && prenotato.getClient_id().equals(passengerID)
                        && prenotato.getOrigin().equals(trav.getOrigin()) && prenotato.getDestination().equals(trav.getDestination())
                        && equalsDate(prenotato.getData(), trav.getData()) && prenotato.getTime().equals(trav.getTime())) {
                    risultato = false;
                }
            }
        }
        if (risultato == true) {
            List<Travel> travels = travelFacade.findAll();
            for (Travel temp : travels) {
                if (temp.getTravel_id().equals(travel_id)) {
                    int viaggiRimasti = temp.getFreeSeats();
                    if (viaggiRimasti > 0) {
                        this.createTravel(temp.getDriver_id(), passengerID, temp.getOrigin(),
                                temp.getDestination(), temp.getData(), temp.getTime(), 0);
                        temp.setFreeSeats(viaggiRimasti - 1);

                        subFreeSeat(travel_id);
                    } else {
                        risultato = false;
                    }
                }
            }
        }
        return risultato;
    }

    /**
     * Verifica se il cliente passato come parametro è anche l'autista di una
     * specifica macchina.
     *
     * @param client_id identificativo univoco del cliente.
     * @param driver_id identificativo univoco del driver.
     * @return boolean vale True se il cliente e il driver sono uguali
     * altrimenti è False.
     */
    protected boolean isCreatorTravel(Long client_id, Long driver_id) {
        boolean isCreator = false;
        List<Driver> drivers = driverFacade.findAll();
        for (Driver driver : drivers) {
            if (driver.getClient_id().equals(client_id) && driver.getDriver_id().equals(driver_id)) {
                isCreator = true;
            }
        }
        return isCreator;
    }

    /**
     * Verifica se le due date passate come parametro sono uguali fra di loro.
     *
     * @param data1 valore della prima data.
     * @param data2 valore della seconda data.
     * @return boolean che vale True se sono uguali False altrimenti.
     */
    protected boolean equalsDate(Date data1, Date data2) {
        boolean uguali = false;
        Calendar calendario = Calendar.getInstance();
        calendario.setTime(data1);
        int anno1 = calendario.get(Calendar.YEAR);
        int mese1 = calendario.get(Calendar.MONTH);
        int giorno1 = calendario.get(Calendar.DAY_OF_MONTH);
        calendario.setTime(data2);
        int anno2 = calendario.get(Calendar.YEAR);
        int mese2 = calendario.get(Calendar.MONTH);
        int giorno2 = calendario.get(Calendar.DAY_OF_MONTH);
        if (anno1 == anno2 && mese1 == mese2 && giorno1 == giorno2) {
            uguali = true;
        }
        return uguali;
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
     * Verifica se la seconda data è prima della prima, entrambe passate come
     * parametro.
     *
     * @param tempo1 valore della prima data da confrontare.
     * @param tempo2 valore della seconda data da confrontare.
     * @return boolean vale True se la seconda data è dopo la prima altrimenti è
     * False
     */
    protected boolean beforeTime(Date tempo1, Date tempo2) {
        return afterTime(tempo2, tempo1);
    }

    /**
     * Aggiorna i posti occupati in un Travel prenotato.
     *
     * @param travel_id identificativo univoco del Travel.
     * @return boolean vale True se l'aggiornamento è andato a buon fine
     * altrimenti vale False.
     */
    protected boolean subFreeSeat(Long travel_id) {
        boolean diminuito = false;
        Travel viaggio = getTravel(travel_id);
        List<Travel> lista = travelFacade.findAll();
        for (Travel temp : lista) {
            if (temp.getDriver_id().equals(viaggio.getDriver_id())
                    && temp.getOrigin().endsWith(viaggio.getOrigin())
                    && temp.getDestination().equals(viaggio.getDestination())
                    && temp.getData().equals(viaggio.getData()) && temp.getTime().equals(viaggio.getTime())
                    && temp.getFreeSeats() != viaggio.getFreeSeats() && !temp.getTravel_id().equals(travel_id)) {
                temp.setFreeSeats(viaggio.getFreeSeats());
                diminuito = true;
            }
        }
        return diminuito;
    }

    /**
     * Verifica se il viaggio esiste già nel database con i parametri passati.
     *
     * @param driver_id identificativo univoco del Driver del viaggio.
     * @param client_id identificativo univoco del client del viaggio.
     * @param data valore dell'attributo data del viaggio.
     * @param time valore dell'attributo time del viaggio.
     * @param origin valore dell'attributo orgin del viaggio.
     * @param destination valore dell'attributo destination del viaggio.
     * @return boolean vale True se il viaggio esiste altrimenti è False.
     */
    protected boolean isExist(Long driver_id, Long client_id, Date data,
            Date time, String origin, String destination) {
        boolean risultato = true;
        origin = origin.toLowerCase();
        destination = destination.toLowerCase();
        Long risID = getTravel_ID(driver_id, client_id, data, time, origin, destination);
        if (risID == null) {
            risultato = false;
        }
        return risultato;
    }

    /**
     * Ordina i viaggi in Taxi in base alla data in cui sono stati creati.
     *
     * @param travels lista di Taxi da ordinare.
     * @return List di Taxi ordinati in base alla data.
     */
    protected List<Travel> sortListByDate(List<Travel> travels) {
        Collections.sort(travels, new Comparator<Travel>() {
            public int compare(Travel trav1, Travel trav2) {
                return trav1.getData().compareTo(trav2.getData());
            }
        });
        return travels;
    }

}
