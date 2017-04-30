package com.jani.webanalyzer.contentprocessor

import com.jani.webanalyzer.db.RequestRepository
import com.jani.webanalyzer.model.request.AddSinglePathRequest
import com.jani.webanalyzer.model.request.GetPathRequest
import com.jani.webanalyzer.utils.JmsAware
import com.jani.webanalyzer.utils.ObjectMapperAware
import groovy.transform.CompileStatic
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.PropertySource
import org.springframework.stereotype.Service

import javax.jms.*

import static com.jani.webanalyzer.model.reponse.GetPathResponse.getPathResponse
/**
 * Created by jacekniedzwiecki on 18.04.2017.
 */
@Service
@PropertySource('classpath:web-analyzer-ws.properties')
@CompileStatic
class ContentProcessor implements JmsAware, MessageListener, ObjectMapperAware {

    private String activeBrokerUrl
    private MessageConsumer messageConsumer
    private MessageProducer messageProducer
    private RequestRepository requestRepository

    @Autowired
    ContentProcessor(@Value('${activemq.broker.url}') String activeBrokerUrl,
                     @Value('${getPath.request.processor.endpoint}') String getPathReqProcessorEndpoint,
                     @Value('${getPath.response.processor.endpoint}') String getPathResProcessorEndpoint,
                     RequestRepository requestRepository) {
        this.activeBrokerUrl = activeBrokerUrl
        this.requestRepository = requestRepository

        messageConsumer = createMessageConsumer getPathReqProcessorEndpoint
        messageConsumer.setMessageListener this

        messageProducer = createMessageProducer getPathResProcessorEndpoint
    }

    @Override
    String getActiveBrokerUrl() {
        return activeBrokerUrl
    }

    @Override
    void onMessage(Message message) {
        def getPathRequest = objectMapper.readValue((message as TextMessage).text, GetPathRequest)
        AddSinglePathRequest addSinglePathRequest = requestRepository.findByUuid(getPathRequest.pathUuid) as AddSinglePathRequest

        def getPathResponse = objectMapper.writeValueAsString(
                getPathResponse(getPathRequest.uuid, addSinglePathRequest.path, addSinglePathRequest.state,
                null))

        messageProducer.send(createTextMessage(getPathResponse))
    }
}
