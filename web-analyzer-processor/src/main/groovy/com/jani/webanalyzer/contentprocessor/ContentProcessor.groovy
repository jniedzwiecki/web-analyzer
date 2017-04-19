package com.jani.webanalyzer.contentprocessor

import groovy.transform.CompileStatic
import org.springframework.context.annotation.PropertySource
import org.springframework.stereotype.Service

/**
 * Created by jacekniedzwiecki on 18.04.2017.
 */
@Service
@PropertySource('classpath:web-analyzer-ws.properties')
@CompileStatic
class ContentProcessor {


}
