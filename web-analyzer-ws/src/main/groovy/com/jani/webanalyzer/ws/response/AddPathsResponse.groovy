package com.jani.webanalyzer.ws.response

import com.jani.webanalyzer.model.reponse.AddSinglePathResponse
import com.jani.webanalyzer.model.request.Aggregable
import groovy.transform.CompileStatic

import javax.ws.rs.core.Response

/**
 * Created by jacekniedzwiecki on 08.03.2017.
 */
@CompileStatic
class AddPathsResponse extends BaseResponse implements Aggregable {

    final Response.Status status
    final List<AddSinglePathResponse> addSinglePathResponses

    static AddPathsResponse addPathsResponse(Response.Status status, String originalUUID, List<AddSinglePathResponse> addSinglePathResponses) {
        return new AddPathsResponse(status, originalUUID, addSinglePathResponses)
    }

    private AddPathsResponse(Response.Status status, String originalUUID, List<AddSinglePathResponse> addSinglePathResponses) {
        this.status = status
        this.setOriginalUuid(originalUUID)
        this.addSinglePathResponses = addSinglePathResponses
    }

    @Override
    String toString() {
        return "{ \"type\" : \"AddResponse\"," +
                " \"status\" : \"" + status.toString() + "\", " +
                " \"originalUUID\" : \"" + getOriginalUuid() + "\"" +
                "}"
    }
}
