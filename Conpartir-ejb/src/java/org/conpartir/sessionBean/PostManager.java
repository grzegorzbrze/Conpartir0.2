/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.conpartir.sessionBean;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import org.conpartir.entity.Post;
import org.conpartir.facade.PostFacadeLocal;


/**
 *
 * @author Blu Light
 */
@Stateless
public class PostManager implements PostManagerLocal {
    @EJB
    private PostFacadeLocal postFacade;

    @Override
    public void createPost(Post post) {
        postFacade.create(post);
    }

    
}
