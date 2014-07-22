package br.edu.uneb.letsfind.db;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class DicaDataSource {

	private SQLiteDatabase database;
	private GameDbHelper dbHelper;
	private String[] allColumns = new String[]{ 
			GameDbHelper.DICA_ID,
			GameDbHelper.DICA_VALOR,
			GameDbHelper.DICA_TEXTO,
			
			GameDbHelper.DICA_LATITUDE,
			GameDbHelper.DICA_LONGITUDE,
			GameDbHelper.DICA_RAIO,
			
			GameDbHelper.DICA_FK_PERGUNTA };
	
	public DicaDataSource(Context context){
		dbHelper = GameDbHelper.getInstance(context);
		try{
			database = dbHelper.getWritableDatabase();
		}catch(SQLException e){
			Log.e("DicaDataSource", "Exception: "+Log.getStackTraceString(e));
		}
	}
	
	public void open() throws SQLException{
		database = dbHelper.getWritableDatabase();
	}
	
	public void close(){
		dbHelper.close();
	}
	
	
	public Dica cursorToDica(Cursor cursor){
		Dica dica = new Dica();
		dica.setId(cursor.getLong(0));
		dica.setValorDeCompra(cursor.getDouble(1));
		dica.setTexto(cursor.getString(2));
		
		dica.setLatitude(cursor.getDouble(3));
		dica.setLongitude(cursor.getDouble(4));
		dica.setRaio(cursor.getDouble(5));
		
		dica.setFkPergunta(cursor.getLong(6));
		return dica;
	}
	
	Dica createDica(Pergunta pergunta, String texto, double valorDeCompra, double latitude, double longitude, double raio){
		
		long fkPergunta = pergunta.getId();
		
		ContentValues values = new ContentValues();
		
		values.put(GameDbHelper.DICA_TEXTO, texto);
		values.put(GameDbHelper.DICA_VALOR, valorDeCompra);
		
		values.put(GameDbHelper.DICA_LATITUDE, valorDeCompra);
		values.put(GameDbHelper.DICA_LONGITUDE, valorDeCompra);
		values.put(GameDbHelper.DICA_RAIO, valorDeCompra);
		
		values.put(GameDbHelper.DICA_FK_PERGUNTA, fkPergunta);
		
		long insertId = database.insert(GameDbHelper.TABLE_DICA, null, values);
		
		Cursor cursor = database.query(GameDbHelper.TABLE_DICA,
				allColumns,
				GameDbHelper.DICA_ID + " = " + insertId,
				null, null, null, null);
		
		cursor.moveToFirst();
		Dica dica = cursorToDica(cursor);
		
		// make sure to close the cursor
		cursor.close();
		
		return dica;
				
	}
	
	void deleteDica(Dica dica){
		long id = dica.getId();		
		database.delete(GameDbHelper.TABLE_DICA, GameDbHelper.DICA_ID + " = " + id, null);
	}
	
	List<Dica> getDicasByPonto(Pergunta pergunta){
		
		long fkPergunta = pergunta.getId();
		List<Dica> dicas = new ArrayList<Dica>();	
		
		Cursor cursor = database.query(GameDbHelper.TABLE_DICA,
				allColumns,
				GameDbHelper.DICA_FK_PERGUNTA + " = " + fkPergunta,
				null, null, null, null);
		
		cursor.moveToFirst();
		
		while(!cursor.isAfterLast()){
			Dica dica = cursorToDica(cursor);
			dicas.add(dica);
			cursor.moveToNext();
		}
		
		// make sure to close the cursor
		cursor.close();
		
		return dicas;
	}
}
