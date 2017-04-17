package com.jani.webanalyzer.db

import com.jani.webanalyzer.model.request.BaseRequest
import org.springframework.data.repository.CrudRepository
/**
 * Created by jacekniedzwiecki on 03.04.2017.
 */
interface RequestRepository extends CrudRepository<BaseRequest, UUID> {

}
