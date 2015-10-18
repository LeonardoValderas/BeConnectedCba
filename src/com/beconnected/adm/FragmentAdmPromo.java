package com.beconnected.adm;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import org.json.JSONException;
import org.json.JSONObject;
import com.beconnected.R;
import com.beconnected.databases.BL;
import com.beconnected.databases.Empresa;
import com.beconnected.databases.GeneralLogic;
import com.beconnected.databases.Promo;
import com.beconnected.databases.Request;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.Toast;

/**
 * A simple {@link Fragment} subclass. Use the
 * {@link FragmentGenerarNoticia.sgoliver.android.toolbartabs.Fragment1#newInstance}
 * factory method to create an instance of this fragment.
 */
public class FragmentAdmPromo extends Fragment {

	private ArrayList<Empresa> empresaArray;
	private ArrayList<Promo> promoArray;
	private EditText editTextTitulo;
	private EditText editTextDescripcion;
	private Spinner spinnerEmpresa;
	private Button buttonGuardar;
	private boolean insertar = true;
	private int posicion;
	private Empresa empresa;
	private boolean actualizar = false;
	private AdapterAdmPromo adapterAdmPromo;
	private Promo promo;
	private boolean botonFecha = true; // desde
	private boolean radiobutton = true; // desde
	private Button buttonDesde;
	private Button buttonHasta;
	private RadioButton radioButtonStock;
	private DateFormat formate = DateFormat.getDateInstance();
	private Calendar calendar = Calendar.getInstance();
	private int idEmpresaExtra;
	private String tituloExtra;
	private String descripcionExtra;
	private String desdeExtra;
	private String hastaExtra;
	private static final String TAG_SUCCESS = "success";
	private static final String TAG_MESSAGE = "message";
	private ProgressDialog dialog;
	private static final String TAG_ID = "id";
	private AlertsMenu alertsMenu;

	public static FragmentAdmPromo newInstance() {
		FragmentAdmPromo fragment = new FragmentAdmPromo();
		return fragment;
	}

	public FragmentAdmPromo() {
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
		return inflater.inflate(R.layout.fragment_adm_promo, container, false);

	}

