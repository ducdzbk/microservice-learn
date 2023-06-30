package com.fds.inventoryservice.exception;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.sql.Timestamp;
import java.util.Date;

@Setter
@Getter
@ResponseStatus(HttpStatus.NOT_FOUND)
public class NotfoundException extends BaseApiException {

    private static final long serialVersionUID = 1L;

    public String timestamp = new Timestamp(new Date().getTime()).toString();

    public Integer status = HttpStatus.NOT_FOUND.value();

    public String error = HttpStatus.NOT_FOUND.name();

    public Object errors;

    public String message;

    public String trace;

    public NotfoundException(Object errors, String message, String trace) {

        super(message);

        this.errors = errors;
        this.message = message;
        this.trace = trace;

    }

    @Override
    public int code() {
        return HttpStatus.NOT_FOUND.value();
    }

}
