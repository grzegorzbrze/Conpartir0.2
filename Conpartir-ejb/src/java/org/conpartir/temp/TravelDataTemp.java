package org.conpartir.temp;

import java.util.List;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import org.conpartir.entity.Client;
import org.conpartir.entity.Driver;

/**
 * Implementazione della classe <i>TravelDataTemp</i> per la gestione di un
 * oggetto composto in formato XML.
 *
 * @author Conpartir Group
 * @version 0.3 21 settembre 2016
 * @see
 * <a href="http://docs.oracle.com/javaee/5/api/javax/xml/bind/annotation/XmlRootElement.html">
 * XmlRootElement </a>
 */
@XmlRootElement(name = "TravelDataTemp")
@XmlType(propOrder = {"driverInfo", "driverCar", "passengers"})
public class TravelDataTemp {

    private Client driverInfo;
    private Driver driverCar;
    private List<Client> passengers;

    /**
     * Restituisce l'oggetto Client relativo al driver
     *
     * @return Client
     */
    public Client getDriverInfo() {
        return driverInfo;
    }

    /**
     * Setta l'oggetto Client relativo al driver
     *
     * @param driverInfo Client
     */
    public void setDriverInfo(Client driverInfo) {
        this.driverInfo = driverInfo;
    }

    /**
     * Restituisce l'oggetto driver relativo
     *
     * @return Driver
     */
    public Driver getDriverCar() {
        return driverCar;
    }

    /**
     * Setta l'oggetto driver relativo
     *
     * @param driverCar Driver
     */
    public void setDriverCar(Driver driverCar) {
        this.driverCar = driverCar;
    }

    /**
     * Restituisce la lista di passeggeri del viaggio
     *
     * @return List di Client
     */
    public List<Client> getPassengers() {
        return passengers;
    }

    /**
     * Setta la lista di passeggeri del viaggio
     *
     * @param passengers List di Client
     */
    public void setPassengers(List<Client> passengers) {
        this.passengers = passengers;
    }

}
