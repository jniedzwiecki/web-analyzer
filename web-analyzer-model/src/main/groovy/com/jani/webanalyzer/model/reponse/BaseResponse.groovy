package com.jani.webanalyzer.model.reponse

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.jani.webanalyzer.model.request.Aggregable
import groovy.transform.CompileStatic

import javax.validation.constraints.NotNull
/**
 * Created by jacekniedzwiecki on 17.04.2017.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@CompileStatic
class BaseResponse implements Aggregable {

    @NotNull
    String uuid = UUID.randomUUID()
}
