package com.beconnected;

import java.util.ArrayList;

import com.beconnected.databases.BL;
import com.beconnected.databases.Info;


import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

public class FragmentInfo extends Fragment {

	private TextView editTextSomos;
	private TextView editTextContacto;
	private ArrayList<Info> arrayInfo;

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
		return inflater.inflate(R.layout.fragment_info, container,
				false);

	}

	private void init() {

		
		arrayInfo = BL.getBl().selectListaInfo();
		
		editTextSomos =(TextView)getView().findViewById(R.id.editTextSomos);
		editTextContacto=(TextView)getView().findViewById(R.id.editTextContacto);
	
		if(arrayInfo.size()!=0)
		{
		
		editTextSomos.setText(arrayInfo.get(0).getSOMOS().toString());
		editTextContacto.setText(arrayInfo.get(0).getCONTACTO().toString());
		}
			

}
}