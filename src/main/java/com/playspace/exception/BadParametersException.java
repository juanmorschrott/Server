package com.playspace.exception;

public class BadParametersException extends Exception {
	
	public BadParametersException(String errorMessage) {
		super(errorMessage);
	}
	
	public BadParametersException(String errorMessage, Throwable err) {
	    super(errorMessage, err);
	}
	
}
