package br.edu.ifpb.pos.fb;

import com.restfb.types.User;
import java.util.List;
import javax.ejb.Remote;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Response;

/**
 *
 * @author Kelson
 */
@Remote
public interface Provedora {
    public List<User> getFriends();
    public void criarTarefa(Tarefa entity);
    public Tarefa criarTarefa2(@PathParam("nome") String nome, @PathParam("descricao") String descricao, 
            @PathParam("datalimite") String datalimite, @PathParam("prioridade") String prioridade);
    public void atualizarTarefa(Long idTask);
    public Tarefa edit(Tarefa entity);
    public Tarefa notificarStatusTask(String idTask);
    public Response notificarStatusTaskTeste(String idTask);
}
