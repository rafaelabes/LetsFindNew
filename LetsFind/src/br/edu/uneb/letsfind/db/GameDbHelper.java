package br.edu.uneb.letsfind.db;

import android.content.ContentValues;
import android.content.Context;
//import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
//import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

/* SqliteUtil ou DbUtil  */
public class GameDbHelper extends SQLiteOpenHelper {

	private static final String DATABASE_NAME = "letsfind.game.db";
	private static final int DATABASE_VERSION = 4;
	
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
			+ PERGUNTA_RESPONDIDA + " BOOLEAN," 
			//+ PERGUNTA_RAIO + " REAL,"
			+ PERGUNTA_FK_TEMA + " INTEGER REFERENCES "+ TABLE_TEMA +" ( " + TEMA_ID + " )"
			+ ")";
	
	
	
	
	
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
	
	
	private static final String CREATE_TABLE_PONTO_5 = "CREATE TABLE "+TABLE_PONTO+" ("+ 
		    PONTO_ID + "        INTEGER        PRIMARY KEY AUTOINCREMENT,"+
		    PONTO_NOME + "      VARCHAR( 45 ),"+
		    PONTO_LATITUDE + "  REAL,"+
		    PONTO_LONGITUDE + " REAL,"+
		    PONTO_RAIO + " REAL, " +
		    PONTO_FK_PERGUNTA + "      INTEGER        REFERENCES " + TABLE_PERGUNTA + " ( " + PERGUNTA_ID + " )"+
		    ");";
	
	
/* TODO: DICA EH UMA TENTATIVA DE ACHAR UMA RESPOSTA: PORTANTO EH RELACIONADA A PERGUNTA  */
	
	public static final String TABLE_DICA = "dica";
	public static final String DICA_ID = "id";
	public static final String DICA_VALOR = "valorDeCompra";
	public static final String DICA_TEXTO = "texto";
	public static final String DICA_LATITUDE = "latitude";
	public static final String DICA_LONGITUDE = "longitude";
	public static final String DICA_RAIO = "raio";
	public static final String DICA_FK_PERGUNTA = "fk_pergunta";
	
	private static final String CREATE_TABLE_DICA_6 = "CREATE TABLE "+TABLE_DICA+" ("+ 
		    DICA_ID + "             INTEGER PRIMARY KEY AUTOINCREMENT, "+
		    DICA_VALOR + "  REAL, "+
		    DICA_TEXTO + "          TEXT, "+
		    DICA_LATITUDE + "  REAL, "+
		    DICA_LONGITUDE + " REAL, "+
		    DICA_RAIO + " REAL, " +
		    DICA_FK_PERGUNTA +" INTEGER REFERENCES " + TABLE_PERGUNTA + " ( " + PERGUNTA_ID + " )"+ 
    ");";
	
	
	/* enchendo os bancos */
	private long fillTema(SQLiteDatabase db, String nome){
		ContentValues values = new ContentValues();
		values.put(TEMA_NOME, nome);
		return db.insert(GameDbHelper.TABLE_TEMA, null, values);
	}
	
	private long fillPergunta(SQLiteDatabase db, long fkTema, String nome){
		ContentValues values = new ContentValues();
		values.put(GameDbHelper.PERGUNTA_TEXTO, nome);
		values.put(GameDbHelper.PERGUNTA_RESPONDIDA, false);
		values.put(GameDbHelper.PERGUNTA_FK_TEMA, fkTema);
		return db.insert(GameDbHelper.TABLE_PERGUNTA, null, values);
	}
	
	
	private long fillPonto(SQLiteDatabase db, long fkPergunta, String nome, double latitude, double longitude, double raio){
		ContentValues values = new ContentValues();
		values.put(GameDbHelper.PONTO_NOME, nome);
		values.put(GameDbHelper.PONTO_LATITUDE, latitude);
		values.put(GameDbHelper.PONTO_LONGITUDE, longitude);
		values.put(GameDbHelper.PONTO_RAIO, raio);
		values.put(GameDbHelper.PONTO_FK_PERGUNTA, fkPergunta);
		return db.insert(GameDbHelper.TABLE_PONTO, null, values);
	}
	
