package com.jani.webanalyzer

import groovy.transform.CompileStatic
import org.apache.camel.Exchange
import org.apache.camel.Predicate
import org.apache.camel.spring.SpringCamelContext
import org.apache.camel.spring.SpringRouteBuilder
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
/**
 * Created by jacekniedzwiecki on 24.03.2017.
 */
@Component
@CompileStatic
class WebAnalyzerRoutesBuilder extends SpringRouteBuilder {

    String addPathsReqEndpoint
    String addSinglePathEndpoint

    @Autowired
    WebAnalyzerRoutesBuilder(@Value('${activemq.endpoint}') String activeMqEndpoint,
                             @Value('${addPaths.request.endpoint}') String addPathsReqEndpoint,
                             @Value('${addSinglePath.request.endpoint}') String addSinglePathEndpoint,
                             SpringCamelContext camelContext) {
        this.addPathsReqEndpoint = activeMqEndpoint + addPathsReqEndpoint
        this.addSinglePathEndpoint = activeMqEndpoint + addSinglePathEndpoint

        camelContext.addRoutes(this)
    }

    @Override
    void configure() throws Exception {
        from(addPathsReqEndpoint).filter(
            new Predicate() {
                @Override
                boolean matches(Exchange exchange) {
                    return false
                }
            }
        ).to(addSinglePathEndpoint)
    }
}
