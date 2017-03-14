package webanalyzer.services;

import webanalyzer.request.AddRequest;
import webanalyzer.response.AddResponse;
import webanalyzer.response.GetResponse;

import javax.validation.Valid;
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
    AddResponse add(@Valid AddRequest request);

    @GET
    @Path("/{id}")
    GetResponse get(@PathParam("id") int id);
}
