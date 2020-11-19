package com.timbrado.center_timbrado.controllers;


import java.io.IOException;

import com.Facturama.sdk_java.Container.FacturamaApi;
import com.Facturama.sdk_java.Models.Address;
import com.Facturama.sdk_java.Models.Client;
import com.Facturama.sdk_java.Models.Exception.FacturamaException;
import com.timbrado.center_timbrado.exceptions.EmptyFieldException;
import com.timbrado.center_timbrado.exceptions.NotFormatRFCException;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class EditClientController{
	
	private FacturamaApi facturama;
	
	//---General Data---
	@FXML
	public TextField txtName;	
	@FXML
	public TextField txtRFC;
	@FXML
	public TextField txtEmail;

	//---Address Data---
	@FXML
	public TextField txtCountry;
	@FXML
	public TextField txtState;
	@FXML
	public TextField txtMunicipality;
	@FXML
	public TextField txtLocality;
	@FXML
	public TextField txtZipCode;
	@FXML
	public TextField txtStreet;
	@FXML
	public TextField txtExteriorNumber;
	@FXML
	public TextField txtInteriorNumber;
	@FXML
	public TextField txtNeighborhood;	

	@FXML
	public Label warning;
	@FXML 
	public Button btnConfirmar;
	
	//Object client for update data
	public Client client;
	public Address clientAddress;
		
	//--------Setter & getter methods for contact to modify-----//
		
	public void setContact(Client client) {
		this.client = client;
	}

	public Client getClient() {
		return this.client;
	}
	
	//---------Show contact information-------//
	
	protected void loadData() {
		
		//General Data
		txtName.setText( this.client.getName() );
		txtRFC.setText( this.client.getRfc() );
		txtEmail.setText( String.valueOf( this.client.getEmail() ) );
		
		//Address Information
        clientAddress = this.client.getAddress();
        
        if( client.getAddress().getCountry() != null )
        	txtCountry.setText( clientAddress.getCountry() );
        
        if( client.getAddress().getState() != null )
        	txtState.setText( clientAddress.getState() );
        
        if( client.getAddress().getMunicipality() != null )
        	txtMunicipality.setText( clientAddress.getMunicipality() );
        
		if( client.getAddress().getLocality() != null )
			txtLocality.setText( clientAddress.getLocality() );
		
		if( client.getAddress().getZipCode() != null )
			txtZipCode.setText( clientAddress.getZipCode() );
		
		if( client.getAddress().getStreet() != null )
			txtStreet.setText( clientAddress.getStreet() );
		
		if( client.getAddress().getExteriorNumber() != null )
			txtExteriorNumber.setText( clientAddress.getExteriorNumber() );
		
		if( client.getAddress().getInteriorNumber() != null )
			txtInteriorNumber.setText( clientAddress.getInteriorNumber() );
		
		if( client.getAddress().getNeighborhood() != null )
			txtNeighborhood.setText( clientAddress.getNeighborhood() );
		
	}
	
	//----------Cancel View-------------//
	
	@FXML
	public void cancelViewClient() {
		
		this.client = null;	//null assignment for validation of changes in contact
		closeWindow();
		
	}
	
	public void closeWindow() {

		Stage stage = (Stage) this.btnConfirmar.getScene().getWindow();
		stage.close();
		
	}
	//-------Save client information----//
	@FXML		
	public void saveData() throws IOException, FacturamaException, Exception {
		
        facturama = new FacturamaApi("ricardomangore", "1nt3rm3zz0", true );

		if( client == null ) {
			
			client = new Client();
			saveDataClient();
					
			client = facturama.Clients().Create(client);
			System.out.println( client.getName() );
			System.out.println( client.getRfc() );
		}
		else {
			saveDataClient();
			facturama.Clients().Update(client, client.getId() );
		}
		if( client.getAddress().getCountry() != null )
		System.out.println( "País: " + client.getAddress().getCountry() );
		closeWindow();	
	}
		
	
	private void saveDataClient() {
		try {
			checkIfFieldsIsNotEmpty();	
			checkIfRFCFormatisValid();
			
			this.client.setName(txtName.getText().trim() );
			this.client.setRfc(txtRFC.getText().trim().toUpperCase() );

			this.client.setEmail("ariel@gmail.com");
			this.client.setCfdiUse("P01");
			
			//add optional data
			Address clientAddress = new Address();
			if( !this.txtCountry.getText().trim().isEmpty() )
				this.clientAddress.setCountry( this.txtCountry.getText().trim() );
			
			if( !this.txtState.getText().trim().isEmpty() )
				this.clientAddress.setState( this.txtState.getText().trim() );
			
			if( !this.txtMunicipality.getText().trim().isEmpty() )
				this.clientAddress.setMunicipality( this.txtMunicipality.getText().trim() );
			
			if( !this.txtLocality.getText().trim().isEmpty() )
				this.clientAddress.setLocality( this.txtLocality.getText().trim() );
			
			if( !this.txtZipCode.getText().trim().isEmpty() )
				this.clientAddress.setZipCode( this.txtZipCode.getText().trim() );
			
			if( !this.txtStreet.getText().trim().isEmpty() )
				this.clientAddress.setStreet( this.txtStreet.getText().trim() );
			
			if( !this.txtExteriorNumber.getText().trim().isEmpty() )
				this.clientAddress.setExteriorNumber( this.txtExteriorNumber.getText().trim() );
			
			if( !this.txtInteriorNumber.getText().trim().isEmpty() )
				this.clientAddress.setInteriorNumber( this.txtInteriorNumber.getText().trim() );
			
			if( !this.txtNeighborhood.getText().trim().isEmpty() )
				this.clientAddress.setNeighborhood( this.txtNeighborhood.getText().trim() );
								             
	        client.setAddress(clientAddress);    
		}catch(EmptyFieldException e ) {
			
			warning.setText( e.getMessage() );
			
		} catch( NotFormatRFCException e) {				
			warning.setText( e.getMessage() );
			
		} 
	}
	

	//--------Exception Methods------------//
	
	public void checkIfFieldsIsNotEmpty()throws EmptyFieldException{
		
		if( this.txtName.getText().trim().isEmpty() )
			throw new EmptyFieldException( "El campo Nombre no puede estar vacío" );
		if( this.txtRFC.getText().trim().isEmpty() )
			throw new EmptyFieldException( "El campo RFC no puede estar vacío" );
		if( this.txtEmail.getText().trim().isEmpty() )
			throw new EmptyFieldException( "El campo Correo Electrónico no puede estar vacío" );
		
		/*if( this.txtCountry.getText().trim().isEmpty() )
			throw new EmptyFieldException( "El campo País no puede estar vacío" );
		if( this.txtState.getText().trim().isEmpty() )
			throw new EmptyFieldException( "El campo Estado no puede estar vacío" );
		if( this.txtMunicipality.getText().trim().isEmpty() )
			throw new EmptyFieldException( "El campo Municipio no puede estar vacío" );
		if( this.txtLocality.getText().trim().isEmpty() )
			throw new EmptyFieldException( "El campo Localidad no puede estar vacío" );
		if( this.txtZipCode.getText().trim().isEmpty() )
			throw new EmptyFieldException( "El campo Código Postal no puede estar vacío" );
		if( this.txtStreet.getText().trim().isEmpty() )
			throw new EmptyFieldException( "El campo Calle no puede estar vacío" );
		if( this.txtExteriorNumber.getText().trim().isEmpty() )
			throw new EmptyFieldException( "El campo Número Exterior no puede estar vacío" );
		if( this.txtInteriorNumber.getText().trim().isEmpty() )
			throw new EmptyFieldException( "El campo Número Interior no puede estar vacío" );
		if( this.txtNeighborhood.getText().trim().isEmpty() )
			throw new EmptyFieldException( "El campo Colonia no puede estar vacío" );*/
		
	}
	private void checkIfRFCFormatisValid() throws NotFormatRFCException {
		
		String rfc = this.txtRFC.getText().trim();
		if ( rfc.length() < 12 ) 
			throw new NotFormatRFCException( "El campo RFC debe tener al menos 12 caracteres");
			
	}
	

}