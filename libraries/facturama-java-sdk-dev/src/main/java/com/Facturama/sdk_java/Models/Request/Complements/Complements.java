
package com.Facturama.sdk_java.Models.Request.Complements;


import com.Facturama.sdk_java.Models.Request.Complements.ForeingTrade.ForeingTrade;
import com.Facturama.sdk_java.Models.Request.Payment;
import com.Facturama.sdk_java.Models.Request.Complements.Donat.Donat;
import com.Facturama.sdk_java.Models.Request.Complements.IEDU.EducationalInstitution;
import com.Facturama.sdk_java.Models.Request.Complements.Terceros.ThirdPartyAccount;
import java.util.List;


/**
 * Complementos del comprobante
 * 
 * - Complemento de pago
 * - Comercio exterior
 * - Donativos
 */
public class Complements {
    
   private List<Payment> Payments;
   private ForeingTrade ForeignTrade;
   private Donat Donation;

 
   
   
   /**
    * Complemento de pago, se pueden representar varios pagos en un solo CFDI
    * Referencia: https://apisandbox.facturama.mx/guias/api-web/cfdi/complemento-pago
    * @return 
    */
   public List<Payment> getPayments()
   {
        return Payments;
   }
   
   /**
    * Complemento de pago, se pueden representar varios pagos en un solo CFDI
    * Referencia: https://apisandbox.facturama.mx/guias/api-web/cfdi/complemento-pago
    * @return 
    */
   public void setPayments(List<Payment> payments)
   {
       this.Payments = payments;
   }
   
   /**
    * Complemento de comercio exterior
    * Referencia: https://apisandbox.facturama.mx/guias/api-web/cfdi/complemento-exterior
    * @return 
    */
   public ForeingTrade getForeingTrades()
   {
        return ForeignTrade;
   }
   
    /**
    * Complemento de comercio exterior
    * Referencia: https://apisandbox.facturama.mx/guias/api-web/cfdi/complemento-exterior
    * @return 
    */
   public void setForeingTrades(ForeingTrade foreingTrades)
   {
       this.ForeignTrade = foreingTrades;
   }
   
   /**
    * Complemento de donativos
    * Referencia: https://apisandbox.facturama.mx/guias/api-web/cfdi/complemento-donativo
    * @return 
    */
   public Donat getDonation()
   {
        return Donation;
   }
   
   /**
    * Complemento de donativos
    * Referencia: https://apisandbox.facturama.mx/guias/api-web/cfdi/complemento-donativo
    * @return 
    */
   public void setDonation(Donat Donation)
   {
       this.Donation = Donation;
   }  
       
}
