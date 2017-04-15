package com.jani.webanalyzer.request

import com.sun.istack.internal.NotNull

import javax.persistence.Entity
/**
 * Created by jacekniedzwiecki on 01.04.2017.
 */
@Entity
class AddSinglePathRequest extends BaseRequest implements Aggregable {

    @NotNull
    String path

    AddSinglePathRequest() {}

    AddSinglePathRequest(UUID originalUuid, String path) {
        setOriginalUuid(originalUuid)
        this.path = path
    }
}
