package com.jani.webanalyzer.extensions
/**
 * Created by jacekniedzwiecki on 21.03.2017.
 */
class ThrowableExtension {

    static String stackToString(final Throwable self) {
        StringWriter sw = new StringWriter()
        self.printStackTrace(new PrintWriter(sw))
        sw.toString()
    }
}
