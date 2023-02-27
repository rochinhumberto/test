package com.example.test.utils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class ArgumentExceptionHandler extends ResponseEntityExceptionHandler {
  
  @Override
  protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers,
      HttpStatus status, WebRequest request) {
    Map<String, List<String>> body = new HashMap<>();
    
    List<String> errors = ex.getBindingResult()
        .getFieldErrors()
        .stream()
        .map(DefaultMessageSourceResolvable::getDefaultMessage)
        .collect(Collectors.toList());

    body.put("errors", errors);

    Map<String, Object> resData = new HashMap<String, Object>();
    resData.put("Mensaje", errors);
    return ResponseHandler.setResponse("FAILURE", resData, HttpStatus.BAD_REQUEST);
  }
}
