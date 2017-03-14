package com.jani.webanalyzer.request;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

/**
 * Created by jacekniedzwiecki on 08.03.2017.
 */
public class AddRequest extends BaseRequest {

    @NotNull
    @Pattern(regexp = "http://.*")
    private String path;

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }
}
