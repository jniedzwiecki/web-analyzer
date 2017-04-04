package com.jani.webanalyzer.request

/**
 * Created by jacekniedzwiecki on 01.04.2017.
 */
trait Aggregable {

    UUID originalUuid

    def getOriginalUuid() {
        originalUuid
    }

    def setOriginalUuid(UUID originalUuid) {
        this.originalUuid = originalUuid
    }
}