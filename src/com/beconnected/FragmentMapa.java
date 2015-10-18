package com.beconnected;

import java.util.ArrayList;

import com.beconnected.databases.BL;
import com.beconnected.databases.Empresa;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;

/**
 * A simple {@link Fragment} subclass. Use the
 * {@link FragmentGenerarNoticia.sgoliver.android.toolbartabs.Fragment1#newInstance}
 * factory method to create an instance of this fragment.
 */
public class FragmentMapa extends Fragment {
	private GoogleMap mapa;
	public static double latCba = -31.400000000000000;
	public static double longCba = -64.183300000000000;
	private double mi_latitude;
	private double mi_longitude;
	private ArrayList<Empresa> empresaArray;
	private GPSTracker gps;

	public static FragmentMapa newInstance() {
		FragmentMapa fragment = new FragmentMapa();
		return fragment;
	}

	public FragmentMapa() {
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
		return inflater.inflate(R.layout.fragment_mapa, container, false);

	}

	@Override
	public void onViewStateRestored(Bundle savedInstanceState) {
		super.onViewStateRestored(savedInstanceState);
		Log.i("leo", "onViewStateRestored");
	}

	@Override
	public void onPause() {

		Fragment fragment = (getChildFragmentManager()
				.findFragmentById(R.id.map));
		FragmentTransaction ft = getActivity().getSupportFragmentManager()
				.beginTransaction();
		ft.remove(fragment);
		ft.commit();

		super.onPause();
	}

	private void init() {

		gps = new GPSTracker(getActivity());

		if (gps.canGetLocation()) {
			mi_latitude = gps.getLatitude();
			mi_longitude = gps.getLongitude();

		} else {
			gps.showSettingsAlert();
		}

		empresaArray = BL.getBl().selectListaEmpresaUsuario();
		mapa = ((SupportMapFragment) getChildFragmentManager()
				.findFragmentById(R.id.map)).getMap();

		mapa.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(latCba,
				longCba), 14.0f));
		// zoom long y lat de bariloche
		CameraPosition cameraPosition = new CameraPosition.Builder()
				.target(new LatLng(mi_latitude, mi_longitude)).zoom(15).build();
		mapa.animateCamera(CameraUpdateFactory
				.newCameraPosition(cameraPosition));

		mapa.addMarker(new MarkerOptions()
				.position(new LatLng(mi_latitude, mi_longitude))
				.title("Mi Posicion")
				.icon(BitmapDescriptorFactory
						.defaultMarker(BitmapDescriptorFactory.HUE_BLUE)));

		for (int i = 0; i < empresaArray.size(); i++) {

			mapa.addMarker(new MarkerOptions().position(
					new LatLng(
							Double.valueOf(empresaArray.get(i).getLATIDUD()),
							Double.valueOf(empresaArray.get(i).getLONGITUD())))
					.title(empresaArray.get(i).getEMPRESA()));
		}

	}

}
