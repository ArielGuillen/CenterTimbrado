package com.timbrado.center_timbrado.controllers;

import java.io.IOException;
import java.net.URL;
import java.text.NumberFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;

import com.Facturama.sdk_java.Models.Client;
import com.Facturama.sdk_java.Models.Product;
import com.Facturama.sdk_java.Models.Exception.FacturamaException;
import com.Facturama.sdk_java.Models.Request.ProductTax;
import com.Facturama.sdk_java.Models.Response.Catalogs.Catalog;
import com.Facturama.sdk_java.Models.Response.Catalogs.Cfdi.Currency;
import com.timbrado.center_timbrado.pojos.Producto;
import com.timbrado.center_timbrado.services.Facturama;

import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.DateCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Callback;
import javafx.util.StringConverter;

public class CreateInvoiceController implements Initializable{
	
	@FXML
	public ComboBox<Client> cbxShowClients;
	
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
	TableColumn<Product, String> colQuantity;
	@FXML
	TableColumn<Product, String> colName;
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
	TableColumn<Product, String> colTaxes;
	@FXML
	TableColumn<Product, Float> colTotal;
	
	ArrayList<Producto> productos = new ArrayList<Producto>();
	


	public void initialize(URL location, ResourceBundle resources) {
		
		try {
			initialiseComboboxClientsConverters();
			initializateDate();
			initializeTableView();
			initializatePaymentForms();
			initializatePaymentMethods();
			initializateCurrencies();
			initializateProductListener();
			
			//initializateDatePickerListener();
		} catch (Exception e) {
			e.printStackTrace();
		}
	
		
	}
	
	
	
	public void initializateDate() {
		
		 Callback<DatePicker, DateCell> dayCellFactory = dp -> new DateCell() {
			 
		      @Override 
		      public void updateItem(LocalDate item, boolean empty) {
		    	  
			       super.updateItem(item, empty); 
		
			       if(item.isBefore(LocalDate.now().minusDays(2)) || item.isAfter(LocalDate.now())) { 
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
					//Código para conoarar productos pendiente
					tabProducts.getItems().add(product);
				} catch (Exception e1) {
					e1.printStackTrace();
				}
	        }
	    });
	}
	
	public void initialiseComboboxClientsConverters(){
		cbxShowClients.setConverter(new StringConverter<Client>() {
			
			@Override
			public String toString(Client c) {
				return c.getName();
			}
			
			@Override
			public Client fromString(String string) {
				return null;
			}
		});
	}
	
	public void initializeTableView() {
		Callback<TableColumn<Product, String>, TableCell<Product, String>> 
		cellFactory = new Callback< TableColumn<Product, String>, TableCell<Product, String> >() {
		      @Override 
		      public TableCell call(final TableColumn<Product, String> param) {
		       final TableCell<Product, String> cell = new TableCell<Product, String>() { 

		        Label labelProduct = new Label();
		        Label labelTitleProduct = new Label("Código de producto");
		        Label labelUnit = new Label();
		        Label labelTitleUnit = new Label("Código de unidad");
		        VBox pane = new VBox( labelTitleProduct, labelProduct, labelTitleUnit, labelUnit);
		        
		        @Override 
		        public void updateItem(String item, boolean empty) {
		        	labelProduct.setStyle("-fx-background-color: whitesmoke;");
		        	labelUnit.setStyle("-fx-background-color: whitesmoke;");
		        	pane.setStyle("-fx-alignment: center; -fx-spacing: 5px;");
		         super.updateItem(item, empty); 
		         if (empty) { 
		          setGraphic(null); 
		          setText(null); 
		         } else {
		        	  Product product = getTableView().getItems().get(getIndex());
		        	  
		        	  labelProduct.setText( product.getCodeProdServ() );
		        	  labelUnit.setText( product.getUnitCode() );
						
		          setGraphic(pane);
		          setText(null); 
		         } 
		        } 
		       };
		       return cell; 
		      } 
		     };
		
		
		colQuantity.setCellValueFactory(new PropertyValueFactory<Product, String>("Quantity"));
		colQuantity.setCellFactory(TextFieldTableCell.forTableColumn() );
		this.tabProducts.setEditable(true);
		colQuantity.setOnEditCommit(data -> {
		    System.out.println("Nuevo Nombre: " +  data.getNewValue());
		    System.out.println("Antiguo Nombre: " + data.getOldValue());
		});
		
		colKeys.setCellFactory(cellFactory);
		colName.setCellValueFactory( new PropertyValueFactory<Product, String >("Name") );
		colDescription.setCellValueFactory(new PropertyValueFactory<Product, String>("Description"));
		colPrice.setCellValueFactory(new PropertyValueFactory<Product, Float>("Price"));
		colSubtotal.setCellValueFactory(new PropertyValueFactory<Product, Float>("subtotal"));
		colDiscount.setCellValueFactory(new PropertyValueFactory<Product, Float>("discount"));
		
		Callback<TableColumn<Product, String>, TableCell<Product, String>> 
		cellFactoryTaxes = new Callback< TableColumn<Product, String>, TableCell<Product, String> >() {
		      @Override 
		      public TableCell call(final TableColumn<Product, String> param) {
		       final TableCell<Product, String> cell = new TableCell<Product, String>() { 

		        
		        Label labelTax = new Label();
		        VBox pane = new VBox(labelTax);
		        
		        @Override 
		        public void updateItem(String item, boolean empty) {
		        	//labelProduct.setStyle("-fx-background-color: #666666;");
		        	//labelUnit.setStyle("-fx-background-color: #666666;");
		        	//pane.setStyle("-fx-alignment: center; -fx-spacing: 15px;");
		         super.updateItem(item, empty); 
		         if (empty) { 
		          setGraphic(null); 
		          setText(null); 
		         } else {
		        	  Product product = getTableView().getItems().get(getIndex());
		        	  String showTaxes = "";
		        	  List<ProductTax> listTax = product.getTaxes();
		        	  for( ProductTax prodTax : listTax ) {
		        		  showTaxes += "\n" + prodTax.getName( ) + " : " + prodTax.getRate()*product.getPrice();
		        	  }
		        	  labelTax.setText( showTaxes );
						
		        	   
		          setGraphic(pane);
		          setText(null); 
		         } 
		        } 
		       };
		       return cell; 
		      } 
		     };
		
		colTaxes.setCellFactory( cellFactoryTaxes );
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
			cbxShowClients.getItems().add(client);
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
		Client client = cbxShowClients.getSelectionModel().getSelectedItem();
		
		if(client != null) {
			FXMLLoader loader = new FXMLLoader( this.getClass().getResource("/fxml/addClient.fxml"));			
			Parent parent = loader.load();				
			EditClientController controller = loader.getController();
	
			controller.setContact( client );
			controller.loadData();
			
			Scene scene = new Scene( parent );
			Stage stage = new Stage();
			stage.initModality( Modality.APPLICATION_MODAL );
			stage.setTitle("Nuevo Cliente");
			stage.setScene( scene );
			stage.showAndWait();			
		}
		
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
