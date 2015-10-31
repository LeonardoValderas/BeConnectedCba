package com.beconnected.databases;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

public class SqliteConnectionAdm extends SQLiteOpenHelper {

	
	 String sqlCreate = "CREATE TABLE Usuarios (codigo INTEGER, nombre TEXT)";
	 String TABLA_EMPRESA = "CREATE TABLE IF NOT EXISTS EMPRESA (ID_EMPRESA INTEGER,"
				+ " EMPRESA VARCHAR(200),"
				+ " LONGITUD VARCHAR(200),"
				+ " LATITUD VARCHAR(200),"
				+ " URL_LOGO VARCHAR(300),"
				+ " LOGO BLOB);";

		String TABLA_PROMO = "CREATE TABLE IF NOT EXISTS PROMO (ID_PROMO INTEGER,"
				+ " TITULO VARCHAR(200),"
				+ " DESCRIPCION VARCHAR(500),"
				+ " ID_EMPRESA INTEGER,"
				+ " FECHA_INICIO VARCHAR(100),"
				+ " FECHA_FIN VARCHAR(100));";

		String TABLA_INFO = "CREATE TABLE IF NOT EXISTS INFO (SOMOS VARCHAR(500),"
				+ " CONTACTO VARCHAR(500));";
	
	public SqliteConnectionAdm(Context context, String name,
			CursorFactory factory, int version) {
		super(context, name, factory, version);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		// TODO Auto-generated method stub
		db.execSQL(TABLA_EMPRESA);
		db.execSQL(TABLA_PROMO);
		db.execSQL(TABLA_INFO);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub
		 db.execSQL("DROP TABLE IF EXISTS EMPRESA");
		 db.execSQL("DROP TABLE IF EXISTS PROMO");
		 db.execSQL("DROP TABLE IF EXISTS INFO");
	        //Se crea la nueva versión de la tabla
		    db.execSQL(TABLA_EMPRESA);
			db.execSQL(TABLA_PROMO);
			db.execSQL(TABLA_INFO);
	}

}
