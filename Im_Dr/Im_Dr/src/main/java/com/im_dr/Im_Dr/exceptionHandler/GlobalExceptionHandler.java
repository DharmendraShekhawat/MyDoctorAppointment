package com.im_dr.Im_Dr.exceptionHandler;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(DoctorNotFoundException.class)
        public ResponseEntity<String> handleDoctorAuthenticationException(DoctorNotFoundException ex){
        return ResponseEntity.status(HttpStatus.NON_AUTHORITATIVE_INFORMATION).body(ex.getMessage());
    }
}
