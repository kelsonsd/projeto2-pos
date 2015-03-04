package br.edu.ifpb.pos.fb;

import com.restfb.json.JsonStringer;
import java.net.InetAddress;
import java.rmi.UnknownHostException;

/**
 *
 * @author Kelson
 */
public class App {

    public static void main(String[] args) throws java.net.UnknownHostException {
        //List<User> friends = new ConsumidorREST().getFriends();

//        for (User friend : friends) {
//            System.out.println(friend.getId());
//            System.out.println(friend.getName());            
//            System.out.println(friend.getPicture().getUrl());
//        }
        //System.out.println(new FBService().getUser().getId());     
        
        String task = new JsonStringer().object().key("og:title").value("OK1").key("og:description").value("OK2")
                .key("og:image").value("http://conteudodigital.ifpb.edu.br/++theme++theme-sisu-2015-sunburst-0.7/images/logo.png")
                .key("og:url").value("OK3").endObject().toString();
        
        System.out.println(task);

//        String task = new JsonStringer().object().key("og:title").value("NomeTarefa").key("og:description").value("DescriçãoTarefa")
//                .key("og:url").value("http://localhost:8080/projeto2-pos/rest/fb/task/" + String.valueOf("10")).endObject().toString();
//        
//        System.out.println(task);
    }
}
