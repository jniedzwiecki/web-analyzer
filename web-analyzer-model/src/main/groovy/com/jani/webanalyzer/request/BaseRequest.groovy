package com.jani.webanalyzer.request

import groovy.transform.CompileStatic

import javax.persistence.Id
import javax.validation.constraints.NotNull
/**
 * Created by jacekniedzwiecki on 17.03.2017.
 */
@CompileStatic
abstract class BaseRequest {

    @Id
    @NotNull
    UUID uuid

    BaseRequest() {
        uuid = UUID.randomUUID()
    }
}
