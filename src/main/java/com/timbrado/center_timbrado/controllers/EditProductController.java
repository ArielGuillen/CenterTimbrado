package com.timbrado.center_timbrado.controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import com.Facturama.sdk_java.Models.Product;
import com.Facturama.sdk_java.Models.Exception.FacturamaException;
import com.Facturama.sdk_java.Models.Request.ProductTax;
import com.Facturama.sdk_java.Models.Response.Catalogs.Cfdi.ProductServices;
import com.Facturama.sdk_java.Models.Response.Catalogs.Cfdi.Unit;
import com.timbrado.center_timbrado.exceptions.EmptyComboBoxSelectionException;
import com.timbrado.center_timbrado.exceptions.EmptyFieldException;
import com.timbrado.center_timbrado.services.Facturama;

import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.BorderPane;
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
	public BorderPane brdrPnKeywordProduct;
	@FXML
	public TextField txtKeywordUnit;
	@FXML
	public BorderPane brdrPnKeywordUnit;
	@FXML
	public TextField txtIeps;
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
	
	//--Radio Buttons--
	@FXML
	public RadioButton rdbtnTasa;

	@FXML
	public RadioButton rdbtnCuota;
	
	
	//-------------------Object Product for update data-------------------
	
	public Product product;
		
	//---Setter & getter methods for contact to modify---//
		
	public void setProduct(Product product) {
		this.product = product;;
	}

	public Product getProduct() {
		return this.product;
	}

	//---------Show Product information to Update-------//
	
	protected void loadData() {
		
		this.lblTitle.setText("Modificar datos del producto");
		//General Client Data
		
		txtName.setText( this.product.getName() );
		txtPrice.setText( String.valueOf(this.product.getPrice()) );
		txtDescription.setText( this.product.getDescription());
		txtId.setText( this.product.getIdentificationNumber());
		txtCPredial.setText( product.getCuentaPredial());
			
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
	public void saveProduct() throws IOException, FacturamaException, Exception {
		if( this.product == null ) {
			
			this.product = new Product();
			saveDataProduct();
			Facturama.facturama.Products().Create(this.product);
		}
		else {
			saveDataProduct();
			Facturama.facturama.Products().Update( this.product, this.product.getId() );
		}
		closeWindow();	
	}
	
		
	
	private void saveDataProduct() {
		try {
			this.warning.setText("");
			checkIfFieldsIsNotEmpty();	
			checkifComboBoxHasASelection();
			//--Add General Data--
			this.product.setName(txtName.getText().trim() );
			this.product.setIdentificationNumber( txtId.getText().trim().toUpperCase() );

			this.product.setDescription( txtDescription.getText().trim() );
			this.product.setPrice( Double.parseDouble( txtPrice.getText().trim() ) );
			
			if( !txtCPredial.getText().isEmpty() )
			this.product.setCuentaPredial( txtCPredial.getText().trim() );
			
			//--Add Codes--
			this.product.setCodeProdServ( this.cbxCProduct.getSelectionModel().getSelectedItem().getValue() );
			this.product.setUnit( this.cbxUnit.getSelectionModel().getSelectedItem().getName() );
			this.product.setUnitCode( this.cbxUnit.getSelectionModel().getSelectedItem().getValue() );
			
			
			//--Add Taxes--
			List< ProductTax > taxes = new ArrayList<ProductTax>();
			if( this.cbxIva.getSelectionModel().getSelectedItem() != null )
				taxes.add( this.cbxIva.getSelectionModel().getSelectedItem() );
			if( this.cbxIvaRet.getSelectionModel().getSelectedItem() != null )
				taxes.add( this.cbxIvaRet.getSelectionModel().getSelectedItem() );
			if( this.cbxIsr.getSelectionModel().getSelectedItem() != null )
				taxes.add( this.cbxIsr.getSelectionModel().getSelectedItem() );
			//Checar ieps si se usa por tasa o cuota
			
			this.product.setTaxes( taxes );			
	        
		}catch(EmptyFieldException e ) {			
			warning.setText( e.getMessage() );			
		} catch( NumberFormatException e) {				
			warning.setText( e.getMessage() );
		} catch (EmptyComboBoxSelectionException e) {
			warning.setText( e.getMessage() );
		}
		
	}

	//--Initialize Methods--
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		try {
			initializatecbxConverter();
			initializeTaxesIva();
			initializeTaxesIvaRet();
			initializeTaxesIsr();
			initializateIeps();
			initializeTextFields();
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
		
		this.cbxIeps.setConverter( new StringConverter<ProductTax>() {

			@Override
			public String toString(ProductTax productTax) {
				return productTax.getName() + " - " + productTax.getRate()*100 + "%" ;
			}

			@Override
			public ProductTax fromString(String string) {
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
	
	//-----------------Initialize Taxes Methods----------------------
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
		
	public void initializeTaxesIsr() {
		this.createTax("ISR", false, .2, true, cbxIsr);
		this.createTax("ISR", false, .10666, true, cbxIsr);
		this.createTax("ISR", false, .10, true, cbxIsr);
		this.createTax("ISR", false, .054, true, cbxIsr);
		this.createTax("ISR", false, .04, true, cbxIsr);
		this.createTax("ISR", false, .03, true, cbxIsr);
		this.createTax("ISR", false, .02, true, cbxIsr);
		this.createTax("ISR", false, .011, true, cbxIsr);
		this.createTax("ISR", false, .009, true, cbxIsr);
		this.createTax("ISR", false, .005, true, cbxIsr);
		this.createTax("ISR", false, .004, true, cbxIsr);
		this.createTax("ISR", false, .000, true, cbxIsr);
		
	}
	
	public void initializateTaxesIeps() {
		this.createTax("IEPS", false, 3.00, true, cbxIeps);
		this.createTax("IEPS", false, 1.60, true, cbxIeps);
		this.createTax("IEPS", false, .53, true, cbxIeps);
		this.createTax("IEPS", false, .50, true, cbxIeps);
		this.createTax("IEPS", false, .35, true, cbxIeps);
		this.createTax("IEPS", false, .304, true, cbxIeps);
		this.createTax("IEPS", false, .30, true, cbxIeps);
		this.createTax("IEPS", false, .2988, true, cbxIeps);
		this.createTax("IEPS", false, .265, true, cbxIeps);
		this.createTax("IEPS", false, .25, true, cbxIeps);
		this.createTax("IEPS", false, .09, true, cbxIeps);
		this.createTax("IEPS", false, .08, true, cbxIeps);
		this.createTax("IEPS", false, .07, true, cbxIeps);
		this.createTax("IEPS", false, .06, true, cbxIeps);
		this.createTax("IEPS", false, .0591, true, cbxIeps);
		this.createTax("IEPS", false, .04, true, cbxIeps);
	}
	
	public void initializateIeps() {
		final ToggleGroup groupIEPS = new ToggleGroup();
		this.rdbtnTasa.setToggleGroup(groupIEPS);
		this.rdbtnCuota.setToggleGroup(groupIEPS);
		
		//Cuando se selecciona el RadioButton de Tasa
		this.rdbtnTasa.setOnMouseClicked(event -> {

			this.cbxIeps.setVisible( true );
			this.txtIeps.setVisible( false );
			initializateTaxesIeps();

		}
		);

		//Cuando se selecciona el RadioButton de Cuota
		this.rdbtnCuota.setOnMouseClicked(event -> {
			this.cbxIeps.setVisible( false );
			this.txtIeps.setVisible( true );
		}
		);
	}
	
	
	public void initializeTextFields() {
		
		this.txtKeywordProduct.focusedProperty().addListener( (observable, oldValue, newValue) -> {
			
			if(newValue == true) {
				this.brdrPnKeywordProduct.getStyleClass().removeAll("border-pane-icon");
				this.brdrPnKeywordProduct.getStyleClass().add("border-pane-icon-focused");
			} else {
				this.brdrPnKeywordProduct.getStyleClass().removeAll("border-pane-icon-focused");
				this.brdrPnKeywordProduct.getStyleClass().add("border-pane-icon");
			}
			
		});
		
		this.txtKeywordUnit.focusedProperty().addListener( (observable, oldValue, newValue) -> {
			
			if(newValue == true) {
				this.brdrPnKeywordUnit.getStyleClass().removeAll("border-pane-icon");
				this.brdrPnKeywordUnit.getStyleClass().add("border-pane-icon-focused");
			} else {
				this.brdrPnKeywordUnit.getStyleClass().removeAll("border-pane-icon-focused");
				this.brdrPnKeywordUnit.getStyleClass().add("border-pane-icon");
			}
			
		});
		
	}
	
	
	public void createTax( String name, boolean quota, double rate, boolean retention, ComboBox<ProductTax> comboBox ) {
		
		ProductTax tax = new ProductTax();	    
	    tax.setName( name );
	    tax.setIsQuota( quota );
	    tax.setRate( rate );
	    tax.setIsRetention( retention );
	    comboBox.getItems().add( tax );
	    
	}
	
	//-----------------------------Exception Methods-------------------------//
	
		public void checkIfFieldsIsNotEmpty()throws EmptyFieldException{
			
			if( this.txtName.getText().trim().isEmpty() )
				throw new EmptyFieldException( "El campo Nombre no puede estar vacío" );
			if( this.txtId.getText().trim().isEmpty() )
				throw new EmptyFieldException( "El campo No. de identificación no puede estar vacío" );
			if( this.txtDescription.getText().trim().isEmpty() )
				throw new EmptyFieldException( "El campo Descripción no puede estar vacío" );
			if( this.txtPrice.getText().trim().isEmpty() )
				throw new EmptyFieldException( "El campo Precio no puede estar vacío" );
			//if( this.txtCPredial.getText().trim().isEmpty() )
				//throw new EmptyFieldException( "El campo Cuenta Predial no puede estar vacío" );
			
		}
		
		public void checkifComboBoxHasASelection()throws EmptyComboBoxSelectionException {
			if( this.cbxCProduct.getSelectionModel().getSelectedItem() == null )
				throw new EmptyComboBoxSelectionException( "Seleccione un código de producto" );
			if( this.cbxUnit.getSelectionModel().getSelectedItem() == null )
				throw new EmptyComboBoxSelectionException( "Seleccione la unidad correspondiente" );
			
		}

}
