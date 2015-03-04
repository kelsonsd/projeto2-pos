package br.edu.ifpb.pos.fb;

import com.restfb.types.User;
import java.io.Serializable;
import java.util.Date;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import org.primefaces.context.RequestContext;

/**
 *
 * @author Kelson
 */

@ManagedBean
@SessionScoped
public class TarefaController implements Serializable {
    private Tarefa tarefa = new Tarefa();
    private User responsavel;
    private User criador = new FBService().getUser();
    
    public TarefaController() {        
    }

    public Tarefa getTarefa() {
        return tarefa;
    }

    public void setTarefa(Tarefa tarefa) {
        this.tarefa = tarefa;
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
        return "tarefa.xhtml?faces-redirect=true";
    }
    
    public String salvarTarefa() {        
        this.tarefa.setIdCriador(this.criador.getId());
        this.tarefa.setCriador(this.getCriador());        
        this.tarefa.setResponsavel(this.responsavel);
        this.tarefa.setIdResponsavel(this.responsavel.getId());
        this.tarefa.setDataCriacao(new Date());
        this.tarefa.setStatus("EM ABERTO");        
        
        ConsumidorREST consumidorREST = new ConsumidorREST();
                
        Long idTask = consumidorREST.atualizarTarefa(this.tarefa).getId();        
        consumidorREST.publishTask(idTask);
        
        this.tarefa = new Tarefa();
        
        RequestContext.getCurrentInstance().showMessageInDialog(new FacesMessage(FacesMessage.SEVERITY_INFO, "Sucesso!", "Tarefa criada com sucesso!"));
        return "index.xhtml?faces-redirect=true";
    }
}
