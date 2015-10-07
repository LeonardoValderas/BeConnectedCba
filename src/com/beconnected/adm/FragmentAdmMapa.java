package com.beconnected.adm;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;

import com.beconnected.R;
import com.beconnected.databases.BL;
import com.beconnected.databases.DL;
import com.beconnected.databases.Empresa;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.OnMapClickListener;
import com.google.android.gms.maps.Projection;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.internal.Point;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

/**
 * A simple {@link Fragment} subclass. Use the
 * {@link FragmentGenerarNoticia.sgoliver.android.toolbartabs.Fragment1#newInstance}
 * factory method to create an instance of this fragment.
 */
public class FragmentAdmMapa extends Fragment{
	private GoogleMap mapa;
	public static double latCba = -31.400000000000000;
	public static double longCba = -64.183300000000000;
	public static double latitud ;
	public static double longitud ;
	private ArrayList<Empresa> empresaArray;
	private EditText editTextEmpresa;
	private ImageButton imageButtonLogo;
	private Button buttonGuardar;
	private boolean insertar = true;
	private int posicion;
	private byte[] imagenLogo = null;
	private Bitmap myImage;
	private ByteArrayOutputStream baos;
	private Empresa empresa;
	private String empresaExtra;
	private static final int SELECT_SINGLE_PICTURE = 1;
	private boolean actualizar = false;
	
//	private ArrayList<Cancha> canchaAdefulArray;
//	private AdaptadorCancha adaptadorCancha;

	public static FragmentAdmMapa newInstance() {
		FragmentAdmMapa fragment = new FragmentAdmMapa();
		return fragment;
	}

	public FragmentAdmMapa() {
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
		return inflater.inflate(R.layout.fragment_mapa_adm, container,
				false);

	}



	
	private void init() {

		actualizar = getActivity().getIntent().getBooleanExtra("actualizar", false);
		mapa = ((SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map)).getMap();
		
		editTextEmpresa= (EditText)getView().findViewById(R.id.editTextEmpresa);
		
		imageButtonLogo=(ImageButton)getView().findViewById(R.id.imageButtonLogo);
		
		imageButtonLogo.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				//
				Intent intent = new Intent(
						Intent.ACTION_PICK,
						android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
				startActivityForResult(intent, SELECT_SINGLE_PICTURE);
			}
		});
		
		
		buttonGuardar=(Button)getView().findViewById(R.id.buttonGuardar);
	
	
		mapa.moveCamera( CameraUpdateFactory.newLatLngZoom(new LatLng(latCba,longCba) , 14.0f) );
		
		if (actualizar) {

			longitud =Double.valueOf( getActivity().getIntent().getStringExtra("longitud"));
			latitud = Double.valueOf(getActivity().getIntent().getStringExtra("latitud"));

			posicion = getActivity().getIntent().getIntExtra("posicion", 0);
			empresaExtra = getActivity().getIntent().getStringExtra("empresa");
			editTextEmpresa.setText(empresaExtra);

			mapa.addMarker(new MarkerOptions().position(
					new LatLng(latitud, longitud)));
					
//					.title(
//					"Dirección: \n" + locationAddress));

		//	tvAddress.setText(locationAddress);
			insertar = false;
		}
		
		
		// zoom long y lat de bariloche
//		CameraPosition cameraPosition = new CameraPosition.Builder().target(new LatLng(latCba, longCba)).zoom(15)
//				.build();
//		mapa.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));

