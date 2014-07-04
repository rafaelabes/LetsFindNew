package br.edu.uneb.letsfind.db;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

public class RankDataSource {

	private SQLiteDatabase database;
	private GameDbHelper dbHelper;
	private String[] allColumns = new String[]{
			GameDbHelper.RANK_ID,
			GameDbHelper.RANK_NOME,
			GameDbHelper.RANK_MOEDAS,
			GameDbHelper.RANK_PONTUACAO
	};
	
	public RankDataSource(){
		database = dbHelper.getWritableDatabase();
	}
	
	public void open() throws SQLException{
		database = dbHelper.getWritableDatabase();
	}
	
	public void close(){
		dbHelper.close();
	}
	
	public Rank cursorToRank(Cursor cursor){
		Rank rank = new Rank();
		rank.setId(cursor.getLong(0));
		rank.setNomeDeUsuario(cursor.getString(1));
		rank.setMoedas(cursor.getDouble(2));
		rank.setPontuacao(cursor.getInt(3));
		return rank;
	}
	
	public Rank createRank(String nomeDeUsuario, double moedas, int pontuacao){
		
		ContentValues values = new ContentValues();		
		values.put(GameDbHelper.RANK_NOME, nomeDeUsuario);
		values.put(GameDbHelper.RANK_MOEDAS, moedas);
		values.put(GameDbHelper.RANK_PONTUACAO, pontuacao);
		
		long insertId = database.insert(GameDbHelper.TABLE_RANK, null, values);
		
		Cursor cursor = database.query(GameDbHelper.TABLE_RANK,
				allColumns,
				GameDbHelper.RANK_ID + " = " + insertId,
				null, null, null, null);
		
		cursor.moveToFirst();
		Rank rank = cursorToRank(cursor);
		
		// make sure to close the cursor
		cursor.close();
		
		return rank;
	}
	
}
