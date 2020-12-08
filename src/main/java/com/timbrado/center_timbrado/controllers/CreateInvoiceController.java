package com.timbrado.center_timbrado.controllers;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.List;
import java.util.ResourceBundle;

import com.Facturama.sdk_java.Models.Client;
import com.Facturama.sdk_java.Models.Product;
import com.Facturama.sdk_java.Models.Exception.FacturamaException;
import com.Facturama.sdk_java.Models.Response.Catalogs.Catalog;
import com.Facturama.sdk_java.Models.Response.Catalogs.Cfdi.Currency;
import com.timbrado.center_timbrado.services.Facturama;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DateCell;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Callback;

public class CreateInvoiceController implements Initializable{
	
	@FXML
	public ComboBox<String> cbxShowClients;
	
	@FXML
	public ComboBox<String> cbxShowProducts;
	
	@FXML
	public ComboBox<String> cbxShowPaymentsForms;
	
	@FXML
	public ComboBox<String> cbxShowPaymentsMethods;

	@FXML
	public ComboBox<String> cbxShowCurrencies;
	
	@FXML
	public DatePicker date;
	
	
	@FXML
	public TableView<Product> tabProducts;
	@FXML
	TableColumn<Product, Integer> colQuantity;
	@FXML
	TableColumn<Product, String> colKeys;
	@FXML
	TableColumn<Product, String> colDescription;
	@FXML
	TableColumn<Product, Float> colPrice;
	@FXML
	TableColumn<Product, Float> colSubtotal;
	@FXML
	TableColumn<Product, Float> colDiscount;
	@FXML
	TableColumn<Product, Float> colTaxes;
	@FXML
	TableColumn<Product, Float> colTotal;
	
//	FacturamaApi facturama = new FacturamaApi("ricardomangore", "1nt3rm3zz0", true );

	public void initialize(URL location, ResourceBundle resources) {
		
		try {
			initializateDate();
			initializeTableView();
			initializatePaymentForms();
			initializatePaymentMethods();
			initializateCurrencies();
			initializateProductListener();
		} catch (Exception e) {
			e.printStackTrace();
		}
	
		
	}
	
	public void initializateDate() {
		
		 Callback<DatePicker, DateCell> dayCellFactory = dp -> new DateCell() 
	     {
	      @Override 
	      public void updateItem(LocalDate item, boolean empty) 
	      { 
	       super.updateItem(item, empty); 

	       if(item.isBefore(LocalDate.now().minusDays(2)) || item.isAfter(LocalDate.now())) 
	       { 
	        setStyle("-fx-background-color: gray;"); 
	        Platform.runLater(() -> setDisable(true));
	       } 
	      } 
	     };
	     
	     date.setDayCellFactory(dayCellFactory); 
	}

	public void initializateProductListener() {
		cbxShowProducts.setOnAction(e -> {
	        if(cbxShowProducts.getSelectionModel().getSelectedIndex()!=-1) {
	        	int indiceP = cbxShowProducts.getSelectionModel().getSelectedIndex();
	        	try {
					Product product = Facturama.facturama.Products().List().get(indiceP);
					tabProducts.getItems().add(product);
					System.out.println(product.getUnitCode());
				} catch (Exception e1) {
					e1.printStackTrace();
				}
	        }
	    });
	}
	
	public void initializeTableView() {
		colQuantity.setCellValueFactory(new PropertyValueFactory<Product, Integer>("quantity"));
		colKeys.setCellValueFactory(new PropertyValueFactory<Product, String>("CodeProdServ"));
		colDescription.setCellValueFactory(new PropertyValueFactory<Product, String>("Description"));
		colPrice.setCellValueFactory(new PropertyValueFactory<Product, Float>("Price"));
		colSubtotal.setCellValueFactory(new PropertyValueFactory<Product, Float>("subtotal"));
		colDiscount.setCellValueFactory(new PropertyValueFactory<Product, Float>("discount"));
		colTaxes.setCellValueFactory(new PropertyValueFactory<Product, Float>("Taxes"));
		colTotal.setCellValueFactory(new PropertyValueFactory<Product, Float>("total"));
	}
	

