package com.jani.webanalyzer.response;

/**
 * Created by jacekniedzwiecki on 08.03.2017.
 */
class GetResponse extends BaseResponse {

    private final String path

    static GetResponse response(String path) {
        return new GetResponse(path)
    }

    private GetResponse(String path) {
        this.path = path
    }

    @Override
    String toString() {
        return "{ \"type\" : \"GetResponse\"," +
                " \"path\" : \"" + path + "\"" +
                "}"
    }
}
