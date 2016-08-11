/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.conpartir.temp;

import java.util.List;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import org.conpartir.entity.Client;
import org.conpartir.entity.Driver;
/**
 *
 * @author Matteo
 */

@XmlRootElement(name = "TravelDataTemp")
@XmlType(propOrder = {"driverInfo","driverCar","passengers"})
public class TravelDataTemp {

    public Client getDriverInfo() {
        return driverInfo;
    }

    public void setDriverInfo(Client driverInfo) {
        this.driverInfo = driverInfo;
    }

    public Driver getDriverCar() {
        return driverCar;
    }

    public void setDriverCar(Driver driverCar) {
        this.driverCar = driverCar;
    }

    public List<Client> getPassengers() {
        return passengers;
    }

    public void setPassengers(List<Client> passengers) {
        this.passengers = passengers;
    }
      
    private Client driverInfo;
    
    private Driver driverCar;
    
    private List<Client> passengers; 
    
}

    
