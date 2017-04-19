package com.jani.webanalyzer.utils

import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Configuration

/**
 * Created by jacekniedzwiecki on 19.04.2017.
 */

@Configuration
trait ObjectMapperAware {

    @Autowired
    ObjectMapper objectMapper

    ObjectMapper getObjectMapper() {
        return objectMapper
    }
}