package com.beconnected.databases;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import org.json.JSONException;
import org.json.JSONObject;
import android.util.Log;

public class ConnectionManager {

	public static String URL = "http://beconnected.esy.es/BeConnected/";
	public static String URLNOTI = "http://beconnected.esy.es/BeConnected/Notification/";

	/**
	 * metodo que trae datos
	 * 
	 * @return
	 */

	static JSONObject jObj = null;
	static String json = "";

	public static String truncateTable() {

		String uri = URL + "truncateTablas.php";
		BufferedReader reader = null;

		try {
			URL url = new URL(uri);
			HttpURLConnection con = (HttpURLConnection) url.openConnection();

			con.setDoOutput(true);

			StringBuilder sb = new StringBuilder();
			reader = new BufferedReader(new InputStreamReader(
					con.getInputStream()));

			String line;
			while ((line = reader.readLine()) != null) {

				sb.append(line + "\n");
			}
			return sb.toString();

		} catch (Exception e) {
			e.printStackTrace();
			return null;

		} finally {
			if (reader != null) {
				try {
					reader.close();

				} catch (IOException e) {
					e.printStackTrace();
					return null;
				}
			}
		}
	}

	// trae empresa splash

	public static String traerEmpresa() {
		String uri = URL + "traerEmpresa.php";

		BufferedReader reader = null;

		try {
			URL url = new URL(uri);
			HttpURLConnection con = (HttpURLConnection) url.openConnection();

			StringBuilder sb = new StringBuilder();
			reader = new BufferedReader(new InputStreamReader(
					con.getInputStream()));

			String line;
			while ((line = reader.readLine()) != null) {

				sb.append(line + "\n");
			}
			return sb.toString();

		} catch (Exception e) {
			e.printStackTrace();
			return null;

		} finally {
			if (reader != null) {
				try {
					reader.close();

				} catch (IOException e) {
					e.printStackTrace();
					return null;
				}
			}
		}
	}

	// trae promo splash
	public static String traerPromo() {
		String uri = URL + "traerPromo.php";

		BufferedReader reader = null;

		try {
			URL url = new URL(uri);
			HttpURLConnection con = (HttpURLConnection) url.openConnection();

			StringBuilder sb = new StringBuilder();
			reader = new BufferedReader(new InputStreamReader(
					con.getInputStream()));

			String line;
			while ((line = reader.readLine()) != null) {

				sb.append(line + "\n");
			}
			return sb.toString();

		} catch (Exception e) {
			e.printStackTrace();
			return null;

		} finally {
			if (reader != null) {
				try {
					reader.close();

				} catch (IOException e) {
					e.printStackTrace();
					return null;
				}
			}
		}
	}

	// trae info splash
	public static String traerInfo() {
		String uri = URL + "traerInfo.php";

		BufferedReader reader = null;

		try {
			URL url = new URL(uri);
			HttpURLConnection con = (HttpURLConnection) url.openConnection();

			StringBuilder sb = new StringBuilder();
			reader = new BufferedReader(new InputStreamReader(
					con.getInputStream()));

			String line;
			while ((line = reader.readLine()) != null) {

				sb.append(line + "\n");
			}
			return sb.toString();

		} catch (Exception e) {
			e.printStackTrace();
			return null;

		} finally {
			if (reader != null) {
				try {
					reader.close();

				} catch (IOException e) {
					e.printStackTrace();
					return null;
				}
			}
		}
	}

	// enviar/editar/eliminar

