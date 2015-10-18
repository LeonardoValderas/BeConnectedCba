package com.beconnected.databases;



import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import android.content.Intent;


/**
 * Created by Administrador on 18/09/2015.
 */
public class BL {
    private static BL bl;
	private ConnectionManager connManager;
public BL(){

    }
    public static BL getBl() {
        if (bl == null) {
            bl = new BL();
        }
        return bl;
    }

    public void crearTablasBDAmd() {

        DL.getDl().getSqliteConnection().createTablesBDAdm();
    }
    public void crearTablasBDUsuario() {

        DL.getDl().getSqliteConnection().createTablesBDUsuario();
    }
    
    public void dropTablasBDUsuario() {

        DL.getDl().getSqliteConnection().dropTablasBDUsuario();
    }
    public void dropTablasBDAdm() {

        DL.getDl().getSqliteConnection().dropTablasBDAdm();
    }
    
	public void creaDirectorios() {

		getCarpetaDataBases().mkdirs();
	
		
	}
    
	public synchronized File getCarpetaDataBases() {

		return new File(GeneralLogic.external.getAbsoluteFile() + getDirSd()
				+ "/Bd/");
	}
	


	
	public String getDirSd() {

		String dir_sd;

				dir_sd = GeneralLogic.SD_DIR_AR;
		
	
		return dir_sd;
	}
	

	
	public boolean insertarEmpresa(int id,Empresa empresa) {

		return DL.getDl().getSqliteConnection().insertEmpresa(id,empresa);

	}
	
	
	public boolean insertarEmpresaAdm(Empresa empresa) {

		return DL.getDl().getSqliteConnection().insertEmpresaAdm(empresa);

	}
	public boolean insertarEmpresaUsuario(Empresa empresa) {

		return DL.getDl().getSqliteConnection().insertEmpresaUsuario(empresa);

	}
	

	public ArrayList<Empresa> selectListaEmpresa() {

		return DL.getDl().getSqliteConnection().selectListaEmpresa();

	}
	
	public ArrayList<Empresa> selectListaEmpresaUsuario() {

		return DL.getDl().getSqliteConnection().selectListaEmpresaUsuario();

	}
	
	
	
	
	
	public boolean actualizarEmpresa(Empresa empresa) {

		return DL.getDl().getSqliteConnection().actualizarEmpresa(empresa);

	}
	public boolean eliminarEmpresa(int id) {

		return DL.getDl().getSqliteConnection().eliminarEmpresa(id);

	}
	
	
	
	public boolean insertarPromo(int id,Promo promo) {

		return DL.getDl().getSqliteConnection().insertPromo(id,promo);

	}
	
	public boolean insertarPromoAdm(Promo promo) {

		return DL.getDl().getSqliteConnection().insertPromoAdm(promo);

	}
	public boolean insertarPromoUsuario(Promo promo) {

		return DL.getDl().getSqliteConnection().insertPromoUsuario(promo);

	}
	
	public ArrayList<Promo> selectListaPromo() {

		return DL.getDl().getSqliteConnection().selectListaPromo();

	}
	
	public ArrayList<Promo> selectListaPromoUsuario() {

		return DL.getDl().getSqliteConnection().selectListaPromoUsuario();

	}
	
	public boolean actualizarPromo(Promo promo) {

		return DL.getDl().getSqliteConnection().actualizarPromo(promo);

	}
	
	public boolean eliminarPromo(int id) {

		return DL.getDl().getSqliteConnection().eliminarPromo(id);

	}
	public boolean eliminarPromoEmpresa(int id) {

		return DL.getDl().getSqliteConnection().eliminarPromoEmpresa(id);

	}
	
	public boolean insertarInfo() {

		return DL.getDl().getSqliteConnection().insertInfo();

	}
	
	public boolean insertarInfoUsuario() {

		return DL.getDl().getSqliteConnection().insertInfoUsuario();

	}
	
	public ArrayList<Info> selectListaInfo() {

		return DL.getDl().getSqliteConnection().selectListaInfo();

	}
	
	public ArrayList<Info> selectListaInfoUsuario() {

		return DL.getDl().getSqliteConnection().selectListaInfoUsuario();

	}
	
	public boolean actualizarInfo(Info info) {

		return DL.getDl().getSqliteConnection().actualizarInfo(info);

	}
	
	public boolean actualizarInfoUsuario(Info info) {

		return DL.getDl().getSqliteConnection().actualizarInfoUsuario(info);

	}
	
	public boolean insertarIdAdm(int id) {

		return DL.getDl().getSqliteConnection().insertIdAdm(id);

	}
	
	public int selectIdAdm() {

		return DL.getDl().getSqliteConnection().selectIdAdm();

	}
	
	
	public boolean insertarIdUsuario(int id) {

		return DL.getDl().getSqliteConnection().insertIdUsuario(id);

	}
	
	public int selectIdUsuario() {

		return DL.getDl().getSqliteConnection().selectIdUsuario();

	}
	

	

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

