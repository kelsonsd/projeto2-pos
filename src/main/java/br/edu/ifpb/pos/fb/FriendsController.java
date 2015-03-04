package br.edu.ifpb.pos.fb;

import com.restfb.types.User;
import java.io.Serializable;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

/**
 *
 * @author Kelson
 */
@ManagedBean
@SessionScoped
public class FriendsController implements Serializable {

    public FriendsController() {
    }
    
    public List<User> getFriends() {
        return new ConsumidorREST().getFriends();
    }
    
}
