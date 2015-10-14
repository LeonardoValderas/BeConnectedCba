package com.beconnected;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import com.beconnected.databases.BL;
import com.beconnected.databases.DL;
import com.beconnected.databases.Empresa;
import com.beconnected.databases.Promo;
import com.beconnected.databases.Request;

import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Bitmap.CompressFormat;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;

public class SplashActivity extends AppCompatActivity {
	private ProgressBar splashProgress;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		splashProgress = (ProgressBar) findViewById(R.id.splashProgress);
		init();
	}

	public void init() {

		DL.getDl().setSqliteConnection(this);
		BL.getBl().dropTablasBDUsuario();
		BL.getBl().crearTablasBDUsuario();
		BL.getBl().creaDirectorios();
	    new TaskEmpresa().execute("");
		
		new TaskPromo().execute("");
	}
	
	
	
	
	   public class TaskEmpresa extends AsyncTask<String, String, String> {

	        @Override
	        protected void onPreExecute() {
	        	splashProgress.setVisibility(View.VISIBLE);
	            //textViewDato.setText("Comienzo");
	            
	        }

	        @Override
	        protected String doInBackground(String... params) {

	 	        
	        	String content = BL.getBl().getConnManager().traerEmpresa();
	               //   parseFeed(content);
	         
	                  
	                  return content;  //retorna string al metodo onPostExecute

	    
	        }

	        @Override
	        protected void onPostExecute(String result) {
	            
	            
	         parseFeed(result);
	        
	            
	        }

	        @Override
	        protected void onProgressUpdate(String... values) {
	            //textViewDato.append(values[0]+"\n");
	        }
	    }
	    
	    

	    
	    
	    public static ArrayList<Empresa> parseFeed(String content)    {
	        
	        try {
	            JSONArray ar = new JSONArray(content);
	            ArrayList<Empresa> datasJson = new ArrayList<>();
	            
	            for (int i = 0; i < ar.length(); i++) {
	                
	            JSONObject obj = ar.getJSONObject(i);
	            String a = obj.getString("URL_LOGO").toString();
	            Bitmap b =getBitmap(a);
	                ByteArrayOutputStream stream = new ByteArrayOutputStream();
	             b.compress(Bitmap.CompressFormat.PNG, 100, stream);
	             byte[] byteArray = stream.toByteArray(); 
	            
	            Empresa empresa = new Empresa(obj.getInt("ID_EMPRESA"),obj.getString("EMPRESA"),obj.getString("LONGITUD"),obj.getString("LATITUD"),byteArray,obj.getString("URL_LOGO"));
	            
	            
	      		BL.getBl().insertarEmpresaUsuario(empresa);

	  
	            datasJson.add(empresa);
	            
	            
	            }
	            return datasJson;
	        } catch (JSONException e) {
	            // TODO Auto-generated catch block
	            e.printStackTrace();
	            return null;
	        }
	        
	            
	    }
	    
	    

	    
	    public static Bitmap getBitmap(String url){
	        
	         StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
	                    .permitAll().build();
	            StrictMode.setThreadPolicy(policy);
	        
	          BitmapFactory.Options bmOptions;
	            bmOptions = new BitmapFactory.Options();
	            bmOptions.inSampleSize = 1;
	            Bitmap bm = loadBitmap(url, bmOptions);
	           return bm;
	          //  mImgView1.setImageBitmap(bm);
	        
	    }
	        

	        public static Bitmap loadBitmap(String URL, BitmapFactory.Options options) {
	            Bitmap bitmap = null;
	            InputStream in = null;
	            try {
	                in = OpenHttpConnection(URL);
	                bitmap = BitmapFactory.decodeStream(in, null, options);
	                in.close();
	            } catch (IOException e1) {
	            }
	            return bitmap;
	        }

	        private static InputStream OpenHttpConnection(String strURL)
	                throws IOException {
	            InputStream inputStream = null;
	            URL url = new URL(strURL);
	            URLConnection conn = url.openConnection();

	            try {
	                HttpURLConnection httpConn = (HttpURLConnection) conn;
	                httpConn.setRequestMethod("GET");
	                httpConn.connect();

	                if (httpConn.getResponseCode() == HttpURLConnection.HTTP_OK) {
	                    inputStream = httpConn.getInputStream();
	                }
	            } catch (Exception ex) {
	            }
	            return inputStream;
	        }

	
	
	
	
