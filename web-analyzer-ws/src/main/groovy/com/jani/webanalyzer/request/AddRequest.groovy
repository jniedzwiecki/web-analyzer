package com.jani.webanalyzer.request

import groovy.transform.CompileStatic
import groovy.transform.Immutable

import javax.validation.constraints.NotNull
import javax.validation.constraints.Pattern
/**
 * Created by jacekniedzwiecki on 08.03.2017.
 */
@CompileStatic
@Immutable
class AddRequest extends BaseRequest {

    @NotNull
    @Pattern(regexp = "http://.*")
    String path
}
