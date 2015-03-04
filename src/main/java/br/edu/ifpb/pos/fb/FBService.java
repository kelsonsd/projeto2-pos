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

    public FacebookClient getFacebookClient() {
        return facebookClient;
    }

    public FBService() {
        this.accessToken = "CAAOKFVdZCy3ABAOjUQ1evsUNFTlZAsBlGft2YdEMLt0ZCbPiBLWqRLz8LWrNhZCQEgWjSLjcyWb1IM7jCuZA0iNgg7IOrGBslyo7WY8lUZCuQxjWVQITc66E1LTkvMaXdLc31TNdnSR048vnZAg8wgnF0ZBqNYg9cnmiZCjkoI26NIfXpq8sPMHb69LZCySBaf7YTTILGw6mHXUdfXffSUlZAZAVSwenAtB2Lum1tjySLRXEdQZDZD";
        facebookClient = new DefaultFacebookClient(accessToken, Version.VERSION_2_2);
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
        String image = "http://www.ifpb.edu.br/arquivos/imagens/icones-mapas/logo-ifpb.png";
        
        String task = new JsonStringer().object().key("og:title").value(tarefa.getNome())
                .key("og:description").value(tarefa.getDescricao())
                .key("og:image").value(image).key("og:url")
                .value(getPath() + String.valueOf(tarefa.getId())).endObject().toString();

        FacebookType publish = facebookClient.publish(
                "me/projetopos:servico", Post.class,
                Parameter.with("pos", task),
                Parameter.with("message", "Status: " + tarefa.getStatus()
                        + " - Criador: " + this.getUser().getName()
                        + " - Responsável: @[" + tarefa.getIdResponsavel() + "]"));

        return publish.getId();
    }

    public void notificarStatusTask(Tarefa tarefa) {
        facebookClient.publish(
                "me/projetopos:servico", Post.class,
                Parameter.with("pos", "http://samples.ogp.me/1003546559674813"),
                Parameter.with("message", "@[kelsonsd]"
                        + " - a tarefa: " + tarefa.getNome() + " alterou o status para: " + tarefa.getStatus() + "."));
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
        return "http://" + this.getIp() + ":8080/projeto2-pos/rest/fb/task/";
    }

}
