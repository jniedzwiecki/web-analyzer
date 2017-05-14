package com.jani.webanalyzer.ws.services

import com.fasterxml.jackson.databind.ObjectMapper
import com.jani.webanalyzer.model.reponse.BaseResponse
import com.jani.webanalyzer.model.request.BaseRequest
import com.jani.webanalyzer.utils.ObjectMapperAware

import javax.jms.Message
import javax.jms.MessageListener
import javax.jms.TextMessage
import java.util.concurrent.CompletableFuture
import java.util.concurrent.ConcurrentHashMap
/**
 * Created by jacekniedzwiecki on 17.04.2017.
 */
class ResponseDispatcher<REQ extends BaseRequest, RES extends BaseResponse> implements MessageListener, ObjectMapperAware {

    private Map<String, CompletableFuture<Message>> uuidToFutureMap = new ConcurrentHashMap<>()

    ResponseDispatcher(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper
    }

    @Override
    void onMessage(Message message) {
        BaseResponse response = objectMapper.readValue((message as TextMessage).text, BaseResponse)
        def future = uuidToFutureMap.remove(response.originalUuid.toString())
        future.complete(message)
    }

    RES responseForRequest(REQ request, Class<RES> resClass) {
        def future = new CompletableFuture<Message>()
        uuidToFutureMap.put(request.uuid, future)
        future.thenApply {
            objectMapper.readValue((it as TextMessage).text, resClass)
        } get()
    }
}
