package com.jani.webanalyzer.ws.response

import groovy.transform.CompileStatic

import javax.ws.rs.core.Response

/**
 * Created by jacekniedzwiecki on 08.03.2017.
 */
@CompileStatic
class AddPathsResponse extends BaseResponse {

    final Response.Status status
    final UUID pathId

    static AddPathsResponse addPathsResponse(Response.Status status, UUID pathId) {
        return new AddPathsResponse(status, pathId)
    }

    private AddPathsResponse(Response.Status status, UUID pathId) {
        this.status = status
        this.pathId = pathId
    }

    @Override
    String toString() {
        return "{ \"type\" : \"AddResponse\"," +
                " \"status\" : \"" + status.toString() + "\", " +
                " \"pathId\" : \"" + pathId + "\"" +
                "}"
    }
}
