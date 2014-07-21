package br.edu.uneb.letsfind.db;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

public class PerguntaDataSource {

	private SQLiteDatabase database;
	private GameDbHelper dbHelper;
	private String[] allColumns = new String[]{ 
			GameDbHelper.PERGUNTA_ID, 
			GameDbHelper.PERGUNTA_TEXTO,
			GameDbHelper.PERGUNTA_RESPONDIDA,
			GameDbHelper.PERGUNTA_FK_TEMA };
	
	public PerguntaDataSource(Context context){
		dbHelper = GameDbHelper.getInstance(context);
	}
	
	public void open() throws SQLException{
		database = dbHelper.getWritableDatabase();
	}
	
	public void close(){
		dbHelper.close();
	}
	
	//
	private Pergunta cursorToPergunta(Cursor cursor){
		Pergunta pergunta = new Pergunta();
		pergunta.setId(cursor.getLong(0));
		pergunta.setTexto(cursor.getString(1));
		pergunta.setRespondida(Boolean.parseBoolean(cursor.getString(2)));
		pergunta.setFkTema(cursor.getLong(3));
		return pergunta;
	}
	
	//
	public Pergunta createPergunta(Tema tema, String texto, boolean respondida){
		
		long fkTema = tema.getId();
		
		ContentValues values = new ContentValues();
		
		values.put(GameDbHelper.PERGUNTA_TEXTO, texto);
		values.put(GameDbHelper.PERGUNTA_RESPONDIDA, respondida);
		values.put(GameDbHelper.PERGUNTA_FK_TEMA, fkTema);
		
		Long insertId = database.insert(GameDbHelper.TABLE_PERGUNTA, null, values);
		
		Cursor cursor = database.query(GameDbHelper.TABLE_PERGUNTA,
				allColumns,
				GameDbHelper.PERGUNTA_ID + " = ?",
				new String[]{ insertId.toString() }, null, null, null);
		
		cursor.moveToFirst();
		Pergunta pergunta = cursorToPergunta(cursor);
		
		// make sure to close the cursor
		cursor.close();
		
		return pergunta;
				
	}
	
	//
	public void deletePergunta(Pergunta pergunta){
		long id = pergunta.getId();		
		database.delete(GameDbHelper.TABLE_PERGUNTA, GameDbHelper.PERGUNTA_ID + " = " + id, null);
	}
	
	//
	public List<Pergunta> getPerguntasByTema(Tema tema){
		
		Long fkTema = tema.getId();
		List<Pergunta> perguntas = new ArrayList<Pergunta>();
		
		Cursor cursor = database.query(GameDbHelper.TABLE_PERGUNTA,
				allColumns,
				GameDbHelper.PERGUNTA_FK_TEMA + " = ?",
				new String[]{ fkTema.toString() }, null, null, null);
		
		cursor.moveToFirst();
		
		while(!cursor.isAfterLast()){
			Pergunta pergunta = cursorToPergunta(cursor);
			perguntas.add(pergunta);
			cursor.moveToNext();
		}
		
		// make sure to close the cursor
		cursor.close();
		
		return perguntas;
	}
	
	public Pergunta getPerguntaAfterId(long temaId, long id){
		
		Pergunta pergunta = null;
		/*
		Cursor cursor = database.query(GameDbHelper.TABLE_PERGUNTA,
				allColumns,
				GameDbHelper.PERGUNTA_FK_TEMA + " = ?"
						+ " AND "
				+ GameDbHelper.PERGUNTA_ID + " > ? ",
				new String[]{ String.valueOf(temaId), String.valueOf(id) }
		, null, null, null);
		*/
		
		Cursor cursor = database.rawQuery("select "
				+ GameDbHelper.PERGUNTA_ID + ", "
				+ GameDbHelper.PERGUNTA_TEXTO + ", "
				+ GameDbHelper.PERGUNTA_RESPONDIDA + ", "
				+ GameDbHelper.PERGUNTA_FK_TEMA + " "
				+ " from "
				+ GameDbHelper.TABLE_PERGUNTA +" "
						+ " where "
						+ GameDbHelper.PERGUNTA_FK_TEMA + " = ?"
						+ " AND "
						+ GameDbHelper.PERGUNTA_ID +" > ? LIMIT 1", new String[]{ String.valueOf(temaId), String.valueOf(id) });
		
		
		cursor.moveToFirst();
		
		while(!cursor.isAfterLast()){
			pergunta = cursorToPergunta(cursor);
		}
		
		// make sure to close the cursor
		cursor.close();
		
		return pergunta;
	}
	
	
	
	public int getCount(){
				
		Cursor cursor = database.rawQuery("select count(*) from " + GameDbHelper.TABLE_PERGUNTA, null);
		return cursor.getInt(0);
		
	}
	
	
	public Pergunta getPerguntaByTexto(String texto){
		
		Cursor cursor = database.query(GameDbHelper.TABLE_PERGUNTA,
				allColumns,
				GameDbHelper.PERGUNTA_TEXTO + " = ?",
				new String[]{ texto }, null, null, null);
		
		
		cursor.moveToFirst();
		Pergunta pergunta = cursorToPergunta(cursor);
		
		// make sure to close the cursor
		cursor.close();
		
		return pergunta;
	}
	
	public Pergunta getPerguntaById(Long id){
		
		Cursor cursor = database.query(GameDbHelper.TABLE_PERGUNTA,
				allColumns,
				GameDbHelper.PERGUNTA_ID + " = ?",
				new String[]{ id.toString() }, null, null, null);
		
		
		cursor.moveToFirst();
		Pergunta pergunta = cursorToPergunta(cursor);
		
		// make sure to close the cursor
		cursor.close();
		
		return pergunta;
	}
	
	
}
