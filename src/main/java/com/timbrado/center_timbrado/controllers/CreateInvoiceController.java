package com.timbrado.center_timbrado.controllers;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
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
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.DateCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Callback;
import javafx.util.StringConverter;
import javafx.util.converter.IntegerStringConverter;

public class CreateInvoiceController implements Initializable{
	
	//--ComboBox--
	@FXML
	public ComboBox<Client> cbxShowClients;
	
	@FXML
	public ComboBox<Product> cbxShowProducts;
	
	@FXML
	public ComboBox<String> cbxShowPaymentsForms;
	
	@FXML
	public ComboBox<String> cbxShowPaymentsMethods;

	@FXML
	public ComboBox<String> cbxShowCurrencies;
	
	@FXML
	public DatePicker date;
	
	//--TableView--
	@FXML
	public TableView<Producto> tabProducts;
	@FXML
	TableColumn<Producto, Integer> colQuantity;
	@FXML
	TableColumn<Producto, String> colName;
	@FXML
	TableColumn<Producto, String> colKeys;
	@FXML
	TableColumn<Producto, String> colDescription;
	@FXML
	TableColumn<Producto, Float> colPrice;
	@FXML
	TableColumn<Producto, Float> colSubtotal;
	@FXML
	TableColumn<Producto, Float> colDiscount;
	@FXML
	TableColumn<Producto, String> colTaxes;
	@FXML
	TableColumn<Producto, Float> colTotal;
	
	
	


	public void initialize(URL location, ResourceBundle resources) {
		
		try {
			initialiseComboboxConverters();
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
	
	//--Initialize Methods--
	
	public void initialiseComboboxConverters(){
		
		//--Clients ComboBox--
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
		
		//--Products ComboBox--
		cbxShowProducts.setConverter( new StringConverter<Product>() {
			@Override
			public String toString(Product p) {
				return p.getName();
			}
			
			@Override
			public Product fromString(String string) {
				return null;
			}
		} );
			
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
	        	try {					
	        		Product product = cbxShowProducts.getSelectionModel().getSelectedItem();	
					int index = searchProduct( product );
					if( index == -1 ) {
						Producto producto = new Producto( product );
						tabProducts.getItems().add( producto );
					}
					else {
						tabProducts.getItems().get(index).increaseQuantity( );
						tabProducts.getItems().get(index).calculateTotal();
						tabProducts.refresh();
					}
				} catch (Exception e1) {
					e1.printStackTrace();
				}
	        }
	    });
	}
	public int searchProduct( Product product ) {				
		int index = -1;
		Producto productAux;
		for( int i = 0; i < tabProducts.getItems().size(); i++ ) {
			productAux = tabProducts.getItems().get( i );
			if( productAux.getProduct().getId().equals( product.getId() ) ) {
				index = i;
				break;
			}
		}
		return index;
	}
	
	
	
	public void initializeTableView() {
		this.tabProducts.setEditable(true);
		
		//--Special Columns--
		createCellFactoryKeys();		
		createCellFactoryTaxes();
		
		
		//--Editable Column--
		colQuantity.setCellValueFactory(new PropertyValueFactory<Producto, Integer> ("Quantity") );
		colQuantity.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter()));
		colQuantity.setOnEditCommit(data -> {
			
			Product product = data.getRowValue().getProduct();	
			int index = searchProduct( product );
			if( index != -1 ) {
				tabProducts.getItems().get(index).setQuantity( data.getNewValue() );
				tabProducts.getItems().get(index).calculateTotal();
				tabProducts.refresh();
			}
			
		    System.out.println("Nuevo Valor: " + data.getRowValue().getQuantity() );
		});
		
		//--Regular Columns--
		colName.setCellValueFactory( new PropertyValueFactory<Producto, String >("Name") );
		colDescription.setCellValueFactory(new PropertyValueFactory<Producto, String>("Description"));
		colPrice.setCellValueFactory(new PropertyValueFactory<Producto, Float>("Price"));
		colSubtotal.setCellValueFactory(new PropertyValueFactory<Producto, Float>("subtotal"));
		colDiscount.setCellValueFactory(new PropertyValueFactory<Producto, Float>("discount"));
		colTotal.setCellValueFactory(new PropertyValueFactory<Producto, Float>("total"));
	}
	

	private void createCellFactoryTaxes() {
		Callback<TableColumn<Producto, String>, TableCell<Producto, String>> 
			cellFactoryTaxes = param -> {
			   final TableCell<Producto, String> cell = new TableCell<Producto, String>() {
				   Label labelTax = new Label();
				   VBox pane = new VBox(labelTax);
			    
				   @Override 
				   public void updateItem(String item, boolean empty) {
					   pane.setStyle("-fx-alignment: top-left");
					   super.updateItem(item, empty); 
					   if (empty) { 
						   setGraphic(null); 
						   setText(null); 
				       } 
					   else {
				    	 	Producto product = getTableView().getItems().get(getIndex());
				    	 	String showTaxes = "";
				    	 	
				    	 	List<ProductTax> listTax = product.getProduct().getTaxes();		    	 	
				    	 	for( ProductTax prodTax : listTax ) 
				    	 		showTaxes += "\n" + prodTax.getName( ) + " : " + prodTax.getRate()*product.getPrice() * product.getQuantity() ;
				    	 	
				    	 	labelTax.setText( showTaxes );
									    	   
				    	 	setGraphic(pane);
				    	 	setText(null); 
				        } 
				   } 
			   };
			   return cell; 
			};
		
		colTaxes.setCellFactory( cellFactoryTaxes );
	}

	private void createCellFactoryKeys() {
		Callback<TableColumn<Producto, String>, TableCell<Producto, String>> 
			cellFactory = param -> {
			   final TableCell<Producto, String> cell = new TableCell<Producto, String>() { 
	
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
					   } 
					   else {
						   Producto product = getTableView().getItems().get(getIndex());
						   labelProduct.setText( product.getCodeProdServ() );
						   labelUnit.setText( product.getUnitCode() );
								
						   setGraphic(pane);
						   setText(null); 
					   } 
				   }
			   };
			return cell; 
		};
		colKeys.setCellFactory(cellFactory);
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
	
	//Obtener todos los clientes
	public List<Client> getClients() throws IOException, FacturamaException, Exception{
		List<Client> allClients = Facturama.facturama.Clients().List();
		return allClients;
	}
	

	public void showProducts() throws IOException, FacturamaException, Exception {
		cbxShowProducts.getItems().clear();
		List<Product> productList = getProducts();
		for(Product product : productList) {
			cbxShowProducts.getItems().add( product );
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

	//Obtener Métodos de Pago	
	public List<Catalog> getPaymentMethods() throws IOException, FacturamaException, Exception {
		List<Catalog> allPaymentsMethods = Facturama.facturama.Catalogs().PaymentMethods();
		return allPaymentsMethods;
	}

	//Inicializar Métodos de Pago
	public void initializatePaymentMethods() throws IOException, FacturamaException, Exception {
		List<Catalog> allPaymentsMethods = getPaymentMethods();
		for(Catalog c : allPaymentsMethods) {
			cbxShowPaymentsMethods.getItems().add(c.getName());		
		}
	}

	//Obtener Monedas
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
	
	
	//---------------Inyected Methods-------------------------
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
	

	

}
