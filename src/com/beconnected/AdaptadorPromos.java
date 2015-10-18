package com.beconnected;

import java.util.ArrayList;

import com.beconnected.databases.Promo;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

public class AdaptadorPromos extends
		RecyclerView.Adapter<AdaptadorPromos.PromosViewHolder> implements
		View.OnClickListener {

	private View.OnClickListener listener;
	private ArrayList<Promo> datosPromo;

	public static class PromosViewHolder extends RecyclerView.ViewHolder {

		private TextView tituloRecyclerView;
		private TextView descripcionRecyclerView;
		private TextView vigenciaRecyclerView;
		private ImageView imageViewLogo;

		public PromosViewHolder(View itemView) {
			super(itemView);

			tituloRecyclerView = (TextView) itemView
					.findViewById(R.id.tituloRecyclerView);
			descripcionRecyclerView = (TextView) itemView
					.findViewById(R.id.descripcionRecyclerView);
			vigenciaRecyclerView = (TextView) itemView
					.findViewById(R.id.vigenciaRecyclerView);

			imageViewLogo = (ImageView) itemView
					.findViewById(R.id.imageViewLogo);
		}

		@SuppressLint("NewApi")
		public void bindTitular(Promo promo) {

			tituloRecyclerView.setText(promo.getTITULO());
			descripcionRecyclerView.setText(promo.getDESCRIPCION());
			vigenciaRecyclerView.setText(promo.getFECHA_INICIO() + "-"
					+ promo.getFECHA_FIN());

			byte[] image = promo.getLOGO();
			if (image == null) {

				imageViewLogo.setImageResource(R.drawable.logo);
			} else {
				Bitmap theImage = BitmapFactory.decodeByteArray(
						promo.getLOGO(), 0, promo.getLOGO().length);

				imageViewLogo.setImageBitmap(theImage);

			}
		}
	}

	public AdaptadorPromos(ArrayList<Promo> datosPromo) {
		this.datosPromo = datosPromo;

	}

	@Override
	public PromosViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
		View itemView = LayoutInflater.from(viewGroup.getContext()).inflate(
				R.layout.recyclerview_item, viewGroup, false);

		itemView.setOnClickListener(this);
		// android:background="?android:attr/selectableItemBackground"

		PromosViewHolder tvh = new PromosViewHolder(itemView);

		// notifyItemInserted(viewType);
		return tvh;
	}

	@Override
	public void onBindViewHolder(PromosViewHolder viewHolder, int pos) {
		Promo promo = datosPromo.get(pos);

		viewHolder.bindTitular(promo);
		// notifyItemInserted(viewType);
	}

	@Override
	public int getItemCount() {
		return datosPromo.size();
	}

	public void setOnClickListener(View.OnClickListener listener) {
		this.listener = listener;
	}

	@Override
	public void onClick(View view) {
		if (listener != null)
			listener.onClick(view);

	}
}