package com.jani.webanalyzer.db

import com.jani.webanalyzer.model.request.BaseRequest
import org.springframework.data.jpa.repository.JpaRepository
/**
 * Created by jacekniedzwiecki on 03.04.2017.
 */
interface RequestRepository extends JpaRepository<BaseRequest, Long> {

    BaseRequest findByUuid(String uuid)
}
