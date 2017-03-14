package webanalyzer.exceptions;

import javax.validation.ConstraintViolationException;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import static webanalyzer.util.FluentBuilder.with;
import static java.util.logging.Logger.getLogger;
import static javax.ws.rs.core.Response.Status.*;

/**
 * Created by jacekniedzwiecki on 12.03.2017.
 */
public class ToHttpErrorExceptionMapper implements ExceptionMapper {

    public static Logger logger = getLogger("ToHttpErrorExceptionMapper");

    private static Map<Class<? extends Throwable>, Response> errorResponseBasedOnException = with(new HashMap<Class<? extends Throwable>, Response>())
            .op(m -> m.put(ConstraintViolationException.class, Response.status(BAD_REQUEST).build()))
            .op(m -> m.put(ArrayIndexOutOfBoundsException.class, Response.status(NOT_FOUND).build()))
            .get();

    @Override
    public Response toResponse(Throwable exception) {
        return errorResponseBasedOnException
                .getOrDefault(with(exception).op(this::storeLog).get(Object::getClass), Response.status(INTERNAL_SERVER_ERROR).build());
    }

    public Throwable storeLog(Throwable throwable) {
        logger.log(Level.WARNING, throwable.toString());
        return throwable;
    }
}
