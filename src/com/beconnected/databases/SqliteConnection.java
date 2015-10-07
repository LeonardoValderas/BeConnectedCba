package com.beconnected.databases;

import java.io.File;
import java.util.ArrayList;

import android.content.ContentValues;
import android.content.Context;
import android.content.ContextWrapper;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class SqliteConnection extends SQLiteOpenHelper {

	private Context context;

	public SqliteConnection(Context context) {
		super(new ContextWrapper(context) {

		}, GeneralLogic.DATABASE_NAME, null, 1);
		this.context = context;
	}

	public SQLiteDatabase getSqLiteDatabase(String metodo) {

		return this.getWritableDatabase();
	}

	@Override
	public void onCreate(SQLiteDatabase db) {

	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

	}

	/**
	 * Crea las tablas necesarias para el sistema
	 * 
	 */
	public void createTablesBD() {

		String TABLA_EMPRESA = "CREATE TABLE IF NOT EXISTS EMPRESA (ID_EMPRESA INTEGER PRIMARY KEY AUTOINCREMENT,"
				+ " EMPRESA VARCHAR(100),"
				+ " LONGITUD VARCHAR(100),"
				+ " LATITUD VARCHAR(100),"
				+ " LOGO BLOB);";
		
		String TABLA_PROMO = "CREATE TABLE IF NOT EXISTS PROMO (ID_PROMO INTEGER PRIMARY KEY AUTOINCREMENT,"
				+ " TITULO VARCHAR(100),"
				+ " DESCRIPCION VARCHAR(100),"
				+ " ID_EMPRESA INTEGER,"
				+ " FECHA_INICIO VARCHAR(100),"
				+ " FECHA_FIN VARCHAR(100),"
				+ " USUARIO VARCHAR(100)," + " FECHA_CREACION VARCHAR(100));";
		
		String TABLA_PROMO2 = "CREATE TABLE IF NOT EXISTS PROMO (ID_PROMO INTEGER PRIMARY KEY AUTOINCREMENT,"
				+ " TITULO VARCHAR(100),"
				+ " DESCRIPCION VARCHAR(100),"
				+ " ID_EMPRESA INTEGER,"
				+ " LOGO BLOB,"
				+ " FECHA_INICIO VARCHAR(100),"
				+ " FECHA_FIN VARCHAR(100),"
				+ " USUARIO VARCHAR(100)," + " FECHA_CREACION VARCHAR(100));";
		
		String TABLA_EMPRESAd= "CREATE TABLE IF NOT EXISTS MAPA (ID_EMPRESA INTEGER PRIMARY KEY AUTOINCREMENT,"
				+ " EMPRESA VARCHAR(100),"
				+ " LONGITUD VARCHAR(100),"
				+ " LATITUD VARCHAR(100),"
				+ " FECHA_CREACION VARCHAR(100),"
				+ " FECHA_ACTUALIZACION VARCHAR(100),"
				+ " ESTADO VARCHAR(1),"
				+ " TABLA VARCHAR(100));";
		
		

		String TABLA_DIVISION_ADEFUL = "CREATE TABLE IF NOT EXISTS DIVISION_ADEFUL (ID_DIVISION INTEGER PRIMARY KEY AUTOINCREMENT,"
				+ " DESCRIPCION VARCHAR(100),"
				+ " USUARIO_CREADOR VARCHAR(100),"
				+ " FECHA_CREACION VARCHAR(100),"
				+ " FECHA_ACTUALIZACION VARCHAR(100),"
				+ " ESTADO VARCHAR(1),"
				+ " TABLA VARCHAR(100));";

		String TABLA_TORNEO_ADEFUL = "CREATE TABLE IF NOT EXISTS TORNEO_ADEFUL (ID_TORNEO INTEGER PRIMARY KEY AUTOINCREMENT,"
				+ " DESCRIPCION VARCHAR(100),"
				+ " USUARIO_CREADOR VARCHAR(100),"
				+ " FECHA_CREACION VARCHAR(100),"
				+ " FECHA_ACTUALIZACION VARCHAR(100),"
				+ " ESTADO VARCHAR(1),"
				+ " TABLA VARCHAR(100));";

		String TABLA_CANCHA_ADEFUL = "CREATE TABLE IF NOT EXISTS CANCHA_ADEFUL (ID_CANCHA INTEGER PRIMARY KEY AUTOINCREMENT,"
				+ " NOMBRE VARCHAR(100),"
				+ " LONGITUD VARCHAR(100),"
				+ " LATITUD VARCHAR(100),"
				+ " DIRECCION VARCHAR(100),"
				+ " USUARIO_CREADOR VARCHAR(100),"
				+ " FECHA_CREACION VARCHAR(100),"
				+ " FECHA_ACTUALIZACION VARCHAR(100),"
				+ " ESTADO VARCHAR(1),"
				+ " TABLA VARCHAR(100));";

		String TABLA_EQUIPO_LIFUBA = "CREATE TABLE IF NOT EXISTS EQUIPO_LIFUBA (ID_EQUIPO INTEGER PRIMARY KEY AUTOINCREMENT,"
				+ " NOMBRE VARCHAR(100),"
				+ " ESCUDO BLOB,"
				+ " USUARIO_CREADOR VARCHAR(100),"
				+ " FECHA_CREACION VARCHAR(100),"
				+ " FECHA_ACTUALIZACION VARCHAR(100),"
				+ " ESTADO VARCHAR(1),"
				+ " TABLA VARCHAR(100));";

		String TABLA_DIVISION_LIFUBA = "CREATE TABLE IF NOT EXISTS DIVISION_LIFUBA (ID_DIVISION INTEGER PRIMARY KEY AUTOINCREMENT,"
				+ " DESCRIPCION VARCHAR(100),"
				+ " USUARIO_CREADOR VARCHAR(100),"
				+ " FECHA_CREACION VARCHAR(100),"
				+ " FECHA_ACTUALIZACION VARCHAR(100),"
				+ " ESTADO VARCHAR(1),"
				+ " TABLA VARCHAR(100));";

		String TABLA_TORNEO_LIFUBA = "CREATE TABLE IF NOT EXISTS TORNEO_LIFUBA (ID_TORNEO INTEGER PRIMARY KEY AUTOINCREMENT,"
				+ " DESCRIPCION VARCHAR(100),"
				+ " USUARIO_CREADOR VARCHAR(100),"
				+ " FECHA_CREACION VARCHAR(100),"
				+ " FECHA_ACTUALIZACION VARCHAR(100),"
				+ " ESTADO VARCHAR(1),"
				+ " TABLA VARCHAR(100));";

		String TABLA_CANCHA_LIFUBA = "CREATE TABLE IF NOT EXISTS CANCHA_LIFUBA(ID_CANCHA INTEGER PRIMARY KEY AUTOINCREMENT,"
				+ " NOMBRE VARCHAR(100),"
				+ " LONGITUD VARCHAR(100),"
				+ " LATITUD VARCHAR(100),"
				+ " DIRECCION VARCHAR(100),"
				+ " USUARIO_CREADOR VARCHAR(100),"
				+ " FECHA_CREACION VARCHAR(100),"
				+ " FECHA_ACTUALIZACION VARCHAR(100),"
				+ " ESTADO VARCHAR(1),"
				+ " TABLA VARCHAR(100));";

		// MODULO MI EQUIPO

		String TABLA_FIXTURE_ADEFUL = "CREATE TABLE IF NOT EXISTS FIXTURE_ADEFUL(ID_FIXTURE INTEGER PRIMARY KEY AUTOINCREMENT,"
				+ " ID_EQUIPO_lOCAL INTERGER,"
				+ " ID_EQUIPO_VISITA INTERGER,"
				+ " ID_DIVISION INTERGER,"
				+ " ID_TORNEO INTERGER,"
				+ " ID_CANCHA INTERGER,"
				+ " ID_EQUIPO INTERGER,"
				+ " ID_FECHA INTERGER,"
				+ " ID_ANIO INTERGER,"
				+ " DIA VARCHAR(100),"
				+ " HORA VARCHAR(100),"
				+ " USUARIO_CREADOR VARCHAR(100),"
				+ " FECHA_CREACION VARCHAR(100),"
				+ " FECHA_ACTUALIZACION VARCHAR(100),"
				+ " ESTADO VARCHAR(1),"
				+ " TABLA VARCHAR(100));";

		String TABLA_FECHA = "CREATE TABLE IF NOT EXISTS FECHA (ID_FECHA INTEGER PRIMARY KEY AUTOINCREMENT,"
				+ " FECHA VARCHAR(100));";
		String TABLA_ANIO = "CREATE TABLE IF NOT EXISTS ANIO (ID_ANIO INTEGER PRIMARY KEY AUTOINCREMENT,"
				+ " ANIO VARCHAR(100));";

		SQLiteDatabase database = getSqLiteDatabase("createTables");
		if (database != null && database.isOpen()) {

			try {
				// MODULO LIGA
				database.execSQL(TABLA_PROMO);
				database.execSQL(TABLA_EMPRESA);
				
				// database.execSQL(TABLA_EQUIPO_LIFUBA);
				// database.execSQL(TABLA_DIVISION_ADEFUL);
				// database.execSQL(TABLA_DIVISION_LIFUBA);
				// database.execSQL(TABLA_TORNEO_ADEFUL);
				// database.execSQL(TABLA_TORNEO_LIFUBA);
				// database.execSQL(TABLA_CANCHA_ADEFUL);
				// database.execSQL(TABLA_CANCHA_LIFUBA);
				// database.execSQL(TABLA_FECHA);
				// database.execSQL(TABLA_ANIO);
				// MODULO MI EQUIPO
				database.execSQL(TABLA_FIXTURE_ADEFUL);
			} catch (Exception e) {
				// e.getMessage();

			}

		} else {
			// error al intentar obtener conexion con la bd
			// RegistroLog.MsjLog("Error al intentar obtener conexion con la bd."
			// + "/nClase SQLiteDBConnection - Metodo createTables()");
		}

		database = null;

		TABLA_PROMO = null;
		TABLA_EMPRESA = null;
		// TABLA_EQUIPO_LIFUBA = null;
		// TABLA_DIVISION_ADEFUL = null;
		// TABLA_DIVISION_LIFUBA = null;
		// TABLA_TORNEO_ADEFUL = null;
		// TABLA_TORNEO_LIFUBA = null;
		// TABLA_CANCHA_ADEFUL = null;
		// TABLA_CANCHA_LIFUBA = null;
		// TABLA_FIXTURE_ADEFUL = null;
		// TABLA_FECHA = null;
		// TABLA_ANIO = null;
	}

	/**
	 * Metodo que elimina la bd.
	 * 
	 */
	public void destroyDatabase() {

		File dbFile = new File(GeneralLogic.IN_DIR.getAbsolutePath() + "/"
				+ GeneralLogic.DATABASE_NAME);
		if (dbFile.exists()) {

			dbFile.delete();
		}
	}

	/**
	 * Metodo que elimina todas las tablas de la base de datos.
	 * 
	 */
	public void dropTablasBD() {

		SQLiteDatabase database = null;
		String sql1 = "DROP TABLE EQUIPO_ADEFUL";
		String sql2 = "DROP TABLE EQUIPO_LIFUBA";
		String sql3 = "DROP TABLE DIVISION_ADEFUL";
		String sql4 = "DROP TABLE DIVISION_LIFUBA";
		String sql5 = "DROP TABLE TORNEO_ADEFUL";
		String sql6 = "DROP TABLE TORNEO_LIFUBA";
		String sql7 = "DROP TABLE CANCHA_ADEFUL";
		database = getSqLiteDatabase("dropTablasBD()");
		if (database != null && database.isOpen()) {

			try {

				database.execSQL(sql1);
				database.execSQL(sql2);
				database.execSQL(sql3);
				database.execSQL(sql4);
				database.execSQL(sql5);
				database.execSQL(sql6);
				database.execSQL(sql7);

			} catch (Exception e) {

				// RegistroLog.ExcepcionLog("SQLiteDBConnection",
				// "dropTablas()",
				// e);
			}
		} else {

			// RegistroLog.MsjLog("Error al intentar obtener conexion con la bd"
			// + "\nClase SQLiteDBConnection - Metodo dropTablasBD()");
		}

		sql1 = null;
		sql2 = null;
		sql3 = null;
		sql4 = null;
		sql5 = null;
		sql6 = null;
		sql7 = null;
		database = null;
	}

	// ///////////////////////////////////////
	// ////////////////////////////////////////
	// /////Metodos tablas///////

	
	
	
	
	public boolean insertEmpresa(Empresa empresa) throws SQLiteException {
		boolean ban = false;

		ContentValues cv = new ContentValues();
		cv.put("EMPRESA", empresa.getEMPRESA());
		cv.put("LONGITUD", empresa.getLONGITUD());
		cv.put("LATITUD", empresa.getLATIDUD());
		cv.put("LOGO", empresa.getLOGO());
		
		SQLiteDatabase database = this.getWritableDatabase();

		database.insert("EMPRESA", null, cv);
		return true;
	}
	
	/**
	 * 
	 * Metodo que obtiene lista de equipos adeful.
	 */
	public ArrayList<Empresa> selectListaEmpresa() {

		String sql = "SELECT * FROM EMPRESA";
		ArrayList<Empresa> arrayEmpresa = new ArrayList<Empresa>();
		String empresaa = null, longitud = null, latitud = null;
		byte[] logo = null;
		int id;
		Cursor cursor = null;
		// Integer isFueraFrecuencia;
		SQLiteDatabase database = null;

		database = getSqLiteDatabase("selectListaEmpresa");
		if (database != null && database.isOpen()) {

			try {
				cursor = database.rawQuery(sql, null);
				if (cursor != null && cursor.getCount() > 0) {

					while (cursor.moveToNext()) {

						Empresa empresa = null;
						id = cursor.getInt(cursor.getColumnIndex("ID_EMPRESA"));
						empresaa = cursor.getString(cursor
								.getColumnIndex("EMPRESA"));

						longitud = cursor.getString(cursor
								.getColumnIndex("LONGITUD"));

						latitud = cursor.getString(cursor
								.getColumnIndex("LATITUD"));

						logo = cursor.getBlob(cursor.getColumnIndex("LOGO"));

						

						empresa = new Empresa(id, empresaa, longitud, latitud,
								logo);

						arrayEmpresa.add(empresa);

					}
				}

			} catch (Exception e) {
				Log.e("selectListaEmpresa", e.toString());
			}
		} else {

			Log.e("selectListaEmpresa", "Error Conexión Base de Datos");
		}

		sql = null;
		
		empresaa = null;
		longitud = null; 
		latitud = null;
		cursor = null;
		database = null;
		return arrayEmpresa;
	}

	
	public boolean actualizarEmpresa(Empresa empresa) throws SQLiteException {
	boolean ban = false;

	ContentValues cv = new ContentValues();
	cv.put("EMPRESA", empresa.getEMPRESA());
	cv.put("LONGITUD", empresa.getLONGITUD());
	cv.put("LATITUD", empresa.getLATIDUD());
	cv.put("LOGO", empresa.getLOGO());


	SQLiteDatabase database = this.getWritableDatabase();

	database.update("EMPRESA", cv,
			"ID_EMPRESA" + "=" + empresa.getID_EMPRESA(), null);
	return true;
}

	
	
	public boolean eliminarEmpresa(int id) {

				boolean res = false;
				SQLiteDatabase database = getSqLiteDatabase("eliminarEmpresa");
				String sql = "DELETE FROM EMPRESA WHERE ID_EMPRESA = " + id;

				if (database != null && database.isOpen()) {

					try {

						database.execSQL(sql);
						res = true;
		
					} catch (Exception e) {
		
						res = false;
						Log.e("eliminarEmpresa", e.toString());
					}
		
				} else {
		
					res = false;
					Log.e("eliminarEmpresa", "Error Conexión Base de Datos");
				}
		
				database = null;
				sql = null;
				return res;
			}
		
	
	
	
	
	
	
	
	
	
	/**
	 * Se usa ContentVales para la utilización de byte[] blob
	 */

	public boolean insertPromo(Promo promo) throws SQLiteException {
		boolean ban = false;

		ContentValues cv = new ContentValues();
		cv.put("TITULO", promo.getTITULO());
		cv.put("DESCRIPCION", promo.getDESCRIPCION());
		cv.put("EMPRESA", promo.getEMPRESA());
		cv.put("LOGO", promo.getLOGO());
		cv.put("FECHA_INICIO", promo.getFECHA_INICIO());
		cv.put("FECHA_FIN", promo.getFECHA_FIN());
		cv.put("USUARIO", promo.getUSUARIO());
		cv.put("FECHA_CREACION", promo.getFECHA_CREACION());

		SQLiteDatabase database = this.getWritableDatabase();

		database.insert("PROMO", null, cv);
		return true;
	}

	/**
	 * 
	 * Metodo que obtiene lista de equipos adeful.
	 */
	public ArrayList<Promo> selectListaPromo() {

		String sql = "SELECT * FROM PROMO";
		ArrayList<Promo> arrayPromo = new ArrayList<Promo>();
		String titulo = null, descripcion = null, empresa = null, fechainicio = null, fechafin = null, usuario = null, fechacreador = null;
		byte[] logo = null;
		int id;
		Cursor cursor = null;
		// Integer isFueraFrecuencia;
		SQLiteDatabase database = null;

		database = getSqLiteDatabase("selectListaPromo");
		if (database != null && database.isOpen()) {

			try {
				cursor = database.rawQuery(sql, null);
				if (cursor != null && cursor.getCount() > 0) {

					while (cursor.moveToNext()) {

						Promo promo = null;
						id = cursor.getInt(cursor.getColumnIndex("ID_PROMO"));
						titulo = cursor.getString(cursor
								.getColumnIndex("TITULO"));

						descripcion = cursor.getString(cursor
								.getColumnIndex("DESCRIPCION"));

						empresa = cursor.getString(cursor
								.getColumnIndex("EMPRESA"));

						logo = cursor.getBlob(cursor.getColumnIndex("LOGO"));

						fechainicio = cursor.getString(cursor
								.getColumnIndex("FECHA_INICIO"));
						fechafin = cursor.getString(cursor
								.getColumnIndex("FECHA_FIN"));
						usuario = cursor.getString(cursor
								.getColumnIndex("USUARIO"));
						fechacreador = cursor.getString(cursor
								.getColumnIndex("FECHA_CREACION"));

						promo = new Promo(id, titulo, descripcion, empresa,
								logo, fechainicio, fechafin, usuario,
								fechacreador);

						arrayPromo.add(promo);

					}
				}

			} catch (Exception e) {
				Log.e("selectListaPromo", e.toString());
			}
		} else {

			Log.e("selectListaPromo", "Error Conexión Base de Datos");
		}

		sql = null;
		
		titulo = null;
		descripcion = null; 
		empresa = null;
		fechainicio = null; 
		fechafin = null;
		usuario = null;
		fechacreador = null;
		cursor = null;
		database = null;
		return arrayPromo;
	}

//	public boolean actualizarEquipoAdeful(Equipo equipo) throws SQLiteException {
//		boolean ban = false;
//
//		ContentValues cv = new ContentValues();
//		cv.put("NOMBRE", equipo.getNOMBRE_EQUIPO());
//		cv.put("ESCUDO", equipo.getESCUDO());
//		cv.put("USUARIO_CREADOR", equipo.getNOMBRE_USUARIO());
//		cv.put("FECHA_ACTUALIZACION", equipo.getFECHA_ACTUALIZACION());
//		cv.put("ESTADO", equipo.getESTADO());
//		cv.put("TABLA", equipo.getTABLA());
//
//		SQLiteDatabase database = this.getWritableDatabase();
//
//		database.update("EQUIPO_ADEFUL", cv,
//				"ID_EQUIPO" + "=" + equipo.getID_EQUIPO(), null);
//		return true;
//	}
//
//	public boolean eliminarEquipoAdeful(int id) {
//
//		boolean res = false;
//		SQLiteDatabase database = getSqLiteDatabase("eliminarDivision");
//		String sql = "DELETE FROM EQUIPO_ADEFUL WHERE ID_EQUIPO = " + id;
//
//		if (database != null && database.isOpen()) {
//
//			try {
//
//				database.execSQL(sql);
//				res = true;
//
//			} catch (Exception e) {
//
//				res = false;
//				Log.e("eliminarEquipoAdeful", e.toString());
//			}
//
//		} else {
//
//			res = false;
//			Log.e("eliminarEquipoAdeful", "Error Conexión Base de Datos");
//		}
//
//		database = null;
//		sql = null;
//		return res;
//	}
//
//	/**
//	 * Metodo que inserta los datos de una division en la base de datos.
//	 * 
//	 */
//
//	public boolean insertDivisionAdeful(Division division) {
//		boolean ban = false;
//
//		String sql = "INSERT INTO DIVISION_ADEFUL ( DESCRIPCION, USUARIO_CREADOR, FECHA_CREACION, FECHA_ACTUALIZACION, ESTADO, TABLA ) VALUES ('"
//				+ division.getDESCRIPCION()
//				+ "', '"
//				+ division.getNOMBRE_USUARIO()
//				+ "', '"
//				+ division.getFECHA_CREACION()
//				+ "', '"
//				+ division.getFECHA_ACTUALIZACION()
//				+ "', '"
//				+ division.getESTADO() + "', '" + division.getTABLA() + "')";
//
//		SQLiteDatabase database = getSqLiteDatabase("insertDivision");
//		if (database != null && database.isOpen()) {
//			try {
//
//				database.execSQL(sql);
//				ban = true;
//
//			} catch (Exception e) {
//
//				Log.e("insertDivision", e.toString());
//				ban = false;
//			}
//		} else {
//			Log.e("insertDivision", "Error Conexión Base de Datos");
//			ban = false;
//		}
//
//		sql = null;
//		database = null;
//		return ban;
//	}
//
//	/**
//	 * 
//	 * Metodo que obtiene lista de division adeful.
//	 */
//	public ArrayList<Division> selectListaDivisionAdeful() {
//
//		String sql = "SELECT * FROM DIVISION_ADEFUL";
//		ArrayList<Division> arrayDivision = new ArrayList<Division>();
//		String descripcion = null, usuario = null, fechaCreacion = null, fechaActualizacion = null, estado = null, tabla = null;
//		int id;
//		Cursor cursor = null;
//		SQLiteDatabase database = null;
//
//		database = getSqLiteDatabase("selectListaDivisionAdeful");
//		if (database != null && database.isOpen()) {
//
//			try {
//				cursor = database.rawQuery(sql, null);
//				if (cursor != null && cursor.getCount() > 0) {
//
//					while (cursor.moveToNext()) {
//
//						Division division = null;
//						id = cursor
//								.getInt(cursor.getColumnIndex("ID_DIVISION"));
//						descripcion = cursor.getString(cursor
//								.getColumnIndex("DESCRIPCION"));
//
//						usuario = cursor.getString(cursor
//								.getColumnIndex("USUARIO_CREADOR"));
//						fechaCreacion = cursor.getString(cursor
//								.getColumnIndex("FECHA_CREACION"));
//						fechaActualizacion = cursor.getString(cursor
//								.getColumnIndex("FECHA_ACTUALIZACION"));
//						estado = cursor.getString(cursor
//								.getColumnIndex("ESTADO"));
//						tabla = cursor
//								.getString(cursor.getColumnIndex("TABLA"));
//
//						division = new Division(id, descripcion, usuario,
//								fechaCreacion, fechaActualizacion, estado,
//								tabla);
//
//						arrayDivision.add(division);
//
//					}
//				}
//
//			} catch (Exception e) {
//				Log.e("selectListaDivisionAdeful", e.toString());
//			}
//		} else {
//
//			Log.e("selectListaDivisionAdeful", "Error Conexión Base de Datos");
//		}
//
//		sql = null;
//		cursor = null;
//		database = null;
//		descripcion = null;
//		usuario = null;
//		fechaCreacion = null;
//		fechaActualizacion = null;
//		estado = null;
//		tabla = null;
//
//		return arrayDivision;
//	}
//
//	public boolean actualizarDivisionAdeful(Division division) {
//
//		boolean res = false;
//		SQLiteDatabase database = getSqLiteDatabase("actualizarDivision");
//		String sql = "UPDATE DIVISION_ADEFUL SET DESCRIPCION='"
//				+ division.getDESCRIPCION() + "', USUARIO_CREADOR='"
//				+ division.getNOMBRE_USUARIO() + "', FECHA_ACTUALIZACION='"
//				+ division.getFECHA_ACTUALIZACION() + "', ESTADO='"
//				+ division.getESTADO() + "', TABLA = '" + division.getTABLA()
//				+ "' WHERE ID_DIVISION ='" + division.getID_DIVISION() + "'";
//
//		if (database != null && database.isOpen()) {
//
//			try {
//
//				database.execSQL(sql);
//				res = true;
//
//			} catch (Exception e) {
//
//				res = false;
//				Log.e("actualizarDivision", e.toString());
//			}
//
//		} else {
//
//			res = false;
//			Log.e("actualizarDivision", "Error Conexión Base de Datos");
//		}
//
//		database = null;
//		sql = null;
//		return res;
//	}
//
//	public boolean eliminarDivisionAdeful(int id) {
//
//		boolean res = false;
//		SQLiteDatabase database = getSqLiteDatabase("eliminarDivision");
//		String sql = "DELETE FROM DIVISION_ADEFUL WHERE ID_DIVISION = " + id;
//
//		if (database != null && database.isOpen()) {
//
//			try {
//
//				database.execSQL(sql);
//				res = true;
//
//			} catch (Exception e) {
//
//				res = false;
//				Log.e("eliminarDivision", e.toString());
//			}
//
//		} else {
//
//			res = false;
//			Log.e("eliminarDivision", "Error Conexión Base de Datos");
//		}
//
//		database = null;
//		sql = null;
//		return res;
//	}

}