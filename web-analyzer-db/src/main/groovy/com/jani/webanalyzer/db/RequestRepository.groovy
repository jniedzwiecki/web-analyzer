package com.jani.webanalyzer.db

import com.jani.webanalyzer.request.BaseRequest
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository
/**
 * Created by jacekniedzwiecki on 03.04.2017.
 */
@Repository
interface RequestRepository extends CrudRepository<BaseRequest, UUID> {

}
