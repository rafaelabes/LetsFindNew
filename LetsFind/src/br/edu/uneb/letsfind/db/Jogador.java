package br.edu.uneb.letsfind.db;

import java.util.Date;

/* O jogador se relaciona a um tema */
public class Jogador {

	private long id;
	private String nomeDeUsuario;
	private int pontuacao;
	private double moedas;
	/*
	private String pais;
	private String estado;
	private String cidade;
	*/
	//private long fkTema;
	private Date ultimaTentativa;
	
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
	
	/*
	public String getPais() {
		return pais;
	}
	public void setPais(String pais) {
		this.pais = pais;
	}
	public String getEstado() {
		return estado;
	}
	public void setEstado(String estado) {
		this.estado = estado;
	}
	public String getCidade() {
		return cidade;
	}
	public void setCidade(String cidade) {
		this.cidade = cidade;
	}
	*/
	
	public Date getUltimaTentativa() {
		return ultimaTentativa;
	}
	public void setUltimaTentativa(Date ultimaTentativa) {
		this.ultimaTentativa = ultimaTentativa;
	}
	
}
