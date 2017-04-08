package com.jani.webanalyzer.request

import groovy.transform.CompileStatic

import javax.persistence.Id
import javax.validation.constraints.NotNull
import javax.persistence.Entity
/**
 * Created by jacekniedzwiecki on 17.03.2017.
 */
@Entity
@CompileStatic
abstract class BaseRequest {

    @Id
    @NotNull
    UUID uuid

    BaseRequest() {
        uuid = UUID.randomUUID()
    }
}
