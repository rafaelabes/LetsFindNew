package br.edu.uneb.letsfind.db;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

public class JogadorDataSource {

	private SQLiteDatabase database;
	private GameDbHelper dbHelper;
	private String[] allColumns = new String[]{
			GameDbHelper.JOGADOR_ID,
			GameDbHelper.JOGADOR_NOME,
			GameDbHelper.JOGADOR_PONTUACAO,
			GameDbHelper.JOGADOR_MOEDAS,
			/*
			GameDbHelper.JOGADOR_PAIS,
			GameDbHelper.JOGADOR_ESTADO,
			GameDbHelper.JOGADOR_CIDADE,
			*/
			//GameDbHelper.JOGADOR_FK_TEMA,
			GameDbHelper.JOGADOR_ULTIMA_TENTATIVA
	};
	
	public JogadorDataSource(Context context){
		dbHelper = new GameDbHelper(context);
	}
	
	public void open() throws SQLException{
		database = dbHelper.getWritableDatabase();
	}
	
	public void close(){
		dbHelper.close();
	}
	
	
	public Jogador cursorToJogador(Cursor cursor){
		Jogador jogador = new Jogador();
		
		jogador.setId(cursor.getLong(0));
		jogador.setNomeDeUsuario(cursor.getString(1));
		jogador.setPontuacao(cursor.getInt(2));
		jogador.setMoedas(cursor.getDouble(3));
		/*
		jogador.setPais(cursor.getString(4));
		jogador.setEstado(cursor.getString(5));
		jogador.setCidade(cursor.getString(6));
		*/
		//jogador.setFkTema(cursor.getLong(4));
		jogador.setUltimaTentativa(new Date(cursor.getLong(4)));
		
		return jogador;
	}
	
	Jogador createJogador(String nomeDeUsuario, int pontuacao, double moedas, String pais, String estado, String cidade){
		
		//long fkTema = tema.getId();
		Date ultimaTentativa = new Date();
		
		ContentValues values = new ContentValues();		
		values.put(GameDbHelper.JOGADOR_NOME, nomeDeUsuario);
		values.put(GameDbHelper.JOGADOR_PONTUACAO, pontuacao);
		values.put(GameDbHelper.JOGADOR_MOEDAS, moedas);
		/*
		values.put(GameDbHelper.JOGADOR_PAIS, pais);
		values.put(GameDbHelper.JOGADOR_ESTADO, estado);
		values.put(GameDbHelper.JOGADOR_CIDADE, cidade);
		*/
		//values.put(GameDbHelper.JOGADOR_FK_TEMA, fkTema);
        values.put(GameDbHelper.JOGADOR_ULTIMA_TENTATIVA, ultimaTentativa.getTime());
		
        long insertId = database.insert(GameDbHelper.TABLE_JOGADOR, null, values);
		
		Cursor cursor = database.query(GameDbHelper.TABLE_JOGADOR,
				allColumns,
				GameDbHelper.JOGADOR_ID + " = " + insertId,
				null, null, null, null);
		
		cursor.moveToFirst();
		Jogador jogador = cursorToJogador(cursor);
		
		// make sure to close the cursor
		cursor.close();
		
		return jogador;
	}
	
	void deleteJogador(Jogador jogador){
		long id = jogador.getId();		
		database.delete(GameDbHelper.TABLE_JOGADOR, GameDbHelper.JOGADOR_ID + " = " + id, null);
	}
	
	
	List<Jogador> getAllJogadores(){
		
		List<Jogador> jogadores = new ArrayList<Jogador>();	
		
		Cursor cursor = database.query(GameDbHelper.TABLE_JOGADOR,
				allColumns,
				null,null, null, null, null);
		
		cursor.moveToFirst();
		
		while(!cursor.isAfterLast()){
			Jogador jogador = cursorToJogador(cursor);
			jogadores.add(jogador);
			cursor.moveToNext();
		}
		
		// make sure to close the cursor
		cursor.close();
		
		return jogadores;
	}
	
}
