package com.epam.lbm.bookservice.exception_handler;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.validation.ConstraintViolationException;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.epam.lbm.bookservice.exceptions.BookDoesNotExistException;
import com.epam.lbm.bookservice.exceptions.DuplicateEntryException;
import com.epam.lbm.bookservice.exceptions.NoBooksAvailableException;

@ControllerAdvice
@RestController
public class BookExceptionHandler {
	
	private Map<String,String> errorFormat(String errorMessage) {
		Map<String, String> errors = new HashMap<>();
		
		errors.put("timestamp", new Date().toString());
		errors.put("status", HttpStatus.BAD_REQUEST.name());
		errors.put("details", errorMessage);
		
		return errors;
	}
	
	@ExceptionHandler
	@ResponseStatus(code = HttpStatus.BAD_REQUEST)
	public Map<String,String> handleInvalidInput(MethodArgumentNotValidException ex){
		StringBuilder errorMessage = new StringBuilder();
		
		ex.getBindingResult().getAllErrors().forEach(error -> {
			errorMessage.append(error.getDefaultMessage()).append(", ");
		});
		
		return errorFormat(errorMessage.toString());
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
	public Map<String,String> handleBookDoesNotExist(BookDoesNotExistException ex) {
		String message = ex.getMessage();
		
		return errorFormat(message);
	}
	
	@ExceptionHandler
	@ResponseStatus(code = HttpStatus.BAD_REQUEST)
	public Map<String,String> handleNoBooksAvailable(NoBooksAvailableException ex) {
		String message = ex.getMessage();
		
		return errorFormat(message);
	}
}
