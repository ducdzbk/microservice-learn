package com.fds.orderservice.exception.handle;//package com.fds.quanlilichlamviec.exception.handle;
//
//import com.fds.flex.common.utility.string.StringPool;
//import com.fds.moti.dto.resp.ErrorModel;
//import com.fds.moti.dto.resp.ExceptionRespDTO;
//import com.fds.moti.exception.*;
//import com.fds.moti.util.MessageUtil;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.MethodArgumentNotValidException;
//import org.springframework.web.bind.annotation.ControllerAdvice;
//import org.springframework.web.bind.annotation.ExceptionHandler;
//import org.springframework.web.bind.annotation.ResponseBody;
//import org.springframework.web.bind.annotation.ResponseStatus;
//
//import javax.validation.ConstraintViolationException;
//import javax.validation.UnexpectedTypeException;
//import java.util.ArrayList;
//import java.util.List;
//
//@ControllerAdvice
//public class ExceptionHandleFactory {
//
//    @ExceptionHandler(BadRequestException.class)
//    @ResponseStatus(HttpStatus.BAD_REQUEST)
//    @ResponseBody
//    public ResponseEntity<?> exceptionHandle(BadRequestException ex) {
//        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ExceptionRespDTO(HttpStatus.BAD_REQUEST.value(),
//                HttpStatus.BAD_REQUEST.name(), ex.errors, ex.message, ex.trace));
//
//    }
//
//    @ExceptionHandler(ConstraintViolationException.class)
//    @ResponseStatus(HttpStatus.BAD_REQUEST)
//    @ResponseBody
//    public ResponseEntity<?> exceptionHandle(ConstraintViolationException ex) {
//
//        String trace = ex.getMessage();// ex.getMessage()
//        String message = "Validation failed ConstraintViolationException";
//        List<ErrorModel> errors = new ArrayList<ErrorModel>();
//        String[] trace_split = trace.split(StringPool.COMMA);
//        for (String trace_i : trace_split) {
//            String[] key_value = trace_i.split(StringPool.COLON);
//            String messageCode = key_value[1];
//
//            ErrorModel error = new ErrorModel();
//            error.setCode(messageCode.trim());
//            error.setMessage(MessageUtil.responseMessage(messageCode.trim()));
//
//            errors.add(error);
//        }
//
//
//        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ExceptionRespDTO(HttpStatus.BAD_REQUEST.value(),
//                HttpStatus.BAD_REQUEST.name(), errors, message, trace));
//
//    }
//
//    @ExceptionHandler(MethodArgumentNotValidException.class)
//    @ResponseStatus(HttpStatus.BAD_REQUEST)
//    @ResponseBody
//    public ResponseEntity<?> exceptionHandle(MethodArgumentNotValidException ex) {
//
//        // JSONObject a = new JSONObject();
//
//        List<ErrorModel> errors = new ArrayList<ErrorModel>();
//
//        if (ex.getFieldErrors() != null) {
//            ex.getFieldErrors().forEach(e -> {
//                ErrorModel error = new ErrorModel();
//                error.setCode(e.getDefaultMessage());
//                error.setField(e.getField());
//                error.setMessage(e.getDefaultMessage());
//
//                errors.add(error);
//            });
//        }
//
//        String trace = StringPool.BLANK;// ex.getMessage()
//
//        String message = "Validation failed for object='" + ex.getObjectName() + "'. Error count: "
//                + ex.getErrorCount();
//
//        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ExceptionRespDTO(HttpStatus.BAD_REQUEST.value(),
//                HttpStatus.BAD_REQUEST.name(), errors, message, trace));
//
//    }
//
//    @ExceptionHandler(UnexpectedTypeException.class)
//    @ResponseStatus(HttpStatus.BAD_REQUEST)
//    @ResponseBody
//    public ResponseEntity<?> exceptionHandle(UnexpectedTypeException ex) {
//        String trace = ex.getMessage();// ex.getMessage()
//        String message = "Validation failed UnexpectedTypeException";
//        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ExceptionRespDTO(HttpStatus.BAD_REQUEST.value(),
//                HttpStatus.BAD_REQUEST.name(), null, message, trace));
//
//    }
//
//    @ExceptionHandler(UnauthorizedException.class)
//    @ResponseStatus(HttpStatus.UNAUTHORIZED)
//    @ResponseBody
//    public ResponseEntity<?> exceptionHandle(UnauthorizedException ex) {
//        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new ExceptionRespDTO(HttpStatus.UNAUTHORIZED.value(),
//                HttpStatus.UNAUTHORIZED.name(), ex.errors, ex.message, ex.trace));
//
//    }
//
//    @ExceptionHandler(ForbiddenException.class)
//    @ResponseStatus(HttpStatus.FORBIDDEN)
//    @ResponseBody
//    public ResponseEntity<?> exceptionHandle(ForbiddenException ex) {
//        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new ExceptionRespDTO(HttpStatus.FORBIDDEN.value(),
//                HttpStatus.FORBIDDEN.name(), ex.errors, ex.message, ex.trace));
//
//    }
//
//    @ExceptionHandler(NotfoundException.class)
//    @ResponseStatus(HttpStatus.NOT_FOUND)
//    @ResponseBody
//    public ResponseEntity<?> exceptionHandle(NotfoundException ex) {
//        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ExceptionRespDTO(HttpStatus.NOT_FOUND.value(),
//                HttpStatus.NOT_FOUND.name(), ex.errors, ex.message, ex.trace));
//
//    }
//
//    @ExceptionHandler(ConflicException.class)
//    @ResponseStatus(HttpStatus.CONFLICT)
//    @ResponseBody
//    public ResponseEntity<?> exceptionHandle(ConflicException ex) {
//        return ResponseEntity.status(HttpStatus.CONFLICT).body(new ExceptionRespDTO(HttpStatus.CONFLICT.value(),
//                HttpStatus.CONFLICT.name(), ex.errors, ex.message, ex.trace));
//
//    }
//
//    @ExceptionHandler(InternalServerException.class)
//    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
//    @ResponseBody
//    public ResponseEntity<?> exceptionHandle(InternalServerException ex) {
//        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
//                .body(new ExceptionRespDTO(HttpStatus.INTERNAL_SERVER_ERROR.value(),
//                        HttpStatus.INTERNAL_SERVER_ERROR.name(), ex.errors, ex.message, ex.trace));
//
//    }
//
//    @ExceptionHandler(ServiceUnavailableException.class)
//    @ResponseStatus(HttpStatus.SERVICE_UNAVAILABLE)
//    @ResponseBody
//    public ResponseEntity<?> exceptionHandle(ServiceUnavailableException ex) {
//        return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE)
//                .body(new ExceptionRespDTO(HttpStatus.SERVICE_UNAVAILABLE.value(),
//                        HttpStatus.SERVICE_UNAVAILABLE.name(), ex.errors, ex.message, ex.trace));
//
//    }
//
//}
