package com.jani.webanalyzer.request

import groovy.transform.CompileStatic

/**
 * Created by jacekniedzwiecki on 16.04.2017.
 */
@CompileStatic
class GetPathRequest extends BaseRequest {

    UUID pathUUID

    static GetPathRequest getPathRequest(UUID pathUUID) {
        return new GetPathRequest(pathUUID)
    }

    GetPathRequest(UUID pathUUID) {
        this.pathUUID = pathUUID
    }
}
