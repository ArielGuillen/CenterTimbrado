
package com.Facturama.sdk_java.Models.Request.Complements;

import com.Facturama.sdk_java.Models.Request.Complements.IEDU.EducationalInstitution;
import com.Facturama.sdk_java.Models.Request.Complements.Terceros.ThirdPartyAccount;

public class ItemComplement {
    
   private EducationalInstitution EducationalInstitution; 
   private ThirdPartyAccount ThirdPartyAccount; 
   
   public EducationalInstitution getEducationalInstitution()
   {
        return EducationalInstitution;
   }
   
   public void setEducationalInstitution(EducationalInstitution EducationalInstitution)
   {
       this.EducationalInstitution = EducationalInstitution;
   }  
   
   public ThirdPartyAccount getThirdPartyAccount()
   {
        return ThirdPartyAccount;
   }
   
   public void setThirdPartyAccount(ThirdPartyAccount ThirdPartyAccount)
   {
       this.ThirdPartyAccount = ThirdPartyAccount;
   } 
}
