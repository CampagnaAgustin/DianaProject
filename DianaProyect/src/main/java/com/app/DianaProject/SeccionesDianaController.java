package com.app.DianaProject;

import java.sql.SQLException;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.app.model.Usuario;

@Controller
public class SeccionesDianaController {
	
	@Autowired
	private UsuariosDianaHelper UsuariosDianaHelper;
	
	@GetMapping("/home")
	public String inicioHome(HttpSession session, Model template) throws SQLException {
		UsuariosDianaHelper.isLoggedIn(session, template);
		UsuariosDianaHelper.datosLogueado(session,  template);
		return "inicio";
	}
	
	@GetMapping("/")
	public String inicioNull () {
		return "redirect:/home";
	}
	
	@GetMapping("/centros-culturales")
	public String centrosCulturales (HttpSession session, Model template) throws SQLException {
		UsuariosDianaHelper.isLoggedIn(session, template);
		UsuariosDianaHelper.datosLogueado(session,  template);
		template.addAttribute("culturalesActive", "active");
		return "culturales";
	}
	
	@GetMapping("/nosotres")
	public String nosotres (HttpSession session, Model template) throws SQLException {
		UsuariosDianaHelper.isLoggedIn(session, template);
		UsuariosDianaHelper.datosLogueado(session,  template);
		template.addAttribute("nosotresActive", "active");
		return "nosotres";
	}

	// inicio registro, eleccion entre Artista o Usuario
	@GetMapping("/registro")
	public String registrar(HttpSession session, Model template) throws SQLException {
		UsuariosDianaHelper.isLoggedIn(session, template);
		UsuariosDianaHelper.datosLogueado(session,  template);
		template.addAttribute("registroActive", "active");
		return "registro";
	}

	@GetMapping("/registro-artista")
	public String registrarArtista(HttpSession session, Model template) throws SQLException {
		UsuariosDianaHelper.isLoggedIn(session, template);
		UsuariosDianaHelper.datosLogueado(session,  template);
		template.addAttribute("registroActive", "active");
		return "registroArtista";
	}
	
	@GetMapping("/registro-usuario")
	public String registrarUsuario(HttpSession session, Model template) throws SQLException {
		UsuariosDianaHelper.isLoggedIn(session, template);
		UsuariosDianaHelper.datosLogueado(session,  template);
		template.addAttribute("registroActive", "active");
		return "registroUsuario";
	}
	
	@GetMapping("/login")
	public String login(HttpSession session, Model template) throws SQLException {
		Usuario logueado = UsuariosDianaHelper.usuarioLogueado(session);
		if (logueado != null) {
		return "redirect:/home";
		}
		UsuariosDianaHelper.isLoggedIn(session, template);
		UsuariosDianaHelper.datosLogueado(session,  template);
		template.addAttribute("loginActive", "active");
		return "login";
	}
	
}
