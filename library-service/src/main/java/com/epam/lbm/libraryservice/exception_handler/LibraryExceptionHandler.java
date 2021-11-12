package com.epam.lbm.libraryservice.exception_handler;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.validation.ConstraintViolationException;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.epam.lbm.libraryservice.exceptions.DuplicateEntryException;
import com.epam.lbm.libraryservice.exceptions.NoLibraryDetailsException;

@RestControllerAdvice
public class LibraryExceptionHandler {
	
	private Map<String,String> errorFormat(String errorMessage) {
		Map<String, String> errors = new HashMap<>();
		
		errors.put("timestamp", new Date().toString());
		errors.put("status", HttpStatus.BAD_REQUEST.name());
		errors.put("details", errorMessage);
		
		return errors;
	}
	
	@ExceptionHandler
	@ResponseStatus(code = HttpStatus.BAD_REQUEST)
	public Map<String,String> handleInvalidInput(ConstraintViolationException ex){
		String errorMessage = ex.getMessage();
		
		return errorFormat(errorMessage);
	}
	
	@ExceptionHandler
	@ResponseStatus(code = HttpStatus.BAD_REQUEST)
	public Map<String,String> handleDuplicateEntry(DuplicateEntryException ex) {
		String message = ex.getMessage();

		return errorFormat(message);
	}
		
	@ExceptionHandler
	@ResponseStatus(code = HttpStatus.BAD_REQUEST)
	public Map<String,String> handleNoLibraryDetailsAvailable(NoLibraryDetailsException ex) {
		String message = ex.getMessage();
		
		return errorFormat(message);
	}
	
		

}
