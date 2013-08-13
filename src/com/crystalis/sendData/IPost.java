package com.crystalis.sendData;


public class IPost {
  
    public String HeaderXML		= " <?xml version='1.0' encoding='UTF-8'?> ";

   public String startBody =
	   HeaderXML+
	   "<atom:entry xmlns:atom='http://www.w3.org/2005/Atom' xmlns:d='http://schemas.microsoft.com/ado/2007/08/dataservices' xmlns:m='http://schemas.microsoft.com/ado/2007/08/dataservices/metadata'> ";
   
   public String halfBody =  
	   "<atom:link rel='http://schemas.microsoft.com/ado/2007/08/dataservices/related/SOItems' type='application/atom+xml;type=feed' title='SALESORDER.SOHeader_SOItems'> "+
   	   "<m:inline> "+
	      "<atom:feed>";
  
   public String endBody = 
	   "</atom:feed>"+
          "</m:inline>"+
  	 "</atom:link>"+
        "</atom:entry>";
   
   public String startEntryCustomer 	= " <entry xml:base='http://crystalismobile.zapto.org/sap/opu/odata/sap/CUSTOMER/' xmlns='http://www.w3.org/2005/Atom' xmlns:m='http://schemas.microsoft.com/ado/2007/08/dataservices/metadata' xmlns:d='http://schemas.microsoft.com/ado/2007/08/dataservices'>";
   public String postidCustumer 	= " <id>http://crystalismobile.zapto.org/sap/opu/odata/sap/CUSTOMER/Customers</id>";
   public String posttitleCustumer 	= " <title type='text'>Customers</title> ";
   public String categoryCustumer	= " <category term='CUSTOMER.Customer' scheme='http://schemas.microsoft.com/ado/2007/08/dataservices/scheme'/> ";
   public String endEntryCustumer	= " </entry> ";
       
   public String putIdCustomer		= " <id>http://SAP09.crystalisconsulting.com:8007/sap/opu/odata/sap/CUSTOMER/Customers('|')</id>";
   public String putTitleCustomer	= " <title type='text'>Customers('|')</title>";
   public String putUpdatedCustomer	= " <updated>$</updated> ";// <updated>2013-07-17T23:34:14Z</updated>
   public String putLinkCustomer	= " <link href=\"Customers('|')\" rel='edit' title='Customer'/>";
   
   
   
   public String postDateid             = " <id>http://crystalismobile.zapto.org/sap/opu/odata/sap/DATING/Datings</id> " ;
   public String PostStartEntryDates    = "<entry xml:base='http://crystalismobile.zapto.org/sap/opu/odata/sap/DATING/' xmlns='http://www.w3.org/2005/Atom' xmlns:m='http://schemas.microsoft.com/ado/2007/08/dataservices/metadata' xmlns:d='http://schemas.microsoft.com/ado/2007/08/dataservices'>";
   
   
   public String putStartEntryDates    = "<entry xml:base='http://crystalismobile.zapto.org/sap/opu/odata/sap/DATING/' xmlns='http://www.w3.org/2005/Atom' xmlns:m='http://schemas.microsoft.com/ado/2007/08/dataservices/metadata' xmlns:d='http://schemas.microsoft.com/ado/2007/08/dataservices'>";
   public String categoryDates		= " <category term='DATING.Dating' scheme='http://schemas.microsoft.com/ado/2007/08/dataservices/scheme'/> ";
   public String putIdDates		= " <id>http://crystalismobile.zapto.org/sap/opu/odata/sap/DATING/Datings</id>";
   public String putTitleDates		= " <title type='text'>Datings('|')</title>";
   public String putUpdateDates		= " <updated>$</updated> ";
   public String putLinkDates		= "<link href=\"Datings('|')\" rel='edit' title='Dating'/>";  


   public String setHeader (String documentType, String customerId, String fecha){
       
       String header=        
	       "<atom:content type='application/xml'>"+
	        "<m:properties>"+
	          "<d:OrderId>0</d:OrderId>"+
	          "<d:DocumentType>"+documentType+"</d:DocumentType>"+
	          "<d:CustomerId>"+customerId+"</d:CustomerId>"+
	          "<d:SalesOrg>PIR1</d:SalesOrg>"+
	          "<d:DistChannel>CA</d:DistChannel>"+
	          "<d:Division>00</d:Division>"+
	          "<d:DocumentDate>"+fecha+"T00:00:00</d:DocumentDate>"+
	          "<d:OrderValue m:null='true' />"+
	          "<d:Currency>MXN</d:Currency>"+
	        "</m:properties>"+
	       "</atom:content>";
       
       return header;
	    
   }
   
