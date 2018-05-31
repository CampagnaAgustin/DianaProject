package com.app.DianaProject;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class SeccionesDianaController {
	
	@GetMapping("/home")
	public String inicioHome() {
		return "inicio";
	}
	
	@GetMapping("/")
	public String inicioNull () {
		return "redirect:/home";
	}
	
	@GetMapping("/centros-culturales")
	public String centrosCulturales (Model template) {
		template.addAttribute("culturalesActive", "active");
		return "culturales";
	}
	
	@GetMapping("/nosotres")
	public String nosotres (Model template) {
		template.addAttribute("nosotresActive", "active");
		return "nosotres";
	}

	// inicio registro, eleccion entre Artista o Usuario
	@GetMapping("/registro")
	public String registrar(Model template) {
		template.addAttribute("registroActive", "active");
		return "registro";
	}

	@GetMapping("/registro-artista")
	public String registrarArtista(Model template) {
		template.addAttribute("registroActive", "active");
		return "registroArtista";
	}
	
	@GetMapping("/registro-usuario")
	public String registrarUsuario(Model template) {
		template.addAttribute("registroActive", "active");
		return "registroUsuario";
	}
	
	@GetMapping("/login")
	public String login(Model template) {
		template.addAttribute("loginActive", "active");
		return "login";
	}
	
	
}
