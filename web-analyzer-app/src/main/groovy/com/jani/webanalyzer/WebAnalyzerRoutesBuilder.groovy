package com.jani.webanalyzer

import groovy.transform.CompileStatic
import org.apache.camel.spring.SpringCamelContext
import org.apache.camel.spring.SpringRouteBuilder
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component

import javax.annotation.PostConstruct

import static org.apache.activemq.camel.component.ActiveMQComponent.activeMQComponent
/**
 * Created by jacekniedzwiecki on 24.03.2017.
 */
@Component
@CompileStatic
class WebAnalyzerRoutesBuilder extends SpringRouteBuilder {

    String addPathsReqEndpoint
    String addSinglePathEndpoint
    SpringCamelContext camelContext

    @Autowired
    WebAnalyzerRoutesBuilder(@Value('${activemq.endpoint}') String activeMqEndpoint,
                             @Value('${addPaths.request.endpoint}') String addPathsReqEndpoint,
                             @Value('${addSinglePath.request.endpoint}') String addSinglePathEndpoint,
                             SpringCamelContext camelContext) {
        camelContext.addComponent("activemq", activeMQComponent("vm://localhost?broker.persistent=false"))
        camelContext.start()
        this.camelContext = camelContext

        this.addPathsReqEndpoint = addPathsReqEndpoint
        this.addSinglePathEndpoint = addSinglePathEndpoint
    }

    @PostConstruct
    void addCamelRoutes() {
        camelContext.addRoutes(this)
    }

    @Override
    void configure() throws Exception {
        from(addPathsReqEndpoint).log("XXXXXXXXXXXX").to(addSinglePathEndpoint)
    }
}