   public String setItem (String iMaterial, String iQuantity){
       
       String item = 

               "<atom:entry>"+
               "<atom:content type='application/xml'>"+
                "<m:properties>"+
                  "<d:OrderId>0</d:OrderId>"+
                  "<d:Item>0</d:Item>" +
                  "<d:Material>"+iMaterial+"</d:Material>"+
                  "<d:Plant>PIR1</d:Plant>"+
                  "<d:Quantity m:Type='Edm.Decimal'>"+iQuantity+"</d:Quantity>"+
                  "<d:Description m:null='true' />"+
                  "<d:UoM m:null='true' />"+
                  "<d:Value m:null='true' />"+
                 "</m:properties>"+
                "</atom:content>"+
               "</atom:entry>";
                
       return item;
	    
   }
   
   
   public String setCustomerPOST (String name, String street, String number, String city, String district, String state, String zip, String country, String rfc, String tel, String movil, String email){
       
       String customer=        
	       "<content type='application/xml'>"+
	        "<m:properties>"+
	          "<d:CustomerId>0</d:CustomerId>"+
	          "<d:Name>"+name+"</d:Name>"+
	          "<d:Street>"+street+"</d:Street>"+
	          "<d:Number>"+number+"</d:Number>"+ 
	          "<d:City>"+city+"</d:City>"+
	          "<d:State>"+state+"</d:State>"+
	          "<d:ZIP>"+zip+"</d:ZIP>"+
	          "<d:Country>"+country+"</d:Country>"+
	          "<d:RFC>"+rfc+"</d:RFC>"+
	          "<d:Tel>"+tel+"</d:Tel>"+
	          "<d:Movil>"+movil+"</d:Movil>"+
	          "<d:Email>"+email+"</d:Email>"+
	        "</m:properties>"+
	       "</content>";
       
       return customer;      
	    
   }
   
   public String setCustomerPUT (String customerId, String name, String street, String number, String city, String district,String state, String zip, String country, String rfc, String tel, String movil, String email){
       
       String customer=        
	       "<content type='application/xml'>"+
	        "<m:properties>"+
	          "<d:CustomerId>"+customerId+"</d:CustomerId>"+
	          "<d:Name>"+name+"</d:Name>"+
	          "<d:Street>"+street+"</d:Street>"+
	          "<d:Number>"+number+"</d:Number>"+ 
	          "<d:City>"+city+"</d:City>"+
	          "<d:District>"+district+"</d:District>"+
	          "<d:State>"+state+"</d:State>"+
	          "<d:ZIP>"+zip+"</d:ZIP>"+
	          "<d:Country>"+country+"</d:Country>"+
	          "<d:RFC>"+rfc+"</d:RFC>"+
	          "<d:Tel>"+tel+"</d:Tel>"+
	          "<d:Movil>"+movil+"</d:Movil>"+
	          "<d:Email>"+email+"</d:Email>"+
	        "</m:properties>"+
	       "</content>";
       
       return customer;      
	    
   }
   
   
public String setDatePost (String customerId, String horainicio, String horafin, 
		String fecha, String asunto, String lugar, String estatus){
       
       String date=        
    		   "<content type='application/xml'>"+
    		   "<m:properties>"+
    		   "<d:Customer>"+customerId+"</d:Customer>"+
    		   "<d:InitialHour>"+horainicio+"</d:InitialHour>"+
    		   "<d:EndHour>"+horafin+"</d:EndHour>"+
    		   "<d:Date>"+fecha+"</d:Date>"+
    		   "<d:Subject>"+asunto+"</d:Subject>"+
    		   "<d:Place>"+lugar+"</d:Place>"+
    		   "<d:Status>"+estatus+"</d:Status>"+
    		   "</m:properties>"+
    		   "</content>";
       
       return date;      
	    
   }

public String setDatePut (String customerId, String horainicio, String horafin, String date, String initialhour_up, String endhour_up, String asunto, 
			  String lugar, String estatus){
       
       String d=        
    		   "<content type='application/xml'>"+
    		   "<m:properties>"+
    		   "<d:Customer>"+customerId+"</d:Customer>"+
    		   "<d:InitialHour>"+horainicio+"</d:InitialHour>"+
    		   "<d:EndHour>"+horafin+"</d:EndHour>"+
    		   "<d:Date>"+date+"</d:Date>"+
    		   "<d:InitialHour_Up>"+initialhour_up+"</d:InitialHour_Up>"+
    		   "<d:EndHour_Up>"+endhour_up+"</d:EndHour_Up>"+
    		   "<d:Subject>"+asunto+"</d:Subject>"+
    		   "<d:Place>"+lugar+"</d:Place>"+
    		   "<d:Status>"+estatus+"</d:Status>"+
    		   "</m:properties>"+
    		   "</content>";
       
       return d;      
	    
   }
   
}












