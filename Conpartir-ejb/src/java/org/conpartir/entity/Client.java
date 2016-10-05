/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
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
 *
 * @author Blu Light
 */
@Entity
@XmlRootElement
public class Client implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Column (name = "CLIENT_ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long clientID;

    @Column (name = "NAME")
    private String name; 
    
    @Column (name = "SURNAME")
    private String surname;
    
    @Column (name = "GENDER")
    private char gender;
    
    @Column (name = "AGE")
    private int age;
    
    @Column (name = "EMAIL", unique = true)
    private String email;
    
    @Column (name = "PASS")
    private String pass;
    
    @Column (name = "URLPHOTO")
    private String urlPhoto;

    @Column (name = "GMAIL")
    private boolean gmail;
    
    @Column (name = "GMAILVALUE")
    private String gmailValue;

    public String getGmailValue() {
        return gmailValue;
    }

    public void setGmailValue(String gmailValue) {
        this.gmailValue = gmailValue;
    }    
    
        
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public char getGender() {
        return gender;
    }

    public void setGender(char gender) {
        this.gender = gender;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public String getUrlPhoto() {
        return urlPhoto;
    }

    public void setUrlPhoto(String urlPhoto) {
        this.urlPhoto = urlPhoto;
    }
    
    public Long getId() {
        return clientID;
    }
    
    public void setGmail(boolean gmail) {
        this.gmail = gmail;
    }
    
    public boolean getGmail() {
        return gmail;
    }
    
    
    

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
        //hash = 53 * hash + Objects.hashCode(this.drivers);
        return hash;
    }

    /**
     * Due client sono uguali se hanno la stessa email. 
     * obj deve essere di tipo Client.
     * @param obj
     * @return 
     */
    
    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Client other = (Client) obj;
        if (!Objects.equals(this.email, other.email)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Client{ " + "clientID=" + clientID + ", name=" + name + ", surname=" 
                + surname + ", gender=" + gender + ", age=" + age + ", email=" 
                + email + ", pass=" + pass + ", urlPhoto=" + urlPhoto + "}";
    }
    
    
}
