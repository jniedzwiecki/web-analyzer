package com.jani.webanalyzer.model.request

/**
 * Created by jacekniedzwiecki on 01.04.2017.
 */
trait Aggregable {

    String originalUuid

    String getOriginalUuid() {
        originalUuid
    }

    def setOriginalUuid(String originalUuid) {
        this.originalUuid = originalUuid
    }
}