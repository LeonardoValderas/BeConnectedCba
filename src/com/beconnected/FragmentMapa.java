package com.beconnected;

import java.lang.reflect.Field;
import java.util.ArrayList;

import com.beconnected.databases.BL;
import com.beconnected.databases.ControladorUsuario;
import com.beconnected.databases.Empresa;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.OnInfoWindowClickListener;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.UiSettings;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import android.app.FragmentManager;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.InflateException;
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
	private SupportMapFragment supportMapFragment; 
	public static double latCba = -31.400000000000000;
	public static double longCba = -64.183300000000000;
	private double mi_latitude;
	private double mi_longitude;
	private ArrayList<Empresa> empresaArray;
	private GPSTracker gps;
	private static View view;
	private ControladorUsuario controladorUsuario;
	
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
		controladorUsuario = new ControladorUsuario(getActivity());
		init();
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		
		 if (view != null) {
	            ViewGroup parent = (ViewGroup) view.getParent();
	            if (parent != null)
	                parent.removeView(view);
	        }
	        try {
	            view = inflater.inflate(R.layout.fragment_mapa, container, false);
	          //  TextView messageTextView = (TextView)view.findViewById(R.id.textView);
	           // messageTextView.setText(message);
	        } catch (InflateException e) {
	            /* map is already there, just return view as it is */
	        }

//	      View v = inflater.inflate(R.layout.myfragment_layout, container, false);


	    return view;
	//	return inflater.inflate(R.layout.fragment_mapa, container, false);

	}
	
	   @Override
	public void onDetach() {
	    super.onDetach();
	    try {
	        Field childFragmentManager = Fragment.class.getDeclaredField("mChildFragmentManager");
	        childFragmentManager.setAccessible(true);
	        childFragmentManager.set(this, null);
	    } catch (NoSuchFieldException e) {
	        throw new RuntimeException(e);
	    } catch (IllegalAccessException e) {
	        throw new RuntimeException(e);
	    }
	}

//	@Override
//	public void onPause() {
//
//		Fragment fragment = (getChildFragmentManager()
//				.findFragmentById(R.id.map));
//		FragmentTransaction ft = getActivity().getSupportFragmentManager()
//				.beginTransaction();
//		ft.remove(fragment);
//		ft.commit();
//
//		super.onPause();
//	}


//	
//	@Override
//	public void onResume() {
//	    super.onResume();
//	    if (mapa == null) {
//	        mapa = supportMapFragment.getMap();
//	        mapa.addMarker(new MarkerOptions().position(new LatLng(0, 0)));
//	    }
//	  }




	private void init() {

		gps = new GPSTracker(getActivity());

		if (gps.canGetLocation()) {
			mi_latitude = gps.getLatitude();
			mi_longitude = gps.getLongitude();

		} else {
			gps.showSettingsAlert();
		}

		controladorUsuario.abrirBaseDeDatos();
		empresaArray=controladorUsuario.selectListaEmpresa();
		controladorUsuario.cerrarBaseDeDatos();
	//	empresaArray = BL.getBl().selectListaEmpresaUsuario();
	
		//1
		supportMapFragment =(SupportMapFragment) getChildFragmentManager()
		.findFragmentById(R.id.map);
		
		
		//2
		if(supportMapFragment==null){
			
			android.support.v4.app.FragmentManager fragment = getFragmentManager();
		        FragmentTransaction fragmentTransaction = fragment.beginTransaction();
		        supportMapFragment = SupportMapFragment.newInstance();
		        fragmentTransaction.replace(R.id.map, supportMapFragment).commit();
			
		}
		 if (supportMapFragment != null)
		    { 
			 supportMapFragment.getMapAsync(new OnMapReadyCallback() {
		    
	            @Override public void onMapReady(GoogleMap mapa) {
	                if (mapa != null) {
	                	
	                	mapa.getUiSettings().setAllGesturesEnabled(true);
	                	mapa.getUiSettings().setMapToolbarEnabled(false);
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
	            					.icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_map))
	            			
	            					.title(empresaArray.get(i).getEMPRESA()));
	            		}
	                
	            		
	            		mapa.setOnInfoWindowClickListener(new OnInfoWindowClickListener()
	                     {   
	                         @Override
	                         public void onInfoWindowClick(Marker arg0) {
	                        	
	                        
	                        	 Uri gmmIntentUri = Uri.parse("geo:"+arg0.getPosition().latitude +","+arg0.getPosition().longitude+"?q="+arg0.getTitle().toString());
	                        	 Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
	                        	 mapIntent.setPackage("com.google.android.apps.maps");
	                        	 startActivity(mapIntent);
	                 
	                         }

	                     });   
	            		
	                }

	            }
	        });
		    }
//		if (mapa==null){
//	
//			
//			
//        mapa = ((SupportMapFragment) getChildFragmentManager()
//				.findFragmentById(R.id.map)).getMap();
//		}
//		mapa.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(latCba,
//				longCba), 14.0f));
//		// zoom long y lat de bariloche
//		CameraPosition cameraPosition = new CameraPosition.Builder()
//				.target(new LatLng(mi_latitude, mi_longitude)).zoom(15).build();
//		mapa.animateCamera(CameraUpdateFactory
//				.newCameraPosition(cameraPosition));
//
//		mapa.addMarker(new MarkerOptions()
//				.position(new LatLng(mi_latitude, mi_longitude))
//				.title("Mi Posicion")
//				.icon(BitmapDescriptorFactory
//						.defaultMarker(BitmapDescriptorFactory.HUE_BLUE)));
//
//		for (int i = 0; i < empresaArray.size(); i++) {
//
//			mapa.addMarker(new MarkerOptions().position(
//					new LatLng(
//							Double.valueOf(empresaArray.get(i).getLATIDUD()),
//							Double.valueOf(empresaArray.get(i).getLONGITUD())))
//					.icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_map))		
//					.title(empresaArray.get(i).getEMPRESA()));
//		}

	}

}
