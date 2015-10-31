package com.beconnected.databases;

import java.util.ArrayList;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.util.Log;

public class ControladorUsuario {

 private SqliteConnectionUsuario sqliteConnectionUsuario;
 private Context ourcontext;
 private SQLiteDatabase database;

 public ControladorUsuario(Context c) {
        ourcontext = c;
 }

 public ControladorUsuario abrirBaseDeDatos() throws SQLException {
	 sqliteConnectionUsuario = new SqliteConnectionUsuario(ourcontext,"BaseDeDatosUsuario",null,1);
  database = sqliteConnectionUsuario.getWritableDatabase();
  return this;
 }

 public void cerrarBaseDeDatos() {
	 sqliteConnectionUsuario.close();
 }

 
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

		

		database.insert("EMPRESA", null, cv);
		return true;
	}

 

 
 
 public boolean insertEmpresaSplash(Empresa empresa) throws SQLiteException {
		boolean ban = false;

		ContentValues cv = new ContentValues();
		cv.put("ID_EMPRESA", empresa.getID_EMPRESA());
		cv.put("EMPRESA", empresa.getEMPRESA());
		cv.put("LONGITUD", empresa.getLONGITUD());
		cv.put("LATITUD", empresa.getLATIDUD());
		cv.put("LOGO", empresa.getLOGO());
		cv.put("URL_LOGO", empresa.getURL_LOGO());

	
		database.insert("EMPRESA_U", null, cv);
		return true;
	}
 
 public ArrayList<Empresa> selectListaEmpresa() {

		String sql = "SELECT * FROM EMPRESA_U";
		ArrayList<Empresa> arrayEmpresa = new ArrayList<Empresa>();
		String empresaa = null, longitud = null, latitud = null, url_logo = null;
		byte[] logo = null;
		int id;
		Cursor cursor = null;
		// Integer isFueraFrecuencia;
	
		
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
 
 

	
	
	
//////////////PROMO/////////////
	
	public boolean insertPromoUsuario(Promo promo) throws SQLiteException {
		boolean ban = false;

		ContentValues cv = new ContentValues();
		cv.put("ID_PROMO", promo.getID_PROMO());
		cv.put("TITULO", promo.getTITULO());
		cv.put("DESCRIPCION", promo.getDESCRIPCION());
		cv.put("ID_EMPRESA", promo.getID_EMPRESA());
		cv.put("FECHA_INICIO", promo.getFECHA_INICIO());
		cv.put("FECHA_FIN", promo.getFECHA_FIN());

		database.insert("PROMO_U", null, cv);
		return true;
	}
	
	
	
 
	public ArrayList<Promo> selectListaPromo() {

		String sql = "SELECT  PROMO_U.ID_PROMO, PROMO_U.TITULO, PROMO_U.DESCRIPCION, PROMO_U.ID_EMPRESA, EMPRESA_U.LOGO, PROMO_U.FECHA_INICIO, PROMO_U.FECHA_FIN FROM PROMO_U, EMPRESA_U WHERE PROMO_U.ID_EMPRESA=EMPRESA_U.ID_EMPRESA ORDER BY PROMO_U.ID_PROMO DESC";
		ArrayList<Promo> arrayPromo = new ArrayList<Promo>();
		String titulo = null, descripcion = null, fechainicio = null, fechafin = null, usuario = null, fechacreador = null;
		byte[] logo = null;
		int id;
		int empresa;
		Cursor cursor = null;
	
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
	
	
	public boolean insertInfo() throws SQLiteException {
		boolean ban = false;

		ContentValues cv = new ContentValues();
		cv.put("SOMOS", "Quienes somos...");
		cv.put("CONTACTO", "Contactos...");

	

		database.insert("INFO_U", null, cv);
		return true;
	}

	
		public ArrayList<Info> selectListaInfo() {

			String sql = "SELECT  * FROM INFO_U";
			ArrayList<Info> arrayInfo = new ArrayList<Info>();
			String somos = null, contacto = null;
			Cursor cursor = null;

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
	 public boolean actualizarInfo(Info info) {

			boolean res = false;
		
			String sql = "UPDATE INFO_U SET SOMOS='" + info.getSOMOS()
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

	 
	 public void dropTablasBDUsuario() {

			
			String sql1 = "DELETE FROM  EMPRESA_U";
			String sql2 = "DELETE FROM  PROMO_U";
			String sql3 = "DELETE FROM  INFO_U";

		
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

		public boolean insertIdUsuario(int id) throws SQLiteException {
			boolean ban = false;

			ContentValues cv = new ContentValues();
			cv.put("ID", id);
		
			database.insert("ID", null, cv);
			return true;
		}

		public int selectIdUsuario() {

			String sql = "SELECT  * FROM ID";
			int id = 0;

			Cursor cursor = null;
		
					
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