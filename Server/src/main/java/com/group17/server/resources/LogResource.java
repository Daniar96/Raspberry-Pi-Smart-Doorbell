package com.group17.server.resources;

import com.group17.JSONObjects.Log;
import com.group17.server.Database;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.List;

@Path("/logs")
public class LogResource {
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getLogs(){
        List<Log> logs = Database.getLogs();
        return Response.status(200).entity(logs).build();
    }
}
