package com.beconnected.databases;

public class Empresa {

private int ID_EMPRESA;
private String EMPRESA=null;
private String LONGITUD=null;
private String LATIDUD=null;
private byte[] LOGO;




public Empresa(int id,String empresa,String longitud,String latitud,byte[] logo ){
	
	

	ID_EMPRESA=id;
	EMPRESA=empresa;
	LONGITUD=longitud;
	LATIDUD=latitud ;
    LOGO=logo;
	
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



}
