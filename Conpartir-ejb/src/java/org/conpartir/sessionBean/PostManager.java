/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.conpartir.sessionBean;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import org.conpartir.entity.Driver;
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

    @Override
    public void createPost(Driver driver, Long client_id, String origin, String destination, Date data, Date time) {
        Post post = new Post();
        post.setClient_id(client_id);
        post.setDriver(driver);
        post.setData(data);
        post.setDestination(destination);
        post.setOrigin(origin);
        post.setTime(time);
        postFacade.create(post);
    }

    @Override
    public Post getPost(Long driver_id, Long client_id) {
        Post post = new Post();
        List<Post> list = postFacade.findAll();
        for (Post temp : list){
            Long temp_driverID = temp.getDriver().getDriver_id();
            Long temp_clientID = temp.getClient_id();
            if (temp_driverID.equals(driver_id) && temp_clientID.equals(client_id)){
                post = temp;
            }
        }
        return post;
    }

    @Override
    public List<Post> searchByOriginDestination(String origin, String destination) {
        List <Post> lista = new ArrayList();
        List <Post> viaggi = postFacade.findAll();
        for (Post temp : viaggi){
            if (temp.getDestination().equals(destination) && temp.getOrigin().equals(origin)){
                lista.add(temp);
            }
        }
        return lista;
    }

    @Override
    public List<Post> searchByDestinationOriginDate(Date data, String destination, String origin) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<Post> searchByDestinationOriginTime(Date time, String destination, String origin) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
