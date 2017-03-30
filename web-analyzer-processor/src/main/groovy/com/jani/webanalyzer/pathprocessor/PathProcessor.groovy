package com.jani.webanalyzer.pathprocessor

import org.apache.activemq.ActiveMQConnectionFactory
import org.slf4j.Logger
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.PropertySource
import org.springframework.stereotype.Service

import javax.jms.MessageConsumer
import javax.jms.Queue
import javax.jms.Session

import static com.jani.webanalyzer.utils.FluentBuilder.with
import static org.slf4j.LoggerFactory.getLogger
/**
 * Created by jacekniedzwiecki on 24.03.2017.
 */
@Service
@PropertySource('classpath:web-analyzer-ws.properties')
class PathProcessor {

    private static Logger logger = getLogger(PathProcessor.class)

    Session session
    MessageConsumer consumer
    Queue queue

    @Autowired
    PathProcessor(@Value('${activemq.endpoint}') String activeMqEndpoint,
                  @Value('${addSinglePath.request.endpoint}') String addSinglePathReqEndpoint) {

        session = with(new ActiveMQConnectionFactory("vm://localhost?broker.persistent=false").createConnection())
                .op { it.start() }
                .op { it.setExceptionListener {
                        exception -> logger.debug(exception.stackToString())
                    }
                }
        .andGet { it.createSession(false, Session.AUTO_ACKNOWLEDGE) }

        queue = session.createQueue("addSinglePath.request")

        consumer = session.createConsumer(queue)
        consumer.setMessageListener {
             message -> logger.debug(message.getObjectProperty("message") as String)
        }
    }
}
