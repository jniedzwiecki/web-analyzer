package com.jani.webanalyzer.utils

/**
 * Created by jacekniedzwiecki on 21.04.2017.
 */
trait StatefulRequest {

    RequestState state = RequestState.NEW
}