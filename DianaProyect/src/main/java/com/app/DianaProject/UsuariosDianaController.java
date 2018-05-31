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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.app.model.Usuario;
import com.app.model.UsuariosDianaHelper;

@Controller
public class UsuariosDianaController {
	
	@Autowired
	private Environment env;
	
	@Autowired
	private UsuariosDianaHelper UsuariosDianaHelper;
	
	// comando registro de artistas
	@PostMapping("/bienvenido-artista")
	public String bienvenidoArtista(@RequestParam String nombre, @RequestParam String email,
			@RequestParam String password)
			throws SQLException {
		Connection connection;
		connection = DriverManager.getConnection(env.getProperty("spring.datasource.url"), env.getProperty("spring.datasource.username"), env.getProperty("spring.datasource.password"));

		PreparedStatement consulta = connection.prepareStatement(
				"INSERT INTO usuarios(nombre, email, contrasenia, artista) VALUES(?, ?, ?, true);");
		consulta.setString(1, nombre);
		consulta.setString(2, email);
		consulta.setString(3, password);

		consulta.executeUpdate();

		connection.close();
		return "redirect:/";
	}
	
	@PostMapping("/bienvenido-usuario")
	public String bienvenidoUsuario(@RequestParam String nombre, @RequestParam String email,
			@RequestParam String password)
			throws SQLException {
		Connection connection;
		connection = DriverManager.getConnection(env.getProperty("spring.datasource.url"), env.getProperty("spring.datasource.username"), env.getProperty("spring.datasource.password"));

		PreparedStatement consulta = connection.prepareStatement(
				"INSERT INTO usuarios(nombre, email, contrasenia, artista) VALUES(?, ?, ?, false);");
		consulta.setString(1, nombre);
		consulta.setString(2, email);
		consulta.setString(3, password);

		consulta.executeUpdate();

		connection.close();
		return "redirect:/";
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
		return "redirect:/login";
	}
	
	// muestra un usuario en detalle
		@GetMapping("/detalle/{id}")
		public String detalle(Model template, @PathVariable int id) throws SQLException {
			
			Connection connection;
			connection = DriverManager.getConnection(env.getProperty("spring.datasource.url"), env.getProperty("spring.datasource.username"), env.getProperty("spring.datasource.password"));
			
			PreparedStatement consulta = 
					connection.prepareStatement("SELECT * FROM usuarios WHERE id = ?;");
			
			consulta.setInt(1, id);

			ResultSet resultado = consulta.executeQuery();
			
			if ( resultado.next() ) {
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
			}
			
			return "detalleUsuario";
		}
	
	@GetMapping("/editar/{id}")
	public String editar(Model template, @PathVariable int id) throws SQLException {

		Connection connection;
		connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/Diana", "postgres", "01100110f");

		PreparedStatement consulta = connection.prepareStatement("SELECT * FROM usuarios WHERE id = ?;");

		consulta.setInt(1, id);

		ResultSet resultado = consulta.executeQuery();

		if ( resultado.next() ) {
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
		}

		return "editarUsuario";
	}
	
	//Editar usuarios
	@GetMapping("/modificar/{id}")
	public String modificar(Model template, @PathVariable int id, @RequestParam String nombre, @RequestParam String email, @RequestParam String password, @RequestParam String localidad, @RequestParam String influencias, @RequestParam String descripcion, @RequestParam String genero, @RequestParam String integrantes) throws SQLException {
		
		Connection connection;
		connection = DriverManager.getConnection(env.getProperty("spring.datasource.url"), env.getProperty("spring.datasource.username"), env.getProperty("spring.datasource.password"));
		
		PreparedStatement consulta = 
				connection.prepareStatement("UPDATE usuarios SET nombre = ?, email = ?, contrasenia = ?, localidad = ?, influencias = ?, descripcion = ?, genero = ?, integrantes = ? WHERE id = ?;");
		
		consulta.setString(1, nombre);
		consulta.setString(2, email);
		consulta.setString(3, password);
		consulta.setString(4, localidad);
		consulta.setString(5, influencias);
		consulta.setString(6, descripcion);
		consulta.setString(7, genero);
		consulta.setString(8, integrantes);
		consulta.setInt(9, id);

		consulta.executeUpdate();

		connection.close();
		
		return "redirect:/detalle/{id}";
	}
		
