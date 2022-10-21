package com.group17.server.resources;

import com.group17.JSONObjects.ServerError;
import com.group17.server.Database;
import com.group17.server.SecurityCheck;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;


import javax.xml.crypto.Data;
import java.sql.SQLException;


@Path("/test")
public class HelloResource {
    @Path("/hello")

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String hello() {
        return "Hello, World!";
    }


    @Path("/list")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response get_users_list() {
        try {
            String userlist = Database.getUserList();
            return Response.status(Response.Status.OK).entity(userlist).build();
        } catch (SQLException e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(new ServerError("SQL error")).build();
        }
    }

    @Path("/rfid")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public Response setRFID() {
        try {
            String rfid= "206970559604";
            Database.setRFID(rfid,"newuser@gmail.com");
            return Response.status(Response.Status.OK).build();
        } catch (SQLException e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(new ServerError("SQL error")).build();
        }


    }
}