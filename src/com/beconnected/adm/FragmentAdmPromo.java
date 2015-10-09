package com.beconnected.adm;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import com.beconnected.R;
import com.beconnected.databases.BL;
import com.beconnected.databases.Empresa;
import com.beconnected.databases.Promo;

import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.Toast;

/**
 * A simple {@link Fragment} subclass. Use the
 * {@link FragmentGenerarNoticia.sgoliver.android.toolbartabs.Fragment1#newInstance}
 * factory method to create an instance of this fragment.
 */
public class FragmentAdmPromo extends Fragment {

	private ArrayList<Empresa> empresaArray;
	private EditText editTextTitulo;
	private EditText editTextDescripcion;
    private Spinner spinnerEmpresa;
	private Button buttonGuardar;
	private boolean insertar = true;
	private int posicion;
	private byte[] imagenLogo = null;
	private Bitmap myImage;
	private ByteArrayOutputStream baos;
	private Empresa empresa;
	private String empresaExtra;
	private boolean actualizar = false;
	private ImageView imageLogo;
	private AdapterAdmPromo adapterAdmPromo;
	private Promo promo;
	private boolean botonFecha = true; // desde
	private boolean radiobutton = true; // desde
	private Button buttonDesde;
	private Button buttonHasta;
	private RadioButton radioButtonStock;
	private DateFormat formate = DateFormat.getDateInstance();
	private Calendar calendar = Calendar.getInstance();
	
	public static FragmentAdmPromo newInstance() {
		FragmentAdmPromo fragment = new FragmentAdmPromo();
		return fragment;
	}

	public FragmentAdmPromo() {
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
		return inflater.inflate(R.layout.fragment_adm_promo, container, false);

	}

