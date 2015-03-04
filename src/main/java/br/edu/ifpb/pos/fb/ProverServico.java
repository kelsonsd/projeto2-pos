package br.edu.ifpb.pos.fb;

import br.edu.ifpb.pos.fb.service.AbstractFacade;
import com.restfb.types.User;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

/**
 *
 * @author Kelson
 */
@Stateless
@Path("fb")
public class ProverServico extends AbstractFacade<Tarefa> implements Serializable, Provedora {

    @PersistenceContext(unitName = "fb-PU")
    private EntityManager em;

    FBService fb = new FBService();

    public ProverServico() {
        super(Tarefa.class);
    }

    @GET
    @Path("friends")
    @Produces("application/json")
    @Override
    public List<User> getFriends() {
        return fb.getFriends();
    }

    @POST
    @Override
    @Consumes("application/json")
    public void criarTarefa(Tarefa entity) {
        super.create(entity);
    }

    @POST
    @Path("newTask")
    @Consumes("application/json")
    @Override
    public void atualizarTarefa(Long idTask) {
        FBService fbService = new FBService();
        Tarefa t = super.find(idTask);

        fbService.publishTask(t);
        super.edit(t);
    }

    @GET
    @Path("task/{id}")
    @Override
    @Consumes("application/json")
    public Tarefa notificarStatusTask(@PathParam("id") String id) {
        FBService fbService = new FBService();
        Tarefa t = super.find(new Long(id));

        t.setDataExecucao(new Date());        
        t.setStatus("EM EXECUÇÃO");
        
        fbService.notificarStatusTask(t);
        return super.edit(t);
    }

    @POST
    @Path("atualizar")
    @Override
    @Consumes("application/json")
    public Tarefa edit(Tarefa entity) {
        return super.edit(entity);
    }

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

}
