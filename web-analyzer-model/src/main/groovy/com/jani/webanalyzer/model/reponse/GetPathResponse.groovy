package com.jani.webanalyzer.model.reponse

import com.jani.webanalyzer.utils.RequestState
import groovy.transform.CompileStatic


/**
 * Created by jacekniedzwiecki on 17.04.2017.
 */
@CompileStatic
class GetPathResponse extends BaseResponse {

    String path
    RequestState requestState
    String content

    static GetPathResponse getPathResponse(String originalUUID, String path, RequestState requestState, Optional<String> content) {
        new GetPathResponse().with {
            it.setOriginalUuid(originalUUID)
            it.path = path
            it.requestState = requestState
            it.content = content
            it
        }
    }
}
