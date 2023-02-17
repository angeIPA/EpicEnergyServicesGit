package com.epic.energyservices.exception;

public class AppServiceException extends RuntimeException{

	private static final long serialVersionUID = 1L;

	
	public AppServiceException() {
		super();
	}
	
	public AppServiceException(Throwable e) {
		super(e);
	}
}
