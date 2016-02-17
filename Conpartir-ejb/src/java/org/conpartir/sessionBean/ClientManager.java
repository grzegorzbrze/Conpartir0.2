/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.conpartir.sessionBean;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import org.conpartir.entity.Client;
import org.conpartir.entity.Driver;
import org.conpartir.facade.ClientFacadeLocal;

/**
 *
 * @author Blu Light
 */
@Stateless
public class ClientManager implements ClientManagerLocal {
    @EJB
    private ClientFacadeLocal clientFacade;    
    
    @Override
    public void createClient(Client client) {
       clientFacade.create(client);
    }

}
