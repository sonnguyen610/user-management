package com.springboot.user_management.exception;

import com.springboot.user_management.constant.FailureMessage;
import com.springboot.user_management.constant.ValidationMessage;
import com.springboot.user_management.utils.BaseResponse;
import com.springboot.user_management.utils.ResponseFactory;
import org.apache.coyote.BadRequestException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    public <I>ResponseEntity<BaseResponse<I>> exceptionHandler(Exception ex) {
        return ResponseFactory.error(HttpStatus.INTERNAL_SERVER_ERROR, null, FailureMessage.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(BadRequestException.class)
    public <I>ResponseEntity<BaseResponse<I>> badRequestExceptionHandler(BadRequestException ex) {
        return ResponseFactory.error(HttpStatus.BAD_REQUEST, null, ex.getMessage());
    }

    @ExceptionHandler(ValidationException.class)
    public <I>ResponseEntity<BaseResponse<Map<String, String>>> validationExceptionHandler(ValidationException ex) {
        return ResponseFactory.error(HttpStatus.BAD_REQUEST, ex.getDatas(), ex.getMessage());
    }
}
