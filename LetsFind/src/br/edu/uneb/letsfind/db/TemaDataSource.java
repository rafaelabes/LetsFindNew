package br.edu.uneb.letsfind.db;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

public class TemaDataSource {

	private SQLiteDatabase database;
	private GameDbHelper dbHelper;
	private String[] allColumns = new String[]{
			GameDbHelper.TEMA_ID,
		    GameDbHelper.TEMA_NOME
	};
	
	public TemaDataSource(Context context){
		dbHelper = new GameDbHelper(context);
	}

	
	public void open() throws SQLException{
		database = dbHelper.getWritableDatabase();
	}
	
	public void close(){
		dbHelper.close();
	}
	
	public Tema cursorToTema(Cursor cursor){
		Tema tema = new Tema();
		tema.setId(cursor.getLong(0));
		tema.setNome(cursor.getString(1));
		return tema;
	}
	
	public Tema createTema(String nome){
		
		ContentValues values = new ContentValues();		
		values.put(GameDbHelper.TEMA_NOME, nome);
		
		long insertId = database.insert(GameDbHelper.TABLE_TEMA, null, values);
		
		Cursor cursor = database.query(GameDbHelper.TABLE_TEMA,
				allColumns,
				GameDbHelper.TEMA_ID + " = " + insertId,
				null, null, null, null);
		
		cursor.moveToFirst();
		Tema tema = cursorToTema(cursor);
		
		// make sure to close the cursor
		cursor.close();
		
		return tema;
		
	}
	
	public void deleteTema(Tema tema){
		long id = tema.getId();
		database.delete(GameDbHelper.TABLE_TEMA, GameDbHelper.TEMA_ID + " = " + id, null);
	}
	
	
	public List<Tema> getAllTemas(){
		List<Tema> temas = new ArrayList<Tema>();	
		
		Cursor cursor = database.query(GameDbHelper.TABLE_TEMA,
				allColumns,
				null,null, null, null, null);
		
		cursor.moveToFirst();
		
		while(!cursor.isAfterLast()){
			Tema tema = cursorToTema(cursor);
			temas.add(tema);
			cursor.moveToNext();
		}
		
		// make sure to close the cursor
		cursor.close();
		
		return temas;
	}
}
