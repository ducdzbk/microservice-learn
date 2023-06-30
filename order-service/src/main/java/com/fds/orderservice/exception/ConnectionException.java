package com.fds.orderservice.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class ConnectionException extends BaseApiException {

    private static final long serialVersionUID = 1L;

    public String errorCode;

    public String messageCode;

    public ConnectionException(String errorCode, String messageCode, String message) {
        super(message);
        this.errorCode = errorCode;
        this.messageCode = messageCode;
    }

    @Override
    public int code() {
        return HttpStatus.CONFLICT.value();
    }

}
