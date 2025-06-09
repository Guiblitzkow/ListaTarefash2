package com.javaweb.Repositorio_ListaTarefas.model;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class Tarefas{
	
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
	
    private String descricao;
    private boolean concluida;
    private String dataCriacao; 
    private String dataConclusao; 
    
    @ManyToOne
    @JoinColumn(name = "usuario_id")
    private Usuario usuario;
    
    public Tarefas() {
    	this.dataCriacao = LocalDate.now().format(DateTimeFormatter.ISO_DATE);
    	this.concluida = false;
    }

     public Tarefas(String descricao, Usuario usuario) {
        this();
    	this.descricao = descricao;
        this.usuario = usuario;
    }

    

    public long getId() { return id; }

    public String getDescricao() { return descricao; }
    public void setDescricao(String descricao) { this.descricao = descricao; }

    public boolean isConcluida() { return concluida; }
    public void setConcluida(boolean concluida) { this.concluida = concluida; }

    public String getDataCriacao() { return dataCriacao; }
    public void setDataCriacao(String dataCriacao) { this.dataCriacao = dataCriacao; } // Permitir set, se necessário

    public String getDataConclusao() { return dataConclusao; }
    public void setDataConclusao(String dataConclusao) { this.dataConclusao = dataConclusao; }

    public Usuario getUsuario() { return usuario; }
    public void setUsuario(Usuario usuario) { this.usuario = usuario; }
}
