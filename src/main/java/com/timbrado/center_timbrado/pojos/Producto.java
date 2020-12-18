package com.timbrado.center_timbrado.pojos;

import com.Facturama.sdk_java.Models.Product;

public class Producto {
	private int quantity;
	private Product product;
	
	
	public Producto() {
		super();
	}
	
	
	
	public Producto( Product product, int quantity ) {
		super();
		this.quantity = quantity;
		this.product = product;
	}



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
	
}
