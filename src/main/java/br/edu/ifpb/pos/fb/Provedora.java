package br.edu.ifpb.pos.fb;

import com.restfb.types.User;
import java.util.List;
import javax.ejb.Remote;

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
}
