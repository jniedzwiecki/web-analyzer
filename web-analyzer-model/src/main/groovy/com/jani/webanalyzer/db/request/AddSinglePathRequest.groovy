package com.jani.webanalyzer.db.request

import com.sun.istack.internal.NotNull
/**
 * Created by jacekniedzwiecki on 01.04.2017.
 */
class AddSinglePathRequest extends BaseRequest implements Aggregable {

    AddSinglePathRequest() {}

    AddSinglePathRequest(UUID originalUuid, String path) {
        setOriginalUuid(originalUuid)
        this.path = path
    }

    @NotNull
    String path
}
