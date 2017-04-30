package com.jani.webanalyzer.model.request

import groovy.transform.CompileStatic

/**
 * Created by jacekniedzwiecki on 16.04.2017.
 */
@CompileStatic
class GetPathRequest extends BaseRequest {

    String pathUuid

    static GetPathRequest getPathRequest(String pathId) {
        return new GetPathRequest(pathId)
    }

    GetPathRequest() {}

    GetPathRequest(String pathUuid) {
        this.pathUuid = pathUuid
    }
}
