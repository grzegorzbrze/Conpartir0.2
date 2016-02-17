/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.conpartir.facade;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.conpartir.entity.Driver;

/**
 *
 * @author Blu Light
 */
@Stateless
public class DriverFacade extends AbstractFacade<Driver> implements DriverFacadeLocal {
    @PersistenceContext(unitName = "Conpartir-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public DriverFacade() {
        super(Driver.class);
    }
    
}
