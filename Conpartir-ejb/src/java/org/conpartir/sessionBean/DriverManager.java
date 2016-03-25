/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.conpartir.sessionBean;

import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import org.conpartir.entity.Driver;
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
        if (!isExist(driver.getCarModel(), driver.getCarYear(), driver.getClient_id())){
            driverFacade.create(driver);
        }
    }

    @Override
    public void createDriver(String carModel, int carYear, Long client_id) {
        if (!isExist(carModel, carYear, client_id)){
            Driver driver = new Driver();
        driver.setCarModel(carModel);
        driver.setCarYear(carYear);
        driver.setClient_id(client_id);
        driverFacade.create(driver);
        } 
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
    public Driver getDriver(String carModel, int carYear, Long client_id) {
        Driver driver = new Driver();
        List<Driver> lista = driverFacade.findAll();
        for (Driver temp : lista){
            if (temp.getCarModel().equals(carModel) && temp.getCarYear() == carYear 
                    && temp.getClient_id().equals(client_id)){
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
    
    
    
    protected boolean isExist(String carModel, int carYear, Long client_id){
        boolean risultato = true;
        Driver driver = getDriver(carModel, carYear, client_id);
        if (driver.getDriver_id() == null){
            risultato = false;
        }
        return risultato;
    }

    @Override
    public List<Driver> getDrivers(Long client_id) {
        List<Driver> drivers = new ArrayList();
        List<Driver> lista = driverFacade.findAll();
        for (Driver driver : lista){
            if (driver.getClient_id().equals(client_id)){
                drivers.add(driver);
            }
        }
        return drivers;
    }

}
