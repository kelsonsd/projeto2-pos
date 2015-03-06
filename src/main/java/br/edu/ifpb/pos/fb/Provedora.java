package br.edu.ifpb.pos.fb;

import com.restfb.types.User;
import java.util.List;
import javax.ejb.Remote;
import javax.servlet.ServletContext;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;

/**
 *
 * @author Kelson
 */
@Remote
public interface Provedora {
    public List<User> getFriends();
    public void criarTarefa(Tarefa tarefa);
    public void atualizarTarefa(Long idTask);
    public Tarefa edit(Tarefa entity);
    public Tarefa notificarStatusTask(String idTask);
    public Response notificarStatusTaskTeste(String idTask);
}
