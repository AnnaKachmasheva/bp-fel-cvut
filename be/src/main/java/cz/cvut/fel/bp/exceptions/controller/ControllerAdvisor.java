package cz.cvut.fel.bp.exceptions.controller;

import cz.cvut.fel.bp.exceptions.*;
import cz.cvut.fel.bp.exceptions.model.ErrorDetails;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ControllerAdvisor {

    @ExceptionHandler({
            DeletedException.class,
            PasswordNotMatchesException.class,
            AlreadyExistException.class,
            AlreadyCompleteException.class})
    ResponseEntity<ErrorDetails> handlerNoUserFoundException(CustomException e) {
        ErrorDetails response = new ErrorDetails(e.getErrorCode(), e.getMessage());
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(NotFoundException.class)
    ResponseEntity<ErrorDetails> handlerNoUserFoundException(NotFoundException e) {
        ErrorDetails response = new ErrorDetails(e.getErrorCode(), e.getMessage());
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(PermissionDeniedException.class)
    ResponseEntity<ErrorDetails> handlerPermissionDeniedException(PermissionDeniedException e) {
        ErrorDetails response = new ErrorDetails(e.getErrorCode(), e.getMessage());
        return new ResponseEntity<>(response, HttpStatus.FORBIDDEN);
    }

}