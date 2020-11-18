
package com.Facturama.sdk_java.Models.Exception;

public class FacturamaException extends Exception {
    private ModelException Model;
    
    
    public FacturamaException(String message) {
        super(message);             
    }
    
    public FacturamaException(String message, ModelException model) {
        super(message);     
        
        this.Model = model;
    }
    
    public ModelException getModel(){
        return this.Model;
    }
    
}

