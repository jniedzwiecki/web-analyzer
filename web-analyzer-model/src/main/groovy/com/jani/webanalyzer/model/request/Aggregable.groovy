package com.jani.webanalyzer.model.request

/**
 * Created by jacekniedzwiecki on 01.04.2017.
 */
trait Aggregable {

    Long originalId

    def getOriginalId() {
        originalId
    }

    def setOriginalId(Long originalId) {
        this.originalId = originalId
    }
}