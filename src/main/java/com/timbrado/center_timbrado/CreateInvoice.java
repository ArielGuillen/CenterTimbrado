package com.timbrado.center_timbrado;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.Facturama.sdk_java.Container.FacturamaApi;
import com.Facturama.sdk_java.Models.Request.Receiver;
import com.Facturama.sdk_java.Models.Request.Tax;
import com.Facturama.sdk_java.Models.Response.Catalogs.Cfdi.Currency;
import com.timbrado.center_timbrado.services.Facturama;


public class CreateInvoice {
	
//	FacturamaApi facturama = new FacturamaApi("ricardomangore", "1nt3rm3zz0", true);
//	
//	List<Currency> lstCurrencies = Facturama.facturama.Catalogs().Currencies();
//	
//	Currency currency = lstCurrencies.stream().filter(p -> p.getValue().equals("MXN")).findFirst().get();
//
//    // Modelo de CFDI (datos de envío)
//	com.Facturama.sdk_java.Models.Request.Cfdi cfdi = new com.Facturama.sdk_java.Models.Request.Cfdi();
//
//    cfdi.setCfdiType(CfdiType.Ingreso.getValue());
//    cfdi.setNameId(facturama.Catalogs().NameIds().get(1).getValue());
//    cfdi.setCfdiType( CfdiType.Ingreso.getValue() );
//    cfdi.setNameId(facturama.Catalogs().NameIds().get(1).getValue());
//    cfdi.setExpeditionPlace("78216");
//    cfdi.setPaymentForm(facturama.Catalogs().PaymentForms().get(3).getValue());
//    cfdi.setPaymentMethod("PUE");
//    cfdi.setCurrency(currency.getValue());
//    DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//    Date date = new Date();
//    cfdi.setDate(dateFormat.format(date));
//    Receiver  receiver = new Receiver();
//
//    receiver.setName("Entidad receptora");
//    receiver.setCfdiUse("P01");
//    receiver.setRfc("XAX010101000");    
//
//    cfdi.setReceiver(receiver);
//
//    List<Item> lstItems = new ArrayList<>();
//
//    
//    Item item = new Item();
//
//    item.setProductCode("84111506");
//    item.setUnitCode("E48");
//    item.setUnit("Unidad de servicio");
//    item.setDescription(" API folios adicionales");
//    item.setIdentificationNumber("23");
//    item.setQuantity(100.00);
//    item.setDiscount(10.00 );
//    item.setUnitPrice(0.50);
//    item.setSubtotal(50.00);
//
//    List<Tax> lstTaxes = new ArrayList<>();
//        
//    Tax tax = new Tax();
//    
//    tax.setName("IVA");
//    tax.setIsQuota(false);
//    tax.setIsRetention(false);
//    tax.setRate( 0.160000d );
//    tax.setBase( 40 );
//    tax.setTotal(6.4);
//    lstTaxes.add(tax);
//
//    item.setTaxes(lstTaxes);
//    item.setTotal(46.40);
//
//    lstItems.add(item);
//
//    Item item2 = new Item();
//
//    item2.setProductCode("84111506");
//    item2.setUnitCode("E48");
//    item2.setUnit("Unidad de servicio");
//    item2.setDescription(" API Implementación");
//    item2.setIdentificationNumber("21");
//    item2.setQuantity(1);
//    item2.setUnitPrice(6000.00);
//    item2.setSubtotal(6000.00);
//
//    List<Tax> lstTaxes2 = new ArrayList<>();
//    
//    Tax tax2 = new Tax();
//
//    tax2.setName("IVA");
//    tax2.setIsQuota(false);
//    tax2.setIsRetention(false);
//    tax2.setRate( 0.160000d );
//    tax2.setBase( 6000 );
//    tax2.setTotal(960);
//    lstTaxes2.add(tax2);
//
//    item2.setTaxes(lstTaxes2);
//    item2.setTotal(6960);
//
//    lstItems.add(item2);
//
//    cfdi.setItems(lstItems);
//
//    // Creación del CFDI mediante la API (Modelo con datos de respuesta)
//    com.Facturama.sdk_java.Models.Response.Cfdi cfdiCreated = facturama.Cfdis().Create(cfdi);
	
}
