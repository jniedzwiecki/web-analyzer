package com.jani.webanalyzer.configs

import com.fasterxml.jackson.jaxrs.json.JacksonJaxbJsonProvider
import com.jani.webanalyzer.aspects.LoggingAspect
import com.jani.webanalyzer.exceptions.ToHttpErrorExceptionMapper
import com.jani.webanalyzer.services.WebAnalyzer
import groovy.transform.CompileStatic
import org.apache.cxf.endpoint.Server
import org.apache.cxf.feature.Feature
import org.apache.cxf.jaxrs.JAXRSServerFactoryBean
import org.apache.cxf.jaxrs.validation.JAXRSBeanValidationFeature
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.EnableAspectJAutoProxy
import org.springframework.context.annotation.PropertySource

import static com.jani.webanalyzer.utils.FluentBuilder.with

/**
 * Created by jacekniedzwiecki on 05.03.2017.
 */
@Configuration
@EnableAspectJAutoProxy
@PropertySource("classpath:web-analyzer-ws.properties")
@CompileStatic
class ServicesConfiguration {

    @Bean
    @Autowired
    Server rsServer(WebAnalyzer webAnalyzer,
                    @Value('${web-service.address}') String webServiceAddress) {
        JAXRSServerFactoryBean endpoint = new JAXRSServerFactoryBean()
        endpoint.setAddress(webServiceAddress)
        endpoint.setServiceBean(webAnalyzer)
        endpoint.setFeatures(
                with(new ArrayList<Feature>())
                .op { ArrayList<Feature> o -> o.add(new JAXRSBeanValidationFeature()) }
                .get()
        )

        List<Object> providers = new ArrayList<>()
        providers.add(new JacksonJaxbJsonProvider())
        providers.add(new ToHttpErrorExceptionMapper())
        endpoint.setProviders(providers)

        return endpoint.create()
    }

    @Bean
    LoggingAspect loggingAspect() {
        return new LoggingAspect()
    }
}
