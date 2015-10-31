package com.beconnected.adm;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.ArrayList;
import org.json.JSONException;
import org.json.JSONObject;
import com.beconnected.GPSTracker;
import com.beconnected.R;
import com.beconnected.databases.BL;
import com.beconnected.databases.ControladorAdm;
import com.beconnected.databases.Empresa;
import com.beconnected.databases.GeneralLogic;
import com.beconnected.databases.Request;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.GoogleMap.OnMapClickListener;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.graphics.Bitmap.CompressFormat;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
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
	public static double latitud = 0.0;
	public static double longitud = 0.0;
	private double mi_latitude;
	private double mi_longitude;
	private ArrayList<Empresa> empresaArray;
	private EditText editTextEmpresa;
	private ImageButton imageButtonLogo;
	private Button buttonGuardar;
	private boolean insertar = true;
	private int id;
	private int posicion;
	private byte[] imagenLogo = null;
	private Bitmap myImage;
	private ByteArrayOutputStream baos;
	private Empresa empresa;
	private String empresaExtra;
	private boolean actualizar = false;
	private ImageView imageLogo;
	private static final String TAG_SUCCESS = "success";
	private static final String TAG_MESSAGE = "message";
	private ProgressDialog dialog;
	private static final String TAG_ID = "id";
	private String encodedImage = null;
	private GPSTracker gps;
	private AlertsMenu alertsMenu;
	private Communicator comm;
	private  Typeface cFont;
	private SupportMapFragment supportMapFragment; 
	int mCurCheckPosition;
	private ControladorAdm base;

	private static final String ARG_PARAM1 = "param1";
	private static final String ARG_PARAM2 = "param2";
	
	private String mParam1;
	private String mParam2;
	
	public static FragmentAdmEmpresa newInstance() {
		FragmentAdmEmpresa fragment = new FragmentAdmEmpresa();
		Bundle args = new Bundle();
		args.putString(ARG_PARAM1, "1");
		args.putString(ARG_PARAM2, "2");
		fragment.setArguments(args);
		return fragment;
	}

	public FragmentAdmEmpresa() {
		// Required empty public constructor
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		  comm= (Communicator)getActivity();
	
		  base =  new ControladorAdm(getActivity());
		  if (savedInstanceState != null) {
			mCurCheckPosition = savedInstanceState.getInt("curChoice", 0);
			
		}
		else{
		init();
		}
	}

	

	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.fragment_mapa_adm, container, false);
		
		

		imageLogo = (ImageView) v.findViewById(R.id.imageLogo);
		editTextEmpresa = (EditText) v.findViewById(
				R.id.editTextEmpresa);
		imageButtonLogo = (ImageButton) v.findViewById(
				R.id.imageButtonLogo);
		buttonGuardar = (Button) v.findViewById(R.id.buttonGuardar);
		return v;

	}

	
	@Override
	public void onSaveInstanceState(Bundle outState) {
	    super.onSaveInstanceState(outState);
	    outState.putInt("curChoice", mCurCheckPosition);
	}
	
	@Override
	public void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		if (getArguments() != null) {
			mParam1 = getArguments().getString(ARG_PARAM1);
			mParam2 = getArguments().getString(ARG_PARAM2);
		}
	}

	
	
	private void init() {

		gps = new GPSTracker(getActivity());

		if (gps.canGetLocation()) {
			mi_latitude = gps.getLatitude();
			mi_longitude = gps.getLongitude();

		} else {
			gps.showSettingsAlert();
		}

		actualizar = getActivity().getIntent().getBooleanExtra("actualizar",
		
			false);
	//	imageLogo = (ImageView) getView().findViewById(R.id.imageLogo);
	
		
		imageLogo.setImageResource(R.drawable.logo);

//		//1
//				supportMapFragment =(SupportMapFragment) getChildFragmentManager()
//				.findFragmentById(R.id.map);
//				
//				
//				//2
//				if(supportMapFragment==null){
//					
//					android.support.v4.app.FragmentManager fragment = getFragmentManager();
//				        FragmentTransaction fragmentTransaction = fragment.beginTransaction();
//				        supportMapFragment = SupportMapFragment.newInstance();
//				        fragmentTransaction.replace(R.id.map, supportMapFragment).commit();
//					
//				}
//				 if (supportMapFragment != null)
//				    { 
//					 supportMapFragment.getMapAsync(new OnMapReadyCallback() {
//				    
//			            @Override public void onMapReady(GoogleMap mapa) {
//			                if (mapa != null) {
//			                	
//			                	mapa.getUiSettings().setAllGesturesEnabled(true);
//			                	mapa.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(latCba,
//			            				longCba), 14.0f));
//			                }
//
//			            }
//			        });
//				    }
//		
		if(mapa==null){
		mapa = ((SupportMapFragment) getChildFragmentManager()
				.findFragmentById(R.id.map)).getMap();
        }
//		editTextEmpresa = (EditText) getView().findViewById(
//				R.id.editTextEmpresa);
		cFont = Typeface.createFromAsset(getActivity().getAssets(), "fonts/NEUROPOL.ttf");
		editTextEmpresa.setTypeface(cFont);
//		imageButtonLogo = (ImageButton) getView().findViewById(
//				R.id.imageButtonLogo);

		imageButtonLogo.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				//

				Image_Picker_Dialog();

			}
		});

