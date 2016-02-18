/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.conpartir.sessionBean;

import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import org.conpartir.entity.Client;
import org.conpartir.entity.Driver;
import org.conpartir.entity.Post;
import org.conpartir.facade.DriverFacadeLocal;

/**
 *
 * @author Blu Light
 */
@Stateless
public class DriverManager implements DriverManagerLocal {
    @EJB
    private DriverFacadeLocal driverFacade;

    @Override
    public void createDriver(Driver driver) {
        driverFacade.create(driver);
    }

    @Override
    public void createDriver(String carModel, int carYear, Client client, List<Post> posts) {
        Driver driver = new Driver();
        driver.setCarModel(carModel);
        driver.setCarYear(carYear);
        driver.setClient(client);
        driver.setPosts(posts);
        driverFacade.create(driver);
    }

    @Override
    public Driver getDriver(Long ID) {
        Driver driver = new Driver();
        List<Driver> list = driverFacade.findAll();
        for (Driver temp : list){
            Long tempID = temp.getDriver_id();
            if (tempID.equals(ID)){
                driver = temp;            
            }
        }
        return driver;
    }

    @Override
    public boolean isDriver(Long ID) {
        boolean risultato = false;
        List<Driver> list = driverFacade.findAll();
        for (Driver temp : list){
            Long tempID = temp.getDriver_id();
            if (tempID.equals(ID)){
                risultato = true;
            }
        }
        return risultato;    
    }
        
}
