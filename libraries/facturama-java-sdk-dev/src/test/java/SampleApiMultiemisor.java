
import com.Facturama.sdk_java.Container.FacturamaApiMultiemisor;
import com.Facturama.sdk_java.Models.Exception.FacturamaException;
import com.Facturama.sdk_java.Models.Csd;
import com.Facturama.sdk_java.Models.Request.CfdiType;
import com.Facturama.sdk_java.Models.Request.Complements.Complements;
import com.Facturama.sdk_java.Models.Request.Issuer;
import com.Facturama.sdk_java.Models.Request.Item;
import com.Facturama.sdk_java.Models.Request.Payment;
import com.Facturama.sdk_java.Models.Request.Receiver;
import com.Facturama.sdk_java.Models.Request.RelatedDocument;
import com.Facturama.sdk_java.Models.Request.Tax;
import com.Facturama.sdk_java.Models.Response.CancelationStatus;
import com.Facturama.sdk_java.Models.Response.Catalogs.Catalog;
import com.Facturama.sdk_java.Models.Response.Catalogs.Cfdi.Currency;
import com.Facturama.sdk_java.Models.Response.Catalogs.Cfdi.NameCfdi;
import com.Facturama.sdk_java.Models.Response.CfdiSearchResult;
import com.Facturama.sdk_java.Services.CfdiService;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;
import com.Facturama.sdk_java.Models.Response.Cfdi;


public class SampleApiMultiemisor {
    
       public static void principal() {        
        System.out.println( "Ejemplos de consumo de la FacturamaAPI Multiemisor, con el usuario de 'pruebas'" );
            
       try {     

            FacturamaApiMultiemisor facturama = createApiInstance();           
        

            // Ejemplo de administración de CSDs (descomenta la linea para incluirlo en la ejecución)
            //sampleCsd(facturama);
            
            // Ejemplo de creación de CFDI 
            sampleCfdi(facturama);
            
            // Ejemplo de creación de "Complemento de Pago"
            //samplePaymentComplement(facturama);
            
            
        } catch (FacturamaException ex) {                        
            // Se muestran los errores
            System.out.println( ex.getMessage() );
                        
            Map<String, String[]> messageDetail = ex.getModel().getDetails();
            messageDetail.entrySet().forEach((entry) -> {
                System.out.println(entry.getKey() + ": " +  String.join(",", entry.getValue() ));
            });                                   
            
        } catch (Exception ex) {
            System.out.println( "Error inesperado: " + ex.getMessage() );
        }
        
    }
    
    /**
     * Creación del Objeto Facturama Multiemisor
     * @return Objeto creado, listo para hacer las peticiones a la API
     */
    private static FacturamaApiMultiemisor createApiInstance(){
        String user = "pruebas";
        String password = "pruebas2011";
        Boolean isDevMode = true;       // true  = Sandbox, false = Productivo
        
        return new FacturamaApiMultiemisor(user, password, isDevMode);
    }
    
