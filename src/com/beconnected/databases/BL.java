package com.beconnected.databases;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by Administrador on 18/09/2015.
 */
public class BL {
	private static BL bl;
	private ConnectionManager connManager;

	public BL() {

	}

	public static BL getBl() {
		if (bl == null) {
			bl = new BL();
		}
		return bl;
	}


	
	//----
	public ConnectionManager getConnManager() {
		if (connManager == null) {
			connManager = new ConnectionManager();
		}
		return connManager;
	}

	public String getFechaOficial() {

		Date dateOficial = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd - HH:mm:ss");
		return sdf.format(dateOficial);
	}
}
