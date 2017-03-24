package com.jani.webanalyzer

import com.jani.webanalyzer.configs.ServicesConfiguration
import com.jani.webanalyzer.exceptions.ToHttpErrorExceptionMapper
import com.jani.webanalyzer.services.WebAnalyzerService
import org.apache.camel.spring.SpringCamelContext
import org.springframework.context.annotation.AnnotationConfigApplicationContext
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer

import static com.jani.webanalyzer.util.FluentBuilder.with
/**
 * Created by jacekniedzwiecki on 03.03.2017.
 */
@Configuration
class Application {

    static void main(String[] args) {
        AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext(
                ServicesConfiguration.class,
                WebAnalyzerService.class,
                ToHttpErrorExceptionMapper.class,
                WebAnalyzerRoutesBuilder.class,
                Application.class
        )
        ctx.start()
    }

    @Bean
    static PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer() {
        new PropertySourcesPlaceholderConfigurer()
    }

    @Bean
    SpringCamelContext camelContext() {
        with(new SpringCamelContext()).lastOp { it.setAutoStartup(true) }
    }
}
