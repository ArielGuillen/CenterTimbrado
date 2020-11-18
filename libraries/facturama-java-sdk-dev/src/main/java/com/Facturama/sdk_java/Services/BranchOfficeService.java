
package com.Facturama.sdk_java.Services;

import com.google.gson.JsonElement;
import com.Facturama.sdk_java.Models.BranchOffice;
import com.squareup.okhttp.OkHttpClient;
import java.lang.reflect.Type;
import com.google.gson.reflect.TypeToken;
import java.util.List;

public class BranchOfficeService extends CrudService <BranchOffice, BranchOffice>{
    
    public BranchOfficeService(OkHttpClient client) {
        super(client, "BranchOffice");
        
        singleType = BranchOffice.class;
        multiType = new TypeToken<List<BranchOffice>>() {}.getType();        
                
    }
}