    /**
     * Ejemplo de manejo de los Sellos Digitales CSD
     * - Eliminar
     * - Agregar
     * - Listar
     * - Mostrar CSD específico
     * 
     * @param facturama Objeto Facturama Multiemisor
     * @throws IOException
     * @throws FacturamaException
     * @throws Exception 
     */
    private static void sampleCsd( FacturamaApiMultiemisor facturama) throws IOException, FacturamaException, Exception{  
        
        System.out.println( "----- Inicio del ejemplo de CSD -----" );
        
        
        
       
        System.out.println( "Eliminando el Certificado para el RFC AAA010101AAA" );
        

        facturama.Csd().Remove("AAA010101AAA");
        
        
        System.out.println( "Agregando el Certificado para el RFC AAA010101AAA" );
        sampleCsdCreate(facturama);                       
        
        
        
        // Listado de todos los Certificados que tiene cargados el usuario       
        System.out.println( "Listado de todos los Certificados que tiene cargados el usuario" );
        List<Csd> lstCsd = facturama.Csd().List();         
       for(int i=0;i <lstCsd.size();i++)
       {
            Csd csdlist = lstCsd.get(i);
            System.out.println();
            System.out.println(csdlist.getCertificate());
            System.out.println(csdlist.getPrivateKey());
            System.out.println(csdlist.getPrivateKeyPassword());
            System.out.println(csdlist.getRfc());                   
       }
 
       // Mostrado del certificado especìfico para el RFC AAA010101AAA
       System.out.println( "Mostrado del certificado especìfico para el RFC AAA010101AAA" );
        System.out.println();
        System.out.println(facturama.Csd().Retrieve("AAA010101AAA").getCertificate());
        System.out.println(facturama.Csd().Retrieve("AAA010101AAA").getPrivateKey());
        System.out.println(facturama.Csd().Retrieve("AAA010101AAA").getPrivateKeyPassword());
        System.out.println(facturama.Csd().Retrieve("AAA010101AAA").getRfc());
        
        System.out.println();
        System.out.println("ejemplo de CSD terminado");
    
    }
     private static Csd sampleCsdCreate( FacturamaApiMultiemisor facturama) throws IOException, FacturamaException, Exception{  
        Csd newCsd = new Csd();
        newCsd.setCertificate("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");
        newCsd.setPrivateKey("MIIFDjBABgkqhkiG9w0BBQ0wMzAbBgkqhkiG9w0BBQwwDgQIAgEAAoIBAQACAggAMBQGCCqGSIb3DQMHBAgwggS9AgEAMASCBMh4EHl7aNSCaMDA1VlRoXCZ5UUmqErAbucRBAKNQXH8tz2zJ7hdZaOZx7PEfMiWh5Nh6e8G8kxY+GW4YCSbLxslkhBtfTR6v5JYv3vhgH7XzMCwJPOfX6gxeeCYZ4HTdDNAyBVCjTbJpqbo778ri33o+I4yx7zgMqA3mzVE61re6MPrGXh1YT/K9zZeEdmwvXQfPs9VnioKUhiswoMcJ3kc3FxGLrEAsjQqv/ZVOHPY3NrbcfpQUyprsCKv3rRdxkIRdMPY4eiA720mffzvDqyzeQ8xfwHTE8Xjunja4KXvW/mV7ItTH0vRXHc3HJQ0dNnyawXmbC1FiYbCVdswoYuVQmslvq3QEXUGwP3KYfxQzKatnU7nprkmsipPqPBqDrzqc6NSN/8rxIc5zTAL4bFul+CEKz9VybwdavgewEy7u3fPnKPN+y4HilNgmlbtS7seWpbIgVPA+woG2Ph5hsgREXZCjGKSRuI77/FLcI5CMrZR+FvbnaqG+gXDBTz2lWhK9pmWlVawT2pvfiHOLzYRf2YyuVbJ79D2EgbUKyp3kCQ6fddMzspPhD/pvLQizExeyIxImb/kQXs2mmtDnyFIsj4Hcn5wCcs+SDIj+FJnwRiKB6YfdzjIig/ZMfpgMpl0u69LX649uL318o+Hy3d5t3wxgSkTaJ5McKhWyh9x9vlHZhYyM6HArBNfP9cGF86M3GwAMHAiJQl9UevyKe6rlvAIDlop6l3M02m5hHUXUpPjz4j7inFXZzvSv0tFoSbEqGgno0Pa+0gWHqRwBEGLGEwHVfyEy+Of8g4+0jzo0jNPIcurA5xRh9HSRSAd3kdEhx75eeVL7lBdLjRUkbtRtg7nelSjqAX7tQZK6Awp5C/17W96+f/vtjB+Y+ZgrSUjnQDADnZCnapIrzHgE3ZanhGAtnMMl+o4aLd1+74inG4jht/GJB60raSQfYrDrM3kBs0oyfpbEk5TI8ISzRlRmejv+mqpTogJaAqhnLP7rAli3d4pRhUjbACn/xQSFKxl2OURdmnMlvlbb6pleXviJHRxzPPQ25NVdWvmCYWrDfAZYn8X1sABOdyrth38BfmAVsyyPATYFB+5cXuNIZkPz1swz3859iZWTn5JRfPEAGICu5G6w6nrgOLYM9UqOPmxofzEdiEPafLQ5orMxdSWF6+3mD2Yw/VP+B43B/oYehgfrYjBUJt2D04VU/v8XK1ZUVgX/Co0odcdcszAP+ljQ7UVhW+uxVMd2sEprwepPPjYT3HvdI6RBB94yYBWfkoCSo/jsrrRpw2DVEyvoDp/hOXKyt8Y/8UGLCxJUhhv5fEiezYnlUAmwAGjgZfzfAErx0gkQFBgNKglEA7jz0Dqc2Z92pGVGTyPtXqRsqX3IYX5WsZVUoJim0wI7+LNmKpu147ePC0G4Sf4AGoZyPWVXq2SZSPpN261pIKSoLEDeA8WIKj2U5JG2DMMYokV0bZ1TsabrwHvwsp3muLnaP8L+n2fBplbhAEE2buBXvsATixMGu57ZI5WKFLnHn4KIBrZzALCtGehfFbCsdf1nBR6aAt+BpWhhZki54fZTurgMr6zuC5hAaP4rExW+LCc3upHMW7R9DcHWaZuZIfwnVDImnAQ9UOsz+A=");
        newCsd.setPrivateKeyPassword("12345678a");
        newCsd.setRfc("AAA010101AAA");
        return facturama.Csd().Create(newCsd);
    
    }   
     private static void sampleCfdi( FacturamaApiMultiemisor facturama) throws IOException, FacturamaException, Exception{  
        
        System.out.println( "----- Inicio del ejemplo de CFDI -----" );      
        
        // Se obtiene la moneda con el valor "MXN"
        List<Currency> lstCurrencies = facturama.Catalogs().Currencies();                
        Currency currency = lstCurrencies.stream().
        filter(p -> p.getValue().equals("MXN")).findFirst().get();
        
        
        // -------- Creacion del cfdi en su forma general (sin items / productos) asociados --------
        com.Facturama.sdk_java.Models.Request.CfdiLite cfdi = createCfdi(facturama, currency);
                
        // -------- Agregar los items que lleva el cfdi ( para este ejemplo, se agregan con datos aleatorios) --------        
        cfdi = addItemsToCfdi(facturama, cfdi);
        
        
        // Se obtiene la factura recien creada
        com.Facturama.sdk_java.Models.Response.Cfdi cfdiCreated = facturama.Cfdis().Create(cfdi);

        System.out.println( "Se creó exitosamente el cfdi con el folio fiscal: " +  cfdiCreated.getComplement().getTaxStamp().getUuid() );
        
        // Descarga de los archivos de la factura
        String filePath = "factura"+cfdiCreated.getComplement().getTaxStamp().getUuid();
        facturama.Cfdis().SaveXml(filePath+".xml", cfdiCreated.getId());
        
        
        // Se elmina la factura recien creada
         CancelationStatus response = facturama.Cfdis().Remove(cfdiCreated.getId());        
        
        System.out.println(response.getStatus());
        
        String strCanceled = "canceled" ;
        String strPending = "canceled" ;
        if( strCanceled.equals(response.getStatus())){
            System.out.println( "Se ha cancelado exitosamente el cfdi con el folio fiscal: " +  cfdiCreated.getComplement().getTaxStamp().getUuid() );
        }else if(strPending.equals(response.getStatus())){
            System.out.println( "La factura está en proceso de cancelación, pueden pasar hasta 72 horas para que se considere cancelada." );
        }
        else{
            System.out.println( "Algo ha pasado, que el CFDI no se ha podido cancelar. Revisa el mensaje: " + response.getMessage() );
        }
        
        
        //El correo que se ingrese debe existir 
        
        // Consulta de cfdis mediante palabra clave o rfc
        System.out.println( "Consulta de RFCs mediante RFC" );  
        
        //List<CfdiSearchResult> lstCfdiFilteredByKeyword = facturama.Cfdis().List("Expresion en Software");
        List<CfdiSearchResult> lstCfdiFilteredByRfc = facturama.Cfdis().ListFilterByRfc("ESO1202108R2");                

        //System.out.println("Se obtiene la lista de facturas: " + lstCfdiFilteredByKeyword.size());
        System.out.println("Se obtiene la lista de facturas por RFC: " + lstCfdiFilteredByRfc.size());
        
        System.out.println( "----- Fin del ejemplo de CFDI -----" );
        
    } 
    private static com.Facturama.sdk_java.Models.Request.CfdiLite createCfdi(FacturamaApiMultiemisor facturama, Currency currency) throws IOException, FacturamaException, Exception{
        

        com.Facturama.sdk_java.Models.Request.CfdiLite cfdi = new com.Facturama.sdk_java.Models.Request.CfdiLite();
          
                
            // Método de pago       
            Catalog paymentMethod = facturama.Catalogs().PaymentMethods().stream().
            filter(p -> p.getName().equals("Pago en una sola exhibición")).findFirst().get();                

            // Forma de pago
            Catalog paymentForm = facturama.Catalogs().PaymentForms().stream().
            filter(p -> p.getName().equals("Efectivo")).findFirst().get();                


            // Cliente (se toma como cliente el "cliente generico", aquel que tiene el RFC genérico),
            // (como los clientes son exclusivos para cada usuario, se debe previamente dar de alta este cliente)
                

            // Lugar de expedición
            cfdi.setFolio("10");
            cfdi.setNameId(facturama.Catalogs().NameIds().get(1).getValue());
            cfdi.setCfdiType( CfdiType.Ingreso.getValue() );        
            cfdi.setPaymentForm( paymentForm.getValue() );
            cfdi.setPaymentMethod( paymentMethod.getValue() );
            cfdi.setCurrency(currency.getValue());
            
            // logo - Se especifica como una URL
            cfdi.setLogoUrl("https://www.ejemplos.co/wp-content/uploads/2015/11/Logo-Chanel.jpg");            
            
            
            // Datos no fiscales (se muestran en el PDF)
            cfdi.setObservations("Este es un ejemplo de observaciones");
            cfdi.setOrderNumber("123321");
            cfdi.setPaymentBankName("BBVA");
            cfdi.setPaymentAccountNumber("6789");
            cfdi.setPaymentConditions("Condiciones");

         
            DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date date = new Date();        
            cfdi.setDate(dateFormat.format(date));            
            cfdi.setExpeditionPlace("78180");
            
            Receiver  receiver = new Receiver();
            receiver.setCfdiUse(facturama.Catalogs().CfdiUses("AAA010101AAA").get(0).getValue());
            receiver.setName("Receptor de Ejemplo");
            receiver.setRfc("ESO1202108R2");
            
            Issuer issuer = new Issuer();
            issuer.setFiscalRegime(facturama.Catalogs().FiscalRegimens().get(0).getValue());
            issuer.setName("Emisor de Ejemplo");
            issuer.setRfc("AAA010101AAA");
            cfdi.setIssuer(issuer);
            cfdi.setReceiver(receiver);           
       
        return cfdi;
                
    }
     private static com.Facturama.sdk_java.Models.Request.CfdiLite addItemsToCfdi(FacturamaApiMultiemisor facturama,
       com.Facturama.sdk_java.Models.Request.CfdiLite cfdi) throws IOException, FacturamaException, Exception{
               
        Double price = 100.00;
        Double quantity = 2.00;
        Double discount = 10.00;
        Currency currency = facturama.Catalogs().Currency("MXN");
        int decimals = (int) currency.getDecimals();
        Double numberOfDecimals = Math.pow(10, decimals);
      
        Double subtotal =   Math.round( (price * quantity) * numberOfDecimals) / numberOfDecimals;
        List<Item> lstItems = new ArrayList<>();
        Item item = new Item();
            item.setProductCode(facturama.Catalogs().ProductsOrServices("desarrollo").get(0).getValue());
            item.setUnitCode(facturama.Catalogs().Units("pieza").get(1).getValue());
            item.setUnit("Libra");
            item.setDescription("Descripción del Producto");
            item.setIdentificationNumber("010101-56");
            item.setQuantity(quantity);
            item.setDiscount( Math.round( discount * numberOfDecimals) / numberOfDecimals );
            item.setUnitPrice(Math.round( price* numberOfDecimals) / numberOfDecimals);
            item.setSubtotal(subtotal);
            lstItems.add(item);
            
        item = addTaxesToItem(item, numberOfDecimals);
               

        
        cfdi.setItems(lstItems);
        
        return cfdi;
    }
    private static Item addTaxesToItem(Item item, Double numberOfDecimals){
        

            List<Tax> lstTaxes = new ArrayList<>();              // Impuestos del item (del cfdi)
                                      
            Tax tax = new Tax();

            tax.setName("IVA");
            tax.setIsQuota(false);
            tax.setIsRetention(false);

            tax.setRate( 0.160000d );
            tax.setBase( Math.round(item.getSubtotal()  * numberOfDecimals) / numberOfDecimals );
            tax.setTotal( Math.round( (/*cambie el baseAmount*/tax.getBase() * tax.getRate()) * numberOfDecimals) / numberOfDecimals );                

            lstTaxes.add(tax);
                       
            Double retentionsAmount = 0D;
            Double transfersAmount = 0D;
            
            // Asignación de los impuestos, en caso de que no se tengan, el campo va nulo
            if(!lstTaxes.isEmpty()){
                item.setTaxes(lstTaxes);
                
                retentionsAmount = item.getTaxes().stream().filter(o -> o.getIsRetention()).mapToDouble(o -> o.getTotal()).sum();
                transfersAmount = item.getTaxes().stream().filter(o -> ! o.getIsRetention()).mapToDouble(o -> o.getTotal()).sum();
            }                        
            
            // Calculo del subtotal
            item.setTotal(item.getSubtotal() - item.getDiscount() + transfersAmount -  retentionsAmount);
            
            return item;
            
    } 
    
