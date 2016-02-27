/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.conpartir.facade;

import java.util.List;
import javax.ejb.Local;
import org.conpartir.entity.Taxi;

/**
 *
 * @author Blu Light
 */
@Local
public interface TaxiFacadeLocal {

    void create(Taxi taxi);

    void edit(Taxi taxi);

    void remove(Taxi taxi);

    Taxi find(Object id);

    List<Taxi> findAll();

    List<Taxi> findRange(int[] range);

    int count();
    
}
