package com.beconnected;

import java.lang.reflect.Field;
import java.util.ArrayList;

import com.beconnected.databases.BL;
import com.beconnected.databases.ControladorUsuario;
import com.beconnected.databases.Promo;
import android.content.Context;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.GestureDetector;
import android.view.InflateException;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

/**
 * A simple {@link Fragment} subclass. Use the
 * {@link FragmentGenerarNoticia.sgoliver.android.toolbartabs.Fragment1#newInstance}
 * factory method to create an instance of this fragment.
 */
public class FragmentPromos extends Fragment {

	private RecyclerView recycleViewPromo;
	private ArrayList<Promo> datosPromo;
	private AdaptadorPromos adaptador;
	private static View view;
	private  Typeface cFont;
	private ControladorUsuario controladorUsuario;
	
	public static FragmentPromos newInstance() {
		FragmentPromos fragment = new FragmentPromos();
		return fragment;
	}

	public FragmentPromos() {
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
	            view = inflater.inflate(R.layout.fragment_promo, container, false);
	            recycleViewPromo = (RecyclerView) view.findViewById(
	    				R.id.recycleViewPromo);
	          //  TextView messageTextView = (TextView)view.findViewById(R.id.textView);
	           // messageTextView.setText(message);
	        } catch (InflateException e) {
	            /* map is already there, just return view as it is */
	        }

//	      View v = inflater.inflate(R.layout.myfragment_layout, container, false);


	    return view;
		//return inflater.inflate(R.layout.fragment_promo, container, false);

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
	
	private void init() {

//		recycleViewPromo = (RecyclerView) getView().findViewById(
//				R.id.recycleViewPromo);
		recyclerViewLoadPromo();

		recycleViewPromo.addOnItemTouchListener(new RecyclerTouchListener(
				getActivity(), recycleViewPromo, new ClickListener() {

					@Override
					public void onLongClick(View view, final int position) {

					}

					@Override
					public void onClick(View view, int position) {
						// TODO Auto-generated method stub

					}
				}));

	}

	
	@Override
	public void onResume() {
		
		super.onResume();
	}
	
	public void recyclerViewLoadPromo() {
		cFont = Typeface.createFromAsset(getActivity().getAssets(), "fonts/NEUROPOL.ttf");
		recycleViewPromo.setLayoutManager(new LinearLayoutManager(
				getActivity(), LinearLayoutManager.VERTICAL, false));
		recycleViewPromo.addItemDecoration(new DividerItemDecoration(
				getActivity(), DividerItemDecoration.VERTICAL_LIST));
		recycleViewPromo.setItemAnimator(new DefaultItemAnimator());
		

		controladorUsuario.abrirBaseDeDatos();
		datosPromo= controladorUsuario.selectListaPromo();
		controladorUsuario.cerrarBaseDeDatos();
		
		//datosPromo = BL.getBl().selectListaPromoUsuario();

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
