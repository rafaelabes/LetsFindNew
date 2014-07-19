package br.edu.uneb.letsfind.db;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

public class UsuarioDataSource {

	private SQLiteDatabase database;
	private GameDbHelper dbHelper;
	private String[] allColumns = new String[]{
			GameDbHelper.USUARIO_ID,
			GameDbHelper.USUARIO_NOME,
			GameDbHelper.USUARIO_PONTUACAO,
			GameDbHelper.USUARIO_MOEDAS,
			GameDbHelper.USUARIO_ULTIMA_TENTATIVA
	};
	
	public UsuarioDataSource(Context context){
		dbHelper = new GameDbHelper(context);
	}
	
	public void open() throws SQLException{
		database = dbHelper.getWritableDatabase();
	}
	
	public void close(){
		dbHelper.close();
	}
	
	
	private Usuario cursorToUsuario(Cursor cursor){
		
		Usuario usuario = new Usuario();
		
		usuario.setId(cursor.getLong(0));
		usuario.setNomeDeUsuario(cursor.getString(1));
		usuario.setPontuacao(cursor.getInt(2));
		usuario.setMoedas(cursor.getDouble(3));
		usuario.setUltimaTentativa(new Date(cursor.getLong(4)));
		
		return usuario;
	}
	
	Usuario createUsuario(String nomeDeUsuario, int pontuacao, double moedas, String pais, String estado, String cidade){
		
		Date ultimaTentativa = new Date();
		
		ContentValues values = new ContentValues();		
		values.put(GameDbHelper.USUARIO_NOME, nomeDeUsuario);
		values.put(GameDbHelper.USUARIO_PONTUACAO, pontuacao);
		values.put(GameDbHelper.USUARIO_MOEDAS, moedas);

        values.put(GameDbHelper.USUARIO_ULTIMA_TENTATIVA, ultimaTentativa.getTime());
		
        long insertId = database.insert(GameDbHelper.TABLE_USUARIO, null, values);
		
		Cursor cursor = database.query(GameDbHelper.TABLE_USUARIO,
				allColumns,
				GameDbHelper.USUARIO_ID + " = " + insertId,
				null, null, null, null);
		
		cursor.moveToFirst();
		Usuario usuario = cursorToUsuario(cursor);
		
		// make sure to close the cursor
		cursor.close();
		
		return usuario;
	}
	
	
	Usuario updateUsuario(Usuario usuario){
		
		ContentValues values = new ContentValues();		
		values.put(GameDbHelper.USUARIO_NOME, usuario.getNomeDeUsuario());
		values.put(GameDbHelper.USUARIO_PONTUACAO, usuario.getPontuacao());
		values.put(GameDbHelper.USUARIO_MOEDAS, usuario.getMoedas());
		values.put(GameDbHelper.USUARIO_ULTIMA_TENTATIVA, usuario.getUltimaTentativa().getTime());
		
        database.update(GameDbHelper.TABLE_USUARIO, values, GameDbHelper.USUARIO_ID + " = ?", new String[]{ usuario.getId().toString() });
		
		return usuario;
	}
	
	void deleteUsuario(Usuario usuario){
		Long id = usuario.getId();		
		database.delete(
				GameDbHelper.TABLE_USUARIO,
				GameDbHelper.USUARIO_ID + " = ?",
				new String[]{ id.toString() });
	}
	
	
	List<Usuario> getAllUsuarios(){
		
		List<Usuario> usuarios = new ArrayList<Usuario>();	
		
		Cursor cursor = database.query(GameDbHelper.TABLE_USUARIO,
				allColumns,
				null,null, null, null, null);
		
		cursor.moveToFirst();
		
		while(!cursor.isAfterLast()){
			Usuario usuario = cursorToUsuario(cursor);
			usuarios.add(usuario);
			cursor.moveToNext();
		}
		
		// make sure to close the cursor
		cursor.close();
		
		return usuarios;
	}
	
	
}
