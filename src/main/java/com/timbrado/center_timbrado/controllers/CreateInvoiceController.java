package com.timbrado.center_timbrado.controllers;

import java.io.IOException;

import com.Facturama.sdk_java.Container.FacturamaApi;
import com.Facturama.sdk_java.Models.Client;
import com.Facturama.sdk_java.Models.Product;
import com.Facturama.sdk_java.Models.Exception.FacturamaException;

import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

public class CreateInvoiceController{
	
	@FXML
	public ComboBox<String> cbxShowClients;
	
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
	
	public void showClients() {
		cbxShowClients.getItems().clear();
		getAllClients();
		System.out.println("ASasdsa");
		
	}
	
	public void getAllClients() throws IOException, FacturamaException, Exception {
		for(Client client : facturama.Clients().List() ) {
			cbxShowClients.getItems().add(client.getName());
		}
	}
	
	
	

}
