package br.edu.uneb.letsfind.db;

import java.util.Date;

/* O jogador se relaciona a um tema */
public class Usuario {

	private long id;
	private String nomeDeUsuario;
	private int pontuacao;
	private double moedas;

	private Date ultimaTentativa;
	
	public Long getId() {
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
	public int getPontuacao() {
		return pontuacao;
	}
	public void setPontuacao(int pontuacao) {
		this.pontuacao = pontuacao;
	}
	public double getMoedas() {
		return moedas;
	}
	public void setMoedas(double moedas) {
		this.moedas = moedas;
	}
	
	public Date getUltimaTentativa() {
		return ultimaTentativa;
	}
	public void setUltimaTentativa(Date ultimaTentativa) {
		this.ultimaTentativa = ultimaTentativa;
	}
	
}
