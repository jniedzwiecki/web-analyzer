package com.jani.webanalyzer.exceptions;

import org.slf4j.Logger;

import javax.validation.ConstraintViolationException;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.Map;

import static com.jani.webanalyzer.util.FluentBuilder.with;
import static javax.ws.rs.core.Response.Status.*;
import static org.slf4j.LoggerFactory.getLogger;

/**
 * Created by jacekniedzwiecki on 12.03.2017.
 */
public class ToHttpErrorExceptionMapper implements ExceptionMapper {

    public static Logger logger = getLogger(ToHttpErrorExceptionMapper.class);

    private static Map<Class<? extends Throwable>, Response> errorResponseBasedOnException = with(new HashMap<Class<? extends Throwable>, Response>())
            .op(m -> m.put(ConstraintViolationException.class, Response.status(BAD_REQUEST).build()))
            .op(m -> m.put(ArrayIndexOutOfBoundsException.class, Response.status(NOT_FOUND).build()))
            .get();

    @Override
    public Response toResponse(Throwable exception) {
        return errorResponseBasedOnException
                .getOrDefault(with(exception).op(this::storeLog).get(Object::getClass), Response.status(INTERNAL_SERVER_ERROR).build());
    }

    private Throwable storeLog(Throwable throwable) {
        logger.info(stackTraceAsString(throwable));
        return throwable;
    }

    private String stackTraceAsString(Throwable throwable) {
        StringWriter sw = new StringWriter();
        throwable.printStackTrace(new PrintWriter(sw));
        return sw.toString();
    }
}
