package com.jani.webanalyzer.services;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

/**
 * Created by jacekniedzwiecki on 05.03.2017.
 */
@Path("/simple")
public interface SimpleService {

    @GET
    @Path("/{name}")
    @Produces(MediaType.TEXT_PLAIN)
    String apply(@PathParam("name") String name);
}
