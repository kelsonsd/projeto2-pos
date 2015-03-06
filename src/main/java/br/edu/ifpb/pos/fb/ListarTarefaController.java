package br.edu.ifpb.pos.fb;

import br.edu.ifpb.por.tarefaService.TarefaService;
import java.io.Serializable;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;

/**
 *
 * @author Kelson
 */
@ManagedBean(name = "listarMB")
@RequestScoped
public class ListarTarefaController implements Serializable {

    @ManagedProperty(value = "#{param.idTarefa}")
    private String idTarefa;
    private Tarefa tarefaTemp = new Tarefa();
    private TarefaService tarefaService = new TarefaService();

    public String getIdTarefa() {
        return idTarefa;
    }

    public void setIdTarefa(String idTarefa) {
        this.idTarefa = idTarefa;
    }

    public Tarefa getTarefaTemp() {
        try {
            tarefaTemp = (Tarefa) tarefaService.buscaTarefaPeloCodigo(idTarefa);
        } catch (NullPointerException ex) {
        }
        System.out.println("codigo que pegou:" + idTarefa);
        return tarefaTemp;
    }

    public void setTarefaTemp(Tarefa tarefaTemp) {
        this.tarefaTemp = tarefaTemp;
    }

    public TarefaService getTarefaService() {
        return tarefaService;
    }

    public void setTarefaService(TarefaService tarefaService) {
        this.tarefaService = tarefaService;
    }

}
