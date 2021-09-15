package com.dbl.nsl.erp.exception;

import java.util.Date;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<?> resourceNotFoundException(ResourceNotFoundException ex, WebRequest request) {
         ErrorDetails errorDetails = new ErrorDetails(new Date(), ex.getMessage(), request.getDescription(false));
//    	ErrorDetails errorDetails = new ErrorDetails(ex.getMessage());
         return new ResponseEntity<>(errorDetails, HttpStatus.NOT_FOUND);
    }
    
    @ExceptionHandler(ForbiddenException.class)
    public ResponseEntity<?> forbiddenExcpetion(ForbiddenException ex, WebRequest request) {
        ErrorDetails errorDetails = new ErrorDetails(new Date(), ex.getMessage(), request.getDescription(false));
//    	ErrorDetails errorDetails = new ErrorDetails(ex.getMessage());
//        return new ResponseEntity<>(errorDetails, HttpStatus.INTERNAL_SERVER_ERROR);
        return new ResponseEntity<>(errorDetails, HttpStatus.FORBIDDEN);
//    	return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
    }
    
    @ExceptionHandler(ResourceAlreadyExists.class)
    public ResponseEntity<?> resourceAlreadyExists(ResourceAlreadyExists ex, WebRequest request) {
//        ExceptionResponse response=new ExceptionResponse();
//        response.setErrorCode("CONFLICT");
//        response.setErrorMessage(ex.getMessage());
//        response.setTimestamp(LocalDateTime.now());
//    	ErrorDetails errorDetails = new ErrorDetails(ex.getMessage());
    	ErrorDetails errorDetails = new ErrorDetails(new Date(), ex.getMessage(), request.getDescription(false));
    	

        return new ResponseEntity<>(errorDetails, HttpStatus.CONFLICT);
    }
    
    @ExceptionHandler(UnauthorizedException.class)
    public ResponseEntity<?> unauthorizedException(UnauthorizedException ex, WebRequest request) {
//        ExceptionResponse response=new ExceptionResponse();
//        response.setErrorCode("UNAUTHORIZED");
//        response.setErrorMessage(ex.getMessage());
//        response.setTimestamp(LocalDateTime.now());
//    	ErrorDetails errorDetails = new ErrorDetails(ex.getMessage());
    	ErrorDetails errorDetails = new ErrorDetails(new Date(), ex.getMessage(), request.getDescription(false));

        return new ResponseEntity<>(errorDetails, HttpStatus.UNAUTHORIZED);
    }
    
    
//  @ExceptionHandler(Exception.class)
//  public ResponseEntity<?> globleExcpetionHandler(Exception ex, WebRequest request) {
////      ErrorDetails errorDetails = new ErrorDetails(new Date(), ex.getMessage(), request.getDescription(false));
//  	ErrorDetails errorDetails = new ErrorDetails("Not authenticated");
//  	System.out.println(ex.getMessage());
////      return new ResponseEntity<>(errorDetails, HttpStatus.INTERNAL_SERVER_ERROR);
//      return new ResponseEntity<>(errorDetails, HttpStatus.UNAUTHORIZED);
////  	return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
//  }

}
