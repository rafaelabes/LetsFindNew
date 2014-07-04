package br.edu.uneb.letsfind.db;

public class Dica {
	
	private long id;
	private double valorDeCompra;
	private String texto;
	
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
	public double getValorDeCompra() {
		return valorDeCompra;
	}
	public void setValorDeCompra(double valorDeCompra) {
		this.valorDeCompra = valorDeCompra;
	}
	public String getTexto() {
		return texto;
	}
	public void setTexto(String texto) {
		this.texto = texto;
	}
	public long getFkPergunta() {
		return fkPergunta;
	}
	public void setFkPergunta(long fkPergunta) {
		this.fkPergunta = fkPergunta;
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
	
	
}