     /**
     * Ejemplo de creación de un CFDI "complemento de pago"
     * Referencia: https://apisandbox.facturama.mx/guias/api-web/cfdi/complemento-pago
     * 
     * En virtud de que el complemento de pago, requiere ser asociado a un CFDI con el campo "PaymentMethod" = "PPD"
     * En este ejemplo se incluye la creacón de este CFDI, para posteriormente realizar el  "Complemento de pago" = "PUE"     
    */
    private static void samplePaymentComplement( FacturamaApiMultiemisor facturama) throws IOException, FacturamaException, Exception{  
        
        System.out.println( "----- Inicio del ejemplo samplePaymentComplement -----" );      
        
        System.out.println( "Creación del CFDI Inicial (PPD)" );      
        // Cfdi Incial (debe ser "PPD")
        // -------- Creacion del cfdi en su forma general (sin items / productos) asociados --------
        com.Facturama.sdk_java.Models.Request.CfdiLite cfdi = createModelCfdiGeneral(facturama);
                
        // -------- Agregar los items que lleva el cfdi ( para este ejemplo, se agregan con datos aleatorios) --------        
        cfdi = addItemsToCfdi(facturama, cfdi);
        cfdi.setFolio("11");
        cfdi.setPaymentMethod("PPD");                   // El método de pago del documento inicial debe ser "PPD"
        
        // logo - Se especifica como una URL
        cfdi.setLogoUrl("https://www.ejemplos.co/wp-content/uploads/2015/11/Logo-Chanel.jpg");            
        
                
        // Se manda timbrar mediante Facturama
        com.Facturama.sdk_java.Models.Response.Cfdi cfdiInicial = facturama.Cfdis().Create(cfdi);

        System.out.println( "Se creó exitosamente el cfdi Inicial (PPD) con el folio fiscal: " +  cfdiInicial.getComplement().getTaxStamp().getUuid() );
        
        // Descarga de los archivos del documento inicial
        String filePath = "factura"+cfdiInicial.getComplement().getTaxStamp().getUuid();
        facturama.Cfdis().SavePdf(filePath+".pdf", cfdiInicial.getId());
        facturama.Cfdis().SaveXml(filePath+".xml", cfdiInicial.getId());
        
        
        
        // Complemento de pago (debe ser "PUE")        
        // Y no lleva "Items" solo especifica el "Complemento"
        
        System.out.println( "Creación del complemento de Pago " );      
        
        com.Facturama.sdk_java.Models.Request.CfdiLite paymentComplementModel = createModelCfdiPaymentComplement(facturama, cfdiInicial);
        
        
        // Se manda timbrar el complemento de pago mediante Facturama
        com.Facturama.sdk_java.Models.Response.Cfdi paymentComplement = facturama.Cfdis().Create(paymentComplementModel);
        
        System.out.println( "Se creó exitosamente el complemento de pago con el folio fiscal: " +  paymentComplement.getComplement().getTaxStamp().getUuid() );
        
        
        // Descarga de los archivos del documento inicial
        String filePathPayment = "factura"+paymentComplement.getComplement().getTaxStamp().getUuid();
        facturama.Cfdis().SavePdf(filePath+".pdf", paymentComplement.getId());
        facturama.Cfdis().SaveXml(filePath+".xml", paymentComplement.getId());
        
                
        
        // Posibilidad de mandar  los cfdis por coreo ( el cfdiInical y complemento de pago)
        System.out.println(facturama.Cfdis().SendEmail("chucho@facturama.mx",com.Facturama.sdk_java.Services.Multiemisor.CfdiService.InvoiceType.IssuedLite, cfdiInicial.getId()));
        System.out.println(facturama.Cfdis().SendEmail("chucho@facturama.mx",com.Facturama.sdk_java.Services.Multiemisor.CfdiService.InvoiceType.IssuedLite, paymentComplement.getId()));
                
        
        System.out.println( "----- Fin del ejemplo de samplePaymentComplement -----" );
        
    }
    
    
    /**
     * Llenado del modelo de CFDI, de una forma general
     * - Se especifica: la moneda, método de pago, forma de pago, cliente, y lugar de expedición     
     */    
    private static com.Facturama.sdk_java.Models.Request.CfdiLite createModelCfdiGeneral(FacturamaApiMultiemisor facturama) throws IOException, FacturamaException, Exception{
        
        System.out.println( "createModelCfdiGeneral" );
        
        Currency currency = facturama.Catalogs().Currency("MXN");
        
        com.Facturama.sdk_java.Models.Request.CfdiLite cfdi = new com.Facturama.sdk_java.Models.Request.CfdiLite();
            // Lista del catálogo de nombres en el PDF
            NameCfdi nameForPdf = facturama.Catalogs().NameIds().get(0); // Nombre en el pdf: "Factura"
                
            // Método de pago                             
            Catalog paymentMethod = facturama.Catalogs().PaymentMethod("PUE");

            // Forma de pago
            Catalog paymentForm = facturama.Catalogs().PaymentForms().stream().
            filter(p -> p.getName().equals("Efectivo")).findFirst().get();                
                    

            cfdi.setNameId(nameForPdf.getValue());
            cfdi.setCfdiType( CfdiType.Ingreso.getValue() );        
            cfdi.setPaymentForm( paymentForm.getValue() );
            cfdi.setPaymentMethod( paymentMethod.getValue() );
            cfdi.setCurrency(currency.getValue());            
            cfdi.setExpeditionPlace("78180");

            DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date date = new Date();        
            cfdi.setDate(dateFormat.format(date));            

           Receiver  receiver = new Receiver();
            receiver.setCfdiUse(facturama.Catalogs().CfdiUses("AAA010101AAA").get(0).getValue());
            receiver.setName("Receptor de Ejemplo");
            receiver.setRfc("ESO1202108R2");
            
            cfdi.setReceiver(receiver);           
            
            Issuer issuer = new Issuer();
            issuer.setFiscalRegime(facturama.Catalogs().FiscalRegimens().get(0).getValue());
            issuer.setName("Emisor de Ejemplo");
            issuer.setRfc("AAA010101AAA");
            cfdi.setIssuer(issuer);
            
        return cfdi;
                
    }
    
    
    
