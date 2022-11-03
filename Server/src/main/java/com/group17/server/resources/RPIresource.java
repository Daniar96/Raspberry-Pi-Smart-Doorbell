package com.group17.server.resources;

import com.group17.JSONObjects.*;
import com.group17.server.Database;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/RPI")
public class RPIresource {

    @POST
    @Path("/smoke")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response smokeAlertRpi(SmokeAlert alert){
        boolean smoke;
        if (alert.getAlert() == 1){
            smoke = true;
        }else {
            smoke = false;
        }
        Database.smokeAlert(smoke);
        return Response.status(200).build();
    }

    @GET
    @Path("/smokeAlert")
    @Produces(MediaType.APPLICATION_JSON)
    public Response smokeAlertWeb(){
        return Response.status(200).entity(Database.getSmoke()).build();
    }

    @POST
    @Path("/flame")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response flameAlertRpi(FlameAlert alert){
        boolean flame;
        if (alert.getAlert() == 1){
            flame = true;
        }else {
            flame = false;
        }
        Database.flameAlert(flame);
        return Response.status(200).build();
    }

    @GET
    @Path("/flameAlert")
    @Produces(MediaType.APPLICATION_JSON)
    public Response flameAlertWeb(){
        return Response.status(200).entity(Database.getFlame()).build();
    }

    @POST
    @Path("/mic")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response smokeAlertRpi(MicAlert alert){
        boolean mic;
        if (alert.getAlert() == 1){
            mic = true;
        }else {
            mic = false;
        }
        Database.micAlert(mic);
        return Response.status(200).build();
    }

    @GET
    @Path("/micAlert")
    @Produces(MediaType.APPLICATION_JSON)
    public Response micAlertWeb(){
        return Response.status(200).entity(Database.getMic()).build();
    }

    @POST
    @Path("/authorize")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response checkTag(TagID id){
        if (Database.checkOnline(id.getTagID())){
            return Response.status(200).build();
        }else {
            return Response.status(401).build();
        }

    }

    @POST
    @Path("/image")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response getImage(Image image){
        Database.insertImage(image.getName(), image.getEncode());
       return Response.status(200).build();
    }


}
