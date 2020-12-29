package com.timbrado.center_timbrado.controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import com.Facturama.sdk_java.Models.Product;
import com.Facturama.sdk_java.Models.Exception.FacturamaException;
import com.timbrado.center_timbrado.services.Facturama;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
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

public class CRUDProductosController {
	
	@FXML
	public TableView<Product> tabProductos;
	
	@FXML
	TableColumn<Product, String> colClave;

	@FXML
	TableColumn<Product, String> colNumIdent;
	
	@FXML
	TableColumn<Product, String> colNombre;
	
	@FXML
	TableColumn<Product, Float> colPrecio;
	
	@FXML
	TableColumn<Product, String> colActions;
	
//	FacturamaApi facturama = new FacturamaApi("ricardomangore", "1nt3rm3zz0", true );

	
	public void initialize(URL location, ResourceBundle resources) {
		initializeTableView();
		try {
			addProduct();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void initializeTableView() {
		colClave.setCellValueFactory(new PropertyValueFactory<Product, String>("clave"));
		colNumIdent.setCellValueFactory(new PropertyValueFactory<Product, String>("Num_identificacion"));
		colNombre.setCellValueFactory(new PropertyValueFactory<Product, String>("nombre"));
		colPrecio.setCellValueFactory(new PropertyValueFactory<Product, Float>("precio"));
		Callback<TableColumn <Product, String>, TableCell<Product, String>> 
		cellFactory = new Callback<TableColumn<Product, String>, TableCell<Product, String> >() {
	      @Override 
	      public TableCell call(final TableColumn<Product, String> param) {
	       final TableCell<Product, String> cell = new TableCell<Product, String>() { 

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
	        	  Product product = getTableView().getItems().get(getIndex());
	        	  try {
					deleteProduct(product);
					
					tabProductos.getItems().clear();
					addProduct();
					
				} catch (Exception e) {
					e.printStackTrace();
					System.out.println("Excepción de borrar Producto");
				}
	        	  System.out.println("Prueba Botón borrar Producto: " + product.getName() ); 
	          }); 
	          
	          btnEdit.setOnAction(event -> { 
	        	  Product product = getTableView().getItems().get(getIndex());
	        	  try {
	        		  editProduct(product);
	        		  
	        		  tabProductos.getItems().clear();
	        		  addProduct();
	        		  
	        	  } catch (Exception e) {
	        		  e.printStackTrace();
	        		  System.out.println("Excepción de editar Producto");
	        	  }
	        	  System.out.println("Prueba Botón Editar Producto: " + product.getName() ); 
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
	
	public void addProduct() throws IOException, FacturamaException, Exception {
		for(Product product: Facturama.facturama.Products().List()) {
			tabProductos.getItems().add(product);
			System.out.println(product.getName());
		}
	}
	
	@FXML
	public void newProduct() throws FacturamaException, Exception {
		//The fxml and the controller are loaded for modify the contact
		FXMLLoader loader = new FXMLLoader( this.getClass().getResource("/fxml/addProduct.fxml"));			
		Parent parent = loader.load();				
		EditProductController controller = loader.getController();
		
		Scene scene = new Scene( parent );
		Stage stage = new Stage();
		stage.initModality( Modality.APPLICATION_MODAL );
		stage.setTitle("Nuevo Producto");
		stage.setScene( scene );
		stage.showAndWait();
		tabProductos.getItems().clear();
		addProduct();
	}
	
	public void deleteProduct(Product product) throws IOException, FacturamaException, Exception {
		Facturama.facturama.Products().Remove(product.getId());
	}
	
	public void editProduct(Product product) throws IOException {
		FXMLLoader loader = new FXMLLoader( this.getClass().getResource("/fxml/addProduct.fxml"));			
		Parent parent = loader.load();		
		EditProductController controller = loader.getController();
		controller.setProduct(product);
		controller.loadData();
		
		Scene scene = new Scene( parent );
		Stage stage = new Stage();
		stage.initModality( Modality.APPLICATION_MODAL );
		stage.setTitle("Nuevo Producto");
		stage.setScene( scene );
		stage.showAndWait();	
	}

}
