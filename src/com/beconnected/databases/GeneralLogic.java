package com.beconnected.databases;

import java.io.File;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Environment;

public class GeneralLogic {
	// nombre de la aplicacion
	public static String APK_NAME = "BeConnected.apk";
	// nombre de la base
	public static String DATABASE_NAME = "be_connected.sqlite";

	// directorios comunes.
	public static File external = Environment.getExternalStorageDirectory();
	public static File internal = Environment.getDataDirectory();

	// directorios de la memoria sd de la aplicacion.
	public static String SD_DIR_AR = "/BE CONENCTED 1.0";

	// directorio de la memoria interna de la aplicacion
	public static File IN_DIR = new File(external.getAbsolutePath()
			+ "/data/com.beconnected/databases");

	public static String URL_LOGO = "http://beconnected.esy.es/BeConnected/picture/";

	public static void close(Context context) {

		Intent intent = new Intent(Intent.ACTION_MAIN);
		intent.addCategory(Intent.CATEGORY_HOME);
		intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		context.startActivity(intent);
		
	}

	public static boolean conexionInternet(Context context) {

		ConnectivityManager connectMgr = (ConnectivityManager) context
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		if (connectMgr != null) {
			NetworkInfo[] netInfo = connectMgr.getAllNetworkInfo();
			if (netInfo != null) {
				for (NetworkInfo net : netInfo) {
					if (net.getState() == NetworkInfo.State.CONNECTED) {
						// if (net.getType() ==
						// ConnectivityManager.TYPE_MOBILE||net.getType() ==
						// ConnectivityManager.TYPE_WIFI) {

						return true;

					}
				}
			}

			return false;
		}
		return false;

	}

	public GeneralLogic() {

	}

}