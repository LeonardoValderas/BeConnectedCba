package com.beconnected.databases;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

import com.beconnected.adm.SubirDatos.TaskEmpresa;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Bitmap.CompressFormat;
import android.os.AsyncTask;
import android.widget.Toast;

public class ConnectionManager {

	public static String URL = "http://beconnected.esy.es/BeConnected/";
	/**
	 * metodo que trae datos
	 * @return
	 */
	
	
	
	public static String truncateTable()
	{

		
		
		String uri =URL+"truncateTablas.php";
		BufferedReader reader = null;
		
		try {
			URL url = new URL(uri);
			HttpURLConnection con = (HttpURLConnection)url.openConnection();

			con.setDoOutput(true);

			StringBuilder sb = new StringBuilder();
			reader = new BufferedReader(new InputStreamReader(con.getInputStream()));
			
			String line;
			while((line =reader.readLine())!=null){
				
				sb.append(line+ "\n");
			}
			return sb.toString();
			
		} catch (Exception e) {
		e.printStackTrace();
		return null;
		
		}finally{
			if(reader!=null){
				try {
					reader.close();
					
				} catch (IOException e) {
					e.printStackTrace();
					return null;
				}
			}
		}
	}
	
	
	
	
	//trae empresa splash
	
	
	public static String traerEmpresa()
	{
		String uri = URL+"traerEmpresa.php";
		
		BufferedReader reader = null;
		
		try {
			URL url = new URL(uri);
			HttpURLConnection con = (HttpURLConnection)url.openConnection();
			
			StringBuilder sb = new StringBuilder();
			reader = new BufferedReader(new InputStreamReader(con.getInputStream()));
		
			
			String line;
			while((line =reader.readLine())!=null){
				
				sb.append(line+ "\n");
			}
			return sb.toString();
			
		} catch (Exception e) {
		e.printStackTrace();
		return null;
		
		}finally{
			if(reader!=null){
				try {
					reader.close();
					
				} catch (IOException e) {
					e.printStackTrace();
					return null;
				}
			}
		}
	}
	
	
	
	//trae promo splash
	public static String traerPromo()
	{
		String uri = URL+"traerPromo.php";
		
		BufferedReader reader = null;
		
		try {
			URL url = new URL(uri);
			HttpURLConnection con = (HttpURLConnection)url.openConnection();
			
			StringBuilder sb = new StringBuilder();
			reader = new BufferedReader(new InputStreamReader(con.getInputStream()));
			
			String line;
			while((line =reader.readLine())!=null){
				
				sb.append(line+ "\n");
			}
			return sb.toString();
			
		} catch (Exception e) {
		e.printStackTrace();
		return null;
		
		}finally{
			if(reader!=null){
				try {
					reader.close();
					
				} catch (IOException e) {
					e.printStackTrace();
					return null;
				}
			}
		}
	}
	
	
	//trae info splash
	public static String traerInfo()
	{
		String uri = URL+"traerInfo.php";
		
		BufferedReader reader = null;
		
		try {
			URL url = new URL(uri);
			HttpURLConnection con = (HttpURLConnection)url.openConnection();
			
			StringBuilder sb = new StringBuilder();
			reader = new BufferedReader(new InputStreamReader(con.getInputStream()));
			
			String line;
			while((line =reader.readLine())!=null){
				
				sb.append(line+ "\n");
			}
			return sb.toString();
			
		} catch (Exception e) {
		e.printStackTrace();
		return null;
		
		}finally{
			if(reader!=null){
				try {
					reader.close();
					
				} catch (IOException e) {
					e.printStackTrace();
					return null;
				}
			}
		}
	}
	
	
	
		
	//enviar/editar/eliminar
	
	
	public static String gestionPromo(Request p){
				
		
		String uri=null;
		if(p.getQuery().equals("SUBIR")){
			  uri =URL+"agregarPromo.php";
		}else if(p.getQuery().equals("EDITAR")){
			uri =URL+"editarPromo.php";
		}else if(p.getQuery().equals("ELIMINAR")){
			uri =URL+"eliminarPromo.php";
		}
		
	
		BufferedReader reader = null;
	//	String uri = p.getUri();
	
		
		if(p.getMethod().equals("GET")){
			uri += "?" + p.getEncodedParams();
		}
		
		try {
			URL url = new URL(uri);
			HttpURLConnection con = (HttpURLConnection)url.openConnection();
			con.setRequestMethod(p.getMethod());
			
	//	JSONObject json =  new JSONObject(p.getParametros());
	//	String parames = "paramentros=" + json.toString();
	
		if(p.getMethod().equals("POST")){	
	
			con.setDoOutput(true);
		OutputStreamWriter write =  new OutputStreamWriter(con.getOutputStream());
		
	//	write.write(parames);
		write.write(p.getEncodedParams());
		write.flush();
		
		
		}
			StringBuilder sb = new StringBuilder();
			reader = new BufferedReader(new InputStreamReader(con.getInputStream()));
			
			String line;
			while((line =reader.readLine())!=null){
				
				sb.append(line+ "\n");
			}
			return sb.toString();
			
		} catch (Exception e) {
		e.printStackTrace();
		return null;
		
		}finally{
			if(reader!=null){
				try {
					reader.close();
					
				} catch (IOException e) {
					e.printStackTrace();
					return null;
				}
			}
		}
   }
	
