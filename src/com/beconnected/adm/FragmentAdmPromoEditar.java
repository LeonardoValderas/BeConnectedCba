package com.beconnected.adm;

import java.util.ArrayList;
import org.json.JSONException;
import org.json.JSONObject;
import com.beconnected.AdaptadorPromos;
import com.beconnected.DividerItemDecoration;
import com.beconnected.R;
import com.beconnected.databases.BL;
import com.beconnected.databases.ControladorAdm;
import com.beconnected.databases.GeneralLogic;
import com.beconnected.databases.Promo;
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
public class FragmentAdmPromoEditar extends Fragment {

	private RecyclerView recycleViewPromo;
	private ArrayList<Promo> datosPromo;
	private AdaptadorPromos adaptador;
	private AlertsMenu alertsMenu;
	private AlertsMenu alertsMenuInternet;
	private static final String TAG_SUCCESS = "success";
	private static final String TAG_MESSAGE = "message";
	private ProgressDialog dialog;
	private int idPromo;
    private  Typeface cFont;
    private ControladorAdm controladorAdm;

	public static FragmentAdmPromoEditar newInstance() {
		FragmentAdmPromoEditar fragment = new FragmentAdmPromoEditar();
		return fragment;
	}

	public FragmentAdmPromoEditar() {
		// Required empty public constructor
	}

	@Override
	public void onActivityCreated(Bundle state) {
		super.onActivityCreated(state);
		controladorAdm = new ControladorAdm(
				getActivity());

		init();
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		return inflater.inflate(R.layout.fragment_promo, container, false);

	}

	private void init() {

		recycleViewPromo = (RecyclerView) getView().findViewById(
				R.id.recycleViewPromo);
		cFont = Typeface.createFromAsset(getActivity().getAssets(), "fonts/NEUROPOL.ttf");
		recyclerViewLoadPromo();

		recycleViewPromo.addOnItemTouchListener(new RecyclerTouchListener(
				getActivity(), recycleViewPromo, new ClickListener() {

					@Override
					public void onClick(View view, int position) {
						// TODO Auto-generated method stub
						Intent promoEdit = new Intent(getActivity(),
								TabsAdmPromo.class);
						promoEdit.putExtra("actualizar", true);
						promoEdit.putExtra("empresa", datosPromo.get(position)
								.getID_EMPRESA());
						promoEdit.putExtra("titulo", datosPromo.get(position)
								.getTITULO());
						promoEdit.putExtra("descripcion",
								datosPromo.get(position).getDESCRIPCION());
						promoEdit.putExtra("desde", datosPromo.get(position)
								.getFECHA_INICIO());
						promoEdit.putExtra("hasta", datosPromo.get(position)
								.getFECHA_FIN());
						promoEdit.putExtra("posicion", position);

						startActivity(promoEdit);

					}

					@Override
					public void onLongClick(View view, final int position) {

						alertsMenu = new AlertsMenu(getActivity(), "ALERTA",
								getActivity().getResources().getString(
										R.string.alert_promo), null, null);
						alertsMenu.btnAceptar.setText("Aceptar");
						alertsMenu.btnCancelar.setText("Cancelar");

						alertsMenu.btnAceptar
								.setOnClickListener(new View.OnClickListener() {

									@SuppressLint("NewApi")
									@Override
									public void onClick(View v) {
										// TODO Auto-generated method stub

										idPromo = datosPromo.get(position)
												.getID_PROMO();

										Request p = new Request();
										p.setMethod("POST");
										p.setQuery("ELIMINAR");
										p.setParametrosDatos("id_promo",
												String.valueOf(idPromo));

										if (GeneralLogic
												.conexionInternet(getActivity())) {
											TaskPromo taskPromo = new TaskPromo();
											taskPromo.execute(p);
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

										alertsMenu.alertDialog.dismiss();

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

	// enviar/editar promo

	public class TaskPromo extends AsyncTask<Request, String, String> {

		@Override
		protected void onPreExecute() {

			dialog = new ProgressDialog(getActivity());
			dialog.setMessage("Procesando...");
			dialog.show();

		}

		@Override
		protected String doInBackground(Request... params) {

			int success;
			JSONObject json =null;

			try {

				 json = BL.getBl().getConnManager()
						.gestionPromo(params[0]);

				 if(json!=null){
				success = json.getInt(TAG_SUCCESS);
				if (success == 1) {
					controladorAdm = new ControladorAdm(
							getActivity());

					controladorAdm.abrirBaseDeDatos();
					controladorAdm.eliminarPromo(idPromo);
					controladorAdm.cerrarBaseDeDatos();
					
					
				//	BL.getBl().eliminarPromo(idPromo);
					return json.getString(TAG_MESSAGE);
				} else {
					// Log.d("Registering Failure!",
					// json.getString(TAG_MESSAGE));
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
			}

			return null;

		}

		@Override
		protected void onPostExecute(String result) {
			dialog.dismiss();

			Toast.makeText(getActivity(), result, Toast.LENGTH_SHORT).show();
			recyclerViewLoadPromo();

		}

		@Override
		protected void onProgressUpdate(String... values) {
			// textViewDato.append(values[0]+"\n");
		}
	}

	public void recyclerViewLoadPromo() {
	
		recycleViewPromo.setLayoutManager(new LinearLayoutManager(
				getActivity(), LinearLayoutManager.VERTICAL, false));
		recycleViewPromo.addItemDecoration(new DividerItemDecoration(
				getActivity(), DividerItemDecoration.VERTICAL_LIST));
		recycleViewPromo.setItemAnimator(new DefaultItemAnimator());
		
	
		controladorAdm.abrirBaseDeDatos();
		datosPromo=	controladorAdm.selectListaPromo();
		controladorAdm.cerrarBaseDeDatos();
		
		//datosPromo = BL.getBl().selectListaPromo();

		adaptador = new AdaptadorPromos(datosPromo,cFont);
		// adaptador.notifyDataSetChanged();
		recycleViewPromo.setAdapter(adaptador);

	}

	/**
	 * Metodo click item recycler
	 * 
	 * @author LEO
	 * 
	 */

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
