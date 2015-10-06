package com.beconnected;

import java.util.ArrayList;


import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
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

public class FragmentInfo extends Fragment {

	//private AlertsMenu alertMenu;
	private RecyclerView recycleViewDivision;
//	private ArrayList<Division> divisionArray;
//	private Division division;
//	private AdaptadorDivision adaptadorDivision;
	private FloatingActionButton botonFloating;
	private EditText editTextDivision;
	private boolean insertar = true;
	private int posicion;

	public static FragmentInfo newInstance() {
		FragmentInfo fragment = new FragmentInfo();
		return fragment;
	}

	public FragmentInfo() {
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
		return inflater.inflate(R.layout.fragment_info, container,
				false);

	}

	private void init() {
//
//		editTextDivision = (EditText) getView().findViewById(
//				R.id.editTextDescripcion);
//		editTextDivision.setHint("Ingrese una División");
//		editTextDivision.setHintTextColor(Color.GRAY);
//
//		ImageButton imageButtonEquipo = (ImageButton) getView().findViewById(
//				R.id.imageButtonEquipo_Cancha);
//		imageButtonEquipo.setVisibility(View.GONE);
//
//		recycleViewDivision = (RecyclerView) getView().findViewById(
//				R.id.recycleViewGeneral);
//
//		recyclerViewLoadDivision();
//		recycleViewDivision.addOnItemTouchListener(new RecyclerTouchListener(
//				getActivity(), recycleViewDivision, new ClickListener() {
//
//					@Override
//					public void onLongClick(View view, final int position) {
//
//						alertMenu = new AlertsMenu(getActivity(), "ALERTA",
//								"Desea eliminar la división?", null, null);
//						alertMenu.btnAceptar.setText("Aceptar");
//						alertMenu.btnCancelar.setText("Cancelar");
//
//						alertMenu.btnAceptar
//								.setOnClickListener(new View.OnClickListener() {
//
//									@Override
//									public void onClick(View v) {
//										// TODO Auto-generated method stub
//										BL.getBl().eliminarDivisionAdeful(
//												divisionArray.get(position)
//														.getID_DIVISION());
//										recyclerViewLoadDivision();
//
//										Toast.makeText(
//												getActivity(),
//												"División Eliminada Correctamente",
//												Toast.LENGTH_SHORT).show();
//
//										alertMenu.alertDialog.dismiss();
//
//									}
//								});
//						alertMenu.btnCancelar
//								.setOnClickListener(new View.OnClickListener() {
//
//									@Override
//									public void onClick(View v) {
//										// TODO Auto-generated method stub
//										alertMenu.alertDialog.dismiss();
//									}
//								});
//
//					}
//
//					@Override
//					public void onClick(View view, int position) {
//						// TODO Auto-generated method stub
//
//						insertar = false;
//						editTextDivision.setText(divisionArray.get(position)
//								.getDESCRIPCION());
//
//						posicion = position;
//
//					}
//				}));
//
//		botonFloating = (FloatingActionButton) getView().findViewById(
//				R.id.botonFloating);
//		botonFloating.setOnClickListener(new View.OnClickListener() {
//
//			@SuppressLint("NewApi")
//			public void onClick(View view) {
//
//				if (editTextDivision.getText().toString().equals("")) {
//					Toast.makeText(getActivity(), "Ingrese la División.",
//							Toast.LENGTH_SHORT).show();
//
//				} else {
//
//					if (insertar) {
//
//						String usuario = "Administrador";
//						String fechaCreacion = BL.getBl().getFechaOficial();
//						String fechaActualizacion = fechaCreacion;
//						String estado = "P";
//						String tabla = "DIVISION_ADEFUL";
//
//						division = new Division(0, editTextDivision.getText()
//								.toString(), usuario, fechaCreacion,
//								fechaActualizacion, estado, tabla);
//
//						BL.getBl().insertarDivisionAdeful(division);
//
//						recyclerViewLoadDivision();
//
//						editTextDivision.setText("");
//
//						Toast.makeText(getActivity(),
//								"División cargada correctamente.",
//								Toast.LENGTH_SHORT).show();
//
//					} else {
//
//						String usuario = "Administrador";
//						String fechaActualizacion = BL.getBl()
//								.getFechaOficial();
//						String estado = "P";
//						// String tabla = "DIVISION_ADEFUL";
//
//						division = new Division(divisionArray.get(posicion)
//								.getID_DIVISION(), editTextDivision.getText()
//								.toString(), usuario, null, fechaActualizacion,
//								estado, null);
//
//						BL.getBl().actualizarDivisionAdeful(division);
//
//						recyclerViewLoadDivision();
//
//						editTextDivision.setText("");
//
//						insertar = true;
//
//						Toast.makeText(getActivity(),
//								"División actualizada correctamente.",
//								Toast.LENGTH_SHORT).show();
//
//					}
//
//				}
//
//			}
//		});
//
//	}
//
//	public void recyclerViewLoadDivision() {
//		recycleViewDivision.setLayoutManager(new LinearLayoutManager(
//				getActivity(), LinearLayoutManager.VERTICAL, false));
//		recycleViewDivision.addItemDecoration(new DividerItemDecoration(
//				getActivity(), DividerItemDecoration.VERTICAL_LIST));
//		recycleViewDivision.setItemAnimator(new DefaultItemAnimator());
//
//		divisionArray = BL.getBl().selectListaDivisionAdeful();
//		adaptadorDivision = new AdaptadorDivision(divisionArray);
//		adaptadorDivision.notifyDataSetChanged();
//		recycleViewDivision.setAdapter(adaptadorDivision);
//
//	}
//
//	/**
//	 * Metodo click item recycler
//	 * 
//	 * @author LEO
//	 * 
//	 */
//
//	public static interface ClickListener {
//
//		public void onClick(View view, int position);
//
//		public void onLongClick(View view, int position);
//
//	}
//
//	static class RecyclerTouchListener implements
//			RecyclerView.OnItemTouchListener {
//
//		private GestureDetector detector;
//		private ClickListener clickListener;
//
//		public RecyclerTouchListener(Context context,
//				final RecyclerView recyclerView,
//				final ClickListener clickListener) {
//			this.clickListener = clickListener;
//			detector = new GestureDetector(context,
//					new GestureDetector.SimpleOnGestureListener() {
//
//						@Override
//						public boolean onSingleTapUp(MotionEvent e) {
//							return true;
//						}
//
//						@Override
//						public void onLongPress(MotionEvent e) {
//							View child = recyclerView.findChildViewUnder(
//									e.getX(), e.getY());
//							if (child != null && clickListener != null) {
//								clickListener.onLongClick(child,
//										recyclerView.getChildPosition(child));
//							}
//						}
//					});
//
//		}
//
//		@Override
//		public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {
//			// TODO Auto-generated method stub
//			View child = rv.findChildViewUnder(e.getX(), e.getY());
//			if (child != null && clickListener != null
//					&& detector.onTouchEvent(e)) {
//				clickListener.onClick(child, rv.getChildPosition(child));
//			}
//			return false;
//		}
//
//		@Override
//		public void onTouchEvent(RecyclerView rv, MotionEvent e) {
//
//		}
//
//		@Override
//		public void onRequestDisallowInterceptTouchEvent(boolean arg0) {
//			// TODO Auto-generated method stub
//
//		}
//
//	}
//
//	// /**
//	// * metodo que se ejecuta al presionar boton atras. 17/08/2015
//	// */
//	// public void onBackPressed() {
//	// getView().setFocusableInTouchMode(true);
//	// getView().requestFocus();
//	//
//	// getView().setOnKeyListener(new View.OnKeyListener() {
//	// @Override
//	// public boolean onKey(View v, int keyCode, KeyEvent event) {
//	// if (event.getAction() == KeyEvent.ACTION_DOWN) {
//	// if (keyCode == KeyEvent.KEYCODE_BACK) {
//	//
//	// Fragment fragment = null;
//	// // fragment = new FragmentEstrella();
//	//
//	// getFragmentManager().beginTransaction()
//	// .replace(R.id.content_frame, fragment).commit();
//	//
//	// return true;
//	// }
//	// }
//	// return false;
//	// }
//	// });
//	// }

}
}