     /**
     * Modelo "Complemento de pago"
     * - Se especifica: la moneda, método de pago, forma de pago, cliente, y lugar de expedición     
     */    
    private static com.Facturama.sdk_java.Models.Request.CfdiLite 
        createModelCfdiPaymentComplement(FacturamaApiMultiemisor facturama,com.Facturama.sdk_java.Models.Response.Cfdi cfdiInicial ) 
                throws IOException, FacturamaException, Exception{
            
            
            System.out.println( "createModelCfdiPaymentComplement" );
                       
           com.Facturama.sdk_java.Models.Request.CfdiLite cfdi = new com.Facturama.sdk_java.Models.Request.CfdiLite();
           
            // Lista del catálogo de nombres en el PDF
            NameCfdi nameForPdf = facturama.Catalogs().NameIds().get(13); // Nombre en el pdf: "Complemento de pago"
                        
            // Forma de pago
            Catalog paymentForm = facturama.Catalogs().PaymentForms().stream().
            filter(p -> p.getName().equals("Efectivo")).findFirst().get();                
            
            cfdi.setNameId(nameForPdf.getValue());            
            cfdi.setCfdiType( CfdiType.Pago.getValue() ); // "P"  (El comprobante es del tipo "Pago")         
            cfdi.setFolio("12");            
            
            // logo - Se especifica como una URL
            cfdi.setLogoUrl("https://www.ejemplos.co/wp-content/uploads/2015/11/Logo-Chanel.jpg");            

            
            
            Receiver  receiver = new Receiver();
            receiver.setCfdiUse(facturama.Catalogs().CfdiUses("AAA010101AAA").get(0).getValue());
            receiver.setName("Receptor de Ejemplo");
            receiver.setRfc("ESO1202108R2");            
            receiver.setCfdiUse("P01");
            cfdi.setReceiver(receiver);                  
            
            Issuer issuer = new Issuer();
            issuer.setFiscalRegime(facturama.Catalogs().FiscalRegimens().get(0).getValue());
            issuer.setName("Emisor de Ejemplo");
            issuer.setRfc("AAA010101AAA");
            cfdi.setIssuer(issuer);
            
            cfdi.setExpeditionPlace("78180");

            // Fecha y hora de expecidión del comprobante
            DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date cfdiDate = new Date();        
            cfdi.setDate(dateFormat.format(cfdiDate));                                
            
            // Complemento de pago ---
            Complements complement = new Complements();
            
            // Pueden representarse más de un pago en un solo CFDI
            List<Payment> lstPagos = new ArrayList(); 
            Payment pago = new Payment();
            
            // Fecha y hora en que se registró el pago en el formato: "yyyy-MM-ddTHH:mm:ss" 
            // (la fecha del pago debe ser menor que la fecha en que se emite el CFDI)
            // Para este ejemplo, se considera que  el pago se realizó hace una hora            
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(cfdiDate);
            calendar.add(Calendar.HOUR_OF_DAY, -1);
            pago.setDate(dateFormat.format(calendar.getTime()));
            
            
            // Selección de la moneda del catálogo
            // La Moneda, puede ser diferente a la del documento inicial
            // (En el caso de que sea diferente, se debe colocar el tipo de cambio)
            List<Currency> lstCurrencies = facturama.Catalogs().Currencies();                
            Currency currency = lstCurrencies.stream().
            filter(p -> p.getValue().equals("MXN")).findFirst().get();                                                
            pago.setCurrency(currency.getValue());              // Moneda en que se realiza el pago
            
            // Monto del pago
            // Este monto se puede distribuir entre los documentos relacionados al pago            
            pago.setAmount(100.00);
            pago.setPaymentForm(paymentForm.getValue());
            
            // Documentos relacionados con el pago
            // En este ejemplo, los datos se obtiene el cfdiInicial, pero puedes colocar solo los datos
            // aun sin tener el "Objeto" del cfdi Inicial, ya que los valores son del tipo "String"
            List<RelatedDocument> lstRelatedDocuments = new ArrayList();
            RelatedDocument relatedDocument = new RelatedDocument();
            relatedDocument.setUuid(cfdiInicial.getComplement().getTaxStamp().getUuid()); // "27568D31-E579-442F-BA77-798CBF30BD7D"
            relatedDocument.setSerie(cfdiInicial.getSerie()); // "EA"
            relatedDocument.setFolio(cfdiInicial.getFolio()); // 34853
            relatedDocument.setCurrency(currency.getValue());
            relatedDocument.setPaymentMethod("PPD");            // Metodo de pago del CFDI Inicial
            relatedDocument.setPartialityNumber(1);
            relatedDocument.setPreviousBalanceAmount(100.00);
            relatedDocument.setAmountPaid(100.00);
            relatedDocument.setImpSaldoInsoluto(0.00);
            
            lstRelatedDocuments.add(relatedDocument);
            
            pago.setRelatedDocument(lstRelatedDocuments);
            
            lstPagos.add(pago);
            
            complement.setPayments(lstPagos);
            
            cfdi.setComplements(complement);
            
       
        return cfdi;
                
    }
}
