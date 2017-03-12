package com.jani.webanalyzer.exceptions;

import javax.validation.ConstraintViolationException;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import java.util.HashMap;
import java.util.Map;

import static com.jani.webanalyzer.util.FluentBuilder.with;
import static java.util.Optional.of;

/**
 * Created by jacekniedzwiecki on 12.03.2017.
 */
public class ToHttpErrorExceptionMapper implements ExceptionMapper {

    private static Map<Class<? extends Throwable>, Response> map = with(new HashMap<Class<? extends Throwable>, Response>())
            .op(m -> m.put(ConstraintViolationException.class, Response.status(Response.Status.BAD_REQUEST).build()))
            .op(m -> m.put(ArrayIndexOutOfBoundsException.class, Response.status(Response.Status.NOT_FOUND).build())).get();

    @Override
    public Response toResponse(Throwable exception) {
        return of(map.get(exception.getClass())).orElse(Response.status(Response.Status.INTERNAL_SERVER_ERROR).build());
    }
}
