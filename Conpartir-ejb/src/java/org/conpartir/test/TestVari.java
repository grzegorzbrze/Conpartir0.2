/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.conpartir.test;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import org.conpartir.entity.Post;
import org.conpartir.facade.PostFacadeLocal;

/**
 *
 * @author Blu Light
 */
public class TestVari {
   
     //@Override
    public List<Post> searchByOriginDestinationDate(Date data, String origin, String destination) {
        List <Post> lista = new ArrayList();
        List <Post> viaggi = new ArrayList();
        Post travel1 = new Post();
            travel1.setClient_id((long)1);
            travel1.setData(new Date());
            travel1.setDestination("Milano");
            travel1.setOrigin("Torino");
            Calendar cal = Calendar.getInstance();
            cal.set(2016, 3, 12, 17, 15, 25);
            Date d = cal.getTime();
            travel1.setTime(d);
            viaggi.add(travel1);
        System.out.println("Data da confrontare: "+data.toString());
        for (Post temp : viaggi){
            System.out.println("Data nel DB: "+temp.getTime().toString());
            //in questo modo vengono controllate tutte le date successive a quelle dell'utente
            if (temp.getData().after(data) && temp.getOrigin().equals(origin) && temp.getDestination().equals(destination)){      
                    lista.add(temp);
                    System.out.println("Aggiunto un risultato");
            }
        }
        return lista;
    }
    
    public static void main(String[] args) {
        
        Date data1 = new Date();
        Calendar cal = Calendar.getInstance();
        cal.set(2016, 3, 12, 17, 15, 25);
        Date data2 = cal.getTime();
        //System.out.println("Data2 è dopo data1: "+data2.after(data1));
        TestVari test = new TestVari();
        test.searchByOriginDestinationDate(data1, "Torino", "Milano");
        
    }

    
}
