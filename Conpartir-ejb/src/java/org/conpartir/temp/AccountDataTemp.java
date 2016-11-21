package org.conpartir.temp;

import java.util.List;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import org.conpartir.entity.Driver;
import org.conpartir.entity.Taxi;
import org.conpartir.entity.Travel;

/**
 * Implementazione della classe <i>AccountDataTemp</i> per la gestione di un
 * oggetto composto in formato XML.
 *
 * @author Conpartir Group
 * @version 0.3 21 settembre 2016
 * @see
 * <a href="http://docs.oracle.com/javaee/5/api/javax/xml/bind/annotation/XmlRootElement.html">
 * XmlRootElement </a>
 */
@XmlRootElement(name = "AccountDataTemp")
@XmlType(propOrder = {"name", "surname", "age", "email", "gender", "urlPhoto", "gmail", "twitter", "drivers", "postedTravels", "bookedTravels", "postedTaxis", "bookedTaxis"})
public class AccountDataTemp {

    private String name;
    private String surname;
    private Integer age;
    private String email;
    private String gender;
    private String urlPhoto;
    private boolean gmail;
    private boolean twitter;
    private List<Driver> drivers;
    private List<Travel> postedTravels;
    private List<Travel> bookedTravels;
    private List<Taxi> postedTaxis;
    private List<Taxi> bookedTaxis;

    /**
     * Restituisce il valore name dell'oggetto
     *
     * @return String valore name dell'oggetto
     */
    public String getName() {
        return name;
    }

    /**
     * Setta il valore name dell'oggetto
     *
     * @param name String valore name dell'oggetto
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Restituisce il valore surname dell'oggetto
     *
     * @return String valore surname dell'oggetto
     */
    public String getSurname() {
        return surname;
    }

    /**
     * Setta il valore surname dell'oggetto
     *
     * @param surname con cui modificare il campo.
     */
    public void setSurname(String surname) {
        this.surname = surname;
    }

    /**
     * Restituisce il valore age dell'oggetto.
     *
     * @return Integer il valore age dell'oggetto
     */
    public Integer getAge() {
        return age;
    }

    /**
     * Setta il valore age dell'oggetto.
     *
     * @param age con cui modificare il campo.
     */
    public void setAge(Integer age) {
        this.age = age;
    }

    /**
     * Restituisce il valore email dell'oggetto
     *
     * @return String valore email dell'oggetto
     */
    public String getEmail() {
        return email;
    }

    /**
     * Setta il valore email dell'oggetto
     *
     * @param email con cui modificare il campo.
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Restituisce il valore gender dell'oggetto
     *
     * @return String valore gender dell'oggetto
     */
    public String getGender() {
        return gender;
    }

    /**
     * Setta il valore gender dell'oggetto
     *
     * @param gender con cui modificare il campo.
     */
    public void setGender(String gender) {
        this.gender = gender;
    }

    /**
     * Restituisce il valore urlPhoto dell'oggetto
     *
     * @return String valore urlPhoto dell'oggetto
     */
    public String getUrlPhoto() {
        return urlPhoto;
    }

    /**
     * Setta il valore urlPhoto dell'oggetto
     *
     * @param urlPhoto con cui modificare il campo.
     */
    public void setUrlPhoto(String urlPhoto) {
        this.urlPhoto = urlPhoto;
    }

    /**
     * Verifica se Gmail esiste
     *
     * @return boolean valore Gmail dell'oggetto.
     */
    public boolean isGmail() {
        return gmail;
    }

    /**
     * Setta il valore gmail
     *
     * @param gmail con cui modificare il campo.
     */
    public void setGmail(boolean gmail) {
        this.gmail = gmail;
    }

    /**
     * Restituisce la lista di Driver dell'oggetto.
     *
     * @return List di Driver cercati.
     */
    public List<Driver> getDrivers() {
        return drivers;
    }

    /**
     * Setta la lista di Driver dell'oggetto
     *
     * @param drivers List di Driver con cui modificare il campo.
     */
    public void setDrivers(List<Driver> drivers) {
        this.drivers = drivers;
    }

    /**
     * Restituisce la lista di Travels postati dal cliente
     *
     * @return List di Travels cercati.
     */
    public List<Travel> getPostedTravels() {
        return postedTravels;
    }

    /**
     * Setta la lista di Travels postati dall'utente
     *
     * @param postedTravels List di Travels.
     */
    public void setPostedTravels(List<Travel> postedTravels) {
        this.postedTravels = postedTravels;
    }

    /**
     * Restituisce la lista di travels prenotati dall'utente
     *
     * @return List di Travels cercati.
     */
    public List<Travel> getBookedTravels() {
        return bookedTravels;
    }

    /**
     * Setta la lista di travels prenotati dall'utente
     *
     * @param bookedTravels List di Travels
     */
    public void setBookedTravels(List<Travel> bookedTravels) {
        this.bookedTravels = bookedTravels;
    }

    /**
     * Restituisce la lista di taxi postati dall'utente
     *
     * @return List di Taxi cercati
     */
    public List<Taxi> getPostedTaxis() {
        return postedTaxis;
    }

    /**
     * Setta la lista di taxi postati dall'utente
     *
     * @param postedTaxis List di Taxi
     */
    public void setPostedTaxis(List<Taxi> postedTaxis) {
        this.postedTaxis = postedTaxis;
    }

    /**
     * Restituisce la lista di taxi prenotati dall'utente
     *
     * @return List di Taxi cercati
     */
    public List<Taxi> getBookedTaxis() {
        return bookedTaxis;
    }

    /**
     * Setta la lista di taxi prenotati dall'utente
     *
     * @param bookedTaxis List di Taxi
     */
    public void setBookedTaxis(List<Taxi> bookedTaxis) {
        this.bookedTaxis = bookedTaxis;
    }

    /**
     * Restituisce il valore twitter dell'oggetto
     *
     * @return boolean vale True se esiste altrimenti è False.
     */
    public boolean isTwitter() {
        return twitter;
    }

    /**
     * Setta il valore twitter
     *
     * @param twitter boolean
     */
    public void setTwitter(boolean twitter) {
        this.twitter = twitter;
    }

    /**
     * Implementa il metodo che scrive l'oggetto come una stringa
     *
     * @return String
     */
    @Override
    public String toString() {
        return "AccountDataTemp{" + "name=" + name + ", surname=" + surname + ", age=" + age + ", email=" + email + ", gender=" + gender + ", urlPhoto=" + urlPhoto + ", drivers=" + drivers + ", postedTravels=" + postedTravels + ", bookedTravels=" + bookedTravels + ", postedTaxis=" + postedTaxis + ", bookedTaxis=" + bookedTaxis + '}';
    }

}
