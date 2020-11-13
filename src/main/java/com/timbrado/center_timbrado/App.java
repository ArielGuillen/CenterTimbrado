package com.timbrado.center_timbrado;


import java.io.IOException;

import com.Facturama.sdk_java.Container.FacturamaApi;
import com.Facturama.sdk_java.Models.Address;
import com.Facturama.sdk_java.Models.Client;
import com.Facturama.sdk_java.Models.Exception.FacturamaException;


public class App {
	static FacturamaApi facturama;
    public static void main( String[] args ) throws IOException, FacturamaException, Exception{
    	
        System.out.println( "Timbrado \n" );
        facturama = new FacturamaApi("ricardomangore", "1nt3rm3zz0", true );

    	System.out.println( "Clientes\n") ;
        
        for( Client client : facturama.Clients().List() ) {
        	System.out.println( "\nNombre: "+client.getName() );
        	System.out.println( "Email: "+client.getEmail() );
        }
        
    }
    public static void crearCliente() throws IOException, FacturamaException, Exception {
    	
    	System.out.println( "Creando Cliente") ;
    	Client cliente = new Client();    
        
        Address clientAddress = new Address();
        clientAddress.setCountry("México");
        clientAddress.setExteriorNumber("1230");
        clientAddress.setInteriorNumber("B");
        clientAddress.setLocality("Puebla");                  
        clientAddress.setMunicipality("Puebla");         
        clientAddress.setNeighborhood("Lomas 4ta");
        clientAddress.setState("Puebla");
        clientAddress.setStreet("Art. 31");
        clientAddress.setZipCode("72499");
             
        cliente.setAddress(clientAddress);         
        cliente.setCfdiUse("P01");
        cliente.setRfc("ESO1202108R2");
        cliente.setEmail("ariel@gmail.com");
        cliente.setName("Ariel Guillen");
             
       cliente = facturama.Clients().Create(cliente);
   		System.out.println( "Cliente Creado") ;
    }
    
  
}

