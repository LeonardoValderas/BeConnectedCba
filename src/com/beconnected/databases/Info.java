package com.beconnected.databases;

public class Info {

	private String SOMOS = null;
	private String CONTACTO = null;

	public Info(String somos, String contacto) {

		SOMOS = somos;
		CONTACTO = contacto;

	}

	public String getSOMOS() {
		return SOMOS;
	}

	public void setSOMOS(String sOMOS) {
		SOMOS = sOMOS;
	}

	public String getCONTACTO() {
		return CONTACTO;
	}

	public void setCONTACTO(String cONTACTO) {
		CONTACTO = cONTACTO;
	}

}