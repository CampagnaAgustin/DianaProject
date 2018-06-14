package com.app.DianaProject;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.servlet.http.HttpSession;

import org.simplejavamail.email.Email;
import org.simplejavamail.email.EmailBuilder;
import org.simplejavamail.mailer.MailerBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.app.model.Post;
import com.app.model.Usuario;

@Controller
public class UsuariosDianaController {

	@Autowired
	private Environment env;

	@Autowired
	private UsuariosDianaHelper UsuariosDianaHelper;

	@GetMapping("/test")
	public String test(Model template) throws SQLException {

		Connection connection;
		connection = DriverManager.getConnection(env.getProperty("spring.datasource.url"),
				env.getProperty("spring.datasource.username"), env.getProperty("spring.datasource.password"));

		PreparedStatement consulta = connection.prepareStatement("SELECT * FROM posts WHERE id_usuario = 14;");

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
		template.addAttribute("listadoPosteos", listadoPosteos);

		connection.close();

		return "test";
	}

	// comando registro de artistas
	@PostMapping("/bienvenido-artista")
	public String bienvenidoArtista(@RequestParam String nombre, @RequestParam String email,
			@RequestParam String password, @RequestParam String tipo) throws SQLException {
		Connection connection;
		connection = DriverManager.getConnection(env.getProperty("spring.datasource.url"),
				env.getProperty("spring.datasource.username"), env.getProperty("spring.datasource.password"));

		PreparedStatement verify = connection.prepareStatement("SELECT nombre FROM usuarios WHERE nombre = ?;");

		verify.setString(1, nombre);

		ResultSet resultado = verify.executeQuery();

		if (resultado.next()) {
			nombre = resultado.getString("nombre");
			return "redirect:/registro-artista";
		}

		PreparedStatement verify2 = connection.prepareStatement("SELECT email FROM usuarios WHERE email = ?;");

		verify2.setString(1, email);

		ResultSet resultado2 = verify2.executeQuery();

		if (resultado2.next()) {
			email = resultado2.getString("email");
			return "redirect:/registro-artista";
		}

		PreparedStatement consulta = connection.prepareStatement(
				"INSERT INTO usuarios(nombre, email, contrasenia, artista, tipo) VALUES(?, ?, ?, true, ?);");
		consulta.setString(1, nombre);
		consulta.setString(2, email);
		consulta.setString(3, password);
		consulta.setString(4, tipo);

		consulta.executeUpdate();

		/*
		 * Email email1 = EmailBuilder.startingBlank() .from("Winky",
		 * "campagna.agustin@gmail.com") .to("Myself", "campagna.agustin@gmail.com")
		 * .withSubject("Artista nuevo")
		 * .withPlainText("Un artista nuevo se ha registrado en la pagina")
		 * .buildEmail();
		 * 
		 * MailerBuilder .withSMTPServer("smtp.sendgrid.net", 587, "apikey",
		 * "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")
		 * .buildMailer() .sendMail(email1);
		 */

		connection.close();
		return "redirect:/login";
	}

