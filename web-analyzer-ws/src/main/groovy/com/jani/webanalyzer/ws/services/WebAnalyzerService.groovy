package com.jani.webanalyzer.ws.services

import com.fasterxml.jackson.databind.ObjectMapper
import com.jani.webanalyzer.model.reponse.GetPathResponse
import com.jani.webanalyzer.model.request.BaseRequest
import com.jani.webanalyzer.model.request.GetPathRequest
import com.jani.webanalyzer.utils.JmsAware
import com.jani.webanalyzer.ws.request.AddPathsRequest as WsAddPathsRequest
import com.jani.webanalyzer.ws.response.AddPathsResponse as WsAddPathsResponse
import com.jani.webanalyzer.ws.response.GetPathResponse as WsGetPathResponse
import groovy.transform.CompileStatic
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.PropertySource
import org.springframework.stereotype.Service

import javax.jms.DeliveryMode
import javax.jms.Message
import javax.jms.MessageConsumer
import javax.jms.MessageProducer

import static com.jani.webanalyzer.model.request.AddPathsRequest.addPathsRequest
import static com.jani.webanalyzer.model.request.GetPathRequest.getPathRequest
import static com.jani.webanalyzer.utils.FluentBuilder.with
import static com.jani.webanalyzer.ws.response.AddPathsResponse.addPathsResponse
import static com.jani.webanalyzer.ws.response.GetPathResponse.getPathResponse
import static javax.ws.rs.core.Response.Status.CREATED
/**
 * Created by jacekniedzwiecki on 06.03.2017.
 */
@Service
@PropertySource('classpath:web-analyzer-ws.properties')
class WebAnalyzerService implements WebAnalyzer, JmsAware {

    private MessageProducer addPathsProducer
    private MessageProducer getPathProducer
    private MessageConsumer getPathConsumer

    private ObjectMapper objectMapper
    private ResponseDispatcher<GetPathRequest, GetPathResponse> getPathResponseDispatcher
    private String activeBrokerUrl

    @Autowired
    WebAnalyzerService(@Value('${activemq.broker.url}') String activeBrokerUrl,
                       @Value('${addPaths.request.endpoint}') String addPathsReqEndpoint,
                       @Value('${getPath.request.endpoint}') String getPathReqEndpoint,
                       @Value('${getPath.response.endpoint}') String getPathResEndpoint,
                       ObjectMapper objectMapper) {
        this.activeBrokerUrl = activeBrokerUrl
        this.objectMapper = objectMapper
        getPathResponseDispatcher = new ResponseDispatcher<>(objectMapper)

        addPathsProducer = createMessageProducer(addPathsReqEndpoint)
        addPathsProducer.setDeliveryMode(DeliveryMode.NON_PERSISTENT)

        getPathProducer = createMessageProducer(getPathReqEndpoint)
        getPathConsumer = createMessageConsumer(getPathResEndpoint)
        getPathConsumer.setMessageListener {
            getPathResponseDispatcher.onMessage(it)
        }
    }

    @Override
    @CompileStatic
    WsAddPathsResponse add(WsAddPathsRequest addRequest) {
        with(addPathsRequest(addRequest.paths))
                .op { this.addPathsProducer.send(messageOf(it)) }
                .map { addPathsResponse(CREATED, it.uuid) }
    }

    @Override
    @CompileStatic
    WsGetPathResponse getPath(String uuid) {
        GetPathResponse response = with(getPathRequest(uuid))
        .op {
            this.getPathProducer.send(messageOf(it))
        }
        .map {
            this.getPathResponseDispatcher.responseForRequest(it, GetPathResponse)
        }
        getPathResponse(response.originalRequestUUID, response.requestState, response.content)
    }

    private Message messageOf(BaseRequest request) {
        createTextMessage(objectMapper.writeValueAsString(request))
    }

    @Override
    String getActiveBrokerUrl() {
        return activeBrokerUrl
    }
}
