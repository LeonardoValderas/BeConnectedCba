package com.beconnected;



import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.beconnected.adm.TabsAdmEmpresa;
import com.beconnected.databases.BL;
import com.beconnected.databases.DL;
import com.beconnected.databases.Empresa;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.NavUtils;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

public class TabsUsuario extends AppCompatActivity {

	private Toolbar toolbar;
	private ActionBarDrawerToggle drawerToggle;
	private ViewPager viewPager;
	private TabLayout tabLayout;
	private int restarMap = 0;
	private TextView txtAbSubTitulo;
	private ProgressDialog dialog;
	// private TextView txtAbSubTitulo;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.tabs_usuario);

//		DL.getDl().setSqliteConnection(this);
//		BL.getBl().crearTablasBD();
//		BL.getBl().creaDirectorios();

//		restarMap = getIntent().getIntExtra("restart", 0);
		init();
		// new MyTask().execute("");
	}

	public void init() {

		// Toolbar
		toolbar = (Toolbar) findViewById(R.id.toolbar);
		setSupportActionBar(toolbar);

		getSupportActionBar().setDisplayShowTitleEnabled(false);

		getSupportActionBar().setHomeButtonEnabled(true);
		getSupportActionBar().setDisplayHomeAsUpEnabled(false);

//		 txtAbSubTitulo = (TextView)
//		 toolbar.findViewById(R.id.txtAbSubTitulo);
//		 txtAbSubTitulo.setText("Bienvenido");

		viewPager = (ViewPager) findViewById(R.id.viewpager);
		viewPager
				.setAdapter(new TabsAdapterUsuario(getSupportFragmentManager()));
	
		
		 
		tabLayout = (TabLayout) findViewById(R.id.appbartabs);
		tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
		tabLayout.setTabMode(TabLayout.MODE_FIXED);
		tabLayout.setupWithViewPager(viewPager);
		
		

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

			//inicia el metodo que trae los datos según su url
			//retorna string sb.toestring()
			
			  String content =  BL.getBl().getConnManager().traerEmpresa();
			  return content;  //retorna string al metodo onPostExecute
		}

		@Override
		protected void onPostExecute(String result) {
			
			
			
			//para listView
			parseFeed(result);
		
			Toast.makeText(TabsUsuario.this, result, Toast.LENGTH_SHORT)
			.show();
			dialog.dismiss();
		//	textViewDato.setText(result);
			
			
			
			
			//
			// upDateDisplay();
			// task.remove(this);
			// if(task.size()==0)
			// {
			// progressBar1.setVisibility(View.INVISIBLE);
			// }
			//
			// if (result==null) {
			// // Toast.makeText(this, "Sin datos", Toast.LENGTH_LONG).show();
			// return;
			// }else{
			//
			// datasJson = ListaJson.parseFeed(result);
			// }
			//
			//
			//
			// }
		}

		@Override
		protected void onProgressUpdate(String... values) {
			//textViewDato.append(values[0]+"\n");
		}
	}
	
	
	
	
	
	public static ArrayList<Empresa> parseFeed(String content)	{
		
		try {
			JSONArray ar = new JSONArray(content);
			ArrayList<Empresa> datasJson = new ArrayList<>();
			
			for (int i = 0; i < ar.length(); i++) {
				
			JSONObject obj = ar.getJSONObject(i);
			Empresa empresa = new Empresa(0, obj.getString("EMPRESA"), obj.getString("LONGITUD"), obj.getString("LATITUD"), null,obj.getString("URL_LOGO"));
			
			BL.getBl().insertarEmpresa(empresa);
			
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
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main_usuario, menu);

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

		
			return true;
		}
		return super.onOptionsItemSelected(item);
//	}
	}
}