package org.conpartir.sessionBean;

import java.util.Date;
import java.util.List;
import javax.ejb.Local;
import org.conpartir.entity.Comment;

/**
 * Interfaccia locale <i>CommentManagerLocal</i> che espone tutti metodi che
 * possono essere applicati al Session Bean.
 *
 * @author Conpartir Group
 * @version 0.3 21 settembre 2016
 * @see <a href="http://docs.oracle.com/javaee/6/tutorial/doc/gipjg.html">
 * Session Bean </a>
 */
@Local
public interface CommentMagangerLocal {
    
    /**
     * Crea una tupla nel database tramite un oggetto di tipo comment
     *
     * @param comment da memorizzare nel database.
     */
    public void createComment(Comment comment);
    
    /**
     * Crea una tupla nel database tramite gli argomenti esplici.
     *
     * @param id_author valore dell'identificativo univoco dell'autore del
     * commento.
     * @param id_clientJudged valore dell'identificativo univoco del cliente
     * giudicato dal commento.
     * @param id_travel valore dell'identificativo univoco del viaggio giudicato
     * dal commento.
     * @param comment valore dell'attributo comment del Entity Commnent.
     * @param feedback valore dell'attributo feedback del Entity Commnent.
     * @param comment_date valore dell'attributo comment_date del Entity
     * Commnent.
     * @param commet_hour valore dell'attributo comment_hour del Entity
     * Commnent.
     */
    public void createComment(Long id_author, Long id_clientJudged, Long id_travel, 
            String comment, int feedback, Date comment_date, Date commet_hour);
    
     /**
     * Restituisce la lista di commenti scritti da uno specifico cliente.
     *
     * @param id_client valore dell'identificativo univoco dell'autore del
     * commento.
     * @return List di oggetti Comment scritti.
     */
    public List<Comment> getCommentWritten(Long id_client);
    
    /**
     * Restituisce la lista di commenti che uno specifico cliente ha ricevuto.
     *
     * @param id_client valore dell'identificativo univoco del ricevente del
     * commento.
     * @return List di oggetti Comment scritti.
     */
    public List<Comment> getCommentReceived(Long id_client);
    
    /**
     * Calcola e restituisce la media del feedback di un client.
     *
     * @param id_client valore dell'identificativo univoco del client.
     * @return Int valore della media di Feedback.
     */
    public int getAverageFeedback(Long id_client);
    
}
