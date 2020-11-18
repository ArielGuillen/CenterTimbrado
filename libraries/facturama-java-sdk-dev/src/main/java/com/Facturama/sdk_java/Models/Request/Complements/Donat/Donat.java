
package com.Facturama.sdk_java.Models.Request.Complements.Donat;

public class Donat {

    private String AuthorizationNumber;
    private String AuthorizationDate;
    private String Legend;
    
    public String getAuthorizationNumber()
    {
        return AuthorizationNumber;
    }
    
    public void setAuthorizationNumber(String AuthorizationNumber)
    {
        this.AuthorizationNumber = AuthorizationNumber;
    } 
        
    public String getAuthorizationDate()
    {
        return AuthorizationDate;
    }
    
    public void setAuthorizationDate(String AuthorizationDate)
    {
        this.AuthorizationDate = AuthorizationDate;
    }
         
    public String getLegend()
    {
        return Legend;
    }
    
    public void setLegend(String Legend)
    {
        this.Legend = Legend;
    } 
    
}
