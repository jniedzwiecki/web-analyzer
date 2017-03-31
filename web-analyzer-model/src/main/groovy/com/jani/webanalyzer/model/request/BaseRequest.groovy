package com.jani.webanalyzer.model.request

import com.sun.istack.internal.NotNull
import groovy.transform.CompileStatic;

/**
 * Created by jacekniedzwiecki on 17.03.2017.
 */
@CompileStatic
abstract class BaseRequest {

    @NotNull
    UUID uuid

    BaseRequest() {
        uuid = UUID.randomUUID()
    }
}
