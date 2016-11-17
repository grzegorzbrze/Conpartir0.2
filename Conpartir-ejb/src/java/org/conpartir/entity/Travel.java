/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
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
 * Implementazione di serializzable per definire <i>Travel</i> memoriazzato nel database grazie all'Entity. 
 * @author Conpartir Group
 * @version 0.3 11 settembre 2016
 * @see <a href="http://docs.oracle.com/javaee/6/tutorial/doc/bnbqa.html"> Entity </a>
 * @see <a href="https://docs.oracle.com/javase/7/docs/api/java/io/Serializable.html"> Serializable </a>
 * 
 */
@Entity
@XmlRootElement
public class Travel implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Column (name = "TRAVEL_ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long travel_id;
    
    @Column (name = "DRIVER_ID")
    private Long driver_id;
    
    @Column (name = "CLIENT_ID")
    private Long client_id;

    @Column (name = "DATA")
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date data;
    
    @Column(name = "TIME")
    @Temporal(javax.persistence.TemporalType.TIME)
    private Date time;
    
    @Column (name = "ORIGIN")
    private String origin;
    
    @Column (name = "DESTINATION")
    private String destination;

    @Column (name = "FREE_SEATS")
    private int freeSeats;
    
    public void setTravel_id(Long travel_id) {
        this.travel_id = travel_id;
    }
    
    public Long getTravel_id() {
        return travel_id;
    }

    public Long getDriver_id() {
        return driver_id;
    }

    public void setDriver_id(Long driver_id) {
        this.driver_id = driver_id;
    }
    
    public Long getClient_id() {
        return client_id;
    }

    public void setClient_id(Long client_id) {
        this.client_id = client_id;
    }

    public Date getData() {
        return data;
    }

    public void setData(Date data) {
        this.data = data;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public String getOrigin() {
        return origin;
    }

    public void setOrigin(String origin) {
        this.origin = origin;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public int getFreeSeats() {
        return freeSeats;
    }

    public void setFreeSeats(int freeSeats) {
        this.freeSeats = freeSeats;
    }

    public Calendar getCalendarData(){
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(data);
        return calendar;
    }
    
    public Calendar getCalendarTime(){
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(time);
        return calendar;
    }
    
    public String getTimeString(){
        Calendar cal = getCalendarTime();
        String hour = cal.get(Calendar.HOUR) +":"+cal.get(Calendar.MINUTE)+":" 
                + cal.get(Calendar.SECOND);
        return hour;
    }
    
    public String getDataString(){
        Calendar cal = getCalendarData();
        String laData = cal.get(Calendar.YEAR)+"/"+cal.get(Calendar.MONTH)+"/"
                +cal.get(Calendar.DAY_OF_MONTH);
        return laData;
    }
    
    @Override
    public int hashCode() {
        int hash = 5;
        hash = 41 * hash + Objects.hashCode(this.travel_id);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Travel other = (Travel) obj;
        if (!Objects.equals(this.travel_id, other.travel_id)) {
            return false;
        }
        if (!Objects.equals(this.data, other.data)) {
            return false;
        }
        if (!Objects.equals(this.time, other.time)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Travel{" + "travel_id=" + travel_id + ", driver=" + driver_id + 
                ", client_id=" + client_id + ", data=" + getDataString() + ", time=" + getTimeString()+
                ", origin=" + origin + ", destination=" + destination + ", freeSeats=" +freeSeats+'}';
    }
        
}
