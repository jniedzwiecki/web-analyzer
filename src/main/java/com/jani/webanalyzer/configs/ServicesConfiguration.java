package com.jani.webanalyzer.configs;

import com.fasterxml.jackson.jaxrs.json.JacksonJaxbJsonProvider;
import com.jani.webanalyzer.aspects.LoggingAspect;
import com.jani.webanalyzer.exceptions.ToHttpErrorExceptionMapper;
import com.jani.webanalyzer.services.WebAnalyzer;
import com.jani.webanalyzer.services.WebAnalyzerService;
import com.jani.webanalyzer.util.FluentBuilder;
import org.apache.cxf.endpoint.Server;
import org.apache.cxf.feature.Feature;
import org.apache.cxf.jaxrs.JAXRSServerFactoryBean;
import org.apache.cxf.jaxrs.validation.JAXRSBeanValidationFeature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

import java.util.ArrayList;
import java.util.List;

import static com.jani.webanalyzer.util.FluentBuilder.with;

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
        endpoint.setFeatures(
                with(new ArrayList<Feature>())
                .op($ -> $.add(new JAXRSBeanValidationFeature()))
                .get()
        );

        List<Object> providers = new ArrayList<>();
        providers.add(new JacksonJaxbJsonProvider());
        providers.add(new ToHttpErrorExceptionMapper());
        endpoint.setProviders(providers);

        return endpoint.create();
    }

    @Bean
    public LoggingAspect loggingAspect() {
        return new LoggingAspect();
    }
}