//		buttonGuardar = (Button) getView().findViewById(R.id.buttonGuardar);

		mapa.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(latCba,
				longCba), 14.0f));

		if (actualizar) {

			longitud = Double.valueOf(getActivity().getIntent().getStringExtra(
					"longitud"));
			latitud = Double.valueOf(getActivity().getIntent().getStringExtra(
					"latitud"));

			// id = getActivity().getIntent().getIntExtra("id", 0);
			posicion = getActivity().getIntent().getIntExtra("posicion", 0);
			empresaExtra = getActivity().getIntent().getStringExtra("empresa");
			editTextEmpresa.setText(empresaExtra);
			
			
			
			
			base.abrirBaseDeDatos();
			empresaArray=base.selectListaEmpresa();
			base.cerrarBaseDeDatos();
			
			
			//empresaArray = BL.getBl().selectListaEmpresa();
			
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
					Toast.makeText(
							getActivity(),
							getActivity().getResources().getString(
									R.string.empresa_nombre_vacio),
							Toast.LENGTH_SHORT).show();
				} else if (latitud == 0.0 || longitud == 0.0) {
					Toast.makeText(
							getActivity(),
							getActivity().getResources().getString(
									R.string.empresa_mapa_vacio),
							Toast.LENGTH_SHORT).show();
				} else {

					if (insertar) {

						Request p = new Request();

						empresa = new Empresa(0, editTextEmpresa.getText()
								.toString(), String.valueOf(longitud), String
								.valueOf(latitud), imagenLogo,
								GeneralLogic.URL_LOGO
										+ editTextEmpresa.getText().toString()
												.replace(" ", "") + ".PNG");

						// subimos los datos al server.

						if (imagenLogo != null) {

							encodedImage = Base64.encodeToString(imagenLogo,
									Base64.DEFAULT);
							p.setParametrosDatos("logo", encodedImage);
							p.setParametrosDatos("url_logo",
									empresa.getURL_LOGO());
						}

						p.setMethod("POST");
						p.setQuery("SUBIR");
						p.setParametrosDatos("empresa", empresa.getEMPRESA());
						p.setParametrosDatos("longitud", empresa.getLONGITUD());
						p.setParametrosDatos("latitud", empresa.getLATIDUD());
						String input = empresa.getEMPRESA().replace(" ", "");
						input = input.trim();
						p.setParametrosDatos("url_empresa", input);

						if (GeneralLogic.conexionInternet(getActivity())) {
							TaskEmpresa taskEmpresa = new TaskEmpresa();
							taskEmpresa.execute(p);
							imageLogo.setImageResource(R.drawable.logo);
							editTextEmpresa.setText("");
							latitud = 0.0;
							longitud = 0.0;
							imagenLogo = null;
							mapa.clear();
						//	insertar = true;
							
						} else {

							alertsMenu = new AlertsMenu(
									getActivity(),
									"ATENCIÓN!!!",
									"Por favor verifique su conexión de Internet",
									"Aceptar", null);
							alertsMenu.btnAceptar
									.setOnClickListener(new View.OnClickListener() {

										@Override
										public void onClick(View v) {
											// TODO Auto-generated method stub

											alertsMenu.alertDialog.dismiss();
											// close();

										}
									});

							alertsMenu.btnCancelar.setVisibility(View.GONE);

						}

					} else {
						Request p = new Request();
						
						base.abrirBaseDeDatos();
						empresaArray=base.selectListaEmpresa();
						base.cerrarBaseDeDatos();
						
						
						//empresaArray = BL.getBl().selectListaEmpresa();
						empresa = new Empresa(empresaArray.get(posicion)
								.getID_EMPRESA(), editTextEmpresa.getText()
								.toString(), String.valueOf(longitud), String
								.valueOf(latitud), imagenLogo,
								GeneralLogic.URL_LOGO
										+ editTextEmpresa.getText().toString()
										+ ".PNG");

						if (imagenLogo != null) {

							encodedImage = Base64.encodeToString(imagenLogo,
									Base64.DEFAULT);
							p.setParametrosDatos("logo", encodedImage);
							p.setParametrosDatos("url_logo",
									empresa.getURL_LOGO());
						}

						p.setMethod("POST");
						p.setQuery("EDITAR");
						p.setParametrosDatos("id_empresa",
								String.valueOf(empresa.getID_EMPRESA()));
						p.setParametrosDatos("empresa", empresa.getEMPRESA());
						p.setParametrosDatos("longitud", empresa.getLONGITUD());
						p.setParametrosDatos("latitud", empresa.getLATIDUD());
						String input = empresa.getEMPRESA().replace(" ", "");
						input = input.trim();
						p.setParametrosDatos("url_empresa", input);

						if (GeneralLogic.conexionInternet(getActivity())) {
							TaskEmpresa taskEmpresa = new TaskEmpresa();
							taskEmpresa.execute(p);
							mapa.clear();
						//insertar = true;
							if (imagenLogo != null) {

								imagenLogo = null;
							}

							editTextEmpresa.setText("");

							imageLogo.setImageResource(R.drawable.logo);

							latitud = 0.0;
							longitud = 0.0;
						} else {

							alertsMenu = new AlertsMenu(
									getActivity(),
									"ATENCIÓN!!!",
									"Por favor verifique su conexión de Internet",
									"Aceptar", null);
							alertsMenu.btnAceptar
									.setOnClickListener(new View.OnClickListener() {

										@Override
										public void onClick(View v) {
											// TODO Auto-generated method stub

											alertsMenu.alertDialog.dismiss();
											// close();

										}
									});

							alertsMenu.btnCancelar.setVisibility(View.GONE);

						}
					}
				}
			}
		});
	}

	// enviar/editar/eliminar empresa

	public class TaskEmpresa extends AsyncTask<Request, String, String> {

		@Override
		protected void onPreExecute() {

			dialog = new ProgressDialog(getActivity());
			dialog.setMessage("Procesando...");
			dialog.show();

		}

		@Override
		protected String doInBackground(Request... params) {

			int success;
			JSONObject json= null;
			try {

				 json = BL.getBl().getConnManager()
						.gestionEmpresa(params[0]);

				if(json!=null){
				success = json.getInt(TAG_SUCCESS);
				if (success == 1) {
					if (insertar) {
						String a = json.getString(TAG_ID);
						int id = json.getInt(TAG_ID);
						
					//	base =  new ControladorAdm(getActivity());
						
						base.abrirBaseDeDatos();
						base.insertEmpresa(id, empresa);
						base.cerrarBaseDeDatos();
						//BL.getBl().insertarEmpresa(id, empresa);
						
						
						BL.getBl().getConnManager().push("E");
						
						
					}
					
					else {
					//	BL.getBl().actualizarEmpresa(empresa);
                     
					//	base =  new ControladorAdm(getActivity());
						
						base.abrirBaseDeDatos();
						base.actualizarEmpresa(empresa);
						base.cerrarBaseDeDatos();
					//	comm.refresh();
						insertar = true;
					}
					return json.getString(TAG_MESSAGE);
				}
					
	
				} else {
					// Log.d("Registering Failure!",
					// json.getString(TAG_MESSAGE));
					 String erroString="Problemas con la Conexión.";
					 return erroString;
				}
			} catch (JSONException e) {
				e.printStackTrace();
				 //return erroString;
				
			}

			return null;

		}

		@Override
		protected void onPostExecute(String result) {
			dialog.dismiss();

			Toast.makeText(getActivity(), result, Toast.LENGTH_SHORT).show();
//			Intent i = new Intent(getActivity(), TabsAdmEmpresa.class);
//			startActivity(i);
			comm.refresh();
			
		//	insertar = true;

		}

		@Override
		protected void onProgressUpdate(String... values) {
			// textViewDato.append(values[0]+"\n");
		}
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

			// String[] column = { MediaStore.Images.Media.DATA };
			// String sel = MediaStore.Images.Media._ID + "=?";

			if (Utility.uri != null) {

				Cursor cursor = getActivity().getContentResolver().query(
						Utility.uri, null, null, null, null);

				cursor.moveToFirst();
				String document_id = cursor.getString(0);
				document_id = document_id.substring(document_id
						.lastIndexOf(":") + 1);

				cursor = getActivity()
						.getContentResolver()
						.query(android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
								null, MediaStore.Images.Media._ID + " = ? ",
								new String[] { document_id }, null);
				cursor.moveToFirst();
				String path = cursor.getString(cursor
						.getColumnIndex(MediaStore.Images.Media.DATA));
				cursor.close();

				// int columnIndex = cursor.getColumnIndex(column[0]);
				// int columnindex =
				// cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
				// String filePath=null;

				// filePath = cursor.getString(columnindex);

				// if (cursor.moveToFirst()) {
				// filePath =
				// cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
				// }
				// cursor.close();
				// User had pick an image.
				// Cursor cursor = getActivity()
				// .getContentResolver()
				// .query(Utility.uri,
				// new String[] {
				// android.provider.MediaStore.Images.ImageColumns.DATA },
				// null, null, null);
				// cursor.moveToFirst();
				// // Link to the image
				// final String imageFilePath = cursor.getString(0);

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
