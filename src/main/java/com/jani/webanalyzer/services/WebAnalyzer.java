package com.jani.webanalyzer.services;

import com.jani.webanalyzer.request.AddRequest;
import com.jani.webanalyzer.response.AddResponse;
import com.jani.webanalyzer.response.GetResponse;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

/**
 * Created by jacekniedzwiecki on 05.03.2017.
 */
@Path("/web-analyzer")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public interface WebAnalyzer {

    @POST
    AddResponse add(AddRequest request);

    @GET
    @Path("/{id}")
    GetResponse get(@PathParam("id") int id);
}
