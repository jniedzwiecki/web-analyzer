package com.jani.webanalyzer.services

import com.fasterxml.jackson.databind.ObjectMapper
import com.jani.webanalyzer.model.request.BaseRequest
import com.jani.webanalyzer.request.AddRequest
import com.jani.webanalyzer.response.AddResponse
import com.jani.webanalyzer.response.GetResponse
import groovy.transform.CompileStatic
import org.apache.activemq.ActiveMQConnectionFactory
import org.slf4j.Logger
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.PropertySource
import org.springframework.stereotype.Service

import javax.jms.*

import static com.jani.webanalyzer.model.request.AddPathsRequest.addPathsRequest
import static com.jani.webanalyzer.utils.FluentBuilder.with
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
    private MessageProducer producer
    private Queue queue
    private ObjectMapper objectMapper = new ObjectMapper()

    @Autowired
    WebAnalyzerService(@Value('${activemq.broker.url}') String activeBrokerUrl,
                       @Value('${addPaths.request.endpoint}') String addPathsReqEndpoint) {

        session = with(new ActiveMQConnectionFactory(activeBrokerUrl).createConnection())
                .op { it.start() }
                .op { it.setExceptionListener {
                        exception -> logger.debug(exception.stackToString())
                    }
                }
        .andGet { it.createSession(false, Session.AUTO_ACKNOWLEDGE) }

        queue = session.createQueue(addPathsReqEndpoint)

        producer = session.createProducer(queue)
        producer.setDeliveryMode(DeliveryMode.NON_PERSISTENT)
    }

    @Override
    @CompileStatic
    AddResponse add(AddRequest addRequest) {
        with(addPathsRequest(addRequest.paths))
                .op { this.producer.send(messageOf(it)) }
                .map { AddResponse.response(CREATED, it.uuid) }
    }

    @Override
    GetResponse get(int id) {
//        return GetResponse.response(paths.map(id))
        null
    }

    private Message messageOf(BaseRequest request) {
        with(session.createTextMessage()).lastOp {
            def valueAsString = objectMapper.writeValueAsString(request)
            it.setText(valueAsString)
        }
    }
}
