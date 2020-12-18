package com.timbrado.center_timbrado.pojos.response;

import com.Facturama.sdk_java.Models.Response.Tax;

public class TaxResponse {

	private Tax tax;
	
	public Tax getTax() {
		return tax;
	}
	public void setTax(Tax tax) {
		this.tax = tax;
	}

	public String toString() {
		
		return String.valueOf(tax.getTotal());
		
	}
	
}
