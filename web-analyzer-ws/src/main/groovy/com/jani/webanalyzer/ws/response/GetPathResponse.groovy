package com.jani.webanalyzer.ws.response

import com.jani.webanalyzer.utils.RequestState
import groovy.transform.CompileStatic
/**
 * Created by jacekniedzwiecki on 08.03.2017.
 */
@CompileStatic
class GetPathResponse extends BaseResponse {

    final String originalRequestUUID
    final RequestState requestState
    final String content

    static GetPathResponse getPathResponse(String originalRequestUUID, RequestState requestState, String content) {
        return new GetPathResponse(originalRequestUUID, requestState, content)
    }

    private GetPathResponse(String originalRequestUUID, RequestState requestState, String content) {
        this.originalRequestUUID = originalRequestUUID
        this.requestState = requestState
        this.content = content
    }

    @Override
    String toString() {
        return "{ \"type\" : \"GetResponse\"," +
                " \"originalRequestUUID\" : \"" + originalRequestUUID + "\"" +
                " \"pathStatus\" : \"" + requestState + "\"" +
                "}"
    }
}
