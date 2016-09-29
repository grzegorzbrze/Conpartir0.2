/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.conpartir.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Blu Light
 */
@Entity
@XmlRootElement
public class Comment implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Column (name = "COMMENT_ID")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column (name = "ID_AUTHOR")
    private Long id_author;
    
    @Column (name = "ID_CLIENT_JUDGED")
    private Long id_clientJudged;
    
    @Column (name = "ID_TRAVEL")
    private Long id_travel;
    
    @Column (name = "COMMENT")
    private String comment;
    
    @Column (name = "FEEDBACK")
    private int feedback;
    
    @Column (name = "COMMENT_DATE")
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date comment_date;
    
    @Column (name = "COMMENT_HOUR")
    @Temporal(javax.persistence.TemporalType.TIME)
    private Date commet_hour;
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId_author() {
        return id_author;
    }

    public void setId_author(Long id_author) {
        this.id_author = id_author;
    }

    public Long getId_clientJudged() {
        return id_clientJudged;
    }

    public void setId_clientJudged(Long id_clientJudged) {
        this.id_clientJudged = id_clientJudged;
    }

    public Long getId_travel() {
        return id_travel;
    }

    public void setId_travel(Long id_travel) {
        this.id_travel = id_travel;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public int getFeedback() {
        return feedback;
    }

    public void setFeedback(int feedback) {
        this.feedback = feedback;
    }

    public Date getComment_date() {
        return comment_date;
    }

    public void setComment_date(Date comment_date) {
        this.comment_date = comment_date;
    }

    public Date getCommet_hour() {
        return commet_hour;
    }

    public void setCommet_hour(Date commet_hour) {
        this.commet_hour = commet_hour;
    }
    
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Comment other = (Comment) obj;
        if (!Objects.equals(this.id_author, other.id_author)) {
            return false;
        }
        if (!Objects.equals(this.id_clientJudged, other.id_clientJudged)) {
            return false;
        }
        if (!Objects.equals(this.id_travel, other.id_travel)) {
            return false;
        }
        if (!Objects.equals(this.comment_date, other.comment_date)) {
            return false;
        }
        if (!Objects.equals(this.commet_hour, other.commet_hour)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Comment{" + "id_author=" + id_author + ", id_clientJudged=" + 
                id_clientJudged + ", id_travel=" + id_travel + ", comment=" + 
                comment + ", feedback=" + feedback + ", comment_date=" + 
                comment_date + ", commet_hour=" + commet_hour + '}';
    }

    
    
}
