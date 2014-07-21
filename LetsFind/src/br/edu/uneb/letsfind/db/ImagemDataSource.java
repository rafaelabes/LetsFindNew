package br.edu.uneb.letsfind.db;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class ImagemDataSource {

	private SQLiteDatabase database;
	private GameDbHelper dbHelper;
	private String[] allColumns = new String[]{ 
			GameDbHelper.IMAGEM_ID, 
			GameDbHelper.IMAGEM_NOME,
			GameDbHelper.IMAGEM_TITULO,
			GameDbHelper.IMAGEM_TIPO,
			GameDbHelper.IMAGEM_DADOS };
	
	
	public ImagemDataSource(Context context){
		dbHelper = new GameDbHelper(context);
	}
	
	public void open() throws SQLException{
		database = dbHelper.getWritableDatabase();
	}
	
	public void close(){
		dbHelper.close();
	}
	
	private Imagem cursorToImagem(Cursor cursor){
		Imagem imagem = new Imagem();
		imagem.setId(cursor.getLong(0));
		imagem.setNome(cursor.getString(1));
		imagem.setTitulo(cursor.getString(2));
		imagem.setTipo(cursor.getString(3));
		imagem.setDados(cursor.getBlob(4));
		return imagem;
	}
	
	public Imagem createImagem(Long id, String nome, String titulo, String tipo, byte[] dados){
		
		ContentValues values = new ContentValues();
		
		values.put(GameDbHelper.IMAGEM_ID, id);
		values.put(GameDbHelper.IMAGEM_NOME, nome);
		values.put(GameDbHelper.IMAGEM_TITULO, titulo);
		values.put(GameDbHelper.IMAGEM_TIPO, tipo);
		values.put(GameDbHelper.IMAGEM_DADOS, dados);
		
		Long insertId = database.insert(GameDbHelper.TABLE_IMAGEM, null, values);
		
		Cursor cursor = database.query(GameDbHelper.TABLE_IMAGEM,
				allColumns,
				GameDbHelper.PERGUNTA_ID + " = ?",
				new String[]{ insertId.toString() }, null, null, null);
		
		cursor.moveToFirst();
		Imagem imagem = cursorToImagem(cursor);
		
		// make sure to close the cursor
		cursor.close();
		
		return imagem;
	}
	
	public Imagem updateImagem(Long id, String nome, String titulo, String tipo, byte[] dados){
		
		if(exists(id) == 0){
			Log.v("updateImagem","Inserindo imagem");
			return createImagem(id, nome, titulo, tipo, dados);
		}
		
		ContentValues values = new ContentValues();
		
		values.put(GameDbHelper.IMAGEM_ID, id);
		values.put(GameDbHelper.IMAGEM_NOME, nome);
		values.put(GameDbHelper.IMAGEM_TITULO, titulo);
		values.put(GameDbHelper.IMAGEM_TIPO, tipo);
		values.put(GameDbHelper.IMAGEM_DADOS, dados);
		
		database.update(GameDbHelper.TABLE_IMAGEM, values, GameDbHelper.IMAGEM_ID + " = ?", new String[]{ id.toString() });		
		Cursor cursor = database.query(GameDbHelper.TABLE_IMAGEM,
				allColumns,
				GameDbHelper.PERGUNTA_ID + " = ?",
				new String[]{ id.toString() }, null, null, null);
		
		cursor.moveToFirst();
		Imagem imagem = cursorToImagem(cursor);
		
		// make sure to close the cursor
		cursor.close();
		
		return imagem;
	}
	
	public void deleteImagem(Imagem imagem){
		Long id = imagem.getId();		
		database.delete(GameDbHelper.TABLE_IMAGEM, GameDbHelper.IMAGEM_ID + " = ?", new String[]{ id.toString() });
	}
	
	public List<Imagem> getAllImagem(){
		
		List<Imagem> imagens = new ArrayList<Imagem>();
		
		Cursor cursor = database.query(GameDbHelper.TABLE_IMAGEM,
				allColumns,
				null,
				null, null, null, null);
		
		cursor.moveToFirst();
		
		while(!cursor.isAfterLast()){
			Imagem imagem = cursorToImagem(cursor);
			imagens.add(imagem);
			cursor.moveToNext();
		}
		
		// make sure to close the cursor
		cursor.close();
		
		return imagens;
	}
	
	
	public int getCount(){
				
		Cursor cursor = database.rawQuery("select count(*) from " + GameDbHelper.TABLE_IMAGEM, null);
		return cursor.getInt(0);
		
	}
	
	public int exists(Long id){
		
		Cursor cursor = database.rawQuery("select count(*) from " + GameDbHelper.TABLE_IMAGEM + " where " + GameDbHelper.IMAGEM_ID + " = ?", new String[]{ id.toString() } );
		return cursor.getInt(0);
		
	}
	
	public Imagem getImagemById(Long id){
		
		Cursor cursor = database.query(GameDbHelper.TABLE_IMAGEM,
				allColumns,
				GameDbHelper.IMAGEM_ID + " = ?",
				new String[]{ id.toString() }, null, null, null);
		
		cursor.moveToFirst();
		Imagem imagem = null;
		
		while(!cursor.isAfterLast()){
			imagem = cursorToImagem(cursor);
		}
		
		// make sure to close the cursor
		cursor.close();
		
		return imagem;
	}
	
	
	
	
}
