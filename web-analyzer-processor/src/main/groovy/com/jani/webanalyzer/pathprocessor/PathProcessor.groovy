package com.jani.webanalyzer.pathprocessor

import com.jani.webanalyzer.model.request.AddSinglePathRequest
import com.jani.webanalyzer.utils.JmsAware
import com.jani.webanalyzer.utils.ObjectMapperAware
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.PropertySource
import org.springframework.stereotype.Service

import javax.jms.*

import static com.jani.webanalyzer.model.reponse.AddSinglePathResponse.addSinglePathResponse

/**
 * Created by jacekniedzwiecki on 24.03.2017.
 */
@Service
@PropertySource('classpath:web-analyzer-ws.properties')
class PathProcessor implements JmsAware, ObjectMapperAware, MessageListener {

    MessageConsumer messageConsumer
    MessageProducer messageProducer
    String activeBrokerUrl

    @Autowired
    PathProcessor(@Value('${activemq.broker.url}') String activeBrokerUrl,
                  @Value('${addSinglePath.request.endpoint}') String addSinglePathReqEndpoint,
                  @Value('${addSinglePath.response.endpoint}') String addSinglePathResEndpoint) {
        this.activeBrokerUrl = activeBrokerUrl

        messageConsumer = createMessageConsumer addSinglePathReqEndpoint
        messageConsumer.setMessageListener this

        messageProducer = createMessageProducer addSinglePathResEndpoint
    }

    @Override
    String getActiveBrokerUrl() {
        return activeBrokerUrl
    }

    @Override
    void onMessage(Message message) {
        def addSinglePathRequest = objectMapper.readValue((message as TextMessage).text, AddSinglePathRequest)

        def addSinglePathResponse = objectMapper.writeValueAsString(addSinglePathResponse(addSinglePathRequest.path, addSinglePathRequest.originalUuid.toString()))
        messageProducer.send(createTextMessage(addSinglePathResponse))
    }
}