//		// click en el mapa crea marker y muestra nombre de la calle
		mapa.setOnMapClickListener(new OnMapClickListener() {
			public void onMapClick(LatLng point) {
//				Projection proj = mapa.getProjection();
//				Point coord = proj.toScreenLocation(point);

				mapa.clear();

				mapa.addMarker(new MarkerOptions().position(new LatLng(point.latitude, point.longitude)));
				latitud = point.latitude;
				longitud = point.longitude;
				//.title("Dirección: \n" + tvAddress.getText().toString()));
//
//				LocationAddress locationAddress = new LocationAddress();
//				locationAddress.getAddressFromLocation(point.latitude, point.longitude, getApplicationContext(),
//						new GeocoderHandler());

			}
		});


		
		
		
		buttonGuardar.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				
				if (editTextEmpresa.getText().toString().equals("")) {
					Toast.makeText(getActivity(),
							"Ingrese el nombre de la empresa.", Toast.LENGTH_SHORT)
							.show();
				}
				else if (latitud==0.0||longitud==0.0) {
						Toast.makeText(getActivity(),
								"Seleccione un punto en el mapa.", Toast.LENGTH_SHORT)
								.show();
				} else {

					if (insertar) {

				
						empresa = new Empresa(0, editTextEmpresa.getText()
								.toString(), String.valueOf(longitud),String.valueOf(latitud),imagenLogo);

						DL.getDl().getSqliteConnection().insertEmpresa(empresa);
						

				
						
						if (imagenLogo != null) {
//							imageButtonLogo.setBackground(ResourcesCompat
//									.getDrawable(getResources(),
//											R.drawable.ic_launcher, null));
							
						}
						
						 
                        Intent i = new Intent(getActivity(),TabsAdmMapa.class);
                        startActivity(i);
						Toast.makeText(getActivity(),
								"Empresa cargado correctamente.",
								Toast.LENGTH_SHORT).show();
						editTextEmpresa.setText("");
						imagenLogo = null;

					} else {

				
						String fechaActualizacion = BL.getBl()
								.getFechaOficial();
						
						empresaArray = BL.getBl().selectListaEmpresa();
						empresa = new Empresa(empresaArray.get(
								posicion).getID_EMPRESA(), editTextEmpresa
								.getText().toString(), String.valueOf(longitud),String.valueOf(latitud),imagenLogo);
                        BL.getBl().actualizarEmpresa(empresa);
						
						
						mapa.clear();

					

//						editTextEmpresa.setText("");
						if (imagenLogo != null) {
//							imageButtonLogo.setBackground(ResourcesCompat
//									.getDrawable(getResources(),
//											R.drawable.ic_launcher, null));
							imagenLogo = null;
						}
						insertar = true;
//						imageButtonLogo.setBackground(ResourcesCompat
//								.getDrawable(getResources(),
//										R.drawable.ic_launcher, null));
						Intent i = new Intent(getActivity(),TabsAdmMapa.class);
                        startActivity(i);
						editTextEmpresa.setText("");
						mapa.clear();
						Toast.makeText(getActivity(),
								"Empresa actualizado correctamente.",
								Toast.LENGTH_SHORT).show();
						imagenLogo = null;

					}
				}

			}
			
		});
		
			

	}
	
	
	@SuppressLint("NewApi")
	public void onActivityResult(int requestCode, int resultCode, Intent data) {

		super.onActivityResult(requestCode, resultCode, data);
		if (resultCode == Activity.RESULT_OK)
			switch (requestCode) {
			case SELECT_SINGLE_PICTURE:
				if (requestCode == 1) {
					Uri uri = data.getData();
					// imagenEscudo = convertImageToByte(uri);
					String[] projection = { MediaStore.Images.Media.DATA };
					Cursor cursor = getActivity().getContentResolver().query(
							uri, projection, null, null, null);
					cursor.moveToFirst();

					int columnIndex = cursor.getColumnIndex(projection[0]);
					String filePath = cursor.getString(columnIndex);
					cursor.close();

					myImage = BitmapFactory.decodeFile(filePath);
					Drawable escudo = new BitmapDrawable(myImage);
					imageButtonLogo.setBackground(escudo);

					baos = new ByteArrayOutputStream();
					myImage.compress(CompressFormat.PNG, 0, baos);
					imagenLogo = baos.toByteArray();

				}
				break;

			default:
				break;
			}

	}
	
	
//	@TargetApi(16)
//	private void setBackgroundV16Plus(View view, Bitmap bitmap) {
//	    view.setBackground(new BitmapDrawable(getActivity().getResources(), bitmap));
//
//	}
//
//	@SuppressWarnings("deprecation")
//	private void setBackgroundV16Minus(View view, Bitmap bitmap) {  
//	    view.setBackgroundDrawable(new BitmapDrawable(bitmap));
//	}
	
	public boolean isGpsActivo() {

		boolean gps = true;

	//	LocationManager locationManager = (LocationManager) .getSystemService( getActivity().LOCATION_SERVICE);
//
//		if (!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)
//				|| !locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)) {
//
//			gps = false;
//		}
//
		return gps;

	}
	
//	private class GeocoderHandler extends Handler {
//		@Override
//		public void handleMessage(Message message) {
//			String locationAddress;
//			switch (message.what) {
//			case 1:
//				Bundle bundle = message.getData();
//				locationAddress = bundle.getString("address");
//				break;
//			default:
//				locationAddress = null;
//			}
//			tvAddress.setText(locationAddress);
//		}
//	}
	
	
}
