package org.conpartir.facade;

import java.util.List;
import javax.ejb.Local;
import org.conpartir.entity.Travel;

/**
 * Interfaccia <i>TravelFacadeLocal</i> espone i metodi implementati dalla
 * classe TravelFacade.
 *
 * @author Conpartir Group
 * @version 0.3 21 settembre 2016
 * @see <a href="http://docs.oracle.com/javaee/6/tutorial/doc/bnbqw.html">
 * EntityManager </a>
 */
@Local
public interface TravelFacadeLocal {

    /**
     * Rende persistente l'entità Travel nel database.
     *
     * @param travel da memorizzare nel database.
     */
    void create(Travel travel);

    /**
     * Rende persistenti le modifiche dell'entity Travel nel database.
     *
     * @param travel da memorizzare nel database.
     */
    void edit(Travel travel);

    /**
     * Elimina l'entità Travel dal database.
     *
     * @param travel da rimuovere dal database.
     */
    void remove(Travel travel);

    /**
     * Restituisce un oggetto di tipo Travel se esiste nella tabella ID ad esso
     * associato.
     *
     * @param id object da usare come parametro di ricerca.
     * @return Travel risultato della ricerca.
     */
    Travel find(Object id);

    /**
     * Restituisce tutte le tuple della tabella Travel che corrispondono ai
     * criteri di ricerca.
     *
     * @return List di Travel che contiene tutti i risultati individuati dalla
     * ricerca.
     */
    List<Travel> findAll();

    /**
     * Restituisce tutte le tuple della tabella Travel che corrispondono ai
     * criteri di ricerca e che si trovano entro un certo range di valori.
     *
     * @param range Array di valori interi con i quali specificare il numero
     * minimo e massimo di risulatati da restituire.
     * @return List di Travel che contiene tutti i risultati individuati dalla
     * ricerca.
     */
    List<Travel> findRange(int[] range);

    /**
     * Conta il numero delle occorrenze in base ad un criterio.
     *
     * @return Int che indica il numero delle occorrenze.
     */
    int count();

}