	public static JSONObject gestionPromo(Request p) {

		String uri = null;
		if (p.getQuery().equals("SUBIR")) {
			uri = URL + "agregarPromo.php";
		} else if (p.getQuery().equals("EDITAR")) {
			uri = URL + "editarPromo.php";
		} else if (p.getQuery().equals("ELIMINAR")) {
			uri = URL + "eliminarPromo.php";
		}

		BufferedReader reader = null;
		// String uri = p.getUri();

		if (p.getMethod().equals("GET")) {
			uri += "?" + p.getEncodedParams();
		}

		try {
			URL url = new URL(uri);
			HttpURLConnection con = (HttpURLConnection) url.openConnection();
			con.setRequestMethod(p.getMethod());

			// JSONObject json = new JSONObject(p.getParametros());
			// String parames = "paramentros=" + json.toString();

			if (p.getMethod().equals("POST")) {

				con.setDoOutput(true);
				OutputStreamWriter write = new OutputStreamWriter(
						con.getOutputStream());

				// write.write(parames);
				write.write(p.getEncodedParams());
				write.flush();

			}
			StringBuilder sb = new StringBuilder();
			reader = new BufferedReader(new InputStreamReader(
					con.getInputStream(),"UTF-8"),8);
			
			String line;
			while ((line = reader.readLine()) != null) {

				sb.append(line + "\n");
			}
			json = sb.toString();

		} catch (Exception e) {
			e.printStackTrace();
			return null;

		}

		try {
			jObj = new JSONObject(json);
		} catch (JSONException e) {
			Log.e("JSON Parser", "Error parsing data " + e.toString());
		}

		// return JSON String
		return jObj;

	}

	// subir/editar/eliminar una empresa
	public static JSONObject gestionEmpresa(Request p) {
		String uri = null;

		if (p.getQuery().equals("SUBIR")) {
			uri = URL + "agregarEmpresa.php";
		} else if (p.getQuery().equals("EDITAR")) {
			uri = URL + "editarEmpresa.php";
		} else if (p.getQuery().equals("ELIMINAR")) {
			uri = URL + "eliminarEmpresa.php";
		}

		BufferedReader reader = null;
		// String uri = p.getUri();

		if (p.getMethod().equals("GET")) {
			uri += "?" + p.getEncodedParams();
		}

		try {
			URL url = new URL(uri);
			HttpURLConnection con = (HttpURLConnection) url.openConnection();
			con.setRequestMethod(p.getMethod());

			// JSONObject json = new JSONObject(p.getParametros());
			// String parames = "paramentros=" + json.toString();

			if (p.getMethod().equals("POST")) {

				con.setDoOutput(true);
				OutputStreamWriter write = new OutputStreamWriter(
						con.getOutputStream());

				// write.write(parames);
				write.write(p.getEncodedParams());
				write.flush();

			}
			StringBuilder sb = new StringBuilder();
			reader = new BufferedReader(new InputStreamReader(
					con.getInputStream()));

			String line;
			while ((line = reader.readLine()) != null) {

				sb.append(line + "\n");
			}
			json = sb.toString();

		} catch (Exception e) {
			e.printStackTrace();
			return null;

		}

		try {
			jObj = new JSONObject(json);
		} catch (JSONException e) {
			
			Log.e("JSON Parser", "Error parsing data " + e.toString());
		}

		// return JSON String
		return jObj;

		// finally{
		// if(reader!=null){
		// try {
		// reader.close();
		//
		// } catch (IOException e) {
		// e.printStackTrace();
		// return null;
		// }
		// }
		// }
	}

	// editar una Info
	public static JSONObject gestionInfo(Request p) {
		String uri = null;

		uri = URL + "editarInfo.php";

		BufferedReader reader = null;

		if (p.getMethod().equals("GET")) {
			uri += "?" + p.getEncodedParams();
		}

		try {
			URL url = new URL(uri);
			HttpURLConnection con = (HttpURLConnection) url.openConnection();
			con.setRequestMethod(p.getMethod());

			// JSONObject json = new JSONObject(p.getParametros());
			// String parames = "paramentros=" + json.toString();

			if (p.getMethod().equals("POST")) {

				con.setDoOutput(true);
				OutputStreamWriter write = new OutputStreamWriter(
						con.getOutputStream());

				// write.write(parames);
				write.write(p.getEncodedParams());
				write.flush();

			}
			StringBuilder sb = new StringBuilder();
			reader = new BufferedReader(new InputStreamReader(
					con.getInputStream()));

			String line;
			while ((line = reader.readLine()) != null) {

				sb.append(line + "\n");
			}
			json = sb.toString();

		} catch (Exception e) {
			e.printStackTrace();
			return null;

		}

		try {
			jObj = new JSONObject(json);
		} catch (JSONException e) {
			Log.e("JSON Parser", "Error parsing data " + e.toString());
		}

		// return JSON String
		return jObj;

	}

