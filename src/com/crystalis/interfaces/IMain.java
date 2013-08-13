package com.crystalis.interfaces;

public interface IMain {

	public static final boolean DEBUG = true;

	
	public static final int FORMAT_YYYY_MM_DD 			= 0;
	public static final int FORMAT_HH_MM 				= 1;
	public static final int FORMAT_YYYY_MM_DD_HH_MM 		= 2;
	public static final int FORMAT_YYYY_MM_DD_T_HH_MM_SS_SSS	= 3;
	public static final int FORMAT_YYYY_MM_DD_T_HH_MM_SS 		= 4;
	public static final int FORMAT_HH_MM_MMM_DD 			= 5;
	public static final int FORMAT_MMM_DD_YYYY_HH_MM 		= 6;
	public static final int FORMAT_HH_MM_SS_MMM_DD_YYYY 		= 7;
	public static final int FORMAT_MMM_DD_YYYY 			= 8;
	public static final int FORMAT_MMM_DD_YYYY_HH_MM_AMPM 		= 9;
	public static final int FORMAT_HH_MM_SS 			= 10;
	public static final int FORMAT_YYYY_MM_DD_HH_MM_SLASH 		= 11;
	public static final int FORMAT_DD_MMM_YYYY			= 12;
	public static final int FORMAT_DDMMYYYY				= 13;
	public static final int FORMAT_DD_MM_YYYY			= 14;
	public static final int FORMAT_YYYYMMDD				= 15;

	
	public static final int REGISTRATION_TIMEOUT = 10 * 1000;
	public static final int WAIT_TIMEOUT = 30 * 1000;
	    
	
	public static final String TXT_ERROR 	= "Error";
	public static final String TXT_CONEXION = "conexion";

	public static final String TXT_ERROR_LOGIN	= "Ingrese usuario/contrasena correcto.";
	public static final String TXT_ERROR_EMPTY	= "Ingrese numero de pedido.";
	public static final String TXT_CARGA_COMPLETA	= "Descarga completada.";
	public static final String MSG_ERROR_CONEXION	= TXT_ERROR+" de "+TXT_CONEXION;
	public static final String TXT_ERROR_UPDATE	= "Accion deshabilitada.";
	public static final String TXT_ACCION_NO_VALIDA	= "Accion no valida.";
	public static final String TXT_CREAR_PEDIDO	= "Crear pedido.";
	public static final String TXT_ACTUALIZAR_PEDIDO= "Actualizar pedido.";
	
	public static final String TXT_CARGANDO		= "Cargando";

	public static final String TXT_PAISES 		= "Paises";
	public static final String TXT_DOTS		= " ..."; 


	
	public static final String ROOT_JSON		="d";
	public static final String SUBROOT_REGIONS_JSON = "Regions";
	public static final String RESULTS_JSON		="results";
	public static final String SOITEMS_JSON		="SOItems";


	public static final String EMPTY_STR = "";
	public static final String URL_KEY = "url";
	
	public static final String ZSUP = "ZSUP";
	public static final String ZSER = "ZSER";
	
	public static final String DIRECCION = "direccion";



	public static final String RFC = "XEXX010101000";

	public static final int	EMPTY = 0;


	public static final int STATUS_CODE_201 = 201;
	public static final int STATUS_CODE_204 = 204;



	public String[] formatsDateTime = new String[] { "yyyy-MM-dd", "HH:mm", "yyyy-MM-dd HH:mm",
							 "yyyy-MM-dd'T'HH:mm:ss.SSS", "yyyy-MM-dd'T'HH:mm:ss", 
							 "HH:mm MMM dd", "MMM dd, yyyy. HH:mm", "HH:mm:ss MMM dd, yyyy", 
							 "MMM dd, yyyy", "MMM dd,yyyy - HH:mm a", "HH:mm:ss", "yyyy/MM/dd HH:mm", 
							 "dd MMM ,yyyy", "ddMMyyyy","dd-MM-yyyy","yyyyMMdd" };

	
}
