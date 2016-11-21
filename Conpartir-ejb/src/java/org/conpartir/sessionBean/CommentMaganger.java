package org.conpartir.sessionBean;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import org.conpartir.entity.Comment;
import org.conpartir.facade.CommentFacadeLocal;

/**
 * Implementazione del SessionBean Stateless <i>CommentManager</i> per la
 * gestione che riguarda il Comment. Implemententa tutti i metodi esposti
 * nell'interfaccia CommentManagerLocal.
 *
 * @author Conpartir Group
 * @version 0.3 21 settembre 2016
 * @see <a href="http://docs.oracle.com/javaee/6/tutorial/doc/gipjg.html">
 * Session Bean </a>
 */
@Stateless
public class CommentMaganger implements CommentMagangerLocal {

    /**
     * EntityManager che rende persistenti le modiche del SessionBean.
     */
    @EJB
    private CommentFacadeLocal commentFacade;

    /**
     * Crea una tupla nel database tramite un oggetto di tipo comment
     *
     * @param comment da memorizzare nel database.
     */
    @Override
    public void createComment(Comment comment) {
        if (comment.getId_author() != null && comment.getId_clientJudged() != null
                && comment.getId_travel() != null && comment.getComment() != null
                && comment.getFeedback() != 0 && comment.getComment_date().toString() != null
                && comment.getCommet_hour().toString() != null) {
            commentFacade.create(comment);
        }
    }

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
     * @param comment_hour valore dell'attributo comment_hour del Entity
     * Commnent.
     */
    @Override
    public void createComment(Long id_author, Long id_clientJudged, Long id_travel,
            String comment, int feedback, Date comment_date, Date comment_hour) {
        if (id_author != null && id_clientJudged != null && id_travel != null
                && comment != null && feedback != 0 && comment_date.toString() != null
                && comment_hour.toString() != null) {
            Comment commento = new Comment();
            commento.setId_author(id_author);
            commento.setId_clientJudged(id_clientJudged);
            commento.setId_travel(id_travel);
            commento.setComment(comment);
            commento.setFeedback(feedback);
            commento.setComment_date(comment_date);
            commento.setCommet_hour(comment_hour);
            commentFacade.create(commento);
        }
    }

    /**
     * Restituisce la lista di commenti scritti da uno specifico cliente.
     *
     * @param id_client valore dell'identificativo univoco dell'autore del
     * commento.
     * @return List di oggetti Comment scritti.
     */
    @Override
    public List<Comment> getCommentWritten(Long id_client) {
        List<Comment> lista = new ArrayList();
        List<Comment> commenti = commentFacade.findAll();
        for (Comment commento : commenti) {
            if (commento.getId_author().equals(id_client)) {
                lista.add(commento);
            }
        }
        return sortListByDate(lista);
    }

    /**
     * Restituisce la lista di commenti che uno specifico cliente ha ricevuto.
     *
     * @param id_client valore dell'identificativo univoco del ricevente del
     * commento.
     * @return List di oggetti Comment scritti.
     */
    @Override
    public List<Comment> getCommentReceived(Long id_client) {
        List<Comment> lista = new ArrayList();
        List<Comment> commenti = commentFacade.findAll();
        for (Comment commento : commenti) {
            if (commento.getId_clientJudged().equals(id_client)) {
                lista.add(commento);
            }
        }
        return sortListByDate(lista);
    }

    /**
     * Calcola e restituisce la media del feedback di un client.
     *
     * @param id_client valore dell'identificativo univoco del client.
     * @return Int valore della media di Feedback.
     */
    @Override
    public int getAverageFeedback(Long id_client) {
        int somma = 0;
        int totFeed = 0;
        List<Comment> commenti = commentFacade.findAll();
        for (Comment commento : commenti) {
            if (commento.getId_clientJudged().equals(id_client)) {
                somma = somma + commento.getFeedback();
                totFeed++;
            }
        }
        if (totFeed == 0) {
            return totFeed;
        } else {
            return somma / totFeed;
        }
    }

    /**
     * Ordina una lista di commenti per la data in cui sono stati scritti.
     *
     * @param commenti da ordinare.
     * @return List di oggetti Comment odrinati per data.
     */
    protected List<Comment> sortListByDate(List<Comment> commenti) {
        Collections.sort(commenti, new Comparator<Comment>() {
            public int compare(Comment com1, Comment com2) {
                return com1.getComment_date().compareTo(com2.getComment_date());
            }
        });
        return commenti;
    }

}
