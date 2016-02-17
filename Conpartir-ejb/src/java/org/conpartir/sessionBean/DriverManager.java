/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.conpartir.sessionBean;

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
    public void cerateDriver(Driver driver) {
        driverFacade.create(driver);
    }
}
