package com.jani.webanalyzer.configs;

import com.jani.webanalyzer.services.SimpleServiceImpl;
import org.apache.cxf.endpoint.Server;
import org.apache.cxf.jaxrs.JAXRSServerFactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Created by jacekniedzwiecki on 05.03.2017.
 */
@Configuration
public class ServicesConfiguration {

    @Bean
    public Server rsServer() {
        JAXRSServerFactoryBean endpoint = new JAXRSServerFactoryBean();
        endpoint.setAddress("http://0.0.0.0:8080");
        endpoint.setResourceClasses(SimpleServiceImpl.class);
        return endpoint.create();
    }
}
