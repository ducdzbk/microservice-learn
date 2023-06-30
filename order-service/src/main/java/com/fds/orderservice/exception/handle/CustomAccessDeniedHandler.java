package com.fds.orderservice.exception.handle;//package com.fds.quanlilichlamviec.exception.handle;

import com.fasterxml.jackson.databind.ObjectMapper;

import com.fds.orderservice.dto.ExceptionRespDTO;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;


@Component
public class CustomAccessDeniedHandler implements AccessDeniedHandler {

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response,
                       AccessDeniedException accessDeniedException) throws IOException, ServletException {

        ExceptionRespDTO exceptionRespDTO = new ExceptionRespDTO(HttpStatus.FORBIDDEN.value(),
                HttpStatus.FORBIDDEN.name(), null, accessDeniedException.getMessage(),
                accessDeniedException.getMessage());
        response.setContentType("application/json");

        response.setStatus(HttpStatus.UNAUTHORIZED.value());

        OutputStream out = response.getOutputStream();

        ObjectMapper mapper = new ObjectMapper();

        mapper.writeValue(out, exceptionRespDTO);

        out.flush();

        if (out != null) {
            out.close();
        }
    }

}
