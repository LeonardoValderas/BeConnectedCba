package com.beconnected;

import java.util.ArrayList;

import com.beconnected.adm.TabsAdmEmpresa;
import com.beconnected.databases.BL;
import com.beconnected.databases.Empresa;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.Projection;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.internal.Point;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

/**
 * A simple {@link Fragment} subclass. Use the
 * {@link FragmentGenerarNoticia.sgoliver.android.toolbartabs.Fragment1#newInstance}
 * factory method to create an instance of this fragment.
 */
public class FragmentMapa extends Fragment {
	private GoogleMap mapa;
	public static double latCba = -31.400000000000000;
	public static double longCba = -64.183300000000000;
	private double mi_latitude ;
	private double mi_longitude ;
	private RecyclerView recycleViewCancha;
	private EditText editTextNombre;
	private ImageButton imageButtonCancha;
	private FloatingActionButton botonFloating;
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
	public void onAttach (Activity activity) {
		super.onAttach(activity);
		Log.i("leo", "onAttach");
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
	public void onViewStateRestored (Bundle savedInstanceState) {
		super.onViewStateRestored(savedInstanceState);
		Log.i("leo", "onViewStateRestored");
	}
	
	
	@Override
	public void onStart () {
		
		Log.i("leo", "onStart");
		super.onStart();
	
	}
	
	@Override
	public void onResume () {
		Log.i("leo", "onResume");
		super.onResume();
		
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
	
	
	@Override
	public void onStop () {
		Log.i("leo", "onStop");
		super.onStop();
		
	}

	@Override
	public void onDestroyView () {
		Log.i("leo", "onDestroyView");
		super.onDestroyView();
	
	}

	@Override
	public void onDestroy () {
		Log.i("leo", "onDestroy");
		super.onDestroy();
	
	}

	@Override
	public void onDetach () {
		Log.i("leo", "onDetach");
		super.onDetach();
	
	}

	private void init() {

		gps = new GPSTracker(getActivity());
		
		if(gps.canGetLocation()) {
			 mi_latitude = gps.getLatitude();
			 mi_longitude = gps.getLongitude();
			
			
		} else {
			gps.showSettingsAlert();
		}
		
		
		empresaArray = BL.getBl().selectListaEmpresaUsuario();
		mapa = ((SupportMapFragment) getChildFragmentManager()
				.findFragmentById(R.id.map)).getMap();

		// tvAddress = (TextView) findViewById(R.id.tvAddress);

		// LatLng a = new LatLng(latCba,longCba);

		mapa.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(latCba,
				longCba), 14.0f));
		// zoom long y lat de bariloche
		 CameraPosition cameraPosition = new
		 CameraPosition.Builder().target(new LatLng(mi_latitude, mi_longitude)).zoom(15)
		 .build();
		 mapa.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));

		 mapa.addMarker(new MarkerOptions().position(new LatLng(mi_latitude, mi_longitude))
					.title("Mi Posicion")
					.icon(BitmapDescriptorFactory
							.defaultMarker(BitmapDescriptorFactory.HUE_BLUE)));
		 
		// // click en el mapa crea marker y muestra nombre de la calle
		// mapa.setOnMapClickListener(new OnMapClickListener() {
		// public void onMapClick(LatLng point) {
		// Projection proj = mapa.getProjection();
		// Point coord = proj.toScreenLocation(point);

		// mapa.clear();

		for (int i = 0; i < empresaArray.size(); i++) {

			mapa.addMarker(new MarkerOptions().position(new LatLng(Double
					.valueOf(empresaArray.get(i).getLATIDUD()), Double
					.valueOf(empresaArray.get(i).getLONGITUD())))
					.title(empresaArray.get(i).getEMPRESA()));
			

		}

		// mapa.addMarker(new MarkerOptions().position(new
		// LatLng(point.latitude, point.longitude))
		// .title("Dirección: \n" + tvAddress.getText().toString()));

		// LocationAddress locationAddress = new LocationAddress();
		// locationAddress.getAddressFromLocation(point.latitude,
		// point.longitude, getApplicationContext(),
		// new GeocoderHandler());
		//
		// }
		// });

		// mapa.setOnMapLongClickListener(new OnMapLongClickListener() {
		//
		// @Override
		// public void onMapLongClick(LatLng latLng) {
		//
		// // mapa.clear();
		//
		// }
		// });
		// }
		// mapa.setOnCameraChangeListener(new OnCameraChangeListener() {
		// public void onCameraChange(CameraPosition position) {
		//
		//
		// Toast.makeText(
		// MainActivity.this,
		// "Cambio Cámara\n" +
		// "Lat: " + position.target.latitude + "\n" +
		// "Lng: " + position.target.longitude + "\n" +
		// "Zoom: " + position.zoom + "\n" +
		// "Orientación: " + position.bearing + "\n" +
		// "Ángulo: " + position.tilt,
		// Toast.LENGTH_LONG).show();
		// }
		// });

		// /***
		// * imageButton que busca imagen que activa el mapa
		// */
		// imageButtonCancha = (ImageButton) getView().findViewById(
		// R.id.imageButtonEquipo_Cancha);
		// imageButtonCancha.setBackground(ResourcesCompat.getDrawable(
		// getResources(), R.drawable.ic_mapa_icono, null));
		// imageButtonCancha.setOnClickListener(new View.OnClickListener() {
		//
		// @Override
		// public void onClick(View v) {
		// // TODO Auto-generated method stub
		// //
		// Intent mapa = new Intent(getActivity(), MapaCancha.class);
		// startActivity(mapa);
		//
		// }
		// });
		//
		// botonFloating = (FloatingActionButton) getView().findViewById(
		// R.id.botonFloating);
		//
		// botonFloating.setVisibility(View.INVISIBLE);
		//
		// editTextNombre = (EditText) getView().findViewById(
		// R.id.editTextDescripcion);
		// editTextNombre.setVisibility(View.GONE);
		//
		// recycleViewCancha = (RecyclerView) getView().findViewById(
		// R.id.recycleViewGeneral);
		//
		// recyclerViewLoadCancha();
		//
		// recycleViewCancha.addOnItemTouchListener(new RecyclerTouchListener(
		// getActivity(), recycleViewCancha, new ClickListener() {
		//
		// @Override
		// public void onLongClick(View view, final int position) {
		//
		// alertMenu = new AlertsMenu(getActivity(), "ALERTA",
		// "Desea eliminar la cancha?", null, null);
		// alertMenu.btnAceptar.setText("Aceptar");
		// alertMenu.btnCancelar.setText("Cancelar");
		//
		// alertMenu.btnAceptar
		// .setOnClickListener(new View.OnClickListener() {
		//
		// @Override
		// public void onClick(View v) {
		// // TODO Auto-generated method stub
		// BL.getBl().eliminarCanchaAdeful(
		// canchaAdefulArray.get(position)
		// .getID_CANCHA());
		// recyclerViewLoadCancha();
		//
		// Toast.makeText(
		// getActivity(),
		// "Cancha Eliminada Correctamente",
		// Toast.LENGTH_SHORT).show();
		//
		// alertMenu.alertDialog.dismiss();
		//
		// }
		// });
		// alertMenu.btnCancelar
		// .setOnClickListener(new View.OnClickListener() {
		//
		// @Override
		// public void onClick(View v) {
		// // TODO Auto-generated method stub
		// alertMenu.alertDialog.dismiss();
		// }
		// });
		//
		// }
		//
		// @Override
		// public void onClick(View view, int position) {
		// // TODO Auto-generated method stub
		//
		// Intent mapa = new Intent(getActivity(),
		// MapaCancha.class);
		// mapa.putExtra("actualizar", true);
		// mapa.putExtra("nombre", canchaAdefulArray.get(position)
		// .getNOMBRE());
		// mapa.putExtra("longitud",
		// canchaAdefulArray.get(position).getLONGITUD());
		// mapa.putExtra("latitud", canchaAdefulArray
		// .get(position).getLATITUD());
		// mapa.putExtra("direccion",
		// canchaAdefulArray.get(position).getDIRECCION());
		// mapa.putExtra("posicion", position);
		//
		// startActivity(mapa);
		//
		// }
		// }));
		//
		// }
		//
		// public void recyclerViewLoadCancha() {
		// recycleViewCancha.setLayoutManager(new LinearLayoutManager(
		// getActivity(), LinearLayoutManager.VERTICAL, false));
		// recycleViewCancha.addItemDecoration(new DividerItemDecoration(
		// getActivity(), DividerItemDecoration.VERTICAL_LIST));
		// recycleViewCancha.setItemAnimator(new DefaultItemAnimator());
		// canchaAdefulArray = BL.getBl().selectListaCanchaAdeful();
		// adaptadorCancha = new AdaptadorCancha(canchaAdefulArray);
		// adaptadorCancha.notifyDataSetChanged();
		// recycleViewCancha.setAdapter(adaptadorCancha);
		//
		// }
		//
		// /**
		// * Metodo click item recycler
		// *
		// * @author LEO
		// *
		// */
		//
		// public static interface ClickListener {
		//
		// public void onClick(View view, int position);
		//
		// public void onLongClick(View view, int position);
		//
		// }
		//
		// static class RecyclerTouchListener implements
		// RecyclerView.OnItemTouchListener {
		//
		// private GestureDetector detector;
		// private ClickListener clickListener;
		//
		// public RecyclerTouchListener(Context context,
		// final RecyclerView recyclerView,
		// final ClickListener clickListener) {
		// this.clickListener = clickListener;
		// detector = new GestureDetector(context,
		// new GestureDetector.SimpleOnGestureListener() {
		//
		// @Override
		// public boolean onSingleTapUp(MotionEvent e) {
		// return true;
		// }
		//
		// @Override
		// public void onLongPress(MotionEvent e) {
		// View child = recyclerView.findChildViewUnder(
		// e.getX(), e.getY());
		// if (child != null && clickListener != null) {
		// clickListener.onLongClick(child,
		// recyclerView.getChildPosition(child));
		// }
		// }
		// });
		//
		// }
		//
		// @Override
		// public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e)
		// {
		// // TODO Auto-generated method stub
		// View child = rv.findChildViewUnder(e.getX(), e.getY());
		// if (child != null && clickListener != null
		// && detector.onTouchEvent(e)) {
		// clickListener.onClick(child, rv.getChildPosition(child));
		// }
		// return false;
		// }
		//
		// @Override
		// public void onTouchEvent(RecyclerView rv, MotionEvent e) {
		//
		// }
		//
		// @Override
		// public void onRequestDisallowInterceptTouchEvent(boolean arg0) {
		// // TODO Auto-generated method stub
		//
		// }
		//
		// }
	}

	public boolean isGpsActivo() {

		boolean gps = true;

		// LocationManager locationManager = (LocationManager)
		// .getSystemService( getActivity().LOCATION_SERVICE);
		//
		// if (!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)
		// ||
		// !locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER))
		// {
		//
		// gps = false;
		// }
		//
		return gps;

	}

	// private class GeocoderHandler extends Handler {
	// @Override
	// public void handleMessage(Message message) {
	// String locationAddress;
	// switch (message.what) {
	// case 1:
	// Bundle bundle = message.getData();
	// locationAddress = bundle.getString("address");
	// break;
	// default:
	// locationAddress = null;
	// }
	// tvAddress.setText(locationAddress);
	// }
	// }

}
