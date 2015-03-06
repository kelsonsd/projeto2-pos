package br.edu.ifpb.pos.fb;

import br.edu.ifpb.pos.fb.service.AbstractFacade;
import com.restfb.types.User;
import java.io.Serializable;
import java.net.URISyntaxException;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

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
    @Path("tasks/{nome}/{descricao}/{datalimite}/{prioridade}/")
    @Override
    @Consumes("application/json")
    @Produces("application/json")
    public Tarefa criarTarefa2(@PathParam("nome") String nome, @PathParam("descricao") String descricao, 
            @PathParam("datalimite") String datalimite, @PathParam("prioridade") String prioridade) {        
        
        Tarefa tarefa = new Tarefa();
        tarefa.setNome(nome);
        tarefa.setDescricao(descricao);
        tarefa.setDataLimiteExecucao(datalimite);
        tarefa.setPrioridade(prioridade);
        return super.edit(tarefa);
    }

    @POST
    @Path("newTask")
    @Consumes("application/json")
    @Produces("application/json")
    @Override
    public void atualizarTarefa(Long idTask) {        
        Tarefa t = super.find(idTask);

        fb.publishTask(t);
        super.edit(t);
    }

    @GET
    @Path("task/{id}")
    @Override
    @Produces("application/json")
    public Tarefa notificarStatusTask(@PathParam("id") String id) {        
        Tarefa t = super.find(new Long(id));

        t.setDataExecucao(new Date());
        t.setStatus("EM EXECUÇÃO");

        System.out.println(t.getNome());

        fb.notificarStatusTask(t);        
    
        return super.edit(t);
        
    }

    @GET
    @Path("taskteste/{id}")
    @Override
    @Produces("application/json")
    public Response notificarStatusTaskTeste(@PathParam("id") String id) {
        Tarefa t = super.find(new Long(id));

        t.setDataExecucao(new Date());
        t.setStatus("EM EXECUÇÃO");

        System.out.println(t.getNome());

        fb.notificarStatusTask(t);   
        
        TarefaController.setTarefaTemp(super.edit(t));        
        
        java.net.URI location = null;
        try {
            location = new java.net.URI("../tarefa.xhtml");
        } catch (URISyntaxException ex) {
            System.out.println("erro");
            Logger.getLogger(ProverServico.class.getName()).log(Level.SEVERE, null, ex);
        }        
        return Response.temporaryRedirect(location).build();
    }

    @POST
    @Path("atualizar")
    @Override
    @Consumes("application/json")
    @Produces("application/json")
    public Tarefa edit(Tarefa entity) {
        return super.edit(entity);
    }

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

}
