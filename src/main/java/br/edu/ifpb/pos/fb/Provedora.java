package br.edu.ifpb.pos.fb;

import com.restfb.types.User;
import java.util.List;
import javax.ejb.Remote;
import javax.ws.rs.FormParam;
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
    public Tarefa criarTarefa2(@FormParam("nome") String nome, @FormParam("descricao") String descricao, 
            @FormParam("datalimite") String datalimite, @FormParam("prioridade") String prioridade,
            @FormParam("idCriador") String idCriador, @FormParam("idResponsavel") String idResponsavel);
    public void atualizarTarefa(Long idTask);
    public Tarefa edit(Tarefa entity);
    public Response notificarStatusTask(String idTask);
    public Tarefa notificarStatusTaskTeste(String idTask);
}
