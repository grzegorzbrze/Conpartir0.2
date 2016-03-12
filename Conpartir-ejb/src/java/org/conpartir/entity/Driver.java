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
public class Driver implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Column (name = "DRIVER_ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long driver_id;

    @Column(name="CLIENT_ID")
    private Long client_id;

    @Column (name = "CAR_MODEL")
    private String carModel;
    
    @Column (name = "CAR_YEAR")
    private int carYear;

    public Long getClient_id() {
        return client_id;
    }

    public void setClient_id(Long client_id) {
        this.client_id = client_id;
    }
    
    public Long getDriver_id() {
        return driver_id;
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
    public int hashCode() {
        int hash = 7;
        hash = 37 * hash + Objects.hashCode(this.driver_id);
        hash = 37 * hash + Objects.hashCode(this.client_id);
        hash = 37 * hash + Objects.hashCode(this.carModel);
        hash = 37 * hash + this.carYear;
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
        final Driver other = (Driver) obj;
        if (!Objects.equals(this.client_id, other.client_id)) {
            return false;
        }
        return true;
    }
    
    @Override
    public String toString() {
        return "Driver{" + "driver_id=" + driver_id + ", client=" + client_id + ", carModel=" + carModel + ", carYear=" + carYear + '}';
    }
    
}
