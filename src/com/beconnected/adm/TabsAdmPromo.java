package com.beconnected.adm;

import com.beconnected.R;
import com.beconnected.TabsUsuario;
import com.beconnected.databases.GeneralLogic;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

public class TabsAdmPromo extends AppCompatActivity implements Communicator {

	private Toolbar toolbar;
	private ViewPager viewPager;
	private TabLayout tabLayout;
	private FragmentTransaction mCurTransaction;
	private static final String TAG = "FragmentPagerAdapter";
	private static final boolean DEBUG = false;
	final int PAGE_COUNT = 2;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.tabs_usuario);

		init();
	}

	public Parcelable saveState() {
		return null;
	}

	public void init() {

		// Toolbar
		toolbar = (Toolbar) findViewById(R.id.toolbar);
		setSupportActionBar(toolbar);

		getSupportActionBar().setDisplayShowTitleEnabled(false);

		getSupportActionBar().setHomeButtonEnabled(true);
		getSupportActionBar().setDisplayHomeAsUpEnabled(false);

		viewPager = (ViewPager) findViewById(R.id.viewpager);
		viewPager.setOffscreenPageLimit(PAGE_COUNT-1);
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

	public class TabsAdapterAdmPromo extends FragmentPagerAdapter {
		private FragmentManager fm;
		//final int PAGE_COUNT = 2;

		private String[] tabTitles = new String[] { "CREAR", "EDITAR" };
		String crear = "CREAR";

		public TabsAdapterAdmPromo(FragmentManager fm) {
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
				fragmentTab = FragmentAdmPromo.newInstance();

				break;
			case 1:
				fragmentTab = FragmentAdmPromoEditar.newInstance();

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

	@Override
	public void refresh() {
		// TODO Auto-generated method stub

		FragmentManager manager = getSupportFragmentManager();
		FragmentAdmPromoEditar fragment = (FragmentAdmPromoEditar) manager
				.findFragmentByTag("android:switcher:" + viewPager.getId()
						+ ":" + 1);

		fragment.recyclerViewLoadPromo();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);

		// menu.getItem(0).setVisible(false);//usuario
		// menu.getItem(1).setVisible(false);//empresa
		menu.getItem(2).setVisible(false);// promo
		// menu.getItem(3).setVisible(false);// info
		// menu.getItem(4).setVisible(false);// sincro
		// menu.getItem(5).setVisible(false);// cerrar

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