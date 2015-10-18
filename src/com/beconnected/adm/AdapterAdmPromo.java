package com.beconnected.adm;

import java.util.ArrayList;

import com.beconnected.R;
import com.beconnected.databases.Empresa;
import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class AdapterAdmPromo extends ArrayAdapter<Empresa> {
	private Activity context;
	ArrayList<Empresa> empresaArray = null;

	public AdapterAdmPromo(Activity context, int resource,
			ArrayList<Empresa> empresaArray) {
		super(context, resource, empresaArray);
		this.context = context;
		this.empresaArray = empresaArray;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) { // Ordinary
																			// view
																			// in
																			// Spinner,
																			// we
																			// use
																			// android.R.layout.simple_spinner_item
		return super.getView(position, convertView, parent);
	}

	public View getDropDownView(int position, View convertView, ViewGroup parent) { // This
																					// view
																					// starts
																					// when
																					// we
																					// click
																					// the
																					// spinner.
		View row = convertView;
		if (row == null) {
			LayoutInflater inflater = context.getLayoutInflater();
			// LayoutInflater inflater = (LayoutInflater)
			// context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
			row = inflater.inflate(R.layout.item_spinner_promo, parent, false);
		}

		Empresa empresa = empresaArray.get(position);

		if (empresa != null) { // Parse the data from each object and set it.
			ImageView imageViewEscudo = (ImageView) row
					.findViewById(R.id.imageViewLogo);
			imageViewEscudo.setVisibility(View.VISIBLE);
			TextView spinnerGeneral = (TextView) row
					.findViewById(R.id.empresaSpinner);

			if (empresa.getLOGO() != null) {
				Bitmap theImage = BitmapFactory.decodeByteArray(
						empresa.getLOGO(), 0, empresa.getLOGO().length);
				imageViewEscudo.setImageBitmap(theImage);
			} else {
				imageViewEscudo.setImageResource(R.drawable.logo);

			}
			if (spinnerGeneral != null)
				spinnerGeneral.setText(empresa.getEMPRESA());

		}

		return row;
	}
}
