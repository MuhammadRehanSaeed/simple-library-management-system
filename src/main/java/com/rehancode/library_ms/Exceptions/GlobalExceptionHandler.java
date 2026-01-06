package com.rehancode.library_ms.Exceptions;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;


@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(UsernameException.class)
    public ResponseEntity<ApiResponse<Object>> handleUsernameException(UsernameException ex){
        ApiResponse<Object> response= ApiResponse.<Object>builder()
        .status(HttpStatus.BAD_REQUEST.value())
        .success(false)
        .message(ex.getMessage())
        .data(null)
        .build();
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);

        }
            @ExceptionHandler(EmailException.class)
    public ResponseEntity<ApiResponse<Object>> handleEmailException(EmailException ex) {
        ApiResponse<Object> response= ApiResponse.<Object>builder()
        .status(HttpStatus.BAD_REQUEST.value())
        .success(false)
        .message(ex.getMessage())
        .data(null)
        .build();

        return ResponseEntity.status(400).body(response);
      
    }
        @ExceptionHandler(MethodArgumentNotValidException.class)
public ResponseEntity<ApiResponse<Object>> handleValidationException(
        MethodArgumentNotValidException ex) {

    Map<String, String> errors = new HashMap<>();

    ex.getBindingResult().getFieldErrors().forEach(error -> {
        errors.put(error.getField(), error.getDefaultMessage());
    });

    ApiResponse<Object> response = ApiResponse.<Object>builder()
            .status(HttpStatus.BAD_REQUEST.value())
            .success(false)
            .message("Validation failed")
            .data(errors)
            .build();

    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
}
    

}
