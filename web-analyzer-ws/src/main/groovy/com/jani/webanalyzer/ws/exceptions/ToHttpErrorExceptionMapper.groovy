package com.jani.webanalyzer.ws.exceptions

import groovy.transform.CompileStatic
import org.slf4j.Logger

import javax.validation.ConstraintViolationException
import javax.ws.rs.core.Response
import javax.ws.rs.ext.ExceptionMapper

import static com.jani.webanalyzer.utils.FluentBuilder.with
import static javax.ws.rs.core.Response.Status.*
import static org.slf4j.LoggerFactory.getLogger
/**
 * Created by jacekniedzwiecki on 12.03.2017.
 */
@CompileStatic
class ToHttpErrorExceptionMapper implements ExceptionMapper {

    public static Logger logger = getLogger(ToHttpErrorExceptionMapper.class)

    private static Map<Class<? extends Throwable>, Response> errorResponseBasedOnException = with(new HashMap<Class<? extends Throwable>, Response>())
            .op { it.put(ConstraintViolationException.class, Response.status(BAD_REQUEST).build()) }
            .op { it.put(ArrayIndexOutOfBoundsException.class, Response.status(NOT_FOUND).build()) }
            .get()

    Response toResponse(Throwable exception) {
        return errorResponseBasedOnException
                .getOrDefault(with(exception).op {storeLog(it)} .map { it.getClass() }, Response.status(INTERNAL_SERVER_ERROR).build())
    }

    private static Throwable storeLog(Throwable throwable) {
        logger.info(throwable.stackToString())
        return throwable
    }
}
