package com.crystalis.dataconnection;

public interface DataConnection {
    public String Url				= "http://$$/sap/opu/odata/sap/";
    public String SALESORDER_SRC		= "SALESORDER/";
    public String SOHEADERS_SRC  		= "SOHeaders";
    public String SOITEMS_SRC  			= "SOItems";
    public String SOMATERIALS_SRC		= "MATERIAL/Material";
    public String LOGIN_SRC			= "CHECK_USER/Users(UserId='usr',Pass='pwd')";
    public String FORMAT_JSON			="$format=json";
    public String CUSTOMER_SRC			= "CUSTOMER/Customers";
    public String DATES_SCR			= "DATING/Datings";
    public String COUNTRY_SRC			= "COUNTRY/Countrys";
    public String DATES				= Url+"DATING/Datings?$filter=Customer%20eq%20'|'&";

    
    public String IMAGE_URL			= "http://|.zapto.org";
    public String SALESORDER_GETWAY_JSON	= Url+SALESORDER_SRC+SOHEADERS_SRC+"?"+FORMAT_JSON;
    public String SALESORDERITEMS_GETWAY_JSON	= Url+SALESORDER_SRC+SOITEMS_SRC;
    public String MATERIALS_GETWAY		= Url+SOMATERIALS_SRC+"?"+FORMAT_JSON;
    public String LOGIN_GATEWAY_JSON		= Url+LOGIN_SRC+"?"+FORMAT_JSON;
    
    public String CUSTOMERS_GATEWAY_JSON	= Url+CUSTOMER_SRC+"?"+FORMAT_JSON;

    public String COUNTRY_GATEWAY_JSON 		= Url+COUNTRY_SRC+"?"+FORMAT_JSON;
    public String REGION_GATEWAY_JSON 		= Url+COUNTRY_SRC+"('|')?$expand=Regions&"+FORMAT_JSON;

    
    public String SALESORDER_GETWAY_XML_POST	= Url+SALESORDER_SRC+SOHEADERS_SRC;
    public String CUSTOMER_GETWAY_XML_POST	= Url+CUSTOMER_SRC;
    public String DATES_GATEWAY_XML_POST    	= Url+DATES_SCR;
    public String DATES_GATEWAY_XML_PUT		= Url+DATES_SCR+"('||')";

    public String CUSTOMER_GETWAY_XML_PUT	= Url+CUSTOMER_SRC+"('||')";

    
    public String SALESORDER_GETWAY_DELETE	= Url+SALESORDER_SRC+SOHEADERS_SRC+"('|')";

    public String URL_ITEMS		= SALESORDERITEMS_GETWAY_JSON;//"http://sap09.crystalisconsulting.com:8007/sap/opu/odata/sap/SALESORDER/SOItems";
    public String EXPAND_HEADER		= "('|')?$expand=SOItems&"+FORMAT_JSON;
    
//    Filtros
    public String FILTROxCITAS		=DATES+FORMAT_JSON;
    public String FILTRO_ORDERID	= Url+SALESORDER_SRC+SOHEADERS_SRC+EXPAND_HEADER;
    public String FILTRO_MATERIALID 	= URL_ITEMS+"?$filter=Material%20eq%20'|'&"+FORMAT_JSON;//http://sap09.crystalisconsulting.com:8007/sap/opu/odata/sap/SALESORDER/SOItems?$filter=Material eq 'DE-1307C'
    public String FILTRO_HEADER		= Url+SALESORDER_SRC+SOHEADERS_SRC + EXPAND_HEADER; //http://sap09.crystalisconsulting.com:8007/sap/opu/odata/sap/SALESORDER/SOHeaders('101000047')
    public String FILTRO_HEADER_CUSTOMERID	  = Url+SALESORDER_SRC+SOHEADERS_SRC +"?$filter=CustomerId%20eq%20'|'&"+FORMAT_JSON;
    public String FILTRO_CUSTUMERS_CUSTOMERID_JSON= Url+CUSTOMER_SRC+"('|')?"+FORMAT_JSON;

//    public String PAISES		= Url+COUNTRY_GATEWAY_JSON;//""http://sap09.crystalisconsulting.com:8007/sap/opu/odata/sap/COUNTRY/Countrys?$format=json
//    public String REGION		= Url+COUNTRY_SRC+"('|')?$expand=Regions&"+FORMAT_JSON;//http://sap09.crystalisconsulting.com:8007/sap/opu/odata/sap/COUNTRY/Countrys('MX')?$expand=Regions&$format=json 
    
    	
    public String user ="demo01";
    public String pwd = "initial"; 
    
}
