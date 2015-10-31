package com.beconnected;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import com.beconnected.adm.TabsAdmEmpresa;
import com.beconnected.adm.TabsAdmPromo.TabsAdapterAdmPromo;
import com.beconnected.databases.BL;
import com.beconnected.databases.ControladorUsuario;
import com.beconnected.databases.Empresa;
import com.beconnected.databases.GeneralLogic;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

public class TabsUsuario extends AppCompatActivity {
	List<Fragment> listFragments;
	private Toolbar toolbar;
	private ViewPager viewPager;
	private TabLayout tabLayout;
	private ProgressDialog dialog;
	private boolean Promo = false;
	private boolean battery = false;
	private FragmentTransaction mCurTransaction;
	private static final String TAG = "FragmentPagerAdapter";
	private static final boolean DEBUG = false;
	final int PAGE_COUNT = 3;
	int viewpagerid;
	private ControladorUsuario controladorUsuario;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.tabs_usuario);
		
	
		controladorUsuario = new ControladorUsuario(TabsUsuario.this);

		// Toolbar
		
		toolbar = (Toolbar) findViewById(R.id.toolbar);
		setSupportActionBar(toolbar);

		getSupportActionBar().setDisplayShowTitleEnabled(false);

		getSupportActionBar().setHomeButtonEnabled(true);
		getSupportActionBar().setDisplayHomeAsUpEnabled(false);

		if (savedInstanceState != null){
		       viewpagerid =savedInstanceState.getInt("viewpagerid", -1 );  
			
		      
			 viewPager = (ViewPager) findViewById(R.id.viewpager);
			 
			 viewPager.setOffscreenPageLimit(PAGE_COUNT-1);
			    if (viewpagerid != -1 ){
			    	viewPager.setId(viewpagerid);
			    }else{
			        viewpagerid=viewPager.getId();
			    }
			    viewPager.setAdapter( new TabsAdapterUsuario (getSupportFragmentManager()));
			}else{
			
			viewPager = (ViewPager) findViewById(R.id.viewpager);
			viewPager.setOffscreenPageLimit(PAGE_COUNT-1);
			viewPager.setAdapter(new TabsAdapterUsuario(
					getSupportFragmentManager()));

			}
	
	
		tabLayout = (TabLayout) findViewById(R.id.appbartabs);
		tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
		tabLayout.setTabMode(TabLayout.MODE_FIXED);
		tabLayout.setupWithViewPager(viewPager);
		
		
		
		init();
		
	
		
	}

	
	

	

	
	public void init() {
		battery = getIntent().getBooleanExtra("battery", false);
		Promo = getIntent().getBooleanExtra("PROMO", false);
		
		if (Promo) {
			viewPager.post(new Runnable() {
		        @Override
		        public void run() {
		            viewPager.setCurrentItem(1);
		            getIntent().removeExtra("PROMO"); 
		        }
		        
		    });
			
			}
		
		
		if(battery){
			Intent	bateria = new Intent(getApplicationContext(), Baterry.class);
		
			stopService(bateria);
			//startService(bateria);
		}
		
		

	

		viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
			@Override
			public void onPageScrolled(int position, float positionOffset,
					int positionOffsetPixels) {
				invalidateOptionsMenu();
			}

			@Override
			public void onPageSelected(int position) {

//				if (Promo) {
//					viewPager.setCurrentItem(1);
//				}
			}

			@Override
			public void onPageScrollStateChanged(int state) {

			}
		   
		});

		  Promo=false;
	}

	
	public class TabsAdapterUsuario extends FragmentPagerAdapter  {
		private FragmentManager fm;
		//final int PAGE_COUNT = 3;
		private String tabTitles[] = new String[] { "MAPA", "PROMOS", "INFO" };

		public TabsAdapterUsuario(FragmentManager fm) {
			super(fm);
			this.fm = fm;
		}

		@Override
		public int getCount() {
			return PAGE_COUNT;
		}

				
		@Override
		public Fragment getItem(int position) {
		
			Fragment fragmentTab = fm.findFragmentByTag("android:switcher:"
					+ viewPager.getId() + ":" + getItemId(position));
			// Fragment fragmentTab = null;

			if (fragmentTab != null) {
				return fragmentTab;
			}

			switch (position) {
			case 0:
				fragmentTab = FragmentMapa.newInstance();

				break;
			case 1:
				fragmentTab = FragmentPromos.newInstance();

				break;

			case 2:
				fragmentTab = FragmentInfo.newInstance();

				break;

			}

			return fragmentTab;
		}

		
		@Override
		public Object instantiateItem(View container, int position) {

			if (fm == null) {
				mCurTransaction = fm.beginTransaction();
			}

			// Do we already have this fragment?
			String name = makeFragmentName(container.getId(), position);
			Fragment fragment = fm.findFragmentByTag(name);
			if (fragment != null) {
				if (DEBUG)
					Log.v(TAG, "Attaching item #" + position + ": f="
							+ fragment);
				mCurTransaction.attach(fragment);
			} else {
				fragment = getItem(position);
				if (DEBUG)
					Log.v(TAG, "Adding item #" + position + ": f=" + fragment);
				mCurTransaction.add(container.getId(), fragment,
						makeFragmentName(container.getId(), position));
			}

			return fragment;
		}

		private String makeFragmentName(int viewId, int index) {
			return "android:switcher:" + viewId + ":" + index;
		}
		@Override
		public CharSequence getPageTitle(int position) {

			return tabTitles[position];
		}
	
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

	public  ArrayList<Empresa> parseFeed(String content) {

		try {
			JSONArray ar = new JSONArray(content);
			ArrayList<Empresa> datasJson = new ArrayList<>();

			for (int i = 0; i < ar.length(); i++) {

				JSONObject obj = ar.getJSONObject(i);
				Empresa empresa = new Empresa(0, obj.getString("EMPRESA"),
						obj.getString("LONGITUD"), obj.getString("LATITUD"),
						null, obj.getString("URL_LOGO"));

				
				
				controladorUsuario.abrirBaseDeDatos();
				controladorUsuario.insertEmpresaSplash(empresa);
		
				controladorUsuario.cerrarBaseDeDatos();
				
				//BL.getBl().insertarEmpresaUsuario(empresa);

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