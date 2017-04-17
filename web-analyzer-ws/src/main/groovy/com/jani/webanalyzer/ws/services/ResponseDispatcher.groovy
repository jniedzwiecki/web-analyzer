package com.jani.webanalyzer.ws.services

import com.fasterxml.jackson.databind.ObjectMapper
import com.jani.webanalyzer.model.reponse.BaseResponse
import com.jani.webanalyzer.model.request.BaseRequest

import javax.jms.Message
import javax.jms.MessageListener
import javax.jms.TextMessage
import java.util.concurrent.CompletableFuture
import java.util.concurrent.ConcurrentHashMap

/**
 * Created by jacekniedzwiecki on 17.04.2017.
 */
class ResponseDispatcher<REQ extends BaseRequest, RES extends BaseResponse> implements MessageListener {

    private Map<UUID, CompletableFuture<Message>> uuidToFutureMap = new ConcurrentHashMap<>()
    private ObjectMapper objectMapper = new ObjectMapper()

    @Override
    void onMessage(Message message) {
        RES response = objectMapper.readValue((message as TextMessage).text, RES)
        def future = uuidToFutureMap.remove(response.originalRequestUUID)
        future.complete(message)
    }

    RES responseToRequest(REQ getPathRequest) {
        def future = new CompletableFuture<Message>()
        uuidToFutureMap.put(getPathRequest.uuid, future)
        future.thenApply {
            objectMapper.readValue((it as TextMessage).text, RES)
        } get()
    }
}
