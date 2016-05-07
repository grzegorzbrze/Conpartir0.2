/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.conpartir.temp;

import java.util.List;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
/**
 *
 * @author Matteo
 */

@XmlRootElement(name = "AccountDataTemp")
@XmlType(propOrder = {"name", "surname", "age", "email", "gender", "urlPhoto", "drivers","postedTravels","bookedTravels","postedTaxis","bookedTaxis"})
public class AccountDataTemp {
    
    private String name;
    
    private String surname;
    
    private Integer age;
    
    private String email;
    
    private char gender;
    
    private String urlPhoto;
    
    private List<DriverTemp> drivers; 
    
    private List<TravelTemp> postedTravels;
    
    private List<TravelTemp> bookedTravels;
    
    private List<TaxiTemp> postedTaxis;
    
    private List<TaxiTemp> bookedTaxis;

    public List<TaxiTemp> getPostedTaxis() {
        return postedTaxis;
    }

    public void setPostedTaxis(List<TaxiTemp> postedTaxis) {
        this.postedTaxis = postedTaxis;
    }

    public List<TaxiTemp> getBookedTaxis() {
        return bookedTaxis;
    }

    public void setBookedTaxis(List<TaxiTemp> bookedTaxis) {
        this.bookedTaxis = bookedTaxis;
    }
    


    public List<TravelTemp> getPostedTravels() {
        return postedTravels;
    }

    public void setPostedTravels(List<TravelTemp> postedTravels) {
        this.postedTravels = postedTravels;
    }

    public List<TravelTemp> getBookedTravels() {
        return bookedTravels;
    }

    public void setBookedTravels(List<TravelTemp> bookedTravels) {
        this.bookedTravels = bookedTravels;
    }
    
    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public char getGender() {
        return gender;
    }

    public void setGender(char gender) {
        this.gender = gender;
    }

    public String getUrlPhoto() {
        return urlPhoto;
    }

    public void setUrlPhoto(String urlPhoto) {
        this.urlPhoto = urlPhoto;
    }


//            <return>
//            <carModel>Opel Corsa</carModel>
//            <carYear>2005</carYear>
//            <client_id>6</client_id>
//            <driver_id>5</driver_id>
//            </return>

    public String getName() {
        return name;
    }

    public void setName(String nomeUtente) {
        this.name = nomeUtente;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String cognomeUtente) {
        this.surname = cognomeUtente;
    }
    
     public List<DriverTemp> getDrivers() {
        return drivers;
    }

    public void setDrivers(List<DriverTemp> driver) {
        this.drivers = driver;
    }
    

//    @Override
//    public String toString() {
//        return "AccountDataTemp{" + "nomeAutore=" + name + ", cognomeAutore=" + surname + '}';
//    }
    
    
}

    

