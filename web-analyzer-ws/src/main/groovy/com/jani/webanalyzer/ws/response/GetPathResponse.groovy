package com.jani.webanalyzer.ws.response

import com.jani.webanalyzer.model.reponse.PathStatus
import groovy.transform.CompileStatic;

/**
 * Created by jacekniedzwiecki on 08.03.2017.
 */
@CompileStatic
class GetPathResponse extends BaseResponse {

    final UUID originalRequestUUID
    final PathStatus pathStatus
    final Optional<String> content

    static GetPathResponse getPathResponse(UUID originalRequestUUID, PathStatus pathStatus, Optional<String> content) {
        return new GetPathResponse(originalRequestUUID, pathStatus, content)
    }

    private GetPathResponse(UUID originalRequestUUID, PathStatus pathStatus, Optional<String> content) {
        this.originalRequestUUID = originalRequestUUID
        this.pathStatus = pathStatus
        this.content = content
    }

    @Override
    String toString() {
        return "{ \"type\" : \"GetResponse\"," +
                " \"originalRequestUUID\" : \"" + originalRequestUUID + "\"" +
                " \"pathStatus\" : \"" + pathStatus + "\"" +
                "}"
    }
}
