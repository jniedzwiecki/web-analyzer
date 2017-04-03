package com.jani.webanalyzer.db.request

import com.sun.istack.internal.NotNull
import groovy.transform.CompileStatic

import static com.jani.webanalyzer.utils.FluentBuilder.with
/**
 * Created by jacekniedzwiecki on 17.03.2017.
 */
@CompileStatic
class AddPathsRequest extends BaseRequest {

    @NotNull
    List<String> paths

    static AddPathsRequest addPathsRequest(List<String> paths) {
        with(new AddPathsRequest())
                .op { AddPathsRequest req -> req.paths = paths }
                .get()
    }
}
