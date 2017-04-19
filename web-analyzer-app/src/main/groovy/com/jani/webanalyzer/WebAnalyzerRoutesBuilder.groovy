package com.jani.webanalyzer

import com.fasterxml.jackson.databind.ObjectMapper
import com.jani.webanalyzer.db.StorageService
import com.jani.webanalyzer.model.request.AddPathsRequest
import com.jani.webanalyzer.model.request.AddSinglePathRequest
import org.apache.camel.Processor
import org.apache.camel.spring.SpringCamelContext
import org.apache.camel.spring.SpringRouteBuilder
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component

import javax.annotation.PostConstruct

import static com.jani.webanalyzer.utils.FluentBuilder.with
import static java.util.stream.Collectors.toList
import static org.apache.activemq.camel.component.ActiveMQComponent.activeMQComponent
/**
 * Created by jacekniedzwiecki on 24.03.2017.
 */
@Component
class WebAnalyzerRoutesBuilder extends SpringRouteBuilder {

    final static String ACTIVEMQ_PREFIX = "activemq:"

    String addPathsReqEndpoint
    String addSinglePathEndpoint
    SpringCamelContext camelContext
    static ObjectMapper objectMapper = new ObjectMapper()
    StorageService storageService

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

    @Autowired
    void setStorageService(StorageService storageService) {
        this.storageService = storageService
    }

    @Override
    void configure() throws Exception {
        from(addPathsReqEndpoint)
                .process(singlePathExtractingProcessor)
                .split(simple('${body}'))
                .wireTap("direct:storeRequest")
                .process(objectToJSonProcessor)
                .to(addSinglePathEndpoint)

        from("direct:storeRequest")
                .bean(storageService, 'storeRequest')
    }

    static final Processor singlePathExtractingProcessor = { exchange ->
        List<AddSinglePathRequest> singlePathRequests =
                with(objectMapper.readValue(exchange.getIn().getBody(String), AddPathsRequest)).map {
                    AddPathsRequest req -> req.paths.stream()
                            .map { String path -> new AddSinglePathRequest(req.uuid, path) }
                            .collect(toList())
                }
        exchange.getOut().setBody(singlePathRequests)
    }

    static final Processor objectToJSonProcessor = { exchange ->
        exchange.getOut().setBody(objectMapper.writeValueAsString(exchange.getIn().getBody()))
    }
}
