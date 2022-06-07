package com.cognizant.retailbank.transaction.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/*
 *Rest Controller to Handle Validation Errors
 */

@RestControllerAdvice
public class TransactionErrorController {

	@ResponseStatus(code = HttpStatus.BAD_REQUEST)
	@ExceptionHandler({MethodArgumentNotValidException.class})
	public ResponseEntity<Object> handleEmployeeValidations(MethodArgumentNotValidException exception,HttpServletRequest request){
		HashMap<String, Object> body=new HashMap<>();
		body.put("timestamp", new Date());
		body.put("status", HttpStatus.BAD_REQUEST.value());
		List<String> errMsgs=exception.getBindingResult().getFieldErrors().stream().map(x -> x.getDefaultMessage()).collect(Collectors.toList());
		body.put("errors", errMsgs);
		body.put("path", request.getRequestURI());
		return new ResponseEntity<Object>(body, HttpStatus.BAD_REQUEST);
	}
}
