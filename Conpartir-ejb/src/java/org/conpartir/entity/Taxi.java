/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.conpartir.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Temporal;

/**
 *
 * @author Blu Light
 */
@Entity
public class Taxi implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long taxi_id;

    @Column (name = "CLIENT_ID")
    private Long client_id;
    
    @Column(name = "CREATOR_ID")
    private Long creator_id;
    
    @Column (name = "ORIGIN")
    private String origin;
    
    @Column (name = "DESTINATION")
    private String destination;
    
    @Column (name = "DATA")
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date data;
    
    @Column(name = "TIME")
    @Temporal(javax.persistence.TemporalType.TIME)
    private Date time;
    
    @Column (name = "FREE_SEATS")
    private int freeSeats;

    public Long getTaxi_id() {
        return taxi_id;
    }

    public void setTaxi_id(Long taxi_id) {
        this.taxi_id = taxi_id;
    }
    
    public Long getCreator_id() {
        return creator_id;
    }

    public void setCreator_id(Long creator_id) {
        this.creator_id = creator_id;
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

    public int getFreeSeats() {
        return freeSeats;
    }

    public void setFreeSeats(int freeSeats) {
        this.freeSeats = freeSeats;
    }

    public Long getClient_id() {
        return client_id;
    }

    public void setClient_id(Long client_id) {
        this.client_id = client_id;
    }
  
    @Override
    public String toString() {
        return "Taxi{" + "id=" + taxi_id + ", client_id=" + client_id + ", creator_id=" 
                + creator_id + ", origin=" + origin + ", destination=" + destination 
                + ", data=" + data + ", time=" + time + ", freeSeats=" + freeSeats + '}';
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 31 * hash + Objects.hashCode(this.taxi_id);
        hash = 31 * hash + Objects.hashCode(this.origin);
        hash = 31 * hash + Objects.hashCode(this.destination);
        hash = 31 * hash + Objects.hashCode(this.data);
        hash = 31 * hash + Objects.hashCode(this.time);
        hash = 31 * hash + this.freeSeats;
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
        final Taxi other = (Taxi) obj;
        if (!Objects.equals(this.taxi_id, other.taxi_id)) {
            return false;
        }
        if (!Objects.equals(this.creator_id, other.creator_id)) {
            return false;
        }
        if (!Objects.equals(this.origin, other.origin)) {
            return false;
        }
        if (!Objects.equals(this.destination, other.destination)) {
            return false;
        }
        if (!Objects.equals(this.data, other.data)) {
            return false;
        }
        if (!Objects.equals(this.time, other.time)) {
            return false;
        }
        if (this.freeSeats != other.freeSeats) {
            return false;
        }
        return true;
    }

   
}
