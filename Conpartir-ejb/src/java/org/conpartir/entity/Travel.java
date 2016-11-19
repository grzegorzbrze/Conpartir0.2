package org.conpartir.entity;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Implementazione di serializzable per definire <i>Travel</i> memoriazzato nel
 * database grazie all'Entity.
 *
 * @author Conpartir Group
 * @version 0.3 21 settembre 2016
 * @see <a href="http://docs.oracle.com/javaee/6/tutorial/doc/bnbqa.html">
 * Entity </a>
 * @see
 * <a href="https://docs.oracle.com/javase/7/docs/api/java/io/Serializable.html">
 * Serializable </a>
 *
 */
@Entity
@XmlRootElement
public class Travel implements Serializable {

    /**
     * Identificativo univoco dell'oggetto all'interno del database.
     */
    private static final long serialVersionUID = 1L;
    @Id
    @Column(name = "TRAVEL_ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long travel_id;

    /**
     * Identificativo univoco dell'oggetto driver all'interno del database
     */
    @Column(name = "DRIVER_ID")
    private Long driver_id;

    /**
     * Identificativo univoco del cliente.
     */
    @Column(name = "CLIENT_ID")
    private Long client_id;

    /**
     * La data in cui avviene il viaggio.
     */
    @Column(name = "DATA")
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date data;

    /**
     * L'orario in cui avviene il viaggio.
     */
    @Column(name = "TIME")
    @Temporal(javax.persistence.TemporalType.TIME)
    private Date time;

    /**
     * L'origine del viaggio.
     */
    @Column(name = "ORIGIN")
    private String origin;

    /**
     * La destinazione del viaggio.
     */
    @Column(name = "DESTINATION")
    private String destination;

    /**
     * Il numero di posti disponibili per il viaggio.
     */
    @Column(name = "FREE_SEATS")
    private int freeSeats;

    /**
     * Restituisce l'identificativo univoco del viaggio memorizzato nel
     * database.
     *
     * @return Valore dell'identificativo del viaggio nel database.
     */
    public Long getTravel_id() {
        return travel_id;
    }

    /**
     * Modifica il valore dell'identificativo univoco del viaggio memorizzato
     * nel database.
     *
     * @param travel_id Long con il quale cambiare l'identificativo univoco.
     */
    public void setTravel_id(Long travel_id) {
        this.travel_id = travel_id;
    }

    /**
     * Restituisce l'identificativo univoco del mezzo memorizzato nel database.
     *
     * @return Valore dell'identificativo del mezzo nel database.
     */
    public Long getDriver_id() {
        return driver_id;
    }

    /**
     * Modifica il valore dell'identificativo univoco del mezzo memorizzato nel
     * database.
     *
     * @param driver_id Long con il quale cambiare l'identificativo univoco.
     */
    public void setDriver_id(Long driver_id) {
        this.driver_id = driver_id;
    }

    /**
     * Restituisce l'identificativo univoco del cliente.
     *
     * @return Valore dell'identificativo del cliente.
     */
    public Long getClient_id() {
        return client_id;
    }

    /**
     * Modifica il valore dell'identificativo del cliente.
     *
     * @param client_id Long con il quale cambiare l'identificativo del cliente.
     */
    public void setClient_id(Long client_id) {
        this.client_id = client_id;
    }

    /**
     * Restituisce la data in cui avviene il viaggio.
     *
     * @return Valore della data del viaggio.
     */
    public Date getData() {
        return data;
    }

    /**
     * Modifica il valore della data in cui avviene il viaggio.
     *
     * @param data Date con la quale modificare la data di partenza.
     */
    public void setData(Date data) {
        this.data = data;
    }

    /**
     * Restituisce l'orario in cui avviene il viaggio.
     *
     * @return Valore dell'orario del viaggio.
     */
    public Date getTime() {
        return time;
    }

    /**
     * Modifica il valore dell'orario in cui avviene il viaggio.
     *
     * @param time Date con la quale modificare l'orario di partenza.
     */
    public void setTime(Date time) {
        this.time = time;
    }

    /**
     * Restituisce l'origine del viaggio.
     *
     * @return Valore dell'origine del viaggio.
     */
    public String getOrigin() {
        return origin;
    }

    /**
     * Modifica il valore dell'origine del viaggio.
     *
     * @param origin String con la quale modificare l'origine.
     */
    public void setOrigin(String origin) {
        this.origin = origin;
    }

    /**
     * Restituisce la destinazione del viaggio.
     *
     * @return Valore della destinazione.
     */
    public String getDestination() {
        return destination;
    }

    /**
     * Modifica il valore della destinazione del viaggio.
     *
     * @param destination String con la quale modificare la destinazione.
     */
    public void setDestination(String destination) {
        this.destination = destination;
    }

    /**
     * Restituisce il numero di posti liberi nel mezzo.
     *
     * @return Il numero di posti liberi.
     */
    public int getFreeSeats() {
        return freeSeats;
    }

    /**
     * Modifica il numero di posti liberi nel mezzo.
     *
     * @param freeSeats Int con il quale modificare il numero di posti liberi.
     */
    public void setFreeSeats(int freeSeats) {
        this.freeSeats = freeSeats;
    }

    /**
     * Restituisce la data del viaggio.
     *
     * @return La data del viaggio come oggetto Calendar.
     */
    public Calendar getCalendarData() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(data);
        return calendar;
    }

    /**
     * Restituisce l'orario del viaggio.
     *
     * @return L'orario del viaggio come oggetto Calendar.
     */
    public Calendar getCalendarTime() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(time);
        return calendar;
    }

    /**
     * Restituisce l'orario del viaggio.
     *
     * @return L'orario del viaggio come String.
     */
    public String getTimeString() {
        Calendar cal = getCalendarTime();
        String hour = cal.get(Calendar.HOUR) + ":" + cal.get(Calendar.MINUTE) + ":"
                + cal.get(Calendar.SECOND);
        return hour;
    }

    /**
     * Restituisce la data del viaggio.
     *
     * @return La data del viaggio come String.
     */
    public String getDataString() {
        Calendar cal = getCalendarData();
        String laData = cal.get(Calendar.YEAR) + "/" + cal.get(Calendar.MONTH) + "/"
                + cal.get(Calendar.DAY_OF_MONTH);
        return laData;
    }

    /**
     * Restituisce il risultato intero di una funzione hash che codifica i
     * parametri del viaggio.
     *
     * @return Int che corrisponde alla risultato di una funzione hash.
     */
    @Override
    public int hashCode() {
        int hash = 5;
        hash = 53 * hash + Objects.hashCode(this.driver_id);
        hash = 53 * hash + Objects.hashCode(this.client_id);
        hash = 53 * hash + Objects.hashCode(this.data);
        hash = 53 * hash + Objects.hashCode(this.time);
        hash = 53 * hash + Objects.hashCode(this.origin);
        hash = 53 * hash + Objects.hashCode(this.destination);
        hash = 53 * hash + this.freeSeats;
        return hash;
    }

    /**
     * Restituisce la stringa che contiene tutti i dati di un viaggio.
     *
     * @return String che contiene tutti i dati di un viaggio.
     */
    @Override
    public String toString() {
        return "Travel{" + "travel_id=" + travel_id + ", driver=" + driver_id
                + ", client_id=" + client_id + ", data=" + getDataString() + ", time=" + getTimeString()
                + ", origin=" + origin + ", destination=" + destination + ", freeSeats=" + freeSeats + '}';
    }

}