package com.jani.webanalyzer.db

import com.fasterxml.jackson.databind.ObjectMapper
import com.jani.webanalyzer.model.request.BaseRequest
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
/**
 * Created by jacekniedzwiecki on 08.04.2017.
 */
@Service
class StorageService {

    static RequestRepository requestRepository
    static ObjectMapper objectMapper

    @Autowired
    StorageService(RequestRepository requestRepository, ObjectMapper objectMapper) {
        this.requestRepository = requestRepository
        this.objectMapper = objectMapper
    }

    static void saveRequest(BaseRequest request) {
        requestRepository.save(request)
    }
}
