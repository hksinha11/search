package com.movies.search.exception;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;


import com.movies.search.message.ResponseMessage;

@ControllerAdvice
@Slf4j
public class FileUploadExceptionAdvice extends ResponseEntityExceptionHandler {

    @ExceptionHandler(MaxUploadSizeExceededException.class)
    public ResponseEntity handleMaxSizeException(MaxUploadSizeExceededException exc) {
        return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new ResponseMessage("File too large!"));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity handleGenricException(Exception e) {
        log.error("Exception", e);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ResponseMessage("Something went wrong"));
    }

    @ExceptionHandler(UnsupportedColumnException.class)
    public ResponseEntity handleUnsupportedColumnException(UnsupportedColumnException e) {
        log.error("Exception", e);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseMessage("Unsupported column name"));
    }
}