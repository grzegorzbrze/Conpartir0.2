/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.conpartir.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

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

    @ManyToOne
    @JoinColumn(name="CLIENT_ID")
    private Client client;

    @Column (name = "CAR_MODEL")
    private String carModel;
    
    @Column (name = "CAR_YEAR")
    private int carYear;
    
    @OneToMany (mappedBy = "driver", cascade = CascadeType.ALL)
    private List<Post> posts = new ArrayList();

    @XmlTransient
    public List<Post> getPosts() {
        return posts;
    }

    public void setPosts(List<Post> posts) {
        this.posts = posts;
    }
        
    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public Long getDriver_id() {
        return driver_id;
    }

    public void setDriver_id(Long driver_id) {
        this.driver_id = driver_id;
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
        hash = 37 * hash + Objects.hashCode(this.client);
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
        if (!Objects.equals(this.client, other.client)) {
            return false;
        }
        if (!Objects.equals(this.carModel, other.carModel)) {
            return false;
        }
        if (this.carYear != other.carYear) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Driver{" + "driver_id=" + driver_id + ", client=" + client + ", carModel=" + carModel + ", carYear=" + carYear + '}';
    }
    
}
