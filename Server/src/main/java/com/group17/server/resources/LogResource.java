package com.group17.server.resources;

import com.group17.JSONObjects.Log;
import com.group17.server.TokenCheck;
import com.group17.server.database.DAO;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.sql.SQLException;
import java.util.List;

@Path("/logs")
public class LogResource {

    @TokenCheck
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getLogs() throws SQLException {
        List<Log> logs = DAO.getLogs("101");
        return Response.status(200).entity(logs).build();
    }
}
