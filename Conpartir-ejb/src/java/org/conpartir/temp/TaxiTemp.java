/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.conpartir.temp;
import java.util.Date;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
/**
 *
 * @author Matteo
 */

@XmlRootElement(name = "TaxiTemp")
@XmlType(propOrder = {"data","time","destination","origin","freeSeats","taxi_id"})

public class TaxiTemp {

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

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public String getOrigin() {
        return origin;
    }

    public void setOrigin(String origin) {
        this.origin = origin;
    }

    public int getFreeSeats() {
        return freeSeats;
    }

    public void setFreeSeats(int freeSeats) {
        this.freeSeats = freeSeats;
    }

    public Long getTaxi_id() {
        return taxi_id;
    }

    public void setTaxi_id(Long travel_id) {
        this.taxi_id = travel_id;
    }
    
    private Date data;
    private Date time;
    private String destination;
    private String origin;
    private int freeSeats;
    private Long taxi_id;
    
    
}
