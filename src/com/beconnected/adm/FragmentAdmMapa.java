package com.beconnected.adm;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.ArrayList;

import com.beconnected.R;
import com.beconnected.databases.BL;
import com.beconnected.databases.DL;
import com.beconnected.databases.Empresa;

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
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
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
public class FragmentAdmMapa extends Fragment {
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
							"Ingrese el nombre de la empresa.",
							Toast.LENGTH_SHORT).show();
				} else if (latitud == 0.0 || longitud == 0.0) {
					Toast.makeText(getActivity(),
							"Seleccione un punto en el mapa.",
							Toast.LENGTH_SHORT).show();
				} else {

					if (insertar) {

						empresa = new Empresa(0, editTextEmpresa.getText()
								.toString(), String.valueOf(longitud), String
								.valueOf(latitud), imagenLogo);

						DL.getDl().getSqliteConnection().insertEmpresa(empresa);

						Intent i = new Intent(getActivity(), TabsAdmMapa.class);
						startActivity(i);
						Toast.makeText(getActivity(),
								"Empresa cargado correctamente.",
								Toast.LENGTH_SHORT).show();
						imageLogo.setImageResource(R.drawable.logo);
						editTextEmpresa.setText("");
						imagenLogo = null;

					} else {

						empresaArray = BL.getBl().selectListaEmpresa();
						empresa = new Empresa(empresaArray.get(posicion)
								.getID_EMPRESA(), editTextEmpresa.getText()
								.toString(), String.valueOf(longitud), String
								.valueOf(latitud), imagenLogo);
						BL.getBl().actualizarEmpresa(empresa);

						mapa.clear();

						if (imagenLogo != null) {

							imagenLogo = null;
						}
						insertar = true;

						Intent i = new Intent(getActivity(), TabsAdmMapa.class);
						startActivity(i);

						Toast.makeText(getActivity(),
								"Empresa actualizado correctamente.",
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
		myAlertDialog.setTitle("Pictures Option");
		myAlertDialog.setMessage("Select Picture Mode");

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

	public void Image_Selecting_Task(Intent data) {
		try {
			Utility.uri = data.getData();
			if (Utility.uri != null) {
				// User had pick an image.
				Cursor cursor = getActivity()
						.getContentResolver()
						.query(Utility.uri,
								new String[] { android.provider.MediaStore.Images.ImageColumns.DATA },
								null, null, null);
				cursor.moveToFirst();
				// Link to the image
				final String imageFilePath = cursor.getString(0);

				// Assign string path to File
				Utility.Default_DIR = new File(imageFilePath);

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
				Toast toast = Toast.makeText(getActivity(),
						"No se selecciono ninguna imagen.", Toast.LENGTH_LONG);
				toast.show();
			}
		} catch (Exception e) {
			// you get this when you will not select any single image
			Log.e("onActivityResult", "" + e);

		}
	}

}
