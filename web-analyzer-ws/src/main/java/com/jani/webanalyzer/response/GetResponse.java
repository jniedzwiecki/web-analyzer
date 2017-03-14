package com.jani.webanalyzer.response;

/**
 * Created by jacekniedzwiecki on 08.03.2017.
 */
public class GetResponse extends BaseResponse {

    private final String path;

    public static GetResponse response(String path) {
        return new GetResponse(path);
    }

    private GetResponse(String path) {
        this.path = path;
    }

    public String getPath() {
        return path;
    }

    @Override
    public String toString() {
        return "{ \"type\" : \"GetResponse\"," +
                " \"path\" : \"" + path + "\"" +
                "}";
    }
}
