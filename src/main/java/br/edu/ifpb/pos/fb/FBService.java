package br.edu.ifpb.pos.fb;

import com.restfb.Connection;
import com.restfb.DefaultFacebookClient;
import com.restfb.FacebookClient;
import com.restfb.Parameter;
import com.restfb.Version;
import com.restfb.json.JsonStringer;
import com.restfb.types.FacebookType;
import com.restfb.types.Post;
import com.restfb.types.User;
import java.io.Serializable;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Kelson
 */
public class FBService implements Serializable {

    private final FacebookClient facebookClient;
    private final String accessToken;    


    public FBService() {
        this.accessToken = "CAAOKFVdZCy3ABAMleBLZASCQLAowqnX1FC7diFnRjDfXmcCYdU4wqJynKl3xXv6xpGtjZC1V6BDuEZAOrr0PTZAv2fr3un9VhjYrV2urJ8QQtBAqwv2FtcZCLhV1h2EMx9LEHMhFBQ9meeZCiI7BbXqStg2yPEID7UjZALYDVWYwDkUSr9AYre8lXB3dUmGVtsx5v1lzgGLgf2tHAky0g13dzYPbHa5oBWC9FRLNIvXlZCgZDZD";
        facebookClient = new DefaultFacebookClient(accessToken, Version.VERSION_2_2);
    }
    
    public FacebookClient getFacebookClient() {
        return facebookClient;
    }
    
    public List<User> getFriends() {
        List<User> friends = new ArrayList<>();
        Connection<User> myFriends = facebookClient.fetchConnection("me/taggable_friends", User.class);

        for (List<User> myFriendsList : myFriends) {
            for (User u : myFriendsList) {
                friends.add(u);
            }
        }
        return friends;
    }

    public User getUser() {
        return facebookClient.fetchObject("me", User.class);
    }

    public String publishTask(Tarefa tarefa) {
        System.out.println("Nome: " + tarefa.getNome());
        
        String image = "http://www.ifpb.edu.br/arquivos/imagens/icones-mapas/logo-ifpb.png";
        
        String task = new JsonStringer().object().key("og:title").value(tarefa.getNome())
                .key("og:description").value(tarefa.getDescricao())
                .key("og:image").value(image)
                .key("og:url").value(getPath() + String.valueOf(tarefa.getId())).endObject().toString();
        

        FacebookType publish = facebookClient.publish(
                "me/projetopos:servico", Post.class,
                Parameter.with("pos", task),
                Parameter.with("message", "Criador: " + this.getUser().getName()
                        + " - Responsável: @[" + tarefa.getIdResponsavel() + "]"
                        + " - Status: " + tarefa.getStatus()
                        + " - Prioridade: " + tarefa.getPrioridade()));

        return publish.getId();
    }

    public void notificarStatusTask(Tarefa tarefa) {
        facebookClient.publish(
                "me/projetopos:servico", Post.class,
                Parameter.with("pos", "http://samples.ogp.me/1003546559674813"),
                Parameter.with("message", "@[kelsonsd]"
                        + " - a tarefa: " + tarefa.getNome() 
                        + " alterou o status para: " + tarefa.getStatus() + ". "
                        + "Responsável: @["+ tarefa.getIdResponsavel() +"]"));
    }    
    
    private String getIp() {
        InetAddress ia = null;
        try {
            ia = InetAddress.getLocalHost();
            return ia.getHostAddress();
        } catch (UnknownHostException ex) {
            Logger.getLogger(FBService.class.getName()).log(Level.SEVERE, null, ex);
            return "localhost";
        }
    }

    public String getPath() {        
//        return "http://" + this.getIp() + ":8080/projeto2-pos/rest/fb/task/";        
        return "http://" + this.getIp() + ":8080/projeto2-pos/rest/fb/taskteste/";        
    }

    public void redirecionar(Tarefa t) {
        TarefaController tc = new TarefaController();
        tc.redirecionar(t);        
    }

}
