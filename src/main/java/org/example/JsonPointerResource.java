package org.example;


import javax.json.Json;
import javax.json.JsonPointer;
import javax.json.JsonValue;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.awt.*;

@Path("/pointer")
public class JsonPointerResource extends BaseResource{

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public JsonValue doGet(@QueryParam("p") String p){
        JsonPointer pointer = Json.createPointer(p);
        return pointer.getValue(getData());
    }
}
