package com.group17.server.resources;

import com.group17.JSONObjects.Log;
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

@Path("/logs")
public class LogResource {

    @Context
    private ContainerRequest request;

    @TokenCheck
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getLogs() throws SQLException {
        List<Log> logs = DAO.getLogs(getRpiIDFromUser());
        return Response.status(200).entity(logs).build();
    }

    private String getRpiIDFromUser() throws SQLException {
        String email = TokenList.getUser(request.getRequestCookies().get("SESSION_ID").getValue());
        return  DAO.getRpiFromEmail(email);
    }
}
