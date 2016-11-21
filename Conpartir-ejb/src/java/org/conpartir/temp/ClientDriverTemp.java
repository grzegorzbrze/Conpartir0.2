package org.conpartir.temp;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

/**
 * Implementazione della classe <i>ClientDriverTemp</i> per la gestione di un
 * oggetto composto in formato XML.
 *
 * @author Conpartir Group
 * @version 0.3 21 settembre 2016
 * @see
 * <a href="http://docs.oracle.com/javaee/5/api/javax/xml/bind/annotation/XmlRootElement.html">
 * XmlRootElement </a>
 */
@XmlRootElement(name = "AccountDataTemp")
@XmlType(propOrder = {"name", "surname", "age", "email", "gender", "urlPhoto", "carModel", "carYear"})
public class ClientDriverTemp {

    private String name;
    private String surname;
    private int age;
    private String email;
    private char gender;
    private String urlPhoto;
    private String carModel;
    private int carYear;

    /**
     * Restituisce il valore name dell'oggetto
     *
     * @return String
     */
    public String getName() {
        return name;
    }

    /**
     * Setta il valore name dell'oggetto
     *
     * @param name String
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Restituisce il valore name dell'oggetto
     *
     * @return String
     */
    public String getSurname() {
        return surname;
    }

    /**
     * Setta il valore surname dell'oggetto
     *
     * @param surname String
     */
    public void setSurname(String surname) {
        this.surname = surname;
    }

    /**
     * Restituisce il valore age dell'oggetto
     *
     * @return int
     */
    public int getAge() {
        return age;
    }

    /**
     * Setta il valore age dell'oggetto
     *
     * @param age int
     */
    public void setAge(int age) {
        this.age = age;
    }

    /**
     * Restituisce il valore email dell'oggetto
     *
     * @return String
     */
    public String getEmail() {
        return email;
    }

    /**
     * Setta il valore email dell'oggetto
     *
     * @param email String
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Restituisce il valore gender dell'oggetto
     *
     * @return char
     */
    public char getGender() {
        return gender;
    }

    /**
     * Setta il valore gender dell'oggetto
     *
     * @param gender char
     */
    public void setGender(char gender) {
        this.gender = gender;
    }

    /**
     * Restituisce il valore urlPhoto dell'oggetto
     *
     * @return String
     */
    public String getUrlPhoto() {
        return urlPhoto;
    }

    /**
     * Setta il valore urlPhoto dell'oggetto
     *
     * @param urlPhoto String
     */
    public void setUrlPhoto(String urlPhoto) {
        this.urlPhoto = urlPhoto;
    }

    /**
     * Restituisce il valore carModel dell'oggetto
     *
     * @return String
     */
    public String getCarModel() {
        return carModel;
    }

    /**
     * Setta il valore carModel dell'oggetto
     *
     * @param carModel String
     */
    public void setCarModel(String carModel) {
        this.carModel = carModel;
    }

    /**
     * Restituisce il valore carYear dell'oggetto
     *
     * @return int
     */
    public int getCarYear() {
        return carYear;
    }

    /**
     * Setta il valore carlYear dell'oggetto
     *
     * @param carYear int
     */
    public void setCarYear(int carYear) {
        this.carYear = carYear;
    }

    /**
     * Implementa il metodo che scrive l'oggetto come una stringa
     *
     * @return String
     */
    @Override
    public String toString() {
        return "ClientDriverTemp{" + "name=" + name + ", surname=" + surname + ", age=" + age + ", email=" + email + ", gender=" + gender + ", urlPhoto=" + urlPhoto + ", carModel=" + carModel + ", carYear=" + carYear + '}';
    }
}
