package com.jani.webanalyzer.model.request

import groovy.transform.CompileStatic
import javax.validation.constraints.NotNull

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
