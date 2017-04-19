package com.jani.webanalyzer.utils

import org.apache.activemq.ActiveMQConnectionFactory
import org.slf4j.Logger

import javax.jms.MessageConsumer
import javax.jms.MessageProducer
import javax.jms.Queue
import javax.jms.Session
import javax.jms.TextMessage

import static com.jani.webanalyzer.utils.FluentBuilder.with
import static com.jani.webanalyzer.utils.Utils.notNull
import static org.slf4j.LoggerFactory.getLogger

/**
 * Created by jacekniedzwiecki on 19.04.2017.
 */

trait JmsAware {

    private static Logger logger = getLogger(JmsAware.class)

    private Optional<Session> session = Optional.empty()
    private Map<String, Queue> queueMap = new HashMap<>()

    Session session() {
        session.orElseGet { ->
            session = Optional.of(
                    with(new ActiveMQConnectionFactory(notNull(activeBrokerUrl)).createConnection())
                            .op { it.start() }
                            .op {
                        it.setExceptionListener {
                            exception -> logger.debug(exception.stackToString())
                        }
                    }
                    .andGet { it.createSession(false, Session.AUTO_ACKNOWLEDGE) })
            session.get()
        }
    }

    abstract String getActiveBrokerUrl()

    MessageProducer createMessageProducer(String endpointUrl) {
        session().createProducer(queue(endpointUrl))
    }

    MessageConsumer createMessageConsumer(String endpointUrl) {
        session().createConsumer(queue(endpointUrl))
    }

    TextMessage createTextMessage(String text) {
        session().createTextMessage(text)
    }

    private Queue queue(String endpointUrl) {
        if (!queueMap.containsKey(endpointUrl)) queueMap.put(endpointUrl, session().createQueue(endpointUrl))
        queueMap[endpointUrl]
    }
}