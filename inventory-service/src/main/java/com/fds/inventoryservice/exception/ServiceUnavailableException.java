package com.fds.inventoryservice.exception;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.sql.Timestamp;
import java.util.Date;

@Setter
@Getter
@ResponseStatus(HttpStatus.SERVICE_UNAVAILABLE)
public class ServiceUnavailableException extends BaseApiException {

    private static final long serialVersionUID = 1L;

    public String timestamp = new Timestamp(new Date().getTime()).toString();

    public Integer status = HttpStatus.SERVICE_UNAVAILABLE.value();

    public String error = HttpStatus.SERVICE_UNAVAILABLE.name();

    public Object errors;

    public String message;

    public String trace;

    public ServiceUnavailableException(Object errors, String message, String trace) {

        super(message);

        this.errors = errors;
        this.message = message;
        this.trace = trace;

    }

    @Override
    public int code() {
        return HttpStatus.SERVICE_UNAVAILABLE.value();
    }

}
