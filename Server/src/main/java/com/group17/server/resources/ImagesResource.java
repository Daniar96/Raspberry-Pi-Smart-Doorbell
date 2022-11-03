package com.group17.server.resources;

import com.group17.JSONObjects.Image;
import com.group17.server.Database;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.List;

@Path("/images")
public class ImagesResource {
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getImages(){
        List<Image> images = Database.getImages();
        return Response.status(200).entity(images).build();
    }
}
