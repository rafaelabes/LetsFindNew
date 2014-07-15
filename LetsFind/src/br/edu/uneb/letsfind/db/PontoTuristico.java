package br.edu.uneb.letsfind.db;

/*
 * Um ponto contem dicas
 * */
public class PontoTuristico {
	
	private long id;
	private String nome;
	
	private double latitude;
	private double longitude;
	private double raio;
	
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
	public double getLatitude() {
		return latitude;
	}
	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}
	public double getLongitude() {
		return longitude;
	}
	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}
	public double getRaio() {
		return raio;
	}
	public void setRaio(double raio) {
		this.raio = raio;
	}
	public long getFkPergunta() {
		return fkPergunta;
	}
	public void setFkPergunta(long fkPergunta) {
		this.fkPergunta = fkPergunta;
	}

}

