package com.mballem.curso.security.web.controller;

import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

	// abrir pagina home
	@GetMapping({"/","home"})
	public String home() {
		return "home";
	}

	// abrir pagina Login
	@GetMapping("/login")
	public String login() {
		return "login";
	}

	// abrir pagina Erro no Login
	@GetMapping("/login-error")
	public String loginError(ModelMap model) {
		model.addAttribute("alerta", "erro");
		model.addAttribute("titulo", "Credenciais invalidas");
		model.addAttribute("texto", "Login ou senha incorreto");
		model.addAttribute("subtexto", "Acesso permitido apenas para cadastrados ja ativos");
		
		return "login";
	}
	
	// abrir pagina Acesso Negado
	@GetMapping("/acesso-negado")
	public String acessoNegado(ModelMap model, HttpServletResponse resp) {
		model.addAttribute("status", resp.getStatus());
		model.addAttribute("error", "Acesso Negado");
		model.addAttribute("message", "Você não tem permissão");
			
		return "error";
	}
}
