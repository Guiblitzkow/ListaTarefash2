package com.javaweb.Repositorio_ListaTarefas.Controller;

import com.javaweb.Repositorio_ListaTarefas.model.Usuario;
import com.javaweb.Repositorio_ListaTarefas.service.UsuarioService;

import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Optional; 

@Controller
public class UsuarioController {

    private final UsuarioService usuarioService;

    @Autowired
    public UsuarioController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @GetMapping("/login")
    public String mostrar_TelaLogin() {
        return "login"; 
    }

    @PostMapping("/validar")
    public String validarCredenciais(@RequestParam String email,
                                    @RequestParam String senha,
                                    Model modelo,
                                    HttpSession sessao) {

        System.out.println("Controller: Attempting login with email: " + email);

        Optional<Usuario> usuarioOptional = usuarioService.autenticar(email, senha);

        if (usuarioOptional.isPresent()) {
            sessao.setAttribute("usuarioLogado", usuarioOptional.get());
            return "redirect:/home";
        } else {
            System.out.println("Controller: Login failed for email: " + email);
            modelo.addAttribute("msg", "Email ou Senha incorretos");
            return "login";
        }
    }

    @GetMapping("/home")
    public String mostraTelaInicial(HttpSession session, Model model) {
        Usuario usuario = (Usuario) session.getAttribute("usuarioLogado");
        if(usuario == null) {
            model.addAttribute("msg", "Sessao expirou ou usuario deslogado");
            return "redirect:/login";
        }
        else {
            model.addAttribute("NomeUsuario", usuario.getNome());
            return "home";
        }
    }

    @GetMapping("/logout")
    public String logout(HttpSession sessao) {
        sessao.invalidate();
        return "redirect:/login";
    }

    @GetMapping("/cadastro")
    public String telaCadastro(Model model) {
        model.addAttribute("usuario", new Usuario()); 
        return "cadastro"; 
    }

    @PostMapping("/cadastrar")
    public String cadastrarUsuario(@ModelAttribute Usuario usuario, Model model) {

        boolean sucesso = usuarioService.cadastrarUsuario(usuario);

        if (!sucesso) {
             model.addAttribute("erro", "As senhas não coincidem");
             return "cadastro"; 
        }

        return "redirect:/login"; 
    }
}