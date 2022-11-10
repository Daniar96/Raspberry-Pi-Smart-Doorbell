package com.group17.server.resources;

import com.group17.JSONObjects.*;
import com.group17.server.RfidPasswordCheck;
import com.group17.server.TokenCheck;
import com.group17.server.TokenList;
import com.group17.server.database.DAO;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.glassfish.jersey.server.ContainerRequest;

import java.sql.SQLException;

@Path("/RPI")
public class RPIresource {
    @Context
    private ContainerRequest request;

    @RfidPasswordCheck
    @POST
    @Path("/smoke")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response smokeAlertRpi(SmokeAlert alert) throws SQLException {
        boolean isSmoke = alert.getAlert() == 1;
        DAO.smokeAlert(isSmoke, getRpiID());
        DAO.addLog("Smoke was detected in the house", getRpiID());
        return Response.status(200).build();
    }
    @RfidPasswordCheck
    @POST
    @Path("/flame")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response flameAlertRpi(FlameAlert alert) throws SQLException {
        boolean isFlame = alert.getAlert() == 1;
        DAO.flameAlert(isFlame, getRpiID());
        DAO.addLog("Fire was detected in the house", getRpiID());
        return Response.status(200).build();
    }
    @RfidPasswordCheck
    @POST
    @Path("/mic")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response smokeAlertRpi(MicAlert alert) throws SQLException {
        boolean isMic= alert.getAlert() == 1;
        DAO.micAlert(isMic, getRpiID());
        return Response.status(200).build();
    }
    @RfidPasswordCheck
    @POST
    @Path("/authorize")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response checkTag(TagID id) throws SQLException {
        if (DAO.checkOnline(id.getTagID(),getRpiID())){
            return Response.status(200).build();
        }else {
            return Response.status(401).build();
        }
    }

    @RfidPasswordCheck
    @POST
    @Path("/image")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response getImage(Image image) throws SQLException {
        if (DAO.getPir(getRpiID()) && !DAO.arePeopleInside(getRpiID())){
            DAO.addLog("PIR sensor has detected movement", getRpiID());
            try {
                DAO.addImage(getRpiID(),image.getName(), image.getEncode());
            } catch (SQLException e) {
                return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(new ServerError("SQL error")).build();
            }
        }
        return Response.status(200).build();
    }


    @RfidPasswordCheck
    @POST
    @Path("/setTemp")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response setTemp(Temp t) throws SQLException {
        DAO.addTemp(t.getTemp(), t.getHumidity(), getRpiID());
        return Response.status(200).build();
    }

    /**
     * Retrieves rpi_id from a cookie
     * @return - rpi_id
     */
    private String getRpiID(){
        return request.getRequestCookies().get("rpi_id").getValue();
    }

    @TokenCheck
    @POST
    @Path("/closePIR")
    public Response stopSensor() throws SQLException {
        DAO.stopPir(getRpiIDFromUser());
        return Response.status(200).build();
    }

    @TokenCheck
    @POST
    @Path("/startPIR")
    public Response startSensor() throws SQLException {
        DAO.startPir(getRpiIDFromUser());
        return Response.status(200).build();
    }

    @TokenCheck
    @GET
    @Path("/smokeAlert")
    @Produces(MediaType.APPLICATION_JSON)
    public Response smokeAlertWeb() throws SQLException {
        return Response.status(200).entity(DAO.getSmoke(getRpiIDFromUser())).build();
    }



    @TokenCheck
    @GET
    @Path("/flameAlert")
    @Produces(MediaType.APPLICATION_JSON)
    public Response flameAlertWeb() throws SQLException {
        return Response.status(200).entity(DAO.getFlame(getRpiIDFromUser())).build();
    }



    @TokenCheck
    @GET
    @Path("/micAlert")
    @Produces(MediaType.APPLICATION_JSON)
    public Response micAlertWeb() throws SQLException {
        return Response.status(200).entity(DAO.getMic(getRpiIDFromUser())).build();
    }

    @TokenCheck
    @GET
    @Path("/getPIR")
    public Response getSensor() throws SQLException {
        int i;
        if (DAO.getPir(getRpiIDFromUser())){
            i = 1;
        }else {
            i = 0;
        }

        if (DAO.arePeopleInside(getRpiIDFromUser())){
            i = 0;
        }

        return Response.status(200).entity(i).build();
    }

    @TokenCheck
    @GET
    @Path("/temp")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getTemp() throws SQLException {
        return Response.status(200).entity(DAO.getTemp(getRpiIDFromUser())).build();
    }

    private String getRpiIDFromUser() throws SQLException {
        String email = TokenList.getUser(request.getRequestCookies().get("SESSION_ID").getValue());
        return  DAO.getRpiFromEmail(email);
    }
}