	public void updateComboboxClients() throws IOException, FacturamaException, Exception {
		cbxShowClients.getItems().clear();
		addClientsToCombobox();
	}
	
//Añadimos todos los clientes al combobox de selección de clientes
	public void addClientsToCombobox() throws IOException, FacturamaException, Exception {
		List<Client> clientList = getClients();
		for(Client client : clientList ) {
			cbxShowClients.getItems().add(client.getName());
		}
	}
	
//	Obtener todos los clientes
	public List<Client> getClients() throws IOException, FacturamaException, Exception{
		List<Client> allClients = Facturama.facturama.Clients().List();
		return allClients;
	}
	
	@FXML
	public void newClient() throws IOException {
		FXMLLoader loader = new FXMLLoader( this.getClass().getResource("/fxml/addClient.fxml"));			
		Parent parent = loader.load();		
		
		Scene scene = new Scene( parent );
		Stage stage = new Stage();
		stage.initModality( Modality.APPLICATION_MODAL );
		stage.setTitle("Nuevo Cliente");
		stage.setScene( scene );
		stage.showAndWait();
	}
	
	@FXML
	public void editClient() throws IOException, FacturamaException, Exception {
		int indice = cbxShowClients.getSelectionModel().getSelectedIndex();
		if(indice!=-1) {
			FXMLLoader loader = new FXMLLoader( this.getClass().getResource("/fxml/addClient.fxml"));			
			Parent parent = loader.load();				
			EditClientController controller = loader.getController();
			
			Client cliente = Facturama.facturama.Clients().List().get(indice);
			controller.setContact( cliente );
			controller.loadData();
			
			Scene scene = new Scene( parent );
			Stage stage = new Stage();
			stage.initModality( Modality.APPLICATION_MODAL );
			stage.setTitle("Nuevo Cliente");
			stage.setScene( scene );
			stage.showAndWait();			
		}
		System.out.println(indice);
	}
	
	public void showProducts() throws IOException, FacturamaException, Exception {
		cbxShowProducts.getItems().clear();
		List<Product> productList = getProducts();
		for(Product product : productList) {
			cbxShowProducts.getItems().add(product.getName());
		}
	}
	
//Obtener todos los Productos
	public List<Product> getProducts() throws IOException, FacturamaException, Exception {
		List<Product> allProducts = Facturama.facturama.Products().List();
		return allProducts;
	}
	
	
//Obtener Formas de Pago
	public List<Catalog> getPaymentForms() throws IOException, FacturamaException, Exception {
		List<Catalog> allPaymentsForms = Facturama.facturama.Catalogs().PaymentForms();
		return allPaymentsForms;
	}
	
	
//Inicializar Formas de Pago	
	public void initializatePaymentForms() throws IOException, FacturamaException, Exception {
		List<Catalog> allPaymentsForms = getPaymentForms();
		for(Catalog c : allPaymentsForms) {
			cbxShowPaymentsForms.getItems().add(c.getName());		
		}
	}

//	Obtener Métodos de Pago	
	public List<Catalog> getPaymentMethods() throws IOException, FacturamaException, Exception {
		List<Catalog> allPaymentsMethods = Facturama.facturama.Catalogs().PaymentMethods();
		return allPaymentsMethods;
	}

//	Inicializar Métodos de Pago
	public void initializatePaymentMethods() throws IOException, FacturamaException, Exception {
		List<Catalog> allPaymentsMethods = getPaymentMethods();
		for(Catalog c : allPaymentsMethods) {
			cbxShowPaymentsMethods.getItems().add(c.getName());		
		}
	}

//	Obtener Monedas
	public List<Currency> getCurrencies() throws IOException, FacturamaException, Exception {
		List<Currency> allCurrencies = Facturama.facturama.Catalogs().Currencies();
		return allCurrencies;
	}
	
//Inicializar Monedas
	public void initializateCurrencies() throws IOException, FacturamaException, Exception {
		List<Currency> allCurrencies = getCurrencies();
		for(Currency c : allCurrencies) {
			cbxShowCurrencies.getItems().add(c.getName());		
		}
	}

	

}
