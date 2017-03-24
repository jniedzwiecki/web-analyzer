package com.jani.webanalyzer.services

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

import static com.jani.webanalyzer.util.FluentBuilder.with
import static javax.ws.rs.core.Response.Status.CREATED
import static org.slf4j.LoggerFactory.getLogger
/**
 * Created by jacekniedzwiecki on 06.03.2017.
 */
@Service
@CompileStatic
@PropertySource('classpath:web-analyzer-ws.properties')
class WebAnalyzerService implements WebAnalyzer {

    private static Logger logger = getLogger(WebAnalyzerService.class)

    private Session session
    private MessageProducer producer
    private Queue queue

    @Autowired
    WebAnalyzerService(@Value('${activemq.endpoint}') String activeMqEndpoint,
                       @Value('${addPaths.request.endpoint}') String addPathsReqEndpoint) {

        session = with(new ActiveMQConnectionFactory(activeMqEndpoint).createConnection())
                .op { it.start() }
                .op { it.setExceptionListener(new ExceptionListener() {

                        @Override
                        void onException(JMSException exception) {
                            logger.debug(exception.stackToString())
                        }
                    }
                )
                }.andGet { it.createSession(true, Session.AUTO_ACKNOWLEDGE) }

        queue = session.createQueue(addPathsReqEndpoint)

        producer = session.createProducer(queue)
        producer.setDeliveryMode(DeliveryMode.PERSISTENT)
    }

    @Override
    AddResponse add(AddRequest addRequest) {
        producer.send(messageOf(addRequest.paths))

        AddResponse.response(CREATED)
    }

    @Override
    GetResponse get(int id) {
//        return GetResponse.response(paths.get(id))
        null
    }

    private Message messageOf(String message) {
        with(session.createMessage())
                .lastOp { it.setObjectProperty("message", message) }
    }
}
