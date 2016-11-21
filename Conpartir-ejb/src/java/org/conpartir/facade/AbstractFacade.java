package org.conpartir.facade;

import java.util.List;
import javax.persistence.EntityManager;

/**
 * Implementazione della classe astratta <i>AbstractFacade</i> per offrire le
 * operazioni CRUD del EntityManager.
 *
 * @author Conpartir Group
 * @version 0.3 21 settembre 2016
 * @see <a href="http://docs.oracle.com/javaee/6/tutorial/doc/bnbqw.html">
 * EntityManager </a>
 */
public abstract class AbstractFacade<T> {

    /**
     * Definizione generica della classe entity.
     */
    private Class<T> entityClass;

    /**
     * Costruttore della classe astratta.
     *
     * @param entityClass con la quale modificare la classe entity.
     */
    public AbstractFacade(Class<T> entityClass) {
        this.entityClass = entityClass;
    }

    /**
     * Rende persistente l'entità nel database.
     *
     * @param entity da memorizzare nel database.
     */
    public void create(T entity) {
        getEntityManager().persist(entity);
    }

    /**
     * Rende persistenti le modifiche dell'entity nel database.
     *
     * @param entity da memorizzare nel database.
     */
    public void edit(T entity) {
        getEntityManager().merge(entity);
    }

    /**
     * Elimina l'entità dal database.
     *
     * @param entity da rimuovere dal database.
     */
    public void remove(T entity) {
        getEntityManager().remove(getEntityManager().merge(entity));
    }

    /**
     * Metodo astratto che restituisce l'EntityManager concreto.
     *
     * @return EntityManager il manager concreto.
     */
    protected abstract EntityManager getEntityManager();

    /**
     * Restituisce un oggetto T se esiste nella tabella ID ad esso associato.
     *
     * @param id object da usare come parametro di ricerca.
     * @return T risultato della ricerca.
     */
    public T find(Object id) {
        return getEntityManager().find(entityClass, id);
    }

    /**
     * Restituisce tutte le tuple della tabella che corrispondono ai criteri di
     * ricerca.
     *
     * @return List che contiene tutti i risultati individuati dalla ricerca.
     */
    public List<T> findAll() {
        javax.persistence.criteria.CriteriaQuery cq = getEntityManager().getCriteriaBuilder().createQuery();
        cq.select(cq.from(entityClass));
        return getEntityManager().createQuery(cq).getResultList();
    }

    /**
     * Restituisce tutte le tuple della tabella che corrispondono ai criteri di
     * ricerca e che si trovano entro un certo range di valori.
     *
     * @param range Array di valori interi con i quali specificare il numero
     * minimo e massimo di risulatati da restituire.
     * @return List che contiene tutti i risultati individuati dalla ricerca.
     */
    public List<T> findRange(int[] range) {
        javax.persistence.criteria.CriteriaQuery cq = getEntityManager().getCriteriaBuilder().createQuery();
        cq.select(cq.from(entityClass));
        javax.persistence.Query q = getEntityManager().createQuery(cq);
        q.setMaxResults(range[1] - range[0] + 1);
        q.setFirstResult(range[0]);
        return q.getResultList();
    }

    /**
     * Conta il numero delle occorrenze in base ad un criterio.
     *
     * @return Int che indica il numero delle occorrenze.
     */
    public int count() {
        javax.persistence.criteria.CriteriaQuery cq = getEntityManager().getCriteriaBuilder().createQuery();
        javax.persistence.criteria.Root<T> rt = cq.from(entityClass);
        cq.select(getEntityManager().getCriteriaBuilder().count(rt));
        javax.persistence.Query q = getEntityManager().createQuery(cq);
        return ((Long) q.getSingleResult()).intValue();
    }

}
