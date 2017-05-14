package com.jani.webanalyzer.model.reponse

import groovy.transform.CompileStatic
/**
 * Created by jacekniedzwiecki on 01.05.2017.
 */
@CompileStatic
class AddPathsResponse extends BaseResponse {

    List<AddSinglePathResponse> addSinglePathResponses
}