	@PostMapping("/bienvenido-usuario")
	public String bienvenidoUsuario(@RequestParam String nombre, @RequestParam String email1,
			@RequestParam String password) throws SQLException {
		Connection connection;
		connection = DriverManager.getConnection(env.getProperty("spring.datasource.url"),
				env.getProperty("spring.datasource.username"), env.getProperty("spring.datasource.password"));

		PreparedStatement verify = connection.prepareStatement("SELECT nombre FROM usuarios WHERE nombre = ?;");

		verify.setString(1, nombre);

		ResultSet resultado = verify.executeQuery();

		if (resultado.next()) {
			nombre = resultado.getString("nombre");
			return "redirect:/registro-usuario";
		}

		PreparedStatement verify2 = connection.prepareStatement("SELECT email FROM usuarios WHERE email = ?;");

		verify2.setString(1, email1);

		ResultSet resultado2 = verify2.executeQuery();

		if (resultado2.next()) {
			email1 = resultado2.getString("email");
			return "redirect:/registro-usuario";
		}
		PreparedStatement consulta = connection
				.prepareStatement("INSERT INTO usuarios(nombre, email, contrasenia, artista) VALUES(?, ?, ?, false);");
		consulta.setString(1, nombre);
		consulta.setString(2, email1);
		consulta.setString(3, password);

		consulta.executeUpdate();

		Email email = EmailBuilder.startingBlank().from("Winky", "campagna.agustin@gmail.com")
				.to("Myself", "campagna.agustin@gmail.com").withSubject("Se registro un usuario")
				.withPlainText("Un usuario nuevo se ha registrado en la pagina").buildEmail();

		MailerBuilder
				.withSMTPServer("smtp.sendgrid.net", 587, "apikey",
						"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")
				.buildMailer().sendMail(email);

		connection.close();
		return "redirect:/login";
	}

	@PostMapping("/procesar-login")
	public String processLogin(HttpSession session, @RequestParam String nombre, @RequestParam String password)
			throws SQLException {
		boolean sePudo = UsuariosDianaHelper.intentarLoguearse(session, nombre, password);
		if (sePudo) {
			return "redirect:/";
		} else {
			// TODO: precargar los datos que lleno, salvo la contraseña
			return "login";
		}
	}

	@GetMapping("/logout")
	public String logout(HttpSession session) throws SQLException {
		UsuariosDianaHelper.cerrarSesion(session);
		return "redirect:/home";
	}

	// muestra un usuario en detalle
	@GetMapping("/detalle/{id}")
	public String detalle(HttpSession session, Model template, @PathVariable int id) throws SQLException {

		Connection connection;
		connection = DriverManager.getConnection(env.getProperty("spring.datasource.url"),
				env.getProperty("spring.datasource.username"), env.getProperty("spring.datasource.password"));

		PreparedStatement consulta = connection.prepareStatement("SELECT * FROM usuarios WHERE id = ? ORDER BY id DESC;");

		consulta.setInt(1, id);

		template.addAttribute("id", id);

		ResultSet resultado = consulta.executeQuery();

		if (resultado.next()) {
			String nombre = resultado.getString("nombre");
			String email = resultado.getString("email");
			String password = resultado.getString("contrasenia");
			boolean artista = resultado.getBoolean("artista");
			String localidad = resultado.getString("localidad");
			String influencias = resultado.getString("influencias");
			String descripcion = resultado.getString("descripcion");
			String genero = resultado.getString("genero");
			String integrantes = resultado.getString("integrantes");
			String imagen = resultado.getString("imagen");
			String tipo = resultado.getString("tipo");
			String facebook = resultado.getString("facebook");
			String instagram = resultado.getString("instagram");
			String twitter = resultado.getString("twitter");
			String web = resultado.getString("web");
			String youtube = resultado.getString("youtube");
			String link1 = resultado.getString("link1");
			String link2 = resultado.getString("link2");
			String link3 = resultado.getString("link3");
			String link4 = resultado.getString("link4");
			String link5 = resultado.getString("link5");

			template.addAttribute("nombre", nombre);
			template.addAttribute("email", email);
			template.addAttribute("password", password);
			template.addAttribute("artista", artista);
			template.addAttribute("localidad", localidad);
			template.addAttribute("influencias", influencias);
			template.addAttribute("descripcion", descripcion);
			template.addAttribute("genero", genero);
			template.addAttribute("integrantes", integrantes);
			template.addAttribute("imagen", imagen);
			template.addAttribute("tipo", tipo);
			template.addAttribute("facebook", facebook);
			template.addAttribute("instagram", instagram);
			template.addAttribute("twitter", twitter);
			template.addAttribute("web", web);
			template.addAttribute("youtube", youtube);
			template.addAttribute("link1", link1);
			template.addAttribute("link2", link2);
			template.addAttribute("link3", link3);
			template.addAttribute("link4", link4);
			template.addAttribute("link5", link5);
			
		}

		PreparedStatement consulta2 = connection.prepareStatement("SELECT * FROM posts WHERE id_usuario = ?;");

		consulta2.setInt(1, id);

		ResultSet resultado2 = consulta2.executeQuery();

		ArrayList<Post> listadoPosteos = new ArrayList<Post>();

		while (resultado2.next()) {
			String titulo = resultado2.getString("titulo");
			String texto = resultado2.getString("texto");
			int idUsuario = resultado2.getInt("id_usuario");
			String imagenPost = resultado2.getString("imagen_post");

			Post x = new Post(id, titulo, texto, idUsuario, imagenPost);
			listadoPosteos.add(x);
		}

		UsuariosDianaHelper.isLoggedIn(session, template);
		int idLogueado = UsuariosDianaHelper.datosLogueado(session, template);
		Boolean mismaId = idLogueado == id;
		System.out.print(mismaId);
		template.addAttribute("idLogueado", idLogueado);
		template.addAttribute("listadoPosteos", listadoPosteos);

		connection.close();

		return "detalleUsuario";
	}

