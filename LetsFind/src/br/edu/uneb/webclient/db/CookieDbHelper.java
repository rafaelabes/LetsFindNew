package br.edu.uneb.webclient.db;

import android.content.Context;
//import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
//import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/* SqliteUtil ou DbUtil  */
public class CookieDbHelper extends SQLiteOpenHelper {

	private static CookieDbHelper cookiesDbInstance = null;
	private Context context;
	
	private static final String DATABASE_NAME = "letsfind.cookies.db";
	private static final int DATABASE_VERSION = 1;
	
	public static final String TABLE_COOKIE = "cookie";
	
	public static final String COOKIE_ID = "id";
	public static final String COOKIE_BASE_DOMAIN ="baseDomain";
	public static final String COOKIE_NAME = "name";
	public static final String COOKIE_VALUE ="value";
	public static final String COOKIE_HOST ="host";
	public static final String COOKIE_PATH = "path";
	public static final String COOKIE_EXPIRY = "expiry";
	public static final String COOKIE_CREATION_TIME = "creationTime";
	public static final String COOKIE_IS_SECURE = "isSecure";
	public static final String COOKIE_LAST_ACESSED = "lastAcessed";
	public static final String COOKIE_IS_HTTP_ONLY = "isHttpOnly"; 
	
	
	private static final String CREATE_TABLE_COOKIE = "CREATE TABLE IF NOT EXISTS cookie ("+
			COOKIE_ID + " INTEGER PRIMARY KEY ASC AUTOINCREMENT,"+
			COOKIE_BASE_DOMAIN + " TEXT,"+
			COOKIE_NAME + " TEXT,"+
			COOKIE_VALUE + " TEXT,"+
			COOKIE_HOST + " TEXT,"+
			COOKIE_PATH + " TEXT,"+
			COOKIE_EXPIRY + " INTEGER,"+
			COOKIE_CREATION_TIME + " INTEGER,"+
			COOKIE_IS_SECURE + " BOOLEAN,"+
			COOKIE_LAST_ACESSED + " INTEGER,"+
			COOKIE_IS_HTTP_ONLY +" BOOLEAN" +
	");";
	
	
	private CookieDbHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
		this.context = context;
	}
	
	public static CookieDbHelper getInstance(Context ctx) {
        if (cookiesDbInstance == null) {
        	cookiesDbInstance = new CookieDbHelper(ctx.getApplicationContext());
        }
        return cookiesDbInstance;
    }

	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL(CREATE_TABLE_COOKIE);
		Log.v("cookies","created");
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		
		//db.execSQL("PRAGMA foreign_keys = OFF;");
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_COOKIE);
		//db.execSQL("PRAGMA foreign_keys = ON;");
		onCreate(db);
	}
	
}
