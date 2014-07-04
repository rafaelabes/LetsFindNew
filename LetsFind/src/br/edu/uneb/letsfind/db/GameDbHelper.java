package br.edu.uneb.letsfind.db;

import android.content.Context;
//import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
//import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

/* SqliteUtil ou DbUtil  */
public class GameDbHelper extends SQLiteOpenHelper {

	private static final String DATABASE_NAME = "letsfind.game.db";
	private static final int DATABASE_VERSION = 1;
	
	public static final String TABLE_RANK = "rank";
	public static final String RANK_ID = "id";
	public static final String RANK_NOME = "nomeDeUsuario";
	public static final String RANK_MOEDAS = "moedas";
	public static final String RANK_PONTUACAO = "pontuacao";
	
	private static final String CREATE_TABLE_RANK_1 = "CREATE TABLE " + TABLE_RANK + " ("+ 
		    RANK_ID + "            INTEGER        PRIMARY KEY AUTOINCREMENT,"+
		    RANK_NOME + " VARCHAR( 45 ),"+
		    RANK_MOEDAS + "        REAL,"+
		    RANK_PONTUACAO + "     INT"+
    ");";
	
	
	/*rank confere */
	
	public static final String TABLE_TEMA = "tema";
	public static final String TEMA_ID = "id";
	public static final String TEMA_NOME = "nome";
	
	private static final String CREATE_TABLE_TEMA_2 = "CREATE TABLE " + TABLE_TEMA + " ("+ 
		    TEMA_ID + "   INTEGER        PRIMARY KEY AUTOINCREMENT,"+
		    TEMA_NOME + " VARCHAR( 45 ) "+
    ");";
	
	public static final String TABLE_PERGUNTA = "pergunta";
	public static final String PERGUNTA_ID = "id";
	public static final String PERGUNTA_TEXTO = "texto";
	public static final String PERGUNTA_RESPONDIDA = "respondida";
	
	//public static final String PERGUNTA_RAIO = "raio";
	public static final String PERGUNTA_FK_TEMA = "fk_tema";
	
	private static final String CREATE_TABLE_PERGUNTA_3 = "CREATE TABLE " + TABLE_PERGUNTA + " ("
			+ PERGUNTA_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
			+ PERGUNTA_TEXTO + " TEXT,"
			+ PERGUNTA_TEXTO + " TEXT,"
			+ PERGUNTA_RESPONDIDA + " BOOLEAN," 
			//+ PERGUNTA_RAIO + " REAL,"
			+ PERGUNTA_FK_TEMA + " INTEGER REFERENCES "+ TABLE_TEMA +" ( " + TEMA_ID + " )";
	
	
	
	
	
	/*tema confere */
	
	public static final String TABLE_JOGADOR = "jogador";
	public static final String JOGADOR_ID = "id";
	public static final String JOGADOR_NOME = "nomeDeUsuario";
	public static final String JOGADOR_PONTUACAO = "pontuacao";
	public static final String JOGADOR_MOEDAS = "moedas";
	//public static final String JOGADOR_FK_TEMA = "tema";
	public static final String JOGADOR_ULTIMA_TENTATIVA = "ultimaTentativa";
	
	private static final String CREATE_TABLE_JOGADOR_4 = "CREATE TABLE " + TABLE_JOGADOR + " ("+
			JOGADOR_ID + "             INTEGER        PRIMARY KEY AUTOINCREMENT,"+
			JOGADOR_NOME + "   VARCHAR( 45 ),"+
			JOGADOR_PONTUACAO + "       INTEGER,"+
			JOGADOR_MOEDAS + "          REAL,"+
			//JOGADOR_FK_TEMA + "            INTEGER        REFERENCES " + TABLE_TEMA + " ( id ),"+
			JOGADOR_ULTIMA_TENTATIVA + " INTEGER"+
	");";
	
	
	/* jogador confere
	 * ultima tentativa esta como integer mas sera uma data: o timestamp de uma data
	 * 
	 * */
		

	public static final String TABLE_PONTO = "pontoTuristico";
	public static final String PONTO_ID = "id";
	public static final String PONTO_NOME = "nome";
	public static final String PONTO_LATITUDE = "latitude";
	public static final String PONTO_LONGITUDE = "longitude";
	public static final String PONTO_RAIO = "raio";
	public static final String PONTO_FK_PERGUNTA = "fk_pergunta";
	
	
	private static final String CREATE_TABLE_PONTO_5 = "CREATE TABLE pontoTuristico ("+ 
		    PONTO_ID + "        INTEGER        PRIMARY KEY AUTOINCREMENT,"+
		    PONTO_NOME + "      VARCHAR( 45 ),"+
		    PONTO_LATITUDE + "  REAL,"+
		    PONTO_LONGITUDE + " REAL,"+
		    PONTO_RAIO + " REAL" +
		    PONTO_FK_PERGUNTA + "      INTEGER        REFERENCES " + TABLE_PERGUNTA + " ( " + PERGUNTA_ID + " ),"+
		    ");";
	
	
/* TODO: DICA EH UMA TENTATIVA DE ACHAR UMA RESPOSTA: PORTANTO EH RELACIONADA A PERGUNTA  */
	
	public static final String TABLE_DICA = "dica";
	public static final String DICA_ID = "id";
	public static final String DICA_VALOR = "valorDeCompra";
	public static final String DICA_TEXTO = "texto";
	public static final String DICA_LATITUDE = "texto";
	public static final String DICA_LONGITUDE = "texto";
	public static final String DICA_RAIO = "texto";
	public static final String DICA_FK_PERGUNTA = "fk_pergunta";
	
	private static final String CREATE_TABLE_DICA_6 = "CREATE TABLE dica ("+ 
		    DICA_ID + "             INTEGER PRIMARY KEY AUTOINCREMENT,"+
		    DICA_VALOR + "  REAL,"+
		    DICA_TEXTO + "          TEXT,"+
		    DICA_LATITUDE + "  REAL,"+
		    DICA_LONGITUDE + " REAL,"+
		    DICA_RAIO + " REAL" +
		    DICA_FK_PERGUNTA +" INTEGER REFERENCES " + TABLE_PERGUNTA + " ( " + PERGUNTA_ID + " )"+ 
    ");";
	
	/* dica confere */
	
	
	
	
	public GameDbHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL(CREATE_TABLE_RANK_1);
		db.execSQL(CREATE_TABLE_TEMA_2);
		db.execSQL(CREATE_TABLE_PERGUNTA_3);
		db.execSQL(CREATE_TABLE_JOGADOR_4);
		db.execSQL(CREATE_TABLE_PONTO_5);
		db.execSQL(CREATE_TABLE_DICA_6);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		
		//db.execSQL("PRAGMA foreign_keys = OFF;");
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_DICA);
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_PONTO);
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_JOGADOR);
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_PERGUNTA);
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_TEMA);
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_RANK);
		//db.execSQL("PRAGMA foreign_keys = ON;");
		onCreate(db);
	}
	
}

