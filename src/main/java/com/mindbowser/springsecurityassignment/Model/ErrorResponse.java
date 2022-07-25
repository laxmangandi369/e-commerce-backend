package com.mindbowser.springsecurityassignment.Model;

import java.util.Date;

public class ErrorResponse {

	private Date timestamp;
	private String message;
	private int errorCode;
	private String errorMessage;
	
	
	
	public ErrorResponse() {
		super();
	}

	public ErrorResponse(Date timestamp, String message, int errorCode, String errorMessage) {
		super();
		this.timestamp = timestamp;
		this.message = message;
		this.errorCode = errorCode;
		this.errorMessage = errorMessage;
	}



	public ErrorResponse(Date timestamp, String message, int errorCode) {
		super();
		this.timestamp = timestamp;
		this.message = message;
		this.errorCode = errorCode;
	}

	public Date getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(Date timestamp) {
		this.timestamp = timestamp;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public int getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(int errorCode) {
		this.errorCode = errorCode;
	}

	public String getErrorMessage() {
		return errorMessage;
	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}
	
	

}
