package com.beconnected.databases;

public class Empresa {

	private int ID_EMPRESA;
	private String EMPRESA = null;
	private String LONGITUD = null;
	private String LATIDUD = null;
	private byte[] LOGO;
	private String URL_LOGO = null;

	public Empresa(int id, String empresa, String longitud, String latitud,
			byte[] logo, String url_logo) {

		ID_EMPRESA = id;
		EMPRESA = empresa;
		LONGITUD = longitud;
		LATIDUD = latitud;
		LOGO = logo;
		URL_LOGO = url_logo;
	}

	public int getID_EMPRESA() {
		return ID_EMPRESA;
	}

	public void setID_EMPRESA(int iD_EMPRESA) {
		ID_EMPRESA = iD_EMPRESA;
	}

	public String getEMPRESA() {
		return EMPRESA;
	}

	public String toString() {
		return EMPRESA;
	}

	public void setEMPRESA(String eMPRESA) {
		EMPRESA = eMPRESA;
	}

	public String getLONGITUD() {
		return LONGITUD;
	}

	public void setLONGITUD(String lONGITUD) {
		LONGITUD = lONGITUD;
	}

	public String getLATIDUD() {
		return LATIDUD;
	}

	public void setLATIDUD(String lATIDUD) {
		LATIDUD = lATIDUD;
	}

	public byte[] getLOGO() {
		return LOGO;
	}

	public void setLOGO(byte[] lOGO) {
		LOGO = lOGO;
	}

	public String getURL_LOGO() {
		return URL_LOGO;
	}

	public void setURL_LOGO(String uRL_LOGO) {
		URL_LOGO = uRL_LOGO;
	}

}
