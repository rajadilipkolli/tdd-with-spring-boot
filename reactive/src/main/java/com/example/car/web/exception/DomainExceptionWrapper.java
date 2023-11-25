package com.example.car.web.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.web.error.ErrorAttributeOptions;
import org.springframework.boot.web.reactive.error.DefaultErrorAttributes;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;

import java.util.Map;

@Component
public class DomainExceptionWrapper extends DefaultErrorAttributes {

    private static final Logger log = LoggerFactory.getLogger(DomainExceptionWrapper.class);

    @Override
    public Map<String, Object> getErrorAttributes(ServerRequest request, ErrorAttributeOptions includeStackTrace) {
        final var error = getError(request);
        final var errorAttributes = super.getErrorAttributes(request, includeStackTrace);
//        errorAttributes.put(ErrorAttribute.TRACE_ID.value, tracer.traceId());
        if (error instanceof DomainException exception) {
            log.error("Caught an instance of: {}, err: {}", DomainException.class, error);
            errorAttributes.replace(ErrorAttribute.STATUS.value, exception.getStatus().value());
            errorAttributes.replace(ErrorAttribute.ERROR.value, exception.getStatus().getReasonPhrase());
            errorAttributes.replace("message", exception.getMessage());
            return errorAttributes;
        }
        return errorAttributes;
    }


    enum ErrorAttribute {
        STATUS("status"),
        ERROR("error"),
        TRACE_ID("traceId");

        private final String value;

        ErrorAttribute(String value) {
            this.value = value;
        }
    }
}
