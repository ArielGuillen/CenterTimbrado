
package com.Facturama.sdk_java.Services;

import com.google.gson.JsonElement;
import com.Facturama.sdk_java.Models.Client;
import com.squareup.okhttp.OkHttpClient;
import java.lang.reflect.Type;
import com.google.gson.reflect.TypeToken;
import java.util.List;

public class ClientService extends CrudService<Client, Client> {
    
    public ClientService(OkHttpClient client) {
        super(client, "client");
        
        singleType = Client.class;
        multiType = new TypeToken<List<Client>>() {}.getType();        
                
    }
    
    
   
}
