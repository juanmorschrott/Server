package com.playspace.exception;

public class IncorrectUserIdException extends Exception {
	
	private static final long serialVersionUID = 1L;

	public IncorrectUserIdException(String errorMessage) {
		super(errorMessage);
	}
	
	public IncorrectUserIdException(String errorMessage, Throwable err) {
	    super(errorMessage, err);
	}
	
}
