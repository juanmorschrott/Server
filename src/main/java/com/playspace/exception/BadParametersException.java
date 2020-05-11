package com.playspace.exception;

public class BadParametersException extends Exception {
	
	private static final long serialVersionUID = 1L;

	public BadParametersException(String errorMessage) {
		super(errorMessage);
	}
	
	public BadParametersException(String errorMessage, Throwable err) {
	    super(errorMessage, err);
	}
	
}
