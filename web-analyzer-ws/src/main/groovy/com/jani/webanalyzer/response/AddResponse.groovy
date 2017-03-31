package com.jani.webanalyzer.response

import groovy.transform.CompileStatic

import javax.ws.rs.core.Response

/**
 * Created by jacekniedzwiecki on 08.03.2017.
 */
@CompileStatic
class AddResponse {

    final Response.Status status
    final UUID pathId

    static AddResponse response(Response.Status status, UUID pathId) {
        return new AddResponse(status, pathId)
    }

    private AddResponse(Response.Status status, UUID pathId) {
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
