package com.mballem.curso.security.web.controller;

import java.security.Principal;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.mballem.curso.security.domain.Medico;
import com.mballem.curso.security.domain.Usuario;
import com.mballem.curso.security.service.MedicoService;
import com.mballem.curso.security.service.UsuarioService;

@Controller
@RequestMapping("medicos")
public class MedicoController {
	
	@Autowired
	private MedicoService ms;
	
	@Autowired
	private UsuarioService us;

	// abrir pagina de dados pessoais de medicos pelo MEDICO
	@GetMapping({"/dados"})
	public String abrirPorMedico(Medico medico, ModelMap model, @AuthenticationPrincipal User user) {
		
		if(medico.hasNotId()) {
			medico = ms.buscarPorEmail(user.getUsername());
			model.addAttribute("medico", medico);
		}
		return "medico/cadastro";
	}
	
	// salvar medico
	@PostMapping({"/salvar"})
	public String salvar(Medico medico, RedirectAttributes attr, 
			@AuthenticationPrincipal User user) {
		if(medico.hasNotId() && medico.getUsuario().hasNotId()) {
			Usuario usuario = us.buscarPorEmail(user.getUsername());
			medico.setUsuario(usuario);
		}
		ms.salvar(medico);
		attr.addFlashAttribute("sucesso", "Operação realidada com sucesso");
		attr.addFlashAttribute("medico", medico);
		
		return "redirect:/medicos/dados";
	}
	
	// editar medico
	@PostMapping({"/editar"})
	public String editar(Medico medico, RedirectAttributes attr) {
		ms.editar(medico);
		attr.addFlashAttribute("sucesso", "Operação realidada com sucesso");
		attr.addFlashAttribute("medico", medico);
		
		return "redirect:/medicos/dados";
	}
	
	// excluir especialidades
		@GetMapping({"/id/{idMed}/excluir/especializacao/{idEsp}"})
		public String excluirEspecialidadePorMedico(@PathVariable("idMed") Long idMed, @PathVariable("idEsp") Long idEsp, RedirectAttributes attr) {
			
			ms.excluirEspecialidadePorMedico(idMed, idEsp);
			attr.addFlashAttribute("sucesso", "Especialidade removida com sucesso!");
			
			return "redirect:/medicos/dados";
		}
}
