package br.edu.ifpb.pos.fb;

import br.edu.ifpb.pos.fb.service.AbstractFacade;
import com.restfb.types.User;
import java.io.Serializable;
import java.net.URISyntaxException;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.Resource;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 *
 * @author Kelson
 */
//@Stateless
@Path("fb")
public class ProverServico extends AbstractFacade<Tarefa> implements Serializable, Provedora {

    @PersistenceContext(unitName = "fb-PU")
    private EntityManager em;

    FBService fb = new FBService();
    @Resource
    private javax.transaction.UserTransaction utx;

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
    @Path("tasks")
    @Override
    @Consumes({"application/x-www-form-urlencoded","application/json"})
    @Produces("application/json")
    public Tarefa criarTarefa2(@FormParam("nome") String nome, @FormParam("descricao") String descricao, 
            @FormParam("datalimite") String datalimite, @FormParam("prioridade") String prioridade,
            @FormParam("idCriador") String idCriador, @FormParam("idResponsavel") String idResponsavel) {        
        
        System.out.println(nome + descricao + datalimite + prioridade);
        
        Tarefa tarefa = new Tarefa();
        tarefa.setNome(nome);
        tarefa.setDescricao(descricao);
        tarefa.setDataLimiteExecucao(datalimite);
        tarefa.setPrioridade(prioridade);        
        tarefa.setDataCriacao(new Date());
        tarefa.setIdCriador(idCriador);
        tarefa.setIdResponsavel(idResponsavel);
        
        System.out.println(tarefa.toString());
        
        return update(tarefa);        
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
    @Consumes({"application/x-www-form-urlencoded","application/json"})
    public Response notificarStatusTask(@PathParam("id") String id) {        
        Tarefa t = findTarefa(new Long(id));

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

    @GET
    @Path("taskteste/{id}")
    @Override
    @Produces("application/json")
    public Tarefa notificarStatusTaskTeste(@PathParam("id") String id) {
        Tarefa t = findTarefa(new Long(id));

        t.setDataExecucao(new Date());
        t.setStatus("EM EXECUÇÃO");

        System.out.println(t.getNome());

        fb.notificarStatusTask(t);   
        
        return t;
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

    public Tarefa update(Object object) {
        try {
            utx.begin();
            Tarefa t = (Tarefa) em.merge(object);
            utx.commit();
            return t;
        } catch (Exception e) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", e);
            throw new RuntimeException(e);
        }
    }
    
    public Tarefa findTarefa(Object object) {
        try {
            utx.begin();
            Tarefa t = (Tarefa) em.find(Tarefa.class, object);
            utx.commit();
            return t;
        } catch (Exception e) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", e);
            throw new RuntimeException(e);
        }
    }
    
    

}
