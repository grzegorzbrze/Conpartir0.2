package org.conpartir.entity;

import java.io.Serializable;
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
 * Implementazione di serializzable per definire <i>Taxi</i> memoriazzato nel
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
public class Taxi implements Serializable {

    /**
     * Identificativo univoco dell'oggetto all'interno del database.
     */
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long taxi_id;

    /**
     * Identificativo del cliente.
     */
    @Column(name = "CLIENT_ID")
    private Long client_id;

    /**
     * Identificativo del cliente che ha creato il viaggio in taxi.
     */
    @Column(name = "CREATOR_ID")
    private Long creator_id;

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
     * Il numero di posti disponibili per il viaggio.
     */
    @Column(name = "FREE_SEATS")
    private int freeSeats;

    /**
     * Le coordinate dell'origine del viaggio.
     */
    @Column(name = "COORD_START")
    private String coordStart;

    /**
     * Le coordinate della destinazione del viaggio.
     */
    @Column(name = "COORD_END")
    private String coordEnd;

    /**
     * Restituisce l'identificativo univoco del taxi memorizzato nel database.
     *
     * @return Valore dell'identificativo del taxi nel database.
     */
    public Long getTaxi_id() {
        return taxi_id;
    }

    /**
     * Modifica il valore dell'identificativo univoco del taxi memorizzato nel
     * databse.
     *
     * @param taxi_id Long con il quale cambiare l'identificativo univoco.
     */
    public void setTaxi_id(Long taxi_id) {
        this.taxi_id = taxi_id;
    }

    /**
     * Restituisce l'identificativo univoco del cliente che ha creato il viaggio
     * in taxi.
     *
     * @return Valore dell'identificativo del cliente creatore.
     */
    public Long getCreator_id() {
        return creator_id;
    }

    /**
     * Modifica il valore dell'identificativo univoco del cliente che ha creato
     * il viaggio.
     *
     * @param creator_id Long con il quale cambiare l'identificativo univoco.
     */
    public void setCreator_id(Long creator_id) {
        this.creator_id = creator_id;
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
     * Restituisce la data in cui avviene il viaggio in taxi.
     *
     * @return Valore della data del viaggio.
     */
    public Date getData() {
        return data;
    }

    /**
     * Modifica il valore della data in cui avviene il viaggio in taxi.
     *
     * @param data Date con la quale modificare la data di partenza.
     */
    public void setData(Date data) {
        this.data = data;
    }

    /**
     * Restituisce l'orario in cui avviene il viaggio in taxi.
     *
     * @return Valore dell'orario del viaggio.
     */
    public Date getTime() {
        return time;
    }

    /**
     * Modifica il valore dell'orario in cui avviene il viaggio in taxi.
     *
     * @param time Date con la quale modificare l'orario di partenza.
     */
    public void setTime(Date time) {
        this.time = time;
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
     * Restituisce le coordinate d'origine del viaggio in taxi.
     *
     * @return Valore delle coordinate d'origine.
     */
    public String getCoordStart() {
        return coordStart;
    }

    /**
     * Modifica le coordinate d'origine del viaggio in taxi.
     *
     * @param coordStart String con la quale cambiare le coordinate.
     */
    public void setCoordStart(String coordStart) {
        this.coordStart = coordStart;
    }

    /**
     * Restituisce le coordinate di destinazione del viaggio in taxi.
     *
     * @return Valore della coordinata di destinazione.
     */
    public String getCoordEnd() {
        return coordEnd;
    }

    /**
     * Modifica le coordinate di destinazione del viaggio in taxi.
     *
     * @param coordEnd String con la quale cambiare le coordinate.
     */
    public void setCoordEnd(String coordEnd) {
        this.coordEnd = coordEnd;
    }

    /**
     * Restituisce la stringa che contiene tutti i dati di un viaggio in taxi
     *
     * @return String che contiene tutti i dati di un viaggio in taxi.
     */
    @Override
    public String toString() {
        return "Taxi{" + "id=" + taxi_id + ", client_id=" + client_id + ", creator_id="
                + creator_id + ", origin=" + origin + ", destination=" + destination
                + ", data=" + data + ", time=" + time + ", freeSeats=" + freeSeats + '}';
    }

    /**
     * Restituisce il risultato intero di una funzione hash che codifica i
     * parametri del viaggio in taxi.
     *
     * @return Int che corrisponde alla risultato di una funzione hash.
     */
    @Override
    public int hashCode() {
        int hash = 7;
        hash = 31 * hash + Objects.hashCode(this.taxi_id);
        hash = 31 * hash + Objects.hashCode(this.origin);
        hash = 31 * hash + Objects.hashCode(this.destination);
        hash = 31 * hash + Objects.hashCode(this.data);
        hash = 31 * hash + Objects.hashCode(this.time);
        hash = 31 * hash + this.freeSeats;
        return hash;
    }
}
