package com.beconnected.adm;

import com.beconnected.R;

import android.app.AlertDialog;
import android.content.Context;
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

	public AlertsMenu(Context context, String tituloA, String mensajeA,
			String hint, String hint2) {
		this.context = context;

		final AlertDialog.Builder builder = new AlertDialog.Builder(context);
		LayoutInflater inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View layout = inflater.inflate(R.layout.alerts_menu, null);
		titulo = (TextView) layout.findViewById(R.id.alertGenericoTitulo);
		mensaje = (TextView) layout.findViewById(R.id.alertGenericoTxtMensaje);
		//editTextUno = (EditText) layout.findViewById(R.id.editTextUno);

		//editTextUno.setHint(hint);

		//editTextDos = (EditText) layout.findViewById(R.id.editTextDos);

		//editTextDos.setHint(hint2);

//		tableroTextErrorVacio = (TextView) layout
//				.findViewById(R.id.tableroTextErrorVacio);
		btnAceptar = (Button) layout.findViewById(R.id.alerGenericoBtnAceptar);
		btnCancelar = (Button) layout
				.findViewById(R.id.alerGenericoBtnCancelar);

		titulo.setText(tituloA);

		builder.setView(layout);
		alertDialog = builder.create();

		mensaje.setText(mensajeA);

		alertDialog.show();
	}

}
