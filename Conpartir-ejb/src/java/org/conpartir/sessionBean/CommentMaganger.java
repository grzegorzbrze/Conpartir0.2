/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
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
 *
 * @author Blu Light
 */
@Stateless
public class CommentMaganger implements CommentMagangerLocal {
    @EJB
    private CommentFacadeLocal commentFacade;

    
    
    @Override
    public void createComment(Comment comment) {
        if (comment.getId_author() != null && comment.getId_clientJudged() != null
                && comment.getId_travel() != null && comment.getComment() != null
                && comment.getFeedback() != 0 && comment.getComment_date().toString() != null
                && comment.getCommet_hour().toString() != null){
            commentFacade.create(comment);
        }
    }

    @Override
    public void createComment(Long id_author, Long id_clientJudged, Long id_travel, 
            String comment, int feedback, Date comment_date, Date comment_hour) {
        if (id_author != null && id_clientJudged != null && id_travel != null &&
                comment != null && feedback != 0 && comment_date.toString() != null
                && comment_hour.toString() != null){
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

    @Override
    public List<Comment> getCommentWritten(Long id_client) {
        List<Comment> lista = new ArrayList();
        List<Comment> commenti = commentFacade.findAll();
        for (Comment commento : commenti){
            if (commento.getId_author().equals(id_client)){
                lista.add(commento);
            }
        }
        return sortListByDate(lista);
    }

    @Override
    public List<Comment> getCommentReceived(Long id_client) {
        List<Comment> lista = new ArrayList();
        List<Comment> commenti = commentFacade.findAll();
        for (Comment commento : commenti){
            if (commento.getId_clientJudged().equals(id_client)){
                lista.add(commento);
            }
        }
        return sortListByDate(lista);
    }

    @Override
    public int getAverageFeedback(Long id_client) {
        int somma = 0;
        int totFeed = 0;
        List <Comment> commenti = commentFacade.findAll();
        for (Comment commento : commenti){
            if (commento.getId_clientJudged().equals(id_client)){
                somma = somma + commento.getFeedback();
                totFeed ++;
            }
        }
        if (totFeed == 0){
            return totFeed;
        }
        else 
            return somma/totFeed;
    }

    protected List<Comment> sortListByDate(List<Comment> commenti){
        Collections.sort(commenti, new Comparator<Comment>() {
            public int compare(Comment com1, Comment com2) {
                return com1.getComment_date().compareTo(com2.getComment_date());
            }
        });
        return commenti;
    }
    
}
