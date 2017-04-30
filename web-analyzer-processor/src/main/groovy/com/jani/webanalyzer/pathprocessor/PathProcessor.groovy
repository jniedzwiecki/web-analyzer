package com.jani.webanalyzer.pathprocessor

import com.jani.webanalyzer.utils.JmsAware
import com.jani.webanalyzer.utils.ObjectMapperAware
import com.jani.webanalyzer.utils.StatefulRequest
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.PropertySource
import org.springframework.stereotype.Service

import javax.jms.Message
import javax.jms.MessageConsumer
import javax.jms.MessageListener
import javax.jms.TextMessage
/**
 * Created by jacekniedzwiecki on 24.03.2017.
 */
@Service
@PropertySource('classpath:web-analyzer-ws.properties')
class PathProcessor implements JmsAware, ObjectMapperAware, MessageListener {

    MessageConsumer consumer
    String activeBrokerUrl

    @Autowired
    PathProcessor(@Value('${activemq.broker.url}') String activeBrokerUrl,
                  @Value('${addSinglePath.request.endpoint}') String addSinglePathReqEndpoint) {
        this.activeBrokerUrl = activeBrokerUrl

        consumer = createMessageConsumer(addSinglePathReqEndpoint)
        consumer.setMessageListener this
    }

    @Override
    String getActiveBrokerUrl() {
        return activeBrokerUrl
    }

    @Override
    void onMessage(Message message) {
        def singlePathRequest = objectMapper.readValue((message as TextMessage).text, StatefulRequest)
        println(singlePathRequest)
    }
}
