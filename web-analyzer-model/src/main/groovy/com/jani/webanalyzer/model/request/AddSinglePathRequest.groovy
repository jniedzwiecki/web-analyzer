package com.jani.webanalyzer.model.request

import com.sun.istack.internal.NotNull

/**
 * Created by jacekniedzwiecki on 01.04.2017.
 */
class AddSinglePathRequest extends BaseRequest implements Aggregable {

    AddSinglePathRequest(UUID originalUuid, String path) {
        this.originalUuid = originalUuid
        this.path = path
    }

    @NotNull
    String path
}
