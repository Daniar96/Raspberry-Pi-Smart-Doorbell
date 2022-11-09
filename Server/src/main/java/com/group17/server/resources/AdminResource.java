package com.group17.server.resources;

import com.group17.JSONObjects.Rfid_Password;
import com.group17.JSONObjects.ServerError;
import com.group17.server.database.DAO;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.sql.SQLException;

@Path("/admin")
public class AdminResource {
    @Path("/hello")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String hello() {
        return "Hello, World!";
    }


    @Path("/rfid")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public Response setRFID(Rfid_Password rfid_password) throws SQLException {
        DAO.registerRpi(rfid_password.getRf_id(),rfid_password.getPassword());
        return Response.status(Response.Status.OK).build();
    }
    
}