/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.conpartir.facade;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.conpartir.entity.Taxi;

/**
 *
 * @author Blu Light
 */
@Stateless
public class TaxiFacade extends AbstractFacade<Taxi> implements TaxiFacadeLocal {
    @PersistenceContext(unitName = "Conpartir-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public TaxiFacade() {
        super(Taxi.class);
    }
    
}
