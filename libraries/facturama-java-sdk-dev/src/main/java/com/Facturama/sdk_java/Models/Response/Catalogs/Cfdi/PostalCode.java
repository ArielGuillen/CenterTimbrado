
package com.Facturama.sdk_java.Models.Response.Catalogs.Cfdi;
import com.Facturama.sdk_java.Models.Response.Catalogs.Catalog;

public class PostalCode extends Catalog
{
    private String StateCode;
    private String MunicipalityCode;
    private String LocationCode;
    
   public String getStateCode()
   {
        return StateCode;
   }
   
   public void setStateCode(String StateCode)
   {
      this.StateCode = StateCode;
   }
   
   public String getMunicipalityCode()
   {
        return MunicipalityCode;
   }
   
   public void setMunicipalityCode(String MunicipalityCode)
   {
      this.MunicipalityCode = MunicipalityCode;
   }
   
   public String getLocationCode()
   {
        return LocationCode;
   }
   
   public void setLocationCode(String LocationCode)
   {
      this.LocationCode = LocationCode;
   }
    
}
