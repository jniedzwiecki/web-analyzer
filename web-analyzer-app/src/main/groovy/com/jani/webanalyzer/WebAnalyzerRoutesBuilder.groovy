package com.jani.webanalyzer

import groovy.transform.CompileStatic
import org.apache.camel.spring.SpringCamelContext
import org.apache.camel.spring.SpringRouteBuilder
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component

import javax.annotation.PostConstruct

import static org.apache.activemq.camel.component.ActiveMQComponent.activeMQComponent
import static org.apache.camel.builder.ExpressionBuilder.languageExpression
/**
 * Created by jacekniedzwiecki on 24.03.2017.
 */
@Component
@CompileStatic
class WebAnalyzerRoutesBuilder extends SpringRouteBuilder {

    final static String ACTIVEMQ_PREFIX = "activemq:"

    String addPathsReqEndpoint
    String addSinglePathEndpoint
    SpringCamelContext camelContext

    @Autowired
    WebAnalyzerRoutesBuilder(@Value('${activemq.broker.url}') String activeBrokerUrl,
                             @Value('${addPaths.request.endpoint}') String addPathsReqEndpoint,
                             @Value('${addSinglePath.request.endpoint}') String addSinglePathEndpoint,
                             SpringCamelContext camelContext) {
        camelContext.addComponent("activemq", activeMQComponent(activeBrokerUrl))
        camelContext.tracing = true
        camelContext.start()
        this.camelContext = camelContext

        this.addPathsReqEndpoint = ACTIVEMQ_PREFIX + addPathsReqEndpoint
        this.addSinglePathEndpoint = ACTIVEMQ_PREFIX + addSinglePathEndpoint
    }

    @PostConstruct
    void addCamelRoutes() {
        camelContext.addRoutes(this)
    }

    @Override
    void configure() throws Exception {
        from(addPathsReqEndpoint)
                .split(languageExpression("jsonpath", '$.[*]'))
                .to(addSinglePathEndpoint)
    }
}
