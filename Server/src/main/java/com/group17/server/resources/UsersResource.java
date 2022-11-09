package com.group17.server.resources;

import com.group17.JSONObjects.Username_AtHomeStatus;
import com.group17.JSONObjects.ServerError;
import com.group17.server.TokenCheck;
import com.group17.server.TokenList;
import com.group17.server.database.DAO;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.*;
import org.glassfish.jersey.server.ContainerRequest;


import java.sql.SQLException;
import java.util.List;

@Path("/users")
public class UsersResource {
    @Context
    private ContainerRequest request;

    @TokenCheck
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getUsers() {
        try {
            List<Username_AtHomeStatus> users = DAO.getAtHomeList(getRpiIDFromUser());
            return Response.status(200).entity(users).build();
        } catch (SQLException e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(new ServerError("SQL error")).build();
        }
    }

    private String getRpiIDFromUser() throws SQLException {
        String email = TokenList.getUser(request.getRequestCookies().get("SESSION_ID").getValue());
        return  DAO.getRpiFromEmail(email);
    }

}
