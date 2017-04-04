package com.jani.webanalyzer.db

import groovy.transform.CompileStatic
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.orm.jpa.LocalEntityManagerFactoryBean
import org.springframework.stereotype.Service
/**
 * Created by jacekniedzwiecki on 04.04.2017.
 */
@Service
@CompileStatic
class StorageService {

    RequestRepository requestRepository

    @Autowired
    StorageService(RequestRepository requestRepository) {
        this.requestRepository = requestRepository
    }

    @Bean
    LocalEntityManagerFactoryBean entityManagerFactory() {
        new LocalEntityManagerFactoryBean()
    }
}
