package com.beconnected.adm;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

public class TabsAdapterAdmPromo extends FragmentPagerAdapter {

	final int PAGE_COUNT = 2;
	
	String crear = "CREAR";
	
	
	private String tabTitles[] = new String[] { "CREAR", "EDITAR" };

	
	public TabsAdapterAdmPromo(FragmentManager fm) {
		super(fm);
	}

	@Override
	public int getCount() {
		return PAGE_COUNT;
	}

	@Override
	public Fragment getItem(int position) {

		Fragment fragmentTab = null;

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
	public CharSequence getPageTitle(int position) {
		
		return tabTitles[position];
	}
}
