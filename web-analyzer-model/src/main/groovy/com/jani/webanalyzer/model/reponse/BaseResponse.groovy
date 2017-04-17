package com.jani.webanalyzer.model.reponse

import groovy.transform.CompileStatic

import javax.validation.constraints.NotNull

/**
 * Created by jacekniedzwiecki on 17.04.2017.
 */
@CompileStatic
class BaseResponse {

    @NotNull
    UUID uuid = UUID.randomUUID()

    @NotNull
    UUID originalRequestUUID

}
