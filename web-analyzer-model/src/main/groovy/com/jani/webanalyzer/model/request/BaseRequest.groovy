package com.jani.webanalyzer.model.request

import groovy.transform.CompileStatic

import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.Id
import javax.validation.constraints.NotNull
/**
 * Created by jacekniedzwiecki on 17.03.2017.
 */
@Entity
@CompileStatic
abstract class BaseRequest {

    @Id
    @GeneratedValue
    long id

    @NotNull
    String uuid = UUID.randomUUID().toString()
}
