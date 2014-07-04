package br.edu.uneb.letsfind.db;

public class Rank {

	private long id;
	private String nomeDeUsuario;
	private double moedas;
	private int pontuacao;
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getNomeDeUsuario() {
		return nomeDeUsuario;
	}
	public void setNomeDeUsuario(String nomeDeUsuario) {
		this.nomeDeUsuario = nomeDeUsuario;
	}
	public double getMoedas() {
		return moedas;
	}
	public void setMoedas(double moedas) {
		this.moedas = moedas;
	}
	public int getPontuacao() {
		return pontuacao;
	}
	public void setPontuacao(int pontuacao) {
		this.pontuacao = pontuacao;
	}
	
	
}