	//ruta edicion de usuario
	@GetMapping("/editar/{id}")
	public String editar(HttpSession session, Model template, @PathVariable int id) throws SQLException {

		int logueado = UsuariosDianaHelper.datosLogueado(session, template);

		if (id != logueado) {
			return "redirect:/editar/" + logueado;
		}

		Connection connection;
		connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/Diana", "postgres", "01100110f");

		PreparedStatement consulta = connection.prepareStatement("SELECT * FROM usuarios WHERE id = ?;");

		consulta.setInt(1, id);

		ResultSet resultado = consulta.executeQuery();

		if (resultado.next()) {
			String nombre = resultado.getString("nombre");
			String email = resultado.getString("email");
			String password = resultado.getString("contrasenia");
			boolean artista = resultado.getBoolean("artista");
			String localidad = resultado.getString("localidad");
			String influencias = resultado.getString("influencias");
			String descripcion = resultado.getString("descripcion");
			String genero = resultado.getString("genero");
			String integrantes = resultado.getString("integrantes");
			String imagen = resultado.getString("imagen");
			String tipo = resultado.getString("tipo");
			boolean admin = resultado.getBoolean("artista");
			String facebook = resultado.getString("facebook");
			String instagram = resultado.getString("instagram");
			String twitter = resultado.getString("twitter");
			String web = resultado.getString("web");
			String youtube = resultado.getString("youtube");
			String link1 = resultado.getString("link1");
			String link2 = resultado.getString("link2");
			String link3 = resultado.getString("link3");
			String link4 = resultado.getString("link4");
			String link5 = resultado.getString("link5");

			template.addAttribute("nombre", nombre);
			template.addAttribute("email", email);
			template.addAttribute("password", password);
			template.addAttribute("artista", artista);
			template.addAttribute("localidad", localidad);
			template.addAttribute("influencias", influencias);
			template.addAttribute("descripcion", descripcion);
			template.addAttribute("genero", genero);
			template.addAttribute("integrantes", integrantes);
			template.addAttribute("imagen", imagen);
			template.addAttribute("tipo", tipo);
			template.addAttribute("admin", admin);
			template.addAttribute("facebook", facebook);
			template.addAttribute("instagram", instagram);
			template.addAttribute("twitter", twitter);
			template.addAttribute("web", web);
			template.addAttribute("youtube", youtube);
			template.addAttribute("link1", link1);
			template.addAttribute("link2", link2);
			template.addAttribute("link3", link3);
			template.addAttribute("link4", link4);
			template.addAttribute("link5", link5);
		}

		UsuariosDianaHelper.isLoggedIn(session, template);
		UsuariosDianaHelper.datosLogueado(session, template);
		return "editarUsuario";
	}

