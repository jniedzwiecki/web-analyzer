package com.jani.webanalyzer.db.request

import com.sun.istack.internal.NotNull
import groovy.transform.CompileStatic
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id

import static com.jani.webanalyzer.utils.FluentBuilder.with
/**
 * Created by jacekniedzwiecki on 17.03.2017.
 */
@Entity
@CompileStatic
class AddPathsRequest extends BaseRequest {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Long id

    @NotNull
    List<String> paths

    static AddPathsRequest addPathsRequest(List<String> paths) {
        with(new AddPathsRequest())
                .op { AddPathsRequest req -> req.paths = paths }
                .get()
    }
}
