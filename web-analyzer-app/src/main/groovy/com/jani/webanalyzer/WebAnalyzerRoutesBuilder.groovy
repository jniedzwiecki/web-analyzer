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
    final static String STORE_REQUEST_ENDPOINT = "direct:storeRequest"

    String addPathsReqEndpoint
    String addSinglePathEndpoint
    String getPathReqEndpoint
    String getPathResEndpoint
    String getPathReqProcessorEndpoint
    String getPathResProcessorEndpoint
    SpringCamelContext camelContext
    static ObjectMapper objectMapper = new ObjectMapper()
    StorageService storageService

    @Autowired
    WebAnalyzerRoutesBuilder(@Value('${activemq.broker.url}') String activeBrokerUrl,
                             @Value('${addPaths.request.endpoint}') String addPathsReqEndpoint,
                             @Value('${addSinglePath.request.endpoint}') String addSinglePathEndpoint,
                             @Value('${getPath.request.endpoint}') String getPathReqEndpoint,
                             @Value('${getPath.response.endpoint}') String getPathResEndpoint,
                             @Value('${getPath.request.processor.endpoint}') String getPathReqProcessorEndpoint,
                             @Value('${getPath.response.processor.endpoint}') String getPathResProcessorEndpoint,
                             SpringCamelContext camelContext) {
        camelContext.addComponent("activemq", activeMQComponent(activeBrokerUrl))
        camelContext.tracing = true
        camelContext.start()
        this.camelContext = camelContext

        this.addPathsReqEndpoint = ACTIVEMQ_PREFIX + addPathsReqEndpoint
        this.addSinglePathEndpoint = ACTIVEMQ_PREFIX + addSinglePathEndpoint
        this.getPathReqEndpoint = ACTIVEMQ_PREFIX + getPathReqEndpoint
        this.getPathResEndpoint = ACTIVEMQ_PREFIX + getPathResEndpoint
        this.getPathReqProcessorEndpoint = ACTIVEMQ_PREFIX + getPathReqProcessorEndpoint
        this.getPathResProcessorEndpoint = ACTIVEMQ_PREFIX + getPathResProcessorEndpoint
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
                .wireTap(STORE_REQUEST_ENDPOINT)
                .process(objectToJSonProcessor)
                .to(addSinglePathEndpoint)

        from(STORE_REQUEST_ENDPOINT)
                .bean(storageService, 'saveRequest')

        from(getPathReqEndpoint)
                .to(getPathReqProcessorEndpoint)
        from(getPathResProcessorEndpoint)
                .to(getPathResEndpoint)
    }

    static final Processor singlePathExtractingProcessor = { exchange ->
        List<AddSinglePathRequest> singlePathRequests =
                with(objectMapper.readValue(exchange.getIn().getBody(String), AddPathsRequest)).map {
                    AddPathsRequest req -> req.paths.stream()
                            .map { String path -> new AddSinglePathRequest(req.id, path) }
                            .collect(toList())
                }
        exchange.getOut().setBody(singlePathRequests)
    }

    static final Processor objectToJSonProcessor = { exchange ->
        exchange.getOut().setBody(objectMapper.writeValueAsString(exchange.getIn().getBody()))
    }
}
