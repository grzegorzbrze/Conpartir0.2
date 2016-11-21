package org.conpartir.facade;

import java.util.List;
import javax.ejb.Local;
import org.conpartir.entity.Client;

/**
 * Interfaccia <i>ClientFacadeLocal</i> espone i metodi implementati dalla
 * classe ClientFacade.
 *
 * @author Conpartir Group
 * @version 0.3 21 settembre 2016
 * @see <a href="http://docs.oracle.com/javaee/6/tutorial/doc/bnbqw.html">
 * EntityManager </a>
 */
@Local
public interface ClientFacadeLocal {

    /**
     * Rende persistente l'entità Client nel database.
     *
     * @param client da memorizzare nel database.
     */
    void create(Client client);

    /**
     * Rende persistenti le modifiche dell'entity Client nel database.
     *
     * @param client da memorizzare nel database.
     */
    void edit(Client client);

    /**
     * Elimina l'entità Client dal database.
     *
     * @param client da rimuovere dal database.
     */
    void remove(Client client);

    /**
     * Restituisce un oggetto di tipo Client se esiste nella tabella ID ad esso
     * associato.
     *
     * @param id object da usare come parametro di ricerca.
     * @return Client risultato della ricerca.
     */
    Client find(Object id);

    /**
     * Restituisce tutte le tuple della tabella Client che corrispondono ai
     * criteri di ricerca.
     *
     * @return List di Client che contiene tutti i risultati individuati dalla
     * ricerca.
     */
    List<Client> findAll();

    /**
     * Restituisce tutte le tuple della tabella Client che corrispondono ai
     * criteri di ricerca e che si trovano entro un certo range di valori.
     *
     * @param range Array di valori interi con i quali specificare il numero
     * minimo e massimo di risulatati da restituire.
     * @return List di Client che contiene tutti i risultati individuati dalla
     * ricerca.
     */
    List<Client> findRange(int[] range);

    /**
     * Conta il numero delle occorrenze in base ad un criterio.
     *
     * @return Int che indica il numero delle occorrenze.
     */
    int count();

}
