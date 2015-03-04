package br.edu.ifpb.pos.fb;

import com.restfb.types.User;
import java.util.List;
import javax.ws.rs.ClientErrorException;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;

/**
 *
 * @author Kelson
 */
public class ConsumidorREST {
    private WebTarget webTarget;
    private Client client;
    private static final String BASE_URI = "http://localhost:8080/projeto2-pos/rest";

    public ConsumidorREST() {
        client = javax.ws.rs.client.ClientBuilder.newClient();
        webTarget = client.target(BASE_URI).path("fb");
    }

    public List<User> getFriends() throws ClientErrorException {
        GenericType<List<User>> customerType = new GenericType<List<User>>() {};
        WebTarget resource = webTarget;
        resource = resource.path("friends");
        return resource.request(javax.ws.rs.core.MediaType.APPLICATION_JSON).get(customerType);
    }

    public void salvarTarefa(Object requestEntity) throws ClientErrorException {
        webTarget.request(MediaType.APPLICATION_JSON).post(Entity.json(requestEntity), Tarefa.class);
    }
    
    public void publishTask(Object idTask) throws ClientErrorException {
        WebTarget resource = webTarget;
        resource = resource.path("newTask");        
        resource.request(MediaType.APPLICATION_JSON).post(Entity.json(idTask));        
    }
    
    public Tarefa atualizarTarefa(Object requestEntity) throws ClientErrorException {
        WebTarget resource = webTarget;
        resource = resource.path("atualizar");        
        return resource.request(MediaType.APPLICATION_JSON).post(Entity.json(requestEntity), Tarefa.class);
    }    
    
//    public Tarefa find(Object responseType) throws ClientErrorException {
//        WebTarget resource = webTarget;
//        resource = resource.path("find");        
//        GenericType<Tarefa> genericType = new GenericType<Tarefa>() {};
//        return resource.request(MediaType.APPLICATION_JSON).get(genericType);
//    }
//    
//    public <T> T find_JSON(Class<T> responseType, String id) throws ClientErrorException {
//        WebTarget resource = webTarget;
//        resource = resource.path("find");
//        
//        resource = resource.path(java.text.MessageFormat.format("{0}", new Object[]{id}));
//        return resource.request(javax.ws.rs.core.MediaType.APPLICATION_JSON).get(responseType);
//    }
    
    public void close() {
        client.close();
    }

}
