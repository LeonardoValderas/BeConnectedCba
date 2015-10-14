package com.beconnected.adm;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;

import com.beconnected.R;
import com.beconnected.TabsUsuario;
import com.beconnected.databases.BL;
import com.beconnected.databases.DL;
import com.beconnected.databases.Empresa;
import com.beconnected.databases.Request;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap.CompressFormat;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.NavUtils;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Base64;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class TabsAdmEmpresa extends AppCompatActivity {

	private Toolbar toolbar;
	private ActionBarDrawerToggle drawerToggle;
	private ViewPager viewPager;
	private TabLayout tabLayout;
	private ProgressDialog dialog;
	private int restarMap = 0;
	TextView txtAbSubTitulo;
	private ArrayList<Empresa> empresaArray;
	private SubirDatos subirDatos;

	// private TextView txtAbSubTitulo;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.tabs_usuario);

		DL.getDl().setSqliteConnection(this);
		BL.getBl().crearTablasBDAmd();
		BL.getBl().creaDirectorios();

		// restarMap = getIntent().getIntExtra("restart", 0);
		init();
	}

	public void init() {

		// Toolbar
		toolbar = (Toolbar) findViewById(R.id.toolbar);
		setSupportActionBar(toolbar);

		getSupportActionBar().setDisplayShowTitleEnabled(false);

		getSupportActionBar().setHomeButtonEnabled(true);
		getSupportActionBar().setDisplayHomeAsUpEnabled(false);

		// txtAbSubTitulo = (TextView)
		// toolbar.findViewById(R.id.txtAbSubTitulo);
		// txtAbSubTitulo.setText("EMPRESA");

		viewPager = (ViewPager) findViewById(R.id.viewpager);
		viewPager.setAdapter(new TabsAdapterAdmEmpresa(
				getSupportFragmentManager()));

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
				Fragment fragmentTab = null;
				if (position == 1) {
					fragmentTab = FragmentAdmEmpresaEditar.newInstance();

				}

			}

			@Override
			public void onPageScrollStateChanged(int state) {

			}
		});

	}

	/*
	 * 
	 * que envia datos String content = InicialConnection.setData(params[0]);
	 * utiliza resquestData()
	 */

//	public class MyTaskSet extends AsyncTask<String, String, String> {
//
//		@Override
//		protected void onPreExecute() {
//			// progressBar1.setVisibility(View.VISIBLE);
//			dialog = new ProgressDialog(TabsAdmEmpresa.this);
//			dialog.setMessage("Enviando Datos...");
//			dialog.show();
//			// textViewDato.setText("Comienzo");
//
//		}
//
//		@Override
//		protected String doInBackground(String... params) {
//
//			// inicia el metodo que trae los datos según su url
//			// retorna string sb.toestring()
//
//			Request p = new Request();
//			String content = null;
//			empresaArray = BL.getBl().selectListaEmpresa();
//			for (int i = 0; i < empresaArray.size(); i++) {
//				p.setMethod("POST");
//				p.setParametrosDatos("empresa", empresaArray.get(i)
//						.getEMPRESA().toString());
//				p.setParametrosDatos("longitud", empresaArray.get(i)
//						.getLONGITUD().toString());
//				p.setParametrosDatos("latitud", empresaArray.get(i)
//						.getLATIDUD().toString());
//										
//				p.setParametrosDatos("logo", Base64.encodeToString(empresaArray.get(i).getLOGO(), Base64.NO_WRAP));
//				
//		
//				content = BL.getBl().getConnManager().setData(p);
//			}
//
//			
//			return content; // retorna string al metodo onPostExecute
//
//		}
//
//		@Override
//		protected void onPostExecute(String result) {
//
//			// para listView
//			// listEquipo = parseFeed(result);
//
//			// textViewDato.setText(result);
//
//			Toast.makeText(TabsAdmEmpresa.this, result, Toast.LENGTH_SHORT)
//					.show();
//
//			//
//			// upDateDisplay();
//			// task.remove(this);
//			// if(task.size()==0)
//			// {
//			// progressBar1.setVisibility(View.INVISIBLE);
//			// }
//			//
//			// if (result==null) {
//			// // Toast.makeText(this, "Sin datos", Toast.LENGTH_LONG).show();
//			// return;
//			// }else{
//			//
//			// datasJson = ListaJson.parseFeed(result);
//			// }
//			//
//			//
//			//
//			// }
//			dialog.dismiss();
//		}
//
//		@Override
//		protected void onProgressUpdate(String... values) {
//			// textViewDato.append(values[0]+"\n");
//		}
//	}

//	private void resquestData() {
//		MyTaskSet taskset = new MyTaskSet();
//		taskset.execute("");
//
//	}

	
	
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);

		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		// return true;
		// if (drawerToggle.onOptionsItemSelected(item)) {
		// return true;
		// }

		int id = item.getItemId();
		// noinspection SimplifiableIfStatement
		if (id == R.id.action_usuario) {

			Intent usuario = new Intent(TabsAdmEmpresa.this, TabsUsuario.class);
			startActivity(usuario);

			return true;
		}
		if (id == R.id.action_promo) {
			Intent promo = new Intent(TabsAdmEmpresa.this, TabsAdmPromo.class);
			startActivity(promo);

			return true;
		}
		if (id == R.id.action_info) {
			Intent info = new Intent(TabsAdmEmpresa.this, TabsAdmInfo.class);
			startActivity(info);

			return true;
		}
		if (id == R.id.action_subir) {

			//resquestData();
			
//			 UploadImage ui = new UploadImage();
//		        ui.execute();
			
			subirDatos =new SubirDatos(TabsAdmEmpresa.this);
			subirDatos.enviarDatos();
			
			
			
			return true;
		}

		if (id == R.id.action_cerrar) {

			return true;
		}
		//
		// if (id == R.id.action_subir) {
		//
		// return true;
		// }
		//
		// if (id == R.id.action_eliminar) {
		//
		// return true;
		// }
		// if (id == R.id.action_adeful) {
		//
		// return true;
		// }
		//
		// if (id == R.id.action_lifuba) {
		//
		// return true;
		// }
		//
		// if (id == R.id.action_puesto) {
		//
		// return true;
		//
		// }
		// if (id == R.id.action_posicion) {
		//
		// return true;
		// }
		//
		// if (id == R.id.action_cargo) {
		//
		// return true;
		// }
		//
		// if (id == android.R.id.home) {
		//
		// NavUtils.navigateUpFromSameTask(this);
		//
		// return true;
		// }
		return super.onOptionsItemSelected(item);
		// }
	}
}