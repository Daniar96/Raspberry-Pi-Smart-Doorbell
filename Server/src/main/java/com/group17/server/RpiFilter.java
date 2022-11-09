package com.group17.server;

import com.group17.JSONObjects.HashedUserCredentials;
import com.group17.JSONObjects.ServerError;
import jakarta.annotation.Priority;
import jakarta.ws.rs.Priorities;
import jakarta.ws.rs.container.ContainerRequestContext;
import jakarta.ws.rs.container.ContainerRequestFilter;
import jakarta.ws.rs.core.Cookie;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.Provider;

import java.sql.SQLException;
import java.util.Map;

import static com.group17.server.SecurityFunctions.passwordsEqual;
import static com.group17.server.database.DAO.getRpi_hashedPassword_salt;

@RfidPasswordCheck
@Provider
@Priority(Priorities.AUTHENTICATION)
public class RpiFilter implements ContainerRequestFilter {

    @Override
    public void filter(ContainerRequestContext requestContext) {
        try {
            Map<String, Cookie> cookies = requestContext.getCookies();
            String rpi_id = cookies.get("rpi_id").getValue();
            String pass = cookies.get("password").getValue();
            // Validate the password
            if (!validCredentials(rpi_id, pass))
                abortWithUnauthorized(requestContext, "WRONG RPI PASSWORD");
        } catch (NullPointerException e) {
            abortWithUnauthorized(requestContext, "Password was not provided");
        }
    }


    private void abortWithUnauthorized(ContainerRequestContext requestContext, String msg) {
        // Abort with 401 error and error message
        requestContext.abortWith(
                Response.status(Response.Status.UNAUTHORIZED).build());
    }

    private boolean validCredentials(String rpi_id, String pass) {
        try {
            String[] pass_salt = getRpi_hashedPassword_salt(rpi_id);
            //If no pass salt pair was found for this rpi_id, return false
            if (pass_salt == null) return false;
            return passwordsEqual(pass, pass_salt[1], pass_salt[0]);
        } catch (SQLException nullUser) {
            return false;
        }
    }

}
