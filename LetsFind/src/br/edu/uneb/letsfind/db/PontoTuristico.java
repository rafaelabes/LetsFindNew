package br.edu.uneb.letsfind.db;

/*
 * Um ponto contem dicas
 * */
public class PontoTuristico {
	
	private long id;
	private String nome;
	
	private long latitude;
	private long longitude;
	private long raio;
	
	private long fkPergunta;
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public long getLatitude() {
		return latitude;
	}
	public void setLatitude(long latitude) {
		this.latitude = latitude;
	}
	public long getLongitude() {
		return longitude;
	}
	public void setLongitude(long longitude) {
		this.longitude = longitude;
	}
	public long getRaio() {
		return raio;
	}
	public void setRaio(long raio) {
		this.raio = raio;
	}
	public long getFkPergunta() {
		return fkPergunta;
	}
	public void setFkPergunta(long fkPergunta) {
		this.fkPergunta = fkPergunta;
	}

}

