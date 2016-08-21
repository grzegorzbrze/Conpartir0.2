/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.conpartir.temp;

import java.util.List;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import org.conpartir.entity.Driver;
import org.conpartir.entity.Taxi;
import org.conpartir.entity.Travel;
/**
 *
 * @author Matteo
 */

@XmlRootElement(name = "AccountDataTemp")
@XmlType(propOrder = {"name", "surname", "age", "email", "gender", "urlPhoto","gmail","drivers","postedTravels","bookedTravels","postedTaxis","bookedTaxis"})
public class AccountDataTemp {
    
    private String name;
    
    private String surname;
    
    private Integer age;
    
    private String email;
    
    private char gender;
    
    private String urlPhoto;

    private boolean gmail;
    
    private List<Driver> drivers; 
    
    private List<Travel> postedTravels;
    
    private List<Travel> bookedTravels;
    
    private List<Taxi> postedTaxis;
    
    private List<Taxi> bookedTaxis;

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
    
    
    public boolean isGmail() {
        return gmail;
    }

    public void setGmail(boolean gmail) {
        this.gmail = gmail;
    }
    

    public List<Driver> getDrivers() {
        return drivers;
    }

    public void setDrivers(List<Driver> drivers) {
        this.drivers = drivers;
    }

    public List<Travel> getPostedTravels() {
        return postedTravels;
    }

    public void setPostedTravels(List<Travel> postedTravels) {
        this.postedTravels = postedTravels;
    }

    public List<Travel> getBookedTravels() {
        return bookedTravels;
    }

    public void setBookedTravels(List<Travel> bookedTravels) {
        this.bookedTravels = bookedTravels;
    }

    public List<Taxi> getPostedTaxis() {
        return postedTaxis;
    }

    public void setPostedTaxis(List<Taxi> postedTaxis) {
        this.postedTaxis = postedTaxis;
    }

    public List<Taxi> getBookedTaxis() {
        return bookedTaxis;
    }

    public void setBookedTaxis(List<Taxi> bookedTaxis) {
        this.bookedTaxis = bookedTaxis;
    }

    @Override
    public String toString() {
        return "AccountDataTemp{" + "name=" + name + ", surname=" + surname + ", age=" + age + ", email=" + email + ", gender=" + gender + ", urlPhoto=" + urlPhoto + ", drivers=" + drivers + ", postedTravels=" + postedTravels + ", bookedTravels=" + bookedTravels + ", postedTaxis=" + postedTaxis + ", bookedTaxis=" + bookedTaxis + '}';
    }   
    
}

    

