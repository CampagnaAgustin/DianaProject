package com.app.model;

public class Post {

	private int id;
	private String titulo;
	private String texto;
	private int idUsuario;
	private String imagenPost;
	
	public Post(int id, String tit, String tex, int u, String img) {
		this.id = id;
		this.titulo = tit;
		this.texto = tex;
		this.idUsuario = u;
		this.imagenPost = img;
	}
	
	@Override
	public String toString() {
		return "Post [id=" + id + ", titulo=" + titulo + ", texto=" + texto + ", imagenPost=" + imagenPost + "]";
	}
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
	
	public int getIdUsuario() {
		return idUsuario;
	}

	public void setIdUsuario(int idUsuario) {
		this.idUsuario = idUsuario;
	}
	
	public String getTitulo(){
		return this.titulo;
	}
	
	public void setTitulo(String titulo){
		this.titulo = titulo;
	}
	
	public String getTexto(){
		return this.texto;
	}
	
	public void setTexto(String texto){
		this.texto = texto;
	}
	
	public String getImagenPost(){
		return this.imagenPost;
	}
	
	public void setImagenPost(String imagenPost){
		this.imagenPost = imagenPost;
	
}

}