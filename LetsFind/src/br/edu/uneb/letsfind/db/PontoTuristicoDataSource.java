package br.edu.uneb.letsfind.db;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class PontoTuristicoDataSource {

	private SQLiteDatabase database;
	private GameDbHelper dbHelper;
	private String[] allColumns = new String[]{
			GameDbHelper.PONTO_ID,
			GameDbHelper.PONTO_NOME,
			GameDbHelper.PONTO_LATITUDE,
			GameDbHelper.PONTO_LONGITUDE,
			GameDbHelper.PONTO_RAIO,
			GameDbHelper.PONTO_FK_PERGUNTA
	};
	
	public PontoTuristicoDataSource(Context context){
		dbHelper = GameDbHelper.getInstance(context);
		try{
			database = dbHelper.getWritableDatabase();
		}catch(SQLException e){
			Log.e("PontoTuristicoDataSource", "Exception: "+Log.getStackTraceString(e));
		}
	}
	
	public void open() throws SQLException{
		database = dbHelper.getWritableDatabase();
	}
	
	public void close(){
		dbHelper.close();
	}
	
	public PontoTuristico cursorToPontoTuristico(Cursor cursor){
		
		PontoTuristico pontoTuristico = new PontoTuristico();
		pontoTuristico.setId(cursor.getLong(0));
		pontoTuristico.setNome(cursor.getString(1));
		pontoTuristico.setLatitude(cursor.getDouble(2));
		pontoTuristico.setLongitude(cursor.getDouble(3));
		pontoTuristico.setRaio(cursor.getDouble(4));
		pontoTuristico.setFkPergunta(cursor.getLong(5));
		
		return pontoTuristico;
	}
	
	public PontoTuristico createPontoTuristico(Pergunta pergunta, String nome, double latitude, double longitude, double raio){
		
		long fkPergunta = pergunta.getId();
		
		ContentValues values = new ContentValues();		
		values.put(GameDbHelper.PONTO_NOME, nome);
		values.put(GameDbHelper.PONTO_LATITUDE, latitude);
		values.put(GameDbHelper.PONTO_LONGITUDE, longitude);
		values.put(GameDbHelper.PONTO_RAIO, raio);
		values.put(GameDbHelper.PONTO_FK_PERGUNTA, fkPergunta);
		
		long insertId = database.insert(GameDbHelper.TABLE_PONTO, null, values);
		
		Cursor cursor = database.query(GameDbHelper.TABLE_PONTO,
				allColumns,
				GameDbHelper.PONTO_ID + " = " + insertId,
				null, null, null, null);
		
		cursor.moveToFirst();
		PontoTuristico pontoTuristico = cursorToPontoTuristico(cursor);
		
		// make sure to close the cursor
		cursor.close();
		
		return pontoTuristico;
		
	}
	
	public int deletePontoTuristico(PontoTuristico pontoTuristico){
		long id = pontoTuristico.getId();		
		return database.delete(GameDbHelper.TABLE_PONTO, GameDbHelper.PONTO_ID + " = " + id, null);
	}
	
	public List<PontoTuristico> getAllPontoTuristico(){
		List<PontoTuristico> pontosTuristicos = new ArrayList<PontoTuristico>();	
		
		Cursor cursor = database.query(GameDbHelper.TABLE_PONTO,
				allColumns,
				null,null, null, null, null);
		
		cursor.moveToFirst();
		
		while(!cursor.isAfterLast()){
			PontoTuristico pontoTuristico = cursorToPontoTuristico(cursor);
			pontosTuristicos.add(pontoTuristico);
			cursor.moveToNext();
		}
		
		// make sure to close the cursor
		cursor.close();
		
		return pontosTuristicos;
	}
	
	public long getCount(){
		
		Cursor cursor = database.rawQuery("select count(*) from " + GameDbHelper.TABLE_PONTO, null);
		return cursor.getLong(0);
		
	}
	
	public long getCountByPergunta(Long fkPergunta){
		
		Cursor cursor = database.rawQuery("select count(*) from " + GameDbHelper.TABLE_PONTO + " where "+ GameDbHelper.PONTO_FK_PERGUNTA + " = ?", new String[]{ fkPergunta.toString() });
		return cursor.getLong(0);
		
	}
		
	public List<PontoTuristico> getPontoTuristicoByPergunta(Pergunta pergunta){
		List<PontoTuristico> pontosTuristicos = new ArrayList<PontoTuristico>();
		
		Long fkPergunta = pergunta.getId();
		
		Cursor cursor = database.query(GameDbHelper.TABLE_PONTO,
				allColumns,
				GameDbHelper.PONTO_FK_PERGUNTA + " = ?",
				new String[]{ fkPergunta.toString() }, null, null, null);
		
		cursor.moveToFirst();
		
		while(!cursor.isAfterLast()){
			PontoTuristico pontoTuristico = cursorToPontoTuristico(cursor);
			pontosTuristicos.add(pontoTuristico);
			cursor.moveToNext();
		}
		
		// make sure to close the cursor
		cursor.close();
		
		return pontosTuristicos;
	}
	
	public PontoTuristico getPontoTuristicoByNome(String nome){
		
		Cursor cursor = database.query(GameDbHelper.TABLE_PONTO,
				allColumns,
				GameDbHelper.PONTO_NOME + " = ?",
				new String[]{ nome }, null, null, null);
		
		cursor.moveToFirst();
		PontoTuristico pontoTuristico = cursorToPontoTuristico(cursor);
		
		// make sure to close the cursor
		cursor.close();
		
		return pontoTuristico;
	}
	
	public PontoTuristico getPontoTuristicoById(Long id){
		
		Cursor cursor = database.query(GameDbHelper.TABLE_PONTO,
				allColumns,
				GameDbHelper.PONTO_NOME + " = ?",
				new String[]{ id.toString() }, null, null, null);
		
		cursor.moveToFirst();
		PontoTuristico pontoTuristico = cursorToPontoTuristico(cursor);
		
		// make sure to close the cursor
		cursor.close();
		
		return pontoTuristico;
	}
	
}
