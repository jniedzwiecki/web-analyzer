package com.jani.webanalyzer.model.reponse

/**
 * Created by jacekniedzwiecki on 01.05.2017.
 */
class AddSinglePathResponse extends BaseResponse {

    String path

    static AddSinglePathResponse addSinglePathResponse(String path, String originalUUID) {
        new AddSinglePathResponse().with {
            it.path = path
            it.setOriginalUuid(originalUUID)
            it
        }
    }
}
