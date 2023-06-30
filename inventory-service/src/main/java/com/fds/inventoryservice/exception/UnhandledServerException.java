package com.fds.inventoryservice.exception;

import com.fds.flex.common.exception.BaseException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
public class UnhandledServerException extends BaseException {

    private static final long serialVersionUID = 1L;

    public UnhandledServerException(String message) {
        super(message);
    }

    public int code() {
        return HttpStatus.INTERNAL_SERVER_ERROR.value();
    }
}