//
//	public class TaskEmpresa extends AsyncTask<String, String, String> {
//
//		@Override
//		protected void onPreExecute() {
//			splashProgress.setVisibility(View.VISIBLE);
//
//		}
//
//		@Override
//		protected String doInBackground(String... params) {
//
//			String content = BL.getBl().getConnManager().traerEmpresa();
//			return content; // retorna string al metodo onPostExecute
//
//		}
//
//		@Override
//		protected void onPostExecute(String result) {
//
//			// para listView
//			parseFeed(result);
//			BL.getBl().getConnManager().seleccionarUrlLogo();
//			
//			
//			
//			// splashProgress.setVisibility(View.INVISIBLE);
//
//			//
//			// Intent usuario = new Intent(SplashActivity.this,
//			// TabsUsuario.class);
//			// startActivity(usuario);
//
//		}
//
//		@Override
//		protected void onProgressUpdate(String... values) {
//			// textViewDato.append(values[0]+"\n");
//			
//			
//		}
//
//	}

	
	
	
	
	
	
	
	
	
	
	
	
	
	
	public class TaskPromo extends AsyncTask<String, String, String> {

		@Override
		protected void onPreExecute() {
			splashProgress.setVisibility(View.VISIBLE);

		}

		@Override
		protected String doInBackground(String... params) {

			String contentPromo = BL.getBl().getConnManager().traerPromo();
			return contentPromo; // retorna string al metodo onPostExecute

		}

		@Override
		protected void onPostExecute(String result) {

			// para listView
			parseFeedPromo(result);

			splashProgress.setVisibility(View.INVISIBLE);

			Intent usuario = new Intent(SplashActivity.this, TabsUsuario.class);
			startActivity(usuario);

		}

		@Override
		protected void onProgressUpdate(String... values) {
			// textViewDato.append(values[0]+"\n");
		}

	}

//	public static ArrayList<Empresa> parseFeed(String content) {
//		Bitmap myBitmap;
//		 byte[]  imagenLogo;
//		try {
//			JSONArray ar = new JSONArray(content);
//			ArrayList<Empresa> datasJson = new ArrayList<>();
//
//			for (int i = 0; i < ar.length(); i++) {
//
//				JSONObject obj = ar.getJSONObject(i);
//			
//				Empresa empresa = new Empresa(0, obj.getString("EMPRESA"),
//						obj.getString("LONGITUD"), obj.getString("LATITUD"),
//				null, obj.getString("URL_LOGO"));
//
//			
//			
//				
//		//		BL.getBl().insertarEmpresaUsuario(empresa);
//
//		//		datasJson.add(empresa);
//			}
//			return datasJson;
//		} catch (JSONException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//			return null;
//		}
//
//	}
//	
	


	

	public static ArrayList<Promo> parseFeedPromo(String contentPromo) {

		try {
			JSONArray ar = new JSONArray(contentPromo);
			ArrayList<Promo> datasJson = new ArrayList<>();

			for (int i = 0; i < ar.length(); i++) {

				JSONObject obj = ar.getJSONObject(i);
				Promo promo = new Promo(0, obj.getString("TITULO"),
						obj.getString("DESCRIPCION"), obj.getInt("ID_EMPRESA"),
						null, obj.getString("FECHA_INICIO"),
						obj.getString("FECHA_FIN"));

				BL.getBl().insertarPromoUsuario(promo);

				datasJson.add(promo);
			}
			return datasJson;
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}

	}

}
