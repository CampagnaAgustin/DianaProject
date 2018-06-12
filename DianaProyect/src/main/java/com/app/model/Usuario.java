package com.app.model;

public class Usuario {

	private int id;
	private String nombre;
	private String email;
	private String contrasenia;
	private boolean artista;
	private String localidad;
	private String influencias;
	// private date nacimiento;
	private String genero;
	private String integrantes;
	private String descripcion;
	private String imagen;
	private String tipo;
	private boolean admin;
	private String facebook;
	private String instagram;
	private String twitter;
	private String web;
	private String youtube;
	private String link1;
	private String link2;
	private String link3;
	private String link4;
	private String link5;

	public Usuario(int id, String nom, String e, String c, boolean a, String l, String inf, String g, String d,
			String in, String img, String t, boolean adm, String face, String insta, String twitt, String web,
			String yt, String l1, String l2, String l3, String l4, String l5) {
		this.id = id;
		this.nombre = nom;
		this.email = e;
		this.contrasenia = c;
		this.artista = a;
		this.localidad = l;
		this.influencias = inf;
		// this.nacimiento = nac;
		this.descripcion = d;
		this.genero = g;
		this.integrantes = in;
		this.imagen = img;
		this.tipo = t;
		this.admin = adm;
		this.facebook = face;
		this.instagram = insta;
		this.twitter = twitt;
		this.web = web;
		this.link1 = l1;
		this.link2 = l2;
		this.link3 = l3;
		this.link4 = l4;
		this.link5 = l5;
	}

	@Override
	public String toString() {
		return "Usuario [id=" + id + ", nombre=" + nombre + ", contrasenia=" + contrasenia + ", artista=" + artista
				+ "]";
	}

	public String getNombre() {
		return this.nombre;
	}

	public void setNombre(String nombreNuevo) {
		this.nombre = nombreNuevo;
	}

	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getContrasenia() {
		return contrasenia;
	}

	public void setContrasenia(String contrasenia) {
		this.contrasenia = contrasenia;
	}

	public boolean isArtista() {
		return artista;
	}

	public void setArtista(boolean artista) {
		this.artista = artista;
	}

	public String getLocalidad() {
		return this.localidad;
	}

	public void setLocalidad(String localidad) {
		this.localidad = localidad;
	}

	public String getInfluencias() {
		return this.influencias;
	}

	public void setInfluencias(String influencias) {
		this.influencias = influencias;
	}

	public String getDescripcion() {
		return this.descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public String getGenero() {
		return this.genero;
	}

	public void setGenero(String genero) {
		this.genero = genero;
	}

	public String getIntegrantes() {
		return this.integrantes;
	}

	public void setIntegrantes(String integrantes) {
		this.integrantes = integrantes;
	}

	public String getImagen() {
		return this.imagen;
	}

	public void setImagen(String imagen) {
		this.imagen = imagen;
	}

	public String getTipo() {
		return this.tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public boolean isAdmin() {
		return this.admin;
	}

	public void setAdmin(boolean admin) {
		this.admin = admin;
	}

	public String getFacebook() {
		return this.facebook;
	}

	public void setFacebook(String facebook) {
		this.facebook = facebook;
	}

	public String getInstagram() {
		return this.instagram;
	}

	public void setInstagram(String instagram) {
		this.instagram = instagram;
	}

	public String getTwitter() {
		return this.twitter;
	}

	public void setTwitter(String twitter) {
		this.twitter = twitter;
	}

	public String getWeb() {
		return this.web;
	}

	public void setWeb(String web) {
		this.web = web;
	}

	public String getYoutube() {
		return this.youtube;
	}

	public void setYoutube(String youtube) {
		this.youtube = youtube;
	}

	public String getLink1() {
		return this.link1;
	}

	public void setLink1(String link1) {
		this.link1 = link1;
	}

	public String getLink2() {
		return this.link2;
	}

	public void setLink2(String link2) {
		this.link2 = link2;
	}

	public String getLink3() {
		return this.link3;
	}

	public void setLink3(String link3) {
		this.link3 = link3;
	}

	public String getLink4() {
		return this.link4;
	}

	public void setLink4(String link4) {
		this.link4 = link4;
	}

	public String getLink5() {
		return this.link5;
	}

	public void setLink5(String link5) {
		this.link5 = link5;
	}

}
