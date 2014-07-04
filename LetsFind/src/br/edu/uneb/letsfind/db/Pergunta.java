package br.edu.uneb.letsfind.db;

public class Pergunta {
	
	private long id;
	private String texto;
	//private long raio;
	private boolean Respondida;
	private long fkTema;
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getTexto() {
		return texto;
	}
	public void setTexto(String texto) {
		this.texto = texto;
	}
	public long getFkTema() {
		return fkTema;
	}
	public void setFkTema(long fkTema) {
		this.fkTema = fkTema;
	}
	public boolean isRespondida() {
		return Respondida;
	}
	public void setRespondida(boolean respondida) {
		Respondida = respondida;
	}
	
	
}
