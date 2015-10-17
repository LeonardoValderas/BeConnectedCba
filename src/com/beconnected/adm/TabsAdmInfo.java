package com.beconnected.adm;



import java.util.ArrayList;

import org.json.JSONException;
import org.json.JSONObject;

import com.beconnected.R;
import com.beconnected.TabsUsuario;
import com.beconnected.databases.BL;
import com.beconnected.databases.DL;
import com.beconnected.databases.Info;
import com.beconnected.databases.Request;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.NavUtils;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class TabsAdmInfo extends AppCompatActivity {

	private Toolbar toolbar;
	private ActionBarDrawerToggle drawerToggle;
	private ViewPager viewPager;
	private TabLayout tabLayout;
	private EditText editTextSomos;
	private EditText editTextContacto;
	private Button buttonGuardar;
	private ArrayList<Info> arrayInfo;
	private Info info;
//	private ImageView 
	private int restarMap = 0;
	TextView txtAbSubTitulo;
	private static final String TAG_SUCCESS = "success";
	private static final String TAG_MESSAGE = "message";
	private ProgressDialog dialog;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.adm_info);


		init();
	}

	public void init() {

		arrayInfo = BL.getBl().selectListaInfo();
		
		editTextSomos =(EditText)findViewById(R.id.editTextSomos);
		editTextContacto=(EditText)findViewById(R.id.editTextContacto);
//		
		if(arrayInfo.size()!=0)
		{
		
		editTextSomos.setText(arrayInfo.get(0).getSOMOS().toString());
		editTextContacto.setText(arrayInfo.get(0).getCONTACTO().toString());
		}else
		{
			BL.getBl().insertarInfo();
			arrayInfo = BL.getBl().selectListaInfo();
			editTextSomos.setText(arrayInfo.get(0).getSOMOS().toString());
			editTextContacto.setText(arrayInfo.get(0).getCONTACTO().toString());
		}
			
		buttonGuardar=(Button)findViewById(R.id.buttonGuardar);
		
		
		buttonGuardar.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
				info = new Info(editTextSomos.getText().toString(), editTextContacto.getText().toString());
				
				
				
				Request p = new Request();

				p.setMethod("POST");
				p.setParametrosDatos("somos", info.getSOMOS());
				p.setParametrosDatos("contacto", info.getCONTACTO());
				
				
				TaskInfo TaskInfo = new TaskInfo();
				TaskInfo.execute(p);
				
//				Toast.makeText(TabsAdmInfo.this,
//						getResources().getString(R.string.info_actualizada),
//						Toast.LENGTH_SHORT).show();
//				
				
				
			}
		});

	}

	
	
	// editar  info

	public class TaskInfo extends AsyncTask<Request, String, String> {

		@Override
		protected void onPreExecute() {

			dialog = new ProgressDialog(TabsAdmInfo.this);
			dialog.setMessage("Procesando...");
			dialog.show();

		}

		@Override
		protected String doInBackground(Request... params) {

			int success;

			try {

				JSONObject json = BL.getBl().getConnManager().gestionInfo(params[0]);

				success = json.getInt(TAG_SUCCESS);
				if (success == 1) {
				
						
					BL.getBl().actualizarInfo(info);
					
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

			Toast.makeText((TabsAdmInfo.this), result, Toast.LENGTH_SHORT).show();

			// Toast.makeText(context, "El Empre", Toast.LENGTH_SHORT).show();
			// TaskPromo taskPromo = new TaskPromo();
			// taskPromo.execute("");

		}

		@Override
		protected void onProgressUpdate(String... values) {
			// textViewDato.append(values[0]+"\n");
		}
	}

	
	
	
	
	
	
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);


		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {

		int id = item.getItemId();
		// noinspection SimplifiableIfStatement
		if (id == R.id.action_usuario) {

			Intent usuario = new Intent(TabsAdmInfo.this,
					TabsUsuario.class);
			startActivity(usuario);

			return true;
		}
		if (id == R.id.action_empresa) {
			Intent promo = new Intent(TabsAdmInfo.this, TabsAdmPromo.class);
			startActivity(promo);

			return true;
		}
		if (id == R.id.action_promo) {
			Intent promo = new Intent(TabsAdmInfo.this, TabsAdmPromo.class);
			startActivity(promo);

			return true;
		}

		if (id == R.id.action_sincro) {
			
			
			return true;
		}

		if (id == R.id.action_cerrar) {

			 //public void close(){
			   	  Intent intent = new Intent(Intent.ACTION_MAIN);
			   	  intent.addCategory(Intent.CATEGORY_HOME);
			   	  intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			   	  startActivity(intent);
			   //	    }  
			
			return true;
		}

		return super.onOptionsItemSelected(item);
	}
}