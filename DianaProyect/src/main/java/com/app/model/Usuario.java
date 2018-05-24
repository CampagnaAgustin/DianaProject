package com.app.model;

public class Usuario {
	
	private int id;
	private String nombre;
	private String email;
	private String contrasenia;
	private boolean artista;
	private String localidad;
	private String influencias;
	//private date nacimiento;
	private String genero;
	private String integrantes;
	private String descripcion;
	private String imagen;

	public Usuario(int id, String nom, String e, String c, boolean a, String l, String inf, String g, String d, String in, String img){
		this.id = id;
		this.nombre = nom;
		this.email = e;
		this.contrasenia = c;
		this.artista = a;
		this.localidad = l;
		this.influencias = inf;
		//this.nacimiento = nac;
		this.descripcion = d;
		this.genero = g;
		this.integrantes = in;
		this.imagen = img;
	}
	
	@Override
	public String toString() {
		return "Usuario [id=" + id + ", nombre=" + nombre + ", contrasenia=" + contrasenia + ", activo=" + artista + "]";
	}
	
	public String getNombre(){
		return this.nombre;
	}
	
	public void setNombre(String nombreNuevo){
		this.nombre = nombreNuevo;
	}
	public String getEmail(){
		return this.email;
	}
	
	public void setEmail(String email){
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
	
	public String getLocalidad(){
		return this.localidad;
	}
	
	public void setLocalidad(String localidad){
		this.localidad = localidad;
	}
	
	public String getInfluencias(){
		return this.influencias;
	}
	
	public void setInfluencias(String influencias){
		this.influencias = influencias;
	}
	
	public String getDescripcion(){
		return this.descripcion;
	}
	
	public void setDescripcion(String descripcion){
		this.descripcion = descripcion;
	}
	
	public String getGenero(){
		return this.genero;
	}
	
	public void setGenero(String genero){
		this.genero = genero;
	}
	
	public String getIntegrantes(){
		return this.integrantes;
	}
	
	public void setIntegrantes(String integrantes){
		this.integrantes = integrantes;
	}
	
	public String getImagen(){
		return this.imagen;
	}
	
	public void setImagen(String imagen){
		this.imagen = imagen;
	}
	
}
