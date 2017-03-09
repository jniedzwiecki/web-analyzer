package com.jani.webanalyzer.configs;

import com.fasterxml.jackson.jaxrs.json.JacksonJaxbJsonProvider;
import com.jani.webanalyzer.services.WebAnalyzerService;
import org.apache.cxf.endpoint.Server;
import org.apache.cxf.jaxrs.JAXRSServerFactoryBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jacekniedzwiecki on 05.03.2017.
 */
@Configuration
public class ServicesConfiguration {

    private static final String WEB_SERVICE_ADDRESS = "http://0.0.0.0:8080";

    @Bean
    @Autowired
    public Server rsServer(WebAnalyzerService webAnalyzerService) {
        JAXRSServerFactoryBean endpoint = new JAXRSServerFactoryBean();
        endpoint.setAddress(WEB_SERVICE_ADDRESS);
        endpoint.setServiceBean(webAnalyzerService);

        List<Object> providers = new ArrayList<>();
        providers.add(new JacksonJaxbJsonProvider());
        endpoint.setProviders(providers);

        return endpoint.create();
    }
}
