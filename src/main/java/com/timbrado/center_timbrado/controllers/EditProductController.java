package com.timbrado.center_timbrado.controllers;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import com.Facturama.sdk_java.Models.Response.Catalogs.Cfdi.ProductServices;
import com.Facturama.sdk_java.Models.Response.Catalogs.Cfdi.Unit;
import com.timbrado.center_timbrado.services.Facturama;

import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.util.StringConverter;

public class EditProductController  implements Initializable{
	
	//--ComboBox--
	@FXML
	public ComboBox<ProductServices> cbxCProduct;
	@FXML
	public ComboBox<Unit> cbxUnit;
	@FXML
	public ComboBox<String> cbxIva;
	@FXML
	public ComboBox<String> cbxIvaRet;
	@FXML
	public ComboBox<String> cbxIeps;
	@FXML
	public ComboBox<String> cbxIsr;
	
	//--TextField--
	@FXML
	public TextField txtName;
	@FXML
	public TextField txtId;
	@FXML
	public TextField txtPrice;
	@FXML
	public TextField txtCPredial;
	@FXML
	public TextField txtKeywordProduct;
	@FXML
	public TextField txtKeywordUnit;
	//--TextArea--
	@FXML 
	public TextArea txtDescription;
	
	//--Buttons--
	@FXML
	public Button btnConfirmar;
	
	//--Label--
	@FXML
	public Label warning;
	@FXML
	public Label lblTitle;
	
	//--Icon--
	@FXML
	public FontAwesomeIconView iconWarning;
	@FXML
	public FontAwesomeIconView iconWarningUnit;
	
	
	//--Initialize Methods--
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		try {
			initializateIva();
			initializateIvaRet();
			initializatecbxConverter();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}


	private void initializateIva() {
		// TODO Auto-generated method stub
		
	}
	
	private void initializateIvaRet() {
		// TODO Auto-generated method stub
		
	}
	
	
	//------------------------------------Main methods--------------------------------
	
	public void initializatecbxConverter() {
		this.cbxCProduct.setConverter( new StringConverter <ProductServices>()  {
			@Override
			public String toString( ProductServices codeProduct ) {
				return codeProduct.getName();
			}
			@Override
			public ProductServices fromString( String string ) {
				return null;
			}

		});
		this.cbxUnit.setConverter( new StringConverter <Unit>()  {
			@Override
			public String toString( Unit unit ) {
				return unit.getName();
			}
			@Override
			public Unit fromString( String string ) {
				return null;
			}

		});
	}
	
	//-------Load ComboBox Data--------//
	
	@FXML
	public void loadCodes(){
		try {
			if( !this.txtKeywordProduct.getText().trim().isEmpty() ) {

				this.iconWarning.setVisible( false );
				
				String key = this.txtKeywordProduct.getText().trim();
				List<ProductServices> productServices =  Facturama.facturama.Catalogs().ProductsOrServices( key );
				for(ProductServices prod : productServices) {
					cbxCProduct.getItems().add( prod );		
				}
			}
			else {
				this.iconWarning.setVisible( true );		
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@FXML
	public void loadUnits(){
		try {
			if( !this.txtKeywordUnit.getText().trim().isEmpty() ) {
				
				this.iconWarningUnit.setVisible( false );

				String key = this.txtKeywordProduct.getText().trim();
				List<Unit> units =  Facturama.facturama.Catalogs().Units( key );
				for(Unit unit : units) {
					cbxUnit.getItems().add( unit );		
				}	
			} 

			else {
				this.iconWarningUnit.setVisible( true );		
			}
		}catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	//-------Cancel operation----//
	@FXML
	public void cancelViewProduct() {
		
		//this.client = null;	//null assignment for validation of changes in contact
		closeWindow();
		
	}
	
	public void closeWindow() {

		Stage stage = (Stage) this.btnConfirmar.getScene().getWindow();
		stage.close();
		
	}
	//-------Save product information----//
	@FXML		
	public void saveData() {
		
	}



}
