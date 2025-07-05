package com.pulse.footballpulse.exception;

import com.pulse.footballpulse.domain.response.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(value = {DataNotFoundException.class})
    @ResponseStatus(value = HttpStatus.NOT_FOUND)
    public ResponseEntity<ApiResponse<Object>> dataNotFoundExceptionHandler(
            DataNotFoundException e) {
        return ResponseEntity
                .status(404)
                .body(
                        ApiResponse.builder()
                        .status(404)
                        .message(e.getMessage())
                        .data(null)
                        .build()
                );
    }

    @ExceptionHandler(value = {NotAcceptableException.class})
    @ResponseStatus(value = HttpStatus.CONFLICT)
    public ResponseEntity<ApiResponse<Object>> notAcceptableException(NotAcceptableException e) {
        return ResponseEntity
                .status(409)
                .body(
                        ApiResponse.builder()
                                .status(409)
                                .message(e.getMessage())
                                .data(null)
                                .build()
                );
    }

    @ExceptionHandler(value = {ForbiddenException.class})
    @ResponseStatus(value = HttpStatus.FORBIDDEN)
    public ResponseEntity<ApiResponse<?>> forbiddenException(ForbiddenException e) {
        return ResponseEntity
                .status(403)
                .body(
                        ApiResponse.builder()
                                .status(409)
                                .message(e.getMessage())
                                .data(null)
                                .build()
                );
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> handleValidationException(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();

        ex.getBindingResult().getFieldErrors().forEach(error ->
                errors.put(error.getField(), error.getDefaultMessage())
        );

        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
    }
}
