package com.nikhil.electronic.store.exception;

public class BadApiRequest extends RuntimeException {
	
	
	public BadApiRequest() {
		super("Bad Api Request !!");
	}
	
	
	public BadApiRequest(String message) {
		super(message);
	}

}
