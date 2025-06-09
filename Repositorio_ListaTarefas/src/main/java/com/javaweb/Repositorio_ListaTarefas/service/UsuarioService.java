package com.javaweb.Repositorio_ListaTarefas.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.javaweb.Repositorio_ListaTarefas.model.Usuario;
import com.javaweb.Repositorio_ListaTarefas.repository.UsuarioRepository;

import java.util.Optional;

@Service
public class UsuarioService {
	
	@Autowired
	private UsuarioRepository usuarioRepository;

    public Optional<Usuario> autenticar(String email, String senha) {
        return usuarioRepository.findByEmailAndSenha(email, senha);
    }

    public boolean cadastrarUsuario(Usuario usuario) {
    	if(!usuario.getSenha().equals(usuario.getConfirmaSenha())) {
    		return false;
    	}
    
    	usuarioRepository.save(usuario);
    	return true;
   
   }
}