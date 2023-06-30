package com.fds.inventoryservice.exception;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.sql.Timestamp;
import java.util.Date;

@Setter
@Getter
@ResponseStatus(HttpStatus.FORBIDDEN)
public class ForbiddenException extends BaseApiException {

    private static final long serialVersionUID = 1L;

    public String timestamp = new Timestamp(new Date().getTime()).toString();

    public Integer status = HttpStatus.FORBIDDEN.value();

    public String error = HttpStatus.FORBIDDEN.name();

    public Object errors;

    public String message;

    public String trace;

    public ForbiddenException(Object errors, String message, String trace) {

        super(message);

        this.errors = errors;
        this.message = message;
        this.trace = trace;

    }

    @Override
    public int code() {
        return HttpStatus.FORBIDDEN.value();
    }

}
