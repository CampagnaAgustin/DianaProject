package com.app.DianaProyect;

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
