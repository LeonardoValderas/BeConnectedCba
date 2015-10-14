package com.beconnected.adm;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.ArrayList;

import com.beconnected.R;
import com.beconnected.databases.BL;
import com.beconnected.databases.DL;
import com.beconnected.databases.Empresa;
import com.beconnected.databases.GeneralLogic;
import com.beconnected.databases.Request;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.OnMapClickListener;

import com.google.android.gms.maps.SupportMapFragment;

import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Bitmap.CompressFormat;
import android.os.Bundle;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.MeasureSpec;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

/**
 * A simple {@link Fragment} subclass. Use the
 * {@link FragmentGenerarNoticia.sgoliver.android.toolbartabs.Fragment1#newInstance}
 * factory method to create an instance of this fragment.
 */
public class FragmentAdmEmpresa extends Fragment {
	private GoogleMap mapa;
	public static double latCba = -31.400000000000000;
	public static double longCba = -64.183300000000000;
	public static double latitud;
	public static double longitud;
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
	private boolean actualizar = false;
	private ImageView imageLogo;
	private SubirDatos subirDatos;

	public static FragmentAdmEmpresa newInstance() {
		FragmentAdmEmpresa fragment = new FragmentAdmEmpresa();
		return fragment;
	}

	public FragmentAdmEmpresa() {
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
		return inflater.inflate(R.layout.fragment_mapa_adm, container, false);

	}

