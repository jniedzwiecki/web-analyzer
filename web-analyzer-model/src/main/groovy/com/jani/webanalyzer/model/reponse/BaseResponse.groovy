package com.jani.webanalyzer.model.reponse

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import groovy.transform.CompileStatic

import javax.validation.constraints.NotNull
/**
 * Created by jacekniedzwiecki on 17.04.2017.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@CompileStatic
class BaseResponse {

    @NotNull
    String uuid = UUID.randomUUID()

    @NotNull
    String originalRequestUUID

}
