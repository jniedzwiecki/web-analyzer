package com.jani.webanalyzer.response

import groovy.transform.CompileStatic

import javax.ws.rs.core.Response

/**
 * Created by jacekniedzwiecki on 08.03.2017.
 */
@CompileStatic
class AddResponse {

    final Response.Status status
    final int pathId

    static AddResponse response(Response.Status status) {
        return new AddResponse(status, 0)
    }

    private AddResponse(Response.Status status, int pathId) {
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
