/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.conpartir.facade;

import java.util.List;
import javax.ejb.Local;
import org.conpartir.entity.Driver;

/**
 *
 * @author Blu Light
 */
@Local
public interface DriverFacadeLocal {

    void create(Driver driver);

    void edit(Driver driver);

    void remove(Driver driver);

    Driver find(Object id);

    List<Driver> findAll();

    List<Driver> findRange(int[] range);

    int count();
    
}