	private void init() {

		empresaArray = BL.getBl().selectListaEmpresa();

		editTextTitulo = (EditText) getView().findViewById(R.id.editTextTitulo);
		editTextDescripcion = (EditText) getView().findViewById(
				R.id.editTextDescripcion);
		spinnerEmpresa = (Spinner) getView().findViewById(R.id.spinnerEmpresa);

		adapterAdmPromo = new AdapterAdmPromo(getActivity(),
				R.layout.simple_spinner_dropdown_item, empresaArray);
		spinnerEmpresa.setAdapter(adapterAdmPromo);

		buttonGuardar = (Button) getView().findViewById(R.id.buttonGuardar);

		actualizar = getActivity().getIntent().getBooleanExtra("actualizar",
				false);

		buttonDesde = (Button) getView().findViewById(R.id.buttonDesde);
		buttonHasta = (Button) getView().findViewById(R.id.buttonHasta);
		radioButtonStock = (RadioButton) getView().findViewById(
				R.id.radioButtonStock);

		if (actualizar) {

			posicion = getActivity().getIntent().getIntExtra("posicion", 0);

			idEmpresaExtra = getActivity().getIntent()
					.getIntExtra("empresa", 0);
			tituloExtra = getActivity().getIntent().getStringExtra("titulo");
			descripcionExtra = getActivity().getIntent().getStringExtra(
					"descripcion");
			desdeExtra = getActivity().getIntent().getStringExtra("desde");
			hastaExtra = getActivity().getIntent().getStringExtra("hasta");

			spinnerEmpresa.setSelection(idEmpresaExtra - 1);
			editTextTitulo.setText(tituloExtra);
			editTextDescripcion.setText(descripcionExtra);
			buttonDesde.setText(desdeExtra);

			if (!hastaExtra.equals(getActivity().getResources().getString(
					R.string.agotar_stock))) {

				buttonHasta.setText(hastaExtra);
			} else {
				radioButtonStock.setChecked(true);
				buttonHasta.setEnabled(false);
				radiobutton = false;
			}

			insertar = false;

		}

		buttonDesde.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				botonFecha = true;
				setDate();

			}
		});

		buttonHasta.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				botonFecha = false;
				setDate();

			}
		});
		radioButtonStock.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

				if (radiobutton) {
					buttonHasta.setEnabled(false);
					radiobutton = false;
				} else {
					radioButtonStock.setChecked(false);
					buttonHasta.setEnabled(true);
					radiobutton = true;

				}

			}
		});

		buttonGuardar.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub

				if (editTextTitulo.getText().toString().equals("")
						|| editTextDescripcion.getText().toString().equals("")) {

					Toast.makeText(getActivity(),
							getResources().getString(R.string.datos_vacios),
							Toast.LENGTH_SHORT).show();

				} else if (buttonDesde.getText().toString().equals("Desde")) {

					Toast.makeText(
							getActivity(),
							getActivity().getResources().getString(
									R.string.fecha_inicio), Toast.LENGTH_SHORT)
							.show();

				} else if (buttonHasta.getText().toString().equals("Hasta")
						&& !radioButtonStock.isChecked()) {

					Toast.makeText(
							getActivity(),
							getActivity().getResources().getString(
									R.string.fecha_fin),

							Toast.LENGTH_SHORT).show();

				} else {

					if (insertar) {

						empresa = (Empresa) spinnerEmpresa.getSelectedItem();

						if (radioButtonStock.isChecked()) {

							promo = new Promo(0, editTextTitulo.getText()
									.toString(), editTextDescripcion.getText()
									.toString(), empresa.getID_EMPRESA(), null,
									buttonDesde.getText().toString(),
									getActivity().getResources().getString(
											R.string.agotar_stock));

						} else {

							promo = new Promo(0, editTextTitulo.getText()
									.toString(), editTextDescripcion.getText()
									.toString(), empresa.getID_EMPRESA(), null,
									buttonDesde.getText().toString(),
									buttonHasta.getText().toString());

						}

						Request p = new Request();
						p.setMethod("POST");
						p.setQuery("SUBIR");
						p.setParametrosDatos("titulo", promo.getTITULO());
						p.setParametrosDatos("descripcion",
								promo.getDESCRIPCION());
						p.setParametrosDatos("id_empresa",
								String.valueOf(promo.getID_EMPRESA()));
						p.setParametrosDatos("fecha_inicio",
								promo.getFECHA_INICIO());
						p.setParametrosDatos("fecha_fin", promo.getFECHA_FIN());

						if (GeneralLogic.conexionInternet(getActivity())) {
							TaskPromo taskPromo = new TaskPromo();
							taskPromo.execute(p);
							editTextTitulo.setText("");
							editTextDescripcion.setText("");
							buttonDesde.setText("Desde");
							buttonHasta.setText("Hasta");
							radioButtonStock.setChecked(false);
						} else {

							alertsMenu = new AlertsMenu(
									getActivity(),
									"ATENCIÓN!!!",
									"Por favor verifique su conexión de Internet",
									"Aceptar", null);
							alertsMenu.btnAceptar
									.setOnClickListener(new View.OnClickListener() {

										@Override
										public void onClick(View v) {
											// TODO Auto-generated method stub

											alertsMenu.alertDialog.dismiss();
											// close();

										}
									});

							alertsMenu.btnCancelar.setVisibility(View.GONE);

						}

					} else {

						promoArray = BL.getBl().selectListaPromo();
						if (radioButtonStock.isChecked()) {

							promo = new Promo(promoArray.get(posicion)
									.getID_PROMO(), editTextTitulo.getText()
									.toString(), editTextDescripcion.getText()
									.toString(), promoArray.get(posicion)
									.getID_EMPRESA(), null, buttonDesde
									.getText().toString(), "Hasta Agotar Stock");

						} else {
							promoArray = BL.getBl().selectListaPromo();

							promo = new Promo(promoArray.get(posicion)
									.getID_PROMO(), editTextTitulo.getText()
									.toString(), editTextDescripcion.getText()
									.toString(), promoArray.get(posicion)
									.getID_EMPRESA(), null, buttonDesde
									.getText().toString(), buttonHasta
									.getText().toString());

						}

						Request p = new Request();
						p.setMethod("POST");
						p.setQuery("EDITAR");
						p.setParametrosDatos("id_promo",
								String.valueOf(promo.getID_PROMO()));
						p.setParametrosDatos("titulo", promo.getTITULO());
						p.setParametrosDatos("descripcion",
								promo.getDESCRIPCION());
						p.setParametrosDatos("id_empresa",
								String.valueOf(promo.getID_EMPRESA()));
						p.setParametrosDatos("fecha_inicio",
								promo.getFECHA_INICIO());
						p.setParametrosDatos("fecha_fin", promo.getFECHA_FIN());

						if (GeneralLogic.conexionInternet(getActivity())) {
							TaskPromo taskPromo = new TaskPromo();
							taskPromo.execute(p);
							editTextTitulo.setText("");
							editTextDescripcion.setText("");
							buttonDesde.setText("Desde");
							buttonHasta.setText("Hasta");
							radioButtonStock.setChecked(false);
						} else {

							alertsMenu = new AlertsMenu(
									getActivity(),
									"ATENCIÓN!!!",
									"Por favor verifique su conexión de Internet",
									"Aceptar", null);
							alertsMenu.btnAceptar
									.setOnClickListener(new View.OnClickListener() {

										@Override
										public void onClick(View v) {
											// TODO Auto-generated method stub

											alertsMenu.alertDialog.dismiss();
											// close();

										}
									});

							alertsMenu.btnCancelar.setVisibility(View.GONE);

						}
					}
				}
			}
		});
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

			try {

				JSONObject json = BL.getBl().getConnManager()
						.gestionPromo(params[0]);

				success = json.getInt(TAG_SUCCESS);
				if (success == 1) {
					if (insertar) {
						int id = json.getInt(TAG_ID);
						BL.getBl().insertarPromo(id, promo);
						BL.getBl().getConnManager().push("P");
					} else {
						BL.getBl().actualizarPromo(promo);
					}
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
			Intent i = new Intent(getActivity(), TabsAdmPromo.class);
			startActivity(i);
			insertar = true;

		}

		@Override
		protected void onProgressUpdate(String... values) {
			// textViewDato.append(values[0]+"\n");
		}
	}

	public void updatedateh() {

		buttonHasta.setText(formate.format(calendar.getTime()));

	}

	public void updatedateD() {

		buttonDesde.setText(formate.format(calendar.getTime()));

	}

	public void setDate() {

		new DatePickerDialog(getActivity(), d, calendar.get(Calendar.YEAR),
				calendar.get(Calendar.MONTH),
				calendar.get(Calendar.DAY_OF_MONTH)).show();

	}

	DatePickerDialog.OnDateSetListener d = new DatePickerDialog.OnDateSetListener() {

		@Override
		public void onDateSet(DatePicker view, int year, int monthOfYear,
				int dayOfMonth) {

			calendar.set(Calendar.YEAR, year);
			calendar.set(Calendar.MONTH, monthOfYear);
			calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);

			if (botonFecha) {
				updatedateD();
			} else {

				updatedateh();
			}
		}
	};

}
