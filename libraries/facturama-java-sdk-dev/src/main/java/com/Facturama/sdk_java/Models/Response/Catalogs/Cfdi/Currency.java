
package com.Facturama.sdk_java.Models.Response.Catalogs.Cfdi;
import com.Facturama.sdk_java.Models.Response.Catalogs.Catalog;

public class Currency extends Catalog
{    
   private double Decimals;
   private double PrecisionRate;
  
   public double getDecimals()
   {
        return Decimals;
   }
   
   public void setDecimals(double Decimals)
   {
      this.Decimals = Decimals;
   }
   
   public double getPrecisionRate()
   {
        return PrecisionRate;
   }
   
   public void setPrecisionRate(double PrecisionRate)
   {
      this.PrecisionRate = PrecisionRate;
   }
    
}
