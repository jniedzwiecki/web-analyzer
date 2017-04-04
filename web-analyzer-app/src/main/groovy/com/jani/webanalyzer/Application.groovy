package com.jani.webanalyzer

import com.jani.webanalyzer.db.StorageService
import com.jani.webanalyzer.pathprocessor.PathProcessor
import com.jani.webanalyzer.ws.configs.ServicesConfiguration
import com.jani.webanalyzer.ws.exceptions.ToHttpErrorExceptionMapper
import com.jani.webanalyzer.ws.services.WebAnalyzerService
import org.apache.camel.spring.SpringCamelContext
import org.springframework.context.annotation.AnnotationConfigApplicationContext
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer
import org.springframework.data.jpa.repository.config.EnableJpaRepositories

import static com.jani.webanalyzer.utils.FluentBuilder.with
/**
 * Created by jacekniedzwiecki on 03.03.2017.
 */
@Configuration
@EnableJpaRepositories("com.jani.webanalyzer.db")
class Application {

    static void main(String[] args) {
        AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext(
                ServicesConfiguration.class,
                WebAnalyzerService.class,
                ToHttpErrorExceptionMapper.class,
                WebAnalyzerRoutesBuilder.class,
                PathProcessor.class,
                Application.class,
                StorageService.class
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
