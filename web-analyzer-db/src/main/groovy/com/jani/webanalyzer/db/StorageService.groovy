package com.jani.webanalyzer.db

import com.jani.webanalyzer.request.BaseRequest
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

/**
 * Created by jacekniedzwiecki on 08.04.2017.
 */
@Service
class StorageService {

    RequestRepository requestRepository

    @Autowired
    StorageService(RequestRepository requestRepository) {
        this.requestRepository = requestRepository
    }

    def <R extends BaseRequest> R storeRequest(R request) {
        requestRepository.save(request)
    }
}
