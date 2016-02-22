/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.conpartir.sessionBean;

import java.util.List;
import javax.ejb.Local;
import org.conpartir.entity.Client;
import org.conpartir.entity.Driver;
import org.conpartir.entity.Travel;

/**
 *
 * @author Blu Light
 */
@Local
public interface DriverManagerLocal {
    
    /**
     * Permette di creare una tupla nel database tramite un oggetto di tipo driver
     * @param driver 
     */
    public void createDriver(Driver driver);
    
    /**
     * Permette di creare una tupla nel database tramite i valori esplici
     */
    public void createDriver(String carModel, int carYear, Client client, List<Travel> travels);
    
    /**
     * Restituisce l'oggetto di tipo driver, se esiste, ricercato per ID
     * @param ID
     * @return 
     */
    public Driver getDriver(Long ID);
    
    /**
     * Restituisce il valore true o false se ID esiste nel DB
     * @param ID
     * @return 
     */
    public boolean isDriver(Long ID);
    
}
