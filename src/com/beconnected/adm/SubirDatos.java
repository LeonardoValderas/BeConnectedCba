package com.beconnected.adm;

import java.util.ArrayList;
import java.util.HashMap;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Base64;
import android.widget.Toast;
import com.beconnected.databases.BL;
import com.beconnected.databases.Empresa;
import com.beconnected.databases.Promo;
import com.beconnected.databases.Request;



public class SubirDatos {

	private ProgressDialog dialog;
	private ArrayList<Empresa> empresaArray;
	private ArrayList<Promo> promoArray;
	   public static final String UPLOAD_URL = "http://beconnected.esy.es/BeConnected/upload.php";
	    public static final String UPLOAD_KEY = "image";
	Context context;

	public SubirDatos(Context context) {

		this.context = context;

	}

	// comienzo de proceso con eliminación de las tablas.
	public void enviarDatos() {

		TaskTruncarTablas taskTruncarTablas = new TaskTruncarTablas();
		taskTruncarTablas.execute("");

	}

	
	//private void uploadImage(){
//        class UploadImage extends AsyncTask<Bitmap,Void,String>{
//
//            ProgressDialog loading;
//            RequestHandler rh = new RequestHandler();
//
//            @Override
//            protected void onPreExecute() {
//                super.onPreExecute();
//                loading = ProgressDialog.show(context, "Uploading...", null,true,true);
//            }
//
//            @Override
//            protected void onPostExecute(String s) {
//                super.onPostExecute(s);
//                loading.dismiss();
//                Toast.makeText(context,s,Toast.LENGTH_LONG).show();
//            }
//
//            @Override
//            protected String doInBackground(Bitmap... params) {
//                Bitmap bitmap = params[0];
//               
//                
//                String uploadImage = getStringImage(bitmap);
//
//                empresaArray = BL.getBl().selectListaEmpresa();
//                for (int i = 0; i < empresaArray.size(); i++) {
//    				p.setMethod("POST");
//    				p.setParametrosDatos("empresa", empresaArray.get(i)
//    						.getEMPRESA().toString());
//    				p.setParametrosDatos("longitud", empresaArray.get(i)
//    						.getLONGITUD().toString());
//    				p.setParametrosDatos("latitud", empresaArray.get(i)
//    						.getLATIDUD().toString());
//
//    				p.setParametrosDatos("logo", Base64.encodeToString(empresaArray
//    						.get(i).getLOGO(), Base64.NO_WRAP));
//    				
//                empresaArray.
//                
//                HashMap<String,String> data = new HashMap<>();
//
//                data.put(UPLOAD_KEY, uploadImage);
//                data.put("name", "foto");
//                String result = rh.sendPostRequest(UPLOAD_URL,data);
//
//                return result;
//            }
//        }
//
//       
	
	
	public class TaskTruncarTablas extends AsyncTask<String, String, String> {

		@Override
		protected void onPreExecute() {
			dialog = new ProgressDialog(context);
			dialog.setMessage("Eliminando Datos...");
			dialog.show();

		}

		@Override
		protected String doInBackground(String... params) {

			// inicia el metodo que trae los datos según su url

			String content = null;
			content = BL.getBl().getConnManager().truncateTable();
			return content; // retorna string al metodo onPostExecute

		}

		@Override
		protected void onPostExecute(String result) {

			dialog.dismiss();

//			TaskEmpresa taskEmpresa = new TaskEmpresa();
//			taskEmpresa.execute("");
		}

		@Override
		protected void onProgressUpdate(String... values) {
			// textViewDato.append(values[0]+"\n");
		}
	}

	//enviar/editar/eliminar empresa
	
	public void resquestDataEmpresa(Request p) {
		
     	TaskEmpresa taskEmpresa = new TaskEmpresa();
		taskEmpresa.execute(p);
		
		//task.execute("Param 1","Param 2","Param 3");
		//task.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR,"Param 1","Param 2","Param 3");
	}
	
	
   
