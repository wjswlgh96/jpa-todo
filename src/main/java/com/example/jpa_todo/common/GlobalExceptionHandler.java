package com.example.jpa_todo.common;

import com.example.jpa_todo.exception.ApplicationException;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ApplicationException.class)
    public ResponseEntity<Map<String, Object>> handleApplicationException(ApplicationException ex) {
        return getErrorResponse(ex.getStatus(), ex.getMessage());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, Object>> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex) {
        String firstErrorMessage = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .findFirst()
                .map(DefaultMessageSourceResolvable::getDefaultMessage)
                .orElseThrow(() -> new IllegalStateException("검증 에러가 반드시 존재해야 합니다."));

        return getErrorResponse(HttpStatus.BAD_REQUEST, firstErrorMessage);
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<Map<String, Object>> handleRuntimeException(RuntimeException ex) {
        return getErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, "알 수 없는 오류가 발생했습니다. 관리자에게 문의 바랍니다.");
    }

    private ResponseEntity<Map<String, Object>> getErrorResponse(HttpStatus status, String message) {
        Map<String, Object> errorResponse = new LinkedHashMap<>();
        errorResponse.put("status", status.name());
        errorResponse.put("code", status.value());
        errorResponse.put("message", message);
        errorResponse.put("timestamp", LocalDateTime.now());

        return new ResponseEntity<>(errorResponse, status);
    }
}
