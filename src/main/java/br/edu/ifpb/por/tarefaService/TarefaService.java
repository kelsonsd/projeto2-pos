package br.edu.ifpb.por.tarefaService;

import br.edu.ifpb.pos.fb.Tarefa;
import br.edu.ifpb.pos.fb.service.AbstractFacade;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author Kelson
 */
public class TarefaService {
    
    private AbstractFacade abstractFacade;
    
    public Object buscaTarefaPeloCodigo(String idTarefa){
        Map<String, Object> parametros = new HashMap<String, Object>();
        parametros.put("idTarefa", idTarefa);
        return this.abstractFacade.buscaObjetoComNamedQuery(Tarefa.BUSCAR_TAREFA_PELO_ID, parametros);
    }
    
}
