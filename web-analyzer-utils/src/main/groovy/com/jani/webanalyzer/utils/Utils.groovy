package com.jani.webanalyzer.utils

/**
 * Created by jacekniedzwiecki on 19.04.2017.
 */
class Utils {

    static <T> T notNull(T t) {
        Optional.of(t).orElseThrow { -> throw new IllegalArgumentException("Missing mandatory value!") }
    }
}
