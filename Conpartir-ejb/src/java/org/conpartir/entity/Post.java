/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.conpartir.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Blu Light
 */
@Entity
@XmlRootElement
public class Post implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Column (name = "POST_ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long post_id;
    
    @ManyToOne
    @JoinColumn (name = "DRIVER_ID")
    private Driver driver;
    
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

    public Long getPost_id() {
        return post_id;
    }

    public Driver getDriver() {
        return driver;
    }

    public void setDriver(Driver driver) {
        this.driver = driver;
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
        String hour = cal.get(Calendar.HOUR) +":"+cal.get(Calendar.MINUTE)+":" + cal.get(Calendar.SECOND);
        return hour;
    }
    
    public String getDataString(){
        Calendar cal = getCalendarData();
        String laData = cal.get(Calendar.YEAR)+"/"+cal.get(Calendar.MONTH)+"/"+cal.get(Calendar.DAY_OF_MONTH);
        return laData;
    }
    
    @Override
    public int hashCode() {
        int hash = 5;
        hash = 41 * hash + Objects.hashCode(this.post_id);
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
        final Post other = (Post) obj;
        if (!Objects.equals(this.post_id, other.post_id)) {
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
        return "Post{" + "post_id=" + post_id + ", driver=" + driver.toString() + 
                ", client_id=" + client_id + ", data=" + getDataString() + ", time=" + getTimeString()+
                ", origin=" + origin + ", destination=" + destination + '}';
    }
        
}
