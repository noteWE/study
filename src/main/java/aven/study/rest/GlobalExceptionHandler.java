package aven.study.rest;

import aven.study.exceptions.StudyException;
import aven.study.models.ResponseException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(StudyException.class)
    public ResponseEntity<ResponseException> exceptionHandler(StudyException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ResponseException.builder()
                        .message(e.getMessage())
                        .build());
    }
}