	private void init() {

		actualizar = getActivity().getIntent().getBooleanExtra("actualizar",
				false);
		imageLogo = (ImageView) getView().findViewById(R.id.imageLogo);
		imageLogo.setImageResource(R.drawable.logo);

		mapa = ((SupportMapFragment) getChildFragmentManager()
				.findFragmentById(R.id.map)).getMap();

		editTextEmpresa = (EditText) getView().findViewById(
				R.id.editTextEmpresa);

		imageButtonLogo = (ImageButton) getView().findViewById(
				R.id.imageButtonLogo);

		imageButtonLogo.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				//

				Image_Picker_Dialog();

			}
		});

		buttonGuardar = (Button) getView().findViewById(R.id.buttonGuardar);

		mapa.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(latCba,
				longCba), 14.0f));

		if (actualizar) {

			longitud = Double.valueOf(getActivity().getIntent().getStringExtra(
					"longitud"));
			latitud = Double.valueOf(getActivity().getIntent().getStringExtra(
					"latitud"));

			posicion = getActivity().getIntent().getIntExtra("posicion", 0);
			empresaExtra = getActivity().getIntent().getStringExtra("empresa");
			editTextEmpresa.setText(empresaExtra);
			empresaArray = BL.getBl().selectListaEmpresa();
			imagenLogo = empresaArray.get(posicion).getLOGO();

			if (imagenLogo != null) {
				Bitmap theImage = BitmapFactory.decodeByteArray(imagenLogo, 0,
						imagenLogo.length);
				theImage = Bitmap.createScaledBitmap(theImage, 150, 150, true);
				imageLogo.setImageBitmap(theImage);

			} else {

				imageLogo.setImageResource(R.drawable.logo);
			}
			mapa.addMarker(new MarkerOptions().position(new LatLng(latitud,
					longitud)));

			insertar = false;
		}

		mapa.setOnMapClickListener(new OnMapClickListener() {
			public void onMapClick(LatLng point) {

				mapa.clear();

				mapa.addMarker(new MarkerOptions().position(new LatLng(
						point.latitude, point.longitude)));
				latitud = point.latitude;
				longitud = point.longitude;

			}
		});

		buttonGuardar.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub

				if (editTextEmpresa.getText().toString().equals("")) {
					Toast.makeText(getActivity(),
							getActivity().getResources().getString(
									R.string.empresa_nombre_vacio),
							Toast.LENGTH_SHORT).show();
				} else if (latitud == 0.0 || longitud == 0.0) {
					Toast.makeText(getActivity(),
							getActivity().getResources().getString(
									R.string.empresa_mapa_vacio),
							Toast.LENGTH_SHORT).show();
				} else {

					if (insertar) {

					//	if(imagenLogo==null)
						empresa = new Empresa(0, editTextEmpresa.getText()
								.toString(), String.valueOf(longitud), String
								.valueOf(latitud), imagenLogo,GeneralLogic.URL_LOGO+editTextEmpresa.getText()
								.toString()+".PGN");

						DL.getDl().getSqliteConnection().insertEmpresa(empresa);

						
						
						//subimos los datos al server.
												
						String encodedImage = Base64.encodeToString(imagenLogo, Base64.DEFAULT);
						
						Request p = new Request();
//						p.setMethod("GET");
						p.setMethod("POST");
						p.setQuery("SUBIR");
//						p.setUri(uri);
						p.setParametrosDatos("empresa", empresa.getEMPRESA());
						p.setParametrosDatos("longitud", empresa.getLONGITUD());
						p.setParametrosDatos("latitud", empresa.getLATIDUD());
						p.setParametrosDatos("logo", encodedImage);
						p.setParametrosDatos("url_logo", empresa.getURL_LOGO());
						
					
						subirDatos= new SubirDatos(getActivity());
						subirDatos.resquestDataEmpresa(p);
						
						
						//actualizamos el activity. ver este punto 
						Intent i = new Intent(getActivity(), TabsAdmEmpresa.class);
						startActivity(i);
						Toast.makeText(
								getActivity(),
								getActivity().getResources().getString(
										R.string.empresa_cargada),
								Toast.LENGTH_SHORT).show();
						imageLogo.setImageResource(R.drawable.logo);
						editTextEmpresa.setText("");
						imagenLogo = null;

					} else {

						empresaArray = BL.getBl().selectListaEmpresa();
						empresa = new Empresa(empresaArray.get(posicion)
								.getID_EMPRESA(), editTextEmpresa.getText()
								.toString(), String.valueOf(longitud), String
								.valueOf(latitud), imagenLogo,GeneralLogic.URL_LOGO+editTextEmpresa.getText()
								.toString()+".PGN");
						BL.getBl().actualizarEmpresa(empresa);

						String encodedImage = Base64.encodeToString(imagenLogo, Base64.DEFAULT);
						Request p = new Request();
//						p.setMethod("GET");
						p.setMethod("POST");
						p.setQuery("EDITAR");
//						p.setUri(uri);
						p.setParametrosDatos("id_empresa", String.valueOf(empresa.getID_EMPRESA()));
						p.setParametrosDatos("empresa", empresa.getEMPRESA());
						p.setParametrosDatos("longitud", empresa.getLONGITUD());
						p.setParametrosDatos("latitud", empresa.getLATIDUD());
						p.setParametrosDatos("logo", encodedImage);
						p.setParametrosDatos("url_logo", empresa.getURL_LOGO());
						
					
						subirDatos= new SubirDatos(getActivity());
						subirDatos.resquestDataEmpresa(p);
						
						
						
						mapa.clear();

						if (imagenLogo != null) {

							imagenLogo = null;
						}
						insertar = true;

						Intent i = new Intent(getActivity(), TabsAdmEmpresa.class);
						startActivity(i);

						Toast.makeText(
								getActivity(),
								getActivity().getResources().getString(
										R.string.empresa_actualizada),
								Toast.LENGTH_SHORT).show();
						editTextEmpresa.setText("");
						mapa.clear();
						imageLogo.setImageResource(R.drawable.logo);
						imagenLogo = null;

					}
				}

			}

		});

	}

	public void Image_Picker_Dialog() {

		AlertDialog.Builder myAlertDialog = new AlertDialog.Builder(
				getActivity());
		myAlertDialog.setTitle("Galeria");
		myAlertDialog.setMessage("Selecciones una Imagen");

		myAlertDialog.setPositiveButton("Gallery",
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface arg0, int arg1) {
						Utility.pictureActionIntent = new Intent(
								Intent.ACTION_GET_CONTENT, null);
						Utility.pictureActionIntent.setType("image/*");
						Utility.pictureActionIntent.putExtra("return-data",
								true);
						startActivityForResult(Utility.pictureActionIntent,
								Utility.GALLERY_PICTURE);
					}
				});

		myAlertDialog.show();

	}

	// After the selection of image you will retun on the main activity with
	// bitmap image
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (requestCode == Utility.GALLERY_PICTURE) {
			// data contains result
			// Do some task
			Image_Selecting_Task(data);
		}
		// } else if (requestCode == Utility.CAMERA_PICTURE)
		// {
		// // Do some task
		// Image_Selecting_Task(data);
		// }
	}

	
	// ver para cambiar el icono del mapa
	
	public static Bitmap createDrawableFromView(View view) {

		view.setDrawingCacheEnabled(true);
		view.measure(MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED),
				MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED));
		view.layout(0, 0, view.getMeasuredWidth(), view.getMeasuredHeight());
		view.buildDrawingCache(true);
		Bitmap bitmap = Bitmap.createBitmap(view.getDrawingCache());
		view.setDrawingCacheEnabled(false);

		return bitmap;
	}
	
	public void Image_Selecting_Task(Intent data) {
		try {
			Utility.uri = data.getData();
			
		  //   String[] column = { MediaStore.Images.Media.DATA }; 
			// String sel = MediaStore.Images.Media._ID + "=?";
			  
			if (Utility.uri != null) {
			 
				
				Cursor cursor = getActivity().getContentResolver().
                        query(Utility.uri, null,null,null,null);
				
				cursor.moveToFirst();
				String document_id = cursor.getString(0);
				   document_id = document_id.substring(document_id.lastIndexOf(":")+1);
			
				   
				   cursor = getActivity().getContentResolver().query( 
						   android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
						   null, MediaStore.Images.Media._ID + " = ? ", new String[]{document_id}, null);
						   cursor.moveToFirst();
						   String path = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA));
						   cursor.close();
				   
				   
				   
				   //int columnIndex = cursor.getColumnIndex(column[0]);
				//int columnindex = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA); 
				//String filePath=null;
				
				
			
			
			//	 filePath = cursor.getString(columnindex);
				 
				 
				 
//	            if (cursor.moveToFirst()) {
//	             filePath = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
//	            }   
//	            cursor.close();
				// User had pick an image.
//				Cursor cursor = getActivity()
//						.getContentResolver()
//						.query(Utility.uri,
//								new String[] { android.provider.MediaStore.Images.ImageColumns.DATA },
//								null, null, null);
//				cursor.moveToFirst();
//				// Link to the image
//				final String imageFilePath = cursor.getString(0);

				// Assign string path to File
				Utility.Default_DIR = new File(path);

				// Create new dir MY_IMAGES_DIR if not created and copy image
				// into that dir and store that image path in valid_photo
				Utility.Create_MY_IMAGES_DIR();

				// Copy your image
				Utility.copyFile(Utility.Default_DIR, Utility.MY_IMG_DIR);

				// Get new image path and decode it
				Bitmap b = Utility.decodeFile(Utility.Paste_Target_Location);

				// use new copied path and use anywhere
				String valid_photo = Utility.Paste_Target_Location.toString();
				b = Bitmap.createScaledBitmap(b, 150, 150, true);

				// set your selected image in image view
				imageLogo.setImageBitmap(b);
				cursor.close();

				baos = new ByteArrayOutputStream();
				b.compress(CompressFormat.PNG, 0, baos);
				imagenLogo = baos.toByteArray();

			} else {
				Toast toast = Toast.makeText(getActivity(), getActivity()
						.getResources().getString(R.string.empresa_foto),
						Toast.LENGTH_LONG);
				toast.show();
			}
		} catch (Exception e) {
			// you get this when you will not select any single image
			Log.e("onActivityResult", "" + e);

		}
	}

}