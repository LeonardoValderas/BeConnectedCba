package com.beconnected.databases;

import java.util.ArrayList;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.util.Log;

public class ControladorAdm {

 private SqliteConnectionAdm sqliteConnectionAdm;
 private Context ourcontext;
 private SQLiteDatabase database;

 public ControladorAdm(Context c) {
        ourcontext = c;
 }

 public ControladorAdm abrirBaseDeDatos() throws SQLException {
	 sqliteConnectionAdm = new SqliteConnectionAdm(ourcontext,"BaseDeDatosDAdm",null,1);
  database = sqliteConnectionAdm.getWritableDatabase();
  return this;
 }

 public void cerrarBaseDeDatos() {
	 sqliteConnectionAdm.close();
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

	
		database.insert("EMPRESA", null, cv);
		return true;
	}
 
 public ArrayList<Empresa> selectListaEmpresa() {

		String sql = "SELECT * FROM EMPRESA ORDER BY ID_EMPRESA DESC";
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
 
 

	public boolean actualizarEmpresa(Empresa empresa) throws SQLiteException {
		boolean ban = false;

		ContentValues cv = new ContentValues();
		cv.put("EMPRESA", empresa.getEMPRESA());
		cv.put("LONGITUD", empresa.getLONGITUD());
		cv.put("LATITUD", empresa.getLATIDUD());
		cv.put("LOGO", empresa.getLOGO());

	   database.update("EMPRESA", cv,
				"ID_EMPRESA" + "=" + empresa.getID_EMPRESA(), null);
		return true;
	}

	
	public boolean eliminarEmpresa(int id) {

		boolean res = false;
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
	
	
//////////////PROMO/////////////
	public boolean insertPromo(int id, Promo promo) throws SQLiteException {
		boolean ban = false;

		ContentValues cv = new ContentValues();
		cv.put("ID_PROMO", id);
		cv.put("TITULO", promo.getTITULO());
		cv.put("DESCRIPCION", promo.getDESCRIPCION());
		cv.put("ID_EMPRESA", promo.getID_EMPRESA());
		cv.put("FECHA_INICIO", promo.getFECHA_INICIO());
		cv.put("FECHA_FIN", promo.getFECHA_FIN());

		
		database.insert("PROMO", null, cv);
		return true;
	}
	
	
	
	public boolean insertPromoSplash(Promo promo) throws SQLiteException {
		boolean ban = false;

		ContentValues cv = new ContentValues();
		cv.put("ID_PROMO", promo.getID_PROMO());
		cv.put("TITULO", promo.getTITULO());
		cv.put("DESCRIPCION", promo.getDESCRIPCION());
		cv.put("ID_EMPRESA", promo.getID_EMPRESA());
		cv.put("FECHA_INICIO", promo.getFECHA_INICIO());
		cv.put("FECHA_FIN", promo.getFECHA_FIN());

		

		database.insert("PROMO", null, cv);
		return true;
	}
	
 
	public ArrayList<Promo> selectListaPromo() {

		String sql = "SELECT  PROMO.ID_PROMO, PROMO.TITULO, PROMO.DESCRIPCION, PROMO.ID_EMPRESA, EMPRESA.LOGO, PROMO.FECHA_INICIO, PROMO.FECHA_FIN FROM PROMO, EMPRESA WHERE PROMO.ID_EMPRESA=EMPRESA.ID_EMPRESA ORDER BY PROMO.ID_PROMO DESC";
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
	
	public boolean actualizarPromo(Promo promo) throws SQLiteException {
		boolean ban = false;

		ContentValues cv = new ContentValues();
		cv.put("TITULO", promo.getTITULO());
		cv.put("DESCRIPCION", promo.getDESCRIPCION());
		cv.put("ID_EMPRESA", promo.getID_EMPRESA());
		cv.put("FECHA_INICIO", promo.getFECHA_INICIO());
		cv.put("FECHA_FIN", promo.getFECHA_FIN());
		cv.put("FECHA_INICIO", promo.getFECHA_INICIO());
		
	   database.update("PROMO", cv,
				"ID_PROMO" + "=" + promo.getID_PROMO(), null);
		return true;
	}
	
	
//	public boolean actualizarPromo(Promo promo) {
//
//		boolean res = false;
//		
//		String sql = "UPDATE PROMO SET TITULO='" + promo.getTITULO()
//				+ "', DESCRIPCION='" + promo.getDESCRIPCION()
//				+ "', ID_EMPRESA='" + promo.getID_EMPRESA()
//				+ "', FECHA_INICIO='" + promo.getFECHA_INICIO()
//				+ "', FECHA_FIN = '" + promo.getFECHA_FIN()
//				+ "' WHERE ID_PROMO ='" + promo.getID_PROMO() + "'";
//		sql = sql.replace("'", "''");
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
//				Log.e("actualizarPromo", e.toString());
//			}
//
//		} else {
//
//			res = false;
//			Log.e("actualizarPromo", "Error Conexión Base de Datos");
//		}
//
//		database = null;
//		sql = null;
//		return res;
//	}

	public boolean eliminarPromo(int id) {

		boolean res = false;
	
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

	

		database.insert("INFO", null, cv);
		return true;
	}

	
		public ArrayList<Info> selectListaInfo() {

			String sql = "SELECT * FROM INFO";
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

	 
	 
	 
	 public void dropTablasBDAdm() {

			
			String sql1 = "DELETE FROM  EMPRESA";
			String sql2 = "DELETE FROM  PROMO";
			//String sql3 = "DELETE FROM  INFO";

		
			if (database != null && database.isOpen()) {

				try {

					database.execSQL(sql1);
					database.execSQL(sql2);
					//database.execSQL(sql3);
					

				} catch (Exception e) {

					Log.e("dropTablasBDAdm", e.toString());
				}
			} else {

				Log.e("dropTablasBDUsuario", "Error Conexión Base de Datos");
			}

			sql1 = null;
			sql2 = null;
		//	sql3 = null;
			database = null;
		}

	 /**
		 * Toma id de GMC para poder actualizar el regID del dispositivo.
		 */

		public boolean insertIdAdm(int id) throws SQLiteException {
			boolean ban = false;

			ContentValues cv = new ContentValues();
			cv.put("ID", id);
		
			database.insert("ID_ADM", null, cv);
			return true;
		}
		
		public int selectIdAdm() {

			String sql = "SELECT  * FROM ID_ADM";
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
					Log.e("selectIdAdm", e.toString());
				}
			} else {

				Log.e("selectIdAdm", "Error Conexión Base de Datos");
			}

			cursor = null;
			database = null;
			return id;
		}

}