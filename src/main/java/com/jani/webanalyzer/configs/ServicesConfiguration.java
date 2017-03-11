package com.jani.webanalyzer.configs;

import com.fasterxml.jackson.jaxrs.json.JacksonJaxbJsonProvider;
import com.jani.webanalyzer.aspects.LoggingAspect;
import com.jani.webanalyzer.services.WebAnalyzer;
import com.jani.webanalyzer.services.WebAnalyzerService;
import org.apache.cxf.endpoint.Server;
import org.apache.cxf.jaxrs.JAXRSServerFactoryBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jacekniedzwiecki on 05.03.2017.
 */
@Configuration
@EnableAspectJAutoProxy
public class ServicesConfiguration {

    private static final String WEB_SERVICE_ADDRESS = "http://0.0.0.0:8080";

    @Bean
    @Autowired
    public Server rsServer(WebAnalyzer webAnalyzer) {
        JAXRSServerFactoryBean endpoint = new JAXRSServerFactoryBean();
        endpoint.setAddress(WEB_SERVICE_ADDRESS);
        endpoint.setServiceBean(webAnalyzer);

        List<Object> providers = new ArrayList<>();
        providers.add(new JacksonJaxbJsonProvider());
        endpoint.setProviders(providers);

        return endpoint.create();
    }

    @Bean
    public LoggingAspect loggingAspect() {
        return new LoggingAspect();
    }
}
