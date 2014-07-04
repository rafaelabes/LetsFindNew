package br.edu.uneb.letsfind.db;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

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
		dbHelper = new GameDbHelper(context);
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
		pontoTuristico.setLatitude(cursor.getLong(2));
		pontoTuristico.setLongitude(cursor.getLong(3));
		pontoTuristico.setRaio(cursor.getLong(4));
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
	
	int deletePontoTuristico(PontoTuristico pontoTuristico){
		long id = pontoTuristico.getId();		
		return database.delete(GameDbHelper.TABLE_PONTO, GameDbHelper.PONTO_ID + " = " + id, null);
	}
	
	List<PontoTuristico> getAllPontoTuristico(){
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
	
}
