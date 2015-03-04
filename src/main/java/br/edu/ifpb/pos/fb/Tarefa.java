package br.edu.ifpb.pos.fb;

import com.restfb.types.User;
import java.io.Serializable;
import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import org.codehaus.jackson.annotate.JsonIgnore;

/**
 *
 * @author Kelson
 */

@Entity
@XmlRootElement
public class Tarefa implements Serializable {    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)    
    private Long id;
    
    private String nome;
    private String descricao;
    
    @Temporal(TemporalType.DATE)    
    private Date dataCriacao;
    
    @Temporal(TemporalType.DATE)
    private Date dataLimiteExecucao;
    
    @Temporal(TemporalType.DATE)
    private Date dataExecucao;
    
    private String prioridade;    
    private String  status;    
    
    @Transient
    private User responsavel;    
    @Transient
    private User criador;    
    
    private String idResponsavel;
    private String idCriador;        
            
    public Tarefa() {        
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Date getDataCriacao() {
        return dataCriacao;
    }

    public void setDataCriacao(Date dataCriacao) {
        this.dataCriacao = dataCriacao;
    }

    public Date getDataLimiteExecucao() {
        return dataLimiteExecucao;
    }

    public void setDataLimiteExecucao(Date dataLimiteExecucao) {
        this.dataLimiteExecucao = dataLimiteExecucao;
    }

    public Date getDataExecucao() {
        return dataExecucao;
    }

    public void setDataExecucao(Date dataExecucao) {
        this.dataExecucao = dataExecucao;
    }    

    public String getPrioridade() {
        return prioridade;
    }

    public void setPrioridade(String prioridade) {
        this.prioridade = prioridade;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @XmlTransient
    @JsonIgnore
    public User getResponsavel() {
        return responsavel;
    }

    public void setResponsavel(User usuario) {
        this.responsavel = usuario;
    }

    @XmlTransient
    @JsonIgnore
    public User getCriador() {
        return criador;
    }

    public void setCriador(User criador) {
        this.criador = criador;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getIdResponsavel() {
        return idResponsavel;
    }

    public void setIdResponsavel(String idResponsavel) {
        this.idResponsavel = idResponsavel;
    }

    public String getIdCriador() {
        return idCriador;
    }

    public void setIdCriador(String idCriador) {
        this.idCriador = idCriador;
    }        

    public String getNome() {
        return this.nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }
    
    
}
