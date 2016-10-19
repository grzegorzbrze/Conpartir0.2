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
 * Implementazione di serializzable per definire <i>Client</i> memoriazzato nel database grazie all'Entity. 
 * @author Conpartir Group
 * @version 0.3 11 settembre 2016
 * @see <a href="http://docs.oracle.com/javaee/6/tutorial/doc/bnbqa.html"> Entity </a>
 * @see <a href="https://docs.oracle.com/javase/7/docs/api/java/io/Serializable.html"> Serializable </a>
 * 
 */
@Entity
@XmlRootElement
public class Client implements Serializable {
    
    /**
     * Identificativo univoco dell'oggetto all'interno del database
     */
    private static final long serialVersionUID = 1L;
    @Id
    @Column (name = "CLIENT_ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long clientID;
   
    /**
     * Attributo name del Client
     */
    @Column (name = "NAME")
    private String name; 
    
    /**
     * Attributo surname del Client
     */
    @Column (name = "SURNAME")
    private String surname;
    
    /**
     * Attributo gender del Client
     */
    @Column (name = "GENDER")
    private char gender;
    
    /**
     * Attributo age del Client
     */
    @Column (name = "AGE")
    private int age;
    
    /**
     * Attributo unico email del Client
     */
    @Column (name = "EMAIL", unique = true)
    private String email;
    
    /**
     * Attributo password del Client
     */
    @Column (name = "PASS")
    private String pass;
    
    /**
     * Attributo path image del Client
     */
    @Column (name = "URLPHOTO")
    private String urlPhoto = "defaultpic_sm.png";
    
    /**
     * Attributo email di Gmail del Client
     */
    @Column (name = "GMAILVALUE")
    private String gmailValue = null;
    
    /**
     * Attributo email di Twitter del Client
     */
    @Column (name = "TWITTERVALUE")
    private String twitterValue = null;

    /**
     * Restituisce il valore dell'email di Twitter del Client
     * @return Valore dell'email di Twitter del Client
     */
    public String getTwitterValue() {
        return twitterValue;
    }

    /**
     * Modifica email di Twitter con la stringa passata come parametro
     * @param twitterValue String con la quale cambiare email di Twitter
     */
    public void setTwitterValue(String twitterValue) {
        this.twitterValue = twitterValue;
    }
    
    /**
     * Restituisce il valore di Gmail del Client
     * @return Valore di Gmail del Client
     */
    public String getGmailValue() {
        return gmailValue;
    }

    /**
     * Modifica email di Gmail con la stringa passata come parametro
     * @param gmailValue String con la quale cambiare il valore di Gmail del Client
     */
    public void setGmailValue(String gmailValue) {
        this.gmailValue = gmailValue;
    }    
    
    /**
     * Resituisci il valore del campo name
     * @return Valore del campo name
     */
    public String getName() {
        return name;
    }

    /**
     * Modifica il campo name con il parametro passato
     * @param name String con la quale cambiare l'attributo name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Restituisce il valore del campo surname
     * @return Valore del campo surname
     */
    public String getSurname() {
        return surname;
    }

    /**
     * Modifica il campo surname con il parametro passato
     * @param surname String con la quale cambiare l'attributo surname
     */
    public void setSurname(String surname) {
        this.surname = surname;
    }

    /**
     * Restituisce il valore del campo gender
     * @return Valore del campo gender
     */
    public char getGender() {
        return gender;
    }

    /**
     * Modifica il campo gender con il parametro passato
     * @param gender Char con il quale cambiare l'attributo gender
     */
    public void setGender(char gender) {
        this.gender = gender;
    }

    /**
     * Restituisce il valore del campo age
     * @return Valore del campo age
     */
    public int getAge() {
        return age;
    }

    /**
     * Modifica il campo age con il parametro passato
     * @param age Int con il quale cambiare l'attributo age
     */
    public void setAge(int age) {
        this.age = age;
    }

    /**
     * Restituisce il valore del campo email
     * @return Valore del campo email
     */
    public String getEmail() {
        return email;
    }

    /**
     * Modifica il campo email con il parametro passato
     * @param email String con la quale cambiare il campo email
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Restituisce il valore del campo password
     * @return Valore del campo password
     */
    public String getPass() {
        return pass;
    }

    /**
     * Modifica il campo password con il parametro passato
     * @param pass String con la quale cambiare l'attributo password
     */
    public void setPass(String pass) {
        this.pass = pass;
    }

    /**
     * Restituisce il valore del campo path image
     * @return Valore del campo path image
     */
    public String getUrlPhoto() {
        return urlPhoto;
    }

    /**
     * Modifica il campo path image con il parametro passato
     * @param urlPhoto String con la quale cambiare l'attributo path image
     */
    public void setUrlPhoto(String urlPhoto) {
        this.urlPhoto = urlPhoto;
    }
    /**
     * Restituisce il valore dell'identificativo univoco del database
     * @return Valore dell'identificativo univoco
     */
    public Long getId() {
        return clientID;
    }
    
    /**
     * Restituisce il risultato intero di una funzione hash che codifica i parametri del cliente
     * @return Int che corrisponde alla risultato di una funzione hash. 
     */
    @Override
    public int hashCode() {
        int hash = 7;
        hash = 53 * hash + Objects.hashCode(this.clientID);
        hash = 53 * hash + Objects.hashCode(this.name);
        hash = 53 * hash + Objects.hashCode(this.surname);
        hash = 53 * hash + this.gender;
        hash = 53 * hash + this.age;
        hash = 53 * hash + Objects.hashCode(this.email);
        hash = 53 * hash + Objects.hashCode(this.pass);
        hash = 53 * hash + Objects.hashCode(this.urlPhoto);
        return hash;
    }

    /**
     * Restituisce la stringa che contiene tutti i dati di un cliente
     * @return String che contiene tutti i dati di un cliente
     */
    @Override
    public String toString() {
        return "Client{" + "clientID=" + clientID + ", name=" + name + ", surname=" + surname + ", gender=" + gender + ", age=" + age + ", email=" + email + ", pass=" + pass + ", urlPhoto=" + urlPhoto + ", gmailValue=" + gmailValue + ", twitterValue=" + twitterValue + '}';
    }   
     
}