	// Accion editar usuarios
	@PostMapping("/modificar/{id}")
	public String modificar(Model template, @PathVariable int id, @RequestParam String nombre,
			@RequestParam String email, @RequestParam String password, @RequestParam String localidad,
			@RequestParam String influencias, @RequestParam String descripcion, @RequestParam String genero,
			@RequestParam String integrantes, @RequestParam String imagen, @RequestParam String facebook,
			@RequestParam String instagram, @RequestParam String twitter, @RequestParam String web,
			@RequestParam String youtube, @RequestParam String link1, @RequestParam String link2,
			@RequestParam String link3, @RequestParam String link4, @RequestParam String link5) throws SQLException {

		Connection connection;
		connection = DriverManager.getConnection(env.getProperty("spring.datasource.url"),
				env.getProperty("spring.datasource.username"), env.getProperty("spring.datasource.password"));

		PreparedStatement consulta = connection.prepareStatement(
				"UPDATE usuarios SET nombre = ?, email = ?, contrasenia = ?, localidad = ?, influencias = ?, descripcion = ?, genero = ?, integrantes = ?, imagen = ?, facebook = ?, instagram = ?, twitter = ?, web = ?, youtube = ?, link1 = ?, link2 = ?, link3 = ?, link4 = ?, link5 = ? WHERE id = ?;");

		consulta.setString(1, nombre);
		consulta.setString(2, email);
		consulta.setString(3, password);
		consulta.setString(4, localidad);
		consulta.setString(5, influencias);
		consulta.setString(6, descripcion);
		consulta.setString(7, genero);
		consulta.setString(8, integrantes);
		consulta.setString(9, imagen);
		consulta.setString(10, facebook);
		consulta.setString(11, instagram);
		consulta.setString(12, twitter);
		consulta.setString(13, web);
		consulta.setString(14, youtube);
		consulta.setString(15, link1);
		consulta.setString(16, link2);
		consulta.setString(17, link3);
		consulta.setString(18, link4);
		consulta.setString(19, link5);
		consulta.setInt(20, id);

		consulta.executeUpdate();

		connection.close();

		return "redirect:/detalle/{id}";
	}

	@GetMapping("/artistas/todes")
	public String listadoTodes(HttpSession session, Model template) throws SQLException {

		Connection connection;
		connection = DriverManager.getConnection(env.getProperty("spring.datasource.url"),
				env.getProperty("spring.datasource.username"), env.getProperty("spring.datasource.password"));

		PreparedStatement consulta = connection.prepareStatement("SELECT * FROM usuarios WHERE artista = true ORDER BY nombre ASC;");

		ResultSet resultado = consulta.executeQuery();

		ArrayList<Usuario> listadoUsuarios = new ArrayList<Usuario>();

		while (resultado.next()) {
			int id = resultado.getInt("id");
			String nombre = resultado.getString("nombre");
			String email = resultado.getString("email");
			String password = resultado.getString("contrasenia");
			boolean artista = resultado.getBoolean("artista");
			String localidad = resultado.getString("localidad");
			String influencias = resultado.getString("influencias");
			String genero = resultado.getString("genero");
			String integrantes = resultado.getString("integrantes");
			String descripcion = resultado.getString("descripcion");
			String imagen = resultado.getString("imagen");
			String tipo = resultado.getString("tipo");
			boolean admin = resultado.getBoolean("admin");
			String facebook = resultado.getString("facebook");
			String instagram = resultado.getString("instagram");
			String twitter = resultado.getString("twitter");
			String web = resultado.getString("web");
			String youtube = resultado.getString("youtube");
			String link1 = resultado.getString("link1");
			String link2 = resultado.getString("link2");
			String link3 = resultado.getString("link3");
			String link4 = resultado.getString("link4");
			String link5 = resultado.getString("link5");

			Usuario x = new Usuario(id, nombre, email, password, artista, localidad, influencias, genero, integrantes,
					descripcion, imagen, tipo, admin, facebook, instagram, twitter, web, youtube, link1, link2, link3,
					link4, link5);
			listadoUsuarios.add(x);
		}

		UsuariosDianaHelper.isLoggedIn(session, template);
		UsuariosDianaHelper.datosLogueado(session, template);
		template.addAttribute("listadoUsuarios", listadoUsuarios);
		template.addAttribute("artistasActive", "active");

		connection.close();
		return "listadoUsuarios";
	}

