package com.beconnected.adm;



import com.beconnected.R;
import com.beconnected.databases.BL;
import com.beconnected.databases.DL;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.NavUtils;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

public class TabsAdmMapa extends AppCompatActivity {

	private Toolbar toolbar;
	private ActionBarDrawerToggle drawerToggle;
	private ViewPager viewPager;
	private TabLayout tabLayout;
	private int restarMap = 0;
	TextView txtAbSubTitulo;
	// private TextView txtAbSubTitulo;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.tabs_usuario);

		DL.getDl().setSqliteConnection(this);
		BL.getBl().crearTablasBD();
		BL.getBl().creaDirectorios();

//		restarMap = getIntent().getIntExtra("restart", 0);
		init();
	}

	public void init() {

		// Toolbar
		toolbar = (Toolbar) findViewById(R.id.toolbar);
		setSupportActionBar(toolbar);

		getSupportActionBar().setDisplayShowTitleEnabled(false);

		getSupportActionBar().setHomeButtonEnabled(true);
		getSupportActionBar().setDisplayHomeAsUpEnabled(false);

		 txtAbSubTitulo = (TextView)
		 toolbar.findViewById(R.id.txtAbSubTitulo);
		 txtAbSubTitulo.setText("EMPRESA");

		viewPager = (ViewPager) findViewById(R.id.viewpager);
		viewPager
				.setAdapter(new TabsAdapterAdmMapa(getSupportFragmentManager()));
	
		
		 
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
	             if(position==1){
	            	 fragmentTab = FragmentAdmMapaEditar.newInstance();
	            	 
	            	
				}
				

			}

			@Override
			public void onPageScrollStateChanged(int state) {

			}
		});

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
//		if (viewPager.getCurrentItem() == 0) {
//			// menu.getItem(0).setVisible(false);//usuario
//			// menu.getItem(1).setVisible(false);//permiso
//			// menu.getItem(2).setVisible(false);//lifuba
//			menu.getItem(3).setVisible(false);// adeful
//			menu.getItem(4).setVisible(false);// puesto
//			menu.getItem(5).setVisible(false);// posicion
//			menu.getItem(6).setVisible(false);// cargo
//			// menu.getItem(7).setVisible(false);//cerrar
//			menu.getItem(8).setVisible(false);// guardar
//			menu.getItem(9).setVisible(false);// Subir
//			menu.getItem(10).setVisible(false); // eliminar
//			menu.getItem(11).setVisible(false); // consultar
//		} else if (viewPager.getCurrentItem() == 1) {
//			// menu.getItem(0).setVisible(false);//usuario
//			// menu.getItem(1).setVisible(false);//permiso
//			// menu.getItem(2).setVisible(false);//lifuba
//			menu.getItem(3).setVisible(false);// adeful
//			menu.getItem(4).setVisible(false);// puesto
//			menu.getItem(5).setVisible(false);// posicion
//			menu.getItem(6).setVisible(false);// cargo
//			// menu.getItem(7).setVisible(false);//cerrar
//			menu.getItem(8).setVisible(false);// guardar
//			menu.getItem(9).setVisible(false);// Subir
//			menu.getItem(10).setVisible(false); // eliminar
//			menu.getItem(11).setVisible(false); // consultar
//		} else if (viewPager.getCurrentItem() == 2) {
//			// menu.getItem(0).setVisible(false);//usuario
//			// menu.getItem(1).setVisible(false);//permiso
//			// menu.getItem(2).setVisible(false);//lifuba
//			menu.getItem(3).setVisible(false);// adeful
//			menu.getItem(4).setVisible(false);// puesto
//			menu.getItem(5).setVisible(false);// posicion
//			menu.getItem(6).setVisible(false);// cargo
//			// menu.getItem(7).setVisible(false);//cerrar
//			menu.getItem(8).setVisible(false);// guardar
//			menu.getItem(9).setVisible(false);// Subir
//			menu.getItem(10).setVisible(false); // eliminar
//			menu.getItem(11).setVisible(false); // consultar
//		} else if (viewPager.getCurrentItem() == 3) {
//			// menu.getItem(0).setVisible(false);//usuario
//			// menu.getItem(1).setVisible(false);//permiso
//			// menu.getItem(2).setVisible(false);//lifuba
//			menu.getItem(3).setVisible(false);// adeful
//			menu.getItem(4).setVisible(false);// puesto
//			menu.getItem(5).setVisible(false);// posicion
//			menu.getItem(6).setVisible(false);// cargo
//			// menu.getItem(7).setVisible(false);//cerrar
//			menu.getItem(8).setVisible(false);// guardar
//			menu.getItem(9).setVisible(false);// Subir
//			menu.getItem(10).setVisible(false); // eliminar
//			menu.getItem(11).setVisible(false); // consultar
//		}
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
	//	return true;
		// if (drawerToggle.onOptionsItemSelected(item)) {
		// return true;
		// }

//		int id = item.getItemId();
//		// noinspection SimplifiableIfStatement
//		if (id == R.id.action_usuario) {
//
//			Intent usuario = new Intent(TabsAdeful.this,
//					NavigationDrawerUsuario.class);
//			startActivity(usuario);
//
//			return true;
//		}
//
//		if (id == R.id.action_permisos) {
//			return true;
//		}
//
//		if (id == R.id.action_guardar) {
//
//			return true;
//		}
//
//		if (id == R.id.action_subir) {
//
//			return true;
//		}
//
//		if (id == R.id.action_eliminar) {
//
//			return true;
//		}
//		if (id == R.id.action_adeful) {
//
//			return true;
//		}
//
//		if (id == R.id.action_lifuba) {
//
//			return true;
//		}
//
//		if (id == R.id.action_puesto) {
//
//			return true;
//
//		}
//		if (id == R.id.action_posicion) {
//
//			return true;
//		}
//
//		if (id == R.id.action_cargo) {
//
//			return true;
//		}
//
//		if (id == android.R.id.home) {
//
//			NavUtils.navigateUpFromSameTask(this);
//
//			return true;
//		}
		return super.onOptionsItemSelected(item);
//	}
	}
}