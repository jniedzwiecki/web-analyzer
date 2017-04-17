package com.jani.webanalyzer.reponse

import groovy.transform.CompileStatic


/**
 * Created by jacekniedzwiecki on 17.04.2017.
 */
@CompileStatic
class GetPathResponse extends BaseResponse {

    String path
    PathStatus pathStatus
    Optional<String> content
}
