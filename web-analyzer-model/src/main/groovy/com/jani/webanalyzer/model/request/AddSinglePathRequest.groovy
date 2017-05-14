package com.jani.webanalyzer.model.request

import com.jani.webanalyzer.utils.StatefulRequest
import com.sun.istack.internal.NotNull

import javax.persistence.Entity
/**
 * Created by jacekniedzwiecki on 01.04.2017.
 */
@Entity
class AddSinglePathRequest extends BaseRequest implements Aggregable, StatefulRequest {

    @NotNull
    String path

    AddSinglePathRequest() {}

    AddSinglePathRequest(String originalUuid, int originalSize, String path) {
        setOriginalUuid(originalUuid)
        setOriginalSize(originalSize)
        this.path = path
    }
}
