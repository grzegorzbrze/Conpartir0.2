package org.conpartir.facade;

import java.util.List;
import javax.ejb.Local;
import org.conpartir.entity.Comment;

/**
 * Interfaccia <i>CommentFacadeLocal</i> espone i metodi implementati dalla
 * classe CommentFacade.
 *
 * @author Conpartir Group
 * @version 0.3 21 settembre 2016
 * @see <a href="http://docs.oracle.com/javaee/6/tutorial/doc/bnbqw.html">
 * EntityManager </a>
 */
@Local
public interface CommentFacadeLocal {

    /**
     * Rende persistente l'entità Comment nel database.
     *
     * @param comment da memorizzare nel database.
     */
    void create(Comment comment);

    /**
     * Rende persistenti le modifiche dell'entity Comment nel database.
     *
     * @param comment da memorizzare nel database.
     */
    void edit(Comment comment);

    /**
     * Elimina l'entità Comment dal database.
     *
     * @param comment da rimuovere dal database.
     */
    void remove(Comment comment);

    /**
     * Restituisce un oggetto di tipo Comment se esiste nella tabella ID ad esso
     * associato.
     *
     * @param id object da usare come parametro di ricerca.
     * @return Comment risultato della ricerca.
     */
    Comment find(Object id);

    /**
     * Restituisce tutte le tuple della tabella Comment che corrispondono ai
     * criteri di ricerca.
     *
     * @return List di Comment che contiene tutti i risultati individuati dalla
     * ricerca.
     */
    List<Comment> findAll();

    /**
     * Restituisce tutte le tuple della tabella Comment che corrispondono ai
     * criteri di ricerca e che si trovano entro un certo range di valori.
     *
     * @param range Array di valori interi con i quali specificare il numero
     * minimo e massimo di risulatati da restituire.
     * @return List di Comment che contiene tutti i risultati individuati dalla
     * ricerca.
     */
    List<Comment> findRange(int[] range);

    /**
     * Conta il numero delle occorrenze in base ad un criterio.
     *
     * @return Int che indica il numero delle occorrenze.
     */
    int count();

}
