package com.group17.server.resources;

import com.group17.JSONObjects.*;
import com.group17.server.database.DAO;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.sql.SQLException;

@Path("/RPI")
public class RPIresource {

    @POST
    @Path("/smoke")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response smokeAlertRpi(SmokeAlert alert) throws SQLException {
        boolean isSmoke = alert.getAlert() == 1;
        DAO.smokeAlert(isSmoke, "101");
        return Response.status(200).build();
    }

    @GET
    @Path("/smokeAlert")
    @Produces(MediaType.APPLICATION_JSON)
    public Response smokeAlertWeb() throws SQLException {
        return Response.status(200).entity(DAO.getSmoke("101")).build();
    }

    @POST
    @Path("/flame")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response flameAlertRpi(FlameAlert alert) throws SQLException {
        boolean isFlame = alert.getAlert() == 1;
        DAO.flameAlert(isFlame, "101");
        return Response.status(200).build();
    }

    @GET
    @Path("/flameAlert")
    @Produces(MediaType.APPLICATION_JSON)
    public Response flameAlertWeb() throws SQLException {
        return Response.status(200).entity(DAO.getFlame("101")).build();
    }

    @POST
    @Path("/mic")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response smokeAlertRpi(MicAlert alert) throws SQLException {
        boolean isMic= alert.getAlert() == 1;
        DAO.micAlert(isMic, "101");
        return Response.status(200).build();
    }

    @GET
    @Path("/micAlert")
    @Produces(MediaType.APPLICATION_JSON)
    public Response micAlertWeb() throws SQLException {
        return Response.status(200).entity(DAO.getMic("101")).build();
    }

    @POST
    @Path("/authorize")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response checkTag(TagID id) throws SQLException {
        if (DAO.checkOnline(id.getTagID(),"101")){
            return Response.status(200).build();
        }else {
            return Response.status(401).build();
        }
    }

    @POST
    @Path("/image")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response getImage(Image image) throws SQLException {
        if (DAO.getPir("101") && !DAO.arePeopleInside("101")){
            DAO.addLog("PIR sensor has detected movement", "101");
            try {
                DAO.addImage("101",image.getName(), image.getEncode());
            } catch (SQLException e) {
                return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(new ServerError("SQL error")).build();
            }
        }
        return Response.status(200).build();
    }

    @POST
    @Path("/closePIR")
    public Response stopSensor() throws SQLException {
        DAO.stopPir("101");
        return Response.status(200).build();
    }

    @POST
    @Path("/startPIR")
    public Response startSensor() throws SQLException {
        DAO.startPir("101");
        return Response.status(200).build();
    }

    @GET
    @Path("/getPIR")
    public Response getSensor() throws SQLException {
        int i;
        if (DAO.getPir("101")){
            i = 1;
        }else {
            i = 0;
        }
        return Response.status(200).entity(i).build();
    }

    @POST
    @Path("/setTemp")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response setTemp(Temp t) throws SQLException {
        DAO.addTemp(t.getTemp(), t.getHumidity(), "101");
        return Response.status(200).build();
    }

    @GET
    @Path("/temp")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getTemp() throws SQLException {
        return Response.status(200).entity(DAO.getTemp("101")).build();
    }


}