	@GetMapping("/artistas/{tipo}")
	public String listadoTipo(HttpSession session, Model template, @PathVariable String tipo) throws SQLException {

		Connection connection;
		connection = DriverManager.getConnection(env.getProperty("spring.datasource.url"),
				env.getProperty("spring.datasource.username"), env.getProperty("spring.datasource.password"));

		PreparedStatement consulta = connection
				.prepareStatement("SELECT * FROM usuarios WHERE artista = true AND tipo = ? ORDER BY nombre ASC;");

		consulta.setString(1, tipo);

		ResultSet resultado = consulta.executeQuery();

		ArrayList<Usuario> listadoUsuarios = new ArrayList<Usuario>();

		while (resultado.next()) {
			int id = resultado.getInt("id");
			String nombre = resultado.getString("nombre");
			String email = resultado.getString("email");
			String password = resultado.getString("contrasenia");
			boolean artista = resultado.getBoolean("artista");
			String localidad = resultado.getString("localidad");
			String influencias = resultado.getString("influencias");
			String genero = resultado.getString("genero");
			String integrantes = resultado.getString("integrantes");
			String descripcion = resultado.getString("descripcion");
			String imagen = resultado.getString("imagen");
			tipo = resultado.getString("tipo");
			boolean admin = resultado.getBoolean("admin");
			String facebook = resultado.getString("facebook");
			String instagram = resultado.getString("instagram");
			String twitter = resultado.getString("twitter");
			String web = resultado.getString("web");
			String youtube = resultado.getString("youtube");
			String link1 = resultado.getString("link1");
			String link2 = resultado.getString("link2");
			String link3 = resultado.getString("link3");
			String link4 = resultado.getString("link4");
			String link5 = resultado.getString("link5");

			Usuario x = new Usuario(id, nombre, email, password, artista, localidad, influencias, genero, integrantes,
					descripcion, imagen, tipo, admin, facebook, instagram, twitter, web, youtube, link1, link2, link3,
					link4, link5);
			listadoUsuarios.add(x);
		}

		UsuariosDianaHelper.isLoggedIn(session, template);
		UsuariosDianaHelper.datosLogueado(session, template);
		template.addAttribute("listadoUsuarios", listadoUsuarios);
		template.addAttribute("artistasActive", "active");

		connection.close();
		return "listadoUsuarios";
	}

	// muestra el listado completo de usuarios
	@GetMapping("/procesarBusqueda")
	public String procesarBusqueda(HttpSession session, Model template, @RequestParam String palabraBuscada)
			throws SQLException {

		Connection connection;
		connection = DriverManager.getConnection(env.getProperty("spring.datasource.url"),
				env.getProperty("spring.datasource.username"), env.getProperty("spring.datasource.password"));

		PreparedStatement consulta = connection.prepareStatement("SELECT * FROM usuarios WHERE nombre LIKE ?;");
		consulta.setString(1, "%" + palabraBuscada + "%");

		ResultSet resultado = consulta.executeQuery();

		ArrayList<Usuario> listadoUsuarios = new ArrayList<Usuario>();

		while (resultado.next()) {
			int id = resultado.getInt("id");
			String nombre = resultado.getString("nombre");
			String email = resultado.getString("email");
			String contrasenia = resultado.getString("contrasenia");
			boolean artista = resultado.getBoolean("artista");
			String localidad = resultado.getString("localidad");
			String influencias = resultado.getString("influencias");
			// date nacimiento = resultado.get
			String genero = resultado.getString("genero");
			String integrantes = resultado.getString("integrantes");
			String descripcion = resultado.getString("descripcion");
			String imagen = resultado.getString("imagen");
			String tipo = resultado.getString("tipo");
			boolean admin = resultado.getBoolean("admin");
			String facebook = resultado.getString("facebook");
			String instagram = resultado.getString("instagram");
			String twitter = resultado.getString("twitter");
			String web = resultado.getString("web");
			String youtube = resultado.getString("youtube");
			String link1 = resultado.getString("link1");
			String link2 = resultado.getString("link2");
			String link3 = resultado.getString("link3");
			String link4 = resultado.getString("link4");
			String link5 = resultado.getString("link5");

			Usuario x = new Usuario(id, nombre, email, contrasenia, artista, localidad, influencias, genero,
					integrantes, descripcion, imagen, tipo, admin, facebook, instagram, twitter, web, youtube, link1,
					link2, link3, link4, link5);
			listadoUsuarios.add(x);
		}

		UsuariosDianaHelper.isLoggedIn(session, template);
		UsuariosDianaHelper.datosLogueado(session, template);
		template.addAttribute("listadoUsuarios", listadoUsuarios);

		connection.close();
		return "listadoUsuarios";
	}

