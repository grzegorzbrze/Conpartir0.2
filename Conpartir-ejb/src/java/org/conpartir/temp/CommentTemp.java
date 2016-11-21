package org.conpartir.temp;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

/**
 * Implementazione della classe <i>CommentTemp</i> per la gestione di un oggetto
 * composto in formato XML.
 *
 * @author Conpartir Group
 * @version 0.3 21 settembre 2016
 * @see
 * <a href="http://docs.oracle.com/javaee/5/api/javax/xml/bind/annotation/XmlRootElement.html">
 * XmlRootElement </a>
 */
@XmlRootElement(name = "CommentTemp")
@XmlType(propOrder = {"nomeAutore", "cognomeAutore", "testoCommento", "feedBackCommento"})
public class CommentTemp {

    private String nomeAutore;
    private String cognomeAutore;
    private String testoCommento;
    private int feedBackCommento;

    /**
     * Restituisce il valore nomeAutore dell'oggetto
     *
     * @return String
     */
    public String getNomeAutore() {
        return nomeAutore;
    }

    /**
     * Setta il valore nomeAutore dell'oggetto
     *
     * @param nomeAutore String
     */
    public void setNomeAutore(String nomeAutore) {
        this.nomeAutore = nomeAutore;
    }

    /**
     * Restituisce il valore cognomeAutore dell'oggetto
     *
     * @return String
     */
    public String getCognomeAutore() {
        return cognomeAutore;
    }

    /**
     * Setta il valore cognomeAutore dell'oggetto
     *
     * @param cognomeAutore String
     */
    public void setCognomeAutore(String cognomeAutore) {
        this.cognomeAutore = cognomeAutore;
    }

    /**
     * Restituisce il testo del commento dell'oggetto
     *
     * @return String
     */
    public String getTestoCommento() {
        return testoCommento;
    }

    /**
     * Setta il testo del commento dell'oggetto
     *
     * @param testoCommento String
     */
    public void setTestoCommento(String testoCommento) {
        this.testoCommento = testoCommento;
    }

    /**
     * Restituisce il valore feedback del commento dell'oggetto
     *
     * @return int
     */
    public int getFeedBackCommento() {
        return feedBackCommento;
    }

    /**
     * Setta il valore feedback del commento dell'oggetto
     *
     * @param feedBackCommento int
     */
    public void setFeedBackCommento(int feedBackCommento) {
        this.feedBackCommento = feedBackCommento;
    }

    /**
     * Implementa il metodo che scrive l'oggetto su stringa
     *
     * @return String
     */
    @Override
    public String toString() {
        return "CommentTemp{" + "nomeAutore=" + nomeAutore + ", cognomeAutore=" + cognomeAutore + ", testoCommento=" + testoCommento + ", feedBackCommento=" + feedBackCommento + '}';
    }

}
