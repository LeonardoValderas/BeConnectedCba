package com.beconnected;

import java.util.ArrayList;

import com.beconnected.databases.BL;
import com.beconnected.databases.Promo;
import android.content.Context;
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

/**
 * A simple {@link Fragment} subclass. Use the
 * {@link FragmentGenerarNoticia.sgoliver.android.toolbartabs.Fragment1#newInstance}
 * factory method to create an instance of this fragment.
 */
public class FragmentPromos extends Fragment {

	private RecyclerView recycleViewPromo;
	private ArrayList<Promo> datosPromo;
	private AdaptadorPromos adaptador;

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

	public void recyclerViewLoadPromo() {

		recycleViewPromo.setLayoutManager(new LinearLayoutManager(
				getActivity(), LinearLayoutManager.VERTICAL, false));
		recycleViewPromo.addItemDecoration(new DividerItemDecoration(
				getActivity(), DividerItemDecoration.VERTICAL_LIST));
		recycleViewPromo.setItemAnimator(new DefaultItemAnimator());
		datosPromo = BL.getBl().selectListaPromoUsuario();

		adaptador = new AdaptadorPromos(datosPromo);
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
