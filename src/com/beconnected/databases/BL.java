package com.beconnected.databases;



import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;


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

    public void crearTablasBD() {

        DL.getDl().getSqliteConnection().createTablesBD();
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
	

	/**
	 * Metodos get set base de datos Liga
	 * @return
	 */
	
	
	public boolean insertarPromo(Promo promo) {

		return DL.getDl().getSqliteConnection().insertPromo(promo);

	}
	public ArrayList<Promo> selectListaPromo() {

		return DL.getDl().getSqliteConnection().selectListaPromo();

	}
	
//	public boolean actualizarEquipoAdeful(Equipo equipo) {
//
//		return DL.getDl().getSqLiteDBConnection().actualizarEquipoAdeful(equipo);
//
//	}
//	
//	public boolean eliminarEquipoAdeful(int id) {
//
//		return DL.getDl().getSqLiteDBConnection().eliminarEquipoAdeful(id);
//
//	}
//	
//	
//	public boolean insertarDivisionAdeful(Division division) {
//
//		return DL.getDl().getSqLiteDBConnection().insertDivisionAdeful(division);
//
//	}
//	
//	public ArrayList<Division> selectListaDivisionAdeful() {
//
//		return DL.getDl().getSqLiteDBConnection().selectListaDivisionAdeful();
//
//	}
//	
//	public boolean actualizarDivisionAdeful(Division division) {
//
//		return DL.getDl().getSqLiteDBConnection().actualizarDivisionAdeful(division);
//
//	}
//	public boolean eliminarDivisionAdeful(int id) {
//
//		return DL.getDl().getSqLiteDBConnection().eliminarDivisionAdeful(id);
//
//	}
//	
//	public boolean insertarTorneoAdeful(Torneo torneo) {
//
//		return DL.getDl().getSqLiteDBConnection().insertTorneoAdeful(torneo);
//
//	}
//	
//	public ArrayList<Torneo> selectListaTorneoAdeful() {
//
//		return DL.getDl().getSqLiteDBConnection().selectListaTorneoAdeful();
//
//	}
//	
//	public boolean actualizarTorneoAdeful(Torneo torneo) {
//
//		return DL.getDl().getSqLiteDBConnection().actualizarTorneoAdeful(torneo);
//
//	}
//	public boolean eliminarTorneoAdeful(int id) {
//
//		return DL.getDl().getSqLiteDBConnection().eliminarTorneoAdeful(id);
//
//	}
//	
//	public boolean insertarCanchaAdeful(Cancha cancha) {
//
//		return DL.getDl().getSqLiteDBConnection().insertCanchaAdeful(cancha);
//
//	}
//	
//	
//	public ArrayList<Cancha> selectListaCanchaAdeful() {
//
//		return DL.getDl().getSqLiteDBConnection().selectListaCanchaAdeful();
//
//	}
//	
//	public boolean actualizarCanchaAdeful(Cancha cancha) {
//
//		return DL.getDl().getSqLiteDBConnection().actualizarCanchaAdeful(cancha);
//
//	}
//	public boolean eliminarCanchaAdeful(int id) {
//
//		return DL.getDl().getSqLiteDBConnection().eliminarCanchaAdeful(id);
//
//	}
//	
	/**
	 * Metodos get set base de datos Mi EQUIPO
	 * @return
	 */
//	public boolean insertarFecha(Fecha fecha) {
//
//		return DL.getDl().getSqLiteDBConnection().insertFecha(fecha);
//
//	}
//	public ArrayList<Fecha> selectListaFecha() {
//
//		return DL.getDl().getSqLiteDBConnection().selectListaFecha();
//
//	}
//	public boolean insertarAnio(Anio anio) {
//
//		return DL.getDl().getSqLiteDBConnection().insertAnio(anio);
//
//	}
//	public ArrayList<Anio> selectListaAnio() {
//
//		return DL.getDl().getSqLiteDBConnection().selectListaAnio();
//
//	}
//	
//	
//	public boolean insertarFixtureAdeful(Fixture fixture) {
//
//		return DL.getDl().getSqLiteDBConnection().insertFixtureAdeful(fixture);
//
//	}
	
	

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

