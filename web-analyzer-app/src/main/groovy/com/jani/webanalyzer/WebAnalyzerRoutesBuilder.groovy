package com.jani.webanalyzer

import com.fasterxml.jackson.databind.ObjectMapper
import com.jani.webanalyzer.db.StorageService
import com.jani.webanalyzer.model.reponse.AddSinglePathResponse
import com.jani.webanalyzer.model.request.AddPathsRequest
import com.jani.webanalyzer.model.request.AddSinglePathRequest
import org.apache.camel.Exchange
import org.apache.camel.Processor
import org.apache.camel.processor.aggregate.AggregationStrategy
import org.apache.camel.spring.SpringCamelContext
import org.apache.camel.spring.SpringRouteBuilder
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component

import javax.annotation.PostConstruct
import javax.ws.rs.core.Response

import static com.jani.webanalyzer.utils.FluentBuilder.with
import static com.jani.webanalyzer.ws.response.AddPathsResponse.addPathsResponse
import static java.util.stream.Collectors.toList
import static org.apache.activemq.camel.component.ActiveMQComponent.activeMQComponent
import static org.apache.camel.builder.script.ScriptBuilder.groovy
/**
 * Created by jacekniedzwiecki on 24.03.2017.
 */
@Component
class WebAnalyzerRoutesBuilder extends SpringRouteBuilder {

    final static String ACTIVEMQ_PREFIX = "activemq:"
    final static String STORE_ADD_SINGLE_PATH_REQUEST_ENDPOINT = "direct:storeRequest"

    String addPathsReqEndpoint
    String addPathsResEndpoint
    String addSinglePathReqEndpoint
    String addSinglePathResEndpoint
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
                             @Value('${addPaths.response.endpoint}') String addPathsResEndpoint,
                             @Value('${addSinglePath.request.endpoint}') String addSinglePathReqEndpoint,
                             @Value('${addSinglePath.response.endpoint}') String addSinglePathResEndpoint,
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
        this.addPathsResEndpoint = ACTIVEMQ_PREFIX + addPathsResEndpoint
        this.addSinglePathReqEndpoint = ACTIVEMQ_PREFIX + addSinglePathReqEndpoint
        this.addSinglePathResEndpoint = ACTIVEMQ_PREFIX + addSinglePathResEndpoint
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
                .wireTap(STORE_ADD_SINGLE_PATH_REQUEST_ENDPOINT)
                .process(objectToJSonProcessor)
                .to(addSinglePathReqEndpoint)

        from(addSinglePathResEndpoint)
                .aggregate(groovy('new groovy.json.JsonSlurper().parseText(request.body).originalUuid'),
                    addSinglePathAggregationStrategy)
                .completionSize(3)
                .process(addSinglePathsToAddPathsProcessor)
                .to(addPathsResEndpoint)

        from(STORE_ADD_SINGLE_PATH_REQUEST_ENDPOINT)
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
                            .map { String path -> new AddSinglePathRequest(req.uuid, path) }
                            .collect(toList())
                }
        exchange.getOut().setBody(singlePathRequests)
    }

    static final Processor objectToJSonProcessor = { exchange ->
        exchange.getOut().setBody(objectMapper.writeValueAsString(exchange.getIn().getBody()))
    }

    static final AggregationStrategy addSinglePathAggregationStrategy = new AggregationStrategy() {
        @Override
        Exchange aggregate(Exchange oldExchange, Exchange newExchange) {
            if (oldExchange == null) return newExchange
            else oldExchange.with {
                in.body = (oldExchange.in.body as String) + ',' + (newExchange.in.body as String)
                it
            }
        }
    }

    static final Processor addSinglePathsToAddPathsProcessor = new Processor() {
        @Override
        void process(Exchange exchange) throws Exception {
            List<Map<String, String>> mapsList = objectMapper.readValue('[' + (exchange.in.body as String) + ']', List)
            List<AddSinglePathResponse> addSinglePathResponses = mapsList.stream().map { it as AddSinglePathResponse }.collect()
            def response = addPathsResponse(Response.Status.CREATED, addSinglePathResponses[0].originalUuid, addSinglePathResponses)
            exchange.out.body = objectMapper.writeValueAsString(response)
        }
    }
}
