
package com.Facturama.sdk_java.Models.Response;


public class CancelationStatus 
{
       
    private String Status;
    private String Message;
    private String RequestDate;
    private String AcuseXmlBase64;
    private String CancelationDate;    
    
    
   public String getStatus()
   {
        return Status;
   }
   
   public void setStatus(String Status)
   {
      this.Status = Status;
   }
   
   public String getMessage()
   {
        return Message;
   }
   
   public void setMessage(String Message)
   {
      this.Message = Message;
   }
   
   public String getRequestDate()
   {
        return RequestDate;
   }
   
   public void setSerie(String RequestDate)
   {
      this.RequestDate = RequestDate;
   }
   
   public String getAcuseXmlBase64()
   {
        return AcuseXmlBase64;
   }
   
   public void setAcuseXmlBase64(String AcuseXmlBase64)
   {
      this.AcuseXmlBase64 = AcuseXmlBase64;
   }
   
   public String getCancelationDate()
   {
        return CancelationDate;
   }
   
   public void setCancelationDate(String CancelationDate)
   {
      this.CancelationDate = CancelationDate;
   }
  
}
