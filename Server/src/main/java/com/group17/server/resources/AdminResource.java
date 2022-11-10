package com.group17.server.resources;

import com.group17.JSONObjects.RpiID_Password;
import com.group17.JSONObjects.ServerError;
import com.group17.server.database.DAO;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.sql.SQLException;


/**
 * This class is only used for testing and must be disabled before deployment
 */
@Path("/admin")
public class AdminResource {
    @Path("/hello")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String hello() {
        return "Hello, World!";
    }


    @Path("/rpi")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response setRFID(RpiID_Password rpiID_password) {
        try {
            if (DAO.registerRpi(rpiID_password.getRpi_id(), rpiID_password.getPassword())) {
                return Response.status(Response.Status.OK).entity(rpiID_password).build();
            } else {
                return Response.status(Response.Status.CONFLICT)
                        .entity(new ServerError("The rpi_id is already registered")).build();
            }
        } catch (SQLException e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(new ServerError("SQL error")).build();
        }

    }

}