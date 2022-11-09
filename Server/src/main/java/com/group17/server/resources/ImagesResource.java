package com.group17.server.resources;

import com.group17.JSONObjects.Image;
import com.group17.JSONObjects.ServerError;
import com.group17.server.TokenCheck;
import com.group17.server.TokenList;
import com.group17.server.database.DAO;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.glassfish.jersey.server.ContainerRequest;

import java.sql.SQLException;
import java.util.List;

@Path("/images")
public class ImagesResource {

    @Context
    private ContainerRequest request;

    @TokenCheck
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getImages(){
        try {
            List<Image> images = DAO.getImages(getRpiIDFromUser());
            return Response.status(200).entity(images).build();
        } catch (SQLException e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(new ServerError("SQL error")).build();
        }
    }

    @Path("/last")
    @TokenCheck
    @GET
    public Response getImage(){
        try {
            return Response.status(200).entity(DAO.getImage(getRpiIDFromUser())).build();
        } catch (SQLException e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(new ServerError("SQL error")).build();
        }
    }

    private String getRpiIDFromUser() throws SQLException {
        String email = TokenList.getUser(request.getRequestCookies().get("SESSION_ID").getValue());
        return  DAO.getRpiFromEmail(email);
    }
}
