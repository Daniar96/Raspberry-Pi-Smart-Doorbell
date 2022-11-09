package com.group17.server.resources;

import com.group17.JSONObjects.Image;
import com.group17.JSONObjects.ServerError;
import com.group17.server.database.DAO;
import com.group17.server.database.Database;
import com.group17.server.SecurityCheck;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.sql.SQLException;
import java.util.List;

@Path("/images")
public class ImagesResource {

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getImages(){
        try {
            List<Image> images = DAO.getImages("101");
            return Response.status(200).entity(images).build();
        } catch (SQLException e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(new ServerError("SQL error")).build();
        }
    }
}
