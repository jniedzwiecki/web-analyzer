package com.jani.webanalyzer.ws.services

import com.fasterxml.jackson.databind.ObjectMapper
import com.jani.webanalyzer.reponse.GetPathResponse
import com.jani.webanalyzer.request.BaseRequest
import com.jani.webanalyzer.request.GetPathRequest
import com.jani.webanalyzer.ws.request.AddPathsRequest as WsAddPathsRequest
import com.jani.webanalyzer.ws.response.AddPathsResponse as WsAddPathsResponse
import com.jani.webanalyzer.ws.response.GetPathResponse as WsGetPathResponse
import groovy.transform.CompileStatic
import org.apache.activemq.ActiveMQConnectionFactory
import org.slf4j.Logger
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.PropertySource
import org.springframework.stereotype.Service

import javax.jms.*

import static com.jani.webanalyzer.request.AddPathsRequest.addPathsRequest
import static com.jani.webanalyzer.request.GetPathRequest.getPathRequest
import static com.jani.webanalyzer.utils.FluentBuilder.with
import static com.jani.webanalyzer.ws.response.GetPathResponse.getPathResponse
import static javax.ws.rs.core.Response.Status.CREATED
import static org.slf4j.LoggerFactory.getLogger
/**
 * Created by jacekniedzwiecki on 06.03.2017.
 */
@Service
@PropertySource('classpath:web-analyzer-ws.properties')
class WebAnalyzerService implements WebAnalyzer {

    private static Logger logger = getLogger(WebAnalyzerService.class)

    private Session session
    private MessageProducer addPathsProducer
    private MessageProducer getPathProducer
    private MessageConsumer getPathConsumer
    private Queue addPathsQueue
    private Queue getPathReqQueue
    private Queue getPathResQueue
    private ObjectMapper objectMapper = new ObjectMapper()

    private ResponseDispatcher<GetPathRequest, GetPathResponse> getPathResponseDispatcher = new ResponseDispatcher<>()

    @Autowired
    WebAnalyzerService(@Value('${activemq.broker.url}') String activeBrokerUrl,
                       @Value('${addPaths.request.endpoint}') String addPathsReqEndpoint,
                       @Value('${getPath.request.endpoint}') String getPathReqEndpoint,
                       @Value('${getPath.response.endpoint}') String getPathResEndpoint) {
        session = with(new ActiveMQConnectionFactory(activeBrokerUrl).createConnection())
                .op { it.start() }
                .op { it.setExceptionListener {
                        exception -> logger.debug(exception.stackToString())
                    }
                }
        .andGet { it.createSession(false, Session.AUTO_ACKNOWLEDGE) }

        addPathsQueue = session.createQueue(addPathsReqEndpoint)
        getPathReqQueue = session.createQueue(getPathReqEndpoint)
        getPathResQueue = session.createQueue(getPathResEndpoint)

        addPathsProducer = session.createProducer(addPathsQueue)
        addPathsProducer.setDeliveryMode(DeliveryMode.NON_PERSISTENT)

        getPathProducer = session.createProducer(getPathReqQueue)
        getPathConsumer = session.createConsumer(getPathResQueue)
        getPathConsumer.setMessageListener {
            getPathResponseDispatcher.onMessage(it)
        }
    }

    @Override
    @CompileStatic
    WsAddPathsResponse add(WsAddPathsRequest addRequest) {
        with(addPathsRequest(addRequest.paths))
                .op { this.addPathsProducer.send(messageOf(it)) }
                .map { WsAddPathsResponse.addPathsResponse(CREATED, it.uuid) }
    }

    @Override
    @CompileStatic
    WsGetPathResponse getPath(UUID uuid) {
        def response = with(getPathRequest(uuid))
                .op { this.addPathsProducer.send(messageOf(it)) }
                .map { getPathResponseDispatcher.responseToRequest(it) }
        getPathResponse(response.originalRequestUUID, response.pathStatus, response.content)
    }

    private Message messageOf(BaseRequest request) {
        with(session.createTextMessage()).lastOp {
            def valueAsString = objectMapper.writeValueAsString(request)
            it.setText(valueAsString)
        }
    }
}
