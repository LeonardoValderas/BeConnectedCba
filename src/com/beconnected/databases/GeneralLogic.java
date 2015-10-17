package com.beconnected.databases;

import java.io.File;

import android.content.Intent;
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
    
    
   
    
    public GeneralLogic() {

    }


}