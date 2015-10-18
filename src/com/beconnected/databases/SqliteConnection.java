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
	 * Crea las tablas del adm
	 * 
	 */
	public void createTablesBDAdm() {

		String TABLA_EMPRESA = "CREATE TABLE IF NOT EXISTS EMPRESA (ID_EMPRESA INTEGER,"
				+ " EMPRESA VARCHAR(100),"
				+ " LONGITUD VARCHAR(100),"
				+ " LATITUD VARCHAR(100),"
				+ " URL_LOGO VARCHAR(200),"
				+ " LOGO BLOB);";

		String TABLA_PROMO = "CREATE TABLE IF NOT EXISTS PROMO (ID_PROMO INTEGER,"
				+ " TITULO VARCHAR(100),"
				+ " DESCRIPCION VARCHAR(100),"
				+ " ID_EMPRESA INTEGER,"
				+ " FECHA_INICIO VARCHAR(100),"
				+ " FECHA_FIN VARCHAR(100));";

		String TABLA_INFO = "CREATE TABLE IF NOT EXISTS INFO (SOMOS VARCHAR(200),"
				+ " CONTACTO VARCHAR(200));";

		String TABLA_ID = "CREATE TABLE IF NOT EXISTS ID_ADM (ID INTEGER);";

		SQLiteDatabase database = getSqLiteDatabase("createTables");
		if (database != null && database.isOpen()) {

			try {

				database.execSQL(TABLA_PROMO);
				database.execSQL(TABLA_EMPRESA);
				database.execSQL(TABLA_INFO);
				database.execSQL(TABLA_ID);

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
		TABLA_INFO = null;
		TABLA_ID = null;
	}

	/**
	 * crea las tablas del usuario
	 */

	public void createTablesBDUsuario() {

		String TABLA_EMPRESA_U = "CREATE TABLE IF NOT EXISTS EMPRESA_U (ID_EMPRESA INTEGER,"
				+ " EMPRESA VARCHAR(100),"
				+ " LONGITUD VARCHAR(100),"
				+ " LATITUD VARCHAR(100),"
				+ " URL_LOGO VARCHAR(200),"
				+ " LOGO BLOB);";

		String TABLA_PROMO_U = "CREATE TABLE IF NOT EXISTS PROMO_U (ID_PROMO INTEGER,"
				+ " TITULO VARCHAR(100),"
				+ " DESCRIPCION VARCHAR(100),"
				+ " ID_EMPRESA INTEGER,"
				+ " FECHA_INICIO VARCHAR(100),"
				+ " FECHA_FIN VARCHAR(100));";

		String TABLA_INFO_U = "CREATE TABLE IF NOT EXISTS INFO_U (SOMOS VARCHAR(200),"
				+ " CONTACTO VARCHAR(200));";

		String TABLA_ID = "CREATE TABLE IF NOT EXISTS ID (ID INTEGER);";

		SQLiteDatabase database = getSqLiteDatabase("createTablesBDUsuario");
		if (database != null && database.isOpen()) {

			try {

				database.execSQL(TABLA_PROMO_U);
				database.execSQL(TABLA_EMPRESA_U);
				database.execSQL(TABLA_INFO_U);
				database.execSQL(TABLA_ID);
			} catch (Exception e) {
				Log.e("createTablesBDUsuario", e.toString());

			}

		} else {
			Log.e("createTablesBDUsuario", "Error Conexión Base de Datos");
		}

		database = null;

		TABLA_PROMO_U = null;
		TABLA_EMPRESA_U = null;
		TABLA_INFO_U = null;
		TABLA_ID = null;
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
	 * Metodo que elimina todas las tablas de la base de datos del usuario.
	 * 
	 */
	public void dropTablasBDUsuario() {

		SQLiteDatabase database = null;
		String sql1 = "DROP TABLE EMPRESA_U";
		String sql2 = "DROP TABLE PROMO_U";
		String sql3 = "DROP TABLE INFO_U";

		database = getSqLiteDatabase("dropTablasBDUsuario()");
		if (database != null && database.isOpen()) {

			try {

				database.execSQL(sql1);
				database.execSQL(sql2);
				database.execSQL(sql3);

			} catch (Exception e) {

				Log.e("dropTablasBDUsuario", e.toString());
			}
		} else {

			Log.e("dropTablasBDUsuario", "Error Conexión Base de Datos");
		}

		sql1 = null;
		sql2 = null;
		sql3 = null;
		database = null;
	}

	/**
	 * Metodo que elimina todas las tablas de la base de datos del usuario.
	 * 
	 */
	public void dropTablasBDAdm() {

		SQLiteDatabase database = null;
		String sql1 = "DROP TABLE EMPRESA";
		String sql2 = "DROP TABLE PROMO";
		String sql3 = "DROP TABLE INFO";

		database = getSqLiteDatabase("dropTablasBDAdm()");
		if (database != null && database.isOpen()) {

			try {

				database.execSQL(sql1);
				database.execSQL(sql2);
				database.execSQL(sql3);

			} catch (Exception e) {

				Log.e("dropTablasBDAdm", e.toString());
			}
		} else {

			Log.e("dropTablasBDUsuario", "Error Conexión Base de Datos");
		}

		sql1 = null;
		sql2 = null;
		sql3 = null;
		database = null;
	}

	// ///////////////////////////////////////
	// ////////////////////////////////////////
	// /////Metodos tablas///////

	/**
	 * 
	 * Metodo que inserta una empresa Adm.
	 */
	public boolean insertEmpresa(int id, Empresa empresa)
			throws SQLiteException {
		boolean ban = false;

		ContentValues cv = new ContentValues();
		cv.put("ID_EMPRESA", id);
		cv.put("EMPRESA", empresa.getEMPRESA());
		cv.put("LONGITUD", empresa.getLONGITUD());
		cv.put("LATITUD", empresa.getLATIDUD());
		cv.put("LOGO", empresa.getLOGO());
		cv.put("URL_LOGO", empresa.getURL_LOGO());

		SQLiteDatabase database = this.getWritableDatabase();

		database.insert("EMPRESA", null, cv);
		return true;
	}

	
	/**
	 * 
	 * Metodo que inserta una empresa Usuario.
	 */
	public boolean insertEmpresaAdm(Empresa empresa) throws SQLiteException {
		boolean ban = false;

		ContentValues cv = new ContentValues();
		cv.put("ID_EMPRESA", empresa.getID_EMPRESA());
		cv.put("EMPRESA", empresa.getEMPRESA());
		cv.put("LONGITUD", empresa.getLONGITUD());
		cv.put("LATITUD", empresa.getLATIDUD());
		cv.put("LOGO", empresa.getLOGO());
		cv.put("URL_LOGO", empresa.getURL_LOGO());

		SQLiteDatabase database = this.getWritableDatabase();

		database.insert("EMPRESA", null, cv);
		return true;
	}
	
	/**
	 * 
	 * Metodo que inserta una empresa Usuario.
	 */
	public boolean insertEmpresaUsuario(Empresa empresa) throws SQLiteException {
		boolean ban = false;

		ContentValues cv = new ContentValues();
		cv.put("ID_EMPRESA", empresa.getID_EMPRESA());
		cv.put("EMPRESA", empresa.getEMPRESA());
		cv.put("LONGITUD", empresa.getLONGITUD());
		cv.put("LATITUD", empresa.getLATIDUD());
		cv.put("LOGO", empresa.getLOGO());
		cv.put("URL_LOGO", empresa.getURL_LOGO());

		SQLiteDatabase database = this.getWritableDatabase();

		database.insert("EMPRESA_U", null, cv);
		return true;
	}

	/**
	 * 
	 * Metodo que trae lista una empresa adm.
	 */
	public ArrayList<Empresa> selectListaEmpresa() {

		String sql = "SELECT * FROM EMPRESA";
		ArrayList<Empresa> arrayEmpresa = new ArrayList<Empresa>();
		String empresaa = null, longitud = null, latitud = null, url_logo = null;
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

						url_logo = cursor.getString(cursor
								.getColumnIndex("URL_LOGO"));

						empresa = new Empresa(id, empresaa, longitud, latitud,
								logo, url_logo);

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

	/**
	 * 
	 * Metodo que trae lista una empresa usuario.
	 */
	public ArrayList<Empresa> selectListaEmpresaUsuario() {

		String sql = "SELECT * FROM EMPRESA_U";
		ArrayList<Empresa> arrayEmpresa = new ArrayList<Empresa>();
		String empresaa = null, longitud = null, latitud = null, url_logo = null;
		byte[] logo = null;
		int id;
		Cursor cursor = null;
		// Integer isFueraFrecuencia;
		SQLiteDatabase database = null;

		database = getSqLiteDatabase("selectListaEmpresaUsuario");
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

						url_logo = cursor.getString(cursor
								.getColumnIndex("URL_LOGO"));

						empresa = new Empresa(id, empresaa, longitud, latitud,
								logo, url_logo);

						arrayEmpresa.add(empresa);

					}
				}

			} catch (Exception e) {
				Log.e("selectListaEmpresaUsuario", e.toString());
			}
		} else {

			Log.e("selectListaEmpresaUsuario", "Error Conexión Base de Datos");
		}

		sql = null;

		empresaa = null;
		longitud = null;
		latitud = null;
		cursor = null;
		database = null;
		return arrayEmpresa;
	}


	/**
	 * 
	 * Metodo que actualiza una empresa Amd.
	 */

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

	/**
	 * 
	 * Metodo que eliminar una empresa Amd.
	 */

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
	 * Se usa ContentVales para la utilización de byte[] blob inseerta promo
	 */

	public boolean insertPromo(int id, Promo promo) throws SQLiteException {
		boolean ban = false;

		ContentValues cv = new ContentValues();
		cv.put("ID_PROMO", id);
		cv.put("TITULO", promo.getTITULO());
		cv.put("DESCRIPCION", promo.getDESCRIPCION());
		cv.put("ID_EMPRESA", promo.getID_EMPRESA());
		cv.put("FECHA_INICIO", promo.getFECHA_INICIO());
		cv.put("FECHA_FIN", promo.getFECHA_FIN());

		SQLiteDatabase database = this.getWritableDatabase();

		database.insert("PROMO", null, cv);
		return true;
	}
	
	public boolean insertPromoAdm(Promo promo) throws SQLiteException {
		boolean ban = false;

		ContentValues cv = new ContentValues();
		cv.put("ID_PROMO", promo.getID_PROMO());
		cv.put("TITULO", promo.getTITULO());
		cv.put("DESCRIPCION", promo.getDESCRIPCION());
		cv.put("ID_EMPRESA", promo.getID_EMPRESA());
		cv.put("FECHA_INICIO", promo.getFECHA_INICIO());
		cv.put("FECHA_FIN", promo.getFECHA_FIN());

		SQLiteDatabase database = this.getWritableDatabase();

		database.insert("PROMO", null, cv);
		return true;
	}


	public boolean insertPromoUsuario(Promo promo) throws SQLiteException {
		boolean ban = false;

		ContentValues cv = new ContentValues();
		cv.put("ID_PROMO", promo.getID_PROMO());
		cv.put("TITULO", promo.getTITULO());
		cv.put("DESCRIPCION", promo.getDESCRIPCION());
		cv.put("ID_EMPRESA", promo.getID_EMPRESA());
		cv.put("FECHA_INICIO", promo.getFECHA_INICIO());
		cv.put("FECHA_FIN", promo.getFECHA_FIN());

		SQLiteDatabase database = this.getWritableDatabase();

		database.insert("PROMO_U", null, cv);
		return true;
	}

	/**
	 * 
	 * Metodo que obtiene lista de promos Adm.
	 */
	public ArrayList<Promo> selectListaPromo() {

		String sql = "SELECT  PROMO.ID_PROMO, PROMO.TITULO, PROMO.DESCRIPCION, PROMO.ID_EMPRESA, EMPRESA.LOGO, PROMO.FECHA_INICIO, PROMO.FECHA_FIN FROM PROMO, EMPRESA WHERE PROMO.ID_EMPRESA=EMPRESA.ID_EMPRESA ORDER BY PROMO.ID_PROMO DESC";
		ArrayList<Promo> arrayPromo = new ArrayList<Promo>();
		String titulo = null, descripcion = null, fechainicio = null, fechafin = null, usuario = null, fechacreador = null;
		byte[] logo = null;
		int id;
		int empresa;
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

						empresa = cursor.getInt(cursor
								.getColumnIndex("ID_EMPRESA"));

						logo = cursor.getBlob(cursor.getColumnIndex("LOGO"));

						fechainicio = cursor.getString(cursor
								.getColumnIndex("FECHA_INICIO"));
						fechafin = cursor.getString(cursor
								.getColumnIndex("FECHA_FIN"));

						promo = new Promo(id, titulo, descripcion, empresa,
								logo, fechainicio, fechafin);

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
		fechainicio = null;
		fechafin = null;

		cursor = null;
		database = null;
		return arrayPromo;
	}

	/**
	 * 
	 * Metodo que obtiene lista de promos usuario.
	 */
	public ArrayList<Promo> selectListaPromoUsuario() {

		String sql = "SELECT  PROMO_U.ID_PROMO, PROMO_U.TITULO, PROMO_U.DESCRIPCION, PROMO_U.ID_EMPRESA, EMPRESA_U.LOGO, PROMO_U.FECHA_INICIO, PROMO_U.FECHA_FIN FROM PROMO_U, EMPRESA_U WHERE PROMO_U.ID_EMPRESA=EMPRESA_U.ID_EMPRESA ORDER BY PROMO_U.ID_PROMO DESC";
		ArrayList<Promo> arrayPromo = new ArrayList<Promo>();
		String titulo = null, descripcion = null, fechainicio = null, fechafin = null, usuario = null, fechacreador = null;
		byte[] logo = null;
		int id;
		int empresa;
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

						empresa = cursor.getInt(cursor
								.getColumnIndex("ID_EMPRESA"));

						logo = cursor.getBlob(cursor.getColumnIndex("LOGO"));

						fechainicio = cursor.getString(cursor
								.getColumnIndex("FECHA_INICIO"));
						fechafin = cursor.getString(cursor
								.getColumnIndex("FECHA_FIN"));

						promo = new Promo(id, titulo, descripcion, empresa,
								logo, fechainicio, fechafin);

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
		fechainicio = null;
		fechafin = null;

		cursor = null;
		database = null;
		return arrayPromo;
	}

	public boolean actualizarPromo(Promo promo) {

		boolean res = false;
		SQLiteDatabase database = getSqLiteDatabase("actualizarPromo");
		String sql = "UPDATE PROMO SET TITULO='" + promo.getTITULO()
				+ "', DESCRIPCION='" + promo.getDESCRIPCION()
				+ "', ID_EMPRESA='" + promo.getID_EMPRESA()
				+ "', FECHA_INICIO='" + promo.getFECHA_INICIO()
				+ "', FECHA_FIN = '" + promo.getFECHA_FIN()
				+ "' WHERE ID_PROMO ='" + promo.getID_PROMO() + "'";

		if (database != null && database.isOpen()) {

			try {

				database.execSQL(sql);
				res = true;

			} catch (Exception e) {

				res = false;
				Log.e("actualizarPromo", e.toString());
			}

		} else {

			res = false;
			Log.e("actualizarPromo", "Error Conexión Base de Datos");
		}

		database = null;
		sql = null;
		return res;
	}

	public boolean eliminarPromo(int id) {

		boolean res = false;
		SQLiteDatabase database = getSqLiteDatabase("eliminarPromo");
		String sql = "DELETE FROM PROMO WHERE ID_PROMO = " + id;

		if (database != null && database.isOpen()) {

			try {

				database.execSQL(sql);
				res = true;

			} catch (Exception e) {

				res = false;
				Log.e("eliminarPromo", e.toString());
			}

		} else {

			res = false;
			Log.e("eliminarPromo", "Error Conexión Base de Datos");
		}

		database = null;
		sql = null;
		return res;
	}

	public boolean eliminarPromoEmpresa(int id) {

		boolean res = false;
		SQLiteDatabase database = getSqLiteDatabase("eliminarPromoEmpresa");
		String sql = "DELETE FROM PROMO WHERE ID_EMPRESA = " + id;

		if (database != null && database.isOpen()) {

			try {

				database.execSQL(sql);
				res = true;

			} catch (Exception e) {

				res = false;
				Log.e("eliminarPromoEmpresa", e.toString());
			}

		} else {

			res = false;
			Log.e("eliminarPromoEmpresa", "Error Conexión Base de Datos");
		}

		database = null;
		sql = null;
		return res;
	}

	public boolean insertInfo() throws SQLiteException {
		boolean ban = false;

		ContentValues cv = new ContentValues();
		cv.put("SOMOS", "Quienes somos...");
		cv.put("CONTACTO", "Contactos...");

		SQLiteDatabase database = this.getWritableDatabase();

		database.insert("INFO", null, cv);
		return true;
	}

	public boolean insertInfoUsuario() throws SQLiteException {
		boolean ban = false;

		ContentValues cv = new ContentValues();
		cv.put("SOMOS", "Quienes somos...");
		cv.put("CONTACTO", "Contactos...");

		SQLiteDatabase database = this.getWritableDatabase();

		database.insert("INFO_U", null, cv);
		return true;
	}

	/**
	 * 
	 * Metodo que obtiene lista de info admi.
	 */
	public ArrayList<Info> selectListaInfo() {

		String sql = "SELECT  * FROM INFO";
		ArrayList<Info> arrayInfo = new ArrayList<Info>();
		String somos = null, contacto = null;
		Cursor cursor = null;
		// Integer isFueraFrecuencia;
		SQLiteDatabase database = null;

		database = getSqLiteDatabase("selectListaInfo");
		if (database != null && database.isOpen()) {

			try {
				cursor = database.rawQuery(sql, null);
				if (cursor != null && cursor.getCount() > 0) {

					while (cursor.moveToNext()) {

						Info info = null;

						somos = cursor
								.getString(cursor.getColumnIndex("SOMOS"));

						contacto = cursor.getString(cursor
								.getColumnIndex("CONTACTO"));

						info = new Info(somos, contacto);

						arrayInfo.add(info);

					}
				}

			} catch (Exception e) {
				Log.e("selectListaInfo", e.toString());
			}
		} else {

			Log.e("selectListaInfo", "Error Conexión Base de Datos");
		}

		sql = null;
		somos = null;
		contacto = null;
		cursor = null;
		database = null;
		return arrayInfo;
	}

	/**
	 * 
	 * Metodo que obtiene lista de info.
	 */
	public ArrayList<Info> selectListaInfoUsuario() {

		String sql = "SELECT  * FROM INFO_U";
		ArrayList<Info> arrayInfo = new ArrayList<Info>();
		String somos = null, contacto = null;
		Cursor cursor = null;
		// Integer isFueraFrecuencia;
		SQLiteDatabase database = null;

		database = getSqLiteDatabase("selectListaInfoUsuario");
		if (database != null && database.isOpen()) {

			try {
				cursor = database.rawQuery(sql, null);
				if (cursor != null && cursor.getCount() > 0) {

					while (cursor.moveToNext()) {

						Info info = null;

						somos = cursor
								.getString(cursor.getColumnIndex("SOMOS"));

						contacto = cursor.getString(cursor
								.getColumnIndex("CONTACTO"));

						info = new Info(somos, contacto);

						arrayInfo.add(info);

					}
				}

			} catch (Exception e) {
				Log.e("selectListaInfoUsuario", e.toString());
			}
		} else {

			Log.e("selectListaInfoUsuario", "Error Conexión Base de Datos");
		}

		sql = null;
		somos = null;
		contacto = null;
		cursor = null;
		database = null;
		return arrayInfo;
	}

	public boolean actualizarInfo(Info info) {

		boolean res = false;
		SQLiteDatabase database = getSqLiteDatabase("actualizarInfo");
		String sql = "UPDATE INFO SET SOMOS='" + info.getSOMOS()
				+ "', CONTACTO='" + info.getCONTACTO() + "'";

		if (database != null && database.isOpen()) {

			try {

				database.execSQL(sql);
				res = true;

			} catch (Exception e) {

				res = false;
				Log.e("actualizarInfo", e.toString());
			}

		} else {

			res = false;
			Log.e("actualizarInfo", "Error Conexión Base de Datos");
		}

		database = null;
		sql = null;
		return res;
	}

	public boolean actualizarInfoUsuario(Info info) {

		boolean res = false;
		SQLiteDatabase database = getSqLiteDatabase("actualizarInfoUsuario");
		String sql = "UPDATE INFO_U SET SOMOS='" + info.getSOMOS()
				+ "', CONTACTO='" + info.getCONTACTO() + "'";

		if (database != null && database.isOpen()) {

			try {

				database.execSQL(sql);
				res = true;

			} catch (Exception e) {

				res = false;
				Log.e("actualizarInfoUsuario", e.toString());
			}

		} else {

			res = false;
			Log.e("actualizarInfoUsuario", "Error Conexión Base de Datos");
		}

		database = null;
		sql = null;
		return res;
	}

	
