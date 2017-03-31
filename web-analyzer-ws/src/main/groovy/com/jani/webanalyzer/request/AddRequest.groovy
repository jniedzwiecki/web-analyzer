package com.jani.webanalyzer.request

import groovy.transform.CompileStatic
import groovy.transform.Immutable

import javax.validation.constraints.NotNull
/**
 * Created by jacekniedzwiecki on 08.03.2017.
 */
@CompileStatic
@Immutable
class AddRequest extends BaseRequest {

    @NotNull
    List<String> paths
}
