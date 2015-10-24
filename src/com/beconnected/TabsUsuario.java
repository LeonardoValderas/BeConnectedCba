package com.beconnected;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import com.beconnected.adm.TabsAdmEmpresa;
import com.beconnected.databases.BL;
import com.beconnected.databases.Empresa;
import com.beconnected.databases.GeneralLogic;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Parcelable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

public class TabsUsuario extends AppCompatActivity {

	private Toolbar toolbar;
	private ViewPager viewPager;
	private TabLayout tabLayout;
	private ProgressDialog dialog;
	private boolean Promo = false;
	private boolean battery = false;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.tabs_usuario);

		init();

	}

	
	
	@Override protected void onRestart() {
 	   super.onRestart();
 		init();
 	  // Toast.makeText(this, "onRestart", Toast.LENGTH_SHORT).show();
 	}
	
	//@Override
	public Parcelable saveState() {
	    return null;
	}
	public void init() {
		battery = getIntent().getBooleanExtra("battery", false);
		if(battery){
			Intent	bateria = new Intent(getApplicationContext(), Baterry.class);
		
			stopService(bateria);
			//startService(bateria);
		}
		
		Promo = getIntent().getBooleanExtra("PROMO", false);
		// Toolbar
		
		toolbar = (Toolbar) findViewById(R.id.toolbar);
		setSupportActionBar(toolbar);

		getSupportActionBar().setDisplayShowTitleEnabled(false);

		getSupportActionBar().setHomeButtonEnabled(true);
		getSupportActionBar().setDisplayHomeAsUpEnabled(false);

		viewPager = (ViewPager) findViewById(R.id.viewpager);
		viewPager
				.setAdapter(new TabsAdapterUsuario(getSupportFragmentManager()));

		tabLayout = (TabLayout) findViewById(R.id.appbartabs);
		tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
		tabLayout.setTabMode(TabLayout.MODE_FIXED);
		tabLayout.setupWithViewPager(viewPager);

		if (Promo) {
			viewPager.setCurrentItem(1);
		}

		viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
			@Override
			public void onPageScrolled(int position, float positionOffset,
					int positionOffsetPixels) {
				invalidateOptionsMenu();
			}

			@Override
			public void onPageSelected(int position) {

			}

			@Override
			public void onPageScrollStateChanged(int state) {

			}
		});

	}

	public class MyTask extends AsyncTask<String, String, String> {

		@Override
		protected void onPreExecute() {
			dialog = new ProgressDialog(TabsUsuario.this);
			dialog.setMessage("Enviando Datos...");
			dialog.show();
		}

		@Override
		protected String doInBackground(String... params) {

			// inicia el metodo que trae los datos según su url
			// retorna string sb.toestring()

			String content = BL.getBl().getConnManager().traerEmpresa();
			return content; // retorna string al metodo onPostExecute
		}

		@Override
		protected void onPostExecute(String result) {

			parseFeed(result);

			Toast.makeText(TabsUsuario.this, result, Toast.LENGTH_SHORT).show();
			dialog.dismiss();

		}

		@Override
		protected void onProgressUpdate(String... values) {
			// textViewDato.append(values[0]+"\n");
		}
	}

	public static ArrayList<Empresa> parseFeed(String content) {

		try {
			JSONArray ar = new JSONArray(content);
			ArrayList<Empresa> datasJson = new ArrayList<>();

			for (int i = 0; i < ar.length(); i++) {

				JSONObject obj = ar.getJSONObject(i);
				Empresa empresa = new Empresa(0, obj.getString("EMPRESA"),
						obj.getString("LONGITUD"), obj.getString("LATITUD"),
						null, obj.getString("URL_LOGO"));

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


	
	
	

	
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {

		if (keyCode == KeyEvent.KEYCODE_BACK) {
			return false;

		}
		return false;

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main_usuario, menu);

		 menu.getItem(0).setVisible(false);
		
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		int id = item.getItemId();
		// noinspection SimplifiableIfStatement
		if (id == R.id.action_administrador) {

			Intent usuario = new Intent(TabsUsuario.this, TabsAdmEmpresa.class);
			startActivity(usuario);

			return true;
		}
		if (id == R.id.action_cerrar) {

			
			//finish();
			
			GeneralLogic.close(TabsUsuario.this);
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}