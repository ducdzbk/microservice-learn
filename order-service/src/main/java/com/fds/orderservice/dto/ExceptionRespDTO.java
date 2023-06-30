package com.fds.orderservice.dto;

import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;
import java.util.Date;

@Setter
@Getter
public class ExceptionRespDTO {

    public Integer status;

    public String error;

    public Object errors;

    public String timestamp = new Timestamp(new Date().getTime()).toString();

    public String message;

    public String trace;

    public ExceptionRespDTO(Integer status, String error, Object errors, String message, String trace) {
        this.status = status;
        this.error = error;
        this.errors = errors;
        this.message = message;
        this.trace = trace;
    }

}