	@GetMapping("/artistas/todes")
	public String listadoTodes(Model template) throws SQLException {
		
		Connection connection;
		connection = DriverManager.getConnection(env.getProperty("spring.datasource.url"), env.getProperty("spring.datasource.username"), env.getProperty("spring.datasource.password"));
		
		PreparedStatement consulta = 
				connection.prepareStatement("SELECT * FROM usuarios WHERE artista = true;");
		
		ResultSet resultado = consulta.executeQuery();
		
		ArrayList<Usuario> listadoUsuarios = new ArrayList<Usuario>();
		
		while ( resultado.next() ) {
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
			
			Usuario x = new Usuario(id, nombre, email, password, artista, localidad, influencias, genero, descripcion, integrantes, imagen, tipo);
			listadoUsuarios.add(x);
		}
		
		template.addAttribute("listadoUsuarios", listadoUsuarios);
		template.addAttribute("artistasActive", "active");
		
		return "listadoUsuarios";
	}
	
		@GetMapping("/artistas/{tipo}")
		public String listadoTipo(Model template, @PathVariable String tipo) throws SQLException {
			
			Connection connection;
			connection = DriverManager.getConnection(env.getProperty("spring.datasource.url"), env.getProperty("spring.datasource.username"), env.getProperty("spring.datasource.password"));
			
			PreparedStatement consulta = 
					connection.prepareStatement("SELECT * FROM usuarios WHERE artista = true AND tipo = ?;");
			
			consulta.setString(1, tipo);
			
			ResultSet resultado = consulta.executeQuery();
			
			ArrayList<Usuario> listadoUsuarios = new ArrayList<Usuario>();
			
			while ( resultado.next() ) {
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
				
				Usuario x = new Usuario(id, nombre, email, password, artista, localidad, influencias, genero, descripcion, integrantes, imagen, tipo);
				listadoUsuarios.add(x);
			}
			
			template.addAttribute("listadoUsuarios", listadoUsuarios);
			template.addAttribute("artistasActive", "active");
			
			return "listadoUsuarios";
		}
		
		// muestra el listado completo de usuarios
		@GetMapping("/procesarBusqueda")
		public String procesarBusqueda(Model template, @RequestParam String palabraBuscada) throws SQLException {

			Connection connection;
			connection = DriverManager.getConnection(env.getProperty("spring.datasource.url"), env.getProperty("spring.datasource.username"), env.getProperty("spring.datasource.password"));

			PreparedStatement consulta = connection.prepareStatement("SELECT * FROM usuarios WHERE nombre LIKE ?;");
			consulta.setString(1, "%" + palabraBuscada + "%");

			ResultSet resultado = consulta.executeQuery();

			ArrayList<Usuario> listadoUsuarios = new ArrayList<Usuario>();

			while (resultado.next()) {
				int id = resultado.getInt ("id");
				String nombre = resultado.getString("nombre");
				String email = resultado.getString("email");
				String contrasenia = resultado.getString("contrasenia");
				boolean artista = resultado.getBoolean("artista");
				String localidad = resultado.getString("localidad");
				String influencias = resultado.getString("influencias");
				//date nacimiento = resultado.get
				String genero = resultado.getString("genero");
				String integrantes = resultado.getString("integrantes");
				String descripcion = resultado.getString("descripcion");
				String imagen = resultado.getString("imagen");
				String tipo = resultado.getString("tipo");

				Usuario x = new Usuario(id, nombre, email, contrasenia, artista, localidad, influencias, genero, integrantes, descripcion, imagen, tipo);
				listadoUsuarios.add(x);
			}

			template.addAttribute("listadoUsuarios", listadoUsuarios);

			return "listadoUsuarios";
		}
	
	// ruta para eliminar por id
	@GetMapping("/eliminar/{id}")
	public String eliminar(@PathVariable int id) throws SQLException {
		
		Connection connection;
		connection = DriverManager.getConnection(env.getProperty("spring.datasource.url"), env.getProperty("spring.datasource.username"), env.getProperty("spring.datasource.password"));
		
		PreparedStatement consulta = connection.prepareStatement("DELETE FROM usuarios WHERE id = ?;");
		consulta.setInt(1, id);
		
		consulta.executeUpdate();
		
		connection.close();
		
		return "redirect:/listado";
	}

	// ruta de prueba, no usar
	@GetMapping("/insertar-usuario-prueba")
	@ResponseBody
	public String insertarUsuarioPrueba() throws SQLException {

		Connection connection;
		connection = DriverManager.getConnection(env.getProperty("spring.datasource.url"), env.getProperty("spring.datasource.username"), env.getProperty("spring.datasource.password"));

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
		connection = DriverManager.getConnection(env.getProperty("spring.datasource.url"), env.getProperty("spring.datasource.username"), env.getProperty("spring.datasource.password"));

		PreparedStatement consulta = connection.prepareStatement("DELETE FROM usuarios WHERE nombre = 'usuarioTest';");

		consulta.executeUpdate();

		connection.close();
		return "OK";
	}

}