	private long fillDica(SQLiteDatabase db, long fkPergunta, String texto, double valorDeCompra, double latitude, double longitude, double raio){
		ContentValues values = new ContentValues();
		values.put(GameDbHelper.DICA_TEXTO, texto);
		values.put(GameDbHelper.DICA_VALOR, valorDeCompra);
		values.put(GameDbHelper.DICA_LATITUDE, latitude);
		values.put(GameDbHelper.DICA_LONGITUDE, longitude);
		values.put(GameDbHelper.DICA_RAIO, raio);
		values.put(GameDbHelper.DICA_FK_PERGUNTA, fkPergunta);
		return db.insert(GameDbHelper.TABLE_DICA, null, values);
	}
	
	
	private void fill(SQLiteDatabase db){
		
		Double raio = 3.0694775721281596E-4;
		long fkTema = 0, fkPergunta = 0, fkPonto = 0, fkDica = 0;
		
		//inserindo tema
		String nome = "Copa do Mundo 2014";
		fkTema = fillTema(db, nome);
		
		//inserindo pergunta e ponto
		nome = "Arena Fonte Nova";
		fkPergunta = fillPergunta(db, fkTema, nome);
		fkPonto = fillPonto(db, fkPergunta, nome, -12.97883,-38.504371, raio);
		
		nome = "Arena Corinthians";
		fkPergunta = fillPergunta(db, fkTema, nome);
		fkPonto = fillPonto(db, fkPergunta, nome, -23.545333,-46.473702, raio);
		
		nome = "Mané Garrincha";
		fkPergunta = fillPergunta(db, fkTema, nome);
		fkPonto = fillPonto(db, fkPergunta, nome, -15.783519,-47.899211, raio);
		
		nome = "Estádio Mineirão";
		fkPergunta = fillPergunta(db, fkTema, nome);
		fkPonto = fillPonto(db, fkPergunta, nome, -19.865867,-43.971132,raio);
		
		nome = "Estádio Maracanã";
		fkPergunta = fillPergunta(db, fkTema, nome);
		fkPonto = fillPonto(db, fkPergunta, nome, -22.9127667,-43.2300316, raio);
		
		nome = "Estádio José do Rego Maciel";
		fkPergunta = fillPergunta(db, fkTema, nome);
		fkPonto = fillPonto(db, fkPergunta, nome, -8.026699,-34.891111, raio);
		
		nome = "Villa Estádio Alvorada";
		fkPergunta = fillPergunta(db, fkTema, nome);
		fkPonto = fillPonto(db, fkPergunta, nome, -3.08026,-60.034402, raio);
		
		nome = "Arena da Amazônia";
		fkPergunta = fillPergunta(db, fkTema, nome);
		fkPonto = fillPonto(db, fkPergunta, nome, -3.0836637,-60.0279703, raio);
		
		nome = "Arena Pantanal";
		fkPergunta = fillPergunta(db, fkTema, nome);
		fkPonto = fillPonto(db, fkPergunta, nome, -15.604017,-56.121632, raio);
		
		nome = "Estadio Beira Rio";
		fkPergunta = fillPergunta(db, fkTema, nome);
		fkPonto = fillPonto(db, fkPergunta, nome, -30.0654729,-51.2358289, raio);
		
		nome = "Arena da Baixada";
		fkPergunta = fillPergunta(db, fkTema, nome);
		fkPonto = fillPonto(db, fkPergunta, nome, -25.448212,-49.276987, raio);
		
		nome = "Estádio das Dunas";
		fkPergunta = fillPergunta(db, fkTema, nome);
		fkPonto = fillPonto(db, fkPergunta, nome, -5.826827,-35.21243, raio);
		
		nome = "Estadio Castelão";
		fkPergunta = fillPergunta(db, fkTema, nome);
		fkPonto = fillPonto(db, fkPergunta, nome, -3.807312,-38.522269, raio);
		
		
		nome = "Arena da baixada";
		fkPergunta = fillPergunta(db, fkTema, nome);
		fkPonto = fillPonto(db, fkPergunta, nome, -25.448212,-49.276987, raio);
		
		//inserindo dicas
		
	}
	
	public GameDbHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL(CREATE_TABLE_RANK_1); //baixado da internet
		db.execSQL(CREATE_TABLE_TEMA_2); // insert
		db.execSQL(CREATE_TABLE_PERGUNTA_3); // insert
		db.execSQL(CREATE_TABLE_JOGADOR_4); //baixado da internet ou cadastrado no dispositivo
		db.execSQL(CREATE_TABLE_PONTO_5); // insert
		db.execSQL(CREATE_TABLE_DICA_6); // insert
		
		fill(db);		
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

