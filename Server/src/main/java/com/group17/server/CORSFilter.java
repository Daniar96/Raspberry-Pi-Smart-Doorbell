package com.group17.server;

import jakarta.ws.rs.container.ContainerRequestContext;
import jakarta.ws.rs.container.ContainerResponseContext;
import jakarta.ws.rs.container.ContainerResponseFilter;
import jakarta.ws.rs.ext.Provider;



@Provider
public class CORSFilter implements ContainerResponseFilter {

	@Override
	public void filter(final ContainerRequestContext req, final ContainerResponseContext res) {
		res.getHeaders().add("Access-Control-Allow-Origin", "*");
		res.getHeaders().add("Access-Control-Allow-Headers", "origin, content-type, accept, authorization");
		res.getHeaders().add("Access-Control-Allow-Credentials", "true");
		res.getHeaders().add("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS, HEAD");
	}

}
