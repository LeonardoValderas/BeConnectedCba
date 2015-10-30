package com.beconnected.adm;

import java.util.ArrayList;

import org.json.JSONException;
import org.json.JSONObject;

import com.beconnected.DividerItemDecoration;
import com.beconnected.R;
import com.beconnected.databases.BL;
import com.beconnected.databases.Empresa;
import com.beconnected.databases.GeneralLogic;
import com.beconnected.databases.Request;
import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

/**
 * A simple {@link Fragment} subclass. Use the
 * {@link FragmentGenerarNoticia.sgoliver.android.toolbartabs.Fragment1#newInstance}
 * factory method to create an instance of this fragment.
 */
public class FragmentAdmEmpresaEditar extends Fragment {

	private RecyclerView recycleViewMapa;

	private ArrayList<Empresa> datosEmpresa;
	private AdaptadorEmpresa adaptador;
	private AlertsMenu alertsMenu;
	private AlertsMenu alertsMenuInternet;
	private static final String TAG_SUCCESS = "success";
	private static final String TAG_MESSAGE = "message";
	private ProgressDialog dialog;
	private int idEmpresa;
	private  Typeface cFont;
    private int mCurCheckPosition;
	private static final String ARG_PARAM1 = "param1";
	private static final String ARG_PARAM2 = "param2";

	private String mParam1;
	private String mParam2;

	public static FragmentAdmEmpresaEditar newInstance() {
		FragmentAdmEmpresaEditar fragment = new FragmentAdmEmpresaEditar();
		Bundle args = new Bundle();
		args.putString(ARG_PARAM1, "1");
		args.putString(ARG_PARAM2, "2");
		fragment.setArguments(args);
		return fragment;
	}

	public FragmentAdmEmpresaEditar() {
		// Required empty public constructor
	}

	@Override
	public void onActivityCreated(Bundle state) {
		super.onActivityCreated(state);
		

		
		
//		  Fragment newFragment = new MainFragment();
//	       FragmentTransaction transaction = getFragmentManager().beginTransaction();
//	       transaction.replace(R.id.fragment, newFragment);
//	       transaction.commit();
//		
		init();
	}

	
	
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View v = inflater
				.inflate(R.layout.fragment_mapa_editar, container, false);
		
		recycleViewMapa = (RecyclerView) v.findViewById(
				R.id.recycleViewMapa);
		
		
		
		return v;

	}
	
	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {


		recycleViewMapa.setLayoutManager(new LinearLayoutManager(getActivity(),
				LinearLayoutManager.VERTICAL, false));
		recycleViewMapa.addItemDecoration(new DividerItemDecoration(
				getActivity(), DividerItemDecoration.VERTICAL_LIST));
		recycleViewMapa.setItemAnimator(new DefaultItemAnimator());
		datosEmpresa = BL.getBl().selectListaEmpresa();
	 
		adaptador = new AdaptadorEmpresa(datosEmpresa,cFont);
		// adaptador.notifyDataSetChanged();
		recycleViewMapa.setAdapter(adaptador);
		super.onViewCreated(view, savedInstanceState);
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
//		recycleViewMapa = (RecyclerView) getView().findViewById(
//				R.id.recycleViewMapa);
		   cFont = Typeface.createFromAsset(getActivity().getAssets(), "fonts/NEUROPOL.ttf");

		//recyclerViewLoadEmpresa();
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
						empresaEdit.putExtra("posicion", position);

						startActivity(empresaEdit);

					}

					@Override
					public void onLongClick(View view, final int position) {

						alertsMenu = new AlertsMenu(getActivity(), "ALERTA",
								getActivity().getResources().getString(
										R.string.alert_empresa), null, null);
						alertsMenu.btnAceptar.setText("Aceptar");
						alertsMenu.btnCancelar.setText("Cancelar");

						alertsMenu.btnAceptar
								.setOnClickListener(new View.OnClickListener() {

									@SuppressLint("NewApi")
									@Override
									public void onClick(View v) {
										// TODO Auto-generated method stub

										idEmpresa = datosEmpresa.get(position)
												.getID_EMPRESA();

										Request p = new Request();
										p.setMethod("POST");
										p.setQuery("ELIMINAR");
										p.setParametrosDatos("id_empresa",
												String.valueOf(idEmpresa));

										if (GeneralLogic
												.conexionInternet(getActivity())) {

											TaskEmpresa taskEmpresa = new TaskEmpresa();
											taskEmpresa.execute(p);
											alertsMenu.alertDialog.dismiss();
										} else {

											alertsMenuInternet = new AlertsMenu(
													getActivity(),
													"ATENCIÓN!!!",
													"Por favor verifique su conexión de Internet",
													"Aceptar", null);
											alertsMenuInternet.btnAceptar
													.setOnClickListener(new View.OnClickListener() {

														@Override
														public void onClick(
																View v) {
															// TODO
															// Auto-generated
															// method stub

															alertsMenuInternet.alertDialog
																	.dismiss();
															alertsMenu.alertDialog
																	.dismiss();
															// close();

														}
													});

											alertsMenuInternet.btnCancelar
													.setVisibility(View.GONE);

										}

									

									}

								});
						
						alertsMenu.btnCancelar.setOnClickListener(new View.OnClickListener() {
							
							@Override
							public void onClick(View v) {
								// TODO Auto-generated method stub
								
								alertsMenu.alertDialog.dismiss();
							}
						});
					}
				}));

	
	}

	@Override
	public void onSaveInstanceState(Bundle outState) {
	    super.onSaveInstanceState(outState);
	    outState.putInt("curChoice", mCurCheckPosition);
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
					BL.getBl().eliminarEmpresa(idEmpresa);
					BL.getBl().eliminarPromoEmpresa(idEmpresa);
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

		}

		@Override
		protected void onPostExecute(String result) {
			dialog.dismiss();

			Toast.makeText(getActivity(), result, Toast.LENGTH_SHORT).show();
			recyclerViewLoadEmpresa();
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
	 
		adaptador = new AdaptadorEmpresa(datosEmpresa,cFont);
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

	@Override
	public void onDestroyView() {
		// TODO Auto-generated method stub
		super.onDestroyView();
	}
}
