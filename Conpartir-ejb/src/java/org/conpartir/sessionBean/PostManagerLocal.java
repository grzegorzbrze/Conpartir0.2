/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.conpartir.sessionBean;

import javax.ejb.Local;
import org.conpartir.entity.Post;

/**
 *
 * @author Blu Light
 */
@Local
public interface PostManagerLocal {
    
    public void createPost(Post post);
}
