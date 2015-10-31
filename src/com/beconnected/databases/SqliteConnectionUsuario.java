package com.beconnected.databases;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class SqliteConnectionUsuario extends SQLiteOpenHelper {

	
		
			String TABLA_EMPRESA_U = "CREATE TABLE IF NOT EXISTS EMPRESA_U (ID_EMPRESA INTEGER,"
					+ " EMPRESA VARCHAR(200),"
					+ " LONGITUD VARCHAR(200),"
					+ " LATITUD VARCHAR(200),"
					+ " URL_LOGO VARCHAR(300),"
					+ " LOGO BLOB);";

			String TABLA_PROMO_U = "CREATE TABLE IF NOT EXISTS PROMO_U (ID_PROMO INTEGER,"
					+ " TITULO VARCHAR(200),"
					+ " DESCRIPCION VARCHAR(500),"
					+ " ID_EMPRESA INTEGER,"
					+ " FECHA_INICIO VARCHAR(100),"
					+ " FECHA_FIN VARCHAR(100));";

			String TABLA_INFO_U = "CREATE TABLE IF NOT EXISTS INFO_U (SOMOS VARCHAR(500),"
					+ " CONTACTO VARCHAR(500));";


	
	public SqliteConnectionUsuario(Context context, String name,
			CursorFactory factory, int version) {
		super(context, name, factory, version);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		// TODO Auto-generated method stub
		db.execSQL(TABLA_EMPRESA_U);
		db.execSQL(TABLA_PROMO_U);
		db.execSQL(TABLA_INFO_U);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub
		 db.execSQL("DROP TABLE IF EXISTS EMPRESA_U");
		 db.execSQL("DROP TABLE IF EXISTS PROMO_U");
		 db.execSQL("DROP TABLE IF EXISTS INFO_U");
	        //Se crea la nueva versión de la tabla
			db.execSQL(TABLA_EMPRESA_U);
			db.execSQL(TABLA_PROMO_U);
			db.execSQL(TABLA_INFO_U);
	}

}
