package com.group17.server.resources;

import com.group17.JSONObjects.UserCredentials;
import com.group17.server.Database;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.*;


import java.util.List;

@Path("/users")
public class UsersResource {
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getUsers() {
        List<UserCredentials> users = Database.getUsersList();
        return Response.status(200).entity(users).build();
    }
}
