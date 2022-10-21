package com.group17.server.resources;

import com.group17.JSONObjects.SmokeAlert;
import com.group17.JSONObjects.TagID;
import com.group17.server.Database;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/RPI")
public class RPIresource {

    static int smoke = 0;

    @POST
    @Path("/smoke")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response smokeAlertRpi(SmokeAlert alert){
        if (alert.getAlert() == 1){
            smoke = 1;
        }else {
            smoke = 0;
        }
        return Response.status(200).build();
    }

    @GET
    @Path("smokeAlert")
    @Produces(MediaType.APPLICATION_JSON)
    public Response smokeAlertWeb(){
        return Response.status(200).entity(smoke).build();
    }

    @POST
    @Path("/authorize")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response checkTag(TagID id){
        Database database = new Database();
        if (Database.checkOnline(id.getTagID())){
            return Response.status(200).build();
        }else {
            return Response.status(401).build();
        }

    }
}
