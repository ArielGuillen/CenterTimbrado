package com.timbrado.center_timbrado.pojos.request;

import com.Facturama.sdk_java.Models.Request.Tax;

public class TaxRequest {

	private Tax tax;
	
	public String toString() {
		
		return String.valueOf(tax.getTotal());
		
	}
	
}
