package com.beconnected;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import com.beconnected.adm.AlertsMenu;
import com.beconnected.databases.BL;
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
import android.os.Handler;
import android.os.StrictMode;

public class SplashActivity extends AppCompatActivity {
	private ProgressBar splashProgress;
	private GoogleCloudMessaging gcm;
	private String regid;
	private static final int PLAY_SERVICES_RESOLUTION_REQUEST = 9000;
	private static final String TAG = "GCMRelated";
	public static final String PROPERTY_REG_ID = "registration_id";
	private static final String PROPERTY_APP_VERSION = "appVersion";
	private boolean Promo = false;
	private Handler hand;
	private Timer timerActualizacion;
	private Handler hand1;
	private final int TIEMPO_SERVICIO = (1000 * 60 * 30); // 1000*60 = 1
	boolean isGPSEnabled = false;
	boolean isNetworkEnabled = false;
	boolean canGetLocation = false;
	boolean isInternet = false;
	protected LocationManager locationManager;
	private AlertsMenu alertsMenu;

	private Intent bateria;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		splashProgress = (ProgressBar) findViewById(R.id.splashProgress);

		/**
		 * Activa el servicio que buscar el nivel de bateria cada 30 min
		 */
		bateria = new Intent(getApplicationContext(), Baterry.class);

		hand = new Handler();
		hand1 = new Handler();
		timerActualizacion = new Timer();
		TimerTask taskActualizacion = new TimerTask() {

			@Override
			public void run() {
				hand.post(new Runnable() {
					@Override
					public void run() {

						startService(bateria);

					}
				});
			}
		};
		timerActualizacion.scheduleAtFixedRate(taskActualizacion, 0,
				TIEMPO_SERVICIO);

		if (GeneralLogic.conexionInternet(SplashActivity.this)) {
			init();
		} else {

			alertsMenu = new AlertsMenu(SplashActivity.this, "ATENCIÓN!!!",
					"Por favor verifique su conexión de Internet", "Aceptar",
					null);
			alertsMenu.btnAceptar
					.setOnClickListener(new View.OnClickListener() {

						@Override
						public void onClick(View v) {
							// TODO Auto-generated method stub

							alertsMenu.alertDialog.dismiss();
							GeneralLogic.close(SplashActivity.this);

						}
					});

			alertsMenu.btnCancelar.setVisibility(View.GONE);

		}

	}

	public void init() {

		DL.getDl().setSqliteConnection(this);
		BL.getBl().dropTablasBDUsuario();
		BL.getBl().crearTablasBDUsuario();
		BL.getBl().creaDirectorios();
		BL.getBl().insertarInfoUsuario();
		registrarCel();

		new TaskEmpresa().execute("");
		new TaskPromo().execute("");
		new TaskInfo().execute("");

		Promo = getIntent().getBooleanExtra("PROMO", false);

	}

	public void registrarCel() {

		if (checkPlayServices()) {
			gcm = GoogleCloudMessaging.getInstance(getApplicationContext());
			regid = getRegistrationId(getApplicationContext());

			// if (regid.isEmpty()) {
			new com.beconnected.RegisterApp(getApplicationContext(), gcm,
					getAppVersion(getApplicationContext())).execute();
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
		return getSharedPreferences(SplashActivity.class.getSimpleName(),
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

			parseFeed(result);

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

	public static ArrayList<Empresa> parseFeed(String content) {

		try {
			JSONArray ar = new JSONArray(content);
			ArrayList<Empresa> datasJson = new ArrayList<>();

			for (int i = 0; i < ar.length(); i++) {

				JSONObject obj = ar.getJSONObject(i);
				String a = obj.getString("URL_LOGO").toString();

				Bitmap b = getBitmap(a);
				ByteArrayOutputStream stream = new ByteArrayOutputStream();
				b.compress(Bitmap.CompressFormat.PNG, 0, stream);
				byte[] byteArray = stream.toByteArray();

				Empresa empresa = new Empresa(obj.getInt("ID_EMPRESA"),
						obj.getString("EMPRESA"), obj.getString("LONGITUD"),
						obj.getString("LATITUD"), byteArray,
						obj.getString("URL_LOGO"));

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

			// para listView
			parseFeedPromo(result);

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

	public static ArrayList<Promo> parseFeedPromo(String contentPromo) {

		try {
			JSONArray ar = new JSONArray(contentPromo);
			ArrayList<Promo> datasJson = new ArrayList<>();

			for (int i = 0; i < ar.length(); i++) {

				JSONObject obj = ar.getJSONObject(i);
				Promo promo = new Promo(obj.getInt("ID_PROMO"),
						obj.getString("TITULO"), obj.getString("DESCRIPCION"),
						obj.getInt("ID_EMPRESA"), null,
						obj.getString("FECHA_INICIO"),
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

			// para listView
			parseFeedInfo(result);

			splashProgress.setVisibility(View.INVISIBLE);

			Intent usuario = new Intent(SplashActivity.this, TabsUsuario.class);
			usuario.putExtra("PROMO", Promo);
			startActivity(usuario);

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

	public static ArrayList<Info> parseFeedInfo(String contentPromo) {

		try {
			JSONArray ar = new JSONArray(contentPromo);
			ArrayList<Info> datasJson = new ArrayList<>();

			for (int i = 0; i < ar.length(); i++) {

				JSONObject obj = ar.getJSONObject(i);
				Info info = new Info(obj.getString("SOMOS"),
						obj.getString("CONTACTOS"));

				BL.getBl().actualizarInfo(info);
				BL.getBl().actualizarInfoUsuario(info);

				datasJson.add(info);
			}
			return datasJson;
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}

	}

}