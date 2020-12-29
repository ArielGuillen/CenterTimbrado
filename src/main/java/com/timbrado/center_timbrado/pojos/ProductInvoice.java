package com.timbrado.center_timbrado.pojos;

import com.Facturama.sdk_java.Models.Product;
import com.Facturama.sdk_java.Models.Request.ProductTax;

public class ProductInvoice {
	private int quantity;
	private Product product;

	private String Name;
	private String Description; 
	private Float Price;
	private String CodeProdServ;
	private String UnitCode;
	
	private Float Subtotal;
	private Float Discount;
	private Float Total;
	
	
	//----------------Constructors-----------------

	public ProductInvoice() {
		super();
	}
	
	public ProductInvoice( Product product ) {
		super();
		this.quantity = 1;
		this.product = product;
		
		this.Name = product.getName();
		this.Description = product.getDescription();
		this.Price = (float) product.getPrice();
		this.CodeProdServ = product.getCodeProdServ();
		this.UnitCode = product.getUnitCode();
		
		this.Subtotal = this.quantity * this.Price;
		this.calculateTotal(); 
	}

	//----------------Getters and Setters-----------------

	public int getQuantity() {
		return quantity;
	}
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	public Product getProduct() {
		return product;
	}
	public void setProduct(Product product) {
		this.product = product;
	}
	
	public String getName() {
		return Name;
	}

	public void setName(String name) {
		Name = name;
	}

	public String getDescription() {
		return Description;
	}

	public void setDescription(String description) {
		Description = description;
	}

	public double getPrice() {
		return Price;
	}

	public void setPrice(float price) {
		Price = price;
	}

	public String getCodeProdServ() {
		return CodeProdServ;
	}

	public void setCodeProdServ(String codeProdServ) {
		CodeProdServ = codeProdServ;
	}

	public String getUnitCode() {
		return UnitCode;
	}

	public void setUnitCode(String unitCode) {
		UnitCode = unitCode;
	}
	
	public Float getSubtotal() {
		return Subtotal;
	}

	public void setSubtotal(Float subtotal) {
		Subtotal = subtotal;
	}

	public Float getDiscount() {
		return Discount;
	}

	public void setDiscount(Float discount) {
		Discount = discount;
	}

	public Float getTotal() {
		return Total;
	}

	public void setTotal(Float total) {
		Total = total;
	}
	
	//--Main Methods--
	
	public void calculateTotal() {

		this.Total = this.Subtotal * this.quantity;
		for( ProductTax prodTax : this.product.getTaxes() ) {
			if( prodTax.getIsRetention() ) 
				this.Total = (float) (this.Total - this.Total * prodTax.getRate()) ;
			else 
				this.Total = (float) (this.Total + this.Total * prodTax.getRate()) ;			
		}
		
	}
	
	public void increaseQuantity( ) {
		this.quantity = this.quantity + 1 ;
	}
}