	// controlador posteos
	@PostMapping("/insertarPost")
	public String test(HttpSession session, Model template, @RequestParam String texto,
			@RequestParam String imagen_post, @RequestParam String titulo) throws SQLException {

		int id_usuario = UsuariosDianaHelper.datosLogueado(session, template);

		Connection connection;
		connection = DriverManager.getConnection(env.getProperty("spring.datasource.url"),
				env.getProperty("spring.datasource.username"), env.getProperty("spring.datasource.password"));

		PreparedStatement consulta = connection
				.prepareStatement("INSERT INTO posts(texto, id_usuario, imagen_post, titulo) VALUES(?, ?, ?, ?);");
		consulta.setString(1, texto);
		consulta.setInt(2, id_usuario);
		consulta.setString(3, imagen_post);
		consulta.setString(4, titulo);

		consulta.executeUpdate();

		connection.close();
		return "redirect:/detalle/" + id_usuario;
	}

	// ruta para eliminar por id
	@GetMapping("/eliminar/{id}")
	public String eliminar(@PathVariable int id) throws SQLException {

		Connection connection;
		connection = DriverManager.getConnection(env.getProperty("spring.datasource.url"),
				env.getProperty("spring.datasource.username"), env.getProperty("spring.datasource.password"));

		PreparedStatement consulta = connection.prepareStatement("DELETE FROM usuarios WHERE id = ?;");
		consulta.setInt(1, id);

		consulta.executeUpdate();

		connection.close();

		return "redirect:/eliminar/";
	}

	// ruta de prueba, no usar
	@GetMapping("/insertar-usuario-prueba")
	@ResponseBody
	public String insertarUsuarioPrueba() throws SQLException {

		Connection connection;
		connection = DriverManager.getConnection(env.getProperty("spring.datasource.url"),
				env.getProperty("spring.datasource.username"), env.getProperty("spring.datasource.password"));

		PreparedStatement consulta = connection.prepareStatement(
				"INSERT INTO usuarios(nombre, email, contrasenia, artista) VALUES('usuarioTest', 'prueba@test.com', '1234', true);");

		consulta.executeUpdate();

		connection.close();
		return "OK";
	}

	// ruta de prueba, no usar
	@GetMapping("/eliminar-usuario-prueba")
	@ResponseBody
	public String eliminarUsuarioPrueba() throws SQLException {

		Connection connection;
		connection = DriverManager.getConnection(env.getProperty("spring.datasource.url"),
				env.getProperty("spring.datasource.username"), env.getProperty("spring.datasource.password"));

		PreparedStatement consulta = connection.prepareStatement("DELETE FROM usuarios WHERE nombre = 'usuarioTest';");

		consulta.executeUpdate();

		connection.close();
		return "OK";
	}

}
