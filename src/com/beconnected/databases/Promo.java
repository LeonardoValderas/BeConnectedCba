package com.beconnected.databases;

public class Promo {

private int ID_PROMO;
private String TITULO=null;
private String DESCRIPCION=null;
private String EMPRESA=null;
private byte[] LOGO;
private String FECHA_INICIO=null;
private String FECHA_FIN=null;
private String USUARIO=null;
private String FECHA_CREACION=null;



public Promo(int id,String titulo,String descripcion,String empresa,byte[] logo,String fecha_inicio,String fecha_fin,String usuario,String fecha_creacion ){
	
	

 ID_PROMO=id;
 TITULO=titulo;
 DESCRIPCION=descripcion;
 EMPRESA=empresa;
 LOGO=logo;
 FECHA_INICIO=fecha_inicio;
 FECHA_FIN=fecha_fin;
 USUARIO=usuario;
 FECHA_CREACION = fecha_creacion;	
}



public int getID_PROMO() {
	return ID_PROMO;
}



public void setID_PROMO(int iD_PROMO) {
	ID_PROMO = iD_PROMO;
}



public String getTITULO() {
	return TITULO;
}



public void setTITULO(String tITULO) {
	TITULO = tITULO;
}



public String getDESCRIPCION() {
	return DESCRIPCION;
}



public void setDESCRIPCION(String dESCRIPCION) {
	DESCRIPCION = dESCRIPCION;
}



public String getEMPRESA() {
	return EMPRESA;
}



public void setEMPRESA(String eMPRESA) {
	EMPRESA = eMPRESA;
}



public byte[] getLOGO() {
	return LOGO;
}



public void setLOGO(byte[] lOGO) {
	LOGO = lOGO;
}



public String getFECHA_INICIO() {
	return FECHA_INICIO;
}



public void setFECHA_INICIO(String fECHA_INICIO) {
	FECHA_INICIO = fECHA_INICIO;
}



public String getFECHA_FIN() {
	return FECHA_FIN;
}



public void setFECHA_FIN(String fECHA_FIN) {
	FECHA_FIN = fECHA_FIN;
}



public String getUSUARIO() {
	return USUARIO;
}



public void setUSUARIO(String uSUARIO) {
	USUARIO = uSUARIO;
}



public String getFECHA_CREACION() {
	return FECHA_CREACION;
}



public void setFECHA_CREACION(String fECHA_CREACION) {
	FECHA_CREACION = fECHA_CREACION;
}

}
