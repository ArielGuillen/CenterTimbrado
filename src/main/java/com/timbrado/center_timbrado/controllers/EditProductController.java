package com.timbrado.center_timbrado.controllers;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import com.Facturama.sdk_java.Models.Request.ProductTax;
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
	public ComboBox< ProductTax > cbxIva;
	@FXML
	public ComboBox< ProductTax > cbxIvaRet;
	@FXML
	public ComboBox< ProductTax > cbxIeps;
	@FXML
	public ComboBox< ProductTax > cbxIsr;
	
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
			initializatecbxConverter();
			initializeTaxesIva();
			initializeTaxesIvaRet();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
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
		this.cbxIva.setConverter( new StringConverter <ProductTax>()  {
			@Override
			public String toString( ProductTax productTax ) {
				return productTax.getName() + " - " + productTax.getRate()*100 + "%" ;
			}
			@Override
			public ProductTax fromString( String string ) {
				return null;
			}

		});
		this.cbxIvaRet.setConverter( new StringConverter <ProductTax>()  {
			@Override
			public String toString( ProductTax productTax ) {
				return productTax.getName() + " - " + productTax.getRate()*100 + "%" ;
			}
			@Override
			public ProductTax fromString( String string ) {
				return null;
			}

		});
		this.cbxIsr.setConverter( new StringConverter <ProductTax>()  {
			@Override
			public String toString( ProductTax productTax ) {
				return productTax.getName() + " - " + productTax.getRate()*100 + "%" ;
			}
			@Override
			public ProductTax fromString( String string ) {
				return null;
			}

		});
	}
	
	//-------Load ComboBox Data--------//
	
	@FXML
	public void loadCodes(){
		try {
			if( !this.txtKeywordProduct.getText().trim().isEmpty() ) {
				this.cbxCProduct.setPromptText("");
				this.iconWarning.setVisible( false );
				
				String key = this.txtKeywordProduct.getText().trim();
				List<ProductServices> productServices =  Facturama.facturama.Catalogs().ProductsOrServices( key );
				if( productServices.size() != 0 ) {
					for(ProductServices prod : productServices) {
						cbxCProduct.getItems().add( prod );		
					}
				}
				else {
					this.cbxCProduct.setPromptText("No se encontraron resultados");
					this.iconWarning.setVisible( true );		
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

				this.cbxUnit.setPromptText("");
				this.iconWarningUnit.setVisible( false );
				String key = this.txtKeywordUnit.getText().trim();
				List<Unit> units =  Facturama.facturama.Catalogs().Units( key );
				if( units.size() != 0 ) {
					for(Unit unit : units) {
						cbxUnit.getItems().add( unit );		
					}	
				}
				else {
					this.cbxUnit.setPromptText("No se encontraron resultados");
					this.iconWarningUnit.setVisible( true );		
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
	
	public void initializeTaxesIva() {
		
		
		this.createTax( "IVA" , false, 0.16, false, cbxIva );
		this.createTax( "IVA" , false, 0.08, false, cbxIva );
		this.createTax( "IVA" , false, 0.00, false, cbxIva );
	    
	}

	private void initializeTaxesIvaRet() {
		
		this.createTax( "IVA Ret" , false, 0.16, true, cbxIvaRet );
		this.createTax( "IVA Ret" , false, 0.106668, true, cbxIvaRet );
		this.createTax( "IVA Ret" , false, 0.106667, true, cbxIvaRet );
		this.createTax( "IVA Ret" , false, 0.106666, true, cbxIvaRet );
		this.createTax( "IVA Ret" , false, 0.1067, true, cbxIvaRet );
		this.createTax( "IVA Ret" , false, 0.1066, true, cbxIvaRet );
		this.createTax( "IVA Ret" , false, 0.106, true, cbxIvaRet );
		this.createTax( "IVA Ret" , false, 0.10, true, cbxIvaRet );
		this.createTax( "IVA Ret" , false, 0.08, true, cbxIvaRet );
		this.createTax( "IVA Ret" , false, 0.06, true, cbxIvaRet );
		this.createTax( "IVA Ret" , false, 0.05, true, cbxIvaRet );
		this.createTax( "IVA Ret" , false, 0.053333, true, cbxIvaRet );
		this.createTax( "IVA Ret" , false, 0.04, true, cbxIvaRet );
		this.createTax( "IVA Ret" , false, 0.03, true, cbxIvaRet );
		this.createTax( "IVA Ret" , false, 0.025, true, cbxIvaRet );
		this.createTax( "IVA Ret" , false, 0.02, true, cbxIvaRet );
		this.createTax( "IVA Ret" , false, 0.007, true, cbxIvaRet );
		this.createTax( "IVA Ret" , false, 0.005, true, cbxIvaRet );
		this.createTax( "IVA Ret" , false, 0.002, true, cbxIvaRet );
		this.createTax( "IVA Ret" , false, 0.000, true, cbxIvaRet );
	    
	}
	
	public void initializeTaxIsr() {
		
	}
	
	public void createTax( String name, boolean quota, double rate, boolean retention, ComboBox<ProductTax> comboBox ) {
		
		ProductTax tax = new ProductTax();	    
	    tax.setName( name );
	    tax.setIsQuota( quota );
	    tax.setRate( rate );
	    tax.setIsRetention( retention );
	    comboBox.getItems().add( tax );
	    
	}

}
