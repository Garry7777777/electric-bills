package com.skypro.bills.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@ControllerAdvice
public class ControllerAdviceEx {

        @ExceptionHandler(IncorrectValueException.class)
        public ResponseEntity<HttpStatus> incorrectValue(){
            return ResponseEntity.badRequest().build();
        }

        @ExceptionHandler(ValueNotFoundException.class)
        public ResponseEntity<HttpStatus> notFoundValue(){
            return ResponseEntity.notFound().build();
        }

}

