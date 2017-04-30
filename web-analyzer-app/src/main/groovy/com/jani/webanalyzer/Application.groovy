package com.jani.webanalyzer

import com.fasterxml.jackson.databind.ObjectMapper
import com.jani.webanalyzer.contentprocessor.ContentProcessor
import com.jani.webanalyzer.db.StorageConfig
import com.jani.webanalyzer.db.StorageService
import com.jani.webanalyzer.pathprocessor.PathProcessor
import com.jani.webanalyzer.utils.ObjectMapperAware
import com.jani.webanalyzer.ws.configs.ServicesConfiguration
import com.jani.webanalyzer.ws.exceptions.ToHttpErrorExceptionMapper
import com.jani.webanalyzer.ws.services.ResponseDispatcher
import com.jani.webanalyzer.ws.services.WebAnalyzerService
import org.apache.camel.spring.SpringCamelContext
import org.springframework.context.ApplicationContext
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
                ServicesConfiguration,
                WebAnalyzerService,
                ToHttpErrorExceptionMapper,
                WebAnalyzerRoutesBuilder,
                PathProcessor,
                Application,
                StorageConfig,
                StorageService,
                ObjectMapperAware,
                ContentProcessor,
                ResponseDispatcher
        )
        ctx.start()
    }

    @Bean
    static PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer() {
        new PropertySourcesPlaceholderConfigurer()
    }

    @Bean
    SpringCamelContext camelContext(ApplicationContext ctx) {
        with(new SpringCamelContext(ctx)).lastOp { it.setAutoStartup(true) }
    }

    @Bean
    ObjectMapper objectMapper() {
        new ObjectMapper()
    }
}
