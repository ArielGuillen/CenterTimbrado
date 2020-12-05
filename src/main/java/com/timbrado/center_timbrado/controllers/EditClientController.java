package com.timbrado.center_timbrado.controllers;


import java.io.IOException;
import java.util.List;

import com.Facturama.sdk_java.Models.Address;
import com.Facturama.sdk_java.Models.Client;
import com.Facturama.sdk_java.Models.Exception.FacturamaException;
import com.Facturama.sdk_java.Models.Response.Catalogs.Cfdi.UseCfdi;
import com.timbrado.center_timbrado.exceptions.EmptyFieldException;
import com.timbrado.center_timbrado.exceptions.NotFormatRFCException;
import com.timbrado.center_timbrado.services.Facturama;

import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class EditClientController{
	
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

	//--Label--
	@FXML
	public Label warning;
	@FXML
	public Label lblTitle;
	
	//--Icon--
	@FXML
	public FontAwesomeIconView iconWarning;
	
	//--Button--
	@FXML 
	public Button btnConfirmar;
	
	@FXML
	public ComboBox<String> cbxUInvoice;
	
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
		
		this.lblTitle.setText("Modificar datos del cliente");
		//General Client Data
		
		txtName.setText( this.client.getName() );
		txtRFC.setText( this.client.getRfc() );
		txtEmail.setText( String.valueOf( this.client.getEmail() ) );
		initializateUsesListener( this.client.getRfc() );
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
	
	

	//--------Main Methods------------//
	
	@FXML 
	public void loadUsesCfdi(){
			if( !this.txtRFC.getText().trim().isEmpty() ) {

				this.iconWarning.setVisible( false );
				this.txtRFC.setPromptText("");	
				
				String rfc = this.txtRFC.getText().trim();
				initializateUsesListener( rfc );
			}
			else {
				this.iconWarning.setVisible( true );
				this.txtRFC.setPromptText( "Ingresa un rfc" );		
			}
	}

	public void initializateUsesListener( String rfc ) {
		try {
			List<UseCfdi> ListUseCfdi = Facturama.facturama.Catalogs().CfdiUses( rfc );
			for(UseCfdi c : ListUseCfdi) {
				cbxUInvoice.getItems().add(c.getName() );		
			}
		} catch (NotFormatRFCException e1) {
			e1.printStackTrace();
		}catch (Exception e1) {
			e1.printStackTrace();
		}
	}
	//---Cancel View---//
	
	@FXML
	public void cancelViewClient() {
		
		this.client = null;	//null assignment for validation of changes in contact
		closeWindow();
		
	}
	
	public void closeWindow() {

		Stage stage = (Stage) this.btnConfirmar.getScene().getWindow();
		stage.close();
		
	}
	
	//---Save client information---//
	@FXML		
	public void saveData() throws IOException, FacturamaException, Exception {
		
		if( client == null ) {
			
			client = new Client();
			saveDataClient();
					
			client = Facturama.facturama.Clients().Create(client);
			System.out.println( client.getName() );
			System.out.println( client.getRfc() );
		}
		else {
			saveDataClient();
			Facturama.facturama.Clients().Update(client, client.getId() );
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

			this.client.setEmail(txtEmail.getText().trim());
			this.client.setCfdiUse("P01");
			int index = this.cbxUInvoice.getSelectionModel().getSelectedIndex();
			this.client.setCfdiUse( Facturama.facturama.Catalogs().CfdiUses("GUBA090497TT1").get( index ).getName() );
			
			//add optional data
			Address clientAddress = this.getAddress();								             
	        client.setAddress(clientAddress);    
	        
		}catch(EmptyFieldException e ) {			
			warning.setText( e.getMessage() );			
		} catch( NotFormatRFCException e) {				
			warning.setText( e.getMessage() );			
		} catch (IOException e) {
			e.printStackTrace();
		} catch (FacturamaException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		} 
	}
	

	private Address getAddress() {
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
		return clientAddress;
	}

	//--------Exception Methods------------//
	
	public void checkIfFieldsIsNotEmpty()throws EmptyFieldException{
		
		if( this.txtName.getText().trim().isEmpty() )
			throw new EmptyFieldException( "El campo Nombre no puede estar vacío" );
		if( this.txtRFC.getText().trim().isEmpty() )
			throw new EmptyFieldException( "El campo RFC no puede estar vacío" );
		if( this.txtEmail.getText().trim().isEmpty() )
			throw new EmptyFieldException( "El campo Correo Electrónico no puede estar vacío" );
		
	}
	
	private void checkIfRFCFormatisValid() throws IOException, FacturamaException, Exception {
		String rfc = this.txtRFC.getText().trim();
		if ( rfc.isEmpty() ) 
			throw new NotFormatRFCException( "Debe llenar el campo RFC");
		rfc = this.txtRFC.getText().trim();
		if ( rfc.length() < 12 ) 
			throw new NotFormatRFCException( "El campo RFC debe tener 12 caracteres");
		if ( rfc.charAt(0) > '1' &&  rfc.charAt(0) < '0') 
			throw new NotFormatRFCException( "RFC no válido");
		rfc.toUpperCase();
		List<Client> clients = Facturama.facturama.Clients().List();
		for( Client client : clients ) {
			if( rfc.equals( client.getRfc() ) )
				throw new NotFormatRFCException( "El RFC registrado está asociado a otro cliente");
		}
			
	}
	
	

}