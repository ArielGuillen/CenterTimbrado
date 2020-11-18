
package com.Facturama.sdk_java.Models.Response.Catalogs.Cfdi;
import com.Facturama.sdk_java.Models.Response.Catalogs.Catalog;


public class UseCfdi extends Catalog
{
    
   private boolean Natural;
   private boolean Moral;
    
   public boolean getNatural()
   {
        return Natural;
   }
   
   public void setNatural(boolean Natural)
   {
      this.Natural = Natural;
   }
   
   public boolean getMoral()
   {
        return Moral;
   }
   
   public void setMoral(boolean Moral)
   {
      this.Moral = Moral;
   }
    
}
