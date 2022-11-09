package com.group17.server;

import com.group17.JSONObjects.ServerError;
import jakarta.annotation.Priority;
import jakarta.ws.rs.Priorities;
import jakarta.ws.rs.container.ContainerRequestContext;
import jakarta.ws.rs.container.ContainerRequestFilter;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.Provider;

@TokenCheck
@Provider
@Priority(Priorities.AUTHENTICATION)
public class LoginFilter implements ContainerRequestFilter {

    @Override
    public void filter(ContainerRequestContext requestContext) {

            String authorizationCookie;

            try {
                authorizationCookie = requestContext.getCookies().get("SESSION_ID").getValue();
            } catch (NullPointerException e) {
                abortWithUnauthorized(requestContext, "Token was not provided");
                return;
            }

            // Validate the token
            if (!validToken(authorizationCookie))
                abortWithUnauthorized(requestContext, "Couldn't authenticate the user, please log in again");

    }


    private void abortWithUnauthorized(ContainerRequestContext requestContext, String msg) {
        // Abort with 401 error and error message
        requestContext.abortWith(
                Response.status(Response.Status.UNAUTHORIZED).build());
    }

    private boolean validToken(String token) {
        TokenList.print();
        if (!TokenList.isValidToken(token)) {
            return false;
        } else {
            System.out.println("User: " + TokenList.getUser(token) + " successfully validated their token");
            return true;
        }
    }

}
