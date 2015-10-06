package com.beconnected;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.Spanned;

public class TabsAdapterUsuario extends FragmentPagerAdapter {

	final int PAGE_COUNT = 3;
	private String tabTitles[] = new String[] { "MAPA", "PROMOS", "INFO"};
	
	public TabsAdapterUsuario(FragmentManager fm) {
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
	public CharSequence getPageTitle(int position) {
		// Generate title based on item position
////		tabTitles[position];
//		SpannableString spannableString = new SpannableString("");
//		spannableString.setSpan(tabTitles[position], 0, tabTitles[position].length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
		return tabTitles[position];
	}
}
