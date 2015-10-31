package com.beconnected.adm;

import java.util.ArrayList;
import org.json.JSONException;
import org.json.JSONObject;
import com.beconnected.R;
import com.beconnected.TabsUsuario;
import com.beconnected.databases.BL;
import com.beconnected.databases.ControladorAdm;
import com.beconnected.databases.GeneralLogic;
import com.beconnected.databases.Info;
import com.beconnected.databases.Request;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class TabsAdmInfo extends AppCompatActivity {

	private EditText editTextSomos;
	private EditText editTextContacto;
	private Button buttonGuardar;
	private ArrayList<Info> arrayInfo;
	private Info info;
	private static final String TAG_SUCCESS = "success";
	private static final String TAG_MESSAGE = "message";
	private ProgressDialog dialog;
	//private  Typeface cFont;
	private AlertsMenu alertsMenu;
	private ControladorAdm controladorAdm;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.adm_info);

		init();
	}

	public void init() {

		controladorAdm =  new ControladorAdm(TabsAdmInfo.this);
		controladorAdm.abrirBaseDeDatos();
		
		arrayInfo = controladorAdm.selectListaInfo();
		controladorAdm.cerrarBaseDeDatos();

      //  cFont = Typeface.createFromAsset(getAssets(), "fonts/NEUROPOL.ttf");
		editTextSomos = (EditText) findViewById(R.id.editTextSomos);
	//	editTextSomos.setTypeface(cFont);
		editTextContacto = (EditText) findViewById(R.id.editTextContacto);
		//editTextContacto.setTypeface(cFont);
		if (arrayInfo.size() != 0) {

			editTextSomos.setText(arrayInfo.get(0).getSOMOS().toString());
			editTextContacto.setText(arrayInfo.get(0).getCONTACTO().toString());
		} else {
			
			controladorAdm =  new ControladorAdm(TabsAdmInfo.this);
			controladorAdm.abrirBaseDeDatos();
			controladorAdm.insertInfo();
			arrayInfo = controladorAdm.selectListaInfo();
			controladorAdm.cerrarBaseDeDatos();
			
			editTextSomos.setText(arrayInfo.get(0).getSOMOS().toString());
			editTextContacto.setText(arrayInfo.get(0).getCONTACTO().toString());
		}

		buttonGuardar = (Button) findViewById(R.id.buttonGuardar);

		buttonGuardar.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

				info = new Info(editTextSomos.getText().toString(),
						editTextContacto.getText().toString());

				Request p = new Request();
				p.setMethod("POST");
				p.setParametrosDatos("somos", info.getSOMOS());
				p.setParametrosDatos("contacto", info.getCONTACTO());

				if (GeneralLogic.conexionInternet(TabsAdmInfo.this)) {
					TaskInfo TaskInfo = new TaskInfo();
					TaskInfo.execute(p);
				} else {

					alertsMenu = new AlertsMenu(TabsAdmInfo.this,
							"ATENCIÓN!!!",
							"Por favor verifique su conexión de Internet",
							"Aceptar", null);
					alertsMenu.btnAceptar
							.setOnClickListener(new View.OnClickListener() {

								@Override
								public void onClick(View v) {
									// TODO Auto-generated method stub

									alertsMenu.alertDialog.dismiss();
									// close();

								}
							});

					alertsMenu.btnCancelar.setVisibility(View.GONE);

				}

			}
		});

	}

	// editar info

	public class TaskInfo extends AsyncTask<Request, String, String> {

		@Override
		protected void onPreExecute() {

			dialog = new ProgressDialog(TabsAdmInfo.this);
			dialog.setMessage("Procesando...");
			dialog.show();

		}

		@Override
		protected String doInBackground(Request... params) {

			int success;
			JSONObject json = null;
			try {

				json = BL.getBl().getConnManager()
						.gestionInfo(params[0]);
              if(json!=null){
				success = json.getInt(TAG_SUCCESS);
				if (success == 1) {

					//BL.getBl().actualizarInfo(info);

					controladorAdm =  new ControladorAdm(TabsAdmInfo.this);
					controladorAdm.abrirBaseDeDatos();
					
				    controladorAdm.actualizarInfo(info);
					controladorAdm.cerrarBaseDeDatos();

					
					
					return json.getString(TAG_MESSAGE);
				} else {

					return json.getString(TAG_MESSAGE);

				}
              } else {
					// Log.d("Registering Failure!",
					// json.getString(TAG_MESSAGE));
					 String erroString="Problemas con la Conexión.";
					 return erroString;
				}
              
              
              } catch (JSONException e) {
				e.printStackTrace();
			}

			return null;

		}

		@Override
		protected void onPostExecute(String result) {
			dialog.dismiss();

			Toast.makeText((TabsAdmInfo.this), result, Toast.LENGTH_SHORT)
					.show();

		}

		@Override
		protected void onProgressUpdate(String... values) {
			// textViewDato.append(values[0]+"\n");
		}
	}
	
	
	

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);

		// menu.getItem(0).setVisible(false);//usuario
		// menu.getItem(1).setVisible(false);//empresa
		// menu.getItem(2).setVisible(false);//promo
	       menu.getItem(3).setVisible(false);// info
		//menu.getItem(4).setVisible(false);// sincro
		//menu.getItem(5).setVisible(false);// cerrar
	
		
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {

		int id = item.getItemId();
		// noinspection SimplifiableIfStatement
		if (id == R.id.action_usuario) {

			Intent usuario = new Intent(TabsAdmInfo.this, TabsUsuario.class);
			startActivity(usuario);

			return true;
		}
		if (id == R.id.action_empresa) {
			Intent promo = new Intent(TabsAdmInfo.this, TabsAdmPromo.class);
			startActivity(promo);

			return true;
		}
		if (id == R.id.action_promo) {
			Intent promo = new Intent(TabsAdmInfo.this, TabsAdmPromo.class);
			startActivity(promo);

			return true;
		}

		if (id == R.id.action_sincro) {
			Intent sincro = new Intent(TabsAdmInfo.this,
					SplashActivityAdm.class);
			startActivity(sincro);
			return true;
		}

		if (id == R.id.action_cerrar) {

			GeneralLogic.close(TabsAdmInfo.this);
			return true;
		}

		return super.onOptionsItemSelected(item);
	}
}