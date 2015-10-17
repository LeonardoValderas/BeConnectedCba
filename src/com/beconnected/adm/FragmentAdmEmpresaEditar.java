package com.beconnected.adm;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;

import org.json.JSONException;
import org.json.JSONObject;

import com.beconnected.DividerItemDecoration;
import com.beconnected.R;
import com.beconnected.adm.FragmentAdmEmpresa.TaskEmpresa;
import com.beconnected.databases.BL;
import com.beconnected.databases.DL;
import com.beconnected.databases.Empresa;
import com.beconnected.databases.Promo;
import com.beconnected.databases.Request;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
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
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

/**
 * A simple {@link Fragment} subclass. Use the
 * {@link FragmentGenerarNoticia.sgoliver.android.toolbartabs.Fragment1#newInstance}
 * factory method to create an instance of this fragment.
 */
public class FragmentAdmEmpresaEditar extends Fragment {

	private static final int SELECT_SINGLE_PICTURE = 1;

	// private byte[] imagenEscudo = null;
	private RecyclerView recycleViewMapa;
	// private ImageButton imageButtonEquipo;
	// private ByteArrayOutputStream baos;
	private Bitmap myImage;
	private Promo promo;
	private ArrayList<Empresa> datosEmpresa;
	private AdaptadorEmpresa adaptador;
	private boolean insertar = true;
	private int posicion;
	private AlertsMenu alertsMenu;

	private static final String TAG_SUCCESS = "success";
	private static final String TAG_MESSAGE = "message";
	private ProgressDialog dialog;
	private int idEmpresa;
	public static FragmentAdmEmpresaEditar newInstance() {
		FragmentAdmEmpresaEditar fragment = new FragmentAdmEmpresaEditar();
		return fragment;
	}

	public FragmentAdmEmpresaEditar() {
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
		return inflater
				.inflate(R.layout.fragment_mapa_editar, container, false);

	}

	// @Override
	// public void onPause() {
	// // Fragment fragment =
	// (getChildFragmentManager().findFragmentById(R.id.map));
	// FragmentAdmMapaEditar fragment = new FragmentAdmMapaEditar();
	// FragmentTransaction ft =
	// getActivity().getSupportFragmentManager().beginTransaction();
	// ft.remove(fragment);
	// ft.commit();
	//
	// //init();
	// super.onPause();
	// }
	//

	private void init() {

		recycleViewMapa = (RecyclerView) getView().findViewById(
				R.id.recycleViewMapa);

		recyclerViewLoadEmpresa();
		recycleViewMapa.addOnItemTouchListener(new RecyclerTouchListener(
				getActivity(), recycleViewMapa, new ClickListener() {

					@Override
					public void onClick(View view, int position) {
						// TODO Auto-generated method stub

						Intent empresaEdit = new Intent(getActivity(),
								TabsAdmEmpresa.class);
						empresaEdit.putExtra("actualizar", true);
						empresaEdit.putExtra("empresa",
								datosEmpresa.get(position).getEMPRESA());
						empresaEdit.putExtra("longitud",
								datosEmpresa.get(position).getLONGITUD());
						empresaEdit.putExtra("latitud",
								datosEmpresa.get(position).getLATIDUD());
						//empresaEdit.putExtra("id", datosEmpresa.get(position).getID_EMPRESA());
						empresaEdit.putExtra("posicion", position);

						startActivity(empresaEdit);

					}

					@Override
					public void onLongClick(View view, final int position) {

						alertsMenu = new AlertsMenu(getActivity(), "ALERTA",
								getActivity().getResources().getString(R.string.alert_empresa), null, null);
						alertsMenu.btnAceptar.setText("Aceptar");
						alertsMenu.btnCancelar.setText("Cancelar");

						alertsMenu.btnAceptar
								.setOnClickListener(new View.OnClickListener() {

									@SuppressLint("NewApi")
									@Override
									public void onClick(View v) {
										// TODO Auto-generated method stub
										
										
										idEmpresa= datosEmpresa.get(position).getID_EMPRESA();
										
										Request p = new Request();
										p.setMethod("POST");
										p.setQuery("ELIMINAR");
										p.setParametrosDatos("id_empresa",String.valueOf(idEmpresa));
										
										TaskEmpresa taskEmpresa = new TaskEmpresa();
										taskEmpresa.execute(p);
									
//										Toast.makeText(
//												getActivity(),
//												getActivity().getResources().getString(R.string.empresa_eliminada),
//												Toast.LENGTH_SHORT).show();

										alertsMenu.alertDialog.dismiss();

									}

								});
					}
				}));

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

				try {

					JSONObject json = BL.getBl().getConnManager()
							.gestionEmpresa(params[0]);

					success = json.getInt(TAG_SUCCESS);
					if (success == 1) {
						BL.getBl().eliminarEmpresa(
								idEmpresa);
						BL.getBl().eliminarPromoEmpresa(
								idEmpresa);
						return json.getString(TAG_MESSAGE);
					} else {
						// Log.d("Registering Failure!",
						// json.getString(TAG_MESSAGE));
						return json.getString(TAG_MESSAGE);

					}
				} catch (JSONException e) {
					e.printStackTrace();
				}

				return null;

				// String content = BL.getBl().getConnManager()
				// .gestionEmpresa(params[0]);
				// return content; // retorna string al metodo onPostExecute

			}

