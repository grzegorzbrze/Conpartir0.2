package org.conpartir.facade;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.conpartir.entity.Taxi;

/**
 * Implementazione concreta dell'EntityManager <i>TaxiFacade</i> della classe
 * Entity Taxi. Estende l'EntityManager astratto AbstractFacade ed implementa
 * anche un'interfaccia TaxiFacadeLocal.
 *
 * @author Conpartir Group
 * @version 0.3 21 settembre 2016
 * @see <a href="http://docs.oracle.com/javaee/6/tutorial/doc/bnbqw.html">
 * EntityManager </a>
 *
 */
@Stateless
public class TaxiFacade extends AbstractFacade<Taxi> implements TaxiFacadeLocal {

    /**
     * Definisce il contesto in cui la Persistence può lavorare e l'oggetto
     * EntityManager concreto.
     */
    @PersistenceContext(unitName = "Conpartir-ejbPU")
    private EntityManager em;

    /**
     * Resituisce l'EntityManager concreto.
     *
     * @return EntityManager di questa classe.
     */
    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    /**
     * Costruttore publico che permette di inizializzare l'oggetto.
     */
    public TaxiFacade() {
        super(Taxi.class);
    }

}
