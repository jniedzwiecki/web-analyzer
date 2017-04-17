package com.jani.webanalyzer.ws.services

import com.jani.webanalyzer.ws.request.AddPathsRequest
import com.jani.webanalyzer.ws.response.AddResponse
import com.jani.webanalyzer.ws.response.GetPathResponse
import groovy.transform.CompileStatic

import javax.validation.Valid
import javax.ws.rs.*
import javax.ws.rs.core.MediaType

/**
 * Created by jacekniedzwiecki on 05.03.2017.
 */
@Path("/web-analyzer")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@CompileStatic
interface WebAnalyzer {

    @POST
    @Path("/paths")
    AddResponse add(@Valid AddPathsRequest request)

    @GET
    @Path("/path/{id}")
    GetPathResponse getPath(@PathParam("id") UUID id)
}
