package org.example;


import org.example.model.Jason;


import javax.json.bind.Jsonb;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.ArrayList;
import java.util.List;

@Path("/jasons")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class JasonResource {
    private  static List<Jason> jsons = new ArrayList<>();

    @GET
    @Path("/all")
    public List<Jason> doList(){
        return jsons;
    }

    public Jason doGet(@QueryParam("id") int id){
        for(Jason jason: jsons){
            if(jason.getId() == id){
                return jason;
            }
        }
        return null;
    }

}
