package com.timbrado.center_timbrado.controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import com.Facturama.sdk_java.Models.Client;
import com.Facturama.sdk_java.Models.Exception.FacturamaException;
import com.timbrado.center_timbrado.services.Facturama;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Callback;

public class CRUDClientController implements Initializable {

	@FXML
	public TableView<Client> tabClients;
	
	@FXML
	TableColumn<Client, String> colRFC;

	@FXML
	TableColumn<Client, String> colName;
	
	@FXML
	TableColumn<Client, String> colMail;
	
	@FXML
	TableColumn<Client, String> colActions;
	
//	FacturamaApi facturama = new FacturamaApi("ricardomangore", "1nt3rm3zz0", true );

	
	public void initialize(URL location, ResourceBundle resources) {
		initializeTableView();
		try {
			addClients();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void initializeTableView() {
		colRFC.setCellValueFactory(new PropertyValueFactory<Client, String>("rfc"));
		colName.setCellValueFactory(new PropertyValueFactory<Client, String>("name"));
		colMail.setCellValueFactory(new PropertyValueFactory<Client, String>("email"));
		Callback<TableColumn<Client, String>, TableCell<Client, String>> 
		cellFactory = new Callback< TableColumn<Client, String>, TableCell<Client, String> >() {
	      @Override 
	      public TableCell call(final TableColumn<Client, String> param) {
	       final TableCell<Client, String> cell = new TableCell<Client, String>() { 

	        Button btnDelete = new Button("Eliminar");
	        Button btnEdit = new Button("Editar");
	        HBox pane = new HBox(btnDelete, btnEdit);
	        
	        @Override 
	        public void updateItem(String item, boolean empty) {
	        	btnDelete.setStyle("-fx-background-color: #8F2626;");
	        	btnEdit.setStyle("-fx-background-color: #396DAC;");
	        	pane.setStyle("-fx-alignment: center; -fx-spacing: 15px;");
	         super.updateItem(item, empty); 
	         if (empty) { 
	          setGraphic(null); 
	          setText(null); 
	         } else {
	          btnDelete.setOnAction(event -> { 
	        	  Client client = getTableView().getItems().get(getIndex());
	        	  try {
					deleteClient(client);
					
					tabClients.getItems().clear();
					addClients();
					
				} catch (Exception e) {
					e.printStackTrace();
					System.out.println("Excepción de borrar Cliente");
				}
	        	  System.out.println("Prueba Botón borrar Cliente: " + client.getName() ); 
	          }); 
	          
	          btnEdit.setOnAction(event -> { 
	        	  Client client = getTableView().getItems().get(getIndex());
	        	  try {
	        		  editClient(client);
	        		  
	        		  tabClients.getItems().clear();
	        		  addClients();
	        		  
	        	  } catch (Exception e) {
	        		  e.printStackTrace();
	        		  System.out.println("Excepción de editar Cliente");
	        	  }
	        	  System.out.println("Prueba Botón Editar Cliente: " + client.getName() ); 
	          }); 
	          
	          setGraphic(pane);
	          setText(null); 
	         } 
	        } 
	       };
	       return cell; 
	      } 
	     };
	     colActions.setCellFactory(cellFactory); 
	}
	
	public void addClients() throws IOException, FacturamaException, Exception {
		for(Client client: Facturama.facturama.Clients().List()) {
			tabClients.getItems().add(client);
			System.out.println(client.getEmail());
		}
	}
	
	@FXML
	public void newClient() throws FacturamaException, Exception {
		//The fxml and the controller are loaded for modify the contact
		FXMLLoader loader = new FXMLLoader( this.getClass().getResource("/fxml/addClient.fxml"));			
		Parent parent = loader.load();				
		EditClientController controller = loader.getController();
		
		Scene scene = new Scene( parent );
		Stage stage = new Stage();
		stage.initModality( Modality.APPLICATION_MODAL );
		stage.setTitle("Nuevo Cliente");
		stage.setScene( scene );
		stage.showAndWait();
		tabClients.getItems().clear();
		addClients();
	}
	
	public void deleteClient(Client client) throws IOException, FacturamaException, Exception {
		Facturama.facturama.Clients().Remove(client.getId());
	}
	
	public void editClient(Client client) throws IOException {
		FXMLLoader loader = new FXMLLoader( this.getClass().getResource("/fxml/addClient.fxml"));			
		Parent parent = loader.load();				
		EditClientController controller = loader.getController();
		controller.setClient( client );
		controller.loadData();
		
		Scene scene = new Scene( parent );
		Stage stage = new Stage();
		stage.initModality( Modality.APPLICATION_MODAL );
		stage.setTitle("Nuevo Cliente");
		stage.setScene( scene );
		stage.showAndWait();	
	}
	
}
