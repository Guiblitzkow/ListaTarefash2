package com.javaweb.Repositorio_ListaTarefas.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import com.javaweb.Repositorio_ListaTarefas.model.Usuario;

	public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
		
		Optional<Usuario> findByEmailAndSenha(String email, String senha);
		
		Optional<Usuario> findByEmail(String email);
	}
	
