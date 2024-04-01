
package com.mit.storesystem;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/store")
public class MyResource {
    
    @GET 
    @Path("/text/plain")
    @Produces(MediaType.TEXT_PLAIN)
    public String getIt() {
        return "Hi there!"; 
    }
    
}

