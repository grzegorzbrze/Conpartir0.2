/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.conpartir.facade;

import java.util.List;
import javax.ejb.Local;
import org.conpartir.entity.Travel;

/**
 *
 * @author Blu Light
 */
@Local
public interface TravelFacadeLocal {

    void create(Travel post);

    void edit(Travel post);

    void remove(Travel post);

    Travel find(Object id);

    List<Travel> findAll();

    List<Travel> findRange(int[] range);

    int count();
    
}
