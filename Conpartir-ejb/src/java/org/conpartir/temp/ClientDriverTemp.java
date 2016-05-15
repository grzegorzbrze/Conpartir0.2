/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.conpartir.temp;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

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

    public String getCarModel() {
        return carModel;
    }

    public void setCarModel(String carModel) {
        this.carModel = carModel;
    }

    public int getCarYear() {
        return carYear;
    }

    public void setCarYear(int carYear) {
        this.carYear = carYear;
    }

    @Override
    public String toString() {
        return "ClientDriverTemp{" + "name=" + name + ", surname=" + surname + ", age=" + age + ", email=" + email + ", gender=" + gender + ", urlPhoto=" + urlPhoto + ", carModel=" + carModel + ", carYear=" + carYear + '}';
    }
    
}