	private void init() {
		
		
		empresaArray = BL.getBl().selectListaEmpresa();
		
		editTextTitulo=(EditText) getView().findViewById(
				R.id.editTextTitulo);
		editTextDescripcion=(EditText) getView().findViewById(
				R.id.editTextDescripcion);
		spinnerEmpresa = (Spinner) getView().findViewById(
				R.id.spinnerEmpresa);

		adapterAdmPromo = new AdapterAdmPromo(getActivity(),
				R.layout.simple_spinner_dropdown_item, empresaArray);
		spinnerEmpresa.setAdapter(adapterAdmPromo);

		buttonGuardar = (Button) getView().findViewById(R.id.buttonGuardar);
		
		actualizar = getActivity().getIntent().getBooleanExtra("actualizar",
				false);
	
		buttonDesde= (Button) getView().findViewById(R.id.buttonDesde);
		buttonHasta= (Button) getView().findViewById(R.id.buttonHasta);
		radioButtonStock= (RadioButton) getView().findViewById(R.id.radioButtonStock);
		
		
		
		if (actualizar) {

		}
		
//			posicion = getActivity().getIntent().getIntExtra("posicion", 0);
//			empresaExtra = getActivity().getIntent().getStringExtra("empresa");
//			editTextEmpresa.setText(empresaExtra);
//			empresaArray = BL.getBl().selectListaEmpresa();
//			imagenLogo = empresaArray.get(posicion).getLOGO();
//
//			if (imagenLogo != null) {
//				Bitmap theImage = BitmapFactory.decodeByteArray(imagenLogo, 0,
//						imagenLogo.length);
//				theImage = Bitmap.createScaledBitmap(theImage, 150, 150, true);
//				imageLogo.setImageBitmap(theImage);
//
//			} else {
//
//				imageLogo.setImageResource(R.drawable.logo);
//			}
//			mapa.addMarker(new MarkerOptions().position(new LatLng(latitud,
//					longitud)));
//
//			insertar = false;
//		}
//
//		mapa.setOnMapClickListener(new OnMapClickListener() {
//			public void onMapClick(LatLng point) {
//
//				mapa.clear();
//
//				mapa.addMarker(new MarkerOptions().position(new LatLng(
//						point.latitude, point.longitude)));
//				latitud = point.latitude;
//				longitud = point.longitude;
//
//			}
//		});
		buttonDesde.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				botonFecha= true;
				setDate();
				
				
			}
		});
		
	buttonHasta.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				botonFecha= false;
				setDate();
				
				
			}
		});
		radioButtonStock.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
				if(radiobutton){
					buttonHasta.setEnabled(false);
					radiobutton= false;
				}else{
					radioButtonStock.setChecked(false);
					buttonHasta.setEnabled(true);
					radiobutton= true;
					
				}
				
				
			}
		});
		
		buttonGuardar.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				
			
				
				
				if(editTextTitulo.getText().toString().equals("")||editTextDescripcion.getText().toString().equals("")){
					
					Toast.makeText(getActivity(),
							"Complete los datos.",
							Toast.LENGTH_SHORT).show();
					
				}
				else if(buttonDesde.getText().toString().equals("Desde")){
				

					Toast.makeText(getActivity(),
							"Seleccione fecha de comienzo de la promo.",
							Toast.LENGTH_SHORT).show();
					
				}
					else if(buttonDesde.getText().toString().equals("Hasta")&&!radioButtonStock.isChecked()){
						

						Toast.makeText(getActivity(),
								"Seleccione fecha de fin de la promo.",
								Toast.LENGTH_SHORT).show();
							
				
				} else {

					if (insertar) {

						empresa = (Empresa) spinnerEmpresa.getSelectedItem();
						
						if(radioButtonStock.isChecked()){
							
							promo = new Promo(0, editTextTitulo.getText()
									.toString(), editTextDescripcion.getText()
									.toString(),empresa.getID_EMPRESA(),null,buttonDesde.getText().toString(),"Hasta Agotar Stock");
							BL.getBl().insertarPromo(promo);
							Intent i = new Intent(getActivity(), TabsAdmPromo.class);
							startActivity(i);
							
							Toast.makeText(getActivity(),
									"Promo cargada correctamente.",
									Toast.LENGTH_SHORT).show();
							editTextTitulo.setText("");
							editTextDescripcion.setText("");
							buttonDesde.setText("Desde");
							buttonHasta.setText("Hasta");
							radioButtonStock.setChecked(false);
						}else{
						
						
						promo = new Promo(0, editTextTitulo.getText()
								.toString(), editTextDescripcion.getText()
								.toString(),empresa.getID_EMPRESA(),null,buttonDesde.getText().toString(),buttonHasta.getText().toString());

						BL.getBl().insertarPromo(promo);

						Intent i = new Intent(getActivity(), TabsAdmPromo.class);
						startActivity(i);
						Toast.makeText(getActivity(),
								"Promo cargada correctamente.",
								Toast.LENGTH_SHORT).show();
						editTextTitulo.setText("");
						editTextDescripcion.setText("");
						buttonDesde.setText("Desde");
						buttonHasta.setText("Hasta");
						radioButtonStock.setChecked(false);
						}
					} else {

//						empresaArray = BL.getBl().selectListaEmpresa();
//						empresa = new Empresa(empresaArray.get(posicion)
//								.getID_EMPRESA(), editTextEmpresa.getText()
//								.toString(), String.valueOf(longitud), String
//								.valueOf(latitud), imagenLogo);
//						BL.getBl().actualizarEmpresa(empresa);
//
//						mapa.clear();
//
//						if (imagenLogo != null) {
//
//							imagenLogo = null;
//						}
//						insertar = true;
//
//						Intent i = new Intent(getActivity(), TabsAdmMapa.class);
//						startActivity(i);
//
//						Toast.makeText(getActivity(),
//								"Empresa actualizado correctamente.",
//								Toast.LENGTH_SHORT).show();
//						editTextEmpresa.setText("");
//						mapa.clear();
//						imageLogo.setImageResource(R.drawable.logo);
//						imagenLogo = null;

					}
				}

			}

		});

	}
	public void updatedateh() {

		buttonHasta.setText(formate.format(calendar.getTime()));

	}
	
	public void updatedateD() {

		buttonDesde.setText(formate.format(calendar.getTime()));

	}
	
	public void setDate() {

		new DatePickerDialog(getActivity(), d, calendar.get(Calendar.YEAR),
				calendar.get(Calendar.MONTH),
				calendar.get(Calendar.DAY_OF_MONTH)).show();

	}
	
	DatePickerDialog.OnDateSetListener d = new DatePickerDialog.OnDateSetListener() {

		@Override
		public void onDateSet(DatePicker view, int year, int monthOfYear,
				int dayOfMonth) {

			calendar.set(Calendar.YEAR, year);
			calendar.set(Calendar.MONTH, monthOfYear);
			calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);

			if(botonFecha)
			{
				updatedateD();
			}else{
				
				updatedateh();	
			}
			

		}
	};


}
