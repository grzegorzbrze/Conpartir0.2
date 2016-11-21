package org.conpartir.facade;

import java.util.List;
import javax.ejb.Local;
import org.conpartir.entity.Taxi;

/**
 * Interfaccia <i>TaxiFacadeLocal</i> espone i metodi implementati dalla classe
 * TaxiFacade.
 *
 * @author Conpartir Group
 * @version 0.3 21 settembre 2016
 * @see <a href="http://docs.oracle.com/javaee/6/tutorial/doc/bnbqw.html">
 * EntityManager </a>
 */
@Local
public interface TaxiFacadeLocal {

    /**
     * Rende persistente l'entità Taxi nel database.
     *
     * @param taxi da memorizzare nel database.
     */
    void create(Taxi taxi);

    /**
     * Rende persistenti le modifiche dell'entity Taxi nel database.
     *
     * @param taxi da memorizzare nel database.
     */
    void edit(Taxi taxi);

    /**
     * Elimina l'entità Taxi dal database.
     *
     * @param taxi da rimuovere dal database.
     */
    void remove(Taxi taxi);

    /**
     * Restituisce un oggetto di tipo Taxi se esiste nella tabella ID ad esso
     * associato.
     *
     * @param id object da usare come parametro di ricerca.
     * @return Taxi risultato della ricerca.
     */
    Taxi find(Object id);

    /**
     * Restituisce tutte le tuple della tabella Taxi che corrispondono ai
     * criteri di ricerca.
     *
     * @return List di Taxi che contiene tutti i risultati individuati dalla
     * ricerca.
     */
    List<Taxi> findAll();

    /**
     * Restituisce tutte le tuple della tabella Taxi che corrispondono ai
     * criteri di ricerca e che si trovano entro un certo range di valori.
     *
     * @param range Array di valori interi con i quali specificare il numero
     * minimo e massimo di risulatati da restituire.
     * @return List di Taxi che contiene tutti i risultati individuati dalla
     * ricerca.
     */
    List<Taxi> findRange(int[] range);

    /**
     * Conta il numero delle occorrenze in base ad un criterio.
     *
     * @return Int che indica il numero delle occorrenze.
     */
    int count();

}
