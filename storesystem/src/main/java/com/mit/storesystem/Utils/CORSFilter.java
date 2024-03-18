package com.mit.storesystem.Utils;

import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.ext.Provider;

import com.sun.jersey.spi.container.ContainerRequest;
import com.sun.jersey.spi.container.ContainerResponse;
import com.sun.jersey.spi.container.ContainerResponseFilter;

@Provider
public class CORSFilter implements ContainerResponseFilter {
	

	 @Override
	    public ContainerResponse filter(ContainerRequest request, ContainerResponse response) {
		 	
		 	MultivaluedMap<String, Object> headers = response.getHttpHeaders();
		 
	        response.getHttpHeaders().add("Access-Control-Allow-Origin", "http://localhost:4200");
	        response.getHttpHeaders().add("Access-Control-Allow-Headers", "origin, content-type, accept, authorization");
	        response.getHttpHeaders().add("Access-Control-Allow-Credentials", "true");
	        response.getHttpHeaders().add("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS, HEAD");
	        response.getHttpHeaders().add("Access-Control-Expose-Headers", "Content-Length, Content-Type");
	        
	        return response;
	    }
}
