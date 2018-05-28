package com.app.DianaProject;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class SeccionesDianaController {
	
	@GetMapping("/home")
	public String inicioHome() {
		return "estructura";
	}
	
	@GetMapping("/")
	public String inicioNull () {
		return "redirect:/home";
	}
	
	/*
	@GetMapping("/artistas-musicales")
	public String musicales () {
		return "musicales";
	}
	
	@GetMapping("/artistas-teatrales")
	public String teatrales () {
		return "teatrales";
	}
	
	@GetMapping("/artistas-literarios")
	public String literarios () {
		return "literarios";
	}
	
	@GetMapping("/artistas-graficos")
	public String graficos () {
		return "graficos";
	}
	
	@GetMapping("/artistas-interdisciplinarios")
	public String interdisciplinarios () {
		return "interdisciplinarios";
	}
	*/
	
	@GetMapping("/centros-culturales")
	public String centrosCulturales () {
		return "culturales";
	}
	
	@GetMapping("/nosotres")
	public String nosotres () {
		return "nosotres";
	}

	// inicio registro, eleccion entre Artista o Usuario
	@GetMapping("/registro")
	public String registrar() {
		return "registro";
	}

	@GetMapping("/registro-artista")
	public String registrarArtista() {
		return "registroArtista";
	}
	
	@GetMapping("/registro-usuario")
	public String registrarUsuario() {
		return "registroUsuario";
	}
	
	@GetMapping("/login")
	public String login() {
		return "login";
	}
	
	
}
