package com.fds.inventoryservice.exception.handle;//package com.fds.quanlilichlamviec.exception.handle;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fds.inventoryservice.dto.ExceptionRespDTO;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;

@Component
public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response,
                         AuthenticationException authException) throws IOException, ServletException {
        ExceptionRespDTO exceptionRespDTO = new ExceptionRespDTO(HttpStatus.UNAUTHORIZED.value(),
                HttpStatus.UNAUTHORIZED.name(), null, authException.getMessage(), authException.getMessage());

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
