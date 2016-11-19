package org.conpartir.entity;

import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Implementazione di serializzable per definire <i>Driver</i> memoriazzato nel
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
public class Driver implements Serializable {

    /**
     * Identificativo univoco dell'oggetto driver all'interno del database
     */
    private static final long serialVersionUID = 1L;
    @Id
    @Column(name = "DRIVER_ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long driver_id;

    /**
     * Identificativo del cliente proprietario della macchina.
     */
    @Column(name = "CLIENT_ID")
    private Long client_id;

    /**
     * Il modello della macchina.
     */
    @Column(name = "CAR_MODEL")
    private String carModel;

    /**
     * L'anno di produzione della macchina.
     */
    @Column(name = "CAR_YEAR")
    private int carYear;

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
     * databse.
     *
     * @param driver_id Long con il quale cambiare l'identificativo univoco.
     */
    public void setDriver_id(Long driver_id) {
        this.driver_id = driver_id;
    }

    /**
     * Restituisce l'identificativo univoco del cliente proprietario del mezzo.
     *
     * @return Valore dell'identificativo del cliente proprietario.
     */
    public Long getClient_id() {
        return client_id;
    }

    /**
     * Modifica il valore dell'identificativo del cliente proprietario del
     * mezzo.
     *
     * @param client_id Long con il quale cambiare l'identificativo del
     * proprietario.
     */
    public void setClient_id(Long client_id) {
        this.client_id = client_id;
    }

    /**
     * Restituisce il modello del mezzo associato al proprietario.
     *
     * @return Il modello del mezzo associato al proprietario.
     */
    public String getCarModel() {
        return carModel;
    }

    /**
     * Modifica il modello del mezzo associato al proprietario.
     *
     * @param carModel String con il quale modifica il modello della macchina.
     */
    public void setCarModel(String carModel) {
        this.carModel = carModel;
    }

    /**
     * Restituisce l'anno di produzione della macchina.
     *
     * @return L'anno di produzione della macchina.
     */
    public int getCarYear() {
        return carYear;
    }

    /**
     * Modifica l'anno di produzione della macchina.
     *
     * @param carYear Int con il quale cambiare l'anno di produzione della
     * macchina.
     */
    public void setCarYear(int carYear) {
        this.carYear = carYear;
    }

    /**
     * Restituisce il risultato intero di una funzione hash che codifica i
     * parametri del autista
     *
     * @return Int che corrisponde alla risultato di una funzione hash.
     */
    @Override
    public int hashCode() {
        int hash = 5;
        hash = 89 * hash + Objects.hashCode(this.client_id);
        hash = 89 * hash + Objects.hashCode(this.carModel);
        hash = 89 * hash + this.carYear;
        return hash;
    }

    /**
     * Restituisce la stringa che contiene tutti i dati di un autista
     *
     * @return String che contiene tutti i dati di un autista
     */
    @Override
    public String toString() {
        return "Driver{" + "driver_id=" + driver_id + ", client=" + client_id + ", carModel=" + carModel + ", carYear=" + carYear + '}';
    }

}
