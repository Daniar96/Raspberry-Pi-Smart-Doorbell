package com.group17.server;

import com.group17.JSONObjects.ServerError;
import jakarta.annotation.Priority;
import jakarta.ws.rs.Priorities;
import jakarta.ws.rs.container.ContainerRequestContext;
import jakarta.ws.rs.container.ContainerRequestFilter;
import jakarta.ws.rs.core.HttpHeaders;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.Provider;

import java.io.IOException;

@SecurityCheck
@Provider
@Priority(Priorities.AUTHENTICATION)
public class LoginFilter implements ContainerRequestFilter {

    @Override
    public void filter(ContainerRequestContext requestContext) throws IOException {

        // Get the Authorization header from the request
        String authorizationHeader = requestContext.getHeaderString(HttpHeaders.AUTHORIZATION);

        // Validate the Authorization header
        if (!isValidToken(authorizationHeader)) {
            System.out.println("Header is invalid");
            abortWithUnauthorized(requestContext);
            return;
        }

        try {
            // Validate the token
            validateToken(authorizationHeader);

        } catch (Exception e) {
            abortWithUnauthorized(requestContext);
        }
    }

    private boolean isValidToken(String authorizationHeader) {
        // Check if a header is valid (not null)
        return authorizationHeader != null;
    }

    private void abortWithUnauthorized(ContainerRequestContext requestContext) {
        // Abort with 401 error and error message
        requestContext.abortWith(
                Response.status(Response.Status.UNAUTHORIZED).entity(new ServerError("Ivalid/expired token")).build());
    }

    private void validateToken(String token) throws Exception {
        TokenList.print();
        // Throw an Exception if the token is invalid
        if (!TokenList.isValidToken(token)) {
            throw new Exception();
        } else {
            System.out.println("User: " + TokenList.getUser(token) + " succesfully validated their token");
        }
    }
}