	//Subir una empresa	
	
	public class TaskEmpresa extends AsyncTask<Request, String, String> {

		@Override
		protected void onPreExecute() {

			dialog = new ProgressDialog(context);
		//	dialog.setMessage("Subiendo Datos de la Empresa...");
			dialog.show();

		}

		@Override
		protected String doInBackground(Request... params) {
			RequestHandler rh = new RequestHandler();
			
			
			String content = BL.getBl().getConnManager().enviarEmpresa(params[0]);
			return content; // retorna string al metodo onPostExecute

		}

		@Override
		protected void onPostExecute(String result) {
			dialog.dismiss();
		//	Toast.makeText(context, "El Empre", Toast.LENGTH_SHORT).show();
		//	TaskPromo taskPromo = new TaskPromo();
		//	taskPromo.execute("");

		}

		@Override
		protected void onProgressUpdate(String... values) {
			// textViewDato.append(values[0]+"\n");
		}
	}

	//enviar/editar/eliminar empresa
	
		public void resquestDataPromo(Request p) {
			
			TaskPromo taskPromo = new TaskPromo();
			taskPromo.execute(p);
			
			//task.execute("Param 1","Param 2","Param 3");
			//task.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR,"Param 1","Param 2","Param 3");
		}
		
	
	
	public class TaskPromo extends AsyncTask<Request, String, String> {

		@Override
		protected void onPreExecute() {

			dialog = new ProgressDialog(context);
		//	dialog.setMessage("Editando Datos de la Empresa...");
			dialog.show();

		}

		@Override
		protected String doInBackground(Request... params) {
		///	RequestHandler rh = new RequestHandler();
			
			String content  = BL.getBl().getConnManager().enviarPromo(params[0]);
	
			return content; // retorna string al metodo onPostExecute

		}

		@Override
		protected void onPostExecute(String result) {
			dialog.dismiss();
		//	Toast.makeText(context, "El Empre", Toast.LENGTH_SHORT).show();
		//	TaskPromo taskPromo = new TaskPromo();
		//	taskPromo.execute("");

		}

		@Override
		protected void onProgressUpdate(String... values) {
			// textViewDato.append(values[0]+"\n");
		}
	}
	
	
	
//	
//	public class TaskPromo extends AsyncTask<Request, String, String> {
//
//		@Override
//		protected void onPreExecute() {
//
//			dialog = new ProgressDialog(context);
//			dialog.setMessage("Subiendo Datos Promo...");
//			dialog.show();
//		}
//
//		@Override
//		protected String doInBackground(Request... params) {
//
//			Request p = new Request();
//			String content = null;
//			promoArray = BL.getBl().selectListaPromo();
//			for (int i = 0; i < promoArray.size(); i++) {
//				p.setMethod("POST");
//				p.setParametrosDatos("titulo", promoArray.get(i).getTITULO()
//						.toString());
//				p.setParametrosDatos("descripcion", promoArray.get(i)
//						.getDESCRIPCION().toString());
//				p.setParametrosDatos("id_empresa",
//						String.valueOf(promoArray.get(i).getID_EMPRESA()));
//				p.setParametrosDatos("fecha_inicio", promoArray.get(i)
//						.getFECHA_INICIO().toString());
//				p.setParametrosDatos("fecha_fin", promoArray.get(i)
//						.getFECHA_FIN().toString());
//
//				content = BL.getBl().getConnManager().enviarPromo(p);
//
//			}
//
//			return content; // retorna string al metodo onPostExecute
//
//		}
//
//		@Override
//		protected void onPostExecute(String result) {
//
//			Toast.makeText(context, result, Toast.LENGTH_SHORT).show();
//			dialog.dismiss();
//		}
//
//		@Override
//		protected void onProgressUpdate(String... values) {
//			// textViewDato.append(values[0]+"\n");
//		}
//	}

	


	
	
}
