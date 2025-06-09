package com.javaweb.Repositorio_ListaTarefas.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.javaweb.Repositorio_ListaTarefas.model.Tarefas;

	public interface TarefasRepository extends JpaRepository<Tarefas, Long>{
		
		List<Tarefas> findByUsuarioEmail(String email);
}
