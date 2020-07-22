package org.example;

import javax.json.Json;
import javax.json.JsonArray;
import java.util.jar.JarInputStream;

public class BaseResource {

    private static JsonArray data = null;


    protected JsonArray getData(){
        if(data == null){
            data = Json.createReader(JsonPatchResource.class.getResourceAsStream("/jsons.json")).readArray();
        }

        return data;
    }

    protected void setData(JsonArray data){
        this.data = data;
    }
}
