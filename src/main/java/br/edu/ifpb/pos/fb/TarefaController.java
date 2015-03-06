package br.edu.ifpb.pos.fb;

import com.restfb.types.User;
import java.io.Serializable;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.Resource;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.primefaces.context.RequestContext;

/**
 *
 * @author Kelson
 */
@ManagedBean(name = "tarefaMB")
@SessionScoped
public class TarefaController implements Serializable{

    private User criador = new FBService().getUser();
    private User responsavel;
    private static Tarefa tarefaTemp;
       
    
    private Tarefa tarefa = new Tarefa();  
    @PersistenceContext(unitName = "fb-PU")
    private EntityManager em;
    @Resource
    private javax.transaction.UserTransaction utx;

    
    public TarefaController() {                
        
    }
    
    
//    public TarefaController() {        
//    }

    public Tarefa getTarefa() {
        return tarefa;
    }

    public void setTarefa(Tarefa tarefa) {
        this.tarefa = tarefa;
    }

    public Tarefa getTarefaTemp() {
        return tarefaTemp;
    }

    public static void setTarefaTemp(Tarefa t) {
        tarefaTemp = t;
    }
 

    public User getResponsavel() {
        return responsavel;
    }

    public void setResponsavel(User responsavel) {
        this.responsavel = responsavel;
    }

    public User getCriador() {
        return criador;
    }

    public void setCriador(User criador) {
        this.criador = criador;
    }
    
    public String criarTarefa(User responsavel) {
        this.setResponsavel(responsavel);
        return "nova-tarefa.xhtml?faces-redirect=true";
    }

    public String salvarTarefa() {
        System.out.println("inicio do método salvar");
        this.tarefa.setIdCriador(this.criador.getId());
        this.tarefa.setCriador(this.getCriador());
        this.tarefa.setResponsavel(this.responsavel);
        this.tarefa.setIdResponsavel(this.responsavel.getId());
        this.tarefa.setDataCriacao(new Date());
        this.tarefa.setStatus("EM ABERTO");
                
        System.out.println("new Tarefa()");
        
        ConsumidorREST consumidorREST = new ConsumidorREST();         
        
        Long idTask = consumidorREST.atualizarTarefa(this.tarefa).getId();
        
        consumidorREST.publishTask(idTask);
   
        this.tarefa = new Tarefa();
        
        RequestContext.getCurrentInstance().showMessageInDialog(new FacesMessage(FacesMessage.SEVERITY_INFO, "Sucesso!", "Tarefa criada com sucesso!"));
        return "index.xhtml?faces-redirect=true";
    }
    
    public String finalizarTarefa() {
        this.getTarefaTemp().setStatus("FINALIZADA");        
        new FBService().notificarStatusTask(this.getTarefaTemp());
        
        this.update(this.getTarefaTemp());
        System.out.println("passou");
        
        return "sucesso.xhtml?faces-redirect=true";
    }
    
    public void redirecionar(Tarefa tarefa) {

//        System.out.println("ss"+FacesContext.getCurrentInstance());
//        try {
//            FacesContext.getCurrentInstance().getExternalContext().redirect("redirect.xhtml");
//        } catch (IOException ex) {
//        }        
    }

    public void persist(Object object) {
        try {
            utx.begin();
            em.persist(object);
            utx.commit();
        } catch (Exception e) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", e);
            throw new RuntimeException(e);
        }
    }
    public void update(Object object) {
        try {
            utx.begin();
            em.merge(object);
            utx.commit();
        } catch (Exception e) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", e);
            throw new RuntimeException(e);
        }
    }
    
    
   

    

}
