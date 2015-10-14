package com.beconnected.adm;



import java.util.ArrayList;

import com.beconnected.R;
import com.beconnected.TabsUsuario;
import com.beconnected.databases.BL;
import com.beconnected.databases.DL;
import com.beconnected.databases.Info;

import android.content.Intent;
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
	// private TextView txtAbSubTitulo;
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
				BL.getBl().actualizarInfo(info);
				Toast.makeText(TabsAdmInfo.this,
						getResources().getString(R.string.info_actualizada),
						Toast.LENGTH_SHORT).show();
				
				
				
				
				
			}
		});

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);


		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
	//	return true;
		// if (drawerToggle.onOptionsItemSelected(item)) {
		// return true;
		// }

		int id = item.getItemId();
		// noinspection SimplifiableIfStatement
		if (id == R.id.action_usuario) {

			Intent usuario = new Intent(TabsAdmInfo.this,
					TabsUsuario.class);
			startActivity(usuario);

			return true;
		}

		if (id == R.id.action_subir) {
			
			
			return true;
		}

		if (id == R.id.action_cerrar) {

			return true;
		}
//
//		if (id == R.id.action_subir) {
//
//			return true;
//		}
//
//		if (id == R.id.action_eliminar) {
//
//			return true;
//		}
//		if (id == R.id.action_adeful) {
//
//			return true;
//		}
//
//		if (id == R.id.action_lifuba) {
//
//			return true;
//		}
//
//		if (id == R.id.action_puesto) {
//
//			return true;
//
//		}
//		if (id == R.id.action_posicion) {
//
//			return true;
//		}
//
//		if (id == R.id.action_cargo) {
//
//			return true;
//		}
//
//		if (id == android.R.id.home) {
//
//			NavUtils.navigateUpFromSameTask(this);
//
//			return true;
//		}
		return super.onOptionsItemSelected(item);
//	}
	}
}