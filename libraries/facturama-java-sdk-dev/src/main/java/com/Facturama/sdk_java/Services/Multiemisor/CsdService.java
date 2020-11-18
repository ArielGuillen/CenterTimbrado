
package com.Facturama.sdk_java.Services.Multiemisor;

import com.Facturama.sdk_java.Services.CrudService;
import com.google.gson.JsonElement;
import com.Facturama.sdk_java.Models.Csd;
import com.squareup.okhttp.OkHttpClient;
import java.lang.reflect.Type;
import com.google.gson.reflect.TypeToken;
import java.util.List;

public class CsdService extends CrudService <Csd, Csd>{
    
    public CsdService(OkHttpClient client) {
        super(client, "api-lite/csds");
        
        singleType = Csd.class;
        multiType = new TypeToken<List<Csd>>() {}.getType();        
                
    }

}