	// subir/editar/eliminar una empresa
	public static JSONObject gestionGMC(Request p) {
		String uri = null;

		if (p.getQuery().equals("USUARIO")) {
			uri = URLNOTI + "register.php";
		} else if (p.getQuery().equals("ADM")) {
			uri = URLNOTI + "registerAdm.php";
		}
		// }else if(p.getQuery().equals("ELIMINAR")){
		// uri =URL+"eliminarEmpresa.php";
		// // }

		BufferedReader reader = null;
		// String uri = p.getUri();

		if (p.getMethod().equals("GET")) {
			uri += "?" + p.getEncodedParams();
		}

		try {
			URL url = new URL(uri);
			HttpURLConnection con = (HttpURLConnection) url.openConnection();
			con.setRequestMethod(p.getMethod());

			// JSONObject json = new JSONObject(p.getParametros());
			// String parames = "paramentros=" + json.toString();

			if (p.getMethod().equals("POST")) {

				con.setDoOutput(true);
				OutputStreamWriter write = new OutputStreamWriter(
						con.getOutputStream());

				// write.write(parames);
				write.write(p.getEncodedParams());
				write.flush();

			}
			StringBuilder sb = new StringBuilder();
			reader = new BufferedReader(new InputStreamReader(
					con.getInputStream()));

			String line;
			while ((line = reader.readLine()) != null) {

				sb.append(line + "\n");
			}
			json = sb.toString();

		} catch (Exception e) {
			e.printStackTrace();
			return null;

		}

		try {
			jObj = new JSONObject(json);
		} catch (JSONException e) {
			Log.e("JSON Parser", "Error parsing data " + e.toString());
		}

		// return JSON String
		return jObj;

	}

	public void push(String mensaje) {
		String uri = null;
		Request p = new Request();
		p.setParametrosDatos("mensaje", mensaje);
		p.setMethod("POST");
		// if(p.getQuery().equals("SUBIR")){
		uri = URLNOTI + "pushEnviar.php";
		// }else if(p.getQuery().equals("EDITAR")){
		// uri =URL+"editarEmpresa.php";
		// }else if(p.getQuery().equals("ELIMINAR")){
		// uri =URL+"eliminarEmpresa.php";
		// // }

		BufferedReader reader = null;
		// String uri = p.getUri();

		try {
			URL url = new URL(uri);
			HttpURLConnection con = (HttpURLConnection) url.openConnection();
			con.setRequestMethod(p.getMethod());

			// JSONObject json = new JSONObject(p.getParametros());
			// String parames = "paramentros=" + json.toString();

			con.setDoOutput(true);
			OutputStreamWriter write = new OutputStreamWriter(
					con.getOutputStream());

			// write.write(parames);
			write.write(p.getEncodedParams());
			write.flush();

			StringBuilder sb = new StringBuilder();
			reader = new BufferedReader(new InputStreamReader(
					con.getInputStream()));

			String line;
			while ((line = reader.readLine()) != null) {

				sb.append(line + "\n");
			}
			json = sb.toString();

		} catch (Exception e) {
			e.printStackTrace();
			// return null;

		}

		try {
			jObj = new JSONObject(json);
		} catch (JSONException e) {
			Log.e("JSON Parser", "Error parsing data " + e.toString());
		}

		// return JSON String
		// return jObj;

	}
}
