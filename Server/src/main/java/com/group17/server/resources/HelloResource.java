package com.group17.server.resources;

import com.group17.server.SecurityCheck;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

@Path("/hello")
public class HelloResource {
    @SecurityCheck
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String hello() {
        return "Hello, World!";
    }
}