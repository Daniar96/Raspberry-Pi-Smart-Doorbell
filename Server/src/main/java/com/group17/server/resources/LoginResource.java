package com.group17.server.resources;

import java.sql.SQLException;

import com.group17.JSONObjects.ServerError;
import com.group17.JSONObjects.UserCredentials;
import com.group17.JSONObjects.Username_Token;
import com.group17.server.Database;
import com.group17.server.TokenList;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.*;


@Path("/login")
public class LoginResource {
    @Context
    private UriInfo uriInfo;
    @Context
    private Request request;

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response login(UserCredentials input) {
        try {
            boolean authorised = Database.checkUser(input.getUsername(), input.getPassword());
            if (!authorised) {
                return Response.status(Response.Status.FORBIDDEN).entity(new ServerError("Wrong user credentials"))
                        .build();
            }
            Username_Token ut = new Username_Token(input.getUsername());
            TokenList.addToken(ut);

            NewCookie session_id = new NewCookie("SESSION_ID", ut.getToken(), "/", "domain",
                    "JWT token", 600, true, true);
            return Response.status(Response.Status.OK).cookie(session_id).header("COOKIE", "SESSION_ID=" + ut.getToken()+";2").entity(ut).build();


        } catch (SQLException e) {
            e.printStackTrace();
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(new ServerError("SQL error")).build();

        }

    }

    @PUT
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response register(UserCredentials input) {
        try {
            boolean registered = Database.registerUser(input.getUsername(), input.getPassword());
            if (registered) {
                return Response.status(Response.Status.OK).entity(input).build();
            } else {
                return Response.status(Response.Status.CONFLICT)
                        .entity(new ServerError("The user is already registered")).build();

            }
        } catch (SQLException e) {

            e.printStackTrace();
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(new ServerError("SQL error")).build();

        }

    }

}
