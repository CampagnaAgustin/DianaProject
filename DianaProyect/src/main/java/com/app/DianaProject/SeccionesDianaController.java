package com.app.DianaProject;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.app.model.Post;
import com.app.model.Usuario;

@Controller
public class SeccionesDianaController {
	
	@Autowired
	private Environment env;
	
	@Autowired
	private UsuariosDianaHelper UsuariosDianaHelper;
	
	@GetMapping("/home")
	public String inicioHome(HttpSession session, Model template) throws SQLException {
		
		Connection connection;
		connection = DriverManager.getConnection(env.getProperty("spring.datasource.url"),
				env.getProperty("spring.datasource.username"), env.getProperty("spring.datasource.password"));
		
		PreparedStatement consulta = connection.prepareStatement("SELECT * FROM posts WHERE id_usuario = 33 ORDER BY id DESC;");
		
		ResultSet resultado = consulta.executeQuery();

		ArrayList<Post> listadoPosteos = new ArrayList<Post>();

		while (resultado.next()) {
			int id = resultado.getInt("id");
			String titulo = resultado.getString("titulo");
			String texto = resultado.getString("texto");
			int idUsuario = resultado.getInt("id_usuario");
			String imagenPost = resultado.getString("imagen_post");

			Post x = new Post(id, titulo, texto, idUsuario, imagenPost);
			listadoPosteos.add(x);
		}
		
		UsuariosDianaHelper.isLoggedIn(session, template);
		UsuariosDianaHelper.datosLogueado(session,  template);
		UsuariosDianaHelper.isAdmin(session, template);
		template.addAttribute("listadoPosteos", listadoPosteos);
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
