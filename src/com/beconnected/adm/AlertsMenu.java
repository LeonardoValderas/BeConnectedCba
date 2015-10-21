package com.beconnected.adm;

import com.beconnected.R;

import android.app.AlertDialog;
import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class AlertsMenu {

	Context context;
	public Button btnAceptar = null;
	public Button btnCancelar = null;
	public AlertDialog alertDialog;
	public TextView titulo;
	public TextView mensaje;
	public TextView tableroTextErrorVacio;
	public EditText editTextUno;
	public EditText editTextDos;

    private  Typeface cFont;

	public AlertsMenu(Context context, String tituloA, String mensajeA,
			String hint, String hint2) {
		this.context = context;

		final AlertDialog.Builder builder = new AlertDialog.Builder(context);
		LayoutInflater inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		cFont = Typeface.createFromAsset(context.getAssets(), "fonts/NEUROPOL.ttf");
		View layout = inflater.inflate(R.layout.alerts_menu, null);
		titulo = (TextView) layout.findViewById(R.id.alertGenericoTitulo);
		titulo.setTypeface(cFont);
		mensaje = (TextView) layout.findViewById(R.id.alertGenericoTxtMensaje);
		mensaje.setTypeface(cFont);
		
		btnAceptar = (Button) layout.findViewById(R.id.alerGenericoBtnAceptar);
		btnAceptar.setTypeface(cFont);
		btnAceptar.setText(hint);
		btnCancelar = (Button) layout
				.findViewById(R.id.alerGenericoBtnCancelar);
		btnCancelar.setTypeface(cFont);
		btnCancelar.setText(hint2);
		titulo.setText(tituloA);

		builder.setView(layout);
		alertDialog = builder.create();

		mensaje.setText(mensajeA);

		alertDialog.show();
	}

}
