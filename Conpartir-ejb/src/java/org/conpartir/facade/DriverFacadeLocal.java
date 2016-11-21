package org.conpartir.facade;

import java.util.List;
import javax.ejb.Local;
import org.conpartir.entity.Driver;

/**
 * Interfaccia <i>DriverFacadeLocal</i> espone i metodi implementati dalla
 * classe DriverFacade.
 *
 * @author Conpartir Group
 * @version 0.3 21 settembre 2016
 * @see <a href="http://docs.oracle.com/javaee/6/tutorial/doc/bnbqw.html">
 * EntityManager </a>
 */
@Local
public interface DriverFacadeLocal {

    /**
     * Rende persistente l'entità Driver nel database.
     *
     * @param driver da memorizzare nel database.
     */
    void create(Driver driver);

    /**
     * Rende persistenti le modifiche dell'entity Driver nel database.
     *
     * @param driver da memorizzare nel database.
     */
    void edit(Driver driver);

    /**
     * Elimina l'entità Driver dal database.
     *
     * @param driver da rimuovere dal database.
     */
    void remove(Driver driver);

    /**
     * Restituisce un oggetto di tipo Driver se esiste nella tabella ID ad esso
     * associato.
     *
     * @param id object da usare come parametro di ricerca.
     * @return Driver risultato della ricerca.
     */
    Driver find(Object id);

    /**
     * Restituisce tutte le tuple della tabella Driver che corrispondono ai
     * criteri di ricerca.
     *
     * @return List di Driver che contiene tutti i risultati individuati dalla
     * ricerca.
     */
    List<Driver> findAll();

    /**
     * Restituisce tutte le tuple della tabella Driver che corrispondono ai
     * criteri di ricerca e che si trovano entro un certo range di valori.
     *
     * @param range Array di valori interi con i quali specificare il numero
     * minimo e massimo di risulatati da restituire.
     * @return List di Driver che contiene tutti i risultati individuati dalla
     * ricerca.
     */
    List<Driver> findRange(int[] range);

    /**
     * Conta il numero delle occorrenze in base ad un criterio.
     *
     * @return Int che indica il numero delle occorrenze.
     */
    int count();

}
