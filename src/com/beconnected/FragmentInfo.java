package com.beconnected;

import java.util.ArrayList;

import com.beconnected.databases.BL;
import com.beconnected.databases.Info;

import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class FragmentInfo extends Fragment {

	private TextView editTextSomos;
	private TextView editTextContacto;
	private ArrayList<Info> arrayInfo;
	private  Typeface cFont;
	public static FragmentInfo newInstance() {
		FragmentInfo fragment = new FragmentInfo();
		return fragment;
	}

	public FragmentInfo() {
		// Required empty public constructor
	}

	@Override
	public void onActivityCreated(Bundle state) {
		super.onActivityCreated(state);

		init();
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		return inflater.inflate(R.layout.fragment_info, container, false);

	}

	private void init() {


     cFont = Typeface.createFromAsset(getActivity().getAssets(), "fonts/NEUROPOL.ttf");
		arrayInfo = BL.getBl().selectListaInfoUsuario();

		editTextSomos = (TextView) getView().findViewById(R.id.editTextSomos);
		editTextSomos.setTypeface(cFont);
		editTextContacto = (TextView) getView().findViewById(		
				R.id.editTextContacto);
		editTextContacto.setTypeface(cFont);
		if (arrayInfo.size() != 0) {

			editTextSomos.setText(arrayInfo.get(0).getSOMOS().toString());
			editTextContacto.setText(arrayInfo.get(0).getCONTACTO().toString());
		}
	}
}