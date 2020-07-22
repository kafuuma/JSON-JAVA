package org.example;


import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonPatch;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

@Path("/patch")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class JsonPatchResource  extends BaseResource{

    @GET
    public JsonArray doGet(){
        return getData();
    }

    @POST
    public JsonArray doPost(JsonArray data){
        setData(data);
        return getData();
    }


    @PATCH()
    public JsonArray doPatch(JsonArray patch){
        JsonPatch p = Json.createPatch(patch);
        JsonArray data = getData();
        System.out.print("******************************");
        JsonArray jsons = p.apply(data);
        setData(jsons);
        return getData();
    }


}
