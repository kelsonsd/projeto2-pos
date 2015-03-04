package br.edu.ifpb.pos.fb;

import com.restfb.types.User;
import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

/**
 *
 * @author Kelson
 */
@ManagedBean(name = "dtPaginatorView")
@ViewScoped
public class PaginatorView implements Serializable {
    private List<User> users;   

    /**
     * Creates a new instance of PaginatorView
     */
    public PaginatorView() {
    }        
     
    public List<User> getFriends() {
        return users;
    }
    
    @PostConstruct
    public void init() {
        users = new FBService().getFriends();
    }
    
}