	//subir/editar/eliminar una empresa
	public static String gestionEmpresa(Request p){
		String uri=null;
		
		if(p.getQuery().equals("SUBIR")){
			 uri =URL+"agregarEmpresa.php";
		}else if(p.getQuery().equals("EDITAR")){
			uri =URL+"editarEmpresa.php";
		}else if(p.getQuery().equals("ELIMINAR")){
			uri =URL+"eliminarEmpresa.php";
		}
		 
		BufferedReader reader = null;
	//	String uri = p.getUri();
	
		
		if(p.getMethod().equals("GET")){
			uri += "?" + p.getEncodedParams();
		}
		
		try {
			URL url = new URL(uri);
			HttpURLConnection con = (HttpURLConnection)url.openConnection();
			con.setRequestMethod(p.getMethod());
			
	//	JSONObject json =  new JSONObject(p.getParametros());
	//	String parames = "paramentros=" + json.toString();
	
		if(p.getMethod().equals("POST")){	
	
			con.setDoOutput(true);
		OutputStreamWriter write =  new OutputStreamWriter(con.getOutputStream());
		
	//	write.write(parames);
		write.write(p.getEncodedParams());
		write.flush();
		
		
		}
			StringBuilder sb = new StringBuilder();
			reader = new BufferedReader(new InputStreamReader(con.getInputStream()));
			
			String line;
			while((line =reader.readLine())!=null){
				
				sb.append(line+ "\n");
			}
			return sb.toString();
			
		} catch (Exception e) {
		e.printStackTrace();
		return null;
		
		}finally{
			if(reader!=null){
				try {
					reader.close();
					
				} catch (IOException e) {
					e.printStackTrace();
					return null;
				}
			}
		}
   }
	
	
	//editar una Info
	public static String gestionInfo(Request p){
		String uri=null;
		
		uri =URL+"editarInfo.php";

		BufferedReader reader = null;
	
		
		if(p.getMethod().equals("GET")){
			uri += "?" + p.getEncodedParams();
		}
		
		try {
			URL url = new URL(uri);
			HttpURLConnection con = (HttpURLConnection)url.openConnection();
			con.setRequestMethod(p.getMethod());
			
	//	JSONObject json =  new JSONObject(p.getParametros());
	//	String parames = "paramentros=" + json.toString();
	
		if(p.getMethod().equals("POST")){	
	
			con.setDoOutput(true);
		OutputStreamWriter write =  new OutputStreamWriter(con.getOutputStream());
		
	//	write.write(parames);
		write.write(p.getEncodedParams());
		write.flush();
		
		
		}
			StringBuilder sb = new StringBuilder();
			reader = new BufferedReader(new InputStreamReader(con.getInputStream()));
			
			String line;
			while((line =reader.readLine())!=null){
				
				sb.append(line+ "\n");
			}
			return sb.toString();
			
		} catch (Exception e) {
		e.printStackTrace();
		return null;
		
		}finally{
			if(reader!=null){
				try {
					reader.close();
					
				} catch (IOException e) {
					e.printStackTrace();
					return null;
				}
			}
		}
   }

	
	
	
}
