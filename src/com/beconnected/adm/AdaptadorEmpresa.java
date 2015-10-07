package com.beconnected.adm;

import java.util.ArrayList;

import com.beconnected.R;
import com.beconnected.databases.Empresa;
import com.beconnected.databases.Promo;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

public class AdaptadorEmpresa extends
		RecyclerView.Adapter<AdaptadorEmpresa.EmpresaViewHolder> implements
		View.OnClickListener {

	// private final static Context context;

	private View.OnClickListener listener;
	private ArrayList<Empresa> datosEmpresa;

	public static class EmpresaViewHolder extends RecyclerView.ViewHolder {

		private TextView tituloRecyclerView;
		private TextView descripcionRecyclerView;
		private TextView vigenciaRecyclerView;
		private ImageView imageViewLogo;

		public EmpresaViewHolder(View itemView) {
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
		public void bindTitular(Empresa empresa) {
		
					
			  tituloRecyclerView.setText(empresa.getEMPRESA());
			//  descripcionRecyclerView.setText(empresa.);
			//  vigenciaRecyclerView.setText(empresa.getFECHA_INICIO()+"-"+promo.getFECHA_FIN());
		

			byte[] image = empresa.getLOGO();
			if (image == null) {

				imageViewLogo.setImageResource(R.drawable.ic_launcher);
			} else {
				Bitmap theImage = BitmapFactory.decodeByteArray(
						empresa.getLOGO(), 0,
						empresa.getLOGO().length);
				
				imageViewLogo.setImageBitmap(theImage);
//				Drawable escudo = new BitmapDrawable(theImage);
//				imageViewEscudo.setImageDrawable(escudo);

			}
		}
	}

	public AdaptadorEmpresa(ArrayList<Empresa> datosEmpresa) {
		this.datosEmpresa = datosEmpresa;
		// notifyItemInserted(datosEquipoArray);
	}

	@Override
	public EmpresaViewHolder onCreateViewHolder(ViewGroup viewGroup,
			int viewType) {
		View itemView = LayoutInflater.from(viewGroup.getContext()).inflate(
				R.layout.recyclerview_item, viewGroup, false);

		itemView.setOnClickListener(this);
		// android:background="?android:attr/selectableItemBackground"

		EmpresaViewHolder tvh = new EmpresaViewHolder(itemView);

		// notifyItemInserted(viewType);
		return tvh;
	}

	@Override
	public void onBindViewHolder(EmpresaViewHolder viewHolder, int pos) {
		Empresa empresa = datosEmpresa.get(pos);

		viewHolder.bindTitular(empresa);
		// notifyItemInserted(viewType);
	}

	@Override
	public int getItemCount() {
		return datosEmpresa.size();
	}

	// public void add(EquipoAdeful item, int position) {
	// datosEquipoArray.add(position, item);
	// notifyItemInserted(position);
	// }

	public void setOnClickListener(View.OnClickListener listener) {
		this.listener = listener;
	}

	@Override
	public void onClick(View view) {
		if (listener != null)
			listener.onClick(view);

	}
}