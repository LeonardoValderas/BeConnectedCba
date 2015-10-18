package com.beconnected.adm;

import com.beconnected.R;
import com.beconnected.TabsUsuario;
import com.beconnected.databases.GeneralLogic;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;


public class TabsAdmPromo extends AppCompatActivity {

	private Toolbar toolbar;
	private ViewPager viewPager;
	private TabLayout tabLayout;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.tabs_usuario);

		init();
	}

	public void init() {

		// Toolbar
		toolbar = (Toolbar) findViewById(R.id.toolbar);
		setSupportActionBar(toolbar);

		getSupportActionBar().setDisplayShowTitleEnabled(false);

		getSupportActionBar().setHomeButtonEnabled(true);
		getSupportActionBar().setDisplayHomeAsUpEnabled(false);

		viewPager = (ViewPager) findViewById(R.id.viewpager);
		viewPager.setAdapter(new TabsAdapterAdmPromo(
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

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);

		// menu.getItem(0).setVisible(false);//usuario
		// menu.getItem(1).setVisible(false);//empresa
		   menu.getItem(2).setVisible(false);//promo
		//menu.getItem(3).setVisible(false);// info
		//menu.getItem(4).setVisible(false);// sincro
		//menu.getItem(5).setVisible(false);// cerrar
	
		
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {

		int id = item.getItemId();
		// noinspection SimplifiableIfStatement
		if (id == R.id.action_usuario) {

			Intent usuario = new Intent(TabsAdmPromo.this, TabsUsuario.class);
			startActivity(usuario);

			return true;
		}

		if (id == R.id.action_empresa) {
			Intent promo = new Intent(TabsAdmPromo.this, TabsAdmEmpresa.class);
			startActivity(promo);

			return true;
		}
		if (id == R.id.action_info) {
			Intent info = new Intent(TabsAdmPromo.this, TabsAdmInfo.class);
			startActivity(info);

			return true;
		}

		if (id == R.id.action_sincro) {

			Intent sincro = new Intent(TabsAdmPromo.this,
					SplashActivityAdm.class);
			startActivity(sincro);
			return true;
		}

		if (id == R.id.action_cerrar) {

			GeneralLogic.close(TabsAdmPromo.this);

			return true;
		}

		return super.onOptionsItemSelected(item);
	}
}