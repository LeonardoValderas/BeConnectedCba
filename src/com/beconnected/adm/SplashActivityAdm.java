package com.beconnected.adm;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Timer;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.beconnected.R;
import com.beconnected.adm.AlertsMenu;
import com.beconnected.databases.BL;
import com.beconnected.databases.ControladorAdm;
import com.beconnected.databases.ControladorUsuario;
import com.beconnected.databases.DL;
import com.beconnected.databases.Empresa;
import com.beconnected.databases.GeneralLogic;
import com.beconnected.databases.Info;
import com.beconnected.databases.Promo;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.gcm.GoogleCloudMessaging;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager.NameNotFoundException;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;

public class SplashActivityAdm extends AppCompatActivity {
	private ProgressBar splashProgress;
	private GoogleCloudMessaging gcm;
	private String regid;
	private static final int PLAY_SERVICES_RESOLUTION_REQUEST = 9000;
	private static final String TAG = "GCMRelated";
	public static final String PROPERTY_REG_ID = "registration_id";
	private static final String PROPERTY_APP_VERSION = "appVersion";
	boolean isGPSEnabled = false;
	boolean isNetworkEnabled = false;
	boolean canGetLocation = false;
	boolean isInternet = false;
	protected LocationManager locationManager;
	private AlertsMenu alertsMenu;
	private ControladorAdm controladorAdm;
	private ControladorUsuario controladorUsuario;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		splashProgress = (ProgressBar) findViewById(R.id.splashProgress);
		controladorAdm = new ControladorAdm(SplashActivityAdm.this);
		controladorUsuario = new ControladorUsuario(SplashActivityAdm.this);
		if (GeneralLogic.conexionInternet(SplashActivityAdm.this)) {
			init();
		} else {

			alertsMenu = new AlertsMenu(SplashActivityAdm.this, "ATENCI�N!!!",
					"Por favor verifique su conexi�n de Internet", "Aceptar",
					null);
			alertsMenu.btnAceptar
					.setOnClickListener(new View.OnClickListener() {

						@Override
						public void onClick(View v) {
							// TODO Auto-generated method stub

							alertsMenu.alertDialog.dismiss();

							GeneralLogic.close(SplashActivityAdm.this);

						}
					});

			alertsMenu.btnCancelar.setVisibility(View.GONE);

		}

	}

	public void init() {

	

		controladorAdm.abrirBaseDeDatos();
		controladorAdm.dropTablasBDAdm();
		controladorAdm.cerrarBaseDeDatos();

		

		controladorUsuario.abrirBaseDeDatos();
		controladorUsuario.dropTablasBDUsuario();
		controladorUsuario.cerrarBaseDeDatos();

//		DL.getDl().setSqliteConnection(this);
//		BL.getBl().creaDirectorios();
//		BL.getBl().dropTablasBDAdm();
//		BL.getBl().dropTablasBDUsuario();
//		BL.getBl().crearTablasBDAmd();
//		BL.getBl().crearTablasBDUsuario();
//		BL.getBl().insertarInfo();
//		BL.getBl().insertarInfoUsuario();

		// registrarCel();

		new TaskEmpresa().execute("");
		//new TaskPromo().execute("");
	//	new TaskInfo().execute("");

		// Promo = getIntent().getBooleanExtra("PROMO", false);

	}

	public void registrarCel() {

		if (checkPlayServices()) {
			gcm = GoogleCloudMessaging.getInstance(getApplicationContext());
			regid = getRegistrationId(getApplicationContext());

			// if (regid.isEmpty()) {
			new com.beconnected.adm.RegisterAppAdm(getApplicationContext(),
					gcm, getAppVersion(getApplicationContext())).execute();
			// }else{
			//
			// SharedPreferences preferenciasId = getSharedPreferences(
			// "id_cel", Context.MODE_PRIVATE);
			// Editor editorEvento = preferenciasId.edit();
			// editorEvento.putString("id_cel", regid);
			// editorEvento.commit();
			//
			// }
		} else {

		}
	}

	private boolean checkPlayServices() {
		int resultCode = GooglePlayServicesUtil
				.isGooglePlayServicesAvailable(this);
		if (resultCode != ConnectionResult.SUCCESS) {
			if (GooglePlayServicesUtil.isUserRecoverableError(resultCode)) {
				GooglePlayServicesUtil.getErrorDialog(resultCode, this,
						PLAY_SERVICES_RESOLUTION_REQUEST).show();
			} else {
				Log.i(TAG, "This device is not supported.");
				finish();
			}
			return false;
		}
		return true;
	}

	/**
	 * Gets the current registration ID for application on GCM service.
	 * <p>
	 * If result is empty, the app needs to register.
	 * 
	 * @return registration ID, or empty string if there is no existing
	 *         registration ID.
	 */
	public String getRegistrationId(Context context) {
		final SharedPreferences prefs = getGCMPreferences(context);
		String registrationId = prefs.getString(PROPERTY_REG_ID, "");
		if (registrationId.isEmpty()) {
			Log.i(TAG, "Registration not found.");
			return "";
		}
		// Check if app was updated; if so, it must clear the registration ID
		// since the existing regID is not guaranteed to work with the new
		// app version.
		int registeredVersion = prefs.getInt(PROPERTY_APP_VERSION,
				Integer.MIN_VALUE);
		int currentVersion = getAppVersion(getApplicationContext());
		if (registeredVersion != currentVersion) {
			Log.i(TAG, "App version changed.");
			return "";
		}
		return registrationId;
	}

	private SharedPreferences getGCMPreferences(Context context) {
		// This sample app persists the registration ID in shared preferences,
		// but
		// how you store the regID in your app is up to you.
		return getSharedPreferences(SplashActivityAdm.class.getSimpleName(),
				Context.MODE_PRIVATE);
	}

	private static int getAppVersion(Context context) {
		try {
			PackageInfo packageInfo = context.getPackageManager()
					.getPackageInfo(context.getPackageName(), 0);
			return packageInfo.versionCode;
		} catch (NameNotFoundException e) {
			// should never happen
			throw new RuntimeException("No se tomo el nombre del pack: " + e);
		}
	}

	/**
	 * Traer todas las empresas
	 * 
	 * @author LEO 14/10/2015
	 */
	public class TaskEmpresa extends AsyncTask<String, String, String> {

		@Override
		protected void onPreExecute() {
			splashProgress.setVisibility(View.VISIBLE);
			// textViewDato.setText("Comienzo");

		}

		@Override
		protected String doInBackground(String... params) {

			String content = BL.getBl().getConnManager().traerEmpresa();
			// parseFeed(content);

			return content; // retorna string al metodo onPostExecute

		}

		@Override
		protected void onPostExecute(String result) {

			if (result != null) {
				
				if(parseFeed(result))
				{
					
					new TaskPromo().execute("");
					
				}else{
					
					Toast.makeText(SplashActivityAdm.this, "Problemas de conexi�n.Cierre y vuelva a intentarlo.",
							Toast.LENGTH_SHORT).show();
					finish();
				}
			} else {
				Toast.makeText(SplashActivityAdm.this, "Problemas de conexi�n.Cierre y vuelva a intentarlo.",
						Toast.LENGTH_SHORT).show();
				finish();
			}
		}

		@Override
		protected void onProgressUpdate(String... values) {
			// textViewDato.append(values[0]+"\n");
		}
	}

	/**
	 * Guarda las empresas en la base de datos y convierte url imagen a bitmap
	 * luego a byte[]
	 * 
	 * 14/10/2015
	 * 
	 */

	public boolean parseFeed(String content) {
		boolean gestionOk= true; 
		JSONArray ar=null;
		try {
			 ar = new JSONArray(content);
		
			for (int i = 0; i < ar.length(); i++) {

				JSONObject obj = ar.getJSONObject(i);
				String a = obj.getString("URL_LOGO").toString();
				// String a =
				// "http://beconnected.esy.es/BeConnected/picture/mv.PGN";

				Bitmap b = getBitmap(a);
				if(b!=null){
				ByteArrayOutputStream stream = new ByteArrayOutputStream();
				
				b.compress(Bitmap.CompressFormat.PNG, 0, stream);
				byte[] byteArray = stream.toByteArray();

				Empresa empresa = new Empresa(obj.getInt("ID_EMPRESA"),
						obj.getString("EMPRESA"), obj.getString("LONGITUD"),
						obj.getString("LATITUD"), byteArray,
						obj.getString("URL_LOGO"));

		//		controladorAdm = new ControladorAdm(SplashActivityAdm.this);

				controladorAdm.abrirBaseDeDatos();
				controladorAdm.insertEmpresaSplash(empresa);
				controladorAdm.cerrarBaseDeDatos();

				//controladorUsuario = new ControladorUsuario(
					//	SplashActivityAdm.this);

				controladorUsuario.abrirBaseDeDatos();
				controladorUsuario.insertEmpresaSplash(empresa);
				controladorUsuario.cerrarBaseDeDatos();

				// BL.getBl().insertarEmpresaAdm(empresa);
				// BL.getBl().insertarEmpresaUsuario(empresa);

				
				}else
				{
					gestionOk=false;
				}
				
			}
			return gestionOk;
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			if(ar==null){
				gestionOk=true;
				return gestionOk;
			}
			gestionOk=false;
			return gestionOk;
		}
		//return gestionOk;
	}

	public static Bitmap getBitmap(String url) {

		StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
				.permitAll().build();
		StrictMode.setThreadPolicy(policy);

		BitmapFactory.Options bmOptions;
		bmOptions = new BitmapFactory.Options();
		bmOptions.inSampleSize = 1;
		Bitmap bm = loadBitmap(url, bmOptions);
		return bm;
		// mImgView1.setImageBitmap(bm);

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

	/**
	 * Traer todas las promos
	 * 
	 * @author LEO 14/10/2015
	 */

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

			if (result != null) {
				if(parseFeedPromo(result)){
				
					new TaskInfo().execute("");
                 }else{
				
				Toast.makeText(SplashActivityAdm.this, "Problemas de conexi�n.Cierre y vuelva a intentarlo.",
						Toast.LENGTH_SHORT).show();
				finish();
			}
			} else {
				Toast.makeText(SplashActivityAdm.this, "Problemas de conexi�n.Cierre y vuelva a intentarlo.",
						Toast.LENGTH_SHORT).show();
				finish();
			}

		}
						
		@Override
		protected void onProgressUpdate(String... values) {
			// textViewDato.append(values[0]+"\n");
		}

	}

	/**
	 * Guarda las promos en la base de datos
	 * 
	 * @param contentPromo
	 * @return
	 */

	public boolean parseFeedPromo(String contentPromo) {
      boolean gestionOk=true;
  	JSONArray ar=null;
 
		try {
			
			
			 ar = new JSONArray(contentPromo);
		

			for (int i = 0; i < ar.length(); i++) {

				JSONObject obj = ar.getJSONObject(i);
				Promo promo = new Promo(obj.getInt("ID_PROMO"),
						obj.getString("TITULO"), obj.getString("DESCRIPCION"),
						obj.getInt("ID_EMPRESA"), null,
						obj.getString("FECHA_INICIO"),
						obj.getString("FECHA_FIN"));

			//	controladorAdm = new ControladorAdm(SplashActivityAdm.this);

				controladorAdm.abrirBaseDeDatos();
				controladorAdm.insertPromoSplash(promo);
				controladorAdm.cerrarBaseDeDatos();

				
				

			//	controladorUsuario = new ControladorUsuario(
					//	SplashActivityAdm.this);

				controladorUsuario.abrirBaseDeDatos();
				controladorUsuario.insertPromoUsuario(promo);
				controladorUsuario.cerrarBaseDeDatos();
				
				
				// BL.getBl().insertarPromoAdm(promo);
				//BL.getBl().insertarPromoUsuario(promo);

				
			}
			return gestionOk;
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		
			if(ar==null){
				gestionOk=true;
				return gestionOk;
			}
			gestionOk=false;
			return gestionOk;
		}
		
	}

	/**
	 * Traer todas las info
	 * 
	 * @author LEO 14/10/2015
	 */

	public class TaskInfo extends AsyncTask<String, String, String> {

		@Override
		protected void onPreExecute() {
			splashProgress.setVisibility(View.VISIBLE);

		}

		@Override
		protected String doInBackground(String... params) {

			String contentInfo = BL.getBl().getConnManager().traerInfo();
			return contentInfo; // retorna string al metodo onPostExecute

		}

		@Override
		protected void onPostExecute(String result) {

			if (result != null) {
				if(parseFeedInfo(result)){
					splashProgress.setVisibility(View.INVISIBLE);

					Intent usuario = new Intent(SplashActivityAdm.this,
							TabsAdmEmpresa.class);
					startActivity(usuario);
					
				}else{
					
					Toast.makeText(SplashActivityAdm.this, "Problemas de conexi�n.Cierre y vuelva a intentarlo.",
							Toast.LENGTH_SHORT).show();
					finish();
				}
			} else {
				Toast.makeText(SplashActivityAdm.this, "Problemas de conexi�n.Cierre y vuelva a intentarlo.",
						Toast.LENGTH_SHORT).show();
				finish();
			}

			

		}

		@Override
		protected void onProgressUpdate(String... values) {
			// textViewDato.append(values[0]+"\n");
		}

	}

	/**
	 * Guarda las promos en la base de datos
	 * 
	 * @param contentPromo
	 * @return
	 */

	public boolean parseFeedInfo(String contentPromo) {
		boolean gestionOk=true;
		JSONArray ar=null;
		try {
			 ar = new JSONArray(contentPromo);


			for (int i = 0; i < ar.length(); i++) {

				JSONObject obj = ar.getJSONObject(i);
				Info info = new Info(obj.getString("SOMOS"),
						obj.getString("CONTACTOS"));

			//	controladorAdm = new ControladorAdm(SplashActivityAdm.this);

				controladorAdm.abrirBaseDeDatos();
				controladorAdm.insertInfo();
				controladorAdm.actualizarInfo(info);
				controladorAdm.cerrarBaseDeDatos();

				
		
				

			//	controladorUsuario = new ControladorUsuario(
				//		SplashActivityAdm.this);

				controladorUsuario.abrirBaseDeDatos();
				controladorUsuario.insertInfo();
				controladorUsuario.actualizarInfo(info);
				controladorUsuario.cerrarBaseDeDatos();
				
				
				// BL.getBl().actualizarInfo(info);
			//	BL.getBl().actualizarInfoUsuario(info);

			
			}
			return gestionOk;
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			if(ar==null){
				gestionOk=true;
				return gestionOk;
			}
			gestionOk = false;
			return gestionOk;
		}

	}

}