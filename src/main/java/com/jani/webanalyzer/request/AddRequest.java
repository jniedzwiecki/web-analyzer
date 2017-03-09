package com.jani.webanalyzer.request;

/**
 * Created by jacekniedzwiecki on 08.03.2017.
 */
public class AddRequest extends BaseRequest {

    private String path;

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }
}
