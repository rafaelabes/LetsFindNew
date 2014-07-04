package br.edu.uneb.webclient.db;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

/*
 * esta classe vai guardar a secao
 * no caso os cookies da pagina
 * porque o programa vai ser como
 * se fosse um navegador.
 * so que as mensagens vao ser todas
 * em json (eu pretendo)
 * */
public class CookieDataSource {
	
	private SQLiteDatabase database;
	private CookieDbHelper dbHelper;
	
	private String hostAddress = null;
	private String path = null;

	public void setHostAddress(String hostAddress) {
		this.hostAddress = hostAddress;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public CookieDataSource(Context context){
		dbHelper = new CookieDbHelper(context);
	}
	
	public void open() throws SQLException{
		database = dbHelper.getWritableDatabase();
	}
	
	public void close(){
		dbHelper.close();
	}
	
	
	private long getCookieId(String baseDomain, String name, String path){
			
		long cookid = 0;
		
		Cursor cursor = database.query(
				CookieDbHelper.TABLE_COOKIE,
				new String[]{ CookieDbHelper.COOKIE_ID },
				CookieDbHelper.COOKIE_NAME + " = ? AND "+
				CookieDbHelper.COOKIE_PATH + " = ? AND "+
				CookieDbHelper.COOKIE_BASE_DOMAIN + " = ?",
				new String[]{
						name.replaceAll("\'", "\\\'"),
						path.replaceAll("\'", "\\\'"),
						baseDomain.replaceAll("\'", "\\\'")
				}
				, null, null, null);
		
		cursor.moveToFirst();
		
		if(!cursor.isAfterLast()){
			cookid = cursor.getLong(0);
		}
		
		// make sure to close the cursor
		cursor.close();
		
		return cookid;
	}
	
	
	public void setCookie(String baseDomain, String name, String value, String host, String path, long expiry, boolean isSecure, boolean isHttpOnly){
		
		long cookid = getCookieId(baseDomain, name, path);
		
		if(cookid > 0){
			cookieUpdate(cookid, baseDomain, name, value, host, path, expiry, isSecure, isHttpOnly);
		}
		else{
			cookieInsert(baseDomain, name, value, host, path, expiry, isSecure, isHttpOnly);
		}
		
	}
	
	
	private void cookieInsert(String baseDomain, String name, String value, String host, String path, long expiry, boolean isSecure, boolean isHttpOnly){
		 
		Date d = new Date();
		long now = d.getTime();
		
		ContentValues values = new ContentValues();
		
		values.put(CookieDbHelper.COOKIE_BASE_DOMAIN, baseDomain);
		values.put(CookieDbHelper.COOKIE_NAME, name);
		values.put(CookieDbHelper.COOKIE_VALUE, value);
		values.put(CookieDbHelper.COOKIE_HOST, host);
		values.put(CookieDbHelper.COOKIE_PATH, path);
		values.put(CookieDbHelper.COOKIE_EXPIRY, expiry);
		values.put(CookieDbHelper.COOKIE_CREATION_TIME, now);
		values.put(CookieDbHelper.COOKIE_IS_SECURE, isSecure);
		values.put(CookieDbHelper.COOKIE_LAST_ACESSED, now);
		values.put(CookieDbHelper.COOKIE_IS_HTTP_ONLY, isHttpOnly);
		
		database.insert(CookieDbHelper.TABLE_COOKIE, null, values);
		
	}

	
	private int cookieUpdate(long cookid, String baseDomain, String name, String value, String host, String path, long expiry, boolean isSecure, boolean isHttpOnly){
		 
		Date d = new Date();
		long now = d.getTime();
		
		ContentValues values = new ContentValues();
		
		values.put(CookieDbHelper.COOKIE_BASE_DOMAIN, baseDomain);
		values.put(CookieDbHelper.COOKIE_NAME, name);
		values.put(CookieDbHelper.COOKIE_VALUE, value);
		values.put(CookieDbHelper.COOKIE_HOST, host);
		values.put(CookieDbHelper.COOKIE_PATH, path);
		values.put(CookieDbHelper.COOKIE_EXPIRY, expiry);
		values.put(CookieDbHelper.COOKIE_CREATION_TIME, now);
		values.put(CookieDbHelper.COOKIE_IS_SECURE, isSecure);
		values.put(CookieDbHelper.COOKIE_LAST_ACESSED, now);
		values.put(CookieDbHelper.COOKIE_IS_HTTP_ONLY, isHttpOnly);
		
		return database.update(CookieDbHelper.TABLE_COOKIE, values, CookieDbHelper.COOKIE_ID + " = ?",
	            new String[] { String.valueOf(cookid) });
		
	}
	
	
	public List<String> getCookies(String baseDomain, String path){
		
		List<String> cookies = new ArrayList<String>();
		Date d = new Date();
		Long now = d.getTime(); 
		
		database.delete(CookieDbHelper.TABLE_COOKIE, " ? > 0 AND ? < ?", new String[]{ CookieDbHelper.COOKIE_EXPIRY, CookieDbHelper.COOKIE_EXPIRY, now.toString() });
		
		Cursor cursor = database.query(CookieDbHelper.TABLE_COOKIE, new String[]{ CookieDbHelper.COOKIE_NAME, CookieDbHelper.COOKIE_VALUE },
				"('."+baseDomain+"' LIKE '%.'||substr(" + CookieDbHelper.COOKIE_BASE_DOMAIN + ", 2))"+
				" OR (" + CookieDbHelper.COOKIE_BASE_DOMAIN + " = '" + baseDomain + "' AND " + CookieDbHelper.COOKIE_PATH + " ='/')" +
				" OR (" + CookieDbHelper.COOKIE_BASE_DOMAIN + " = '" + baseDomain + "' AND " + CookieDbHelper.COOKIE_PATH + " ='"+path.replaceAll("\'", "\\\'")+"')",
				null, null, null, null);
		
		cursor.moveToFirst();
		
		while(!cursor.isAfterLast()){
			cookies.add(cursor.getString(0)+"="+cursor.getString(1));
			cursor.moveToNext();
		}
		
	    return cookies;
	}
	
	/*
	 * novo: usar para 
	 * */
	public String toString(){
		
		if(this.hostAddress == null){
			Log.e("CookieDataSource", "the host address is null");
			return null;
		}
		
		if(this.path == null){
			this.path = "";
		}
		
		
		List<String> cookies = getCookies(this.hostAddress, this.path);
		String scookies = "";
		
		if(cookies.size() > 0){
			for (String cookie : cookies) {
				//takes only the first part of the cookie
				//scookies += cookie.substring(0, cookie.indexOf(';')) + ";";
				scookies += cookie + ";";
			}
		}
		return scookies;
		
	}
	
	public Date parseDate(String val){
		
		//DateFormat df = new SimpleDateFormat("EEE, dd MMM yyyy kk:mm:ss Z", Locale.ENGLISH);
		DateFormat df = new SimpleDateFormat("EEE, dd-MMM-yyyy kk:mm:ss Z", Locale.ENGLISH);
		                                      //Tue, 06-May-2014 23
		Date d = null;
		try {
				d = df.parse(val);
				//System.out.println(d.toString());
			} catch (ParseException e) {
				e.printStackTrace();
			}
		return d;
	}
	
	public void setCookie(String cookie_text){

		String[] partes = cookie_text.split(";");
		
		String baseDomain = this.hostAddress;
		String name = partes[0].substring(0, partes[0].indexOf('='));
		String value = partes[0].substring(partes[0].indexOf('=')+1, partes[0].length());
		
		//System.err.println("V"+value);
		
		String host = this.hostAddress;
		String path = "/";
		long expiry = 0;
		boolean isSecure = false;
		boolean isHttpOnly = false;
		
		for (int i = 0; i < partes.length; i++) {
				String[] part = partes[i].split("=");
				if(part[0].toLowerCase(Locale.getDefault()).trim().equals("expires")){
					expiry = parseDate(part[1]).getTime();
				}
				else if(part[0].toLowerCase(Locale.getDefault()).trim().equals("path")){
					path = part[1];
				}
				else if(part[0].toLowerCase(Locale.getDefault()).trim().equals("domain")){
					baseDomain = part[1];
				}
				else if(part[0].toLowerCase(Locale.getDefault()).trim().equals("secure")){
					isSecure = true;
				}
				else if(part[0].toLowerCase(Locale.getDefault()).trim().equals("httponly")){
					isHttpOnly = true;
				}
		}			
		
		
		long cookid = getCookieId(baseDomain, name, path);
		
		if(cookid > 0){
			cookieUpdate(cookid, baseDomain, name, value, host, path, expiry, isSecure, isHttpOnly);
		}
		else{
			cookieInsert(baseDomain, name, value, host, path, expiry, isSecure, isHttpOnly);
		}
		
	}

}
