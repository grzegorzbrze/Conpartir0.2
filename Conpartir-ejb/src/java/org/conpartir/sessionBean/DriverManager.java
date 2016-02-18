/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.conpartir.sessionBean;

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
        driverFacade.create(driver);
    }
    
    @Override
    public void createDriver(Long ID) {
        Driver nuovo = new Driver();
        nuovo.setDriver_id(ID);
        driverFacade.create(null);
    }

    @Override
    public String getDriver(Long ID) {
        String risposta = null;
       
        Driver risultato;
        risultato = driverFacade.find(ID);
        
        risposta= risultato.getDriver_id().toString() + risultato.getCarModel();
        
    return risposta;
    }
}