/**
 * Toma id de GMC para poder actualizar el regID del dispositivo.
 */
	
	public boolean insertIdAdm(int id) throws SQLiteException {
		boolean ban = false;

		ContentValues cv = new ContentValues();
		cv.put("ID", id);
		SQLiteDatabase database = this.getWritableDatabase();

		database.insert("ID_ADM", null, cv);
		return true;
	}
	
	public boolean insertIdUsuario(int id) throws SQLiteException {
		boolean ban = false;

		ContentValues cv = new ContentValues();
		cv.put("ID", id);
		SQLiteDatabase database = this.getWritableDatabase();

		database.insert("ID", null, cv);
		return true;
	}

	public int selectIdAdm() {

		String sql = "SELECT  * FROM ID_ADM";
		int id = 0;

		Cursor cursor = null;
		// Integer isFueraFrecuencia;
		SQLiteDatabase database = null;

		database = getSqLiteDatabase("selectIdAdm");
		if (database != null && database.isOpen()) {

			try {
				cursor = database.rawQuery(sql, null);
				if (cursor != null && cursor.getCount() > 0) {

					while (cursor.moveToNext()) {

						Info info = null;

						id = cursor.getInt(cursor.getColumnIndex("ID"));

					}
				}

			} catch (Exception e) {
				Log.e("selectIdAdm", e.toString());
			}
		} else {

			Log.e("selectIdAdm", "Error Conexión Base de Datos");
		}

		cursor = null;
		database = null;
		return id;
	}
	
	
	public int selectIdUsuario() {

		String sql = "SELECT  * FROM ID";
		int id = 0;

		Cursor cursor = null;
		// Integer isFueraFrecuencia;
		SQLiteDatabase database = null;

		database = getSqLiteDatabase("selectIdUsuario");
		if (database != null && database.isOpen()) {

			try {
				cursor = database.rawQuery(sql, null);
				if (cursor != null && cursor.getCount() > 0) {

					while (cursor.moveToNext()) {

						Info info = null;

						id = cursor.getInt(cursor.getColumnIndex("ID"));

					}
				}

			} catch (Exception e) {
				Log.e("selectIdUsuario", e.toString());
			}
		} else {

			Log.e("selectIdUsuario", "Error Conexión Base de Datos");
		}

		cursor = null;
		database = null;
		return id;
	}

}