package com.beconnected.databases;

public class Promo {

private int ID_PROMO;
private String TITULO=null;
private String DESCRIPCION=null;

private int ID_EMPRESA;
private byte[] LOGO=null;
private String FECHA_INICIO=null;
private String FECHA_FIN=null;



public Promo(int id,String titulo,String descripcion,int id_empresa,byte[] logo,String fecha_inicio,String fecha_fin){
	
	

 ID_PROMO=id;
 TITULO=titulo;
 DESCRIPCION=descripcion;
 ID_EMPRESA=id_empresa;
 LOGO = logo;
 FECHA_INICIO=fecha_inicio;
 FECHA_FIN=fecha_fin;

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



public int getID_EMPRESA() {
	return ID_EMPRESA;
}



public void setID_EMPRESA(int iD_EMPRESA) {
	ID_EMPRESA = iD_EMPRESA;
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




}