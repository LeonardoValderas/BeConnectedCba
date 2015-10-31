package com.beconnected;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONException;
import org.json.JSONObject;
import com.beconnected.SplashActivity;
import com.beconnected.databases.BL;
import com.beconnected.databases.ControladorUsuario;
import com.beconnected.databases.Request;
import com.google.android.gms.gcm.GoogleCloudMessaging;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.util.Log;

public class RegisterApp extends AsyncTask<Void, Void, String> {

	private static final String TAG = "GCMRelated";
	private Context ctx;
	private GoogleCloudMessaging gcm;
	private String SENDER_ID = "970015523728";
	private String regid = null;
	private int appVersion;
	private int id_base;
	public static String URLNOTI = "http://beconnected.esy.es/BeConnected/Notification/";
	private static final String TAG_SUCCESS = "success";
	private static final String TAG_MESSAGE = "message";
	private ControladorUsuario controladorUsuario;

	private static final String TAG_ID = "id";

	public RegisterApp(Context ctx, GoogleCloudMessaging gcm, int appVersion) {
		this.ctx = ctx;
		this.gcm = gcm;
		this.appVersion = appVersion;
		// this.id_base =idBase;

	}

	@Override
	protected void onPreExecute() {
		super.onPreExecute();

	}

	@Override
	protected String doInBackground(Void... arg0) {
		String msg = "";

		try {
			if (gcm == null) {
				gcm = GoogleCloudMessaging.getInstance(ctx);
			}
			regid = gcm.register(SENDER_ID);
			msg = "Device registered, registration ID=" + regid;

			// You should send the registration ID to your server over HTTP,
			// so it can use GCM/HTTP or CCS to send messages to your app.
			// The request to your server should be authenticated if your app
			// is using accounts.
			// sendRegistrationIdToBackend();
			requestGMC();
			// For this demo: we don't need to send it because the device
			// will send upstream messages to a server that echo back the
			// message using the 'from' address in the message.

			// Persist the regID - no need to register again.
			storeRegistrationId(ctx, regid);
		} catch (IOException ex) {
			msg = "Error :" + ex.getMessage();
			// If there is an error, don't just keep trying to register.
			// Require the user to click a button again, or perform
			// exponential back-off.
		}
		return msg;
	}

	private void storeRegistrationId(Context ctx, String regid) {
		final SharedPreferences prefs = ctx.getSharedPreferences(
				SplashActivity.class.getSimpleName(), Context.MODE_PRIVATE);
		Log.i(TAG, "Saving regId on app version " + appVersion);
		SharedPreferences.Editor editor = prefs.edit();
		editor.putString("registration_id", regid);
		editor.putInt("appVersion", appVersion);
		// editor.putInt("idBase", id_base);
		editor.commit();

	}

	private void requestGMC() {
		Request p = new Request();
		controladorUsuario = new ControladorUsuario(ctx);
		//id_base = BL.getBl().selectIdUsuario();
		
		controladorUsuario.abrirBaseDeDatos();
		id_base = controladorUsuario.selectIdUsuario();
		controladorUsuario.cerrarBaseDeDatos();
		
		p.setMethod("POST");
		p.setQuery("USUARIO");

		p.setParametrosDatos("regId", regid);
		p.setParametrosDatos("idBase", String.valueOf(id_base));

		TaskGMC taskGMC = new TaskGMC();
		taskGMC.execute(p);

	}

	public class TaskGMC extends AsyncTask<Request, String, String> {

		@Override
		protected void onPreExecute() {

		}

		@Override
		protected String doInBackground(Request... params) {

			int success;

			try {

				JSONObject json = BL.getBl().getConnManager()
						.gestionGMC(params[0]);

				success = json.getInt(TAG_SUCCESS);
				if (success == 1) {
					controladorUsuario = new ControladorUsuario(ctx);
					//id_base = BL.getBl().selectIdUsuario();
					
					controladorUsuario.abrirBaseDeDatos();
					id_base = json.getInt(TAG_ID);
					controladorUsuario.insertIdUsuario(id_base);
					controladorUsuario.cerrarBaseDeDatos();
					// String a = json.getString(TAG_ID);
					//id_base = json.getInt(TAG_ID);
				//	BL.getBl().insertarIdUsuario(id_base);

					return json.getString(TAG_MESSAGE);
				} else {
					// Log.d("Registering Failure!",
					// json.getString(TAG_MESSAGE));
					return json.getString(TAG_MESSAGE);

				}
			} catch (JSONException e) {
				e.printStackTrace();
			}

			return null;

		}

		@Override
		protected void onPostExecute(String result) {

		}

		@Override
		protected void onProgressUpdate(String... values) {
			// textViewDato.append(values[0]+"\n");
		}
	}

	//
	// private void sendRegistrationIdToBackend() {
	// URI url = null;
	//
	// String register = "register.php";
	// String dato = "?regId=";
	//
	//
	// try {
	// url = new URI(URLNOTI+register+dato+regid);
	//
	// // url = new URI("http://10.0.2.2/gcm_3/register.php?regId=" + regid);
	// } catch (URISyntaxException e) {
	// // TODO Auto-generated catch block
	// e.printStackTrace();
	// }
	// HttpClient httpclient = new DefaultHttpClient();
	// HttpGet request = new HttpGet();
	// request.setURI(url);
	// try {
	// httpclient.execute(request);
	// } catch (ClientProtocolException e) {
	// // TODO Auto-generated catch block
	// e.printStackTrace();
	// } catch (IOException e) {
	// // TODO Auto-generated catch block
	// e.printStackTrace();
	// }
	// }

	@Override
	protected void onPostExecute(String result) {
		super.onPostExecute(result);
		// Toast.makeText(ctx, "Registración Completa.Bienvenido!!!",
		// Toast.LENGTH_SHORT).show();
		Log.v(TAG, result);
	}
}
