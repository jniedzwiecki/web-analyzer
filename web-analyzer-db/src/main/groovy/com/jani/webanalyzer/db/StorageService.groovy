package com.jani.webanalyzer.db

import com.jani.webanalyzer.request.AddSinglePathRequest
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

    AddSinglePathRequest storeRequest(AddSinglePathRequest request) {
        requestRepository.save(request)
    }
}
