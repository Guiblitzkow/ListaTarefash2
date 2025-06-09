package com.javaweb.Repositorio_ListaTarefas.service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.javaweb.Repositorio_ListaTarefas.model.Tarefas;
import com.javaweb.Repositorio_ListaTarefas.model.Usuario;
import com.javaweb.Repositorio_ListaTarefas.repository.TarefasRepository;
import com.javaweb.Repositorio_ListaTarefas.repository.UsuarioRepository;

@Service
public class TarefasService {

    @Autowired
    private TarefasRepository tarefasRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    public List<Tarefas> getTarefasPorUsuario(String emailUsuario) {
        return tarefasRepository.findByUsuarioEmail(emailUsuario);
    }

    public void adicionarTarefa(String emailUsuario, String descricao) {
        Usuario usuario = usuarioRepository.findByEmail(emailUsuario)
        .orElseThrow(() -> new RuntimeException("Usuário não encontrado com o email:" + emailUsuario));


        Tarefas novaTarefa = new Tarefas(descricao, usuario);
        novaTarefa.setConcluida(false);
        novaTarefa.setDataCriacao(LocalDate.now().format(DateTimeFormatter.ISO_DATE));
        tarefasRepository.save(novaTarefa);
    }

    public boolean marcarComoConcluida(String emailUsuario, long tarefaId, String dataConclusaoStr) {
        return tarefasRepository.findById(tarefaId).map(tarefa -> {
            if (!tarefa.getUsuario().getEmail().equals(emailUsuario)) return false;

            tarefa.setConcluida(true);
            try {
                LocalDate dataConclusao = LocalDate.parse(dataConclusaoStr, DateTimeFormatter.ISO_DATE);
                tarefa.setDataConclusao(dataConclusao.format(DateTimeFormatter.ISO_DATE));
            } catch (Exception e) {
                tarefa.setDataConclusao(LocalDate.now().format(DateTimeFormatter.ISO_DATE));
            }

            tarefasRepository.save(tarefa);
            return true;
        }).orElse(false);
    }

    public boolean removerTarefa(String emailUsuario, long tarefaId) {
        return tarefasRepository.findById(tarefaId).map(tarefa -> {
            if (!tarefa.getUsuario().getEmail().equals(emailUsuario)) return false;

            tarefasRepository.delete(tarefa);
            return true;
        }).orElse(false);
    }
}
