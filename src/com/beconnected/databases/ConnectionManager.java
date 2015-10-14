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
	
	
	
	
	//verrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrr
	public void seleccionarUrlLogo() {
	
		LoadImage loadImage = new LoadImage();
		loadImage.execute("");
		

	}
//	private class LoadImage extends AsyncTask<String, String, Bitmap> {
		private class LoadImage extends AsyncTask<String, String, String> {	
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
         //   pDialog = new ProgressDialog(MainActivity.this);
          //  pDialog.setMessage("Loading Image ....");
           // pDialog.show();
 
        }
        Bitmap bitmap;
        int id;
        Empresa empresa;
         protected String doInBackground(String... paramers) {
           String resuldo= "ok";
        	 try {
            	 
            		ArrayList<Empresa> empresaArray;
            		empresaArray = BL.getBl().selectListaUrl();
            		
            	for (int i = 0; i < empresaArray.size(); i++) {
            		
            		 bitmap = BitmapFactory.decodeStream((InputStream)new URL("http://beconnected.esy.es/BeConnected/picture/jxnv.PGN").getContent());
            		 id=empresaArray.get(i).getID_EMPRESA();
            		 
            		 bitmap = Bitmap.createScaledBitmap(bitmap, 150, 150, true);
                      
                      ByteArrayOutputStream  baos = new ByteArrayOutputStream();
                      bitmap.compress(CompressFormat.PNG, 0, baos);
                     byte[]  imagenLogo = baos.toByteArray();
                      empresa = new Empresa(id, null, null, null, imagenLogo, null);
                    BL.getBl().insertarEmpresaUsuarioUrl(empresa);
            	}
            	 
                
 
            } catch (Exception e) {
                 e.printStackTrace();
            }
            return resuldo;
         }
 
         protected void onPostExecute(boolean image) {
// 
        	 
        	 image = true;
//             if(image != null){
//             //img.setImageBitmap(image);
//        //     pDialog.dismiss();
//             
//             
//             image = Bitmap.createScaledBitmap(image, 150, 150, true);
//             
//             ByteArrayOutputStream  baos = new ByteArrayOutputStream();
//             image.compress(CompressFormat.PNG, 0, baos);
//            byte[]  imagenLogo = baos.toByteArray();
//             empresa = new Empresa(id, null, null, null, imagenLogo, null);
//           BL.getBl().insertarEmpresaUsuarioUrl(empresa);
//             
//				//imagenLogo = baos.toByteArray();
// 
//             }else{
 
         //    pDialog.dismiss();
         //   Toast.makeText(MainActivity.this, "Image Does Not exist or Network Error", Toast.LENGTH_SHORT).show();
 
      //       }
         }
     }
	
	
	
	
	
	public static String enviarPromo(Request p){
				
		
		String uri=null;
		if(p.getQuery().equals("SUBIR")){
			  uri =URL+"agregarPromo.php";
		}else if(p.getQuery().equals("EDITAR")){
			uri =URL+"editarPromo.php";
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
	
	//subir/editar/eliminar una empresa
	public static String enviarEmpresa(Request p){
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
	

	
	
	
}
