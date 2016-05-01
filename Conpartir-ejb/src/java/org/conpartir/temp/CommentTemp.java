/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.conpartir.temp;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

/**
 *
 * @author Blu Light
 */
@XmlRootElement(name = "CommentTemp")
@XmlType(propOrder = {"nomeAutore", "cognomeAutore", "testoCommento", "feedBackCommento"})
public class CommentTemp {
   
    private String nomeAutore;
    
    private String cognomeAutore;
    
    private String testoCommento;
    
    private int feedBackCommento;

    public String getNomeAutore() {
        return nomeAutore;
    }

    public void setNomeAutore(String nomeAutore) {
        this.nomeAutore = nomeAutore;
    }

    public String getCognomeAutore() {
        return cognomeAutore;
    }

    public void setCognomeAutore(String cognomeAutore) {
        this.cognomeAutore = cognomeAutore;
    }

    public String getTestoCommento() {
        return testoCommento;
    }

    public void setTestoCommento(String testoCommento) {
        this.testoCommento = testoCommento;
    }

    public int getFeedBackCommento() {
        return feedBackCommento;
    }

    public void setFeedBackCommento(int feedBackCommento) {
        this.feedBackCommento = feedBackCommento;
    }

    @Override
    public String toString() {
        return "CommentTemp{" + "nomeAutore=" + nomeAutore + ", cognomeAutore=" + cognomeAutore + ", testoCommento=" + testoCommento + ", feedBackCommento=" + feedBackCommento + '}';
    }
    
    
}
