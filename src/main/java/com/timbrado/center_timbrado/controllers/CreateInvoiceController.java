package com.timbrado.center_timbrado.controllers;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import com.Facturama.sdk_java.Container.FacturamaApi;
import com.Facturama.sdk_java.Models.Client;
import com.Facturama.sdk_java.Models.Product;
import com.Facturama.sdk_java.Models.Exception.FacturamaException;
import com.Facturama.sdk_java.Models.Request.Payment;
import com.Facturama.sdk_java.Models.Response.Catalogs.Catalog;
import com.Facturama.sdk_java.Models.Response.Catalogs.Cfdi.Currency;
import com.Facturama.sdk_java.Models.Response.Catalogs.Cfdi.PostalCode;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;

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
	
	FacturamaApi facturama = new FacturamaApi("ricardomangore", "1nt3rm3zz0", true );

	public void initialize(URL location, ResourceBundle resources) {
		
		try {
			initializeTableView();
			initializatePaymentForms();
			initializatePaymentMethods();
			initializateCurrencies();
			initializateProductListener();
		} catch (Exception e) {
			e.printStackTrace();
		}
	
		
	}
	
	public void addProductToTable() throws IOException, FacturamaException, Exception {
		
		
		
	}
	
	public void initializateProductListener() {
		cbxShowProducts.setOnAction(e -> {
	        if(cbxShowProducts.getSelectionModel().getSelectedIndex()!=-1) {
	        	int indiceP = cbxShowProducts.getSelectionModel().getSelectedIndex();
	        	try {
					Product product = facturama.Products().List().get(indiceP);
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
		colKeys.setCellValueFactory(new PropertyValueFactory<Product, String>("keys"));
		colDescription.setCellValueFactory(new PropertyValueFactory<Product, String>("description"));
		colPrice.setCellValueFactory(new PropertyValueFactory<Product, Float>("price"));
		colSubtotal.setCellValueFactory(new PropertyValueFactory<Product, Float>("subtotal"));
		colDiscount.setCellValueFactory(new PropertyValueFactory<Product, Float>("discount"));
		colTaxes.setCellValueFactory(new PropertyValueFactory<Product, Float>("tax"));
		colTotal.setCellValueFactory(new PropertyValueFactory<Product, Float>("total"));
	}
	

	public void showClients() throws IOException, FacturamaException, Exception {
		cbxShowClients.getItems().clear();
		getClients();
	}
	
	public void getClients() throws IOException, FacturamaException, Exception {
		for(Client client : facturama.Clients().List() ) {
			cbxShowClients.getItems().add(client.getName());
		}
	}
	
	@FXML
	public void newClient() throws IOException {
		//The fxml and the controller are loaded for modify the contact
		FXMLLoader loader = new FXMLLoader( this.getClass().getResource("/fxml/addClient.fxml"));			
		Parent parent = loader.load();				
		EditClientController controller = loader.getController();
//		Cliente client
//		controller.setContact( contact );
//		controller.loadData();
		
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
			
			Client cliente = facturama.Clients().List().get(indice);
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
		addProductToTable();
	}
	
	public List<Product> getProducts() throws IOException, FacturamaException, Exception {
		List<Product> allProducts = facturama.Products().List();
		return allProducts;
	}
	
	
//Obtener Formas de Pago
	public List<Catalog> getPaymentForms() throws IOException, FacturamaException, Exception {
		List<Catalog> allPaymentsForms = facturama.Catalogs().PaymentForms();
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
		List<Catalog> allPaymentsMethods = facturama.Catalogs().PaymentMethods();
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
		List<Currency> allCurrencies = facturama.Catalogs().Currencies();
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