			@Override
			protected void onPostExecute(String result) {
				dialog.dismiss();

				Toast.makeText(getActivity(), result, Toast.LENGTH_SHORT).show();
				recyclerViewLoadEmpresa();
				// Toast.makeText(context, "El Empre", Toast.LENGTH_SHORT).show();
				// TaskPromo taskPromo = new TaskPromo();
				// taskPromo.execute("");

			}

			@Override
			protected void onProgressUpdate(String... values) {
				// textViewDato.append(values[0]+"\n");
			}
		}


	public void recyclerViewLoadEmpresa() {

		recycleViewMapa.setLayoutManager(new LinearLayoutManager(getActivity(),
				LinearLayoutManager.VERTICAL, false));
		recycleViewMapa.addItemDecoration(new DividerItemDecoration(
				getActivity(), DividerItemDecoration.VERTICAL_LIST));
		recycleViewMapa.setItemAnimator(new DefaultItemAnimator());
		datosEmpresa = BL.getBl().selectListaEmpresa();

		adaptador = new AdaptadorEmpresa(datosEmpresa);
		// adaptador.notifyDataSetChanged();
		recycleViewMapa.setAdapter(adaptador);

	}

	//
	//
	// /**
	// * Metodo click item recycler
	// *
	// * @author LEO
	// *
	// */
	//
	public static interface ClickListener {

		public void onClick(View view, int position);

		public void onLongClick(View view, int position);

	}

	static class RecyclerTouchListener implements
			RecyclerView.OnItemTouchListener {

		private GestureDetector detector;
		private ClickListener clickListener;

		public RecyclerTouchListener(Context context,
				final RecyclerView recyclerView,
				final ClickListener clickListener) {
			this.clickListener = clickListener;
			detector = new GestureDetector(context,
					new GestureDetector.SimpleOnGestureListener() {

						@Override
						public boolean onSingleTapUp(MotionEvent e) {
							return true;
						}

						@Override
						public void onLongPress(MotionEvent e) {
							View child = recyclerView.findChildViewUnder(
									e.getX(), e.getY());
							if (child != null && clickListener != null) {
								clickListener.onLongClick(child,
										recyclerView.getChildPosition(child));
							}
						}
					});

		}

		@Override
		public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {
			// TODO Auto-generated method stub
			View child = rv.findChildViewUnder(e.getX(), e.getY());
			if (child != null && clickListener != null
					&& detector.onTouchEvent(e)) {
				clickListener.onClick(child, rv.getChildPosition(child));
			}
			return false;
		}

		@Override
		public void onTouchEvent(RecyclerView rv, MotionEvent e) {

		}

		@Override
		public void onRequestDisallowInterceptTouchEvent(boolean arg0) {
			// TODO Auto-generated method stub

		}

	}

}
