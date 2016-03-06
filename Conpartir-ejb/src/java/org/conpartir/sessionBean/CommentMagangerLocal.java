/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.conpartir.sessionBean;

import java.util.Date;
import java.util.List;
import javax.ejb.Local;
import org.conpartir.entity.Comment;

/**
 *
 * @author Blu Light
 */
@Local
public interface CommentMagangerLocal {
    
    public void createComment(Comment comment);
    //penso che forse potremo fare i commenti anche senza feedback subito, in modo tale che 
    //il feedback venga fatto in un secondo momento... per ora fa parte del costruttore
    public void createComment(Long id_author, Long id_clientJudged, Long id_travel, 
            String comment, int feedback, Date comment_date, Date commet_hour);
    
    public List<Comment> getCommentWritten(Long id_client);
    
    public List<Comment> getCommentReceived(Long id_client);
    
    public int getAverageFeedback(Long id_client);
    
}
