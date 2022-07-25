package com.mindbowser.springsecurityassignment.exception;

import java.io.IOException;
import java.nio.file.AccessDeniedException;
import java.util.Date;

import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.client.ResourceAccessException;

import com.mindbowser.springsecurityassignment.Model.ErrorResponse;

@RestControllerAdvice
public class GlobalExceptionHandler {
	
	
	@ExceptionHandler(value = {IOException.class})
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public ErrorResponse badRequest(Exception ex)
	{
		return new ErrorResponse(new Date(), "Bad request", HttpStatus.BAD_REQUEST.value(), ex.getMessage());
	}

	@ExceptionHandler(value = {NotFoundException.class})
	@ResponseStatus(HttpStatus.NOT_FOUND)
	public ErrorResponse unKnownException(Exception ex)
	{
		return new ErrorResponse(new Date(), "Not found", HttpStatus.NOT_FOUND.value(), ex.getMessage());
	}

	@ExceptionHandler(value = {Exception.class})
	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	public ErrorResponse internalServerException(Exception ex)
	{
		return new ErrorResponse(new Date(), "Internal server error",
				HttpStatus.INTERNAL_SERVER_ERROR.value(), ex.getMessage());
	}

	@ExceptionHandler(value = {AccessDeniedException.class})
	@ResponseStatus(HttpStatus.FORBIDDEN)
	public ErrorResponse accessDeniedException(Exception ex)
	{
		return new ErrorResponse(new Date(), "Access denied", HttpStatus.FORBIDDEN.value(), ex.getMessage());
	}
	
	@ExceptionHandler(value = {CustomException.class})
	@ResponseStatus(HttpStatus.ALREADY_REPORTED)
	public ErrorResponse resourceAreadyExistException(Exception ex)
	{
			return new ErrorResponse(new Date()," Resource already Exists ", HttpStatus.ALREADY_REPORTED.value(),ex.getMessage());
	}
	
	@ExceptionHandler(ResourceAccessException.class)
	@ResponseStatus(HttpStatus.NOT_FOUND)
	public ErrorResponse resourceNotFoundException(Exception ex)
	{
		return new ErrorResponse(new Date(),"Resource not found",HttpStatus.NOT_FOUND.value(),ex.getMessage());
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
