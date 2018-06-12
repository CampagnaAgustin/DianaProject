package com.app.DianaProject;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import com.app.model.Usuario;

@Service
public class UsuariosDianaHelper {

	@Autowired
	Environment env;

	public boolean intentarLoguearse(HttpSession session, String nombre, String password) throws SQLException {

		Connection connection;
		connection = DriverManager.getConnection(env.getProperty("spring.datasource.url"),
				env.getProperty("spring.datasource.username"), env.getProperty("spring.datasource.password"));

		PreparedStatement consulta = connection
				.prepareStatement("SELECT * FROM usuarios WHERE nombre = ? AND contrasenia = ?;");

		consulta.setString(1, nombre);
		consulta.setString(2, password);

		ResultSet resultado = consulta.executeQuery();

		if (resultado.next()) {
			String codigo = UUID.randomUUID().toString();
			session.setAttribute("codigo-autorizacion", codigo);

			consulta = connection.prepareStatement("UPDATE usuarios SET codigo = ? WHERE nombre = ?;");
			consulta.setString(1, codigo);
			consulta.setString(2, nombre);

			consulta.executeUpdate();

			return true;
		} else {
			return false;
		}

	}

	public Usuario usuarioLogueado(HttpSession session) throws SQLException {

		String codigo = (String) session.getAttribute("codigo-autorizacion");

		if (codigo != null) {

			Connection connection;
			connection = DriverManager.getConnection(env.getProperty("spring.datasource.url"),
					env.getProperty("spring.datasource.username"), env.getProperty("spring.datasource.password"));

			PreparedStatement consulta = connection.prepareStatement("SELECT * FROM usuarios WHERE codigo = ?;");

			consulta.setString(1, codigo);

			ResultSet resultado = consulta.executeQuery();

			if (resultado.next()) {
				Usuario logueado = new Usuario(resultado.getInt("id"), resultado.getString("nombre"),
						resultado.getString("email"), resultado.getString("contrasenia"),
						resultado.getBoolean("artista"), resultado.getString("localidad"),
						resultado.getString("influencias"), resultado.getString("genero"),
						resultado.getString("integrantes"), resultado.getString("descripcion"),
						resultado.getString("imagen"), resultado.getString("tipo"), resultado.getBoolean("admin"),
						resultado.getString("facebook"), resultado.getString("instagram"),
						resultado.getString("twitter"), resultado.getString("web"), resultado.getString("youtube"),
						resultado.getString("link1"), resultado.getString("link2"), resultado.getString("link3"),
						resultado.getString("link4"), resultado.getString("link5"));
				return logueado;
			} else {
				return null;
			}

		} else {
			return null;
		}
	}

	public void cerrarSesion(HttpSession session) throws SQLException {

		String codigo = (String) session.getAttribute("codigo-autorizacion");

		session.removeAttribute("codigo-autorizacion");

		Connection connection;
		connection = DriverManager.getConnection(env.getProperty("spring.datasource.url"),
				env.getProperty("spring.datasource.username"), env.getProperty("spring.datasource.password"));

		PreparedStatement consulta = connection.prepareStatement("UPDATE usuarios Set codigo = null WHERE codigo = ?;");

		consulta.setString(1, codigo);
		consulta.executeUpdate();
		connection.close();

	}

	public void isLoggedIn(HttpSession session, Model template) throws SQLException {
		Usuario logueado = this.usuarioLogueado(session);
		if (logueado != null) {
			template.addAttribute("isLoggedIn", true);
		} else {
			template.addAttribute("isLoggedIn", false);
		}
	}

	public int datosLogueado(HttpSession session, Model template) throws SQLException {

		Usuario logueado = this.usuarioLogueado(session);

		if (logueado != null) {

			String nombreUsuario = logueado.getNombre();
			int idUsuario = logueado.getId();

			template.addAttribute("nombreUsuario", nombreUsuario);
			template.addAttribute("idUsuario", idUsuario);
			return idUsuario;
		}
		return 0;
	}
}