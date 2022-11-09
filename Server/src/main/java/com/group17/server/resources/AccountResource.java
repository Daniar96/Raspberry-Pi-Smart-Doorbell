package com.group17.server.resources;
import com.group17.JSONObjects.ServerError;
import com.group17.JSONObjects.UserInfo;
import com.group17.server.database.DAO;
import com.group17.server.SecurityCheck;
import com.group17.server.TokenList;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.*;
import org.glassfish.jersey.server.ContainerRequest;
import java.sql.SQLException;

@Path("/account")
public class AccountResource {
    @Context
    private UriInfo uriInfo;
    @Context
    private ContainerRequest request;


    @SecurityCheck
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getUserInfo(){
        String email = TokenList.getUser(request.getRequestCookies().get("SESSION_ID").getValue());
        try {
            UserInfo info = DAO.getUserInfo(email);
            return Response.status(200).entity(info).build();
        } catch (SQLException e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(new ServerError("SQL error")).build();
        }
    }

    @SecurityCheck
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response setUserInfo(UserInfo userInfo){
        String email = TokenList.getUser(request.getRequestCookies().get("SESSION_ID").getValue());
        System.out.println("Email " + email);
        //Email can't be null because security check was already performed
        try {
            DAO.setUserInfo(email,userInfo);
            return Response.status(200).build();
        } catch (SQLException e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(new ServerError("SQL error")).build();
        }
    }
}
