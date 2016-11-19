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
 * Implementazione di serializzable per definire <i>Comment</i> memoriazzato nel
 * database grazie all'Entity.
 *
 * @author Conpartir Group
 * @version 0.3 21 settembre 2016
 * @see <a href="http://docs.oracle.com/javaee/6/tutorial/doc/bnbqa.html">
 * Entity </a>
 * @see
 * <a href="https://docs.oracle.com/javase/7/docs/api/java/io/Serializable.html">
 * Serializable </a>
 *
 */
@Entity
@XmlRootElement
public class Comment implements Serializable {

    /**
     * Identificativo univoco dell'oggetto all'interno del database
     */
    private static final long serialVersionUID = 1L;
    @Id
    @Column(name = "COMMENT_ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Identificativo del cliente autore del commento.
     */
    @Column(name = "ID_AUTHOR")
    private Long id_author;

    /**
     * Identificativo del cliente giudicato nel commento.
     */
    @Column(name = "ID_CLIENT_JUDGED")
    private Long id_clientJudged;

    /**
     * Identificativo del viaggio commentato.
     */
    @Column(name = "ID_TRAVEL")
    private Long id_travel;

    /**
     * Il testo del commento.
     */
    @Column(name = "COMMENT")
    private String comment;

    /**
     * Valore di feedback rilasciato dall'autore.
     */
    @Column(name = "FEEDBACK")
    private int feedback;

    /**
     * La data in cui il commento è rilasciato.
     */
    @Column(name = "COMMENT_DATE")
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date comment_date;

    /**
     * L'orario in cui il commento è rilasciato.
     */
    @Column(name = "COMMENT_HOUR")
    @Temporal(javax.persistence.TemporalType.TIME)
    private Date commet_hour;

    /**
     * Restituisce l'identificativo univoco dell'oggetto nel database.
     *
     * @return Valore dell'identificativo univoco
     */
    public Long getId() {
        return id;
    }

    /**
     * Modifica il valore dell'identificativo univoco dell'oggetto nel database.
     *
     * @param id Long con il quale cambiare l'identificativo.
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Resitituisce il valore dell'identificativo univoco dell'autore del
     * commento.
     *
     * @return Il valore dell'identificativo dell'autore del commento.
     */
    public Long getId_author() {
        return id_author;
    }

    /**
     * Modifica il valore dell'identificativo univoco dell'autore del commento.
     *
     * @param id_author Long con il quale cambiare l'identificativo dell'autore.
     */
    public void setId_author(Long id_author) {
        this.id_author = id_author;
    }

    /**
     * Restituisce il valore dell'identificativo univoco del cliente giudicato
     *
     * @return Il valore dell'identificativo del cliente giudicato.
     */
    public Long getId_clientJudged() {
        return id_clientJudged;
    }

    /**
     * Modifica il valore dell'identificativo univoco del cliente giudicato nel
     * commento.
     *
     * @param id_clientJudged Long con il quale cambiare l'identificativo del
     * cliente giudicato.
     */
    public void setId_clientJudged(Long id_clientJudged) {
        this.id_clientJudged = id_clientJudged;
    }

    /**
     * Restituisce il valore dell'identificativo univoco del viaggio commentato.
     *
     * @return Il valore dell'identificativo del viaggio giudicato.
     */
    public Long getId_travel() {
        return id_travel;
    }

    /**
     * Modifica il valore dell'identificativo univoco del viaggio commentato.
     *
     * @param id_travel Long con il quale cambiare l'identificativo del viaggio
     * commentato.
     */
    public void setId_travel(Long id_travel) {
        this.id_travel = id_travel;
    }

    /**
     * Restituisce la stringa del commento che riguarda il viaggio.
     *
     * @return Il testo del commento.
     */
    public String getComment() {
        return comment;
    }

    /**
     * Modifica il commento che riguarda il viaggio.
     *
     * @param comment String con la quale cambiare il commento sul viaggio.
     */
    public void setComment(String comment) {
        this.comment = comment;
    }

    /**
     * Restituisce il valore del feedback espresso dal autore del commento.
     *
     * @return Il valore del feedback.
     */
    public int getFeedback() {
        return feedback;
    }

    /**
     * Modifica il valore del feeeback espresso dall'autore del commento.
     *
     * @param feedback Int con il quale cambiare il feedback.
     */
    public void setFeedback(int feedback) {
        this.feedback = feedback;
    }

    /**
     * Restituisce la data in cui è stato creato o modificato il commento.
     *
     * @return La data in cui il commento è stato creato o modificato.
     */
    public Date getComment_date() {
        return comment_date;
    }

    /**
     * Modifica la data in cui il commento è stato creato o modificato.
     *
     * @param comment_date Date con la quale cambiare la data della creazione o
     * modifica del commento.
     */
    public void setComment_date(Date comment_date) {
        this.comment_date = comment_date;
    }

    /**
     * Restituisce l'orario in cui il commento è stato creato o modificato.
     *
     * @return L'orario in cui il commento è stato creato o modificato.
     */
    public Date getCommet_hour() {
        return commet_hour;
    }

    /**
     * Modifica l'orario in cui il commento è stato creato o modificato.
     *
     * @param commet_hour Date con la quale cambiare l'orario della creazione o
     * modifica.
     */
    public void setCommet_hour(Date commet_hour) {
        this.commet_hour = commet_hour;
    }

    /**
     * Restituisce il risultato intero di una funzione hash che codifica i
     * parametri del commento
     *
     * @return Int che corrisponde alla risultato di una funzione hash.
     */
    @Override
    public int hashCode() {
        int hash = 5;
        hash = 53 * hash + Objects.hashCode(this.id_author);
        hash = 53 * hash + Objects.hashCode(this.id_clientJudged);
        hash = 53 * hash + Objects.hashCode(this.id_travel);
        hash = 53 * hash + Objects.hashCode(this.comment);
        hash = 53 * hash + this.feedback;
        hash = 53 * hash + Objects.hashCode(this.comment_date);
        hash = 53 * hash + Objects.hashCode(this.commet_hour);
        return hash;
    }

    /**
     * Restituisce la stringa che contiene tutti i dati di un commento
     *
     * @return String che contiene tutti i dati di un commento
     */
    @Override
    public String toString() {
        return "Comment{" + "id_author=" + id_author + ", id_clientJudged="
                + id_clientJudged + ", id_travel=" + id_travel + ", comment="
                + comment + ", feedback=" + feedback + ", comment_date="
                + comment_date + ", commet_hour=" + commet_hour + '}';
    }

}
